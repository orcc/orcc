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
package net.sf.orcc.ir.actor;

import java.util.List;

import net.sf.orcc.ir.VarDef;

/**
 * @author Matthieu Wipliez
 * 
 */
public class Actor {

	private List<Action> actions;

	private ActionScheduler actionScheduler;

	private List<Action> initializes;

	private List<VarDef> inputs;

	private String name;

	private List<VarDef> outputs;

	private List<Procedure> procs;

	private List<StateVar> stateVars;

	public Actor(String name, List<VarDef> inputs, List<VarDef> outputs,
			List<StateVar> stateVars, List<Procedure> procs,
			List<Action> actions, List<Action> initializes,
			ActionScheduler scheduler) {
		this.actions = actions;
		this.initializes = initializes;
		this.inputs = inputs;
		this.name = name;
		this.outputs = outputs;
		this.procs = procs;
		this.actionScheduler = scheduler;
		this.stateVars = stateVars;
	}

	public List<Action> getActions() {
		return actions;
	}

	public ActionScheduler getActionScheduler() {
		return actionScheduler;
	}

	public List<Action> getInitializes() {
		return initializes;
	}

	public VarDef getInput(String port) {
		for (VarDef varDef : inputs) {
			if (varDef.getName().equals(port)) {
				return varDef;
			}
		}

		return null;
	}

	public List<VarDef> getInputs() {
		return inputs;
	}

	public String getName() {
		return name;
	}

	public VarDef getOutput(String port) {
		for (VarDef varDef : outputs) {
			if (varDef.getName().equals(port)) {
				return varDef;
			}
		}

		return null;
	}

	public List<VarDef> getOutputs() {
		return outputs;
	}

	public List<Procedure> getProcs() {
		return procs;
	}

	public List<StateVar> getStateVars() {
		return stateVars;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return name;
	}

}
