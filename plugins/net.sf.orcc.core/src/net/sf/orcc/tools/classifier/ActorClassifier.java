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
import java.util.Set;

import net.sf.orcc.OrccException;
import net.sf.orcc.OrccRuntimeException;
import net.sf.orcc.ir.Action;
import net.sf.orcc.ir.ActionScheduler;
import net.sf.orcc.ir.Actor;
import net.sf.orcc.ir.ActorTransformation;
import net.sf.orcc.ir.FSM;
import net.sf.orcc.ir.FSM.NextStateInfo;
import net.sf.orcc.ir.FSM.State;
import net.sf.orcc.ir.Pattern;
import net.sf.orcc.moc.CSDFMoC;
import net.sf.orcc.moc.DynamicMoC;
import net.sf.orcc.moc.MoC;
import net.sf.orcc.moc.QSDFMoC;
import net.sf.orcc.moc.SDFMoC;
import net.sf.orcc.util.UniqueEdge;

import org.jgrapht.DirectedGraph;
import org.jgrapht.alg.ConnectivityInspector;
import org.jgrapht.traverse.DepthFirstIterator;

/**
 * This class defines an actor classifier that uses symbolic execution to
 * classify an actor as static, cyclo-static, quasi-static, or dynamic.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class ActorClassifier implements ActorTransformation {

	private Actor actor;

	private ConfigurationAnalyzer analyzer;

	/**
	 * Creates a new classifier
	 */
	public ActorClassifier() {
	}

	/**
	 * Classifies the actor as dynamic, quasi-static, or static.
	 * 
	 * @return the class of the actor
	 */
	public void classify() {
		// interpreter needs the analyzer
		analyzer = new ConfigurationAnalyzer(actor);

		// checks for empty actors
		List<Action> actions = actor.getActions();
		if (actions.isEmpty()) {
			System.out.println("actor " + actor
					+ " does not contain any actions, defaults to dynamic");
			actor.setMoC(new DynamicMoC());
			return;
		}

		// checks for actors with time-dependent behavior
		boolean td = new TimeDependencyAnalyzer(actor).isTimeDependent();
		actor.setTimeDependent(td);
		if (actor.isTimeDependent()) {
			System.out.println("actor " + actor
					+ " is time-dependent, defaults to dynamic");
			actor.setMoC(new DynamicMoC());
			return;
		}

		// first tries SDF with *all* the actions of the actor
		MoC clasz = classifySDF(actions);
		if (!clasz.isSDF()) {
			try {
				// not SDF, tries CSDF
				clasz = classifyCSDF();
			} catch (OrccRuntimeException e) {
				// data-dependent behavior
			}

			ActionScheduler sched = actor.getActionScheduler();
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
			SDFMoC staticClass = (SDFMoC) clasz;
			staticClass.printTokenConsumption();
			staticClass.printTokenProduction();
			System.out.println();
		} else if (clasz.isCSDF()) {
			CSDFMoC csdfClass = (CSDFMoC) clasz;
			csdfClass.printTokenConsumption();
			csdfClass.printTokenProduction();
			System.out.println();
		} else if (clasz.isDynamic()) {
			System.out.println("actor " + actor + " classified dynamic");
		}

		actor.setMoC(clasz);
	}

	/**
	 * Tries to classify an actor as CSDF. Classification works only on actor
	 * with a non-empty state. The state consists of all scalar state variables
	 * that have an initial value.
	 * 
	 * @return an actor class
	 */
	private MoC classifyCSDF() {
		// new interpreter must be called before creation of ActorState
		AbstractInterpreter interpreter = newInterpreter();

		ActorState state = new ActorState(actor);
		if (state.isEmpty()) {
			return classifyCSDFStateless(interpreter);
		} else {
			return classifyCSDFStateful(state, interpreter);
		}
	}

	/**
	 * This method tries to classify an actor as CSDF based on its state (the
	 * set of state variables that are used in guards) and FSM state.
	 * 
	 * @param state
	 * @param interpreter
	 * @return
	 */
	private MoC classifyCSDFStateful(ActorState state,
			AbstractInterpreter interpreter) {
		FSM fsm = actor.getActionScheduler().getFsm();
		String initialState = null;
		if (fsm != null) {
			initialState = fsm.getInitialState().getName();
		}

		// creates the MoC
		CSDFMoC staticClass = new CSDFMoC();
		int nbPhases = 0;

		// loops until the actor goes back to the initial state, or there is a
		// data-dependent condition
		do {
			if (!interpreter.schedule()) {
				return new DynamicMoC();
			}

			Action latest = interpreter.getScheduledAction();
			staticClass.addAction(latest);
			nbPhases++;
		} while (!state.isInitialState()
				|| !interpreter.getFsmState().equals(initialState));

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
	 * @param interpreter
	 *            the abstract interpreter
	 * @return an actor class
	 */
	private MoC classifyCSDFStateless(AbstractInterpreter interpreter) {
		FSM fsm = actor.getActionScheduler().getFsm();
		if (fsm == null || !isCycloStaticFsm(fsm)) {
			// no state, no cyclo-static FSM => dynamic
			return new DynamicMoC();
		}

		// schedule the actor
		CSDFMoC staticClass = new CSDFMoC();
		String initialState = fsm.getInitialState().getName();

		int nbPhases = 0;
		do {
			interpreter.schedule();
			staticClass.addAction(interpreter.getScheduledAction());
			nbPhases++;
		} while (!initialState.equals(interpreter.getFsmState()));

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
	private SDFMoC classifyFsmConfiguration(String initialState, Action action) {
		SDFMoC staticClass = new SDFMoC();

		AbstractInterpreter interpretedActor = newInterpreter();
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
	private MoC classifyQSDF() {
		ActionScheduler sched = actor.getActionScheduler();
		FSM fsm = sched.getFsm();
		if (isQuasiStaticFsm(fsm)) {
			System.out.println(actor.getName()
					+ " has a quasi-static-compatible FSM");
			String initialState = fsm.getInitialState().getName();

			// analyze the configuration of this actor
			analyzer.analyze();

			// will unroll for each branch departing from the initial state
			QSDFMoC quasiStatic = new QSDFMoC();

			for (NextStateInfo info : fsm.getTransitions(initialState)) {
				Action action = info.getAction();
				SDFMoC staticClass = classifyFsmConfiguration(initialState,
						action);
				quasiStatic.addConfiguration(action, staticClass);
			}

			return quasiStatic;
		} else {
			System.out.println(actor.getName()
					+ " has an FSM that is NOT compatible with quasi-static");
			return new DynamicMoC();
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
	private MoC classifySDF(List<Action> actions) {
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
					return new DynamicMoC();
				}
			}
		} else {
			// an empty actor is considered dynamic
			// because the only actors with no actions are system actors
			// such as source and display
			return new DynamicMoC();
		}

		// schedule
		SDFMoC staticClass = new SDFMoC();
		AbstractInterpreter interpretedActor = newInterpreter();
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
	private boolean isQuasiStaticFsm(FSM fsm) {
		DirectedGraph<State, UniqueEdge> graph = fsm.getGraph();
		State initialState = fsm.getInitialState();
		Set<UniqueEdge> edges = graph.outgoingEdgesOf(initialState);
		for (UniqueEdge edge : edges) {
			State target = graph.getEdgeTarget(edge);

			DepthFirstIterator<State, UniqueEdge> it;
			it = new DepthFirstIterator<FSM.State, UniqueEdge>(graph, target);

			boolean cyclesBackToInitialState = false;
			while (it.hasNext()) {
				State state = it.next();
				if (state.equals(initialState)) {
					cyclesBackToInitialState = true;
					break;
				}
			}

			if (!cyclesBackToInitialState) {
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
	private AbstractInterpreter newInterpreter() {
		AbstractInterpreter interpretedActor = new AbstractInterpreter(actor,
				analyzer);

		actor.resetTokenConsumption();
		actor.resetTokenProduction();

		interpretedActor.initialize();

		return interpretedActor;
	}

	@Override
	public String toString() {
		return actor.toString();
	}

	@Override
	public void transform(Actor actor) throws OrccException {
		this.actor = actor;
		classify();
		actor = null;
	}

}
