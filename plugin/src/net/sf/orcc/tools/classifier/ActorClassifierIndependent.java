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

import java.util.Iterator;
import java.util.List;

import net.sf.orcc.OrccRuntimeException;
import net.sf.orcc.ir.Action;
import net.sf.orcc.ir.ActionScheduler;
import net.sf.orcc.ir.Actor;
import net.sf.orcc.ir.ActorClass;
import net.sf.orcc.ir.FSM;
import net.sf.orcc.ir.Pattern;
import net.sf.orcc.ir.FSM.NextStateInfo;
import net.sf.orcc.ir.FSM.State;
import net.sf.orcc.ir.classes.DynamicClass;
import net.sf.orcc.ir.classes.QuasiStaticClass;
import net.sf.orcc.ir.classes.StaticClass;
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
	public ActorClass classify(Actor actor) {
		this.actor = actor;
		// interpreter needs the analyzer
		analyzer = new ConfigurationAnalyzer(actor);

		// checks for empty actors
		List<Action> actions = actor.getActions();
		if (actions.isEmpty()) {
			System.out.println("actor " + actor
					+ " does not contain any actions, defaults to dynamic");
			return new DynamicClass();
		}

		// checks for actors with time-dependent behavior
		boolean td = new TimeDependencyAnalyzer(actor).isTimeDependent();
		actor.setTimeDependent(td);
		if (actor.isTimeDependent()) {
			System.out.println("actor " + actor
					+ " is time-dependent, defaults to dynamic");
			return new DynamicClass();
		}

		// first tries SDF with *all* the actions of the actor
		ActorClass clasz = classifySDF(actions);
		if (!clasz.isStatic()) {
			ActionScheduler sched = actor.getActionScheduler();
			try {
				// not SDF, tries CSDF
				clasz = classifyCSDF(sched);
			} catch (OrccRuntimeException e) {
				// data-dependent behavior
			}

			if (!clasz.isStatic()) {
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

		if (clasz.isStatic()) {
			// print port token rates
			StaticClass staticClass = (StaticClass) clasz;
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
	private ActorClass classifyCSDF(ActionScheduler sched) {
		// new interpreter must be called before creation of ActorState
		PartiallyInterpretedActor interpretedActor = newInterpreter();

		ActorState state = new ActorState(actor);
		if (state.isEmpty()) {
			if (sched.hasFsm()) {
				FSM fsm = sched.getFsm();
				if (isCycloStaticFsm(fsm)) {
					return classifyCSDFStateless(sched.getFsm(),
							interpretedActor);
				}
			}

			// no state, no cyclo-static FSM => dynamic
			return new DynamicClass();
		}

		// schedule the actor
		StaticClass staticClass = new StaticClass();
		int scheduled;
		do {
			scheduled = interpretedActor.schedule();
			if (scheduled != 0) {
				Action latest = interpretedActor.getScheduledAction();
				staticClass.addAction(latest);
			}
		} while (!state.isInitialState() || scheduled == 0);

		// set token rates
		staticClass.setTokenConsumptions(actor);
		staticClass.setTokenProductions(actor);

		System.out.println("actor " + actor + " is CSDF");

		return staticClass;
	}

	/**
	 * Tries to classify this actor with no state as CSDF.
	 * 
	 * @return an actor class
	 */
	private ActorClass classifyCSDFStateless(FSM fsm,
			PartiallyInterpretedActor interpretedActor) {
		// schedule the actor
		StaticClass staticClass = new StaticClass();
		String initialState = fsm.getInitialState().getName();
		String state = null;
		int scheduled;
		do {
			scheduled = interpretedActor.schedule();
			if (scheduled != 0) {
				Action latest = interpretedActor.getScheduledAction();
				staticClass.addAction(latest);
				state = interpretedActor.getFsmState();
			}
		} while (!initialState.equals(state) || scheduled == 0);

		// set token rates
		staticClass.setTokenConsumptions(actor);
		staticClass.setTokenProductions(actor);

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
	private StaticClass classifyFsmConfiguration(String initialState,
			Action action) {
		StaticClass staticClass = new StaticClass();

		actor.resetTokenConsumption();
		actor.resetTokenProduction();

		// creates a partial interpreter
		PartiallyInterpretedActor interpretedActor = new PartiallyInterpretedActor(
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
	private ActorClass classifyQSDF() {
		ActionScheduler sched = actor.getActionScheduler();
		FSM fsm = sched.getFsm();
		if (isQuasiStaticFsm(fsm)) {
			String initialState = fsm.getInitialState().getName();

			// analyze the configuration of this actor
			analyzer.analyze();

			// will unroll for each branch departing from the initial state
			QuasiStaticClass quasiStatic = new QuasiStaticClass();

			for (NextStateInfo info : fsm.getTransitions(initialState)) {
				Action action = info.getAction();
				StaticClass staticClass = classifyFsmConfiguration(
						initialState, action);
				quasiStatic.addConfiguration(action, staticClass);
			}

			return quasiStatic;
		} else {
			return new DynamicClass();
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
	private ActorClass classifySDF(List<Action> actions) {
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
					return new DynamicClass();
				}
			}
		} else {
			// an empty actor is considered dynamic
			// because the only actors with no actions are system actors
			// such as source and display
			return new DynamicClass();
		}

		// schedule
		PartiallyInterpretedActor interpretedActor = newInterpreter();
		int scheduled;
		do {
			scheduled = interpretedActor.schedule();
		} while (scheduled == 0);

		// set token rates
		StaticClass staticClass = new StaticClass();
		staticClass.setTokenConsumptions(actor);
		staticClass.setTokenProductions(actor);

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
	private boolean isQuasiStaticFsm(FSM fsm) {
		return false;
	}

	/**
	 * Creates a new interpreted actor, initializes it, and resets the actor's
	 * token production/consumption.
	 * 
	 * @return the interpreter created
	 */
	private PartiallyInterpretedActor newInterpreter() {
		PartiallyInterpretedActor interpretedActor = new PartiallyInterpretedActor(
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
