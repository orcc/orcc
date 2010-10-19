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
package net.sf.orcc.backends.llvm;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import net.sf.orcc.ir.Action;
import net.sf.orcc.ir.ActionScheduler;
import net.sf.orcc.ir.Actor;
import net.sf.orcc.ir.FSM;
import net.sf.orcc.ir.FSM.NextStateInfo;
import net.sf.orcc.ir.FSM.Transition;
import net.sf.orcc.ir.Pattern;
import net.sf.orcc.ir.Port;
import net.sf.orcc.ir.Procedure;
import net.sf.orcc.ir.Variable;

/**
 * This class computes a map for inserting metadata information into LLVM
 * template.
 * 
 * @author Jerome GORIN
 * 
 */
public class LLVMTemplateData {

	/**
	 * Actor to compute
	 */
	private Actor actor;

	/**
	 * Medata identifier counter
	 */
	private int id;

	/**
	 * Medata container
	 */
	private HashMap<Object, Integer> templateDataMap;

	/**
	 * Computes a map of metadata for LLVM template
	 * 
	 */
	public LLVMTemplateData(Actor actor) {
		templateDataMap = new HashMap<Object, Integer>();
		this.actor = actor;
		this.id = 0;

		computeTemplateMaps();
	}

	private void computeAction(Action action) {
		templateDataMap.put(action, id++);
		// Avoid to add null id in map
		if (!action.getTag().getIdentifiers().isEmpty()) {
			templateDataMap.put(action.getTag(), id++);
		}

		// Write port pattern
		computePattern(action.getInputPattern());
		computePattern(action.getOutputPattern());

		// Write action elements
		templateDataMap.put(action.getScheduler(), id++);
		templateDataMap.put(action.getBody(), id++);
	}

	private void computeActionScheduler(ActionScheduler actSched) {
		templateDataMap.put(actor.getActionScheduler(), id++);

		if (!actSched.getActions().isEmpty()) {
			templateDataMap.put(actSched.getActions(), id++);
		}

		if (actSched.hasFsm()) {
			FSM fsm = actSched.getFsm();
			templateDataMap.put(fsm, id++);
			templateDataMap.put(fsm.getStates(), id++);
			templateDataMap.put(fsm.getTransitions(), id++);

			for (Transition transition : fsm.getTransitions()) {
				templateDataMap.put(transition, id++);
				templateDataMap.put(transition.getNextStateInfo(), id++);
				for (NextStateInfo nextState : transition.getNextStateInfo()) {
					templateDataMap.put(nextState, id++);
				}

			}
		}
	}

	private void computePattern(Pattern pattern) {
		if (!pattern.isEmpty()) {
			templateDataMap.put(pattern, id++);

			for (Entry<Port, Integer> entry : pattern.entrySet()) {
				templateDataMap.put(entry, id++);
			}
		}
	}

	private void computePort(Port port) {
		templateDataMap.put(port, id++);
		templateDataMap.put(port.getType(), id++);
	}

	private void computeProc(Procedure proc) {
		templateDataMap.put(proc, id++);
	}

	private void computeTemplateMaps() {
		// Insert source file info
		templateDataMap.put(actor.getFile(), id++);

		// Insert name
		templateDataMap.put(actor.getName(), id++);

		// Insert action scheduler
		computeActionScheduler(actor.getActionScheduler());

		// Insert inputs
		for (Port input : actor.getInputs()) {
			computePort(input);
		}

		// Insert outputs
		for (Port output : actor.getOutputs()) {
			computePort(output);
		}

		// Insert statevars
		for (Variable var : actor.getStateVars()) {
			computeVar(var);
		}

		// Insert parameters
		for (Variable parameter : actor.getParameters()) {
			computeVar(parameter);
		}

		// Insert procedures
		for (Procedure procedure : actor.getProcs()) {
			computeProc(procedure);
		}

		// Insert procedures
		for (Action initialize : actor.getInitializes()) {
			computeAction(initialize);
		}

		// Insert actions
		for (Action action : actor.getActions()) {
			computeAction(action);
		}
	}

	private void computeVar(Variable var) {
		templateDataMap.put(var, id++);
		templateDataMap.put(var.getName(), id++);
		templateDataMap.put(var.getType(), id++);
	}

	/**
	 * get the template map computed
	 * 
	 * @return a map of metadata information associated with their id.
	 */
	public Map<Object, Integer> getTemplateData() {
		return templateDataMap;
	}

}
