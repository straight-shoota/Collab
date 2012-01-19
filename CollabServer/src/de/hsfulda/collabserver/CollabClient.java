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

	public void send(String action, JSONObject data){
		send(new Message(action, data));
	}
	public void send(String action, JSONAble data) throws JSONException{
		send(action, data.toJSON());
	}
	@Override
	public void send(Message message) {
		try {
			getConnection().send(message.toJSON().toString());
			System.out.println(getUID() + " < " + message);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public JSONObject toJSON() throws JSONException {
		JSONObject object = new JSONObject();
		object.put("name", getUsername());
		object.put("uid", getUID());
		return object;
	}

}
