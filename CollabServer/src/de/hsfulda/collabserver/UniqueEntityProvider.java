package de.hsfulda.collabserver;

import java.util.HashMap;
import java.util.Map;

public class UniqueEntityProvider {
	private Map<Object, Integer> uidClasses = new HashMap<Object, Integer>();
	private Map<Object, Object> uidObjects = new HashMap<Object, Object>();
	
	public Object getNextUID(Object clazz){
		int uid = 1;
		if(! uidClasses.containsKey(clazz)){
			uidClasses.put(clazz, uid);
		}else{
			uid = uidClasses.get(clazz);
			uid++;
			uidClasses.put(clazz, uid);
		}
		return uid;
	}
	
	public Object getUID(Object o){
		if(uidObjects.containsKey(o)){
			return uidObjects.get(o);
		}
		Object uid = getNextUID(o.getClass());
		uidObjects.put(o, uid);
		return uid;
	}
	
	private static UniqueEntityProvider _default = null;
	public static UniqueEntityProvider getDefault(){
		if(_default == null){
			_default = new UniqueEntityProvider();
		}
		return _default;
	}
	public static Object NextUID(Object type){
		return getDefault().getNextUID(type);
	}
	public static Object UID(Object type){
		return getDefault().getUID(type);
	}
}
