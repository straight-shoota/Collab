package de.hsfulda.collabserver.uid;

public interface UIDDirectory<UID, T extends UniqueEntity<UID>> {
	public UID getUID(T t);
	public T getEntity(UID uid);
}
