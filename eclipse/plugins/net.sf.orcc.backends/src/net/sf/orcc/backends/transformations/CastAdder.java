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
import net.sf.orcc.ir.TypeList;
import net.sf.orcc.ir.Var;
import net.sf.orcc.ir.util.AbstractActorVisitor;
import net.sf.orcc.ir.util.EcoreHelper;

import org.eclipse.emf.common.util.BasicEList;
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
public class CastAdder extends AbstractActorVisitor<Expression> {

	private boolean usePreviousJoinNode;
	private Type parentType;

	/**
	 * Creates a new cast transformation
	 * 
	 * @param usePreviousJoinNode
	 *            <code>true</code> if the current IR form has join node before
	 *            while node
	 */
	public CastAdder(boolean usePreviousJoinNode) {
		this.usePreviousJoinNode = usePreviousJoinNode;
	}

	@Override
	public Expression caseExprBinary(ExprBinary expr) {
		Type oldParentType = parentType;
		if (expr.getOp().isComparison()) {
			Type type1 = expr.getE1().getType();
			Type type2 = expr.getE2().getType();
			if (type1.getSizeInBits() < type2.getSizeInBits()) {
				parentType = type2;
			} else {
				parentType = type1;
			}
		} else {
			parentType = expr.getType();
		}
		expr.setE1(doSwitch(expr.getE1()));
		expr.setE2(doSwitch(expr.getE2()));
		parentType = oldParentType;
		return castExpression(expr);
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
		castExpressionList(expr.getValue());
		return expr;
	}

	@Override
	public Expression caseExprString(ExprString expr) {
		return expr;
	}

	@Override
	public Expression caseExprUnary(ExprUnary expr) {
		Type oldParentType = parentType;
		parentType = expr.getType();
		expr.setExpr(doSwitch(expr.getExpr()));
		parentType = oldParentType;
		return castExpression(expr);
	}

	@Override
	public Expression caseExprVar(ExprVar expr) {
		return castExpression(expr);
	}

	@Override
	public Expression caseInstAssign(InstAssign assign) {
		Type oldParentType = parentType;
		parentType = assign.getTarget().getVariable().getType();
		Expression newValue = doSwitch(assign.getValue());
		parentType = oldParentType;
		if(newValue != assign.getValue()){
			// Assign is useless anymore
			EList<Instruction> instructions = assign.getBlock().getInstructions();
			InstCast cast = (InstCast) instructions.get(instructions.indexOf(assign) - 1);
			cast.setTarget(IrFactory.eINSTANCE.createDef(assign.getTarget().getVariable()));
			
			EcoreHelper.delete(assign);
		}
		return null;
	}

	@Override
	public Expression caseInstCall(InstCall call) {
		/*
		Type oldParentType = parentType;
		EList<Expression> expressions = call.getParameters();
		EList<Expression> newExpressions = new BasicEList<Expression>();
		for (int i = 0; i < expressions.size();) {
			Expression expression = expressions.get(i);
			parentType = call.getProcedure().getParameters().get(i).getType();
			newExpressions.add(doSwitch(expression));
			if (expression != null) {
				i++;
			}
		}
		expressions.clear();
		expressions.addAll(newExpressions);
		parentType = oldParentType;
		*/
		return null;
	}

	@Override
	public Expression caseInstLoad(InstLoad load) {
		// Indexes are not casted...
		Var source = load.getSource().getVariable();
		Var target = load.getTarget().getVariable();

		Type uncastedType;

		if (load.getIndexes().isEmpty()) {
			// Load from a scalar variable
			uncastedType = EcoreHelper.copy(source.getType());
		} else {
			// Load from an array variable
			uncastedType = EcoreHelper.copy(((TypeList) source.getType())
					.getElementType());
		}

		if (needCast(target.getType(), uncastedType)) {
			Var castedTarget = procedure.newTempLocalVariable(target.getType(),
					"casted_" + target.getName());
			castedTarget.setIndex(1);

			target.setType(uncastedType);

			InstCast cast = InstructionsFactory.eINSTANCE.createInstCast(
					target, castedTarget);

			load.getBlock().add(indexInst + 1, cast);
		}
		return null;
	}

	@Override
	public Expression caseInstPhi(InstPhi phi) {
		Type oldParentType = parentType;
		parentType = phi.getTarget().getVariable().getType();
		castExpressionList(phi.getValues());
		parentType = oldParentType;
		return null;
	}

	@Override
	public Expression caseInstReturn(InstReturn returnInstr) {
		Expression expr = returnInstr.getValue();
		if (expr != null) {
			Type oldParentType = parentType;
			parentType = procedure.getReturnType();
			returnInstr.setValue(doSwitch(expr));
			parentType = oldParentType;
		}
		return null;
	}

	@Override
	public Expression caseInstStore(InstStore store) {
		// Indexes are not casted...
		Type oldParentType = parentType;
		if (store.getIndexes().isEmpty()) {
			// Store to a scalar variable
			parentType = store.getTarget().getVariable().getType();
		} else {
			// Store to an array variable
			parentType = ((TypeList) store.getTarget().getVariable().getType())
					.getElementType();
		}
		store.setValue(doSwitch(store.getValue()));
		parentType = oldParentType;
		return null;
	}

	@Override
	public Expression caseNodeIf(NodeIf nodeIf) {
		Type oldParentType = parentType;
		parentType = IrFactory.eINSTANCE.createTypeBool();
		nodeIf.setCondition(doSwitch(nodeIf.getCondition()));
		doSwitch(nodeIf.getThenNodes());
		doSwitch(nodeIf.getElseNodes());
		doSwitch(nodeIf.getJoinNode());
		parentType = oldParentType;
		return null;
	}

	@Override
	public Expression caseNodeWhile(NodeWhile nodeWhile) {
		Type oldParentType = parentType;
		parentType = IrFactory.eINSTANCE.createTypeBool();
		nodeWhile.setCondition(doSwitch(nodeWhile.getCondition()));
		doSwitch(nodeWhile.getNodes());
		doSwitch(nodeWhile.getJoinNode());
		parentType = oldParentType;
		return null;
	}

	private Expression castExpression(Expression expr) {
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
				EcoreHelper
						.addInstBeforeExpr(expr, assign, usePreviousJoinNode);
			}

			Var newVar = procedure.newTempLocalVariable(
					EcoreUtil.copy(parentType),
					"castedExpr_" + procedure.getName());
			InstCast cast = InstructionsFactory.eINSTANCE.createInstCast(
					oldVar, newVar);
			EcoreHelper.addInstBeforeExpr(expr, cast, usePreviousJoinNode);
			EcoreHelper.delete(expr);
			return IrFactory.eINSTANCE.createExprVar(newVar);
		}

		return expr;
	}

	private void castExpressionList(EList<Expression> expressions) {
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

	private boolean needCast(Type type1, Type type2) {
		return (type1.getClass() != type2.getClass())
				|| (type1.getSizeInBits() != type2.getSizeInBits());
	}

}
