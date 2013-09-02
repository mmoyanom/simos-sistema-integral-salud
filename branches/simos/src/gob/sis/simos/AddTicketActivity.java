package gob.sis.simos;

import gob.sis.simos.adapters.TicketListAdapter;

import java.util.ArrayList;
import java.util.List;

import roboguice.activity.RoboActivity;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

@ContentView(R.layout.actvt_vrfccn_tickets)
public class AddTicketActivity extends RoboActivity implements OnItemLongClickListener, View.OnClickListener , DialogInterface.OnClickListener, InputFilter, TextWatcher {
	
	private AlertDialog alertDialog;
	private AlertDialog alertAddTicket;
	private TicketListAdapter adapter;
	private EditText etTicket;
	@InjectView(R.id.lst_tickets) private ListView lstTickets;
	@InjectView(R.id.btn_add) private ImageButton btnAddTicket;
	@InjectView(R.id.btn_done) private Button btnDone;
	private String selectedItem;
	GestureDetector gestureDetector;	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
	
		List<String> rspts = new ArrayList<String>();
		
		Bundle b = getIntent().getExtras();
		if(b != null){
			String[] array = b.getStringArray("bundle_tickets");
			if(array != null){
				for(int x = 0; x < array.length; x++){
					rspts.add(array[x]);
				}
			}
		}
		
		this.adapter = new TicketListAdapter(this, android.R.layout.simple_list_item_1,rspts);
		this.lstTickets.setAdapter(adapter);
		
		this.lstTickets.setOnItemLongClickListener(this);
		
		alertDialog = new AlertDialog.Builder(this).create();
		alertDialog.setTitle("Eliminar");
	    alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Eliminar", this); 
	    alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancelar", this); 
	    
	    this.btnAddTicket.setOnClickListener(this);
	    this.btnDone.setOnClickListener(this);
	    
	    LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    final View layout = inflater.inflate(R.layout.dialog_add_ticket, (ViewGroup) findViewById(R.id.layout_add_ticket));
		
		alertAddTicket = new AlertDialog.Builder(this).create();
		alertAddTicket.setTitle("Agregar Boleta");
		alertAddTicket.setView(layout);
		alertAddTicket.setCancelable(false);
		alertAddTicket.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancelar", this);
		alertAddTicket.setButton(AlertDialog.BUTTON_POSITIVE, "Aceptar", this);
		
		etTicket = (EditText)layout.findViewById(R.id.et_ticket);
		etTicket.setFilters(new InputFilter[]{this});
		etTicket.addTextChangedListener(this);
		etTicket.requestFocus();
		
	}

	@Override
	public boolean onItemLongClick(AdapterView<?> arg0, View view, int position, long id) {
		String r = (String) this.lstTickets.getItemAtPosition(position);
		alertDialog.setMessage(String.format("Desea eliminar '%s'?", r));
		alertDialog.show();
		this.selectedItem = r;
		return false;
	}

	@Override
	public void onClick(View v) {
		
		if(v == this.btnDone){
			Bundle bundle = new Bundle();
			for(int i= 0; i < this.adapter.getCount(); i++){
				String r = this.adapter.getItem(i);
				bundle.putString(""+i, r);
			}
			Intent in = new Intent(this, VerificacionPagosActivity.class);
			in.putExtra("bundle_tickets", bundle);
			setResult(RESULT_OK, in);
			finish();
			
		} else if(v == this.btnAddTicket){
			this.alertAddTicket.show();
			this.etTicket.setText("");
			Button btn = this.alertAddTicket.getButton(DialogInterface.BUTTON_POSITIVE);
			if(btn != null){
				btn.setEnabled(false);
			}
		}
	}

	@Override
	public void onClick(DialogInterface dialog, int which) {
		if(dialog == this.alertAddTicket){
			if(which == DialogInterface.BUTTON_NEGATIVE){
				
			} else if(which == DialogInterface.BUTTON_POSITIVE){
				/*Respuesta r = new Respuesta();
				r.setRespuestaTexto(this.etTicket.getText().toString());
				r.setPreguntaId(11);
				r.setOpcionRespuestaId(55);*/
				this.addTicket(this.etTicket.getText().toString());
			}
		}
		if(dialog == this.alertDialog){
			if(which == DialogInterface.BUTTON_NEGATIVE){
				
			} else if(which == DialogInterface.BUTTON_POSITIVE){
				adapter.remove(this.selectedItem);
			}
			this.selectedItem = null;
			this.lstTickets.setSelection(-1);
		}
	}
	
	private void addTicket(String r){
		if(findTicket(r) == null){
			this.adapter.add(r);
		} else {
			this.showToastMessage(String.format( "Ya existe el elemento '%s'", r), Toast.LENGTH_SHORT);
		}
	}
	
	private String findTicket(String ticket){
		for(int x = 0; x < adapter.getCount(); x++){
			String a = adapter.getItem(x);
			if(a.equals(ticket))
				return a;
		}
		return null;
	}
	
	private void showToastMessage(String msg, int lenght){
		Toast.makeText(this, msg, lenght).show();
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

	@Override
	public void afterTextChanged(Editable text) {
		// TODO Auto-generated method stub
		if(this.alertAddTicket != null){
			if(text.length() > 0){
				this.alertAddTicket.getButton(DialogInterface.BUTTON_POSITIVE).setEnabled(true);
			} else {
				this.alertAddTicket.getButton(DialogInterface.BUTTON_POSITIVE).setEnabled(false);
			}
		}
			
	}

	@Override
	public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
			int arg3) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub
		
	}
}
