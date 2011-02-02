/*
 * Copyright (c) 2010, IRISA
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
 *   * Neither the name of IRISA nor the names of its
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
package net.sf.orcc.backends.xlim.transformations;

import java.util.ListIterator;

import net.sf.orcc.backends.xlim.instructions.TernaryOperation;
import net.sf.orcc.ir.Actor;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.Instruction;
import net.sf.orcc.ir.IrFactory;
import net.sf.orcc.ir.LocalVariable;
import net.sf.orcc.ir.Procedure;
import net.sf.orcc.ir.Use;
import net.sf.orcc.ir.expr.VarExpr;
import net.sf.orcc.ir.instructions.Assign;
import net.sf.orcc.ir.instructions.PhiAssignment;
import net.sf.orcc.ir.nodes.BlockNode;
import net.sf.orcc.ir.nodes.IfNode;
import net.sf.orcc.ir.transformations.AbstractActorTransformation;

/**
 * 
 * This class defines a transformation that replace ifNode in function by our
 * ternary operation.
 * 
 * @author Herve Yviquel
 * 
 */
public class TernaryOperationAdder extends AbstractActorTransformation {

	private BlockNode newBlockNode;
	private LocalVariable condVar;

	@Override
	public void transform(Actor actor) {
		for (Procedure proc : actor.getProcs()) {
			if (!proc.getReturnType().isVoid()) {
				newBlockNode = new BlockNode(proc);
				visit(proc);
				proc.getNodes().clear();
				proc.getNodes().add(newBlockNode);
			}
		}
	}

	@Override
	public void visit(BlockNode blockNode) {
		ListIterator<Instruction> it = blockNode.listIterator();
		while (it.hasNext()) {
			Instruction instruction = it.next();
			instructionIterator = it;
			if (instruction.isPhi()) {
				PhiAssignment phi = (PhiAssignment) instruction;

				TernaryOperation ternaryOp = new TernaryOperation(null,
						phi.getTarget(), new VarExpr(new Use(condVar)), phi
								.getValues().get(0), phi.getValues().get(1));

				// add uses
				Use.addUses(ternaryOp, ternaryOp.getConditionValue());
				Use.addUses(ternaryOp, ternaryOp.getTrueValue());
				Use.addUses(ternaryOp, ternaryOp.getFalseValue());

				newBlockNode.add(ternaryOp);
			} else {
				newBlockNode.add(instruction);
			}
		}

	}

	@Override
	public void visit(IfNode ifNode) {
		LocalVariable oldCondVar = condVar;

		Expression condExpr = ifNode.getValue();
		condVar = procedure.newTempLocalVariable(null,
				IrFactory.eINSTANCE.createTypeBool(), "ifCondition_"
						+ ifNode.getLocation().getStartLine());
		condVar.setIndex(1);
		Assign assignCond = new Assign(condVar, condExpr);
		condVar.setInstruction(assignCond);
		newBlockNode.add(assignCond);

		// clean uses
		ifNode.setValue(null);
		Use.removeUses(ifNode, condExpr);

		// add uses
		Use.addUses(assignCond, condExpr);

		visit(ifNode.getThenNodes());
		visit(ifNode.getElseNodes());
		visit(ifNode.getJoinNode());
		condVar = oldCondVar;
	}
}
