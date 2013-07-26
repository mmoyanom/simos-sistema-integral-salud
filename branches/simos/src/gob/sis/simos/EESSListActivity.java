package gob.sis.simos;

import java.util.List;

import com.google.inject.Inject;

import gob.sis.simos.adapters.EESSListAdapter;
import gob.sis.simos.controller.EESSController;
import gob.sis.simos.entity.EESS;
import gob.sis.simos.ui.DialogDiaryTask;
import roboguice.activity.RoboActivity;
import roboguice.inject.InjectView;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class EESSListActivity extends RoboActivity implements OnItemClickListener {

	@InjectView(R.id.lst_eess)
	protected ListView _lstEESS;

	@Inject
	protected EESSController _controller;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		this.setContentView(R.layout.activity_eess_list);

		this._controller.setContext(this);

		List<EESS> items = this._controller.getEESSList();
		EESSListAdapter adapter = new EESSListAdapter(this,
				R.layout.adapter_eess_list, items);
		this._lstEESS.setAdapter(adapter);
		this._lstEESS.setOnItemClickListener(this);
		DialogDiaryTask dialog = new DialogDiaryTask(this);
		dialog.show();
	}

	@Override
	public void onItemClick(AdapterView<?> view, View v, int position, long resourceId) {
		Intent i = new Intent(this, InquestListActivity.class);
		this.startActivity(i);
	}
	
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		//this.overridePendingTransition(R.anim.animation_enter,R.anim.animation_leave);
	}

}
