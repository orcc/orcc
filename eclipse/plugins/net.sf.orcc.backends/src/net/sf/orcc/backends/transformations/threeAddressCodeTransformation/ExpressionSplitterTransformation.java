/*
 * Copyright (c) 2009-2010, IETR/INSA of Rennes
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
package net.sf.orcc.backends.transformations.threeAddressCodeTransformation;

import net.sf.orcc.OrccRuntimeException;
import net.sf.orcc.ir.ExprBinary;
import net.sf.orcc.ir.ExprUnary;
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
import net.sf.orcc.ir.OpBinary;
import net.sf.orcc.ir.Type;
import net.sf.orcc.ir.Var;
import net.sf.orcc.ir.util.AbstractActorVisitor;
import net.sf.orcc.ir.util.EcoreHelper;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.util.EcoreUtil;

/**
 * Split expression and effective node that contains more than one fundamental
 * operation into a series of simple expressions.
 * 
 * @author Jerome GORIN
 * @author Matthieu Wipliez
 * @author Herve Yviquel
 * 
 */
public class ExpressionSplitterTransformation extends
		AbstractActorVisitor<Expression> {

	public ExpressionSplitterTransformation() {
		super(true);
	}

	@Override
	public Expression caseExprBinary(ExprBinary expr) {
		expr.setE1(doSwitch(expr.getE1()));
		expr.setE2(doSwitch(expr.getE2()));

		// Make a new assignment to the binary expression
		Var target = procedure.newTempLocalVariable(
				EcoreUtil.copy(expr.getType()), procedure.getName() + "_"
						+ "expr");
		// Add assignment to instruction's list
		EcoreHelper.addInstBeforeExpr(expr,
				IrFactory.eINSTANCE.createInstAssign(target, expr));

		return IrFactory.eINSTANCE.createExprVar(target);
	}

	@Override
	public Expression caseExprUnary(ExprUnary expr) {
		expr.setExpr(doSwitch(expr.getExpr()));

		// Make a new assignment to the binary expression
		Var target = procedure.newTempLocalVariable(
				EcoreUtil.copy(expr.getType()), procedure.getName() + "_"
						+ "expr");
		// Add assignment to instruction's list
		EcoreHelper.addInstBeforeExpr(expr, IrFactory.eINSTANCE
				.createInstAssign(target, transformUnaryExpr(expr)));

		return IrFactory.eINSTANCE.createExprVar(target);
	}

	@Override
	public Expression caseInstAssign(InstAssign assign) {
		assign.setValue(doSwitch(assign.getValue()));
		return null;
	}

	@Override
	public Expression caseInstCall(InstCall call) {
		EList<Expression> parameters = call.getParameters();
		EList<Expression> newParameters = new BasicEList<Expression>();
		for (Expression expr : parameters) {
			newParameters.add(doSwitch(expr));
		}
		parameters.clear();
		parameters.addAll(newParameters);
		return null;
	}

	@Override
	public Expression caseInstLoad(InstLoad load) {
		EList<Expression> indexes = load.getIndexes();
		EList<Expression> newIndexes = new BasicEList<Expression>();
		for (Expression expr : indexes) {
			newIndexes.add(doSwitch(expr));
		}
		indexes.clear();
		indexes.addAll(newIndexes);
		return null;
	}

	@Override
	public Expression caseInstPhi(InstPhi phi) {
		EList<Expression> values = phi.getValues();
		EList<Expression> newValues = new BasicEList<Expression>();
		for (Expression expr : values) {
			newValues.add(doSwitch(expr));
		}
		values.clear();
		values.addAll(newValues);
		return null;
	}

	@Override
	public Expression caseInstReturn(InstReturn returnInstr) {
		Expression expr = returnInstr.getValue();
		if (expr != null) {
			returnInstr.setValue(doSwitch(expr));
		}
		return null;
	}

	@Override
	public Expression caseInstStore(InstStore store) {
		EList<Expression> indexes = store.getIndexes();
		EList<Expression> newIndexes = new BasicEList<Expression>();
		for (Expression expr : indexes) {
			newIndexes.add(doSwitch(expr));
		}
		indexes.clear();
		indexes.addAll(newIndexes);
		doSwitch(store.getValue());
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

	public ExprBinary transformUnaryExpr(ExprUnary expr) {
		Expression constExpr;
		Type type = expr.getType();

		switch (expr.getOp()) {
		case MINUS:
			constExpr = IrFactory.eINSTANCE.createExprInt(0);
			return IrFactory.eINSTANCE.createExprBinary(constExpr,
					OpBinary.MINUS, expr, type);
		case LOGIC_NOT:
			constExpr = IrFactory.eINSTANCE.createExprBool(false);
			return IrFactory.eINSTANCE.createExprBinary(expr, OpBinary.EQ,
					constExpr, type);
		case BITNOT:
			return IrFactory.eINSTANCE.createExprBinary(expr, OpBinary.BITXOR,
					expr, type);
		default:
			throw new OrccRuntimeException("unsupported operator");
		}
	}

}