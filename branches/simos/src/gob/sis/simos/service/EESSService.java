package gob.sis.simos.service;

import gob.sis.simos.entity.EESS;

import java.util.List;

import android.content.Context;

public interface EESSService {

	public void setContext(Context context);
	public List<EESS> getEESSList();
	
}
