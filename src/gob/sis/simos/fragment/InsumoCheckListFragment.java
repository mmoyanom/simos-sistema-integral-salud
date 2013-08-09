package gob.sis.simos.fragment;

import gob.sis.simos.R;
import gob.sis.simos.adapters.InsumoCheckListAdapter;
import gob.sis.simos.controller.RecetaController;
import gob.sis.simos.entity.ICuantificable;
import gob.sis.simos.entity.Insumo;

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

public class InsumoCheckListFragment extends RoboFragment implements OnItemClickListener, IMaintainableFragment {
	
	public static final String ARG_SECTION_NUMBER = "section_number";

	@Inject
	protected RecetaController controller;

	public ListView lstPrescription;
	public InsumoCheckListAdapter adapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.frgmnt_insms_check_list,container, false);
		
		List<Insumo> items = new ArrayList<Insumo>();
		
		lstPrescription = (ListView)rootView.findViewById(R.id.lst_insumos);
		adapter = new InsumoCheckListAdapter(getActivity().getBaseContext(), R.layout.adptr_insms_check_list, items);
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
		List<Insumo> items = adapter.getItems();
		for (int i = 0 ; i < items.size() ; i++) {
			Insumo insumo = items.get(i);
			insumo.setChecked(true);
		}
		adapter.notifyDataSetChanged();
	}


	@Override
	public void clear() {
		List<Insumo> items = adapter.getItems();
		for (int i = 0 ; i < items.size() ; i++) {
			Insumo insumo = items.get(i);
			insumo.setChecked(false);
		}
		adapter.notifyDataSetChanged();
	}


	@Override
	public void deleteCheckedItems() {
		List<Insumo> items = adapter.getItems();
		Iterator<Insumo> iterator = items.iterator();
		while(iterator.hasNext()){
			Insumo insumo = iterator.next();
			if(insumo.isChecked()){
				iterator.remove();
			}
		}
		adapter.notifyDataSetChanged();
	}


	@Override
	public ICuantificable findItem(ICuantificable c) {
		List<Insumo> items = adapter.getItems();
		Iterator<Insumo> iterator = items.iterator();
		while(iterator.hasNext()){
			Insumo insumo = iterator.next();
			if(insumo.getId().equals(c.getId())){
				return insumo;
			}
		}
		return null;
	}
}
