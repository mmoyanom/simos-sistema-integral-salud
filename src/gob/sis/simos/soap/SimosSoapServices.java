package gob.sis.simos.soap;

import gob.sis.simos.entity.Encuesta01;
import gob.sis.simos.resources.AppProperties;

import java.io.IOException;

import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import android.content.Context;

public class SimosSoapServices {
	
	private String OPERATION_NAME = null;
	private String NAMESPACE = null;
	private String ADDRESS = null;
	private Context _context;
	public SimosSoapServices(Context ctx) {
		this._context = ctx;
		this.NAMESPACE = AppProperties.getProperty(ctx, "soap_namespace");
		this.ADDRESS = AppProperties.getProperty(ctx, "soap_address");
	}
	
	public String Login(String username, String password) throws IOException, XmlPullParserException {
		this.OPERATION_NAME = AppProperties.getProperty(this._context, "soap_login_operation");
		
		String soap_action = this.NAMESPACE.concat(this.OPERATION_NAME);
		SoapObject request = new SoapObject(this.NAMESPACE, this.OPERATION_NAME);
		
		PropertyInfo pi;
		
		pi = new PropertyInfo();
		pi.setName(AppProperties.getProperty(this._context, "soap_login_property_username"));
		pi.setType(String.class);
		pi.setValue(username);
		request.addProperty(pi);
		
		pi = new PropertyInfo();
		pi.setName(AppProperties.getProperty(this._context, "soap_login_property_password"));
		pi.setType(String.class);
		pi.setValue(password);
		request.addProperty(pi);
		
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapSerializationEnvelope.VER11);
		envelope.dotNet = true;
		envelope.setOutputSoapObject(request);
		
		HttpTransportSE httpTransport = new HttpTransportSE(this.ADDRESS);
		httpTransport.call(soap_action, envelope);
		Object response = envelope.getResponse();
			if(response != null) return response.toString();
		return null;
	}

	public String sendEncuesta(Encuesta01 encuesta) {
		
		return "failed";
	}

}
