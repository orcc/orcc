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

import java.awt.Container;
import java.util.ArrayList;
import java.util.List;

import net.sf.orcc.ir.Actor;
import net.sf.orcc.ir.ExprBinary;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.InstAssign;
import net.sf.orcc.ir.InstCall;
import net.sf.orcc.ir.InstReturn;
import net.sf.orcc.ir.IrFactory;
import net.sf.orcc.ir.NodeBlock;
import net.sf.orcc.ir.NodeIf;
import net.sf.orcc.ir.NodeWhile;
import net.sf.orcc.ir.OpBinary;
import net.sf.orcc.ir.Procedure;
import net.sf.orcc.ir.Var;
import net.sf.orcc.ir.util.AbstractActorVisitor;
import net.sf.orcc.ir.util.EcoreHelper;

import org.eclipse.emf.ecore.util.EcoreUtil;

/**
 * This class defines a visitor that transforms a division into an equivalent
 * hardware compilable function specified in xilinx division model
 * 
 * @author Khaled Jerbi
 * 
 */
public class DivisionSubstitution extends AbstractActorVisitor<Object> {
	private IrFactory factory = IrFactory.eINSTANCE;
	private int counter;
	private Procedure divProc;
	private List<Expression> parameters;
	private Actor myActor;

	public DivisionSubstitution() {
		super(true);
	}

	@Override
	public Object caseActor(Actor actor) {
		myActor = actor;
		super.caseActor(actor);
		
		if (divProc != null) {
			actor.getProcs().add(0, divProc);
		}
		return null;
	}

	@Override
	public Object caseExprBinary(ExprBinary expr) {
		parameters = new ArrayList<Expression>();
		if (expr.getOp() == OpBinary.DIV) {
			// what ever the epression type of division operands they are
			// put in local variables VarNum and varDenum the result of
			// callInst is put in tmp
			Var varNum = factory.createVar(factory.createTypeInt(), "num",
					true, counter);
			Var varDenum = factory.createVar(factory.createTypeInt(), "den",
					true, counter);
			Var tmp = factory.createVar(factory.createTypeInt(), "divVar",
					true, 0);
			tmp.setGlobal(true);
			counter++;

			procedure.getLocals().add(varNum);
			procedure.getLocals().add(varDenum);
			if(counter == 1){
			myActor.getStateVars().add(tmp);
			}
			InstAssign assign0 = factory.createInstAssign(varNum, expr.getE1());
			InstAssign assign1 = factory.createInstAssign(varDenum,
					expr.getE2());
			InstAssign assign2 = factory.createInstAssign(tmp,
					factory.createExprInt(0));
			if (divProc == null) {
				divProc = createDivProc(tmp);
			}

			parameters.add(factory.createExprVar(varNum));
			parameters.add(factory.createExprVar(varDenum));

			InstCall call = factory.createInstCall(null, divProc, parameters);
			EcoreHelper.addInstBeforeExpr(expr, assign0, true);
			EcoreHelper.addInstBeforeExpr(expr, assign1, true);
			EcoreHelper.addInstBeforeExpr(expr, assign2, true);
			EcoreHelper.addInstBeforeExpr(expr, call, true);

			EcoreUtil.replace(expr, factory.createExprVar(tmp));
		}
		return null;
	}

	/**
	 * This method creates the alternative division function using the num and
	 * the denom
	 * 
	 * @param varNum
	 *            numerator
	 * @param varDenum
	 *            denomerator
	 * @return division function
	 */
	private Procedure createDivProc(Var tmp) {
		Procedure divProc = factory.createProcedure("DIV_II", 0,
				factory.createTypeInt());

		Var varNum = factory.createVar(factory.createTypeInt(), "num", true, 0);
		Var varDenum = factory.createVar(factory.createTypeInt(), "den", true,
				0);
		// counter++;
		divProc.getParameters().add(varNum);
		divProc.getParameters().add(varDenum);

		Var result = factory.createVar(factory.createTypeInt(), "result", true,
				0);
		divProc.getLocals().add(result);

		Var i = factory.createVar(factory.createTypeInt(), "i", true, 0);
		divProc.getLocals().add(i);

		Var flipResult = factory.createVar(factory.createTypeInt(),
				"flipResult", true, 0);
		divProc.getLocals().add(flipResult);

		Var denom = factory.createVar(factory.createTypeInt(64), "denom", true,
				0);
		divProc.getLocals().add(denom);

		Var numer = factory.createVar(factory.createTypeInt(64), "numer", true,
				0);
		divProc.getLocals().add(numer);

		Var mask = factory.createVar(factory.createTypeInt(), "mask", true, 0);
		divProc.getLocals().add(mask);

		Var remainder = factory.createVar(factory.createTypeInt(), "remainder",
				true, 0);
		divProc.getLocals().add(remainder);

		NodeBlock initBlock = createInitBlock(result, flipResult);
		divProc.getNodes().add(initBlock);

		NodeIf nodeIf_1 = createNodeIf(varNum, flipResult);
		divProc.getNodes().add(nodeIf_1);

		NodeIf nodeIf_2 = createNodeIf(varDenum, flipResult);
		divProc.getNodes().add(nodeIf_2);

		NodeBlock block_1 = factory.createNodeBlock();
		InstAssign assign_blk10 = factory.createInstAssign(remainder,
				factory.createExprVar(varNum));
		Expression blk11And = factory.createExprBinary(
				factory.createExprVar(varDenum), OpBinary.BITAND,
				factory.createExprInt(0xFFFFFFFFL), factory.createTypeInt());
		InstAssign assign_blk11 = factory.createInstAssign(denom, blk11And);
		InstAssign assign_blk12 = factory.createInstAssign(mask,
				factory.createExprInt(0x80000000L));
		InstAssign assign_blk13 = factory.createInstAssign(i,
				factory.createExprInt(0));
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

		NodeBlock stateVarUpDate = factory.createNodeBlock();
		InstAssign sateVarAssign = factory.createInstAssign(tmp,
				factory.createExprVar(result));
		stateVarUpDate.add(sateVarAssign);
		divProc.getNodes().add(stateVarUpDate);

		NodeBlock blockReturn = factory.createNodeBlock();
		InstReturn instReturn = factory.createInstReturn();
		blockReturn.add(instReturn);
		divProc.setReturnType(factory.createTypeVoid());
		divProc.getNodes().add(blockReturn);

		return divProc;
	}

