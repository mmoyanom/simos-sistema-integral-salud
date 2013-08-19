package gob.sis.simos;

import gob.sis.simos.entity.VerificacionPago;
import roboguice.activity.RoboActivity;
import roboguice.inject.InjectView;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class VerificacionPagos01Activity extends RoboActivity implements OnCheckedChangeListener {
	
	@InjectView(R.id.rg_payment_location)
	protected RadioGroup _rgPaymentLocation;
	
	@InjectView(R.id.separator_layout_nro_ticket)
	protected View separatorLayoutTicket;
	
	@InjectView(R.id.layout_nro_ticket)
	protected LinearLayout layoutNroTicket;
	
	private VerificacionPago verificacion;
	
	public static final int ADD_SERVICE = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {	
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.frgmnt_vrfccn_pgs_01);
		getActionBar().setDisplayShowHomeEnabled(false);
		
		this._rgPaymentLocation.setOnCheckedChangeListener(this);
		
		this.verificacion = (VerificacionPago) getIntent().getSerializableExtra("verificacion");
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.menu_payment_01, menu);
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		Intent i = new Intent(this, VerificacionPagos02Activity.class);
		this.startActivityForResult(i, ADD_SERVICE);
		return true;
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		this.setResult(RESULT_OK);
		this.finish();
	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		RadioButton r = (RadioButton) this.findViewById(checkedId);
		int index = this._rgPaymentLocation.indexOfChild(r);
		if(index == 0 || index == 4){
			this.separatorLayoutTicket.setVisibility(View.VISIBLE);
			this.layoutNroTicket.setVisibility(View.VISIBLE);
		} else {
			this.separatorLayoutTicket.setVisibility(View.GONE);
			this.layoutNroTicket.setVisibility(View.GONE);	
		}
	}

}
