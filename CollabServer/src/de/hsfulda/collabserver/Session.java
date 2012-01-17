package de.hsfulda.collabserver;

import java.util.LinkedList;
import java.util.List;

import org.json.JSONObject;

public class Session<C extends Client<C>> extends DefaultUniqueEntity {

	private List<C> clients = new LinkedList<C>();

	public void add(C c){
		clients.add(c);
	}
	public boolean remove(C c){
		return clients.remove(c);
	}
	public Iterable<C> getClients(){
		return clients;
	}

	public void send(String action, JSONObject data){
		send(new Message(action, data));
	}
	public void send(Message message){
		for(C client : getClients()){
			client.send(message);
		}
	}
	
	public String toString(){
		return getUID().toString();
	}
	
}
