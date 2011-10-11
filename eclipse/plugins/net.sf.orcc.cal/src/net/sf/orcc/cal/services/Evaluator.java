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
import net.sf.orcc.cache.CachePackage;
import net.sf.orcc.cal.cal.AstExpression;
import net.sf.orcc.cal.cal.ExpressionBinary;
import net.sf.orcc.cal.cal.ExpressionBoolean;
import net.sf.orcc.cal.cal.ExpressionCall;
import net.sf.orcc.cal.cal.ExpressionElsif;
import net.sf.orcc.cal.cal.ExpressionFloat;
import net.sf.orcc.cal.cal.ExpressionIf;
import net.sf.orcc.cal.cal.ExpressionIndex;
import net.sf.orcc.cal.cal.ExpressionInteger;
import net.sf.orcc.cal.cal.ExpressionList;
import net.sf.orcc.cal.cal.ExpressionString;
import net.sf.orcc.cal.cal.ExpressionUnary;
import net.sf.orcc.cal.cal.ExpressionVariable;
import net.sf.orcc.cal.cal.Function;
import net.sf.orcc.cal.cal.Generator;
import net.sf.orcc.cal.cal.Variable;
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
		if (eObject instanceof Variable) {
			return CacheManager.instance.getOrCompute(eObject, new Evaluator(),
					CachePackage.eINSTANCE.getCache_ExpressionsMap());
		} else {
			return new Evaluator().doSwitch(eObject);
		}
	}

	private static void setValue(EObject eObject, Expression value) {
		Resource resource = eObject.eResource();
		if (resource != null) {
			Cache cache = CacheManager.instance.getCache(resource);
			cache.getExpressionsMap().put(eObject, value);
		}
	}

	@Override
	public Expression caseExpressionBinary(ExpressionBinary expression) {
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
	public Expression caseExpressionBoolean(ExpressionBoolean expression) {
		return IrFactory.eINSTANCE.createExprBool(expression.isValue());
	}

	@Override
	public Expression caseExpressionCall(ExpressionCall expression) {
		Function function = expression.getFunction();
		if (expression.getParameters().size() != function.getParameters()
				.size()) {
			return null;
		}

		// set the value of parameters
		Iterator<Variable> itFormal = function.getParameters().iterator();
		Iterator<AstExpression> itActual = expression.getParameters()
				.iterator();
		while (itFormal.hasNext() && itActual.hasNext()) {
			Variable paramV = itFormal.next();
			AstExpression paramE = itActual.next();

			setValue(paramV, doSwitch(paramE));
		}

		// set the value of variables
		for (Variable variable : function.getVariables()) {
			setValue(variable, doSwitch(variable.getValue()));
		}

		// do not cache value because it is influenced by variables
		return doSwitch(function.getExpression());
	}

	@Override
	public Expression caseExpressionFloat(ExpressionFloat expression) {
		return IrFactory.eINSTANCE.createExprFloat(expression.getValue());
	}

	@Override
	public Expression caseExpressionIf(ExpressionIf expression) {
		Expression condition = getValue(expression.getCondition());

		if (condition != null && condition.isBooleanExpr()) {
			if (((ExprBool) condition).isValue()) {
				return getValue(expression.getThen());
			} else {
				for (ExpressionElsif elsif : expression.getElsifs()) {
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
	public Expression caseExpressionIndex(ExpressionIndex expression) {
		Variable variable = expression.getSource().getVariable();
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
	public Expression caseExpressionInteger(ExpressionInteger expression) {
		return IrFactory.eINSTANCE.createExprInt(expression.getValue());
	}

	@Override
	public Expression caseExpressionList(ExpressionList expression) {
		List<AstExpression> expressions = expression.getExpressions();
		List<Generator> generators = expression.getGenerators();

		ExprList list = IrFactory.eINSTANCE.createExprList();
		computeList(list, expressions, generators, 0);

		return list;
	}

	@Override
	public Expression caseExpressionString(ExpressionString expression) {
		return IrFactory.eINSTANCE.createExprString(OrccUtil
				.getEscapedString(expression.getValue()));
	}

	@Override
	public Expression caseExpressionUnary(ExpressionUnary expression) {
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
	public Expression caseExpressionVariable(ExpressionVariable expression) {
		Variable variable = expression.getValue().getVariable();
		Expression value = getValue(variable);
		return EcoreUtil.copy(value);
	}

	@Override
	public Expression caseVariable(Variable variable) {
		AstExpression expression = variable.getValue();
		if (expression == null) {
			return null;
		}

		Expression value = getValue(expression);
		return value;
	}

	private void computeList(ExprList list, List<AstExpression> expressions,
			List<Generator> generators, int index) {
		if (index < generators.size()) {
			Generator generator = generators.get(index);
			int lower = getIntValue(generator.getLower());
			int higher = getIntValue(generator.getHigher());
			for (int i = lower; i <= higher; i++) {
				ExprInt value = IrFactory.eINSTANCE.createExprInt(i);
				setValue(generator.getVariable(), value);

				computeList(list, expressions, generators, index + 1);
			}
			setValue(generator.getVariable(), null);
		} else {
			for (AstExpression subExpression : expressions) {
				// do not cache value because it is influenced by variables
				Expression value = doSwitch(subExpression);
				if (value != null) {
					list.getValue().add(EcoreUtil.copy(value));
				}
			}
		}
	}

	@Override
	public Expression doSwitch(EObject eObject) {
		if (eObject == null) {
			return null;
		}

		return super.doSwitch(eObject);
	}

}
