/*
 * Copyright (c) 2011, EPFL
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
 *   * Neither the name of the EPFL nor the names of its
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
package net.sf.orcc.backends.transform;

import java.util.HashMap;
import java.util.Map;

import net.sf.orcc.df.Actor;
import net.sf.orcc.df.util.DfVisitor;
import net.sf.orcc.ir.InstLoad;
import net.sf.orcc.ir.IrFactory;
import net.sf.orcc.ir.Type;
import net.sf.orcc.ir.Var;
import net.sf.orcc.ir.util.AbstractIrVisitor;

/**
 * This class defines a transformation that replaces actor parameters by a
 * global variables. actor.
 * 
 * @author Thavot Richard
 * @version 1.1
 */
public class ParameterImporter extends DfVisitor<Void> {

	private Map<Var, Var> paramToGlobalVarMap;

	public ParameterImporter() {
		this.irVisitor = new InnerIrVisitor();
		this.paramToGlobalVarMap = new HashMap<Var, Var>();
	}

	private class InnerIrVisitor extends AbstractIrVisitor<Void> {
		/**
		 * Overwrite each load source that calls an actor parameter
		 */
		@Override
		public Void caseInstLoad(InstLoad load) {
			Var loadedVar = load.getSource().getVariable();
			if (paramToGlobalVarMap.containsKey(loadedVar)) {
				load.getSource()
						.setVariable(paramToGlobalVarMap.get(loadedVar));
			}
			return null;
		}
	}

	/**
	 * Visit an actor for replacing actor parameters by a global variables.
	 */
	@Override
	public Void caseActor(Actor actor) {
		this.actor = actor;

		for (Var parameter : actor.getParameters()) {
			Var paramVar = newGlobalVariable(parameter.getType(), "param_"
					+ parameter.getName());
			paramVar.setInitialValue(IrFactory.eINSTANCE
					.createExprVar(parameter));
			paramToGlobalVarMap.put(parameter, paramVar);
		}
		return super.caseActor(actor);
	}

	/**
	 * Create a new unassignable global variable.
	 * 
	 * @param type
	 * @param hint
	 * @return
	 */
	private Var newGlobalVariable(Type type, String hint) {
		String name = hint;
		Var variable = actor.getStateVar(name);
		int i = 0;
		while (variable != null) {
			name = hint + i;
			variable = actor.getStateVar(name);
			i++;
		}

		variable = IrFactory.eINSTANCE.createVar(0, type, name, false, 0);
		variable.setGlobal(true);
		actor.getStateVars().add(variable);
		return variable;
	}

}
