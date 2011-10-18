/*
 * Copyright (c) 2011, Åbo Akademi University
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
 *   * Neither the name of the Åbo Akademi University nor the names of its
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

package net.sf.orcc.backends.promela.transformations;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import net.sf.orcc.ir.Action;
import net.sf.orcc.ir.Actor;
import net.sf.orcc.ir.Def;
import net.sf.orcc.ir.Instruction;
import net.sf.orcc.ir.Port;
import net.sf.orcc.ir.Use;
import net.sf.orcc.ir.Var;
import net.sf.orcc.ir.transformations.DeadGlobalElimination;
import net.sf.orcc.ir.util.IrUtil;
import net.sf.orcc.util.EcoreHelper;

/**
 * This class removes state variables not needed for scheduling, 
 * 
 * @author Johan Ersfolk
 * 
 */
public class PromelaDeadGlobalElimination extends DeadGlobalElimination {

	Set<Var> usedVars;
	Set<Port> usedPorts;
	
	public PromelaDeadGlobalElimination(Set<Var> usedVars, Set<Port> usedPorts) {
		super();
		this.usedVars = usedVars;
		this.usedPorts = usedPorts;
	}

	@Override
	public Object caseActor(Actor actor) {
		List<Var> stateVariables = actor.getStateVars();
		Iterator<Var> it = stateVariables.iterator();
		while (it.hasNext()) {
			Var variable = it.next();
			if (!usedVars.contains(variable)) {
				remove(variable);
				it.remove();
			}
		}
		for (Action action : actor.getActions()) {
			Iterator<Port> itp = action.getOutputPattern().getPorts().iterator();
			while (itp.hasNext()) {
				Port port = itp.next();
				if (!usedPorts.contains(port)) {
					remove(action.getOutputPattern().getPortToVarMap().get(port));
				}
			}
			itp = action.getInputPattern().getPorts().iterator();
			while (itp.hasNext()) {
				Port port = itp.next();
				if (!usedPorts.contains(port)) {
					remove(action.getInputPattern().getPortToVarMap().get(port));
				}
			}
		}
		return null;
	}

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

}
