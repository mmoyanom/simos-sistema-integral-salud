package gob.sis.simos.adapters;

import gob.sis.simos.R;
import gob.sis.simos.entity.OpcionRespuesta;
import gob.sis.simos.ui.UICheckBox;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;

public class SimpleCheckListAdapter extends ArrayAdapter<OpcionRespuesta> implements OnCheckedChangeListener {

	private List<OpcionRespuesta> items;
	
	public SimpleCheckListAdapter(Context context, int textViewResourceId,
			List<OpcionRespuesta> objects) {
		super(context, textViewResourceId, objects);
		this.items = objects;
	}
	
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;
		ViewHolder holder;
		if (v == null) {
			LayoutInflater li = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = li.inflate(R.layout.adptr_simple_check_list, null);
			holder = new ViewHolder();
			holder.textView = (TextView)v.findViewById(R.id.textView);
			holder.checkBox = (UICheckBox)v.findViewById(R.id.checkBox);
			v.setTag(holder);
		} else {
			holder = (ViewHolder)v.getTag();
		}
		OpcionRespuesta or = this.getItem(position);
		if(or != null){
			holder.textView.setText(or.getDescripcion());
			holder.checkBox.setIndex(position);
			holder.checkBox.setChecked(or.isChecked());
			holder.checkBox.setOnCheckedChangeListener(this);
			
		}
		return v;
	}
	
	public static class ViewHolder {
		public UICheckBox checkBox;
		public TextView textView;
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		UICheckBox view = (UICheckBox)buttonView;
		OpcionRespuesta or = items.get(view.getIndex());
		if(or != null){
			or.setChecked(isChecked);
		}
		this.notifyDataSetChanged();
	}
}
