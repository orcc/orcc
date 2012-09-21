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

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import net.sf.orcc.OrccRuntimeException;
import net.sf.orcc.backends.ir.BlockFor;
import net.sf.orcc.backends.ir.IrSpecificFactory;
import net.sf.orcc.df.Actor;
import net.sf.orcc.df.util.DfVisitor;
import net.sf.orcc.ir.Block;
import net.sf.orcc.ir.BlockBasic;
import net.sf.orcc.ir.BlockIf;
import net.sf.orcc.ir.BlockWhile;
import net.sf.orcc.ir.CfgNode;
import net.sf.orcc.ir.Def;
import net.sf.orcc.ir.ExprVar;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.InstAssign;
import net.sf.orcc.ir.Instruction;
import net.sf.orcc.ir.Var;
import net.sf.orcc.ir.transform.ControlFlowAnalyzer;
import net.sf.orcc.ir.util.AbstractIrVisitor;
import net.sf.orcc.util.Attribute;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.EcoreUtil.Copier;

/**
 * Replace BlockWhile by BlockFor when it is possible. Cfg must be built before
 * any call to this transformation. To do that, please run the
 * {@link ControlFlowAnalyzer} before this one.
 * 
 * @author Jerome Gorin
 * @author Antoine Lorence
 * @see net.sf.orcc.ir.transform.ControlFlowAnalyzer
 */
public class BlockForAdder extends DfVisitor<Void> {

	public BlockForAdder() {
		irVisitor = new Builder(false);
	}

	public BlockForAdder(boolean fullReplacement) {
		irVisitor = new Builder(fullReplacement);
	}

	/**
	 * Build the Cfg for procedure containing specific 1 or more "for" blocks.
	 * 
	 * @author Antoine Lorence
	 * 
	 */
	public class BlockForCfg extends ControlFlowAnalyzer {

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * net.sf.orcc.ir.util.IrSwitch#defaultCase(org.eclipse.emf.ecore.EObject
		 * )
		 */
		@Override
		public CfgNode defaultCase(EObject object) {
			if (object instanceof BlockFor) {
				return caseBlockFor((BlockFor) object);
			}
			return super.defaultCase(object);
		}

