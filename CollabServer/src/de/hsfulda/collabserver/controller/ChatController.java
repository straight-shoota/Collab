package de.hsfulda.collabserver.controller;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;

import de.hsfulda.collabserver.CollabClient;
import de.hsfulda.collabserver.Message;

public class ChatController extends ActionDelegateController {
	List<ChatMessage> chatLog = new ArrayList<ChatMessage>();
	
	@Override
	protected void initActions(){
		bind("message", new CollabMessageListener() {
			@Override
			public void action(Message message, CollabClient client) throws JSONException {
				ChatMessage cm = new ChatMessage(client, message.getContent().getString("text"));
				chatLog.add(cm);
				System.out.println(cm);
				client.getSession().send(new Message.Outgoing("chat.message", cm.toJSON()));
			}
		});
	}
}
