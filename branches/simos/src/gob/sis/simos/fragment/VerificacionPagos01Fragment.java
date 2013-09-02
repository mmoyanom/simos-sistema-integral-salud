package gob.sis.simos.fragment;

import gob.sis.simos.AddTicketActivity;
import gob.sis.simos.R;
import gob.sis.simos.SimpleCheckListActivity;
import gob.sis.simos.adapters.OpcionRespuestaSpinnerAdapter;
import gob.sis.simos.controller.VerificacionPagoController;
import gob.sis.simos.entity.OpcionRespuesta;
import gob.sis.simos.entity.Respuesta;
import gob.sis.simos.entity.VerificacionPago;
import gob.sis.simos.ui.UIEditText;
import gob.sis.simos.ui.UIRadioButton;
import gob.sis.simos.ui.UITextView;

import java.util.ArrayList;
import java.util.List;

import roboguice.fragment.RoboFragment;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.inject.Inject;


public class VerificacionPagos01Fragment extends RoboFragment implements OnClickListener, OnCheckedChangeListener, InputFilter, OnLongClickListener,TextWatcher {

	protected RadioGroup rgPaymentLocation;
	protected RadioGroup rgHaveTicket;
	protected RadioGroup rgImproperPayment;
	protected UIEditText etAmount;
	public TextView txtPaymentIn;
	public UITextView txtTickets;
	protected Spinner spPaymentOutEESS;
	protected LinearLayout lyPaymentOut;
	protected LinearLayout lyPaymentIn;
	protected LinearLayout lyTickets;
	protected LinearLayout lyPaymentIn14;
	static final int IN_EESS = 40;
	static final int OUT_EESS = 41;
	static final int BOTH_EESS = 42;
	static final int HAVE_TICKETS_YES = 38;
	static final int HAVE_TICKETS_NO = 39;
	private static final int SELECT_ITEMS = 5;
	private static final int ADD_TICKETS = 6;
	public List<Respuesta> rsptsPaymentIn;
	public List<String> rsptsTickets;
	
