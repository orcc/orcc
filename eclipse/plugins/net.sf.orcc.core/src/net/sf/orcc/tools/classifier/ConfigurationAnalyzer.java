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
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import jp.ac.kobe_u.cs.cream.DefaultSolver;
import jp.ac.kobe_u.cs.cream.IntVariable;
import jp.ac.kobe_u.cs.cream.Solution;
import net.sf.orcc.ir.Action;
import net.sf.orcc.ir.Actor;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.FSM;
import net.sf.orcc.ir.IrFactory;
import net.sf.orcc.ir.Pattern;
import net.sf.orcc.ir.Port;
import net.sf.orcc.ir.State;
import net.sf.orcc.ir.util.AbstractActorVisitor;

/**
 * This class defines a configuration analyzer.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class ConfigurationAnalyzer {

	/**
	 * This class defines a visitor that finds a set of ports peeked.
	 * 
	 * @author Matthieu Wipliez
	 * 
	 */
	private class PeekVisitor extends AbstractActorVisitor {

		private Set<Port> candidates;

		/**
		 * Creates a new peek visitor.
		 */
		public PeekVisitor() {
			candidates = new HashSet<Port>();
		}

		/**
		 * Returns the set of candidate ports.
		 * 
		 * @return the set of candidate ports
		 */
		public Set<Port> getCandidates() {
			return candidates;
		}

		@Override
		public void visit(Action action) {
			Pattern pattern = action.getPeekPattern();
			for (Port port : pattern.getPorts()) {
				if (pattern.getVariable(port) != null) {
					candidates.add(port);
				}
			}
		}

	}

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
	 * Creates the configuration for the given action using the constraints set
	 * by the given constraint builder.
	 * 
	 * @param action
	 *            an action
	 * @param visitor
	 *            a constraint builder
	 */
	private void createConfiguration(Action action, ConstraintBuilder visitor) {
		// solve all ports
		Map<Port, Expression> configuration = new HashMap<Port, Expression>();
		for (Port port : ports) {
			IntVariable variable = visitor.getVariable(port.getName());
			if (variable == null) {
				System.out.println("no constraint on " + port);
			} else {
				DefaultSolver solver = new DefaultSolver(variable.getNetwork());
				Solution solution = solver.findFirst();
				if (solution != null) {
					int value = solution.getIntValue(variable);
					configuration.put(port,
							IrFactory.eINSTANCE.createExprInt(value));
				}
			}
		}

		// add the configuration
		configurations.put(action, configuration);
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
			ConstraintBuilder visitor = new ConstraintBuilder(actor);

			// add negated constraints of previous actions
			visitor.setNegateConstraints(true);
			for (Action action : previous) {
				visitor.visitAction(action);
			}

			// add constraint of current action
			visitor.setNegateConstraints(false);
			visitor.visitAction(targetAction);

			// add current action to "previous" list
			previous.add(targetAction);

			// create the configuration for this action based on the constraints
			createConfiguration(targetAction, visitor);
		}
	}

	/**
	 * Finds the configuration ports of this actor, if any.
	 */
	private void findConfigurationPorts() {
		// visits the scheduler of each action departing from the initial state
		List<Set<Port>> ports = new ArrayList<Set<Port>>();

		FSM fsm = actor.getFsm();
		State initialState = fsm.getInitialState();
		for (Action action : fsm.getTargetActions(initialState)) {
			PeekVisitor visitor = new PeekVisitor();
			visitor.visit(action);
			ports.add(visitor.getCandidates());
		}

		// add all ports peeked
		Set<Port> candidates = new HashSet<Port>();
		for (Set<Port> set : ports) {
			candidates.addAll(set);
		}

		// and then only retains the ones that are common to every action
		for (Set<Port> set : ports) {
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
