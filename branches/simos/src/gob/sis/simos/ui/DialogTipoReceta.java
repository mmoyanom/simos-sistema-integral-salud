package gob.sis.simos.ui;

import gob.sis.simos.R;
import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;

public class DialogTipoReceta extends Dialog implements android.view.View.OnClickListener{

	public Button btnGenerico;
	public Button btnComercial;
	
	public DialogTipoReceta(Context context, int theme) {
		super(context, theme);
		this.setContentView(R.layout.dialog_tipo_rcta);
		this.btnGenerico = (Button) findViewById(R.id.btn_generico);
		this.btnGenerico.setOnClickListener(this);
		
		this.btnComercial = (Button) findViewById(R.id.btn_comercial);
		this.btnComercial.setOnClickListener(this);
	}

	protected DialogTipoReceta(Context context, boolean cancelable,
			OnCancelListener cancelListener) {
		super(context, cancelable, cancelListener);
		this.setContentView(R.layout.dialog_tipo_rcta);
		this.btnGenerico = (Button) findViewById(R.id.btn_generico);
		this.btnGenerico.setOnClickListener(this);
		
		this.btnComercial = (Button) findViewById(R.id.btn_comercial);
		this.btnComercial.setOnClickListener(this);
	}

	public DialogTipoReceta(Context context) {
		super(context);
		this.setContentView(R.layout.dialog_tipo_rcta);
		this.btnGenerico = (Button) findViewById(R.id.btn_generico);
		this.btnGenerico.setOnClickListener(this);
		
		this.btnComercial = (Button) findViewById(R.id.btn_comercial);
		this.btnComercial.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		
	}
	
	

}
