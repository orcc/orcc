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

import java.util.List;

import net.sf.orcc.OrccRuntimeException;
import net.sf.orcc.ir.Action;
import net.sf.orcc.ir.ActionScheduler;
import net.sf.orcc.ir.Actor;
import net.sf.orcc.ir.ActorClass;
import net.sf.orcc.ir.FSM;
import net.sf.orcc.ir.Port;
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
	 * Creates a new FSM unroller on the given actor. The unroller starts by
	 * analyzing the actor's configuration if it has any.
	 * 
	 * @param actor
	 *            an actor
	 */
	public ActorClassifier(Actor actor) {
		this.actor = actor;

		// analyze the configuration of this actor
		analyzer = new ConfigurationAnalyzer(actor);
		analyzer.analyze();
	}

	/**
	 * Classifies the actor as dynamic, quasi-static, or static.
	 * 
	 * @return the class of the actor
	 */
	public ActorClass classify() {
		// Generates a subgraph for each initial transition
		try {
			ActionScheduler sched = actor.getActionScheduler();
			if (sched.hasFsm()) {
				// will unroll for each branch departing from the initial state
				QuasiStaticClass quasiStatic = new QuasiStaticClass();

				FSM fsm = sched.getFsm();
				String initialState = fsm.getInitialState().getName();
				for (NextStateInfo info : fsm.getTransitions(initialState)) {
					Action action = info.getAction();
					StaticClass staticClass = classifyFSM(initialState, action);
					quasiStatic.addConfiguration(action, staticClass);
				}

				return quasiStatic;
			} else {
				return classifyActions(sched.getActions());
			}
		} catch (OrccRuntimeException e) {
			e.printStackTrace();
			return new DynamicClass();
		}
	}

	/**
	 * Classify the given list of actions. This method is only called for
	 * FSM-less actors.
	 * 
	 * @param actions
	 *            a list of actions sorted by descending priority
	 * @return an actor class
	 */
	private ActorClass classifyActions(List<Action> actions) {
		PartiallyInterpretedActor interpretedActor = new PartiallyInterpretedActor(
				actor.getName(), actor, analyzer);
		interpretedActor.initialize();
		if (actions.size() == 1) {
			// an actor with a single action is static
			interpretedActor.schedule();

			StaticClass staticClass = new StaticClass();
			staticClass.addAction(interpretedActor.getScheduledAction());

			// set token rates
			staticClass.setTokenConsumptions(actor);
			staticClass.setTokenProductions(actor);

			// print port token rates
			staticClass.printTokenConsumption();
			staticClass.printTokenProduction();

			return staticClass;
		} else {
			System.out.println("TODO NO FSM");

			return new DynamicClass();
		}
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
	private StaticClass classifyFSM(String initialState, Action action) {
		StaticClass staticClass = new StaticClass();

		// resets input consumption rates
		for (Port port : actor.getInputs()) {
			port.resetTokenConsumption();
		}

		// resets output production rates
		for (Port port : actor.getOutputs()) {
			port.resetTokenProduction();
		}

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

}
