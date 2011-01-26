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

import net.sf.orcc.ir.Action;
import net.sf.orcc.ir.ActionScheduler;
import net.sf.orcc.ir.Actor;
import net.sf.orcc.ir.FSM;
import net.sf.orcc.ir.FSM.NextStateInfo;
import net.sf.orcc.ir.FSM.Transition;
import net.sf.orcc.ir.GlobalVariable;
import net.sf.orcc.ir.Pattern;
import net.sf.orcc.ir.Port;
import net.sf.orcc.ir.Procedure;
import net.sf.orcc.ir.Variable;
import net.sf.orcc.moc.CSDFMoC;
import net.sf.orcc.moc.MoC;
import net.sf.orcc.moc.QSDFMoC;

/**
 * This class computes a map for inserting metadata information into LLVM
 * template.
 * 
 * @author Jerome GORIN
 * 
 */
public class LLVMTemplateData {

	/**
	 * Medata container of actions
	 */
	private Map<Object, Integer> actions;

	/**
	 * Medata container of action scheduler
	 */
	private Map<Object, Integer> actionScheduler;

	/**
	 * Actor to compute
	 */
	private Actor actor;

	/**
	 * Medata container of expressions
	 */
	private Map<Object, Integer> exprs;

	/**
	 * Medata identifier counter
	 */
	private int id;

	/**
	 * Medata container of MoCs
	 */
	private Map<Object, Integer> mocs;

	/**
	 * Medata container of names
	 */
	private Map<Object, Integer> names;

	/**
	 * Medata container of patterns
	 */
	private Map<Object, Integer> patterns;

	/**
	 * Medata container of ports
	 */
	private Map<Object, Integer> ports;

	/**
	 * Medata container of procedures
	 */
	private Map<Object, Integer> procs;

	/**
	 * Medata container of types
	 */
	private Map<Object, Integer> types;

	/**
	 * Medata container of variables
	 */
	private Map<Object, Integer> vars;

	public LLVMTemplateData(Actor actor) {
		ports = new HashMap<Object, Integer>();
		patterns = new HashMap<Object, Integer>();
		actions = new HashMap<Object, Integer>();
		actionScheduler = new HashMap<Object, Integer>();
		procs = new HashMap<Object, Integer>();
		vars = new HashMap<Object, Integer>();
		types = new HashMap<Object, Integer>();
		names = new HashMap<Object, Integer>();
		mocs = new HashMap<Object, Integer>();
		exprs = new HashMap<Object, Integer>();
		this.actor = actor;
		this.id = 0;

		computeActor();
	}

	private void computeAction(Action action) {
		actions.put(action, id++);
		// Avoid to add null id in map
		if (!action.getTag().getIdentifiers().isEmpty()) {
			actions.put(action.getTag(), id++);
		}

		// Write port pattern
		computePattern(action.getInputPattern());
		computePattern(action.getOutputPattern());

		// Write action elements
		actions.put(action.getScheduler(), id++);
		actions.put(action.getBody(), id++);
	}

	private void computeActionScheduler(ActionScheduler actSched) {
		actionScheduler.put(actor.getActionScheduler(), id++);

		if (!actSched.getActions().isEmpty()) {
			actionScheduler.put(actSched.getActions(), id++);
		}

		if (actSched.hasFsm()) {
			FSM fsm = actSched.getFsm();
			actionScheduler.put(fsm, id++);
			actionScheduler.put(fsm.getStates(), id++);
			actionScheduler.put(fsm.getTransitions(), id++);

			for (Transition transition : fsm.getTransitions()) {
				actionScheduler.put(transition, id++);
				actionScheduler.put(transition.getNextStateInfo(), id++);
				for (NextStateInfo nextState : transition.getNextStateInfo()) {
					actionScheduler.put(nextState, id++);
				}

			}
		}
	}

	/**
	 * Computes metadata of actors.
	 * 
	 */
	private void computeActor() {
		// Insert source file info
		names.put(actor.getFile(), id++);

		// Insert name
		names.put(actor.getName(), id++);

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
		for (GlobalVariable var : actor.getStateVars()) {
			computeStateVar(var);
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

		if (actor.hasMoC()) {
			computeMoC(actor.getMoC());
		}
	}

	private void computeCSDFMoC(CSDFMoC sdfmoc) {
		mocs.put(sdfmoc, id++);
		computePattern(sdfmoc.getInputPattern());
		computePattern(sdfmoc.getOutputPattern());
	}

	private void computeMoC(MoC moc) {
		if (moc.isSDF() || moc.isCSDF()) {
			computeCSDFMoC((CSDFMoC) moc);
		} else if (moc.isQuasiStatic()) {
			mocs.put(moc, id++);
			QSDFMoC qsdfMoc = (QSDFMoC) moc;
			for (Action action : qsdfMoc.getActions()) {
				computeCSDFMoC(qsdfMoc.getStaticClass(action));
			}
		} else if (moc.isDPN() || moc.isKPN()) {
			mocs.put(moc, id++);
		}
	}

	private void computePattern(Pattern pattern) {
		if (!pattern.isEmpty()) {
			patterns.put(pattern, id++);
		}
	}

	private void computePort(Port port) {
		ports.put(port, id++);
		types.put(port.getType(), id++);
	}

	private void computeProc(Procedure proc) {
		procs.put(proc, id++);
	}

	private void computeStateVar(GlobalVariable var) {
		computeVar(var);
		if (var.isInitialized()) {
			exprs.put(var.getName(), id++);
		}
	}

	private void computeVar(Variable var) {
		vars.put(var, id++);
		names.put(var.getName(), id++);
		types.put(var.getType(), id++);
	}

	/**
	 * get actions map
	 * 
	 * @return a map of action information.
	 */
	public Map<Object, Integer> getActions() {
		return actions;
	}

	/**
	 * get action scheduler map
	 * 
	 * @return a map of action scheduler information.
	 */
	public Map<Object, Integer> getActionScheduler() {
		return actionScheduler;
	}

	/**
	 * get expression map
	 * 
	 * @return a map of expression information.
	 */
	public Map<Object, Integer> getExprs() {
		return exprs;
	}

	/**
	 * get Model of Computations map
	 * 
	 * @return a map of MoC information.
	 */
	public Map<Object, Integer> getMocs() {
		return mocs;
	}

	/**
	 * get names map
	 * 
	 * @return a map of variable information.
	 */
	public Map<Object, Integer> getNames() {
		return names;
	}

	/**
	 * get patterns map
	 * 
	 * @return a map of pattern information.
	 */
	public Map<Object, Integer> getPatterns() {
		return patterns;
	}

	/**
	 * get port map
	 * 
	 * @return a map of port information.
	 */
	public Map<Object, Integer> getPorts() {
		return ports;
	}

	/**
	 * get procedures map
	 * 
	 * @return a map of procedure information.
	 */
	public Map<Object, Integer> getProcs() {
		return procs;
	}

	/**
	 * get types map
	 * 
	 * @return a map of type information.
	 */
	public Map<Object, Integer> getTypes() {
		return types;
	}

	/**
	 * get variables map
	 * 
	 * @return a map of variable information.
	 */
	public Map<Object, Integer> getVars() {
		return vars;
	}

}
