package gob.sis.simos;

import gob.sis.simos.adapters.EncuestaListAdapter;

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
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		this.setContentView(R.layout.actvt_lst_encsts);
		
		List<String> items = new ArrayList<String>();
		items.add("Primer formulario");
		
		EncuestaListAdapter adapter = new EncuestaListAdapter(this, R.layout.adptr_encsts_simple_list, items);
		this._lstInquest.setAdapter(adapter);
		this._lstInquest.setOnItemClickListener(this);
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
		Intent i = new Intent(this, InformacionEncuestadoActivity.class);
		this.startActivity(i);
	}
	
}
