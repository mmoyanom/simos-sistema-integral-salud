package gob.sis.simos.adapters;

import gob.sis.simos.R;
import gob.sis.simos.entity.Encuesta01;

import java.text.SimpleDateFormat;
import java.util.List;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class StoredEncuestaListAdapter extends ArrayAdapter<Encuesta01> {

	public StoredEncuestaListAdapter(Context context, int textViewResourceId,
			List<Encuesta01> objects) {
		super(context, textViewResourceId, objects);
	}
	
	public StoredEncuestaListAdapter(Context context, int textViewResourceId) {
		super(context, textViewResourceId);
	}
	
	public StoredEncuestaListAdapter(Context context, int textViewResourceId,
			Encuesta01[] objects) {
		super(context, textViewResourceId, objects);
		// TODO Auto-generated constructor stub
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;
		ViewHolder holder;
		if (v == null) {
			LayoutInflater li = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = li.inflate(R.layout.adptr_strd_encsts, null);
			holder = new ViewHolder();
			holder.text1 = (TextView)v.findViewById(R.id.text1);
			holder.text2 = (TextView)v.findViewById(R.id.text2);
			holder.image = (ImageView)v.findViewById(R.id.image);
			v.setTag(holder);
		} else {
			holder = (ViewHolder)v.getTag();
		}
		Encuesta01 or = this.getItem(position);
		if(or != null){
			holder.text1.setText(or.getNroCuestionario());
			if(or.getSent()==0){
				holder.image.setImageResource(R.drawable.unsent);
				holder.text2.setText(
						String.format("%s - %s \n%s", or.getFormularioId(),
						new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(or.getCreated()),
						"No enviado"));
			} else if(or.getSent()==1){
				holder.image.setImageResource(R.drawable.sent);
				holder.text2.setText(
						String.format("%s - %s \n%s", or.getFormularioId(),
						new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(or.getCreated()),
						"Enviado"));
			}
			
		}
		return v;
	}
	
	public static class ViewHolder {
		public ImageView image;
		public TextView text1;
		public TextView text2;
	}
}
