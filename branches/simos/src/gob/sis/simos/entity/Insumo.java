package gob.sis.simos.entity;

import java.io.Serializable;

public class Insumo implements Serializable, ICuantificable, ICheckable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final String COMERCIAL = "COMERCIAL";
	
	private String id;	
	private String nombre;
	private String categoria;
	private String descripcion;
	private Integer recetado;
	private Integer entregado;
	
	private boolean checked = false;
	
	public String getCategoria() {
		return categoria;
	}
	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	@Override
	public int getEntregado() {
		// TODO Auto-generated method stub
		return this.entregado;
	}
	@Override
	public void setEntregado(int entregado) {
		// TODO Auto-generated method stub
		this.entregado = entregado;
	}
	@Override
	public int getRecetado() {
		// TODO Auto-generated method stub
		return this.recetado;
	}
	@Override
	public void setRecetado(int recetado) {
		// TODO Auto-generated method stub
		this.recetado = recetado;
	}
	@Override
	public void setChecked(boolean checked) {
		// TODO Auto-generated method stub
		this.checked = checked;
	}
	@Override
	public boolean isChecked() {
		// TODO Auto-generated method stub
		return this.checked;
	}
	
	
	
}
