package gob.sis.simos;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import roboguice.activity.RoboActivity;

public class InquestPayment03Activity extends RoboActivity {

	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		this.setContentView(R.layout.activity_payment_inquest_03);
		getActionBar().setDisplayShowHomeEnabled(false);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.person_information_menu, menu);
		return super.onCreateOptionsMenu(menu);
		
	}
}
