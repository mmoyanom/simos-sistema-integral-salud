package gob.sis.simos.resources;

import gob.sis.simos.R;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import android.content.Context;

public class AppProperties {

	public static String getProperty(Context ctx, String key){
		InputStream is = ctx.getResources().openRawResource(R.raw.simos);
		Properties properties = new Properties();
		try {
			properties.load(is);
			return properties.getProperty(key);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
