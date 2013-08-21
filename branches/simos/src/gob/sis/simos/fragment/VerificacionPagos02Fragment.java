package gob.sis.simos.fragment;

import gob.sis.simos.R;
import gob.sis.simos.adapters.OpcionRespuestaSpinnerAdapter;
import gob.sis.simos.controller.VerificacionPagoController;
import gob.sis.simos.entity.Respuesta;

import java.util.List;

import roboguice.fragment.RoboFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;

import com.google.inject.Inject;


public class VerificacionPagos02Fragment extends RoboFragment {

	private Spinner splugarIndicacionPago;
	private Spinner spPersonaIndicaPago;
	private Spinner spContribuyenteDevolucion;
	@Inject
	private VerificacionPagoController controller;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.frgmnt_vrfccn_pgs_02, null);
		
		this.splugarIndicacionPago = (Spinner)v.findViewById(R.id.spinner_1);
		this.spPersonaIndicaPago = (Spinner)v.findViewById(R.id.spinner_2);
		this.spContribuyenteDevolucion = (Spinner)v.findViewById(R.id.spinner_3);
		return v;
	}
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		loadPreguntas();
	}

	private void loadPreguntas() {
		
		List<Respuesta> items = controller.getRespuestas(16);
		OpcionRespuestaSpinnerAdapter adapter = new OpcionRespuestaSpinnerAdapter(getActivity(), items);
		this.splugarIndicacionPago.setAdapter(adapter);
		
		items = controller.getRespuestas(17);
		adapter = new OpcionRespuestaSpinnerAdapter(getActivity(), items);
		this.spPersonaIndicaPago.setAdapter(adapter);
		
		items = controller.getRespuestas(19);
		adapter = new OpcionRespuestaSpinnerAdapter(getActivity(), items);
		this.spContribuyenteDevolucion.setAdapter(adapter);
		
	}

	public void setVisibility(int visibility){
		getView().setVisibility(visibility);
	}
	
}
