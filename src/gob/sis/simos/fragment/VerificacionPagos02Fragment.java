package gob.sis.simos.fragment;

import gob.sis.simos.R;
import gob.sis.simos.SimpleCheckListActivity;
import gob.sis.simos.controller.VerificacionPagoController;
import gob.sis.simos.entity.Respuesta;
import gob.sis.simos.entity.VerificacionPago;
import gob.sis.simos.ui.UIRadioButton;
import gob.sis.simos.ui.UIRadioGroup;

import java.util.ArrayList;
import java.util.List;

import roboguice.fragment.RoboFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.inject.Inject;

public class VerificacionPagos02Fragment extends RoboFragment implements OnClickListener {

	//private UISpinner splugarIndicacionPago;
	//private UISpinner spPersonaIndicaPago;
	//private UISpinner spContribuyenteDevolucion;
	private LinearLayout lyPregunta16;
	private LinearLayout lyPregunta17;
	private LinearLayout lyPregunta19;
	public TextView txtPregunta16;
	public TextView txtPregunta17;
	public TextView txtPregunta19;
	public List<Respuesta> rsptsPregunta16;
	public List<Respuesta> rsptsPregunta17;
	public List<Respuesta> rsptsPregunta19;
	private UIRadioGroup rgDevolucion;
	private UIRadioGroup rgFormalizarReclamo;
	@Inject
	private VerificacionPagoController controller;
	private VerificacionPago verificacion;
	private static final int SELECT_ITEMS = 5;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.frgmnt_vrfccn_pgs_02, null);
		
		this.lyPregunta16 = (LinearLayout)v.findViewById(R.id.ly_question_16);
		this.lyPregunta16.setOnClickListener(this);
		this.txtPregunta16 = (TextView)v.findViewById(R.id.txt_question_16);
		this.rsptsPregunta16 = new ArrayList<Respuesta>();
		
		this.lyPregunta17 = (LinearLayout)v.findViewById(R.id.ly_question_17);
		this.lyPregunta17.setOnClickListener(this);
		this.txtPregunta17 = (TextView)v.findViewById(R.id.txt_question_17);
		this.rsptsPregunta17 = new ArrayList<Respuesta>();
		
		this.lyPregunta19 = (LinearLayout)v.findViewById(R.id.ly_question_19);
		this.lyPregunta19.setOnClickListener(this);
		this.txtPregunta19 = (TextView)v.findViewById(R.id.txt_question_19);
		this.rsptsPregunta19 = new ArrayList<Respuesta>();
		
		this.rgDevolucion = (UIRadioGroup)v.findViewById(R.id.rg_devolucion);
		this.rgFormalizarReclamo = (UIRadioGroup)v.findViewById(R.id.rg_formalizar_reclamo);
		return v;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		//loadPreguntas();
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
						if(this.rsptsPregunta16 != null){
							this.rsptsPregunta16.add(or);
						}
					}
					
					// pregunta 17
					if(or.getPreguntaId() == 17){
						if(this.rsptsPregunta17 != null){
							this.rsptsPregunta17.add(or);
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
						if(this.rsptsPregunta19 != null){
							this.rsptsPregunta19.add(or);
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
		if(this.rsptsPregunta16 != null){
			if(this.rsptsPregunta16.size() > 0){
				for(int x = 0; x < this.rsptsPregunta16.size() ; x++){
					this.rsptsPregunta16.get(x).setPreguntaParentId(7);
				}
				rspts.addAll(this.rsptsPregunta16);
			} else {
					Respuesta r = new Respuesta();
					r.setPreguntaId(16);
					r.setPreguntaParentId(7);
					r.setOpcionRespuestaId(null);
					rspts.add(r);
			}
		}		
		
		// pregunta 17
		if(this.rsptsPregunta17 != null){
			if(this.rsptsPregunta17.size() > 0){
				for(int x = 0; x < this.rsptsPregunta17.size() ; x++){
					this.rsptsPregunta17.get(x).setPreguntaParentId(7);
				}
				rspts.addAll(this.rsptsPregunta17);
			} else {
					Respuesta r = new Respuesta();
					r.setPreguntaId(17);
					r.setPreguntaParentId(7);
					r.setOpcionRespuestaId(null);
					rspts.add(r);
			}
		}	
		
		// pregunta 18
		rgDevolucion.getRespuesta().setRespuestaParentId(null);
		rspts.add(rgDevolucion.getRespuesta());
		
		// pregunta 19
		if(this.rsptsPregunta19 != null){
			if(this.rsptsPregunta19.size() > 0){
				for(int x = 0; x < this.rsptsPregunta19.size() ; x++){
					this.rsptsPregunta19.get(x).setPreguntaParentId(7);
				}
				rspts.addAll(this.rsptsPregunta19);
			} else {
					Respuesta r = new Respuesta();
					r.setPreguntaId(19);
					r.setPreguntaParentId(7);
					r.setOpcionRespuestaId(null);
					rspts.add(r);
			}
		}	
		
		// pregunta 20
		rgFormalizarReclamo.getRespuesta().setRespuestaParentId(null);
		rspts.add(rgFormalizarReclamo.getRespuesta());
		
		return rspts;
	}

	/*private void loadPreguntas() {
		
		List<OpcionRespuesta> items = controller.getRespuestas(16);
		OpcionRespuestaSpinnerAdapter adapter = new OpcionRespuestaSpinnerAdapter(getActivity(), items);
		this.splugarIndicacionPago.setAdapter(adapter);
		
		items = controller.getRespuestas(17);
		adapter = new OpcionRespuestaSpinnerAdapter(getActivity(), items);
		this.spPersonaIndicaPago.setAdapter(adapter);
		
		items = controller.getRespuestas(19);
		adapter = new OpcionRespuestaSpinnerAdapter(getActivity(), items);
		this.spContribuyenteDevolucion.setAdapter(adapter);
		
	}*/

	public void setVisibility(int visibility){
		getView().setVisibility(visibility);
	}

	public VerificacionPago getVerificacion() {
		return verificacion;
	}

	public void setVerificacion(VerificacionPago verificacion) {
		this.verificacion = verificacion;
	}

	@Override
	public void onClick(View view) {
		if(view == this.lyPregunta16){
			Intent in = new Intent(getActivity(), SimpleCheckListActivity.class);
			in.putExtra("preguntaId", 16);
			if(this.rsptsPregunta16 != null){
				if(this.rsptsPregunta16.size() > 0){
					int[] int_array = new int[this.rsptsPregunta16.size()];
					for(int x = 0; x < this.rsptsPregunta16.size(); x++){
						Respuesta r = this.rsptsPregunta16.get(x);
						int_array[x] = r.getOpcionRespuestaId();
					}
					in.putExtra("bundle_pregunta_16", int_array);
				}
			}
			in.putExtra("pregunta_text", getResources().getString(R.string.question_16));
			getActivity().startActivityForResult(in, SELECT_ITEMS);
		}
		if(view == this.lyPregunta17){
			Intent in = new Intent(getActivity(), SimpleCheckListActivity.class);
			in.putExtra("preguntaId", 17);
			if(this.rsptsPregunta17 != null){
				if(this.rsptsPregunta17.size() > 0){
					int[] int_array = new int[this.rsptsPregunta17.size()];
					for(int x = 0; x < this.rsptsPregunta17.size(); x++){
						Respuesta r = this.rsptsPregunta17.get(x);
						int_array[x] = r.getOpcionRespuestaId();
					}
					in.putExtra("bundle_pregunta_17", int_array);
				}
			}
			in.putExtra("pregunta_text", getResources().getString(R.string.question_17));
			getActivity().startActivityForResult(in, SELECT_ITEMS);
		}
		if(view == this.lyPregunta19){
			Intent in = new Intent(getActivity(), SimpleCheckListActivity.class);
			in.putExtra("preguntaId", 19);
			if(this.rsptsPregunta19 != null){
				if(this.rsptsPregunta19.size() > 0){
					int[] int_array = new int[this.rsptsPregunta19.size()];
					for(int x = 0; x < this.rsptsPregunta19.size(); x++){
						Respuesta r = this.rsptsPregunta19.get(x);
						int_array[x] = r.getOpcionRespuestaId();
					}
					in.putExtra("bundle_pregunta_19", int_array);
				}
			}
			in.putExtra("pregunta_text", getResources().getString(R.string.question_19));
			getActivity().startActivityForResult(in, SELECT_ITEMS);
		}
	}
}
