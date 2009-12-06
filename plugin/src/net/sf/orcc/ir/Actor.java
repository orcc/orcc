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
package net.sf.orcc.ir;

import java.util.List;

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
	private ActorClass actorClass;

	/**
	 * The RVC-CAL file this actor was defined in.
	 */
	private String file;

	private List<Action> initializes;

	private OrderedMap<Port> inputs;

	private List<Procedure> instantations;

	private String name;

	private OrderedMap<Port> outputs;

	private OrderedMap<Variable> parameters;

	private OrderedMap<Procedure> procs;

	private OrderedMap<Variable> stateVars;

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
	 * @param instantations
	 *            a list of instantiation procedures
	 */
	public Actor(String name, String file, OrderedMap<Variable> parameters,
			OrderedMap<Port> inputs, OrderedMap<Port> outputs,
			OrderedMap<Variable> stateVars, OrderedMap<Procedure> procs,
			List<Action> actions, List<Action> initializes,
			ActionScheduler scheduler, List<Procedure> instantations) {
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
		this.instantations = instantations;
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
	public ActorClass getActorClass() {
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

	public List<Procedure> getInstantations() {
		return instantations;
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

	public OrderedMap<Variable> getParameters() {
		return parameters;
	}

	public OrderedMap<Procedure> getProcs() {
		return procs;
	}

	public OrderedMap<Variable> getStateVars() {
		return stateVars;
	}

	/**
	 * Sets the class of this actor.
	 * 
	 * @param actorClass
	 *            an actor class
	 */
	public void setActorClass(ActorClass actorClass) {
		this.actorClass = actorClass;
	}

	public void setInstantations(List<Procedure> instantations) {
		this.instantations = instantations;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return name;
	}

}
