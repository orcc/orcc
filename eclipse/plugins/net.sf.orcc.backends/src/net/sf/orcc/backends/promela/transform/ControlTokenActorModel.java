/*
 * Copyright (c) 2011, Abo Akademi University
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
 *   * Neither the name of the Abo Akademi University nor the names of its
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

package net.sf.orcc.backends.promela.transform;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import net.sf.orcc.backends.promela.transform.ControlTokenLinkModel;
import net.sf.orcc.df.Action;
import net.sf.orcc.df.Actor;
import net.sf.orcc.df.Port;
import net.sf.orcc.ir.Var;

public class ControlTokenActorModel extends VariableGraph{
	private Map<Port, ControlTokenLinkModel> inLinks = new HashMap<Port, ControlTokenLinkModel>();
	private Map<ControlTokenLinkModel, Port> outLinks = new HashMap<ControlTokenLinkModel, Port>();
	private Map<Port, Set<Var>> outputPortToVarsMap = new HashMap<Port, Set<Var>>();
	private Map<Var, Port> varToInputPortMap = new HashMap<Var, Port>();
	private Set<Var> extSchedulingVars = new HashSet<Var>();
	private Set<Var> schedOutPortVars = new HashSet<Var>();
	private Set<Port> schedOutPort = new HashSet<Port>();
	
	public ControlTokenActorModel(Actor actor) {
		super(actor);
		this.actor = actor;
		// make some helper maps
		for (Action action : actor.getActions()) {
			for (Port port : action.getOutputPattern().getPorts()) {
				if (!outputPortToVarsMap.containsKey(port)) {
					outputPortToVarsMap.put(port, new HashSet<Var>());
				}
				outputPortToVarsMap.get(port).add(
						action.getOutputPattern().getVariable(port));
			}
			for (Port port : action.getInputPattern().getPorts()) {
				varToInputPortMap.put(action.getInputPattern()
						.getVariable(port), port);
			}
			for (Port port : action.getPeekPattern().getPorts()) {
				varToInputPortMap.put(
						action.getPeekPattern().getVariable(port), port);
			}
		}
	}

	public void addExtSchedulingDepVars(Port outputPort) {
		for (Var v : outputPortToVarsMap.get(outputPort)) {
			this.schedOutPort.add(outputPort);
			this.schedOutPortVars.add(v);
			this.extSchedulingVars.add(v);
			this.extSchedulingVars.addAll(getReachableVars(v));
		}
		buildInterActorDependencies();
	}

	public void addInLink(ControlTokenLinkModel link, Port port) {
		inLinks.put(port, link);
	}

	public void addOutLink(ControlTokenLinkModel link, Port port) {
		outLinks.put(link, port);
	}

	public void buildInterActorDependencies() {
		Set<Var> depsSet = new HashSet<Var>();
		for (Var var : localSchedulingVars) {
			depsSet.addAll(getReachableVars(var));
		}
		localSchedulingVars.addAll(depsSet);
		for (Port ip : getSchedulingInputPorts()) {
			inLinks.get(ip).setControlLink(true);
			ControlTokenActorModel remoteActor = inLinks.get(ip).getSource();
			Port remotePort = inLinks.get(ip).getConnection().getSourcePort();
			if (remoteActor != null) {
				remoteActor.addExtSchedulingDepVars(remotePort);
			}
		}
	}

	public Set<Var> getExtSchedulingVars() {
		return extSchedulingVars;
	}

	public Set<Var> getLocalSchedulingVars() {
		return localSchedulingVars;
	}

	public Set<Var> getSchedOutPortVars() {
		return schedOutPortVars;
	}

	private Set<Port> getSchedulingInputPorts() {
		Set<Port> ports = new HashSet<Port>();
		for (Var v : localSchedulingVars) {
			if (varToInputPortMap.containsKey(v)) {
				ports.add(varToInputPortMap.get(v));
			}
		}
		for (Var v : extSchedulingVars) {
			if (varToInputPortMap.containsKey(v)) {
				ports.add(varToInputPortMap.get(v));
			}
		}
		return ports;
	}

	public boolean isPort(Var var) {
		if (varToInputPortMap.containsKey(var)) {
			return true;
		}
		for (Set<Var> vars : outputPortToVarsMap.values()) {
			if (vars.contains(var)) {
				return true;
			}
		}
		return false;
	}
	
	public Set<Port> getPortsUsedInScheduling() {
		Set<Port> ports = getSchedulingInputPorts();
		ports.addAll(schedOutPort);
		return ports;
	}

}