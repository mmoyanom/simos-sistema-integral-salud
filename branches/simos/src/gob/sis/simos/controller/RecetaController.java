package gob.sis.simos.controller;

import gob.sis.simos.entity.Insumo;
import gob.sis.simos.entity.Medicamento;
import gob.sis.simos.service.impl.PrescriptionServiceImpl;

import java.util.List;

import com.google.inject.Inject;


public class RecetaController {
	
	@Inject
	protected PrescriptionServiceImpl service;
	
	public List<Medicamento> findMedicamento(String text){
		return service.findMedicamento(text);
	}
	
	public List<Medicamento> getMedicamentos(){
		return service.getListaMedicamento();
	}
	
	public List<Insumo> getInsumos(){
		return service.getListaInsumos();
	}
	
	public List<Insumo> findInsumos(String text){
		return service.findInsumos(text);
	}

}
