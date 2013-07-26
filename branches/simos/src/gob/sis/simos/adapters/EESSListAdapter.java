package gob.sis.simos.adapters;

import gob.sis.simos.R;
import gob.sis.simos.entity.EESS;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class EESSListAdapter extends ArrayAdapter<EESS> {

	LayoutInflater inflater;
	Context context;
	List<EESS> items;

	public EESSListAdapter(Context context, int textViewResourceId,
			List<EESS> items) {
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
			v = vi.inflate(R.layout.adapter_eess_list, null);
			holder = new ViewHolder();
			holder.title = (TextView) v.findViewById(R.id.topTitle);
			holder.description = (TextView) v
					.findViewById(R.id.bottomDescription);
			v.setTag(holder);
		} else
			holder = (ViewHolder) v.getTag();
		EESS eess = this.items.get(position);
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
