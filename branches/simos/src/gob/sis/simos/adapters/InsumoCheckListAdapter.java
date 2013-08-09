package gob.sis.simos.adapters;

import gob.sis.simos.R;
import gob.sis.simos.entity.Insumo;
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

public class InsumoCheckListAdapter extends ArrayAdapter<Insumo> implements OnCheckedChangeListener {

	private List<Insumo> items;
	
	public InsumoCheckListAdapter(Context context, int textViewResourceId,
			List<Insumo> objects) {
		super(context, textViewResourceId, objects);
		this.items = objects;
	}
	
	public List<Insumo> getItems(){
		return this.items;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;
		ViewHolder holder;
		if (v == null) {
			LayoutInflater vi = (LayoutInflater) this.getContext()
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = vi.inflate(R.layout.adptr_insms_check_list, null);
			holder = new ViewHolder();
			holder.checkBox = (UICheckBox) v.findViewById(R.id.checkBox);
			holder.title = (TextView) v.findViewById(R.id.topTitle);
			holder.description = (TextView) v.findViewById(R.id.bottomDescription);
			v.setTag(holder);
		} else {
			holder = (ViewHolder) v.getTag();
		}
		Insumo i = this.items.get(position);
		if (i != null) {
			holder.checkBox.setIndex(position);
			holder.checkBox.setChecked(i.isChecked());
			holder.checkBox.setOnCheckedChangeListener(this);
			holder.title.setText(i.getNombre());
			holder.description.setText("Recetado : "+i.getRecetado()+", Entregado : "+i.getEntregado());
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
		Insumo i = items.get(view.getIndex());
		if(i != null){
			i.setChecked(isChecked);
		}
		this.notifyDataSetChanged();
	}
	
}
