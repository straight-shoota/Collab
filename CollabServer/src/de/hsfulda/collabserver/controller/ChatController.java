package de.hsfulda.collabserver.controller;

import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;

import de.hsfulda.collabserver.CollabClient;
import de.hsfulda.collabserver.Message;

public class ChatController extends ActionDelegateController {
	@Override
	protected void initActions() {
		bind("message", new CollabMessageListener() {
			@Override
			public void doAction(Message message, CollabClient client)
					throws Exception {
				String content;
				content = message.getContent().getString("text");
				content = Jsoup.clean(content, Whitelist.simpleText());

				// content.replaceAll("(.*://[^<>[:space:]]+[[:alnum:]/])",
				// "<a href=\"$1\">HereWasAnURL</a>");

				content = content.replaceAll("(\\A|\\s)(www\\.\\S+)(\\s|\\z)",
						"$1http://$2$3");

				content = content.replaceAll(
						"(\\A|\\s)((http|https|ftp|mailto):\\S+)(\\s|\\z)",
						"$1<a href=\"$2\">$2</a>$4");

				ChatMessage chatMessage = new ChatMessage(client, content);
				// chatLog.add(cm);
				client.getSession().getLog().append(chatMessage);
				// System.out.println(chatMessage);
				client.getSession().send("chat.message", chatMessage);
			}
		});
	}
}
