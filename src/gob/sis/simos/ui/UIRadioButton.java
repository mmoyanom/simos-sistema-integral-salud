package gob.sis.simos.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RadioButton;

public class UIRadioButton extends RadioButton {

	public String value;
	
	public UIRadioButton(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	public UIRadioButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public UIRadioButton(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	
	
}
