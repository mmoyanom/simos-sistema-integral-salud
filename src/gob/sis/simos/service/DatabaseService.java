package gob.sis.simos.service;

import android.content.Context;

public interface DatabaseService {

	public boolean databaseExists();
	public void copyDatabase(Context context) throws Exception;
	
}
