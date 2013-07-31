package gob.sis.simos;

import roboguice.activity.RoboActivity;
import roboguice.inject.InjectView;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class InquestPaymentActivity extends RoboActivity implements OnCheckedChangeListener {

	@InjectView(R.id.rg_services)
	protected RadioGroup _rgServices;
	
	protected Menu menu;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		this.setContentView(R.layout.activity_payment_inquest_01);
		String[] items = new String[11];
		items[0] = "Consulta";
		items[1] = "Medicamentos";
		items[2] = "Insumos";
		items[3] = "Examenes auxiliares";
		items[4] = "Procedimientos de apoyo al diagnóstico";
		items[5] = "Traslado de emergencia";
		items[6] = "Alimentación (En caso de traslados por emergencia)";
		items[7] = "Tomografía";
		items[8] = "Procedimientos especiales";
		items[9] = "Resonancia magnética";
		items[10] = "Otros";
		
		for(int i = 0; i < items.length ; i++){
			RadioButton rb = new RadioButton(this);
			rb.setText(items[i]);
			this._rgServices.addView(rb);
		}
		this._rgServices.setOnCheckedChangeListener(this);
		getActionBar().setDisplayShowHomeEnabled(false);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.person_information_menu, menu);
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		Intent i = new Intent(this, InquestPayment02Activity.class);
		this.startActivity(i);
		return true;
	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		
	/*	RadioButton r = (RadioButton) this.findViewById(checkedId);
		int index = this._rgServices.indexOfChild(r);
		MenuItem item = menu.findItem(R.id.action_next);
		if(index == 0){
			item.setTitle("SIGUIENTE");	
		} else {
			item.setTitle("GUARDAR");	
		}*/
	}
}
