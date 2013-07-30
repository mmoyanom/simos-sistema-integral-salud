package gob.sis.simos.service;

import gob.sis.simos.entity.Insumos;
import gob.sis.simos.entity.Medicamento;

import java.util.List;

public interface PrescriptionService {

	public List<Medicamento> getListaMedicamento();
	public List<Insumos> getListaInsumos();
	
}
