package gob.sis.simos.fragment;

import gob.sis.simos.R;
import gob.sis.simos.adapters.OpcionRespuestaSpinnerAdapter;
import gob.sis.simos.controller.VerificacionPagoController;
import gob.sis.simos.entity.OpcionRespuesta;
import gob.sis.simos.entity.Respuesta;
import gob.sis.simos.entity.VerificacionPago;
import gob.sis.simos.ui.UIRadioButton;

import java.util.ArrayList;
import java.util.List;

import roboguice.fragment.RoboFragment;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Spinner;

import com.google.inject.Inject;


public class VerificacionPagos01Fragment extends RoboFragment implements OnCheckedChangeListener, InputFilter, OnLongClickListener,TextWatcher {

	protected RadioGroup rgPaymentLocation;
	protected RadioGroup rgHaveTicket;
	protected RadioGroup rgImproperPayment;
	protected EditText etAmount;
	protected Spinner spPaymentInEESS;
	protected Spinner spPaymentOutEESS;
	protected LinearLayout lyPaymentOut;
	protected LinearLayout lyPaymentIn;
	static final int IN_EESS = 40;
	static final int OUT_EESS = 41;
	static final int BOTH_EESS = 42;
	static final int HAVE_TICKETS_YES = 38;
	static final int HAVE_TICKETS_NO = 39;
	
	private List<View> views;
	
