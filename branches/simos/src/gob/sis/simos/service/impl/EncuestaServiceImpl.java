package gob.sis.simos.service.impl;

import gob.sis.simos.R;
import gob.sis.simos.application.MyApplication;
import gob.sis.simos.controller.Result;
import gob.sis.simos.controller.ResultType;
import gob.sis.simos.db.DBHelper;
import gob.sis.simos.entity.Cuenta;
import gob.sis.simos.entity.Encuesta01;
import gob.sis.simos.entity.EncuestaGrupo;
import gob.sis.simos.entity.Respuesta;
import gob.sis.simos.soap.DownloadListOfOpcionRespuestaResult;
import gob.sis.simos.soap.SendEncuestaResult;
import gob.sis.simos.soap.SimosSoapServices;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import roboguice.inject.ContextSingleton;
import android.app.Application;
import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.inject.Inject;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.table.TableUtils;

@ContextSingleton
public class EncuestaServiceImpl {

	private DBHelper dbhelper;
	
	@Inject
	private Context context;
	
	@Inject
	private Application application;
	
	@Inject
	private LoginServiceImpl loginService;
	
	@Inject
	private OpcionesRespuestaServiceImpl opcionesRespuestaService;
	
	private Gson gson;
	
	// encuestas almacenadas
	public SendEncuestaResult SendEncuestasNoEnviadas(){
		
		try {
			
			gson = new GsonBuilder()
	        .excludeFieldsWithoutExposeAnnotation()
	        .create();
			
			Dao<EncuestaGrupo, Integer> enctaGrupoDao = getHelper().getEncuestaGrupoDao();
			Dao<Encuesta01, Integer> encuestaDao = getHelper().getEncuestaDao();

			QueryBuilder<Encuesta01, Integer> builder = encuestaDao.queryBuilder();
			
			List<EncuestaGrupo> grupos = enctaGrupoDao.queryForAll();
			
			JsonArray array = new JsonArray();
			
			List<Encuesta01> enctas = new ArrayList<Encuesta01>();
			
			for (EncuestaGrupo eg : grupos) {
				builder.setWhere(builder.where().eq("sent", 0).and().eq("group_id", eg.getId()));
				enctas = encuestaDao.query(builder.prepare());
				JsonArray json_array = new JsonArray();
				JsonParser parser = new JsonParser();
				for (Encuesta01 e : enctas) {
					JsonElement json_obj = parser.parse(e.getJson());
					json_array.add(json_obj);
				}
				JsonElement json_eg = parser.parse(gson.toJson(eg));
				JsonObject json_obj = new JsonObject();
				json_obj.add("group", json_eg);
				json_obj.add("encta", json_array);
				
				array.add(json_obj);
			}
			
			SimosSoapServices services = new SimosSoapServices(context);
			String str = array.toString();
			SendEncuestaResult result = services.sendEncuestas(str);
			
			if(result.getResult() == SendEncuestaResult.SUCCEEDED) {
				for (Encuesta01 e : enctas) {
					e.setSent(1);
					encuestaDao.update(e);
				}
				/*
				TableUtils.clearTable(getHelper().getConnectionSource(), Respuesta.class);
				TableUtils.clearTable(getHelper().getConnectionSource(), Encuesta01.class);
				TableUtils.clearTable(getHelper().getConnectionSource(), EncuestaGrupo.class);
				*/
			}
			System.gc();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			SendEncuestaResult rslt = new SendEncuestaResult();
			rslt.setErrorMessage(context.getResources().getString(R.string.msg_send_encuesta_failed));
			rslt.setResult(SendEncuestaResult.FAILED);
			return rslt;
		}
	}
	
