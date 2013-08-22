package gob.sis.simos.ui;

import gob.sis.simos.entity.OpcionRespuesta;

import java.util.List;
import android.content.Context;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

public class UIRadioGroupQuestion extends LinearLayout {

	private TextView _topLabel;
	private RadioGroup _radioGroup;
	private List<OpcionRespuesta> _items;
	
	public UIRadioGroupQuestion(Context context) {
		super(context);
		this.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT));
		this.setOrientation(LinearLayout.VERTICAL);

		this._topLabel = new TextView(context);
		this._topLabel.setLayoutParams(new LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		this._topLabel.setTextIsSelectable(false);
		this._topLabel.setTextAppearance(context,
				android.R.style.TextAppearance_Large);
		this.addView(this._topLabel);

		this._radioGroup = new RadioGroup(context);
		this._radioGroup.setLayoutParams(new LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		this._radioGroup.setOrientation(RadioGroup.VERTICAL);
		this.addView(this._radioGroup);
		
	}

	public UIRadioGroupQuestion(Context context, String labelText) {
		super(context);
		this.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT));
		this.setOrientation(LinearLayout.VERTICAL);

		this._topLabel = new TextView(context);
		this._topLabel.setLayoutParams(new LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		this._topLabel.setTextIsSelectable(false);
		this._topLabel.setTextAppearance(context,
				android.R.style.TextAppearance_Large);
		this.addView(this._topLabel);

		this._radioGroup = new RadioGroup(context);
		this._radioGroup.setLayoutParams(new LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		this._radioGroup.setOrientation(RadioGroup.VERTICAL);
		this.addView(this._radioGroup);
		
		this.setLabelText(labelText);
	}

	public void setLabelText(CharSequence text) {
		this._topLabel.setText(text);
	}

	public CharSequence getLabelText() {
		return this._topLabel.getText();
	}

	public void add(RadioButton radioButton) {
		this._radioGroup.addView(radioButton);
	}

	public void setItems(List<OpcionRespuesta> items) {
		this._items = items;
		this.update();
	}

	public OpcionRespuesta getAnswer() {
		RadioButton rb = (RadioButton) this._radioGroup
				.findViewById(this._radioGroup.getCheckedRadioButtonId());
		int index = this._radioGroup.indexOfChild(rb);
		return _items.get(index);
	}

	public void setOnCheckedChangeListener(OnCheckedChangeListener listener) {
		this._radioGroup.setOnCheckedChangeListener(listener);
	}

	public void update() {
		for (OpcionRespuesta answer : _items) {
			RadioButton b = new RadioButton(getContext());
			//b.setText(answer.getId());
			this.add(b);
		}
	}

}
