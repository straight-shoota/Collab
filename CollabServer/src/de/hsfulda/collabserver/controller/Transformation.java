package de.hsfulda.collabserver.controller;

import org.json.JSONException;
import org.json.JSONObject;

import de.hsfulda.collabserver.log.LogRecord;
import de.hsfulda.collabserver.uid.UniqueEntity;

@SuppressWarnings("serial")
public abstract class Transformation<T extends UniqueEntity<?>> extends LogRecord{
	T target;
	
	public Transformation(T target, String type){
		super(type);
		this.target = target;
	}
	public abstract void apply();
	public abstract void reset();

	public T getTarget() {
		return target;
	}

	@Override
	public JSONObject toJSON() throws JSONException {
		JSONObject o = super.toJSON();
		o.put("target", getTarget().getUID());
		return o;
	}
}