		public CfgNode caseBlockFor(BlockFor block) {
			CfgNode join = addNode(block.getJoinBlock());
			cfg.getVertices().add(join);

			if (last != null) {
				addEdge(join);
			}

			CfgNode cfgNode = addNode(block);
			addEdge(cfgNode);

			last = join;
			flag = true;
			last = doSwitch(block.getBlocks());

			// reset flag (in case there are no nodes in "then" branch)
			flag = false;
			addEdge(join);
			last = cfgNode;

			return cfgNode;
		}
	}

	/**
	 * Build a list of all variables used in an Expression.
	 * 
	 */
	private class VarGetter extends AbstractIrVisitor<Void> {

		private List<Var> vars;
		private Expression expr;

		public VarGetter(Expression expr) {
			vars = new ArrayList<Var>();
			this.expr = expr;
		}

		@Override
		public Void caseExprVar(ExprVar expr) {
			Var var = expr.getUse().getVariable();
			if (!vars.contains(var)) {
				vars.add(var);
			}
			return null;
		}

		public List<Var> get() {
			doSwitch(expr);
			return vars;
		}

	}

	/**
	 * Main visitor. Effectively replace while loops by for loops
	 * 
	 */
	private class Builder extends AbstractIrVisitor<Void> {

		boolean fullReplacement = false;

		public Builder(boolean fullReplacement) {
			this.fullReplacement = fullReplacement;
		}

		@Override
		public Void caseBlockWhile(BlockWhile blockWhile) {

			// Ensure that inner while blocks are replaced by for blocks before
			// executing this
			super.caseBlockWhile(blockWhile);

			// Get the list of variables used in while condition
			Expression condition = blockWhile.getCondition();
			List<Var> conditionVars = new VarGetter(condition).get();

			// Get the last block of while's body
			EList<Block> blocks = blockWhile.getBlocks();
			if (blocks.isEmpty()) {
				// Don't treat empty blocks
				return null;
			}
			Block lastBlock = blocks.get(blocks.size() - 1);

			// Get the block immediately preceding while loop
			
			CfgNode joinNode = blockWhile.getJoinBlock().getCfgNode();
			if(joinNode == null) {
				throw new OrccRuntimeException(
						"Control Flow Graph must be built. Please apply the ControlFlowAnalyzer before BlockForAdder (actor : "
								+ actor.getName() + ").");
			}
			Block prevBlock = ((CfgNode) joinNode.getPredecessors().get(0))
					.getNode();

			List<Instruction> stepInstrList = new ArrayList<Instruction>();
			List<Instruction> initInstrList = new ArrayList<Instruction>();

			for (Var conditionVar : conditionVars) {
				// Get the last assignment on each variable contained in the
				// condition
				Instruction initInstr = getLastAssign(conditionVar, prevBlock);
				if (initInstr != null) {
					initInstrList.add(initInstr);
				}

				Instruction stepInstr = getLastAssign(conditionVar, lastBlock);
				if (stepInstr != null) {
					stepInstrList.add(stepInstr);
				}
			}

			// If fullReplacment == true, step or init can be
			// empty but not both (for loop is created with blank fields)
			// If fullReplacment == false, no empty field is allowed.
			if (fullReplacement && stepInstrList.isEmpty()
					&& initInstrList.isEmpty()) {
				return null;
			} else if (stepInstrList.isEmpty() || initInstrList.isEmpty()) {
				return null;
			}

			// Create block for
			BlockFor blockFor = IrSpecificFactory.eINSTANCE.createBlockFor();
			blockFor.setCondition(blockWhile.getCondition());
			blockFor.setLineNumber(blockWhile.getLineNumber());
			blockFor.setJoinBlock(blockWhile.getJoinBlock());
			blockFor.getBlocks().addAll(blockWhile.getBlocks());

			// Add init and step instructions, after removing them from their
			// original location
			for (Instruction instr : initInstrList) {
				EcoreUtil.remove(instr);
			}
			blockFor.getInit().addAll(initInstrList);
			for (Instruction instr : stepInstrList) {
				EcoreUtil.remove(instr);
			}
			blockFor.getStep().addAll(stepInstrList);

			// Copy attributes
			Copier copier = new EcoreUtil.Copier();
			for (Attribute attribute : blockWhile.getAttributes()) {
				blockFor.getAttributes()
						.add((Attribute) copier.copy(attribute));
			}

			// Replace node
			EcoreUtil.replace(blockWhile, blockFor);

			return null;
		}

		/**
		 * Get the last <code>InstAssign</code> from <i>block</i> which targets
		 * <i>var</i>.
		 * 
		 * @param var
		 * @param block
		 * @return the instruction
		 */
		private Instruction getLastAssign(Var var, Block block) {
			EList<Def> defs = var.getDefs();

			// Get the last block's BasicBlock
			BlockBasic lastBlockBasic = null;
			if (block.isBlockIf()) {
				lastBlockBasic = ((BlockIf) block).getJoinBlock();
			} else if (block.isBlockWhile()) {
				lastBlockBasic = ((BlockWhile) block).getJoinBlock();
			} else if (block.isBlockBasic()) {
				lastBlockBasic = (BlockBasic) block;
			} else {
				lastBlockBasic = ((BlockFor) block).getJoinBlock();
			}

			// Return in case of an empty node
			EList<Instruction> instructions = lastBlockBasic.getInstructions();
			if (instructions.isEmpty()) {
				return null;
			}

			// Search for the last InstAssign concerning var (def or use)
			ListIterator<Instruction> it = instructions
					.listIterator(instructions.size());
			while (it.hasPrevious()) {
				Instruction candidateInstr = it.previous();

				if (candidateInstr.isInstAssign()) {
					if (defs.contains(((InstAssign) candidateInstr).getTarget())) {
						return candidateInstr;
					} else if (new VarGetter(
							((InstAssign) candidateInstr).getValue()).get()
							.contains(var)) {
						return null;
					}
				}
			}

			// No corresponding Instruction found, don't return anything
			return null;
		}
	}

	@Override
	public Void caseActor(Actor actor) {

		// Transform actor, try to replace WhileBlock by ForBlock
		super.caseActor(actor);

		// Rebuild CFG with for loops
		new DfVisitor<CfgNode>(new BlockForCfg()).doSwitch(actor);

		return null;
	}

}
