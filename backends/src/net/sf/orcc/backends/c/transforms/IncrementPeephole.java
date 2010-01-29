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

import java.util.ListIterator;

import net.sf.orcc.backends.c.instructions.AbstractCInstruction;
import net.sf.orcc.backends.c.instructions.Decrement;
import net.sf.orcc.backends.c.instructions.Increment;
import net.sf.orcc.backends.c.instructions.SelfAssignment;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.Instruction;
import net.sf.orcc.ir.LocalVariable;
import net.sf.orcc.ir.Location;
import net.sf.orcc.ir.Variable;
import net.sf.orcc.ir.expr.BinaryExpr;
import net.sf.orcc.ir.expr.BinaryOp;
import net.sf.orcc.ir.expr.IntExpr;
import net.sf.orcc.ir.expr.VarExpr;
import net.sf.orcc.ir.instructions.Load;
import net.sf.orcc.ir.instructions.Store;
import net.sf.orcc.ir.nodes.BlockNode;
import net.sf.orcc.ir.transforms.AbstractActorTransformation;
import net.sf.orcc.util.OrderedMap;

/**
 * Replaces nodes by specific C nodes where appropriate.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class IncrementPeephole extends AbstractActorTransformation {

	private BlockNode block;

	private void examine(ListIterator<Instruction> it) {
		Instruction node1 = it.next();
		if (it.hasNext()) {
			Instruction node2 = it.next();
			boolean res = false;

			if (node1 instanceof Load && node2 instanceof Store) {
				Load load = (Load) node1;
				Store store = (Store) node2;
				Variable varDef = load.getSource().getVariable();
				LocalVariable varDefTmp = load.getTarget();
				if (varDef == store.getTarget().getVariable()
						&& load.getIndexes().isEmpty()) {
					Expression expr = store.getValue();
					if (expr.getType() == Expression.BINARY) {
						BinaryExpr binExpr = (BinaryExpr) expr;
						BinaryOp op = binExpr.getOp();
						Expression e1 = binExpr.getE1();
						Expression e2 = binExpr.getE2();

						if (e1.getType() == Expression.VAR) {
							VarExpr v1 = (VarExpr) e1;
							if (v1.getVar().getVariable() == varDefTmp) {
								res = replaceSelfAssignment(procedure
										.getLocals(), it, varDefTmp, varDef,
										v1, op, e2);
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

	private boolean replaceSelfAssignment(OrderedMap<Variable> locals,
			ListIterator<Instruction> it, LocalVariable varDefTmp,
			Variable varDef, VarExpr v1, BinaryOp op, Expression e2) {
		AbstractCInstruction instruction;
		if (op == BinaryOp.PLUS && e2.getType() == Expression.INT
				&& ((IntExpr) e2).getValue() == 1) {
			instruction = new Increment(new Location(), varDef);
		} else if (op == BinaryOp.MINUS && e2.getType() == Expression.INT
				&& ((IntExpr) e2).getValue() == 1) {
			instruction = new Decrement(new Location(), varDef);
		} else if (op == BinaryOp.BITAND || op == BinaryOp.BITOR
				|| op == BinaryOp.BITXOR || op == BinaryOp.DIV
				|| op == BinaryOp.MINUS || op == BinaryOp.MOD
				|| op == BinaryOp.PLUS || op == BinaryOp.SHIFT_LEFT
				|| op == BinaryOp.SHIFT_RIGHT || op == BinaryOp.TIMES) {
			instruction = new SelfAssignment(new Location(), varDef, op, e2);
		} else {
			// nothing for us, just return
			return false;
		}

		it.previous();
		it.remove();
		it.previous();
		it.remove();
		instruction.setBlock(block);
		it.add(instruction);

		// if we recognized something we could change, remove the temp var
		locals.remove(varDefTmp);

		return true;
	}

	@Override
	public void visit(BlockNode node, Object... args) {
		block = node;
		ListIterator<Instruction> it = node.listIterator();
		while (it.hasNext()) {
			examine(it);
		}
	}

}
