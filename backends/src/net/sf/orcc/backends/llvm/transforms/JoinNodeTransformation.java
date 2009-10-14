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
package net.sf.orcc.backends.llvm.transforms;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Map.Entry;

import net.sf.orcc.backends.llvm.nodes.AbstractLLVMNodeVisitor;
import net.sf.orcc.backends.llvm.nodes.BrLabelNode;
import net.sf.orcc.backends.llvm.nodes.BrNode;
import net.sf.orcc.backends.llvm.nodes.LabelNode;
import net.sf.orcc.backends.llvm.nodes.PhiNode;
import net.sf.orcc.ir.VarDef;
import net.sf.orcc.ir.actor.Action;
import net.sf.orcc.ir.actor.Actor;
import net.sf.orcc.ir.actor.Procedure;
import net.sf.orcc.ir.nodes.AbstractNode;
import net.sf.orcc.ir.type.IType;

/**
 * Change every load on array into getElementPtrNode.
 * 
 * @author Jérôme GORIN
 * 
 */
public class JoinNodeTransformation extends AbstractLLVMNodeVisitor {

	List<AbstractNode> abstractNodes;

	public JoinNodeTransformation(Actor actor) {

		for (Procedure proc : actor.getProcs()) {
			visitProc(proc);
		}

		for (Action action : actor.getActions()) {
			visitProc(action.getBody());
			visitProc(action.getScheduler());
		}

		for (Action action : actor.getInitializes()) {
			visitProc(action.getBody());
			visitProc(action.getScheduler());
		}
	}

	private void mergeBrNode(BrNode node) {
		LabelNode labelNode = node.getLabelEndNode();

		// Successor of a phiNode Branch can only be of type BrLabelNode
		BrLabelNode tmpLabelNode = (BrLabelNode) labelNode.getSuccessor();
		abstractNodes.remove(tmpLabelNode);

		List<LabelNode> precedences = labelNode.getPrecedence();
		for (LabelNode precedence : precedences) {
			AbstractNode successorNode = precedence.getSuccessor();

			if (successorNode instanceof BrLabelNode) {
				BrLabelNode brLabelNode = (BrLabelNode) successorNode;
				brLabelNode.setLabelNode(tmpLabelNode.getLabelNode());
			} else if (successorNode instanceof BrNode) {
				BrNode brNode = (BrNode) successorNode;
				if (brNode.getLabelTrueNode().equals(labelNode)) {
					brNode.setLabelTrueNode(tmpLabelNode.getLabelNode());
				}
				if (brNode.getLabelTrueNode().equals(labelNode)) {
					brNode.setLabelFalseNode(tmpLabelNode.getLabelNode());
				}
			}
		}
		node.setLabelEndNode(null);
	}

	private void mergePhiNode(PhiNode sourceNode,
			Map<LabelNode, VarDef> assignements, IType phiNodeType) {
		// Match and merge a couple vardef/brLabel into imbricated brNode
		LabelNode labelNode = null;
		VarDef varDef = sourceNode.getVarDef();

		for (Entry<LabelNode, VarDef> targetAssignement : assignements
				.entrySet()) {

			if (targetAssignement.getValue().equals(varDef)) {
				labelNode = targetAssignement.getKey();
			}
		}

		assignements.remove(labelNode);

		Map<LabelNode, VarDef> sourceAssignements = sourceNode
				.getAssignements();
		for (Entry<LabelNode, VarDef> sourceAssignement : sourceAssignements
				.entrySet()) {

			LabelNode labKey = sourceAssignement.getKey();
			VarDef varVal = sourceAssignement.getValue();
			varVal.setType(phiNodeType);

			assignements.put(labKey, varVal);
		}

		/*
		 * //Add undefined LabelNode branch into every targeted PhiNode for
		 * (Entry<LabelNode, VarDef> sourceAssignement :
		 * sourceNode.getAssignements().entrySet()){ for (PhiNode targetNode :
		 * targetNodes){ Map<LabelNode, VarDef> targetAssignements =
		 * targetNode.getAssignements(); if
		 * (!targetAssignements.containsKey(sourceAssignement.getKey())){ VarDef
		 * vardef = targetAssignements.get(mergeNode); if (vardef != null){
		 * targetAssignements.put(sourceAssignement.getKey(), vardef); } } } } }
		 */
	}

	@SuppressWarnings("unchecked")
	@Override
	public void visit(BrNode node, Object... args) {

		List<PhiNode> phiNodes;
		List<PhiNode> phiNodesSource = node.getPhiNodes();

		// Chech if Br Node are imbricated
		if (args.length != 1) {
			// if not
			phiNodes = new ArrayList<PhiNode>();
			phiNodes.addAll(node.getPhiNodes());
		} else {
			// Else check for joinNode that can be merged
			phiNodes = (List<PhiNode>) args[0];

			ListIterator<PhiNode> it = phiNodesSource.listIterator();
			while (it.hasNext()) {
				PhiNode phiNodeSource = it.next();
				VarDef varDef = phiNodeSource.getVarDef();

				for (PhiNode phiNode : phiNodes) {
					Map<LabelNode, VarDef> assignements = phiNode
							.getAssignements();
					if (assignements.containsValue(varDef)) {
						mergePhiNode(phiNodeSource, assignements, phiNode
								.getType());
						it.remove();
						if (phiNodesSource.size() == 0) {
							mergeBrNode(node);
						}
					}
				}
			}
		}

		List<AbstractNode> tmpNode = abstractNodes;
		visitNodes(node.getThenNodes(), phiNodes);
		visitNodes(node.getElseNodes(), phiNodes);
		abstractNodes = tmpNode;
	}

	private void visitNodes(List<AbstractNode> nodes, Object... args) {
		ListIterator<AbstractNode> it = nodes.listIterator();
		abstractNodes = nodes;
		while (it.hasNext()) {
			it.next().accept(this, args);
		}
	}

	private void visitProc(Procedure proc) {
		List<AbstractNode> nodes = proc.getNodes();

		visitNodes(nodes);
	}
}
