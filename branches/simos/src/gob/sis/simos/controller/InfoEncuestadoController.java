package gob.sis.simos.controller;

import gob.sis.simos.entity.Respuesta;
import gob.sis.simos.service.impl.OpcionesRespuestaServiceImpl;

import java.util.List;

import roboguice.inject.ContextSingleton;

import com.google.inject.Inject;

@ContextSingleton
public class InfoEncuestadoController {


	@Inject
	protected OpcionesRespuestaServiceImpl service;
	
	public List<Respuesta> getRespuestas(int preguntaId){
		return service.getRespuestas(preguntaId);
	}

}
