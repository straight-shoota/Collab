package de.hsfulda.collabserver.uid;

public interface UniqueEntity<UID> {
	public UID getUID();
	public void setUID(UID uid);
}
