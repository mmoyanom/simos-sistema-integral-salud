package gob.sis.simos.db;

import gob.sis.simos.entity.Config;
import gob.sis.simos.entity.Cuenta;
import gob.sis.simos.entity.Encuesta01;
import gob.sis.simos.entity.Asignacion;
import gob.sis.simos.entity.EncuestaGrupo;
import gob.sis.simos.entity.Jornada;
import gob.sis.simos.entity.OpcionRespuesta;
import gob.sis.simos.entity.Respuesta;
import gob.sis.simos.resources.AppProperties;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

public class DBHelper extends OrmLiteSqliteOpenHelper {

	private static final String DATABASE_NAME = "simos.sqlite";
	private static final int DATABASE_VERSION = 1;
	private Dao<Jornada, Integer> jornadaDao;
	private Dao<Cuenta, String> accountDao;
	private Dao<Asignacion, Integer> AsignacionDao;
	private Dao<OpcionRespuesta, Integer> opcionesRespuestaDao;
	private Dao<Class<?>, String> medicineDao;
	private Dao<Class<?>, String> supplyDao;
	private Dao<EncuestaGrupo, Integer> encuestaGrupoDao;
	private Dao<Encuesta01, Integer> encuestaDao;
	private Dao<Respuesta, Integer> respuestaDao;
	private Dao<Config, Integer> configDao;

	public DBHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		// super(context,
		// Environment.getExternalStorageDirectory()+File.separator+DATABASE_NAME,
		// null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase arg0, ConnectionSource connectionSource) {
		try{
			
			TableUtils.createTableIfNotExists(connectionSource,Cuenta.class);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource,
			int arg2, int arg3) {
		onCreate(db, connectionSource);
	}
	
	public Dao<?, String> getInsumoDao(Class<?> InsumoClass) throws Exception {
		if(supplyDao == null){
			supplyDao = getDao(InsumoClass);
		} else {
			if(!supplyDao.getDataClass().equals(InsumoClass)){
				supplyDao = getDao(InsumoClass);
			}
		}
		return supplyDao;
	}

	public Dao<?, String> getMedicamentoDao(Class<?> MedicamentoClass) throws Exception {
		if(medicineDao == null){
			medicineDao = getDao(MedicamentoClass);
		} else {
			if(!medicineDao.getDataClass().equals(MedicamentoClass)){
				medicineDao = getDao(MedicamentoClass);
			}
		}
		return medicineDao;
	}
	
	public Dao<Cuenta, String> getAccountDao() throws SQLException {
		if (accountDao == null) {
			accountDao = getDao(Cuenta.class);
		}
		return accountDao;
	}
	
	public Dao<OpcionRespuesta, Integer> getOpcionesRespuestaDao() throws SQLException {
		if (opcionesRespuestaDao == null){
			opcionesRespuestaDao = getDao(OpcionRespuesta.class);
		}
		return opcionesRespuestaDao;
	}
	
	public Dao<Asignacion, Integer> getAsignacionDao() throws SQLException {
		if(AsignacionDao == null){
			AsignacionDao = getDao(Asignacion.class);
		}
		return AsignacionDao;
	}
	
	public Dao<EncuestaGrupo, Integer> getEncuestaGrupoDao() throws SQLException {
		if(encuestaGrupoDao == null){
			encuestaGrupoDao = getDao(EncuestaGrupo.class);
		}
		return encuestaGrupoDao;
	}
	
	public Dao<Encuesta01, Integer> getEncuestaDao() throws SQLException {
		if(encuestaDao == null){
			encuestaDao = getDao(Encuesta01.class);
		}
		return encuestaDao;
	}
	
	public Dao<Respuesta, Integer> getRespuestaDao() throws SQLException {
		if(respuestaDao == null){
			respuestaDao = getDao(Respuesta.class);
		}
		return respuestaDao;
	}
	
	public Dao<Config, Integer> getConfig() throws SQLException {
		if(configDao == null){
			configDao = getDao(Config.class);
		}
		return configDao;
	}
	

	public Dao<Jornada, Integer> getJornadaDao() throws SQLException {
		if(jornadaDao == null){
			jornadaDao = getDao(Jornada.class);
		}
		return jornadaDao;
	}

	@Override
	public void close() {
		super.close();
		accountDao = null;
		AsignacionDao = null;
	}

	public static void copyDataBase(Context context) throws IOException {
		String path = AppProperties.getProperty(context, "path").concat("databases");
		File dir = new File(path);
		if (!dir.exists())
			dir.mkdir();
		InputStream myInput = context.getAssets().open(DATABASE_NAME);
		String outFileName = path.concat("/simos.sqlite");
		OutputStream myOutput = new FileOutputStream(outFileName);
		byte[] buffer = new byte[1024];
		int length;
		while ((length = myInput.read(buffer)) > 0) {
			myOutput.write(buffer, 0, length);
		}
		myOutput.flush();
		myOutput.close();
		myInput.close();
	}

	/*
	 * public boolean importDatabase(String dbPath) throws IOException {
	 * 
	 * close(); File newDb = new File(dbPath); new File() File oldDb = new
	 * File("/data/data/gob.sis.simos/databases/simos.db"); if (newDb.exists())
	 * { FileUtils.copyFile(new FileInputStream(newDb), new
	 * FileOutputStream(oldDb)); // Access the copied database so SQLiteHelper
	 * will cache it and mark // it as created. getWritableDatabase().close();
	 * return true; } return false; }
	 */

	public static boolean databaseExists() {
		SQLiteDatabase checkDB = null;
		try {
			String path = "/data/data/gob.sis.simos/databases/";
			checkDB = SQLiteDatabase.openDatabase(path+DATABASE_NAME, null,
					SQLiteDatabase.OPEN_READONLY);
			checkDB.close();
		} catch (SQLiteException e) {
			// database doesn't exist yet.
		}
		return checkDB != null ? true : false;
	}

}
