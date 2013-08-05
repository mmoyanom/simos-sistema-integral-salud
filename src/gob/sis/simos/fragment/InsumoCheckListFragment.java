package gob.sis.simos.fragment;

import gob.sis.simos.R;
import gob.sis.simos.adapters.InsumoListAdapter;
import gob.sis.simos.controller.RecetaController;
import gob.sis.simos.entity.Insumo;
import gob.sis.simos.service.impl.PrescriptionServiceImpl;

import java.util.List;

import roboguice.fragment.RoboFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.google.inject.Inject;

public class InsumoCheckListFragment extends RoboFragment implements OnItemClickListener {
	
	public static final String ARG_SECTION_NUMBER = "section_number";

	@Inject
	protected RecetaController controller;
	//protected PrescriptionServiceImpl service;

	public ListView lstPrescription;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.frgmnt_insms_check_list,container, false);
		
		List<Insumo> items = controller.getInsumos();
		
		lstPrescription = (ListView)rootView.findViewById(R.id.lst_insumos);
		InsumoListAdapter adapter = new InsumoListAdapter(getActivity().getBaseContext(), R.layout.adptr_insms_check_list, items);
		lstPrescription.setAdapter(adapter);
		lstPrescription.setOnItemClickListener(this);
		
		/*TextView dummyTextView = (TextView) rootView
				.findViewById(R.id.section_label);
		dummyTextView.setText(Integer.toString(getArguments().getInt(
				ARG_SECTION_NUMBER)));*/
		return rootView;
	}


	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		System.out.println("click!");
	}

}
