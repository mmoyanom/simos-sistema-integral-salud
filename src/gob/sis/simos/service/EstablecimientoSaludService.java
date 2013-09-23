package gob.sis.simos.service;

import gob.sis.simos.entity.Asignacion;

import java.util.List;

import android.content.Context;

public interface EstablecimientoSaludService {

	public void setContext(Context context);
	public List<Asignacion> getEESSList();
	
}
