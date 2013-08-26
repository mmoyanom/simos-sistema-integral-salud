package gob.sis.simos.fragment;

import gob.sis.simos.R;
import gob.sis.simos.entity.Respuesta;
import gob.sis.simos.entity.VerificacionPago;

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
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class VerificacionPagosTicketsFragment extends RoboFragment implements OnItemLongClickListener {
	
	private ListView lstTickets;
	private AlertDialog alertDialog;
	public ArrayAdapter<String> adapter;
	private VerificacionPago verificacion;
	
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
		
		loadPreguntas();
		loadVerificacion();
	}

	private void loadVerificacion() {
		if(getVerificacion() != null){
			List<Respuesta> rsptas = getVerificacion().getRespuestas();
			if(rsptas.size() > 0){
				for(int i = 0; i < rsptas.size() ; i++){
					Respuesta or = rsptas.get(i);
					
					// pregunta 12
					if(or.getPreguntaId() == 12){
						if(or.getRespuestaTexto() != null){
							String[] arrTickets = or.getRespuestaTexto().split(";");
							adapter.clear();
							for(int x = 0; x < arrTickets.length ; x++){
								adapter.add(arrTickets[x]);
							}
						}
					}
				}
			}
		}
	}

	private void loadPreguntas() {
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

	public VerificacionPago getVerificacion() {
		return verificacion;
	}

	public void setVerificacion(VerificacionPago verificacion) {
		this.verificacion = verificacion;
	}

	
}
