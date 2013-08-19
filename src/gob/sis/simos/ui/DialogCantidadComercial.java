package gob.sis.simos.ui;

import gob.sis.simos.R;
import android.app.Dialog;
import android.content.Context;
import android.widget.Button;
import android.widget.NumberPicker;

public class DialogCantidadComercial extends Dialog {

	public Button btnOK;
	public Button btnCANCEL;
	public NumberPicker npCantidad;

	public DialogCantidadComercial(Context context) {
		super(context);
		this.setContentView(R.layout.dialog_cntdad_cmrcl);
		
		this.btnOK = (Button)findViewById(R.id.btn_ok);
		this.btnCANCEL = (Button)findViewById(R.id.btn_cancel);
		this.npCantidad = (NumberPicker)findViewById(R.id.numberPicker1);
		this.npCantidad.setMaxValue(100);
		this.npCantidad.setValue(1);
		this.npCantidad.setMinValue(0);
		this.npCantidad.setWrapSelectorWheel(false);

	}
	
	public int getCantidad(){
		return npCantidad.getValue();
	}
	
	public void setCantidad(int value){
		this.npCantidad.setValue(value);
	}
	
	public void reset(){
		this.npCantidad.setValue(1);
	}

}
