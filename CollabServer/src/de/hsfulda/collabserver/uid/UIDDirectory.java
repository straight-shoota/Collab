package de.hsfulda.collabserver.uid;

public interface UIDDirectory<UID, T extends UniqueEntity> {
	public UID getUID(T t);
	public T getEntity(UID uid);
}
