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
import net.sf.orcc.ir.Type;
import net.sf.orcc.ir.Variable;
import net.sf.orcc.ir.FSM.NextStateInfo;
import net.sf.orcc.ir.expr.BinaryExpr;
import net.sf.orcc.ir.expr.BinaryOp;
import net.sf.orcc.ir.expr.BoolExpr;
import net.sf.orcc.ir.expr.ExpressionEvaluator;
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
import net.sf.orcc.ir.type.IntType;
import net.sf.orcc.ir.type.UintType;

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

		/**
		 * whether constraints should be negated
		 */
		private boolean negateConstraints;

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
		 * @param negateConstraints
		 *            if true, each constraint is created to satisfy the
		 *            negation of the expression
		 */
		public ConstraintExpressionVisitor(Variable target,
				IntVariable constraintVariable, boolean negateConstraints) {
			this.target = target;
			this.constraintVariable = constraintVariable;
			this.negateConstraints = negateConstraints;
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
				if (o2 instanceof Integer) {
					return v1.bitand((Integer) o2);
				}
				break;
			case EQ:
				if (o2 instanceof IntVariable) {
					v1.equals((IntVariable) o2);
				} else if (o2 instanceof Integer) {
					v1.equals(((Integer) o2).intValue());
				}
				break;
			case GE:
				if (o2 instanceof IntVariable) {
					v1.ge((IntVariable) o2);
				} else if (o2 instanceof Integer) {
					v1.ge((Integer) o2);
				}
				break;
			case GT:
				if (o2 instanceof IntVariable) {
					v1.gt((IntVariable) o2);
				} else if (o2 instanceof Integer) {
					v1.gt((Integer) o2);
				}
				break;
			case LE:
				if (o2 instanceof IntVariable) {
					v1.le((IntVariable) o2);
				} else if (o2 instanceof Integer) {
					v1.le((Integer) o2);
				}
				break;
			case LT:
				if (o2 instanceof IntVariable) {
					v1.lt((IntVariable) o2);
				} else if (o2 instanceof Integer) {
					v1.lt((Integer) o2);
				}
				break;
			case NE:
				if (o2 instanceof IntVariable) {
					v1.notEquals((IntVariable) o2);
				} else if (o2 instanceof Integer) {
					v1.notEquals((Integer) o2);
				}
				break;
			}

			return null;
		}

		@Override
		public Object interpret(BinaryExpr expr, Object... args) {
			Object o1 = expr.getE1().accept(this);
			Object o2 = expr.getE2().accept(this);

			BinaryOp op = expr.getOp();
			if (negateConstraints && op.isComparison()) {
				op = op.getInverse();
			}

			if (o1 instanceof IntVariable) {
				IntVariable v1 = (IntVariable) o1;
				return addConstraint(v1, op, o2);
			} else {
				if (o2 instanceof IntVariable) {
					IntVariable v2 = (IntVariable) o2;
					return addConstraint(v2, op.getReversedInequality(), o1);
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

		/**
		 * the constraint variable created with the name and domain of the
		 * configuration port
		 */
		private IntVariable constraintVariable;

		/**
		 * whether constraints added by the constraint expression visitor should
		 * be negated
		 */
		private boolean negateConstraints;

		/**
		 * the constraint network
		 */
		private Network network;

		/**
		 * the variable loaded from the tokens array
		 */
		private Variable target;

		/**
		 * the variable that holds the tokens peeked on the configuration port
		 */
		private Variable tokens;

		/**
		 * Creates a guard visitor. This visitor visits the previous actions,
		 * and add negated constraints about the guards.
		 * 
		 * @param previous
		 */
		public GuardVisitor(List<Action> previous) {
			network = new Network();
			negateConstraints = true;
			for (Action action : previous) {
				visitAction(action, this);
			}

			negateConstraints = false;
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
					target, constraintVariable, negateConstraints);
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

				if (constraintVariable == null) {
					int lo;
					int hi;
					if (port.getType().getType() == Type.INT) {
						IntType type = (IntType) port.getType();
						Expression size = type.getSize();
						ExpressionEvaluator evaluator = new ExpressionEvaluator();
						int num = evaluator.evaluateAsInteger(size);
						lo = -(1 << (num - 1));
						hi = (1 << (num - 1)) - 1;
					} else if (port.getType().getType() == Type.UINT) {
						UintType type = (UintType) port.getType();
						Expression size = type.getSize();
						ExpressionEvaluator evaluator = new ExpressionEvaluator();
						int num = evaluator.evaluateAsInteger(size);
						lo = 0;
						hi = 1 << num - 1;
					} else {
						throw new OrccRuntimeException("integer ports only");
					}

					constraintVariable = new IntVariable(network, lo, hi, port
							.getName());
				}
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
			GuardVisitor visitor = new GuardVisitor(previous);
			visitAction(info.getAction(), visitor);
			previous.add(info.getAction());
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
