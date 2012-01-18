package de.hsfulda.collabserver.scene;

import javax.vecmath.Point3d;

import org.json.JSONException;
import org.json.JSONObject;

import de.hsfulda.collabserver.JSONAble;
import de.hsfulda.collabserver.uid.UniqueEntity;
import de.hsfulda.collabserver.uid.UniqueEntityProvider;
import de.hsfulda.collabserver.uid.UniqueObject;


public class Object3D extends UniqueObject<Integer> 
implements JSONAble {
	Point3d position = new Point3d();
	
	public Point3d getPosition() {
		return position;
	}

	public void setPosition(Point3d position) {
		this.getPosition().set(position);
	}
	public void setPosition(double x, double y, double z) {
		this.getPosition().set(x, y, z);
	}
	
	@Override
	public JSONObject toJSON() throws JSONException {
		JSONObject o = new JSONObject();
		o.put("position", new JSONObject(getPosition()));
		o.put("uid", getUID());
		
		return o;
	}
}
