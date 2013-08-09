package gob.sis.simos;

import gob.sis.simos.entity.Medicamento;
import gob.sis.simos.fragment.InsumoCheckListFragment;
import gob.sis.simos.fragment.MedicamentoCheckListFragment;
import gob.sis.simos.ui.DialogAddToReceta;
import gob.sis.simos.ui.DialogAddServicio;
import roboguice.activity.RoboFragmentActivity;
import roboguice.inject.ContentView;
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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

@ContentView(R.layout.actvt_encsta_01)
public class Encuesta01PrincipalActivity extends RoboFragmentActivity implements OnClickListener, ActionBar.TabListener {

	SectionsPagerAdapter mSectionsPagerAdapter;
	
	@InjectView(R.id.pager)
	protected ViewPager mViewPager;
	
	@InjectView(R.id.btn_add)
	protected Button btnAdd;
	
	DialogAddServicio addServiceDialog;
	DialogAddToReceta addPrescriptionDialog;
	
	MedicamentoCheckListFragment medicineFragment;
	InsumoCheckListFragment inputFragment;
	
	public static final int ADD_SERVICE = 0;
	public static final int ADD_PRESCRIPTION = 1;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//this.setContentView(R.layout.activity_inquest_00);
		
		final ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		actionBar.setDisplayUseLogoEnabled(false);
		actionBar.setIcon(R.drawable.ic_menu_mark);
		
		this.btnAdd.setOnClickListener(this);
		this.addServiceDialog = new DialogAddServicio(this);
		this.addPrescriptionDialog = new DialogAddToReceta(this);
		
		mSectionsPagerAdapter = new SectionsPagerAdapter(
				getSupportFragmentManager());
		
		mViewPager.setAdapter(mSectionsPagerAdapter);
		
		mViewPager
		.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
			@Override
			public void onPageSelected(int position) {
				actionBar.setSelectedNavigationItem(position);
			}
		});
		for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
			actionBar.addTab(actionBar.newTab()
					.setText(mSectionsPagerAdapter.getPageTitle(i))
					.setTabListener(this));
		}
	}
	
	@Override
	public void onTabReselected(Tab tab, FragmentTransaction arg1) {

	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction arg1) {
		
		if(tab.getPosition() == 0){
			this.btnAdd.setText("Agregar servicio");
		} else if(tab.getPosition() == 1){
			this.btnAdd.setText("Agregar receta");
		}
		mViewPager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabUnselected(Tab arg0, FragmentTransaction arg1) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.menu_inquest_main, menu);
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		if(item.getItemId() == R.id.action_next){
			Intent i = new Intent(this, VerificacionPagos02Activity.class);
			this.startActivity(i);
		}
		return true;
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(requestCode == ADD_SERVICE){
			Medicamento m = new Medicamento();
			m.setNombre("Medicamento agregado");
			medicineFragment.adapter.add(m);
			Toast.makeText(this, "Servicio guardado, "+resultCode, Toast.LENGTH_SHORT).show();
		} else if(requestCode == ADD_PRESCRIPTION){
			Toast.makeText(this, "Receta guardada, "+resultCode, Toast.LENGTH_SHORT).show();
		}
		
	}

	
	public class SectionsPagerAdapter extends FragmentPagerAdapter {

		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			
			if(position == 0){
				medicineFragment = new MedicamentoCheckListFragment();
				return medicineFragment;
			} else if(position == 1){
				inputFragment = new InsumoCheckListFragment();
				return inputFragment;
			}
			return null;
		}

		@Override
		public int getCount() {
			return 2;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			switch (position) {
			case 0:
				return "VERIFICACION PAGO"; //getString(R.string.title_section1).toUpperCase(l);
			case 1:
				return "ENTREGA RECETAS"; //getString(R.string.title_section2).toUpperCase(l);
			}
			return null;
		}
	}


	@Override
	public void onClick(View v) {
		
		if(v == this.btnAdd){
			if(this.mViewPager.getCurrentItem() == 0){
				addServiceDialog.setTitle(this.btnAdd.getText());
				addServiceDialog.btnContinuar.setOnClickListener(this);
				addServiceDialog.btnCancelar.setOnClickListener(this);
				addServiceDialog.show();
				
			} else if(this.mViewPager.getCurrentItem() == 1){
				addPrescriptionDialog.setTitle(this.btnAdd.getText());
				addPrescriptionDialog.btnContinuar.setOnClickListener(this);
				addPrescriptionDialog.btnCancelar.setOnClickListener(this);
				addPrescriptionDialog.show();
				
			}
		} else if(v == this.addServiceDialog.btnContinuar){
				this.addServiceDialog.dismiss();
				Intent i = new Intent(this, VerificacionPagos01Activity.class);
				this.startActivityForResult(i, ADD_SERVICE);
		} else if(v == this.addServiceDialog.btnCancelar){
				this.addServiceDialog.dismiss();
		} else if(v == this.addPrescriptionDialog.btnContinuar){
				this.addPrescriptionDialog.dismiss();
				Intent i = new Intent(this, EntregaRecetasActivity.class);
				this.startActivityForResult(i, ADD_PRESCRIPTION);
		} else if(v == this.addPrescriptionDialog.btnCancelar){
				this.addPrescriptionDialog.dismiss();
		}
		
	}
}
