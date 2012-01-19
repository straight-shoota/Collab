package de.hsfulda.collabserver.scene;

import javax.vecmath.Point3d;

import org.json.JSONException;
import org.json.JSONObject;

import de.hsfulda.collabserver.controller.Transformation;

public class Translation extends Transformation<Object3D>{
	Point3d source;
	Point3d destination;
	/**
	 * 
	 */
	private static final long serialVersionUID = 2774653183096476135L;

	public Translation(Object3D o) {
		this(o, o.getPosition());
	}
	public Translation(Object3D o, Point3d destination) {
		this(o, o.getPosition(), destination);
	}
	public Translation(Object3D target, Point3d source, Point3d destination) {
		super(target, "translation");
		this.source = new Point3d(source);
		this.destination = new Point3d(destination);
	}
	
	@Override
	public void apply(){
		getTarget().setPosition(getDestination());
	}
	@Override
	public void reset(){
		getTarget().setPosition(getSource());
	}
	public Point3d getSource() {
		return source;
	}

	public Point3d getDestination() {
		return destination;
	}

	@Override
	public JSONObject toJSON() throws JSONException {
		JSONObject o = super.toJSON();
		o.put("source", new JSONObject(getSource()));
		o.put("destination", new JSONObject(getDestination()));
		return o;
	}
}
