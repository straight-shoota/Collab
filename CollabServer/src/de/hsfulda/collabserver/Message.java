package de.hsfulda.collabserver;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Message {
	//protected Object bean;
	protected String action;
	protected JSONObject content;
	protected String controller;

	protected Message(String action){
		String[] t = action.split("\\.", 2);
		this.controller = t[0];
		this.action = t[1];
		//System.out.println(this.controller + "::" + this.action);
	}
	protected Message(String action, JSONObject o){
		this(action);
		this.content = o;
	}
	protected Message(String action, Object bean){
		this(action, new JSONObject(bean));
		//this.bean = bean;
	}
	protected Message(String action, JSONAble jsonable) throws JSONException{
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
	public String toString(){
		JSONArray array = new JSONArray();
		array.put(getController() + "." + getAction());
		array.put(getContent());
		return array.toString();
	}

	public Object get(String key) {
		try {
			return getContent().get(key);
		} catch (JSONException e) {
		}
		return null;
	}
	
	public static Message.Incoming Incoming(String source) throws JSONException{
		JSONArray array = new JSONArray(source);
		JSONObject content = array.optJSONObject(1);
		System.out.println(content);
		return new Message.Incoming(array.getString(0), content);
	}
	
	public static class Incoming extends Message {
		public Incoming(String s, JSONObject content) throws JSONException{
			super(s, content);
		}
	}
	public static class Outgoing extends Message {
		public Outgoing(String action, Object o){
			super(action, o);
		}
		public Outgoing(String action, JSONObject o){
			super(action, o);
		}
		public Outgoing(String action){
			this(action, null);
		}

		public void set(String key, Object value) {
			try {
				getContent().put(key, value);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
