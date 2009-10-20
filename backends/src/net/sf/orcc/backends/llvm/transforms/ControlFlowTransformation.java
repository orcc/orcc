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
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import net.sf.orcc.backends.llvm.nodes.AbstractLLVMNodeVisitor;
import net.sf.orcc.backends.llvm.nodes.BrLabelNode;
import net.sf.orcc.backends.llvm.nodes.BrNode;
import net.sf.orcc.backends.llvm.nodes.LabelNode;
import net.sf.orcc.backends.llvm.nodes.PhiNode;
import net.sf.orcc.backends.llvm.nodes.SelectNode;
import net.sf.orcc.common.LocalUse;
import net.sf.orcc.common.LocalVariable;
import net.sf.orcc.common.Location;
import net.sf.orcc.ir.actor.Action;
import net.sf.orcc.ir.actor.Actor;
import net.sf.orcc.ir.actor.Procedure;
import net.sf.orcc.ir.expr.BooleanExpr;
import net.sf.orcc.ir.expr.IExpr;
import net.sf.orcc.ir.expr.TypeExpr;
import net.sf.orcc.ir.nodes.AbstractNode;
import net.sf.orcc.ir.nodes.IfNode;
import net.sf.orcc.ir.nodes.JoinNode;
import net.sf.orcc.ir.nodes.PhiAssignment;
import net.sf.orcc.ir.nodes.ReturnNode;
import net.sf.orcc.ir.nodes.WhileNode;
import net.sf.orcc.ir.transforms.IActorTransformation;
import net.sf.orcc.ir.type.IType;
import net.sf.orcc.ir.type.IntType;
import net.sf.orcc.ir.type.VoidType;

/**
 * Adds control flow.
 * 
 * @author Jérôme GORIN
 * 
 */
