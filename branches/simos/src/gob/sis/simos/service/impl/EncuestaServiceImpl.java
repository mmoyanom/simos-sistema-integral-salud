package gob.sis.simos.service.impl;

import gob.sis.simos.db.DBHelper;
import gob.sis.simos.entity.Encuesta01;
import gob.sis.simos.entity.Respuesta;
import gob.sis.simos.entity.VerificacionPago;
import gob.sis.simos.soap.SimosSoapServices;

import java.util.Date;
import java.util.List;

import android.content.Context;

import com.google.inject.Inject;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;

public class EncuestaServiceImpl {

	private DBHelper dbhelper;
	
	@Inject
	private Context context;
	
	public String saveEncuesta(Encuesta01 encuesta){
		try {
			encuesta.setCreated(new Date());
			
			Dao<Encuesta01, Integer> encuestaDao = getHelper().getEncuestaDao();
			encuestaDao.create(encuesta);
			
			List<Respuesta> rspts = encuesta.getDatosEncuestado();
			
			for(int i = 0; i < encuesta.getVerificaciones().size() ; i++){
				VerificacionPago vf = encuesta.getVerificaciones().get(i);
				rspts.addAll(vf.getRespuestas());
			}
			
			Dao<Respuesta, Integer> respuestaDao = getHelper().getRespuestaDao();
			
			SimosSoapServices services = new SimosSoapServices(context);
			String response = services.sendEncuesta(encuesta);
			if(response.equals("success")){
				encuesta.setSent(1);
				encuestaDao.update(encuesta);
			} else if(response.equals("failed")){
				encuesta.setSent(0);
				encuestaDao.update(encuesta);
				return response;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public DBHelper getHelper() {
		if (this.dbhelper == null) {
			this.dbhelper = OpenHelperManager.getHelper(this.context, //this._context,
					DBHelper.class);
		}
		return this.dbhelper;
	}

}
