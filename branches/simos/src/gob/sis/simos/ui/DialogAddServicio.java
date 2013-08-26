package gob.sis.simos.ui;

import gob.sis.simos.R;
import gob.sis.simos.controller.VerificacionPagoController;
import gob.sis.simos.entity.OpcionRespuesta;
import gob.sis.simos.entity.Respuesta;

import java.util.Iterator;
import java.util.List;

import android.app.Dialog;
import android.content.Context;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class DialogAddServicio extends Dialog implements OnCheckedChangeListener {

	private RadioGroup _rgServices;
	private RadioGroup _rgRealizoPago;
	public Button btnContinuar;
	public Button btnCancelar;
	public VerificacionPagoController controller;
	
	public DialogAddServicio(Context context) {
		super(context);
		
		this.setContentView(R.layout.dialog_add_to_srvcs);
		
		this._rgServices = (RadioGroup)findViewById(R.id.rg_services);
		this._rgServices.setOnCheckedChangeListener(this);
		
		this._rgRealizoPago = (RadioGroup)findViewById(R.id.rg_payment_made);
		this._rgRealizoPago.setOnCheckedChangeListener(this);
		
		this.btnCancelar = (Button) findViewById(R.id.btn_cancel);
		this.btnContinuar = (Button) findViewById(R.id.btn_continue);
		this.btnContinuar.setEnabled(false);

	}
	
	public Respuesta getRespuestaService(){
		int id = this._rgServices.getCheckedRadioButtonId();
		if(id == -1){
			UIRadioButton rb = (UIRadioButton)findViewById(id);
			Respuesta rsp7 = new Respuesta();
			rsp7.setPreguntaId(rb.getPreguntaId());
			rsp7.setOpcionRespuestaId(rb.getOpcionRespuestaId());
			return rsp7;
		}
		return null;
	}
	
	public Respuesta getRespuestaRealizoPago(){
		int id = this._rgRealizoPago.getCheckedRadioButtonId();
		if(id == -1){
			UIRadioButton rb = (UIRadioButton)findViewById(id);
			Respuesta rsp7 = new Respuesta();
			rsp7.setPreguntaId(rb.getPreguntaId());
			rsp7.setOpcionRespuestaId(rb.getOpcionRespuestaId());
			return rsp7;
		}
		return null;
	}
	
	public String getSelectedServiceName(){
		int id = this._rgServices.getCheckedRadioButtonId();
		RadioButton rb = (RadioButton)this.findViewById(id);
		if(rb != null)
			return rb.getText().toString();
		return null;
	}
	
	public boolean realizoPago(){
		int id = this._rgRealizoPago.getCheckedRadioButtonId();
		if(id == R.id.rb_yes)
			return true;
		else
			return false;
	}
	
	public void setItems(List<OpcionRespuesta> items){
		this._rgServices.removeAllViews();
		Iterator<OpcionRespuesta> it = items.iterator();
		while(it.hasNext()){
			OpcionRespuesta r = it.next();
			
			UIRadioButton rb = new UIRadioButton(getContext());
			rb.setPreguntaId(r.getPreguntaId());
			rb.setOpcionRespuestaId(r.getOpcionRespuestaId());
			rb.setText(r.getDescripcion());
			
			this._rgServices.addView(rb);
		}
	}
	
	public void clear(){
		this._rgRealizoPago.clearCheck();
		this._rgServices.clearCheck();
		this.btnContinuar.setEnabled(false);
	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		
		if(this._rgServices.getCheckedRadioButtonId() != -1 && this._rgRealizoPago.getCheckedRadioButtonId() != -1){
			this.btnContinuar.setEnabled(true);
		}
	}

	
	
	
}
