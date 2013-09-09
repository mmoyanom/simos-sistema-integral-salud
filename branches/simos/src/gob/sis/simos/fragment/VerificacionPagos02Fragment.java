package gob.sis.simos.fragment;

import gob.sis.simos.R;
import gob.sis.simos.adapters.OpcionRespuestaSpinnerAdapter;
import gob.sis.simos.controller.VerificacionPagoController;
import gob.sis.simos.entity.OpcionRespuesta;
import gob.sis.simos.entity.Respuesta;
import gob.sis.simos.entity.VerificacionPago;
import gob.sis.simos.ui.UIRadioButton;
import gob.sis.simos.ui.UIRadioGroup;
import gob.sis.simos.ui.UISpinner;

import java.util.ArrayList;
import java.util.List;

import roboguice.fragment.RoboFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;

import com.google.inject.Inject;

public class VerificacionPagos02Fragment extends RoboFragment {

	private UISpinner splugarIndicacionPago;
	private UISpinner spPersonaIndicaPago;
	private UIRadioGroup rgDevolucion;
	private UIRadioGroup rgFormalizarReclamo;
	private UISpinner spContribuyenteDevolucion;
	@Inject
	private VerificacionPagoController controller;
	private VerificacionPago verificacion;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.frgmnt_vrfccn_pgs_02, null);
		
		this.splugarIndicacionPago = (UISpinner)v.findViewById(R.id.spinner_1);
		this.spPersonaIndicaPago = (UISpinner)v.findViewById(R.id.spinner_2);
		this.spContribuyenteDevolucion = (UISpinner)v.findViewById(R.id.spinner_3);
		this.rgDevolucion = (UIRadioGroup)v.findViewById(R.id.rg_devolucion);
		this.rgFormalizarReclamo = (UIRadioGroup)v.findViewById(R.id.rg_formalizar_reclamo);
		return v;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		loadPreguntas();
		loadVerificacion();
	}
	
	public String isClear(){
		String str = "No respondio la pregunta \"%s\".";
		if(this.rgDevolucion.getCheckedRadioButtonId() == -1)
			return String.format(str, "ÀSe realizo la devolucion?");
		
		if(this.rgFormalizarReclamo.getCheckedRadioButtonId() == -1)
			return String.format(str, "ÀDesea formalizar el reclamo?");
		
		return "";
	}

	private void loadVerificacion() {
		
		if(getVerificacion() != null){
			List<Respuesta> rsptas = getVerificacion().getRespuestas();
			if(rsptas.size() > 0){
				for(int i = 0; i < rsptas.size() ; i++){
					Respuesta or = rsptas.get(i);
					
					// pregunta 16
					if(or.getPreguntaId() == 16){
						Adapter adapter = splugarIndicacionPago.getAdapter();
						for(int x=0;x < adapter.getCount(); x++){
							OpcionRespuesta orx = (OpcionRespuesta)adapter.getItem(x);
							if(orx.getOpcionRespuestaId() != null){
								if(orx.getOpcionRespuestaId().equals(or.getOpcionRespuestaId())){
									splugarIndicacionPago.setSelection(x);
								}
							}
						}
					}
					
					// pregunta 17
					if(or.getPreguntaId() == 17){
						Adapter adapter = spPersonaIndicaPago.getAdapter();
						for(int x=0;x < adapter.getCount(); x++){
							OpcionRespuesta orx = (OpcionRespuesta)adapter.getItem(x);
							if(orx.getOpcionRespuestaId() != null){
								if(orx.getOpcionRespuestaId().equals(or.getOpcionRespuestaId())){
									spPersonaIndicaPago.setSelection(x);
								}
							}
						}
					}
					
					// pregunta 18
					if(or.getPreguntaId() == 18){
						for(int x = 0; x < this.rgDevolucion.getChildCount(); x++){
							if(this.rgDevolucion.getChildAt(x) instanceof UIRadioButton){
								UIRadioButton rb = (UIRadioButton)this.rgDevolucion.getChildAt(x);
								if(or.getOpcionRespuestaId() != null){
									if(or.getOpcionRespuestaId().equals(rb.getOpcionRespuestaId())){
										this.rgDevolucion.check(rb.getId());
									}
								}
							}
						}
					}
					
					// pregunta 19
					if(or.getPreguntaId() == 19){
						Adapter adapter = spContribuyenteDevolucion.getAdapter();
						for(int x=0;x < adapter.getCount(); x++){
							OpcionRespuesta orx = (OpcionRespuesta)adapter.getItem(x);
							if(or.getOpcionRespuestaId() != null){
								if(orx.getOpcionRespuestaId().equals(or.getOpcionRespuestaId())){
									spContribuyenteDevolucion.setSelection(x);
								}
							}
						}
					}
					
					// pregunta 20
					if(or.getPreguntaId() == 20){
						for(int x = 0; x < this.rgFormalizarReclamo.getChildCount(); x++){
							if(this.rgFormalizarReclamo.getChildAt(x) instanceof UIRadioButton){
								UIRadioButton rb = (UIRadioButton)this.rgFormalizarReclamo.getChildAt(x);
								if(or.getOpcionRespuestaId() != null){
									if(or.getOpcionRespuestaId().equals(rb.getOpcionRespuestaId())){
										this.rgFormalizarReclamo.check(rb.getId());
									}
								}
							}
						}
					}
					
				}
			}
		}
	}
	
	public List<Respuesta> getRespuestas(){
		
		List<Respuesta> rspts = new ArrayList<Respuesta>();
		
		// pregunta 16
		splugarIndicacionPago.getRespuesta().setRespuestaParentId(null);
		rspts.add(splugarIndicacionPago.getRespuesta());
		
		// pregunta 17
		spPersonaIndicaPago.getRespuesta().setRespuestaParentId(null);
		rspts.add(spPersonaIndicaPago.getRespuesta());
		
		// pregunta 18
		rgDevolucion.getRespuesta().setRespuestaParentId(null);
		rspts.add(rgDevolucion.getRespuesta());
		
		// pregunta 19
		spContribuyenteDevolucion.getRespuesta().setRespuestaParentId(null);
		rspts.add(spContribuyenteDevolucion.getRespuesta());
		
		// pregunta 20
		rgFormalizarReclamo.getRespuesta().setRespuestaParentId(null);
		rspts.add(rgFormalizarReclamo.getRespuesta());
		
		return rspts;
	}

	private void loadPreguntas() {
		
		List<OpcionRespuesta> items = controller.getRespuestas(16);
		OpcionRespuestaSpinnerAdapter adapter = new OpcionRespuestaSpinnerAdapter(getActivity(), items);
		this.splugarIndicacionPago.setAdapter(adapter);
		
		items = controller.getRespuestas(17);
		adapter = new OpcionRespuestaSpinnerAdapter(getActivity(), items);
		this.spPersonaIndicaPago.setAdapter(adapter);
		
		items = controller.getRespuestas(19);
		adapter = new OpcionRespuestaSpinnerAdapter(getActivity(), items);
		this.spContribuyenteDevolucion.setAdapter(adapter);
		
	}

	public void setVisibility(int visibility){
		getView().setVisibility(visibility);
	}

	public VerificacionPago getVerificacion() {
		return verificacion;
	}

	public void setVerificacion(VerificacionPago verificacion) {
		this.verificacion = verificacion;
	}
}
