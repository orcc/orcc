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

import net.sf.orcc.backends.llvm.nodes.BrLabelNode;
import net.sf.orcc.backends.llvm.nodes.BrNode;
import net.sf.orcc.backends.llvm.nodes.LabelNode;
import net.sf.orcc.backends.llvm.nodes.PhiNode;
import net.sf.orcc.backends.llvm.nodes.SelectNode;
import net.sf.orcc.ir.Location;
import net.sf.orcc.ir.VarDef;
import net.sf.orcc.ir.actor.Action;
import net.sf.orcc.ir.actor.Actor;
import net.sf.orcc.ir.actor.Procedure;
import net.sf.orcc.ir.actor.VarUse;
import net.sf.orcc.ir.expr.BooleanExpr;
import net.sf.orcc.ir.expr.IExpr;
import net.sf.orcc.ir.expr.TypeExpr;
import net.sf.orcc.ir.nodes.AbstractNode;
import net.sf.orcc.ir.nodes.AbstractNodeVisitor;
import net.sf.orcc.ir.nodes.IfNode;
import net.sf.orcc.ir.nodes.JoinNode;
import net.sf.orcc.ir.nodes.PhiAssignment;
import net.sf.orcc.ir.nodes.ReturnNode;
import net.sf.orcc.ir.nodes.WhileNode;
import net.sf.orcc.ir.type.AbstractType;
import net.sf.orcc.ir.type.IntType;
import net.sf.orcc.ir.type.VoidType;

/**
 * Adds control flow.
 * 
 * @author Jérôme GORIN
 * 
 */
public class ControlFlowTransformation extends AbstractNodeVisitor {

	private int BrCounter;
	private LabelNode labelNode;
	private LabelNode entryNode;
	List<PhiNode> tmpPhiNodes;
	
	ListIterator<AbstractNode> it;

