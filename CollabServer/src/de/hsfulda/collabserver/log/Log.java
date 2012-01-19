package de.hsfulda.collabserver.log;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import de.hsfulda.collabserver.uid.DefaultUniqueEntityProvider;
import de.hsfulda.collabserver.uid.UIDDirectory;

public class Log {
	List<LogRecord> allRecords = new LinkedList<LogRecord>();
	Map<String, List<LogRecord>> recordsByType = new HashMap<String, List<LogRecord>>(); 
	
	private UIDDirectory<Integer, LogRecord> recordUidProvider = new DefaultUniqueEntityProvider<LogRecord>();
	
	public UIDDirectory<Integer, LogRecord> getRecordUIDProvider(){
		return recordUidProvider;
	}
	public void registerUID(LogRecord r){
		getRecordUIDProvider().getUID(r);
	}
	
	public void append(LogRecord record){
		registerUID(record);
		
		allRecords.add(record);
		
		if(! recordsByType.containsKey(record.getType())) {
			recordsByType.put(record.getType(), new LinkedList<LogRecord>());
		}
		recordsByType.get(record.getType()).add(record);
	}
}
