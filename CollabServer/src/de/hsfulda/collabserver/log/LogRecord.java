package de.hsfulda.collabserver.log;

import java.io.Serializable;
import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

import de.hsfulda.collabserver.JSONAble;
import de.hsfulda.collabserver.uid.UniqueObject;

public abstract class LogRecord
extends UniqueObject<Integer>  implements Serializable, JSONAble {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Date date;
	private String type;
	
	public LogRecord(String type){
		date = new Date();
		this.type = type;
	}

	public Date getDate(){
		return date;
	}
	public String getType(){
		return type;
	}
	
	@Override
	public JSONObject toJSON() throws JSONException {
		JSONObject o = new JSONObject();
		o.put("uid", getUID());
		o.put("timestamp", getDate().getTime());
		o.put("type", getType());
		return o;
	}
}
