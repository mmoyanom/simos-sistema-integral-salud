package gob.sis.simos;

import gob.sis.simos.controller.LoginController;
import gob.sis.simos.controller.Result;
import gob.sis.simos.db.DBHelper;

import java.io.IOException;

import roboguice.activity.RoboActivity;
import roboguice.inject.InjectView;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.inject.Inject;


public class LoginActivity extends RoboActivity implements OnClickListener, InputFilter, TextWatcher {
	
	@InjectView(R.id.username)
	protected EditText _username;
	
	@InjectView(R.id.password)
	protected EditText _password;
	
	@InjectView(R.id.btnLogin)
	protected Button _btnLogin;
	
	private LoginTask _loginTask;
	
	@Inject
	protected LoginController _controller;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		try {
			if(!DBHelper.databaseExists()){
				DBHelper.copyDataBase(this);
			}
		} catch (IOException e) {
			Toast.makeText(this, "Error copiando la base de datos.", Toast.LENGTH_SHORT).show();
		}
		setContentView(R.layout.activity_login);
		getActionBar().setIcon(R.drawable.logo_minsa);
		getActionBar().setTitle("");
		
		this._username.setFilters(new InputFilter[]{this});
		this._username.addTextChangedListener(this);

		this._password.setFilters(new InputFilter[]{this});
		this._password.addTextChangedListener(this);
		
		this._btnLogin.setOnClickListener(this);
		this._btnLogin.setEnabled(false);
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		return true;
	}

	@Override
	public void onClick(View v) {
		
		if(v == this._btnLogin){
			this.doLogin();
		}
	}

	@Override
	public CharSequence filter(CharSequence source, int start, int end,
			Spanned dest, int dstart, int dend) {
		for (int i = start; i < end; i++) { 
            if (!Character.isLetterOrDigit(source.charAt(i))) { 
                    return ""; 
            } 
		} 
		return null;
	}
	
	private void doLogin(){
		if(this.checkInternetConnection()){
			this._loginTask = new LoginTask();
			this._loginTask.execute();
		} else {
			this.showToastMessage(R.string.msg_no_internet_connection);
		    return;
		}
	}
	
	private void showToastMessage(int textResource){
		Toast.makeText(this, textResource, Toast.LENGTH_LONG).show();
	}
	
	private void showDialogMessage(int textResource){
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(textResource);
		builder.setNeutralButton(R.string.btn_text_OK, null);
		builder.show();
	}

	@Override
	public void afterTextChanged(Editable s) {
		String username = this._username.getText().toString().trim();
		String password = this._password.getText().toString().trim();
		if(username.length() > 0 && password.length() > 0){
			if(!this._btnLogin.isEnabled()){
				this._btnLogin.setEnabled(true);
			}
		} else {
			if(this._btnLogin.isEnabled()){
				this._btnLogin.setEnabled(false);
			}
		}
	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {
		// nothing to do here
	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		// nothing to do here
	}

	protected boolean checkInternetConnection(){
		ConnectivityManager conMgr =  (ConnectivityManager)this.getSystemService(Context.CONNECTIVITY_SERVICE);
		 NetworkInfo i = conMgr.getActiveNetworkInfo();
		  if (i == null){
		    return false;
		  } if (!i.isConnected()) {
		    return false;
		  } if (!i.isAvailable()) {
		    return false;
		  }
		  return true;
	}
	
	public class LoginTask extends AsyncTask<Void, Void, Result> {

		private ProgressDialog _progressDialog;
		
		public LoginTask() {
			this._progressDialog = new  ProgressDialog(LoginActivity.this);
			this._progressDialog.setMessage(LoginActivity.this.getResources().getString(R.string.msg_please_wait));
			this._progressDialog.setCancelable(false);
			this._progressDialog.setCanceledOnTouchOutside(false);
		}
		
		@Override
		protected void onPreExecute() {
			this._progressDialog.show();
		}
		
		@Override
		protected Result doInBackground(Void... params) {
			String username = LoginActivity.this._username.getText().toString().trim();
			String password = LoginActivity.this._password.getText().toString().trim();
			return LoginActivity.this._controller.login(username, password);
		}
		
		@Override
		protected void onPostExecute(Result result) {
			if(result != null){
				switch(result.resultType){
					case LOGIN_SUCCEEDED:
						this._progressDialog.dismiss();
						Intent i = new Intent(LoginActivity.this,MainMenuActivity.class);
						LoginActivity.this._username.setText("");
						LoginActivity.this._password.setText("");
						LoginActivity.this.showToastMessage(R.string.msg_login_succeeded);
						LoginActivity.this._controller.startActivity(i);
						/*Intent intent = new Intent();
						intent.putExtra("data", "data");
						LoginActivity.this.setResult(1,intent);
						LoginActivity.this.finish();*/
						break;
					case LOGIN_INVALID:
						this._progressDialog.dismiss();
						LoginActivity.this.showDialogMessage(R.string.msg_login_user_password_invalid);
						break;
					case FAILED:
						this._progressDialog.dismiss();
						LoginActivity.this.showToastMessage(R.string.msg_login_failed);
						break;
					default :
						break;
				}
			}
		}
	}
}
