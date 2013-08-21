package gob.sis.simos.fragment;

import gob.sis.simos.R;

import java.util.ArrayList;
import java.util.List;

import roboguice.fragment.RoboFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class VerificacionPagosTicketsFragment extends RoboFragment {
	
	private ListView lstTickets;
	
	public ArrayAdapter<String> adapter;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.frgmnt_vrfccn_pgs_tickets, null);
		this.lstTickets = (ListView) v.findViewById(R.id.lst_tickets);
		return v;
	}
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		
		List<String> items = new ArrayList<String>();
		this.adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1,items);
		this.lstTickets.setAdapter(adapter);
	}

}
