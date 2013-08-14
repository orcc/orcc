package net.sf.orcc.backends.promela.transform;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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
	
	public Set<State> getPotentialChoiseStates() {
		return potentialChoiseStates;
	}

	public void addPotentialChoiseState(State state) {
		this.potentialChoiseStates.add(state);
	}

	public Action getEnablingAction() {
		return enablingAction;
	}

	public void setEnablingAction(Action enablingAction) {
		this.enablingAction = enablingAction;
	}

	public int getNrLeaves() {
		return nrLeaves;
	}

	public void incNrLeaves() {
		this.nrLeaves++;
	}

	public State getInitState() {
		return initState;
	}

	public List<Action> getSequence() {
		return sequence;
	}

	public State getEndState() {
		return endState;
	}

	public void setEndState(State endState) {
		this.endState = endState;
	}
	
	public Schedule(State initState, Action action) {
		this.initState=initState;
		setEnablingAction(action);
	}
	
	@Override
	public String toString() {
		String s = "<superaction name=\"" + initState+"_"+ enablingAction +"\" guard=\" NULL \">\n";
		for (Action a : sequence) {
			s += "<iterand action=\"" + a + "\" actor=\"" + "instance.getName()" + "\"/>\n";
		}
		s += "</superaction>\n";
		return s;
	}
	
	public Schedule() {	}
}

