/*
 * Copyright (c) 2009-2010, IETR/INSA of Rennes
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
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.FSM;
import net.sf.orcc.ir.LocalVariable;
import net.sf.orcc.ir.Pattern;
import net.sf.orcc.ir.Procedure;
import net.sf.orcc.ir.Type;
import net.sf.orcc.ir.Variable;
import net.sf.orcc.ir.FSM.NextStateInfo;
import net.sf.orcc.ir.FSM.Transition;
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
public class TimeDependencyAnalyzer {

	/**
	 * This class defines a visitor that examines expressions that depend on a
	 * value peeked from the configuration port.
	 * 
	 * @author Matthieu Wipliez
	 * 
	 */
	private class ConstraintExpressionVisitor implements ExpressionInterpreter {

		/**
		 * Creates a new constraint expression visitor.
		 * 
		 */
		public ConstraintExpressionVisitor() {
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
			default:
				throw new OrccRuntimeException("unsupported binary operator");
			}

			return null;
		}

		@Override
		public Object interpret(BinaryExpr expr, Object... args) {
			Object o1 = expr.getE1().accept(this);
			Object o2 = expr.getE2().accept(this);

			BinaryOp op = expr.getOp();
			if (o1 instanceof IntVariable) {
				IntVariable v1 = (IntVariable) o1;
				return addConstraint(v1, op, o2);
			} else {
				if (o2 instanceof IntVariable) {
					IntVariable v2 = (IntVariable) o2;
					return addConstraint(v2, op.getReversedInequality(), o1);
				} else {
					switch (op) {
					case LOGIC_AND:
						return null;
					default:
						throw new OrccRuntimeException(
								"no variable in expression");
					}
				}
			}
		}

		@Override
		public Object interpret(BoolExpr expr, Object... args) {
			return expr.getValue();
		}

		@Override
		public Object interpret(IntExpr expr, Object... args) {
			return expr.getValue();
		}

		@Override
		public Object interpret(ListExpr expr, Object... args) {
			throw new OrccRuntimeException("unsupported list expression");
		}

		@Override
		public Object interpret(StringExpr expr, Object... args) {
			throw new OrccRuntimeException("unsupported string expression");
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

			throw new OrccRuntimeException("unsupported unary operation");
		}

		@Override
		public Object interpret(VarExpr expr, Object... args) {
			Variable variable = expr.getVar().getVariable();
			if (variable == null) {
				throw new OrccRuntimeException("unknown variable");
			}
			return variables.get(variable);
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
		 * Creates a guard visitor.
		 * 
		 */
		public GuardVisitor() {
		}

		/**
		 * Associate the target local variable with an int variable that has the
		 * characteristics (name, domain) of the given variable.
		 * 
		 * @param target
		 *            a target local variable
		 * @param variable
		 *            a state variable or a port
		 */
		private void associateVariable(LocalVariable target, Variable variable) {
			IntVariable intVar = variables.get(variable);
			if (intVar == null) {
				// create int variable associated with given variable
				// variable may be a state variable or a port
				intVar = new IntVariable(network, getDomain(variable), variable
						.getName());
				variables.put(variable, intVar);
			}

			// associate int variable with local variable
			variables.put(target, intVar);
		}

		/**
		 * Returns the domain of the given variable, or throws an exception.
		 * 
		 * @param variable
		 *            a variable
		 * @return the domain of the given variable
		 */
		private IntDomain getDomain(Variable variable) {
			int lo;
			int hi;

			if (variable.getType().getType() == Type.INT) {
				IntType type = (IntType) variable.getType();
				Expression size = type.getSize();
				ExpressionEvaluator evaluator = new ExpressionEvaluator();
				int num = evaluator.evaluateAsInteger(size);
				lo = -(1 << (num - 1));
				hi = (1 << (num - 1)) - 1;
			} else if (variable.getType().getType() == Type.UINT) {
				UintType type = (UintType) variable.getType();
				Expression size = type.getSize();
				ExpressionEvaluator evaluator = new ExpressionEvaluator();
				int num = evaluator.evaluateAsInteger(size);
				lo = 0;
				hi = 1 << num - 1;
			} else if (variable.getType().getType() == Type.BOOLEAN) {
				lo = 0;
				hi = 1;
			} else {
				throw new OrccRuntimeException("type of variable not supported");
			}

			return new IntDomain(lo, hi);
		}

		@Override
		public void visit(Assign assign, Object... args) {
			ConstraintExpressionVisitor visitor = new ConstraintExpressionVisitor();
			assign.getValue().accept(visitor);
		}

		@Override
		public void visit(Load load, Object... args) {
			associateVariable(load.getTarget(), load.getSource().getVariable());
		}

		@Override
		public void visit(Peek peek, Object... args) {
			associateVariable(peek.getTarget(), peek.getPort());
		}

	}

	private Actor actor;

	/**
	 * the constraint network
	 */
	private Network network;

	/**
	 * a map of IR variables to constraint variables
	 */
	private Map<Variable, IntVariable> variables;

	/**
	 * Creates a new configuration analyzer for the given actor
	 * 
	 * @param actor
	 *            an actor
	 */
	public TimeDependencyAnalyzer(Actor actor) {
		this.actor = actor;
	}

	/**
	 * Tries to evaluate the guards to check if they are compatible.
	 * 
	 * @param previous
	 *            the action that occurs before <code>action</code>
	 * @param action
	 *            an action
	 * @return <code>true</code> if the guards of the given actions are
	 *         compatible
	 */
	private boolean areGuardsCompatible(Action previous, Action action) {
		network = new Network();
		variables = new HashMap<Variable, IntVariable>();

		try {
			GuardVisitor visitor = new GuardVisitor();
			visitAction(previous, visitor);
			visitAction(action, visitor);
		} catch (OrccRuntimeException e) {
			System.out.println(actor + ": could not evaluate guards");
			return true;
		}

		DefaultSolver solver = new DefaultSolver(network);
		Solution solution = solver.findFirst();
		if (solution != null) {
			System.out.println(actor + ": guards are compatible");
			return true;
		}

		return false;
	}

	/**
	 * Returns <code>true</code> if this actor has a time-dependent behavior.
	 * 
	 * @return <code>true</code> if this actor has a time-dependent behavior
	 */
	public boolean isTimeDependent() {
		ActionScheduler sched = actor.getActionScheduler();
		if (sched.hasFsm()) {
			FSM fsm = sched.getFsm();
			for (Transition transition : fsm.getTransitions()) {
				List<Action> actions = new ArrayList<Action>();
				for (NextStateInfo stateInfo : transition.getNextStateInfo()) {
					actions.add(stateInfo.getAction());
				}

				if (isTimeDependent(actions)) {
					return true;
				}
			}

			return false;
		} else {
			return isTimeDependent(sched.getActions());
		}
	}

	/**
	 * Returns <code>true</code> if the given action list has a time-dependent
	 * behavior.
	 * 
	 * @param actions
	 *            a list of actions
	 * @return <code>true</code> if the given action list has a time-dependent
	 *         behavior
	 */
	private boolean isTimeDependent(List<Action> actions) {
		Iterator<Action> it = actions.iterator();
		if (it.hasNext()) {
			Action previous = it.next();
			Pattern input = previous.getInputPattern();
			while (it.hasNext()) {
				Action action = it.next();
				Pattern newInput = action.getInputPattern();
				if (input.isSubsetOf(newInput)) {
					// because the next action must have a pattern which is
					// a superset of (or equal to) this one
					input = newInput;
				} else {
					// the new pattern is not a superset of (or equal to)
					// this one, this means time-dependent behavior
					if (areGuardsCompatible(previous, action)) {
						return true;
					}
				}

				previous = action;
			}
		}

		return false;
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
