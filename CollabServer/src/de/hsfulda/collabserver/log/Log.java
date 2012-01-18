package de.hsfulda.collabserver.log;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Log {
	List<LogRecord> allRecords = new LinkedList<LogRecord>();
	Map<String, List<LogRecord>> recordsByType = new HashMap<String, List<LogRecord>>(); 
	
	public void append(LogRecord record){
		allRecords.add(record);
		
		if(! recordsByType.containsKey(record.getType())) {
			recordsByType.put(record.getType(), new LinkedList<LogRecord>());
		}
		recordsByType.get(record.getType()).add(record);
	}
}
