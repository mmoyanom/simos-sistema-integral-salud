package gob.sis.simos.service.impl;

import java.sql.SQLException;
import java.util.List;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;

import android.content.Context;

import gob.sis.simos.db.DBHelper;
import gob.sis.simos.entity.EESS;
import gob.sis.simos.service.EESSService;

public class EESSServiceImpl implements EESSService {
	
	private Context _context;
	private DBHelper dbhelper;
	
	@Override
	public void setContext(Context context) {
		this._context = context;
	}
	
	@Override
	public List<EESS> getEESSList() {
		try {
			Dao<EESS, String> dao = getHelper().getEESSDao();
			List<EESS> items = dao.queryForAll();
			return items;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public DBHelper getHelper() {
		if (this.dbhelper == null) {
			this.dbhelper = OpenHelperManager.getHelper(this._context,
					DBHelper.class);
		}
		return this.dbhelper;
	}

}
