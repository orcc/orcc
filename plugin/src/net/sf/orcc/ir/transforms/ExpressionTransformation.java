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
package net.sf.orcc.ir.transforms;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import net.sf.orcc.ir.Location;
import net.sf.orcc.ir.VarDef;
import net.sf.orcc.ir.actor.Action;
import net.sf.orcc.ir.actor.Actor;
import net.sf.orcc.ir.actor.Procedure;
import net.sf.orcc.ir.actor.VarUse;
import net.sf.orcc.ir.expr.IExpr;
import net.sf.orcc.ir.expr.BinaryExpr;
import net.sf.orcc.ir.expr.VarExpr;
import net.sf.orcc.ir.nodes.AbstractNode;
import net.sf.orcc.ir.nodes.AbstractNodeVisitor;
import net.sf.orcc.ir.nodes.AssignVarNode;
import net.sf.orcc.ir.nodes.IfNode;
import net.sf.orcc.ir.nodes.LoadNode;
import net.sf.orcc.ir.nodes.StoreNode;
import net.sf.orcc.ir.nodes.WhileNode;
import net.sf.orcc.ir.type.AbstractType;


/**
 * Split expression and effective node.
 * 
 * @author Jérôme GORIN
 * 
 */
public class ExpressionTransformation extends AbstractNodeVisitor {

	int exprCounter;

	public ExpressionTransformation(Actor actor) {
		
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
	@SuppressWarnings("unchecked")
	public void visit(IfNode node, Object... args) {
		ListIterator<AbstractNode> it = (ListIterator<AbstractNode>) args[0];	
		
		if (node.getCondition() instanceof BinaryExpr) {
			
			BinaryExpr binExpr = (BinaryExpr)node.getCondition();
			VarExpr expr = splitBinaryExpr(binExpr, it);

			node.setCondition(expr);
		}

		visitNodes(node.getThenNodes());
		visitNodes(node.getElseNodes());
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public void visit(AssignVarNode node, Object... args) {
		ListIterator<AbstractNode> it = (ListIterator<AbstractNode>) args[0];	
		IExpr value = node.getValue();
		
		if (value instanceof BinaryExpr){
			BinaryExpr expr = (BinaryExpr)value;
			if (expr.getE1() instanceof BinaryExpr){
				expr.setE1(splitBinaryExpr((BinaryExpr)expr.getE1(), it));
			}
			
			if (expr.getE2() instanceof BinaryExpr){
				expr.setE2(splitBinaryExpr((BinaryExpr)expr.getE2(), it));
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	public VarExpr splitBinaryExpr(BinaryExpr expr, Object... args){
		ListIterator<AbstractNode> it = (ListIterator<AbstractNode>) args[0];		
		
		if (expr.getE1() instanceof BinaryExpr){ 
			expr.setE1(splitBinaryExpr((BinaryExpr)expr.getE1(), it));
		}
		
		if (expr.getE2() instanceof BinaryExpr){
			expr.setE2(splitBinaryExpr((BinaryExpr)expr.getE2(), it));
		}
		
		VarDef vardef = varDefCreate (expr.getType());
		VarUse varuse = new VarUse(vardef, null);
		VarExpr varexpr = new VarExpr(new Location(), varuse);
		
		AssignVarNode assignNode = new AssignVarNode(0, new Location(),
				vardef, expr);
		
		it.previous();
		it.add(assignNode);
		it.next();
		
		return varexpr;
		
	}
	
	@SuppressWarnings("unchecked")
	public List<IExpr> splitIndex(List<IExpr> indexes, Object... args){
		ListIterator<AbstractNode> it = (ListIterator<AbstractNode>) args[0];		
		List<IExpr> tmpIndexes = new ArrayList<IExpr>();
		
		for (IExpr index : indexes){
			if (index instanceof BinaryExpr){
				VarExpr expr = splitBinaryExpr((BinaryExpr)index, it);
				tmpIndexes.add(expr);
			}
			else{
				tmpIndexes.add(index);
			}
		}
		
		return tmpIndexes;
		
	}
	

	@Override
	@SuppressWarnings("unchecked")
	public void visit(StoreNode node, Object... args) {
		ListIterator<AbstractNode> it = (ListIterator<AbstractNode>) args[0];

		if (node.getValue() instanceof BinaryExpr) {			
			VarExpr expr = splitBinaryExpr((BinaryExpr)node.getValue(), it);
			node.setValue(expr);
		}
		
		List<IExpr> indexes = node.getIndexes();
		if (!indexes.isEmpty()) {			
			node.setIndexes(splitIndex(indexes,args));
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public void visit(WhileNode node, Object... args) {
		ListIterator<AbstractNode> it = (ListIterator<AbstractNode>) args[0];
		
		if (node.getCondition() instanceof BinaryExpr) {
			VarExpr expr = splitBinaryExpr((BinaryExpr)node.getCondition(), it);
			node.setCondition(expr);
		}
		
		visitNodes(node.getNodes());
		
	}

	
	@Override
	public void visit(LoadNode node, Object... args) {
		List<IExpr> indexes = node.getIndexes();
	
		if (!indexes.isEmpty()) {			
			node.setIndexes(splitIndex(indexes,args));
		}
	}

	private VarDef varDefCreate(AbstractType type){
		return new VarDef(false, false, exprCounter++, new Location(), "expr", null, null, 0, type);
	}
	
	private void visitNodes(List<AbstractNode> nodes) {
		ListIterator<AbstractNode> it = nodes.listIterator();

		while (it.hasNext()) {
			it.next().accept(this, it);
		}
	}

	private void visitProc(Procedure proc) {
		exprCounter = 0;

		List<AbstractNode> nodes = proc.getNodes();
		visitNodes(nodes);
	}
}
