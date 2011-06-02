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

package net.sf.orcc.backends.transformations;

import java.util.ArrayList;
import java.util.List;

import net.sf.orcc.ir.Actor;
import net.sf.orcc.ir.ExprBinary;
import net.sf.orcc.ir.ExprInt;
import net.sf.orcc.ir.ExprVar;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.InstAssign;
import net.sf.orcc.ir.InstCall;
import net.sf.orcc.ir.IrFactory;
import net.sf.orcc.ir.NodeBlock;
import net.sf.orcc.ir.NodeIf;
import net.sf.orcc.ir.NodeWhile;
import net.sf.orcc.ir.OpBinary;
import net.sf.orcc.ir.Procedure;
import net.sf.orcc.ir.Type;
import net.sf.orcc.ir.Var;
import net.sf.orcc.ir.impl.IrFactoryImpl;
import net.sf.orcc.ir.util.AbstractActorVisitor;
import net.sf.orcc.ir.util.EcoreHelper;

/**
 * This class defines a visitor that transforms a division into an equivalent
 * hardware compilable function
 * 
 * @author Khaled Jerbi
 * 
 */
public class DivisionSubstitution extends AbstractActorVisitor<Object> {

	private Procedure divProc = IrFactory.eINSTANCE.createProcedure();
	private Type typeInt = IrFactory.eINSTANCE.createTypeInt();
	private Type typeBool = IrFactory.eINSTANCE.createTypeBool();

	@Override
	public Object caseInstAssign(InstAssign assign) {
		Actor actor = EcoreHelper.getContainerOfType(procedure, Actor.class);
		parameters = new ArrayList<Expression>();
		if (assign.getValue().isBinaryExpr()) {
			ExprBinary expr = (ExprBinary) assign.getValue();
			OpBinary op = expr.getOp();
			if (op == OpBinary.DIV) {
				Var varNum = IrFactory.eINSTANCE.createVar(typeInt, "num",
						true, 0);
				Var varDenum = IrFactory.eINSTANCE.createVar(typeInt, "den",
						true, 0);
				procedure.getLocals().add(varNum);
				procedure.getLocals().add(varDenum);

				InstAssign assign0 = IrFactory.eINSTANCE.createInstAssign(
						varNum, expr.getE1());
				InstAssign assign1 = IrFactory.eINSTANCE.createInstAssign(
						varDenum, expr.getE2());
				NodeBlock blk = (NodeBlock) assign.getBlock();

				// creation and addition of the new div function once per
				// actor
				
				if (!actor.getProcs().contains(divProc)) {
					divProc = createDivProc(varNum, varDenum);
					actor.getProcs().add(0, divProc);
				}
				
				parameters.add(IrFactory.eINSTANCE.createExprVar(varNum));
				parameters.add(IrFactory.eINSTANCE.createExprVar(varDenum));
				//IrFactory.eINSTANCE.createVar(
					//	IrFactory.eINSTANCE.createTypeInt(), "temp", true, 0);
				InstCall call = IrFactory.eINSTANCE.createInstCall(assign
						.getTarget().getVariable(), divProc, parameters);
				//blk.getInstructions().remove(assign);
				blk.add(indexInst, call);
				blk.add(indexInst, assign1);
				blk.add(indexInst, assign0);
				//EcoreHelper.delete(assign);

			}
		}
		// }
		return null;
	}

	private List<Expression> parameters;

