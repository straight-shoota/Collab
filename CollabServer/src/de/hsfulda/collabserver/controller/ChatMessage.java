package de.hsfulda.collabserver.controller;

import java.io.Serializable;

import org.json.JSONException;
import org.json.JSONObject;

import de.hsfulda.collabserver.CollabClient;
import de.hsfulda.collabserver.log.LogRecord;

public class ChatMessage
extends LogRecord implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7836424141223101286L;
	CollabClient sender;
	String text;
	
	public ChatMessage(CollabClient sender, String text){
		super("message");
		this.sender = sender;
		this.text = text;
	}
	public CollabClient getSender(){
		return sender;
	}
	public String getText(){
		return text;
	}
	public void setSender(CollabClient sender) {
		this.sender = sender;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String toString(){
		return "[" + getDate() + " " + getSender() + "] " + getText(); 
	}
	@Override
	public JSONObject toJSON() throws JSONException {
		JSONObject object = super.toJSON();
		object.put("text", getText());
		object.put("sender", getSender().getUID());
		object.put("userName", getSender().getUsername());
		return object;
	}
}
