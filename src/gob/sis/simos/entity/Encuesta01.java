package gob.sis.simos.entity;

import gob.sis.simos.dto.Receta;

import java.util.ArrayList;
import java.util.List;

public class Encuesta01 {

	private Integer id;
	private List<Receta> recetas;
	
	public Encuesta01() {
		this.recetas = new ArrayList<Receta>();
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
	
	
}
