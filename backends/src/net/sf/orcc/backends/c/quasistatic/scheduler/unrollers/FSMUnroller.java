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
package net.sf.orcc.backends.c.quasistatic.scheduler.unrollers;

import java.util.ArrayList;
import java.util.List;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DirectedMultigraph;

import net.sf.orcc.OrccRuntimeException;
import net.sf.orcc.backends.c.quasistatic.scheduler.exceptions.QuasiStaticSchedulerException;
import net.sf.orcc.ir.Action;
import net.sf.orcc.ir.ActionScheduler;
import net.sf.orcc.ir.Actor;
import net.sf.orcc.ir.FSM;
import net.sf.orcc.ir.FSM.NextStateInfo;

/**
 * This class defines an FSM unroller that uses the partial interpreter.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class FSMUnroller implements AbstractFSMUnroller {

	private Actor actor;

	private ConfigurationAnalyzer analyzer;

	public FSMUnroller(Actor actor) {
		this.actor = actor;

		// analyze the configuration of this actor
		analyzer = new ConfigurationAnalyzer(actor);
		analyzer.analyze();
	}

	/**
	 * Unrolls the FSM, generating a set of SDF graphs
	 * 
	 * @return the list of SDF graphs
	 */
	public List<Graph<Action, DefaultEdge>> unroll() throws QuasiStaticSchedulerException {
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

	private Graph<Action, DefaultEdge> unroll(List<Action> actions) {
		Graph<Action, DefaultEdge> actorSg = new DirectedMultigraph<Action, DefaultEdge>(DefaultEdge.class);

		PartiallyInterpretedActor interpretedActor = new PartiallyInterpretedActor(
				actor.getName(), actor, analyzer);
		interpretedActor.initialize();
		if (actions.size() == 1) {
			actorSg.addVertex(actions.get(0));
			interpretedActor.schedule();
		} else {
			System.out.println("TODO NO FSM");
		}

		return actorSg;
	}

	private Graph<Action, DefaultEdge> unroll(String initialState, Action action)
			throws QuasiStaticSchedulerException {
		System.out.println();
		System.out.println("unroll " + actor.getName() + " from "
				+ initialState + ", configuration action " + action);
		Graph<Action, DefaultEdge> actorSg = new DirectedMultigraph<Action, DefaultEdge>(DefaultEdge.class);

		PartiallyInterpretedActor interpretedActor = new PartiallyInterpretedActor(
				actor.getName(), actor, analyzer);
		interpretedActor.initialize();
		interpretedActor.setAction(action);

		Action previous = null;
		do {
			interpretedActor.schedule();
			Action latest = interpretedActor.getScheduledAction();
			actorSg.addVertex(latest);
			if (previous != null) {
				actorSg.addEdge(latest, previous);
			}
		} while (!interpretedActor.getFsmState().equals(initialState));

		return actorSg;
	}

}
