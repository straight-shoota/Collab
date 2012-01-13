package de.hsfulda.collabserver.action;

public interface UndoableTransaction
extends Transaction{
	public void reset();
}
