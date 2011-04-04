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
package net.sf.orcc.ir.expr;

import java.util.ArrayList;
import java.util.List;

import net.sf.orcc.OrccRuntimeException;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.Var;

/**
 * This class defines an expression evaluator.
 * 
 * @author Pierre-Laurent Lagalaye
 * 
 */
public class ExpressionEvaluator extends AbstractExpressionInterpreter {

	private boolean throwException;

	/**
	 * Evaluates this expression and return its value as an integer.
	 * 
	 * @param expr
	 *            an expression to evaluate
	 * @return the expression evaluated as an integer
	 * @throws OrccRuntimeException
	 *             if the expression cannot be evaluated as an integer
	 */
	public int evaluateAsInteger(Expression expr) {
		Expression value = (Expression) expr.accept(this);
		if (value != null && value.isIntExpr()) {
			return ((IntExpr) value).getIntValue();
		}

		// evaluated ok, but not as an integer
		throw new OrccRuntimeException("expected integer expression");
	}

	@Override
	public Object interpret(BinaryExpr expr, Object... args) {
		Expression val1 = (Expression) expr.getE1().accept(this);
		Expression val2 = (Expression) expr.getE2().accept(this);
		Expression result = interpretBinaryExpr(val1, expr.getOp(), val2);

		if (result == null) {
			// will throw exception if uninitialized variable used
			throwException = true;
			expr.getE1().accept(this);
			expr.getE2().accept(this);
			throwException = false;

			// if no exception has been thrown, throw it now
			throw new OrccRuntimeException(
					"Could not evaluate binary expression " + " expr " + expr
							+ " " + expr.getOp().toString() + "(" + " exp1: "
							+ expr.getE1() + " = " + val1
							+ expr.getOp().getText() + " exp2: " + expr.getE2()
							+ " = " + val2 + ")\n");
		}
		return result;
	}

	@Override
	public Object interpret(ListExpr expr, Object... args) {
		List<Expression> expressions = expr.getValue();
		List<Expression> values = new ArrayList<Expression>(expressions.size());
		for (Expression expression : expressions) {
			values.add((Expression) expression.accept(this));
		}

		return new ListExpr(values);
	}

	@Override
	public Object interpret(UnaryExpr expr, Object... args) {
		Expression value = (Expression) expr.getExpr().accept(this);
		Expression result = interpretUnaryExpr(expr.getOp(), value);

		if (result == null) {
			// will throw exception if uninitialized variable used
			throwException = true;
			expr.getExpr().accept(this);
			throwException = false;

			// if no exception has been thrown, throw it now
			throw new OrccRuntimeException(
					"Could not evaluate unary expression " + "expr "
							+ expr.getOp().toString() + "("
							+ expr.getOp().getText() + ")\n");
		}
		return result;
	}

	@Override
	public Object interpret(VarExpr expr, Object... args) {
		Var var = expr.getVar().getVariable();
		Expression value = var.getValue();
		if (value == null && throwException) {
			throwException = false;
			throw new OrccRuntimeException("Uninitialized variable: "
					+ var.getName());
		}
		return value;
	}

