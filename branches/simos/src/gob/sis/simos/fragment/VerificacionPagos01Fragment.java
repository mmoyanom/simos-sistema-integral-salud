package gob.sis.simos.fragment;

import gob.sis.simos.R;
import gob.sis.simos.controller.VerificacionPagoController;
import gob.sis.simos.entity.OpcionRespuesta;

import java.util.Hashtable;

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
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import com.google.inject.Inject;


public class VerificacionPagos01Fragment extends RoboFragment implements OnCheckedChangeListener, InputFilter, OnLongClickListener,TextWatcher {

	protected RadioGroup rgPaymentLocation;
	protected RadioGroup rgHaveTicket;
	protected RadioGroup rgImproperPayment;
	protected EditText etAmount;
	static final int IN_EESS = 0;
	static final int OUT_EESS = 2;
	static final int BOTH_EESS = 4;
	protected int paymentoLocation;
	
	@Inject
	protected VerificacionPagoController controller;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.frgmnt_vrfccn_pgs_01, null);
		
		this.rgPaymentLocation = (RadioGroup)v.findViewById(R.id.rg_payment_location);
		this.rgPaymentLocation.setOnCheckedChangeListener(this);

		this.rgHaveTicket = (RadioGroup)v.findViewById(R.id.rg_have_ticket);
		this.rgHaveTicket.setOnCheckedChangeListener(this);
		
		this.rgImproperPayment = (RadioGroup)v.findViewById(R.id.rg_improper_payment);
		this.rgImproperPayment.setOnCheckedChangeListener(this);
		
		this.etAmount = (EditText)v.findViewById(R.id.et_ammount);
		this.etAmount.addTextChangedListener(this);
		
		return v;
	}
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
	}
	
	public void setVisibility(int visibility){
		getView().setVisibility(visibility);
	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		
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
		int id = this.rgPaymentLocation.getCheckedRadioButtonId();
		RadioButton r = (RadioButton) getView().findViewById(id);
		int index = this.rgPaymentLocation.indexOfChild(r);
		if(index == IN_EESS || index == BOTH_EESS) {
			//this.paymentoLocation = index;
			return true;
		} else {
			//this.paymentoLocation = OUT_EESS;
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
	
	
}
