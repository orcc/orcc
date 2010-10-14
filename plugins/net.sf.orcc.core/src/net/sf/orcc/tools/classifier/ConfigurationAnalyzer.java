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
import net.sf.orcc.OrccRuntimeException;
import net.sf.orcc.ir.Action;
import net.sf.orcc.ir.ActionScheduler;
import net.sf.orcc.ir.Actor;
import net.sf.orcc.ir.CFGNode;
import net.sf.orcc.ir.FSM;
import net.sf.orcc.ir.FSM.NextStateInfo;
import net.sf.orcc.ir.Port;
import net.sf.orcc.ir.Procedure;
import net.sf.orcc.ir.instructions.Peek;
import net.sf.orcc.ir.nodes.NodeVisitor;
import net.sf.orcc.ir.transforms.AbstractActorTransformation;

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
	private class PeekVisitor extends AbstractActorTransformation {

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
		public void visit(Peek peek) {
			candidates.add(peek.getPort());
		}

	}

	private Actor actor;

	private FSM fsm;

	private String initialState;

	private Port port;

	private Map<Action, Integer> values;

	/**
	 * Creates a new configuration analyzer for the given actor
	 * 
	 * @param actor
	 *            an actor
	 */
	public ConfigurationAnalyzer(Actor actor) {
		this.actor = actor;
		values = new HashMap<Action, Integer>();
	}

	/**
	 * Analyze the actor given at construction time
	 */
	public void analyze() {
		ActionScheduler sched = actor.getActionScheduler();
		if (sched.hasFsm()) {
			fsm = sched.getFsm();
			initialState = fsm.getInitialState().getName();

			findConfigurationPort();
			if (port != null) {
				findConstraints();
			}
		}
	}

	/**
	 * Finds the configuration port of this FSM is there is one.
	 */
	private void findConfigurationPort() {
		// visits the scheduler of each action departing from the initial state
		List<Set<Port>> ports = new ArrayList<Set<Port>>();
		for (NextStateInfo info : fsm.getTransitions(initialState)) {
			PeekVisitor visitor = new PeekVisitor();
			visitAction(info.getAction(), visitor);
			ports.add(visitor.getCandidates());
		}

		// get the intersection of all ports
		Set<Port> candidates = new HashSet<Port>();

		// add all ports peeked
		for (Set<Port> set : ports) {
			candidates.addAll(set);
		}

		// get the intersection
		for (Set<Port> set : ports) {
			if (!set.isEmpty()) {
				candidates.retainAll(set);
			}
		}

		// set the port if there is only one
		if (candidates.size() == 1) {
			port = candidates.iterator().next();
		}
	}

	/**
	 * For each action departing from the initial state, visits its guards and
	 * stores a constrained variable that will contain the value to read from
	 * the configuration port when solved.
	 */
	private void findConstraints() {
		List<Action> previous = new ArrayList<Action>();

		// visits the scheduler of each action departing from the initial state
		for (NextStateInfo info : fsm.getTransitions(initialState)) {
			ConstraintBuilder visitor = new ConstraintBuilder();

			// add negated constraints of previous actions
			visitor.setNegateConstraints(true);
			for (Action action : previous) {
				visitor.visitAction(action);
			}

			// add constraint of current action
			visitor.setNegateConstraints(false);
			visitor.visitAction(info.getAction());

			// add current action to "previous" list
			previous.add(info.getAction());

			// solve
			IntVariable variable = visitor.getVariable(port.getName());
			if (variable == null) {
				System.out.println("no constraint on " + port);
			} else {
				DefaultSolver solver = new DefaultSolver(variable.getNetwork());
				Solution solution = solver.findFirst();
				if (solution != null) {
					int value = solution.getIntValue(variable);
					values.put(info.getAction(), value);
				}
			}
		}
	}

	/**
	 * Returns the configuration port.
	 * 
	 * @return the configuration port
	 */
	public Port getConfigurationPort() {
		return port;
	}

	/**
	 * Get a value that, read on the configuration port, would enable the given
	 * action to fire.
	 * 
	 * @param action
	 *            an action
	 * @return an integer value
	 */
	public int getConfigurationValue(Action action) {
		Integer value = values.get(action);
		if (value != null) {
			return value;
		}

		throw new OrccRuntimeException("expected value for " + action);
	}

	/**
	 * Visits the given action with the given visitor.
	 * 
	 * @param action
	 *            action associated with the next state
	 * @param visitor
	 *            a node visitor
	 */
	private void visitAction(Action action, NodeVisitor visitor) {
		Procedure scheduler = action.getScheduler();
		for (CFGNode node : scheduler.getNodes()) {
			node.accept(visitor);
		}
	}

}
