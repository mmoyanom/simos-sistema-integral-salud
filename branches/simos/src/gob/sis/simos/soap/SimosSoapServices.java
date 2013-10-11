package gob.sis.simos.soap;

import gob.sis.simos.R;
import gob.sis.simos.resources.AppProperties;
import gob.sis.simos.service.impl.ConfigurationServiceImpl;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.SocketTimeoutException;
import java.util.Date;

import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


public class SimosSoapServices {
	
	private String OPERATION_NAME = null;
	private String NAMESPACE = null;
	private Context _context;
	private Gson gson;
	private ConfigurationServiceImpl cfgService;
	
	public SimosSoapServices(Context ctx) {
		this._context = ctx;
		this.NAMESPACE = AppProperties.getProperty(ctx, "soap_namespace");
		this.gson = new Gson();
		this.cfgService = new ConfigurationServiceImpl();
	}
	
	public String Login(String username, String password) throws IOException, XmlPullParserException {
		this.OPERATION_NAME = "Login";
		
		String soap_action = this.NAMESPACE.concat(this.OPERATION_NAME);
		SoapObject request = new SoapObject(this.NAMESPACE, this.OPERATION_NAME);
		
		PropertyInfo p1 = new PropertyInfo();
		p1.setName("username");
		p1.setType(String.class);
		p1.setValue(username);
		request.addProperty(p1);
		
		PropertyInfo p2 = new PropertyInfo();
		p2.setName("password");
		p2.setType(String.class);
		p2.setValue(password);
		request.addProperty(p2);
		
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapSerializationEnvelope.VER11);
		envelope.dotNet = true;
		envelope.setOutputSoapObject(request);
		
		String url = String.format("http://%s/simosws/LoginService.asmx", cfgService.getConfiguration().getServer());
		