	/**
	 * this method creates an initializing instructions for result and
	 * flipResult variables to zero
	 * 
	 * @param result
	 *            to be initialized to zero
	 * @param flipResult
	 *            to be initialized to zero
	 * @return node block with initialization assigns
	 */
	private NodeBlock createInitBlock(Var result, Var flipResult) {
		NodeBlock initBlock = factory.createNodeBlock();
		InstAssign initResult = factory.createInstAssign(result,
				factory.createExprInt(0));
		InstAssign initFlip = factory.createInstAssign(flipResult,
				factory.createExprInt(0));
		initBlock.add(initResult);
		initBlock.add(initFlip);
		return initBlock;
	}

	/**
	 * creates the following if node: if (var < 0) { var = -var; flip ^= 1; }
	 * 
	 * @param var
	 *            (see definition)
	 * @param flip
	 *            (see definition)
	 * @return if node
	 */
	private NodeIf createNodeIf(Var var, Var flip) {
		NodeIf nodeIf = factory.createNodeIf();
		NodeBlock blockIf_1 = factory.createNodeBlock();
		Expression conditionIf_1 = factory.createExprBinary(
				factory.createExprVar(var), OpBinary.LT,
				factory.createExprInt(0), factory.createTypeBool());
		nodeIf.setCondition(conditionIf_1);
		NodeBlock join = factory.createNodeBlock();
		nodeIf.setJoinNode(join);
		Expression oppNomerator = factory.createExprBinary(
				factory.createExprInt(0), OpBinary.MINUS,
				factory.createExprVar(var), factory.createTypeInt());
		InstAssign assign10 = factory.createInstAssign(var, oppNomerator);
		blockIf_1.add(assign10);
		Expression xorFlip = factory.createExprBinary(
				factory.createExprVar(flip), OpBinary.BITXOR,
				factory.createExprInt(1), factory.createTypeInt());
		InstAssign assign11 = factory.createInstAssign(flip, xorFlip);
		blockIf_1.add(assign11);
		nodeIf.getThenNodes().add(blockIf_1);
		NodeBlock blockIf_2 = factory.createNodeBlock();
		InstAssign assign20 = factory.createInstAssign(var,
				factory.createExprVar(var));
		blockIf_2.add(assign20);
		InstAssign assign21 = factory.createInstAssign(flip,
				factory.createExprVar(flip));
		blockIf_2.add(assign21);
		nodeIf.getElseNodes().add(blockIf_2);
		return nodeIf;
	}

