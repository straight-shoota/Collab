package de.hsfulda.collabserver.log;

import java.io.Serializable;
import java.util.Date;

import de.hsfulda.collabserver.JSONAble;
import de.hsfulda.collabserver.uid.UniqueObject;

public abstract class LogRecord
extends UniqueObject<Integer>  implements Serializable, JSONAble {
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
