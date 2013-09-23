package gob.sis.simos.dto;

import java.util.List;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;

import gob.sis.simos.entity.Encuesta01;
import gob.sis.simos.entity.Respuesta;

public class EncuestaSenderObject {

	@Expose
	private Encuesta01 encuesta;
	@Expose
	private List<Respuesta> respuestas;
	
	public Encuesta01 getEncuesta() {
		return encuesta;
	}
	public void setEncuesta(Encuesta01 encuesta) {
		this.encuesta = encuesta;
	}
	public List<Respuesta> getRespuestas() {
		return respuestas;
	}
	public void setRespuestas(List<Respuesta> respuestas) {
		this.respuestas = respuestas;
	}
	
	public String toJson(Gson gson){
		return gson.toJson(this);
	}
	
}
