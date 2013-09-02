package gob.sis.simos.fragment;

import gob.sis.simos.R;
import gob.sis.simos.VerificacionPagosActivity;
import gob.sis.simos.adapters.VerificacionPagoCheckListAdapter;
import gob.sis.simos.controller.VerificacionPagoController;
import gob.sis.simos.entity.ICuantificable;
import gob.sis.simos.entity.VerificacionPago;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import roboguice.fragment.RoboFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;

import com.google.inject.Inject;

public class VerificacionPagoCheckListFragment extends RoboFragment implements OnClickListener, IMaintainableFragment, OnItemLongClickListener  {

	@Inject
	protected VerificacionPagoController controller;
	
	public ListView lstVerificaciones;
	public VerificacionPagoCheckListAdapter adapter;
	public static final int EDIT_SERVICE = 3;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.frgmnt_vrfccn_check_list, container, false);
		
		List<VerificacionPago> items = new ArrayList<VerificacionPago>();
		
		lstVerificaciones = (ListView)rootView.findViewById(R.id.lst_vrfccn);
		adapter = new VerificacionPagoCheckListAdapter(getActivity(), R.layout.adptr_vrfccn_check_list,  items);
		lstVerificaciones.setAdapter(adapter);
		lstVerificaciones.setOnItemLongClickListener(this);
		return rootView;
	}
	
	@Override
	public void checkAllItems() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteCheckedItems() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ICuantificable findItem(ICuantificable c) {
		VerificacionPago cvr = (VerificacionPago)c;
		List<VerificacionPago> items = adapter.getItems();
		Iterator<VerificacionPago> iterator = items.iterator();
		while(iterator.hasNext()){
			VerificacionPago vr = iterator.next();
			if(vr.getNombre().equals(cvr.getNombre())){
				return c;
			}
		}
		return null;
	}

	@Override
	public void updateItem(ICuantificable c) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}

	public void notifyChanges(List<VerificacionPago> items){
		adapter.clear();
		adapter.addAll(items);
		adapter.notifyDataSetChanged();
	}
	
	@Override
	public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
		VerificacionPago vr = (VerificacionPago)lstVerificaciones.getItemAtPosition(position);
		Intent i = new Intent(getActivity(), VerificacionPagosActivity.class);
		i.putExtra("verificacion", vr);
		i.putExtra("action", EDIT_SERVICE);
		getActivity().startActivityForResult(i, EDIT_SERVICE);
		return false;
	}


}
