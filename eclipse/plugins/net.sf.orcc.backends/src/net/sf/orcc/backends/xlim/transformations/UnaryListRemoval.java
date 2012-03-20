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
import java.util.List;

import net.sf.orcc.df.Action;
import net.sf.orcc.df.Pattern;
import net.sf.orcc.df.Port;
import net.sf.orcc.ir.InstAssign;
import net.sf.orcc.ir.InstLoad;
import net.sf.orcc.ir.InstStore;
import net.sf.orcc.ir.IrFactory;
import net.sf.orcc.ir.Procedure;
import net.sf.orcc.ir.TypeList;
import net.sf.orcc.ir.Var;
import net.sf.orcc.ir.util.AbstractActorVisitor;
import net.sf.orcc.ir.util.IrUtil;
import net.sf.orcc.util.EcoreHelper;

import org.eclipse.emf.ecore.util.EcoreUtil;

/**
 * This class defines an actor transformation that replace list of one element
 * used to stock input/output value by a scalar
 * 
 * @author Herve Yviquel
 * 
 */
public class UnaryListRemoval extends AbstractActorVisitor<Object> {

	@Override
	public Object caseAction(Action action) {
		doSwitch(action.getInputPattern());
		doSwitch(action.getOutputPattern());

		return null;
	}

	@Override
	public Object casePattern(Pattern pattern) {
		List<Port> ports = new ArrayList<Port>(pattern.getPorts());
		for (Port port : ports) {
			if (pattern.getNumTokens(port) == 1) {
				Var oldTarget = pattern.getVariable(port);
				Var newTarget;

				Procedure procedure = EcoreHelper.getContainerOfType(pattern,
						Action.class).getBody();

				if (!oldTarget.getUses().isEmpty()) {
					// First case: an input variable
					InstLoad load = EcoreHelper.getContainerOfType(oldTarget
							.getUses().get(0), InstLoad.class);
					newTarget = load.getTarget().getVariable();

					IrUtil.delete(load);
				} else {
					// Second case: an output variable or an input swallower
					// i.e. an input variable which just consumes tokens
					newTarget = createScalarVariable(oldTarget, procedure);
					while (!oldTarget.getDefs().isEmpty()) {
						InstStore store = EcoreHelper.getContainerOfType(
								oldTarget.getDefs().get(0), InstStore.class);
						InstAssign assign = IrFactory.eINSTANCE
								.createInstAssign(newTarget, store.getValue());
						EcoreUtil.replace(store, assign);
						IrUtil.delete(store);
					}

				}
				// Replace variable in pattern
				pattern.setVariable(port, newTarget);
				// Remove useless variable
				EcoreUtil.remove(oldTarget);
			}
		}
		return null;
	}

	private Var createScalarVariable(Var listVar, Procedure procedure) {
		Var scalarVar = procedure.newTempLocalVariable(
				((TypeList) listVar.getType()).getInnermostType(), "scalar_"
						+ listVar.getName());
		scalarVar.setAssignable(true);
		return scalarVar;
	}
}
