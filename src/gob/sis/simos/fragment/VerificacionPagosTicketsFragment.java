package gob.sis.simos.fragment;

import gob.sis.simos.R;

import java.util.ArrayList;
import java.util.List;

import roboguice.fragment.RoboFragment;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemLongClickListener;

public class VerificacionPagosTicketsFragment extends RoboFragment implements OnItemLongClickListener {
	
	private ListView lstTickets;
	private AlertDialog alertDialog;
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
		this.lstTickets.setOnItemLongClickListener(this);
	}
	
	public int getTicketsCount(){
		if(lstTickets != null){
			return adapter.getCount();
		}
		return 0;
	}

	public void delete(final String nroBoleta){
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder
			.setTitle("Eliminar Boleta")
			.setMessage(String.format("Esta seguro de eliminar la boleta '%s'?",nroBoleta))
			.setCancelable(false)
			.setNegativeButton("No", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					
				}
			})
			.setPositiveButton("Si", new DialogInterface.OnClickListener() {
				@Override
		        public void onClick(DialogInterface dialog, int id) {
		        	  adapter.remove(nroBoleta);
		        }
		     });
		this.alertDialog = builder.create();
		this.alertDialog.show();
	}

	@Override
	public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
		String item = (String)this.lstTickets.getItemAtPosition(position);
		this.delete(item);
		return false;
	}

}
