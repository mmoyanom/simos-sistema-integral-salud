package gob.sis.simos.ui;

import gob.sis.simos.R;
import android.app.Dialog;
import android.content.Context;
import android.widget.Button;

public class DialogAddToReceta extends Dialog {

	public Button btnContinuar;
	public Button btnCancelar;
	
	public DialogAddToReceta(Context context) {
		super(context);
		
		this.setContentView(R.layout.dialog_add_to_rcta);	
		
		this.btnCancelar = (Button) findViewById(R.id.btn_cancel);
		this.btnContinuar = (Button) findViewById(R.id.btn_continue);
		
	}


}
