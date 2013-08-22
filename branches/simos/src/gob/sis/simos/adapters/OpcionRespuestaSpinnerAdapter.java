package gob.sis.simos.adapters;

import gob.sis.simos.R;
import gob.sis.simos.entity.OpcionRespuesta;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class OpcionRespuestaSpinnerAdapter extends BaseAdapter {
	
	LayoutInflater inflater;
	Context context;
	List<OpcionRespuesta> items;

	public OpcionRespuestaSpinnerAdapter(Context context, List<OpcionRespuesta> items) {
		super();
		this.context = context;
		this.items = items;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;
		if (v == null) {
			LayoutInflater vi = (LayoutInflater) this.context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = vi.inflate(R.layout.adptr_rspta_simple_list, null);
		}
		TextView title = (TextView) v.findViewById(R.id.txt_description);
		OpcionRespuesta rspta = this.items.get(position);
		if (rspta != null) {
			title.setText(rspta.getDescripcion());
		}
		return v;
	}

	@Override
	public int getCount() {
		return items.size();
	}

	@Override
	public Object getItem(int index) {
		return items.get(index);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

}
