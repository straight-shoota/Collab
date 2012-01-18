package de.hsfulda.collabserver.uid;

public class UniqueEntityProvider<UID, T extends UniqueEntity<UID>> extends MapUIDDirectory<UID, T> {
	UIDGenerator<UID> generator;
	
	public UniqueEntityProvider(UIDGenerator<UID> generator){
		this.generator = generator;
	}
	
	public UIDGenerator<UID> getGenerator() {
		return generator;
	}

	public void setGenerator(UIDGenerator<UID> generator) {
		this.generator = generator;
	}

	@Override
	public UID getUID(T t) {
		if(! getDirectory().containsKey(t)){
			UID uid = generator.getNextUID();
			put(uid, t);
			t.setUID(uid);
		}
		return super.getUID(t);
	}
}
