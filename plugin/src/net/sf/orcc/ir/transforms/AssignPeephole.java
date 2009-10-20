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

import net.sf.orcc.common.LocalVariable;
import net.sf.orcc.common.Location;
import net.sf.orcc.ir.expr.IExpr;
import net.sf.orcc.ir.expr.IntExpr;
import net.sf.orcc.ir.expr.VarExpr;
import net.sf.orcc.ir.nodes.AbstractNode;
import net.sf.orcc.ir.nodes.AssignVarNode;
import net.sf.orcc.ir.nodes.IfNode;
import net.sf.orcc.ir.nodes.JoinNode;
import net.sf.orcc.ir.nodes.PhiAssignment;
import net.sf.orcc.ir.nodes.WhileNode;

/**
 * Move writes to the beginning of an action (because we use pointers).
 * 
 * @author Jérôme Gorin
 * @author Matthieu Wipliez
 * 
 */
public class AssignPeephole extends AbstractActorTransformation {

	@Override
	@SuppressWarnings("unchecked")
	public void visit(AssignVarNode node, Object... args) {
		ListIterator<AbstractNode> it = (ListIterator<AbstractNode>) args[0];
		if ((node.getValue().getType() == IExpr.BOOLEAN)
				|| (node.getValue().getType() == IExpr.INT)
				|| (node.getValue().getType() == IExpr.STRING)) {
			LocalVariable vardef = node.getVar();
			vardef.setConstant(node.getValue());
			it.remove();
		} else if (node.getValue().getType() == IExpr.VAR) {
			LocalVariable vardef = node.getVar();
			VarExpr expr = (VarExpr) node.getValue();

			// we can safely cast because in a VarExpr in an actor, only local
			// variables are used (globals must be load'ed first)
			vardef.duplicate((LocalVariable) expr.getVar().getVariable());
			it.remove();
		}
	}

	@Override
	public void visit(IfNode node, Object... args) {
		visitNodes(node.getThenNodes());
		visitNodes(node.getElseNodes());
		visit(node.getJoinNode(), args);
	}

	@Override
	public void visit(JoinNode node, Object... args) {
		List<PhiAssignment> phis = node.getPhis();
		if (!phis.isEmpty()) {
			for (PhiAssignment phi : phis) {
				LocalVariable source = phi.getVars().get(0).getLocalVariable();
				// if source is a local variable with index = 0, we remove it
				// from the procedure and translate the PHI by an assignment of
				// 0 (zero) to target.
				// Otherwise, we just create an assignment target = source.
				List<LocalVariable> parameters = procedure.getParameters();
				if (source.getIndex() == 0 && !parameters.contains(source)) {
					IntExpr expr = new IntExpr(new Location(), 0);
					source.setConstant(expr);
				}
			}
		}
	}

	@Override
	public void visit(WhileNode node, Object... args) {
		visitNodes(node.getNodes());
		visit(node.getJoinNode(), args);
	}

}
