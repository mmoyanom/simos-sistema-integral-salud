package gob.sis.simos.soap;

import gob.sis.simos.controller.Result;
import gob.sis.simos.dto.EncuestaSenderObject;
import gob.sis.simos.dto.Receta;
import gob.sis.simos.entity.Encuesta01;
import gob.sis.simos.entity.Insumo;
import gob.sis.simos.entity.Medicamento;
import gob.sis.simos.entity.Respuesta;
import gob.sis.simos.entity.VerificacionPago;
import gob.sis.simos.resources.AppProperties;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

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

	public Result sendEncuesta(Encuesta01 encuesta) throws IOException, XmlPullParserException {
		
		EncuestaSenderObject obj = new EncuestaSenderObject();
		obj.setEncuesta(encuesta);
		
		List<Respuesta> rspts = this.getRespuestas(encuesta);
		obj.setRespuestas(rspts);
		
		String data = gson.toJson(obj);
		
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
		
		HttpTransportSE httpTransport = new HttpTransportSE("http://192.168.1.12/simosws/ApplicationService.asmx");
		httpTransport.call(soap_action, envelope);
		SoapObject response = (SoapObject)envelope.getResponse();
		String strResponse = response.getPropertyAsString(0);
		if(strResponse != null){
			Type type = new TypeToken<Result>(){}.getType();
			Result result = gson.fromJson(strResponse, type);
			return result;
		}
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
					rpTipoReceta.setPreguntaParentId(21);
					rpTipoReceta.setOpcionRespuestaId(rc.getTipoRecetaId());
					rspts.add(rpTipoReceta);
					
					for(int x = 0; x < rc.getMedicamentos().size() ; x++){
						Medicamento me = rc.getMedicamentos().get(x);
						// Comercial
						if(me.getNombre().equals(Medicamento.COMERCIAL)){
							Respuesta rpMedName = new Respuesta();
							rpMedName.setPreguntaId(23);
							rpMedName.setPreguntaParentId(21);
							rpMedName.setOpcionRespuestaId(102);
							rpMedName.setRespuestaTexto(me.getNombre());
							rpMedName.setPrescripcionId("9999999");
							rspts.add(rpMedName);
							
							Respuesta rpMedProductType = new Respuesta();
							rpMedProductType.setPreguntaId(24);
							rpMedProductType.setPreguntaParentId(23);
							rpMedProductType.setOpcionRespuestaId(109);
							rspts.add(rpMedProductType);
							
							Respuesta rpPrescribed = new Respuesta();
							rpPrescribed.setPreguntaId(25);
							rpPrescribed.setPreguntaParentId(23);
							rpPrescribed.setOpcionRespuestaId(105);
							rpPrescribed.setRespuestaNumero(me.getRecetado()*1.0);
							rspts.add(rpPrescribed);
							
						// Generico
						} else {
							Respuesta rpMedName = new Respuesta();
							rpMedName.setPreguntaId(23);
							rpMedName.setPreguntaParentId(21);
							rpMedName.setOpcionRespuestaId(102);
							rpMedName.setRespuestaTexto(me.getNombre());
							rpMedName.setPrescripcionId(me.getId());
							rspts.add(rpMedName);
							
							Respuesta rpMedProductType = new Respuesta();
							rpMedProductType.setPreguntaId(24);
							rpMedProductType.setPreguntaParentId(23);
							rpMedProductType.setOpcionRespuestaId(103);
							rspts.add(rpMedProductType);
							
							Respuesta rpPrescribed = new Respuesta();
							rpPrescribed.setPreguntaId(25);
							rpPrescribed.setPreguntaParentId(23);
							rpPrescribed.setOpcionRespuestaId(105);
							rpPrescribed.setRespuestaNumero(me.getRecetado()*1.0);
							rspts.add(rpPrescribed);
							
							Respuesta rpRecieved = new Respuesta();
							rpRecieved.setPreguntaId(26);
							rpRecieved.setPreguntaParentId(23);
							rpRecieved.setOpcionRespuestaId(106);
							rpRecieved.setRespuestaNumero(me.getEntregado()*1.0);
							rspts.add(rpRecieved);
						}
					}
					
					for(int x = 0; x < rc.getInsumos().size() ; x++){
						Insumo ins = rc.getInsumos().get(x);
						
						Respuesta rpSuppyName = new Respuesta();
						rpSuppyName.setPreguntaId(23);
						rpSuppyName.setPreguntaParentId(21);
						rpSuppyName.setOpcionRespuestaId(102);
						rpSuppyName.setRespuestaTexto(ins.getNombre());
						rpSuppyName.setPrescripcionId(ins.getId());
						rspts.add(rpSuppyName);
							
						Respuesta rpSupplyProductType = new Respuesta();
						rpSupplyProductType.setPreguntaId(24);
						rpSupplyProductType.setPreguntaParentId(23);
						rpSupplyProductType.setOpcionRespuestaId(104);
						rspts.add(rpSupplyProductType);
							
						Respuesta rpPrescribed = new Respuesta();
						rpPrescribed.setPreguntaId(25);
						rpPrescribed.setPreguntaParentId(23);
						rpPrescribed.setOpcionRespuestaId(105);
						rpPrescribed.setRespuestaNumero(ins.getRecetado()*1.0);
						rspts.add(rpPrescribed);
						
						Respuesta rpRecieved = new Respuesta();
						rpRecieved.setPreguntaId(26);
						rpRecieved.setPreguntaParentId(23);
						rpRecieved.setOpcionRespuestaId(106);
						rpRecieved.setRespuestaNumero(ins.getEntregado()*1.0);
						rspts.add(rpRecieved);
					}
				}
			}
			
			List<Respuesta> child = new ArrayList<Respuesta>();
			Iterator<Respuesta> it = rspts.iterator();
			
			while(it.hasNext()){
				Respuesta r = it.next();
				if(r.getPreguntaParentId() != null){
					if(r.getPreguntaParentId().equals(7)){
						child.add(r);
						it.remove();
					}
				}
			}
			
			for(int z = 0; z < rspts.size() ; z++){
				Respuesta r = rspts.get(z);
				if(r.getPreguntaId().equals(7)){
					r.setChild(child);
				}
			}
			if(encuesta.getRecetas().size() > 0){
				
				it = rspts.iterator();
				child = new ArrayList<Respuesta>();
				Respuesta rsp21 = null;
				while(it.hasNext()){
					Respuesta r = it.next();
					if(r.getPreguntaId().equals(21)){
						rsp21 = r;
					}
					if(r.getPreguntaParentId() != null){
						if(r.getPreguntaParentId().equals(21)){
							child.add(r);
							it.remove();
						}
					}
				}
				rsp21.setChild(child);
				
				it = rspts.iterator();
				child = new ArrayList<Respuesta>();
				while(it.hasNext()){
					Respuesta r = it.next();
					if(r.getPreguntaParentId() != null){
						if(r.getPreguntaParentId().equals(23)){
							child.add(r);
							it.remove();
						}
					}
				}
				for(int z = 0; z < rsp21.getChild().size() ; z++){
					Respuesta r = rsp21.getChild().get(z);
					if(r.getPreguntaId().equals(23)){
						r.setChild(child);
						break;
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
