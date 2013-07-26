package gob.sis.simos;

import gob.sis.simos.adapters.MainMenuListAdapter;
import gob.sis.simos.controller.MainMenuController;
import gob.sis.simos.dto.MenuOption;
import java.util.ArrayList;
import roboguice.activity.RoboActivity;
import roboguice.inject.InjectView;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class MainMenuActivity extends RoboActivity implements
		OnItemClickListener {

	@InjectView(gob.sis.simos.R.id.lstMainMenu)
	protected ListView _lstMainMenu;

	private MainMenuController _controller;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_main_menu);

		this._controller = new MainMenuController();
		this._controller.setContext(this);

		ArrayList<MenuOption> items = this._controller.getMainMenuItems();
		MainMenuListAdapter adapter = new MainMenuListAdapter(this,
				R.layout.adapter_main_menu, items);
		this._lstMainMenu.setAdapter(adapter);
		this._lstMainMenu.setOnItemClickListener(this);
	}

	@Override
	public void onItemClick(AdapterView<?> view, View v, int position,
			long resourceId) {

		MenuOption m = (MenuOption) this._lstMainMenu
				.getItemAtPosition(position);
		if (m.getId() == 0) {
			Intent i = new Intent(this, EESSListActivity.class);
			this.startActivity(i);
		}
	}

}