		HttpTransportSE httpTransport = new HttpTransportSE(url,10000);
		httpTransport.call(soap_action, envelope);
		Object response = envelope.getResponse();
			if(response != null) return response.toString();
		return null;
	}

	/*public SendEncuestaResult sendEncuesta(EncuestaSenderObject sender) {
		
		String data = gson.toJson(sender);
		
		System.out.println("data_string : "+data);
		
		this.OPERATION_NAME = "SetRespuestaEncuesta";
		
		String soap_action = this.NAMESPACE.concat(this.OPERATION_NAME);
		SoapObject request = new SoapObject(this.NAMESPACE, this.OPERATION_NAME);
		
		PropertyInfo pi;
		pi = new PropertyInfo();
		pi.setName("SrptaEnc");
		pi.setType(String.class);
		pi.setValue(data);
		request.addProperty(pi);
		
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapSerializationEnvelope.VER11);
		envelope.dotNet = true;
		envelope.setOutputSoapObject(request);
		
		String url = String.format("http://%s/simosws/ApplicationService.asmx", cfgService.getConfiguration().getServer());
		HttpTransportSE httpTransport = new HttpTransportSE(url,10000);
		try {
			httpTransport.call(soap_action, envelope);
			Object soapObject = (Object)envelope.getResponse();
			if(soapObject != null){
				String str_result = soapObject.toString();
				Type type = new TypeToken<SendEncuestaResult>(){}.getType();
				SendEncuestaResult rslt = gson.fromJson(str_result, type);
				return rslt;
			}
		} catch (SocketTimeoutException e){
			e.printStackTrace();
			SendEncuestaResult result = new SendEncuestaResult();
			result.setErrorMessage("Error de conexi—n. Tiempo de espera agotado.");
			return result;
		} catch (IOException e) {		
			e.printStackTrace();
			SendEncuestaResult rslt = new SendEncuestaResult();
			rslt.setErrorMessage(e.getMessage());
			rslt.setResult(SendEncuestaResult.FAILED);
			return rslt;
		} catch (XmlPullParserException e) {
			e.printStackTrace();
			SendEncuestaResult rslt = new SendEncuestaResult();
			rslt.setErrorMessage(e.getMessage());
			rslt.setResult(SendEncuestaResult.FAILED);
			return rslt;
		}
		SendEncuestaResult rslt = new SendEncuestaResult();
		rslt.setErrorMessage(_context.getResources().getString(R.string.msg_send_encuesta_failed));
		rslt.setResult(SendEncuestaResult.FAILED);
		return rslt;
	}*/
	
	public DownloadListOfAsignacionesResult getAsignaciones(String userId, Date date) {
		this.OPERATION_NAME = "GetAsignaciones";
		
		String soap_action = this.NAMESPACE.concat(this.OPERATION_NAME);
		SoapObject request = new SoapObject(this.NAMESPACE, this.OPERATION_NAME);

		PropertyInfo p1;
		p1 = new PropertyInfo();
		p1.setName("UsuarioId");
		p1.setType(String.class);
		p1.setValue(userId);
		request.addProperty(p1);
		
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapSerializationEnvelope.VER11);
		envelope.dotNet = true;
		envelope.setOutputSoapObject(request);
		
		String url = String.format("http://%s/simosws/ApplicationService.asmx", cfgService.getConfiguration().getServer());
		HttpTransportSE httpTransport = new HttpTransportSE(url,10000);
		try {
			httpTransport.call(soap_action, envelope);
			Object soapObject = (Object)envelope.getResponse();
			if(soapObject != null){
				String str_result = soapObject.toString();
				Type type = new TypeToken<DownloadListOfAsignacionesResult>(){}.getType();
				DownloadListOfAsignacionesResult rslt = gson.fromJson(str_result, type);
				
				return rslt;
			}
		} catch (SocketTimeoutException e){
			e.printStackTrace();
			DownloadListOfAsignacionesResult rslt = new DownloadListOfAsignacionesResult();
			rslt.setErrorMessage("Error de conexi—n. Tiempo de espera agotado.");
			return rslt;
		} catch (IOException e) {
			e.printStackTrace();
			DownloadListOfAsignacionesResult rslt = new DownloadListOfAsignacionesResult();
			rslt.setErrorMessage(e.getMessage());
			return rslt;
		} catch (XmlPullParserException e){
			e.printStackTrace();
			DownloadListOfAsignacionesResult rslt = new DownloadListOfAsignacionesResult();
			rslt.setErrorMessage(e.getMessage());
			return rslt;
		}
		DownloadListOfAsignacionesResult rslt = new DownloadListOfAsignacionesResult();
		rslt.setErrorMessage(_context.getResources().getString(R.string.msg_download_asignaciones_failed));
		return rslt;
	}
	
	
	public DownloadListOfOpcionRespuestaResult getOpcionesRespuestas(){
		
		this.OPERATION_NAME = "GetRespuestas";
		
		String soap_action = this.NAMESPACE.concat(this.OPERATION_NAME);
		SoapObject request = new SoapObject(this.NAMESPACE, this.OPERATION_NAME);
		
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapSerializationEnvelope.VER11);
		envelope.dotNet = true;
		envelope.setOutputSoapObject(request);
		
		String url = String.format("http://%s/simosws/ApplicationService.asmx", cfgService.getConfiguration().getServer());
		HttpTransportSE httTransport = new HttpTransportSE(url,10000);
		try {
			httTransport.call(soap_action, envelope);
			Object soapObject = envelope.getResponse();
			if(soapObject != null){
				String str_result = soapObject.toString();
				Type type = new TypeToken<DownloadListOfOpcionRespuestaResult>(){}.getType();
				DownloadListOfOpcionRespuestaResult result = gson.fromJson(str_result, type);
				return result;
			}
		} catch (SocketTimeoutException e){
			e.printStackTrace();
			DownloadListOfOpcionRespuestaResult result = new DownloadListOfOpcionRespuestaResult();
			result.setErrorMessage("Error de conexi—n. Tiempo de espera agotado.");
			return result;
		} catch (IOException e) {
			e.printStackTrace();
			DownloadListOfOpcionRespuestaResult result = new DownloadListOfOpcionRespuestaResult();
			result.setErrorMessage(e.getMessage());
			return result;
		} catch (XmlPullParserException e){
			e.printStackTrace();
			DownloadListOfOpcionRespuestaResult result = new DownloadListOfOpcionRespuestaResult();
			result.setErrorMessage(e.getMessage());
			return result;
		}
		DownloadListOfOpcionRespuestaResult result = new DownloadListOfOpcionRespuestaResult();
		result.setErrorMessage(_context.getResources().getString(R.string.msg_download_opciones_respuesta_failed));
		return result;
	}
	
	
	public SendEncuestaResult sendEncuestas(String data) {
		this.OPERATION_NAME = "SaveEncuestas";

		String soap_action = this.NAMESPACE.concat(this.OPERATION_NAME);
		SoapObject request = new SoapObject(this.NAMESPACE, this.OPERATION_NAME);

		PropertyInfo pi;
		pi = new PropertyInfo();
		pi.setName("data_json");
		pi.setType(String.class);
		pi.setValue(data);
		request.addProperty(pi);

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapSerializationEnvelope.VER11);
		envelope.dotNet = true;
		envelope.setOutputSoapObject(request);
		
		String url = String.format("http://%s/simosws/ApplicationService.asmx", cfgService.getConfiguration().getServer());
		HttpTransportSE httpTransport = new HttpTransportSE(url,20000);
		try {
			httpTransport.call(soap_action, envelope);
			Object soapObject = (Object)envelope.getResponse();
			if(soapObject != null){
				String str_result = soapObject.toString();
				Type type = new TypeToken<SendEncuestaResult>(){}.getType();
				SendEncuestaResult rslt = gson.fromJson(str_result, type);
				return rslt;
			}
		} catch (SocketTimeoutException e) {		
			e.printStackTrace();
			SendEncuestaResult rslt = new SendEncuestaResult();
			rslt.setErrorMessage("Error de conexi—n. Tiempo de espera agotado.");
			rslt.setResult(SendEncuestaResult.FAILED);
			return rslt;	
		} catch (IOException e) {		
			e.printStackTrace();
			SendEncuestaResult rslt = new SendEncuestaResult();
			rslt.setErrorMessage(e.getMessage());
			rslt.setResult(SendEncuestaResult.FAILED);
			return rslt;
		} catch (XmlPullParserException e) {
			e.printStackTrace();
			SendEncuestaResult rslt = new SendEncuestaResult();
			rslt.setErrorMessage(e.getMessage());
			rslt.setResult(SendEncuestaResult.FAILED);
			return rslt;
		}
		SendEncuestaResult rslt = new SendEncuestaResult();
		rslt.setErrorMessage(_context.getResources().getString(R.string.msg_send_encuesta_failed));
		rslt.setResult(SendEncuestaResult.FAILED);
		return rslt;
	}
	
	/*private OnSendEncuestasListener onSendEncuestasListener;
	
	public void setOnSendEncuestasListener(OnSendEncuestasListener listener){
		this.onSendEncuestasListener = listener;
	}
	
	public interface OnSendEncuestasListener {
		public abstract void onFinished();
	}*/

}
