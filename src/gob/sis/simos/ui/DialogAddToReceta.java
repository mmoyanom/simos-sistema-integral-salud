package gob.sis.simos.ui;

import gob.sis.simos.R;
import gob.sis.simos.dto.Receta;
import android.app.Dialog;
import android.content.Context;
import android.widget.Button;
import android.widget.RadioGroup;

public class DialogAddToReceta extends Dialog {

	public Button btnContinuar;
	public Button btnCancelar;
	private RadioGroup rgTipoReceta;
	
	public DialogAddToReceta(Context context) {
		super(context);
		
		this.setContentView(R.layout.dialog_add_to_rcta);	
		
		this.rgTipoReceta = (RadioGroup) findViewById(R.id.rg_tipo_receta);
		
		this.btnCancelar = (Button) findViewById(R.id.btn_cancel);
		this.btnContinuar = (Button) findViewById(R.id.btn_continue);
		
	}
	
	public String getTipoReceta(){
		int id = rgTipoReceta.getCheckedRadioButtonId();
		if(id == R.id.rb_estandarizada)
			return Receta.TIPO_ESTANDAR;
		else if (id == R.id.rb_no_estandarizada)
			return Receta.TIPO_NO_ESTANDAR;
		
		return null;
	}


}
