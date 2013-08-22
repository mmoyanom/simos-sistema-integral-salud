package gob.sis.simos.controller;

import gob.sis.simos.entity.OpcionRespuesta;
import gob.sis.simos.service.impl.OpcionesRespuestaServiceImpl;

import java.util.List;

import roboguice.inject.ContextSingleton;

import com.google.inject.Inject;

@ContextSingleton
public class VerificacionPagoController {
	
	@Inject
	protected OpcionesRespuestaServiceImpl service;
	
	public List<OpcionRespuesta> getRespuestas(int preguntaId){
		return service.getRespuestas(preguntaId);
	}

}
