package gob.sis.simos;

import gob.sis.simos.adapters.EstablecimientoSaludListAdapter;
import gob.sis.simos.controller.EstablecimientoSaludController;
import gob.sis.simos.entity.EstablecimientoSalud;
import gob.sis.simos.ui.DialogItinerario;

import java.util.List;

import roboguice.activity.RoboActivity;
import roboguice.inject.InjectView;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.google.inject.Inject;

public class ListaEstablecimientoSaludActivity extends RoboActivity implements OnItemClickListener {

	@InjectView(R.id.lst_eess)
	protected ListView _lstEESS;

	@Inject
	protected EstablecimientoSaludController _controller;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		this.setContentView(R.layout.actvt_lst_eess);

		//this._controller.setContext(this);

		List<EstablecimientoSalud> items = this._controller.getEESSList();
		EstablecimientoSaludListAdapter adapter = new EstablecimientoSaludListAdapter(this,
				R.layout.adptr_eess_simple_list, items);
		this._lstEESS.setAdapter(adapter);
		this._lstEESS.setOnItemClickListener(this);
		DialogItinerario dialog = new DialogItinerario(this);
		dialog.show();
	}

	@Override
	public void onItemClick(AdapterView<?> view, View v, int position, long resourceId) {
		Intent i = new Intent(this, ListaEncuestasActivity.class);
		this.startActivity(i);
	}
	
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		//this.overridePendingTransition(R.anim.animation_enter,R.anim.animation_leave);
	}

}
