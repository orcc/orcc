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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.sf.orcc.OrccRuntimeException;
import net.sf.orcc.ir.Action;
import net.sf.orcc.ir.ActionScheduler;
import net.sf.orcc.ir.Actor;
import net.sf.orcc.ir.FSM;
import net.sf.orcc.ir.Port;
import net.sf.orcc.ir.FSM.NextStateInfo;
import net.sf.orcc.util.OrderedMap;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DirectedMultigraph;

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

	private void printInputs(OrderedMap<Port> inputs) {
		System.out.print("input ports: [");
		Iterator<Port> it = inputs.iterator();
		if (it.hasNext()) {
			Port port = it.next();
			int tokens = port.getNumTokensConsumed();
			System.out.print(port.getName() + ": " + tokens);
			while (it.hasNext()) {
				port = it.next();
				tokens = port.getNumTokensConsumed();
				System.out.print(", " + port.getName() + ": " + tokens);
			}
		}
		System.out.println("]");
	}

	private void printOutputs(OrderedMap<Port> outputs) {
		System.out.print("output ports: [");
		Iterator<Port> it = outputs.iterator();
		if (it.hasNext()) {
			Port port = it.next();
			int tokens = port.getNumTokensProduced();
			System.out.print(port.getName() + ": " + tokens);
			while (it.hasNext()) {
				port = it.next();
				tokens = port.getNumTokensProduced();
				System.out.print(", " + port.getName() + ": " + tokens);
			}
		}
		System.out.println("]");
	}

	/**
	 * Unrolls the FSM, generating a set of SDF graphs
	 * 
	 * @return the list of SDF graphs
	 */
	public List<Graph<Action, DefaultEdge>> unroll() {
		List<Graph<Action, DefaultEdge>> actorSubgraphs = new ArrayList<Graph<Action, DefaultEdge>>();

		// Generates a subgraph for each initial transition
		try {
			ActionScheduler sched = actor.getActionScheduler();
			if (sched.hasFsm()) {
				// will unroll for each branch departing from the initial state
				FSM fsm = sched.getFsm();
				String initialState = fsm.getInitialState().getName();
				for (NextStateInfo info : fsm.getTransitions(initialState)) {
					actorSubgraphs.add(unroll(initialState, info.getAction()));
				}
			} else {
				actorSubgraphs.add(unroll(sched.getActions()));
			}
		} catch (OrccRuntimeException e) {
			e.printStackTrace();
		}

		return actorSubgraphs;
	}

	/**
	 * Unroll a list of actions. This method is only called for FSM-less actors.
	 * 
	 * @param actions
	 *            a list of actions sorted by descending priority
	 * @return a graph
	 */
	private Graph<Action, DefaultEdge> unroll(List<Action> actions) {
		Graph<Action, DefaultEdge> actorSg = new DirectedMultigraph<Action, DefaultEdge>(
				DefaultEdge.class);

		PartiallyInterpretedActor interpretedActor = new PartiallyInterpretedActor(
				actor.getName(), actor, analyzer);
		interpretedActor.initialize();
		if (actions.size() == 1) {
			Action action = actions.get(0);
			actorSg.addVertex(action);
			interpretedActor.schedule();

			// print port token rates
			System.out.println("only one action: " + action);
			printInputs(actor.getInputs());
			printOutputs(actor.getOutputs());
		} else {
			System.out.println("TODO NO FSM");
		}

		return actorSg;
	}

	/**
	 * Unroll the FSM of the actor this unroller was created with, starting from
	 * the given initial state, and using the given configuration action.
	 * 
	 * @param initialState
	 *            the initial state of the FSM
	 * @param action
	 *            the action to use for configuring the FSM
	 * @return a graph
	 */
	private Graph<Action, DefaultEdge> unroll(String initialState, Action action) {
		Graph<Action, DefaultEdge> actorSg = new DirectedMultigraph<Action, DefaultEdge>(
				DefaultEdge.class);

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
		Action previous = null;
		do {
			interpretedActor.schedule();
			Action latest = interpretedActor.getScheduledAction();
			actorSg.addVertex(latest);
			if (previous != null) {
				actorSg.addEdge(latest, previous);
			}
		} while (!interpretedActor.getFsmState().equals(initialState));

		// print port token rates
		System.out.println("configuration " + action);
		printInputs(actor.getInputs());
		printOutputs(actor.getOutputs());

		return actorSg;
	}

}
