package de.hsfulda.collabserver.uid;

public class IntegerUIDGenerator implements UIDGenerator<Integer> {
	private int counter = 1;
	@Override
	public Integer getNextUID() {
		return counter++; 
	}
	
	public static <T extends UniqueEntity<Integer>> UniqueEntityProvider<Integer, T> getProvider(){
		return new UniqueEntityProvider<Integer, T>(new IntegerUIDGenerator());
	}
}
