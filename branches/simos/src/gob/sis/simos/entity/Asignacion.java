package gob.sis.simos.entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName="eess")
public class Asignacion {
	
	@DatabaseField(columnName="encta_id",id=true)
	private Integer encuestaId;
	
	@DatabaseField(columnName="eess_id")
	private String establecimientoId;
	
	@DatabaseField(columnName="eess_name")
	private String establecimientoName;
	
	@DatabaseField(columnName="eess_desc")
	private String establecimientoDesc;
	
	@DatabaseField(columnName="turn_id")
	private Integer turnoId;
	
	@DatabaseField(columnName="turn_name")
	private String turnoDescripcion;
	
	@DatabaseField(columnName="programmed")
	private String fechaProgramada;
	
	@DatabaseField(columnName="created")
	private String fechaProgramacion;

	public Integer getEncuestaId() {
		return encuestaId;
	}

	public void setEncuestaId(Integer encuestaId) {
		this.encuestaId = encuestaId;
	}

	public String getEstablecimientoId() {
		return establecimientoId;
	}

	public void setEstablecimientoId(String establecimientoId) {
		this.establecimientoId = establecimientoId;
	}

	public String getEstablecimientoName() {
		return establecimientoName;
	}

	public void setEstablecimientoName(String establecimientoName) {
		this.establecimientoName = establecimientoName;
	}

	public String getEstablecimientoDesc() {
		return establecimientoDesc;
	}

	public void setEstablecimientoDesc(String establecimientoDesc) {
		this.establecimientoDesc = establecimientoDesc;
	}

	public Integer getTurnoId() {
		return turnoId;
	}

	public void setTurnoId(Integer turnoId) {
		this.turnoId = turnoId;
	}

	public String getTurnoDescripcion() {
		return turnoDescripcion;
	}

	public void setTurnoDescripcion(String turnoDescripcion) {
		this.turnoDescripcion = turnoDescripcion;
	}

	public String getFechaProgramada() {
		return fechaProgramada;
	}

	public void setFechaProgramada(String fechaProgramada) {
		this.fechaProgramada = fechaProgramada;
	}

	public String getFechaProgramacion() {
		return fechaProgramacion;
	}

	public void setFechaProgramacion(String fechaProgramacion) {
		this.fechaProgramacion = fechaProgramacion;
	}
	
	
	

}
