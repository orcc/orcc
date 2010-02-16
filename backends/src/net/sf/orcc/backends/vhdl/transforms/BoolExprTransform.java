/*
 * Copyright (c) 2010, IETR/INSA of Rennes
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
package net.sf.orcc.backends.vhdl.transforms;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import net.sf.orcc.ir.CFGNode;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.Instruction;
import net.sf.orcc.ir.LocalVariable;
import net.sf.orcc.ir.expr.BoolExpr;
import net.sf.orcc.ir.instructions.Assign;
import net.sf.orcc.ir.nodes.BlockNode;
import net.sf.orcc.ir.nodes.IfNode;
import net.sf.orcc.ir.transforms.AbstractActorTransformation;

/**
 * This class defines an actor transformation that transforms assignments whose
 * right hand side is a boolean expression to if nodes.
 * 
 * @author Matthieu Wipliez
 * @author Nicolas Siret
 * 
 */
public class BoolExprTransform extends AbstractActorTransformation {

	private ListIterator<CFGNode> nodeIt;

	/**
	 * Creates an "if" node that assign <code>true</code> or <code>false</code>
	 * to <code>target</code> if the given expression is <code>true</code>,
	 * respectively <code>false</code>/
	 * 
	 * @param target
	 *            target local variable
	 * @param expr
	 *            an expression
	 */
	private void createIfNode(LocalVariable target, Expression expr) {
		List<CFGNode> thenNodes = new ArrayList<CFGNode>();
		List<CFGNode> elseNodes = new ArrayList<CFGNode>();
		IfNode node = new IfNode(procedure, expr, thenNodes, elseNodes,
				new BlockNode(procedure));

		// add "then" nodes
		BlockNode block = new BlockNode(procedure);
		thenNodes.add(block);
		Assign assign = new Assign(target, new BoolExpr(true));
		block.add(assign);

		// add "else" nodes
		block = new BlockNode(procedure);
		elseNodes.add(block);
		assign = new Assign(target, new BoolExpr(false));
		block.add(assign);

		nodeIt.add(node);
	}

	/**
	 * Creates a new block node that contains the instructions after the assign.
	 * 
	 * @param iit
	 *            list iterator
	 */
	private void createNewBlock(ListIterator<Instruction> iit) {
		BlockNode block = new BlockNode(procedure);
		iit.previous();
		iit.remove();
		while (iit.hasNext()) {
			Instruction instruction = iit.next();
			iit.remove();
			block.add(instruction);
		}

		nodeIt.add(block);
	}

	@Override
	@SuppressWarnings("unchecked")
	public void visit(Assign node, Object... args) {
		if (node.getTarget().getType().isBool()) {
			Expression expr = node.getValue();
			if (expr.isBinaryExpr() || expr.isUnaryExpr()) {
				ListIterator<Instruction> iit = (ListIterator<Instruction>) args[0];
				createIfNode(node.getTarget(), expr);
				createNewBlock(iit);
			}
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public void visit(BlockNode node, Object... args) {
		nodeIt = (ListIterator<CFGNode>) args[0];
		ListIterator<Instruction> it = node.listIterator();
		while (it.hasNext()) {
			it.next().accept(this, it);
		}
	}

}
