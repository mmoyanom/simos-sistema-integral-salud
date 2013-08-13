package gob.sis.simos.fragment;

import gob.sis.simos.EntregaRecetasActivity;
import gob.sis.simos.R;
import gob.sis.simos.adapters.RecetaCheckListAdapter;
import gob.sis.simos.controller.RecetaController;
import gob.sis.simos.dto.Receta;
import gob.sis.simos.entity.ICuantificable;

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

public class RecetaCheckListFragment extends RoboFragment implements OnClickListener, IMaintainableFragment, OnItemLongClickListener  {

	@Inject
	protected RecetaController controller;
	
	public ListView lstRecetas;
	public RecetaCheckListAdapter adapter;
	
	public static final int ADD_SERVICE = 0;
	public static final int EDIT_PRESCRIPTION = 2;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.frgmnt_rctas_check_list, container, false);
		
		List<Receta> items = new ArrayList<Receta>();
		
		lstRecetas = (ListView)rootView.findViewById(R.id.lst_recetas);
		adapter = new RecetaCheckListAdapter(getActivity(), R.layout.adptr_rcta_check_list,  items);
		lstRecetas.setAdapter(adapter);
		lstRecetas.setOnItemLongClickListener(this);
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
		List<Receta> items = adapter.getItems();
		Iterator<Receta> iterator = items.iterator();
		while(iterator.hasNext()){
			Receta rc = iterator.next();
			if(rc.getId().equals(c.getId())){
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

	@Override
	public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
		Receta rc = (Receta)lstRecetas.getItemAtPosition(position);
		Intent i = new Intent(getActivity(), EntregaRecetasActivity.class);
		i.putExtra("receta", rc);
		i.putExtra("action", EDIT_PRESCRIPTION);
		getActivity().startActivityForResult(i, EDIT_PRESCRIPTION);
		return false;
	}


}
