package gob.sis.simos.ui;

import gob.sis.simos.R;
import android.app.Dialog;
import android.content.Context;
import android.widget.Button;
import android.widget.NumberPicker;

public class DialogCantidadGenerico extends Dialog  {

	public Button btnOK;
	public Button btnCANCEL;
	public NumberPicker npRecetado;
	public NumberPicker npEntregado;
	
	public DialogCantidadGenerico(Context context) {
		super(context);
		this.setContentView(R.layout.dialog_cntdad_gnrco);
		
		this.btnOK = (Button)findViewById(R.id.btn_ok);
		this.btnCANCEL = (Button)findViewById(R.id.btn_cancel);
		this.npRecetado = (NumberPicker)findViewById(R.id.numberPicker1);
		this.npRecetado.setMaxValue(100);
		this.npRecetado.setValue(1);
		this.npRecetado.setMinValue(0);
		this.npRecetado.setWrapSelectorWheel(false);
		
		this.npEntregado = (NumberPicker)findViewById(R.id.numberPicker2);
		this.npEntregado.setMaxValue(100);
		this.npEntregado.setValue(1);
		this.npEntregado.setMinValue(0);
		this.npEntregado.setWrapSelectorWheel(false);
	}
	
	
	public int getCantidadRecetada() {
		return npRecetado.getValue();			
	}
	
	public void setCantidadRecetada(int value){
		this.npRecetado.setValue(value);
	}
	
	public int getCantidadEntregada() {
		return npEntregado.getValue();
	}
	
	public void setCantidadEntregada(int value){
		this.npEntregado.setValue(value);
	}

	public void reset() {
		this.npEntregado.setValue(1);
		this.npRecetado.setValue(1);
	}

}
