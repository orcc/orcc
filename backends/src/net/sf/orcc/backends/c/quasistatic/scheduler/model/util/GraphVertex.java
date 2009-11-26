package net.sf.orcc.backends.c.quasistatic.scheduler.model.util;

import net.sf.orcc.ir.Action;

public class GraphVertex {

	private Action action;
	
	public GraphVertex(Action action){
		this.action = action;
	}
	
	public String getVertexName(){
		return action.toString();
	}
	
	public boolean containsAction(Action action){
		return this.action.equals(action);
	}
	
	public String toString(){
		return action.toString();
	}
}
