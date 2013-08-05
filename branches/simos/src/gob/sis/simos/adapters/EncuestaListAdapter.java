package gob.sis.simos.adapters;

import gob.sis.simos.R;
import java.util.List;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class EncuestaListAdapter extends ArrayAdapter<String> {
	
	LayoutInflater inflater;
	Context context;
	List<String> items;

	public EncuestaListAdapter(Context context, int textViewResourceId,
			List<String> items) {
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
			v.setTag(holder);
		} else
			holder = (ViewHolder) v.getTag();
		String value = this.items.get(position);
		if (value != null) {
			holder.title.setText(value);
		}
		return v;
	}

	public static class ViewHolder {
		public TextView title;
	}
}
