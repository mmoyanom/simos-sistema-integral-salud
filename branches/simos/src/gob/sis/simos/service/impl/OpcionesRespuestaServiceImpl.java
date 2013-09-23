package gob.sis.simos.service.impl;

import gob.sis.simos.db.DBHelper;
import gob.sis.simos.entity.OpcionRespuesta;
import gob.sis.simos.soap.DownloadListOfOpcionRespuestaResult;
import gob.sis.simos.soap.SimosSoapServices;

import java.sql.SQLException;
import java.util.List;

import roboguice.inject.ContextSingleton;
import android.content.Context;

import com.google.inject.Inject;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.table.TableUtils;

@ContextSingleton
public class OpcionesRespuestaServiceImpl {

	private DBHelper dbhelper;
	
	@Inject
	private Context context;
	
	public List<OpcionRespuesta> getOpcionesRespuestas(int preguntaId){
		try {
			Dao<OpcionRespuesta, Integer> dao = getHelper().getOpcionesRespuestaDao();
			List<OpcionRespuesta> items = dao.queryForEq("question_id", preguntaId);
			return items;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public int getRespuestasCount() {
		try {
			Dao<OpcionRespuesta, Integer> dao = getHelper().getOpcionesRespuestaDao();
			return (int)dao.countOf();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	public DownloadListOfOpcionRespuestaResult downloadOpcionesRespuestas(){
		
		SimosSoapServices service = new SimosSoapServices(context);
		DownloadListOfOpcionRespuestaResult result = service.getOpcionesRespuestas();
		
		if(result.isSuccess()){
			try {
				Dao<OpcionRespuesta, Integer> opDao = getHelper().getOpcionesRespuestaDao();
				TableUtils.clearTable(opDao.getConnectionSource(), OpcionRespuesta.class);
				for (OpcionRespuesta or : result.getData()) {
					opDao.create(or);
				}
				return result;
			} catch (SQLException e) {
				e.printStackTrace();
				result.setData(null);
				result.setErrorMessage(e.getMessage());
				result.setSuccess(false);
				return result;
			}
		}
		return result;
	} 
	
	public DBHelper getHelper() {
		if (this.dbhelper == null) {
			this.dbhelper = OpenHelperManager.getHelper(this.context, //this._context,
					DBHelper.class);
		}
		return this.dbhelper;
	}

	public void cleanRespuestas() {
		try {
			Dao<OpcionRespuesta, Integer> opDao = getHelper().getOpcionesRespuestaDao();
			TableUtils.clearTable(opDao.getConnectionSource(), OpcionRespuesta.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	
}
