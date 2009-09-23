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
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;

import net.sf.orcc.backends.llvm.nodes.AbstractLLVMNode;
import net.sf.orcc.backends.llvm.nodes.AbstractLLVMNodeVisitor;
import net.sf.orcc.backends.llvm.nodes.BitcastNode;
import net.sf.orcc.backends.llvm.nodes.BrNode;
import net.sf.orcc.backends.llvm.nodes.LabelNode;
import net.sf.orcc.backends.llvm.nodes.LoadFifo;
import net.sf.orcc.backends.llvm.nodes.SelectNode;
import net.sf.orcc.backends.llvm.nodes.SextNode;
import net.sf.orcc.backends.llvm.nodes.TruncNode;
import net.sf.orcc.backends.llvm.nodes.ZextNode;
import net.sf.orcc.backends.llvm.type.LLVMAbstractType;
import net.sf.orcc.backends.llvm.type.PointType;
import net.sf.orcc.ir.Location;
import net.sf.orcc.ir.VarDef;
import net.sf.orcc.ir.actor.Action;
import net.sf.orcc.ir.actor.Actor;
import net.sf.orcc.ir.actor.Procedure;
import net.sf.orcc.ir.actor.StateVar;
import net.sf.orcc.ir.actor.VarUse;
import net.sf.orcc.ir.expr.BinaryExpr;
import net.sf.orcc.ir.expr.BooleanExpr;
import net.sf.orcc.ir.expr.ExprVisitor;
import net.sf.orcc.ir.expr.IntExpr;
import net.sf.orcc.ir.expr.ListExpr;
import net.sf.orcc.ir.expr.StringExpr;
import net.sf.orcc.ir.expr.TypeExpr;
import net.sf.orcc.ir.expr.UnaryExpr;
import net.sf.orcc.ir.expr.VarExpr;
import net.sf.orcc.ir.nodes.AbstractNode;
import net.sf.orcc.ir.nodes.AssignVarNode;
import net.sf.orcc.ir.nodes.CallNode;
import net.sf.orcc.ir.nodes.EmptyNode;
import net.sf.orcc.ir.nodes.HasTokensNode;
import net.sf.orcc.ir.nodes.IfNode;
import net.sf.orcc.ir.nodes.JoinNode;
import net.sf.orcc.ir.nodes.LoadNode;
import net.sf.orcc.ir.nodes.PeekNode;
import net.sf.orcc.ir.nodes.PhiAssignment;
import net.sf.orcc.ir.nodes.ReadNode;
import net.sf.orcc.ir.nodes.ReturnNode;
import net.sf.orcc.ir.nodes.StoreNode;
import net.sf.orcc.ir.nodes.WhileNode;
import net.sf.orcc.ir.nodes.WriteNode;
import net.sf.orcc.ir.type.AbstractType;
import net.sf.orcc.ir.type.BoolType;
import net.sf.orcc.ir.type.IntType;
import net.sf.orcc.ir.type.UintType;

/**
 * Verify type coherence for every nodes.
 * 
 * @author Jérôme GORIN
 * 
 */
public class TypeTransformation extends AbstractLLVMNodeVisitor implements ExprVisitor{

	ListIterator<AbstractNode> it;
	
	List<AbstractType> types;
	Hashtable<String, Integer> portIndex;
	int nodeCount;
	
	public TypeTransformation(Actor actor) {
		
		portIndex = new Hashtable<String, Integer>();
		fillPorts(actor.getInputs());
		fillPorts(actor.getOutputs());
		
		visitStateVars(actor.getStateVars());
		
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
		
		portIndex.clear();
	}

	private void visitNodes(List<AbstractNode> nodes) {
		it = nodes.listIterator();
		while (it.hasNext()) {
			types = new ArrayList<AbstractType>();
			it.next().accept(this);
			types.clear();
		}
	}

	private void visitProc(Procedure proc) {
		nodeCount = 0;
		
		visitLocals(proc.getLocals());
		clearPorts();
		visitNodes(proc.getNodes());
	}
	
	/**
	 * Store ports of the current actor
	 * 
	 */
	private void fillPorts(List<VarDef> ports) {
		for (VarDef port : ports) {
			portIndex.put(port.getName(), new Integer(0));
		}
	}
	
	@SuppressWarnings("unchecked")
	private void clearPorts() {	
		Set entries = portIndex.entrySet();
		Iterator itr = entries.iterator();
		
		while (itr.hasNext()) {
			Map.Entry entry = (Map.Entry) itr.next();
			entry.setValue(0);
		}	
	}
	
	/**
	 * Variable visitor.
	 * 
	 */
	
	private void visitLocals(List<VarDef> locals){
		for (VarDef local : locals){
			if (portIndex.containsKey(local.getName())){
				//PointType newType = new PointType(local.getType());
				PointType newType = new PointType(new IntType(32));
				local.setType(newType);
			}
		}
	}
	
