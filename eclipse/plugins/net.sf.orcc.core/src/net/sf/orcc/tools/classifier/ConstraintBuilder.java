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

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.ac.kobe_u.cs.cream.IntDomain;
import jp.ac.kobe_u.cs.cream.IntVariable;
import jp.ac.kobe_u.cs.cream.Network;
import net.sf.orcc.OrccRuntimeException;
import net.sf.orcc.interpreter.ActorInterpreter;
import net.sf.orcc.ir.Action;
import net.sf.orcc.ir.Actor;
import net.sf.orcc.ir.ExprBinary;
import net.sf.orcc.ir.ExprBool;
import net.sf.orcc.ir.ExprInt;
import net.sf.orcc.ir.ExprList;
import net.sf.orcc.ir.ExprString;
import net.sf.orcc.ir.ExprUnary;
import net.sf.orcc.ir.ExprVar;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.InstAssign;
import net.sf.orcc.ir.InstLoad;
import net.sf.orcc.ir.IrFactory;
import net.sf.orcc.ir.OpBinary;
import net.sf.orcc.ir.Pattern;
import net.sf.orcc.ir.Port;
import net.sf.orcc.ir.Type;
import net.sf.orcc.ir.TypeInt;
import net.sf.orcc.ir.TypeList;
import net.sf.orcc.ir.TypeUint;
import net.sf.orcc.ir.Var;
import net.sf.orcc.ir.util.ExpressionEvaluator;
import net.sf.orcc.ir.util.IrSwitch;

public class ConstraintBuilder extends ActorInterpreter {

	/**
	 * This class defines a visitor that examines expressions that depend on a
	 * value peeked from the configuration port.
	 * 
	 * @author Matthieu Wipliez
	 * 
	 */
	private class ConstraintExpressionVisitor extends IrSwitch<Object> {

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
		private Object addConstraint(IntVariable v1, OpBinary op, Object o2) {
			switch (op) {
			case BITAND:
				if (o2 instanceof ExprInt) {
					return v1.bitand(((ExprInt) o2).getIntValue());
				}
				break;
			case EQ:
				if (o2 instanceof IntVariable) {
					v1.equals((IntVariable) o2);
				} else if (o2 instanceof ExprInt) {
					v1.equals(((ExprInt) o2).getIntValue());
				}
				break;
			case GE:
				if (o2 instanceof IntVariable) {
					v1.ge((IntVariable) o2);
				} else if (o2 instanceof ExprInt) {
					v1.ge(((ExprInt) o2).getIntValue());
				}
				break;
			case GT:
				if (o2 instanceof IntVariable) {
					v1.gt((IntVariable) o2);
				} else if (o2 instanceof ExprInt) {
					v1.gt(((ExprInt) o2).getIntValue());
				}
				break;
			case LE:
				if (o2 instanceof IntVariable) {
					v1.le((IntVariable) o2);
				} else if (o2 instanceof ExprInt) {
					v1.le(((ExprInt) o2).getIntValue());
				}
				break;
			case LT:
				if (o2 instanceof IntVariable) {
					v1.lt((IntVariable) o2);
				} else if (o2 instanceof ExprInt) {
					v1.lt(((ExprInt) o2).getIntValue());
				}
				break;
			case NE:
				if (o2 instanceof IntVariable) {
					v1.notEquals((IntVariable) o2);
				} else if (o2 instanceof ExprInt) {
					v1.notEquals(((ExprInt) o2).getIntValue());
				}
				break;
			default:
				throw new OrccRuntimeException("unsupported binary operator");
			}

			return null;
		}

