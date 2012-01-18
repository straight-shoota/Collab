package de.hsfulda.collabserver.uid;

public class UniqueObject<UID> implements UniqueEntity<UID> {
	private UID uid;
	
	@Override
	public UID getUID() {
		return uid;
	}

	@Override
	public void setUID(UID uid) {
		if(this.uid != null){
			throw new IllegalAccessError("UID may only be set once");
		}
		this.uid = uid;
	}
}
