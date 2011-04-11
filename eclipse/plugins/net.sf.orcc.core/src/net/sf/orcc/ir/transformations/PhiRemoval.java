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

import java.util.ArrayList;
import java.util.List;

import net.sf.orcc.ir.ExprVar;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.InstAssign;
import net.sf.orcc.ir.InstPhi;
import net.sf.orcc.ir.Instruction;
import net.sf.orcc.ir.IrFactory;
import net.sf.orcc.ir.IrPackage;
import net.sf.orcc.ir.Node;
import net.sf.orcc.ir.NodeBlock;
import net.sf.orcc.ir.NodeIf;
import net.sf.orcc.ir.NodeWhile;
import net.sf.orcc.ir.Procedure;
import net.sf.orcc.ir.Var;
import net.sf.orcc.ir.util.AbstractActorVisitor;
import net.sf.orcc.ir.util.EcoreHelper;

/**
 * This class removes phi assignments and transforms them to copies.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class PhiRemoval extends AbstractActorVisitor {

	private class PhiRemover extends AbstractActorVisitor {
		
		@Override
		public void visit(NodeBlock block) {
			for (int i = 0; i < block.getInstructions().size();) {
				Instruction instruction = block.getInstructions().get(i);
				if (instruction instanceof InstPhi) {
					EcoreHelper.delete(instruction);
				} else {
					i++;
				}
			}
		}

	}

	private List<Var> localsToRemove;

	private int phiIndex;

	private NodeBlock targetBlock;

	@Override
	public void visit(InstPhi phi) {
		Var target = phi.getTarget().getVariable();
		ExprVar sourceExpr = (ExprVar) phi.getValues().get(phiIndex);
		Var source = sourceExpr.getUse().getVariable();

		// if source is a local variable with index = 0, we remove it from the
		// procedure and translate the PHI by an assignment of 0 (zero) to
		// target. Otherwise, we just create an assignment target = source.
		Expression expr;
		if (source.getIndex() == 0
				&& source.eContainmentFeature() != IrPackage.eINSTANCE
						.getProcedure_Parameters()) {
			localsToRemove.add(source);
			if (target.getType().isBool()) {
				expr = IrFactory.eINSTANCE.createExprBool(false);
			} else {
				expr = IrFactory.eINSTANCE.createExprInt(0);
			}
		} else {
			expr = IrFactory.eINSTANCE.createExprVar(source);
		}

		InstAssign assign = IrFactory.eINSTANCE.createInstAssign(target, expr);
		targetBlock.add(assign);
	}

	@Override
	public void visit(NodeIf node) {
		NodeBlock join = node.getJoinNode();
		targetBlock = procedure.getLast(node.getThenNodes());
		phiIndex = 0;
		join.accept(this);

		targetBlock = procedure.getLast(node.getElseNodes());
		phiIndex = 1;
		join.accept(this);
		join.accept(new PhiRemover());

		visit(node.getThenNodes());
		visit(node.getElseNodes());
	}

	@Override
	public void visit(NodeWhile node) {
		// the node before the while.
		if (itNode.hasPrevious()) {
			Node previousNode = itNode.previous();
			if (previousNode.isBlockNode()) {
				targetBlock = (NodeBlock) previousNode;
			} else {
				targetBlock = IrFactory.eINSTANCE.createNodeBlock();
				itNode.add(targetBlock);
			}
		} else {
			targetBlock = IrFactory.eINSTANCE.createNodeBlock();
			itNode.add(targetBlock);
		}

		NodeBlock join = node.getJoinNode();
		phiIndex = 0;
		join.accept(this);

		// go back to the while
		itNode.next();

		// last node of the while
		targetBlock = procedure.getLast(node.getNodes());
		phiIndex = 1;
		join.accept(this);
		join.accept(new PhiRemover());
		visit(node.getNodes());
	}

	@Override
	public void visit(Procedure procedure) {
		localsToRemove = new ArrayList<Var>();

		super.visit(procedure);

		for (Var local : localsToRemove) {
			procedure.getLocals().remove(local);
		}
	}

}
