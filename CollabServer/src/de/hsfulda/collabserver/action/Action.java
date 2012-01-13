package de.hsfulda.collabserver.action;

import java.io.Serializable;
import java.util.Date;

import de.hsfulda.collabserver.Client;

public interface Action extends Serializable {
	public Client<?> getInvoker();
	public Date getTime();
	public Revision getTargetRevision();
}
