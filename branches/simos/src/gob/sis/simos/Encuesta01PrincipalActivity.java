package gob.sis.simos;

import gob.sis.simos.controller.EncuestaController;
import gob.sis.simos.controller.VerificacionPagoController;
import gob.sis.simos.dto.Receta;
import gob.sis.simos.entity.Encuesta01;
import gob.sis.simos.entity.ICuantificable;
import gob.sis.simos.entity.Insumo;
import gob.sis.simos.entity.Medicamento;
import gob.sis.simos.entity.OpcionRespuesta;
import gob.sis.simos.entity.Respuesta;
import gob.sis.simos.entity.VerificacionPago;
import gob.sis.simos.fragment.RecetaCheckListFragment;
import gob.sis.simos.fragment.VerificacionPagoCheckListFragment;
import gob.sis.simos.soap.SendEncuestaResult;
import gob.sis.simos.ui.DialogAddServicio;
import gob.sis.simos.ui.DialogPickTypeReceta;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import roboguice.activity.RoboFragmentActivity;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
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

import com.google.inject.Inject;

@ContentView(R.layout.actvt_encsta_01)
public class Encuesta01PrincipalActivity extends RoboFragmentActivity implements OnClickListener, ActionBar.TabListener {

	SectionsPagerAdapter mSectionsPagerAdapter;
	
	@Inject
	protected EncuestaController encuestaController;
	
	@Inject
	protected VerificacionPagoController verificacionController;
	
	@InjectView(R.id.pager)
	protected ViewPager mViewPager;
	
	@InjectView(R.id.btn_add)
	protected Button btnAdd;
	
	private Encuesta01 encuesta;
	
	DialogAddServicio addServiceDialog;
	DialogPickTypeReceta addPrescriptionDialog;
	
	RecetaCheckListFragment recetasFragment;
	VerificacionPagoCheckListFragment verificacionesFragment;
	
	AlertDialog alert;
	AlertDialog alertConfirmSave;
	
	public static final int ADD_SERVICE = 0;
	public static final int EDIT_SERVICE = 3;
	public static final int ADD_PRESCRIPTION = 1;
	public static final int EDIT_PRESCRIPTION = 2;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		dialog = new ProgressDialog(this);
		 
		final ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		actionBar.setDisplayUseLogoEnabled(false);
		actionBar.setIcon(R.drawable.ic_menu_mark);
		
		this.btnAdd.setOnClickListener(this);
		
		this.addServiceDialog = new DialogAddServicio(this);
		List<OpcionRespuesta> items = verificacionController.getRespuestas(7); 
		this.addServiceDialog.setItems(items);
		
		this.addPrescriptionDialog = new DialogPickTypeReceta(this);
		