	public SendEncuestaResult save(Encuesta01 encta){
		
		try {
			
			Cuenta account = loginService.getStoredAccount();
			
			MyApplication app = (MyApplication)application;
			
			Dao<EncuestaGrupo, Integer> enctaGrupoDao = getHelper().getEncuestaGrupoDao();
			QueryBuilder<EncuestaGrupo, Integer> enctaGrupoBuilder = enctaGrupoDao.queryBuilder();
			Calendar c = Calendar.getInstance();
			
			enctaGrupoBuilder.setWhere(enctaGrupoBuilder
			.where().eq("id_eess", app.getAsignacion().getEstablecimientoId())
			.and().eq("id_encta", app.getFormulario())
			.and().eq("id_enctdor", account.getUserId())
			.and().eq("fec_encta_yyyymmdd", new SimpleDateFormat("yyyyMMdd",java.util.Locale.getDefault()).format(c.getTime())));
			
			EncuestaGrupo grupo = enctaGrupoDao.queryForFirst(enctaGrupoBuilder.prepare());
			
			if(grupo == null){
				grupo = new EncuestaGrupo();
				grupo.setEncuestadorId(Integer.parseInt(account.getUserId()));
				grupo.setEncuestaId(app.getFormulario());
				grupo.setEstablecimientoId(app.getAsignacion().getEstablecimientoId());
				grupo.setFechaEncuesta(c.getTime());
				grupo.setFechaEncuestaYYYYMMDD(new SimpleDateFormat("yyyyMMdd",java.util.Locale.getDefault()).format(c.getTime()));
				enctaGrupoDao.create(grupo);
				
				grupo = enctaGrupoDao.queryForMatching(grupo).get(0);
			}
			
			Dao<Encuesta01, Integer> enctaDao = getHelper().getEncuestaDao();
			
			long count = enctaDao.countOf();
			String yyDDMM = new SimpleDateFormat("yyMMdd",java.util.Locale.getDefault()).format(Calendar.getInstance().getTime());
			String formulario = String.format("F%s", app.getFormulario());
			String gestor_id = String.format("%03d", Integer.parseInt(account.getUserId()));
			String count_id = String.format("%03d", count+1);
			String format_id = String.format("G%s%s%s%s", formulario, gestor_id, yyDDMM, count_id);
			
			encta.setFormularioId(String.format("%s", app.getFormulario()));
			encta.setEncuestaGrupo(grupo.getId());
			encta.setNroCuestionario(format_id);
			
			enctaDao.create(encta);

			Encuesta01 foundEncuesta = null;
			QueryBuilder<Encuesta01, Integer> builder = enctaDao.queryBuilder();
			builder.setWhere(builder.where().eq("nro_encsta", format_id));
			foundEncuesta = enctaDao.queryForFirst(builder.prepare());

			Dao<Respuesta, Integer> respuestaDao = getHelper().getRespuestaDao();
			if(encta.getRespuestas().size() > 0){
				for(int i = 0; i < encta.getRespuestas().size(); i++){
					Respuesta rp = encta.getRespuestas().get(i);
					rp.setEncuestadoId(foundEncuesta.getId());
					respuestaDao.create(rp);
					if(rp.getChild()!=null){
						List<Respuesta> rp_child = rp.getChild();
						for(int x = 0;x < rp_child.size();x++){
							Respuesta rpx = rp_child.get(x);
							rpx.setEncuestadoId(foundEncuesta.getId());
							respuestaDao.create(rpx);
							if(rpx.getChild() != null){
								List<Respuesta> rpx_child = rpx.getChild();
								for(int y=0; y < rpx_child.size();y++){
									Respuesta rpy = rpx_child.get(y);
									rpy.setEncuestadoId(foundEncuesta.getId());
									respuestaDao.create(rpy);
								}
							}
						}
					}
				}
			}
			
			gson = new GsonBuilder()
	        .excludeFieldsWithoutExposeAnnotation()
	        .create();
			
			SendEncuestaResult result = new SendEncuestaResult();
			result.setResult(SendEncuestaResult.SUCCEEDED);
			
			foundEncuesta.setSent(0);
			foundEncuesta.setJson(gson.toJson(encta));
			enctaDao.update(foundEncuesta);
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			SendEncuestaResult rslt = new SendEncuestaResult();
			rslt.setErrorMessage(context.getResources().getString(R.string.msg_send_encuesta_failed));
			rslt.setResult(SendEncuestaResult.FAILED);
			return rslt;
		}
	}
	
	/*
	public SendEncuestaResult saveEncuesta(Encuesta01 encuesta, int foo){
		try {
			encuesta.setCreated(new Date());
			encuesta.setFormularioId("F1");
			encuesta.setEncuestaGrupo(3);
			Dao<Encuesta01, Integer> encuestaDao = getHelper().getEncuestaDao();
			
			long count_id = encuestaDao.countOf()+1;
			Cuenta account = loginService.getStoredAccount();
			String yyDDMM = new SimpleDateFormat("yyMMdd",java.util.Locale.getDefault()).format(Calendar.getInstance().getTime());
			
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
			QueryBuilder<Encuesta01, Integer> builder = encuestaDao.queryBuilder();
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
			//SendEncuestaResult result = services.sendEncuesta(encuesta);
			SendEncuestaResult result = services.sendEncuesta(null);
			if(result.getResult()==SendEncuestaResult.SUCCEEDED){
				foundEncuesta.setSent(1);
				encuestaDao.update(foundEncuesta);
				return result;
			} else {
				foundEncuesta.setSent(0);
				encuestaDao.update(foundEncuesta);
				result.setErrorMessage("Error : La encuesta no ha sido enviada. ".concat(result.getErrorMessage()));
				return result;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			SendEncuestaResult rslt = new SendEncuestaResult();
			rslt.setErrorMessage(context.getResources().getString(R.string.msg_send_encuesta_failed));
			rslt.setResult(SendEncuestaResult.FAILED);
			return rslt;
		}
	}
	*/
	
