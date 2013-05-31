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
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.common.util.EList;

import net.sf.orcc.df.Actor;
import net.sf.orcc.df.Connection;
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

/**
 * This class defines a visitor that removes unconnected output ports
 * 
 * 
 * @author Mariem Abid
 * 
 */
public class DisconnectedOutputPortRemoval extends DfVisitor<Void> {

	List<Port> discPorts = new ArrayList<Port>();

	@Override
	public Void caseInstance(Instance instance) {
		if (instance.isActor()) {
			EList<Port> outports = instance.getActor().getOutputs();
			Map<Port, List<Connection>> outMap = instance.getOutgoingPortMap();
			Set<Port> outgp = outMap.keySet();
			discPorts = findDiscPorts(outports, outgp);
			outports.removeAll(discPorts);
			return super.caseActor(instance.getActor());
		} else {
			return super.caseInstance(instance);

		}
	}

	@Override
	public Void caseActor(Actor actor) {

		// TODO Auto-generated method stub
		EList<Port> outports = actor.getOutputs();

		Map<Port, List<Connection>> outMap = actor.getOutgoingPortMap();
		Set<Port> outgp = outMap.keySet();

		discPorts = findDiscPorts(outports, outgp);
		outports.removeAll(discPorts);

		return super.caseActor(actor);

	}

	List<Port> findDiscPorts(EList<Port> outports, Set<Port> outgp) {
		ListIterator<Port> it = outports.listIterator();
		while (it.hasNext()) {
			Port port = it.next();
			if (!outgp.contains(port)) {
				System.out.println(port.getName());
				discPorts.add(port);

			}

		}
		return discPorts;
	}

	public Void casePattern(Pattern pattern) {
		EList<Port> ports = pattern.getPorts();

		for (Port discPort : discPorts) {
			if (ports.contains(discPort)) {
				Var varOfPort = pattern.getVariable(discPort);
				System.out.println(varOfPort.getName());

				removeDefUseInst(varOfPort);

				IrUtil.delete(varOfPort);

				pattern.remove(discPort);

				// ports.remove(discPort);

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
