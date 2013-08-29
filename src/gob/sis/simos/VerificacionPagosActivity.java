package gob.sis.simos;

import gob.sis.simos.entity.OpcionRespuesta;
import gob.sis.simos.entity.Respuesta;
import gob.sis.simos.entity.VerificacionPago;
import gob.sis.simos.fragment.VerificacionPagos01Fragment;
import gob.sis.simos.fragment.VerificacionPagos02Fragment;
import gob.sis.simos.fragment.VerificacionPagosTicketsFragment;
import roboguice.activity.RoboFragmentActivity;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

public class VerificacionPagosActivity extends RoboFragmentActivity implements FragmentManager.OnBackStackChangedListener, OnClickListener, InputFilter, TextWatcher {
	
	private VerificacionPagos01Fragment frgmnt1;
	private VerificacionPagos02Fragment frgmnt2;
	private VerificacionPagosTicketsFragment frgmntTickets;
	private String dynamicFragment = "_dynamicFragment";
	private Menu menu;
	private LinearLayout layoutBtnAdd;
	private Button btnAddTicket;
	private EditText etTicket;
	private AlertDialog alert;
	private VerificacionPago verificacion;
	private static final int SELECT_ITEMS = 5;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.actvt_vrfccn_pgs);
		
		this.frgmnt1 = new VerificacionPagos01Fragment();
		this.frgmnt2 = new VerificacionPagos02Fragment();
		this.frgmntTickets = new VerificacionPagosTicketsFragment();
		
		this.layoutBtnAdd = (LinearLayout)findViewById(R.id.layout_btn_add);
		
		this.btnAddTicket = (Button)findViewById(R.id.btn_add);
		this.btnAddTicket.setOnClickListener(this);
		
		FragmentManager mgr = getSupportFragmentManager();
		mgr.addOnBackStackChangedListener(this);
		
		FragmentTransaction fmt = mgr.beginTransaction();
		fmt.add(R.id.fragment_container, frgmnt1);
		fmt.commit();
		loadVerificacion();
	}
	
	
	
	private void loadVerificacion(){
		this.verificacion = (VerificacionPago) getIntent().getSerializableExtra("verificacion");
		Respuesta or9 = new Respuesta();
		or9.setPreguntaId(9);
		or9.setOpcionRespuestaId(39);
		this.verificacion.getRespuestas().add(or9);
		
		Respuesta or10 = new Respuesta();
		or10.setPreguntaId(10);
		or10.setOpcionRespuestaId(42);
		this.verificacion.getRespuestas().add(or10);
		
		Respuesta or11 = new Respuesta();
		or11.setPreguntaId(11);
		or11.setOpcionRespuestaId(46);
		this.verificacion.getRespuestas().add(or11);
		
		Respuesta or12 = new Respuesta();
		or12.setPreguntaId(12);
		or12.setOpcionRespuestaId(55);
		or12.setRespuestaTexto("123456;654321");
		this.verificacion.getRespuestas().add(or12);
		
		Respuesta or13 = new Respuesta();
		or13.setPreguntaId(13);
		or13.setOpcionRespuestaId(56);
		or13.setRespuestaNumero(18.0);
		this.verificacion.getRespuestas().add(or13);
		
		Respuesta or14 = new Respuesta();
		or14.setPreguntaId(14);
		or14.setOpcionRespuestaId(58);
		this.verificacion.getRespuestas().add(or14);
		
		// MULTIPLE!!!!!
		/*Respuesta or15 = new Respuesta();
		or15.setPreguntaId(14);
		or15.setOpcionRespuestaId(57);
		this.verificacion.getRespuestas().add(or14);*/
		
		Respuesta or15 = new Respuesta();
		or15.setPreguntaId(15);
		or15.setOpcionRespuestaId(66);
		this.verificacion.getRespuestas().add(or15);
		
		Respuesta or16 = new Respuesta();
		or16.setPreguntaId(16);
		or16.setOpcionRespuestaId(70);
		this.verificacion.getRespuestas().add(or16);
		
		Respuesta or17 = new Respuesta();
		or17.setPreguntaId(17);
		or17.setOpcionRespuestaId(86);
		this.verificacion.getRespuestas().add(or17);
		
		Respuesta or18 = new Respuesta();
		or18.setPreguntaId(18);
		or18.setOpcionRespuestaId(108);
		this.verificacion.getRespuestas().add(or18);
		
		Respuesta or19 = new Respuesta();
		or19.setPreguntaId(19);
		or19.setOpcionRespuestaId(88);
		this.verificacion.getRespuestas().add(or19);
		
		Respuesta or20 = new Respuesta();
		or20.setPreguntaId(20);
		or20.setOpcionRespuestaId(98);
		this.verificacion.getRespuestas().add(or20);
		
		if(this.verificacion != null){
			this.frgmnt1.setVerificacion(this.verificacion);
			this.frgmntTickets.setVerificacion(this.verificacion);
			this.frgmnt2.setVerificacion(this.verificacion);
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.menu_payment_01, menu);
	    this.menu = menu;
		return true;
	}
	
	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		/* Cuando el fragmento 1 es visible */
		if(frgmnt1.isVisible()){
			if(frgmnt1.isClear()){
				showMessage("Por favor, Aségurese de responder todas las preguntas.", Toast.LENGTH_SHORT);
			} else {
				if(frgmnt1.enterTickets()){
					layoutBtnAdd.setVisibility(View.VISIBLE);
					FragmentManager mgr = getSupportFragmentManager();
					FragmentTransaction fmt = mgr.beginTransaction();
					fmt.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
					fmt.remove(frgmnt1).add(R.id.fragment_container, frgmntTickets);
					fmt.addToBackStack(dynamicFragment);
					fmt.commit();
					item.setTitle("Siguiente");
				} else {
					FragmentManager mgr = getSupportFragmentManager();
					FragmentTransaction fmt = mgr.beginTransaction();
					fmt.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
					fmt.remove(frgmnt1).add(R.id.fragment_container, frgmnt2);
					fmt.addToBackStack(dynamicFragment);
					fmt.commit();
					item.setTitle("Guardar");
				}
			}
			
			/* Cuando se ingresan las boletas */
		} else if (frgmntTickets.isVisible()){
			if(frgmntTickets.getTicketsCount() > 0){
				layoutBtnAdd.setVisibility(View.GONE);
				FragmentManager mgr = getSupportFragmentManager();
				FragmentTransaction fmt = mgr.beginTransaction();
				fmt.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
				fmt.remove(frgmntTickets).add(R.id.fragment_container, frgmnt2);
				fmt.addToBackStack(dynamicFragment);
				fmt.commit();
				item.setTitle("Guardar");
			} else {
				showMessage("Usted cuenta con boletas. Debe ingresar, al menos un numero de boleta.", Toast.LENGTH_LONG);
			}
		} else if (frgmnt2.isVisible()){
			saveVerificacion();
		}
		return true;
	}

	private void saveVerificacion() {	
		
	}

	@Override
	public void onBackStackChanged() {
		FragmentManager mgr = getSupportFragmentManager();
		int counter = mgr.getBackStackEntryCount();
		for(int i=0; i<counter; i++){
			Log.d("gob.sis.simos", mgr.getBackStackEntryAt(i).toString());
		}
	}
	
	@Override
	public void onBackPressed() {
		 FragmentManager fm = getSupportFragmentManager();
		 int count = fm.getBackStackEntryCount();
		    if (count > 0) {
		        Log.i("MainActivity", "popping backstack");
		        getSupportFragmentManager().popBackStack();
		        if(frgmntTickets.isVisible()){
		        	layoutBtnAdd.setVisibility(View.GONE);
		        }
		        if(frgmnt2.isVisible()){
		        	if(count == 1){
		        		MenuItem item = this.menu.getItem(0);
			        	item.setTitle("SIGUIENTE");
			        	layoutBtnAdd.setVisibility(View.GONE);
		        	}else if(count == 2){
		        		MenuItem item = this.menu.getItem(0);
			        	item.setTitle("SIGUIENTE");
			        	layoutBtnAdd.setVisibility(View.VISIBLE);
		        	}
		        }
		    } else {
		        Log.i("MainActivity", "nothing on backstack, calling super");
		        super.onBackPressed();  
		    }
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		
		if(requestCode == SELECT_ITEMS && resultCode == Activity.RESULT_OK){
			Bundle b = data.getExtras();
			if(b != null){
				Bundle array = b.getBundle("bundle");
				if(array.size() == 0){
					this.frgmnt1.txtPaymentIn.setText("Ninguno seleccionado.");
				}
				
				String[] keys = new String[array.size()]; 
				array.keySet().toArray(keys);
				StringBuffer sbf = new StringBuffer();
				
				for(int i = 0 ; i < array.size(); i++){
					OpcionRespuesta or = (OpcionRespuesta)array.get(keys[i]);
					sbf.append("* ").append(or.getDescripcion());
					if(i != array.size() -1){
						sbf.append("\n");
					}
				}
				this.frgmnt1.txtPaymentIn.setText(sbf.toString());
			}
		}
	}

	@Override
	public void onClick(View v) {
		if(v == this.btnAddTicket){
			LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		    final View layout = inflater.inflate(R.layout.dialog_add_ticket, (ViewGroup) findViewById(R.id.layout_add_ticket));
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder
				.setTitle("Agregar Boleta")
				.setView(layout)
				.setCancelable(false)
				.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						
					}
				})
				.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
					@Override
			        public void onClick(DialogInterface dialog, int id) {
			        	   if(frgmntTickets != null){
			        		   if(frgmntTickets.adapter != null){
			        			   String ticketNumber = etTicket.getText().toString();
			        			   frgmntTickets.adapter.add(ticketNumber);
			        		   }
			        	   }
			        }
			     });
			alert = builder.create();
			alert.show();
			alert.getButton(Dialog.BUTTON_POSITIVE).setEnabled(false);
			etTicket = (EditText)layout.findViewById(R.id.et_ticket);
			etTicket.setFilters(new InputFilter[]{this});
			etTicket.addTextChangedListener(this);
			etTicket.requestFocus();
		}
	}

	@Override
	public CharSequence filter(CharSequence source, int start, int end,
			Spanned dest, int dstart, int dend) {
		for (int i = start; i < end; i++) {
			if (source.equals("\n")){
				return null;
			}
			if (source.charAt(i) == ','){
				return null;
			}
			if (source.charAt(i) == '-'){
				return null;
			}
            if (!Character.isDigit(source.charAt(i))) { 
            	return ""; 
            }
		}
		return null;
	}
	
	private void showMessage(String text, int length){
		Toast.makeText(this, text, length).show();
	}

	@Override
	public void afterTextChanged(Editable arg0) {
		
	}

	@Override
	public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
			int arg3) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		if(this.etTicket.length() > 0){
			if(alert != null){
				alert.getButton(Dialog.BUTTON_POSITIVE).setEnabled(true);
			}
		} else {
			if(alert != null){
				alert.getButton(Dialog.BUTTON_POSITIVE).setEnabled(false);
			}
		}
	}

}