	public List<Encuesta01> findAll() throws SQLException {
		
		Dao<Encuesta01, Integer> encuestaDao = getHelper().getEncuestaDao();
		List<Encuesta01> items = encuestaDao.queryForAll();
		return items;
		
	}
	
	public List<Encuesta01> findAllByDate(Date date) throws SQLException {
		Dao<Encuesta01, Integer> encuestaDao = getHelper().getEncuestaDao();
		QueryBuilder<Encuesta01, Integer> builder = encuestaDao.queryBuilder();
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND,1);
		Date dateInit = c.getTime();
		
		c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.HOUR_OF_DAY,23);
		c.set(Calendar.MINUTE,59);
		c.set(Calendar.SECOND,59);
		Date dateFinal = c.getTime();
		
		builder.setWhere(builder.where().between("created", dateInit, dateFinal));
		List<Encuesta01> items = encuestaDao.query(builder.prepare());
		return items;
	}
	
	public List<Encuesta01> findUnsent() throws SQLException {
		
		Dao<Encuesta01, Integer> encuestaDao = getHelper().getEncuestaDao();
		List<Encuesta01> items = encuestaDao.queryForEq("sent",0);
		return items;
		
	}
	
	public List<Encuesta01> findUnsentByDate(Date date) throws SQLException {
		
		Dao<Encuesta01, Integer> encuestaDao = getHelper().getEncuestaDao();
		QueryBuilder<Encuesta01, Integer> builder = encuestaDao.queryBuilder();
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND,1);
		Date dateInit = c.getTime();
		
		c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.HOUR_OF_DAY,23);
		c.set(Calendar.MINUTE,59);
		c.set(Calendar.SECOND,59);
		Date dateFinal = c.getTime();
		
		builder.setWhere(builder.where().eq("sent", 0).and().between("created", dateInit,dateFinal));
		List<Encuesta01> items = encuestaDao.query(builder.prepare());
		return items;
		
	}

	public List<Encuesta01> findSent() throws SQLException {
		
		Dao<Encuesta01, Integer> encuestaDao = getHelper().getEncuestaDao();
		List<Encuesta01> items = encuestaDao.queryForEq("sent",1);
		return items;
		
	}
	
	public List<Encuesta01> findSentByDate(Date date) throws SQLException {
		
		Dao<Encuesta01, Integer> encuestaDao = getHelper().getEncuestaDao();
		QueryBuilder<Encuesta01, Integer> builder = encuestaDao.queryBuilder();
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND,1);
		Date dateInit = c.getTime();
		
		c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.HOUR_OF_DAY,23);
		c.set(Calendar.MINUTE,59);
		c.set(Calendar.SECOND,59);
		Date dateFinal = c.getTime();
		builder.setWhere(builder.where().eq("sent", 1).and().between("created", dateInit,dateFinal));
		List<Encuesta01> items = encuestaDao.query(builder.prepare());
		return items;
		
	}
	
	public DBHelper getHelper() {
		if (this.dbhelper == null) {
			this.dbhelper = OpenHelperManager.getHelper(this.context, //this._context,
					DBHelper.class);
		}
		return this.dbhelper;
	}

	public Result downloadOpcionesRespuesta() {
		Result r = new Result();
		DownloadListOfOpcionRespuestaResult rsDownloadOpcionesRespuestas = opcionesRespuestaService.downloadOpcionesRespuestas();
		if(rsDownloadOpcionesRespuestas.isSuccess()){
			r.resultType = ResultType.SUCCEED;
		} else {
			r.resultType = ResultType.FAILED;
			r.setMessage(rsDownloadOpcionesRespuestas.getErrorMessage());
		}
		return r;
	}

	public void cleanStoredEncuestas() {
		try {
			Dao<Encuesta01, Integer> enctaGrupoDao = getHelper().getEncuestaDao();
			TableUtils.clearTable(enctaGrupoDao.getConnectionSource(), EncuestaGrupo.class);
			
			Dao<Encuesta01, Integer> enctaDao = getHelper().getEncuestaDao();
			TableUtils.clearTable(enctaDao.getConnectionSource(), Encuesta01.class);
			
			Dao<Respuesta, Integer> rsptaDao = getHelper().getRespuestaDao();
			TableUtils.clearTable(rsptaDao.getConnectionSource(), Respuesta.class);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}


}
