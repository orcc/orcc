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
package net.sf.orcc.cal.services;

import static net.sf.orcc.cal.cal.CalPackage.eINSTANCE;

import java.util.Iterator;
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
import net.sf.orcc.cal.cal.AstFunction;
import net.sf.orcc.cal.cal.AstGenerator;
import net.sf.orcc.cal.cal.AstVariable;
import net.sf.orcc.cal.cal.util.CalSwitch;
import net.sf.orcc.cal.type.TypeChecker;
import net.sf.orcc.cal.util.Util;
import net.sf.orcc.cal.validation.CalJavaValidator;
import net.sf.orcc.ir.ExprBool;
import net.sf.orcc.ir.ExprInt;
import net.sf.orcc.ir.ExprList;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.IrFactory;
import net.sf.orcc.ir.OpBinary;
import net.sf.orcc.ir.OpUnary;
import net.sf.orcc.ir.Type;
import net.sf.orcc.ir.TypeList;
import net.sf.orcc.ir.util.ExpressionEvaluator;
import net.sf.orcc.ir.util.ValueUtil;
import net.sf.orcc.util.OrccUtil;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.EcoreUtil;

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
		OpBinary op = OpBinary.getOperator(expression.getOperator());
		Expression e1 = evaluate(expression.getLeft());
		Expression e2 = evaluate(expression.getRight());

		Object val1 = ValueUtil.getValue(e1);
		Object val2 = ValueUtil.getValue(e2);

		Object result = new ExpressionEvaluator().interpretBinaryExpr(val1, op,
				val2);
		return ValueUtil.getExpression(result);
	}

	@Override
	public Expression caseAstExpressionBoolean(AstExpressionBoolean expression) {
		return IrFactory.eINSTANCE.createExprBool(expression.isValue());
	}

	@Override
	public Expression caseAstExpressionCall(AstExpressionCall expression) {
		AstFunction function = expression.getFunction();
		if (expression.getParameters().size() != function.getParameters()
				.size()) {
			return null;
		}

		Iterator<AstVariable> itFormal = function.getParameters().iterator();
		Iterator<AstExpression> itActual = expression.getParameters()
				.iterator();
		// int index = 0;
		while (itFormal.hasNext() && itActual.hasNext()) {
			/* AstVariable paramV = */itFormal.next();
			AstExpression paramE = itActual.next();

			evaluate(paramE);

			// index++;
		}

		return null;
	}

	@Override
	public Expression caseAstExpressionFloat(AstExpressionFloat expression) {
		return IrFactory.eINSTANCE.createExprFloat(expression.getValue());
	}

	@Override
	public Expression caseAstExpressionIf(AstExpressionIf expression) {
		Expression condition = evaluate(expression.getCondition());

		// evaluates both branches so errors are caught early
		Expression oThen = evaluate(expression.getThen());
		Expression oElse = evaluate(expression.getElse());

		if (condition != null && condition.isBooleanExpr()) {
			if (((ExprBool) condition).isValue()) {
				return oThen;
			} else {
				return oElse;
			}
		} else {
			error("expected condition of type bool", expression,
					eINSTANCE.getAstExpressionIf_Condition(), -1);
			return null;
		}
	}

	@Override
	public Expression caseAstExpressionIndex(AstExpressionIndex expression) {
		AstVariable variable = expression.getSource().getVariable();
		Expression value = evaluate(variable.getValue());
		if (value == null) {
			error("variable \"" + variable.getName() + "\" ("
					+ Util.getLocation(variable)
					+ ") does not have a compile-time constant value",
					expression, eINSTANCE.getAstExpressionIndex_Source(), -1);
			return null;
		}

		List<AstExpression> indexes = expression.getIndexes();
		int errorIdx = 0;
		for (AstExpression index : indexes) {
			Expression indexValue = evaluate(index);
			if (value != null && value.isListExpr()) {
				ExprList list = (ExprList) value;
				if (indexValue != null && indexValue.isIntExpr()) {
					ExprInt intExpr = (ExprInt) indexValue;
					if (intExpr.isLong()) {
						error("index must be a int(size=32) integer",
								expression,
								eINSTANCE.getAstExpressionIndex_Indexes(),
								errorIdx);
					} else {
						try {
							value = list.get(intExpr.getIntValue());
						} catch (IndexOutOfBoundsException e) {
							error("index out of bounds", expression,
									eINSTANCE.getAstExpressionIndex_Indexes(),
									errorIdx);
						}
					}
				} else {
					error("index must be an integer", expression,
							eINSTANCE.getAstExpressionIndex_Indexes(), errorIdx);
				}
			} else {
				error("variable \"" + variable.getName() + "\" ("
						+ Util.getLocation(variable) + ") must be of type List",
						expression, eINSTANCE.getAstExpressionIndex_Source(),
						-1);
			}
			errorIdx++;
		}

		return EcoreUtil.copy(value);
	}

	@Override
	public Expression caseAstExpressionInteger(AstExpressionInteger expression) {
		return IrFactory.eINSTANCE.createExprInt(expression.getValue());
	}

	@Override
	public Expression caseAstExpressionList(AstExpressionList expression) {
		List<AstExpression> expressions = expression.getExpressions();
		List<AstGenerator> generators = expression.getGenerators();

		ExprList list = IrFactory.eINSTANCE.createExprList();
		if (generators.isEmpty()) {
			for (AstExpression subExpression : expressions) {
				list.getValue().add(EcoreUtil.copy(evaluate(subExpression)));
			}
		} else {
			// generators will be translated to statements in initialize

			// for some weird reason the evaluation of generators slows down *a
			// lot* everything (even code generation, although I have no idea
			// why), so we will not use it
		}

		return list;
	}

	@Override
	public Expression caseAstExpressionString(AstExpressionString expression) {
		return IrFactory.eINSTANCE.createExprString(OrccUtil
				.getEscapedString(expression.getValue()));
	}

	@Override
	public Expression caseAstExpressionUnary(AstExpressionUnary expression) {
		OpUnary op = OpUnary.getOperator(expression.getUnaryOperator());

		switch (op) {
		case BITNOT: {
			Expression value = evaluate(expression.getExpression());
			if (value != null && value.isIntExpr()) {
				ExprInt i = (ExprInt) value;
				return i.not();
			}

			error("bitnot expects an integer expression", expression,
					eINSTANCE.getAstExpressionUnary_UnaryOperator(), -1);
			return null;
		}
		case LOGIC_NOT: {
			Expression value = evaluate(expression.getExpression());
			if (value != null && value.isBooleanExpr()) {
				return ((ExprBool) value).not();
			}

			error("not expects a boolean expression", expression,
					eINSTANCE.getAstExpressionUnary_UnaryOperator(), -1);
			return null;
		}
		case MINUS: {
			Expression value = evaluate(expression.getExpression());
			if (value != null && value.isIntExpr()) {
				ExprInt i = (ExprInt) value;
				return i.negate();
			}

			error("minus expects an integer expression", expression,
					eINSTANCE.getAstExpressionUnary_UnaryOperator(), -1);
			return null;
		}
		case NUM_ELTS:
			TypeChecker typeChecker = new TypeChecker(validator);
			Type type = typeChecker.getType(expression.getExpression());
			if (type != null && type.isList()) {
				return IrFactory.eINSTANCE.createExprInt(((TypeList) type)
						.getSize());
			}

			error("operator # expects a list expression", expression,
					eINSTANCE.getAstExpressionUnary_UnaryOperator(), -1);
			return null;
		}

		return null;
	}

	@Override
	public Expression caseAstExpressionVariable(AstExpressionVariable expression) {
		AstVariable variable = expression.getValue().getVariable();
		return evaluate(variable.getValue());
	}

	private void error(String string, EObject source,
			EStructuralFeature feature, int index) {
		if (validator != null) {
			validator.error(string, source, feature, index);
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
		EObject cter = expression.eContainer();
		EStructuralFeature feature = expression.eContainingFeature();
		Expression value = evaluate(expression);
		if (value != null && value.isIntExpr()) {
			ExprInt intExpr = (ExprInt) value;
			if (intExpr.isLong()) {
				error("integer expression too large", cter, feature, -1);
			} else {
				return intExpr.getIntValue();
			}
		}

		// evaluated ok, but not as an integer
		error("expected compile-time constant integer expression", cter,
				feature, -1);
		return 0;
	}

}
