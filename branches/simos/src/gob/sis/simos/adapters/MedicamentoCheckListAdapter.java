package gob.sis.simos.adapters;

import gob.sis.simos.R;
import gob.sis.simos.entity.Medicamento;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class MedicamentoCheckListAdapter extends ArrayAdapter<Medicamento>{

	private List<Medicamento> items;
	
	public MedicamentoCheckListAdapter(Context context, int textViewResourceId,
			List<Medicamento> objects) {
		super(context, textViewResourceId, objects);
		this.items = objects;
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
			holder.title = (TextView) v.findViewById(R.id.topTitle);
			v.setTag(holder);
		} else
			holder = (ViewHolder) v.getTag();
		Medicamento m = this.items.get(position);
		if (m != null) {
			holder.title.setText(m.getName());
		}
		return v;
	}

	public static class ViewHolder {
		public TextView title;
	}
	
}
