package de.hsfulda.collabserver.scene;

import java.util.HashSet;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import de.hsfulda.collabserver.JSONAble;
import de.hsfulda.collabserver.controller.Transformation;
import de.hsfulda.collabserver.uid.DefaultUniqueEntityProvider;
import de.hsfulda.collabserver.uid.UIDDirectory;

public class Scene
implements JSONAble {
	Set<Object3D> objects = new HashSet<Object3D>();

	private UIDDirectory<Integer, Object3D> objectUidProvider = new DefaultUniqueEntityProvider<Object3D>();
	private UIDDirectory<Integer, Transformation<Object3D>> transformationUidProvider = new DefaultUniqueEntityProvider<Transformation<Object3D>>();
	
	public UIDDirectory<Integer, Object3D> getObjectUIDProvider(){
		return objectUidProvider;
	}
	public UIDDirectory<Integer, Transformation<Object3D>> getTransformationUIDProvider(){
		return transformationUidProvider;
	}
	
	public void registerUID(Object3D r){
		getObjectUIDProvider().getUID(r);
	}
	public void registerUID(Transformation<Object3D> t){
		getTransformationUIDProvider().getUID(t);
	}
	
	public Object3D getObject(Integer uid){
		return getObjectUIDProvider().getEntity(uid);
	}

	public void addCube(){
		Cube cube = new Cube();
		registerUID(cube);
		objects.add(cube);
	}
	public void add(Object3D o){
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
