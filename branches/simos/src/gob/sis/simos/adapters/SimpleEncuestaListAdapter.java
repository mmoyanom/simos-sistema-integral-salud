package gob.sis.simos.adapters;

import gob.sis.simos.R;
import gob.sis.simos.entity.Encuesta01;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class SimpleEncuestaListAdapter extends ArrayAdapter<Encuesta01> {

	public SimpleEncuestaListAdapter(Context context, int textViewResourceId,
			List<Encuesta01> objects) {
		super(context, textViewResourceId, objects);
	}
	
	public SimpleEncuestaListAdapter(Context context, int textViewResourceId) {
		super(context, textViewResourceId);
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;
		ViewHolder holder;
		if (v == null) {
			LayoutInflater li = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = li.inflate(android.R.layout.simple_list_item_1, null);
			holder = new ViewHolder();
			holder.textView = (TextView)v.findViewById(android.R.id.text1);
			v.setTag(holder);
		} else {
			holder = (ViewHolder)v.getTag();
		}
		Encuesta01 or = this.getItem(position);
		if(or != null){
			holder.textView.setText(or.getFormularioId()+"\n"+or.getNroCuestionario());
		}
		return v;
	}
	
	public static class ViewHolder {
		public TextView textView;
	}
}
