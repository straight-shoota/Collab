package de.hsfulda.collabserver.controller;

public class ControllerException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8238038049608885995L;
	public ControllerException(String message){
		super(message);
	}
	public ControllerException(Exception e) {
		super(e);
	}
}
