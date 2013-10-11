package gob.sis.simos.entity;

import java.io.Serializable;
import java.util.Date;

import com.google.gson.annotations.Expose;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName="encta_group")
public class EncuestaGrupo implements Serializable {

	private static final long serialVersionUID = 1L;
	@DatabaseField(columnName="id", id=true) private Integer id;
	@DatabaseField(columnName="id_encta") @Expose private Integer encuestaId;
	@DatabaseField(columnName="id_eess") @Expose private String establecimientoId;
	@DatabaseField(columnName="id_enctdor") @Expose private Integer encuestadorId;
	@DatabaseField(columnName="fec_encta") @Expose private Date fechaEncuesta;
	@DatabaseField(columnName="fec_encta_yyyymmdd") @Expose private String fechaEncuestaYYYYMMDD;
	
	
	public EncuestaGrupo(){
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

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

	public Integer getEncuestadorId() {
		return encuestadorId;
	}

	public void setEncuestadorId(Integer encuestadorId) {
		this.encuestadorId = encuestadorId;
	}

	public Date getFechaEncuesta() {
		return fechaEncuesta;
	}

	public void setFechaEncuesta(Date fechaEncuesta) {
		this.fechaEncuesta = fechaEncuesta;
	}

	public String getFechaEncuestaYYYYMMDD() {
		return fechaEncuestaYYYYMMDD;
	}

	public void setFechaEncuestaYYYYMMDD(String fechaEncuestaYYYYMMDD) {
		this.fechaEncuestaYYYYMMDD = fechaEncuestaYYYYMMDD;
	}
	
	

}