public class ControlFlowTransformation extends AbstractLLVMNodeVisitor
		implements IActorTransformation {

	private int BrCounter;

	ListIterator<AbstractNode> it;

	private LabelNode labelNode;

	List<PhiNode> tmpPhiNodes;

	private BrNode brNodeCreate(IfNode node, Object... args) {

		// Get IfNode attributes
		int id = node.getId();
		Location location = node.getLocation();
		IExpr condition = node.getCondition();
		List<AbstractNode> conditionNodes = new ArrayList<AbstractNode>();
		List<AbstractNode> thenNodes = node.getThenNodes();
		List<AbstractNode> elseNodes = node.getElseNodes();

		LabelNode thenLabelNode = null;
		LabelNode elseLabelNode = null;
		LabelNode endThenLabelNode = null;
		LabelNode endElseLabelNode = null;
		LabelNode endLabelNode = null;
		List<PhiNode> phiNodes = null;

		// Create entry LabelNode
		LabelNode entryLabelNode = labelNode;

		// Set labelNode inside thenNodes
		if (!thenNodes.isEmpty()) {
			// Create thenLabelNode
			thenLabelNode = new LabelNode(node.getId(), node.getLocation(),
					"bb" + Integer.toString(BrCounter++));

			// Store current iterator and branch label
			ListIterator<AbstractNode> itTmp = it;
			labelNode = thenLabelNode;

			// Continue transformation on thenNode
			visitNodes(thenNodes, node);

			// Restore current iterator
			it = itTmp;
		}

		endThenLabelNode = labelNode;

		// Set labelNode inside elseNodes
		if (!(elseNodes.isEmpty())) {

			// Create elseLabelNode
			elseLabelNode = new LabelNode(node.getId(), node.getLocation(),
					"bb" + Integer.toString(BrCounter++));
			labelNode = elseLabelNode;

			// Store current iterator and branch label
			ListIterator<AbstractNode> itTmp = it;

			// Continue transformation on elseNode
			visitNodes(elseNodes, node);

			// Restore current iterator
			it = itTmp;
		}

		endElseLabelNode = labelNode;

		// Create elseLabelNode
		endLabelNode = new LabelNode(node.getId(), node.getLocation(), "bb"
				+ Integer.toString(BrCounter++));
		labelNode = endLabelNode;

		if (!thenNodes.isEmpty()) {
			thenNodes.add(new BrLabelNode(0, new Location(), endLabelNode,
					endThenLabelNode));
		} else {
			thenLabelNode = endLabelNode;
			thenLabelNode.addPrecedence(entryLabelNode);
		}

		if (!elseNodes.isEmpty()) {
			elseNodes.add(new BrLabelNode(0, new Location(), endLabelNode,
					endElseLabelNode));
		} else {
			elseLabelNode = endLabelNode;
			elseLabelNode.addPrecedence(entryLabelNode);
		}

		return new BrNode(id, location, condition, conditionNodes, thenNodes,
				elseNodes, phiNodes, entryLabelNode, thenLabelNode,
				elseLabelNode, endLabelNode);
	}

	private BrNode brNodeCreate(WhileNode node, Object... args) {

		// Get whileNode information
		int id = node.getId();
		Location location = node.getLocation();
		IExpr condition = node.getCondition();
		List<AbstractNode> conditionNodes = new ArrayList<AbstractNode>();
		List<AbstractNode> thenNodes = node.getNodes();
		List<AbstractNode> elseNodes = new ArrayList<AbstractNode>();
		LabelNode entryLabelNode = labelNode;
		LabelNode thenLabelNode = null;
		List<PhiNode> phiNodes = new ArrayList<PhiNode>();

		thenLabelNode = new LabelNode(node.getId(), node.getLocation(), "bb"
				+ Integer.toString(BrCounter++));
		labelNode = thenLabelNode;

		// Store current iterator and branch label
		ListIterator<AbstractNode> itTmp = it;

		// Continue transformation on thenNode
		visitNodes(thenNodes, node);

		// Restore current iterator
		it = itTmp;

		// Add branch to first conditions
		thenNodes.add(new BrLabelNode(0, new Location(), entryLabelNode,
				labelNode));

		// Set endNode
		LabelNode elseLabelNode = new LabelNode(node.getId(), node
				.getLocation(), "bb" + Integer.toString(BrCounter++));
		elseLabelNode.addPrecedence(entryLabelNode);
		LabelNode endLabelNode = elseLabelNode;
		labelNode = endLabelNode;

		return new BrNode(id, location, condition, conditionNodes, thenNodes,
				elseNodes, phiNodes, entryLabelNode, thenLabelNode,
				elseLabelNode, endLabelNode);
	}

	// Remove ifNode in case of a constant condition
	private List<AbstractNode> clearIfNode(IfNode node) {
		BooleanExpr condition = (BooleanExpr) node.getCondition();
		List<AbstractNode> nodes;
		List<PhiAssignment> phis = node.getJoinNode().getPhis();
		boolean value = condition.getValue();

		if (value == true) {
			nodes = node.getThenNodes();
		} else {
			nodes = node.getElseNodes();
		}

		for (PhiAssignment phi : phis) {
			LocalVariable varDef = phi.getVarDef();
			List<LocalUse> localUses = phi.getVars();
			LocalVariable phiVar;
			if (value == true) {
				phiVar = localUses.get(0).getLocalVariable();

			} else {
				phiVar = localUses.get(1).getLocalVariable();
			}

			varDef.duplicate(phiVar);
		}

		return nodes;
	}

	// Return a list of PhiNode supported by the LLVM backend from JoinNode
	private List<PhiNode> phiNodeCreate(JoinNode node, LabelNode labelTrueNode,
			LabelNode labelFalseNode) {
		List<PhiAssignment> phis = node.getPhis();
		List<PhiNode> PhiNodes = new ArrayList<PhiNode>();

		for (PhiAssignment phi : phis) {
			Map<LabelNode, LocalVariable> assignements = new HashMap<LabelNode, LocalVariable>();
			LocalVariable varDef = phi.getVarDef();
			IType varType = varDef.getType();
			List<LocalUse> vars = phi.getVars();

			LocalVariable trueVar = vars.get(0).getLocalVariable();
			LocalVariable falseVar = vars.get(1).getLocalVariable();

			// Force varDef's type to phiNode type to prevent cast problem
			if (varType.getType() == IType.INT) {
				IntType intType = (IntType) varType;
				if (trueVar.getType().getType() == IType.INT) {
					((IntType) trueVar.getType()).setSize(intType.getSize());
				} else {
					trueVar.setType(varType);
				}

				if (falseVar.getType().getType() == IType.INT) {
					((IntType) falseVar.getType()).setSize(intType.getSize());
				} else {
					falseVar.setType(varType);
				}
			}

			assignements.put(labelTrueNode, trueVar);
			assignements.put(labelFalseNode, falseVar);

			PhiNodes.add(new PhiNode(0, new Location(), varDef, varDef
					.getType(), assignements));
		}

		return PhiNodes;

	}

	// Return a selectNode
	private SelectNode selectNodeCreate(IfNode node) {
		int id = node.getId();
		Location location = node.getLocation();
		IExpr condition = node.getCondition();
		JoinNode joinNode = node.getJoinNode();
		List<PhiAssignment> phis = joinNode.getPhis();

		return new SelectNode(id, location, condition, phis);
	}

	@Override
	public void transform(Actor actor) {
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

	@Override
	public void visit(IfNode node, Object... args) {
		List<AbstractNode> thenNodes = node.getThenNodes();
		List<AbstractNode> elseNodes = node.getElseNodes();
		JoinNode joinNode = node.getJoinNode();

		// Select the appropriate llvm Node to describe the current IfNode
		if ((thenNodes.isEmpty()) && (elseNodes.isEmpty())) {

			SelectNode selectNode = selectNodeCreate(node);
			it.remove();
			it.add(selectNode);
		} else if (node.getCondition().getType() == IExpr.BOOLEAN) {
			List<AbstractNode> brNodes = clearIfNode(node);
			it.remove();
			for (AbstractNode brNode : brNodes) {
				it.add(brNode);
			}
		} else {

			BrNode brNode = brNodeCreate(node);
			List<LabelNode> label = brNode.getLabelEndNode().getPrecedence();
			List<PhiNode> phiNodes = phiNodeCreate(joinNode, label.get(0),
					label.get(1));
			brNode.setPhiNodes(phiNodes);
			it.remove();
			it.add(brNode);
		}

	}

	@Override
	public void visit(WhileNode node, Object... args) {

		// Store current label node
		LabelNode entryLabelNode = labelNode;

		it.remove();

		LabelNode conditionLabelNode = new LabelNode(node.getId(), node
				.getLocation(), "bb" + Integer.toString(BrCounter++));
		it.add(new BrLabelNode(0, new Location(), conditionLabelNode,
				entryLabelNode));
		it.add(conditionLabelNode);
		labelNode = conditionLabelNode;

		BrNode brNode = brNodeCreate(node);
		List<LabelNode> label = brNode.getLabelEntryNode().getPrecedence();
		List<PhiNode> phiNodes = phiNodeCreate(node.getJoinNode(),
				label.get(0), label.get(1));

		// Move PhiNodes in current node
		for (PhiNode phiNode : phiNodes) {
			it.add(phiNode);
		}

		phiNodes.clear();

		// Add BrNode into the current function
		it.add(brNode);

	}

	private void visitNodes(List<AbstractNode> nodes, Object... args) {
		it = nodes.listIterator();

		while (it.hasNext()) {
			it.next().accept(this, args);
		}
	}

	private void visitProc(Procedure proc) {
		List<AbstractNode> nodes = proc.getNodes();
		BrCounter = 0;
		tmpPhiNodes = null;
		labelNode = new LabelNode(0, null, "entry");

		visitNodes(nodes);

		// Add void return
		if (proc.getReturnType().getType() == IType.VOID) {
			TypeExpr expr = new TypeExpr(null, new VoidType());
			nodes.add(new ReturnNode(0, null, expr));
		}
	}
}