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
import net.sf.orcc.util.util.EcoreHelper;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;

/**
 * Replace BlockWhile by BlockFor when it is possible.
 * 
 * Cfg must be built before any call to this transformation.
 * 
 * @author Jerome Gorin
 * @author Antoine Lorence
 */
public class BlockForAdder extends DfVisitor<Void> {

	public BlockForAdder() {
		irVisitor = new Builder();
	}

	/**
	 * Build the Cfg for procedure containing specific 1 or more "for" blocks.
	 * 
	 * @author Antoine Lorence
	 * 
	 */
	private class BlockForCfg extends ControlFlowAnalyzer {

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * net.sf.orcc.ir.util.IrSwitch#defaultCase(org.eclipse.emf.ecore.EObject
		 * )
		 */
		@Override
		public CfgNode defaultCase(EObject object) {
			if( object instanceof BlockFor) {
				return caseBlockFor((BlockFor) object);
			}
			return super.defaultCase(object);
		}

		public CfgNode caseBlockFor(BlockFor block) {
			CfgNode join = addNode(block.getJoinNode());
			cfg.getVertices().add(join);

			if (last != null) {
				addEdge(join);
			}

			CfgNode cfgNode = addNode(block);
			addEdge(cfgNode);

			last = join;
			flag = true;
			last = doSwitch(block.getNodes());

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
	 * @author Antoine Lorence
	 * 
	 */
	private class VarGetter extends AbstractIrVisitor<Void> {

		private List<Var> vars;

		public VarGetter(Expression expr) {
			vars = new ArrayList<Var>();
			doSwitch(expr);
		}

		@Override
		public Void caseExprVar(ExprVar expr) {
			vars.add(expr.getUse().getVariable());
			return null;
		}

		public List<Var> get() {
			return vars;
		}

	}

	/**
	 * Class used for ?
	 * 
	 * @author Antoine Lorence
	 * 
	 */
	private class Builder extends AbstractIrVisitor<Void> {
		@Override
		public Void caseBlockWhile(BlockWhile blockWhile) {

			// Ensure that while body procedure has its while blocks replaced
			// before
			super.caseBlockWhile(blockWhile);

			// Get properties of the while block
			EList<Block> blocks = blockWhile.getBlocks();

			if (blocks.isEmpty()) {
				// Don't treat empty nodes
				return null;
			}

			Expression condition = blockWhile.getCondition();
			Block lastBlock = blocks.get(blocks.size() - 1);

			CfgNode previousCFGNode = (CfgNode) blockWhile.getJoinBlock()
					.getCfgNode().getPredecessors().get(0);
			Block previousNode = previousCFGNode.getNode();

			List<Var> conditionVars = new VarGetter(condition).get();
			List<Instruction> loopCnts = new ArrayList<Instruction>();
			List<Instruction> initCnts = new ArrayList<Instruction>();

			for (Var conditionVar : conditionVars) {
				// Get assignements on the variables contained in the condition
				Instruction loopCnt = getLastAssign(conditionVar, lastBlock);
				Instruction initCnt = getLastAssign(conditionVar, previousNode);

				if (loopCnt != null) {
					loopCnts.add(loopCnt);
				}

				if (initCnt != null) {
					initCnts.add(initCnt);
				}
			}

			// No loop counter founds, no for node to create
			if (loopCnts.isEmpty() && initCnts.isEmpty()) {
				return null;
			}

			// Create node for
			BlockFor blockFor = IrSpecificFactory.eINSTANCE.createBlockFor();

			blockFor.setCondition(blockWhile.getCondition());
			blockFor.setLineNumber(blockWhile.getLineNumber());
			blockFor.setJoinNode(blockWhile.getJoinBlock());
			blockFor.getNodes().addAll(blockWhile.getBlocks());

			// Add loop counters and inits
			// FIXME :
			if (loopCnts.size() > 0)
				blockFor.setStep(loopCnts.get(0));
			if (initCnts.size() > 0)
				blockFor.setInit(initCnts.get(0));

			// Copy attributes
			for (Attribute attribute : blockWhile.getAttributes()) {
				// TODO: copy attribute instead of linking it
				blockFor.setAttribute(attribute.getName(), attribute.getValue());
			}

			// Replace node
			EcoreUtil.replace(blockWhile, blockFor);

			return null;
		}

		private Instruction getLastAssign(Var var, Block lastNode) {
			EList<Def> defs = var.getDefs();

			// Check if one var defs is located in the last node
			BlockBasic lastBlockNode = null;

			if (lastNode.isBlockIf()) {
				lastBlockNode = ((BlockIf) lastNode).getJoinBlock();
			} else if (lastNode.isBlockWhile()) {
				lastBlockNode = ((BlockWhile) lastNode).getJoinBlock();
			} else if (lastNode.isBlockBasic()) {
				lastBlockNode = (BlockBasic) lastNode;
			} else {
				lastBlockNode = ((BlockFor) lastNode).getJoinNode();
			}

			// Return in case of an empty node
			EList<Instruction> instructions = lastBlockNode.getInstructions();
			if (instructions.isEmpty()) {
				return null;
			}

			// Look for the last assignation
			InstAssign lastAssign = null;
			for (Def def : defs) {
				InstAssign instAssign = EcoreHelper.getContainerOfType(def,
						InstAssign.class);

				Instruction lastInstruction = instructions.get(instructions
						.size() - 1);

				// Get last assignation
				if (lastInstruction.equals(instAssign)) {
					lastAssign = instAssign;
					break;
				}
			}

			if (lastAssign == null) {
				// No assignation found
				return null;
			}

			// Remove assign instructions and return the corresponding
			// instruction
			EcoreUtil.remove(lastAssign);

			return lastAssign;
		}
	}

	@Override
	public Void caseActor(Actor actor) {
		// First build Cfg for existing code
		new DfVisitor<CfgNode>(new ControlFlowAnalyzer()).doSwitch(actor);

		// Transform actor, try to replace WhileBlock by ForBlock
		super.caseActor(actor);

		// Rebuild CFG with for node
		new DfVisitor<CfgNode>(new BlockForCfg()).doSwitch(actor);

		return null;
	}

}
