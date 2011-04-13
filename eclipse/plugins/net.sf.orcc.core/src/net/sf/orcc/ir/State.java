package net.sf.orcc.ir;

/**
 * This class defines a state of a Finite State Machine.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class State implements Comparable<State> {

	public int index;

	public String name;

	/**
	 * Creates a new state with the given name and index.
	 * 
	 * @param name
	 *            name of this state
	 * @param index
	 *            index of this state
	 */
	public State(String name, int index) {
		this.index = index;
		this.name = name;
	}

	@Override
	public int compareTo(State o) {
		if (index < o.index) {
			return -1;
		} else if (index > o.index) {
			return 1;
		} else {
			return 0;
		}
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof State) {
			return index == ((State) obj).index;
		} else {
			return false;
		}
	}

	/**
	 * Returns the index of this state.
	 * 
	 * @return the index of this state
	 */
	public int getIndex() {
		return index;
	}

	/**
	 * Returns the name of this state.
	 * 
	 * @return the name of this state
	 */
	public String getName() {
		return name;
	}

	@Override
	public int hashCode() {
		return index;
	}

	@Override
	public String toString() {
		return getName();
	}

}