package de.hsfulda.collabserver.controller;

import de.hsfulda.collabserver.CollabClient;
import de.hsfulda.collabserver.CollabSession;
import de.hsfulda.collabserver.Message;

public interface Controller extends CollabMessageListener {
	public String getName();
	
	@Override
	public void doAction(Message message, CollabClient client) throws ControllerException;
}
