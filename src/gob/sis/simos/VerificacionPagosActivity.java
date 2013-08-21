package gob.sis.simos;

import gob.sis.simos.entity.VerificacionPago;
import gob.sis.simos.fragment.VerificacionPagos01Fragment;
import gob.sis.simos.fragment.VerificacionPagos02Fragment;
import gob.sis.simos.fragment.VerificacionPagosTicketsFragment;
import roboguice.activity.RoboFragmentActivity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.InputFilter;
import android.text.Spanned;
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
import android.widget.TextView;

public class VerificacionPagosActivity extends RoboFragmentActivity implements FragmentManager.OnBackStackChangedListener, OnClickListener, InputFilter {
	
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
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.actvt_vrfccn_pgs);
		
		this.frgmnt1 = new VerificacionPagos01Fragment();
		this.frgmnt2 = new VerificacionPagos02Fragment();
		this.frgmntTickets = new VerificacionPagosTicketsFragment();
		
		this.verificacion = (VerificacionPago) getIntent().getSerializableExtra("verificacion");
		
		this.layoutBtnAdd = (LinearLayout)findViewById(R.id.layout_btn_add);
		
		this.btnAddTicket = (Button)findViewById(R.id.btn_add);
		this.btnAddTicket.setOnClickListener(this);
		
		FragmentManager mgr = getSupportFragmentManager();
		mgr.addOnBackStackChangedListener(this);
		
		FragmentTransaction fmt = mgr.beginTransaction();
		fmt.add(R.id.fragment_container, frgmnt1);
		fmt.commit();
		
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
		if(frgmnt1.isVisible()){
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
		return true;
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
		    if (fm.getBackStackEntryCount() > 0) {
		        Log.i("MainActivity", "popping backstack");
		        getSupportFragmentManager().popBackStack();
		        if(frgmntTickets.isVisible()){
		        	layoutBtnAdd.setVisibility(View.GONE);
		        }
		        if(frgmnt2.isVisible()){
		        	MenuItem item = this.menu.getItem(0);
		        	item.setTitle("SIGUIENTE");
		        }
		    } else {
		        Log.i("MainActivity", "nothing on backstack, calling super");
		        super.onBackPressed();  
		    }
	}

	@Override
	public void onClick(View v) {
		if(v == this.btnAddTicket){
			LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		    final View layout = inflater.inflate(R.layout.dialog_add_ticket, (ViewGroup) findViewById(R.id.layout_add_ticket));
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setMessage("Ingrese el numero de boleta")
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
			etTicket = (EditText)layout.findViewById(R.id.et_ticket);
			etTicket.setFilters(new InputFilter[]{this});
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

}
