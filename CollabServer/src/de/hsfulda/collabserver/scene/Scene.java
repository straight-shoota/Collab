package de.hsfulda.collabserver.scene;

import java.util.HashSet;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import de.hsfulda.collabserver.JSONAble;

public class Scene
implements JSONAble {
	Set<Object3D> objects = new HashSet<Object3D>();
	
	public void addObject(Object3D o){
		objects.add(o);
	}
	public Object3D[] getObjects(){
		return objects.toArray(new Object3D[objects.size()]);
	}
	@Override
	public JSONObject toJSON() throws JSONException {
		JSONObject o = new JSONObject();

		JSONArray objectList = new JSONArray();
		for(Object3D object : getObjects()){
			objectList.put(object.toJSON());
		}
		o.put("objects", objectList);
		
		return o;
	}
}
