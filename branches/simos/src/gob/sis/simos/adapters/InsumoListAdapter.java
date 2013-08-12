package gob.sis.simos.adapters;

import gob.sis.simos.R;
import gob.sis.simos.entity.Insumo;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class InsumoListAdapter extends ArrayAdapter<Insumo> {

	private List<Insumo> items;
	
	public InsumoListAdapter(Context context, int textViewResourceId,
			List<Insumo> objects) {
		super(context, textViewResourceId, objects);
		this.items = objects;
	}
	
	public List<Insumo> getItems() {
		return items;
	}

	public void setItems(List<Insumo> items) {
		this.items = items;
	}



	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;
		ViewHolder holder;
		if (v == null) {
			LayoutInflater vi = (LayoutInflater) this.getContext()
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = vi.inflate(R.layout.adptr_insms_medcmnto_simple_list, null);
			holder = new ViewHolder();
			holder.title = (TextView) v.findViewById(R.id.text);
			v.setTag(holder);
		} else
			holder = (ViewHolder) v.getTag();
		Insumo m = this.items.get(position);
		if (m != null) {
			holder.title.setText(m.getNombre());
		}
		return v;
	}

	public static class ViewHolder {
		public TextView title;
	}
	
	
}
