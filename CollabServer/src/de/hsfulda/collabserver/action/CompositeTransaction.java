package de.hsfulda.collabserver.action;

import java.util.ArrayList;
import java.util.List;

import de.hsfulda.collabserver.Client;

public class CompositeTransaction extends AbstractTransaction {
	private List<Transaction> transactions = new ArrayList<Transaction>();
	
	public CompositeTransaction(Revision source, Client<?> invoker) {
		super(source, invoker);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void execute() {
		for(Transaction t : transactions) {
			t.execute();
		}
	}


}
