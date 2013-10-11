package gob.sis.simos.service.impl;

import java.sql.SQLException;

import gob.sis.simos.db.DBHelper;
import gob.sis.simos.entity.Config;
import android.content.Context;

import com.google.inject.Inject;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;

import roboguice.inject.ContextSingleton;


@ContextSingleton
public class ConfigurationServiceImpl {

	@Inject
	private Context context;
	
	private DBHelper dbhelper;

	public Config getConfiguration(){
		try {
			Dao<Config, Integer> dao = getHelper().getConfig();
			Config c = dao.queryForId(1);
			return c;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public DBHelper getHelper() {
		if (this.dbhelper == null) {
			this.dbhelper = OpenHelperManager.getHelper(context, //this._context,
					DBHelper.class);
		}
		return this.dbhelper;
	}

	public void updateConfiguration(Config cfg) {
		try {
			Dao<Config, Integer> dao = getHelper().getConfig();
			Config c = dao.queryForId(1);
			c.setServer(cfg.getServer());
			dao.update(c);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void createConfiguration(Config cfg) {
		try {
			Dao<Config, Integer> dao = getHelper().getConfig();
			dao.create(cfg);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
}
