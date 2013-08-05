package gob.sis.simos;

import roboguice.activity.RoboActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class VerificacionPagos02Activity extends RoboActivity {

	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		this.setContentView(gob.sis.simos.R.layout.frgmnt_vrfccn_pgs_02);
		getActionBar().setDisplayShowHomeEnabled(false);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.menu_payment_02, menu);
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		this.setResult(RESULT_OK);
		this.finish();
		
		return true;
	}
}
