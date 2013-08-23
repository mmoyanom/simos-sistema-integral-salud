package gob.sis.simos.entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName="answer_options")
public class OpcionRespuesta {

	@DatabaseField(columnName="id")
	private Integer opcionRespuestaId;
	
	@DatabaseField(columnName="question_id")
	private Integer preguntaId;
	
	@DatabaseField(columnName="description")
	private String descripcion;
	
	public Integer getOpcionRespuestaId() {
		return opcionRespuestaId;
	}
	public void setOpcionRespuestaId(Integer opcionRespuestaId) {
		this.opcionRespuestaId = opcionRespuestaId;
	}
	public Integer getPreguntaId() {
		return preguntaId;
	}
	public void setPreguntaId(Integer preguntaId) {
		this.preguntaId = preguntaId;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	
	
}
