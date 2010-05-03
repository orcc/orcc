/*
 * Copyright (c) 2009-2010, IETR/INSA of Rennes
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
package net.sf.orcc.tools.classifier;

import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Queue;
import java.util.Set;

import net.sf.orcc.OrccRuntimeException;
import net.sf.orcc.classes.CSDFActorClass;
import net.sf.orcc.classes.DynamicActorClass;
import net.sf.orcc.classes.IClass;
import net.sf.orcc.classes.QuasiStaticClass;
import net.sf.orcc.classes.SDFActorClass;
import net.sf.orcc.ir.Action;
import net.sf.orcc.ir.ActionScheduler;
import net.sf.orcc.ir.Actor;
import net.sf.orcc.ir.FSM;
import net.sf.orcc.ir.FSM.NextStateInfo;
import net.sf.orcc.ir.FSM.State;
import net.sf.orcc.ir.Pattern;
import net.sf.orcc.util.UniqueEdge;

import org.jgrapht.DirectedGraph;
import org.jgrapht.alg.ConnectivityInspector;

/**
 * This class defines an actor classifier that uses symbolic execution to
 * classify an actor as static, cyclo-static, quasi-static, or dynamic.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class ActorClassifierIndependent {

	private Actor actor;

	private ConfigurationAnalyzer analyzer;

	/**
	 * Creates a new classifier
	 */
	public ActorClassifierIndependent() {
	}

	/**
	 * Classifies the actor as dynamic, quasi-static, or static.
	 * 
	 * @param actor
	 *            an actor
	 * @return the class of the actor
	 */
	public IClass classify(Actor actor) {
		this.actor = actor;
		// interpreter needs the analyzer
		analyzer = new ConfigurationAnalyzer(actor);

		// checks for empty actors
		List<Action> actions = actor.getActions();
		if (actions.isEmpty()) {
			System.out.println("actor " + actor
					+ " does not contain any actions, defaults to dynamic");
			return new DynamicActorClass();
		}

		// checks for actors with time-dependent behavior
		boolean td = new TimeDependencyAnalyzer(actor).isTimeDependent();
		actor.setTimeDependent(td);
		if (actor.isTimeDependent()) {
			System.out.println("actor " + actor
					+ " is time-dependent, defaults to dynamic");
			return new DynamicActorClass();
		}

		// first tries SDF with *all* the actions of the actor
		IClass clasz = classifySDF(actions);
		if (!clasz.isSDF()) {
			ActionScheduler sched = actor.getActionScheduler();
			try {
				// not SDF, tries CSDF
				clasz = classifyCSDF(sched);
			} catch (OrccRuntimeException e) {
				// data-dependent behavior
			}

			if (!clasz.isCSDF()) {
				// not CSDF, tries QSDF
				if (sched.hasFsm()) {
					try {
						clasz = classifyQSDF();
					} catch (OrccRuntimeException e) {
						// data-dependent behavior
					}
				}
			}
		}

		if (clasz.isSDF()) {
			// print port token rates
			SDFActorClass staticClass = (SDFActorClass) clasz;
			staticClass.printTokenConsumption();
			staticClass.printTokenProduction();
			System.out.println();
		} else if (clasz.isDynamic()) {
			System.out.println("actor " + actor + " classified dynamic");
		}

		return clasz;
	}

	/**
	 * Tries to classify an actor as CSDF. Classification works only on actor
	 * with a non-empty state. Such a state consists in all scalar state
	 * variables that have an initial value.
	 * 
	 * @param actions
	 *            a list of actions
	 * @return an actor class
	 */
	private IClass classifyCSDF(ActionScheduler sched) {
		// new interpreter must be called before creation of ActorState
		AbstractInterpretedActor interpretedActor = newInterpreter();

		ActorState state = new ActorState(actor);
		FSM fsm = sched.getFsm();
		if (state.isEmpty()) {
			if (sched.hasFsm()) {
				if (isCycloStaticFsm(fsm)) {
					return classifyCSDFStateless(sched.getFsm(),
							interpretedActor);
				}
			}

			// no state, no cyclo-static FSM => dynamic
			return new DynamicActorClass();
		}

		// schedule the actor
		CSDFActorClass staticClass = new CSDFActorClass();
		int scheduled;
		int nbPhases = 0;
		if (fsm == null) {
			do {
				scheduled = interpretedActor.schedule();
				if (scheduled != 0) {
					Action latest = interpretedActor.getScheduledAction();
					staticClass.addAction(latest);
				}
				nbPhases++;
			} while (!state.isInitialState() || scheduled == 0);
		} else {
			String initialState = fsm.getInitialState().getName();
			do {
				scheduled = interpretedActor.schedule();
				if (scheduled != 0) {
					Action latest = interpretedActor.getScheduledAction();
					staticClass.addAction(latest);
				}
				nbPhases++;
			} while (!state.isInitialState()
					|| !initialState.equals(interpretedActor.getFsmState())
					|| scheduled == 0);
		}

		// set token rates
		staticClass.setTokenConsumptions(actor);
		staticClass.setTokenProductions(actor);
		staticClass.setNumberOfPhases(nbPhases);

		System.out.println("actor " + actor + " is CSDF");

		return staticClass;
	}

	/**
	 * Tries to classify this actor with no state as CSDF.
	 * 
	 * @return an actor class
	 */
	private IClass classifyCSDFStateless(FSM fsm,
			AbstractInterpretedActor interpretedActor) {
		// schedule the actor
		CSDFActorClass staticClass = new CSDFActorClass();
		String initialState = fsm.getInitialState().getName();

		int nbPhases = 0;

		do {
			interpretedActor.schedule();
			staticClass.addAction(interpretedActor.getScheduledAction());
			nbPhases++;
		} while (!initialState.equals(interpretedActor.getFsmState()));

		// set token rates
		staticClass.setTokenConsumptions(actor);
		staticClass.setTokenProductions(actor);
		staticClass.setNumberOfPhases(nbPhases);

		System.out.println("actor " + actor + " is CSDF");

		return staticClass;
	}

	/**
	 * Classify the FSM of the actor this unroller was created with, starting
	 * from the given initial state, and using the given configuration action.
	 * 
	 * @param initialState
	 *            the initial state of the FSM
	 * @param action
	 *            the action to use for configuring the FSM
	 * @return a static class
	 */
	private SDFActorClass classifyFsmConfiguration(String initialState,
			Action action) {
		SDFActorClass staticClass = new SDFActorClass();

		actor.resetTokenConsumption();
		actor.resetTokenProduction();

		// creates a partial interpreter
		AbstractInterpretedActor interpretedActor = new AbstractInterpretedActor(
				actor.getName(), actor, analyzer);
		interpretedActor.initialize();
		interpretedActor.setAction(action);

		// schedule the actor
		do {
			interpretedActor.schedule();
			Action latest = interpretedActor.getScheduledAction();
			staticClass.addAction(latest);
		} while (!interpretedActor.getFsmState().equals(initialState));

		// set token rates
		staticClass.setTokenConsumptions(actor);
		staticClass.setTokenProductions(actor);

		// print token rates
		System.out.println("configuration " + action);
		staticClass.printTokenConsumption();
		staticClass.printTokenProduction();

		return staticClass;
	}

	/**
	 * Tries to classify this actor with an FSM as CSDF or QSDF.
	 * 
	 * @return an actor class
	 */
	private IClass classifyQSDF() {
		ActionScheduler sched = actor.getActionScheduler();
		FSM fsm = sched.getFsm();
		if (isQuasiStaticFsm(fsm)) {
			System.out.println(actor.getName()
					+ " has a quasi-static-compatible FSM");
			String initialState = fsm.getInitialState().getName();

			// analyze the configuration of this actor
			analyzer.analyze();

			// will unroll for each branch departing from the initial state
			QuasiStaticClass quasiStatic = new QuasiStaticClass();

			for (NextStateInfo info : fsm.getTransitions(initialState)) {
				Action action = info.getAction();
				SDFActorClass staticClass = classifyFsmConfiguration(
						initialState, action);
				quasiStatic.addConfiguration(action, staticClass);
			}

			return quasiStatic;
		} else {
			return new DynamicActorClass();
		}
	}

	/**
	 * Tries to classify an actor with several actions as SDF. An actor is SDF
	 * if all its actions have the same patterns.
	 * 
	 * @param actions
	 *            a list of actions sorted by descending priority
	 * @return an actor class
	 */
	private IClass classifySDF(List<Action> actions) {
		Iterator<Action> it = actions.iterator();
		if (it.hasNext()) {
			Action action = it.next();
			Pattern input = action.getInputPattern();
			Pattern output = action.getOutputPattern();
			while (it.hasNext()) {
				action = it.next();
				if ((input.equals(action.getInputPattern()) && output
						.equals(action.getOutputPattern())) == false) {
					// one pattern is not equal to another
					return new DynamicActorClass();
				}
			}
		} else {
			// an empty actor is considered dynamic
			// because the only actors with no actions are system actors
			// such as source and display
			return new DynamicActorClass();
		}

		// schedule
		SDFActorClass staticClass = new SDFActorClass();
		AbstractInterpretedActor interpretedActor = newInterpreter();
		interpretedActor.schedule();
		staticClass.addAction(interpretedActor.getScheduledAction());

		// set token rates
		staticClass.setTokenConsumptions(actor);
		staticClass.setTokenProductions(actor);

		// an SDF actor is a specific CSDF with 1 phase
		staticClass.setNumberOfPhases(1);

		System.out.println("actor " + actor + " is SDF");

		return staticClass;
	}

	/**
	 * Returns <code>true</code> if the given FSM looks like the FSM of a
	 * cyclo-static actor, <code>false</code> otherwise. A potentially
	 * cyclo-static actor is an actor with an FSM that cycles back to its
	 * initial state.
	 * 
	 * @param fsm
	 *            a Finite State Machine
	 * @return <code>true</code> if the given FSM has cyclo-static form
	 */
	private boolean isCycloStaticFsm(FSM fsm) {
		DirectedGraph<State, UniqueEdge> graph = fsm.getGraph();
		ConnectivityInspector<State, UniqueEdge> inspector;
		inspector = new ConnectivityInspector<State, UniqueEdge>(graph);
		State initial = fsm.getInitialState();
		return inspector.pathExists(initial, initial);
	}

	/**
	 * Returns <code>true</code> if the given FSM looks like the FSM of a
	 * quasi-static actor, <code>false</code> otherwise.
	 * 
	 * @param fsm
	 *            a Finite State Machine
	 * @return <code>true</code> if the given FSM has quasi-static form
	 */
	@SuppressWarnings("unchecked")
	private boolean isQuasiStaticFsm(FSM fsm) {
		DirectedGraph<State, UniqueEdge> graph = fsm.getGraph();
		State initialState = fsm.getInitialState();

		int n = graph.vertexSet().size();
		int[] belongs = new int[n];
		Set<State>[] branches = new Set[n];

		Queue<State> queue = new ArrayDeque<State>();
		Set<UniqueEdge> edges = graph.outgoingEdgesOf(initialState);
		for (UniqueEdge edge : edges) {
			State target = graph.getEdgeTarget(edge);
			Set<State> set = new HashSet<State>();
			set.add(target);
			branches[target.getIndex()] = set;

			if (!target.equals(initialState)) {
				belongs[target.getIndex()] = target.getIndex();
				queue.add(target);
			}
		}

		while (!queue.isEmpty()) {
			State state = queue.remove();
			int index = belongs[state.getIndex()];

			edges = graph.outgoingEdgesOf(state);
			for (UniqueEdge edge : edges) {
				State target = graph.getEdgeTarget(edge);
				if (target.equals(initialState)) {
					if (branches[index].contains(initialState)) {
						// at most one path back to initial state
						return false;
					}
					branches[index].add(initialState);
				} else if (!target.equals(state)) {
					// ignore loops
					if (belongs[target.getIndex()] == 0) {
						belongs[target.getIndex()] = index;
						branches[index].add(target);
						queue.add(target);
					} else if (belongs[target.getIndex()] != index) {
						// state belongs to another branch
						return false;
					}
				}
			}
		}

		for (Set<State> branch : branches) {
			if (branch != null && !branch.isEmpty()
					&& !branch.contains(initialState)) {
				return false;
			}
		}

		return true;
	}

	/**
	 * Creates a new interpreted actor, initializes it, and resets the actor's
	 * token production/consumption.
	 * 
	 * @return the interpreter created
	 */
	private AbstractInterpretedActor newInterpreter() {
		AbstractInterpretedActor interpretedActor = new AbstractInterpretedActor(
				actor.getName(), actor, analyzer);
		interpretedActor.initialize();

		actor.resetTokenConsumption();
		actor.resetTokenProduction();

		return interpretedActor;
	}

	@Override
	public String toString() {
		return actor.toString();
	}

}
