package gob.sis.simos;


import gob.sis.simos.entity.ICuantificable;
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
import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
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

public class EntregaRecetasActivity extends RoboFragmentActivity
 implements ActionBar.TabListener, OnClickListener {
	
	SectionsPagerAdapter mSectionsPagerAdapter;
	ViewPager mViewPager;
	
	@InjectView(R.id.btn_add)
	protected Button btnAdd;
	
	DialogTipoReceta dialogTipoReceta;
	DialogCantidad dialogQuantity;
	
	MedicamentoCheckListFragment medicineFragment;
	InsumoCheckListFragment inputFragment;
	AlertDialog alert;
	
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
		
		dialogTipoReceta = new DialogTipoReceta(this);
		dialogTipoReceta.btnComercial.setOnClickListener(this);
		dialogTipoReceta.btnGenerico.setOnClickListener(this);
		
		dialogQuantity = new DialogCantidad(this);
		dialogQuantity.btnOK.setOnClickListener(this);
		dialogQuantity.btnCANCEL.setOnClickListener(this);
		
		
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
		if(item.getItemId() == R.id.item_select_all){
			checkAll();
		} else if(item.getItemId() == R.id.item_clear) {
			clear();
		} else if(item.getItemId() == R.id.item_delete) {
			delete();
		}
		return true;
	}

	private void delete(){
			AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
			
			alertDialogBuilder.setTitle("ELIMINAR");
			alertDialogBuilder
				.setMessage("¿Desea eliminar los elementos seleccionados?")
				.setCancelable(false)
				.setPositiveButton("Sí",new DialogInterface.OnClickListener(){
					public void onClick(DialogInterface dialog,int id) {
						if(mViewPager.getCurrentItem() == 0){
							medicineFragment.deleteCheckedItems();
						} else if(mViewPager.getCurrentItem() == 1){
							inputFragment.deleteCheckedItems();
						}
					}
				  })
				.setNegativeButton("No",new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog,int id) {
						dialog.cancel();
					}
				});
				AlertDialog alertDialog = alertDialogBuilder.create();
				alertDialog.show();
	}

	private void clear(){
		if(mViewPager.getCurrentItem() == 0){
			medicineFragment.clear();
		} else if(mViewPager.getCurrentItem() == 1){
			inputFragment.clear();
		}
	}

	private void checkAll() {
		if(mViewPager.getCurrentItem() == 0){
			medicineFragment.checkAllItems();
		} else if(mViewPager.getCurrentItem() == 1){
			inputFragment.checkAllItems();
		}
	}

	private void add(ICuantificable c){
		if(c != null){
			if(c instanceof Medicamento){
				medicineFragment.adapter.add((Medicamento)c);
			}else if(c instanceof Insumo){
				inputFragment.adapter.add((Insumo)c);
			}
		}
	}
	
	private ICuantificable find(ICuantificable c){
		if(c != null){
			if(c instanceof Medicamento){
				return medicineFragment.findItem(c);
			}else if(c instanceof Insumo){
				return inputFragment.findItem(c);
			}
		}
		return null;
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(requestCode == 1 && resultCode == RESULT_OK){
			ICuantificable c = (ICuantificable)data.getSerializableExtra("data");
			if(this.find(c) != null){
				String str = "El elemento '%s' ya existe en el listado. Si desea modificar las cantidades, mantenga seleccionado el elemento."; 
				if(c instanceof Medicamento){
					Medicamento m = (Medicamento)c;
					str = String.format(str, m.getNombre());
				} else if (c instanceof Insumo){
					Insumo in = (Insumo)c;
					str = String.format(str, in.getNombre());
				}
				showAlert(str);
			} else {
				this.add(c);
			}
		} else if (requestCode == 1 && resultCode == RESULT_CANCELED){
			Toast.makeText(this, "Operación cancelada", Toast.LENGTH_SHORT).show();
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

	@Override
	public void onClick(View v) {
		if(v == this.btnAdd){
			dialogTipoReceta.setTitle(btnAdd.getText());
			dialogTipoReceta.show();
		}
		if (dialogTipoReceta != null){
			if(v == dialogTipoReceta.btnComercial){
				ICuantificable c = mViewPager.getCurrentItem() == 0?new Medicamento() : new Insumo();
				c.setId(Medicamento.COMERCIAL);
				if(find(c) != null){
					dialogTipoReceta.dismiss();
					showAlert("El elemento 'COMERCIAL' ya existe en el listado. Si desea modificar las cantidades, mantenga seleccionado el elemento.");					
				} else {
					dialogTipoReceta.dismiss();
					dialogQuantity.setTitle("Comercial");
					dialogQuantity.show();
				}
				
			}else if(v == dialogTipoReceta.btnGenerico){
				dialogTipoReceta.dismiss();
				Intent i = new Intent(this, AddToRecetaActivity.class);
				i.putExtra("index", mViewPager.getCurrentItem());
				this.startActivityForResult(i,1);
			}
		}
		if (dialogQuantity != null){
			if(v == dialogQuantity.btnOK){
				dialogQuantity.dismiss();
				if(mViewPager.getCurrentItem() == 0){
					Medicamento c = new Medicamento();
					c.setId(Medicamento.COMERCIAL);
					c.setNombre(Medicamento.COMERCIAL);
					c.setEntregado(dialogQuantity.getCantidadEntregada());
					c.setRecetado(dialogQuantity.getCantidadRecetada());
					this.add(c);
				} else if(mViewPager.getCurrentItem() == 1){
					Insumo in = new Insumo();
					in.setId(Insumo.COMERCIAL);
					in.setNombre(Insumo.COMERCIAL);
					in.setEntregado(dialogQuantity.getCantidadEntregada());
					in.setRecetado(dialogQuantity.getCantidadRecetada());
					this.add(in);
				}
				
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
