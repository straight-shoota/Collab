package de.hsfulda.collabserver;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

public class DefaultUniqueEntity implements UniqueEntity  {

	private Object uid;
	
	public DefaultUniqueEntity(){
		generateUID();
	}
	
	protected void generateUID(){
		uid = nextUID(getClass());
	}
	@Override
	public Object getUID(){
		return uid;
	}
	
	
	private static Object nextUID(Class<?> type){
		int uid = 1;
		if(! uidStates.containsKey(type)){
			uidStates.put(type, uid);
		}else{
			uid = uidStates.get(type);
			uid++;
			uidStates.put(type, uid);
		}
		return uid;
	}
	private static Map<Class<?>, Integer> uidStates = new HashMap<Class<?>, Integer>();
}
