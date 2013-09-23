package gob.sis.simos;

import gob.sis.simos.controller.ConfigurationController;
import gob.sis.simos.controller.LoginController;
import gob.sis.simos.controller.Result;
import gob.sis.simos.db.DBHelper;
import gob.sis.simos.entity.Config;

import java.io.IOException;

import roboguice.activity.RoboActivity;
import roboguice.inject.InjectView;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.inject.Inject;


public class LoginActivity extends RoboActivity implements OnClickListener, InputFilter, TextWatcher , DialogInterface.OnClickListener {
	
	@InjectView(R.id.username)
	protected EditText _username;
	
	@InjectView(R.id.password)
	protected EditText _password;
	
	@InjectView(R.id.btnLogin)
	protected Button _btnLogin;
	
	private LoginTask _loginTask;
	private AlertDialog alertSetAddress;
	private EditText etAddress;
	
	@Inject
	protected LoginController loginController;
	
	@Inject
	protected ConfigurationController configController;

	private int SESSION_ACTIVE = 1;
	
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
		setContentView(R.layout.actvt_login);
		getActionBar().setIcon(R.drawable.logo_minsa);
		getActionBar().setTitle("");
		
		this._username.setFilters(new InputFilter[]{this});
		this._username.addTextChangedListener(this);

		this._password.setFilters(new InputFilter[]{this});
		this._password.addTextChangedListener(this);
		
		this._btnLogin.setOnClickListener(this);
		this._btnLogin.setEnabled(false);
		
		if(loginController.isActiveSession()){
			Intent intent = new Intent(this, MenuPrincipalActivity.class);
			startActivityForResult(intent, SESSION_ACTIVE);
		}
		
	    LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    final View layout = inflater.inflate(R.layout.dialog_add_ticket, (ViewGroup) findViewById(R.id.layout_add_ticket));
		
		alertSetAddress = new AlertDialog.Builder(this).create();
		alertSetAddress.setTitle("Configurar conexi—n");
		alertSetAddress.setView(layout);
		alertSetAddress.setCancelable(false);
		alertSetAddress.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancelar", this);
		alertSetAddress.setButton(AlertDialog.BUTTON_POSITIVE, "Guardar", this);
		
		etAddress = (EditText)layout.findViewById(R.id.et_ticket);
		etAddress.setHint("Ingrese la direcci—n de conexi—n");
	    InputFilter[] filters = new InputFilter[1];
	    filters[0] = new InputFilter() {
	        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
	            if (end > start) {
	                String destTxt = dest.toString();
	                String resultingTxt = destTxt.substring(0, dstart) + source.subSequence(start, end) + destTxt.substring(dend);
	                if (!resultingTxt.matches ("^\\d{1,3}(\\.(\\d{1,3}(\\.(\\d{1,3}(\\.(\\d{1,3})?)?)?)?)?)?")) { 
	                    return "";
	                } else {
	                    String[] splits = resultingTxt.split("\\.");
	                    for (int i=0; i<splits.length; i++) {
	                        if (Integer.valueOf(splits[i]) > 255) {
	                            return "";
	                        }
	                    }
	                }
	            }
	        return null;
	        }
	    };
	    etAddress.setFilters(filters);
		etAddress.addTextChangedListener(this);
		etAddress.requestFocus();
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(requestCode == SESSION_ACTIVE){
			if(resultCode == RESULT_CANCELED){
				finish();
			} else if(resultCode == RESULT_OK){
				this._username.setText("");
				this._password.setText("");
			}
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.login, menu);	    
		return true;
	}
	
	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		Config cfg = configController.getConfig();
		if(cfg != null){
			etAddress.setText(cfg.getServer());
		} else {
			etAddress.setText("");
		}
		alertSetAddress.show();
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
		this._loginTask = new LoginTask();
		this._loginTask.execute();
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
			return LoginActivity.this.loginController.login(username, password);
		}
		
		@Override
		protected void onPostExecute(Result result) {
			if(result != null){
				switch(result.resultType){
					case LOGIN_SUCCEEDED:
						this._progressDialog.dismiss();
						Intent i = new Intent(LoginActivity.this,MenuPrincipalActivity.class);
						LoginActivity.this._username.setText("");
						LoginActivity.this._password.setText("");
						LoginActivity.this.showToastMessage(R.string.msg_login_succeeded);
						//LoginActivity.this._controller.startActivity(i);
						startActivityForResult(i, SESSION_ACTIVE);
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

	@Override
	public void onClick(DialogInterface arg0, int wich) {
		
		if(wich == DialogInterface.BUTTON_POSITIVE){
			Config cfg = configController.getConfig();
			cfg.setServer(etAddress.getText().toString());
			configController.updateConfig(cfg);
			etAddress.setText("");
		} else if (wich == DialogInterface.BUTTON_NEGATIVE){
			arg0.dismiss();
		}
	}
}
