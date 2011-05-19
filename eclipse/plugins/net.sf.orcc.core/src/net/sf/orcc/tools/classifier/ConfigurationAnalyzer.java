/*
 * Copyright (c) 2009-2011, IETR/INSA of Rennes
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
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.orcc.ir.Action;
import net.sf.orcc.ir.Actor;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.FSM;
import net.sf.orcc.ir.Port;
import net.sf.orcc.ir.State;

/**
 * This class defines a configuration analyzer.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class ConfigurationAnalyzer {

	private Actor actor;

	private Map<Action, Map<Port, Expression>> configurations;

	private List<Port> ports;

	/**
	 * Creates a new configuration analyzer.
	 */
	public ConfigurationAnalyzer() {
	}

	/**
	 * Analyzes the given actor.
	 * 
	 * @param actor
	 *            an actor
	 */
	public void analyze(Actor actor) {
		this.actor = actor;
		ports = new ArrayList<Port>();
		configurations = new HashMap<Action, Map<Port, Expression>>();

		if (actor.hasFsm()) {
			findConfigurationPorts();
			if (!ports.isEmpty()) {
				createConfigurations();
			}
		}
	}

	/**
	 * For each action departing from the initial state, visits its guards and
	 * stores a constrained variable that will contain the value to read from
	 * the configuration port when solved.
	 */
	private void createConfigurations() {
		List<Action> previous = new ArrayList<Action>();

		// visits the scheduler of each action departing from the initial state
		FSM fsm = actor.getFsm();
		State initialState = fsm.getInitialState();
		for (Action targetAction : fsm.getTargetActions(initialState)) {
			// create the configuration for this action
			GuardSatChecker checker = new GuardSatChecker(actor);
			Map<Port, Expression> configuration = checker.computeTokenValues(
					ports, previous, targetAction);

			// add the configuration
			configurations.put(targetAction, configuration);

			// add current action to "previous" list
			previous.add(targetAction);
		}
	}

	/**
	 * Finds the configuration ports of this actor, if any.
	 */
	private void findConfigurationPorts() {
		List<Set<Port>> actionPorts = new ArrayList<Set<Port>>();

		FSM fsm = actor.getFsm();
		State initialState = fsm.getInitialState();

		// visits the scheduler of each action departing from the initial state
		for (Action action : fsm.getTargetActions(initialState)) {
			Set<Port> candidates = new HashSet<Port>();
			candidates.addAll(action.getPeekPattern().getPorts());
			actionPorts.add(candidates);
		}

		// add all ports peeked
		Set<Port> candidates = new HashSet<Port>();
		for (Set<Port> set : actionPorts) {
			candidates.addAll(set);
		}

		// and then only retains the ones that are common to every action
		for (Set<Port> set : actionPorts) {
			if (!set.isEmpty()) {
				candidates.retainAll(set);
			}
		}

		// copies the candidates to the ports list
		this.ports = new ArrayList<Port>(candidates);
	}

	/**
	 * Returns the configuration associated with the given action.
	 * 
	 * @param action
	 *            an action
	 * @return the configuration associated with the given action
	 */
	public Map<Port, Expression> getConfiguration(Action action) {
		return configurations.get(action);
	}

}
