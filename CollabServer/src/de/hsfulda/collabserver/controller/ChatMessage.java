package de.hsfulda.collabserver.controller;

import java.io.Serializable;
import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

import de.hsfulda.collabserver.CollabClient;
import de.hsfulda.collabserver.UniqueEntityProvider;


public class ChatMessage implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7836424141223101285L;
	CollabClient sender;
	String text;
	Date date;
	
	public ChatMessage(CollabClient sender, String text){
		this.sender = sender;
		this.text = text;
		this.date = new Date();
	}
	public CollabClient getSender(){
		return sender;
	}
	public String getText(){
		return text;
	}
	public Date getDate(){
		return date;
	}
	public void setSender(CollabClient sender) {
		this.sender = sender;
	}
	public void setText(String text) {
		this.text = text;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String toString(){
		return "[" + getDate() + " " + getSender() + "] " + getText(); 
	}
	public JSONObject toJSON() throws JSONException {
		JSONObject object = new JSONObject();
		object.put("text", getText());
		object.put("sender", UniqueEntityProvider.UID(getSender()));
		object.put("date", getDate());
		object.put("timestamp", getDate().getTime());
		return object;
	}
}
