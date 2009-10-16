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
package net.sf.orcc.backends.c.transforms;

import java.util.List;
import java.util.ListIterator;

import net.sf.orcc.backends.c.nodes.DecrementNode;
import net.sf.orcc.backends.c.nodes.IncrementNode;
import net.sf.orcc.backends.c.nodes.SelfAssignment;
import net.sf.orcc.common.Location;
import net.sf.orcc.common.LocalVariable;
import net.sf.orcc.ir.actor.Action;
import net.sf.orcc.ir.actor.Actor;
import net.sf.orcc.ir.actor.Procedure;
import net.sf.orcc.ir.expr.BinaryExpr;
import net.sf.orcc.ir.expr.BinaryOp;
import net.sf.orcc.ir.expr.IExpr;
import net.sf.orcc.ir.expr.IntExpr;
import net.sf.orcc.ir.expr.VarExpr;
import net.sf.orcc.ir.nodes.AbstractNode;
import net.sf.orcc.ir.nodes.LoadNode;
import net.sf.orcc.ir.nodes.StoreNode;
import net.sf.orcc.ir.transforms.Peephole;

/**
 * Replaces nodes by specific C nodes where appropriate.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class IncrementPeephole extends Peephole {

	public IncrementPeephole(Actor actor) {
		for (Procedure proc : actor.getProcs()) {
			visitNodes(proc);
		}

		for (Action action : actor.getActions()) {
			visitNodes(action.getBody());
			visitNodes(action.getScheduler());
		}

		for (Action action : actor.getInitializes()) {
			visitNodes(action.getBody());
			visitNodes(action.getScheduler());
		}
	}

	@Override
	public void examine(Procedure proc, ListIterator<AbstractNode> it) {
		AbstractNode node1 = it.next();
		if (it.hasNext()) {
			AbstractNode node2 = it.next();
			boolean res = false;

			if (node1 instanceof LoadNode && node2 instanceof StoreNode) {
				LoadNode load = (LoadNode) node1;
				StoreNode store = (StoreNode) node2;
				LocalVariable varDef = load.getSource().getVarDef();
				LocalVariable varDefTmp = load.getTarget();
				if (varDef == store.getTarget().getVarDef()
						&& load.getIndexes().isEmpty()) {
					IExpr expr = store.getValue();
					if (expr.getType() == IExpr.BINARY) {
						BinaryExpr binExpr = (BinaryExpr) expr;
						BinaryOp op = binExpr.getOp();
						IExpr e1 = binExpr.getE1();
						IExpr e2 = binExpr.getE2();

						if (e1.getType() == IExpr.VAR) {
							VarExpr v1 = (VarExpr) e1;
							if (v1.getVar().getVarDef() == varDefTmp) {
								res = replaceSelfAssignment(proc.getLocals(),
										it, varDefTmp, varDef, v1, op, e2);
							}
						}
					}
				}
			}

			// if res, nodes were replaced. if not res, just forget we have read
			// the second node, so we can try again later
			if (!res) {
				it.previous();
			}
		}
	}

	private boolean replaceSelfAssignment(List<LocalVariable> locals,
			ListIterator<AbstractNode> it, LocalVariable varDefTmp, LocalVariable varDef,
			VarExpr v1, BinaryOp op, IExpr e2) {
		AbstractNode node;
		if (op == BinaryOp.PLUS && e2.getType() == IExpr.INT
				&& ((IntExpr) e2).getValue() == 1) {
			node = new IncrementNode(0, new Location(), varDef);
		} else if (op == BinaryOp.MINUS && e2.getType() == IExpr.INT
				&& ((IntExpr) e2).getValue() == 1) {
			node = new DecrementNode(0, new Location(), varDef);
		} else if (op == BinaryOp.BAND || op == BinaryOp.BOR
				|| op == BinaryOp.BXOR || op == BinaryOp.DIV
				|| op == BinaryOp.MINUS || op == BinaryOp.MOD
				|| op == BinaryOp.PLUS || op == BinaryOp.SHIFT_LEFT
				|| op == BinaryOp.SHIFT_RIGHT || op == BinaryOp.TIMES) {
			node = new SelfAssignment(0, new Location(), varDef, op, e2);
		} else {
			// nothing for us, just return
			return false;
		}

		it.previous();
		it.remove();
		it.previous();
		it.remove();
		it.add(node);

		// if we recognized something we could change, remove the temp var
		locals.remove(varDefTmp);

		return true;
	}

}
