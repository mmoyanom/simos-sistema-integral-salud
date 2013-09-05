package gob.sis.simos.entity;

import gob.sis.simos.dto.Receta;

import java.io.Serializable; 
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Encuesta01 implements Serializable {

	private static final long serialVersionUID = 1L;
	private Integer id;
	private List<Respuesta> datosEncuestado;
	private List<Receta> recetas;
	private List<VerificacionPago> verificaciones;
	private Date created;
	private Integer sent;
	
	public Encuesta01() {
		this.datosEncuestado = new ArrayList<Respuesta>();
		this.recetas = new ArrayList<Receta>();
		this.verificaciones = new ArrayList<VerificacionPago>();
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public List<Receta> getRecetas() {
		return recetas;
	}
	public void setRecetas(List<Receta> recetas) {
		this.recetas = recetas;
	}
	
	public List<VerificacionPago> getVerificaciones() {
		return verificaciones;
	}
	
	public void setVerificaciones(List<VerificacionPago> verificaciones) {
		this.verificaciones = verificaciones;
	}

	public List<Respuesta> getDatosEncuestado() {
		return datosEncuestado;
	}

	public void setDatosEncuestado(List<Respuesta> datosEncuestado) {
		this.datosEncuestado = datosEncuestado;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Integer getSent() {
		return sent;
	}

	public void setSent(Integer sent) {
		this.sent = sent;
	}
	
}
