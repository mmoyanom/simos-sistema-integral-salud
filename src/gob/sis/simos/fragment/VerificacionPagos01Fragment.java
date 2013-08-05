package gob.sis.simos.fragment;

import gob.sis.simos.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;


public class VerificacionPagos01Fragment extends Fragment implements OnCheckedChangeListener {

	protected RadioGroup _rgPaymentLocation;
	protected View separatorLayoutTicket;
	protected LinearLayout layoutNroTicket;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.frgmnt_vrfccn_pgs_01, null);
		
		this._rgPaymentLocation = (RadioGroup)v.findViewById(R.id.rg_payment_location);
		this._rgPaymentLocation.setOnCheckedChangeListener(this);
		
		this.separatorLayoutTicket = (View)v.findViewById(R.id.separator_layout_nro_ticket);
		
		this.layoutNroTicket = (LinearLayout)v.findViewById(R.id.layout_nro_ticket);
		return v;
	}
	
	public void setVisibility(int visibility){
		getView().setVisibility(visibility);
	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		RadioButton r = (RadioButton) getView().findViewById(checkedId);
		int index = this._rgPaymentLocation.indexOfChild(r);
		if(index == 0 || index == 4){
			this.separatorLayoutTicket.setVisibility(View.VISIBLE);
			this.layoutNroTicket.setVisibility(View.VISIBLE);
		} else {
			this.separatorLayoutTicket.setVisibility(View.GONE);
			this.layoutNroTicket.setVisibility(View.GONE);	
		}
	}
}
