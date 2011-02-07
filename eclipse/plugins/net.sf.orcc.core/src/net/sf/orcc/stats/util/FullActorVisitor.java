/*
 * Copyright (c) 2009, IRISA
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
package net.sf.orcc.stats.util;

import java.util.List;
import java.util.ListIterator;

import net.sf.orcc.ir.Action;
import net.sf.orcc.ir.Actor;
import net.sf.orcc.ir.CFGNode;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.Instruction;
import net.sf.orcc.ir.Procedure;
import net.sf.orcc.ir.expr.BinaryExpr;
import net.sf.orcc.ir.expr.BoolExpr;
import net.sf.orcc.ir.expr.ExpressionVisitor;
import net.sf.orcc.ir.expr.FloatExpr;
import net.sf.orcc.ir.expr.IntExpr;
import net.sf.orcc.ir.expr.ListExpr;
import net.sf.orcc.ir.expr.StringExpr;
import net.sf.orcc.ir.expr.UnaryExpr;
import net.sf.orcc.ir.expr.VarExpr;
import net.sf.orcc.ir.instructions.Assign;
import net.sf.orcc.ir.instructions.Call;
import net.sf.orcc.ir.instructions.InstructionVisitor;
import net.sf.orcc.ir.instructions.Load;
import net.sf.orcc.ir.instructions.Peek;
import net.sf.orcc.ir.instructions.PhiAssignment;
import net.sf.orcc.ir.instructions.Read;
import net.sf.orcc.ir.instructions.Return;
import net.sf.orcc.ir.instructions.SpecificInstruction;
import net.sf.orcc.ir.instructions.Store;
import net.sf.orcc.ir.instructions.Write;
import net.sf.orcc.ir.nodes.BlockNode;
import net.sf.orcc.ir.nodes.IfNode;
import net.sf.orcc.ir.nodes.NodeVisitor;
import net.sf.orcc.ir.nodes.WhileNode;

/**
 * This abstract class implements a visitor of an actor. This class should be
 * extended by classes that visit actor.
 * 
 * @author Herve Yviquel
 * 
 */
public abstract class FullActorVisitor implements NodeVisitor,
		InstructionVisitor, ExpressionVisitor {

	protected Actor actor;

	protected ListIterator<Action> itAction;

	protected ListIterator<Instruction> itInstruction;

	protected ListIterator<CFGNode> itNode;

	protected Procedure procedure;

	public void visit(Action action) {
		visit(action.getBody());
	}

	public void visit(Actor actor) {
		this.actor = actor;

		itAction = actor.getActions().listIterator();
		while (itAction.hasNext()) {
			Action action = itAction.next();
			visit(action);
		}

		itAction = actor.getInitializes().listIterator();
		while (itAction.hasNext()) {
			Action action = itAction.next();
			visit(action);
		}
	}

	@Override
	public void visit(Assign assign) {
		assign.getValue().accept(this, (Object) null);
	}

	@Override
	public void visit(BinaryExpr expr, Object... args) {
		expr.getE1().accept(this, args);
		expr.getE2().accept(this, args);
	}

	@Override
	public void visit(BlockNode blockNode) {
		ListIterator<Instruction> it = blockNode.listIterator();
		while (it.hasNext()) {
			Instruction instruction = it.next();
			itInstruction = it;
			instruction.accept(this);
		}
	}

	@Override
	public void visit(BoolExpr expr, Object... args) {
	}

	@Override
	public void visit(Call call) {
		visit(call.getProcedure());
		for (Expression expr : call.getParameters()) {
			expr.accept(this, (Object) null);
		}
	}

	@Override
	public void visit(FloatExpr expr, Object... args) {
	}

	@Override
	public void visit(IfNode ifNode) {
		visit(ifNode.getThenNodes());
		visit(ifNode.getElseNodes());
		visit(ifNode.getJoinNode());
	}

	@Override
	public void visit(IntExpr expr, Object... args) {
	}

	public void visit(List<CFGNode> nodes) {
		ListIterator<CFGNode> it = nodes.listIterator();
		while (it.hasNext()) {
			CFGNode node = it.next();
			itNode = it;
			node.accept(this);
		}
	}

	@Override
	public void visit(ListExpr expr, Object... args) {
	}

	@Override
	public void visit(Load load) {
		for (Expression expr : load.getIndexes()) {
			expr.accept(this, (Object) null);
		}
	}

	@Override
	public void visit(Peek peek) {
	}

	@Override
	public void visit(PhiAssignment phi) {
		for (Expression expr : phi.getValues()) {
			expr.accept(this, (Object) null);
		}
	}

	public void visit(Procedure procedure) {
		this.procedure = procedure;
		List<CFGNode> nodes = procedure.getNodes();
		visit(nodes);
	}

	@Override
	public void visit(Read read) {
	}

	@Override
	public void visit(Return returnInstr) {
		returnInstr.getValue().accept(this, (Object) null);
	}

	@Override
	public void visit(SpecificInstruction instr) {
	}

	@Override
	public void visit(Store store) {
		for (Expression expr : store.getIndexes()) {
			expr.accept(this, (Object) null);
		}
	}

	@Override
	public void visit(StringExpr expr, Object... args) {
	}

	@Override
	public void visit(UnaryExpr expr, Object... args) {
		expr.getExpr().accept(this, args);
	}

	@Override
	public void visit(VarExpr expr, Object... args) {
	}

	@Override
	public void visit(WhileNode whileNode) {
		visit(whileNode.getNodes());
		visit(whileNode.getJoinNode());
	}

	@Override
	public void visit(Write write) {
	}

}
