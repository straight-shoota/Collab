package de.hsfulda.collabserver.action;

public interface Revision {
	public Transaction getTransaction();
	public Transaction[] getNextTransactions();
	
}
