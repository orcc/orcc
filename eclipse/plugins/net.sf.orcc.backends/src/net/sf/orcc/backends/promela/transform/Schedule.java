package net.sf.orcc.backends.promela.transform;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.orcc.df.Action;
import net.sf.orcc.df.State;

public class Schedule {
	
	private State initState = null;
	
	private List<Action> sequence = new ArrayList<Action>();
	
	private State endState = null;
	
	private int nrLeaves = 0;
	
	private Action enablingAction = null;
	
	private Set<State> potentialChoiseStates = new HashSet<State>();

	// The required input sequence for this schedule
	private Map<String, List<Object>> portPeeks = new HashMap<String, List<Object>>();

	private Map<String, List<Object>> portReads = new HashMap<String, List<Object>>();
	
	private Map<String, List<Object>> portWrites = new HashMap<String, List<Object>>();

	public Schedule() {}

	public Schedule(State initState, Action action) {
		this.initState = initState;
		setEnablingAction(action);
	}

	public void addPotentialChoiseState(State state) {
		this.potentialChoiseStates.add(state);
	}

	public Action getEnablingAction() {
		return enablingAction;
	}

	public State getEndState() {
		return endState;
	}

	public State getInitState() {
		return initState;
	}

	public int getNrLeaves() {
		return nrLeaves;
	}

	public Map<String, List<Object>> getPortPeeks() {
		return portPeeks;
	}

	public Map<String, List<Object>> getPortReads() {
		return portReads;
	}
	
	public Map<String, List<Object>> getPortWrites() {
		return portWrites;
	}

	public Set<State> getPotentialChoiseStates() {
		return potentialChoiseStates;
	}

	public List<Action> getSequence() {
		return sequence;
	}

	public void incNrLeaves() {
		this.nrLeaves++;
	}

	public void setEnablingAction(Action enablingAction) {
		this.enablingAction = enablingAction;
	}

	public void setEndState(State endState) {
		this.endState = endState;
	}

	@Override
	public String toString() {
		String s = "<superaction name=\"" + initState + "_" + enablingAction
				+ "\" guard=\" NULL \">\n";
		for (Action a : sequence) {
			s += "<iterand action=\"" + a + "\" actor=\""
					+ "instance.getName()" + "\"/>\n";
		}
		s += "</superaction>\n";
		return s;
	}
}
