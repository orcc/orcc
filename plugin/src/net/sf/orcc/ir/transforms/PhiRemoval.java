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

import net.sf.orcc.ir.INode;
import net.sf.orcc.ir.LocalVariable;
import net.sf.orcc.ir.Location;
import net.sf.orcc.ir.Use;
import net.sf.orcc.ir.Variable;
import net.sf.orcc.ir.expr.IntExpr;
import net.sf.orcc.ir.expr.VarExpr;
import net.sf.orcc.ir.nodes.AssignVarNode;
import net.sf.orcc.ir.nodes.IfNode;
import net.sf.orcc.ir.nodes.JoinNode;
import net.sf.orcc.ir.nodes.PhiAssignment;
import net.sf.orcc.ir.nodes.WhileNode;
import net.sf.orcc.util.OrderedMap;

/**
 * Removes phi assignments and translates them to copies.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class PhiRemoval extends AbstractActorTransformation {

	@Override
	public void visit(IfNode node, Object... args) {
		List<INode> nodes = node.getThenNodes();
		ListIterator<INode> it = nodes.listIterator(nodes.size());
		node.getJoinNode().accept(this, it, 0);

		nodes = node.getElseNodes();
		it = nodes.listIterator(nodes.size());
		node.getJoinNode().accept(this, it, 1);
		node.getJoinNode().getPhiAssignments().clear();

		visitNodes(node.getThenNodes());
		visitNodes(node.getElseNodes());
	}

	@Override
	@SuppressWarnings("unchecked")
	public void visit(JoinNode node, Object... args) {
		List<PhiAssignment> phis = node.getPhiAssignments();
		if (!phis.isEmpty()) {
			ListIterator<INode> it = (ListIterator<INode>) args[0];
			int phiIndex = (Integer) args[1];

			for (PhiAssignment phi : phis) {
				LocalVariable target = phi.getTarget();
				LocalVariable source = (LocalVariable) phi.getVars().get(
						phiIndex).getVariable();

				// if source is a local variable with index = 0, we remove it
				// from the procedure and translate the PHI by an assignment of
				// 0 (zero) to target.
				// Otherwise, we just create an assignment target = source.
				OrderedMap<Variable> parameters = procedure.getParameters();
				AssignVarNode assign;
				if (source.getIndex() == 0 && !parameters.contains(source)) {
					procedure.getLocals().remove(source);
					IntExpr expr = new IntExpr(new Location(), 0);
					assign = new AssignVarNode(0, new Location(), target, expr);
				} else {
					Use localUse = new Use(source);
					VarExpr expr = new VarExpr(new Location(), localUse);
					assign = new AssignVarNode(0, new Location(), target, expr);
				}

				it.add(assign);
			}
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public void visit(WhileNode node, Object... args) {
		ListIterator<INode> it = (ListIterator<INode>) args[0];

		// the node before the while.
		it.previous();
		node.getJoinNode().accept(this, it, 0);

		// go back to the while
		it.next();

		it = node.getNodes().listIterator(node.getNodes().size());
		node.getJoinNode().accept(this, it, 1);
		node.getJoinNode().getPhiAssignments().clear();
		visitNodes(node.getNodes());
	}

}
