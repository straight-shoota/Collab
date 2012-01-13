package de.hsfulda.collabserver.action;

public interface Transaction
extends Action {
	public void execute();
	public Revision getSourceRevision();
}
