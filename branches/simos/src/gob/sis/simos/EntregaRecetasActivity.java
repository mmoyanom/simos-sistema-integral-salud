package gob.sis.simos;


import gob.sis.simos.dto.Receta;
import gob.sis.simos.entity.ICuantificable;
import gob.sis.simos.entity.Insumo;
import gob.sis.simos.entity.Medicamento;
import gob.sis.simos.fragment.InsumoCheckListFragment;
import gob.sis.simos.fragment.MedicamentoCheckListFragment;
import gob.sis.simos.ui.DialogCantidad;
import gob.sis.simos.ui.DialogTipoReceta;

import java.util.Iterator;
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
	
	private Receta receta;
	
	DialogTipoReceta dialogTipoReceta;
	DialogCantidad dialogQuantity;
	
	MedicamentoCheckListFragment medicineFragment;
	InsumoCheckListFragment inputFragment;
	AlertDialog alert;
	
	public static final int ADD_PRESCRIPTION = 1;
	public static final int EDIT_PRESCRIPTION = 2;
	
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
		this.receta = (Receta) getIntent().getSerializableExtra("receta");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
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
		} else if(item.getItemId() == R.id.action_save) {
			saveReceta();
		}
		return true;
	}
	
	private void saveReceta(){
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
		String message = "";
		Bundle bundle = getIntent().getExtras();
		if(bundle != null){
			Integer action = bundle.getInt("action");
			if(action == ADD_PRESCRIPTION){
				message = "Desea guardar la receta?";
			} else if(action == EDIT_PRESCRIPTION){
				message = "Desea actualizar la receta?";
			}
		}
		
		alertDialogBuilder.setTitle("Guardar");
		alertDialogBuilder
			.setMessage(message)
			.setCancelable(false)
			.setPositiveButton("Si",new DialogInterface.OnClickListener(){
				public void onClick(DialogInterface dialog,int id) {
					receta.setMedicamentos(medicineFragment.adapter.getItems());
					receta.setInsumos(inputFragment.adapter.getItems());
					Intent i = new Intent(EntregaRecetasActivity.this, Encuesta01PrincipalActivity.class);
					i.putExtra("receta",receta);
					setResult(RESULT_OK, i);
					finish();
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

	private void delete(){
		
		if(mViewPager.getCurrentItem() == 0){
			if(this.receta.getMedicamentos().size() == 0){
				showMessage("No hay elementos para eliminar");
				return;
			} else {
				Iterator<Insumo> it = this.receta.getInsumos().iterator();
				while(it.hasNext()){
					if(it.next().isChecked()){
						break;
					} else {
						showMessage("No hay elementos seleccionados para eliminar.");
						return;
					}
				}
			}
		} else if(mViewPager.getCurrentItem() == 1){
			if(this.receta.getInsumos().size() == 0){
				showMessage("No hay elementos para eliminar");
				return;
			} else {
				Iterator<Insumo> it = this.receta.getInsumos().iterator();
				while(it.hasNext()){
					if(it.next().isChecked()){
						break;
					} else {
						showMessage("No hay elementos seleccionados para eliminar.");
						return;
					}
				}
			}
		}
		
			AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
			
			alertDialogBuilder.setTitle("ELIMINAR");
			alertDialogBuilder
				.setMessage("Desea eliminar los elementos seleccionados?")
				.setCancelable(false)
				.setPositiveButton("Sí",new DialogInterface.OnClickListener(){
					public void onClick(DialogInterface dialog,int id) {
						if(mViewPager.getCurrentItem() == 0){
							Iterator<Medicamento> it = receta.getMedicamentos().iterator();
							while(it.hasNext()){
								Medicamento m = it.next();
								if(m.isChecked()) it.remove();
							}
							medicineFragment.notifyChanges(receta.getMedicamentos());
						} else if(mViewPager.getCurrentItem() == 1){
							Iterator<Insumo> it = receta.getInsumos().iterator();
							while(it.hasNext()){
								Insumo in = it.next();
								if(in.isChecked()) it.remove();
							}
							inputFragment.notifyChanges(receta.getInsumos());
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
			Iterator<Medicamento> it = receta.getMedicamentos().iterator();
			while(it.hasNext()){
				Medicamento m = it.next();
				m.setChecked(false);
			}
			medicineFragment.notifyChanges(receta.getMedicamentos());
		} else if(mViewPager.getCurrentItem() == 1){
			Iterator<Insumo> it = receta.getInsumos().iterator();
			while(it.hasNext()){
				Insumo in = it.next();
				in.setChecked(false);
			}
			inputFragment.notifyChanges(receta.getInsumos());
		}
	}

	private void checkAll() {
		if(mViewPager.getCurrentItem() == 0){
			Iterator<Medicamento> it = this.receta.getMedicamentos().iterator();
			while(it.hasNext()){
				Medicamento medicamento = it.next();
				medicamento.setChecked(true);
			}
			medicineFragment.notifyChanges(this.receta.getMedicamentos());
		} else if(mViewPager.getCurrentItem() == 1){
			Iterator<Insumo> it = this.receta.getInsumos().iterator();
			while(it.hasNext()){
				Insumo insumo = it.next();
				insumo.setChecked(true);
			}
			inputFragment.notifyChanges(this.receta.getInsumos());
		}
	}

	private void add(ICuantificable c){
		if(c != null){
			if(c instanceof Medicamento){
				Medicamento m = (Medicamento)c;
				receta.getMedicamentos().add(m);
				medicineFragment.notifyChanges(receta.getMedicamentos());
			}else if(c instanceof Insumo){
				Insumo in = (Insumo)c;
				receta.getInsumos().add(in);
				inputFragment.notifyChanges(receta.getInsumos());
			}
		}
	}
	
	private ICuantificable find(ICuantificable c){
		if(c != null){
			if(c instanceof Medicamento){
				Iterator<Medicamento> it = receta.getMedicamentos().iterator();
				while(it.hasNext()){
					Medicamento m = it.next();
					if(m.getId().equals(c.getId())){
						return m;
					}
				}
			}else if(c instanceof Insumo){
				Iterator<Insumo> it = receta.getInsumos().iterator();
				while(it.hasNext()){
					Insumo in = it.next();
					if(in.getId().equals(c.getId())){
						return in;
					}
				}
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
			Toast.makeText(this, "Operacion cancelada", Toast.LENGTH_SHORT).show();
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
				medicineFragment = new MedicamentoCheckListFragment(){
					@Override
					public void onViewCreated(View view,
							Bundle savedInstanceState) {
						if(receta != null){
							adapter.addAll(receta.getMedicamentos());
							adapter.notifyDataSetChanged();
						}
					}
				};
				return medicineFragment;
			} else if(position == 1){
				inputFragment = new InsumoCheckListFragment(){
					@Override
					public void onViewCreated(View view,
							Bundle savedInstanceState) {
						if(receta != null){
							adapter.addAll(receta.getInsumos());
							adapter.notifyDataSetChanged();
						}
					}
				};
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
	
	private void showMessage(String text){
		Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
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
