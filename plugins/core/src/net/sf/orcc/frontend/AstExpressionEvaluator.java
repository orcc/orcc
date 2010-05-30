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
package net.sf.orcc.frontend;

import java.util.HashMap;
import java.util.Map;

import net.sf.orcc.OrccRuntimeException;
import net.sf.orcc.cal.cal.AstExpression;
import net.sf.orcc.cal.cal.AstExpressionBinary;
import net.sf.orcc.cal.cal.AstExpressionBoolean;
import net.sf.orcc.cal.cal.AstExpressionInteger;
import net.sf.orcc.cal.cal.AstExpressionString;
import net.sf.orcc.cal.cal.AstExpressionUnary;
import net.sf.orcc.cal.cal.AstExpressionVariable;
import net.sf.orcc.cal.cal.AstVariable;
import net.sf.orcc.cal.cal.util.CalSwitch;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.Location;
import net.sf.orcc.ir.expr.BinaryOp;
import net.sf.orcc.ir.expr.IntExpr;
import net.sf.orcc.ir.expr.UnaryOp;

/**
 * This class defines an expression evaluator.
 * 
 * @author Pierre-Laurent Lagalaye
 * 
 */
public class AstExpressionEvaluator extends CalSwitch<Object> {

	private Map<AstVariable, Object> values;

	public AstExpressionEvaluator() {
		this.values = new HashMap<AstVariable, Object>();
	}

	@Override
	public Object caseAstExpressionBinary(AstExpressionBinary expression) {
		BinaryOp op = BinaryOp.getOperator(expression.getOperator());
		Object val1 = doSwitch(expression.getLeft());
		Object val2 = doSwitch(expression.getRight());

		switch (op) {
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
			} else if (val1 instanceof Boolean && val2 instanceof Boolean) {
				boolean b1 = (Boolean) val1;
				boolean b2 = (Boolean) val2;
				return b1 == b2;
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

		throw new OrccRuntimeException("Uninitialized variable at line "
				+ Util.getLocation(expression).getStartLine()
				+ "\nCould not evaluate binary expression " + op.toString()
				+ "(" + op.getText() + ")\n");
	}

	@Override
	public Object caseAstExpressionBoolean(AstExpressionBoolean expression) {
		return expression.isValue();
	}

	@Override
	public Object caseAstExpressionInteger(AstExpressionInteger expression) {
		return expression.getValue();
	}

	@Override
	public Object caseAstExpressionString(AstExpressionString expression) {
		return expression.getValue();
	}

	@Override
	public Object caseAstExpressionUnary(AstExpressionUnary expression) {
		UnaryOp op = UnaryOp.getOperator(expression.getUnaryOperator());
		Object value = doSwitch(expression.getExpression());

		switch (op) {
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

		throw new OrccRuntimeException("Uninitialized variable at line "
				+ Util.getLocation(expression).getStartLine()
				+ "\nCould not evaluate unary expression " + op.toString()
				+ "(" + op.getText() + ")\n");
	}

	@Override
	public Object caseAstExpressionVariable(AstExpressionVariable expression) {
		AstVariable variable = expression.getValue().getVariable();
		Object value = values.get(variable);
		if (value == null) {
			throw new OrccRuntimeException("variable " + variable.getName()
					+ " does not have a compile-time constant value");
		}

		return value;
	}

	/**
	 * Evaluates the given AST expression and returns an object that can be a
	 * boolean, an integer, a string, or a list of objects.
	 * 
	 * @param expression
	 *            an AST expression
	 * @return the expression value
	 * @throws OrccRuntimeException
	 *             if the given expression cannot be evaluated.
	 */
	public Object evaluate(AstExpression expression) {
		return doSwitch(expression);
	}

	/**
	 * Evaluates the given AST expression and returns the expression as an
	 * integer, or throws an exception.
	 * 
	 * @param expression
	 *            an AST expression
	 * @return an integer
	 * @throws OrccRuntimeException
	 *             if the given expression cannot be evaluated.
	 */
	public int evaluateAsInteger(AstExpression expression) {
		Object value = doSwitch(expression);
		if (value instanceof Integer) {
			return ((Integer) value).intValue();
		}

		// evaluated ok, but not as an integer
		throw new OrccRuntimeException("expected integer expression");
	}

	public Expression evaluateAsIntExpr(AstExpression expression) {
		int value = evaluateAsInteger(expression);
		Location location = Util.getLocation(expression);
		return new IntExpr(location, value);
	}

	/**
	 * Registers the given variable with the given value.
	 * 
	 * @param variable
	 *            an AST variable
	 * @param value
	 *            a value as an object.
	 */
	public void registerValue(AstVariable variable, Object value) {
		values.put(variable, value);
	}

}
