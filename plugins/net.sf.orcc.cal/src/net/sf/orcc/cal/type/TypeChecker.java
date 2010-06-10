/*
 * Copyright (c) 2010, IETR/INSA of Rennes
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
package net.sf.orcc.cal.type;

import net.sf.orcc.OrccRuntimeException;
import net.sf.orcc.cal.cal.AstExpression;
import net.sf.orcc.cal.cal.AstExpressionBinary;
import net.sf.orcc.cal.cal.AstExpressionBoolean;
import net.sf.orcc.cal.cal.AstExpressionCall;
import net.sf.orcc.cal.cal.AstExpressionIf;
import net.sf.orcc.cal.cal.AstExpressionIndex;
import net.sf.orcc.cal.cal.AstExpressionInteger;
import net.sf.orcc.cal.cal.AstExpressionList;
import net.sf.orcc.cal.cal.AstExpressionString;
import net.sf.orcc.cal.cal.AstExpressionUnary;
import net.sf.orcc.cal.cal.AstExpressionVariable;
import net.sf.orcc.cal.cal.AstGenerator;
import net.sf.orcc.cal.cal.AstType;
import net.sf.orcc.cal.cal.AstTypeInt;
import net.sf.orcc.cal.cal.AstTypeUint;
import net.sf.orcc.cal.cal.CalFactory;
import net.sf.orcc.cal.cal.util.CalSwitch;
import net.sf.orcc.frontend.Util;
import net.sf.orcc.ir.expr.UnaryOp;

/**
 * This class defines a type checker for RVC-CAL AST.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class TypeChecker extends CalSwitch<AstType> {

	public boolean areTypeCompatible(AstType type) {
		// TODO Auto-generated method stub
		return true;
	}

	/*@Override
	public AstType caseAstExpressionBinary(AstExpressionBinary expression) {

	}

	@Override
	public AstType caseAstExpressionBoolean(AstExpressionBoolean expression) {
		return CalFactory.eINSTANCE.createAstTypeBool();
	}

	@Override
	public AstType caseAstExpressionCall(AstExpressionCall call) {
		for (AstExpression parameter : call.getParameters()) {
			if (doSwitch(parameter)) {
				return true;
			}
		}

		return false;
	}

	@Override
	public AstType caseAstExpressionIf(AstExpressionIf expression) {
		if (doSwitch(expression.getCondition())
				|| doSwitch(expression.getThen())
				|| doSwitch(expression.getElse())) {
			return true;
		}

		return false;
	}

	@Override
	public AstType caseAstExpressionIndex(AstExpressionIndex expression) {
		for (AstExpression index : expression.getIndexes()) {
			if (doSwitch(index)) {
				return true;
			}
		}

		return false;
	}

	@Override
	public AstType caseAstExpressionInteger(AstExpressionInteger expression) {
		return getUintType(expression.getValue());
	}

	@Override
	public AstType caseAstExpressionList(AstExpressionList expression) {
		for (AstExpression subExpression : expression.getExpressions()) {
			if (doSwitch(subExpression)) {
				return true;
			}
		}

		for (AstGenerator generator : expression.getGenerators()) {
			if (doSwitch(generator.getLower())
					|| doSwitch(generator.getHigher())) {
				return true;
			}
		}

		return false;
	}

	@Override
	public AstType caseAstExpressionString(AstExpressionString expression) {
		return false;
	}

	@Override
	public AstType caseAstExpressionUnary(AstExpressionUnary expression) {
		UnaryOp op = UnaryOp.getOperator(expression.getUnaryOperator());
		Object value = evaluate(expression.getExpression());

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
	public AstType caseAstExpressionVariable(AstExpressionVariable expression) {
		return false;
	}*/

	/**
	 * Computes and returns the type of the given expression.
	 * 
	 * @param expression
	 *            an AST expression
	 * @return a type
	 */
	public AstType getType(AstExpression expression) {
		return doSwitch(expression);
	}

	private AstTypeUint getUintType(int number) {
		int length = (int) Math.floor(Math.log(number) / Math.log(2)) + 1;
		AstExpressionInteger value = CalFactory.eINSTANCE
				.createAstExpressionInteger();
		value.setValue(length);

		AstTypeUint type = CalFactory.eINSTANCE.createAstTypeUint();
		type.setSize(value);
		return type;
	}

}
