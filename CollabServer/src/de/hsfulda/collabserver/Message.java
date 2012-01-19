package de.hsfulda.collabserver;


import java.util.Date;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Message {
	protected String controller;
	protected String action;
	protected JSONObject content;

	public Message(String action){
		String[] t = action.split("\\.", 2);
		if(t.length == 2){
			this.controller = t[0];
			this.action = t[1];
		}else{
			this.controller = action;
		}
	}
	public Message(String action, JSONObject o){
		this(action);
		this.content = o;
		if(content != null && !content.has("timestamp")){
			try {
				content.put("timestamp", new Date().getTime());
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	protected Message(String action, Object bean){
		this(action, new JSONObject(bean));
	}
	public Message(String action, JSONAble jsonable) throws JSONException{
		this(action, jsonable.toJSON());
	}
	
	public String getController(){
		return controller;
	}

	public String getAction() {
		return action;
	}

	public JSONObject getContent() {
		return content;
	}

	public Object get(String key) {
		try {
			return getContent().get(key);
		} catch (JSONException e) {
		}
		return null;
	}
	public void set(String key, Object value) {
		try {
			getContent().put(key, value);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String getActionString(){
		return getController() + "." + getAction();
	}
	
	public String toString(){
		try {
			return toJSON().toString();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}

	
	public static Message parseMessage(String source) throws JSONException{
		JSONArray array = new JSONArray(source);
		JSONObject content = array.optJSONObject(1);
		return new Message(array.getString(0), content);
	}
	public JSONArray toJSON() throws JSONException {
		JSONArray array = new JSONArray();
		array.put(getActionString());
		array.put(getContent());
		return array;
	}
}
