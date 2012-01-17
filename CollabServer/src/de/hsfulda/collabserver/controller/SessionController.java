package de.hsfulda.collabserver.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.json.JSONException;

import de.hsfulda.collabserver.Client;
import de.hsfulda.collabserver.CollabClient;
import de.hsfulda.collabserver.CollabSession;
import de.hsfulda.collabserver.Message;

public class SessionController extends ActionDelegateController {
	private Map<Object, CollabSession> sessions = new HashMap<Object, CollabSession>();
	
	@Override
	protected void initActions(){
		bind("listUsers", new CollabMessageListener() {
			@Override
			public void action(Message message, CollabClient client) throws JSONException {
				sendSessionInfo(client);
			}
		});
		bind("user.joined", new CollabMessageListener() {
			@Override
			public void action(Message message, CollabClient client) throws JSONException {
				sendSessionInfo(client.getSession());
			}
		});
		bind("user.left", new CollabMessageListener() {
			@Override
			public void action(Message message, CollabClient client) throws JSONException {
				sendSessionInfo(client.getSession());
			}
		});
	}
	
	public void sendSessionInfo(CollabSession session) throws JSONException{
		session.send(new Message("session.info", session.toJSON()));
	}
	public void sendSessionInfo(CollabClient client) throws JSONException{
		client.send(new Message("session.info", client.getSession().toJSON()));
	}
		/*
	public JSONObject getSessionInfo(){
		JSONObject info = null;
		try {
			info = new JSONObject();
			info.put("sessionID", getSessionId());
			JSONArray clients = new JSONArray();
			for(Client c : getClients()){
				clients.put(c.getClientInfo());
			}
			info.put("clients", clients);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return info;
	}
	
	if(command.length == 1){
			throw new InvalidControllerActionException(command);
		}
		
		if(command[1].equals("create")){
			CollabSession session = createSession(client);
			//client.sendMessage("+session.create");
			//client.sendMessage(session.getSessionInfo());
		}else if(command[1].equals("join")){
			/*JSONObject object = client.readJSONObject();
			if(object != null){
				try {
					String sessionId = object.getString("sessionId");
					CollabSession session = joinSession(client, sessionId);
				}catch(JSONException e){
					e.printStackTrace();
				}
			}
		}else if(command[1].equals("list")){
			/*JSONArray array = new JSONArray();
			for(CollabSession session : sessions.values()){
				array.put(session.getSessionInfo());
			}
			client.sendMessage(array);
		}else if(command[1].equals("info")){
			/*CollabSession session = client.getSession();
			if(session == null){
				client.sendMessage("session.info:-session is empty");
				return;
			}
			JSONObject info = session.getSessionInfo();
			client.sendMessage(info);
		}else if(command[1].equals("message")){
			/*JSONObject message = client.readJSONObject();
			if(message != null){
				try {
					message.put("from", client.getClientInfo());
					message.put("timestamp", new Date().toString());
					client.getSession().sendMessage("session.message");
					client.getSession().sendMessage(message);
				}catch(JSONException e){
					e.printStackTrace();
				}
			}
		}else{
			throw new InvalidControllerActionException(command);
		}*/
	
	
	public CollabSession createSession(Client client){
		CollabSession session = new CollabSession();
		sessions.put(session.getUID(), session);
		
		joinSession(client, session);

		return session;
	}
	public CollabSession joinSession(Client client, String sessionId){
		CollabSession session = null;
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
	
	public void joinSession(Client client, CollabSession session){
		if(client.getSession() != null){
			leaveSession(client);
		}
		
		client.setSession(session);
		/*session.addClient(client);
		session.sendMessage("session.newUser");
		session.sendMessage(client.getClientInfo());*/
	}
	public void leaveSession(Client client){
		/*CollabSession session = client.getSession();
		client.setSession(null);
		session.sendMessage("session.left");*/
	}
}
