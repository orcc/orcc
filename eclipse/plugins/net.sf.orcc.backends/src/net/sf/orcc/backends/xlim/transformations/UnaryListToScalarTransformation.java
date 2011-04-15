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

import net.sf.orcc.ir.ExprVar;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.InstAssign;
import net.sf.orcc.ir.InstLoad;
import net.sf.orcc.ir.InstStore;
import net.sf.orcc.ir.Instruction;
import net.sf.orcc.ir.IrFactory;
import net.sf.orcc.ir.Pattern;
import net.sf.orcc.ir.Port;
import net.sf.orcc.ir.TypeList;
import net.sf.orcc.ir.Var;
import net.sf.orcc.ir.util.AbstractActorVisitor;
import net.sf.orcc.ir.util.EcoreHelper;

/**
 * This class defines an actor transformation that replace list of one element
 * used to stock input/output value by a scalar
 * 
 * @author Herve Yviquel
 * 
 */
public class UnaryListToScalarTransformation extends AbstractActorVisitor<Object> {

	private Map<Instruction, InstAssign> toBeAdded;
	private List<Instruction> toBeRemoved;

	public UnaryListToScalarTransformation() {
		toBeRemoved = new ArrayList<Instruction>();
		toBeAdded = new HashMap<Instruction, InstAssign>();
	}

	@Override
	public void visit(InstLoad load) {
		if (toBeRemoved.remove(load)) {
			itInstruction.remove();
		}
	}

	@Override
	public void visit(Pattern pattern) {
		List<Port> ports = new ArrayList<Port>(pattern.getPorts());
		for (Port port : ports) {
			if (pattern.getNumTokens(port) == 1) {
				Var oldTarget = pattern.getVariable(port);
				if (!oldTarget.getUses().isEmpty() && oldTarget.getUses().get(0).eContainer() != null) {
					Instruction instruction = EcoreHelper.getContainerOfType(
							oldTarget.getUses().get(0), Instruction.class);

					if (instruction.isLoad()) {
						InstLoad load = (InstLoad) instruction;
						Var newTarget = load.getTarget().getVariable();

						pattern.setVariable(port, newTarget);

						// clean up uses
						load.setTarget(null);
						load.setSource(null);

						// remove instruction
						toBeRemoved.add(instruction);
						action.getBody().getLocals()
								.remove(oldTarget.getName());
					}
				} else {
					if (!oldTarget.getDefs().isEmpty()) {
						Instruction instruction = EcoreHelper
								.getContainerOfType(oldTarget.getDefs().get(0),
										Instruction.class);
						if (instruction.isStore()) {
							InstStore store = (InstStore) instruction;
							Expression expr = store.getValue();

							Var newTarget;

							if (expr.isVarExpr()) {
								ExprVar var = (ExprVar) expr;
								newTarget = var.getUse().getVariable();

							} else {
								Var localNewTarget = action
										.getBody()
										.newTempLocalVariable(expr.getType(),
												"scalar_" + oldTarget.getName());
								localNewTarget.setAssignable(true);
								localNewTarget.setIndex(1);

								InstAssign assign = IrFactory.eINSTANCE
										.createInstAssign(localNewTarget, expr);

								newTarget = localNewTarget;

								toBeAdded.put(instruction, assign);
							}

							pattern.setVariable(port, newTarget);

							// remove instruction
							toBeRemoved.add(instruction);
							action.getBody().getLocals()
									.remove(oldTarget.getName());

							// clean up uses
							store.setTarget(null);
							store.setValue(null);
						}
					} else {
						Var localNewTarget = action
								.getBody()
								.newTempLocalVariable(
										((TypeList) oldTarget.getType())
												.getElementType(),
										"scalar_" + oldTarget.getName());
						localNewTarget.setAssignable(true);
						localNewTarget.setIndex(1);

						pattern.setVariable(port, localNewTarget);

						// remove instruction
						action.getBody().getLocals()
								.remove(oldTarget.getName());
					}
				}
			}
		}
	}

	@Override
	public void visit(InstStore store) {
		if (toBeRemoved.remove(store)) {
			itInstruction.remove();
			if (toBeAdded.containsKey(store)) {
				itInstruction.add(toBeAdded.get(store));
				toBeAdded.remove(store);
			}
		}
	}
}
