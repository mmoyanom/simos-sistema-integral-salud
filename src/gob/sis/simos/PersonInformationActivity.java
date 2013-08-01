package gob.sis.simos;

import roboguice.activity.RoboActivity;
import roboguice.inject.InjectView;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

public class PersonInformationActivity extends RoboActivity implements OnCheckedChangeListener {

	@InjectView(R.id.lbl_identification) protected TextView _lblIdentification;
	@InjectView(R.id.lbl_document_type) protected TextView _lblDni;
	@InjectView(R.id.lbl_id_number) protected TextView _lblNroDoc;
	@InjectView(R.id.lbl_genere) protected TextView _lblGenere;
	
	@InjectView(R.id.lbl_patient) protected TextView _lblPatient;
	@InjectView(R.id.lbl_relationship) protected TextView _lblRelationship;
	@InjectView(R.id.lbl_have_reference) protected TextView _lblHaveReference;
	@InjectView(R.id.lbl_reason) protected TextView _lblReason;
	@InjectView(R.id.rg_refence) protected RadioGroup _rgReference;
	
	@InjectView(R.id.layout_reason) protected LinearLayout _layoutReason;
	@InjectView(R.id.separator_reason) protected View _separator;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		this.setContentView(R.layout.activity_person_information);
		
		Typeface font0 = Typeface.createFromAsset(getAssets(), "fonts/Arial Rounded Bold.ttf");
		
		this._lblIdentification.setTypeface(font0);
		this._lblDni.setTypeface(font0);
		this._lblNroDoc.setTypeface(font0);
		this._lblGenere.setTypeface(font0);
		
		this._lblPatient.setTypeface(font0);
		this._lblRelationship.setTypeface(font0);
		this._lblHaveReference.setTypeface(font0);
		
		this._lblReason.setTypeface(font0);
		this._separator.setVisibility(View.GONE);
		this._layoutReason.setVisibility(View.GONE);
		
		this._rgReference.setOnCheckedChangeListener(this);
		
		getActionBar().setDisplayShowHomeEnabled(false);
		/*getActionBar().setDisplayShowTitleEnabled(false);
		getActionBar().setDisplayShowHomeEnabled(false);
		getActionBar().setTitle("Hola");*/
		
		/*LinearLayout actionBarLayout = (LinearLayout) getLayoutInflater().inflate(
	            R.layout.actionbar_person_information,null);
		
		ActionBar actionBar = this.getActionBar();
		actionBar.setDisplayShowHomeEnabled(false);
	    actionBar.setDisplayShowTitleEnabled(false);
	    actionBar.setDisplayShowCustomEnabled(true);
	    actionBar.setCustomView(actionBarLayout);*/
		
	}

	@Override
	public void onCheckedChanged(RadioGroup rg, int checkedId) {
		
		RadioButton r = (RadioButton) this.findViewById(checkedId);
		int index = this._rgReference.indexOfChild(r); 
		if(index == 0){
			this._separator.setVisibility(View.INVISIBLE);
			this._layoutReason.setVisibility(View.GONE);
		} else {
			this._separator.setVisibility(View.VISIBLE);
			this._layoutReason.setVisibility(View.VISIBLE);
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		
		//super.onCreateOptionsMenu(menu);
		
		/*MenuItem menuItemCreateCart = menu.findItem(R.id.action_next);
	    if (menuItemCreateCart  == null) {
	        menuItemCreateCart  = menu.add(0, R.id.action_next, 0, "Siguiente");
	    }
	    
		TextView tv = new TextView(this);
	    tv.setText("Hola");
	    tv.setTextColor(getResources().getColor(R.color.actionButton_color));
	    tv.setBackgroundColor(getResources().getColor(R.color.item_color));
	    
	    menuItemCreateCart.setActionView(tv);*/
		MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.person_information_menu, menu);
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		//Toast.makeText(this, "Click!", Toast.LENGTH_LONG).show();
		Intent i = new Intent(this, InquestPaymentActivity00.class);
		this.startActivity(i);
		return true;
	}
	
}
