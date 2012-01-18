package de.hsfulda.collabserver.log;

import java.io.Serializable;
import java.util.Date;

import de.hsfulda.collabserver.JSONAble;

public abstract class LogRecord implements Serializable, JSONAble {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Date date;
	private String type;
	
	public LogRecord(String type){
		date = new Date();
		this.type = type;
	}

	public Date getDate(){
		return date;
	}
	public String getType(){
		return type;
	}
}