	/**
	 * creates the required if node specified in the xilinx division model
	 * 
	 * @param numer
	 * @param denom
	 * @param result
	 * @param mask
	 * @param remainder
	 * @param varDenum
	 * @param i
	 * @return if Node
	 */
	private NodeIf createNodeIfWhile(Var numer, Var denom, Var result,
			Var mask, Var remainder, Var varDenum, Var i) {
		NodeIf nodeIf = factory.createNodeIf();
		Expression condition = factory.createExprBinary(
				factory.createExprVar(numer), OpBinary.GE,
				factory.createExprVar(denom), factory.createTypeBool());
		nodeIf.setCondition(condition);

		NodeBlock join = factory.createNodeBlock();
		nodeIf.setJoinNode(join);

		NodeBlock nodeBlk = factory.createNodeBlock();
		Expression orExpr = factory.createExprBinary(
				factory.createExprVar(result), OpBinary.BITOR,
				factory.createExprVar(mask), factory.createTypeInt());
		InstAssign assignBlk_0 = factory.createInstAssign(result, orExpr);
		nodeBlk.add(assignBlk_0);

		Expression minusExpr = factory.createExprBinary(
				factory.createExprInt(31), OpBinary.MINUS,
				factory.createExprVar(i), factory.createTypeInt());
		Expression lShiftExpr = factory.createExprBinary(
				factory.createExprVar(varDenum), OpBinary.SHIFT_LEFT,
				minusExpr, factory.createTypeInt());
		Expression RemainderMinus = factory.createExprBinary(
				factory.createExprVar(remainder), OpBinary.MINUS, lShiftExpr,
				factory.createTypeInt());
		InstAssign assignBlk_1 = factory.createInstAssign(remainder,
				RemainderMinus);
		nodeBlk.add(assignBlk_1);

		nodeIf.getThenNodes().add(nodeBlk);

		return nodeIf;
	}

	/**
	 * returns the required while node Specified in the xilinx division model
	 * 
	 * @param i
	 * @param numer
	 * @param remainder
	 * @param denom
	 * @param result
	 * @param mask
	 * @param varDenum
	 * @return
	 */
	private NodeWhile createNodeWhile(Var i, Var numer, Var remainder,
			Var denom, Var result, Var mask, Var varDenum) {
		NodeWhile nodeWhile = factory.createNodeWhile();

		Expression condition = factory.createExprBinary(
				factory.createExprVar(i), OpBinary.LT,
				factory.createExprInt(32), factory.createTypeBool());
		nodeWhile.setCondition(condition);
		NodeBlock joinWhile = factory.createNodeBlock();
		nodeWhile.setJoinNode(joinWhile);

		NodeBlock nodeBlk_0 = factory.createNodeBlock();

		Expression andExpr = factory.createExprBinary(
				factory.createExprVar(remainder), OpBinary.BITAND,
				factory.createExprInt(0xFFFFFFFFL), factory.createTypeInt());
		Expression minusExpr = factory.createExprBinary(
				factory.createExprInt(31), OpBinary.MINUS,
				factory.createExprVar(i), factory.createTypeInt());
		Expression shiftExpr = factory.createExprBinary(andExpr,
				OpBinary.SHIFT_RIGHT, minusExpr, factory.createTypeInt());

		InstAssign assignBlk_0 = factory.createInstAssign(numer, shiftExpr);

		nodeBlk_0.add(assignBlk_0);
		nodeWhile.getNodes().add(nodeBlk_0);

		NodeIf nodeIf = createNodeIfWhile(numer, denom, result, mask,
				remainder, varDenum, i);
		nodeWhile.getNodes().add(nodeIf);

		NodeBlock nodeBlk_1 = factory.createNodeBlock();
		Expression maskShift = factory.createExprBinary(
				factory.createExprVar(mask), OpBinary.SHIFT_RIGHT,
				factory.createExprInt(1), factory.createTypeInt());
		Expression assignBlk_1Value = factory.createExprBinary(maskShift,
				OpBinary.BITAND, factory.createExprInt(0x7FFFFFFFL),
				factory.createTypeInt());
		InstAssign assignBlk_10 = factory.createInstAssign(mask,
				assignBlk_1Value);
		nodeBlk_1.add(assignBlk_10);
		Expression iIncrement = factory.createExprBinary(
				factory.createExprVar(i), OpBinary.PLUS,
				factory.createExprInt(1), factory.createTypeInt());
		InstAssign assignBlk_11 = factory.createInstAssign(i, iIncrement);
		nodeBlk_1.add(assignBlk_11);
		nodeWhile.getNodes().add(nodeBlk_1);

		return nodeWhile;
	}

	/**
	 * returns an if node if (flipResult != 0) { result = -result; }
	 * 
	 * @param flipResult
	 *            (see definition)
	 * @param result
	 *            (see definition)
	 * @return If node (see definition)
	 */
	private NodeIf createResultNodeIf(Var flipResult, Var result) {
		NodeIf nodeIf = factory.createNodeIf();
		NodeBlock blockIf_1 = factory.createNodeBlock();
		Expression conditionIf = factory.createExprBinary(
				factory.createExprVar(flipResult), OpBinary.NE,
				factory.createExprInt(0), factory.createTypeBool());
		nodeIf.setCondition(conditionIf);
		NodeBlock join = factory.createNodeBlock();
		nodeIf.setJoinNode(join);
		Expression oppflip = factory.createExprBinary(factory.createExprInt(0),
				OpBinary.MINUS, factory.createExprVar(result),
				factory.createTypeInt());
		InstAssign assign10 = factory.createInstAssign(result, oppflip);
		blockIf_1.add(assign10);
		nodeIf.getThenNodes().add(blockIf_1);
		return nodeIf;
	}
}