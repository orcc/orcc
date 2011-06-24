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
import net.sf.orcc.ir.NodeIf;
import net.sf.orcc.ir.NodeWhile;
import net.sf.orcc.ir.Var;
import net.sf.orcc.ir.util.AbstractActorVisitor;
import net.sf.orcc.ir.util.IrUtil;

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
public class LiteralIntegersAdder extends AbstractActorVisitor<Expression> {

	private boolean usePreviousJoinNode;

	/**
	 * Creates a new transformation which put literals into variables
	 * 
	 * @param usePreviousJoinNode
	 *            <code>true</code> if the current IR form has join node before
	 *            while node
	 */
	public LiteralIntegersAdder(boolean usePreviousJoinNode) {
		super(true);
		this.usePreviousJoinNode = usePreviousJoinNode;
	}

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
		transformExpressionList(expr.getValue());
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
		if (value.isBinaryExpr() || value.isUnaryExpr()) {
			assign.setValue(doSwitch(value));
		}
		return null;
	}

	@Override
	public Expression caseInstCall(InstCall call) {
		transformExpressionList(call.getParameters());
		return null;
	}

	@Override
	public Expression caseInstLoad(InstLoad load) {
		transformExpressionList(load.getIndexes());
		return null;
	}

	@Override
	public Expression caseInstPhi(InstPhi phi) {
		transformExpressionList(phi.getValues());
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
		transformExpressionList(store.getIndexes());
		return null;
	}

	@Override
	public Expression caseNodeIf(NodeIf nodeIf) {
		nodeIf.setCondition(doSwitch(nodeIf.getCondition()));
		doSwitch(nodeIf.getThenNodes());
		doSwitch(nodeIf.getElseNodes());
		doSwitch(nodeIf.getJoinNode());
		return null;
	}

	@Override
	public Expression caseNodeWhile(NodeWhile nodeWhile) {
		nodeWhile.setCondition(doSwitch(nodeWhile.getCondition()));
		doSwitch(nodeWhile.getNodes());
		doSwitch(nodeWhile.getJoinNode());
		return null;
	}

	private Expression createExprVarAndAssign(Expression expr) {
		Var target = procedure.newTempLocalVariable(
				EcoreUtil.copy(expr.getType()), "literal");

		InstAssign assign = IrFactory.eINSTANCE.createInstAssign(target,
				IrUtil.copy(expr));
		IrUtil.addInstBeforeExpr(expr, assign, usePreviousJoinNode);

		return IrFactory.eINSTANCE.createExprVar(target);
	}

	private void transformExpressionList(EList<Expression> expressions) {
		EList<Expression> newExpressions = new BasicEList<Expression>();
		for (int i = 0; i < expressions.size();) {
			Expression expression = expressions.get(i);
			newExpressions.add(doSwitch(expression));
			if (expression != null) {
				i++;
			}
		}
		expressions.clear();
		expressions.addAll(newExpressions);
	}

}
