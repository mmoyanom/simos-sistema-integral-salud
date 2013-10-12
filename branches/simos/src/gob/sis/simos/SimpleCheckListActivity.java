package gob.sis.simos;

import gob.sis.simos.adapters.SimpleCheckListAdapter;
import gob.sis.simos.controller.VerificacionPagoController;
import gob.sis.simos.entity.OpcionRespuesta;

import java.util.List;

import roboguice.activity.RoboActivity;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;

import com.google.inject.Inject;

@ContentView(R.layout.actvt_simple_check_list)
public class SimpleCheckListActivity extends RoboActivity implements OnClickListener {

	@InjectView(R.id.lst_check_list)
	private ListView lstCheckList;
	
	@InjectView(R.id.btn_OK)
	private Button btnOk;
	
	private SimpleCheckListAdapter adapter;
	
	private Integer preguntaId;
	
	@Inject
	private VerificacionPagoController verificacionController;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		this.btnOk.setOnClickListener(this);
		
		Bundle b = getIntent().getExtras();
		
		String pregunta_text = b.getString("pregunta_text");
		if (pregunta_text != null) {
			setTitle(pregunta_text);
		}
		
		Integer preguntaId = b.getInt("preguntaId");
		if(preguntaId != null){
			
			this.preguntaId = preguntaId;
			
			if(this.preguntaId == 19){
				List<OpcionRespuesta> items = verificacionController.getRespuestas(19);
				adapter = new SimpleCheckListAdapter(this, R.layout.adptr_simple_check_list, items);
				lstCheckList.setAdapter(adapter);
				
				int[] int_array = b.getIntArray("bundle_pregunta_19");
				if(int_array != null){
					for(int x = 0 ; x < int_array.length; x++){
						for(int i =0; i < adapter.getCount(); i++){
							OpcionRespuesta or = adapter.getItem(i);
							if(or.getOpcionRespuestaId().intValue() == int_array[x]){
								or.setChecked(true);
								break;
							}
						}
					}
				}
				
			} else if(this.preguntaId == 17){
				List<OpcionRespuesta> items = verificacionController.getRespuestas(17);
				adapter = new SimpleCheckListAdapter(this, R.layout.adptr_simple_check_list, items);
				lstCheckList.setAdapter(adapter);
				
				int[] int_array = b.getIntArray("bundle_pregunta_17");
				if(int_array != null){
					for(int x = 0 ; x < int_array.length; x++){
						for(int i =0; i < adapter.getCount(); i++){
							OpcionRespuesta or = adapter.getItem(i);
							if(or.getOpcionRespuestaId().intValue() == int_array[x]){
								or.setChecked(true);
								break;
							}
						}
					}
				}
				
			} else if(this.preguntaId == 16){
				List<OpcionRespuesta> items = verificacionController.getRespuestas(16);
				adapter = new SimpleCheckListAdapter(this, R.layout.adptr_simple_check_list, items);
				lstCheckList.setAdapter(adapter);
				
				int[] int_array = b.getIntArray("bundle_pregunta_16");
				if(int_array != null){
					for(int x = 0 ; x < int_array.length; x++){
						for(int i =0; i < adapter.getCount(); i++){
							OpcionRespuesta or = adapter.getItem(i);
							if(or.getOpcionRespuestaId().intValue() == int_array[x]){
								or.setChecked(true);
								break;
							}
						}
					}
				}
				
			} else if(this.preguntaId == 14){
				List<OpcionRespuesta> items = verificacionController.getRespuestas(14);
				adapter = new SimpleCheckListAdapter(this, R.layout.adptr_simple_check_list, items);
				lstCheckList.setAdapter(adapter);
				
				int[] int_array = b.getIntArray("bundle_pregunta_14");
				if(int_array != null){
					for(int x = 0 ; x < int_array.length; x++){
						for(int i =0; i < adapter.getCount(); i++){
							OpcionRespuesta or = adapter.getItem(i);
							if(or.getOpcionRespuestaId().intValue() == int_array[x]){
								or.setChecked(true);
								break;
							}
						}
					}
				}
				
			} else if(this.preguntaId == 11){
				List<OpcionRespuesta> items = verificacionController.getRespuestas(11);
				adapter = new SimpleCheckListAdapter(this, R.layout.adptr_simple_check_list, items);
				lstCheckList.setAdapter(adapter);
				
				int[] int_array = b.getIntArray("bundle_pregunta_11");
				if(int_array != null){
					for(int x = 0 ; x < int_array.length; x++){
						for(int i =0; i < adapter.getCount(); i++){
							OpcionRespuesta or = adapter.getItem(i);
							if(or.getOpcionRespuestaId().intValue() == int_array[x]){
								or.setChecked(true);
								break;
							}
						}
					}
				}
			}
		}
	}

	@Override
	public void onClick(View v) {
		if(v == this.btnOk){
			Intent intent = new Intent();
			Bundle bundle = new Bundle();
			for(int i = 0; i < lstCheckList.getCount(); i ++){
				OpcionRespuesta or = (OpcionRespuesta)lstCheckList.getItemAtPosition(i);
				if(or.isChecked()){
					bundle.putSerializable(""+i, or);
				}
			}
			intent.putExtra("bundle", bundle);
			intent.putExtra("bundle_preguntaId", preguntaId);
			
			this.setResult(RESULT_OK, intent);
			this.finish();
		}
	}
}
