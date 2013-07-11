package net.sf.orcc.tools.merger.actor;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import net.sf.orcc.df.Action;
import net.sf.orcc.df.Actor;
import net.sf.orcc.df.FSM;
import net.sf.orcc.df.Port;
import net.sf.orcc.df.State;
import net.sf.orcc.graph.Vertex;

public class MergerUtil {

	public static int findPort(List<Port> portList, String name) {
		for (int i = 0; i < portList.size(); i++) {
			if (portList.get(i).getName().equals(name)) {
				return i;
			}
		}
		return -1;
	}

	public static Action findAction(Actor actor, String actionName) {
		for(Action action : actor.getActions()) {
			if(action.getName().equals(actionName)) {
				return action;
			}
		}
		return null;
	}
	
	public static State findState(FSM fsm, String stateName) {
		for(State state : fsm.getStates()) {
			if(state.getName().equals(stateName))
				return state;
		}
		return null;
	}

	public static void copyNumTokensToActionPorts(List<Vertex> vertices) {
		for (Vertex vertex : vertices) {
			for (Action action : vertex.getAdapter(Actor.class).getActions()) {
				for (Port port : action.getInputPattern().getPorts()) {
					port.setNumTokensConsumed(action.getInputPattern().getNumTokens(port)); 
				}			
				for (Port port : action.getOutputPattern().getPorts()) {
					port.setNumTokensProduced(action.getOutputPattern().getNumTokens(port)); 
				}			
			}
		}
	}

	public static boolean testFilePresence(String fileName) {
		try {
			InputStream is = new FileInputStream(fileName);
			is.close();
			return true;
		} catch (IOException e) {
			return false;
		}
	}
	
}
