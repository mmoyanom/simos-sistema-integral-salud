package gob.sis.simos.soap;

import gob.sis.simos.entity.Asignacion;

import java.util.ArrayList;

public class DownloadListOfAsignacionesResult {

	private Boolean success = false;
	private ArrayList<Asignacion> data;
	private String errorMessage;
	private String updateMessage;
	
	public DownloadListOfAsignacionesResult() {
		
	}
	
	public String getUpdateMessage() {
		return updateMessage;
	}
	
	public void setUpdateMessage(String updateMessage) {
		this.updateMessage = updateMessage;
	}

	public Boolean isSuccess() {
		return success;
	}

	public void setSuccess(Boolean success) {
		this.success = success;
	}

	public ArrayList<Asignacion> getData() {
		return data;
	}

	public void setData(ArrayList<Asignacion> data) {
		this.data = data;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	
	

}
