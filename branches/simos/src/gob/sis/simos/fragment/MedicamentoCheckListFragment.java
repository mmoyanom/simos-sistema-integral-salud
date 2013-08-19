package gob.sis.simos.fragment;

import gob.sis.simos.R;
import gob.sis.simos.adapters.MedicamentoCheckListAdapter;
import gob.sis.simos.controller.RecetaController;
import gob.sis.simos.entity.ICuantificable;
import gob.sis.simos.entity.Medicamento;
import gob.sis.simos.ui.DialogCantidadComercial;
import gob.sis.simos.ui.DialogCantidadGenerico;

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
	
	private DialogCantidadGenerico dialogQuantityGeneric;
	private DialogCantidadComercial dialogQuantityComercial;
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
		
		dialogQuantityGeneric = new DialogCantidadGenerico(getActivity());
		dialogQuantityGeneric.btnOK.setOnClickListener(this);
		dialogQuantityGeneric.btnCANCEL.setOnClickListener(this);
		
		dialogQuantityComercial = new  DialogCantidadComercial(getActivity());
		dialogQuantityComercial.btnOK.setOnClickListener(this);
		dialogQuantityComercial.btnCANCEL.setOnClickListener(this);
		return rootView;
	}

	@Override
	public void onClick(View v) {
		if(dialogQuantityGeneric != null){
			if(v == dialogQuantityGeneric.btnOK){
				if(cuantificable != null){
					updateItem(cuantificable);
					dialogQuantityGeneric.dismiss();
				}
			} else if(v == dialogQuantityGeneric.btnCANCEL){
				dialogQuantityGeneric.dismiss();
			}
		}
		if (dialogQuantityComercial != null){
			if(v == dialogQuantityComercial.btnOK){
				if(cuantificable != null){
					updateItem(cuantificable);
					dialogQuantityComercial.dismiss();
				}
			} else if(v == dialogQuantityComercial.btnCANCEL) {
				dialogQuantityComercial.dismiss();
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
		if(me.getId().equals(Medicamento.COMERCIAL)){
			dialogQuantityComercial.setTitle(me.getNombre());
			dialogQuantityComercial.setCantidad(cuantificable.getRecetado());
			dialogQuantityComercial.show();
		} else {
			dialogQuantityGeneric.setTitle(me.getNombre());
			dialogQuantityGeneric.setCantidadEntregada(cuantificable.getEntregado());
			dialogQuantityGeneric.setCantidadRecetada(cuantificable.getRecetado());
			dialogQuantityGeneric.show();
		}
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
		
			if(c.getId().equals(Medicamento.COMERCIAL)){
				cuantificable.setRecetado(dialogQuantityComercial.getCantidad());
				adapter.notifyDataSetChanged();
			} else {
				cuantificable.setEntregado(dialogQuantityGeneric.getCantidadEntregada());
				cuantificable.setRecetado(dialogQuantityGeneric.getCantidadRecetada());
				adapter.notifyDataSetChanged();
			}
		
	}

}
