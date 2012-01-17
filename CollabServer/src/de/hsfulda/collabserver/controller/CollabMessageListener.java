package de.hsfulda.collabserver.controller;

import org.json.JSONException;

import de.hsfulda.collabserver.CollabClient;
import de.hsfulda.collabserver.Message;

public interface CollabMessageListener {
	public void action(Message message, CollabClient client) throws JSONException;
}
