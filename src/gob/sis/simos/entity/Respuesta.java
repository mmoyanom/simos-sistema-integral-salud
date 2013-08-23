package gob.sis.simos.entity;

public class Respuesta {

	//ID
	private Integer respuestaId;
	private Integer preguntaId;
	private Integer opcionRespuestaId;
	private Integer encuestadoId;
	private Integer respuestaParentId;
	private String respuestaTexto;
	private Double respuestaNumero;
	private Integer prescripcionId;
	
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
	public Integer getPrescripcionId() {
		return prescripcionId;
	}
	public void setPrescripcionId(Integer prescripcionId) {
		this.prescripcionId = prescripcionId;
	}
	public Integer getPreguntaId() {
		return preguntaId;
	}
	public void setPreguntaId(Integer preguntaId) {
		this.preguntaId = preguntaId;
	}
	
	
}