		//this.encuesta = new Encuesta01();
		this.encuesta = (Encuesta01)getIntent().getSerializableExtra("encuesta");
		
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
			this.btnAdd.setText("Agregar Servicio");
		} else if(tab.getPosition() == 1){
			this.btnAdd.setText("Agregar Receta");
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
		
		if(item.getItemId() == R.id.item_select_all) {
			checkAll();
		} else if (item.getItemId() == R.id.item_clear) {
			clear();
		} else if (item.getItemId() == R.id.item_delete) {
			delete();
		} else if (item.getItemId() == R.id.action_save) {
			if(verificacionesFragment.lstVerificaciones.getCount() == 0 && recetasFragment.lstRecetas.getCount() == 0){
				showMessage("Para guardar una encuesta es necesario ingresar, al menos, una verificaci�n/receta.", Toast.LENGTH_LONG);
			} else {
				save();
			}
		}
		return true;
	}
	
	private void save() {
		// TODO Auto-generated method stub
		this.alertConfirmSave = new AlertDialog.Builder(this).create();
		this.alertConfirmSave.setTitle("Guardar Encuesta");
		this.alertConfirmSave.setMessage("A continuaci�n se guardar� esta encuesta.");
		this.alertConfirmSave.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancelar", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				// TODO Auto-generated method stub
				alertConfirmSave.cancel();
			}
		});
		this.alertConfirmSave.setButton(DialogInterface.BUTTON_POSITIVE,"Confirmar", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				// TODO Auto-generated method stub
				new SendEncuestaTask().execute(encuesta);
			}
		});
		this.alertConfirmSave.show();
	}

	@Override
	public void onBackPressed() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
	    builder.setMessage("�Esta seguro de salir?")
	           .setCancelable(false)
	           .setPositiveButton("Si", new DialogInterface.OnClickListener() {
	               public void onClick(DialogInterface dialog, int id) {
	                    Encuesta01PrincipalActivity.this.finish();
	               }
	           })
	           .setNegativeButton("No", new DialogInterface.OnClickListener() {
	               public void onClick(DialogInterface dialog, int id) {
	                    dialog.cancel();
	               }
	           });
	    AlertDialog alert = builder.create();
	    alert.show();
	}
	
	private void checkAll(){
		if(mViewPager.getCurrentItem() == 0){
			Iterator<VerificacionPago> it = this.encuesta.getVerificaciones().iterator();
			while(it.hasNext()){
				VerificacionPago vf = it.next();
				vf.setChecked(true);
			}
			verificacionesFragment.notifyChanges(this.encuesta.getVerificaciones());
			
		} else if (mViewPager.getCurrentItem() == 1){
			Iterator<Receta> it = this.encuesta.getRecetas().iterator();
			while(it.hasNext()){
				Receta rc = it.next();
				rc.setChecked(true);
			}
			recetasFragment.notifyChanges(this.encuesta.getRecetas());
		}
	}
	
	private void clear(){
		if(mViewPager.getCurrentItem() == 0){
			Iterator<VerificacionPago> it = this.encuesta.getVerificaciones().iterator();
			while(it.hasNext()){
				VerificacionPago vf = it.next();
				vf.setChecked(false);
			}
			verificacionesFragment.notifyChanges(this.encuesta.getVerificaciones());
			
		} else if (mViewPager.getCurrentItem() == 1){
			Iterator<Receta> it = this.encuesta.getRecetas().iterator();
			while(it.hasNext()){
				Receta rc = it.next();
				rc.setChecked(false);
			}
			recetasFragment.notifyChanges(this.encuesta.getRecetas());
		}
	}
	
	private void delete(){
		
		if(mViewPager.getCurrentItem() == 0){
			if(this.encuesta.getVerificaciones().size() == 0){
				showMessage("No hay elementos para eliminar.", Toast.LENGTH_SHORT);
				return;
			} else {
				Iterator<VerificacionPago> it = this.encuesta.getVerificaciones().iterator();
				int c = 0;
				while(it.hasNext()){
					if(it.next().isChecked()){
						c++;
					}
				}
				if(c == 0){
					showMessage("No hay elementos seleccionados para eliminar.", Toast.LENGTH_SHORT);
					return;
				}
			}
			
		} else if(mViewPager.getCurrentItem() == 1){
			if(this.encuesta.getRecetas().size() == 0){
				showMessage("No hay elementos para eliminar.", Toast.LENGTH_SHORT);
				return;
			} else {
				Iterator<Receta> it = this.encuesta.getRecetas().iterator();
				int c = 0;
				while(it.hasNext()){
					if(it.next().isChecked()){
						c++;
					}
				}
				if(c == 0){
					showMessage("No hay elementos seleccionados para eliminar.", Toast.LENGTH_SHORT);
					return;
				}
			}
		}
		
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
		
		alertDialogBuilder.setTitle("ELIMINAR");
		alertDialogBuilder
			.setMessage("Desea eliminar los elementos seleccionados?")
			.setCancelable(false)
			.setPositiveButton("S�",new DialogInterface.OnClickListener(){
				public void onClick(DialogInterface dialog,int id) {
					if(mViewPager.getCurrentItem() == 0){
						Iterator<VerificacionPago> it = encuesta.getVerificaciones().iterator();
						while(it.hasNext()){
							VerificacionPago vf = it.next();
							if(vf.isChecked()) it.remove();
						}
						verificacionesFragment.notifyChanges(encuesta.getVerificaciones());
						
					} else if(mViewPager.getCurrentItem() == 1){
						Iterator<Receta> it = encuesta.getRecetas().iterator();
						while(it.hasNext()){
							Receta rc = it.next();
							if(rc.isChecked()) it.remove();
						}
						recetasFragment.notifyChanges(encuesta.getRecetas());
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
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(requestCode == ADD_SERVICE){
			if(resultCode == RESULT_OK){
				ICuantificable c = (ICuantificable)data.getSerializableExtra("verificacion");
				if(c != null){
					this.add(c);
					Toast.makeText(this, "Verificacion guardada satisfactoriamente.", Toast.LENGTH_SHORT).show();
				}
			} else if(resultCode == RESULT_CANCELED){
				Toast.makeText(this, "Accion cancelada.", Toast.LENGTH_SHORT).show();
			}
			
		} else if(requestCode == EDIT_SERVICE){
			if(resultCode == RESULT_OK){
				ICuantificable c = (ICuantificable)data.getSerializableExtra("verificacion");
				if(c != null){
					String msg = "Verificacion actualizada satisfactoriamente.";
					this.update(c);
					Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
				}
			} else if(resultCode == RESULT_CANCELED){
				Toast.makeText(this, "Accion cancelada.", Toast.LENGTH_SHORT).show();
			}
			
		} else if(requestCode == ADD_PRESCRIPTION){
			if(resultCode == RESULT_OK){
				ICuantificable c = (ICuantificable)data.getSerializableExtra("receta");
				if(c != null){
					this.add(c);
					Toast.makeText(this, "Receta guardada satisfactoriamente.", Toast.LENGTH_SHORT).show();
				}
			} else if(resultCode == RESULT_CANCELED){
				Toast.makeText(this, "Accion cancelada.", Toast.LENGTH_SHORT).show();
			}
		} else if (requestCode == EDIT_PRESCRIPTION){
			if(resultCode == RESULT_OK){
				ICuantificable c = (ICuantificable)data.getSerializableExtra("receta");
				if(c != null){
					String msg = "Receta actualizada satisfactoriamente"; 
					this.update(c);
					Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
				}
			} else if (resultCode == RESULT_CANCELED){
				Toast.makeText(this, "Accion cancelada.", Toast.LENGTH_SHORT).show();
			}
		}
		
	}

	private void add(ICuantificable c){
		if(c instanceof Receta){
			Receta rc = (Receta)c;
			this.encuesta.getRecetas().add(rc);
			this.recetasFragment.notifyChanges(this.encuesta.getRecetas());
		} else if(c instanceof VerificacionPago){
			VerificacionPago vr = (VerificacionPago)c;
			this.encuesta.getVerificaciones().add(vr);
			this.verificacionesFragment.notifyChanges(this.encuesta.getVerificaciones());
		}
	}
	
	private void update(ICuantificable c){
		if(c instanceof Receta){
			Receta rc = (Receta)c;
			Integer index = Integer.parseInt(rc.getId().trim());
			this.encuesta.getRecetas().set((index-1), rc);
			this.recetasFragment.notifyChanges(encuesta.getRecetas());
		} else if (c instanceof VerificacionPago){
			VerificacionPago vr = (VerificacionPago)c;
			for(int x = 0; x < verificacionesFragment.adapter.getCount(); x++){
				VerificacionPago vx = verificacionesFragment.adapter.getItem(x);
				if(vx.getId().equals(vr.getId())){
					this.encuesta.getVerificaciones().set(x, vr);
				}
			}
			this.verificacionesFragment.notifyChanges(encuesta.getVerificaciones());
		}
	}
	
	public class SectionsPagerAdapter extends FragmentPagerAdapter {

		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			
			if(position == 0){
				verificacionesFragment = new VerificacionPagoCheckListFragment();
				return verificacionesFragment;
			} else if(position == 1){
				recetasFragment = new RecetaCheckListFragment();
				return recetasFragment;
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
				
				VerificacionPago vr = new VerificacionPago();
				vr.setId(UUID.randomUUID().toString());
				vr.setNombre(this.addServiceDialog.getSelectedServiceName());
				Intent i = new Intent(this, VerificacionPagosActivity.class);
				i.putExtra("verificacion", vr);
				String[] added_services = new String[verificacionesFragment.adapter.getCount()];
				for(int x = 0; x < verificacionesFragment.adapter.getCount(); x++){
					VerificacionPago vx = verificacionesFragment.adapter.getItem(x);
					added_services[x] = vx.getNombre();
				}
				i.putExtra("added_services", added_services);
				i.putExtra("action", ADD_SERVICE);
				this.startActivityForResult(i, ADD_SERVICE);
				
				/*addServiceDialog.setTitle(this.btnAdd.getText());
				addServiceDialog.btnContinuar.setOnClickListener(this);
				addServiceDialog.btnCancelar.setOnClickListener(this);
				this.addServiceDialog.clear();
				addServiceDialog.show();*/
				
			} else if(this.mViewPager.getCurrentItem() == 1){
				addPrescriptionDialog.setTitle(this.btnAdd.getText());
				addPrescriptionDialog.btnContinuar.setOnClickListener(this);
				addPrescriptionDialog.btnCancelar.setOnClickListener(this);
				addPrescriptionDialog.show();
				
			}
		} else if (v == this.addServiceDialog.btnContinuar){
				this.addServiceDialog.dismiss();
				VerificacionPago vr = new VerificacionPago();
				vr.setId(UUID.randomUUID().toString());
				vr.setNombre(this.addServiceDialog.getSelectedServiceName());
				
				if (this.verificacionesFragment.findItem(vr) == null) {
					if(this.addServiceDialog.realizoPago()){
						Intent i = new Intent(this, VerificacionPagosActivity.class);
						i.putExtra("verificacion", vr);
						String[] added_services = new String[verificacionesFragment.adapter.getCount()];
						for(int x = 0; x < verificacionesFragment.adapter.getCount(); x++){
							VerificacionPago vx = verificacionesFragment.adapter.getItem(x);
							added_services[x] = vx.getNombre();
						}
						i.putExtra("added_services", added_services);
						i.putExtra("action", ADD_SERVICE);
						this.startActivityForResult(i, ADD_SERVICE);
					} else {
						this.add(vr);
					}
				} else {
					String str = "El servicio '%s' ya existe en el listado. Si desea modificar las cantidades, mantenga seleccionado el elemento.";
					str = String.format(str, vr.getNombre());
					showMessage(str,Toast.LENGTH_LONG);
				}
		} else if (v == this.addServiceDialog.btnCancelar){
				this.addServiceDialog.dismiss();
				
		} else if (v == this.addPrescriptionDialog.btnContinuar){
				this.addPrescriptionDialog.dismiss();
				
				Receta rc = new Receta();
				rc.setId(UUID.randomUUID().toString());
				List<Insumo> insumos = new ArrayList<Insumo>();
				rc.setInsumos(insumos);
				List<Medicamento> medicamentos = new ArrayList<Medicamento>();
				rc.setMedicamentos(medicamentos);
				rc.setTipo(addPrescriptionDialog.getTipoReceta());
				rc.setTipoRecetaId(addPrescriptionDialog.getRespuestaTipoReceta().getOpcionRespuestaId());
				
				Intent i = new Intent(this, EntregaRecetasActivity.class);
				i.putExtra("receta", rc);
				i.putExtra("action", ADD_PRESCRIPTION);
				this.startActivityForResult(i, ADD_PRESCRIPTION);
		} else if (v == this.addPrescriptionDialog.btnCancelar){
				this.addPrescriptionDialog.dismiss();
		}
		
	}
	
	private void showMessage(String text, int length){
		Toast.makeText(this, text, length).show();
	}
	
	@SuppressWarnings("unused")
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
	
	private ProgressDialog dialog;
	
	class SendEncuestaTask extends AsyncTask<Encuesta01, Void, SendEncuestaResult> {

		@Override
		protected void onPreExecute() {
			dialog.setCancelable(false);
			dialog.setTitle(R.string.title_save_encta);
			dialog.setMessage(getResources().getString(R.string.please_wait));
			dialog.show();
		}
		
		@Override
		protected SendEncuestaResult doInBackground(Encuesta01... params) {
			Encuesta01 encta = new Encuesta01();
			encta.setCreated(Calendar.getInstance().getTime());
			//encta.setEncuestaGrupo(3);
			//encta.setFormularioId("F1");
			
			List<Respuesta> rspts = new ArrayList<Respuesta>();
			rspts.addAll(encuesta.getDatosEncuestado());
			rspts.addAll(orderVerificaciones());
			rspts.addAll(orderRecetas());
			
			encta.setRespuestas(rspts);
			encuesta = null;
			
			System.gc();
			
			SendEncuestaResult rslt = encuestaController.save(encta);
			
			return rslt;
		}
		
		@Override
		protected void onPostExecute(SendEncuestaResult result) {
			dialog.dismiss();
			Intent in = new Intent(Encuesta01PrincipalActivity.this, InformacionEncuestadoActivity.class);
			in.putExtra("sendEncuestaResult", result);
			setResult(RESULT_OK, in);
			Encuesta01PrincipalActivity.this.finish();
		}
		
	}
	
	public List<Respuesta> orderVerificaciones(){
		List<Respuesta> rspts = new ArrayList<Respuesta>();
		for(int i=0; i < encuesta.getVerificaciones().size();i++){
			VerificacionPago v = encuesta.getVerificaciones().get(i);
			rspts.addAll(v.getRespuestas());
		}
		return rspts;
	}
	
	public List<Respuesta> orderRecetas(){
		
		List<Respuesta> rspts = new ArrayList<Respuesta>();
		
		int c_index = 0;
		for(int i = 0; i < encuesta.getRecetas().size(); i++){
			Receta rc = encuesta.getRecetas().get(i);
			c_index++;
			Respuesta r_21 = new Respuesta();
			r_21.setPreguntaId(21);
			r_21.setOpcionRespuestaId(99);
			r_21.setRespuestaTexto(String.format("%s", c_index));
			List<Respuesta> r_21_child = new ArrayList<Respuesta>();
			
			Respuesta r_22 = new Respuesta();
			r_22.setPreguntaId(22);
			r_22.setPreguntaParentId(21);
			r_22.setOpcionRespuestaId(rc.getTipoRecetaId());
			r_21_child.add(r_22);
			
			List<Medicamento> meds = rc.getMedicamentos();
			if(meds.size() > 0){
				List<Respuesta> med_child = new ArrayList<Respuesta>();
				for(int x = 0 ; x < meds.size() ; x++){
					Medicamento m = meds.get(x);
					
					if(m.getNombre().equals(Medicamento.COMERCIAL)){

						Respuesta r_23 = new Respuesta();
						r_23.setPreguntaId(23);
						r_23.setPreguntaParentId(21);
						r_23.setOpcionRespuestaId(102);
						r_23.setRespuestaTexto(m.getNombre());
						r_23.setPrescripcionId("9999999");
						List<Respuesta> r_23_child = new ArrayList<Respuesta>();
						
						Respuesta r_24 = new Respuesta();
						r_24.setPreguntaId(24);
						r_24.setPreguntaParentId(23);
						r_24.setOpcionRespuestaId(109);
						r_23_child.add(r_24);
						
						Respuesta r_25 = new Respuesta();
						r_25.setPreguntaId(25);
						r_25.setPreguntaParentId(23);
						r_25.setOpcionRespuestaId(105);
						r_25.setRespuestaNumero(m.getRecetado()*1.0);
						r_23_child.add(r_25);
						
						r_23.setChild(r_23_child);
						
						med_child.add(r_23);
					} else {

						Respuesta r_23 = new Respuesta();
						r_23.setPreguntaId(23);
						r_23.setPreguntaParentId(21);
						r_23.setOpcionRespuestaId(102);
						r_23.setRespuestaTexto(m.getNombre());
						r_23.setPrescripcionId(m.getId());
						List<Respuesta> r_23_child = new ArrayList<Respuesta>();
						
						Respuesta r_24 = new Respuesta();
						r_24.setPreguntaId(24);
						r_24.setPreguntaParentId(23);
						r_24.setOpcionRespuestaId(103);
						r_23_child.add(r_24);
						
						Respuesta r_25 = new Respuesta();
						r_25.setPreguntaId(25);
						r_25.setPreguntaParentId(23);
						r_25.setOpcionRespuestaId(105);
						r_25.setRespuestaNumero(m.getRecetado()*1.0);
						r_23_child.add(r_25);
						
						Respuesta r_26 = new Respuesta();
						r_26.setPreguntaId(26);
						r_26.setPreguntaParentId(23);
						r_26.setOpcionRespuestaId(106);
						r_26.setRespuestaNumero(m.getEntregado()*1.0);
						r_23_child.add(r_26);
						
						r_23.setChild(r_23_child);
						
						med_child.add(r_23);
					}
				}
				r_21_child.addAll(med_child);
			}
			
			List<Insumo> ins = new ArrayList<Insumo>();
			if(ins.size() > 0){
				List<Respuesta> ins_child = new ArrayList<Respuesta>();
				for(int x =0 ; x < ins.size(); x++){
					Insumo in = ins.get(x);
					
					Respuesta r_23 = new Respuesta();
					r_23.setPreguntaId(23);
					r_23.setPreguntaParentId(21);
					r_23.setOpcionRespuestaId(102);
					r_23.setRespuestaTexto(in.getNombre());
					r_23.setPrescripcionId(in.getId());
					List<Respuesta> r_23_child = new ArrayList<Respuesta>();
					
					Respuesta r_24 = new Respuesta();
					r_24.setPreguntaId(24);
					r_24.setPreguntaParentId(23);
					r_24.setOpcionRespuestaId(104);
					r_23_child.add(r_24);
					
					Respuesta r_25 = new Respuesta();
					r_25.setPreguntaId(25);
					r_25.setPreguntaParentId(23);
					r_25.setOpcionRespuestaId(105);
					r_25.setRespuestaNumero(in.getRecetado()*1.0);
					r_23_child.add(r_25);
					
					Respuesta r_26 = new Respuesta();
					r_26.setPreguntaId(26);
					r_26.setPreguntaParentId(23);
					r_26.setOpcionRespuestaId(106);
					r_26.setRespuestaNumero(in.getEntregado()*1.0);
					r_23_child.add(r_26);
					
					r_23.setChild(r_23_child);
					
					ins_child.add(r_23);
				}
				r_21_child.addAll(ins_child);
			}
			// the end of the world!
			r_21.setChild(r_21_child);
			rspts.add(r_21);
		}
		return rspts;
	}
	
}
