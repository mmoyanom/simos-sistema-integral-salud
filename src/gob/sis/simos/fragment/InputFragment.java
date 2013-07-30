package gob.sis.simos.fragment;

import gob.sis.simos.R;
import gob.sis.simos.adapters.InputListAdapter;
import gob.sis.simos.adapters.MedicineListAdapter;
import gob.sis.simos.entity.Insumos;
import gob.sis.simos.entity.Medicamento;
import gob.sis.simos.service.impl.PrescriptionServiceImpl;

import java.util.List;

import roboguice.fragment.RoboFragment;

import com.google.inject.Inject;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class InputFragment extends RoboFragment {
	
	public static final String ARG_SECTION_NUMBER = "section_number";

	@Inject
	protected PrescriptionServiceImpl service;


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_prescription01,
				container, false);
		
		List<Insumos> items = service.getListaInsumos();
		
		ListView lstPrescription = (ListView)rootView.findViewById(R.id.lst_prescription);
		InputListAdapter adapter = new InputListAdapter(getActivity().getBaseContext(), R.layout.adapter_main_menu, items);
		lstPrescription.setAdapter(adapter);
		
		/*TextView dummyTextView = (TextView) rootView
				.findViewById(R.id.section_label);
		dummyTextView.setText(Integer.toString(getArguments().getInt(
				ARG_SECTION_NUMBER)));*/
		return rootView;
	}

}
