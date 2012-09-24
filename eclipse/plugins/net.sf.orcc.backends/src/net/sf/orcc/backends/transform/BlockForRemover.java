/*
 * Copyright (c) 2011, IRISA
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
 *   * Neither the name of the IRISA nor the names of its
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
package net.sf.orcc.backends.transform;

import net.sf.orcc.OrccRuntimeException;
import net.sf.orcc.backends.ir.BlockFor;
import net.sf.orcc.df.Actor;
import net.sf.orcc.df.util.DfVisitor;
import net.sf.orcc.ir.Block;
import net.sf.orcc.ir.BlockBasic;
import net.sf.orcc.ir.BlockWhile;
import net.sf.orcc.ir.CfgNode;
import net.sf.orcc.ir.IrFactory;
import net.sf.orcc.ir.transform.ControlFlowAnalyzer;
import net.sf.orcc.ir.util.AbstractIrVisitor;
import net.sf.orcc.ir.util.IrUtil;
import net.sf.orcc.util.Attribute;
import net.sf.orcc.util.util.EcoreHelper;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.EcoreUtil.Copier;

/**
 * Replace BlockWhile by BlockFor. This transformation is the inverse operation
 * of {@link BlockForAdder}. Cfg must be built before any call to this
 * transformation. To do that, please run the {@link BlockForAdder.BlockForCfg}
 * before this one.
 * 
 * @author Jerome Gorin
 * @author Antoine Lorence
 * @see BlockForAdder.BlockForCfg
 */
public class BlockForRemover extends DfVisitor<Void> {

	public BlockForRemover() {
		irVisitor = new Builder();
	}

	/**
	 * Main visitor. Effectively replace while loops by for loops
	 * 
	 */
	private class Builder extends AbstractIrVisitor<Void> {

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * net.sf.orcc.ir.util.IrSwitch#defaultCase(org.eclipse.emf.ecore.EObject
		 * )
		 */
		@Override
		public Void defaultCase(EObject object) {
			if (object instanceof BlockFor) {
				return replaceBlockFor((BlockFor) object);
			} else {
				return super.defaultCase(object);
			}
		}

		public Void replaceBlockFor(BlockFor blockFor) {

			// Ensure that inner while blocks are replaced by for blocks before
			// executing this
			doSwitch(blockFor.getJoinBlock());
			doSwitch(blockFor.getBlocks());


			// Get block basic imediatly preceding for loop
			CfgNode joinCfgNode = blockFor.getJoinBlock().getCfgNode();
			if (joinCfgNode == null) {
				throw new OrccRuntimeException(
						"Control Flow Graph must be built. Please apply the ControlFlowAnalyzer before BlockForAdder.");
			}
			Block prevBlock = ((CfgNode) joinCfgNode.getPredecessors().get(0))
					.getNode();
			if (!prevBlock.isBlockBasic()) {
				prevBlock = IrFactory.eINSTANCE.createBlockBasic();
				EcoreHelper.getContainingList(prevBlock).add(prevBlock);
			}

			// Create block while
			BlockWhile blockWhile = IrFactory.eINSTANCE.createBlockWhile();
			blockWhile.setCondition(blockFor.getCondition());
			blockWhile.setLineNumber(blockFor.getLineNumber());
			blockWhile.setJoinBlock(blockFor.getJoinBlock());
			blockWhile.getBlocks().addAll(blockFor.getBlocks());

			// Add init instructions to block immediately preceding for loop
			((BlockBasic) prevBlock).getInstructions().addAll(
					blockFor.getInit());

			// Add step instructions to the end of while blocks
			BlockBasic lastBlock = IrUtil.getLast(blockWhile.getBlocks());
			lastBlock.getInstructions().addAll(blockFor.getStep());

			// Copy attributes
			Copier copier = new EcoreUtil.Copier();
			for (Attribute attribute : blockFor.getAttributes()) {
				blockWhile.getAttributes().add(
						(Attribute) copier.copy(attribute));
			}

			// Replace node
			EcoreUtil.replace(blockFor, blockWhile);

			return null;
		}
	}

	@Override
	public Void caseActor(Actor actor) {
		// Transform actor, try to replace ForBlock by WhileBlock
		super.caseActor(actor);

		// Rebuild CFG with while loops
		new DfVisitor<CfgNode>(new ControlFlowAnalyzer()).doSwitch(actor);

		return null;
	}

}
