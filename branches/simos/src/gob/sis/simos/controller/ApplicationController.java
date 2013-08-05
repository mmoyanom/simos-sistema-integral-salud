package gob.sis.simos.controller;

import gob.sis.simos.AppSession;
import gob.sis.simos.R;
import gob.sis.simos.dto.OpcionMenu;

import java.util.ArrayList;

import android.content.Context;
import android.content.res.Resources;

public class ApplicationController {

	
	private Context _context;
	
	public void setContext(Context context){
		this._context = context;
	}
	
	public boolean isActiveSession(){
		if(AppSession.get(AppSession.ACCOUNT) == null){
			return false;
		}
		return true;
	}
	
	public ArrayList<OpcionMenu> getMainMenuItems(){
		
		ArrayList<OpcionMenu> items = new ArrayList<OpcionMenu>();
		
		Resources res = this._context.getResources();
		String[] title_array = res.getStringArray(R.array.main_menu_titles);
		String[] desc_array = res.getStringArray(R.array.main_menu_desc);
		String[] values_array = res.getStringArray(R.array.main_menu_values);
		for(int i=0 ; i < title_array.length ; i++){
			OpcionMenu op = new OpcionMenu();
			op.setId(i);
			op.setTitle(title_array[i]);
			op.setDescription(desc_array[i]);
			op.setValue(values_array[i]);
			items.add(op);
		}
		return items;
	}
	
	
}
