package gob.sis.simos.ui;

import gob.sis.simos.R;
import android.app.Dialog;
import android.content.Context;
import android.widget.Button;

public class DialogCantidad extends Dialog  {

	public Button btnOK;
	public Button btnCANCEL;
	
	public DialogCantidad(Context context) {
		super(context);
		this.setContentView(R.layout.dialog_cntdad);
		
		this.btnOK = (Button)findViewById(R.id.btn_ok);
		this.btnCANCEL = (Button)findViewById(R.id.btn_cancel);
	}

}
