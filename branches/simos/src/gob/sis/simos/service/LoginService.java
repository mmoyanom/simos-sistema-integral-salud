package gob.sis.simos.service;

import gob.sis.simos.entity.Cuenta;

import java.io.IOException;

import org.xmlpull.v1.XmlPullParserException;

import roboguice.inject.ContextSingleton;

import com.google.inject.Singleton;

import android.content.Context;

public interface LoginService {
	
	public void setContext(Context context);
	public String loginFromServer(String username, String password) throws IOException, XmlPullParserException;
	public Cuenta getStoredAccount(String userLogin, String password);
	public Cuenta getStoredAccount();
	public int storeAccount(Cuenta account);
	
}
