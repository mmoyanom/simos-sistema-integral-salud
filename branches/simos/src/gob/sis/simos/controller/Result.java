package gob.sis.simos.controller;

public class Result {

	public ResultType resultType;
	private String message;
	
	public ResultType getResultType() {
		return resultType;
	}
	public void setResultType(ResultType resultType) {
		this.resultType = resultType;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	
	
}
