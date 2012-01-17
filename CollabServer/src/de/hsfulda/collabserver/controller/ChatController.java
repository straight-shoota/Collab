package de.hsfulda.collabserver.controller;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;

import de.hsfulda.collabserver.CollabClient;
import de.hsfulda.collabserver.Message;

public class ChatController extends ActionDelegateController {
	List<ChatMessage> chatLog = new ArrayList<ChatMessage>();
	
	@Override
	protected void initActions(){
		bind("message", new CollabMessageListener() {
			@Override
			public void action(Message message, CollabClient client) throws JSONException {
				String content = message.getContent().getString("text");
				content = Jsoup.clean(content, Whitelist.simpleText());
				ChatMessage cm = new ChatMessage(client, content);
				chatLog.add(cm);
				System.out.println(cm);
				client.getSession().send("chat.message", cm.toJSON());
			}
		});
	}
}