	protected int paymentoLocation;
	private VerificacionPago verificacion;
	@Inject
	protected VerificacionPagoController controller;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.frgmnt_vrfccn_pgs_01, null);
		
		this.rgHaveTicket = (RadioGroup)v.findViewById(R.id.rg_have_ticket);
		this.rgHaveTicket.setOnCheckedChangeListener(this);
		
		this.rgPaymentLocation = (RadioGroup)v.findViewById(R.id.rg_payment_location);
		this.rgPaymentLocation.setOnCheckedChangeListener(this);	
		
		this.lyPaymentOut = (LinearLayout)v.findViewById(R.id.layout_payment_out);
		this.spPaymentOutEESS = (Spinner)v.findViewById(R.id.sp_paymentOut);

		this.etAmount = (UIEditText)v.findViewById(R.id.et_ammount);
		this.etAmount.addTextChangedListener(this);
		
		this.txtPaymentIn = (TextView)v.findViewById(R.id.txt_paymentIn);
		this.rsptsPaymentIn = new ArrayList<Respuesta>();
		
		this.txtTickets = (UITextView)v.findViewById(R.id.txt_tickets);
		this.rsptsTickets = new ArrayList<String>();
		
		this.lyPaymentIn = (LinearLayout)v.findViewById(R.id.layout_payment_in);
		
		this.lyTickets = (LinearLayout)v.findViewById(R.id.ly_tickets);
		this.lyTickets.setOnClickListener(this);
		
		this.lyPaymentIn14 = (LinearLayout)v.findViewById(R.id.ly_paymentIn14);
		this.lyPaymentIn14.setOnClickListener(this);

		this.rgImproperPayment = (RadioGroup)v.findViewById(R.id.rg_improper_payment);
		this.rgImproperPayment.setOnCheckedChangeListener(this);
		
		return v;
	}
	
	
	public List<Respuesta> getRespuestas(){
		List<Respuesta> rspts = new ArrayList<Respuesta>();
		
		// pregunta 9
		int idHaveTickets = this.rgHaveTicket.getCheckedRadioButtonId();
		UIRadioButton rbHaveTickes = (UIRadioButton)this.rgHaveTicket.findViewById(idHaveTickets);
		Respuesta rpHaveTickets = rbHaveTickes.getRespuesta();
		rspts.add(rpHaveTickets);
		
		// pregunta 10
		int idPaymentLocation = this.rgPaymentLocation.getCheckedRadioButtonId();
		UIRadioButton rbPaymentLocation = (UIRadioButton)this.rgPaymentLocation.findViewById(idPaymentLocation);
		Respuesta rpPaymentLocation = rbPaymentLocation.getRespuesta();
		rspts.add(rpPaymentLocation);

		// dentro del EESS
		if(rpPaymentLocation.getOpcionRespuestaId() == 40){

			// pregunta 11		
			Respuesta rpPaymentOut = new Respuesta();
			rpPaymentOut.setPreguntaId(11);
			rpPaymentOut.setOpcionRespuestaId(null);
			rspts.add(rpPaymentOut);

			// pregunta 12
			if(this.rsptsTickets != null){
				StringBuffer sbf = new StringBuffer();
				for(int i = 0; i < this.rsptsTickets.size() ; i++){
					sbf.append(this.rsptsTickets.get(i));
					if(i != this.rsptsTickets.size()-1)
						sbf.append(",");
				}
				Respuesta rsp = new Respuesta();
				rsp.setRespuestaTexto(sbf.toString());
				rsp.setPreguntaId(12);
				rsp.setOpcionRespuestaId(55);
				rspts.add(rsp);
			}

			// pregunta 13
			Double amount = Double.parseDouble(this.etAmount.getText().toString());
			Respuesta rpAmountPaymentIn = new Respuesta();
			rpAmountPaymentIn.setRespuestaNumero(amount);
			rpAmountPaymentIn.setPreguntaId(this.etAmount.getPreguntaId());
			rpAmountPaymentIn.setOpcionRespuestaId(this.etAmount.getOpcionRespuestaId());
			rspts.add(rpAmountPaymentIn);
			
			// pregunta 14
			if(this.rsptsPaymentIn != null){
				if(this.rsptsPaymentIn.size() > 0){
					rspts.addAll(this.rsptsPaymentIn);
				} else {
					Respuesta r = new Respuesta();
					r.setPreguntaId(14);
					r.setOpcionRespuestaId(null);
				}
			}
			
		// fuera del EESS
		} else if(rpPaymentLocation.getOpcionRespuestaId() == 41){

			// pregunta 11		
			OpcionRespuesta orPaymentOut = (OpcionRespuesta)this.spPaymentOutEESS.getSelectedItem();
			Respuesta rpPaymentOut = new Respuesta();
			rpPaymentOut.setPreguntaId(orPaymentOut.getPreguntaId());
			rpPaymentOut.setOpcionRespuestaId(orPaymentOut.getOpcionRespuestaId());
			rspts.add(rpPaymentOut);
			
			// pregunta 12
			Respuesta rsp = new Respuesta();
			rsp.setRespuestaTexto(null);
			rsp.setPreguntaId(12);
			rsp.setOpcionRespuestaId(55);
			rspts.add(rsp);

			// pregunta 13
			Respuesta rpAmountPaymentIn = new Respuesta();
			rpAmountPaymentIn.setRespuestaNumero(null);
			rpAmountPaymentIn.setPreguntaId(this.etAmount.getPreguntaId());
			rpAmountPaymentIn.setOpcionRespuestaId(this.etAmount.getOpcionRespuestaId());
			rspts.add(rpAmountPaymentIn);
			
			// pregunta 14
			if(this.rsptsPaymentIn != null){
				if(this.rsptsPaymentIn.size() > 0){
					rspts.addAll(this.rsptsPaymentIn);
				} else {
					Respuesta r = new Respuesta();
					r.setPreguntaId(14);
					r.setOpcionRespuestaId(null);
				}
			}
			
		// Ambas
		} else if(rpPaymentLocation.getOpcionRespuestaId() == 42){

			// pregunta 11		
			OpcionRespuesta orPaymentOut = (OpcionRespuesta)this.spPaymentOutEESS.getSelectedItem();
			Respuesta rpPaymentOut = new Respuesta();
			rpPaymentOut.setPreguntaId(orPaymentOut.getPreguntaId());
			rpPaymentOut.setOpcionRespuestaId(orPaymentOut.getOpcionRespuestaId());
			rspts.add(rpPaymentOut);

			// pregunta 12
			if(this.rsptsTickets != null){
				StringBuffer sbf = new StringBuffer();
				for(int i = 0; i < this.rsptsTickets.size() ; i++){
					sbf.append(this.rsptsTickets.get(i));
					if(i != this.rsptsTickets.size()-1)
						sbf.append(",");
				}
				Respuesta rsp = new Respuesta();
				rsp.setRespuestaTexto(sbf.toString());
				rsp.setPreguntaId(12);
				rsp.setOpcionRespuestaId(55);
				rspts.add(rsp);
			}
			
			// pregunta 13
			Double amount = Double.parseDouble(this.etAmount.getText().toString());
			Respuesta rpAmountPaymentIn = new Respuesta();
			rpAmountPaymentIn.setRespuestaNumero(amount);
			rpAmountPaymentIn.setPreguntaId(this.etAmount.getPreguntaId());
			rpAmountPaymentIn.setOpcionRespuestaId(this.etAmount.getOpcionRespuestaId());
			rspts.add(rpAmountPaymentIn);

			// pregunta 14
			if(this.rsptsPaymentIn != null){
				if(this.rsptsPaymentIn.size() > 0){
					rspts.addAll(this.rsptsPaymentIn);
				} else {
					Respuesta r = new Respuesta();
					r.setPreguntaId(14);
					r.setOpcionRespuestaId(null);
				}
			}
		}
		
		// pregunta 15
		int idImproperPayment = this.rgImproperPayment.getCheckedRadioButtonId();
		UIRadioButton rbImproperPayment = (UIRadioButton)this.rgImproperPayment.findViewById(idImproperPayment);
		Respuesta rpImproperPayment = rbImproperPayment.getRespuesta();
		rspts.add(rpImproperPayment);
		
		return rspts;
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
		//this.btnPaymentInEESS.setAdapter(adapter);
		//this.btnPaymentInEESS.setOnClickListener(this);
		this.lyPaymentIn14.setOnClickListener(this);
		
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
				this.lyTickets.setVisibility(View.VISIBLE);
				this.lyPaymentOut.setVisibility(View.GONE);
				
			} else if(rb.getOpcionRespuestaId() == OUT_EESS){
				this.lyPaymentIn.setVisibility(View.GONE);
				this.lyTickets.setVisibility(View.GONE);
				this.lyPaymentOut.setVisibility(View.VISIBLE);
				
			} else if(rb.getOpcionRespuestaId() == BOTH_EESS){
				this.lyPaymentIn.setVisibility(View.VISIBLE);
				this.lyTickets.setVisibility(View.VISIBLE);
				this.lyPaymentOut.setVisibility(View.VISIBLE);
			}
		}
	}
	
	public String isClear(){
		String str = "No respondi— la pregunta \"%s\".";

		if(this.rgHaveTicket.getCheckedRadioButtonId() == -1)
			return String.format(str, "ÀCuenta con boletas?");
		
		if(this.rgPaymentLocation.getCheckedRadioButtonId() == -1)
			return String.format(str, "ÀDonde realizo los pagos?");
		
		if(this.rgImproperPayment.getCheckedRadioButtonId() == -1)
			return String.format(str, "ÀEs cobro indebido?");
		
		int idPaymentLocation = this.rgPaymentLocation.getCheckedRadioButtonId();
		UIRadioButton rbPaymentLocation = (UIRadioButton)this.rgPaymentLocation.findViewById(idPaymentLocation);
		
		if(rbPaymentLocation.getOpcionRespuestaId() == 40){
			if(this.etAmount.getText().length() == 0){
				return String.format(str, "Monto pagado dentro del EESS");
			}
			if(this.txtPaymentIn.getText().equals("Ninguno seleccionado")){
				return String.format(str, "ÀPorque pago dentro del establecimiento de salud?");
			}
			if(this.txtTickets.getText().equals("No se han ingresado boletas.")){
				return String.format(str, "Boletas entregadas dentro del EESS");
			}
			
		} else if(rbPaymentLocation.getOpcionRespuestaId() == 41) {
			// la spinner de paymentOutESS tiene una seleccion por defecto. No necesita validacion. 
			
		} else if(rbPaymentLocation.getOpcionRespuestaId() == 42) {
			if(this.etAmount.getText().length() == 0){
				return String.format(str, "Monto pagado dentro del EESS");
			}
			if(this.txtPaymentIn.getText().equals("Ninguno seleccionado")){
				return String.format(str, "ÀPorque pago dentro del establecimiento de salud?");
			}
			if(this.txtTickets.getText().equals("No se han ingresado boletas.")){
				return String.format(str, "Boletas entregadas dentro del EESS");
			}
		}
		return "";
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


	public void loadVerificacion() {
		if(getVerificacion() != null){
			this.rsptsPaymentIn.clear();
			List<Respuesta> rsptas = getVerificacion().getRespuestas();
			StringBuffer sbf14 = new StringBuffer();
			if(rsptas.size() > 0){
				for(int i = 0; i < rsptas.size() ; i++){
					Respuesta or = rsptas.get(i);
					
					// pregunta 9
					if(or.getPreguntaId() == 9){
						for(int x = 0; x < this.rgHaveTicket.getChildCount(); x++){
							if(this.rgHaveTicket.getChildAt(x) instanceof UIRadioButton){
								UIRadioButton rb = (UIRadioButton)this.rgHaveTicket.getChildAt(x);
								if(or.getOpcionRespuestaId() != null){
									if(or.getOpcionRespuestaId().equals(rb.getOpcionRespuestaId())){
										this.rgHaveTicket.check(rb.getId());
									}
								}
							}
						}
					}
					
					// pregunta 10
					if(or.getPreguntaId() == 10){
						for(int x = 0; x < this.rgPaymentLocation.getChildCount(); x++){
							if(this.rgPaymentLocation.getChildAt(x) instanceof UIRadioButton){
								UIRadioButton rb = (UIRadioButton)this.rgPaymentLocation.getChildAt(x);
								if(or.getOpcionRespuestaId() != null){
									if(or.getOpcionRespuestaId().equals(rb.getOpcionRespuestaId())){
										this.rgPaymentLocation.check(rb.getId());
									}
								}
							}
						}
					}
					
					//pregunta 11
					if(or.getPreguntaId() == 11){
						Adapter adapter = spPaymentOutEESS.getAdapter();
						for(int x=0;x < adapter.getCount(); x++){
							OpcionRespuesta orx = (OpcionRespuesta)adapter.getItem(x);
							if(orx.getOpcionRespuestaId() != null){
								if(orx.getOpcionRespuestaId().equals(or.getOpcionRespuestaId())){
									spPaymentOutEESS.setSelection(x);
								}
							}
						}
					}
					
					// pregunta 12
					if(or.getPreguntaId() == 12){
						if(or.getRespuestaTexto() != null){
							String[] tickets = or.getRespuestaTexto().split(",");
							StringBuffer sbf = new StringBuffer();
							this.rsptsTickets.clear();
							for(int x = 0; x < tickets.length; x++){
								this.rsptsTickets.add(tickets[x]);
								sbf.append("* ").append(tickets[x]);
								if(i != this.rsptsTickets.size()-1){
									sbf.append("\n");
								}
							}
							this.txtTickets.setText(sbf.toString());
						}
					}
					
					// pregunta 13
					if(or.getPreguntaId() == 13){
						etAmount.setText(or.getRespuestaNumero()+"");
					}
					
					// pregunta 14
					if(or.getPreguntaId() == 14){
						if(this.rsptsPaymentIn != null){
							this.rsptsPaymentIn.add(or);
							sbf14.append("* ").append(or.getRespuestaTexto()).append("\n");	
						}
					}
					
					// pregunta 15
					if(or.getPreguntaId() == 15){
						for(int x = 0; x < this.rgImproperPayment.getChildCount(); x++){
							if(this.rgImproperPayment.getChildAt(x) instanceof UIRadioButton){
								UIRadioButton rb = (UIRadioButton)this.rgImproperPayment.getChildAt(x);
								if(or.getOpcionRespuestaId() != null){
									if(or.getOpcionRespuestaId().equals(rb.getOpcionRespuestaId())){
										this.rgImproperPayment.check(rb.getId());
									}
								}
							}
						}
					}
				}
				if(sbf14.length() > 0){ // si hay elementos de la pregunta 14
					this.txtPaymentIn.setText(sbf14.toString());
				}
			}
		}
	}

	@Override
	public void onClick(View view) {
		if(view == this.lyPaymentIn14){
			Intent in = new Intent(getActivity(), SimpleCheckListActivity.class);
			in.putExtra("preguntaId", 14);
			if(this.rsptsPaymentIn != null){
				if(this.rsptsPaymentIn.size() > 0){
					int[] int_array = new int[this.rsptsPaymentIn.size()];
					for(int x = 0; x < this.rsptsPaymentIn.size(); x++){
						Respuesta r = this.rsptsPaymentIn.get(x);
						int_array[x] = r.getOpcionRespuestaId();
					}
					in.putExtra("bundle_payment_reasons", int_array);
				}
			}
			getActivity().startActivityForResult(in, SELECT_ITEMS);
		}
		if(view == this.lyTickets){
			Intent in = new Intent(getActivity(), AddTicketActivity.class);
			if(this.rsptsTickets != null){
				if(this.rsptsTickets.size() > 0){
					String[] str_array = new String[this.rsptsTickets.size()];
					in.putExtra("bundle_tickets", this.rsptsTickets.toArray(str_array));
				}
			}
			getActivity().startActivityForResult(in, ADD_TICKETS);
		}
	}
	

	
	
}
