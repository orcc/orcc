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
import java.util.Map;

import net.sf.orcc.df.Actor;
import net.sf.orcc.df.Connection;
import net.sf.orcc.df.Instance;
import net.sf.orcc.df.Pattern;
import net.sf.orcc.df.Port;
import net.sf.orcc.df.util.DfVisitor;
import net.sf.orcc.ir.Var;
import net.sf.orcc.ir.util.IrUtil;
import net.sf.orcc.util.OrccLogger;

import org.eclipse.emf.common.util.EList;

/**
 * Removes unconnected output ports from an actor. Also delete all instructions
 * which use or set variables associated with these ports.
 * 
 * @author Mariem Abid
 * 
 */
public class DisconnectedOutputPortRemoval extends DfVisitor<Void> {

	private List<Port> disconnectedOutputs;

	@Override
	public Void caseInstance(Instance instance) {
		if (instance.isActor()) {
			EList<Port> outputs = instance.getActor().getOutputs();
			disconnectedOutputs = getDisconnectedOutputs(outputs,
					instance.getOutgoingPortMap());

			for (Port port : disconnectedOutputs) {
				outputs.remove(port);
				OrccLogger.noticeln("[" + instance.getName() + "] Port "
						+ port.getName() + " not connected.");
			}
			return super.caseActor(instance.getActor());
		}
		return null;
	}

	@Override
	public Void caseActor(Actor actor) {
		EList<Port> outputs = actor.getOutputs();
		disconnectedOutputs = getDisconnectedOutputs(outputs,
				actor.getOutgoingPortMap());

		for (Port port : disconnectedOutputs) {
			outputs.remove(port);
			OrccLogger.noticeln("[" + actor.getName() + "] Port "
					+ port.getName() + " not connected.");
		}

		return super.caseActor(actor);
	}

	@Override
	public Void casePattern(Pattern pattern) {
		for (Port port : disconnectedOutputs) {
			if (pattern.getPorts().contains(port)) {
				Var portVar = pattern.getVariable(port);
				IrUtil.removeInstrRelated(portVar);
				IrUtil.delete(portVar);
				pattern.remove(port);
			}
		}
		return null;
	}

	private List<Port> getDisconnectedOutputs(List<Port> outputs,
			Map<Port, List<Connection>> outgoingPortMap) {
		List<Port> disconnectedOutputs = new ArrayList<Port>();

		for (Port port : outputs) {
			if (!outgoingPortMap.containsKey(port)) {
				disconnectedOutputs.add(port);
			}
		}

		return disconnectedOutputs;
	}

}
