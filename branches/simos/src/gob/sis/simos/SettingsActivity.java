package gob.sis.simos;

import gob.sis.simos.R;
import android.os.Bundle;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Menu;

public class SettingsActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);

		getActionBar().setBackgroundDrawable(new ColorDrawable(Color.argb(25, 10, 0, 0)));
		getActionBar().setIcon(R.drawable.logo_minsa);
		getActionBar().setTitle("");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.settings, menu);
		return true;
	}

}
