/*
 * Copyright (c) 2009, IETR/INSA of Rennes
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 
 *   * Redistributions of source code must retain the above copyright notice,
 *     this list of conditions and the following disclaimer.
 *   * Redistributions in binary form must reproduce the above copyright notice,
 *     this list of conditions and the following disclaimer in the documentation
 *     and/or other materials provided with the distribution.
 *   * Neither the name of the IETR/INSA of Rennes nor the names of its
 *     contributors may be used to endorse or promote products derived from this
 *     software without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT,
 * STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY
 * WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 */
package net.sf.orcc.ir;

import org.eclipse.emf.ecore.EObject;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import net.sf.orcc.OrccException;
import net.sf.orcc.util.UniqueEdge;

import org.jgrapht.DirectedGraph;

/**
 * This class defines a Finite State Machine (FSM). A FSM is a directed
 * multi-graph, where a vertex is a state, and an edge is a list of actions.
 * 
 * @author Matthieu Wipliez
 * @model
 */
public interface FSM extends EObject {

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

	/**
	 * Adds a state with the given name only if the given state is not already
	 * present.
	 * 
	 * @param name
	 *            name of a state
	 * @return the state created
	 */
	State addState(String name);

	/**
	 * Adds a transition between two state with the given action.
	 * 
	 * @param source
	 *            name of the source state
	 * @param action
	 *            an action
	 * @param target
	 *            name of the target state
	 */
	void addTransition(String source, Action action, String target);

	/**
	 * Creates and returns a graph representation of this FSM. Note that the
	 * graph is built each time this method is called.
	 * 
	 * @return a graph representation of this FSM
	 */
	DirectedGraph<State, UniqueEdge> getGraph();

	/**
	 * Returns the initial state.
	 * 
	 * @return the initial state
	 */
	State getInitialState();

	/**
	 * Returns the list of states sorted by alphabetical order.
	 * 
	 * @return the list of states sorted by alphabetical order
	 */
	List<String> getStates();

	/**
	 * Returns the list of transitions of this FSM as a list of
	 * {@link Transition}.
	 * 
	 * @return the list of transitions of this FSM as a list of
	 *         {@link Transition}
	 */
	List<Transition> getTransitions();

	/**
	 * Returns the transitions departing from the given state.
	 * 
	 * @param state
	 *            a state name
	 * @return a list of next state transitions
	 */
	List<NextStateInfo> getTransitions(String state);

	/**
	 * Prints a graph representation of this FSM.
	 * 
	 * @param file
	 *            output file
	 * @throws OrccException
	 *             if something goes wrong (most probably I/O error)
	 */
	void printGraph(File file) throws OrccException;

	/**
	 * Removes the transition from the state whose name is given by
	 * <code>source</code> and whose action equals to the given action.
	 * 
	 * @param source
	 *            name of source state
	 * @param action
	 *            action associated with the transition
	 */
	void removeTransition(String source, Action action);

	/**
	 * Replaces the target of the transition from the state whose name is given
	 * by <code>source</code> and whose action equals to the given action by a
	 * target state with the given name.
	 * 
	 * @param source
	 *            name of source state
	 * @param action
	 *            action associated with the transition
	 * @param newTargetName
	 *            name of the new target state
	 */
	void replaceTarget(String source, Action action, String newTargetName);

	/**
	 * Sets the initial state of this FSM to the given state.
	 * 
	 * @param state
	 *            a state name
	 */
	void setInitialState(String state);

}
