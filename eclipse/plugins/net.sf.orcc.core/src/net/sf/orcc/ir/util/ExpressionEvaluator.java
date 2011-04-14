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
package net.sf.orcc.ir.util;

import java.util.List;

import org.eclipse.emf.ecore.util.EcoreUtil;

import net.sf.orcc.OrccRuntimeException;
import net.sf.orcc.ir.ExprBinary;
import net.sf.orcc.ir.ExprBool;
import net.sf.orcc.ir.ExprFloat;
import net.sf.orcc.ir.ExprInt;
import net.sf.orcc.ir.ExprList;
import net.sf.orcc.ir.ExprString;
import net.sf.orcc.ir.ExprUnary;
import net.sf.orcc.ir.ExprVar;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.IrFactory;
import net.sf.orcc.ir.OpBinary;
import net.sf.orcc.ir.OpUnary;
import net.sf.orcc.ir.Var;

/**
 * This class defines an expression evaluator.
 * 
 * @author Pierre-Laurent Lagalaye
 * 
 */
public class ExpressionEvaluator extends IrSwitch<Expression> {

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
		Expression value = doSwitch(expr);
		if (value != null && value.isIntExpr()) {
			return ((ExprInt) value).getIntValue();
		}

		// evaluated ok, but not as an integer
		throw new OrccRuntimeException("expected integer expression");
	}

	@Override
	public Expression caseExprBinary(ExprBinary expr) {
		Expression val1 = doSwitch(expr.getE1());
		Expression val2 = doSwitch(expr.getE2());
		Expression result = interpretBinaryExpr(val1, expr.getOp(), val2);

		if (result == null) {
			// will throw exception if uninitialized variable used
			throwException = true;
			doSwitch(expr.getE1());
			doSwitch(expr.getE2());
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
	public Expression caseExprBool(ExprBool expr) {
		return EcoreUtil.copy(expr);
	}

	@Override
	public Expression caseExprFloat(ExprFloat expr) {
		return EcoreUtil.copy(expr);
	}

	@Override
	public Expression caseExprInt(ExprInt expr) {
		return EcoreUtil.copy(expr);
	}

	@Override
	public Expression caseExprList(ExprList expr) {
		ExprList list = IrFactory.eINSTANCE.createExprList();
		List<Expression> expressions = expr.getValue();
		List<Expression> values = list.getValue();
		for (Expression expression : expressions) {
			values.add(doSwitch(expression));
		}

		return list;
	}

	@Override
	public Expression caseExprString(ExprString expr) {
		return EcoreUtil.copy(expr);
	}

	@Override
	public Expression caseExprUnary(ExprUnary expr) {
		Expression value = doSwitch(expr.getExpr());
		Expression result = interpretUnaryExpr(expr.getOp(), value);

		if (result == null) {
			// will throw exception if uninitialized variable used
			throwException = true;
			doSwitch(expr.getExpr());
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
	public Expression caseExprVar(ExprVar expr) {
		Var var = expr.getUse().getVariable();
		Expression value = var.getValue();
		if (value == null) {
			value = var.getInitialValue();
		}
		if (value == null && throwException) {
			throwException = false;
			throw new OrccRuntimeException("Uninitialized variable: "
					+ var.getName());
		}
		return EcoreUtil.copy(value);
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
	public Expression interpretBinaryExpr(Expression val1, OpBinary op,
			Expression val2) {
		switch (op) {
		case BITAND:
			if (val1 != null && val1.isIntExpr() && val2 != null
					&& val2.isIntExpr()) {
				ExprInt i1 = (ExprInt) val1;
				ExprInt i2 = (ExprInt) val2;
				return i1.and(i2);
			}
			break;
		case BITOR:
			if (val1 != null && val1.isIntExpr() && val2 != null
					&& val2.isIntExpr()) {
				ExprInt i1 = (ExprInt) val1;
				ExprInt i2 = (ExprInt) val2;
				return i1.or(i2);
			}
			break;
		case BITXOR:
			if (val1 != null && val1.isIntExpr() && val2 != null
					&& val2.isIntExpr()) {
				ExprInt i1 = (ExprInt) val1;
				ExprInt i2 = (ExprInt) val2;
				return i1.xor(i2);
			}
			break;
		case DIV:
			if (val1 != null && val1.isIntExpr() && val2 != null
					&& val2.isIntExpr()) {
				ExprInt i1 = (ExprInt) val1;
				ExprInt i2 = (ExprInt) val2;
				return i1.divide(i2);
			}
			break;
		case DIV_INT:
			if (val1 != null && val1.isIntExpr() && val2 != null
					&& val2.isIntExpr()) {
				ExprInt i1 = (ExprInt) val1;
				ExprInt i2 = (ExprInt) val2;
				return i1.divide(i2);
			}
			break;
		case EQ:
			if (val1 != null && val1.isIntExpr() && val2 != null
					&& val2.isIntExpr()) {
				ExprInt i1 = (ExprInt) val1;
				ExprInt i2 = (ExprInt) val2;
				return IrFactory.eINSTANCE.createExprBool(i1.equals(i2));
			} else if (val1 != null && val1.isBooleanExpr() && val2 != null
					&& val2.isBooleanExpr()) {
				ExprInt b1 = (ExprInt) val1;
				ExprInt b2 = (ExprInt) val2;
				return IrFactory.eINSTANCE.createExprBool(b1.equals(b2));
			}
			break;
		case EXP:
			break;
		case GE:
			if (val1 != null && val1.isIntExpr() && val2 != null
					&& val2.isIntExpr()) {
				ExprInt i1 = (ExprInt) val1;
				ExprInt i2 = (ExprInt) val2;
				return i1.ge(i2);
			}
			break;
		case GT:
			if (val1 != null && val1.isIntExpr() && val2 != null
					&& val2.isIntExpr()) {
				ExprInt i1 = (ExprInt) val1;
				ExprInt i2 = (ExprInt) val2;
				return i1.gt(i2);
			}
			break;
		case LOGIC_AND:
			if (val1 != null && val1.isBooleanExpr() && val2 != null
					&& val2.isBooleanExpr()) {
				ExprBool b1 = (ExprBool) val1;
				ExprBool b2 = (ExprBool) val2;
				return IrFactory.eINSTANCE.createExprBool(b1.isValue()
						&& b2.isValue());
			}
			break;
		case LE:
			if (val1 != null && val1.isIntExpr() && val2 != null
					&& val2.isIntExpr()) {
				ExprInt i1 = (ExprInt) val1;
				ExprInt i2 = (ExprInt) val2;
				return i1.le(i2);
			}
			break;
		case LOGIC_OR:
			if (val1 != null && val1.isBooleanExpr() && val2 != null
					&& val2.isBooleanExpr()) {
				ExprBool b1 = (ExprBool) val1;
				ExprBool b2 = (ExprBool) val2;
				return IrFactory.eINSTANCE.createExprBool(b1.isValue()
						|| b2.isValue());
			}
			break;
		case LT:
			if (val1 != null && val1.isIntExpr() && val2 != null
					&& val2.isIntExpr()) {
				ExprInt i1 = (ExprInt) val1;
				ExprInt i2 = (ExprInt) val2;
				return i1.lt(i2);
			}
			break;
		case MINUS:
			if (val1 != null && val1.isIntExpr() && val2 != null
					&& val2.isIntExpr()) {
				ExprInt i1 = (ExprInt) val1;
				ExprInt i2 = (ExprInt) val2;
				return i1.subtract(i2);
			}
			break;
		case MOD:
			if (val1 != null && val1.isIntExpr() && val2 != null
					&& val2.isIntExpr()) {
				ExprInt i1 = (ExprInt) val1;
				ExprInt i2 = (ExprInt) val2;
				return i1.mod(i2);
			}
			break;
		case NE:
			if (val1 != null && val1.isIntExpr() && val2 != null
					&& val2.isIntExpr()) {
				ExprInt i1 = (ExprInt) val1;
				ExprInt i2 = (ExprInt) val2;
				return IrFactory.eINSTANCE.createExprBool(!i1.equals(i2));
			} else if (val1 != null && val1.isBooleanExpr() && val2 != null
					&& val2.isBooleanExpr()) {
				ExprBool b1 = (ExprBool) val1;
				ExprBool b2 = (ExprBool) val2;
				return IrFactory.eINSTANCE.createExprBool(!b1.equals(b2));
			}
			break;
		case PLUS:
			if (val1 != null && val1.isIntExpr() && val2 != null
					&& val2.isIntExpr()) {
				ExprInt i1 = (ExprInt) val1;
				ExprInt i2 = (ExprInt) val2;
				return i1.add(i2);
			}

			if (val1 != null && val1.isListExpr() && val2 != null
					&& val2.isListExpr()) {
				ExprList l1 = (ExprList) val1;
				ExprList l2 = (ExprList) val2;
				return IrFactory.eINSTANCE.createExprList(l1, l2);
			}
			break;
		case SHIFT_LEFT:
			if (val1 != null && val1.isIntExpr() && val2 != null
					&& val2.isIntExpr()) {
				ExprInt i1 = (ExprInt) val1;
				ExprInt i2 = (ExprInt) val2;
				return i1.shiftLeft(i2);
			}
			break;
		case SHIFT_RIGHT:
			if (val1 != null && val1.isIntExpr() && val2 != null
					&& val2.isIntExpr()) {
				ExprInt i1 = (ExprInt) val1;
				ExprInt i2 = (ExprInt) val2;
				return i1.shiftRight(i2);
			}
			break;
		case TIMES:
			if (val1 != null && val1.isIntExpr() && val2 != null
					&& val2.isIntExpr()) {
				ExprInt i1 = (ExprInt) val1;
				ExprInt i2 = (ExprInt) val2;
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
	public Expression interpretUnaryExpr(OpUnary op, Expression value) {
		switch (op) {
		case BITNOT:
			if (value != null && value.isIntExpr()) {
				return ((ExprInt) value).not();
			}
			break;
		case LOGIC_NOT:
			if (value != null && value.isBooleanExpr()) {
				return ((ExprBool) value).not();
			}
			break;
		case MINUS:
			if (value != null && value.isIntExpr()) {
				return ((ExprInt) value).negate();
			}
			break;
		case NUM_ELTS:
			break;
		}

		return null;
	}

}