	/**
	 * This method creates the alternative division function using the nom and
	 * the denom
	 * 
	 * @param varNum
	 * @param varDenum
	 * @return
	 */
	private Procedure createDivProc(Var varNum, Var varDenum) {
		Procedure divProc = IrFactory.eINSTANCE.createProcedure("DIV_II", 0,
				IrFactory.eINSTANCE.createTypeInt());
		divProc.getParameters().add(varNum);
		divProc.getParameters().add(varDenum);

		Var result = divProc.newTempLocalVariable(
				IrFactory.eINSTANCE.createTypeInt(), "result");
		Var i = divProc.newTempLocalVariable(
				IrFactory.eINSTANCE.createTypeInt(), "i");
		Var flipResult = divProc.newTempLocalVariable(
				IrFactory.eINSTANCE.createTypeInt(), "flipResult");
		Var denom = divProc.newTempLocalVariable(
				IrFactory.eINSTANCE.createTypeInt(64), "denom");
		Var numer = divProc.newTempLocalVariable(
				IrFactory.eINSTANCE.createTypeInt(64), "numer");
		Var mask = divProc.newTempLocalVariable(
				IrFactory.eINSTANCE.createTypeInt(), "mask");
		Var remainder = divProc.newTempLocalVariable(
				IrFactory.eINSTANCE.createTypeInt(), "remainder");

		NodeIf nodeIf_1 = createNodeIf(varNum, flipResult);
		divProc.getNodes().add(nodeIf_1);

		NodeIf nodeIf_2 = createNodeIf(varDenum, flipResult);
		divProc.getNodes().add(nodeIf_2);

		NodeBlock block_1 = IrFactoryImpl.eINSTANCE.createNodeBlock();
		InstAssign assign_blk10 = IrFactory.eINSTANCE.createInstAssign(
				remainder, IrFactory.eINSTANCE.createExprVar(varNum));
		Expression blk11And = IrFactory.eINSTANCE.createExprBinary(
				IrFactory.eINSTANCE.createExprVar(varDenum), OpBinary.BITAND,
				IrFactory.eINSTANCE.createExprInt(0xFFFFFFFFL),
				IrFactory.eINSTANCE.createTypeInt());
		InstAssign assign_blk11 = IrFactory.eINSTANCE.createInstAssign(denom,
				blk11And);
		InstAssign assign_blk12 = IrFactory.eINSTANCE.createInstAssign(mask,
				IrFactory.eINSTANCE.createExprInt(0x80000000L));
		InstAssign assign_blk13 = IrFactory.eINSTANCE.createInstAssign(i,
				IrFactory.eINSTANCE.createExprInt(0));
		block_1.add(assign_blk10);
		block_1.add(assign_blk11);
		block_1.add(assign_blk12);
		block_1.add(assign_blk13);
		divProc.getNodes().add(block_1);

		NodeWhile nodeWhile = createNodeWhile(i, numer, remainder, denom,
				result, mask, varDenum);
		divProc.getNodes().add(nodeWhile);
		NodeIf nodeIf_3 = createResultNodeIf(flipResult, result);
		divProc.getNodes().add(nodeIf_3);

		NodeBlock blockReturn = IrFactoryImpl.eINSTANCE.createNodeBlock();
		blockReturn.add(IrFactory.eINSTANCE
				.createInstReturn(IrFactory.eINSTANCE.createExprVar(result)));
		divProc.getNodes().add(blockReturn);

		return divProc;
	}

	private NodeWhile createNodeWhile(Var i, Var numer, Var remainder,
			Var denom, Var result, Var mask, Var varDenum) {
		NodeWhile nodeWhile = IrFactoryImpl.eINSTANCE.createNodeWhile();
		Expression condition = IrFactory.eINSTANCE.createExprBinary(
				IrFactory.eINSTANCE.createExprVar(i), OpBinary.LT,
				IrFactory.eINSTANCE.createExprInt(32),
				IrFactory.eINSTANCE.createTypeBool());
		nodeWhile.setCondition(condition);

		NodeBlock nodeBlk_0 = IrFactory.eINSTANCE.createNodeBlock();
		Expression andExpr = IrFactory.eINSTANCE.createExprBinary(
				IrFactory.eINSTANCE.createExprVar(remainder), OpBinary.BITAND,
				IrFactory.eINSTANCE.createExprInt(0xFFFFFFFFL),
				IrFactory.eINSTANCE.createTypeInt());
		Expression minusExpr = IrFactory.eINSTANCE.createExprBinary(
				IrFactory.eINSTANCE.createExprInt(31), OpBinary.MINUS,
				IrFactory.eINSTANCE.createExprVar(i),
				IrFactory.eINSTANCE.createTypeInt());
		Expression shiftExpr = IrFactory.eINSTANCE.createExprBinary(andExpr,
				OpBinary.SHIFT_RIGHT, minusExpr,
				IrFactory.eINSTANCE.createTypeInt());
		InstAssign assignBlk_0 = IrFactory.eINSTANCE.createInstAssign(numer,
				shiftExpr);
		nodeBlk_0.add(assignBlk_0);
		nodeWhile.getNodes().add(nodeBlk_0);

		NodeIf nodeIf = createNodeIfWhile(numer, denom, result, mask,
				remainder, varDenum, i);
		nodeWhile.getNodes().add(nodeIf);

		NodeBlock nodeBlk_1 = IrFactory.eINSTANCE.createNodeBlock();
		Expression maskShift = IrFactory.eINSTANCE.createExprBinary(
				IrFactory.eINSTANCE.createExprVar(mask), OpBinary.SHIFT_RIGHT,
				IrFactory.eINSTANCE.createExprInt(1),
				IrFactory.eINSTANCE.createTypeInt());
		Expression assignBlk_1Value = IrFactory.eINSTANCE.createExprBinary(
				maskShift, OpBinary.BITAND,
				IrFactory.eINSTANCE.createExprInt(0x7FFFFFFFL),
				IrFactory.eINSTANCE.createTypeInt());
		InstAssign assignBlk_10 = IrFactory.eINSTANCE.createInstAssign(mask,
				assignBlk_1Value);
		nodeBlk_1.add(assignBlk_10);
		Expression iIncrement = IrFactory.eINSTANCE.createExprBinary(
				IrFactory.eINSTANCE.createExprVar(i), OpBinary.PLUS,
				IrFactory.eINSTANCE.createExprInt(1),
				IrFactory.eINSTANCE.createTypeInt());
		InstAssign assignBlk_11 = IrFactory.eINSTANCE.createInstAssign(i,
				iIncrement);
		nodeBlk_1.add(assignBlk_11);
		nodeWhile.getNodes().add(nodeBlk_1);
		return nodeWhile;
	}

