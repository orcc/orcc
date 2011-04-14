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
package net.sf.orcc.backends.vhdl.transformations;

import net.sf.orcc.ir.ExprBinary;
import net.sf.orcc.ir.ExprBool;
import net.sf.orcc.ir.ExprFloat;
import net.sf.orcc.ir.ExprInt;
import net.sf.orcc.ir.ExprList;
import net.sf.orcc.ir.ExprString;
import net.sf.orcc.ir.ExprUnary;
import net.sf.orcc.ir.ExprVar;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.IrFactory;
import net.sf.orcc.ir.NodeIf;
import net.sf.orcc.ir.NodeWhile;
import net.sf.orcc.ir.OpBinary;
import net.sf.orcc.ir.OpUnary;
import net.sf.orcc.ir.Type;
import net.sf.orcc.ir.util.AbstractActorVisitor;
import net.sf.orcc.ir.util.IrSwitch;

import org.eclipse.emf.ecore.util.EcoreUtil;

/**
 * This class defines an actor transformation that transform the simple
 * condition tests <code>var</code> and <code>not var</code> to
 * <code>var = true</code> and to <code>var = false</code> respectively.
 * 
 * @author Matthieu Wipliez
 * @author Nicolas Siret
 * 
 */
public class TransformConditionals extends AbstractActorVisitor<Object> {

	private class TransformExpressionInterpreter extends IrSwitch<Expression> {

		@Override
		public Expression caseExprBinary(ExprBinary expr) {
			Expression e1 = doSwitch(expr.getE1());
			Expression e2 = doSwitch(expr.getE2());
			OpBinary op = expr.getOp();
			Type type = expr.getType();
			return IrFactory.eINSTANCE.createExprBinary(e1, op, e2, type);
		}

		@Override
		public Expression caseExprBool(ExprBool expr) {
			return expr;
		}

		@Override
		public Expression caseExprFloat(ExprFloat expr) {
			return expr;
		}

		@Override
		public Expression caseExprInt(ExprInt expr) {
			return expr;
		}

		@Override
		public Expression caseExprList(ExprList expr) {
			return expr;
		}

		@Override
		public Expression caseExprString(ExprString expr) {
			return expr;
		}

		@Override
		public Expression caseExprUnary(ExprUnary expr) {
			OpUnary op = expr.getOp();
			Expression subExpr = expr.getExpr();
			if (op == OpUnary.LOGIC_NOT) {
				if (subExpr.isVarExpr()) {
					// "not a" => "a = false"
					return IrFactory.eINSTANCE.createExprBinary(subExpr,
							OpBinary.EQ,
							IrFactory.eINSTANCE.createExprBool(false),
							IrFactory.eINSTANCE.createTypeBool());
				} else {
					// "not (expr)" => "(expr) = false"
					return IrFactory.eINSTANCE.createExprBinary(
							doSwitch(subExpr), OpBinary.EQ,
							IrFactory.eINSTANCE.createExprBool(false),
							IrFactory.eINSTANCE.createTypeBool());
				}
			} else {
				return IrFactory.eINSTANCE.createExprUnary(op,
						doSwitch(subExpr), EcoreUtil.copy(expr.getType()));
			}
		}

		@Override
		public Expression caseExprVar(ExprVar expr) {
			if (expr.getType().isBool()) {
				return IrFactory.eINSTANCE.createExprBinary(expr, OpBinary.EQ,
						IrFactory.eINSTANCE.createExprBool(true),
						IrFactory.eINSTANCE.createTypeBool());
			} else {
				return expr;
			}
		}

	}

	@Override
	public Object caseNodeIf(NodeIf nodeIf) {
		nodeIf.setCondition(new TransformExpressionInterpreter()
				.doSwitch(nodeIf.getCondition()));
		super.visit(nodeIf);
		return null;
	}

	@Override
	public Object caseNodeWhile(NodeWhile nodeWhile) {
		nodeWhile.setCondition(new TransformExpressionInterpreter()
				.doSwitch(nodeWhile.getCondition()));
		super.visit(nodeWhile);
		return null;
	}

}
