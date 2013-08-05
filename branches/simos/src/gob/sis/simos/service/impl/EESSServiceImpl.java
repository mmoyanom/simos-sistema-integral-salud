package gob.sis.simos.service.impl;

import java.sql.SQLException;
import java.util.List;

import roboguice.inject.ContextSingleton;

import com.google.inject.Inject;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;

import android.content.Context;

import gob.sis.simos.ListaEstablecimientoSaludActivity;
import gob.sis.simos.db.DBHelper;
import gob.sis.simos.entity.EstablecimientoSalud;
import gob.sis.simos.service.EstablecimientoSaludService;


@ContextSingleton
public class EESSServiceImpl{// implements EESSService {
	
	//private Context _context;
	private DBHelper dbhelper;
	
	@Inject
	private Context context;
	
	/*@Override
	public void setContext(Context context) {
		this._context = context;
	}*/
	
	//@Override
	public List<EstablecimientoSalud> getEESSList() {
		try {
			Dao<EstablecimientoSalud, String> dao = getHelper().getEESSDao();
			List<EstablecimientoSalud> items = dao.queryForAll();
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

}
