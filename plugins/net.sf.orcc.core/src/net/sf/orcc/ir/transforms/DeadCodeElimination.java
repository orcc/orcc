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

import net.sf.orcc.ir.Actor;
import net.sf.orcc.ir.CFGNode;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.Instruction;
import net.sf.orcc.ir.LocalVariable;
import net.sf.orcc.ir.Location;
import net.sf.orcc.ir.Use;
import net.sf.orcc.ir.expr.BoolExpr;
import net.sf.orcc.ir.expr.VarExpr;
import net.sf.orcc.ir.instructions.Assign;
import net.sf.orcc.ir.instructions.PhiAssignment;
import net.sf.orcc.ir.nodes.BlockNode;
import net.sf.orcc.ir.nodes.IfNode;

/**
 * This class defines a very simple Dead Code Elimination.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class DeadCodeElimination extends AbstractActorTransformation {

	private void addNodes(ListIterator<CFGNode> it, List<CFGNode> nodes,
			BlockNode join, int index) {
		it.previous();
		it.remove();

		for (CFGNode node : nodes) {
			it.add(node);
		}

		it.add(join);
		replacePhis(join, index);
	}

	private void replacePhis(BlockNode joinNode, int index) {
		ListIterator<Instruction> it = joinNode.listIterator();
		while (it.hasNext()) {
			Instruction instruction = it.next();
			if (instruction instanceof PhiAssignment) {
				PhiAssignment phi = (PhiAssignment) instruction;

				LocalVariable target = phi.getTarget();
				LocalVariable source = (LocalVariable) phi.getVars().get(index)
						.getVariable();

				// translate the phi to an assign
				Use localUse = new Use(source);
				VarExpr expr = new VarExpr(localUse);
				Assign assign = new Assign(new Location(), target, expr);

				it.set(assign);

				// remove the other variable
				LocalVariable local = (LocalVariable) phi.getVars()
						.get(1 - index).getVariable();
				procedure.getLocals().remove(local);
			}
		}
	}

	@Override
	public void transform(Actor actor) {
		// remove dead ifs and whiles
		super.transform(actor);

		// combines adjacent blocks that may have been created
		new BlockCombine().transform(actor);
	}

	@Override
	@SuppressWarnings("unchecked")
	public void visit(IfNode node, Object... args) {
		ListIterator<CFGNode> it = (ListIterator<CFGNode>) args[0];
		Expression condition = node.getValue();
		if (condition.isBooleanExpr()) {
			if (((BoolExpr) condition).getValue()) {
				addNodes(it, node.getThenNodes(), node.getJoinNode(), 0);
			} else {
				addNodes(it, node.getElseNodes(), node.getJoinNode(), 1);
			}
		}
	}

}
