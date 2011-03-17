/*
 * Copyright (c) 2010-2011, IRISA
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
 *   * Neither the name of the IRISA nor the names of its
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.orcc.ir.AbstractActorVisitor;
import net.sf.orcc.ir.Action;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.Instruction;
import net.sf.orcc.ir.LocalVariable;
import net.sf.orcc.ir.Pattern;
import net.sf.orcc.ir.Port;
import net.sf.orcc.ir.Use;
import net.sf.orcc.ir.Variable;
import net.sf.orcc.ir.expr.VarExpr;
import net.sf.orcc.ir.instructions.Assign;
import net.sf.orcc.ir.instructions.Load;
import net.sf.orcc.ir.instructions.Store;

/**
 * This class defines an actor transformation that replace list of one element
 * used to stock input/output value by a scalar
 * 
 * @author Herve Yviquel
 * 
 */
public class UnaryListToScalarTransformation extends AbstractActorVisitor {

	private List<Instruction> toBeRemoved;
	private Map<Instruction, Assign> toBeAdded;

	@Override
	public void visit(Action action) {
		toBeRemoved = new ArrayList<Instruction>();
		toBeAdded = new HashMap<Instruction, Assign>();
		super.visit(action);
	}

	@Override
	public void visit(Pattern pattern) {
		for (Port port : pattern.getNumTokensMap().keySet()) {
			if (pattern.getNumTokens(port) == 1) {
				Variable oldTarget = pattern.getVariableMap().get(port);
				Instruction instruction = oldTarget.getInstruction();
				if (instruction.isLoad()) {
					Load load = (Load) instruction;
					Variable newTarget = load.getTarget();

					pattern.remove(port);
					pattern.setVariable(port, newTarget);

					// clean up uses
					load.setTarget(null);
					load.setSource(null);

					// remove instruction
					toBeRemoved.add(instruction);
					procedure.getLocals().remove(oldTarget.getName());
				} else if (instruction.isStore()) {
					Store store = (Store) instruction;
					Expression expr = store.getValue();

					Variable newTarget;

					if (expr.isVarExpr()) {
						VarExpr var = (VarExpr) expr;
						newTarget = var.getVar().getVariable();

					} else {
						LocalVariable localNewTarget = procedure
								.newTempLocalVariable(null, expr.getType(),
										"scalar_" + oldTarget.getName());
						localNewTarget.setAssignable(true);
						localNewTarget.setIndex(1);

						Assign assign = new Assign(localNewTarget, expr);
						Use.addUses(assign, expr);

						newTarget = localNewTarget;

						toBeAdded.put(instruction, assign);
					}

					pattern.remove(port);
					pattern.setVariable(port, newTarget);

					// remove instruction
					toBeRemoved.add(instruction);
					procedure.getLocals().remove(oldTarget.getName());

					// clean up uses
					store.setTarget(null);
					store.setValue(null);
				}
				oldTarget = pattern.getPeeked(port);
				if (oldTarget != null) {
					instruction = oldTarget.getInstruction();
					if (instruction.isLoad()) {
						Load load = (Load) instruction;
						Variable newTarget = load.getTarget();

						pattern.remove(port);
						pattern.setVariable(port, newTarget);

						// clean up uses
						load.setTarget(null);
						load.setSource(null);

						// remove instruction
						itInstruction.remove();
						procedure.getLocals().remove(oldTarget.getName());
					}
				}
			}
		}
	}
}
