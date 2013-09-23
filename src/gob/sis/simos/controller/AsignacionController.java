package gob.sis.simos.controller;

import gob.sis.simos.entity.Asignacion;
import gob.sis.simos.service.impl.AsignacionServiceImpl;
import gob.sis.simos.soap.DownloadListOfAsignacionesResult;

import java.util.List;

import roboguice.inject.ContextSingleton;

import com.google.inject.Inject;

@ContextSingleton
public class AsignacionController  {

	@Inject
	protected AsignacionServiceImpl _service;
	//protected EESSService _service;
	
	public List<Asignacion> getAsignacionList(){
		return _service.getAsignacionList();
	}
	
	public DownloadListOfAsignacionesResult downloadAsignaciones(){
		return _service.downloadAsignaciones();
	}

	public void cleanAsignaciones() {
		_service.cleanAsignaciones();
	}
	
}
