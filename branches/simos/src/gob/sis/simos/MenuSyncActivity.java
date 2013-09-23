package gob.sis.simos;

import gob.sis.simos.adapters.ImageItemListAdapter;
import gob.sis.simos.controller.EncuestaController;
import gob.sis.simos.controller.Result;
import gob.sis.simos.controller.ResultType;
import gob.sis.simos.controller.VerificacionPagoController;
import gob.sis.simos.dto.OpcionMenu;
import gob.sis.simos.soap.SendEncuestaResult;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import roboguice.activity.RoboActivity;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.google.inject.Inject;

@ContentView(R.layout.actvt_menu_sync)
public class MenuSyncActivity extends RoboActivity implements OnItemClickListener {

	ImageItemListAdapter adapter;
	
	@InjectView(R.id.lv_menu_sync) private ListView lvSyncMenu;
	@Inject EncuestaController enctaController;
	@Inject VerificacionPagoController verifController;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		dialog = new ProgressDialog(this);
		
		String[] array = getResources().getStringArray(R.array.main_menu_sync);
		
		List<OpcionMenu> menu = new ArrayList<OpcionMenu>();
		
		OpcionMenu op2 = new OpcionMenu();
		op2.setTitle(array[1]);
		try {
			Integer unsent = enctaController.findUnsent().size();
			if(unsent > 0){
				op2.setDescription(String.format("Env’a las encuestas que est‡n almacenadas en el dispositivo. (%s pendientes)", unsent));
			} else {
				op2.setDescription("Env’a las encuestas que est‡n almacenadas en el dispositivo.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		op2.setId(R.drawable.upload_db);
		menu.add(op2);
	
		OpcionMenu op1 = new OpcionMenu();
		op1.setTitle(array[0]);
		op1.setDescription("Descarga las preguntas, opciones de respuestas, etc.");
		op1.setId(R.drawable.download_db);
		menu.add(op1);
		
		adapter = new ImageItemListAdapter(this, R.layout.simple_list_item_image, menu);
		lvSyncMenu.setAdapter(adapter);
		lvSyncMenu.setOnItemClickListener(this);
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
		
		if(position == 0){
			try {
				int c = this.enctaController.findUnsent().size();
				if(c > 0){
					new SendEncuestasUnsentTask().execute();
				} else {
					showMessage("No se han encontrado encuestas pendientes de Env’o", Toast.LENGTH_LONG);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else if(position == 1){
			new DownloadParametersTask().execute(); 
		}
		
	}
	

	private ProgressDialog dialog;
	
	public class DownloadParametersTask extends AsyncTask<Void, Void, Result> {
		
		@Override
		protected void onPreExecute() {
			dialog.setCancelable(false);
			dialog.setTitle("Descargando par‡metros");
			dialog.setMessage(getResources().getString(R.string.please_wait));
			dialog.show();
		}
		
		@Override
		protected Result doInBackground(Void... params) {
			Result result = enctaController.downloadOpcionesRespuesta();
			return result;
		}
		
		@Override
		protected void onPostExecute(Result result) {
			dialog.dismiss();
			if(result.resultType == ResultType.SUCCEED){
				showMessage("Par‡metros descargados satisfactoriamente.", Toast.LENGTH_LONG);
			} else {
				showMessage(result.getMessage(), Toast.LENGTH_LONG);
			}
		}
	}
	
	public class SendEncuestasUnsentTask extends AsyncTask<Void, Void, SendEncuestaResult> {

		@Override
		protected void onPreExecute() {
			dialog.setCancelable(false);
			dialog.setTitle("Enviando encuestas");
			dialog.setMessage(getResources().getString(R.string.please_wait));
			dialog.show();
		}
		
		@Override
		protected SendEncuestaResult doInBackground(Void... params) {
			SendEncuestaResult result = enctaController.SendEncuestasUnsent();
			return result;
		}
		
		@Override
		protected void onPostExecute(SendEncuestaResult result) {
			dialog.dismiss();
			if(result.getResult()==SendEncuestaResult.SUCCEEDED){
				showMessage("Las encuestas han sido enviadas exitosamente.", Toast.LENGTH_LONG);
			} else {
				showMessage("Hubo un error al enviar las encuestas.", Toast.LENGTH_LONG);
			}
		}
	}
	
	private void showMessage(String text, int length){
		Toast.makeText(this, text, length).show();
	}
	
	private AlertDialog alert;
	
	private void showAlert(String text){
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(text)
		       .setCancelable(false)
		       .setPositiveButton("OK", new DialogInterface.OnClickListener() {
		           public void onClick(DialogInterface dialog, int id) {
		        	   
		           }
		       });
		alert = builder.create();
		alert.show();
	}

}
