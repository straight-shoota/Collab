package de.hsfulda.collabserver.scene;

import javax.vecmath.Point3d;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import de.hsfulda.collabserver.log.LogRecord;

public class Transformation extends LogRecord{
	Object3D target;
	Point3d source;
	Point3d destination;
	/**
	 * 
	 */
	private static final long serialVersionUID = 2774653183096476135L;

	public Transformation(Object3D o) {
		this(o, o.getPosition());
	}
	public Transformation(Object3D o, Point3d destination) {
		this(o, o.getPosition(), destination);
	}
	public Transformation(Object3D o, Point3d source, Point3d destination) {
		super("transformation");
		
		this.target = o;
		this.source = new Point3d(source);
		this.destination = new Point3d(destination);
	}
	
	public void apply(){
		getTarget().setPosition(getDestination());
	}
	public void reset(){
		getTarget().setPosition(getSource());
	}

	public Object3D getTarget() {
		return target;
	}

	public Point3d getSource() {
		return source;
	}

	public Point3d getDestination() {
		return destination;
	}

	@Override
	public JSONObject toJSON() throws JSONException {
		JSONObject o = new JSONObject();
		o.put("target", getTarget().getUID());
		o.put("uid", getUID());
		o.put("source", new JSONObject(getSource()));
		o.put("destination", new JSONObject(getDestination()));
		o.put("timestamp", getDate().getTime());
		
		return o;
	}

}
