package gob.sis.simos.adapters;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class TicketListAdapter extends ArrayAdapter<String> {

	public TicketListAdapter(Context context, int textViewResourceId, List<String> objects) {
		super(context, textViewResourceId, objects);
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
		String resp = this.getItem(position);
		if(resp != null){
			holder.textView.setText(resp);
		}
		return v;
	}
	
	public static class ViewHolder {
		public TextView textView;
	}
	

}
