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
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import jp.ac.kobe_u.cs.cream.DefaultSolver;
import jp.ac.kobe_u.cs.cream.IntDomain;
import jp.ac.kobe_u.cs.cream.IntVariable;
import jp.ac.kobe_u.cs.cream.Network;
import jp.ac.kobe_u.cs.cream.Solution;
import net.sf.orcc.OrccRuntimeException;
import net.sf.orcc.ir.Action;
import net.sf.orcc.ir.ActionScheduler;
import net.sf.orcc.ir.Actor;
import net.sf.orcc.ir.CFGNode;
import net.sf.orcc.ir.FSM;
import net.sf.orcc.ir.Port;
import net.sf.orcc.ir.Procedure;
import net.sf.orcc.ir.Variable;
import net.sf.orcc.ir.FSM.NextStateInfo;
import net.sf.orcc.ir.expr.BinaryExpr;
import net.sf.orcc.ir.expr.BinaryOp;
import net.sf.orcc.ir.expr.BoolExpr;
import net.sf.orcc.ir.expr.ExpressionInterpreter;
import net.sf.orcc.ir.expr.IntExpr;
import net.sf.orcc.ir.expr.ListExpr;
import net.sf.orcc.ir.expr.StringExpr;
import net.sf.orcc.ir.expr.UnaryExpr;
import net.sf.orcc.ir.expr.VarExpr;
import net.sf.orcc.ir.instructions.Assign;
import net.sf.orcc.ir.instructions.Load;
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
	 * This class defines a visitor that examines expressions that depend on a
	 * value peeked from the configuration port.
	 * 
	 * @author Matthieu Wipliez
	 * 
	 */
	private class ConstraintExpressionVisitor implements ExpressionInterpreter {

		private IntVariable constraintVariable;

		private Variable target;

		/**
		 * Creates a new constraint expression visitor that adds constraints on
		 * the given token variables to expressions that involve the given
		 * target.
		 * 
		 * @param target
		 *            target variable
		 * @param constraintVariable
		 *            constraint variable
		 */
		public ConstraintExpressionVisitor(Variable target,
				IntVariable constraintVariable) {
			this.target = target;
			this.constraintVariable = constraintVariable;
		}

		/**
		 * Adds a constraint between an integer variable and either another
		 * integer variable or an integer value.
		 * 
		 * @param v1
		 *            an integer variable
		 * @param op
		 *            a binary operator
		 * @param o2
		 *            an integer variable or an integer value
		 */
		private Object addConstraint(IntVariable v1, BinaryOp op, Object o2) {
			switch (op) {
			case BITAND:
				return addConstraintBitand(v1, o2);
			case EQ:
				if (o2 instanceof IntVariable) {
					v1.equals((IntVariable) o2);
				} else if (o2 instanceof Integer) {
					v1.equals((Integer) o2);
				} else {
					break;
				}
				return v1;
			case GE:
				if (o2 instanceof IntVariable) {
					v1.ge((IntVariable) o2);
				} else if (o2 instanceof Integer) {
					v1.ge((Integer) o2);
				} else {
					break;
				}
				return v1;
			case GT:
				if (o2 instanceof IntVariable) {
					v1.gt((IntVariable) o2);
				} else if (o2 instanceof Integer) {
					v1.gt((Integer) o2);
				} else {
					break;
				}
				return v1;
			case LE:
				if (o2 instanceof IntVariable) {
					v1.le((IntVariable) o2);
				} else if (o2 instanceof Integer) {
					v1.le((Integer) o2);
				} else {
					break;
				}
				return v1;
			case LT:
				if (o2 instanceof IntVariable) {
					v1.lt((IntVariable) o2);
				} else if (o2 instanceof Integer) {
					v1.lt((Integer) o2);
				} else {
					break;
				}
				return v1;
			case NE:
				if (o2 instanceof IntVariable) {
					v1.notEquals((IntVariable) o2);
				} else if (o2 instanceof Integer) {
					v1.notEquals((Integer) o2);
				} else {
					break;
				}
				return v1;
			}

			return null;
		}

		private Object addConstraintBitand(IntVariable v1, Object o2) {
			if (!(o2 instanceof Integer)) {
				return null;
			}

			int mask = (Integer) o2;
			double numBits = Math.log(mask) / Math.log(2);
			if (numBits - Math.floor(numBits) > 0.0) {
				throw new OrccRuntimeException(
						"masks not a power of two not supported");
			}

			Network network = v1.getNetwork();

			// step 1: let x = v1 divided by MASK
			// x = v1 / MASK <=> v1 = x * MASK
			IntVariable x = new IntVariable(network);
			IntVariable xTimesMask = x.multiply(mask);
			xTimesMask.equals(v1);

			// step 2: let result is r = x modulo 2
			// <=> x = 2 * q + r with q in 0..MAX, r in domain 0..1

			IntVariable r = new IntVariable(network, 0, 1);

			// x = y + r
			IntVariable y = new IntVariable(network);
			IntVariable sum = y.add(r);
			sum.equals(x);

			// y = 2 * q
			IntVariable q = new IntVariable(network, 0, IntDomain.MAX_VALUE);
			IntVariable twoTimesQ = q.multiply(2);
			y.equals(twoTimesQ);

			return r;
		}

		@Override
		public Object interpret(BinaryExpr expr, Object... args) {
			Object o1 = expr.getE1().accept(this);
			Object o2 = expr.getE2().accept(this);

			if (o1 instanceof IntVariable) {
				IntVariable v1 = (IntVariable) o1;
				return addConstraint(v1, expr.getOp(), o2);
			} else {
				if (o2 instanceof IntVariable) {
					IntVariable v2 = (IntVariable) o2;
					return addConstraint(v2, expr.getOp()
							.inequalityOpChangeOrder(), o1);
				} else {
					// no variable in binary expression
					return null;
				}
			}
		}

		@Override
		public Object interpret(BoolExpr expr, Object... args) {
			return null;
		}

		@Override
		public Object interpret(IntExpr expr, Object... args) {
			return expr.getValue();
		}

		@Override
		public Object interpret(ListExpr expr, Object... args) {
			return null;
		}

		@Override
		public Object interpret(StringExpr expr, Object... args) {
			return null;
		}

		@Override
		public Object interpret(UnaryExpr expr, Object... args) {
			switch (expr.getOp()) {
			case MINUS:
				Object obj = expr.getExpr().accept(this);
				if (obj instanceof IntVariable) {
					return ((IntVariable) obj).negate();
				} else if (obj instanceof Integer) {
					return -((Integer) obj);
				}
			}

			return null;
		}

		@Override
		public Object interpret(VarExpr expr, Object... args) {
			Variable variable = expr.getVar().getVariable();
			if (variable.equals(target)) {
				return constraintVariable;
			} else {
				return null;
			}
		}

	}

	/**
	 * This class defines a visitor that examines guards that depend on a value
	 * peeked from the configuration port. This visitor is only run if there is
	 * a configuration port.
	 * 
	 * @author Matthieu Wipliez
	 * 
	 */
	private class GuardVisitor extends AbstractActorTransformation {

		private IntVariable constraintVariable;

		private Network network;

		private Variable target;

		private Variable tokens;

		public GuardVisitor() {
			network = new Network();
		}

		/**
		 * Returns the constraint variable.
		 * 
		 * @return the constraint variable
		 */
		public IntVariable getVariable() {
			return constraintVariable;
		}

		@Override
		public void visit(Assign assign, Object... args) {
			ConstraintExpressionVisitor visitor = new ConstraintExpressionVisitor(
					target, constraintVariable);
			assign.getValue().accept(visitor);
		}

		@Override
		public void visit(Load load, Object... args) {
			if (load.getSource().getVariable().equals(tokens)) {
				target = load.getTarget();
			}
		}

		@Override
		public void visit(Peek peek, Object... args) {
			if (peek.getPort().equals(port)) {
				tokens = peek.getTarget();
				constraintVariable = new IntVariable(network, port.getName());
			}
		}

	}

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
		public void visit(Peek peek, Object... args) {
			candidates.add(peek.getPort());
		}

	}

	private Actor actor;

	private FSM fsm;

	private String initialState;

	private Port port;

	private Map<Action, IntVariable> values;

	/**
	 * Creates a new configuration analyzer for the given actor
	 * 
	 * @param actor
	 *            an actor
	 */
	public ConfigurationAnalyzer(Actor actor) {
		this.actor = actor;
		values = new HashMap<Action, IntVariable>();
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
			visitAction(info, visitor);
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

	private void findConstraints() {
		// visits the scheduler of each action departing from the initial state
		for (NextStateInfo info : fsm.getTransitions(initialState)) {
			GuardVisitor visitor = new GuardVisitor();
			visitAction(info, visitor);
			if (visitor.getVariable() == null) {
				System.out.println("no constraint on " + port);
			} else {
				values.put(info.getAction(), visitor.getVariable());
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
		IntVariable variable = values.get(action);

		if (variable != null) {
			DefaultSolver solver = new DefaultSolver(variable.getNetwork());
			Solution solution = solver.findFirst();
			if (solution != null) {
				int value = solution.getIntValue(variable);
				System.out.println("solution found for " + action
						+ ", returning " + value);
				return value;
			}
		}

		System.out.println("returning 0 for " + action);
		return 0;
	}

	/**
	 * Visits the action stored in the given next state information with the
	 * given visitor.
	 * 
	 * @param info
	 *            information about the next state
	 * @param visitor
	 *            a node visitor
	 */
	private void visitAction(NextStateInfo info, NodeVisitor visitor) {
		Action action = info.getAction();
		Procedure scheduler = action.getScheduler();
		for (CFGNode node : scheduler.getNodes()) {
			node.accept(visitor);
		}
	}

}
