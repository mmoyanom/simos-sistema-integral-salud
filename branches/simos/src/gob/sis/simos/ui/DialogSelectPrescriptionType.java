package gob.sis.simos.ui;

import gob.sis.simos.R;
import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;

public class DialogSelectPrescriptionType extends Dialog implements android.view.View.OnClickListener{

	public Button btnGenerico;
	public Button btnComercial;
	
	public DialogSelectPrescriptionType(Context context, int theme) {
		super(context, theme);
		this.setContentView(R.layout.dialog_prescription_type);
		this.btnGenerico = (Button) findViewById(R.id.btn_generico);
		this.btnGenerico.setOnClickListener(this);
		
		this.btnComercial = (Button) findViewById(R.id.btn_comercial);
		this.btnComercial.setOnClickListener(this);
	}

	protected DialogSelectPrescriptionType(Context context, boolean cancelable,
			OnCancelListener cancelListener) {
		super(context, cancelable, cancelListener);
		this.setContentView(R.layout.dialog_prescription_type);
		this.btnGenerico = (Button) findViewById(R.id.btn_generico);
		this.btnGenerico.setOnClickListener(this);
		
		this.btnComercial = (Button) findViewById(R.id.btn_comercial);
		this.btnComercial.setOnClickListener(this);
	}

	public DialogSelectPrescriptionType(Context context) {
		super(context);
		this.setContentView(R.layout.dialog_prescription_type);
		this.btnGenerico = (Button) findViewById(R.id.btn_generico);
		this.btnGenerico.setOnClickListener(this);
		
		this.btnComercial = (Button) findViewById(R.id.btn_comercial);
		this.btnComercial.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		
	}
	
	

}
