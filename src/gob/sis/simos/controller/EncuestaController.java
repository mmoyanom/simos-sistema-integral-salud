package gob.sis.simos.controller;

import com.google.inject.Inject;

import gob.sis.simos.entity.Encuesta01;
import gob.sis.simos.service.impl.EncuestaServiceImpl;
import roboguice.inject.ContextSingleton;

@ContextSingleton
public class EncuestaController {

	@Inject
	private EncuestaServiceImpl encuestaService;
	
	public void save(Encuesta01 encuesta) {
		encuestaService.saveEncuesta(encuesta);
	}

	

}
