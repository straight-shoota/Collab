package de.hsfulda.collabserver.uid;

import org.apache.commons.collections15.BidiMap;
import org.apache.commons.collections15.bidimap.DualHashBidiMap;

public class MapUIDDirectory<UID, T extends UniqueEntity<UID>> implements UIDDirectory<UID, T> {
	private BidiMap<UID, T> directory = new DualHashBidiMap<UID, T>();

	@Override
	public UID getUID(T t) {
		return directory.getKey(t);
	}

	@Override
	public T getEntity(UID uid) {
		return directory.get(uid);
	}
	
	public void put(UID uid, T t){
		directory.put(uid, t);
	}
	
	public BidiMap<UID, T> getDirectory(){
		return directory;
	}
}
