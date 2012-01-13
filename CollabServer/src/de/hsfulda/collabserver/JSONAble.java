package de.hsfulda.collabserver;

import org.json.JSONException;
import org.json.JSONObject;

public interface JSONAble {
	public JSONObject toJSON() throws JSONException;
}
