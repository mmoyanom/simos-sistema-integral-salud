package gob.sis.simos;

import java.util.List;

import gob.sis.simos.adapters.InsumoListAdapter;
import gob.sis.simos.adapters.MedicamentoListAdapter;
import gob.sis.simos.controller.RecetaController;
import gob.sis.simos.entity.ICuantificable;
import gob.sis.simos.entity.Insumo;
import gob.sis.simos.entity.Medicamento;
import gob.sis.simos.ui.DialogCantidad;
import roboguice.activity.RoboActivity;
import roboguice.inject.InjectView;
import android.app.ActionBar.LayoutParams;
import android.content.Intent;
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
	
	private ICuantificable cuantificable;
	private DialogCantidad dialog;
	
	private int index;
	
	//private MedicamentoListAdapter medicamentoAdapter;
	//private InsumoListAdapter insumoAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		this.setContentView(R.layout.actvt_add_to_rcta);
		
		this.getActionBar().setDisplayShowHomeEnabled(false);
		this.getActionBar().setIcon(R.drawable.ic_menu_close_clear_cancel);
		
		index = getIntent().getExtras().getInt("index");
		if(index == 0){
			MedicamentoListAdapter adapter = new MedicamentoListAdapter(this, R.layout.adptr_insms_medcmnto_simple_list, controller.findMedicamento(""));
			this.lstResult.setAdapter(adapter);
		} else if(index == 1){
			InsumoListAdapter adapter = new InsumoListAdapter(this, R.layout.adptr_insms_medcmnto_simple_list, controller.getInsumos());
			this.lstResult.setAdapter(adapter);
		}
		
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
			dialog.dismiss();
			if(v == dialog.btnOK){
				if(cuantificable != null){
					if(cuantificable instanceof Medicamento){
						Medicamento m = (Medicamento)cuantificable;
						m.setEntregado(dialog.getCantidadEntregada());
						m.setRecetado(dialog.getCantidadRecetada());
						this.setResult(RESULT_OK,new Intent().putExtra("data", m));
						this.cuantificable = null;
						this.finish();
					} else if(cuantificable instanceof Insumo){
						Insumo i = (Insumo)cuantificable;
						i.setEntregado(dialog.getCantidadEntregada());
						i.setRecetado(dialog.getCantidadRecetada());
						this.setResult(RESULT_OK,new Intent().putExtra("data", i));
						this.cuantificable = null;
						this.finish();
					}
				}
			} else if (v == dialog.btnCANCEL){
				dialog.reset();
				this.cuantificable = null;
			}
		}
	}

	@Override
	public boolean onQueryTextChange(String newText) {
		return false;
	}

	@Override
	public boolean onQueryTextSubmit(String query) {
		
		if(query.length() > 5) {
			if(index == 0){
				List<Medicamento> items = controller.findMedicamento(query);
				MedicamentoListAdapter adapter = (MedicamentoListAdapter)lstResult.getAdapter();
				adapter.setItems(items);
				adapter.notifyDataSetChanged();
			} else if (index == 1) {
				List<Insumo> items = controller.findInsumos(query);
				InsumoListAdapter adapter = (InsumoListAdapter)lstResult.getAdapter();
				adapter.setItems(items);
				adapter.notifyDataSetChanged();
			}
		}
		
		return true;
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
		ICuantificable c = (ICuantificable)this.lstResult.getItemAtPosition(position);
		cuantificable = c;
		dialog = new DialogCantidad(this);
		if(c instanceof Medicamento){
			dialog.setTitle(((Medicamento)c).getNombre());
		} else if(c instanceof Insumo){
			dialog.setTitle(((Insumo)c).getNombre());
		}
		dialog.btnOK.setOnClickListener(this);
		dialog.btnCANCEL.setOnClickListener(this);
		dialog.show();
	}

	
}
