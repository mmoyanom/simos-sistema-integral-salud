package gob.sis.simos.soap;

import java.io.Serializable;

public class SendEncuestaResult implements Serializable {
	
	private static final long serialVersionUID = 1L;
	public static final int SUCCEEDED = 1;
	public static final int FAILED = 0;
	private Integer result;
	private Integer encuestaId;
	private String errorMessage;
	
	public Integer getResult() {
		return result;
	}
	public void setResult(Integer success) {
		this.result = success;
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
