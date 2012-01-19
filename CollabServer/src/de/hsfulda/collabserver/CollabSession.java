package de.hsfulda.collabserver;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.webbitserver.WebSocketConnection;

import de.hsfulda.collabserver.scene.Scene;
import de.hsfulda.collabserver.uid.DefaultUniqueEntityProvider;
import de.hsfulda.collabserver.uid.UIDDirectory;

public class CollabSession extends Session<CollabClient> implements JSONAble {
	Scene scene;
	CollabWebSockets server;
	private UIDDirectory<Integer, CollabClient> clientUidProvider = new DefaultUniqueEntityProvider<CollabClient>();  
	
	public CollabSession(CollabWebSockets server){		
		this.server = server;
		initScene();
	}
	
	public CollabWebSockets getServer(){
		return server;
	}
	
	public Scene getScene(){
		return scene;
	}
	protected void initScene(){
		scene = new Scene();
		scene.addCube();
	}
	
	public CollabClient add(WebSocketConnection connection){
		CollabClient client = new CollabClient(connection);
		connection.data("client", client);
		client.setSession(this);
		getClientUIDProvider().getUID(client);
		add(client);
		return client;
	}
	
	@Override
	public void send(Message message) {
		String s = message.toString();
		System.out.println("* < " + message);
		for(CollabClient client : getClients()){
			client.getConnection().send(s);
		}
	}
	
	public UIDDirectory<Integer, CollabClient> getClientUIDProvider(){
		return clientUidProvider;
	}
	
	@Override
	public JSONObject toJSON() throws JSONException {
		JSONObject o = new JSONObject();
		o.put("uid", getUID());
		
		JSONArray userlist = new JSONArray();
		for(CollabClient c : getClients()){
			userlist.put(c.toJSON());
		}
		o.put("users", userlist);
		
		return o;
	}
}
