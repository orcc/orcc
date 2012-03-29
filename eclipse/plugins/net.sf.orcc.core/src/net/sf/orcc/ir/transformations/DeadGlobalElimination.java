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

import java.util.Iterator;
import java.util.List;

import net.sf.dftools.util.util.EcoreHelper;
import net.sf.orcc.df.Actor;
import net.sf.orcc.ir.Def;
import net.sf.orcc.ir.Instruction;
import net.sf.orcc.ir.Use;
import net.sf.orcc.ir.Var;
import net.sf.orcc.ir.util.AbstractActorVisitor;
import net.sf.orcc.ir.util.IrUtil;

/**
 * This class defines a very simple Dead Global Elimination.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class DeadGlobalElimination extends AbstractActorVisitor<Object> {

	/**
	 * Removes the instructions that define an unused state variable.
	 * 
	 * @param definitions
	 *            a list of definitions
	 */
	private void remove(Var variable) {
		List<Def> definitions = variable.getDefs();
		while (!definitions.isEmpty()) {
			Def def = definitions.get(0);
			Instruction instruction = EcoreHelper.getContainerOfType(def,
					Instruction.class);
			IrUtil.delete(instruction);
		}

		List<Use> uses = variable.getUses();
		while (!uses.isEmpty()) {
			Use use = uses.get(0);
			Instruction instruction = EcoreHelper.getContainerOfType(use,
					Instruction.class);
			IrUtil.delete(instruction);
		}
	}

	@Override
	public Object caseActor(Actor actor) {
		List<Var> stateVariables = actor.getStateVars();
		Iterator<Var> it = stateVariables.iterator();
		while (it.hasNext()) {
			Var variable = it.next();
			if (!variable.isUsed()) {
				remove(variable);
				it.remove();
			}
		}
		return null;
	}

}
