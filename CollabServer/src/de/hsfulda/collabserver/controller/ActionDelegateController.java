package de.hsfulda.collabserver.controller;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import de.hsfulda.collabserver.CollabClient;
import de.hsfulda.collabserver.Message;

public class ActionDelegateController extends AbstractController {
	private Map<String, Set<CollabMessageListener>> actionHandlers = new HashMap<String, Set<CollabMessageListener>>();

	public ActionDelegateController(){
		super();
		initActions();
	}
	public ActionDelegateController(String name){
		this();
		setName(name);
	}
	
	protected void initActions(){
		
	}
	
	public ActionDelegateController bind(String action, CollabMessageListener handler){
		if(! actionHandlers.containsKey(action)){
			actionHandlers.put(action, new HashSet<CollabMessageListener>());
		}
		actionHandlers.get(action).add(handler);
		return this;
	}
	public ActionDelegateController unbind(String action, CollabMessageListener handler){
		if(actionHandlers.containsKey(action)){
			actionHandlers.get(action).remove(handler);
		}
		return this;
	}
	
	@Override
	public void doAction(Message message, CollabClient client) throws ControllerException {
		Set<CollabMessageListener> handlers = actionHandlers.get(message.getAction());
		if(handlers == null || handlers.size() == 0) {
			throw new ControllerException("no action handler for action: " + message.getAction());
		}
		for(CollabMessageListener handler : handlers){
			try {
				handler.action(message, client);
			}catch(Exception e){
				throw new ControllerException(e);
			}
		}
	}

	public Set<String> getActions(){
		return actionHandlers.keySet();
	}
	public String toString(){
		StringBuilder builder = new StringBuilder();
		builder.append(getName()).append("[");
		if(getActions().size() > 0){
			for(String s : getActions()){
				builder.append(s).append(",");
			}
			builder.deleteCharAt(builder.length()-1);
		}
		builder.append("]");
		return builder.toString();
	}
}
