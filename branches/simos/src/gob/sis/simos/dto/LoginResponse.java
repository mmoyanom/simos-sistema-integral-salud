package gob.sis.simos.dto;

import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class LoginResponse {
	
	public static final String INVALID = "ERR";
	public static final String SUCCESS = "GES";
	
	private String SystemId;
	private String ModuleId;
	private String Message;
	private String UserLogin;
	private String UserId;
	
	public String getSystemId() {
		return SystemId;
	}
	public void setSystemId(String systemId) {
		SystemId = systemId;
	}
	public String getModuleId() {
		return ModuleId;
	}
	public void setModuleId(String moduleId) {
		ModuleId = moduleId;
	}
	public String getMessage() {
		return Message;
	}
	public void setMessage(String message) {
		Message = message;
	}
	public String getUserLogin() {
		return UserLogin;
	}
	public void setUserLogin(String userLogin) {
		UserLogin = userLogin;
	}
	public String getUserId() {
		return UserId;
	}
	public void setUserId(String userId) {
		UserId = userId;
	}
	public String getResult(){
		return this.ModuleId;
	}
	
	public void fromJson(String json, Gson gson){
		Type type = new TypeToken<LoginResponse>(){}.getType();
		LoginResponse x = gson.fromJson(json,type);
		this.setSystemId(x.getSystemId());
		this.setModuleId(x.getModuleId());
		this.setMessage(x.getMessage());
		this.setUserId(x.getUserId());
		this.setUserLogin(x.getUserLogin());
	}
	

}
