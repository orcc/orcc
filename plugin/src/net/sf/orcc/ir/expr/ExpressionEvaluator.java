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

import net.sf.orcc.OrccRuntimeException;
import net.sf.orcc.ir.Expression;

/**
 * This class defines an expression evaluator.
 * 
 * @author Pierre-Laurent Lagalaye
 * 
 */
public class ExpressionEvaluator implements ExpressionInterpreter {

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
		Object value = expr.accept(this, Integer.MIN_VALUE);
		if (value instanceof Integer) {
			return (Integer) value;
		}

		// evaluated ok, but not as an integer
		throw new OrccRuntimeException("expected integer expression");
	}

	@Override
	public Object interpret(BinaryExpr expr, Object... args) {
		Object val1 = expr.getE1().accept(this);
		Object val2 = expr.getE2().accept(this);
		switch (expr.getOp()) {
		case BITAND:
			if (val1 instanceof Integer && val2 instanceof Integer) {
				int i1 = (Integer) val1;
				int i2 = (Integer) val2;
				return i1 & i2;
			}
			break;
		case BITOR:
			if (val1 instanceof Integer && val2 instanceof Integer) {
				int i1 = (Integer) val1;
				int i2 = (Integer) val2;
				return i1 | i2;
			}
			break;
		case BITXOR:
			if (val1 instanceof Integer && val2 instanceof Integer) {
				int i1 = (Integer) val1;
				int i2 = (Integer) val2;
				return i1 ^ i2;
			}
			break;
		case DIV:
			if (val1 instanceof Integer && val2 instanceof Integer) {
				int i1 = (Integer) val1;
				int i2 = (Integer) val2;
				return i1 / i2;
			}
			break;
		case DIV_INT:
			if (val1 instanceof Integer && val2 instanceof Integer) {
				int i1 = (Integer) val1;
				int i2 = (Integer) val2;
				return i1 / i2;
			}
			break;
		case EQ:
			if (val1 instanceof Integer && val2 instanceof Integer) {
				int i1 = (Integer) val1;
				int i2 = (Integer) val2;
				return i1 == i2;
			}
			break;
		case EXP:
			break;
		case GE:
			if (val1 instanceof Integer && val2 instanceof Integer) {
				int i1 = (Integer) val1;
				int i2 = (Integer) val2;
				return i1 >= i2;
			}
			break;
		case GT:
			if (val1 instanceof Integer && val2 instanceof Integer) {
				int i1 = (Integer) val1;
				int i2 = (Integer) val2;
				return i1 > i2;
			}
			break;
		case LOGIC_AND:
			if (val1 instanceof Boolean && val2 instanceof Boolean) {
				boolean b1 = (Boolean) val1;
				boolean b2 = (Boolean) val2;
				return b1 && b2;
			}
			break;
		case LE:
			if (val1 instanceof Integer && val2 instanceof Integer) {
				int i1 = (Integer) val1;
				int i2 = (Integer) val2;
				return i1 <= i2;
			}
			break;
		case LOGIC_OR:
			if (val1 instanceof Boolean && val2 instanceof Boolean) {
				boolean b1 = (Boolean) val1;
				boolean b2 = (Boolean) val2;
				return b1 || b2;
			}
			break;
		case LT:
			if (val1 instanceof Integer && val2 instanceof Integer) {
				int i1 = (Integer) val1;
				int i2 = (Integer) val2;
				return i1 < i2;
			}
			break;
		case MINUS:
			if (val1 instanceof Integer && val2 instanceof Integer) {
				int i1 = (Integer) val1;
				int i2 = (Integer) val2;
				return i1 - i2;
			}
			break;
		case MOD:
			if (val1 instanceof Integer && val2 instanceof Integer) {
				int i1 = (Integer) val1;
				int i2 = (Integer) val2;
				return i1 % i2;
			}
			break;
		case NE:
			if (val1 instanceof Integer && val2 instanceof Integer) {
				int i1 = (Integer) val1;
				int i2 = (Integer) val2;
				return i1 != i2;
			}
			break;
		case PLUS:
			if (val1 instanceof Integer && val2 instanceof Integer) {
				int i1 = (Integer) val1;
				int i2 = (Integer) val2;
				return i1 + i2;
			}
			break;
		case SHIFT_LEFT:
			if (val1 instanceof Integer && val2 instanceof Integer) {
				int i1 = (Integer) val1;
				int i2 = (Integer) val2;
				return i1 << i2;
			}
			break;
		case SHIFT_RIGHT:
			if (val1 instanceof Integer && val2 instanceof Integer) {
				int i1 = (Integer) val1;
				int i2 = (Integer) val2;
				return i1 >> i2;
			}
			break;
		case TIMES:
			if (val1 instanceof Integer && val2 instanceof Integer) {
				int i1 = (Integer) val1;
				int i2 = (Integer) val2;
				return i1 * i2;
			}
			break;
		}

		throw new OrccRuntimeException(
				"could not evaluate binary expression with OP="
						+ expr.getOp().toString() + "("
						+ expr.getOp().getText() + ") and E1=" + val1 + "; E2="
						+ val2);
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
		throw new OrccRuntimeException("can not evaluate List expression");
	}

	@Override
	public Object interpret(StringExpr expr, Object... args) {
		return expr.getValue();
	}

	@Override
	public Object interpret(UnaryExpr expr, Object... args) {
		Object value = expr.getExpr().accept(this, Integer.MIN_VALUE);
		switch (expr.getOp()) {
		case BITNOT:
			if (value instanceof Integer) {
				int i = (Integer) value;
				return ~i;
			}
			break;
		case LOGIC_NOT:
			if (value instanceof Boolean) {
				boolean b = (Boolean) value;
				return !b;
			}
			break;
		case MINUS:
			if (value instanceof Integer) {
				int i = (Integer) value;
				return -i;
			}
			break;
		case NUM_ELTS:
			break;
		}

		throw new OrccRuntimeException(
				"could not evaluate unary expression with OP="
						+ expr.getOp().toString() + "("
						+ expr.getOp().getText() + ")");
	}

	@Override
	public Object interpret(VarExpr expr, Object... args) {
		return expr.getVar().getVariable().getValue();
	}
}
