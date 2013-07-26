package gob.sis.simos.ui;

import android.content.Context;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class UITextQuestion extends LinearLayout {

	private TextView _topLabel;
	private EditText _textInput;
	
	public UITextQuestion(Context context) {
		super(context);
		
		this.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT));
		this.setOrientation(LinearLayout.VERTICAL);
		
		this._topLabel = new TextView(context);
		this._topLabel.setLayoutParams(new LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		this._topLabel.setTextIsSelectable(false);
		this._topLabel.setTextAppearance(context,
				android.R.style.TextAppearance_Large);
		this.addView(this._topLabel);
		
		this._textInput = new EditText(getContext());
		this._textInput.setLayoutParams(new LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		this.addView(this._textInput);
	}
	
	public void setLabelText(CharSequence text){
		this._topLabel.setText(text);
	}
	
	public CharSequence getLabelText(){
		return this._topLabel.getText();
	}

}
