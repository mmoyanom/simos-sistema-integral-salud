package gob.sis.simos;

import android.opengl.Visibility;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import roboguice.activity.RoboActivity;
import roboguice.inject.InjectView;

public class InquestPayment02Activity extends RoboActivity implements OnCheckedChangeListener {
	
	@InjectView(R.id.rg_payment_location)
	protected RadioGroup _rgPaymentLocation;
	
	@InjectView(R.id.separator_layout_nro_ticket)
	protected View separatorLayoutTicket;
	
	@InjectView(R.id.layout_nro_ticket)
	protected LinearLayout layoutNroTicket;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {	
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_payment_inquest_02);
		getActionBar().setDisplayShowHomeEnabled(false);
		
		this._rgPaymentLocation.setOnCheckedChangeListener(this);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.person_information_menu, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		RadioButton r = (RadioButton) this.findViewById(checkedId);
		int index = this._rgPaymentLocation.indexOfChild(r);
		if(index == 0){
			this.separatorLayoutTicket.setVisibility(View.VISIBLE);
			this.layoutNroTicket.setVisibility(View.VISIBLE);
		} else {
			this.separatorLayoutTicket.setVisibility(View.GONE);
			this.layoutNroTicket.setVisibility(View.GONE);	
		}
	}

}
