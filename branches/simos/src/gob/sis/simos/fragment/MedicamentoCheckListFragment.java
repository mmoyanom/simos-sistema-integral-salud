package gob.sis.simos.fragment;

import gob.sis.simos.R;
import gob.sis.simos.adapters.MedicamentoCheckListAdapter;
import gob.sis.simos.controller.RecetaController;
import gob.sis.simos.entity.ICuantificable;
import gob.sis.simos.entity.Medicamento;
import gob.sis.simos.ui.DialogCantidad;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import roboguice.fragment.RoboFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;

import com.google.inject.Inject;

public class MedicamentoCheckListFragment extends RoboFragment implements OnClickListener, IMaintainableFragment, OnItemLongClickListener {

	public static final String ARG_SECTION_NUMBER = "section_number";

	@Inject
	protected RecetaController controller;
	
	public ListView lstPrescription;
	public MedicamentoCheckListAdapter adapter;
	
	private DialogCantidad dialog;
	private ICuantificable cuantificable;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.frgmnt_medcmntos_check_list, container, false);
		
		List<Medicamento> items =  new ArrayList<Medicamento>();
		
		lstPrescription = (ListView)rootView.findViewById(R.id.lst_medicamentos);
		adapter = new MedicamentoCheckListAdapter(getActivity().getBaseContext(), R.layout.adptr_medcmnto_check_list, items);
		lstPrescription.setAdapter(adapter);
		lstPrescription.setOnItemLongClickListener(this);
		
		dialog = new DialogCantidad(getActivity());
		dialog.btnOK.setOnClickListener(this);
		dialog.btnCANCEL.setOnClickListener(this);
		return rootView;
	}

	@Override
	public void onClick(View v) {
		if(dialog != null){
			if(v == dialog.btnOK){
				if(cuantificable != null){
					updateItem(cuantificable);
					dialog.dismiss();
				}
			} else if(v == dialog.btnCANCEL){
				dialog.dismiss();
			}
		}
	}
	
	public void notifyChanges(List<Medicamento> items){
		adapter.clear();
		adapter.addAll(items);
		adapter.notifyDataSetChanged();
	}
	
	@Override
	public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
		Medicamento me = adapter.getItem(position);
		this.cuantificable = me;
		dialog.setTitle(me.getNombre());
		dialog.setCantidadEntregada(cuantificable.getEntregado());
		dialog.setCantidadRecetada(cuantificable.getRecetado());
		dialog.show();
		return true;
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

	@Override
	public void updateItem(ICuantificable c) {
		// TODO Auto-generated method stub
		cuantificable.setEntregado(dialog.getCantidadEntregada());
		cuantificable.setRecetado(dialog.getCantidadRecetada());
		adapter.notifyDataSetChanged();
	}

}
