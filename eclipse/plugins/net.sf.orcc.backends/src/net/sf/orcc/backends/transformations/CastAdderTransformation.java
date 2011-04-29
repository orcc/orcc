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
package net.sf.orcc.backends.transformations;

import net.sf.orcc.backends.instructions.InstCast;
import net.sf.orcc.backends.instructions.InstructionsFactory;
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
import net.sf.orcc.ir.Instruction;
import net.sf.orcc.ir.IrFactory;
import net.sf.orcc.ir.NodeIf;
import net.sf.orcc.ir.NodeWhile;
import net.sf.orcc.ir.Type;
import net.sf.orcc.ir.Var;
import net.sf.orcc.ir.util.AbstractActorVisitor;
import net.sf.orcc.ir.util.EcoreHelper;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.util.EcoreUtil;

/**
 * Add cast in IR in the form of assign instruction where target's type differs
 * from source type.
 * 
 * @author Jerome Goring
 * @author Herve Yviquel
 * @author Matthieu Wipliez
 * 
 */
public class CastAdderTransformation extends AbstractActorVisitor<Expression> {

	@Override
	public Expression caseExprBinary(ExprBinary expr) {
		expr.setE1(doSwitch(expr.getE1()));
		expr.setE2(doSwitch(expr.getE2()));
		return castExpression(expr);
	}

	@Override
	public Expression caseExprBool(ExprBool expr) {
		return castExpression(expr);
	}

	@Override
	public Expression caseExprFloat(ExprFloat expr) {
		return castExpression(expr);
	}

	@Override
	public Expression caseExprInt(ExprInt expr) {
		return castExpression(expr);
	}

	@Override
	public Expression caseExprList(ExprList expr) {
		return castExpression(expr);
	}

	@Override
	public Expression caseExprString(ExprString expr) {
		return castExpression(expr);
	}

	@Override
	public Expression caseExprUnary(ExprUnary expr) {
		expr.setExpr(doSwitch(expr.getExpr()));
		return castExpression(expr);
	}

	@Override
	public Expression caseExprVar(ExprVar expr) {
		return castExpression(expr);
	}

	@Override
	public Expression caseInstAssign(InstAssign assign) {
		assign.setValue(doSwitch(assign.getValue()));
		return null;
	}

	@Override
	public Expression caseInstCall(InstCall call) {
		EList<Expression> parameters = call.getParameters();
		for (Expression expr : parameters) {
			parameters.set(parameters.indexOf(expr), doSwitch(expr));
		}
		return null;
	}

	@Override
	public Expression caseInstLoad(InstLoad load) {
		EList<Expression> indexes = load.getIndexes();
		for (Expression expr : indexes) {
			indexes.set(indexes.indexOf(expr), doSwitch(expr));
		}

		Var source = load.getSource().getVariable();
		Var target = load.getTarget().getVariable();

		if (needCast(target.getType(), source.getType())) {
			Var newTarget = procedure.newTempLocalVariable(
					EcoreUtil.copy(source.getType()),
					"casted_" + target.getName());
			newTarget.setIndex(1);

			InstCast cast = InstructionsFactory.eINSTANCE.createInstCast(
					newTarget, target);

			load.getBlock().add(indexInst, cast);
			load.setTarget(IrFactory.eINSTANCE.createDef(newTarget));
		}

		return null;
	}

	@Override
	public Expression caseInstPhi(InstPhi phi) {
		EList<Expression> values = phi.getValues();
		for (Expression expr : values) {
			values.set(values.indexOf(expr), doSwitch(expr));
		}
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
		for (Expression expr : indexes) {
			indexes.set(indexes.indexOf(expr), doSwitch(expr));
		}
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

	private Expression castExpression(Expression expr) {
		Type parentType = getParentType(expr);

		if (needCast(expr.getType(), parentType)) {
			Var oldVar;
			if (expr.isVarExpr()) {
				oldVar = ((ExprVar) expr).getUse().getVariable();
			} else {
				oldVar = procedure.newTempLocalVariable(
						EcoreUtil.copy(expr.getType()),
						"expr_" + procedure.getName());
				InstAssign assign = IrFactory.eINSTANCE.createInstAssign(
						oldVar, EcoreHelper.copy(expr));
				EcoreHelper.addInstBeforeExpr(expr, assign);
			}

			Var newVar = procedure.newTempLocalVariable(
					EcoreUtil.copy(parentType),
					"castedExpr_" + procedure.getName());
			InstCast cast = InstructionsFactory.eINSTANCE.createInstCast(
					oldVar, newVar);
			EcoreHelper.addInstBeforeExpr(expr, cast);
			return IrFactory.eINSTANCE.createExprVar(newVar);
		}

		return expr;
	}

	private Type getParentType(Expression expr) {
		Expression parentExpr = EcoreHelper.getContainerOfType(expr,
				Expression.class);
		if (parentExpr != null) {
			return parentExpr.getType();
		} else {
			Instruction parentInst = EcoreHelper.getContainerOfType(expr,
					Instruction.class);
			if (parentInst.isStore()) {
				return ((InstStore) parentInst).getTarget().getVariable()
						.getType();
			} else if (parentInst.isPhi()) {
				return ((InstPhi) parentInst).getTarget().getVariable()
						.getType();
			} else if (parentInst.isReturn()) {
				return procedure.getReturnType();
			} else if (parentInst.isCall()) {
				int index = EcoreHelper.getContainingList(expr).indexOf(expr);
				return ((InstCall) parentInst).getProcedure().getParameters()
						.get(index).getType();
			}
		}
		return null;
	}

	private boolean needCast(Type type1, Type type2) {
		return (type1.getClass() != type2.getClass())
				|| (type1.getSizeInBits() != type2.getSizeInBits());
	}

}
