package gob.sis.simos;

import gob.sis.simos.entity.OpcionRespuesta;
import gob.sis.simos.entity.Respuesta;
import gob.sis.simos.entity.VerificacionPago;
import gob.sis.simos.fragment.VerificacionPagos00Fragment;
import gob.sis.simos.fragment.VerificacionPagos01Fragment;
import gob.sis.simos.fragment.VerificacionPagos02Fragment;
import gob.sis.simos.fragment.VerificacionPagosTicketsFragment;

import java.util.ArrayList;
import java.util.List;

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
	
	private VerificacionPagos00Fragment frgmnt0;
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
	private static final int ADD_TICKETS = 6;
	public static final int ADD_SERVICE = 0;
	public static final int EDIT_SERVICE = 3;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.actvt_vrfccn_pgs);
		
		this.frgmnt0 = new VerificacionPagos00Fragment();
		this.frgmnt0.setArguments(getIntent().getExtras());
		
		this.frgmnt1 = new VerificacionPagos01Fragment();
		this.frgmnt2 = new VerificacionPagos02Fragment();
		this.frgmntTickets = new VerificacionPagosTicketsFragment();	
		
		this.layoutBtnAdd = (LinearLayout)findViewById(R.id.layout_btn_add);
		
		this.btnAddTicket = (Button)findViewById(R.id.btn_add);
		this.btnAddTicket.setOnClickListener(this);
		
		this.verificacion = (VerificacionPago) getIntent().getSerializableExtra("verificacion");
		
		FragmentManager mgr = getSupportFragmentManager();
		mgr.addOnBackStackChangedListener(this);
		
		FragmentTransaction fmt = mgr.beginTransaction();
		fmt.add(R.id.fragment_container, frgmnt0);
		fmt.commit();
		loadVerificacion();
	}
	
	
	protected void loadVerificacion(){
		
		if(this.verificacion != null){
			this.frgmnt0.setVerificacion(this.verificacion);
			this.frgmnt1.setVerificacion(this.verificacion);
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
		if(frgmnt0.isVisible()){
			String response = frgmnt0.isClear();
			if(!response.isEmpty()){
				showMessage(response, Toast.LENGTH_SHORT);
			} else {
				this.verificacion.setNombre(frgmnt0.getSelectedServiceName());
				this.verificacion.setPaid(this.frgmnt0.goAhead()?"Si":"No");
				
				if(frgmnt0.goAhead()){
					FragmentManager mgr = getSupportFragmentManager();
					FragmentTransaction fmt = mgr.beginTransaction();
					fmt.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
					fmt.remove(frgmnt0).add(R.id.fragment_container, frgmnt1);
					fmt.addToBackStack(dynamicFragment);
					fmt.commit();
					item.setTitle("SIGUIENTE");
				} else {
					saveVerificacion();
				}
			}
			
		} else if(frgmnt1.isVisible()){
			String response = frgmnt1.isClear();
			if(!response.isEmpty()){
				showMessage(response, Toast.LENGTH_SHORT);
			} else {
				FragmentManager mgr = getSupportFragmentManager();
				FragmentTransaction fmt = mgr.beginTransaction();
				fmt.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
				fmt.remove(frgmnt1).add(R.id.fragment_container, frgmnt2);
				fmt.addToBackStack(dynamicFragment);
				fmt.commit();
				item.setTitle("Guardar");
			}
			
			/* Cuando se ingresan las boletas */
		} else if (frgmnt2.isVisible()){
			String response = frgmnt2.isClear();
			if(!response.isEmpty()){
				showMessage(response, Toast.LENGTH_SHORT);
			} else {
				saveVerificacion();
			}
			
		}
		return true;
	}

	private void saveVerificacion() {
		
		List<Respuesta> respuestas = new ArrayList<Respuesta>();
		
		if(this.frgmnt0.isVisible()){
			Respuesta rp7 = this.frgmnt0.getRespuestaService();
			List<Respuesta> child = new ArrayList<Respuesta>();
			child.add(this.frgmnt0.getRespuestaRealizoPago());
			rp7.setChild(child);
			respuestas.add(rp7);
			
		} else if(this.frgmnt2.isVisible()){
			Respuesta rp7 = this.frgmnt0.getRespuestaService();
			List<Respuesta> child = new ArrayList<Respuesta>();
			child.add(this.frgmnt0.getRespuestaRealizoPago());
			child.addAll(this.frgmnt1.getRespuestas());
			child.addAll(this.frgmnt2.getRespuestas());
			
			rp7.setChild(child);
			respuestas.add(rp7);
			
		}
		
		this.verificacion.setRespuestas(respuestas);
		
		for(int i=0; i < verificacion.getRespuestas().size() ; i++){
			Respuesta r = verificacion.getRespuestas().get(i);
			if( r == null){
				System.out.println(i+" "+r);
			}else{
				System.out.println(String.format("pId : %s, opId : %s, nbr: %s, txt: %s",
						r.getPreguntaId(),
						r.getOpcionRespuestaId(),
						r.getRespuestaNumero(),
						r.getRespuestaTexto()));
			}
		}
		
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
		alertDialogBuilder.setTitle("Guardar");
		alertDialogBuilder
		.setMessage("ÀDesea guardar la verificacion?")
		.setCancelable(false)
		.setPositiveButton("Si",new DialogInterface.OnClickListener(){
			public void onClick(DialogInterface dialog,int id) {
					Intent in = new Intent(VerificacionPagosActivity.this, Encuesta01PrincipalActivity.class);
					in.putExtra("verificacion", verificacion);
					setResult(RESULT_OK, in);
					finish();
				}
			  })
		.setNegativeButton("No",new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog,int id) {
				dialog.cancel();
			}
		});
		AlertDialog alertDialog = alertDialogBuilder.create();
		alertDialog.show();
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
		        if(frgmnt2.isVisible() && count == 2){
		        	if(count == 1){
		        		MenuItem item = this.menu.getItem(0);
			        	item.setTitle("SIGUIENTE");
			        	layoutBtnAdd.setVisibility(View.GONE);
		        	}else if(count == 2){
		        		MenuItem item = this.menu.getItem(0);
			        	item.setTitle("SIGUIENTE");
			        	layoutBtnAdd.setVisibility(View.VISIBLE);
		        	}
		        } else if (frgmnt2.isVisible() && count == 1){
		        	MenuItem item = this.menu.getItem(0);
		        	item.setTitle("SIGUIENTE");
		        	layoutBtnAdd.setVisibility(View.GONE);
		        }
		    } else {
		        Log.i("MainActivity", "nothing on backstack, calling super");
		        super.onBackPressed();  
		    }
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		
		if(requestCode == ADD_TICKETS && resultCode == Activity.RESULT_OK){
			Bundle b = data.getExtras();
			if(b != null){
				Bundle array = b.getBundle("bundle_tickets");
				if(array.size() == 0){
					this.frgmnt1.txtTickets.setText("No se han agregado boletas.");
					return;
				}
				
				String[] keys = new String[array.size()]; 
				array.keySet().toArray(keys);
				StringBuffer sbf = new StringBuffer();
				this.frgmnt1.rsptsTickets.clear();
				for(int i = 0 ; i < array.size(); i++){
					String r = array.getString(keys[i]);
					sbf.append("* ").append(r);
					this.frgmnt1.rsptsTickets.add(r);
					if(i != array.size()-1){
						sbf.append("\n");
					}
				}
				this.frgmnt1.txtTickets.setText(sbf.toString());
			}
			
		} else if(requestCode == SELECT_ITEMS && resultCode == Activity.RESULT_OK){
			Bundle b = data.getExtras();
			if(b != null){
				Integer preguntaId = b.getInt("bundle_preguntaId");
				if(preguntaId != null){
					
					if(preguntaId == 19){
						Bundle array = b.getBundle("bundle");
						if(array.size() == 0){
							this.frgmnt2.txtPregunta19.setText("Ninguno seleccionado.");
							return;
						}
						
						String[] keys = new String[array.size()]; 
						array.keySet().toArray(keys);
						StringBuffer sbf = new StringBuffer();
						this.frgmnt2.rsptsPregunta19.clear();
						for(int i = 0 ; i < array.size(); i++){
							OpcionRespuesta or = (OpcionRespuesta)array.get(keys[i]);
							sbf.append("* ").append(or.getDescripcion());
							Respuesta r = new Respuesta();
							r.setPreguntaId(or.getPreguntaId());
							r.setOpcionRespuestaId(or.getOpcionRespuestaId());
							r.setRespuestaTexto(or.getDescripcion());
							
							// pregunta 19
							this.frgmnt2.rsptsPregunta19.add(r);
							if(i != array.size() -1){
								sbf.append("\n");
							}
						}
						this.frgmnt2.txtPregunta19.setText(sbf.toString());
						
					} else if(preguntaId == 17){
						Bundle array = b.getBundle("bundle");
						if(array.size() == 0){
							this.frgmnt2.txtPregunta17.setText("Ninguno seleccionado.");
							return;
						}
						
						String[] keys = new String[array.size()]; 
						array.keySet().toArray(keys);
						StringBuffer sbf = new StringBuffer();
						this.frgmnt2.rsptsPregunta17.clear();
						for(int i = 0 ; i < array.size(); i++){
							OpcionRespuesta or = (OpcionRespuesta)array.get(keys[i]);
							sbf.append("* ").append(or.getDescripcion());
							Respuesta r = new Respuesta();
							r.setPreguntaId(or.getPreguntaId());
							r.setOpcionRespuestaId(or.getOpcionRespuestaId());
							r.setRespuestaTexto(or.getDescripcion());
							
							// pregunta 17
							this.frgmnt2.rsptsPregunta17.add(r);
							if(i != array.size() -1){
								sbf.append("\n");
							}
						}
						this.frgmnt2.txtPregunta17.setText(sbf.toString());
						
					} else if(preguntaId == 16){
						Bundle array = b.getBundle("bundle");
						if(array.size() == 0){
							this.frgmnt2.txtPregunta16.setText("Ninguno seleccionado.");
							return;
						}
						
						String[] keys = new String[array.size()]; 
						array.keySet().toArray(keys);
						StringBuffer sbf = new StringBuffer();
						this.frgmnt2.rsptsPregunta16.clear();
						for(int i = 0 ; i < array.size(); i++){
							OpcionRespuesta or = (OpcionRespuesta)array.get(keys[i]);
							sbf.append("* ").append(or.getDescripcion());
							Respuesta r = new Respuesta();
							r.setPreguntaId(or.getPreguntaId());
							r.setOpcionRespuestaId(or.getOpcionRespuestaId());
							r.setRespuestaTexto(or.getDescripcion());
							
							// pregunta 16
							this.frgmnt2.rsptsPregunta16.add(r);
							if(i != array.size() -1){
								sbf.append("\n");
							}
						}
						this.frgmnt2.txtPregunta16.setText(sbf.toString());
						
					} else if(preguntaId == 14){
						Bundle array = b.getBundle("bundle");
						if(array.size() == 0){
							this.frgmnt1.txtPaymentIn.setText("Ninguno seleccionado.");
							return;
						}
						
						String[] keys = new String[array.size()]; 
						array.keySet().toArray(keys);
						StringBuffer sbf = new StringBuffer();
						this.frgmnt1.rsptsPaymentIn.clear();
						for(int i = 0 ; i < array.size(); i++){
							OpcionRespuesta or = (OpcionRespuesta)array.get(keys[i]);
							sbf.append("* ").append(or.getDescripcion());
							Respuesta r = new Respuesta();
							r.setPreguntaId(or.getPreguntaId());
							r.setOpcionRespuestaId(or.getOpcionRespuestaId());
							r.setRespuestaTexto(or.getDescripcion());
							
							// pregunta 14
							this.frgmnt1.rsptsPaymentIn.add(r);
							if(i != array.size() -1){
								sbf.append("\n");
							}
						}
						this.frgmnt1.txtPaymentIn.setText(sbf.toString());
						
					} else if(preguntaId == 11){
						Bundle array = b.getBundle("bundle");
						if(array.size() == 0){
							this.frgmnt1.txtPaymentOut.setText("Ninguno seleccionado.");
							return;
						}
						
						String[] keys = new String[array.size()]; 
						array.keySet().toArray(keys);
						StringBuffer sbf = new StringBuffer();
						this.frgmnt1.rsptsPaymentOut.clear();
						for(int i = 0 ; i < array.size(); i++){
							OpcionRespuesta or = (OpcionRespuesta)array.get(keys[i]);
							sbf.append("* ").append(or.getDescripcion());
							Respuesta r = new Respuesta();
							r.setPreguntaId(or.getPreguntaId());
							r.setOpcionRespuestaId(or.getOpcionRespuestaId());
							r.setRespuestaTexto(or.getDescripcion());
							
							// pregunta 11
							this.frgmnt1.rsptsPaymentOut.add(r);
							if(i != array.size() -1){
								sbf.append("\n");
							}
						}
						this.frgmnt1.txtPaymentOut.setText(sbf.toString());
					} 
					
				}
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
