package gob.sis.simos.adapters;

import gob.sis.simos.R;
import gob.sis.simos.dto.OpcionMenu;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class MenuPrincipalListAdapter extends ArrayAdapter<OpcionMenu> {

	LayoutInflater inflater;
	Context context;
	ArrayList<OpcionMenu> items;
	
	public MenuPrincipalListAdapter(Context context, int textViewResourceId,ArrayList<OpcionMenu> items) {
		super(context, textViewResourceId, items);
		this.context = context;
		this.items = items;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;
		ViewHolder holder;
		if(v == null){
			LayoutInflater vi = (LayoutInflater)this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = vi.inflate(R.layout.adptr_main_menu_simple_list, null);
			holder = new ViewHolder();
			holder.title = (TextView)v.findViewById(R.id.topTitle);
			holder.description = (TextView)v.findViewById(R.id.bottomDescription);
			v.setTag(holder);
		}else
			holder = (ViewHolder)v.getTag();
		OpcionMenu menuOption = this.items.get(position);
		if(menuOption != null){
			holder.title.setText(menuOption.getTitle());
			holder.description.setText(menuOption.getDescription());
		}
		return v;
	}
	
	public static class ViewHolder{
		public TextView title;
		public TextView description;
	}

}