	public ControlFlowTransformation(Actor actor) {

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

	private BrNode brNodeCreate(IfNode node, Object... args) {

		// Get IfNode attributes
		int id = node.getId();
		Location location = node.getLocation();
		IExpr condition = node.getCondition();
		List<AbstractNode> conditionNodes = new ArrayList<AbstractNode>();
		List<AbstractNode> thenNodes = node.getThenNodes();
		List<AbstractNode> elseNodes = node.getElseNodes();
		JoinNode joinNode = node.getJoinNode();
		
		LabelNode thenLabelNode = null;
		LabelNode elseLabelNode= null;
		LabelNode endLabelNode= null;
		List<PhiNode> phiNodes= null;

		// Create LabelNode
		LabelNode entryLabelNode = labelNode;

		if (!thenNodes.isEmpty()) {
			// Create thenLabelNode
			thenLabelNode = new LabelNode(node.getId(), node
					.getLocation(), "bb" + Integer.toString(BrCounter++));
			
			
			//Store current iterator and branch label
			ListIterator<AbstractNode> itTmp = it;
			labelNode = thenLabelNode;
			
			// Continue transformation on thenNode
			visitNodes(thenNodes, node);
			
			//Restore current iterator
			it = itTmp;
		}
		
		if (!(elseNodes.isEmpty())) {
			
			// Create elseLabelNode
			elseLabelNode = new LabelNode(node.getId(), node
					.getLocation(), "bb" + Integer.toString(BrCounter++));
			
			//Store current iterator and branch label
			ListIterator<AbstractNode> itTmp = it;
			labelNode = elseLabelNode;
			
			// Continue transformation on elseNode
			visitNodes(elseNodes, node);
			
			//Restore current iterator
			it = itTmp;
		}
		
		
		//Create phiNode
		if (thenNodes.isEmpty())
		{		
			phiNodes = phiNodeCreate(joinNode, entryLabelNode, elseLabelNode);		
		}else if (elseNodes.isEmpty()){
			phiNodes = phiNodeCreate(joinNode, thenLabelNode, entryLabelNode);
		} else {
			phiNodes = phiNodeCreate(joinNode, thenLabelNode, elseLabelNode);
		}
			
		
		//Simplify controlflow structure by removing imbricated if node
		if (tmpPhiNodes != null){
			mergePhiNode(tmpPhiNodes, phiNodes);
			tmpPhiNodes=null;
		}
		
		BrLabelNode endBrLabelNode = null;
		
		
		// Detect if phiNode can be simplify
		if ((args.length != 0)&&(!it.hasNext())){ 
			tmpPhiNodes = phiNodes;
			phiNodes = new ArrayList<PhiNode>();
			LabelNode nextLabelNode = new LabelNode(node.getId(), node.getLocation(), "bb"
					+ Integer.toString(BrCounter));	
			
			//Simplify the control flow and branch to it to the next node
			if (thenNodes.isEmpty())
			{
				thenLabelNode = nextLabelNode;			
			}else if (elseNodes.isEmpty()){
				elseLabelNode = nextLabelNode;
			} else {
				thenNodes.add(new BrLabelNode(0, new Location(),nextLabelNode));
			}
			
		}else{
			
			// if node set  label
			endLabelNode = new LabelNode(node.getId(), node.getLocation(), "bb"
					+ Integer.toString(BrCounter++));
			
			endBrLabelNode = new BrLabelNode(0, new Location(), endLabelNode);
			labelNode = endLabelNode;
			
			//Set labels of brNode and phiNodes 
			if (thenNodes.isEmpty())
			{
				thenLabelNode = endLabelNode;			
				elseNodes.add(endBrLabelNode);
			}else if (elseNodes.isEmpty()){
				elseLabelNode = endLabelNode;
				thenNodes.add(new BrLabelNode(0, new Location(), endLabelNode));
			} else {
				elseNodes.add(endBrLabelNode);
				thenNodes.add(endBrLabelNode);
			}
		}
			
		return new BrNode(id, location, condition, conditionNodes, thenNodes, elseNodes, phiNodes,
				entryLabelNode, thenLabelNode, elseLabelNode,
				endLabelNode);
	}

	
	private List<PhiNode> phiNodeCreate(JoinNode node, LabelNode labelTrueNode, LabelNode labelFalseNode) {
		List<PhiAssignment> phis = node.getPhis();
		List<PhiNode> PhiNodes = new ArrayList<PhiNode>();
		
		for (PhiAssignment phi : phis){
			Map<VarDef, LabelNode> assignements = new HashMap<VarDef, LabelNode>();
			VarDef varDef = phi.getVarDef();
			AbstractType varType = varDef.getType();
			List<VarUse> vars =  phi.getVars();
			
			VarDef trueVar = vars.get(0).getVarDef();
			VarDef falseVar = vars.get(1).getVarDef();

			//Force varDef's type to phiNode type to prevent cast problem
			if (varType instanceof IntType){
				IntType intType = (IntType)varType;
				if (trueVar.getType() instanceof IntType){
					((IntType)trueVar.getType()).setSize(intType.getSize());
				}else{		
					trueVar.setType(varType);
				}
				
				if (falseVar.getType() instanceof IntType){
					((IntType)falseVar.getType()).setSize(intType.getSize());
				}else{
					falseVar.setType(varType);
				}
			}
			
			assignements.put(trueVar, labelTrueNode);
			assignements.put(falseVar, labelFalseNode);

			PhiNodes.add(new PhiNode(0, new Location(), varDef, varDef.getType(), assignements));
		}
		
		return PhiNodes;
		
	}
	
	private void mergePhiNode(List<PhiNode> sourceNodes, List<PhiNode> targetNodes){
		
		// Match and merge a couple vardef/brLabel into imbricated brNode
		for (PhiNode sourceNode : sourceNodes){
			Boolean varDefFound = false;		
			VarDef varDef = sourceNode.getVarDef();
			
			for (PhiNode targetNode : targetNodes){
				Map<VarDef, LabelNode> targetAssignements = targetNode.getAssignements();
				if (targetAssignements.containsKey(varDef)){
					AbstractType targetType = targetNode.getType();
					targetAssignements.remove(varDef);
					
					Map<VarDef, LabelNode> sourceAssignements = sourceNode.getAssignements();
					for(Map.Entry<VarDef,LabelNode> sourceAssignement : sourceAssignements.entrySet()){
						VarDef keyVar = sourceAssignement.getKey();
						LabelNode valueLab = sourceAssignement.getValue();					
						keyVar.setType(targetType);
						
						targetAssignements.put(keyVar, valueLab);
					}

					varDefFound = true;
				}
			}
			
			if (varDefFound== false){
				targetNodes.add(sourceNode);
			}
			
		}
		sourceNodes.clear();
		
	}
	
	private SelectNode selectNodeCreate(IfNode node) {
		int id = node.getId();
		Location location = node.getLocation();
		IExpr condition = node.getCondition();
		JoinNode joinNode = node.getJoinNode();
		List<PhiAssignment> phis = joinNode.getPhis();

		return new SelectNode(id, location, condition, phis);
	}
	
	private List<AbstractNode> clearIfNode(IfNode node){
		BooleanExpr condition = (BooleanExpr)node.getCondition();
		List<AbstractNode> nodes;
		List<PhiAssignment> phis = node.getJoinNode().getPhis();
		boolean value = condition.getValue();
		
		if (value == true){
			nodes = node.getThenNodes();
		}else{
			nodes = node.getThenNodes();
		}
		
		for (PhiAssignment phi : phis){
			VarDef varDef = phi.getVarDef();
			List<VarUse> varUses = phi.getVars();
			VarDef phiVar;
			if (value == true) {
				phiVar = varUses.get(0).getVarDef();
				
			}else {
				phiVar = varUses.get(1).getVarDef();
			}
					
			varDef.duplicate(phiVar);
		}
		
		return nodes;
	}

	@Override
	public void visit(IfNode node, Object... args) {
		List<AbstractNode> thenNodes = node.getThenNodes();
		List<AbstractNode> elseNodes = node.getElseNodes();

		if ((thenNodes.isEmpty())
				&& (elseNodes.isEmpty())) {

			SelectNode selectNode = selectNodeCreate(node);
			it.remove();
			it.add(selectNode);
		}else if (node.getCondition() instanceof BooleanExpr){
			List<AbstractNode> brNodes = clearIfNode(node);
			it.remove();
			for (AbstractNode brNode: brNodes){
				it.add(brNode);
			}
		} else {
			BrNode brNode = brNodeCreate(node, args);
			it.remove();
			it.add(brNode);
		}

	}
	
	@Override
	public void visit(WhileNode node, Object... args) {
		
		it.remove();
		
		//Get whileNode information
		int id = node.getId();
		Location location= node.getLocation();
		IExpr condition = node.getCondition();
		List<AbstractNode> conditionNodes = new ArrayList<AbstractNode>();
		List<AbstractNode> thenNodes = node.getNodes();
		List<AbstractNode> elseNodes = new ArrayList<AbstractNode>();
		JoinNode joinNode = node.getJoinNode();
		LabelNode entryLabelNode = null;
		LabelNode conditionLabelNode = null;
		LabelNode thenLabelNode = null;
		
		if (args.length == 0 ) {
			entryLabelNode = labelNode;
			
			conditionLabelNode = new LabelNode(node.getId(), node.getLocation(), "bb" + Integer.toString(BrCounter++));
			it.add(new BrLabelNode(0, new Location(), conditionLabelNode));
	
			it.add(conditionLabelNode);
			thenLabelNode = new LabelNode(node.getId(), node.getLocation(), "bb" + Integer.toString(BrCounter++));
			entryNode = conditionLabelNode;
			labelNode = thenLabelNode;
			
		}else if (args[0] instanceof WhileNode){
			entryLabelNode = entryNode;
			conditionLabelNode = labelNode;
			entryNode = labelNode;
			thenLabelNode = new LabelNode(node.getId(), node.getLocation(), "bb" + Integer.toString(BrCounter++));
			labelNode = thenLabelNode;
		}	
		
		
		
		//Store current iterator and branch label
		ListIterator<AbstractNode> itTmp = it;
				
		// Continue transformation on thenNode
		visitNodes(thenNodes, node);
		
		//Restore current iterator
		it = itTmp;
		
		//Create PhiNode
		List<PhiNode> phiNodes = phiNodeCreate(joinNode, entryLabelNode, labelNode);
		
		//Set endNode
		LabelNode elseLabelNode = new LabelNode(node.getId(), node.getLocation(), "bb" + Integer.toString(BrCounter++));
		LabelNode endLabelNode = elseLabelNode;
		labelNode = endLabelNode;
		

		
		//Move PhiNodes in current node 
		for (PhiNode phiNode : phiNodes){
			it.add(phiNode);
		}
		
		phiNodes.clear();
		
		//Add branch to first conditions
		thenNodes.add(new BrLabelNode(0, new Location(), conditionLabelNode));
		
		//Add BrNode into the current function
		it.add(new BrNode(id, location, condition, conditionNodes, thenNodes, elseNodes, phiNodes,
				entryLabelNode, thenLabelNode, elseLabelNode, endLabelNode));
		
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
		
		//Add void return
		if (proc.getReturnType() instanceof VoidType) {
			TypeExpr expr = new TypeExpr(null, new VoidType());
			nodes.add(new ReturnNode(0, null, expr));
		}
	}
}