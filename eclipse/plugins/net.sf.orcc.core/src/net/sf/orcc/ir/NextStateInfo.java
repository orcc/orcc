package net.sf.orcc.ir;


/**
 * Action associated to the next state.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class NextStateInfo {

	public Action action;

	public State targetState;

	public NextStateInfo(Action action, State targetState) {
		this.action = action;
		this.targetState = targetState;
	}

	public Action getAction() {
		return action;
	}

	public State getTargetState() {
		return targetState;
	}

	@Override
	public String toString() {
		return "(" + action + ") --> " + targetState;
	}

}