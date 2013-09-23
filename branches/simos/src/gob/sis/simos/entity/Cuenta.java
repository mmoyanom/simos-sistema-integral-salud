package gob.sis.simos.entity;

import java.util.Date;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName="account")
public class Cuenta {	
	
	public static final String LOGIN_FIELD_NAME = "user_login";
	public static final String ID_FIELD_NAME = "user_id";
	public static final String PASSWORD_FIELD_NAME = "user_password";
	public static final String LAST_LOGIN = "last_login";

	@DatabaseField(id=true,columnName=LOGIN_FIELD_NAME,unique=true)
	private String username;
	
	@DatabaseField(columnName=ID_FIELD_NAME)
	private String userId;
	
	@DatabaseField(columnName=PASSWORD_FIELD_NAME)
	private String password;
	
	@DatabaseField(columnName=LAST_LOGIN)
	private Date lastLogin;
	
	@DatabaseField(columnName="session_active")
	private boolean sessionActive;
	
	@DatabaseField(columnName="day_finished")
	private boolean dayFinished;
	
	@DatabaseField(columnName="day_started")
	private boolean dayStarted;
	
	@DatabaseField(columnName="date_started")
	private Date dateStarted;
	
	

	public Date getDateStarted() {
		return dateStarted;
	}

	public void setDateStarted(Date dateStarted) {
		this.dateStarted = dateStarted;
	}

	public Date getLastLogin() {
		return lastLogin;
	}

	public void setLastLogin(Date lastLogin) {
		this.lastLogin = lastLogin;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public boolean isSessionActive() {
		return sessionActive;
	}

	public void setSessionActive(boolean sessionActive) {
		this.sessionActive = sessionActive;
	}

	public boolean isDayStarted() {
		return dayStarted;
	}

	public void setDayStarted(boolean dayStarted) {
		this.dayStarted = dayStarted;
	}

	public boolean isDayFinished() {
		return dayFinished;
	}

	public void setDayFinished(boolean dayFinished) {
		this.dayFinished = dayFinished;
	}
	
	
	
}
