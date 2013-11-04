/*
 * Copyright (c) 2009, IETR/INSA of Rennes
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
package net.sf.orcc.ir.transform;

import static net.sf.orcc.util.SwitchUtil.DONE;
import static net.sf.orcc.util.SwitchUtil.visit;

import java.util.ArrayList;
import java.util.List;

import net.sf.orcc.ir.Block;
import net.sf.orcc.ir.BlockBasic;
import net.sf.orcc.ir.BlockIf;
import net.sf.orcc.ir.BlockWhile;
import net.sf.orcc.ir.ExprVar;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.InstAssign;
import net.sf.orcc.ir.InstPhi;
import net.sf.orcc.ir.IrFactory;
import net.sf.orcc.ir.Procedure;
import net.sf.orcc.ir.Var;
import net.sf.orcc.ir.util.AbstractIrVisitor;
import net.sf.orcc.ir.util.IrUtil;
import net.sf.orcc.util.Void;
import net.sf.orcc.util.util.EcoreHelper;

/**
 * This class removes phi assignments and transforms them to copies.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class PhiRemoval extends AbstractIrVisitor<Void> {

	private class PhiRemover extends AbstractIrVisitor<Void> {

		@Override
		public Void caseInstPhi(InstPhi phi) {
			IrUtil.delete(phi);
			indexInst--;
			return null;
		}

	}

	private List<Var> localsToRemove;

	private int phiIndex;

	private BlockBasic targetBlock;

	@Override
	public Void caseBlockBasic(BlockBasic block) {
		visit(this, block.getInstructions());
		return null;
	}

	@Override
	public Void caseBlockIf(BlockIf block) {
		BlockBasic join = block.getJoinBlock();
		targetBlock = IrUtil.getLast(block.getThenBlocks());
		phiIndex = 0;
		caseBlockBasic(join);

		targetBlock = IrUtil.getLast(block.getElseBlocks());
		phiIndex = 1;
		caseBlockBasic(join);
		new PhiRemover().caseBlockBasic(join);

		doSwitch(block.getThenBlocks());
		doSwitch(block.getElseBlocks());

		return null;
	}

	@Override
	public Void caseBlockWhile(BlockWhile block) {
		List<Block> blocks = EcoreHelper.getContainingList(block);
		// the block before the while.
		if (indexBlock > 0) {
			Block previousBlock = blocks.get(indexBlock - 1);
			if (previousBlock.isBlockBasic()) {
				targetBlock = (BlockBasic) previousBlock;
			} else {
				targetBlock = IrFactory.eINSTANCE.createBlockBasic();
				blocks.add(indexBlock, targetBlock);
			}
		} else {
			targetBlock = IrFactory.eINSTANCE.createBlockBasic();
			blocks.add(indexBlock, targetBlock);
		}

		BlockBasic join = block.getJoinBlock();
		phiIndex = 0;
		caseBlockBasic(join);

		// last block of the while
		targetBlock = IrUtil.getLast(block.getBlocks());
		phiIndex = 1;
		caseBlockBasic(join);
		new PhiRemover().caseBlockBasic(join);

		// visit inner blocks
		doSwitch(block.getBlocks());

		return null;
	}

	@Override
	public Void caseInstPhi(InstPhi phi) {
		Var target = phi.getTarget().getVariable();
		ExprVar sourceExpr = (ExprVar) phi.getValues().get(phiIndex);
		Var source = sourceExpr.getUse().getVariable();

		// if source is a local variable with index = 0, we remove it from the
		// procedure and translate the PHI by an assignment of 0 (zero) to
		// target. Otherwise, we just create an assignment target = source.
		Expression expr;
		if (source.getIndex() == 0 && !source.isParam()) {
			localsToRemove.add(source);
			if (target.getType().isBool()) {
				expr = IrFactory.eINSTANCE.createExprBool(false);
			} else {
				expr = IrFactory.eINSTANCE.createExprInt(0);
			}
		} else {
			expr = IrFactory.eINSTANCE.createExprVar(source);
		}

		InstAssign assign = IrFactory.eINSTANCE.createInstAssign(target, expr);
		targetBlock.add(assign);

		return DONE;
	}

	@Override
	public Void caseProcedure(Procedure procedure) {
		localsToRemove = new ArrayList<Var>();

		super.caseProcedure(procedure);

		for (Var local : localsToRemove) {
			procedure.getLocals().remove(local);
		}

		return DONE;
	}

}
