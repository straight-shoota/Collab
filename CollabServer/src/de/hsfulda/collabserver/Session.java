package de.hsfulda.collabserver;

import java.util.LinkedList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import de.hsfulda.collabserver.log.Log;
import de.hsfulda.collabserver.log.LogRecord;
import de.hsfulda.collabserver.uid.UniqueEntity;
import de.hsfulda.collabserver.uid.UniqueEntityProvider;
import de.hsfulda.collabserver.uid.UniqueObject;

public class Session<C extends Client<C>> extends UniqueObject<Integer>  {

	private List<C> clients = new LinkedList<C>();
	private Log log = new Log();

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
	public void send(String action, JSONAble data) throws JSONException{
		send(action, data.toJSON());
	}
	public void send(Message message){
		for(C client : getClients()){
			client.send(message);
		}
	}
	
	public Log getLog(){
		return log;
	}
	public void add(LogRecord record){
		getLog().append(record);
	}
	
	public String toString(){
		return getUID().toString();
	}
}
