package de.hsfulda.collabserver;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.webbitserver.WebSocketConnection;

public class CollabSession extends Session<CollabClient> implements JSONAble {
	public CollabClient add(WebSocketConnection connection){
		CollabClient client = new CollabClient(connection);
		connection.data("client", client);
		client.setSession(this);
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
