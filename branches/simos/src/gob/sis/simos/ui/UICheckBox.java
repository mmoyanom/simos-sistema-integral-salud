package gob.sis.simos.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.CheckBox;

public class UICheckBox extends CheckBox {

	private int index;
	
	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public UICheckBox(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	public UICheckBox(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public UICheckBox(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	

	
}
