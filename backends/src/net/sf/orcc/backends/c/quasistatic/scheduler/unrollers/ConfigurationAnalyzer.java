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
import jp.ac.kobe_u.cs.cream.IntVariable;
import jp.ac.kobe_u.cs.cream.Network;
import jp.ac.kobe_u.cs.cream.Solution;
import net.sf.orcc.OrccRuntimeException;
import net.sf.orcc.ir.Action;
import net.sf.orcc.ir.ActionScheduler;
import net.sf.orcc.ir.Actor;
import net.sf.orcc.ir.CFGNode;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.FSM;
import net.sf.orcc.ir.Port;
import net.sf.orcc.ir.Procedure;
import net.sf.orcc.ir.Variable;
import net.sf.orcc.ir.FSM.NextStateInfo;
import net.sf.orcc.ir.expr.AbstractExpressionVisitor;
import net.sf.orcc.ir.expr.BinaryExpr;
import net.sf.orcc.ir.expr.BinaryOp;
import net.sf.orcc.ir.expr.BoolExpr;
import net.sf.orcc.ir.expr.IntExpr;
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
	 * This class defines a visitor that examines guards that depend on a value
	 * peeked from the configuration port. This visitor is only run if there is
	 * a configuration port.
	 * 
	 * @author Matthieu Wipliez
	 * 
	 */
	private class GuardVisitor extends AbstractActorTransformation {

		private class BitandVisitor extends AbstractExpressionVisitor {

			private boolean bitandOnly;

			private List<Integer> bitands;

			public BitandVisitor() {
				bitands = new ArrayList<Integer>();
				bitandOnly = true;
			}

			/**
			 * Returns a or'ed combination of all bitands performed on the
			 * target variable.
			 * 
			 * @return an integer
			 */
			public int getValue() {
				int result = 0;
				for (Integer value : bitands) {
					result |= value;
				}

				return result;
			}

			public boolean hasBitandOnly() {
				return bitandOnly;
			}

			@Override
			public void visit(BinaryExpr expr, Object... args) {
				Expression e1 = expr.getE1();
				int t1 = e1.getType();
				Expression e2 = expr.getE2();
				int t2 = e2.getType();
				BinaryOp op = expr.getOp();

				if (op == BinaryOp.NE) {
					int val = 0;
					BinaryExpr binary = null;

					if (t1 == Expression.INT && t2 == Expression.BINARY) {
						val = ((IntExpr) e1).getValue();
						binary = (BinaryExpr) e2;
					} else if (t1 == Expression.BINARY && t2 == Expression.INT) {
						binary = (BinaryExpr) e1;
						val = ((IntExpr) e2).getValue();
					}

					// we get a bitand with our target variable
					if (binary != null && val == 0) {
						binary.accept(this);
						bitandOnly &= true;
						return;
					}
				} else if (op == BinaryOp.BITAND) {
					int val = 0;
					Variable var = null;

					if (t1 == Expression.INT && t2 == Expression.VAR) {
						val = ((IntExpr) e1).getValue();
						var = ((VarExpr) e2).getVar().getVariable();
					} else if (t1 == Expression.VAR && t2 == Expression.INT) {
						var = ((VarExpr) e1).getVar().getVariable();
						val = ((IntExpr) e2).getValue();
					}

					// we get a bitand with our target variable
					if (target.equals(var)) {
						bitands.add(val);
						bitandOnly &= true;
						return;
					}
				} else if (op == BinaryOp.LOGIC_AND) {
					// visits branches
					e1.accept(this);
					e2.accept(this);
					bitandOnly &= true;
					return;
				}

				bitandOnly = false;
			}

			@Override
			public void visit(BoolExpr expr, Object... args) {
				bitandOnly = false;
			}

			@Override
			public void visit(IntExpr expr, Object... args) {
				bitandOnly = false;
			}

			@Override
			public void visit(UnaryExpr expr, Object... args) {
				bitandOnly = false;
			}

			@Override
			public void visit(VarExpr expr, Object... args) {
				bitandOnly = false;
			}

		}

		private class SimpleArithVisitor extends AbstractExpressionVisitor {

			/**
			 * Adds a constraint if op is a comparison operator.
			 * 
			 * @param op
			 *            a binary operator
			 * @param value
			 *            an integer value
			 */
			private void addConstraint(BinaryOp op, int value) {
				switch (op) {
				case EQ:
					tokenVariable.equals(value);
					break;
				case GE:
					tokenVariable.ge(value);
					break;
				case GT:
					tokenVariable.gt(value);
					break;
				case LE:
					tokenVariable.le(value);
					break;
				case LT:
					tokenVariable.lt(value);
					break;
				case NE:
					tokenVariable.notEquals(value);
					break;
				}
			}

			@Override
			public void visit(BinaryExpr expr, Object... args) {
				Expression e1 = expr.getE1();
				int t1 = e1.getType();
				Expression e2 = expr.getE2();
				int t2 = e2.getType();
				int val;

				if (t1 == Expression.INT && t2 == Expression.VAR
						&& target.equals(((VarExpr) e2).getVar().getVariable())) {
					val = ((IntExpr) e1).getValue();
					addConstraint(expr.getOp().inequalityOpChangeOrder(), val);
				} else if (t1 == Expression.VAR && t2 == Expression.INT
						&& target.equals(((VarExpr) e1).getVar().getVariable())) {
					val = ((IntExpr) e2).getValue();
					addConstraint(expr.getOp(), val);
				} else {
					throw new OrccRuntimeException("guard not understood");
				}
			}

		}

		private Network network;

		private Variable target;

		private Variable tokens;

		private IntVariable tokenVariable;

		public GuardVisitor() {
			network = new Network();
		}

		public IntVariable getVariable() {
			return tokenVariable;
		}

		@Override
		public void visit(Assign assign, Object... args) {
			BitandVisitor visitor = new BitandVisitor();
			assign.getValue().accept(visitor);
			if (visitor.hasBitandOnly()) {
				int bitand = visitor.getValue();
				System.out.println("bitand = " + bitand);
			} else {
				try {
					assign.getValue().accept(new SimpleArithVisitor());
				} catch (OrccRuntimeException e) {
					e.printStackTrace();
				}
			}
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
				tokenVariable = new IntVariable(network, port.getName());
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

		DefaultSolver solver = new DefaultSolver(variable.getNetwork());
		Solution solution = solver.findFirst();
		if (solution == null) {
			System.out.println("no solutions found for " + action
					+ ", returning 0");
			return 0;
		} else {
			int value = solution.getIntValue(variable);
			System.out.println("solution found for " + action + ", returning "
					+ value);
			return value;
		}
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
