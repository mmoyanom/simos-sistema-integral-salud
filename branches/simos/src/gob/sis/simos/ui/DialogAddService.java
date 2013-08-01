package gob.sis.simos.ui;

import gob.sis.simos.R;
import android.app.Dialog;
import android.content.Context;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class DialogAddService extends Dialog {

	protected RadioGroup _rgServices;
	public Button btnContinuar;
	public Button btnCancelar;
	
	public DialogAddService(Context context) {
		super(context);
		
		this.setContentView(R.layout.dialog_add_service);
		
		this._rgServices = (RadioGroup)findViewById(R.id.rg_services);
		
		this.btnCancelar = (Button) findViewById(R.id.btn_cancel);
		this.btnContinuar = (Button) findViewById(R.id.btn_continue);
		
		String[] items = new String[11];
		items[0] = "Consulta";
		items[1] = "Medicamentos";
		items[2] = "Insumos";
		items[3] = "Examenes auxiliares";
		items[4] = "Procedimientos de apoyo al diagnóstico";
		items[5] = "Traslado de emergencia";
		items[6] = "Alimentación (En caso de traslados por emergencia)";
		items[7] = "Tomografía";
		items[8] = "Procedimientos especiales";
		items[9] = "Resonancia magnética";
		items[10] = "Otros";
		
		for(int i = 0; i < items.length ; i++){
			RadioButton rb = new RadioButton(getContext());
			rb.setText(items[i]);
			this._rgServices.addView(rb);
		}
	}

	
	
	
}
