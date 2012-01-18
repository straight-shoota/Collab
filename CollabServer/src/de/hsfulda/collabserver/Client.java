package de.hsfulda.collabserver;

import org.json.JSONObject;

import de.hsfulda.collabserver.uid.UniqueObject;


public abstract class Client<C extends Client<C>> extends UniqueObject<Integer>  {
	private String username;
	private Session<C> session = null;
	
	private static int client_counter = 1;
	
	public Client(){
		this.setUsername("Client " + client_counter++);
	}

	public Session<C> getSession() {
		return session;
	}

	public void setSession(Session<C> session) {
		this.session = session;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	public void send(String action, JSONObject data){
		send(new Message(action, data));
	}
	public abstract void send(Message message);
	
	public String toString(){
		return getUsername();
	}
}
