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
package net.sf.orcc.cal.expression;

import java.util.ArrayList;
import java.util.List;

import net.sf.orcc.OrccRuntimeException;
import net.sf.orcc.cal.cal.AstExpression;
import net.sf.orcc.cal.cal.AstExpressionBinary;
import net.sf.orcc.cal.cal.AstExpressionBoolean;
import net.sf.orcc.cal.cal.AstExpressionCall;
import net.sf.orcc.cal.cal.AstExpressionFloat;
import net.sf.orcc.cal.cal.AstExpressionIf;
import net.sf.orcc.cal.cal.AstExpressionIndex;
import net.sf.orcc.cal.cal.AstExpressionInteger;
import net.sf.orcc.cal.cal.AstExpressionList;
import net.sf.orcc.cal.cal.AstExpressionString;
import net.sf.orcc.cal.cal.AstExpressionUnary;
import net.sf.orcc.cal.cal.AstExpressionVariable;
import net.sf.orcc.cal.cal.AstGenerator;
import net.sf.orcc.cal.cal.AstVariable;
import net.sf.orcc.cal.cal.CalPackage;
import net.sf.orcc.cal.cal.util.CalSwitch;
import net.sf.orcc.cal.validation.CalJavaValidator;
import net.sf.orcc.frontend.Util;
import net.sf.orcc.ir.expr.BinaryOp;
import net.sf.orcc.ir.expr.UnaryOp;
import net.sf.orcc.util.StringUtil;

