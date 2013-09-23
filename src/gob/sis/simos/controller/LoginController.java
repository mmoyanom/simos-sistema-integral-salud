package gob.sis.simos.controller;

import gob.sis.simos.AppSession;
import gob.sis.simos.R;
import gob.sis.simos.dto.LoginResponse;
import gob.sis.simos.entity.Cuenta;
import gob.sis.simos.service.impl.AsignacionServiceImpl;
import gob.sis.simos.service.impl.EncuestaServiceImpl;
import gob.sis.simos.service.impl.LoginServiceImpl;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.xmlpull.v1.XmlPullParserException;

import roboguice.inject.ContextSingleton;
import android.content.Context;
import android.content.Intent;

import com.google.gson.Gson;
import com.google.inject.Inject;

@ContextSingleton
public class LoginController {

	@Inject private Context _context;
	@Inject protected LoginServiceImpl loginService;
	@Inject protected AsignacionServiceImpl asignacionService;
	@Inject protected EncuestaServiceImpl enctaService;
	@Inject protected Gson gson;
	
	
	public boolean isActiveSession(){
		return loginService.isSessionActive();
	}
	
	public void setSessionActive(boolean active){
		AppSession.put(AppSession.ACCOUNT,loginService.setSessionActive(active));
	}
	
	/*public boolean isDayStarted(){
		return loginService.isDayStarted();
	}
	
	public boolean isDayFinished(){
		return loginService.isDayFinished();
	}
	
	public void setDayStarted(boolean started){
		AppSession.put(AppSession.ACCOUNT,loginService.setDayStarted(started));
	}
	
	public void setDayFinished(boolean finished) {
		AppSession.put(AppSession.ACCOUNT,loginService.setDayFinished(finished));
	}
	
	public void setDateStarted(Date date){
		AppSession.put(AppSession.ACCOUNT,this.loginService.setDateStarted(date));
	}
	
	public boolean isDateStarted(Date date){
		boolean isStarted = false;
		Date started = getDateStarted();
		if(started != null){
			String date1 = new SimpleDateFormat("yyyyMMdd").format(date);
			String date2 = new SimpleDateFormat("yyyyMMdd").format(started);
			if(date1.equals(date2)){
				isStarted = true;
			} else {
				isStarted = false;
			}
		}
		return isStarted;
	}
	
	public Date getDateStarted(){
		return this.loginService.getDateStarted();
	}*/

	public Result login(String username, String password){
		Result result = null;
		try {
			Cuenta account = this.loginService.getStoredAccount();
			
			if(account != null){ //do login locally
				result = new Result();
				if(account.getUsername().equals(username) && account.getPassword().equals(password)){
					this.loginService.setSessionActive(true);
					result.setMessage(this._context.getResources().getString(R.string.msg_login_succeeded));
					result.resultType = ResultType.LOGIN_SUCCEEDED;
					return result;
				} else {
					result.setMessage(this._context.getResources().getString(R.string.msg_login_user_password_invalid));
					result.resultType = ResultType.LOGIN_INVALID;
					return result;
				}
				
			} else { //do login from server
				
				String strResponse = this.loginService.loginFromServer(username, password);
				result = (strResponse==null?null:new Result());
				
				LoginResponse response = new LoginResponse();
				response.fromJson(strResponse, gson);
				
				if(response.getResult().equals(LoginResponse.INVALID))
				{
					result.setMessage(this._context.getResources().getString(R.string.msg_login_user_password_invalid));
					result.resultType = ResultType.LOGIN_INVALID;
					return result;
				}
				else if(response.getResult().equals(LoginResponse.SUCCESS))
				{
					account = new Cuenta();
					account.setUsername(response.getUserLogin());
					account.setPassword(password);
					account.setUserId(response.getUserId());
					account.setLastLogin(Calendar.getInstance().getTime());
					
					this.loginService.storeAccount(account);
					this.loginService.setSessionActive(true);
					AppSession.put(AppSession.ACCOUNT, account);
					
					result.setMessage(this._context.getResources().getString(R.string.msg_login_succeeded));
					result.resultType =ResultType.LOGIN_SUCCEEDED ;
					return result;
				}
			}
		} catch(SocketTimeoutException e){
			e.printStackTrace();
			result = new Result();
			result.setMessage("Error de conexi—n. Tiempo de espera agotado.");
			result.resultType =ResultType.FAILED ;
			return result;
		} catch (IOException e) {
			e.printStackTrace();
			result = new Result();
			result.resultType = ResultType.FAILED;
			return result;
		} catch (XmlPullParserException e) {		
			e.printStackTrace();
			result = new Result();
			result.resultType = ResultType.FAILED;
			return result;
		}
		return result;
	}

	public void startActivity(Intent intent) {
		this._context.startActivity(intent);
	}

	public void cleanAll() {
		enctaService.cleanStoredEncuestas();
		asignacionService.cleanAsignaciones();
		loginService.cleanAccount();
	}

}
