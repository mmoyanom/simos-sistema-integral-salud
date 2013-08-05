package gob.sis.simos.controller;

import gob.sis.simos.entity.EstablecimientoSalud;
import gob.sis.simos.service.impl.EESSServiceImpl;

import java.util.List;

import roboguice.inject.ContextSingleton;

import com.google.inject.Inject;

@ContextSingleton
public class EstablecimientoSaludController  {

	@Inject
	protected EESSServiceImpl _service;
	//protected EESSService _service;
	
	public List<EstablecimientoSalud> getEESSList(){
		return _service.getEESSList();
	}
	
	
}