/**
 * This class defines an expression evaluator.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class AstExpressionEvaluator extends CalSwitch<Object> {

	/**
	 * Creates a new AST expression evaluator.
	 */
	public AstExpressionEvaluator() {
	}

	@Override
	public Object caseAstExpressionBinary(AstExpressionBinary expression) {
		BinaryOp op = BinaryOp.getOperator(expression.getOperator());
		Object val1 = evaluate(expression.getLeft());
		Object val2 = evaluate(expression.getRight());

		switch (op) {
		case BITAND:
			if (val1 instanceof Long && val2 instanceof Long) {
				long i1 = (Long) val1;
				long i2 = (Long) val2;
				return i1 & i2;
			}
			break;
		case BITOR:
			if (val1 instanceof Long && val2 instanceof Long) {
				long i1 = (Long) val1;
				long i2 = (Long) val2;
				return i1 | i2;
			}
			break;
		case BITXOR:
			if (val1 instanceof Long && val2 instanceof Long) {
				long i1 = (Long) val1;
				long i2 = (Long) val2;
				return i1 ^ i2;
			}
			break;
		case DIV:
			if (val1 instanceof Long && val2 instanceof Long) {
				long i1 = (Long) val1;
				long i2 = (Long) val2;
				return i1 / i2;
			}
			break;
		case DIV_INT:
			if (val1 instanceof Long && val2 instanceof Long) {
				long i1 = (Long) val1;
				long i2 = (Long) val2;
				return i1 / i2;
			}
			break;
		case EQ:
			if (val1 instanceof Long && val2 instanceof Long) {
				long i1 = (Long) val1;
				long i2 = (Long) val2;
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
			if (val1 instanceof Long && val2 instanceof Long) {
				long i1 = (Long) val1;
				long i2 = (Long) val2;
				return i1 >= i2;
			}
			break;
		case GT:
			if (val1 instanceof Long && val2 instanceof Long) {
				long i1 = (Long) val1;
				long i2 = (Long) val2;
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
			if (val1 instanceof Long && val2 instanceof Long) {
				long i1 = (Long) val1;
				long i2 = (Long) val2;
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
			if (val1 instanceof Long && val2 instanceof Long) {
				long i1 = (Long) val1;
				long i2 = (Long) val2;
				return i1 < i2;
			}
			break;
		case MINUS:
			if (val1 instanceof Long && val2 instanceof Long) {
				long i1 = (Long) val1;
				long i2 = (Long) val2;
				return i1 - i2;
			}
			break;
		case MOD:
			if (val1 instanceof Long && val2 instanceof Long) {
				long i1 = (Long) val1;
				long i2 = (Long) val2;
				return i1 % i2;
			}
			break;
		case NE:
			if (val1 instanceof Long && val2 instanceof Long) {
				long i1 = (Long) val1;
				long i2 = (Long) val2;
				return i1 != i2;
			}
			break;
		case PLUS:
			if (val1 instanceof Long && val2 instanceof Long) {
				long i1 = (Long) val1;
				long i2 = (Long) val2;
				return i1 + i2;
			}
			break;
		case SHIFT_LEFT:
			if (val1 instanceof Long && val2 instanceof Long) {
				long i1 = (Long) val1;
				long i2 = (Long) val2;
				return i1 << i2;
			}
			break;
		case SHIFT_RIGHT:
			if (val1 instanceof Long && val2 instanceof Long) {
				long i1 = (Long) val1;
				long i2 = (Long) val2;
				return i1 >> i2;
			}
			break;
		case TIMES:
			if (val1 instanceof Long && val2 instanceof Long) {
				long i1 = (Long) val1;
				long i2 = (Long) val2;
				return i1 * i2;
			}
			break;
		}

		return null;
	}

	@Override
	public Object caseAstExpressionBoolean(AstExpressionBoolean expression) {
		return expression.isValue();
	}

	@Override
	public Object caseAstExpressionCall(AstExpressionCall expression) {
		String name = expression.getFunction().getName();
		List<AstExpression> parameters = expression.getParameters();
		List<Object> values = new ArrayList<Object>(parameters.size());
		for (AstExpression parameter : parameters) {
			values.add(evaluate(parameter));
		}

		if (expression.getFunction().eContainer() == null) {
			if ("bitnot".equals(name)) {
				if (values.size() == 1) {
					Object obj = values.get(0);
					if (obj instanceof Long) {
						long value = (Long) obj;
						return ~value;
					}
				}

				CalJavaValidator.getInstance().error(
						"bitnot expects one integer expression", expression,
						CalPackage.AST_EXPRESSION_CALL);
				return null;
			}

			BinaryOp op = BinaryOp.getOperator(name);
			if (op == null) {
				return null;
			}

			switch (op) {
			case BITAND:
				if (values.size() == 2) {
					Object obj1 = values.get(0);
					Object obj2 = values.get(1);
					if (obj1 instanceof Long && obj2 instanceof Long) {
						return (Long) obj1 & (Long) obj2;
					}
				}

				CalJavaValidator.getInstance().error(
						"bitand expects two integer expressions", expression,
						CalPackage.AST_EXPRESSION_CALL);
				return null;
			case BITOR:
				if (values.size() == 2) {
					Object obj1 = values.get(0);
					Object obj2 = values.get(1);
					if (obj1 instanceof Long && obj2 instanceof Long) {
						return (Long) obj1 | (Long) obj2;
					}
				}

				CalJavaValidator.getInstance().error(
						"bitor expects two integer expressions", expression,
						CalPackage.AST_EXPRESSION_CALL);
				return null;
			case BITXOR:
				if (values.size() == 2) {
					Object obj1 = values.get(0);
					Object obj2 = values.get(1);
					if (obj1 instanceof Long && obj2 instanceof Long) {
						return (Long) obj1 ^ (Long) obj2;
					}
				}

				CalJavaValidator.getInstance().error(
						"bitxor expects two integer expressions", expression,
						CalPackage.AST_EXPRESSION_CALL);
				return null;
			case SHIFT_LEFT:
				if (values.size() == 2) {
					Object obj1 = values.get(0);
					Object obj2 = values.get(1);
					if (obj1 instanceof Long && obj2 instanceof Long) {
						return (Long) obj1 << (Long) obj2;
					}
				}

				CalJavaValidator.getInstance().error(
						"lshift expects two integer expressions", expression,
						CalPackage.AST_EXPRESSION_CALL);
				return null;
			case SHIFT_RIGHT:
				if (values.size() == 2) {
					Object obj1 = values.get(0);
					Object obj2 = values.get(1);
					if (obj1 instanceof Long && obj2 instanceof Long) {
						return (Long) obj1 >> (Long) obj2;
					}
				}

				CalJavaValidator.getInstance().error(
						"rshift expects two integer expressions", expression,
						CalPackage.AST_EXPRESSION_CALL);
				return null;
			default:
				break;
			}
		}

		CalJavaValidator.getInstance().error(
				"unknown function \"" + name + "\"", expression,
				CalPackage.AST_EXPRESSION_CALL__FUNCTION);

		return null;
	}

	@Override
	public Object caseAstExpressionFloat(AstExpressionFloat expression) {
		return expression.getValue();
	}

	@Override
	public Object caseAstExpressionIf(AstExpressionIf expression) {
		Object condition = evaluate(expression.getCondition());

		// evaluates both branches so errors are caught early
		Object oThen = evaluate(expression.getThen());
		Object oElse = evaluate(expression.getElse());

		if (condition instanceof Boolean) {
			if ((Boolean) condition) {
				return oThen;
			} else {
				return oElse;
			}
		} else {
			CalJavaValidator.getInstance().error(
					"expected condition of type bool", expression,
					CalPackage.AST_EXPRESSION_IF__CONDITION);
			return null;
		}
	}

	@Override
	public Object caseAstExpressionIndex(AstExpressionIndex expression) {
		AstVariable variable = expression.getSource().getVariable();
		Object value = variable.getInitialValue();
		if (value == null) {
			CalJavaValidator.getInstance().error(
					"variable \"" + variable.getName() + "\" ("
							+ Util.getLocation(variable)
							+ ") does not have a compile-time constant value",
					expression, CalPackage.AST_EXPRESSION_INDEX__SOURCE);
			return null;
		}

		List<AstExpression> indexes = expression.getIndexes();

		for (AstExpression index : indexes) {
			Object indexValue = evaluate(index);
			if (value instanceof List<?>) {
				List<?> list = (List<?>) value;
				if (indexValue instanceof Long) {
					int intValue = ((Long) indexValue).intValue();
					long longValue = ((Long) indexValue).longValue();
					if (intValue == longValue) {
						try {
							value = list.get(intValue);
						} catch (IndexOutOfBoundsException e) {
							CalJavaValidator.getInstance().error(
									"index out of bounds", expression,
									CalPackage.AST_EXPRESSION_INDEX__INDEXES);
						}
					} else {
						CalJavaValidator.getInstance().error(
								"index must be a int(size=32) integer",
								expression,
								CalPackage.AST_EXPRESSION_INDEX__INDEXES);
					}
				} else {
					CalJavaValidator.getInstance().error(
							"index must be an integer", expression,
							CalPackage.AST_EXPRESSION_INDEX__INDEXES);
				}
			} else {
				CalJavaValidator.getInstance().error(
						"variable \"" + variable.getName() + "\" ("
								+ Util.getLocation(variable)
								+ ") must be of type List", expression,
						CalPackage.AST_EXPRESSION_INDEX__SOURCE);
			}
		}

		return value;
	}

	@Override
	public Object caseAstExpressionInteger(AstExpressionInteger expression) {
		return expression.getValue();
	}

	@Override
	public Object caseAstExpressionList(AstExpressionList expression) {
		List<AstGenerator> generators = expression.getGenerators();
		if (!generators.isEmpty()) {
			// generators will be translated to statements in initialize
			// meanwhile we return an empty list
			return new ArrayList<Object>(0);
		}

		List<AstExpression> expressions = expression.getExpressions();
		List<Object> list = new ArrayList<Object>(expressions.size());
		for (AstExpression subExpression : expressions) {
			list.add(evaluate(subExpression));
		}
		return list;
	}

	@Override
	public Object caseAstExpressionString(AstExpressionString expression) {
		return StringUtil.getEscapedString(expression.getValue());
	}

	@Override
	public Object caseAstExpressionUnary(AstExpressionUnary expression) {
		UnaryOp op = UnaryOp.getOperator(expression.getUnaryOperator());
		Object value = evaluate(expression.getExpression());

		switch (op) {
		case BITNOT:
			if (value instanceof Long) {
				long i = (Long) value;
				return ~i;
			}

			CalJavaValidator.getInstance().error(
					"bitnot expects an integer expression", expression,
					CalPackage.AST_EXPRESSION_UNARY__UNARY_OPERATOR);
			return null;
		case LOGIC_NOT:
			if (value instanceof Boolean) {
				boolean b = (Boolean) value;
				return !b;
			}

			CalJavaValidator.getInstance().error(
					"not expects a boolean expression", expression,
					CalPackage.AST_EXPRESSION_UNARY__UNARY_OPERATOR);
			return null;
		case MINUS:
			if (value instanceof Long) {
				long i = (Long) value;
				return -i;
			}

			CalJavaValidator.getInstance().error(
					"minus expects an integer expression", expression,
					CalPackage.AST_EXPRESSION_UNARY__UNARY_OPERATOR);
			return null;
		case NUM_ELTS:
			CalJavaValidator.getInstance()
					.error("# is not yet supported", expression,
							CalPackage.AST_EXPRESSION_UNARY__UNARY_OPERATOR);
			return null;
		}

		return null;
	}

	@Override
	public Object caseAstExpressionVariable(AstExpressionVariable expression) {
		AstVariable variable = expression.getValue().getVariable();
		Object value = variable.getInitialValue();
		if (value == null) {
			CalJavaValidator.getInstance().error(
					"variable \"" + variable.getName() + "\" ("
							+ Util.getLocation(variable)
							+ ") does not have a compile-time constant value",
					expression, CalPackage.AST_EXPRESSION_VARIABLE);
			return null;
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
		if (expression == null) {
			return null;
		}

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
		Object value = evaluate(expression);
		if (value instanceof Long) {
			Long longValue = (Long) value;
			if (longValue.intValue() == longValue.longValue()) {
				return longValue.intValue();
			}

			CalJavaValidator.getInstance().error(
					"integer expression too large", expression,
					CalPackage.AST_EXPRESSION);
			return 0;
		}

		// evaluated ok, but not as an integer
		CalJavaValidator.getInstance().error(
				"expected compile-time constant integer expression",
				expression, CalPackage.AST_EXPRESSION);
		return 0;
	}

}
