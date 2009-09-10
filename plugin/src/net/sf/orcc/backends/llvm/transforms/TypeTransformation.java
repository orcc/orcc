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

import net.sf.orcc.backends.llvm.nodes.AbstractLLVMNodeVisitor;
import net.sf.orcc.backends.llvm.nodes.BitcastNode;
import net.sf.orcc.backends.llvm.nodes.BrLabelNode;
import net.sf.orcc.backends.llvm.nodes.BrNode;
import net.sf.orcc.backends.llvm.nodes.LabelNode;
import net.sf.orcc.backends.llvm.nodes.LoadFifo;
import net.sf.orcc.backends.llvm.nodes.SelectNode;
import net.sf.orcc.backends.llvm.nodes.TruncNode;
import net.sf.orcc.backends.llvm.nodes.ZextNode;
import net.sf.orcc.backends.llvm.type.PointType;
import net.sf.orcc.backends.llvm.type.LLVMAbstractType;
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


/**
 * Verify type coherence for every nodes.
 * 
 * @author Jérôme GORIN
 * 
 */
public class TypeTransformation extends AbstractLLVMNodeVisitor implements ExprVisitor{

	ListIterator<AbstractNode> it;
	
	List<AbstractType> types;
	List<String> portNames;
	
	public TypeTransformation(Actor actor) {
		if(actor.getName().compareTo("clip")==0)
		{
			int i=0;
			i=i+1;
		}
		
		portNames = new ArrayList<String>();;
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
		
		portNames.clear();
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
		visitLocals(proc.getLocals());
		visitNodes(proc.getNodes());
	}
	
	/**
	 * Store ports of the current actor
	 * 
	 */
	private void fillPorts(List<VarDef> ports) {
		for (VarDef port : ports) {
			portNames.add(port.getName());
		}
	}
	
	/**
	 * Variable visitor.
	 * 
	 */
	
	private void visitLocals(List<VarDef> locals){
		for (VarDef local : locals){
			if (portNames.contains(local.getName())){
				PointType newType = new PointType(local.getType());
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
		node.getValue().accept(this, node.getVar().getType());
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
					varUse.setVarDef(castNodeCreate(varUse.getVarDef(), typeRef));
				}
				 
			 }
			 
		 }
	}
	
	@Override
	public void visit(ReadNode node, Object... args) {	
		
		//Type to cast into
		LLVMAbstractType addrType =  new PointType(new IntType(8));

		//Vardef to cast
		VarDef readVar = node.getVarDef();
		VarUse varUse = new VarUse(readVar, null);
		VarExpr expr = new VarExpr(new Location(), varUse);

		//New vardef casted
		VarDef vardef = new VarDef(false, false, 0, new Location(), node
				.getFifoName()
				+ "_addr", null, null, 0, addrType);
		
		//Create bitcast node
		BitcastNode bitcast = new BitcastNode(node.getId(), node.getLocation(),
				vardef, expr);
		
		//Create a load fifo node
		LoadFifo loadfifo = new LoadFifo(node.getId(), node.getLocation(), node
				.getFifoName(), node.getVarDef());
	
		node.setVar(vardef);

		it.previous();
		it.add(loadfifo);
		it.add(bitcast);
		it.next();
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
				VarExpr sourceVar = (VarExpr)node.getValue();
				VarDef sourceVarDef = sourceVar.getVar().getVarDef();
				VarDef targetVarDef = castNodeCreate(sourceVarDef , typeE1);
				VarUse targetvarUse = new VarUse(targetVarDef, null);
				VarExpr targetExpr = new VarExpr(new Location(), targetvarUse);
				
				node.setValue(targetExpr);
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
				VarExpr sourceVar = (VarExpr)expr.getE2();
				VarDef sourceVarDef = sourceVar.getVar().getVarDef();
				VarDef targetVarDef = castNodeCreate(sourceVarDef , typeE1);
				VarUse targetvarUse = new VarUse(targetVarDef, null);
				VarExpr targetExpr = new VarExpr(new Location(), targetvarUse);
				
				expr.setE2(targetExpr);
			}
		}
		types = tmpTypes;
	}

	
	private VarDef castNodeCreate(VarDef var, AbstractType targetType){
		
		//Get source size and target size
		int sourceSize = sizeOf(var.getType());
		int targetSize = sizeOf(targetType);
		
		
		//Get attribute of the source vardef
		boolean assignable = var.isAssignable();
		boolean global = var.isGlobal();
		int index = var.getIndex();
		Location location = var.getLoc();
		String name = var.getName();
		AbstractNode node = null;
		List<VarUse> reference = null;
		Integer suffix;
		if (var.hasSuffix()){
			suffix=var.getSuffix();
		}else{
			suffix = null;
		}

		// Create target vardef for bitcast
		VarDef vardef = new VarDef(assignable, global, index+1, location, name, node, reference, suffix, targetType);
		VarUse varUse = new VarUse(var, null);
		VarExpr expr = new VarExpr(new Location(), varUse);
		
		
		//Add cast node before the current expression
		it.previous();
		
		//Select the type of cast (trunc if smaller, zext otherwise)
		if (sourceSize<targetSize)
		{
			ZextNode cast = new ZextNode(0, new Location(), vardef, expr);
			it.add(cast);
		}else{
			TruncNode cast = new TruncNode(0, new Location(), vardef, expr);
			it.add(cast);
		}
		
		it.next();
		
		return vardef;
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
	
	
	public void visit(BooleanExpr expr, Object... args){
		
	}

	public void visit(IntExpr expr, Object... args){
		
	}

	public void visit(ListExpr expr, Object... args){
		
	}

	public void visit(StringExpr expr, Object... args){
		
	}

	public void visit(TypeExpr expr, Object... args){
		
	}

	public void visit(UnaryExpr expr, Object... args){
		
	}

	public void visit(VarExpr expr, Object... args){
		types.add(expr.getVar().getVarDef().getType());
	}
	@Override
	public void visit(BitcastNode node, Object... args) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(BrLabelNode node, Object... args) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(BrNode node, Object... args) {
		visitNodes(node.getThenNodes());
		visitNodes(node.getElseNodes());		
	}

	@Override
	public void visit(LabelNode node, Object... args) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(LoadFifo node, Object... args) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(CallNode node, Object... args) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(EmptyNode node, Object... args) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(HasTokensNode node, Object... args) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(JoinNode node, Object... args) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(LoadNode node, Object... args) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(PeekNode node, Object... args) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(ReturnNode node, Object... args) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(WhileNode node, Object... args) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(WriteNode node, Object... args) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(TruncNode node, Object... args) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(ZextNode node, Object... args) {
		// TODO Auto-generated method stub
		
	}
	
}
