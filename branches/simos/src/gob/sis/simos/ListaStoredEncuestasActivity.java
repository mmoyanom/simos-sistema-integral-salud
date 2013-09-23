package gob.sis.simos;

import java.util.Calendar;
import java.util.List;

import gob.sis.simos.adapters.StoredEncuestaListAdapter;
import gob.sis.simos.controller.EncuestaController;
import gob.sis.simos.entity.Encuesta01;
import gob.sis.simos.soap.SendEncuestaResult;
import roboguice.activity.RoboActivity;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;
import android.app.ActionBar;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.google.inject.Inject;

@ContentView(R.layout.actvt_lst_strd_encsts)
public class ListaStoredEncuestasActivity extends RoboActivity {
	
	@InjectView(R.id.lv_stored)
	private ListView lvStored;
	
	@Inject
	private EncuestaController controller;
	
	public static final int ALL = 0;
	public static final int SENT = 1;
	public static final int UNSENT = 2;
	private int selected;
	private MenuItem menuItem;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		

		dialog = new ProgressDialog(this);
		
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM | ActionBar.DISPLAY_SHOW_HOME | ActionBar.DISPLAY_SHOW_TITLE);
		
		selected = getIntent().getIntExtra("selected", -1);
		Log.d("gob.sis.simos", selected+"");
		findViewById(R.id.lv_stored_encts);
		
		List<Encuesta01> items = null;
		
		try {
			if(selected == ALL){
				items = this.controller.findAllByDate(Calendar.getInstance().getTime());
				actionBar.setTitle("Encuestas - Todas");
			} else if(selected == SENT) {
				items = this.controller.findSentByDate(Calendar.getInstance().getTime());
				actionBar.setTitle("Encuestas - Enviadas");
			} else if(selected == UNSENT){
				items = this.controller.findUnsentByDate(Calendar.getInstance().getTime());
				actionBar.setTitle("Encuestas - No enviadas");
			}

			StoredEncuestaListAdapter adapter = new StoredEncuestaListAdapter(this, R.layout.adptr_strd_encsts, items);
			lvStored.setAdapter(adapter);
			adapter.notifyDataSetChanged();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		new SendEncuestasUnsent().execute();
		return true;
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.menu_stored_encsts, menu);
	    menuItem = menu.getItem(0);
		if(selected == ALL){
			menuItem.setVisible(false);
		} else if(selected == SENT) {
			menuItem.setVisible(false);
		} else if(selected == UNSENT){
			menuItem.setVisible(true);
		}
	    return true;
	}
	
	private ProgressDialog dialog;
	
	public class SendEncuestasUnsent extends AsyncTask<Void, Void, SendEncuestaResult> {

		@Override
		protected void onPreExecute() {
			dialog.setCancelable(false);
			dialog.setTitle(R.string.title_send_encta);
			dialog.setMessage(getResources().getString(R.string.please_wait));
			dialog.show();
		}
		
		@Override
		protected SendEncuestaResult doInBackground(Void... params) {
			SendEncuestaResult result = controller.SendEncuestasUnsent();
			return result;
		}
		
		@Override
		protected void onPostExecute(SendEncuestaResult result) {
			dialog.dismiss();
			showMessage(result.getResult()+"", Toast.LENGTH_LONG);
			setResult(RESULT_OK);
			finish();
		}
	}
	
	private void showMessage(String text, int length){
		Toast.makeText(this, text, length).show();
	}

}
