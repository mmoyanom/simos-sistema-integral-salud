package gob.sis.simos.service;

import gob.sis.simos.entity.Account;

import java.io.IOException;

import org.xmlpull.v1.XmlPullParserException;

import roboguice.inject.ContextSingleton;

import com.google.inject.Singleton;

import android.content.Context;

public interface LoginService {
	
	public void setContext(Context context);
	public String loginFromServer(String username, String password) throws IOException, XmlPullParserException;
	public Account getStoredAccount(String userLogin, String password);
	public Account getStoredAccount();
	public int storeAccount(Account account);
	
}
