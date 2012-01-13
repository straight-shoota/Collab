package de.hsfulda.collabserver.controller;

import java.io.IOException;

public class InvalidControllerActionException extends IOException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4886345953882426670L;
	String[] command;
	public InvalidControllerActionException(String[] command){
		this.command = command;
	}
	public String getMessage(){
		StringBuilder b = new StringBuilder();
		boolean first = true;
		for(String s : this.command){
			if(! first){
				b.append(".");
			}else{
				first = false;
			}
			b.append(s);
		}
		return b.toString();
	}
}
