package de.hsfulda.collabserver;

public class DefaultUniqueEntity implements UniqueEntity  {
	private Object uid;
	
	public DefaultUniqueEntity(){
		generateUID();
	}
	
	protected void generateUID(){
		uid = UniqueEntityProvider.NextUID(this);
	}
	@Override
	public Object getUID(){
		return uid;
	}
}
