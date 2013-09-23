package gob.sis.simos.soap;

import gob.sis.simos.entity.OpcionRespuesta;

import java.util.ArrayList;

public class DownloadListOfOpcionRespuestaResult {

	private Boolean success = false;
	private ArrayList<OpcionRespuesta> data;
	private String errorMessage;
	
	public DownloadListOfOpcionRespuestaResult() {
		// TODO Auto-generated constructor stub
	}

	public Boolean isSuccess() {
		return success;
	}

	public void setSuccess(Boolean success) {
		this.success = success;
	}

	public ArrayList<OpcionRespuesta> getData() {
		return data;
	}

	public void setData(ArrayList<OpcionRespuesta> data) {
		this.data = data;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	
	
	

}
