package gob.sis.simos;

import java.util.ArrayList;
import java.util.List;

import gob.sis.simos.adapters.InquestSelectableListAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import roboguice.activity.RoboActivity;
import roboguice.inject.InjectView;

public class InquestListActivity extends RoboActivity implements OnItemClickListener {

	@InjectView(R.id.lst_inquest)
	protected ListView _lstInquest;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		this.setContentView(R.layout.activity_inquest);
		
		List<String> items = new ArrayList<String>();
		items.add("Primer formulario");
		
		InquestSelectableListAdapter adapter = new InquestSelectableListAdapter(this, R.layout.adapter_inquest_list, items);
		this._lstInquest.setAdapter(adapter);
		this._lstInquest.setOnItemClickListener(this);
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
		Intent i = new Intent(this, PersonInformationActivity.class);
		this.startActivity(i);
	}
	
}
