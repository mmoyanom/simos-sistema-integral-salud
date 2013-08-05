package gob.sis.simos.adapters;

import gob.sis.simos.R;
import gob.sis.simos.entity.EstablecimientoSalud;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class EstablecimientoSaludListAdapter extends ArrayAdapter<EstablecimientoSalud> {

	LayoutInflater inflater;
	Context context;
	List<EstablecimientoSalud> items;

	public EstablecimientoSaludListAdapter(Context context, int textViewResourceId,
			List<EstablecimientoSalud> items) {
		super(context, textViewResourceId, items);
		this.context = context;
		this.items = items;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;
		ViewHolder holder;
		if (v == null) {
			LayoutInflater vi = (LayoutInflater) this.getContext()
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = vi.inflate(R.layout.adptr_eess_simple_list, null);
			holder = new ViewHolder();
			holder.title = (TextView) v.findViewById(R.id.topTitle);
			holder.description = (TextView) v
					.findViewById(R.id.bottomDescription);
			v.setTag(holder);
		} else
			holder = (ViewHolder) v.getTag();
		EstablecimientoSalud eess = this.items.get(position);
		if (eess != null) {
			holder.title.setText(eess.getName());
			holder.description.setText(eess.getAddress()!=null?eess.getAddress().isEmpty()?"Sin dirección":eess.getAddress():"Sin dirección");
		}
		return v;
	}

	public static class ViewHolder {
		public TextView title;
		public TextView description;
	}
}
