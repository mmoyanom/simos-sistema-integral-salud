package gob.sis.simos.adapters;

import gob.sis.simos.R;
import gob.sis.simos.entity.Asignacion;

import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class AsignacionListAdapter extends ArrayAdapter<Asignacion> {

	LayoutInflater inflater;
	Context context;
	int mornign;
	int afternoon;
	
	public AsignacionListAdapter(Context context, int textViewResourceId,
			List<Asignacion> items) {
		super(context, textViewResourceId, items);
		this.context = context;
		mornign = Color.parseColor("#81DAF5");
		afternoon = Color.parseColor("#F5DA81");
	}
	
	public AsignacionListAdapter(Context context, int textViewResourceId) {
		super(context, textViewResourceId);
		this.context = context;
		mornign = Color.parseColor("#81DAF5");
		afternoon = Color.parseColor("#F5DA81");
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
			holder.description = (TextView) v.findViewById(R.id.bottomDescription);
			holder.indicator = (LinearLayout)v.findViewById(R.id.indicator);
			holder.label = (TextView)v.findViewById(R.id.label);
			v.setTag(holder);
			
		} else
			holder = (ViewHolder) v.getTag();
		Asignacion eess = getItem(position);
		if (eess != null) {
			holder.title.setText(eess.getEstablecimientoName());
			if(eess.getEstablecimientoDesc()!=null){
				holder.description.setText(eess.getEstablecimientoDesc());
			} else {
				holder.description.setText("Sin descripcion.");
			}
			if(eess.getTurnoDescripcion() != null){
				if(eess.getTurnoDescripcion().equals("Ma–ana")){
					holder.indicator.setBackgroundColor(mornign);
					holder.label.setText("M");
				} else if(eess.getTurnoDescripcion().equals("Tarde")){
					holder.indicator.setBackgroundColor(afternoon);
					holder.label.setText("T");
				}
			}
		}
		return v;
	}

	public static class ViewHolder {
		public View indicator;
		public TextView title;
		public TextView description;
		public TextView label;
	}
}
