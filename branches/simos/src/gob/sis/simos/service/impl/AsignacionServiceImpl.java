package gob.sis.simos.service.impl;

import gob.sis.simos.db.DBHelper;
import gob.sis.simos.entity.Asignacion;
import gob.sis.simos.entity.Cuenta;
import gob.sis.simos.soap.DownloadListOfAsignacionesResult;
import gob.sis.simos.soap.SimosSoapServices;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.List;

import roboguice.inject.ContextSingleton;
import android.content.Context;

import com.google.inject.Inject;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.table.TableUtils;


@ContextSingleton
public class AsignacionServiceImpl{
	
	private DBHelper dbhelper;
	
	@Inject
	private Context context;
	
	@Inject
	private LoginServiceImpl loginService;
	
	public List<Asignacion> getAsignacionList() {
		try {
			Dao<Asignacion, Integer> dao = getHelper().getAsignacionDao();
			List<Asignacion> items = dao.queryForAll();
			return items;
		} catch (SQLException e) {
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
	
	public DownloadListOfAsignacionesResult downloadAsignaciones(){
		Cuenta account = loginService.getStoredAccount();
		SimosSoapServices service = new SimosSoapServices(context);
		DownloadListOfAsignacionesResult result = service.getAsignaciones(account.getUserId(),Calendar.getInstance().getTime());
		if(result.isSuccess()){
			try {
				if(result.getData().size() > 0){
					Dao<Asignacion, Integer> asDao = getHelper().getAsignacionDao();
					TableUtils.clearTable(asDao.getConnectionSource(), Asignacion.class);
					for(Asignacion as : result.getData()){
						asDao.create(as);
					}
				} else {
					result.setErrorMessage("No hay establecimientos asignados para hoy.");
					result.setUpdateMessage(result.getErrorMessage());
					result.setSuccess(false);
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
		result.setSuccess(false);
		return result;
	}

	public void cleanAsignaciones() {
		try {
			Dao<Asignacion, Integer> asDao = getHelper().getAsignacionDao();
			TableUtils.clearTable(asDao.getConnectionSource(), Asignacion.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
