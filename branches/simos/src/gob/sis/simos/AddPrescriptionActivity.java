package gob.sis.simos;

import org.h2.result.SearchRow;

import roboguice.activity.RoboActivity;
import android.app.ActionBar.LayoutParams;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.SearchView;
import android.widget.Toast;

public class AddPrescriptionActivity extends RoboActivity implements OnClickListener, SearchView.OnQueryTextListener {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		this.setContentView(R.layout.activity_prescription_add);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.prescription_add_menu, menu);
	    SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
	    LayoutParams layout = new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.MATCH_PARENT);
	    layout.setMargins(0, 0, 0, 20);
	    searchView.setLayoutParams(layout);
	    
	    searchView.setOnQueryTextListener(this);
	    searchView.setIconifiedByDefault(false);
	    searchView.setSubmitButtonEnabled(true);
	    
	    
		return true;
	}

	@Override
	public void onClick(View v) {
		Toast.makeText(this, "Click", Toast.LENGTH_LONG).show();
	}

	@Override
	public boolean onQueryTextChange(String newText) {
		return false;
	}

	@Override
	public boolean onQueryTextSubmit(String query) {
		
		// AQUI SE EJECUTA LA BUSQUEDA!!!
		
		return false;
	}

	
}
