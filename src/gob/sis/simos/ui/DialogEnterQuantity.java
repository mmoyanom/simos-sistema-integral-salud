package gob.sis.simos.ui;

import gob.sis.simos.R;
import android.app.Dialog;
import android.content.Context;
import android.widget.Button;

public class DialogEnterQuantity extends Dialog  {

	public Button btnOK;
	public Button btnCANCEL;
	
	public DialogEnterQuantity(Context context) {
		super(context);
		this.setContentView(R.layout.dialog_enter_quantity);
		
		this.btnOK = (Button)findViewById(R.id.btn_ok);
		this.btnCANCEL = (Button)findViewById(R.id.btn_cancel);
	}

}
