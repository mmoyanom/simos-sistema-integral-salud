package gob.sis.simos.fragment;

import java.util.ArrayList;
import java.util.List;

import gob.sis.simos.R;
import gob.sis.simos.adapters.RecetaCheckListAdapter;
import gob.sis.simos.controller.RecetaController;
import gob.sis.simos.dto.Receta;
import gob.sis.simos.entity.ICuantificable;
import roboguice.fragment.RoboFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ListView;

import com.google.inject.Inject;

public class RecetaCheckListFragment extends RoboFragment implements OnClickListener, IMaintainableFragment  {

	@Inject
	protected RecetaController controller;
	
	public ListView lstRecetas;
	public RecetaCheckListAdapter adapter;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.frgmnt_rctas_check_list, container, false);
		
		List<Receta> items = new ArrayList<Receta>();
		
		lstRecetas = (ListView)rootView.findViewById(R.id.lst_recetas);
		adapter = new RecetaCheckListAdapter(getActivity(), R.layout.adptr_rcta_check_list,  items);
		lstRecetas.setAdapter(adapter);
		
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
		// TODO Auto-generated method stub
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

}
