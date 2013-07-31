package gob.sis.simos;

import gob.sis.simos.adapters.MedicamentoListAdapter;
import gob.sis.simos.controller.PrescriptionController;
import gob.sis.simos.entity.Medicamento;
import gob.sis.simos.ui.DialogEnterQuantity;

import com.google.inject.Inject;

import roboguice.activity.RoboActivity;
import roboguice.inject.InjectView;
import android.app.ActionBar.LayoutParams;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

public class AddPrescriptionActivity extends RoboActivity implements OnClickListener, SearchView.OnQueryTextListener, OnItemClickListener {
	
	@Inject
	protected PrescriptionController controller; 
	
	@InjectView(R.id.lst_result)
	protected ListView lstResult;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		this.setContentView(R.layout.activity_prescription_add);
		
		this.getActionBar().setDisplayShowHomeEnabled(false);
		this.getActionBar().setIcon(R.drawable.ic_menu_close_clear_cancel);
		
		MedicamentoListAdapter adapter = new MedicamentoListAdapter(this, R.layout.adapter_searchlist_medicine, controller.buscarMedicamento(""));
		this.lstResult.setAdapter(adapter);
		this.lstResult.setOnItemClickListener(this);
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

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
		
		Medicamento m = (Medicamento)this.lstResult.getItemAtPosition(position);
		
		DialogEnterQuantity dialog = new DialogEnterQuantity(this);
		dialog.setTitle(m.getName());
		dialog.show();
	}

	
}
