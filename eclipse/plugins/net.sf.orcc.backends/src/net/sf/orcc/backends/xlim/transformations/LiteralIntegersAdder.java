/*
 * Copyright (c) 2009, Ecole Polytechnique Fédérale de Lausanne
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
 *   * Neither the name of the Ecole Polytechnique Fédérale de Lausanne nor the names of its
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
package net.sf.orcc.backends.xlim.transformations;

import java.util.List;

import net.sf.orcc.ir.BlockIf;
import net.sf.orcc.ir.BlockWhile;
import net.sf.orcc.ir.ExprBinary;
import net.sf.orcc.ir.ExprBool;
import net.sf.orcc.ir.ExprFloat;
import net.sf.orcc.ir.ExprInt;
import net.sf.orcc.ir.ExprList;
import net.sf.orcc.ir.ExprString;
import net.sf.orcc.ir.ExprUnary;
import net.sf.orcc.ir.ExprVar;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.InstAssign;
import net.sf.orcc.ir.InstCall;
import net.sf.orcc.ir.InstLoad;
import net.sf.orcc.ir.InstPhi;
import net.sf.orcc.ir.InstReturn;
import net.sf.orcc.ir.InstStore;
import net.sf.orcc.ir.IrFactory;
import net.sf.orcc.ir.Var;
import net.sf.orcc.ir.util.AbstractIrVisitor;
import net.sf.orcc.ir.util.IrUtil;
import net.sf.orcc.util.util.EcoreHelper;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.util.EcoreUtil;

/**
 * 
 * This class defines a transformation that transforms literals used in
 * instructions into variables. This transformation is needed since XLIM ports
 * cannot contain literals.
 * 
 * @author Ghislain Roquier
 * @author Herve Yviquel
 * 
 */
public class LiteralIntegersAdder extends AbstractIrVisitor<Expression> {

	@Override
	public Expression caseExprBinary(ExprBinary expr) {
		expr.setE1(doSwitch(expr.getE1()));
		expr.setE2(doSwitch(expr.getE2()));
		return expr;
	}

	@Override
	public Expression caseExprBool(ExprBool expr) {
		return createExprVarAndAssign(expr);
	}

	@Override
	public Expression caseExprFloat(ExprFloat expr) {
		return createExprVarAndAssign(expr);
	}

	@Override
	public Expression caseExprInt(ExprInt expr) {
		return createExprVarAndAssign(expr);
	}

	@Override
	public Expression caseExprList(ExprList expr) {
		List<Expression> newIndexes = transformExpressionList(expr.getValue());
		expr.getValue().clear();
		expr.getValue().addAll(newIndexes);
		return null;
	}

	@Override
	public Expression caseExprString(ExprString expr) {
		return expr;
	}

	@Override
	public Expression caseExprUnary(ExprUnary expr) {
		expr.setExpr(doSwitch(expr.getExpr()));
		return expr;
	}

	@Override
	public Expression caseExprVar(ExprVar expr) {
		return expr;
	}

	@Override
	public Expression caseInstAssign(InstAssign assign) {
		Expression value = assign.getValue();
		if (value.isExprBinary() || value.isExprUnary()) {
			assign.setValue(doSwitch(value));
		}
		return null;
	}

	@Override
	public Expression caseInstCall(InstCall call) {
		List<Expression> newArgs = transformExpressionList(EcoreHelper
				.getObjects(call, Expression.class));
		call.getParameters().clear();
		call.getParameters().addAll(
				IrFactory.eINSTANCE.createArgsByVal(newArgs));
		return null;
	}

	@Override
	public Expression caseInstLoad(InstLoad load) {
		List<Expression> newIndexes = transformExpressionList(load.getIndexes());
		load.getIndexes().clear();
		load.getIndexes().addAll(newIndexes);
		return null;
	}

	@Override
	public Expression caseInstPhi(InstPhi phi) {
		List<Expression> newIndexes = transformExpressionList(phi.getValues());
		phi.getValues().clear();
		phi.getValues().addAll(newIndexes);
		return null;
	}

	@Override
	public Expression caseInstReturn(InstReturn returnInstr) {
		if (!procedure.getReturnType().isVoid()) {
			Expression expr = returnInstr.getValue();
			returnInstr.setValue(doSwitch(expr));
		}
		return null;
	}

	@Override
	public Expression caseInstStore(InstStore store) {
		store.setValue(doSwitch(store.getValue()));
		List<Expression> newIndexes = transformExpressionList(store
				.getIndexes());
		store.getIndexes().clear();
		store.getIndexes().addAll(newIndexes);
		return null;
	}

	@Override
	public Expression caseBlockIf(BlockIf nodeIf) {
		nodeIf.setCondition(doSwitch(nodeIf.getCondition()));
		doSwitch(nodeIf.getThenBlocks());
		doSwitch(nodeIf.getElseBlocks());
		doSwitch(nodeIf.getJoinBlock());
		return null;
	}

	@Override
	public Expression caseBlockWhile(BlockWhile nodeWhile) {
		nodeWhile.setCondition(doSwitch(nodeWhile.getCondition()));
		doSwitch(nodeWhile.getBlocks());
		doSwitch(nodeWhile.getJoinBlock());
		return null;
	}

	private Expression createExprVarAndAssign(Expression expr) {
		Var target = procedure.newTempLocalVariable(
				EcoreUtil.copy(expr.getType()), "literal");

		InstAssign assign = IrFactory.eINSTANCE.createInstAssign(target,
				IrUtil.copy(expr));
		IrUtil.addInstBeforeExpr(expr, assign);

		return IrFactory.eINSTANCE.createExprVar(target);
	}

	private List<Expression> transformExpressionList(
			Iterable<Expression> expressions) {
		List<Expression> oldExpressions = new BasicEList<Expression>();
		for (Expression expr : expressions) {
			oldExpressions.add(expr);
		}

		EList<Expression> newExpressions = new BasicEList<Expression>();
		for (int i = 0; i < oldExpressions.size();) {
			Expression expression = oldExpressions.get(i);
			newExpressions.add(doSwitch(expression));
			if (expression != null) {
				i++;
			}
		}

		return newExpressions;
	}

}
