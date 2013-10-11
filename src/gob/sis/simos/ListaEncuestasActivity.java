package gob.sis.simos;

import gob.sis.simos.adapters.MenuEncuestaListAdapter;
import gob.sis.simos.application.MyApplication;
import gob.sis.simos.dto.OpcionMenu;

import java.util.ArrayList;
import java.util.List;

import roboguice.activity.RoboActivity;
import roboguice.inject.InjectView;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class ListaEncuestasActivity extends RoboActivity implements OnItemClickListener {

	@InjectView(R.id.lst_inquest)
	protected ListView _lstInquest;
	
	private MyApplication application;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		this.setContentView(R.layout.actvt_lst_encsts);
		
		List<OpcionMenu> items = new ArrayList<OpcionMenu>();
		
		OpcionMenu op = new OpcionMenu();
		op.setTitle("Verificacion de pagos y entrega de medicamentos.");
		op.setId(R.drawable.f1);
		op.setDescription("Contiene preguntas sobre la verificacion de pagos de servicios y entrega de medicamentos segun prescripcion.");
		op.setValue("F1");
		items.add(op);
		
		MenuEncuestaListAdapter adapter = new MenuEncuestaListAdapter(this, R.layout.simple_list_item_image, items);
		this._lstInquest.setAdapter(adapter);
		this._lstInquest.setOnItemClickListener(this);
		
		application = (MyApplication)getApplication();
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
		Intent i = new Intent(this, InformacionEncuestadoActivity.class);
		application.setSelectedFormulario(1);
		this.startActivity(i);
	}
	
}
