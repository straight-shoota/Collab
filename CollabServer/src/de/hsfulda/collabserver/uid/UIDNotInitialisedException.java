package de.hsfulda.collabserver.uid;

public class UIDNotInitialisedException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public UIDNotInitialisedException(UniqueEntity<?> entity){
		super(entity.getClass().toString());
	}
}
