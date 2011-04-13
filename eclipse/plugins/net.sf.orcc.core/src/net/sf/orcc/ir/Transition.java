package net.sf.orcc.ir;

import java.util.ArrayList;
import java.util.List;

/**
 * A transition in the FSM.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class Transition {

	public List<NextStateInfo> nextStateInfo;

	public State sourceState;

	/**
	 * Creates a transition from a source state.
	 * 
	 * @param sourceState
	 *            source state
	 */
	public Transition(State sourceState) {
		this.sourceState = sourceState;
		this.nextStateInfo = new ArrayList<NextStateInfo>();
	}

	public List<NextStateInfo> getNextStateInfo() {
		return nextStateInfo;
	}

	public State getSourceState() {
		return sourceState;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		for (NextStateInfo info : nextStateInfo) {
			builder.append(sourceState);
			builder.append(' ');
			builder.append(info.toString());
			builder.append('\n');
		}
		return builder.toString();
	}

}