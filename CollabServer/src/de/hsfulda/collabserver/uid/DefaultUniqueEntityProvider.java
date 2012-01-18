package de.hsfulda.collabserver.uid;

public class DefaultUniqueEntityProvider<T extends UniqueObject<Integer>> extends UniqueEntityProvider<Integer, T> {
	public DefaultUniqueEntityProvider(){
		super(new IntegerUIDGenerator());
	}
}
