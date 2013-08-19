package gob.sis.simos.entity;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class VerificacionPago implements Serializable, ICuantificable, ICheckable {

	private static final long serialVersionUID = 1L;
	
	private String id;
	private List<Respuesta> respuestas;
	
	private boolean checked = false;
	
	public VerificacionPago() {
		this.respuestas = new ArrayList<Respuesta>();
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
	public String getId() {
		// TODO Auto-generated method stub
		return this.id;
	}

	@Override
	public void setId(String id) {
		// TODO Auto-generated method stub
		this.id = id;
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

	public List<Respuesta> getRespuestas() {
		return respuestas;
	}

	public void setRespuestas(List<Respuesta> respuestas) {
		this.respuestas = respuestas;
	}

	
	
}
