package gob.sis.simos;

import gob.sis.simos.adapters.AsignacionListAdapter;
import gob.sis.simos.application.MyApplication;
import gob.sis.simos.controller.AsignacionController;
import gob.sis.simos.controller.EncuestaController;
import gob.sis.simos.controller.JornadaController;
import gob.sis.simos.controller.LoginController;
import gob.sis.simos.controller.Result;
import gob.sis.simos.controller.ResultType;
import gob.sis.simos.controller.VerificacionPagoController;
import gob.sis.simos.entity.Asignacion;
import gob.sis.simos.soap.DownloadListOfAsignacionesResult;
import gob.sis.simos.soap.SendEncuestaResult;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import roboguice.activity.RoboActivity;
import roboguice.inject.InjectView;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.google.inject.Inject;

public class ListaAsignacionesActivity extends RoboActivity implements OnItemClickListener {

	@InjectView(R.id.lst_eess)
	protected ListView _lstAsignaciones;

	@Inject
	protected AsignacionController asignacionController;
	
	@Inject
	protected LoginController loginController;
	
	@Inject
	protected VerificacionPagoController verifController;
	
	@Inject
	protected EncuestaController enctaController;
	
	@Inject
	protected JornadaController jndaController;

	private AsignacionListAdapter adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		this.setContentView(R.layout.actvt_lst_eess);
		
		dialog = new ProgressDialog(this);
		dialogDownloadParameter = new ProgressDialog(this);
		dialogEncuestasSend = new ProgressDialog(this);
		
		adapter = new AsignacionListAdapter(this, R.layout.adptr_eess_simple_list);
		this._lstAsignaciones.setAdapter(adapter);
		this._lstAsignaciones.setOnItemClickListener(this);
		
