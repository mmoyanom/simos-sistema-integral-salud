package gob.sis.simos;

import gob.sis.simos.adapters.MedicamentoListAdapter;
import gob.sis.simos.controller.RecetaController;
import gob.sis.simos.entity.Medicamento;
import gob.sis.simos.ui.DialogCantidad;
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

import com.google.inject.Inject;

public class AddToRecetaActivity extends RoboActivity implements OnClickListener, SearchView.OnQueryTextListener, OnItemClickListener {
	
	@Inject
	protected RecetaController controller; 
	
	@InjectView(R.id.lst_result)
	protected ListView lstResult;
	
	DialogCantidad dialog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		this.setContentView(R.layout.actvt_add_to_rcta);
		
		this.getActionBar().setDisplayShowHomeEnabled(false);
		this.getActionBar().setIcon(R.drawable.ic_menu_close_clear_cancel);
		
		MedicamentoListAdapter adapter = new MedicamentoListAdapter(this, R.layout.adptr_insms_medcmnto_simple_list, controller.buscarMedicamento(""));
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
		if(dialog != null){
			if(v == dialog.btnOK){
				this.setResult(RESULT_OK);
				this.finish();
			} else if (v == dialog.btnCANCEL){
				
			}
		}
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
		dialog = new DialogCantidad(this);
		dialog.setTitle(m.getName());
		dialog.btnOK.setOnClickListener(this);
		dialog.btnCANCEL.setOnClickListener(this);
		dialog.show();
	}

	
}
