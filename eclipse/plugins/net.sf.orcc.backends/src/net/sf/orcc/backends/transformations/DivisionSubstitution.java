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
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.InstAssign;
import net.sf.orcc.ir.InstCall;
import net.sf.orcc.ir.InstReturn;
import net.sf.orcc.ir.IrFactory;
import net.sf.orcc.ir.Node;
import net.sf.orcc.ir.NodeBlock;
import net.sf.orcc.ir.NodeIf;
//import net.sf.orcc.ir.NodeWhile;
import net.sf.orcc.ir.OpBinary;
import net.sf.orcc.ir.Procedure;
import net.sf.orcc.ir.Var;
import net.sf.orcc.ir.util.AbstractActorVisitor;
import net.sf.orcc.ir.util.IrUtil;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.util.EcoreUtil;

/**
 * This class defines a visitor that transforms a division into an equivalent
 * hardware compilable function specified in xilinx division model
 * 
 * @author Khaled Jerbi
 * @author Herve Yviquel
 * 
 */
public class DivisionSubstitution extends AbstractActorVisitor<Object> {
	private IrFactory factory = IrFactory.eINSTANCE;
	private Procedure divProc;

	public DivisionSubstitution() {
		super(true);
	}

	@Override
	public Object caseActor(Actor actor) {
		super.caseActor(actor);

		if (divProc != null) {
			actor.getProcs().add(0, divProc);
		}
		return null;
	}

