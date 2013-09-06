package gob.sis.simos.soap;

import gob.sis.simos.dto.EncuestaSenderObject;
import gob.sis.simos.dto.Receta;
import gob.sis.simos.entity.Encuesta01;
import gob.sis.simos.entity.Insumo;
import gob.sis.simos.entity.Medicamento;
import gob.sis.simos.entity.Respuesta;
import gob.sis.simos.entity.VerificacionPago;
import gob.sis.simos.resources.AppProperties;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class SimosSoapServices {
	
	private String OPERATION_NAME = null;
	private String NAMESPACE = null;
	private String ADDRESS = null;
	private Context _context;
	private Gson gson;
	
	public SimosSoapServices(Context ctx) {
		this._context = ctx;
		this.NAMESPACE = AppProperties.getProperty(ctx, "soap_namespace");
		this.ADDRESS = AppProperties.getProperty(ctx, "soap_address");
		this.gson = new GsonBuilder()
        .excludeFieldsWithoutExposeAnnotation()
        .create();
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

	public String sendEncuesta(Encuesta01 encuesta) throws IOException, XmlPullParserException {
		
		EncuestaSenderObject obj = new EncuestaSenderObject();
		obj.setEncuesta(encuesta);
		
		List<Respuesta> rspts = this.getRespuestas(encuesta);
		obj.setRespuestas(rspts);
		
		String data = gson.toJson(obj);
		
		this.OPERATION_NAME = "saveEncuesta";
		
		String soap_action = this.NAMESPACE.concat(this.OPERATION_NAME);
		SoapObject request = new SoapObject(this.NAMESPACE, this.OPERATION_NAME);
		
		PropertyInfo pi;
		pi = new PropertyInfo();
		pi.setName("dataString");
		pi.setType(String.class);
		pi.setValue(data);
		request.addProperty(pi);
		
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapSerializationEnvelope.VER11);
		envelope.dotNet = true;
		envelope.setOutputSoapObject(request);
		
		HttpTransportSE httpTransport = new HttpTransportSE("http://190.81.47.202:5000/simosws/ApplicationService.asmx");
		httpTransport.call(soap_action, envelope);
		Object response = envelope.getResponse();
		if(response != null) return response.toString();
			return null;
	}
	
	public List<Respuesta> getRespuestas(Encuesta01 encuesta){
		try {
			
			List<Respuesta> rspts = new ArrayList<Respuesta>();
			rspts.addAll(encuesta.getDatosEncuestado());
			
			for(int i = 0; i < encuesta.getVerificaciones().size() ; i++){
				VerificacionPago vf = encuesta.getVerificaciones().get(i);
				rspts.addAll(vf.getRespuestas());
			}
			int rc_count=0;
			for(int i = 0; i < encuesta.getRecetas().size(); i++){
				Receta rc = encuesta.getRecetas().get(i);
				if(rc != null){
					rc_count++;
					Respuesta rpIdReceta = new Respuesta();
					rpIdReceta.setPreguntaId(21);
					rpIdReceta.setOpcionRespuestaId(99);
					rpIdReceta.setRespuestaTexto(rc_count+"");
					rspts.add(rpIdReceta);
					
					Respuesta rpTipoReceta = new Respuesta();
					rpTipoReceta.setPreguntaId(22);
					rpTipoReceta.setOpcionRespuestaId(rc.getTipoRecetaId());
					rspts.add(rpTipoReceta);
					
					for(int x = 0; x < rc.getMedicamentos().size() ; x++){
						Medicamento me = rc.getMedicamentos().get(x);
						// Comercial
						if(me.getNombre().equals(Medicamento.COMERCIAL)){
							Respuesta rpMedName = new Respuesta();
							rpMedName.setPreguntaId(23);
							rpMedName.setOpcionRespuestaId(102);
							rpMedName.setRespuestaTexto(me.getNombre());
							rpMedName.setPrescripcionId("9999999");
							rspts.add(rpMedName);
							
							Respuesta rpMedProductType = new Respuesta();
							rpMedProductType.setPreguntaId(24);
							rpMedProductType.setOpcionRespuestaId(109);
							rspts.add(rpMedProductType);
							
							Respuesta rpPrescribed = new Respuesta();
							rpPrescribed.setPreguntaId(25);
							rpPrescribed.setOpcionRespuestaId(105);
							rpPrescribed.setRespuestaNumero(me.getRecetado()*1.0);
							rspts.add(rpPrescribed);
							
						// Generico
						} else {
							Respuesta rpMedName = new Respuesta();
							rpMedName.setPreguntaId(23);
							rpMedName.setOpcionRespuestaId(102);
							rpMedName.setRespuestaTexto(me.getNombre());
							rpMedName.setPrescripcionId(me.getId());
							rspts.add(rpMedName);
							
							Respuesta rpMedProductType = new Respuesta();
							rpMedProductType.setPreguntaId(24);
							rpMedProductType.setOpcionRespuestaId(103);
							rspts.add(rpMedProductType);
							
							Respuesta rpPrescribed = new Respuesta();
							rpPrescribed.setPreguntaId(25);
							rpPrescribed.setOpcionRespuestaId(105);
							rpPrescribed.setRespuestaNumero(me.getRecetado()*1.0);
							rspts.add(rpPrescribed);
							
							Respuesta rpRecieved = new Respuesta();
							rpRecieved.setPreguntaId(26);
							rpRecieved.setOpcionRespuestaId(106);
							rpRecieved.setRespuestaNumero(me.getEntregado()*1.0);
							rspts.add(rpRecieved);
						}
					}
					
					for(int x = 0; x < rc.getInsumos().size() ; x++){
						Insumo ins = rc.getInsumos().get(x);
						
						Respuesta rpSuppyName = new Respuesta();
						rpSuppyName.setPreguntaId(23);
						rpSuppyName.setOpcionRespuestaId(102);
						rpSuppyName.setRespuestaTexto(ins.getNombre());
						rpSuppyName.setPrescripcionId(ins.getId());
						rspts.add(rpSuppyName);
							
						Respuesta rpSupplyProductType = new Respuesta();
						rpSupplyProductType.setPreguntaId(24);
						rpSupplyProductType.setOpcionRespuestaId(104);
						rspts.add(rpSupplyProductType);
							
						Respuesta rpPrescribed = new Respuesta();
						rpPrescribed.setPreguntaId(25);
						rpPrescribed.setOpcionRespuestaId(105);
						rpPrescribed.setRespuestaNumero(ins.getRecetado()*1.0);
						rspts.add(rpPrescribed);
					}
				}
			}
			
			return rspts;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
