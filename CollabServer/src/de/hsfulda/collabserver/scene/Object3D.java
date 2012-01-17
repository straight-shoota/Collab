package de.hsfulda.collabserver.scene;

import javax.vecmath.Vector3d;

import org.json.JSONException;
import org.json.JSONObject;

import de.hsfulda.collabserver.JSONAble;
import de.hsfulda.collabserver.UniqueEntityProvider;


public abstract class Object3D 
implements JSONAble {
	Vector3d position = new Vector3d();
	
	public Vector3d getPosition() {
		return position;
	}

	public void setPosition(Vector3d position) {
		this.getPosition().set(position);
	}
	public void setPosition(double x, double y, double z) {
		this.getPosition().set(x, y, z);
	}

	@Override
	public JSONObject toJSON() throws JSONException {
		JSONObject o = new JSONObject();
		o.put("position", getPosition());
		o.put("uid", UniqueEntityProvider.UID(this));
		
		return o;
	}
}
