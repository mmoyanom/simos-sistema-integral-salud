package gob.sis.simos.entity;

import java.io.Serializable;

public class Medicamento implements Serializable, ICuantificable, ICheckable {

	private static final long serialVersionUID = 1L;
	private String id;
	private String categoria;
	private String nombre;
	private String descripcion;
	private Integer entregado;
	private Integer recetado;
	
	private boolean checked = false;
	
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
	@Override
	public String getId() {
		return id;
	}
	@Override
	public void setId(String id) {
		this.id = id;
	}
	@Override
	public int getEntregado() {
		return this.entregado;
	}
	@Override
	public void setEntregado(int entregado) {
		this.entregado = entregado;
	}
	@Override
	public int getRecetado() {
		return this.recetado;
	}
	@Override
	public void setRecetado(int recetado) {
		this.recetado = recetado;
	}
	@Override
	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	@Override
	public boolean isChecked() {
		// TODO Auto-generated method stub
		return this.checked;
	}
	
}
