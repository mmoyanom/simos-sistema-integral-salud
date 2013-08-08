package gob.sis.simos.entity;

import java.io.Serializable;

public class Medicamento extends Cuantificable implements Serializable {

	private static final long serialVersionUID = 1L;
	private String categoria;
	private String nombre;
	private String descripcion;
	
	public String getCategoria() {
		return categoria;
	}
	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}
	public String getNombre() {
		return nombre;
	}
	public void setName(String nombre) {
		this.nombre = nombre;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
}
