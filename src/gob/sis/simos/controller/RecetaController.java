package gob.sis.simos.controller;

import gob.sis.simos.entity.Insumo;
import gob.sis.simos.entity.Medicamento;
import java.util.List;

import gob.sis.simos.service.impl.PrescriptionServiceImpl;

import com.google.inject.Inject;


public class RecetaController {
	
	@Inject
	protected PrescriptionServiceImpl service;
	
	public List<Medicamento> buscarMedicamento(String text){
		return service.buscarMedicamento(text);
	}
	
	public List<Medicamento> getMedicamentos(){
		return service.getListaMedicamento();
	}
	
	public List<Insumo> getInsumos(){
		return service.getListaInsumos();
	}

}
