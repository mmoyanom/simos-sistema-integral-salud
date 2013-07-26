package gob.sis.simos.controller;

import gob.sis.simos.entity.EESS;
import gob.sis.simos.service.EESSService;

import java.util.List;

import android.content.Context;

import com.google.inject.Inject;

public class EESSController  {

	@Inject
	protected EESSService _service;
	private Context _context;
	
	public EESSController() {
	}
	
	public void setContext(Context context){
		this._context = context;
		this._service.setContext(this._context);
	}
	
	public List<EESS> getEESSList(){
		return _service.getEESSList();
	}
	
	
}
