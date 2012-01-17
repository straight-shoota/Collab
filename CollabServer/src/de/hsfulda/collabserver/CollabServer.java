package de.hsfulda.collabserver;

import java.io.IOException;

import org.webbitserver.WebServer;
import org.webbitserver.WebServers;

public class CollabServer {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			WebServer webServer = WebServers.createWebServer(3141);
			
			System.out.println("starting CollabServer listening on " + webServer.getUri() + " ...");
			
			webServer.add("/", new CollabWebSockets())
				      .start();
			System.out.println("========================================");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
