package de.hsfulda.collabserver.controller;

import de.hsfulda.collabserver.CollabClient;
import de.hsfulda.collabserver.Message;

public interface Controller {

	public String getName();
	
	public void doAction(Message message, CollabClient client) throws ControllerException;
}
