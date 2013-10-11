package gob.sis.simos.application;

import gob.sis.simos.entity.Asignacion;
import gob.sis.simos.entity.EncuestaGrupo;
import android.app.Application;

public class MyApplication extends Application {

	public Asignacion asignacion;
	public Integer formulario;
	public EncuestaGrupo encuestaGrupo;
	
	public Asignacion getSelectedAsignacion(){
		return asignacion;
	}
	
	public void setSelectedAsignacion(Asignacion asignacion) {
		this.asignacion = asignacion;
	}
	
	public void setSelectedFormulario(Integer formulario){
		this.formulario = formulario;
	}

	public Asignacion getAsignacion() {
		return asignacion;
	}

	public void setAsignacion(Asignacion asignacion) {
		this.asignacion = asignacion;
	}

	public Integer getFormulario() {
		return formulario;
	}

	public void setFormulario(Integer formulario) {
		this.formulario = formulario;
	}

	public EncuestaGrupo getEncuestaGrupo(){
		if(encuestaGrupo == null){
			encuestaGrupo = new EncuestaGrupo();
		}
		return encuestaGrupo;
	}

}
