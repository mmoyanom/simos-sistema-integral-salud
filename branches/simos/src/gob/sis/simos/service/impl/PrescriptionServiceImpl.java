package gob.sis.simos.service.impl;

import gob.sis.simos.entity.Insumo;
import gob.sis.simos.entity.Medicamento;

import java.util.ArrayList;
import java.util.List;

import roboguice.inject.ContextSingleton;

@ContextSingleton
public class PrescriptionServiceImpl { // implements PrescriptionService {

	//@Override
	public List<Medicamento> getListaMedicamento() {
		List<Medicamento> items = new ArrayList<Medicamento>();
		for(int i=0; i < 3; i++){
			Medicamento m = new Medicamento();
			m.setId(""+(i+1));
			m.setName("Medicamento "+(i+1));
			m.setDescripcion("Medida");
			m.setRecetado(3);
			m.setEntregado(2);
			m.setCategoria("Categoria");
			items.add(m);
		}
		return items;
	}
	
	public List<Medicamento> buscarMedicamento(String text) {
		List<Medicamento> items = new ArrayList<Medicamento>();
		for(int i=0; i < 10; i++){
			Medicamento m = new Medicamento();
			m.setId(""+(i+1));
			m.setName("Medicamento "+(i+1));
			m.setDescripcion("Medida");
			m.setRecetado(3);
			m.setEntregado(2);
			m.setCategoria("Categoria");
			items.add(m);
		}
		return items;
	}

	//@Override
	public List<Insumo> getListaInsumos() {
		List<Insumo> items = new ArrayList<Insumo>();
		for(int i=0;i < 3; i++){
			Insumo in = new Insumo();
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
