package gob.sis.simos;

import java.util.ArrayList;
import java.util.List;

import gob.sis.simos.adapters.InquestListAdapter;
import android.os.Bundle;
import android.widget.ListView;
import roboguice.activity.RoboActivity;
import roboguice.inject.InjectView;

public class InquestListActivity extends RoboActivity {

	@InjectView(R.id.lst_inquest)
	protected ListView _lstInquest;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		this.setContentView(R.layout.activity_inquest);
		
		List<String> items = new ArrayList<String>();
		items.add("Primer formulario");
		
		InquestListAdapter adapter = new InquestListAdapter(this, R.layout.adapter_inquest_list, items);
		this._lstInquest.setAdapter(adapter);
	}
	
}
