package gob.sis.simos.ui;

import gob.sis.simos.R;
import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class DialogItinerario extends Dialog implements OnClickListener {

	private Button btnOk;
	
	public DialogItinerario(Context context) {
		super(context);
		this.setContentView(R.layout.dialog_itnrrio);
		
		this.btnOk = (Button)findViewById(R.id.btn_ok);
		this.btnOk.setOnClickListener(this);
	}

	public DialogItinerario(Context context, boolean cancelable,
			OnCancelListener cancelListener) {
		super(context, cancelable, cancelListener);
	}

	public DialogItinerario(Context context, int theme) {
		super(context, theme);
	}

	@Override
	public void onClick(View v) {
		this.dismiss();
	}
	
	

}
