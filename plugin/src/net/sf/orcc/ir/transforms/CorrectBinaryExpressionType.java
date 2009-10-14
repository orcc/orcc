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

import net.sf.orcc.OrccException;
import net.sf.orcc.ir.actor.Action;
import net.sf.orcc.ir.actor.Actor;
import net.sf.orcc.ir.actor.Procedure;
import net.sf.orcc.ir.expr.BinaryExpr;
import net.sf.orcc.ir.expr.BinaryOp;
import net.sf.orcc.ir.expr.IExpr;
import net.sf.orcc.ir.expr.Util;
import net.sf.orcc.ir.expr.VarExpr;
import net.sf.orcc.ir.nodes.AbstractNode;
import net.sf.orcc.ir.nodes.AbstractNodeVisitor;
import net.sf.orcc.ir.nodes.AssignVarNode;
import net.sf.orcc.ir.nodes.IfNode;
import net.sf.orcc.ir.nodes.WhileNode;
import net.sf.orcc.ir.type.BoolType;
import net.sf.orcc.ir.type.IType;
import net.sf.orcc.ir.type.IntType;

/**
 * Split expression and effective node.
 * 
 * @author Jérôme GORIN
 * 
 */
public class CorrectBinaryExpressionType extends AbstractNodeVisitor {

	public CorrectBinaryExpressionType(Actor actor) {
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

	public IType checkType(BinaryExpr expr, Object... args) {
		IType type;

		if ((expr.getOp() == BinaryOp.EQ) || (expr.getOp() == BinaryOp.GE)
				|| (expr.getOp() == BinaryOp.GT)
				|| (expr.getOp() == BinaryOp.LE)
				|| (expr.getOp() == BinaryOp.LT)
				|| (expr.getOp() == BinaryOp.NE)) {
			type = new BoolType();
		} else if ((expr.getE1().getType() == IExpr.VAR)
				&& (expr.getE2().getType() == IExpr.VAR)) {
			IType typeE1 = ((VarExpr) expr.getE1()).getVar().getVarDef()
					.getType();
			IType typeE2 = ((VarExpr) expr.getE1()).getVar().getVarDef()
					.getType();

			if (sizeOf(typeE1) > sizeOf(typeE2)) {
				type = typeE1;
			} else {
				type = typeE2;
			}

		} else if (expr.getE1().getType() == IExpr.VAR) {
			type = ((VarExpr) expr.getE1()).getVar().getVarDef().getType();
		} else if (expr.getE2().getType() == IExpr.VAR) {
			type = ((VarExpr) expr.getE2()).getVar().getVarDef().getType();
		} else {
			type = expr.getUnderlyingType();
		}

		return type;
	}

	private int sizeOf(IType type) {
		int size = 0;

		if (type.getType() == IType.INT) {
			try {
				return Util.evaluateAsInteger(((IntType) type).getSize());
			} catch (OrccException e) {
				e.printStackTrace();
				return 32;
			}
		} else if (type.getType() == IType.BOOLEAN) {
			size = 1;
		}

		return size;
	}

	@Override
	public void visit(AssignVarNode node, Object... args) {
		IExpr value = node.getValue();

		if (value.getType() == IExpr.BINARY) {
			BinaryExpr expr = (BinaryExpr) value;
			IType type = checkType(expr);
			expr.setType(type);
			node.getVar().setType(type);
		}
	}

	@Override
	public void visit(IfNode node, Object... args) {
		visitNodes(node.getThenNodes());
		visitNodes(node.getElseNodes());
	}

	@Override
	public void visit(WhileNode node, Object... args) {
		visitNodes(node.getNodes());
	}

	private void visitNodes(List<AbstractNode> nodes) {
		ListIterator<AbstractNode> it = nodes.listIterator();

		while (it.hasNext()) {
			it.next().accept(this, it);
		}
	}

	private void visitProc(Procedure proc) {
		List<AbstractNode> nodes = proc.getNodes();
		visitNodes(nodes);
	}
}
