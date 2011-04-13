/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package net.sf.orcc.ir.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import net.sf.orcc.OrccException;
import net.sf.orcc.ir.Action;
import net.sf.orcc.ir.FSM;
import net.sf.orcc.ir.IrPackage;
import net.sf.orcc.ir.NextStateInfo;
import net.sf.orcc.ir.State;
import net.sf.orcc.ir.Transition;
import net.sf.orcc.util.UniqueEdge;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.jgrapht.DirectedGraph;
import org.jgrapht.ext.DOTExporter;
import org.jgrapht.ext.StringEdgeNameProvider;
import org.jgrapht.ext.StringNameProvider;
import org.jgrapht.graph.DirectedMultigraph;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>FSM</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * </p>
 *
 * @generated
 */
public class FSMImpl extends EObjectImpl implements FSM {

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return IrPackage.Literals.FSM;
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
	 * Creates an FSM.
	 * 
	 */
	protected FSMImpl() {
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
	 * @param action
	 *            an action
	 * @param target
	 *            name of the target state
	 */
	public void addTransition(String source, Action action, String target) {
		State tgtState = addState(target);

		Transition transition = transitions.get(source);
		List<NextStateInfo> nextState = transition.getNextStateInfo();
		nextState.add(new NextStateInfo(action, tgtState));
	}

	/**
	 * Creates and returns a graph representation of this FSM. Note that the
	 * graph is built each time this method is called.
	 * 
	 * @return a graph representation of this FSM
	 */
	public DirectedGraph<State, UniqueEdge> getGraph() {
		DirectedGraph<State, UniqueEdge> graph = new DirectedMultigraph<State, UniqueEdge>(
				UniqueEdge.class);
		for (State source : states.values()) {
			graph.addVertex(source);
			List<NextStateInfo> nextState = getTransitions(source.getName());
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
	 * Removes the transition from the state whose name is given by
	 * <code>source</code> and whose action equals to the given action.
	 * 
	 * @param source
	 *            name of source state
	 * @param action
	 *            action associated with the transition
	 */
	public void removeTransition(String source, Action action) {
		Transition transition = transitions.get(source);
		Iterator<NextStateInfo> it = transition.getNextStateInfo().iterator();
		while (it.hasNext()) {
			NextStateInfo info = it.next();
			if (info.getAction() == action) {
				it.remove();
			}
		}
	}

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
	public void replaceTarget(String source, Action action, String newTargetName) {
		Transition transition = transitions.get(source);
		Iterator<NextStateInfo> it = transition.getNextStateInfo().iterator();
		while (it.hasNext()) {
			NextStateInfo info = it.next();
			if (info.getAction() == action) {
				// updates target state of this transition
				info.targetState = addState(newTargetName);
			}
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
		}
		builder.append("end");
		return builder.toString();
	}

} //FSMImpl
