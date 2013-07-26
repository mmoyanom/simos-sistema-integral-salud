package gob.sis.simos;

import gob.sis.simos.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class Anx01Activity extends Activity implements OnItemSelectedListener, OnCheckedChangeListener {
	int check;
	boolean isOpen = true;
	private LinearLayout rootLayout;
	
	private LinearLayout layoutDevoluciones;
	private LinearLayout layoutRG1;
	private LinearLayout layoutRG2;
	private RadioGroup radioGroup1;
	private RadioGroup radioGroup2;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_anx01);
		
		getActionBar().setIcon(null);
		getActionBar().setDisplayShowHomeEnabled(false);
		getActionBar().setDisplayShowTitleEnabled(true);
		getActionBar().setTitle("Pagos realizados por los asegurados");
		
		/*sp1 = (Spinner) findViewById(R.id.spinner3);
		sp1.setOnItemSelectedListener(this);
		
		sp9 = (Spinner) findViewById(R.id.spinner9);
		sp9.setOnItemSelectedListener(this);*/

		layoutDevoluciones = (LinearLayout)findViewById(R.id.layoutDevoluciones);
		
		layoutRG1 = (LinearLayout)findViewById(R.id.layoutRG1);
		layoutRG2 = (LinearLayout)findViewById(R.id.layoutRG2);
		
		radioGroup1 = (RadioGroup)findViewById(R.id.radioGroup1);
		radioGroup1.setOnCheckedChangeListener(this);
		
		radioGroup2 = (RadioGroup)findViewById(R.id.radioGroup2);
		radioGroup2.setOnCheckedChangeListener(this);
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		return super.onCreateOptionsMenu(menu);
	}
	
	public void slideToBottom(View view){
		
		TranslateAnimation animate = new TranslateAnimation(0,0,0,view.getHeight());
		animate.setDuration(500);
		animate.setFillAfter(true);
		view.startAnimation(animate);
		view.setVisibility(View.GONE);
	}
	
	public void slideToTop(View view){
		TranslateAnimation animate = new TranslateAnimation(0,0,0,-view.getHeight());
		animate.setDuration(500);
		animate.setFillAfter(true);
		view.startAnimation(animate);
		view.setVisibility(View.GONE);
		}

	@Override
	public void onItemSelected(AdapterView<?> arg0, View view, int position, long id) {
		
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		
	}
	
	public void showPagoContainer(){
		TranslateAnimation anim = null;
		 rootLayout.setVisibility(View.VISIBLE);
	     anim = new TranslateAnimation(0.0f, 0.0f, rootLayout.getHeight(), 0.0f);
	     anim.setDuration(500);
		 anim.setInterpolator(new AccelerateInterpolator(2.0f));
		 rootLayout.startAnimation(anim);
	}
	
	public void animatedShowView(View view){
		TranslateAnimation anim = null;
		 rootLayout.setVisibility(View.VISIBLE);
	     anim = new TranslateAnimation(0.0f, 0.0f, view.getHeight(), 0.0f);
	     anim.setDuration(500);
		 anim.setInterpolator(new AccelerateInterpolator(2.0f));
		 rootLayout.startAnimation(anim);
	}
	
	public void hidePagoContainer(){
		TranslateAnimation anim = null;
		anim = new TranslateAnimation(0.0f, 0.0f, 0.0f, rootLayout.getHeight());
        anim.setAnimationListener(collapseListener);
        anim.setDuration(500);
	    anim.setInterpolator(new AccelerateInterpolator(2.0f));
	    rootLayout.startAnimation(anim);
	}
	
	public void animatedHideView(View view){
		TranslateAnimation anim = null;
		anim = new TranslateAnimation(0.0f, 0.0f, 0.0f, view.getHeight());
        anim.setAnimationListener(collapseListener);
        anim.setDuration(500);
	    anim.setInterpolator(new AccelerateInterpolator(2.0f));
	    rootLayout.startAnimation(anim);
	}

	Animation.AnimationListener collapseListener = new Animation.AnimationListener() {
	    public void onAnimationEnd(Animation animation) {
	        rootLayout.setVisibility(View.GONE);
	    }

	    @Override
	    public void onAnimationRepeat(Animation animation) {
	    }

	    @Override
	    public void onAnimationStart(Animation animation) {
	    }
	};

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		if(group != null){
			if(group == radioGroup1){
				RadioButton checked = (RadioButton)findViewById(checkedId);
				if(group.indexOfChild(checked) == 0){
					layoutRG1.setVisibility(View.VISIBLE);
					layoutDevoluciones.setVisibility(View.VISIBLE);
					//animatedShowView(layoutRG1);
					//animatedShowView(layoutRG2);
				} else {
					layoutRG1.setVisibility(View.GONE);
					layoutDevoluciones.setVisibility(View.GONE);
					//animatedHideView(layoutRG1);
					//animatedHideView(layoutRG2);
				}
			} else if(group == radioGroup2){
				RadioButton checked = (RadioButton)findViewById(checkedId);
				if(group.indexOfChild(checked) == 0){
					layoutRG2.setVisibility(View.VISIBLE);
				} else {
					layoutRG2.setVisibility(View.GONE);
				}
			}
			
			
		}
			
	}


}