	private NodeIf createNodeIf(Var var, Var flip) {
		NodeIf nodeIf = IrFactoryImpl.eINSTANCE.createNodeIf();
		NodeBlock blockIf_1 = IrFactoryImpl.eINSTANCE.createNodeBlock();
		Expression conditionIf_1 = IrFactory.eINSTANCE.createExprBinary(
				IrFactory.eINSTANCE.createExprVar(var), OpBinary.LT,
				IrFactory.eINSTANCE.createExprInt(0), typeBool);
		nodeIf.setCondition(conditionIf_1);
		Expression oppNomerator = IrFactory.eINSTANCE.createExprBinary(
				IrFactory.eINSTANCE.createExprInt(0), OpBinary.MINUS,
				IrFactory.eINSTANCE.createExprVar(var), typeInt);
		InstAssign assign10 = IrFactory.eINSTANCE.createInstAssign(var,
				oppNomerator);
		blockIf_1.add(assign10);
		Expression xorFlip = IrFactory.eINSTANCE.createExprBinary(
				IrFactory.eINSTANCE.createExprVar(flip), OpBinary.BITXOR,
				IrFactory.eINSTANCE.createExprInt(1), typeInt);
		InstAssign assign11 = IrFactory.eINSTANCE.createInstAssign(flip,
				xorFlip);
		blockIf_1.add(assign11);
		nodeIf.getThenNodes().add(blockIf_1);
		return nodeIf;
	}

	private NodeIf createNodeIfWhile(Var numer, Var denom, Var result,
			Var mask, Var remainder, Var varDenum, Var i) {
		NodeIf nodeIf = IrFactory.eINSTANCE.createNodeIf();
		Expression condition = IrFactory.eINSTANCE.createExprBinary(
				IrFactory.eINSTANCE.createExprVar(numer), OpBinary.GE,
				IrFactory.eINSTANCE.createExprVar(denom),
				IrFactory.eINSTANCE.createTypeInt());
		nodeIf.setCondition(condition);

		NodeBlock nodeBlk = IrFactory.eINSTANCE.createNodeBlock();
		Expression orExpr = IrFactory.eINSTANCE.createExprBinary(
				IrFactory.eINSTANCE.createExprVar(result), OpBinary.BITOR,
				IrFactory.eINSTANCE.createExprVar(mask),
				IrFactory.eINSTANCE.createTypeInt());
		InstAssign assignBlk_0 = IrFactory.eINSTANCE.createInstAssign(result,
				orExpr);
		nodeBlk.add(assignBlk_0);

		Expression minusExpr = IrFactory.eINSTANCE.createExprBinary(
				IrFactory.eINSTANCE.createExprInt(31), OpBinary.MINUS,
				IrFactory.eINSTANCE.createExprVar(i),
				IrFactory.eINSTANCE.createTypeInt());
		Expression lShiftExpr = IrFactory.eINSTANCE.createExprBinary(
				IrFactory.eINSTANCE.createExprVar(varDenum),
				OpBinary.SHIFT_LEFT, minusExpr,
				IrFactory.eINSTANCE.createTypeInt());
		Expression RemainderMinus = IrFactory.eINSTANCE.createExprBinary(
				IrFactory.eINSTANCE.createExprVar(remainder), OpBinary.MINUS,
				lShiftExpr, IrFactory.eINSTANCE.createTypeInt());
		InstAssign assignBlk_1 = IrFactory.eINSTANCE.createInstAssign(
				remainder, RemainderMinus);
		nodeBlk.add(assignBlk_1);

		nodeIf.getThenNodes().add(nodeBlk);

		return nodeIf;
	}

	private NodeIf createResultNodeIf(Var flipResult, Var result) {
		NodeIf nodeIf = IrFactoryImpl.eINSTANCE.createNodeIf();
		NodeBlock blockIf_1 = IrFactoryImpl.eINSTANCE.createNodeBlock();
		Expression conditionIf = IrFactory.eINSTANCE.createExprBinary(
				IrFactory.eINSTANCE.createExprVar(flipResult), OpBinary.NE,
				IrFactory.eINSTANCE.createExprInt(0), typeBool);
		nodeIf.setCondition(conditionIf);
		Expression oppflip = IrFactory.eINSTANCE.createExprBinary(
				IrFactory.eINSTANCE.createExprInt(0), OpBinary.MINUS,
				IrFactory.eINSTANCE.createExprVar(result), typeInt);
		InstAssign assign10 = IrFactory.eINSTANCE.createInstAssign(result,
				oppflip);
		blockIf_1.add(assign10);
		nodeIf.getThenNodes().add(blockIf_1);
		return nodeIf;
	}

}