		@Override
		public Object caseExprBinary(ExprBinary expr) {
			Object o1 = doSwitch(expr.getE1());
			Object o2 = doSwitch(expr.getE2());

			OpBinary op = expr.getOp();
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
						return new ExpressionEvaluator().interpretBinaryExpr(
								(Expression) o1, op, (Expression) o2);
					}
				}
			}
		}

		@Override
		public Object caseExprList(ExprList expr) {
			throw new OrccRuntimeException("unsupported list expression");
		}

		@Override
		public Object caseExprString(ExprString expr) {
			throw new OrccRuntimeException("unsupported string expression");
		}

		@Override
		public Object caseExprUnary(ExprUnary expr) {
			switch (expr.getOp()) {
			case LOGIC_NOT: {
				Object obj = doSwitch(expr.getExpr());
				if (obj instanceof IntVariable) {
					return addConstraint((IntVariable) obj, OpBinary.EQ,
							IrFactory.eINSTANCE.createExprInt(0));
				}
				break;
			}

			case MINUS:
				Object obj = doSwitch(expr.getExpr());
				if (obj instanceof IntVariable) {
					return ((IntVariable) obj).negate();
				} else if (obj instanceof ExprInt) {
					return ((ExprInt) obj).negate();
				}
			}

			throw new OrccRuntimeException("unsupported unary operation");
		}

		@Override
		public Object caseExprVar(ExprVar expr) {
			Var var = expr.getUse().getVariable();
			if (var == null) {
				throw new OrccRuntimeException("unknown variable");
			}

			Var source = vars.get(var);
			if (source == null) {
				source = var;
			}

			// if the source is a constant retrieve its value
			if (source.isGlobal() && !source.isAssignable()) {
				Expression value = source.getInitialValue();
				if (value != null && value.isIntExpr()) {
					return value;
				}
			}

			return getIntVariable(var);
		}

	}

	private boolean negateConstraints;

	/**
	 * the constraint network
	 */
	private Network network;

	/**
	 * a map of name to constraint vars
	 */
	private Map<String, IntVariable> variableConstraints;

	/**
	 * a map of IR vars to constraint vars
	 */
	private Map<Var, Var> vars;

	private boolean initializeMode;

	public ConstraintBuilder(Actor actor) {
		super(new HashMap<String, Expression>(0), actor);
		network = new Network();
		variableConstraints = new HashMap<String, IntVariable>();
		vars = new HashMap<Var, Var>();
	}

	/**
	 * Associate the target local variable with the source variable.
	 * 
	 * @param target
	 *            a target local variable
	 * @param var
	 *            a state variable or a port
	 */
	private void associateVariable(Var target, Var var) {
		Var source = vars.get(target);
		if (source == null) {
			vars.put(target, var);
		}
	}

	/**
	 * Returns the domain of the given variable, or throws an exception.
	 * 
	 * @param var
	 *            a variable
	 * @return the domain of the given variable
	 */
	private IntDomain getDomain(Var var) {
		int lo;
		int hi;

		Expression value = var.getValue();
		Type type = var.getType();
		if (type.isList()) {
			type = ((TypeList) type).getElementType();
		}

		if (type.isInt()) {
			if (value != null && value.isIntExpr()) {
				lo = ((ExprInt) value).getIntValue();
				hi = lo;
			} else {
				int size = ((TypeInt) type).getSize();
				lo = -(1 << (size - 1));
				hi = (1 << (size - 1)) - 1;
			}
		} else if (type.isUint()) {
			if (value != null && value.isIntExpr()) {
				lo = ((ExprInt) value).getIntValue();
				hi = lo;
			} else {
				int size = ((TypeUint) type).getSize();
				lo = 0;
				hi = 1 << size - 1;
			}
		} else if (type.isBool()) {
			if (value != null && value.isBooleanExpr()) {
				lo = ((ExprBool) value).isValue() ? 1 : 0;
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

	private IntVariable getIntVariable(Var var) {
		Var source = vars.get(var);
		if (source == null) {
			source = var;
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

	@Override
	public void initialize() {
		initializeMode = true;
		super.initialize();
		initializeMode = false;
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
	public Object caseInstAssign(InstAssign assign) {
		if (initializeMode) {
			super.caseInstAssign(assign);
		} else {
			ConstraintExpressionVisitor visitor = new ConstraintExpressionVisitor();
			visitor.doSwitch(assign.getValue());
		}
		return null;
	}

	@Override
	public Object caseInstLoad(InstLoad load) {
		// execute the load
		super.caseInstLoad(load);

		if (!initializeMode) {
			Var source = load.getSource().getVariable();
			List<Expression> indexes = load.getIndexes();
			if (!indexes.isEmpty()) {
				if (indexes.size() != 1) {
					throw new OrccRuntimeException("loading multi-dimensional "
							+ "arrays not supported by constraint builder");
				}

				Expression index0 = indexes.get(0);
				if (!(index0.isIntExpr() && ((ExprInt) index0).getValue()
						.equals(BigInteger.ZERO))) {
					throw new OrccRuntimeException("loading arrays "
							+ "with index != 0 not supported");
				}
			}

			associateVariable(load.getTarget().getVariable(), source);
		}
		return null;
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
		// allocate patterns
		Pattern inputPattern = action.getInputPattern();
		Pattern outputPattern = action.getOutputPattern();
		allocatePattern(inputPattern);
		allocatePattern(outputPattern);

		Pattern pattern = action.getPeekPattern();
		for (Port port : pattern.getPorts()) {
			Var peeked = pattern.getVariable(port);
			if (peeked != null) {
				// allocate list for peeked
				peeked.setValue(listAllocator.doSwitch(peeked.getType()));

				// associate variable
				Var source = vars.get(peeked);
				if (source == null) {
					source = IrFactory.eINSTANCE.createVar(port.getType(),
							port.getName(), true, 0);
					vars.put(peeked, source);
				}
			}
		}

		doSwitch(action.getScheduler());
	}

}
