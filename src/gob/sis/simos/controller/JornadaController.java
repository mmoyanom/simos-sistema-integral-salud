package gob.sis.simos.controller;

import gob.sis.simos.entity.Cuenta;
import gob.sis.simos.entity.Jornada;
import gob.sis.simos.service.impl.AsignacionServiceImpl;
import gob.sis.simos.service.impl.JornadaServiceImpl;
import gob.sis.simos.service.impl.LoginServiceImpl;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import roboguice.inject.ContextSingleton;
import android.annotation.SuppressLint;

import com.google.inject.Inject;

@ContextSingleton
public class JornadaController {

	@Inject
	private JornadaServiceImpl jrnService;
	
	@Inject
	private LoginServiceImpl loginService;
	
	@Inject
	private AsignacionServiceImpl asigService;
	
	public Jornada getJornada(){
		return jrnService.getJornada();
	}

	@SuppressLint("SimpleDateFormat")
	public void iniciarJornada(Date start){
		Jornada jr = jrnService.getJornada();
		if(jr == null){
			jr = new Jornada();
		}
		String yyyymmdd = new SimpleDateFormat("yyyyMMdd").format(start);
		jr.setStart(start);
		jr.setDayString(yyyymmdd);
		jr.setFinish(null);
		jr.setTimeString(null);
		String userId = loginService.getStoredAccount().getUserId();
		jr.setUserId(userId);
		jrnService.setJornada(jr);
	}
	
	public boolean jornadaIniciada(){
		boolean iniciada = false;
		Jornada jr = jrnService.getJornada();
		if(jr != null){
			Cuenta cn = loginService.getStoredAccount();
			if(jr.getStart() != null && jr.getUserId().equals(cn.getUserId()))
				iniciada = true;
			else
				iniciada = false;
			return iniciada;
		} else {
			return false;
		}
		
	}
	
	public void finalizarJornada(Date finish) {
		Jornada jr = jrnService.getJornada();
		if(jr != null){
			asigService.cleanAsignaciones();
			jr.setFinish(finish);
			jrnService.setJornada(jr);
		}
	}
	
	public boolean jornadaFinalizada(){
		boolean finalizada = false;
		Jornada jr = jrnService.getJornada();
		if(jr != null){
			Cuenta cn = loginService.getStoredAccount();
			if(jr.getFinish() != null && jr.getUserId().equals(cn.getUserId())){
				finalizada = true;
			}else{
				finalizada = false;
			}
			return finalizada;
		} else {
			return false;
		}
		
	}

	@SuppressLint("SimpleDateFormat")
	public boolean equalsToday(Date start) {
		boolean isStarted = false;
		Date started = start;
		if(started != null){
			String date1 = new SimpleDateFormat("yyyyMMdd").format(Calendar.getInstance().getTime());
			String date2 = new SimpleDateFormat("yyyyMMdd").format(started);
			if(date1.equals(date2)){
				isStarted = true;
			} else {
				isStarted = false;
			}
		}
		return isStarted;
	}
	
}
