package gob.sis.simos.ui;

import gob.sis.simos.R;
import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;

public class DialogEnterQuantity extends Dialog implements android.view.View.OnClickListener {

	private Button btnOK;
	private Button btnCANCEL;
	
	public DialogEnterQuantity(Context context) {
		super(context);
		this.setContentView(R.layout.dialog_enter_quantity);
		
		this.btnOK = (Button)findViewById(R.id.btn_ok);
		this.btnOK.setOnClickListener(this);
		
		this.btnCANCEL = (Button)findViewById(R.id.btn_cancel);
		this.btnCANCEL.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		
	}

}
