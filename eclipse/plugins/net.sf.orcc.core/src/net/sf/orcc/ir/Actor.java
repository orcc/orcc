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

import java.util.ArrayList;
import java.util.List;

import net.sf.orcc.moc.MoC;
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
public class Actor implements Comparable<Actor> {

	private List<Action> actions;

	private ActionScheduler actionScheduler;

	/**
	 * The RVC-CAL file this actor was defined in.
	 */
	private String file;

	private List<Action> initializes;

	private OrderedMap<String, Port> inputs;

	/**
	 * the class of this actor. Initialized to unknown.
	 */
	private MoC moc;

	private String name;

	private boolean nativeFlag;

	private OrderedMap<String, Port> outputs;

	private OrderedMap<String, GlobalVariable> parameters;

	private OrderedMap<String, Procedure> procs;

	private OrderedMap<String, GlobalVariable> stateVars;

	/**
	 * holds template-specific data.
	 */
	private Object templateData;

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
	public Actor(String name, String file,
			OrderedMap<String, GlobalVariable> parameters,
			OrderedMap<String, Port> inputs, OrderedMap<String, Port> outputs,
			boolean nativeFlag, OrderedMap<String, GlobalVariable> stateVars,
			OrderedMap<String, Procedure> procs, List<Action> actions,
			List<Action> initializes, ActionScheduler scheduler) {
		this.actions = actions;
		this.file = file;
		this.initializes = initializes;
		this.inputs = inputs;
		this.name = name;
		this.nativeFlag = nativeFlag;
		this.outputs = outputs;
		this.parameters = parameters;
		this.procs = procs;
		this.actionScheduler = scheduler;
		this.stateVars = stateVars;
		this.moc = null;
	}

	@Override
	public int compareTo(Actor actor) {
		return name.compareTo(actor.getName());
	}

	/**
	 * Returns all the actions of this actor.
	 * 
	 * @return all the actions of this actor
	 */
	public List<Action> getActions() {
		return actions;
	}

	/**
	 * Returns the action scheduler of this actor.
	 * 
	 * @return the action scheduler of this actor
	 */
	public ActionScheduler getActionScheduler() {
		return actionScheduler;
	}

	/**
	 * Returns the RVC-CAL file this actor was declared in.
	 * 
	 * @return the RVC-CAL file this actor was declared in
	 */
	public String getFile() {
		return file;
	}

	/**
	 * Returns the list of initialize actions.
	 * 
	 * @return the list of initialize actions
	 */
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
	public OrderedMap<String, Port> getInputs() {
		return inputs;
	}

	/**
	 * Returns the MoC of this actor.
	 * 
	 * @return an MoC
	 */
	public MoC getMoC() {
		return moc;
	}

	/**
	 * Returns the name of this actor.
	 * 
	 * @return the name of this actor
	 */
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
	public OrderedMap<String, Port> getOutputs() {
		return outputs;
	}

	/**
	 * Returns the package of this actor.
	 * 
	 * @return the package of this actor
	 */
	public String getPackage() {
		int index = name.lastIndexOf('.');
		if (index == -1) {
			return "";
		} else {
			return name.substring(0, index);
		}
	}

	/**
	 * Returns the package of this actor as a list of strings.
	 * 
	 * @return the package of this actor as a list of strings
	 */
	public List<String> getPackageAsList() {
		String[] segments = name.split("\\.");
		List<String> list = new ArrayList<String>(segments.length - 1);
		for (int i = 0; i < segments.length - 1; i++) {
			list.add(segments[i]);
		}
		return list;
	}

	/**
	 * Returns the ordered map of parameters.
	 * 
	 * @return the ordered map of parameters
	 */
	public OrderedMap<String, GlobalVariable> getParameters() {
		return parameters;
	}

	/**
	 * Returns the ordered map of procedures.
	 * 
	 * @return the ordered map of procedures
	 */
	public OrderedMap<String, Procedure> getProcs() {
		return procs;
	}

	/**
	 * Returns the simple name of this actor.
	 * 
	 * @return the simple name of this actor
	 */
	public String getSimpleName() {
		int index = name.lastIndexOf('.');
		if (index == -1) {
			return name;
		} else {
			return name.substring(index + 1);
		}
	}

	/**
	 * Returns the ordered map of state variables.
	 * 
	 * @return the ordered map of state variables
	 */
	public OrderedMap<String, GlobalVariable> getStateVars() {
		return stateVars;
	}

	/**
	 * Returns an object with template-specific data.
	 * 
	 * @return an object with template-specific data
	 */
	public Object getTemplateData() {
		return templateData;
	}

	/**
	 * Returns true if the actor has a Model of Computation.
	 * 
	 * @return true if actor has MoC, otherwise false.
	 */
	public Boolean hasMoC() {
		return moc != null;
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
	public boolean isNative() {
		return nativeFlag;
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
	 * Sets the MoC of this actor.
	 * 
	 * @param moc
	 *            an MoC
	 */
	public void setMoC(MoC moc) {
		this.moc = moc;
	}

	/**
	 * Sets the name of this actor.
	 * 
	 * @param name
	 *            the new name of this actor
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Sets the template data associated with this actor. Template data should
	 * hold data that is specific to a given template.
	 * 
	 * @param templateData
	 *            an object with template-specific data
	 */
	public void setTemplateData(Object templateData) {
		this.templateData = templateData;
	}

	@Override
	public String toString() {
		return name;
	}

}