		if(jndaController.jornadaFinalizada() && jndaController.equalsToday(jndaController.getJornada().getFinish())){
			AlertDialog alertDialog1 = new AlertDialog.Builder(this).create();
	        alertDialog1.setTitle("Jornada Finalizada");
	        alertDialog1.setMessage("Esta jornada ya ha sido finalizada.");
	        alertDialog1.setButton(AlertDialog.BUTTON_POSITIVE,"OK", new DialogInterface.OnClickListener() {
	            public void onClick(DialogInterface dialog, int which) {
	            	finish();
	            }
	        });
	        alertDialog1.show();
		} else {
			if(asignacionController.getAsignacionList().size() > 0){
				adapter.clear();
				adapter.addAll(asignacionController.getAsignacionList());
				adapter.notifyDataSetChanged();
			} else {
				downloadAsignaciones();
			}
		}
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		checkDay();
		MyApplication application = (MyApplication)getApplication();
		application.setSelectedAsignacion(null);
	}
	
	private void checkDay(){
		
		if(jndaController.getJornada() == null)
			return;
		if(jndaController.getJornada().getStart() == null)
			return;
		if(jndaController.getJornada().getFinish() == null)
			return;
		
		if(!jndaController.jornadaFinalizada() && !jndaController.equalsToday(jndaController.getJornada().getStart())){
			Calendar c = Calendar.getInstance();
			c.setTime(Calendar.getInstance().getTime());
			c.set(Calendar.HOUR, 11);
			c.set(Calendar.MINUTE, 59);
			c.set(Calendar.SECOND, 59);
			Date finish = c.getTime();
			jndaController.finalizarJornada(finish);
			new SendEncuestasUnsentTask().execute();
		}
	}
	
	private void finalizeDay(String msg){
		AlertDialog alertDialog1 = new AlertDialog.Builder(this).create();
	    alertDialog1.setTitle("Jornada Finalizada");
	    alertDialog1.setMessage(msg);
	    alertDialog1.setButton(AlertDialog.BUTTON_POSITIVE,"OK", new DialogInterface.OnClickListener() {
	        	
	            public void onClick(DialogInterface dialog, int which) {
	    			adapter.clear();
	    			adapter.addAll(asignacionController.getAsignacionList());
	    			adapter.notifyDataSetChanged();
	            	finish();
	            }
	     });
	    alertDialog1.show();
	}
	
	public void downloadAsignaciones(){
		AlertDialog alertDialog1 = new AlertDialog.Builder(this).create();
        alertDialog1.setTitle("Asignaciones");
        alertDialog1.setCancelable(false);
        alertDialog1.setMessage("No se han encontrado asignaciones. Desea comprobar actualizaciones?");
        alertDialog1.setButton(AlertDialog.BUTTON_POSITIVE,"Si", new DialogInterface.OnClickListener() {
        	
            public void onClick(DialogInterface dialog, int which) {
            	new DownloadAsignacionesTask().execute();
            }
        });
        alertDialog1.setButton(AlertDialog.BUTTON_NEGATIVE,"No", new DialogInterface.OnClickListener() {
        	
            public void onClick(DialogInterface dialog, int which) {
            	finish();
            }
        });
        alertDialog1.show();
	}

	@Override
	public void onItemClick(AdapterView<?> view, View v, int position, long resourceId) {
		
		Asignacion a = (Asignacion)_lstAsignaciones.getItemAtPosition(position);
		MyApplication application = (MyApplication)getApplication();
		application.setSelectedAsignacion(a);
		
		checkDay();
		
		if(jndaController.jornadaFinalizada() && jndaController.equalsToday(jndaController.getJornada().getFinish())){
			AlertDialog alertDialog1 = new AlertDialog.Builder(this).create();
	        alertDialog1.setTitle("Jornada Finalizada");
	        alertDialog1.setMessage("Usted ya ha finalizado su jornada para el d’a de hoy.");
	        alertDialog1.setButton(AlertDialog.BUTTON_POSITIVE,"OK", new DialogInterface.OnClickListener() {
	            public void onClick(DialogInterface dialog, int which) {}
	        });
	        alertDialog1.show();
	        return;
		}
		
		if(jndaController.jornadaIniciada() && jndaController.equalsToday(jndaController.getJornada().getStart())){
			int opcionesRstas_count = this.verifController.getRespuestasCount();
			if(opcionesRstas_count > 0){
				Intent i = new Intent(this, ListaEncuestasActivity.class);
				this.startActivity(i);
			}else {
				// PASAR ESTO AL ESCOGER UN FORMULARIO!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
				AlertDialog alertDialog1 = new AlertDialog.Builder(this).create();
		        alertDialog1.setTitle("Opciones de respuesta");
		        alertDialog1.setCancelable(false);
		        alertDialog1.setMessage("No se encontraron opciones de respuestas. ÀDesea actualizar ahora?");
		        alertDialog1.setButton(AlertDialog.BUTTON_POSITIVE,"Si", new DialogInterface.OnClickListener() {
		        	
		            public void onClick(DialogInterface dialog, int which) {
		            	new DownloadParametersTask().execute();
		            }
		        });
		        alertDialog1.setButton(AlertDialog.BUTTON_NEGATIVE,"No", new DialogInterface.OnClickListener() {
		            public void onClick(DialogInterface dialog, int which) {}
		        });
		        alertDialog1.show();
			}
			
		}else{
			
			AlertDialog alertDialog1 = new AlertDialog.Builder(this).create();
	        alertDialog1.setTitle("Iniciar jornada");
	        alertDialog1.setMessage("No se puede comenzar a realizar encuestas sin iniciar jornada. Desea hacerlo ahora?");
	        alertDialog1.setButton(AlertDialog.BUTTON_POSITIVE,"Si", new DialogInterface.OnClickListener() {
	        	
	        	public void onClick(DialogInterface dialog, int which) {
	            	Date start = Calendar.getInstance().getTime();
	            	jndaController.iniciarJornada(start);
	            	
	            	// PASAR ESTO AL ESCOGER UN FORMULARIO!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	            	int opcionesRstas_count = verifController.getRespuestasCount();
	    			if(opcionesRstas_count > 0){
	    				Intent i = new Intent(ListaAsignacionesActivity.this, ListaEncuestasActivity.class);
	    				startActivity(i);
	    			}else {
	    				AlertDialog alertDialog1 = new AlertDialog.Builder(ListaAsignacionesActivity.this).create();
	    		        alertDialog1.setTitle("Opciones de respuesta");
	    		        alertDialog1.setCancelable(false);
	    		        alertDialog1.setMessage("No se encontraron opciones de respuestas. ÀDesea actualizar ahora?");
	    		        alertDialog1.setButton(AlertDialog.BUTTON_POSITIVE,"Si", new DialogInterface.OnClickListener() {
	    		        	
	    		            public void onClick(DialogInterface dialog, int which) {
	    		            	new DownloadParametersTask().execute();
	    		            }
	    		        });
	    		        alertDialog1.setButton(AlertDialog.BUTTON_NEGATIVE,"No", new DialogInterface.OnClickListener() {
	    		            public void onClick(DialogInterface dialog, int which) {}
	    		        });
	    		        alertDialog1.show();
	    			}
	            }
	        });
	        alertDialog1.setButton(AlertDialog.BUTTON_NEGATIVE,"No", new DialogInterface.OnClickListener() {
	            public void onClick(DialogInterface dialog, int which) {}
	        });

	        // Showing Alert Message
	        alertDialog1.show();
		}
	}
	
	private ProgressDialog dialog;
	
	public class DownloadAsignacionesTask extends AsyncTask<Void, Void, DownloadListOfAsignacionesResult>{

		@Override
		protected void onPreExecute() {
			dialog.setCancelable(false);
			dialog.setTitle("Descargando asignaciones");
			dialog.setMessage(getResources().getString(R.string.please_wait));
			dialog.show();
		}
		
		@Override
		protected DownloadListOfAsignacionesResult doInBackground(
				Void... params) {
			DownloadListOfAsignacionesResult result = asignacionController.downloadAsignaciones();
			return result;
		}
		
		@Override
		protected void onPostExecute(DownloadListOfAsignacionesResult result) {
			dialog.dismiss();
			if(result.isSuccess()){
				adapter.clear();
				List<Asignacion> items = asignacionController.getAsignacionList();
				adapter.addAll(items);
				adapter.notifyDataSetChanged();
				verifController.cleanRespuestas();
				showMessage("Las asignaciones se han descargado satisfactoriamente.", Toast.LENGTH_LONG);
			} else {
				if(result.getUpdateMessage() != null){
					showMessage(result.getUpdateMessage(),Toast.LENGTH_LONG);
				} else {
					showMessage(result.getErrorMessage(), Toast.LENGTH_LONG);
				}
				finish();
			}
			
		}
		
		
	}
	
	private ProgressDialog dialogDownloadParameter;
	
	public class DownloadParametersTask extends AsyncTask<Void, Void, Result> {
		
		@Override
		protected void onPreExecute() {
			dialogDownloadParameter.setCancelable(false);
			dialogDownloadParameter.setTitle("Descargando par‡metros");
			dialogDownloadParameter.setMessage(getResources().getString(R.string.please_wait));
			dialogDownloadParameter.show();
		}
		
		@Override
		protected Result doInBackground(Void... params) {
			Result result = enctaController.downloadOpcionesRespuesta();
			return result;
		}
		
		@Override
		protected void onPostExecute(Result result) {
			dialogDownloadParameter.dismiss();
			if(result.resultType == ResultType.SUCCEED){
				showMessage("Par‡metros descargados satisfactoriamente.", Toast.LENGTH_LONG);
				Intent i = new Intent(ListaAsignacionesActivity.this, ListaEncuestasActivity.class);
				startActivity(i);
			} else {
				showMessage(result.getMessage(), Toast.LENGTH_LONG);
			}
		}
	}

	
	private ProgressDialog dialogEncuestasSend;

	public class SendEncuestasUnsentTask extends AsyncTask<Void, Void, SendEncuestaResult> {

		@Override
		protected void onPreExecute() {
			dialogEncuestasSend.setCancelable(false);
			dialogEncuestasSend.setTitle("Enviando encuestas pendientes.");
			dialogEncuestasSend.setMessage(getResources().getString(R.string.please_wait));
			dialogEncuestasSend.show();
		}
		
		@Override
		protected SendEncuestaResult doInBackground(Void... params) {
			SendEncuestaResult result = enctaController.SendEncuestasUnsent();
			return result;
		}
		
		@Override
		protected void onPostExecute(SendEncuestaResult result) {
			dialogEncuestasSend.dismiss();
			String str = "Esta jornada ha sido finalizada.";
			if(result.getResult()==SendEncuestaResult.SUCCEEDED){
				enctaController.cleanStoredEncuestas();
				showMessage("Las encuestas han sido enviadas exitosamente.", Toast.LENGTH_LONG);
				str ="Las encuestas han sido enviadas exitosamente.";
			} else {
				showMessage("Hubo un error al enviar las encuestas.", Toast.LENGTH_LONG);
				str = "Hubo un error al enviar las encuestas. Se han almacenado en el dispositivo.";
			}
			finalizeDay(str);
		}
	}
	
	private void showMessage(String text, int length){
		Toast.makeText(this, text, length).show();
	}
}
