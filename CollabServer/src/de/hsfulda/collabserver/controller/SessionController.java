package de.hsfulda.collabserver.controller;

import org.json.JSONException;

import de.hsfulda.collabserver.CollabClient;
import de.hsfulda.collabserver.CollabSession;
import de.hsfulda.collabserver.Message;

public class SessionController extends ActionDelegateController {
	//private Map<Object, CollabSession> sessions = new HashMap<Object, CollabSession>();
	
	@Override
	protected void initActions(){
		bind("listUsers", new CollabMessageListener() {
			@Override
			public void doAction(Message message, CollabClient client) throws JSONException {
				sendSessionInfo(client);
			}
		});
		bind("user.joined", new CollabMessageListener() {
			@Override
			public void doAction(Message message, CollabClient client) throws JSONException {
				sendSessionInfo(client.getSession());
			}
		});
		bind("user.left", new CollabMessageListener() {
			@Override
			public void doAction(Message message, CollabClient client) throws JSONException {
				sendSessionInfo(client.getSession());
			}
		});
	}
	
	public void sendSessionInfo(CollabSession session) throws JSONException{
		session.send("session.info", session);
	}
	public void sendSessionInfo(CollabClient client) throws JSONException{
		client.send("session.info", client.getSession());
	}
	
	/*public CollabSession createSession(CollabClient client){
		CollabSession session = new CollabSession();
		//sessions.put(session.get, session);
		
		joinSession(client, session);

		return session;
	}
	public CollabSession joinSession(CollabClient client, String sessionId){
		/*CollabSession session = null;
		try {
			session = sessions.get(UUID.fromString(sessionId));
		}catch(IllegalArgumentException exc){
			exc.printStackTrace();
		}
		if(session == null){
			return null;
		}
		joinSession(client, session);
		return session;
	}
	
	public void joinSession(CollabClient client, CollabSession session){
		if(client.getSession() != null){
			leaveSession(client);
		}
		
		client.setSession(session);
		/*session.addClient(client);
		session.sendMessage("session.newUser");
		session.sendMessage(client.getClientInfo());
	}
	public void leaveSession(CollabClient client){
		/*CollabSession session = client.getSession();
		client.setSession(null);
		session.sendMessage("session.left");
	}*/
}
