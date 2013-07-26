package gob.sis.simos;

import gob.sis.simos.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class Annx02Activity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_annxsecond);

		getActionBar().setIcon(R.drawable.prescription);
		//getActionBar().setDisplayShowHomeEnabled(false);
		//getActionBar().setDisplayShowTitleEnabled(true);
		
		getActionBar().setIcon(R.drawable.logo_minsa);
		getActionBar().setTitle("ENTREGA DE RECETAS");
		
		Spinner sp1 = (Spinner) findViewById(R.id.spinner1);
		ArrayAdapter adapter1 = ArrayAdapter.createFromResource(this, R.array.country_arrays, R.layout.item_spinner);
		adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		sp1.setAdapter(adapter1);
		
		Spinner sp2 = (Spinner) findViewById(R.id.spinner2);
		ArrayAdapter adapter2 = ArrayAdapter.createFromResource(this, R.array.entrega_arrays, R.layout.item_spinner);
		adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		sp2.setAdapter(adapter2);
		
		Spinner sp3 = (Spinner) findViewById(R.id.spinner3);
		ArrayAdapter adapter3 = ArrayAdapter.createFromResource(this, R.array.entrega_arrays, R.layout.item_spinner);
		adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		sp3.setAdapter(adapter3);
		
		Spinner sp4 = (Spinner) findViewById(R.id.spinner4);
		ArrayAdapter adapter4 = ArrayAdapter.createFromResource(this, R.array.receta_arrays, R.layout.item_spinner);
		adapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		sp4.setAdapter(adapter4);
		
		Spinner sp5 = (Spinner) findViewById(R.id.spinner5);
		ArrayAdapter adapter5 = ArrayAdapter.createFromResource(this, R.array.receta_arrays, R.layout.item_spinner);
		adapter5.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		sp5.setAdapter(adapter5);
		/*TextView textView = (TextView)findViewById(R.id.textView1);
		
		Typeface type=Typeface.createFromAsset(this.getAssets(),"Arial Rounded Bold.ttf");
		textView.setTypeface(type);*/
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.annx02, menu);
		return true;
	}

}
