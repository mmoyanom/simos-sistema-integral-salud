package gob.sis.simos.adapters;

import gob.sis.simos.R;
import gob.sis.simos.dto.OpcionMenu;
import java.util.List;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ImageItemListAdapter extends ArrayAdapter<OpcionMenu>{

	public ImageItemListAdapter(Context context, int textViewResourceId,
			List<OpcionMenu> objects) {
		super(context, textViewResourceId, objects);
	}

	public ImageItemListAdapter(Context context, int resource) {
		super(context, resource);
	}
	

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;
		ViewHolder holder;
		if (v == null) {
			LayoutInflater li = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = li.inflate(R.layout.simple_list_item_image, null);
			holder = new ViewHolder();
			holder.imageView1 = (ImageView)v.findViewById(R.id.image);
			holder.textView1 = (TextView)v.findViewById(R.id.text1);
			holder.textView2 = (TextView)v.findViewById(R.id.text2);
			v.setTag(holder);
		} else {
			holder = (ViewHolder)v.getTag();
		}
		OpcionMenu or = this.getItem(position);
		if(or != null){
			holder.imageView1.setImageResource(or.getId());
			holder.textView1.setText(or.getTitle());
			holder.textView2.setText(or.getDescription());
		}
		return v;
	}
	
	public static class ViewHolder {
		public ImageView imageView1;
		public TextView textView1;
		public TextView textView2;
	}

}
