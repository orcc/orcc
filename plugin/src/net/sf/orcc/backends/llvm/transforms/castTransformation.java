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

import net.sf.orcc.backends.llvm.nodes.BitcastNode;
import net.sf.orcc.backends.llvm.nodes.BrLabelNode;
import net.sf.orcc.backends.llvm.nodes.BrNode;
import net.sf.orcc.backends.llvm.nodes.LabelNode;
import net.sf.orcc.backends.llvm.nodes.LoadFifo;
import net.sf.orcc.backends.llvm.nodes.SelectNode;
import net.sf.orcc.backends.llvm.nodes.TruncNode;
import net.sf.orcc.backends.llvm.type.IType;
import net.sf.orcc.ir.Location;
import net.sf.orcc.ir.VarDef;
import net.sf.orcc.ir.actor.Action;
import net.sf.orcc.ir.actor.Actor;
import net.sf.orcc.ir.actor.Procedure;
import net.sf.orcc.ir.actor.VarUse;
import net.sf.orcc.ir.expr.BinaryExpr;
import net.sf.orcc.ir.expr.BooleanExpr;
import net.sf.orcc.ir.expr.AbstractExpr;
import net.sf.orcc.ir.expr.IntExpr;
import net.sf.orcc.ir.expr.ListExpr;
import net.sf.orcc.ir.expr.StringExpr;
import net.sf.orcc.ir.expr.TypeExpr;
import net.sf.orcc.ir.expr.UnaryExpr;
import net.sf.orcc.ir.expr.VarExpr;
import net.sf.orcc.ir.nodes.AbstractNode;
import net.sf.orcc.ir.nodes.AbstractNodeVisitor;
import net.sf.orcc.ir.nodes.AssignVarNode;
import net.sf.orcc.ir.nodes.CallNode;
import net.sf.orcc.ir.nodes.EmptyNode;
import net.sf.orcc.ir.nodes.HasTokensNode;
import net.sf.orcc.ir.nodes.IfNode;
import net.sf.orcc.ir.nodes.JoinNode;
import net.sf.orcc.ir.nodes.LoadNode;
import net.sf.orcc.ir.nodes.PeekNode;
import net.sf.orcc.ir.nodes.ReadNode;
import net.sf.orcc.ir.nodes.ReturnNode;
import net.sf.orcc.ir.nodes.StoreNode;
import net.sf.orcc.ir.nodes.WhileNode;
import net.sf.orcc.ir.nodes.WriteNode;
import net.sf.orcc.ir.type.BoolType;
import net.sf.orcc.ir.type.IntType;
import net.sf.orcc.ir.type.ListType;
import net.sf.orcc.ir.type.StringType;
import net.sf.orcc.ir.type.UintType;
import net.sf.orcc.ir.type.VoidType;
import net.sf.orcc.ir.expr.ExprVisitor;
import net.sf.orcc.backends.llvm.type.LLVMTypeVisitor;
import net.sf.orcc.ir.type.AbstractType;
import net.sf.orcc.backends.llvm.nodes.AbstractLLVMNodeVisitor;


/**
 * Verify type coherence for every nodes.
 * 
 * @author Jérôme GORIN
 * 
 */
public class castTransformation extends AbstractLLVMNodeVisitor implements ExprVisitor{

	ListIterator<AbstractNode> it;
	
	List<AbstractType> types;
	
	
	public castTransformation(Actor actor) {
			
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

	private void visitNodes(List<AbstractNode> nodes) {
		it = nodes.listIterator();
		while (it.hasNext()) {
			types = new ArrayList<AbstractType>();
			it.next().accept(this);
			types.clear();
		}
	}

	private void visitProc(Procedure proc) {
		visitNodes(proc.getNodes());
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
	@SuppressWarnings("unchecked")
	public void visit(ReadNode node, Object... args) {
		VarDef vardef = new VarDef(false, false, 0, new Location(), node
				.getFifoName()
				+ "_addr", null, null, 0, new IType(new IntType(8), true));

		VarDef exprVarDef = node.getVarDef();
		exprVarDef.setType(new IType(exprVarDef.getType(), true));

		VarUse varUse = new VarUse(node.getVarDef(), null);
		VarExpr expr = new VarExpr(new Location(), varUse);

		LoadFifo loadfifo = new LoadFifo(node.getId(), node.getLocation(), node
				.getFifoName(), node.getVarDef());
		BitcastNode bitcast = new BitcastNode(node.getId(), node.getLocation(),
				vardef, expr);
		node.setVar(vardef);

		it.previous();
		it.add(loadfifo);
		it.add(bitcast);
		it.next();
	}

	@Override
	public void visit(StoreNode node, Object... args) {

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
				expr.setE2(castNodeCreate(typeE2, expr.getE2(), typeE1));
			}
		}
		types = tmpTypes;
	}

	
	private AbstractExpr castNodeCreate(AbstractType sourceType, AbstractExpr sourceExpr, AbstractType targetType){
		int sourceSize = sizeOf(sourceType);
		int targetSize = sizeOf(targetType);
		
		VarExpr sourceVar = (VarExpr)sourceExpr;
		VarDef sourceVarDef = sourceVar.getVar().getVarDef();
			
		VarDef vardef = new VarDef(false, false, sourceVarDef.getIndex()+1, new Location(), sourceVarDef.getName(), null, null, 0, targetType);
		
		VarUse varUse = new VarUse(vardef, null);
		VarExpr expr = new VarExpr(new Location(), varUse);
		
		it.previous();
		
		if (sourceSize<targetSize)
		{
			BitcastNode cast = new BitcastNode(0, new Location(), vardef, sourceExpr);
			it.add(cast);
		}else{
			TruncNode cast = new TruncNode(0, new Location(), vardef, sourceExpr);
			it.add(cast);
		}
		
		it.next();
		
		return expr;
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
	public void visit(SelectNode node, Object... args) {
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
	
}
