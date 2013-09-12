package gob.sis.simos.fragment;

import java.sql.SQLException;
import java.util.List;

import com.google.inject.Inject;

import gob.sis.simos.R;
import gob.sis.simos.adapters.SimpleEncuestaListAdapter;
import gob.sis.simos.controller.EncuestaController;
import gob.sis.simos.entity.Encuesta01;
import roboguice.fragment.RoboFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

public class ListaEncuestasAlmacenadasFragment extends RoboFragment {

	@Inject private EncuestaController controller;
			private ListView lvEncuestas;
			private SimpleEncuestaListAdapter adapter;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.frgmnt_strd_encsts, null);
		this.adapter = new SimpleEncuestaListAdapter(getActivity(), android.R.layout.simple_list_item_1);
		this.lvEncuestas = (ListView)v.findViewById(R.id.listView);
		this.lvEncuestas.setAdapter(this.adapter);
		return v;
	}
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		
		try {
			List<Encuesta01> items = controller.findAll();
			adapter.clear();
			adapter.addAll(items);
			adapter.notifyDataSetChanged();
		} catch (SQLException e) {			
			e.printStackTrace();
			showMessage(e.getMessage(), Toast.LENGTH_LONG);
		}
	}
	
	private void showMessage(String text, int length){
		Toast.makeText(getActivity(), text, length).show();
	}
	
}
