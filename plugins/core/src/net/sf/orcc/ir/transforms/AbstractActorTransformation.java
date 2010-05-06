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

import java.util.List;
import java.util.ListIterator;

import net.sf.orcc.ir.Action;
import net.sf.orcc.ir.Actor;
import net.sf.orcc.ir.ActorTransformation;
import net.sf.orcc.ir.CFGNode;
import net.sf.orcc.ir.Instruction;
import net.sf.orcc.ir.Procedure;
import net.sf.orcc.ir.instructions.Assign;
import net.sf.orcc.ir.instructions.Call;
import net.sf.orcc.ir.instructions.HasTokens;
import net.sf.orcc.ir.instructions.InitPort;
import net.sf.orcc.ir.instructions.InstructionVisitor;
import net.sf.orcc.ir.instructions.Load;
import net.sf.orcc.ir.instructions.Peek;
import net.sf.orcc.ir.instructions.PhiAssignment;
import net.sf.orcc.ir.instructions.Read;
import net.sf.orcc.ir.instructions.ReadEnd;
import net.sf.orcc.ir.instructions.Return;
import net.sf.orcc.ir.instructions.SpecificInstruction;
import net.sf.orcc.ir.instructions.Store;
import net.sf.orcc.ir.instructions.Write;
import net.sf.orcc.ir.instructions.WriteEnd;
import net.sf.orcc.ir.nodes.BlockNode;
import net.sf.orcc.ir.nodes.IfNode;
import net.sf.orcc.ir.nodes.NodeVisitor;
import net.sf.orcc.ir.nodes.WhileNode;

/**
 * This abstract class implements an no-op transformation on an actor. This
 * class should be extended by classes that implement actor transformations.
 * 
 * @author Matthieu Wipliez
 * 
 */
public abstract class AbstractActorTransformation implements NodeVisitor,
		InstructionVisitor, ActorTransformation {

	protected Procedure procedure;

	@Override
	public void transform(Actor actor) {
		for (Procedure proc : actor.getProcs()) {
			visitProcedure(proc);
		}

		for (Action action : actor.getActions()) {
			visitProcedure(action.getBody());
			visitProcedure(action.getScheduler());
		}

		for (Action action : actor.getInitializes()) {
			visitProcedure(action.getBody());
			visitProcedure(action.getScheduler());
		}
	}

	@Override
	public void visit(Assign assign, Object... args) {
	}

	@Override
	public void visit(BlockNode blockNode, Object... args) {
		ListIterator<Instruction> it = blockNode.listIterator();
		while (it.hasNext()) {
			it.next().accept(this, it);
		}
	}

	@Override
	public void visit(Call call, Object... args) {
	}

	@Override
	public void visit(HasTokens hasTokens, Object... args) {
	}

	@Override
	public void visit(IfNode ifNode, Object... args) {
		visit(ifNode.getThenNodes());
		visit(ifNode.getElseNodes());
		visit(ifNode.getJoinNode(), args);
	}

	@Override
	public void visit(InitPort initPort, Object... args) {
	}

	/**
	 * Visits the nodes of the given node list.
	 * 
	 * @param nodes
	 *            a list of nodes that belong to a procedure
	 * @param args
	 *            arguments
	 */
	protected void visit(List<CFGNode> nodes, Object... args) {
		ListIterator<CFGNode> it = nodes.listIterator();
		while (it.hasNext()) {
			CFGNode node = it.next();
			node.accept(this, it);
		}
	}

	@Override
	public void visit(Load load, Object... args) {
	}

	@Override
	public void visit(Peek peek, Object... args) {
	}

	@Override
	public void visit(PhiAssignment phi, Object... args) {
	}

	@Override
	public void visit(Read read, Object... args) {
	}

	@Override
	public void visit(ReadEnd readEnd, Object... args) {
	}

	@Override
	public void visit(Return returnInstr, Object... args) {
	}

	@Override
	public void visit(SpecificInstruction node, Object... args) {
		// default implementation does nothing
	}

	@Override
	public void visit(Store store, Object... args) {
	}

	@Override
	public void visit(WhileNode whileNode, Object... args) {
		visit(whileNode.getNodes());
		visit(whileNode.getJoinNode(), args);
	}

	@Override
	public void visit(Write write, Object... args) {
	}

	@Override
	public void visit(WriteEnd writeEnd, Object... args) {
	}

	/**
	 * Visits the given procedure.
	 * 
	 * @param procedure
	 *            a procedure
	 */
	public void visitProcedure(Procedure procedure) {
		this.procedure = procedure;
		List<CFGNode> nodes = procedure.getNodes();
		visit(nodes);
	}

}
