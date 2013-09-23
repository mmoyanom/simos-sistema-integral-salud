package gob.sis.simos.controller;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import com.google.inject.Inject;

import gob.sis.simos.entity.Encuesta01;
import gob.sis.simos.service.impl.EncuestaServiceImpl;
import gob.sis.simos.soap.DownloadListOfOpcionRespuestaResult;
import gob.sis.simos.soap.SendEncuestaResult;
import roboguice.inject.ContextSingleton;

@ContextSingleton
public class EncuestaController {

	@Inject
	private EncuestaServiceImpl encuestaService;
	
	public List<Encuesta01> findAll() throws SQLException {
		return encuestaService.findAll();
	}
	
	public List<Encuesta01> findAllByDate(Date date) throws SQLException {
		return encuestaService.findAllByDate(date);
	}
	
	public List<Encuesta01> findUnsent() throws SQLException {
		return encuestaService.findUnsent();
	}

	public List<Encuesta01> findUnsentByDate(Date date) throws SQLException {
		return encuestaService.findUnsentByDate(date);
	}
	
	public List<Encuesta01> findSent() throws SQLException {		
		return encuestaService.findSent();
	}
	
	public List<Encuesta01> findSentByDate(Date date) throws SQLException {		
		return encuestaService.findSentByDate(date);
	}
	
	public SendEncuestaResult save(Encuesta01 encuesta) {
		return encuestaService.save(encuesta);
	}
	
	public SendEncuestaResult SendEncuestasUnsent(){
		return encuestaService.SendEncuestasNoEnviadas();
	}

	public Result downloadOpcionesRespuesta() {
		return encuestaService.downloadOpcionesRespuesta();
	}
	
	public void cleanStoredEncuestas(){
		encuestaService.cleanStoredEncuestas();
	}

}
