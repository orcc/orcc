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
package net.sf.orcc.backends.transformations;

import java.util.ArrayList;
import java.util.List;

import net.sf.orcc.backends.ir.IrSpecificFactory;
import net.sf.orcc.backends.ir.NodeFor;
import net.sf.orcc.ir.Def;
import net.sf.orcc.ir.ExprVar;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.InstAssign;
import net.sf.orcc.ir.Instruction;
import net.sf.orcc.ir.Node;
import net.sf.orcc.ir.NodeBlock;
import net.sf.orcc.ir.NodeIf;
import net.sf.orcc.ir.NodeWhile;
import net.sf.orcc.ir.Var;
import net.sf.orcc.ir.util.AbstractActorVisitor;
import net.sf.orcc.util.EcoreHelper;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.util.EcoreUtil;

/**
 * Replace NodeWhile into NodeFor.
 * 
 * CFG must be build before any call to this transformation.
 * 
 * @author Jerome Gorin
 */
public class NodeForAdder extends AbstractActorVisitor<Object> {

	private class VarGetter extends AbstractActorVisitor<Object> {

		private List<Var> vars;

		public VarGetter(Expression expr) {
			vars = new ArrayList<Var>();
			doSwitch(expr);
		}

		@Override
		public Object caseExprVar(ExprVar expr) {
			vars.add(expr.getUse().getVariable());
			return null;
		}

		public List<Var> get() {
			return vars;
		}

	}

	@Override
	public Object caseNodeWhile(NodeWhile nodeWhile) {

		super.caseNodeWhile(nodeWhile);

		// Get properties of the while node
		EList<Node> nodes = nodeWhile.getNodes();

		if (nodes.isEmpty()) {
			// Don't treat empty nodes
			return null;
		}

		Expression condition = nodeWhile.getCondition();
		Node endNode = nodes.get(nodes.size() - 1);
		Node previousNode = nodeWhile.getJoinNode().getPredecessors().get(0);

		List<Var> conditionVars = new VarGetter(condition).get();
		List<Instruction> loopCnts = new ArrayList<Instruction>();
		List<Instruction> initCnts = new ArrayList<Instruction>();

		for (Var conditionVar : conditionVars) {
			// Get assignements on the variables contained in the condition
			Instruction loopCnt = getLastAssign(conditionVar, endNode);
			Instruction initCnt = getLastAssign(conditionVar, previousNode);

			if (loopCnt != null) {
				loopCnts.add(loopCnt);
			}

			if (initCnt != null) {
				initCnts.add(initCnt);
			}
		}

		// No loop counter founds, no for node to create
		if (loopCnts.isEmpty()&& initCnts.isEmpty()) {
			return null;
		}

		// Create node for
		NodeFor nodeFor = IrSpecificFactory.eINSTANCE.createNodeFor();

		nodeFor.setCondition(nodeWhile.getCondition());
		nodeFor.setLineNumber(nodeWhile.getLineNumber());
		nodeFor.setJoinNode(nodeWhile.getJoinNode());
		nodeFor.getNodes().addAll(nodeWhile.getNodes());

		// Add loop counters and inits
		nodeFor.getLoopCounter().addAll(loopCnts);
		nodeFor.getInit().addAll(initCnts);

		// Replace node
		EcoreUtil.replace(nodeWhile, nodeFor);

		return null;
	}

	private Instruction getLastAssign(Var var, Node lastNode) {
		EList<Def> defs = var.getDefs();

		// Check if one var defs is located in the last node
		NodeBlock lastBlockNode = null;

		if (lastNode.isNodeIf()) {
			lastBlockNode = ((NodeIf) lastNode).getJoinNode();
		} else if (lastNode.isNodeWhile()) {
			lastBlockNode = ((NodeWhile) lastNode).getJoinNode();
		} else if (lastNode.isNodeBlock()) {
			lastBlockNode = (NodeBlock) lastNode;
		} else {
			lastBlockNode = ((NodeFor) lastNode).getJoinNode();
		}

		// Return in case of an empty node
		EList<Instruction> instructions = lastBlockNode.getInstructions();
		if (instructions.isEmpty()){
			return null;
		}

		// Look for the last assignation
		InstAssign lastAssign = null;
		for (Def def : defs) {
			InstAssign instAssign = EcoreHelper.getContainerOfType(def,
					InstAssign.class);
			
			Instruction lastInstruction = instructions.get(instructions.size()-1);
			
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

		// Remove assign instructions and return the corresponding instruction
		EcoreUtil.remove(lastAssign);

		return lastAssign;
	}

}
