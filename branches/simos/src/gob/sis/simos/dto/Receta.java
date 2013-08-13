package gob.sis.simos.dto;

import gob.sis.simos.entity.ICheckable;
import gob.sis.simos.entity.ICuantificable;
import gob.sis.simos.entity.Insumo;
import gob.sis.simos.entity.Medicamento;

import java.io.Serializable;
import java.util.List;

public class Receta implements Serializable, ICheckable, ICuantificable {

	private static final long serialVersionUID = 1L;
	
	public static final String TIPO_ESTANDAR = "ESTANDAR";
	public static final String TIPO_NO_ESTANDAR = "NO ESTANDAR";
	
	private String id;
	private String tipo;
	private List<Insumo> insumos;
	private List<Medicamento> medicamentos;
	private boolean checked = false;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public List<Insumo> getInsumos() {
		return insumos;
	}
	public void setInsumos(List<Insumo> insumos) {
		this.insumos = insumos;
	}
	public List<Medicamento> getMedicamentos() {
		return medicamentos;
	}
	public void setMedicamentos(List<Medicamento> medicamentos) {
		this.medicamentos = medicamentos;
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
	
	@Override
	public int getEntregado() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public void setEntregado(int entregado) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public int getRecetado() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public void setRecetado(int recetado) {
		// TODO Auto-generated method stub
		
	}
	
	
}