	/**
	 * Returns the value of <code>val1</code> <code>op</code> <code>val2</code>.
	 * Returns <code>null</code> if the value of the expression cannot be
	 * computed.
	 * 
	 * @param val1
	 *            an expression
	 * @param op
	 *            a binary operator
	 * @param val2
	 *            another expression
	 * @return the value of <code>val1</code> <code>op</code> <code>val2</code>
	 */
	public Expression interpretBinaryExpr(Expression val1, BinaryOp op,
			Expression val2) {
		switch (op) {
		case BITAND:
			if (val1 != null && val1.isIntExpr() && val2 != null
					&& val2.isIntExpr()) {
				IntExpr i1 = (IntExpr) val1;
				IntExpr i2 = (IntExpr) val2;
				return i1.and(i2);
			}
			break;
		case BITOR:
			if (val1 != null && val1.isIntExpr() && val2 != null
					&& val2.isIntExpr()) {
				IntExpr i1 = (IntExpr) val1;
				IntExpr i2 = (IntExpr) val2;
				return i1.or(i2);
			}
			break;
		case BITXOR:
			if (val1 != null && val1.isIntExpr() && val2 != null
					&& val2.isIntExpr()) {
				IntExpr i1 = (IntExpr) val1;
				IntExpr i2 = (IntExpr) val2;
				return i1.xor(i2);
			}
			break;
		case DIV:
			if (val1 != null && val1.isIntExpr() && val2 != null
					&& val2.isIntExpr()) {
				IntExpr i1 = (IntExpr) val1;
				IntExpr i2 = (IntExpr) val2;
				return i1.divide(i2);
			}
			break;
		case DIV_INT:
			if (val1 != null && val1.isIntExpr() && val2 != null
					&& val2.isIntExpr()) {
				IntExpr i1 = (IntExpr) val1;
				IntExpr i2 = (IntExpr) val2;
				return i1.divide(i2);
			}
			break;
		case EQ:
			if (val1 != null && val1.isIntExpr() && val2 != null
					&& val2.isIntExpr()) {
				IntExpr i1 = (IntExpr) val1;
				IntExpr i2 = (IntExpr) val2;
				return new BoolExpr(i1.equals(i2));
			} else if (val1 != null && val1.isBooleanExpr() && val2 != null
					&& val2.isBooleanExpr()) {
				BoolExpr b1 = (BoolExpr) val1;
				BoolExpr b2 = (BoolExpr) val2;
				return new BoolExpr(b1.equals(b2));
			}
			break;
		case EXP:
			break;
		case GE:
			if (val1 != null && val1.isIntExpr() && val2 != null
					&& val2.isIntExpr()) {
				IntExpr i1 = (IntExpr) val1;
				IntExpr i2 = (IntExpr) val2;
				return i1.ge(i2);
			}
			break;
		case GT:
			if (val1 != null && val1.isIntExpr() && val2 != null
					&& val2.isIntExpr()) {
				IntExpr i1 = (IntExpr) val1;
				IntExpr i2 = (IntExpr) val2;
				return i1.gt(i2);
			}
			break;
		case LOGIC_AND:
			if (val1 != null && val1.isBooleanExpr() && val2 != null
					&& val2.isBooleanExpr()) {
				BoolExpr b1 = (BoolExpr) val1;
				BoolExpr b2 = (BoolExpr) val2;
				return new BoolExpr(b1.getValue() && b2.getValue());
			}
			break;
		case LE:
			if (val1 != null && val1.isIntExpr() && val2 != null
					&& val2.isIntExpr()) {
				IntExpr i1 = (IntExpr) val1;
				IntExpr i2 = (IntExpr) val2;
				return i1.le(i2);
			}
			break;
		case LOGIC_OR:
			if (val1 != null && val1.isBooleanExpr() && val2 != null
					&& val2.isBooleanExpr()) {
				BoolExpr b1 = (BoolExpr) val1;
				BoolExpr b2 = (BoolExpr) val2;
				return new BoolExpr(b1.getValue() || b2.getValue());
			}
			break;
		case LT:
			if (val1 != null && val1.isIntExpr() && val2 != null
					&& val2.isIntExpr()) {
				IntExpr i1 = (IntExpr) val1;
				IntExpr i2 = (IntExpr) val2;
				return i1.lt(i2);
			}
			break;
		case MINUS:
			if (val1 != null && val1.isIntExpr() && val2 != null
					&& val2.isIntExpr()) {
				IntExpr i1 = (IntExpr) val1;
				IntExpr i2 = (IntExpr) val2;
				return i1.subtract(i2);
			}
			break;
		case MOD:
			if (val1 != null && val1.isIntExpr() && val2 != null
					&& val2.isIntExpr()) {
				IntExpr i1 = (IntExpr) val1;
				IntExpr i2 = (IntExpr) val2;
				return i1.mod(i2);
			}
			break;
		case NE:
			if (val1 != null && val1.isIntExpr() && val2 != null
					&& val2.isIntExpr()) {
				IntExpr i1 = (IntExpr) val1;
				IntExpr i2 = (IntExpr) val2;
				return new BoolExpr(!i1.equals(i2));
			} else if (val1 != null && val1.isBooleanExpr() && val2 != null
					&& val2.isBooleanExpr()) {
				BoolExpr b1 = (BoolExpr) val1;
				BoolExpr b2 = (BoolExpr) val2;
				return new BoolExpr(!b1.equals(b2));
			}
			break;
		case PLUS:
			if (val1 != null && val1.isIntExpr() && val2 != null
					&& val2.isIntExpr()) {
				IntExpr i1 = (IntExpr) val1;
				IntExpr i2 = (IntExpr) val2;
				return i1.add(i2);
			}

			if (val1 != null && val1.isListExpr() && val2 != null
					&& val2.isListExpr()) {
				ListExpr l1 = (ListExpr) val1;
				ListExpr l2 = (ListExpr) val2;
				return new ListExpr(l1, l2);
			}
			break;
		case SHIFT_LEFT:
			if (val1 != null && val1.isIntExpr() && val2 != null
					&& val2.isIntExpr()) {
				IntExpr i1 = (IntExpr) val1;
				IntExpr i2 = (IntExpr) val2;
				return i1.shiftLeft(i2);
			}
			break;
		case SHIFT_RIGHT:
			if (val1 != null && val1.isIntExpr() && val2 != null
					&& val2.isIntExpr()) {
				IntExpr i1 = (IntExpr) val1;
				IntExpr i2 = (IntExpr) val2;
				return i1.shiftRight(i2);
			}
			break;
		case TIMES:
			if (val1 != null && val1.isIntExpr() && val2 != null
					&& val2.isIntExpr()) {
				IntExpr i1 = (IntExpr) val1;
				IntExpr i2 = (IntExpr) val2;
				return i1.multiply(i2);
			}
			break;
		}

		return null;
	}

	/**
	 * Returns the value of <code>op</code> <code>value</code>. Returns
	 * <code>null</code> if the value of the expression cannot be computed.
	 * 
	 * @param op
	 *            a unary operator
	 * @param value
	 *            an expression
	 * @return the value of <code>op</code> <code>value</code>
	 */
	public Expression interpretUnaryExpr(UnaryOp op, Expression value) {
		switch (op) {
		case BITNOT:
			if (value != null && value.isIntExpr()) {
				return ((IntExpr) value).not();
			}
			break;
		case LOGIC_NOT:
			if (value != null && value.isBooleanExpr()) {
				return ((BoolExpr) value).not();
			}
			break;
		case MINUS:
			if (value != null && value.isIntExpr()) {
				return ((IntExpr) value).negate();
			}
			break;
		case NUM_ELTS:
			break;
		}

		return null;
	}

}
