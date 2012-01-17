package de.hsfulda.collabserver.action;

import java.util.LinkedList;
import java.util.List;

public class DefaultRevision implements Revision {
	private Transaction transaction;
	private List<Transaction> nextTransactions = new LinkedList<Transaction>();
	
	public DefaultRevision(Transaction transaction){
		this.transaction = transaction;
	}
	
	@Override
	public Transaction getTransaction() {
		return transaction;
	}

	@Override
	public Transaction[] getNextTransactions() {
		return nextTransactions.toArray(new Transaction[nextTransactions.size()]);
	}

}
