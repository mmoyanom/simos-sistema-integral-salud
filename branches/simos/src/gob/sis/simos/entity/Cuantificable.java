package gob.sis.simos.entity;

public class Cuantificable {

	protected String id;
	protected int entregado;
	protected int recetado;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getEntregado() {
		return entregado;
	}
	public void setEntregado(int entregado) {
		this.entregado = entregado;
	}
	public int getRecetado() {
		return recetado;
	}
	public void setRecetado(int recetado) {
		this.recetado = recetado;
	}
	
	
	/*public abstract void setCantidad(int cantidad);
	public abstract int getCantidad();
	
	public abstract void setId(String id);
	public abstract String getId();
	
	public abstract void setEntregado(int entregado);
	public abstract int getEntregado();*/
}
