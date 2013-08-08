package gob.sis.simos;

import gob.sis.simos.adapters.MenuPrincipalListAdapter;
import gob.sis.simos.controller.ApplicationController;
import gob.sis.simos.dto.OpcionMenu;
import java.util.ArrayList;
import roboguice.activity.RoboActivity;
import roboguice.inject.InjectView;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class MenuPrincipalActivity extends RoboActivity implements
		OnItemClickListener {

	@InjectView(gob.sis.simos.R.id.lstMainMenu)
	protected ListView _lstMainMenu;

	private ApplicationController _controller;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.actvt_menu_principal);

		this._controller = new ApplicationController();
		this._controller.setContext(this);

		ArrayList<OpcionMenu> items = this._controller.getMainMenuItems();
		MenuPrincipalListAdapter adapter = new MenuPrincipalListAdapter(this,
				R.layout.adptr_main_menu_simple_list, items);
		this._lstMainMenu.setAdapter(adapter);
		this._lstMainMenu.setOnItemClickListener(this);
	}

	@Override
	public void onItemClick(AdapterView<?> view, View v, int position,
			long resourceId) {

		OpcionMenu m = (OpcionMenu) this._lstMainMenu.getItemAtPosition(position);
		if (m.getId() == 0) {
			Intent i = new Intent(this, ListaEstablecimientoSaludActivity.class);
			this.startActivity(i);
		}
	}

}
