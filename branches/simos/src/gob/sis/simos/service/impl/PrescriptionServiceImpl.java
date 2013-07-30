package gob.sis.simos.service.impl;

import java.util.ArrayList;
import java.util.List;

import roboguice.inject.ContextSingleton;

import gob.sis.simos.entity.Insumos;
import gob.sis.simos.entity.Medicamento;
import gob.sis.simos.service.PrescriptionService;

@ContextSingleton
public class PrescriptionServiceImpl { // implements PrescriptionService {

	//@Override
	public List<Medicamento> getListaMedicamento() {
		List<Medicamento> items = new ArrayList<Medicamento>();
		for(int i=0; i < 10; i++){
			Medicamento m = new Medicamento();
			m.setId(""+(i+1));
			m.setName("Medicamento "+(i+1));
			m.setDescription("Medida");
			m.setPrescribed(3);
			m.setCommited(2);
			m.setCategory("Categoria");
			items.add(m);
		}
		return items;
	}

	//@Override
	public List<Insumos> getListaInsumos() {
		List<Insumos> items = new ArrayList<Insumos>();
		for(int i=0;i < 3; i++){
			Insumos in = new Insumos();
			in.setId(""+(i+1));
			in.setName("Insumo "+(i+1));
			in.setDescription("Medida");
			in.setPrescribed(3);
			in.setCommited(2);
			in.setCategory("Categoria");
			items.add(in);
		}
		return items;
	}
	
	

}
