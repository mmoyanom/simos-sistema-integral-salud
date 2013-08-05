package gob.sis.simos.ui;

import gob.sis.simos.dto.Respuesta;

import java.util.ArrayList;
import java.util.List;
import android.content.Context;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

public class UICheckBoxGroup extends LinearLayout {

	private TextView _topLabel;
	private LinearLayout _checkBoxLayout;
	private List<Respuesta> _items;

	public UICheckBoxGroup(Context context) {
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

		this._checkBoxLayout = new LinearLayout(context);
		this._checkBoxLayout.setLayoutParams(new LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		this._checkBoxLayout.setOrientation(LinearLayout.VERTICAL);
		this.addView(this._checkBoxLayout);

	}

	public void setLabelText(CharSequence text) {
		this._topLabel.setText(text);
	}

	public CharSequence getLabelText() {
		return this._topLabel.getText();
	}

	public void setItems(List<Respuesta> items) {
		this._items = items;
		this.update();
	}

	public void update() {
		for (Respuesta answer : _items) {
			CheckBox cb = new CheckBox(getContext());
			cb.setText(answer.getText());
			this.add(cb);
		}
	}

	public void add(CheckBox checkBox) {
		this._checkBoxLayout.addView(checkBox);
	}
	
	public List<Respuesta> getAnswerArray(){
		List<Respuesta> checkedItems = new ArrayList<Respuesta>();
		for(int x = 0 ; x < this._items.size() ; x++){
			CheckBox cb = (CheckBox) this._checkBoxLayout.getChildAt(x);
			if(cb.isChecked()){
				checkedItems.add(this._items.get(x));
			}
		}
		return checkedItems;
	}

}
