package de.hsfulda.collabserver;

import org.json.JSONException;
import org.json.JSONObject;
import org.webbitserver.WebSocketConnection;

public class CollabClient extends Client<CollabClient>
implements JSONAble {
	WebSocketConnection connection;
	public CollabClient(WebSocketConnection connection){
		this.connection = connection;
	}
	public CollabSession getSession(){
		return (CollabSession) super.getSession();
	}
	public WebSocketConnection getConnection(){
		return connection;
	}
	
	@Override
	public void send(Message message) {
		getConnection().send(message.toString());
		System.out.println(UniqueEntityProvider.UID(this) + " < " + message);
	}
	
	
	
	public JSONObject toJSON() throws JSONException {
		JSONObject object = new JSONObject();
		object.put("name", getUsername());
		object.put("uid", UniqueEntityProvider.UID(this));
		return object;
	}
}
