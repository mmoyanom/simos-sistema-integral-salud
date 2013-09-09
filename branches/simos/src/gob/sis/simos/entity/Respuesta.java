package gob.sis.simos.entity;

import java.io.Serializable;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName="encta_rsptas")
public class Respuesta implements Serializable {

	private static final long serialVersionUID = 1L;
	//ID
	@DatabaseField(columnName="id")
	@Expose
	private Integer respuestaId;
	
	@DatabaseField(columnName="preg_id")
	@Expose
	private Integer preguntaId;
	
	@DatabaseField(columnName="op_rsp_id")
	@Expose
	private Integer opcionRespuestaId;
	
	@DatabaseField(columnName="encstdo_id")
	private Integer encuestadoId;
	
	@DatabaseField(columnName="preg_prnt_id")
	private Integer respuestaParentId;
	
	@DatabaseField(columnName="rsp_text")
	@Expose
	private String respuestaTexto;
	
	@DatabaseField(columnName="rsp_number")
	@Expose
	private Double respuestaNumero;
	
	@DatabaseField(columnName="rsp_prd_id")
	@Expose
	private String prescripcionId;
	
	@Expose
	private Integer preguntaParentId;
	
	@Expose
	private List<Respuesta> child;
	
	
	public Integer getPreguntaParentId() {
		return preguntaParentId;
	}
	public void setPreguntaParentId(Integer preguntaParentId) {
		this.preguntaParentId = preguntaParentId;
	}
	public List<Respuesta> getChild() {
		return child;
	}
	public void setChild(List<Respuesta> child) {
		this.child = child;
	}
	public Integer getOpcionRespuestaId() {
		return opcionRespuestaId;
	}
	public void setOpcionRespuestaId(Integer opcionRespuestaId) {
		this.opcionRespuestaId = opcionRespuestaId;
	}
	public Integer getEncuestadoId() {
		return encuestadoId;
	}
	public void setEncuestadoId(Integer encuestadoId) {
		this.encuestadoId = encuestadoId;
	}
	public Integer getRespuestaId() {
		return respuestaId;
	}
	public void setRespuestaId(Integer respuestaId) {
		this.respuestaId = respuestaId;
	}
	public Integer getRespuestaParentId() {
		return respuestaParentId;
	}
	public void setRespuestaParentId(Integer respuestaParentId) {
		this.respuestaParentId = respuestaParentId;
	}
	public String getRespuestaTexto() {
		return respuestaTexto;
	}
	public void setRespuestaTexto(String respuestaTexto) {
		this.respuestaTexto = respuestaTexto;
	}
	public Double getRespuestaNumero() {
		return respuestaNumero;
	}
	public void setRespuestaNumero(Double respuestaNumero) {
		this.respuestaNumero = respuestaNumero;
	}
	public String getPrescripcionId() {
		return prescripcionId;
	}
	public void setPrescripcionId(String prescripcionId) {
		this.prescripcionId = prescripcionId;
	}
	public Integer getPreguntaId() {
		return preguntaId;
	}
	public void setPreguntaId(Integer preguntaId) {
		this.preguntaId = preguntaId;
	}
	
	
}
