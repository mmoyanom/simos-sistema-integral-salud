package gob.sis.simos.controller;

import gob.sis.simos.AppSession;
import gob.sis.simos.R;
import gob.sis.simos.dto.LoginResponse;
import gob.sis.simos.entity.Account;
import gob.sis.simos.service.LoginService;

import java.io.IOException;
import java.util.Calendar;

import org.xmlpull.v1.XmlPullParserException;

import android.content.Context;
import android.content.Intent;

import com.google.gson.Gson;
import com.google.inject.Inject;

public class LoginController {

	@Inject
	protected LoginService _service;
	@Inject
	protected Gson gson;
	private Context _context;

	public LoginController() {

	}

	public void setContext(Context context) {
		this._context = context;
		this._service.setContext(this._context);
	}
	
	public boolean isActiveSession(){
		if(AppSession.get(AppSession.ACCOUNT) == null){
			return false;
		}
		return true;
	}

	public Result login(String username, String password){
		Result result = null;
		try {
			Account account = this._service.getStoredAccount();
			
			if(account != null){ //do login locally
				result = new Result();
				if(account.getUsername().equals(username) && account.getPassword().equals(password)){
					result.setMessage(this._context.getResources().getString(R.string.msg_login_succeeded));
					result.setResultType(ResultType.LOGIN_SUCCEEDED);
					return result;
				} else {
					result.setMessage(this._context.getResources().getString(R.string.msg_login_user_password_invalid));
					result.setResultType(ResultType.LOGIN_INVALID);
					return result;
				}
				
			} else { //do login from server
				
				String strResponse = this._service.loginFromServer(username, password);
				result = (strResponse==null?null:new Result());
				
				LoginResponse response = new LoginResponse();
				response.fromJson(strResponse, gson);
				
				if(response.getResult().equals(LoginResponse.INVALID))
				{
					result.setMessage(this._context.getResources().getString(R.string.msg_login_user_password_invalid));
					result.setResultType(ResultType.LOGIN_INVALID);
					return result;
				}
				else if(response.getResult().equals(LoginResponse.SUCCESS))
				{
					account = new Account();
					account.setUsername(response.getUserLogin());
					account.setPassword(password);
					account.setUserId(response.getUserId());
					account.setLastLogin(Calendar.getInstance().getTime());
					
					this._service.storeAccount(account);
					AppSession.put(AppSession.ACCOUNT, account);
					
					result.setMessage(this._context.getResources().getString(R.string.msg_login_succeeded));
					result.setResultType(ResultType.LOGIN_SUCCEEDED);
					return result;
				}
			}
			
		} catch (IOException e) {
			e.printStackTrace();
			result = new Result();
			result.setResultType(ResultType.FAILED);
		} catch (XmlPullParserException e) {		
			e.printStackTrace();
			result = new Result();
			result.setResultType(ResultType.FAILED);
		}
		return result;
	}

	public void startActivity(Intent intent) {
		this._context.startActivity(intent);
	}

}