	protected int paymentoLocation;
	private VerificacionPago verificacion;
	@Inject
	protected VerificacionPagoController controller;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.frgmnt_vrfccn_pgs_01, null);
		
		this.views = new ArrayList<View>();
		
		this.rgPaymentLocation = (RadioGroup)v.findViewById(R.id.rg_payment_location);
		this.rgPaymentLocation.setOnCheckedChangeListener(this);
		this.views.add(this.rgImproperPayment);
		
		this.rgHaveTicket = (RadioGroup)v.findViewById(R.id.rg_have_ticket);
		this.rgHaveTicket.setOnCheckedChangeListener(this);
		this.views.add(this.rgHaveTicket);
		
		this.rgImproperPayment = (RadioGroup)v.findViewById(R.id.rg_improper_payment);
		this.rgImproperPayment.setOnCheckedChangeListener(this);
		this.views.add(this.rgImproperPayment);
		
		this.spPaymentInEESS = (Spinner)v.findViewById(R.id.sp_paymentIn);
		this.views.add(this.spPaymentInEESS);
		
		this.spPaymentOutEESS = (Spinner)v.findViewById(R.id.sp_paymentOut);
		this.views.add(this.spPaymentOutEESS);
		
		this.lyPaymentOut = (LinearLayout)v.findViewById(R.id.layout_payment_out);
		this.lyPaymentIn = (LinearLayout)v.findViewById(R.id.layout_payment_in);
		
		this.etAmount = (EditText)v.findViewById(R.id.et_ammount);
		this.etAmount.addTextChangedListener(this);
		this.views.add(this.etAmount);
		
		return v;
	}
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		loadPreguntas();
		loadVerificacion();
	}

	private void loadPreguntas() {
		List<OpcionRespuesta> items = controller.getRespuestas(11);
		OpcionRespuestaSpinnerAdapter adapter = new OpcionRespuestaSpinnerAdapter(getActivity(), items);
		this.spPaymentOutEESS.setAdapter(adapter);
		
		items = controller.getRespuestas(14);
		adapter = new OpcionRespuestaSpinnerAdapter(getActivity(), items);
		this.spPaymentInEESS.setAdapter(adapter);
		
	}

	public void setVisibility(int visibility){
		getView().setVisibility(visibility);
	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		if(group == this.rgPaymentLocation){
			int id = this.rgPaymentLocation.getCheckedRadioButtonId();
			UIRadioButton rb = (UIRadioButton)getView().findViewById(id);
			if(rb.getOpcionRespuestaId() == IN_EESS){
				this.lyPaymentIn.setVisibility(View.VISIBLE);
				this.lyPaymentOut.setVisibility(View.GONE);
				
			} else if(rb.getOpcionRespuestaId() == OUT_EESS){
				this.lyPaymentIn.setVisibility(View.GONE);
				this.lyPaymentOut.setVisibility(View.VISIBLE);
				
			} else if(rb.getOpcionRespuestaId() == BOTH_EESS){
				this.lyPaymentIn.setVisibility(View.VISIBLE);
				this.lyPaymentOut.setVisibility(View.VISIBLE);
			}
		}
	}
	
	public boolean isClear(){
		if(this.rgPaymentLocation.getCheckedRadioButtonId() == -1 
				|| this.rgHaveTicket.getCheckedRadioButtonId() == -1 
					|| this.rgImproperPayment.getCheckedRadioButtonId() == -1
						|| this.etAmount.getText().length() == 0){
			return true;
		}
		return false;
	}
	
	public boolean enterTickets(){
		int idhaveTickets = this.rgHaveTicket.getCheckedRadioButtonId();
		UIRadioButton rHaveTickets = (UIRadioButton)getView().findViewById(idhaveTickets);
		
		if(rHaveTickets.getOpcionRespuestaId() == HAVE_TICKETS_YES){
			int idPayment= this.rgPaymentLocation.getCheckedRadioButtonId();
			UIRadioButton r = (UIRadioButton) getView().findViewById(idPayment);
			if(r.getOpcionRespuestaId() == IN_EESS ||
					r.getOpcionRespuestaId() == BOTH_EESS) {				
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
		
	}

	@Override
	public CharSequence filter(CharSequence source, int start, int end,
			Spanned dest, int dstart, int dend) {
		for (int i = start; i < end; i++) {
			if (source.equals("\n")){
				return null;
			}
			if (source.charAt(i) == ','){
				return null;
			}
			if (source.charAt(i) == '-'){
				return null;
			}
            if (!Character.isDigit(source.charAt(i))) { 
            	return ""; 
            }
		}
		return null;
	}

	@Override
	public boolean onLongClick(View arg0) {
	
		return false;
	}

	@Override
	public void afterTextChanged(Editable text) {
		
	}

	@Override
	public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
			int arg3) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub
		
	}
	
	public void setVerificacion(VerificacionPago verificacion){
		this.verificacion = verificacion;
	}
	
	public VerificacionPago getVerificacion(){
		return this.verificacion;
	}
	
	public List<Respuesta> getRespuestas(){
		List<Respuesta> rspts = new ArrayList<Respuesta>();
		for(int i = 0; i < this.views.size() ; i++){
			View v = this.views.get(i);
			if(v instanceof RadioGroup){
				RadioGroup rg = (RadioGroup)v;
				int id = rg.getCheckedRadioButtonId();
				if(id != -1){
					UIRadioButton rb = (UIRadioButton)getView().findViewById(id);
					Respuesta rsp = rb.getRespuesta();
					rspts.add(rsp);					
				}
			}
			if(v instanceof Spinner){
				
			}
		}
		return null;
	}

	public void loadVerificacion() {
		if(getVerificacion() != null){
			List<Respuesta> rsptas = getVerificacion().getRespuestas();
			if(rsptas.size() > 0){
				for(int i = 0; i < rsptas.size() ; i++){
					Respuesta or = rsptas.get(i);
					
					// pregunta 9
					if(or.getPreguntaId() == 9){
						for(int x = 0; x < this.rgHaveTicket.getChildCount(); x++){
							if(this.rgHaveTicket.getChildAt(x) instanceof UIRadioButton){
								UIRadioButton rb = (UIRadioButton)this.rgHaveTicket.getChildAt(x);
								if(or.getOpcionRespuestaId() == rb.getOpcionRespuestaId()){
									this.rgHaveTicket.check(rb.getId());
								}
							}
						}
					}
					
					// pregunta 10
					if(or.getPreguntaId() == 10){
						for(int x = 0; x < this.rgPaymentLocation.getChildCount(); x++){
							if(this.rgPaymentLocation.getChildAt(x) instanceof UIRadioButton){
								UIRadioButton rb = (UIRadioButton)this.rgPaymentLocation.getChildAt(x);
								if(or.getOpcionRespuestaId() == rb.getOpcionRespuestaId()){
									this.rgPaymentLocation.check(rb.getId());
								}
							}
						}
					}
					
					// pregunta 11
					if(or.getPreguntaId() == 11){
						Adapter adapter = spPaymentOutEESS.getAdapter();
						for(int x=0;x < adapter.getCount(); x++){
							OpcionRespuesta orx = (OpcionRespuesta)adapter.getItem(x);
							if(orx.getOpcionRespuestaId() == or.getOpcionRespuestaId()){
								spPaymentOutEESS.setSelection(x);
							}
						}
					}
					
					// pregunta 13
					if(or.getPreguntaId() == 13){
						etAmount.setText(or.getRespuestaNumero()+"");
					}
					
					// pregunta 14
					if(or.getPreguntaId() == 14){
						Adapter adapter = spPaymentInEESS.getAdapter();
						for(int x=0;x < adapter.getCount(); x++){
							OpcionRespuesta orx = (OpcionRespuesta)adapter.getItem(x);
							if(orx.getOpcionRespuestaId() == or.getOpcionRespuestaId()){
								spPaymentInEESS.setSelection(x);
							}
						}
					}
					
					// pregunta 15
					if(or.getPreguntaId() == 15){
						for(int x = 0; x < this.rgImproperPayment.getChildCount(); x++){
							if(this.rgImproperPayment.getChildAt(x) instanceof UIRadioButton){
								UIRadioButton rb = (UIRadioButton)this.rgImproperPayment.getChildAt(x);
								if(or.getOpcionRespuestaId() == rb.getOpcionRespuestaId()){
									this.rgImproperPayment.check(rb.getId());
								}
							}
						}
					}
				}
				
			}
		}
	}
	
	
}
