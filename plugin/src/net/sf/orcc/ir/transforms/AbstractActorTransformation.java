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

import net.sf.orcc.ir.IActorTransformation;
import net.sf.orcc.ir.IInstruction;
import net.sf.orcc.ir.INode;
import net.sf.orcc.ir.Procedure;
import net.sf.orcc.ir.actor.Action;
import net.sf.orcc.ir.actor.Actor;
import net.sf.orcc.ir.nodes.AssignVarNode;
import net.sf.orcc.ir.nodes.BlockNode;
import net.sf.orcc.ir.nodes.CallNode;
import net.sf.orcc.ir.nodes.HasTokensNode;
import net.sf.orcc.ir.nodes.IfNode;
import net.sf.orcc.ir.nodes.InitPortNode;
import net.sf.orcc.ir.nodes.InstructionVisitor;
import net.sf.orcc.ir.nodes.LoadNode;
import net.sf.orcc.ir.nodes.NodeVisitor;
import net.sf.orcc.ir.nodes.PeekNode;
import net.sf.orcc.ir.nodes.PhiAssignment;
import net.sf.orcc.ir.nodes.ReadEndNode;
import net.sf.orcc.ir.nodes.ReadNode;
import net.sf.orcc.ir.nodes.ReturnNode;
import net.sf.orcc.ir.nodes.StoreNode;
import net.sf.orcc.ir.nodes.WhileNode;
import net.sf.orcc.ir.nodes.WriteEndNode;
import net.sf.orcc.ir.nodes.WriteNode;

/**
 * This abstract class implements an no-op transformation on an actor. This
 * class should be extended by classes that implement actor transformations.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class AbstractActorTransformation implements NodeVisitor,
		InstructionVisitor, IActorTransformation {

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
	public void visit(AssignVarNode node, Object... args) {
	}

	@Override
	public void visit(BlockNode node, Object... args) {
		ListIterator<IInstruction> it = node.listIterator();
		while (it.hasNext()) {
			it.next().accept(this, it);
		}
	}

	@Override
	public void visit(CallNode node, Object... args) {
	}

	@Override
	public void visit(HasTokensNode node, Object... args) {
	}

	@Override
	public void visit(IfNode node, Object... args) {
		visit(node.getThenNodes());
		visit(node.getElseNodes());
		visit(node.getJoinNode());
	}

	@Override
	public void visit(InitPortNode node, Object... args) {
	}

	/**
	 * Visits the nodes of the given node list.
	 * 
	 * @param nodes
	 *            a list of nodes that belong to a procedure
	 */
	protected void visit(List<INode> nodes) {
		ListIterator<INode> it = nodes.listIterator();
		while (it.hasNext()) {
			INode node = it.next();
			node.accept(this, it);
		}
	}

	@Override
	public void visit(LoadNode node, Object... args) {
	}

	@Override
	public void visit(PeekNode node, Object... args) {
	}

	@Override
	public void visit(PhiAssignment node, Object... args) {
	}

	@Override
	public void visit(ReadEndNode node, Object... args) {
	}

	@Override
	public void visit(ReadNode node, Object... args) {
	}

	@Override
	public void visit(ReturnNode node, Object... args) {
	}

	@Override
	public void visit(StoreNode node, Object... args) {
	}

	@Override
	public void visit(WhileNode node, Object... args) {
	}

	@Override
	public void visit(WriteEndNode node, Object... args) {
	}

	@Override
	public void visit(WriteNode node, Object... args) {
	}

	/**
	 * Visits the given procedure.
	 * 
	 * @param procedure
	 *            a procedure
	 */
	protected void visitProcedure(Procedure procedure) {
		this.procedure = procedure;
		List<INode> nodes = procedure.getNodes();
		visit(nodes);
	}

}
