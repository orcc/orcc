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
package net.sf.orcc.ir.transforms;

import java.util.Iterator;
import java.util.List;

import net.sf.orcc.ir.Actor;
import net.sf.orcc.ir.Instruction;
import net.sf.orcc.ir.StateVariable;
import net.sf.orcc.ir.Use;
import net.sf.orcc.ir.Variable;
import net.sf.orcc.ir.instructions.Store;
import net.sf.orcc.util.OrderedMap;

/**
 * This class defines a very simple Dead Global Elimination.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class DeadGlobalElimination extends AbstractActorTransformation {

	/**
	 * Removes the given instructions that store to an unused state variable.
	 * 
	 * @param instructions
	 *            a list of instructions
	 */
	private void remove(List<Instruction> instructions) {
		if (instructions != null) {
			Iterator<Instruction> it = instructions.iterator();
			while (it.hasNext()) {
				Instruction instruction = it.next();
				instruction.accept(this);
				it.remove();

				instruction.getBlock().getInstructions().remove(instruction);
			}
		}
	}

	@Override
	public void transform(Actor actor) {
		OrderedMap<String, Variable> stateVariables = actor.getStateVars();
		Iterator<Variable> it = stateVariables.iterator();
		while (it.hasNext()) {
			StateVariable variable = (StateVariable) it.next();
			if (!variable.isUsed()) {
				it.remove();
				remove(variable.getInstructions());
			}
		}
	}

	@Override
	public void visit(Store store, Object... args) {
		// clean up uses
		Use.removeUses(store, store.getIndexes());
		store.setValue(null);
	}

}
