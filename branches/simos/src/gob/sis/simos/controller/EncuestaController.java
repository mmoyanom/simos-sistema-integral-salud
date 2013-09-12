package gob.sis.simos.controller;

import java.sql.SQLException;
import java.util.List;

import com.google.inject.Inject;

import gob.sis.simos.entity.Encuesta01;
import gob.sis.simos.service.impl.EncuestaServiceImpl;
import gob.sis.simos.soap.SendEncuestaResult;
import roboguice.inject.ContextSingleton;

@ContextSingleton
public class EncuestaController {

	@Inject
	private EncuestaServiceImpl encuestaService;
	
	public List<Encuesta01> findAll() throws SQLException {
		return encuestaService.findAll();
	}
	
	public SendEncuestaResult save(Encuesta01 encuesta) {
		return encuestaService.saveEncuesta(encuesta);
	}
	

}
