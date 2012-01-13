package de.hsfulda.collabserver.action;

import de.hsfulda.collabserver.UniqueEntity;

public interface Revision extends UniqueEntity {
	public Transaction getTransaction();
	public Transaction[] getNextTransactions();
	
}
