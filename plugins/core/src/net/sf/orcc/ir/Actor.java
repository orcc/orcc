/*
 * Copyright (c) 2009-2010, IETR/INSA of Rennes
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
package net.sf.orcc.ir;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import net.sf.orcc.classes.IClass;
import net.sf.orcc.ir.FSM.NextStateInfo;
import net.sf.orcc.ir.FSM.Transition;
import net.sf.orcc.util.OrderedMap;

/**
 * This class defines an actor. An actor has parameters, input and output ports,
 * state variables, procedures, actions and an action scheduler. The action
 * scheduler has information about the FSM if the actor has one, and the order
 * in which actions should be scheduled.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class Actor {

	private List<Action> actions;

	private ActionScheduler actionScheduler;

	/**
	 * the class of this actor. Initialized to unknown.
	 */
	private IClass actorClass;

	/**
	 * The RVC-CAL file this actor was defined in.
	 */
	private String file;

	private List<Action> initializes;

	private OrderedMap<Port> inputs;

	private Map<Transition, String> maskInputs;

	private Map<Port, String> maskOutputs;

	private String name;

	private OrderedMap<Port> outputs;

	private OrderedMap<Variable> parameters;

	private OrderedMap<Procedure> procs;

	private OrderedMap<Variable> stateVars;

	private boolean timeDependent;

	/**
	 * Creates a new actor.
	 * 
	 * @param name
	 *            actor name
	 * @param file
	 *            the RVC-CAL file this actor was defined in
	 * @param parameters
	 *            an ordered map of parameters
	 * @param inputs
	 *            an ordered map of input ports
	 * @param outputs
	 *            an ordered map of output ports
	 * @param stateVars
	 *            an ordered map of state variables
	 * @param procs
	 *            an ordered map of procedures
	 * @param actions
	 *            a list of actions
	 * @param initializes
	 *            a list of initialize actions
	 * @param scheduler
	 *            an action scheduler
	 */
	public Actor(String name, String file, OrderedMap<Variable> parameters,
			OrderedMap<Port> inputs, OrderedMap<Port> outputs,
			OrderedMap<Variable> stateVars, OrderedMap<Procedure> procs,
			List<Action> actions, List<Action> initializes,
			ActionScheduler scheduler) {
		this.actions = actions;
		this.file = file;
		this.initializes = initializes;
		this.inputs = inputs;
		this.name = name;
		this.outputs = outputs;
		this.parameters = parameters;
		this.procs = procs;
		this.actionScheduler = scheduler;
		this.stateVars = stateVars;
	}

	/**
	 * Builds the mask inputs map.
	 */
	private void buildMaskInputs() {
		if (!actionScheduler.hasFsm()) {
			return;
		}

		FSM fsm = actionScheduler.getFsm();
		for (Transition transition : fsm.getTransitions()) {
			Set<Port> ports = new HashSet<Port>();
			for (NextStateInfo info : transition.getNextStateInfo()) {
				Pattern pattern = info.getAction().getInputPattern();
				for (Entry<Port, Integer> entry : pattern.entrySet()) {
					ports.add(entry.getKey());
				}
			}

			// create the mask
			int mask = 0;
			int i = 0;
			for (Port port : inputs) {
				if (ports.contains(port)) {
					mask |= (1 << i);
				}

				i++;
			}

			maskInputs.put(transition, Integer.toHexString(mask));
		}
	}

	/**
	 * Builds the mask outputs map.
	 */
	private void buildMaskOutputs() {
		int i = 0;
		for (Port port : outputs) {
			int mask = (1 << i);
			i++;
			maskOutputs.put(port, Integer.toHexString(mask));
		}
	}

	/**
	 * Computes the mask map that associate a port mask to a transition. The
	 * port mask defines the port(s) read by actions in each transition.
	 */
	public void computeTemplateMaps() {
		maskInputs = new HashMap<FSM.Transition, String>();
		maskOutputs = new HashMap<Port, String>();
		buildMaskInputs();
		buildMaskOutputs();
	}

	public List<Action> getActions() {
		return actions;
	}

	public ActionScheduler getActionScheduler() {
		return actionScheduler;
	}

	/**
	 * Returns the class of this actor.
	 * 
	 * @return an actor class
	 */
	public IClass getActorClass() {
		return actorClass;
	}

	/**
	 * Returns the RVC-CAL file this actor was declared in.
	 * 
	 * @return the RVC-CAL file this actor was declared in
	 */
	public String getFile() {
		return file;
	}

	public List<Action> getInitializes() {
		return initializes;
	}

	/**
	 * Returns the input port whose name matches the given name.
	 * 
	 * @param name
	 *            the port name
	 * @return an input port whose name matches the given name
	 */
	public Port getInput(String name) {
		return inputs.get(name);
	}

	/**
	 * Returns the ordered map of input ports.
	 * 
	 * @return the ordered map of input ports
	 */
	public OrderedMap<Port> getInputs() {
		return inputs;
	}

	/**
	 * Returns the mask for all the input ports of the actor. Bit 0 is set for
	 * port 0, until bit n is set for port n.
	 * 
	 * @return an integer mask for all the input ports of the actor
	 */
	public String getMaskInputs() {
		int n = inputs.size();
		int mask = (1 << n) - 1;
		return Integer.toHexString(mask);
	}

	/**
	 * Returns the map of transition to mask of input ports of the actor read by
	 * actions in the transition. Bit 0 is set for port 0, until bit n is set
	 * for port n.
	 * 
	 * @return a map of transitions to input ports' masks
	 */
	public Map<Transition, String> getMaskInputsTransition() {
		return maskInputs;
	}

	/**
	 * Returns the mask for the output port of the actor read by actions in the
	 * given transition. Bit 0 is set for port 0, until bit n is set for port n.
	 * 
	 * @return an integer mask for the input ports of the actor read by actions
	 *         in the given transition
	 */
	public Map<Port, String> getMaskOutput() {
		return maskOutputs;
	}

	public String getName() {
		return name;
	}

	/**
	 * Returns the output port whose name matches the given name.
	 * 
	 * @param name
	 *            the port name
	 * @return an output port whose name matches the given name
	 */
	public Port getOutput(String name) {
		return outputs.get(name);
	}

	/**
	 * Returns the ordered map of output ports.
	 * 
	 * @return the ordered map of output ports
	 */
	public OrderedMap<Port> getOutputs() {
		return outputs;
	}

	/**
	 * Returns the ordered map of parameters.
	 * 
	 * @return the ordered map of parameters
	 */
	public OrderedMap<Variable> getParameters() {
		return parameters;
	}

	/**
	 * Returns the ordered map of procedures.
	 * 
	 * @return the ordered map of procedures
	 */
	public OrderedMap<Procedure> getProcs() {
		return procs;
	}

	/**
	 * Returns the ordered map of state variables.
	 * 
	 * @return the ordered map of state variables
	 */
	public OrderedMap<Variable> getStateVars() {
		return stateVars;
	}

	/**
	 * Returns <code>true</code> if this actor is a <code>system</code> actor,
	 * which means that it is supposed to be replaced by a hand-written
	 * implementation. An actor is identified as "system" if it does not contain
	 * any actions.
	 * 
	 * @return <code>true</code> if this actor is a <code>system</code> actor,
	 *         <code>false</code> otherwise
	 */
	public boolean isSystem() {
		return actions.isEmpty();
	}

	/**
	 * Returns <code>true</code> if this actor is time-dependent,
	 * <code>false</code> otherwise.
	 * 
	 * @return <code>true</code> if this actor is time-dependent,
	 *         <code>false</code> otherwise
	 */
	public boolean isTimeDependent() {
		return timeDependent;
	}

	/**
	 * Resets input consumption rates.
	 */
	public void resetTokenConsumption() {
		for (Port port : inputs) {
			port.resetTokenConsumption();
		}
	}

	/**
	 * Resets output production rates.
	 */
	public void resetTokenProduction() {
		for (Port port : outputs) {
			port.resetTokenProduction();
		}
	}

	/**
	 * Sets the class of this actor.
	 * 
	 * @param actorClass
	 *            an actor class
	 */
	public void setActorClass(IClass actorClass) {
		this.actorClass = actorClass;
	}

	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Sets this actor to be time-dependent or not.
	 * 
	 * @param timeDependent
	 *            a boolean indicating if this actor is time-dependent or not
	 */
	public void setTimeDependent(boolean timeDependent) {
		this.timeDependent = timeDependent;
	}

	@Override
	public String toString() {
		return name;
	}

}
