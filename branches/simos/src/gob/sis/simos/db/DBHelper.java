package gob.sis.simos.db;

import gob.sis.simos.entity.Account;
import gob.sis.simos.entity.EESS;
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
	private Dao<Account, String> accountDao;
	private Dao<EESS, String> EESSDao;

	public DBHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		// super(context,
		// Environment.getExternalStorageDirectory()+File.separator+DATABASE_NAME,
		// null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase arg0, ConnectionSource connectionSource) {
		try{
			
			TableUtils.createTableIfNotExists(connectionSource,Account.class);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource,
			int arg2, int arg3) {
		onCreate(db, connectionSource);
	}

	public Dao<Account, String> getAccountDao() throws SQLException {
		if (accountDao == null) {
			accountDao = getDao(Account.class);
		}
		return accountDao;
	}
	
	public Dao<EESS, String> getEESSDao() throws SQLException {
		if(EESSDao == null){
			EESSDao = getDao(EESS.class);
		}
		return EESSDao;
	}

	@Override
	public void close() {
		super.close();
		accountDao = null;
		EESSDao = null;
	}

	public static void copyDataBase(Context context) throws IOException {
		String path = AppProperties.getProperty(context, "path").concat("databases");
		File dir = new File(path);
		if (!dir.exists())
			dir.mkdir();
		InputStream myInput = context.getAssets().open(DATABASE_NAME);
		/*
		 * InputStream myInput2 = context.getAssets().open("database_ab");
		 * InputStream myInput3 = context.getAssets().open("database_ac");
		 */
		String outFileName = path.concat("/simos.sqlite");
		OutputStream myOutput = new FileOutputStream(outFileName);
		byte[] buffer = new byte[1024];
		int length;
		while ((length = myInput.read(buffer)) > 0) {
			myOutput.write(buffer, 0, length);
		}/*
		 * while ((length = myInput2.read(buffer))>0){ myOutput.write(buffer, 0,
		 * length); } while ((length = myInput3.read(buffer))>0){
		 * myOutput.write(buffer, 0, length); }
		 */
		myOutput.flush();
		myOutput.close();
		myInput.close();
		// myInput2.close();
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
