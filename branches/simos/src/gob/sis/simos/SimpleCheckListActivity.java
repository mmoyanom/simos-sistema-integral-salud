package gob.sis.simos;

import java.util.ArrayList;
import java.util.List;

import com.google.inject.Inject;

import gob.sis.simos.adapters.SimpleCheckListAdapter;
import gob.sis.simos.controller.VerificacionPagoController;
import gob.sis.simos.entity.OpcionRespuesta;
import roboguice.activity.RoboActivity;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;

@ContentView(R.layout.actvt_simple_check_list)
public class SimpleCheckListActivity extends RoboActivity implements OnClickListener {

	@InjectView(R.id.lst_check_list)
	private ListView lstCheckList;
	
	@InjectView(R.id.btn_OK)
	private Button btnOk;
	
	private SimpleCheckListAdapter adapter;
	
	@Inject
	private VerificacionPagoController verificacionController;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		this.btnOk.setOnClickListener(this);
		
		Bundle b = getIntent().getExtras();
		Integer preguntaId = b.getInt("preguntaId");
		if(preguntaId != null){
			if(preguntaId == 14){
				List<OpcionRespuesta> items = verificacionController.getRespuestas(14);
				adapter = new SimpleCheckListAdapter(this, R.layout.adptr_simple_check_list, items);
				lstCheckList.setAdapter(adapter);
			}
		}
	}

	@Override
	public void onClick(View v) {
		if(v == this.btnOk){
			Intent intent = new Intent();
			
			List<String> items = new ArrayList<String>();
			for(int i = 0; i < lstCheckList.getCount(); i ++){
				OpcionRespuesta or = (OpcionRespuesta)lstCheckList.getItemAtPosition(i);
				if(or.isChecked()){
					items.add(or.getDescripcion());
				}
			}
			String[] array = new String[items.size()];
			array= items.toArray(array);
			intent.putExtra("string_array", array);
			this.setResult(RESULT_OK, intent);
			this.finish();
		}
	}
}