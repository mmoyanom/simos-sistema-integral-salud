package gob.sis.simos.service;

import gob.sis.simos.entity.EstablecimientoSalud;

import java.util.List;

import android.content.Context;

public interface EstablecimientoSaludService {

	public void setContext(Context context);
	public List<EstablecimientoSalud> getEESSList();
	
}
