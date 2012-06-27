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

import java.math.BigInteger;
import java.util.List;

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
import net.sf.orcc.ir.Type;
import net.sf.orcc.ir.TypeList;
import net.sf.orcc.ir.Var;

import org.eclipse.emf.ecore.EObject;

/**
 * This class defines an expression evaluator.
 * 
 * @author Pierre-Laurent Lagalaye
 * @author Matthieu Wipliez
 * 
 */
public class ExpressionEvaluator extends IrSwitch<Object> {

	private TypeList typeList;

	@Override
	public Object caseExprBinary(ExprBinary expr) {
		Object val1 = doSwitch(expr.getE1());
		Object val2 = doSwitch(expr.getE2());
		Object result = ValueUtil.compute(val1, expr.getOp(), val2);

		if (result == null) {
			throw new OrccRuntimeException(
					"Could not evaluate binary expression:\n"
							+ new ExpressionPrinter().doSwitch(expr) + "\n");
		}
		return result;
	}

	@Override
	public Object caseExprBool(ExprBool expr) {
		return expr.isValue();
	}

	@Override
	public Object caseExprFloat(ExprFloat expr) {
		return expr.getValue();
	}

	@Override
	public Object caseExprInt(ExprInt expr) {
		return expr.getValue();
	}

	@Override
	public Object caseExprList(ExprList expr) {
		if (typeList == null) {
			// if no type has been defined, use the expression's type
			typeList = (TypeList) expr.getType();
		}

		Object array = ValueUtil.createArray(typeList);
		computeInitValue(array, typeList, expr);

		// reset the type for future calls
		typeList = null;

		return array;
	}

	@Override
	public Object caseExprString(ExprString expr) {
		// note the difference with the caseExprString method from the
		// expression printer: here we return the string without quotes
		return expr.getValue();
	}

	@Override
	public Object caseExprUnary(ExprUnary expr) {
		Object value = doSwitch(expr.getExpr());
		Object result = ValueUtil.compute(expr.getOp(), value);

		if (result == null) {
			throw new OrccRuntimeException(
					"Could not evaluate unary expression "
							+ new ExpressionPrinter().doSwitch(expr) + "\n");
		}
		return result;
	}

	@Override
	public Object caseExprVar(ExprVar expr) {
		Var var = expr.getUse().getVariable();
		Object value = var.getValue();
		if (value == null) {
			throw new OrccRuntimeException("Uninitialized variable: "
					+ var.getName());
		}
		return value;
	}

	/**
	 * Evaluates the given expression and copy it into the given array.
	 * 
	 * @param array
	 *            the array to visit
	 * @param type
	 *            type of the current dimension
	 * @param expr
	 *            expression associated with the current dimension
	 * @param indexes
	 *            indexes that lead to the current dimension (empty for the
	 *            outermost call)
	 */
	private void computeInitValue(Object array, Type type, Expression expr,
			Object... indexes) {
		if (type.isList()) {
			TypeList typeList = (TypeList) type;
			List<Expression> list = ((ExprList) expr).getValue();

			Type eltType = typeList.getType();

			Object[] innerIndexes = new Object[indexes.length + 1];
			System.arraycopy(indexes, 0, innerIndexes, 0, indexes.length);
			for (int i = 0; (i < list.size()) && (i < typeList.getSize()); i++) {
				innerIndexes[indexes.length] = i;
				computeInitValue(array, eltType, list.get(i), innerIndexes);
			}
		} else {
			ValueUtil.set(type, array, doSwitch(expr), indexes);
		}
	}

	@Override
	public Object doSwitch(EObject eObject) {
		if (eObject == null) {
			return null;
		}
		return super.doSwitch(eObject);
	}

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
		Object value = doSwitch(expr);
		if (ValueUtil.isInt(value)) {
			return ((BigInteger) value).intValue();
		}

		// evaluated ok, but not as an integer
		throw new OrccRuntimeException("expected integer expression");
	}

	public void setType(TypeList typeList) {
		this.typeList = typeList;
	}

}