	private void visitStateVars(List<StateVar> stateVars){
		for (StateVar stateVar : stateVars){
			VarDef vardef = stateVar.getDef();
			
			PointType newType = new PointType(vardef.getType());
			vardef.setType(newType);
		}
	}
	
	
	/**
	 * Nodes visitor.
	 * 
	 */
	
	@Override
	public void visit(AssignVarNode node, Object... args) {
		AbstractType varType = node.getVar().getType();
		
		node.getValue().accept(this, varType);
		if (!types.isEmpty()){
			for (AbstractType type : types){
				if (!varType.equals(type)){
					VarDef castVar = varDefCreate(type);			
					it.add(castNodeCreate(castVar, node.getVar()));
					node.setVar(castVar);
				}
			}
		}
	}

	@Override
	public void visit(IfNode node, Object... args) {
	}
	
	@Override
	public void visit(SelectNode node, Object... args) {
		 List<PhiAssignment> phis = node.getPhis();
		 for (PhiAssignment phi : phis){
			 VarDef varDef = phi.getVarDef();
			 AbstractType typeRef =  varDef.getType();
			 List<VarUse> varUses = phi.getVars();
		
			 for (VarUse varUse : varUses){
				 AbstractType type = varUse.getVarDef().getType();
				 
				if (!(type.equals(typeRef))){
					it.previous();
					VarDef castVar = varDefCreate(typeRef);
					it.add(castNodeCreate(varUse.getVarDef(), castVar));
					varUse.setVarDef(castVar);
					it.next();
				}
				 
			 }
			 
		 }
	}
	
	public VarDef castFifo(VarDef readVar, String fifoName) {
		
		//Type to cast into
		LLVMAbstractType addrType =  new PointType(new IntType(8));

		int index = (Integer)portIndex.get(fifoName);
		
		//Vardef to cast
		VarUse varUse = new VarUse(readVar, null);
		VarExpr expr = new VarExpr(new Location(), varUse);
		//New vardef casted
		VarDef vardef = new VarDef(false, false, index, new Location(), fifoName, null, null, 0, addrType);
		
		
		//Create bitcast node
		BitcastNode bitcast = new BitcastNode(0, new Location(), vardef, expr);
		
		//Create a load fifo node
		LoadFifo loadfifo = new LoadFifo(0, new Location(), fifoName, readVar, index);

		it.previous();
		it.add(loadfifo);
		it.add(bitcast);
		it.next();
		
		return vardef;

		
	}
	
	@Override
	public void visit(ReadNode node, Object... args) {	
		
		String fifoName = node.getFifoName();
		
		node.setVar(castFifo(node.getVarDef(), fifoName));
		int index = (Integer)portIndex.get(fifoName);
		node.setFifoName(fifoName + "_" + Integer.toString(index));
		portIndex.remove(fifoName);
		portIndex.put(fifoName, new Integer(++index));
		
	}
	
	@Override
	public void visit(WriteNode node, Object... args) {
		String fifoName = node.getFifoName();
			
		node.setVarDef(castFifo(node.getVarDef(), fifoName));
		int index = (Integer)portIndex.get(fifoName);
		node.setFifoName(fifoName + "_" + Integer.toString(index));
		portIndex.remove(fifoName);
		portIndex.put(fifoName, new Integer(++index));
		
	}
	
	@Override
	public void visit(PeekNode node, Object... args) {
		node.setVar(castFifo(node.getVarDef(), node.getFifoName()));
	}


	@Override
	public void visit(StoreNode node, Object... args) {
		types.clear();
		PointType type = (PointType)node.getTarget().getVarDef().getType();
		
		types.add(type.getType());
		node.getValue().accept(this);
		if (types.size()==2){
			AbstractType typeE1 = types.get(0);
			AbstractType typeE2 = types.get(1);
			if (!(typeE1.equals(typeE2))){
				it.previous();
								
				VarExpr sourceVar = (VarExpr)node.getValue();
				VarUse varUse = sourceVar.getVar();
								
				VarDef castVar = varDefCreate(typeE1);
				it.add(castNodeCreate(varUse.getVarDef(), castVar));
				varUse.setVarDef(castVar);
				
				node.setValue(sourceVar);
				it.next();
			}
		}
	}

	/**
	 * Expressions visitor.
	 * 
	 */

