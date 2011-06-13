/*
 * Copyright (c) 2011, IETR/INSA of Rennes
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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
import net.sf.orcc.cal.cal.AstVariable;
import net.sf.orcc.cal.cal.util.CalSwitch;
import net.sf.orcc.cal.util.Util;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.IrFactory;
import net.sf.orcc.ir.OpBinary;
import net.sf.orcc.ir.OpUnary;
import net.sf.orcc.ir.TypeList;
import net.sf.orcc.ir.Var;
import net.sf.orcc.util.OrccUtil;

import org.eclipse.emf.ecore.EObject;

/**
 * This class defines an AST to IR expression translator.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class AstToIrExprTranslator extends CalSwitch<Expression> {

	private Map<EObject, EObject> mapAstIr;

	public AstToIrExprTranslator(Map<EObject, EObject> mapAstIr) {
		this.mapAstIr = mapAstIr;
	}

	@Override
	public Expression caseAstExpressionBinary(AstExpressionBinary expression) {
		OpBinary op = OpBinary.getOperator(expression.getOperator());
		Expression e1 = doSwitch(expression.getLeft());
		Expression e2 = doSwitch(expression.getRight());

		return IrFactory.eINSTANCE.createExprBinary(e1, op, e2,
				Util.getType(expression));
	}

	@Override
	public Expression caseAstExpressionBoolean(AstExpressionBoolean expression) {
		boolean value = expression.isValue();
		return IrFactory.eINSTANCE.createExprBool(value);
	}

	@Override
	public Expression caseAstExpressionCall(AstExpressionCall astCall) {
		throw new OrccRuntimeException("unexpected call expression");
	}

	@Override
	public Expression caseAstExpressionIf(AstExpressionIf expression) {
		throw new OrccRuntimeException("unexpected if expression");
	}

	@Override
	public Expression caseAstExpressionIndex(AstExpressionIndex expression) {
		throw new OrccRuntimeException("unexpected index expression");
	}

	@Override
	public Expression caseAstExpressionInteger(AstExpressionInteger expression) {
		long value = expression.getValue();
		return IrFactory.eINSTANCE.createExprInt(value);
	}

	@Override
	public Expression caseAstExpressionList(AstExpressionList astExpression) {
		throw new OrccRuntimeException("unexpected list expression");
	}

	@Override
	public Expression caseAstExpressionString(AstExpressionString expression) {
		return IrFactory.eINSTANCE.createExprString(OrccUtil
				.getEscapedString(expression.getValue()));
	}

	@Override
	public Expression caseAstExpressionUnary(AstExpressionUnary expression) {
		OpUnary op = OpUnary.getOperator(expression.getUnaryOperator());
		Expression expr = doSwitch(expression.getExpression());

		if (OpUnary.NUM_ELTS == op) {
			TypeList typeList = (TypeList) expr.getType();
			return IrFactory.eINSTANCE.createExprInt(typeList.getSize());
		}

		return IrFactory.eINSTANCE.createExprUnary(op, expr,
				Util.getType(expression));
	}

	@Override
	public Expression caseAstExpressionVariable(AstExpressionVariable expression) {
		AstVariable astVariable = expression.getValue().getVariable();
		Var variable = (Var) mapAstIr.get(astVariable);
		return IrFactory.eINSTANCE.createExprVar(variable);
	}

	public Expression transformExpression(AstExpression expression) {
		return doSwitch(expression);
	}

	public List<Expression> transformExpressions(List<AstExpression> expressions) {
		List<Expression> expressionList = new ArrayList<Expression>(
				expressions.size());
		for (AstExpression expression : expressions) {
			expressionList.add(doSwitch(expression));
		}
		return expressionList;
	}

}
