package gob.sis.simos.entity;

import gob.sis.simos.dto.Receta;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName="encstdo")
public class Encuesta01 implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@DatabaseField(columnName="id",id=true)
	@Expose
	private Integer id;
	
	@DatabaseField(columnName="group_id")
	@Expose
	private Integer encuestaGrupo;
	
	@DatabaseField(columnName="nro_encsta")
	@Expose
	private String nroCuestionario;
	
	@DatabaseField(columnName="created",dataType=DataType.DATE_STRING)
	@Expose
	private Date created;
	
	@DatabaseField(columnName="json")
	private String json;
	
	@DatabaseField(columnName="sent")
	@Expose
	private Integer sent;
	
	@DatabaseField(columnName="form_id")
	@Expose
	private String formularioId;
	
	private List<Respuesta> datosEncuestado;
	private List<Receta> recetas;
	private List<VerificacionPago> verificaciones;
	private List<Respuesta> respuestas;
	
	public Encuesta01() {
		this.datosEncuestado = new ArrayList<Respuesta>();
		this.recetas = new ArrayList<Receta>();
		this.verificaciones = new ArrayList<VerificacionPago>();
	}
	
	public String getFormularioId() {
		return formularioId;
	}

	public void setFormularioId(String formularioId) {
		this.formularioId = formularioId;
	}

	public Integer getEncuestaGrupo() {
		return encuestaGrupo;
	}

	public void setEncuestaGrupo(Integer encuestaGrupo) {
		this.encuestaGrupo = encuestaGrupo;
	}
	
	public String getNroCuestionario() {
		return nroCuestionario;
	}

	public void setNroCuestionario(String nroCuestinario) {
		this.nroCuestionario = nroCuestinario;
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
	
	public List<VerificacionPago> getVerificaciones() {
		return verificaciones;
	}
	
	public void setVerificaciones(List<VerificacionPago> verificaciones) {
		this.verificaciones = verificaciones;
	}

	public List<Respuesta> getDatosEncuestado() {
		return datosEncuestado;
	}

	public void setDatosEncuestado(List<Respuesta> datosEncuestado) {
		this.datosEncuestado = datosEncuestado;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Integer getSent() {
		return sent;
	}

	public void setSent(Integer sent) {
		this.sent = sent;
	}

	public List<Respuesta> getRespuestas() {
		return respuestas;
	}

	public void setRespuestas(List<Respuesta> respuestas) {
		this.respuestas = respuestas;
	}

	public String getJson() {
		return json;
	}

	public void setJson(String json) {
		this.json = json;
	}
	
	
	
}
