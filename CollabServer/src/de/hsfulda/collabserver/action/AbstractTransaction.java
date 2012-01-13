package de.hsfulda.collabserver.action;

import java.util.Date;

import de.hsfulda.collabserver.Client;

public abstract class AbstractTransaction implements Transaction {
	private Client<?> invoker;
	private Date time;
	private Revision source;
	private Revision target;
	
	public AbstractTransaction(Revision source, Client<?> invoker){
		this.source = source;
		this.invoker = invoker;
		this.time = new Date();
		this.target = new DefaultRevision(this);
	}
	
	@Override
	public Client<?> getInvoker() {
		return invoker;
	}

	@Override
	public Date getTime() {
		return time;
	}

	@Override
	public Revision getTargetRevision() {
		return target;
	}


	@Override
	public Revision getSourceRevision() {
		return source;
	}

}
