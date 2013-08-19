package gob.sis.simos.entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName="answer_options")
public class Respuesta {

	@DatabaseField(columnName="id")
	private String respuestaId;
	
	@DatabaseField(columnName="question_id")
	private String preguntaId;
	
	@DatabaseField(columnName="description")
	private String descripcion;
	
	public String getRespuestaId() {
		return respuestaId;
	}
	public void setRespuestaId(String respuestaId) {
		this.respuestaId = respuestaId;
	}
	public String getPreguntaId() {
		return preguntaId;
	}
	public void setPreguntaId(String preguntaId) {
		this.preguntaId = preguntaId;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	
	
}
