/*
 * Copyright (c) 2011, IETR/INSA of Rennes
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

import java.util.ArrayList;
import java.util.List;

import net.sf.orcc.OrccRuntimeException;
import net.sf.orcc.ir.Block;
import net.sf.orcc.ir.BlockBasic;
import net.sf.orcc.ir.BlockIf;
import net.sf.orcc.ir.BlockWhile;
import net.sf.orcc.ir.ExprUnary;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.Instruction;
import net.sf.orcc.ir.IrFactory;
import net.sf.orcc.ir.OpUnary;
import net.sf.orcc.ir.Predicate;
import net.sf.orcc.ir.Procedure;
import net.sf.orcc.ir.util.AbstractIrVisitor;
import net.sf.orcc.ir.util.IrUtil;
import net.sf.orcc.util.util.EcoreHelper;

import org.eclipse.emf.ecore.util.EcoreUtil;

/**
 * This class performs the inverse of if-conversion on the given procedure.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class IfDeconverter extends AbstractIrVisitor<Object> {

	private Predicate currentPredicate;

	private List<BlockIf> blockIfList;

	@Override
	public Object caseBlockBasic(BlockBasic block) {
		Procedure procedure = EcoreHelper.getContainerOfType(block,
				Procedure.class);
		BlockBasic targetBlock = null;

		List<Instruction> instructions = block.getInstructions();
		while (!instructions.isEmpty()) {
			Instruction inst = instructions.get(0);

			Predicate predicate = inst.getPredicate();
			if (!predicate.isSameAs(currentPredicate)) {
				// deletes the current predicate
				if (currentPredicate != null) {
					IrUtil.delete(currentPredicate);
				}

				// updates the target block
				targetBlock = updateTargetBlock(procedure, predicate);
			}

			// deletes the predicate of the instruction
			IrUtil.delete(predicate);

			// moves the instruction
			targetBlock.getInstructions().add(inst);
		}

		// deletes the current predicate
		if (currentPredicate != null) {
			IrUtil.delete(currentPredicate);
		}

		// remove this block
		EcoreUtil.remove(block);

		return null;
	}

	@Override
	public Object caseBlockWhile(BlockWhile blockWhile) {
		throw new OrccRuntimeException("unsupported BlockWhile");
	}

	@Override
	public Object caseProcedure(Procedure procedure) {
		// do not perform if-deconversion if procedure contains whiles
		if (EcoreHelper.getObjects(procedure, BlockWhile.class).iterator()
				.hasNext()) {
			return null;
		}

		blockIfList = new ArrayList<BlockIf>();

		// initialized to "null" so that the first empty predicate will create
		// an unconditional block
		currentPredicate = null;

		List<Block> blocks = procedure.getBlocks();
		if (!blocks.isEmpty()) {
			doSwitch(blocks.get(0));
		}

		return null;
	}

	/**
	 * Returns the list of blocks that matches the given condition (if any).
	 * 
	 * @param parentBlocks
	 *            list of blocks to which the "if" we're looking for belongs
	 * @param condition
	 *            a condition
	 * @return the list of blocks that matches the given condition, or
	 *         <code>null</code>
	 */
	private List<Block> findBlocks(List<Block> parentBlocks, Expression condition) {
		for (BlockIf blockIf : blockIfList) {
			List<Block> blocks = EcoreHelper.getContainingList(blockIf);
			if (EcoreUtil.equals(condition, blockIf.getCondition())
					&& parentBlocks == blocks) {
				return blockIf.getThenBlocks();
			} else {
				if (condition.isExprUnary()) {
					ExprUnary unary = (ExprUnary) condition;
					Expression expr = unary.getExpr();

					if (unary.getOp() == OpUnary.LOGIC_NOT
							&& EcoreUtil.equals(expr, blockIf.getCondition())
							&& parentBlocks == blocks) {
						return blockIf.getElseBlocks();
					}
				}
			}
		}

		return null;
	}

	/**
	 * Updates targetBlock according to the given predicate.
	 * 
	 * @param procedure
	 *            the current procedure
	 * @param predicate
	 *            a predicate
	 */
	private BlockBasic updateTargetBlock(Procedure procedure,
			Predicate predicate) {
		currentPredicate = IrFactory.eINSTANCE.createPredicate();
		List<Block> parentBlocks = procedure.getBlocks();

		if (predicate.isEmpty()) {
			// unconditioned predicate => forgets all ifs
			blockIfList.clear();

			// creates a new block
			BlockBasic block = IrFactory.eINSTANCE.createBlockBasic();
			procedure.getBlocks().add(block);
			return block;
		} else {
			for (Expression condition : predicate.getExpressions()) {
				List<Block> blocks = findBlocks(parentBlocks, condition);
				if (blocks == null) {
					// create a new if
					BlockIf blockIf = IrFactory.eINSTANCE.createBlockIf();
					blockIf.setCondition(IrUtil.copy(condition));
					blockIf.setJoinBlock(IrFactory.eINSTANCE.createBlockBasic());
					blockIfList.add(blockIf);
					parentBlocks.add(blockIf);

					// use the blocks of the "then" branch
					parentBlocks = blockIf.getThenBlocks();
				} else {
					parentBlocks = blocks;
				}

				currentPredicate.add(IrUtil.copy(condition));
			}

			return IrUtil.getLast(parentBlocks);
		}
	}

}
