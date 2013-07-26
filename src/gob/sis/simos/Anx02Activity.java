package gob.sis.simos;

import gob.sis.simos.R;

import pe.gob.simos.app.photo.PhotoHandler;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.hardware.Camera;
import android.hardware.Camera.CameraInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class Anx02Activity extends Activity implements OnClickListener {
	
	public final static String DEBUG_TAG = "Anx02Activity";
	private Camera camera;
	private int cameraId;
	private Button btnTakePicture;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_anx02);
		
		//getActionBar().setIcon(R.drawable.prescription);
		getActionBar().setIcon(null);
		getActionBar().setDisplayShowHomeEnabled(false);
		getActionBar().setDisplayShowTitleEnabled(true);
		getActionBar().setTitle("Entrega de recetas");
		
		if (!getPackageManager()
		        .hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
		      Toast.makeText(this, "No camera on this device", Toast.LENGTH_LONG)
		          .show();
		    } else {
		      cameraId = findFrontFacingCamera();
		      if (cameraId < 0) {
		        Toast.makeText(this, "No front facing camera found.",
		            Toast.LENGTH_LONG).show();
		      } else {
		        camera = Camera.open(cameraId);
		      }
		}
		
		//btnTakePicture = (Button)findViewById(R.id.btn_take_picture);
		//btnTakePicture.setOnClickListener(this);
		/*Spinner sp1 = (Spinner) findViewById(R.id.spinner1);
		ArrayAdapter adapter1 = ArrayAdapter.createFromResource(this, R.array.country_arrays, R.layout.item_spinner);
		adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		sp1.setAdapter(adapter1);*/
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0, 0, 0, "Guardar").setIcon(android.R.drawable.ic_menu_save)
        .setShowAsAction(MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);
		return super.onCreateOptionsMenu(menu);
	}
	
	private int findFrontFacingCamera() {
	    int cameraId = -1;
	    // Search for the front facing camera
	    int numberOfCameras = Camera.getNumberOfCameras();
	    for (int i = 0; i < numberOfCameras; i++) {
	      CameraInfo info = new CameraInfo();
	      Camera.getCameraInfo(i, info);
	      if (info.facing == CameraInfo.CAMERA_FACING_FRONT) {
	        Log.d(DEBUG_TAG, "Camera found");
	        cameraId = i;
	        break;
	      }
	    }
	    return cameraId;
	}
	
	@Override
	protected void onPause() {
		if (camera != null) {
		      camera.release();
		      camera = null;
		}
		super.onPause();
	}

	@Override
	public void onClick(View v) {
		/*if(v == btnTakePicture){
			camera.takePicture(null, null, new PhotoHandler(getApplicationContext()));
		}*/
	}
}