	public void visit(BinaryExpr expr, Object... args){
	
		List<AbstractType> tmpTypes = types;
		types = new ArrayList<AbstractType>(); 
		expr.getE1().accept(this);
		expr.getE2().accept(this);
		if (types.size()==2){
			AbstractType typeE1 = types.get(0);
			AbstractType typeE2 = types.get(1);
			if (!(typeE1.equals(typeE2))){
				if (expr.getE2() instanceof VarExpr){
					VarExpr sourceVar;

					//Add cast node before the current expression
					it.previous();

					sourceVar = (VarExpr)expr.getE2();
					VarDef sourceVarDef = sourceVar.getVar().getVarDef();
					VarDef vardef = varDefCreate(typeE1);
					it.add(castNodeCreate(sourceVarDef , vardef));
					VarUse targetvarUse = new VarUse(vardef, null);
					VarExpr targetExpr = new VarExpr(new Location(), targetvarUse);
					
					expr.setE2(targetExpr);
					
					it.next();
				}
			}
		}
		types = tmpTypes;
		types.add(expr.getType());
	}

	
	private AbstractLLVMNode castNodeCreate(VarDef var, VarDef targetVar){
		
		//Get source size and target size
		int sourceSize = sizeOf(var.getType());
		AbstractType targetType = targetVar.getType();
		int targetSize = sizeOf(targetType);
		
		// Create target expr for bitcast
		VarUse varUse = new VarUse(var, null);
		VarExpr expr = new VarExpr(new Location(), varUse);
		
		//Select the type of cast (trunc if smaller, zext otherwise)
		if (sourceSize<targetSize)
		{
			if (var.getType() instanceof UintType){
				return new ZextNode(0, new Location(), targetVar, expr);
			}else {
				return new SextNode(0, new Location(), targetVar, expr);	
			}
		}else{
			return new TruncNode(0, new Location(), targetVar, expr);
		}
	}
	
	private VarDef varDefCreate(AbstractType type){
		
		return new VarDef(false, false, 0, new Location(), "", null, null, nodeCount++, type);
	}
	
	private int sizeOf(AbstractType type){
		int size=0;
		
		if (type instanceof IntType)
		{
			size = ((IntType)type).getSize();
		} else if (type instanceof BoolType){
			size = 1;
		}			
		
		return size;
	}
	
	
	public void visit(VarExpr expr, Object... args){
		types.add(expr.getVar().getVarDef().getType());
	}

	@Override
	public void visit(BrNode node, Object... args) {
		
		ListIterator<AbstractNode> tmpit = it;
		visitNodes(node.getThenNodes());
		visitNodes(node.getElseNodes());
		it= tmpit;
		node.getJoinNode().accept(this, node);
		
	}

	@Override
	public void visit(LabelNode node, Object... args) {
		
		
	}

	@Override
	public void visit(LoadFifo node, Object... args) {
		
		
	}

	@Override
	public void visit(CallNode node, Object... args) {
		
		
	}

	@Override
	public void visit(EmptyNode node, Object... args) {
		
		
	}

	@Override
	public void visit(HasTokensNode node, Object... args) {
	}

	@Override
	public void visit(JoinNode node, Object... args) {		
	/*	List<PhiAssignment> phis = node.getPhis();
		BrNode brNode = (BrNode)args[0];
		
		for (PhiAssignment phi : phis){
			int count=0;
			
			VarDef varDef = phi.getVarDef();
			List<VarUse> varUses = phi.getVars();
			
			for (VarUse varUse : varUses){
				VarDef varDefUse = varUse.getVarDef();
				AbstractType TypeE1 = varDef.getType();
				AbstractType TypeE2 = varDefUse.getType();
				if(!(TypeE1.equals(TypeE2))){
					List<AbstractNode> nodes;
					if (count==0){
						nodes = brNode.getThenNodes();
					}else {
						nodes = brNode.getElseNodes();
					}
					VarDef castVar = varDefCreate(TypeE1);
					AbstractLLVMNode castNode = castNodeCreate(varDefUse, castVar);
					varUse.setVarDef(castVar);
					nodes.add(castNode);
				}
				
				count++;
			}
			
			
		}*/
		
	}

	@Override
	public void visit(LoadNode node, Object... args) {
		VarDef sourceVar = node.getSource().getVarDef();
		VarDef targetVar = node.getTarget();
		
		AbstractType targetType = targetVar.getType();
		PointType sourceType = (PointType)sourceVar.getType();
		if (!(targetType.equals(sourceType.getType()))){
			VarDef castVar = new VarDef(sourceVar);
			castVar.setName("");
			castVar.setIndex(0);
			castVar.setType(sourceType.getType());
			castVar.setSuffix(nodeCount++);
			node.setTarget(castVar);
			it.add(castNodeCreate(castVar, targetVar));
		}
	}

	@Override
	public void visit(BooleanExpr expr, Object... args) {
		
	}

	@Override
	public void visit(IntExpr expr, Object... args) {
		
	}

	@Override
	public void visit(ListExpr expr, Object... args) {
		
	}

	@Override
	public void visit(StringExpr expr, Object... args) {
		
	}

	@Override
	public void visit(TypeExpr expr, Object... args) {
		
	}

	@Override
	public void visit(UnaryExpr expr, Object... args) {
		
	}

	@Override
	public void visit(ReturnNode node, Object... args) {
		
	}

	@Override
	public void visit(WhileNode node, Object... args) {
		
	}

}
