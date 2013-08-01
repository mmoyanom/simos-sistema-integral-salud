package gob.sis.simos.fragment;

import gob.sis.simos.R;
import gob.sis.simos.adapters.MedicineSelectableListAdapter;
import gob.sis.simos.controller.PrescriptionController;
import gob.sis.simos.entity.Medicamento;

import java.util.ArrayList;
import java.util.List;

import roboguice.fragment.RoboFragment;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.google.inject.Inject;

@ContentView(R.layout.fragment_prescription01)
public class MedicineFragment extends RoboFragment implements OnItemClickListener {

	
	public static final String ARG_SECTION_NUMBER = "section_number";

	@Inject
	protected PrescriptionController controller;
	
	public ListView lstPrescription;

	public MedicineSelectableListAdapter adapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_prescription01, container, false);
		
		List<Medicamento> items =  new ArrayList<Medicamento>();//controller.getMedicamentos();
		
		lstPrescription = (ListView)rootView.findViewById(R.id.lst_prescription);
		adapter = new MedicineSelectableListAdapter(getActivity().getBaseContext(), R.layout.adapter_selectable_medicine, items);
		lstPrescription.setAdapter(adapter);
		lstPrescription.setOnItemClickListener(this);
		
		return rootView;
	}


	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		System.out.println("click!");
	}
}