package gob.sis.simos.fragment;

import gob.sis.simos.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class VerificacionPagos02Fragment extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.frgmnt_vrfccn_pgs_02, null);
		return v;
	}

	public void setVisibility(int visibility){
		getView().setVisibility(visibility);
	}
	
}
