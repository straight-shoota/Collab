package de.hsfulda.collabserver.action;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class ActionHistory {
	Queue<Action> history = new LinkedList<Action>();
	
	public void add(Action a){
		history.add(a);
	}
	
}
