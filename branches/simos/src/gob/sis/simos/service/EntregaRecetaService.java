package gob.sis.simos.service;

import gob.sis.simos.entity.Insumo;
import gob.sis.simos.entity.Medicamento;

import java.util.List;

public interface EntregaRecetaService {

	public List<Medicamento> getListaMedicamento();
	public List<Insumo> getListaInsumos();
	
}
