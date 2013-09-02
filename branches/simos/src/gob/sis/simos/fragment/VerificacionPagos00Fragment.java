package gob.sis.simos.fragment;

import gob.sis.simos.R;
import gob.sis.simos.controller.VerificacionPagoController;
import gob.sis.simos.entity.OpcionRespuesta;
import gob.sis.simos.entity.Respuesta;
import gob.sis.simos.entity.VerificacionPago;
import gob.sis.simos.ui.UIRadioButton;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import roboguice.fragment.RoboFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.google.inject.Inject;

public class VerificacionPagos00Fragment extends RoboFragment implements OnCheckedChangeListener {
	
	private RadioGroup rgServices;
	private RadioGroup rgRealizoPago;
	private String[] added_services;
	private VerificacionPago verificacion;
	@Inject private VerificacionPagoController controller;
	private MenuItem item;
	public static final int ADD_SERVICE = 0;
	public static final int EDIT_SERVICE = 3;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		setHasOptionsMenu(true);
		
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View view = inflater.inflate(R.layout.frgmnt_vrfccn_pgs_00, null);
		
		this.rgServices = (RadioGroup)view.findViewById(R.id.rg_services);
		this.rgServices.setOnCheckedChangeListener(this);
		
		this.rgRealizoPago = (RadioGroup)view.findViewById(R.id.rg_payment_made);
		this.rgRealizoPago.setOnCheckedChangeListener(this);
		return view;
	}
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		loadPreguntas();
		loadVerificacion();
	}

	private void loadVerificacion() {
		
		if(this.verificacion != null){
			List<Respuesta> rspts = this.verificacion.getRespuestas();
			if(rspts.size() > 0){
				for(int i = 0; i < rspts.size() ; i++){
					Respuesta r = rspts.get(i);
					
					if(r.getPreguntaId() == 7){
						for(int x = 0; x < this.rgServices.getChildCount(); x++){
							if(this.rgServices.getChildAt(x) instanceof UIRadioButton){
								UIRadioButton rb = (UIRadioButton)this.rgServices.getChildAt(x);
								if(r.getOpcionRespuestaId() != null){
									if(r.getOpcionRespuestaId().equals(rb.getOpcionRespuestaId())){
										this.rgServices.check(rb.getId());
									}
								}
							}
						}
					}
					if(r.getPreguntaId() == 8){
						for(int x = 0; x < this.rgRealizoPago.getChildCount(); x++){
							if(this.rgRealizoPago.getChildAt(x) instanceof UIRadioButton){
								UIRadioButton rb = (UIRadioButton)this.rgRealizoPago.getChildAt(x);
								if(r.getOpcionRespuestaId() != null){
									if(r.getOpcionRespuestaId().equals(rb.getOpcionRespuestaId())){
										this.rgRealizoPago.check(rb.getId());
									}
								}
							}
						}
					}
				}
			}
		}
	}

	private void loadPreguntas() {
		
		List<OpcionRespuesta> items = controller.getRespuestas(7);
		if(items != null){
			Bundle b = getArguments();
			this.added_services = b.getStringArray("added_services");
			if(this.added_services != null){
				Iterator<OpcionRespuesta> it = items.iterator();
				while(it.hasNext()){
					OpcionRespuesta or = it.next();
					for(int z = 0 ; z < this.added_services.length; z++){
						if(this.added_services[z].equals(or.getDescripcion())){
							it.remove();
						}
					}
				}
			}
			boolean enabled = true;
			if(b.containsKey("action")){
				int action = b.getInt("action");
				if(action == ADD_SERVICE){
					enabled = true;
				} else if(action == EDIT_SERVICE){
					enabled = false;
				}
			}
			for(int i = 0; i < items.size(); i++){
				OpcionRespuesta or = items.get(i);
				UIRadioButton rb = new UIRadioButton(getActivity());
				rb.setPreguntaId(or.getPreguntaId());
				rb.setOpcionRespuestaId(or.getOpcionRespuestaId());
				rb.setText(or.getDescripcion());
				rb.setEnabled(enabled);
				this.rgServices.addView(rb);
			}
		}
	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		// TODO Auto-generated method stub
		if(group == this.rgRealizoPago){
			if(this.rgRealizoPago.getCheckedRadioButtonId() != -1){
				if(this.rgRealizoPago.getCheckedRadioButtonId() == R.id.rb_yes){
					this.item.setTitle("SIGUIENTE");
				} else if (this.rgRealizoPago.getCheckedRadioButtonId() == R.id.rb_no){
					this.item.setTitle("GUARDAR");
				}
			}
		}
	}
	
	public boolean goAhead(){
		boolean goAhead = false;
		if(this.rgRealizoPago.getCheckedRadioButtonId() == R.id.rb_yes){
			goAhead = true;
		} else if (this.rgRealizoPago.getCheckedRadioButtonId() == R.id.rb_no){
			goAhead = false;
		}
		return goAhead;
	}
	
	public Respuesta getRespuestaService(){
		int id = this.rgServices.getCheckedRadioButtonId();
		if(id != -1){
			UIRadioButton rb = (UIRadioButton)this.rgServices.findViewById(id);
			Respuesta rsp7 = new Respuesta();
			rsp7.setPreguntaId(rb.getPreguntaId());
			rsp7.setOpcionRespuestaId(rb.getOpcionRespuestaId());
			return rsp7;
		}
		return null;
	}
	
	public Respuesta getRespuestaRealizoPago(){
		int id = this.rgRealizoPago.getCheckedRadioButtonId();
		if(id != -1){
			UIRadioButton rb = (UIRadioButton)this.rgRealizoPago.findViewById(id);
			Respuesta rsp7 = new Respuesta();
			rsp7.setPreguntaId(rb.getPreguntaId());
			rsp7.setOpcionRespuestaId(rb.getOpcionRespuestaId());
			return rsp7;
		}
		return null;
	}
	
	public String getSelectedServiceName(){
		int id = this.rgServices.getCheckedRadioButtonId();
		RadioButton rb = (RadioButton)this.rgServices.findViewById(id);
		if(rb != null)
			return rb.getText().toString();
		return null;
	}
	
	public List<Respuesta> getRespuestas(){
		List<Respuesta> rspts = new ArrayList<Respuesta>();
		rspts.add(getRespuestaService());
		rspts.add(getRespuestaRealizoPago());
		return rspts;
	}
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		this.item = menu.findItem(R.id.action_next);
		super.onCreateOptionsMenu(menu, inflater);
	}

	public VerificacionPago getVerificacion() {
		return verificacion;
	}

	public void setVerificacion(VerificacionPago verificacion) {
		this.verificacion = verificacion;
	}
	
}