	@Override
	public Object caseExprBinary(ExprBinary expr) {
		if (expr.getOp() == OpBinary.DIV) {
			if (divProc == null) {
				divProc = createDivProc();
			}

			// what ever the expression type of division operands they are
			// put in local variables VarNum and varDenum the result of
			// callInst is put in result
			Var varNum = procedure.newTempLocalVariable(
					factory.createTypeInt(16), "num");
			Var varDenum = procedure.newTempLocalVariable(
					factory.createTypeInt(16), "den");
			Var varResult = procedure.newTempLocalVariable(
					factory.createTypeInt(16), "result");

			InstAssign assign0 = factory.createInstAssign(varNum, expr.getE1());
			InstAssign assign1 = factory.createInstAssign(varDenum,
					expr.getE2());

			List<Expression> parameters = new ArrayList<Expression>();
			parameters.add(factory.createExprVar(varNum));
			parameters.add(factory.createExprVar(varDenum));

			InstCall call = factory.createInstCall(varResult, divProc,
					parameters);
			IrUtil.addInstBeforeExpr(expr, assign0, true);
			IrUtil.addInstBeforeExpr(expr, assign1, true);
			IrUtil.addInstBeforeExpr(expr, call, true);

			EcoreUtil.replace(expr, factory.createExprVar(varResult));
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
	 *            denumerator
	 * @return division function
	 */
	private Procedure createDivProc() {
		Procedure divProc = factory.createProcedure("DIV_II", 0,
				factory.createTypeInt());

		// Create parameters
		Var varNum = factory.createVarInt("num", true, 0);
		varNum.setType(factory.createTypeInt(16));
		Var varDenum = factory.createVarInt("den", true, 0);
		varDenum.setType(factory.createTypeInt(16));
		divProc.getParameters().add(factory.createParam(varNum));
		divProc.getParameters().add(factory.createParam(varDenum));

		// Create needed local variables
		Var result = divProc.newTempLocalVariable(factory.createTypeInt(16),
				"result");
		Var i = divProc.newTempLocalVariable(factory.createTypeInt(), "i");
		Var flipResult = divProc.newTempLocalVariable(factory.createTypeInt(),
				"flipResult");
		Var denom = divProc.newTempLocalVariable(factory.createTypeInt(),
				"denom");
		Var numer = divProc.newTempLocalVariable(factory.createTypeInt(),
				"numer");
		Var mask = divProc
				.newTempLocalVariable(factory.createTypeInt(), "mask");
		Var remainder = divProc.newTempLocalVariable(factory.createTypeInt(16),
				"remainder");

		// Create procedural code
		EList<Node> nodes = divProc.getNodes();
		nodes.add(createInitBlock(result, flipResult, denom,
				 numer,  mask, remainder));
		nodes.add(createNodeIf(varNum, flipResult));
		nodes.add(createNodeIf(varDenum, flipResult));
		nodes.add(createAssignmentBlock(varDenum, remainder, varNum, denom,
				mask, i));
		for (int k = 0; k < 16; k++) {
			createRepeatBlock(nodes, k, numer, remainder, denom, result, mask,
					varDenum);
		}
		nodes.add(createResultNodeIf(flipResult, result));

		// Create return instruction
		NodeBlock blockReturn = factory.createNodeBlock();
		InstReturn instReturn = factory.createInstReturn(factory
				.createExprVar(result));
		blockReturn.add(instReturn);
		divProc.setReturnType(factory.createTypeInt(16));
		divProc.getNodes().add(blockReturn);

		return divProc;
	}

	private NodeBlock createAssignmentBlock(Var varDenum, Var remainder,
			Var varNum, Var denom, Var mask, Var i) {
		NodeBlock block = factory.createNodeBlock();
		Expression blk11And = factory.createExprBinary(
				factory.createExprVar(varDenum), OpBinary.BITAND,
				factory.createExprInt(0x0000FFFF), factory.createTypeInt());
		block.add(factory.createInstAssign(remainder, varNum));
		block.add(factory.createInstAssign(denom, blk11And));
		block.add(factory.createInstAssign(mask, 0x8000));
		block.add(factory.createInstAssign(i, 0));
		return block;
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
	private NodeBlock createInitBlock(Var result, Var flipResult, Var denom,
			Var numer, Var mask, Var remainder) {
		NodeBlock initBlock = factory.createNodeBlock();
		initBlock.add(factory.createInstAssign(result, 0));
		initBlock.add(factory.createInstAssign(flipResult, 0));
		initBlock.add(factory.createInstAssign(denom, 0));
		initBlock.add(factory.createInstAssign(numer, 0));
		initBlock.add(factory.createInstAssign(mask, 0));
		initBlock.add(factory.createInstAssign(remainder, 0));
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
		InstAssign assign20 = factory.createInstAssign(var, var);
		blockIf_2.add(assign20);
		InstAssign assign21 = factory.createInstAssign(flip, flip);
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
	private NodeIf createNodeIfRepeatBlock(Var numer, Var denom, Var result,
			Var mask, Var remainder, Var varDenum, int k) {
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
				factory.createExprInt(15), OpBinary.MINUS,
				factory.createExprInt(k), factory.createTypeInt());
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
	private void createRepeatBlock(EList<Node> nodes, int k, Var numer,
			Var remainder, Var denom, Var result, Var mask, Var varDenum) {

		NodeBlock nodeBlk_0 = factory.createNodeBlock();
		NodeBlock nodeBlk_1 = factory.createNodeBlock();
	
		Expression andExpr = factory.createExprBinary(
				factory.createExprVar(remainder), OpBinary.BITAND,
				factory.createExprInt(0x0000FFFF), factory.createTypeInt());
		Expression minusExpr = factory.createExprBinary(
				factory.createExprInt(15), OpBinary.MINUS,
				factory.createExprInt(k), factory.createTypeInt());
		Expression shiftExpr = factory.createExprBinary(andExpr,
				OpBinary.SHIFT_RIGHT, minusExpr, factory.createTypeInt());

		InstAssign assignBlk_0 = factory.createInstAssign(numer, shiftExpr);

		nodeBlk_0.add(assignBlk_0);
		nodes.add(nodeBlk_0);

		NodeIf nodeIf = createNodeIfRepeatBlock(numer, denom, result, mask,
				remainder, varDenum, k);
		nodes.add(nodeIf);

		Expression maskShift = factory.createExprBinary(
				factory.createExprVar(mask), OpBinary.SHIFT_RIGHT,
				factory.createExprInt(1), factory.createTypeInt());
		Expression assignBlk_1Value = factory.createExprBinary(maskShift,
				OpBinary.BITAND, factory.createExprInt(0x7FFFFFFF),
				factory.createTypeInt());
		InstAssign assignBlk_10 = factory.createInstAssign(mask,
				assignBlk_1Value);
		nodeBlk_1.add(assignBlk_10);
		nodes.add(nodeBlk_1);
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