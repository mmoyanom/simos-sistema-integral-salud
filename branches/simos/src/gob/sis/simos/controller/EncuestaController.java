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
	
	public List<Encuesta01> findUnsent() throws SQLException {
		return encuestaService.findUnsent();
	}

	public List<Encuesta01> findSent() throws SQLException {		
		return encuestaService.findSent();
	}
	
	public SendEncuestaResult save(Encuesta01 encuesta) {
		return encuestaService.saveEncuesta(encuesta);
	}

	

}
