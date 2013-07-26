package gob.sis.simos;

import java.util.HashMap;

public class AppSession {

	public static final String ACCOUNT = "current_session";
	private static HashMap<String, Object> map = new HashMap<String, Object>();
	
	public AppSession() {
		
	}
	
	public static void put(String key, Object value){
		map.put(key, value);
	}
	
	public static Object get(String key){
		return map.get(key);
	}
	
}
