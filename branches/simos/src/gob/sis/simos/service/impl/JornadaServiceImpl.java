package gob.sis.simos.service.impl;

import java.sql.SQLException;

import gob.sis.simos.db.DBHelper;
import gob.sis.simos.entity.Jornada;
import android.content.Context;

import com.google.inject.Inject;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;

import roboguice.inject.ContextSingleton;

@ContextSingleton
public class JornadaServiceImpl {

	@Inject
	private Context context;
	private DBHelper dbhelper;
	
	public void setJornada(Jornada jornada){
		try {
			Dao<Jornada, Integer> dao = getHelper().getJornadaDao();
			if(dao.countOf() == 0){
				jornada.setId(1);
				dao.create(jornada);
			} else {
				dao.update(jornada);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	public Jornada getJornada(){
		try {
			Dao<Jornada, Integer> dao = getHelper().getJornadaDao();
			Jornada jor = dao.queryForId(1);
			return jor;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public DBHelper getHelper() {
		if (this.dbhelper == null) {
			this.dbhelper = OpenHelperManager.getHelper(context,DBHelper.class);
		}
		return this.dbhelper;
	}

}
