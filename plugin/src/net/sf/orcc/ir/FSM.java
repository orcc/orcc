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

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import net.sf.orcc.OrccException;
import net.sf.orcc.util.UniqueEdge;

import org.jgrapht.DirectedGraph;
import org.jgrapht.ext.DOTExporter;
import org.jgrapht.ext.StringEdgeNameProvider;
import org.jgrapht.ext.StringNameProvider;
import org.jgrapht.graph.DirectedMultigraph;

/**
 * This class defines a Finite State Machine (FSM). A FSM is a directed
 * multi-graph, where a vertex is a state, and an edge is a list of actions.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class FSM {

	/**
	 * Action associated to the next state.
	 * 
	 * @author Matthieu Wipliez
	 * 
	 */
	public class NextStateInfo {

		private Action action;

		private State targetState;

		private NextStateInfo(Action action, State targetState) {
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

		private int index;

		private String name;

		/**
		 * Creates a new state with the given name and index.
		 * 
		 * @param name
		 *            name of this state
		 * @param index
		 *            index of this state
		 */
		private State(String name, int index) {
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

		private List<NextStateInfo> nextStateInfo;

		private State sourceState;

		/**
		 * Creates a transition from a source state.
		 * 
		 * @param sourceState
		 *            source state
		 */
		private Transition(State sourceState) {
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
	 * index of last state added to the state map
	 */
	private int index;

	/**
	 * initial state
	 */
	private State initialState;

	/**
	 * map of state name to state
	 */
	private Map<String, State> states;

	/**
	 * a linked hash map of transitions.
	 */
	private LinkedHashMap<String, Transition> transitions;

	/**
	 * Creates an FSM with the given state as an initial state.
	 * 
	 */
	public FSM() {
		states = new HashMap<String, State>();
		transitions = new LinkedHashMap<String, Transition>();
	}

	/**
	 * Adds a state with the given name only if the given state is not already
	 * present.
	 * 
	 * @param name
	 *            name of a state
	 * @return the state created
	 */
	public State addState(String name) {
		State state = states.get(name);
		if (state == null) {
			state = new State(name, index++);
			states.put(name, state);
			transitions.put(name, new Transition(state));
		}

		return state;
	}

	/**
	 * Adds a transition between two state with the given action.
	 * 
	 * @param source
	 *            name of the source state
	 * @param target
	 *            name of the target state
	 * @param action
	 *            an action
	 */
	public void addTransition(String source, String target, Action action) {
		State tgtState = states.get(target);

		Transition transition = transitions.get(source);
		List<NextStateInfo> nextState = transition.getNextStateInfo();
		nextState.add(new NextStateInfo(action, tgtState));
	}

	/**
	 * Returns a graph representation of this FSM.
	 * 
	 * @return a graph representation of this FSM
	 */
	private DirectedGraph<State, UniqueEdge> getGraph() {
		DirectedGraph<State, UniqueEdge> graph = new DirectedMultigraph<State, UniqueEdge>(
				UniqueEdge.class);
		for (State source : states.values()) {
			graph.addVertex(source);
			int index = source.getIndex();
			Transition transition = transitions.get(index);
			List<NextStateInfo> nextState = transition.getNextStateInfo();
			for (NextStateInfo info : nextState) {
				State target = info.getTargetState();
				graph.addVertex(target);
				graph.addEdge(source, target, new UniqueEdge(info.getAction()));
			}
		}

		return graph;
	}

	/**
	 * Returns the initial state.
	 * 
	 * @return the initial state
	 */
	public State getInitialState() {
		return initialState;
	}

	/**
	 * Returns the list of states sorted by alphabetical order.
	 * 
	 * @return the list of states sorted by alphabetical order
	 */
	public List<String> getStates() {
		return new ArrayList<String>(new TreeSet<String>(states.keySet()));
	}

	/**
	 * Returns the list of transitions of this FSM as a list of
	 * {@link Transition}.
	 * 
	 * @return the list of transitions of this FSM as a list of
	 *         {@link Transition}
	 */
	public List<Transition> getTransitions() {
		return new ArrayList<Transition>(transitions.values());
	}

	/**
	 * Returns the transitions departing from the given state.
	 * 
	 * @param state
	 *            a state name
	 * @return a list of next state transitions
	 */
	public List<NextStateInfo> getTransitions(String state) {
		return transitions.get(state).getNextStateInfo();
	}

	/**
	 * Prints a graph representation of this FSM.
	 * 
	 * @param file
	 *            output file
	 * @throws OrccException
	 *             if something goes wrong (most probably I/O error)
	 */
	public void printGraph(File file) throws OrccException {
		try {
			OutputStream out = new FileOutputStream(file);
			DOTExporter<State, UniqueEdge> exporter = new DOTExporter<State, UniqueEdge>(
					new StringNameProvider<State>(), null,
					new StringEdgeNameProvider<UniqueEdge>());
			exporter.export(new OutputStreamWriter(out), getGraph());
		} catch (IOException e) {
			throw new OrccException("I/O error", e);
		}
	}

	/**
	 * Sets the initial state of this FSM to the given state.
	 * 
	 * @param state
	 *            a state name
	 */
	public void setInitialState(String state) {
		initialState = addState(state);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("schedule fsm ");
		builder.append(initialState);
		builder.append(" : \n");
		List<Transition> transitions = getTransitions();
		for (Transition transition : transitions) {
			builder.append(transition.toString());
			builder.append('\n');
		}
		builder.append("end");
		return builder.toString();
	}

}
