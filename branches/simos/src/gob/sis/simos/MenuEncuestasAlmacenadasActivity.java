package gob.sis.simos;

import gob.sis.simos.adapters.MenuEncuestaListAdapter;
import gob.sis.simos.controller.EncuestaController;
import gob.sis.simos.dto.OpcionMenu;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import roboguice.activity.RoboFragmentActivity;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.google.inject.Inject;

@ContentView(R.layout.actvt_menu_strd_encts)
public class MenuEncuestasAlmacenadasActivity extends RoboFragmentActivity implements OnItemClickListener {

	@Inject 							
	private EncuestaController controller;
	
	@InjectView(R.id.lv_stored_encts)
	private ListView lvStoredEncuestas;
	
	private MenuEncuestaListAdapter adapter;
	private AlertDialog alert;
	
	public static final int VIEW_LIST = 1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {		
		super.onCreate(savedInstanceState);
		
		String[] array = getResources().getStringArray(R.array.main_menu_stored_enscts);
		try {
			
			List<OpcionMenu> menu = new ArrayList<OpcionMenu>();
			
			Date today = Calendar.getInstance().getTime();
			
			OpcionMenu op1 = new OpcionMenu();
			op1.setValue(this.controller.findAllByDate(today).size()+"");
			op1.setTitle(array[0]+String.format("(%s)",op1.getValue()));
			op1.setId(R.drawable.all);
			op1.setDescription("Todas las encuestas realizadas.");
			menu.add(op1);
			
			OpcionMenu op2 = new OpcionMenu();
			op2.setValue(this.controller.findSentByDate(today).size()+"");
			op2.setTitle(array[1]+String.format("(%s)",op2.getValue()));
			op2.setId(R.drawable.sent);
			op2.setDescription("Todas las encuestas que fueron enviadas.");
			menu.add(op2);
			
			OpcionMenu op3 = new OpcionMenu();
			op3.setValue(this.controller.findUnsentByDate(today).size()+"");
			op3.setTitle(array[2]+String.format("(%s)",op3.getValue()));
			op3.setId(R.drawable.unsent);
			op3.setDescription("Todas las encuestas que no han podido ser enviadas.");
			menu.add(op3);
			
			adapter = new MenuEncuestaListAdapter(this, R.layout.simple_list_item_image,menu);
			this.lvStoredEncuestas.setAdapter(adapter);
			this.lvStoredEncuestas.setOnItemClickListener(this);
			
		} catch (Exception e) {
			e.printStackTrace();
			showMessage(e.getMessage(), Toast.LENGTH_LONG);
		}
		
	}
	
	private void showMessage(String text, int length){
		Toast.makeText(this, text, length).show();
	}
	
	private void notifyChanges(){

		String[] array = getResources().getStringArray(R.array.main_menu_stored_enscts);
		
		try {
			List<OpcionMenu> menu = new ArrayList<OpcionMenu>();
			
			Date today = Calendar.getInstance().getTime();
			
			OpcionMenu op1 = new OpcionMenu();
			op1.setValue(this.controller.findAllByDate(today).size()+"");
			op1.setTitle(array[0]+String.format("(%s)",op1.getValue()));
			op1.setId(R.drawable.all);
			op1.setDescription("Todas las encuestas realizadas.");
			menu.add(op1);
			
			OpcionMenu op2 = new OpcionMenu();
			op2.setValue(this.controller.findSentByDate(today).size()+"");
			op2.setTitle(array[1]+String.format("(%s)",op2.getValue()));
			op2.setId(R.drawable.sent);
			op2.setDescription("Todas las encuestas que fueron enviadas.");
			menu.add(op2);
			
			OpcionMenu op3 = new OpcionMenu();
			op3.setValue(this.controller.findUnsentByDate(today).size()+"");
			op3.setTitle(array[2]+String.format("(%s)",op3.getValue()));
			op3.setId(R.drawable.unsent);
			op3.setDescription("Todas las encuestas que no han podido ser enviadas.");
			menu.add(op3);
			
			adapter.clear();
			adapter.addAll(menu);
			adapter.notifyDataSetChanged();
			
		} catch (Exception e) {
			e.printStackTrace();
			showMessage(e.getMessage(), Toast.LENGTH_LONG);
		}
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
		OpcionMenu o = (OpcionMenu)this.lvStoredEncuestas.getItemAtPosition(position);
		int c = Integer.parseInt(o.getValue());
		if(c == 0){
			showAlert("No hay elemenos para mostrar.");
			return;
		}
		Intent in = new Intent(this, ListaStoredEncuestasActivity.class);
		in.putExtra("selected", position);
		startActivityForResult(in, VIEW_LIST);
		
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		
		if(requestCode == VIEW_LIST){
			if(resultCode == RESULT_OK){
				this.notifyChanges();
			}
		}
		
	}
	
	private void showAlert(String text){
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(text)
		       .setCancelable(false)
		       .setPositiveButton("OK", new DialogInterface.OnClickListener() {
		           public void onClick(DialogInterface dialog, int id) {
		        	   
		           }
		       });
		alert = builder.create();
		alert.show();
	}

}
