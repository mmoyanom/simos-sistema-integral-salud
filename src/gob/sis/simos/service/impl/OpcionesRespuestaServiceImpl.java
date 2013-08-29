package gob.sis.simos.service.impl;

import gob.sis.simos.db.DBHelper;
import gob.sis.simos.entity.OpcionRespuesta;

import java.sql.SQLException;
import java.util.List;

import roboguice.inject.ContextSingleton;
import android.content.Context;

import com.google.inject.Inject;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;

@ContextSingleton
public class OpcionesRespuestaServiceImpl {

	private DBHelper dbhelper;
	
	@Inject
	private Context context;
	
	public List<OpcionRespuesta> getRespuestas(int preguntaId){
		try {
			Dao<OpcionRespuesta, Integer> dao = getHelper().getOpcionesRespuestaDao();
			List<OpcionRespuesta> items = dao.queryForEq("question_id", preguntaId);
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
