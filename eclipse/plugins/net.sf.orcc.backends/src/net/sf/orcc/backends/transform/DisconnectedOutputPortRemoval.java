/*
 * Copyright (c) 2010, IETR/INSA of Rennes
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
package net.sf.orcc.backends.transform;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import net.sf.orcc.df.Actor;
import net.sf.orcc.df.Instance;
import net.sf.orcc.df.Pattern;
import net.sf.orcc.df.Port;
import net.sf.orcc.df.util.DfVisitor;
import net.sf.orcc.ir.Def;
import net.sf.orcc.ir.Instruction;
import net.sf.orcc.ir.Use;
import net.sf.orcc.ir.Var;
import net.sf.orcc.ir.util.IrUtil;
import net.sf.orcc.util.util.EcoreHelper;

import org.eclipse.emf.common.util.EList;

/**
 * Removes unconnected output ports from an actor. Also delete all instructions
 * which use or set variables associated with these ports.
 * 
 * 
 * @author Mariem Abid
 * 
 */
public class DisconnectedOutputPortRemoval extends DfVisitor<Void> {

	private List<Port> disconnectedOutputPorts = new ArrayList<Port>();

	@Override
	public Void caseInstance(Instance instance) {
		if (instance.isActor()) {
			return super.caseActor(instance.getActor());
		}
		return null;
	}

	@Override
	public Void caseActor(Actor actor) {
		disconnectedOutputPorts.clear();

		EList<Port> outports = actor.getOutputs();

		Set<Port> connectedOutPorts = actor.getOutgoingPortMap().keySet();

		findDiscPorts(outports, connectedOutPorts);
		outports.removeAll(disconnectedOutputPorts);

		return super.caseActor(actor);
	}

	/**
	 * Add all unconnectedPorts of the current Actor to disconnectedOutputPorts
	 * member list
	 * 
	 * @param outports
	 * @param connectedOutPorts
	 */
	private void findDiscPorts(EList<Port> outports, Set<Port> connectedOutPorts) {
		for (Port portToCheck : outports) {
			if (!connectedOutPorts.contains(portToCheck)) {
				disconnectedOutputPorts.add(portToCheck);
			}
		}
	}

	@Override
	public Void casePattern(Pattern pattern) {
		EList<Port> patternPorts = pattern.getPorts();

		for (Port discPort : disconnectedOutputPorts) {
			if (patternPorts.contains(discPort)) {
				Var varOfPort = pattern.getVariable(discPort);
				removeDefUseInst(varOfPort);
				IrUtil.delete(varOfPort);
				pattern.remove(discPort);
			}
		}
		return null;
	}

	private void removeDefUseInst(Var variable) {
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
