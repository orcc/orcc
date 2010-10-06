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
import net.sf.orcc.cal.type.TypeChecker;
import net.sf.orcc.cal.validation.CalJavaValidator;
import net.sf.orcc.frontend.Util;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.Type;
import net.sf.orcc.ir.TypeList;
import net.sf.orcc.ir.expr.BinaryOp;
import net.sf.orcc.ir.expr.BoolExpr;
import net.sf.orcc.ir.expr.ExpressionEvaluator;
import net.sf.orcc.ir.expr.FloatExpr;
import net.sf.orcc.ir.expr.IntExpr;
import net.sf.orcc.ir.expr.ListExpr;
import net.sf.orcc.ir.expr.StringExpr;
import net.sf.orcc.ir.expr.UnaryOp;
import net.sf.orcc.util.StringUtil;

import org.eclipse.emf.ecore.EObject;

/**
 * This class defines an expression evaluator.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class AstExpressionEvaluator extends CalSwitch<Expression> {

	private CalJavaValidator validator;

	/**
	 * Creates a new AST expression evaluator.
	 */
	public AstExpressionEvaluator(CalJavaValidator validator) {
		this.validator = validator;
	}

	@Override
	public Expression caseAstExpressionBinary(AstExpressionBinary expression) {
		BinaryOp op = BinaryOp.getOperator(expression.getOperator());
		Expression val1 = evaluate(expression.getLeft());
		Expression val2 = evaluate(expression.getRight());
		return new ExpressionEvaluator().interpretBinaryExpr(val1, op, val2);
	}

	@Override
	public Expression caseAstExpressionBoolean(AstExpressionBoolean expression) {
		return new BoolExpr(expression.isValue());
	}

	@Override
	public Expression caseAstExpressionCall(AstExpressionCall expression) {
		String name = expression.getFunction().getName();
		List<AstExpression> parameters = expression.getParameters();
		List<Expression> values = new ArrayList<Expression>(parameters.size());
		for (AstExpression parameter : parameters) {
			values.add(evaluate(parameter));
		}

		if (expression.getFunction().eContainer() == null) {
			if ("bitnot".equals(name)) {
				if (values.size() == 1) {
					Expression expr = values.get(0);
					if (expr != null && expr.isIntExpr()) {
						IntExpr value = (IntExpr) expr;
						return value.not();
					}
				}

				error("bitnot expects one integer expression", expression,
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
					Expression val1 = values.get(0);
					Expression val2 = values.get(1);
					if (val1 != null && val1.isIntExpr() && val2 != null
							&& val2.isIntExpr()) {
						IntExpr i1 = (IntExpr) val1;
						IntExpr i2 = (IntExpr) val2;
						return i1.and(i2);
					}
				}

				error("bitand expects two integer expressions", expression,
						CalPackage.AST_EXPRESSION_CALL);
				return null;
			case BITOR:
				if (values.size() == 2) {
					Expression val1 = values.get(0);
					Expression val2 = values.get(1);
					if (val1 != null && val1.isIntExpr() && val2 != null
							&& val2.isIntExpr()) {
						IntExpr i1 = (IntExpr) val1;
						IntExpr i2 = (IntExpr) val2;
						return i1.or(i2);
					}
				}

				error("bitor expects two integer expressions", expression,
						CalPackage.AST_EXPRESSION_CALL);
				return null;
			case BITXOR:
				if (values.size() == 2) {
					Expression val1 = values.get(0);
					Expression val2 = values.get(1);
					if (val1 != null && val1.isIntExpr() && val2 != null
							&& val2.isIntExpr()) {
						IntExpr i1 = (IntExpr) val1;
						IntExpr i2 = (IntExpr) val2;
						return i1.xor(i2);
					}
				}

				error("bitxor expects two integer expressions", expression,
						CalPackage.AST_EXPRESSION_CALL);
				return null;
			case SHIFT_LEFT:
				if (values.size() == 2) {
					Expression val1 = values.get(0);
					Expression val2 = values.get(1);
					if (val1 != null && val1.isIntExpr() && val2 != null
							&& val2.isIntExpr()) {
						IntExpr i1 = (IntExpr) val1;
						IntExpr i2 = (IntExpr) val2;
						return i1.shiftLeft(i2);
					}
				}

				error("lshift expects two integer expressions", expression,
						CalPackage.AST_EXPRESSION_CALL);
				return null;
			case SHIFT_RIGHT:
				if (values.size() == 2) {
					Expression val1 = values.get(0);
					Expression val2 = values.get(1);
					if (val1 != null && val1.isIntExpr() && val2 != null
							&& val2.isIntExpr()) {
						IntExpr i1 = (IntExpr) val1;
						IntExpr i2 = (IntExpr) val2;
						return i1.shiftRight(i2);
					}
				}

				error("rshift expects two integer expressions", expression,
						CalPackage.AST_EXPRESSION_CALL);
				return null;
			default:
				break;
			}
		}

		error("unknown function \"" + name + "\"", expression,
				CalPackage.AST_EXPRESSION_CALL__FUNCTION);

		return null;
	}

	@Override
	public Expression caseAstExpressionFloat(AstExpressionFloat expression) {
		return new FloatExpr(expression.getValue());
	}

	@Override
	public Expression caseAstExpressionIf(AstExpressionIf expression) {
		Expression condition = evaluate(expression.getCondition());

		// evaluates both branches so errors are caught early
		Expression oThen = evaluate(expression.getThen());
		Expression oElse = evaluate(expression.getElse());

		if (condition != null && condition.isBooleanExpr()) {
			if (((BoolExpr) condition).getValue()) {
				return oThen;
			} else {
				return oElse;
			}
		} else {
			error("expected condition of type bool", expression,
					CalPackage.AST_EXPRESSION_IF__CONDITION);
			return null;
		}
	}

	@Override
	public Expression caseAstExpressionIndex(AstExpressionIndex expression) {
		AstVariable variable = expression.getSource().getVariable();
		Expression value = (Expression) variable.getInitialValue();
		if (value == null) {
			error("variable \"" + variable.getName() + "\" ("
					+ Util.getLocation(variable)
					+ ") does not have a compile-time constant value",
					expression, CalPackage.AST_EXPRESSION_INDEX__SOURCE);
			return null;
		}

		List<AstExpression> indexes = expression.getIndexes();

		for (AstExpression index : indexes) {
			Expression indexValue = evaluate(index);
			if (value != null && value.isListExpr()) {
				ListExpr list = (ListExpr) value;
				if (indexValue != null && indexValue.isIntExpr()) {
					IntExpr intExpr = (IntExpr) indexValue;
					if (intExpr.isLong()) {
						error("index must be a int(size=32) integer",
								expression,
								CalPackage.AST_EXPRESSION_INDEX__INDEXES);
					} else {
						try {
							value = list.get(intExpr.getIntValue());
						} catch (IndexOutOfBoundsException e) {
							error("index out of bounds", expression,
									CalPackage.AST_EXPRESSION_INDEX__INDEXES);
						}
					}
				} else {
					error("index must be an integer", expression,
							CalPackage.AST_EXPRESSION_INDEX__INDEXES);
				}
			} else {
				error("variable \"" + variable.getName() + "\" ("
						+ Util.getLocation(variable) + ") must be of type List",
						expression, CalPackage.AST_EXPRESSION_INDEX__SOURCE);
			}
		}

		return value;
	}

	@Override
	public Expression caseAstExpressionInteger(AstExpressionInteger expression) {
		return new IntExpr(expression.getValue());
	}

	@Override
	public Expression caseAstExpressionList(AstExpressionList expression) {
		List<AstExpression> expressions = expression.getExpressions();
		List<AstGenerator> generators = expression.getGenerators();

		List<Expression> list;
		if (generators.isEmpty()) {
			int size = expressions.size();
			list = new ArrayList<Expression>(size);
			for (AstExpression subExpression : expressions) {
				list.add(evaluate(subExpression));
			}
		} else {
			// generators will be translated to statements in initialize
			list = new ArrayList<Expression>(0);

			// for some weird reason the evaluation of generators slows down *a
			// lot* everything (even code generation, although I have no idea
			// why), so we will not use it
		}

		return new ListExpr(list);
	}

	@Override
	public Expression caseAstExpressionString(AstExpressionString expression) {
		return new StringExpr(
				StringUtil.getEscapedString(expression.getValue()));
	}

	@Override
	public Expression caseAstExpressionUnary(AstExpressionUnary expression) {
		UnaryOp op = UnaryOp.getOperator(expression.getUnaryOperator());

		switch (op) {
		case BITNOT: {
			Expression value = evaluate(expression.getExpression());
			if (value != null && value.isIntExpr()) {
				IntExpr i = (IntExpr) value;
				return i.not();
			}

			error("bitnot expects an integer expression", expression,
					CalPackage.AST_EXPRESSION_UNARY__UNARY_OPERATOR);
			return null;
		}
		case LOGIC_NOT: {
			Expression value = evaluate(expression.getExpression());
			if (value != null && value.isBooleanExpr()) {
				BoolExpr b = (BoolExpr) value;
				return new BoolExpr(!b.getValue());
			}

			error("not expects a boolean expression", expression,
					CalPackage.AST_EXPRESSION_UNARY__UNARY_OPERATOR);
			return null;
		}
		case MINUS: {
			Expression value = evaluate(expression.getExpression());
			if (value != null && value.isIntExpr()) {
				IntExpr i = (IntExpr) value;
				return i.negate();
			}

			error("minus expects an integer expression", expression,
					CalPackage.AST_EXPRESSION_UNARY__UNARY_OPERATOR);
			return null;
		}
		case NUM_ELTS:
			TypeChecker typeChecker = new TypeChecker(validator);
			Type type = typeChecker.getType(expression.getExpression());
			if (type != null && type.isList()) {
				return new IntExpr(((TypeList) type).getSize());
			}

			error("operator # expects a list expression", expression,
					CalPackage.AST_EXPRESSION_UNARY__UNARY_OPERATOR);
			return null;
		}

		return null;
	}

	@Override
	public Expression caseAstExpressionVariable(AstExpressionVariable expression) {
		AstVariable variable = expression.getValue().getVariable();
		Expression value = (Expression) variable.getInitialValue();
		if (value == null) {
			error("variable \"" + variable.getName() + "\" ("
					+ Util.getLocation(variable)
					+ ") does not have a compile-time constant value",
					expression, CalPackage.AST_EXPRESSION_VARIABLE);
			return null;
		}

		return value;
	}

	private void error(String string, EObject source, int feature) {
		if (validator != null) {
			validator.error(string, source, feature);
		}
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
	public Expression evaluate(AstExpression expression) {
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
		Expression value = evaluate(expression);
		if (value != null && value.isIntExpr()) {
			IntExpr intExpr = (IntExpr) value;
			if (intExpr.isLong()) {
				error("integer expression too large", expression,
						CalPackage.AST_EXPRESSION);
			} else {
				return intExpr.getIntValue();
			}
		}

		// evaluated ok, but not as an integer
		error("expected compile-time constant integer expression", expression,
				CalPackage.AST_EXPRESSION);
		return 0;
	}

}
