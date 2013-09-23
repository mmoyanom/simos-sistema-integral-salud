package gob.sis.simos;

import gob.sis.simos.ListaAsignacionesActivity.SendEncuestasUnsentTask;
import gob.sis.simos.adapters.OpcionRespuestaSpinnerAdapter;
import gob.sis.simos.controller.AsignacionController;
import gob.sis.simos.controller.EncuestaController;
import gob.sis.simos.controller.InfoEncuestadoController;
import gob.sis.simos.controller.JornadaController;
import gob.sis.simos.controller.LoginController;
import gob.sis.simos.entity.Encuesta01;
import gob.sis.simos.entity.OpcionRespuesta;
import gob.sis.simos.entity.Respuesta;
import gob.sis.simos.soap.SendEncuestaResult;
import gob.sis.simos.ui.UIEditText;
import gob.sis.simos.ui.UIRadioButton;

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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.inject.Inject;

public class InformacionEncuestadoActivity extends RoboActivity implements OnCheckedChangeListener {

	@Inject
	protected InfoEncuestadoController infoController;
	@Inject
	protected LoginController loginController; 
	@Inject
	protected AsignacionController asigController;
	@Inject
	protected EncuestaController enctaController;
	@Inject
	protected JornadaController jndaController;
	
	private Encuesta01 encuesta;
	private static final int CALL_ENCUESTA = 1;
	
	@InjectView(R.id.rg_refence) protected RadioGroup _rgReference;
	
	@InjectView(R.id.layout_reason) protected LinearLayout _layoutReason;
	@InjectView(R.id.separator_reason) protected View _separator;
	
	@InjectView(R.id.sp_document_type) private Spinner _spDocumentType;
	@InjectView(R.id.sp_relacion_paciente) private Spinner _spRelacionPaciente;
	@InjectView(R.id.sp_referencia) private Spinner _spReferencia;
	
	@InjectView(R.id.number_id) private UIEditText etNumberId;
	@InjectView(R.id.rg_genere) private RadioGroup rgGenere;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		this.setContentView(R.layout.actvt_inf_encstdo);
		
		this.encuesta = new Encuesta01();
		
		dialog = new ProgressDialog(this);
		
		this._separator.setVisibility(View.GONE);
		this._layoutReason.setVisibility(View.GONE);
		
		this._rgReference.setOnCheckedChangeListener(this);
		
		getActionBar().setDisplayShowHomeEnabled(false);
		
		List<OpcionRespuesta> items = infoController.getRespuestas(1);
		OpcionRespuestaSpinnerAdapter adapter = new OpcionRespuestaSpinnerAdapter(this, items);
		_spDocumentType.setAdapter(adapter);
		
		items = infoController.getRespuestas(4);
		adapter = new OpcionRespuestaSpinnerAdapter(this, items);
		_spRelacionPaciente.setAdapter(adapter);
		
