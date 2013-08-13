package gob.sis.simos.adapters;

import gob.sis.simos.R;
import gob.sis.simos.dto.Receta;
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

public class RecetaCheckListAdapter extends ArrayAdapter<Receta> implements OnCheckedChangeListener{

	private List<Receta> items;
	
	public RecetaCheckListAdapter(Context context, int textViewResourceId,
			List<Receta> objects) {
		super(context, textViewResourceId, objects);
		this.items = objects;
	}
	
	public List<Receta> getItems(){
		return this.items;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;
		ViewHolder holder;
		if (v == null) {
			LayoutInflater vi = (LayoutInflater) this.getContext()
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = vi.inflate(R.layout.adptr_rcta_check_list, null);
			holder = new ViewHolder();
			holder.checkBox = (UICheckBox) v.findViewById(R.id.checkBox);
			holder.title = (TextView) v.findViewById(R.id.topTitle);
			holder.description = (TextView) v.findViewById(R.id.bottomDescription);
			v.setTag(holder);
		} else {
			holder = (ViewHolder) v.getTag();
		}
		Receta r = this.items.get(position);
		if (r != null) {
			holder.checkBox.setIndex(position);
			holder.checkBox.setChecked(r.isChecked());
			holder.checkBox.setOnCheckedChangeListener(this);
			holder.title.setText(r.getId()+"_"+r.getTipo());
			holder.description.setText("Medicamentos : "+r.getMedicamentos().size()+", Insumos : "+r.getInsumos().size());
		}
		return v;
	}

	public static class ViewHolder {
		public UICheckBox checkBox;
		public TextView title;
		public TextView description;
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		UICheckBox view = (UICheckBox)buttonView;
		Receta r = items.get(view.getIndex());
		if(r != null){
			r.setChecked(isChecked);
		}
		this.notifyDataSetChanged();
	}
	
}
