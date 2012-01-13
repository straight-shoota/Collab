package de.hsfulda.collabserver;

import org.json.JSONException;
import org.json.JSONObject;

public class JSONEntity extends DefaultUniqueEntity
implements JSONAble {


	@Override
	public JSONObject toJSON() throws JSONException {
		JSONObject o = new JSONObject();
		o.put("uid", getUID());
		return o;
	}
}
