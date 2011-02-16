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
package net.sf.orcc.ir.transformations;

import java.util.ListIterator;

import net.sf.orcc.ir.AbstractActorVisitor;
import net.sf.orcc.ir.CFGNode;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.Instruction;
import net.sf.orcc.ir.LocalVariable;
import net.sf.orcc.ir.Location;
import net.sf.orcc.ir.Use;
import net.sf.orcc.ir.expr.BoolExpr;
import net.sf.orcc.ir.expr.IntExpr;
import net.sf.orcc.ir.expr.VarExpr;
import net.sf.orcc.ir.instructions.AbstractInstructionVisitor;
import net.sf.orcc.ir.instructions.Assign;
import net.sf.orcc.ir.instructions.PhiAssignment;
import net.sf.orcc.ir.instructions.SpecificInstruction;
import net.sf.orcc.ir.nodes.BlockNode;
import net.sf.orcc.ir.nodes.IfNode;
import net.sf.orcc.ir.nodes.WhileNode;
import net.sf.orcc.util.OrderedMap;

/**
 * This class removes phi assignments and transforms them to copies.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class PhiRemoval extends AbstractActorVisitor {

	private class PhiRemover extends AbstractInstructionVisitor {

		@Override
		public void visit(PhiAssignment instruction) {
			itInstruction.remove();
		}

		@Override
		public void visit(SpecificInstruction node) {
			// nothing to do here
		}

	}

	private void removePhis(BlockNode join) {
		ListIterator<Instruction> it = join.listIterator();
		while (it.hasNext()) {
			itInstruction = it;
			it.next().accept(new PhiRemover());
		}
	}

	private BlockNode targetBlock;

	private int phiIndex;

	@Override
	public void visit(IfNode node) {
		BlockNode join = node.getJoinNode();
		targetBlock = BlockNode.getLast(procedure, node.getThenNodes());
		phiIndex = 0;
		join.accept(this);

		targetBlock = BlockNode.getLast(procedure, node.getElseNodes());
		phiIndex = 1;
		join.accept(this);
		removePhis(join);

		visit(node.getThenNodes());
		visit(node.getElseNodes());
	}

	@Override
	public void visit(PhiAssignment phi) {
		LocalVariable target = phi.getTarget();
		VarExpr sourceExpr = (VarExpr) phi.getValues().get(phiIndex);
		LocalVariable source = (LocalVariable) sourceExpr.getVar()
				.getVariable();

		// if source is a local variable with index = 0, we remove it from the
		// procedure and translate the PHI by an assignment of 0 (zero) to
		// target. Otherwise, we just create an assignment target = source.
		OrderedMap<String, LocalVariable> parameters = procedure.getParameters();
		Assign assign;
		if (source.getIndex() == 0 && !parameters.contains(source.getName())) {
			procedure.getLocals().remove(source.getName());
			Expression expr;
			if (target.getType().isBool()) {
				expr = new BoolExpr(false);
			} else {
				expr = new IntExpr(0);
			}
			assign = new Assign(new Location(), target, expr);
		} else {
			Use localUse = new Use(source);
			VarExpr expr = new VarExpr(localUse);
			assign = new Assign(new Location(), target, expr);
		}

		targetBlock.add(assign);
	}

	@Override
	public void visit(WhileNode node) {
		// the node before the while.
		if (itNode.hasPrevious()) {
			CFGNode previousNode = itNode.previous();
			if (previousNode.isBlockNode()) {
				targetBlock = (BlockNode) previousNode;
			} else {
				targetBlock = new BlockNode(procedure);
				itNode.add(targetBlock);
			}
		} else {
			targetBlock = new BlockNode(procedure);
			itNode.add(targetBlock);
		}

		BlockNode join = node.getJoinNode();
		phiIndex = 0;
		join.accept(this);

		// go back to the while
		itNode.next();

		// last node of the while
		targetBlock = BlockNode.getLast(procedure, node.getNodes());
		phiIndex = 1;
		join.accept(this);
		removePhis(join);
		visit(node.getNodes());
	}

}
