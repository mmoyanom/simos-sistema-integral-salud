package gob.sis.simos.soap;

import java.io.Serializable;

public class SendEncuestaResult implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private boolean success = false;
	private Integer encuestaId;
	private String errorMessage;
	
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public Integer getEncuestaId() {
		return encuestaId;
	}
	public void setEncuestaId(Integer encuestaId) {
		this.encuestaId = encuestaId;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	
}
