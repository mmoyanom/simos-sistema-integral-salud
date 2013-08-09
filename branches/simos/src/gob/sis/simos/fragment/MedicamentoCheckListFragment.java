package gob.sis.simos.fragment;

import gob.sis.simos.R;
import gob.sis.simos.adapters.MedicamentoCheckListAdapter;
import gob.sis.simos.controller.RecetaController;
import gob.sis.simos.entity.ICuantificable;
import gob.sis.simos.entity.Medicamento;

import java.util.ArrayList;
import java.util.Iterator;
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

public class MedicamentoCheckListFragment extends RoboFragment implements OnItemClickListener,IMaintainableFragment {

	public static final String ARG_SECTION_NUMBER = "section_number";

	@Inject
	protected RecetaController controller;
	
	public ListView lstPrescription;
	public MedicamentoCheckListAdapter adapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.frgmnt_medcmntos_check_list, container, false);
		
		List<Medicamento> items =  new ArrayList<Medicamento>();
		
		lstPrescription = (ListView)rootView.findViewById(R.id.lst_medicamentos);
		adapter = new MedicamentoCheckListAdapter(getActivity().getBaseContext(), R.layout.adptr_medcmnto_check_list, items);
		lstPrescription.setAdapter(adapter);
		lstPrescription.setOnItemClickListener(this);
		
		return rootView;
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		System.out.println("click!");
	}

	 
	
	@Override
	public void checkAllItems() {
		List<Medicamento> items = adapter.getItems();
		for (int i = 0 ; i < items.size() ; i++) {
			Medicamento medicamento = items.get(i);
			medicamento.setChecked(true);
		}
		adapter.notifyDataSetChanged();
	}

	@Override
	public void clear() {		
		List<Medicamento> items = adapter.getItems();
		for (int i = 0 ; i < items.size() ; i++) {
			Medicamento medicamento = items.get(i);
			medicamento.setChecked(false);
		}
		adapter.notifyDataSetChanged();
	}

	@Override
	public void deleteCheckedItems() {
		List<Medicamento> items = adapter.getItems();
		Iterator<Medicamento> iterator = items.iterator();
		while(iterator.hasNext()){
			Medicamento medicamento = iterator.next();
			if(medicamento.isChecked()){
				iterator.remove();
			}
		}
		adapter.notifyDataSetChanged();
	}

	@Override
	public ICuantificable findItem(ICuantificable c) {
		List<Medicamento> items = adapter.getItems();
		Iterator<Medicamento> iterator = items.iterator();
		while(iterator.hasNext()){
			Medicamento medicamento = iterator.next();
			if(medicamento.getId().equals(c.getId())){
				return medicamento;
			}
		}
		return null;
	}
}
