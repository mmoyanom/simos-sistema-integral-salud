package gob.sis.simos.controller;

import gob.sis.simos.entity.Insumos;
import gob.sis.simos.entity.Medicamento;
import java.util.List;

import gob.sis.simos.service.impl.PrescriptionServiceImpl;

import com.google.inject.Inject;


public class PrescriptionController {
	
	@Inject
	protected PrescriptionServiceImpl service;
	
	public List<Medicamento> buscarMedicamento(String text){
		return service.buscarMedicamento(text);
	}
	
	public List<Medicamento> getMedicamentos(){
		return service.getListaMedicamento();
	}
	
	public List<Insumos> getInsumos(){
		return service.getListaInsumos();
	}

}
