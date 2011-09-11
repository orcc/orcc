/*
 * Copyright (c) 2009-2011, IETR/INSA of Rennes
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

import java.util.Iterator;
import java.util.List;

import net.sf.orcc.cache.Cache;
import net.sf.orcc.cache.CacheManager;
import net.sf.orcc.cal.cal.AstExpression;
import net.sf.orcc.cal.cal.AstExpressionBinary;
import net.sf.orcc.cal.cal.AstExpressionBoolean;
import net.sf.orcc.cal.cal.AstExpressionCall;
import net.sf.orcc.cal.cal.AstExpressionElsif;
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

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;

/**
 * This class defines an expression evaluator.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class Evaluator extends CalSwitch<Expression> {

	/**
	 * Returns the integer value associated with the given object using its URI.
	 * 
	 * @param eObject
	 *            an AST node
	 * @return the integer value associated with the given object
	 */
	public static int getIntValue(EObject eObject) {
		Expression value = getValue(eObject);
		if (value != null && value.isIntExpr()) {
			ExprInt intExpr = (ExprInt) value;
			if (!intExpr.isLong()) {
				return intExpr.getIntValue();
			}
		}

		// evaluated ok, but not as an integer
		return -1;
	}

	/**
	 * Returns the value associated with the given object using its URI.
	 * 
	 * @param eObject
	 *            an AST node
	 * @return the value associated with the given object
	 */
	public static Expression getValue(EObject eObject) {
		Resource resource = eObject.eResource();
		Expression value;
		if (resource == null) {
			value = new Evaluator().doSwitch(eObject);
		} else {
			Cache cache = CacheManager.instance.getCache(resource.getURI());

			URI uri = EcoreUtil.getURI(eObject);
			String fragment = uri.fragment();
			value = cache.getExpressionsMap().get(fragment);

			if (value == null) {
				value = new Evaluator().doSwitch(eObject);
				if (value != null) {
					cache.getExpressions().add(value);
					cache.getExpressionsMap().put(fragment, value);
				}
			}
		}

		return value;
	}

	private static void setValue(EObject eObject, AstExpression value) {
		Resource resource = eObject.eResource();
		if (resource != null && value != null) {
			Cache cache = CacheManager.instance.getCache(resource.getURI());

			URI uri = EcoreUtil.getURI(eObject);
			String fragment = uri.fragment();
			cache.getExpressionsMap().put(fragment, getValue(value));
		}
	}

	@Override
	public Expression caseAstExpressionBinary(AstExpressionBinary expression) {
		OpBinary op = OpBinary.getOperator(expression.getOperator());
		Expression e1 = getValue(expression.getLeft());
		Expression e2 = getValue(expression.getRight());

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

		// set the value of parameters
		Iterator<AstVariable> itFormal = function.getParameters().iterator();
		Iterator<AstExpression> itActual = expression.getParameters()
				.iterator();
		while (itFormal.hasNext() && itActual.hasNext()) {
			AstVariable paramV = itFormal.next();
			AstExpression paramE = itActual.next();

			setValue(paramV, paramE);
		}

		// set the value of variables
		for (AstVariable variable : function.getVariables()) {
			setValue(variable, variable.getValue());
		}

		return getValue(function.getExpression());
	}

	@Override
	public Expression caseAstExpressionFloat(AstExpressionFloat expression) {
		return IrFactory.eINSTANCE.createExprFloat(expression.getValue());
	}

	@Override
	public Expression caseAstExpressionIf(AstExpressionIf expression) {
		Expression condition = getValue(expression.getCondition());

		if (condition != null && condition.isBooleanExpr()) {
			if (((ExprBool) condition).isValue()) {
				return getValue(expression.getThen());
			} else {
				for (AstExpressionElsif elsif : expression.getElsifs()) {
					condition = getValue(elsif.getCondition());
					if (condition != null && condition.isBooleanExpr()) {
						if (((ExprBool) condition).isValue()) {
							return getValue(elsif.getThen());
						}
					}
				}

				return getValue(expression.getElse());
			}
		} else {
			return null;
		}
	}

	@Override
	public Expression caseAstExpressionIndex(AstExpressionIndex expression) {
		AstVariable variable = expression.getSource().getVariable();
		Expression value = getValue(variable.getValue());
		if (value == null) {
			return null;
		}

		List<AstExpression> indexes = expression.getIndexes();
		for (AstExpression index : indexes) {
			Expression indexValue = getValue(index);
			if (value != null && value.isListExpr()) {
				ExprList list = (ExprList) value;
				if (indexValue != null && indexValue.isIntExpr()) {
					ExprInt intExpr = (ExprInt) indexValue;
					if (!intExpr.isLong()) {
						try {
							value = list.get(intExpr.getIntValue());
						} catch (IndexOutOfBoundsException e) {
							e.printStackTrace();
						}
					}
				}
			}
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
				list.getValue().add(EcoreUtil.copy(getValue(subExpression)));
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
			Expression value = getValue(expression.getExpression());
			if (value != null && value.isIntExpr()) {
				ExprInt i = (ExprInt) value;
				return i.not();
			}
			return null;
		}
		case LOGIC_NOT: {
			Expression value = getValue(expression.getExpression());
			if (value != null && value.isBooleanExpr()) {
				return ((ExprBool) value).not();
			}
			return null;
		}
		case MINUS: {
			Expression value = getValue(expression.getExpression());
			if (value != null && value.isIntExpr()) {
				ExprInt i = (ExprInt) value;
				return i.negate();
			}
			return null;
		}
		case NUM_ELTS:
			Type type = Typer.getType(expression.getExpression());
			if (type != null && type.isList()) {
				return IrFactory.eINSTANCE.createExprInt(((TypeList) type)
						.getSize());
			}
			return null;
		}

		return null;
	}

	@Override
	public Expression caseAstExpressionVariable(AstExpressionVariable expression) {
		AstVariable variable = expression.getValue().getVariable();
		Expression value = getValue(variable);
		return value;
	}

	@Override
	public Expression caseAstVariable(AstVariable variable) {
		AstExpression expression = variable.getValue();
		if (expression == null) {
			return null;
		}

		Expression value = getValue(expression);
		return value;
	}

}
