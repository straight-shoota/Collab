package de.hsfulda.collabserver.controller;

import de.hsfulda.collabserver.CollabClient;
import de.hsfulda.collabserver.Message;
import de.hsfulda.collabserver.Message;

public abstract class AbstractController implements Controller{
	private String name;
	public AbstractController(){
		String n = getClass().getSimpleName();
		if(n.substring(n.length() - 10).equals("Controller")){
			n = n.substring(0, n.length() - 10);
		}
		setName(n);
	}
	public AbstractController(String name){
		setName(name);
	}
	
	public void setName(String name) {
		this.name = name.toLowerCase();
	}
	public String getName(){
		return name;
	}
}
