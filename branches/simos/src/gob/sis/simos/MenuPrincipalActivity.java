package gob.sis.simos;

import gob.sis.simos.adapters.ImageItemListAdapter;
import gob.sis.simos.controller.AsignacionController;
import gob.sis.simos.controller.EncuestaController;
import gob.sis.simos.controller.JornadaController;
import gob.sis.simos.controller.LoginController;
import gob.sis.simos.dto.OpcionMenu;
import gob.sis.simos.soap.SendEncuestaResult;

import java.util.ArrayList;
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

public class MenuPrincipalActivity extends RoboActivity implements
		OnItemClickListener {

	@InjectView(gob.sis.simos.R.id.lstMainMenu)
	protected ListView _lstMainMenu;

	@Inject
	private LoginController controller;
	
	@Inject
	private AsignacionController asigController;
	
	@Inject
	private EncuestaController enctaController;
	
	@Inject
	private JornadaController jndaController;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.actvt_menu_principal);

		dialog = new ProgressDialog(this);
		
		List<OpcionMenu> menu = new ArrayList<OpcionMenu>();
		OpcionMenu op1 = new OpcionMenu();
		op1.setTitle("Establecimientos asignados");
		op1.setId(R.drawable.route);
		op1.setDescription("Seleccione esta opci—n para consultar sus establecimientos asignados para el d’a.");
		menu.add(op1);
		
		OpcionMenu op3 = new OpcionMenu();
		op3.setTitle("Reporte del d’a");
		op3.setId(R.drawable.reports);
		op3.setDescription("Muestra el reporte de las encuestas realizadas en el d’a.");
		menu.add(op3);
		
		OpcionMenu op4 = new OpcionMenu();
		op4.setTitle("Sincronizaci—n");
		op4.setId(R.drawable.sync);
		op4.setDescription("Env’o de las encuestas pendientes, descargar par‡metros, etc.");
		menu.add(op4);
		
		OpcionMenu op2 = new OpcionMenu();
		op2.setTitle("Finalizar jornada");
		op2.setId(R.drawable.finish_day);
		op2.setDescription("Seleccione esta opci—n para finalizar las actividades del d’a.");
		menu.add(op2);
		
		OpcionMenu op5 = new OpcionMenu();
		op5.setTitle("Cerrar sesi—n");
		op5.setId(R.drawable.logout);
		op5.setDescription("Cierra la sesi—n del usuario para salir de la aplicaci—n.");
		menu.add(op5);
		
		ImageItemListAdapter adapter = new ImageItemListAdapter(this, R.layout.simple_list_item_image, menu);
		this._lstMainMenu.setAdapter(adapter);
		this._lstMainMenu.setOnItemClickListener(this);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		checkDay();
	}
	
	private void checkDay(){
		
		if(jndaController.getJornada() == null)
			return;
		if(jndaController.getJornada().getStart() == null)
			return;
		
		if(!jndaController.jornadaFinalizada() && !jndaController.equalsToday(jndaController.getJornada().getStart())){
			
			Calendar c = Calendar.getInstance();
			c.setTime(jndaController.getJornada().getStart());
			c.set(Calendar.HOUR, 11);
			c.set(Calendar.MINUTE, 59);
			c.set(Calendar.SECOND, 59);
			Date finish = c.getTime();
			jndaController.finalizarJornada(finish);
			new SendEncuestasUnsentTask().execute();
		}
	}
	
	private void notifyDayFinished(){
		
		AlertDialog alertDialog1 = new AlertDialog.Builder(this).create();
	    alertDialog1.setTitle("Jornada Finalizada");
	    alertDialog1.setMessage("Esta jornada ya esta finalizada.");
	    alertDialog1.setButton(AlertDialog.BUTTON_POSITIVE,"OK", new DialogInterface.OnClickListener() {
	        	
	            public void onClick(DialogInterface dialog, int which) {
	            	/*Intent in = new Intent(MenuPrincipalActivity.this, MenuPrincipalActivity.class);
	            	in.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
	            	in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	            	startActivity(in);*/
	            }
	     });
	    alertDialog1.show();
	}
	

	@Override
	public void onItemClick(AdapterView<?> view, View v, int position,
			long resourceId) {

		if (position == 0) {
			Intent i = new Intent(this, ListaAsignacionesActivity.class);
			this.startActivity(i);
		} else if(position == 1){	
			// finalizar
			finalizeDay("Esta jornada ya ha sido finalizada.");
			
		} else if(position == 2){
			Intent i = new Intent(this, ReporteActivity.class);
			this.startActivity(i);
		} else if(position == 3){
			Intent i = new Intent(this, MenuSyncActivity.class);
			this.startActivity(i);
		} else if(position == 4){
			if(!jndaController.jornadaFinalizada()){
				notifyDayStarted();
			}else{
				logOut();
			}
		}
		
	}
	
	private void finalizeDay(String message){
		
		if(jndaController.getJornada() == null){
			AlertDialog alertDialog1 = new AlertDialog.Builder(this).create();
	        alertDialog1.setTitle("Jornada no iniciada");
	        alertDialog1.setMessage("Usted no ha iniciado la jornada.");
	        alertDialog1.setButton(AlertDialog.BUTTON_POSITIVE,"OK", new DialogInterface.OnClickListener() {
	        	
	            public void onClick(DialogInterface dialog, int which) {
	            }
	        });
	        alertDialog1.show();
	        return;
		}
		if(jndaController.getJornada().getStart()==null){
			AlertDialog alertDialog1 = new AlertDialog.Builder(this).create();
	        alertDialog1.setTitle("Jornada no iniciada");
	        alertDialog1.setMessage("Usted no ha iniciado la jornada.");
	        alertDialog1.setButton(AlertDialog.BUTTON_POSITIVE,"OK", new DialogInterface.OnClickListener() {
	        	
	            public void onClick(DialogInterface dialog, int which) {
	            }
	        });
	        alertDialog1.show();
	        return;
		}
		if(jndaController.getJornada().getFinish()==null){
			AlertDialog alertDialog1 = new AlertDialog.Builder(this).create();
	        alertDialog1.setTitle("Finalizar Jornada");
	        alertDialog1.setMessage("Desea finalizar la jornada ahora?");
	        alertDialog1.setButton(AlertDialog.BUTTON_POSITIVE,"Si", new DialogInterface.OnClickListener() {
	        	
	            public void onClick(DialogInterface dialog, int which) {
	            	Date finish = Calendar.getInstance().getTime();
	            	jndaController.finalizarJornada(finish);
	            	new SendEncuestasUnsentTask().execute();
	            }
	        });
	        alertDialog1.setButton(AlertDialog.BUTTON_NEGATIVE,"No", new DialogInterface.OnClickListener() {
	        	
	            public void onClick(DialogInterface dialog, int which) {
	            }
	        });
	        alertDialog1.show();
	        return;
		}
		
		if(jndaController.jornadaFinalizada() && jndaController.equalsToday(jndaController.getJornada().getFinish())){
			AlertDialog alertDialog1 = new AlertDialog.Builder(this).create();
	        alertDialog1.setTitle("Jornada Finalizada");
	        alertDialog1.setMessage(message);
	        alertDialog1.setButton(AlertDialog.BUTTON_POSITIVE,"OK", new DialogInterface.OnClickListener() {
	            public void onClick(DialogInterface dialog, int which) {}
	        });
	        alertDialog1.show();
	        return;
		}
		
		if(!jndaController.jornadaFinalizada() && jndaController.equalsToday(jndaController.getJornada().getStart())){
			AlertDialog alertDialog1 = new AlertDialog.Builder(this).create();
	        alertDialog1.setTitle("Finalizar Jornada");
	        alertDialog1.setMessage("Desea finalizar la jornada ahora?");
	        alertDialog1.setButton(AlertDialog.BUTTON_POSITIVE,"Si", new DialogInterface.OnClickListener() {
	        	
	            public void onClick(DialogInterface dialog, int which) {
	            	Date finish = Calendar.getInstance().getTime();
	            	jndaController.finalizarJornada(finish);
	            	new SendEncuestasUnsentTask().execute();
	            }
	        });
	        alertDialog1.setButton(AlertDialog.BUTTON_NEGATIVE,"No", new DialogInterface.OnClickListener() {
	        	
	            public void onClick(DialogInterface dialog, int which) {
	            }
	        });

	        alertDialog1.show();
		} else {
			AlertDialog alertDialog1 = new AlertDialog.Builder(this).create();
	        alertDialog1.setTitle("Jornada no iniciada");
	        alertDialog1.setMessage("Usted no ha iniciado la jornada.");
	        alertDialog1.setButton(AlertDialog.BUTTON_POSITIVE,"OK", new DialogInterface.OnClickListener() {
	        	
	            public void onClick(DialogInterface dialog, int which) {
	            }
	        });
	        alertDialog1.show();
		}
		
	}
	
	private void notifyDayStarted(){
		AlertDialog alertDialog1 = new AlertDialog.Builder(this).create();
        alertDialog1.setTitle("Finalizar Jornada");
        alertDialog1.setMessage("Debe finalizar la jornada antes de cerrar sesi—n. ÀDesea finalizar la jornada ahora?");
        alertDialog1.setButton(AlertDialog.BUTTON_POSITIVE,"Si", new DialogInterface.OnClickListener() {
        	
            public void onClick(DialogInterface dialog, int which) {
            	Date finish = Calendar.getInstance().getTime();
            	jndaController.finalizarJornada(finish);
            	//logOut();
            	new SendEncuestasUnsentCerrarSesionTask().execute();
            }
        });
        alertDialog1.setButton(AlertDialog.BUTTON_NEGATIVE,"No", new DialogInterface.OnClickListener() {
        	
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        alertDialog1.show();
	}

	private void logOut() {
		AlertDialog alertDialog1 = new AlertDialog.Builder(this).create();
        alertDialog1.setTitle("Cerrar sesi—n");
        alertDialog1.setMessage("La eliminar‡ la informaci—n almacenada y sus credenciales en Žste dispositivo. ÀDesea cerrar sesi—n?");
        alertDialog1.setButton(AlertDialog.BUTTON_POSITIVE,"Si", new DialogInterface.OnClickListener() {
        	
            public void onClick(DialogInterface dialog, int which) {
            	controller.setSessionActive(false);
            	controller.cleanAll();
            	setResult(RESULT_OK);
            	finish();
            }
        });
        alertDialog1.setButton(AlertDialog.BUTTON_NEGATIVE,"No", new DialogInterface.OnClickListener() {
        	
            public void onClick(DialogInterface dialog, int which) {
            	controller.setSessionActive(true);
            }
        });

        // Showing Alert Message
        alertDialog1.show();
	}
	
	

	private ProgressDialog dialog;

	public class SendEncuestasUnsentTask extends AsyncTask<Void, Void, SendEncuestaResult> {

		@Override
		protected void onPreExecute() {
			dialog.setCancelable(false);
			dialog.setTitle("Enviando encuestas pendientes.");
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
	
	public class SendEncuestasUnsentCerrarSesionTask extends AsyncTask<Void, Void, SendEncuestaResult> {

		@Override
		protected void onPreExecute() {
			dialog.setCancelable(false);
			dialog.setTitle("Enviando encuestas pendientes.");
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
				enctaController.cleanStoredEncuestas();
				showMessage("Las encuestas han sido enviadas exitosamente.", Toast.LENGTH_LONG);
			} else {
				showMessage("Hubo un error al enviar las encuestas.", Toast.LENGTH_LONG);
			}
			logOut();
		}
	}
	
	private void showMessage(String text, int length){
		Toast.makeText(this, text, length).show();
	}
	

}
