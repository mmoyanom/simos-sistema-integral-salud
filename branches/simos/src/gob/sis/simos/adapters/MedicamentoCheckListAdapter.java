package gob.sis.simos.adapters;

import gob.sis.simos.R;
import gob.sis.simos.entity.Medicamento;
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

public class MedicamentoCheckListAdapter extends ArrayAdapter<Medicamento> implements OnCheckedChangeListener{

	private List<Medicamento> items;
	
	public MedicamentoCheckListAdapter(Context context, int textViewResourceId,
			List<Medicamento> objects) {
		super(context, textViewResourceId, objects);
		this.items = objects;
	}
	
	public List<Medicamento> getItems(){
		return this.items;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;
		ViewHolder holder;
		if (v == null) {
			LayoutInflater vi = (LayoutInflater) this.getContext()
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = vi.inflate(R.layout.adptr_medcmnto_check_list, null);
			holder = new ViewHolder();
			holder.checkBox = (UICheckBox) v.findViewById(R.id.checkBox);
			holder.title = (TextView) v.findViewById(R.id.topTitle);
			holder.description = (TextView) v.findViewById(R.id.bottomDescription);
			v.setTag(holder);
		} else {
			holder = (ViewHolder) v.getTag();
		}
		Medicamento m = this.items.get(position);
		if (m != null) {
			holder.checkBox.setIndex(position);
			holder.checkBox.setChecked(m.isChecked());
			holder.checkBox.setOnCheckedChangeListener(this);
			holder.title.setText(m.getNombre());
			if(m.getId().equals(Medicamento.COMERCIAL)){
				holder.description.setText("Cantidad : "+m.getRecetado());
			} else {
				holder.description.setText("Recetado : "+m.getRecetado()+", Entregado : "+m.getEntregado());
			}
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
		Medicamento m = items.get(view.getIndex());
		if(m != null){
			m.setChecked(isChecked);
		}
		this.notifyDataSetChanged();
	}
	
}
