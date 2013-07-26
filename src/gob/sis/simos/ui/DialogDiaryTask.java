package gob.sis.simos.ui;

import gob.sis.simos.R;
import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class DialogDiaryTask extends Dialog implements OnClickListener {

	private Button btnOk;
	
	public DialogDiaryTask(Context context) {
		super(context);
		this.setContentView(R.layout.dialog_diary_task);
		
		this.btnOk = (Button)findViewById(R.id.btn_ok);
		this.btnOk.setOnClickListener(this);
	}

	public DialogDiaryTask(Context context, boolean cancelable,
			OnCancelListener cancelListener) {
		super(context, cancelable, cancelListener);
	}

	public DialogDiaryTask(Context context, int theme) {
		super(context, theme);
	}

	@Override
	public void onClick(View v) {
		this.dismiss();
	}
	
	

}