		items = infoController.getRespuestas(6);
		adapter = new OpcionRespuestaSpinnerAdapter(this, items);
		_spReferencia.setAdapter(adapter);
	}

	@Override
	public void onCheckedChanged(RadioGroup rg, int checkedId) {
		
		RadioButton r = (RadioButton) this.findViewById(checkedId);
		int index = this._rgReference.indexOfChild(r); 
		if(index == 0){
			this._separator.setVisibility(View.INVISIBLE);
			this._layoutReason.setVisibility(View.GONE);
		} else {
			this._separator.setVisibility(View.VISIBLE);
			this._layoutReason.setVisibility(View.VISIBLE);
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.person_information_menu, menu);
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {		
		
		if(requestCode == CALL_ENCUESTA){
			if(resultCode == RESULT_OK){
				clear();
				SendEncuestaResult rslt = (SendEncuestaResult)data.getSerializableExtra("sendEncuestaResult");
				if(rslt.getResult()==SendEncuestaResult.SUCCEEDED){
					showMessage(getResources().getString(R.string.msg_send_encuesta_succeeded), Toast.LENGTH_LONG);
				} else {
					showMessage(rslt.getErrorMessage(), Toast.LENGTH_LONG);
				}
				return;
			} else if(resultCode == RESULT_CANCELED){
				showMessage(getResources().getString(R.string.msg_canceled_by_user), Toast.LENGTH_LONG);
				return;
			}
		}
	}
	/**
	 * Este metodo limpia los campos
	 * */
	private void clear() {
		
		this._spDocumentType.setSelection(0);
		this.etNumberId.setText("");
		this.rgGenere.clearCheck();
		this._spRelacionPaciente.setSelection(0);
		this._rgReference.clearCheck();
		this._spReferencia.setSelection(0);
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		String str = isClear();
		if(!str.isEmpty()){
			showMessage(str, Toast.LENGTH_SHORT);
			return false;
		} else {
			List<Respuesta> rspts = getRespuestas();
			this.encuesta.setDatosEncuestado(rspts);
			
			Intent i = new Intent(this, Encuesta01PrincipalActivity.class);
			i.putExtra("encuesta", this.encuesta);
			this.startActivityForResult(i, CALL_ENCUESTA);
			return true;
		}
		
	}
	
	private String isClear(){
		String str = "No respondio la pregunta \"%s\".";
		
		if(this.etNumberId.getText().toString().trim().length() == 0){
			return String.format(str, "Nro. Documento");
		}
		if(this.rgGenere.getCheckedRadioButtonId() == -1){
			return String.format(str, "Sexo");
		}
		if(this._rgReference.getCheckedRadioButtonId() == -1){
			return String.format(str, "Hoja de referencia");
		}
		
		return "";
	}
	
	private List<Respuesta> getRespuestas(){
		List<Respuesta> rspts = new ArrayList<Respuesta>();
		
		OpcionRespuesta orDocumentType = (OpcionRespuesta)this._spDocumentType.getSelectedItem();
		Respuesta rpDocumentType = new Respuesta();
		rpDocumentType.setOpcionRespuestaId(orDocumentType.getOpcionRespuestaId());
		rpDocumentType.setPreguntaId(orDocumentType.getPreguntaId());
		rspts.add(rpDocumentType);
		
		String numberId = etNumberId.getText().toString();
		Respuesta rpNumberId = new Respuesta();
		rpNumberId.setOpcionRespuestaId(etNumberId.getOpcionRespuestaId());
		rpNumberId.setPreguntaId(etNumberId.getPreguntaId());
		rpNumberId.setRespuestaTexto(numberId);
		rspts.add(rpNumberId);
		
		int idGenere = this.rgGenere.getCheckedRadioButtonId();
		UIRadioButton rbGenere = (UIRadioButton)this.rgGenere.findViewById(idGenere);
		Respuesta rpGenere = rbGenere.getRespuesta();
		rpGenere.setPreguntaParentId(null);
		rpGenere.setRespuestaParentId(null);
		rspts.add(rpGenere);
		
		OpcionRespuesta orPatientRelation = (OpcionRespuesta)this._spRelacionPaciente.getSelectedItem();
		Respuesta rpPatientRelation = new Respuesta();
		rpPatientRelation.setOpcionRespuestaId(orPatientRelation.getOpcionRespuestaId());
		rpPatientRelation.setPreguntaId(orPatientRelation.getPreguntaId());
		rpPatientRelation.setRespuestaParentId(null);
		rspts.add(rpPatientRelation);
		
		int idReference = this._rgReference.getCheckedRadioButtonId();
		UIRadioButton rbReference = (UIRadioButton)this._rgReference.findViewById(idReference);
		Respuesta rpReference = rbReference.getRespuesta();
		rpReference.setPreguntaParentId(null);
		rpReference.setRespuestaParentId(null);
		rspts.add(rpReference);
		
		if(rpReference.getPreguntaId().equals(15)){
			OpcionRespuesta orNoReferenceSheet = (OpcionRespuesta)this._spReferencia.getSelectedItem();
			Respuesta rpNoReferenceSheet = new Respuesta();
			rpNoReferenceSheet.setOpcionRespuestaId(orNoReferenceSheet.getOpcionRespuestaId());
			rpNoReferenceSheet.setPreguntaId(orNoReferenceSheet.getPreguntaId());
			rpNoReferenceSheet.setRespuestaParentId(null);
			rspts.add(rpNoReferenceSheet);			
		}

		return rspts;
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		checkDay();
	}
	
	private void checkDay(){
		if(jndaController.jornadaIniciada() && !jndaController.equalsToday(jndaController.getJornada().getStart())){
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
	
	private void finalizeDay(String strMessage){
		AlertDialog alertDialog1 = new AlertDialog.Builder(this).create();
	    alertDialog1.setTitle("Jornada Finalizada");
	    alertDialog1.setMessage(strMessage);
	    alertDialog1.setButton(AlertDialog.BUTTON_POSITIVE,"OK", new DialogInterface.OnClickListener() {
	        	
	            public void onClick(DialogInterface dialog, int which) {
	            	Intent in = new Intent(InformacionEncuestadoActivity.this, MenuPrincipalActivity.class);
	            	in.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
	            	in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	            	startActivity(in);
	            }
	     });
	    alertDialog1.show();
	}

	private void showMessage(String text, int length){
		Toast.makeText(this, text, length).show();
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
				str = "Hubo un error al enviar las encuestas.";
			}
			finalizeDay(str);
		}
	}
	
}
