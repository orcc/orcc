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
import net.sf.orcc.ir.classes.DynamicClass;
import net.sf.orcc.ir.classes.QuasiStaticClass;
import net.sf.orcc.ir.classes.StaticClass;

/**
 * This class defines an actor classifier that uses the partial interpreter to
 * determine if an actor is quasi-static or static.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class ActorClassifier {

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
	 * @param actor
	 *            an actor
	 * @return the class of the actor
	 */
	public ActorClass classify(Actor actor) {
		this.actor = actor;
		analyzer = new ConfigurationAnalyzer(actor);

		// Generates a subgraph for each initial transition
		try {
			ActionScheduler sched = actor.getActionScheduler();
			if (sched.hasFsm()) {
				return new DynamicClass();
			} else {
				return classifyNoFsm(sched.getActions());
			}
		} catch (OrccRuntimeException e) {
			e.printStackTrace();
			return new DynamicClass();
		}
	}

	private ActorClass classifyActionsNoState(
			PartiallyInterpretedActor interpretedActor, List<Action> actions) {

		return new DynamicClass();
	}

	/**
	 * Classifies an actor that has no FSM, but a list of actions and a
	 * non-empty state. This state consists in all scalar state variables that
	 * have an initial value.
	 * 
	 * @param interpretedActor
	 *            the partial interpreter
	 * @param state
	 *            the actor state
	 * @param actions
	 *            a list of actions
	 * @return a static class
	 */
	private StaticClass classifyActionsWithState(
			PartiallyInterpretedActor interpretedActor, ActorState state,
			List<Action> actions) {
		StaticClass staticClass = new StaticClass();

		// schedule the actor
		do {
			interpretedActor.schedule();
			Action latest = interpretedActor.getScheduledAction();
			staticClass.addAction(latest);
		} while (!state.isInitialState());

		// set token rates
		staticClass.setTokenConsumptions(actor);
		staticClass.setTokenProductions(actor);

		// print token rates
		staticClass.printTokenConsumption();
		staticClass.printTokenProduction();

		return staticClass;
	}

	/**
	 * Classifies this actor. This method is called when the actor this
	 * classifier was built with has an FSM.
	 * 
	 * @return a quasi-static class if possible
	 */
	private QuasiStaticClass classifyFsm() {
		// analyze the configuration of this actor
		analyzer.analyze();

		// TODO: check FSM against the configuration/processing pattern
		ActionScheduler sched = actor.getActionScheduler();

		// will unroll for each branch departing from the initial state
		QuasiStaticClass quasiStatic = new QuasiStaticClass();

		FSM fsm = sched.getFsm();
		String initialState = fsm.getInitialState().getName();
		for (NextStateInfo info : fsm.getTransitions(initialState)) {
			Action action = info.getAction();
			StaticClass staticClass = classifyFsmConfiguration(initialState,
					action);
			quasiStatic.addConfiguration(action, staticClass);
		}

		return quasiStatic;
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
	 * Classify an actor without FSM.
	 * 
	 * @param actions
	 *            a list of actions sorted by descending priority
	 * @return an actor class
	 */
	private ActorClass classifyNoFsm(List<Action> actions) {
		ActorClass clasz = classifyNoFsmSDF(actions);
		if (clasz.isDynamic()) {
			// actor cannot be considered SDF

			/*
			 * PartiallyInterpretedActor interpretedActor = new
			 * PartiallyInterpretedActor( actor.getName(), actor, analyzer);
			 * interpretedActor.initialize();
			 * 
			 * actor.resetTokenConsumption(); actor.resetTokenProduction();
			 * 
			 * ActorState state = new ActorState(actor); if (state.isEmpty()) {
			 * return classifyActionsNoState(interpretedActor, actions); } else
			 * { return classifyActionsWithState(interpretedActor, state,
			 * actions); }
			 */
			return clasz;
		} else {
			return clasz;
		}
	}

	/**
	 * Tries to classify an actor with several actions and no FSM as SDF.
	 * 
	 * @param actions
	 *            a list of actions sorted by descending priority
	 * @return an actor class
	 */
	private ActorClass classifyNoFsmSDF(List<Action> actions) {
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

		StaticClass staticClass = new StaticClass();

		actor.resetTokenConsumption();
		actor.resetTokenProduction();

		// schedule
		PartiallyInterpretedActor interpretedActor = new PartiallyInterpretedActor(
				actor.getName(), actor, analyzer);
		interpretedActor.initialize();
		interpretedActor.schedule();

		// set token rates
		staticClass.setTokenConsumptions(actor);
		staticClass.setTokenProductions(actor);

		// print port token rates
		System.out.println("actor " + actor + " is SDF");
		staticClass.printTokenConsumption();
		staticClass.printTokenProduction();
		System.out.println();

		return staticClass;
	}

}
