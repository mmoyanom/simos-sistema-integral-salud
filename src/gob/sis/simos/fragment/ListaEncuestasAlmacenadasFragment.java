package gob.sis.simos.fragment;

import gob.sis.simos.R;
import gob.sis.simos.adapters.SimpleEncuestaListAdapter;
import gob.sis.simos.entity.Encuesta01;

import java.util.List;

import roboguice.fragment.RoboFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class ListaEncuestasAlmacenadasFragment extends RoboFragment {
	
	public static final int LIST_ALL = 0;
	public static final int LIST_UNSENT = 1;
	public static final int LIST_SENT = 2;
	private ListView lvEncuestas;
	private List<Encuesta01> items;
	public SimpleEncuestaListAdapter adapter;
	
	
	
	public ListaEncuestasAlmacenadasFragment() {
		super();		
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.frgmnt_strd_encsts, null);
		this.adapter = new SimpleEncuestaListAdapter(getActivity(), android.R.layout.simple_list_item_1);
		this.lvEncuestas = (ListView)v.findViewById(R.id.listView);
		this.lvEncuestas.setAdapter(this.adapter);
		return v;
	}
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		if(items != null){
			this.adapter.clear();
			this.adapter.addAll(items);
			this.adapter.notifyDataSetChanged();
		}
	}
	
	public void setItems(List<Encuesta01> items){
		this.items = items;
	}
	
	
}
