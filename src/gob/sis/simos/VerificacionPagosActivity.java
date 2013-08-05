package gob.sis.simos;

import gob.sis.simos.fragment.VerificacionPagos01Fragment;
import gob.sis.simos.fragment.VerificacionPagos02Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class VerificacionPagosActivity extends FragmentActivity implements FragmentManager.OnBackStackChangedListener {
	
	private VerificacionPagos01Fragment frgmnt1;
	private VerificacionPagos02Fragment frgmnt2;
	private String dynamicFragment = "_dynamicFragment";
	private Menu menu;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.actvt_vrfccn_pgs);
		
		frgmnt1 = new VerificacionPagos01Fragment();
		frgmnt2 = new VerificacionPagos02Fragment();
		
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
			FragmentManager mgr = getSupportFragmentManager();
			FragmentTransaction fmt = mgr.beginTransaction();
			fmt.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
			fmt.remove(frgmnt1).add(R.id.fragment_container, frgmnt2);
			fmt.addToBackStack(dynamicFragment);
			fmt.commit();
			item.setTitle("Guardar");
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
		        if(frgmnt2.isVisible()){
		        	MenuItem item = this.menu.getItem(0);
		        	item.setTitle("SIGUIENTE");
		        }
		    } else {
		        Log.i("MainActivity", "nothing on backstack, calling super");
		        super.onBackPressed();  
		    }
	}

}
