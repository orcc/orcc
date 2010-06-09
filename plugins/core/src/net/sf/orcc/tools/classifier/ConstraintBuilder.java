/*
 * Copyright (c) 2010, IETR/INSA of Rennes
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.ac.kobe_u.cs.cream.IntDomain;
import jp.ac.kobe_u.cs.cream.IntVariable;
import jp.ac.kobe_u.cs.cream.Network;
import net.sf.orcc.OrccRuntimeException;
import net.sf.orcc.ir.Action;
import net.sf.orcc.ir.CFGNode;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.Procedure;
import net.sf.orcc.ir.Type;
import net.sf.orcc.ir.Variable;
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
import net.sf.orcc.ir.type.IntType;
import net.sf.orcc.ir.type.ListType;
import net.sf.orcc.ir.type.UintType;

public class ConstraintBuilder extends AbstractNodeInterpreter {

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

			Type type = variable.getType();
			Object value = variable.getValue();
			if (value != null && (type.isInt() || type.isUint())) {
				return value;
			} else {
				return getIntVariable(variable);
			}
		}

	}

	private boolean negateConstraints;

	/**
	 * the constraint network
	 */
	private Network network;

	/**
	 * a map of name to constraint variables
	 */
	private Map<String, IntVariable> variableConstraints;

	/**
	 * a map of IR variables to constraint variables
	 */
	private Map<Variable, Variable> variables;

	public ConstraintBuilder() {
		network = new Network();
		variableConstraints = new HashMap<String, IntVariable>();
		variables = new HashMap<Variable, Variable>();
	}

	/**
	 * Associate the target local variable with the source variable.
	 * 
	 * @param target
	 *            a target local variable
	 * @param variable
	 *            a state variable or a port
	 */
	private void associateVariable(Variable target, Variable variable) {
		Variable source = variables.get(target);
		if (source == null) {
			variables.put(target, variable);
		}
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

		Object value = variable.getValue();
		Type type = variable.getType();
		if (type.isList()) {
			type = ((ListType) type).getElementType();
		}

		if (type.isInt()) {
			if (value instanceof Integer) {
				lo = (Integer) value;
				hi = lo;
			} else {
				int size = ((IntType) type).getSize();
				lo = -(1 << (size - 1));
				hi = (1 << (size - 1)) - 1;
			}
		} else if (type.isUint()) {
			if (value instanceof Integer) {
				lo = (Integer) value;
				hi = lo;
			} else {
				int size = ((UintType) type).getSize();
				lo = 0;
				hi = 1 << size - 1;
			}
		} else if (type.isBool()) {
			if (value instanceof Boolean) {
				lo = ((Boolean) value) ? 1 : 0;
				hi = lo;
			} else {
				lo = 0;
				hi = 1;
			}
		} else {
			throw new OrccRuntimeException("type of variable not supported");
		}

		return new IntDomain(lo, hi);
	}

	private IntVariable getIntVariable(Variable variable) {
		Variable source = variables.get(variable);
		if (source == null) {
			source = variable;
		}

		IntVariable intVar = variableConstraints.get(source.getName());
		if (intVar == null) {
			// create int variable associated with given variable
			// variable may be a state variable or a port
			intVar = new IntVariable(network, getDomain(source),
					source.getName());
			variableConstraints.put(intVar.getName(), intVar);
		}

		return intVar;
	}

	/**
	 * Returns the network of constraints associated with this constraint
	 * builder.
	 * 
	 * @return the network of constraints associated with this constraint
	 *         builder
	 */
	public Network getNetwork() {
		return network;
	}

	/**
	 * Returns the constraint variable with the given name.
	 * 
	 * @return the constraint variable with the given name
	 */
	public IntVariable getVariable(String name) {
		return variableConstraints.get(name);
	}

	/**
	 * Sets the negateConstraints flag.
	 * 
	 * @param negateConstraints
	 *            if <code>true</code>, constraints created will be negated
	 */
	public void setNegateConstraints(boolean negateConstraints) {
		this.negateConstraints = negateConstraints;
	}

	@Override
	public void visit(Assign assign, Object... args) {
		ConstraintExpressionVisitor visitor = new ConstraintExpressionVisitor();
		assign.getValue().accept(visitor);
	}

	@Override
	public void visit(Load load, Object... args) {
		// execute the load
		super.visit(load);

		Variable source = load.getSource().getVariable();
		List<Expression> indexes = load.getIndexes();
		if (!indexes.isEmpty()) {
			if (indexes.size() != 1) {
				throw new OrccRuntimeException("loading multi-dimensional "
						+ "arrays not supported by constraint builder");
			}
			if (!indexes.get(0).equals(new IntExpr(0))) {
				throw new OrccRuntimeException("loading arrays "
						+ "with index != 0 not supported");
			}
		}

		associateVariable(load.getTarget(), source);
	}

	@Override
	public void visit(Peek peek, Object... args) {
		associateVariable(peek.getTarget(), peek.getPort());
	}

	/**
	 * Visits the given action with the given visitor.
	 * 
	 * @param action
	 *            action associated with the next state
	 * @param visitor
	 *            a node visitor
	 */
	public void visitAction(Action action) {
		Procedure scheduler = action.getScheduler();
		for (CFGNode node : scheduler.getNodes()) {
			node.accept(this);
		}
	}

}
