package de.hsfulda.collabserver;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;
import org.webbitserver.WebSocketConnection;
import org.webbitserver.WebSocketHandler;

import de.hsfulda.collabserver.controller.AbstractController;
import de.hsfulda.collabserver.controller.ChatController;
import de.hsfulda.collabserver.controller.Controller;
import de.hsfulda.collabserver.controller.ControllerException;
import de.hsfulda.collabserver.controller.SceneController;
import de.hsfulda.collabserver.controller.SessionController;
import de.hsfulda.collabserver.controller.UserController;
import de.hsfulda.collabserver.uid.DefaultUniqueEntityProvider;
import de.hsfulda.collabserver.uid.UIDDirectory;

public class CollabWebSockets implements WebSocketHandler {
	CollabSession session;
	ServerInfo serverInfo;

	private Map<String, Controller> controllers = new HashMap<String, Controller>();
	private UIDDirectory<Integer, CollabSession> sessionUidProvider = new DefaultUniqueEntityProvider<CollabSession>();
	
	public CollabWebSockets(){
		serverInfo = new ServerInfo();
		serverInfo.setName("Collab Server");
		serverInfo.setVersion("0.1");
		
		session = new CollabSession(this);
		sessionUidProvider.getUID(session);
		
		registerController(new SessionController());
		registerController(new UserController());
		registerController(new ChatController());
		registerController(new SceneController());
	}

	public ServerInfo getServerInfo() {
		return serverInfo;
	}

	public void registerController(AbstractController c){
		controllers.put(c.getName(), c);
		System.out.println(" initialized controller: " + c);
	}
	public void unregisterController(AbstractController c){
		controllers.remove(c.getName());
	}

	@Override
	public void onMessage(WebSocketConnection connection, String messageString)
			throws Throwable {
		CollabClient client = (CollabClient) connection.data("client");
		Message message = Message.parseMessage(messageString);
		System.out.println(client.getUID() + " > " + message);
		
		String controller = message.getController();
		//System.out.println(message);
		if(controllers.containsKey(controller)){
			controllers.get(controller).doAction(message, client);
		}else{
			throw new ControllerException("Unknown controller for action " +  message.getActionString());
		}
	}

	@Override
	public void onMessage(WebSocketConnection connection, byte[] arg1)
			throws Throwable {
		// TODO Auto-generated method stub

	}

	@Override
	public void onOpen(WebSocketConnection connection) throws Exception {
		CollabClient client = session.add(connection);
		controllers.get("session").doAction(new Message("session.userJoined"), client);
	}
	
	@Override
	public void onClose(WebSocketConnection connection) throws Exception {
		CollabClient client = (CollabClient) connection.data("client");
		//System.out.println("connection closed: " + client);
		controllers.get("session").doAction(new Message("session.userLeft"), client);
	}

	@Override
	public void onPong(WebSocketConnection arg0, String arg1) throws Throwable {
		// TODO Auto-generated method stub

	}
	
	public static class ServerInfo implements Serializable, JSONAble {
		/**
		 * 
		 */
		private static final long serialVersionUID = 7165432629356414128L;
		private String name;
		private String version;
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getVersion() {
			return version;
		}
		public void setVersion(String version) {
			this.version = version;
		}
		@Override
		public JSONObject toJSON() throws JSONException {
			return new JSONObject(this);
		}
	}
}
