package gob.sis.simos.service.impl;

import gob.sis.simos.R;
import gob.sis.simos.controller.Result;
import gob.sis.simos.controller.ResultType;
import gob.sis.simos.db.DBHelper;
import gob.sis.simos.dto.Receta;
import gob.sis.simos.entity.Cuenta;
import gob.sis.simos.entity.Encuesta01;
import gob.sis.simos.entity.Insumo;
import gob.sis.simos.entity.Medicamento;
import gob.sis.simos.entity.Respuesta;
import gob.sis.simos.entity.VerificacionPago;
import gob.sis.simos.soap.SimosSoapServices;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import roboguice.inject.ContextSingleton;

import android.content.Context;

import com.google.inject.Inject;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;

@ContextSingleton
public class EncuestaServiceImpl {

	private DBHelper dbhelper;
	
	@Inject
	private Context context;
	
	@Inject
	private LoginServiceImpl loginService;
	
	public Result saveEncuesta(Encuesta01 encuesta){
		try {
			encuesta.setCreated(new Date());
			encuesta.setFormularioId("F1");
			encuesta.setEncuestaGrupo(1);
			Dao<Encuesta01, Integer> encuestaDao = getHelper().getEncuestaDao();
			
			long count_id = encuestaDao.countOf()+1;
			Cuenta account = loginService.getStoredAccount();
			String yyDDMM = new SimpleDateFormat("yyMMdd").format(Calendar.getInstance().getTime());
			
			String format_id = String.format("G%s%s%s%03d", encuesta.getFormularioId(), account.getUserId(), yyDDMM, count_id);
			encuesta.setNroCuestionario(format_id);
			
			encuestaDao.create(encuesta);
			
			List<Respuesta> rspts = new ArrayList<Respuesta>();
			rspts.addAll(encuesta.getDatosEncuestado());
			
			for(int i = 0; i < encuesta.getVerificaciones().size() ; i++){
				VerificacionPago vf = encuesta.getVerificaciones().get(i);
				rspts.addAll(vf.getRespuestas());
			}
			int rc_count=0;
			for(int i = 0; i < encuesta.getRecetas().size(); i++){
				Receta rc = encuesta.getRecetas().get(i);
				if(rc != null){
					rc_count++;
					Respuesta rpIdReceta = new Respuesta();
					rpIdReceta.setPreguntaId(21);
					rpIdReceta.setOpcionRespuestaId(99);
					rpIdReceta.setRespuestaTexto(rc_count+"");
					rspts.add(rpIdReceta);
					
					Respuesta rpTipoReceta = new Respuesta();
					rpTipoReceta.setPreguntaId(22);
					rpTipoReceta.setPreguntaParentId(21);
					rpTipoReceta.setOpcionRespuestaId(rc.getTipoRecetaId());
					rspts.add(rpTipoReceta);
					
					for(int x = 0; x < rc.getMedicamentos().size() ; x++){
						Medicamento me = rc.getMedicamentos().get(x);
						// Comercial
						if(me.getNombre().equals(Medicamento.COMERCIAL)){
							Respuesta rpMedName = new Respuesta();
							rpMedName.setPreguntaId(23);
							rpMedName.setPreguntaParentId(21);
							rpMedName.setOpcionRespuestaId(102);
							rpMedName.setRespuestaTexto(me.getNombre());
							rpMedName.setPrescripcionId("9999999");
							rspts.add(rpMedName);
							
							Respuesta rpMedProductType = new Respuesta();
							rpMedProductType.setPreguntaId(24);
							rpMedProductType.setPreguntaParentId(23);
							rpMedProductType.setOpcionRespuestaId(109);
							rspts.add(rpMedProductType);
							
							Respuesta rpPrescribed = new Respuesta();
							rpPrescribed.setPreguntaId(25);
							rpPrescribed.setPreguntaParentId(23);
							rpPrescribed.setOpcionRespuestaId(105);
							rpPrescribed.setRespuestaNumero(me.getRecetado()*1.0);
							rspts.add(rpPrescribed);
							
						// Generico
						} else {
							Respuesta rpMedName = new Respuesta();
							rpMedName.setPreguntaId(23);
							rpMedName.setPreguntaParentId(21);
							rpMedName.setOpcionRespuestaId(102);
							rpMedName.setRespuestaTexto(me.getNombre());
							rpMedName.setPrescripcionId(me.getId());
							rspts.add(rpMedName);
							
							Respuesta rpMedProductType = new Respuesta();
							rpMedProductType.setPreguntaId(24);
							rpMedProductType.setPreguntaParentId(23);
							rpMedProductType.setOpcionRespuestaId(103);
							rspts.add(rpMedProductType);
							
							Respuesta rpPrescribed = new Respuesta();
							rpPrescribed.setPreguntaId(25);
							rpPrescribed.setPreguntaParentId(23);
							rpPrescribed.setOpcionRespuestaId(105);
							rpPrescribed.setRespuestaNumero(me.getRecetado()*1.0);
							rspts.add(rpPrescribed);
							
							Respuesta rpRecieved = new Respuesta();
							rpRecieved.setPreguntaId(26);
							rpRecieved.setPreguntaParentId(23);
							rpRecieved.setOpcionRespuestaId(106);
							rpRecieved.setRespuestaNumero(me.getEntregado()*1.0);
							rspts.add(rpRecieved);
						}
					}
					
					for(int x = 0; x < rc.getInsumos().size() ; x++){
						Insumo ins = rc.getInsumos().get(x);
						
						Respuesta rpSuppyName = new Respuesta();
						rpSuppyName.setPreguntaId(23);
						rpSuppyName.setPreguntaParentId(21);
						rpSuppyName.setOpcionRespuestaId(102);
						rpSuppyName.setRespuestaTexto(ins.getNombre());
						rpSuppyName.setPrescripcionId(ins.getId());
						rspts.add(rpSuppyName);
							
						Respuesta rpSupplyProductType = new Respuesta();
						rpSupplyProductType.setPreguntaId(24);
						rpSupplyProductType.setPreguntaParentId(23);
						rpSupplyProductType.setOpcionRespuestaId(104);
						rspts.add(rpSupplyProductType);
							
						Respuesta rpPrescribed = new Respuesta();
						rpPrescribed.setPreguntaId(25);
						rpPrescribed.setPreguntaParentId(23);
						rpPrescribed.setOpcionRespuestaId(105);
						rpPrescribed.setRespuestaNumero(ins.getRecetado()*1.0);
						rspts.add(rpPrescribed);
						
						Respuesta rpRecieved = new Respuesta();
						rpRecieved.setPreguntaId(26);
						rpRecieved.setPreguntaParentId(23);
						rpRecieved.setOpcionRespuestaId(106);
						rpRecieved.setRespuestaNumero(ins.getEntregado()*1.0);
						rspts.add(rpRecieved);
					}
				}
			}
			
			Encuesta01 foundEncuesta = null;
			QueryBuilder builder = encuestaDao.queryBuilder();
			builder.setWhere(builder.where().eq("nro_encsta", format_id));
			foundEncuesta = encuestaDao.queryForFirst(builder.prepare());
			
			Dao<Respuesta, Integer> respuestaDao = getHelper().getRespuestaDao();
			if(rspts.size() > 0){
				for(int i = 0; i < rspts.size(); i++){
					Respuesta rp = rspts.get(i);
					rp.setEncuestadoId(foundEncuesta.getId());
					respuestaDao.create(rp);
				}
			}
			
			SimosSoapServices services = new SimosSoapServices(context);
			String response = services.sendEncuesta(encuesta);
			if(response.equals("success")){
				foundEncuesta.setSent(1);
				encuestaDao.update(foundEncuesta);
				Result res = new Result();
				res.setMessage(context.getResources().getString(R.string.msg_send_encuesta_succeeded));
				res.resultType = ResultType.SUCCEED;
				return res;
			} else if(response.equals("failed")){
				foundEncuesta.setSent(0);
				encuestaDao.update(foundEncuesta);
				Result res = new Result();
				res.setMessage(context.getResources().getString(R.string.msg_send_encuesta_failed));
				res.resultType = ResultType.FAILED;
				return res;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			Result res = new Result();
			res.setMessage(context.getResources().getString(R.string.msg_send_encuesta_failed));
			res.resultType = ResultType.FAILED;
			return res;
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
