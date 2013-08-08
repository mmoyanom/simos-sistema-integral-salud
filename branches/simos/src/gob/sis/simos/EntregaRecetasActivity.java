package gob.sis.simos;


import gob.sis.simos.entity.Cuantificable;
import gob.sis.simos.entity.Insumo;
import gob.sis.simos.entity.Medicamento;
import gob.sis.simos.fragment.InsumoCheckListFragment;
import gob.sis.simos.fragment.MedicamentoCheckListFragment;
import gob.sis.simos.ui.DialogCantidad;
import gob.sis.simos.ui.DialogTipoReceta;

import java.util.Locale;

import roboguice.activity.RoboFragmentActivity;
import roboguice.inject.InjectView;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

public class EntregaRecetasActivity extends RoboFragmentActivity
 implements ActionBar.TabListener, OnClickListener {
	
	SectionsPagerAdapter mSectionsPagerAdapter;
	ViewPager mViewPager;
	
	@InjectView(R.id.btn_add)
	protected Button btnAdd;
	
	DialogTipoReceta dialog;
	
	MedicamentoCheckListFragment medicineFragment;
	InsumoCheckListFragment inputFragment;
	
	public static final int ADD_PRESCRIPTION = 1;
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);	
		
		this.setContentView(R.layout.actvt_entrga_rcta);
		
		this.btnAdd.setOnClickListener(this);
		
		final ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		actionBar.setDisplayUseLogoEnabled(false);
		actionBar.setIcon(R.drawable.ic_menu_mark);
		
		mSectionsPagerAdapter = new SectionsPagerAdapter(
				getSupportFragmentManager());
		
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);
		
		mViewPager
		.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
			@Override
			public void onPageSelected(int position) {
				actionBar.setSelectedNavigationItem(position);
			}
		});
		for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
			// Create a tab with text corresponding to the page title defined by
			// the adapter. Also specify this Activity object, which implements
			// the TabListener interface, as the callback (listener) for when
			// this tab is selected.
			actionBar.addTab(actionBar.newTab()
					.setText(mSectionsPagerAdapter.getPageTitle(i))
					.setTabListener(this));
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.prescription_menu, menu);
		return true;
	}
	
	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		return true;
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		int index = mViewPager.getCurrentItem();
		if(index == 0){
			Cuantificable c = (Cuantificable)data.getSerializableExtra("data");
			if(c != null){
				if(c instanceof Medicamento){
					medicineFragment.adapter.add((Medicamento)c);
				}else if(c instanceof Insumo){
					inputFragment.adapter.add((Insumo)c);
				}
			}
		}
	}
	
	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		if(tab.getPosition() == 0){
			this.btnAdd.setText("Agregar medicamento");
		} else if(tab.getPosition() == 1){
			this.btnAdd.setText("Agregar insumo");
		}
		mViewPager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}
	
	
	public class SectionsPagerAdapter extends FragmentPagerAdapter {

		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			// getItem is called to instantiate the fragment for the given page.
			// Return a DummySectionFragment (defined as a static inner class
			// below) with the page number as its lone argument.
			if(position == 0){
				medicineFragment = new MedicamentoCheckListFragment();
				return medicineFragment;
			} else if(position == 1){
				inputFragment = new InsumoCheckListFragment();
				return inputFragment;
			}
			
			/*Fragment fragment = new DummySectionFragment();
			Bundle args = new Bundle();
			args.putInt(DummySectionFragment.ARG_SECTION_NUMBER, position + 1);
			fragment.setArguments(args);*/
			return null;
		}

		@Override
		public int getCount() {
			// Show 3 total pages.
			return 2;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			Locale l = Locale.getDefault();
			switch (position) {
			case 0:
				return getString(R.string.title_section1).toUpperCase(l);
			case 1:
				return getString(R.string.title_section2).toUpperCase(l);
			}
			return null;
		}
	}

	/**
	 * A dummy fragment representing a section of the app, but that simply
	 * displays dummy text.
	 */
	public static class DummySectionFragment extends Fragment {
		
		public static final String ARG_SECTION_NUMBER = "section_number";



		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.frgmnt_medcmntos_check_list,
					container, false);
			
			
			/*ListView lstPrescription = (ListView)rootView.findViewById(R.id.lst_prescription);
			lstPrescription.setAdapter(adapter);*/
			
			/*TextView dummyTextView = (TextView) rootView
					.findViewById(R.id.section_label);
			dummyTextView.setText(Integer.toString(getArguments().getInt(
					ARG_SECTION_NUMBER)));*/
			return rootView;
		}
	}

	@Override
	public void onClick(View v) {
		if(v == this.btnAdd){
			dialog = new DialogTipoReceta(this);
			dialog.setTitle(btnAdd.getText());
			dialog.btnComercial.setOnClickListener(this);
			dialog.btnGenerico.setOnClickListener(this);
			dialog.show();
		}else if (dialog != null){
			if(v == dialog.btnComercial){
				dialog.dismiss();
				DialogCantidad dialogQuantity = new DialogCantidad(this);
				dialogQuantity.setTitle("Comercial");
				dialogQuantity.show();
			}else if(v == dialog.btnGenerico){
				dialog.dismiss();
				Intent i = new Intent(this, AddToRecetaActivity.class);
				this.startActivityForResult(i, ADD_PRESCRIPTION);
			}
		}
		
	}
}