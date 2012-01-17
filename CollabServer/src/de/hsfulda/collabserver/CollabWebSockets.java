package de.hsfulda.collabserver;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.webbitserver.WebSocketConnection;
import org.webbitserver.WebSocketHandler;

import de.hsfulda.collabserver.controller.AbstractController;
import de.hsfulda.collabserver.controller.ChatController;
import de.hsfulda.collabserver.controller.ConnectionController;
import de.hsfulda.collabserver.controller.Controller;
import de.hsfulda.collabserver.controller.SessionController;
import de.hsfulda.collabserver.controller.UserController;

public class CollabWebSockets implements WebSocketHandler {
	CollabSession session = new CollabSession();
	ServerInfo serverInfo;
	
	private Map<String, Controller> controllers = new HashMap<String, Controller>();
	
	public CollabWebSockets(){
		serverInfo = new ServerInfo();
		serverInfo.setName("Collab Server");
		serverInfo.setVersion("0.1");
		
		registerController(new SessionController());
		registerController(new UserController());
		registerController(new ConnectionController());
		registerController(new ChatController());
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
			System.out.println("Unknown controller " +  controller + " for message from " + client);
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
		controllers.get("session").doAction(new Message("session.user.joined"), client);
		
		client.send(new Message("server.info", serverInfo));
	}
	
	@Override
	public void onClose(WebSocketConnection connection) throws Exception {
		CollabClient client = (CollabClient) connection.data("client");
		System.out.println("connection closed: " + client);
		controllers.get("session").doAction(new Message("session.user.left"), client);
		session.remove(client);
	}

	@Override
	public void onPong(WebSocketConnection arg0, String arg1) throws Throwable {
		// TODO Auto-generated method stub

	}
	public static class ServerInfo implements Serializable {
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
	}
}
