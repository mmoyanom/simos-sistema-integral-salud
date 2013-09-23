package gob.sis.simos.service.impl;

import gob.sis.simos.db.DBHelper;
import gob.sis.simos.entity.Cuenta;
import gob.sis.simos.soap.SimosSoapServices;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;

import org.xmlpull.v1.XmlPullParserException;

import roboguice.inject.ContextSingleton;
import android.content.Context;

import com.google.inject.Inject;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.table.TableUtils;

@ContextSingleton
public class LoginServiceImpl{ // implements LoginService {

	@Inject
	private Context activity;
	
	private DBHelper dbhelper;
	
	public String loginFromServer(String username, String password)
			throws IOException, XmlPullParserException {
		SimosSoapServices services = new SimosSoapServices(activity);
		return services.Login(username, password);
	}

	public Cuenta getStoredAccount(String userLogin, String password) {
		return null;
	}

	public Cuenta getStoredAccount() {
		Dao<Cuenta, String> dao;
		try {
			dao = getHelper().getAccountDao();
			QueryBuilder<Cuenta, String> qb = dao.queryBuilder();
			Cuenta account = qb.limit((long)1).queryForFirst();
			return account;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public boolean isSessionActive() {
		boolean isActive = false;
		if(getStoredAccount() != null){
			isActive = getStoredAccount().isSessionActive();
		}
		return isActive;
	}
	
	/*public boolean isDayFinished(){
		boolean isDayFinished = false;
		if(getStoredAccount() != null){
			isDayFinished = getStoredAccount().isDayFinished();
		}
		return isDayFinished;
	}

	public Cuenta setDayFinished(boolean finished) {
		Cuenta cuenta = getStoredAccount();
		cuenta.setDayFinished(finished);
		try {
			Dao<Cuenta, String> dao = getHelper().getAccountDao();
			dao.update(cuenta);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return cuenta;
	}
	
	public boolean isDayStarted(){
		boolean isDayStarted = false;
		if(getStoredAccount() != null){
			isDayStarted = getStoredAccount().isDayStarted();
		}
		return isDayStarted;
	}
	
	public Cuenta setDayStarted(boolean started){
		Cuenta cuenta = getStoredAccount();
		cuenta.setDayStarted(started);
		try {
			Dao<Cuenta, String> dao = getHelper().getAccountDao();
			dao.update(cuenta);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return cuenta;
	}*/
	
	public Cuenta setSessionActive(boolean isActive){
		Cuenta cuenta = getStoredAccount();
		cuenta.setSessionActive(isActive);
		try {
			Dao<Cuenta, String> dao = getHelper().getAccountDao();
			dao.update(cuenta);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return cuenta;
	}

	public Cuenta setDateStarted(Date date) {
		Cuenta cuenta = getStoredAccount();
		cuenta.setDateStarted(date);
		try {
			Dao<Cuenta, String> dao = getHelper().getAccountDao();
			dao.update(cuenta);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return cuenta;
	}

	public Date getDateStarted() {
		return getStoredAccount().getDateStarted();
	}

	public int storeAccount(Cuenta account) {
		Dao<Cuenta, String> dao;
		try {
			dao = getHelper().getAccountDao();
			dao.create(account);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return -1;
	}

	public DBHelper getHelper() {
		if (this.dbhelper == null) {
			this.dbhelper = OpenHelperManager.getHelper(activity, //this._context,
					DBHelper.class);
		}
		return this.dbhelper;
	}

	public void cleanAccount() {
		try {
			Dao<Cuenta, String> dao = getHelper().getAccountDao();
			TableUtils.clearTable(dao.getConnectionSource(), Cuenta.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}


}
