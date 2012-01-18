package de.hsfulda.collabserver.controller;

public class UnsyncedException extends ControllerException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Object target, assumedValue, value;
	
	public UnsyncedException(Object target, Object assumedValue, Object value, String message) {
		super(target + " unsynced: assumed " + assumedValue + ", but value was " + value + ". " + message);
		this.target = target;
		this.assumedValue = value;
		this.value = value;
	}
	
	public Object getTarget() {
		return target;
	}

	public Object getAssumedValue() {
		return assumedValue;
	}

	public Object getValue() {
		return value;
	}
}
