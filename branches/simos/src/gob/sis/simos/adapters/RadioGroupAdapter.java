package gob.sis.simos.adapters;

import gob.sis.simos.dto.Answer;

import java.util.List;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class RadioGroupAdapter extends BaseAdapter {

	private List<Answer> items;
	private Context context;
	private LayoutInflater inflater;
	
	public RadioGroupAdapter(List<Answer> items, Context context){
		this.items = items;
		this.context = context;
		this.inflater = LayoutInflater.from(context);
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
	public long getItemId(int index) {
		return index;
	}

	@Override
	public View getView(int index, View view, ViewGroup viewGroup) {
		
		if(view == null){
			
		}
		return null;
	}

}
