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

import java.util.List;

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
import net.sf.orcc.ir.Node;
import net.sf.orcc.ir.NodeBlock;
import net.sf.orcc.ir.NodeIf;
import net.sf.orcc.ir.NodeWhile;
import net.sf.orcc.ir.OpBinary;
import net.sf.orcc.ir.Type;
import net.sf.orcc.ir.TypeList;
import net.sf.orcc.ir.Var;
import net.sf.orcc.ir.util.AbstractActorVisitor;
import net.sf.orcc.ir.util.IrUtil;
import net.sf.orcc.util.EcoreHelper;

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

	private boolean castToUnsigned;
	private Type parentType;
	private boolean usePreviousJoinNode;

	/**
	 * Creates a new cast transformation
	 * 
	 * @param usePreviousJoinNode
	 *            <code>true</code> if the current IR form has join node before
	 *            while node
	 */
	public CastAdder(boolean usePreviousJoinNode, boolean castToUnsigned) {
		this.usePreviousJoinNode = usePreviousJoinNode;
		this.castToUnsigned = castToUnsigned;
	}

	@Override
	public Expression caseExprBinary(ExprBinary expr) {
		Type oldParentType = parentType;
		Expression e1 = expr.getE1();
		Expression e2 = expr.getE2();
		if (isTypeReducer(expr.getOp())) {
			// TOFIX: Probably a better solution
			expr.setType(IrUtil.copy(getBigger(e1.getType(), e2.getType())));
		}
		if (expr.getOp().isComparison()) {
			parentType = getBigger(e1.getType(), e2.getType());
		} else {
			parentType = expr.getType();
		}
		expr.setE1(doSwitch(e1));
		expr.setE2(doSwitch(e2));
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
		if (newValue != assign.getValue()) {
			// Assign is useless anymore
			EList<Instruction> instructions = assign.getBlock()
					.getInstructions();
			InstCast cast = (InstCast) instructions.get(instructions
					.indexOf(assign) - 1);
			cast.setTarget(IrFactory.eINSTANCE.createDef(assign.getTarget()
					.getVariable()));

			IrUtil.delete(assign);
		}
		return null;
	}

	@Override
	public Expression caseInstCall(InstCall call) {
		if (!call.isPrint()) {
			Type oldParentType = parentType;
			Iterable<Expression> expressions = EcoreHelper.getObjects(call,
					Expression.class);
			EList<Expression> oldExpressions = new BasicEList<Expression>();
			for (Expression expr : expressions) {
				oldExpressions.add(expr);
			}

			EList<Expression> newExpressions = new BasicEList<Expression>();
			for (int i = 0; i < oldExpressions.size(); i++) {
				parentType = call.getProcedure().getParameters().get(i)
						.getVariable().getType();
				newExpressions.add(doSwitch(oldExpressions.get(i)));
			}

			call.getParameters().clear();
			call.getParameters().addAll(
					IrFactory.eINSTANCE.createArgsByVal(newExpressions));

			parentType = oldParentType;
		}
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
			uncastedType = IrUtil.copy(source.getType());
		} else {
			// Load from an array variable
			uncastedType = IrUtil.copy(((TypeList) source.getType())
					.getInnermostType());
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
		EList<Expression> values = phi.getValues();
		Node containingNode = (Node) phi.eContainer().eContainer();
		Expression value0 = phi.getValues().get(0);
		Expression value1 = phi.getValues().get(1);
		if (containingNode.isIfNode()) {
			NodeIf nodeIf = (NodeIf) containingNode;
			if (value0.isVarExpr()) {
				NodeBlock block0 = IrFactory.eINSTANCE.createNodeBlock();
				nodeIf.getThenNodes().add(block0);
				values.set(0, castExpression(value0, block0, 0));
			}
			if (value1.isVarExpr()) {
				NodeBlock block1 = IrFactory.eINSTANCE.createNodeBlock();
				nodeIf.getElseNodes().add(block1);
				values.set(1, castExpression(value1, block1, 0));
			}
		} else {
			NodeWhile nodeWhile = (NodeWhile) containingNode;
			if (value0.isVarExpr()) {
				NodeBlock block = IrFactory.eINSTANCE.createNodeBlock();
				EcoreHelper.getContainingList(containingNode).add(indexNode,
						block);
				indexNode++;
				values.set(0, castExpression(value0, block, 0));
			}
			if (value1.isVarExpr()) {
				NodeBlock block = IrFactory.eINSTANCE.createNodeBlock();
				nodeWhile.getNodes().add(block);
				values.set(1, castExpression(value1, block, 0));
			}
		}
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
					.getInnermostType();
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
						oldVar, IrUtil.copy(expr));
				IrUtil.addInstBeforeExpr(expr, assign, usePreviousJoinNode);
			}

			Var newVar = procedure.newTempLocalVariable(
					EcoreUtil.copy(parentType),
					"castedExpr_" + procedure.getName());
			InstCast cast = InstructionsFactory.eINSTANCE.createInstCast(
					oldVar, newVar);
			if (IrUtil.addInstBeforeExpr(expr, cast, usePreviousJoinNode)) {
				indexInst++;
			}
			IrUtil.delete(expr);
			return IrFactory.eINSTANCE.createExprVar(newVar);
		}

		return expr;
	}

	private Expression castExpression(Expression expr, NodeBlock node, int index) {
		if (needCast(expr.getType(), parentType)) {
			Var oldVar;
			if (expr.isVarExpr()) {
				oldVar = ((ExprVar) expr).getUse().getVariable();
			} else {
				oldVar = procedure.newTempLocalVariable(
						EcoreUtil.copy(expr.getType()),
						"expr_" + procedure.getName());
				InstAssign assign = IrFactory.eINSTANCE.createInstAssign(
						oldVar, IrUtil.copy(expr));
				node.add(index, assign);
				index++;
			}

			Var newVar = procedure.newTempLocalVariable(
					EcoreUtil.copy(parentType),
					"castedExpr_" + procedure.getName());
			InstCast cast = InstructionsFactory.eINSTANCE.createInstCast(
					oldVar, newVar);
			node.add(index, cast);
			return IrFactory.eINSTANCE.createExprVar(newVar);
		}

		return expr;
	}

	private void castExpressionList(EList<Expression> expressions) {
		EList<Expression> oldExpression = new BasicEList<Expression>(
				expressions);
		EList<Expression> newExpressions = new BasicEList<Expression>();
		for (Expression expression : oldExpression) {
			newExpressions.add(doSwitch(expression));
		}
		expressions.clear();
		expressions.addAll(newExpressions);
	}

	private Type getBigger(Type type1, Type type2) {
		if (type1.getSizeInBits() < type2.getSizeInBits()) {
			return type2;
		} else {
			return type1;
		}
	}

	private boolean isTypeReducer(OpBinary op) {
		switch (op) {
		case SHIFT_LEFT:
		case SHIFT_RIGHT:
		case MOD:
			return true;
		default:
			return false;
		}
	}

	private boolean needCast(Type type1, Type type2) {
		if (type1.isList() && type2.isList()) {
			TypeList typeList1 = (TypeList) type1;
			TypeList typeList2 = (TypeList) type2;
			List<Integer> dim1 = typeList1.getDimensions();
			List<Integer> dim2 = typeList2.getDimensions();
			for (int i = 0; i < dim1.size() && i < dim2.size(); i++) {
				if (!dim1.get(i).equals(dim2.get(i))) {
					return true;
				}
			}
			return needCast(typeList1.getInnermostType(),
					typeList2.getInnermostType());
		} else {
			return (type1.getSizeInBits() != type2.getSizeInBits())
					|| (castToUnsigned && type1.getClass() != type2.getClass())
					|| (!((type1.isInt() && type2.isUint()) || (type1.isUint() && type2
							.isInt())) && (type1.getClass() != type2.getClass()));
		}
	}

}
