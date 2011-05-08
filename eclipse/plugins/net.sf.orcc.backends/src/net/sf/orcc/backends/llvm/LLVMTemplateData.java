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
import java.util.List;
import java.util.Map;

import net.sf.orcc.ir.Action;
import net.sf.orcc.ir.Actor;
import net.sf.orcc.ir.FSM;
import net.sf.orcc.ir.Node;
import net.sf.orcc.ir.Pattern;
import net.sf.orcc.ir.Port;
import net.sf.orcc.ir.Procedure;
import net.sf.orcc.ir.Transition;
import net.sf.orcc.ir.Transitions;
import net.sf.orcc.ir.Type;
import net.sf.orcc.ir.Var;
import net.sf.orcc.moc.CSDFMoC;
import net.sf.orcc.moc.MoC;
import net.sf.orcc.moc.QSDFMoC;
import net.sf.orcc.moc.SDFMoC;

import org.eclipse.emf.common.util.EMap;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;

/**
 * This class computes a map for inserting metadata information into LLVM
 * template.
 * 
 * @author Jerome GORIN
 * @author Herve Yviquel
 * 
 */
public class LLVMTemplateData {

	/**
	 * Medata container of actions of MoCs
	 */
	private Map<Object, Integer> actionMoC;

	/**
	 * Medata container of actions
	 */
	private Map<Object, Integer> actions;

	/**
	 * Medata container of action scheduler
	 */
	private Map<Object, Integer> actionScheduler;

	/**
	 * Medata container of configurations
	 */
	private Map<Object, Integer> configurations;

	/**
	 * Medata container of expressions
	 */
	private Map<String, Integer> exprs;

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
	private Map<String, Integer> names;

	/**
	 * Label of all nodes
	 */
	private Map<Node, Integer> nodeToLabelMap;

	private Map<Object, Integer> numTokenPattern;

	/**
	 * Medata container of patterns
	 */
	private Map<Object, Integer> patterns;

	/**
	 * Medata container of ports
	 */
	private Map<Port, Integer> ports;

	/**
	 * Medata container of procedures
	 */
	private Map<Procedure, Integer> procs;

	/**
	 * Medata container of types
	 */
	private Map<Type, Integer> types;

	private Map<Object, Integer> varPattern;

	/**
	 * Medata container of variables
	 */
	private Map<Var, Integer> vars;

	public LLVMTemplateData(Actor actor) {
		nodeToLabelMap = new HashMap<Node, Integer>();

		// Initialize metadata maps
		ports = new HashMap<Port, Integer>();
		actions = new HashMap<Object, Integer>();
		actionScheduler = new HashMap<Object, Integer>();
		actionMoC = new HashMap<Object, Integer>();
		configurations = new HashMap<Object, Integer>();
		patterns = new HashMap<Object, Integer>();
		varPattern = new HashMap<Object, Integer>();
		numTokenPattern = new HashMap<Object, Integer>();
		procs = new HashMap<Procedure, Integer>();
		vars = new HashMap<Var, Integer>();
		types = new HashMap<Type, Integer>();
		names = new HashMap<String, Integer>();
		mocs = new HashMap<Object, Integer>();
		exprs = new HashMap<String, Integer>();
		id = 0;

		computeTemplateMaps(actor);
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

	private void computeActionScheduler(Actor actor) {
		if (!actor.getActionsOutsideFsm().isEmpty()) {
			actionScheduler.put(actor.getActionsOutsideFsm(), id++);
		}

		if (actor.hasFsm()) {
			FSM fsm = actor.getFsm();
			actionScheduler.put(fsm, id++);
			actionScheduler.put(fsm.getStates(), id++);
			actionScheduler.put(fsm.getTransitions(), id++);

			for (Transitions transitions : fsm.getTransitions()) {
				actionScheduler.put(transitions, id++);
				actionScheduler.put(transitions.getList(), id++);
				for (Transition transition : transitions.getList()) {
					actionScheduler.put(transition, id++);
				}
			}
		}
	}

	private void computeConfiguration(Action action, SDFMoC sdfMoC) {
		configurations.put(action, id++);
		computeCSDFMoC(sdfMoC);
	}

	private void computeCSDFMoC(CSDFMoC sdfmoc) {
		actionMoC.put(sdfmoc, id++);
		actionMoC.put(sdfmoc.getActions(), id++);
		computePattern(sdfmoc.getInputPattern());
		computePattern(sdfmoc.getOutputPattern());
	}

	private void computeMetadataMaps(Actor actor) {
		// Insert source file info
		names.put(actor.getFile(), id++);

		// Insert name
		names.put(actor.getName(), id++);

		// Insert action scheduler
		computeActionScheduler(actor);

		// Insert inputs
		for (Port input : actor.getInputs()) {
			computePort(input);
		}

		// Insert outputs
		for (Port output : actor.getOutputs()) {
			computePort(output);
		}

		// Insert statevars
		for (Var var : actor.getStateVars()) {
			computeStateVar(var);
		}

		// Insert parameters
		for (Var parameter : actor.getParameters()) {
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

	private void computeMoC(MoC moc) {
		mocs.put(moc, id++);
		if (moc.isSDF() || moc.isCSDF()) {
			computeCSDFMoC((CSDFMoC) moc);
		} else if (moc.isQuasiStatic()) {
			mocs.put(moc, id++);
			computeQSDFMoC((QSDFMoC) moc);
		}
	}

	private void computeNodeToLabelMap(Actor actor) {
		TreeIterator<EObject> it = actor.eAllContents();
		int label = 0;
		while (it.hasNext()) {
			EObject object = it.next();

			if (object instanceof Node) {
				Node node = (Node) object;
				nodeToLabelMap.put(node, label);
				label++;
			}
		}
	}

	private void computeNumTokens(EMap<Port, Integer> numTokens) {
		if (!numTokens.isEmpty()) {
			numTokenPattern.put(numTokens, id++);
		}
	}

	private void computePattern(Pattern pattern) {
		if (!pattern.isEmpty()) {
			patterns.put(pattern, id++);
			computeNumTokens(pattern.getNumTokensMap());
			computeVars(pattern.getVariables());
		}
	}

	private void computePort(Port port) {
		ports.put(port, id++);
		types.put(port.getType(), id++);
	}

	private void computeProc(Procedure proc) {
		procs.put(proc, id++);
	}

	private void computeQSDFMoC(QSDFMoC qsdfMoc) {
		for (Action action : qsdfMoc.getActions()) {
			computeConfiguration(action, qsdfMoc.getStaticClass(action));
		}
	}

	private void computeStateVar(Var var) {
		computeVar(var);
		if (var.isInitialized()) {
			exprs.put(var.getName(), id++);
		}
	}

	public void computeTemplateMaps(Actor actor) {
		computeMetadataMaps(actor);
		computeNodeToLabelMap(actor);
	}

	private void computeVar(Var var) {
		vars.put(var, id++);
		names.put(var.getName(), id++);
		types.put(var.getType(), id++);
	}

	private void computeVars(List<Var> vars) {
		if (!vars.isEmpty()) {
			varPattern.put(vars, id++);
		}
	}

	/**
	 * get the actions of a MoC
	 * 
	 * @return a map of actions of MoC information.
	 */
	public Map<Object, Integer> getActionMoC() {
		return actionMoC;
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
	 * get configurations map
	 * 
	 * @return a map of configuration information.
	 */
	public Map<Object, Integer> getConfigurations() {
		return configurations;
	}

	/**
	 * get expression map
	 * 
	 * @return a map of expression information.
	 */
	public Map<String, Integer> getExprs() {
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
	public Map<String, Integer> getNames() {
		return names;
	}

	public Map<Node, Integer> getNodeToLabelMap() {
		return nodeToLabelMap;
	}

	/**
	 * get the number of tokens map of patterns
	 * 
	 * @return the number of tokens map of patterns.
	 */
	public Map<Object, Integer> getNumTokenPattern() {
		return numTokenPattern;
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
	public Map<Port, Integer> getPorts() {
		return ports;
	}

	/**
	 * get procedures map
	 * 
	 * @return a map of procedure information.
	 */
	public Map<Procedure, Integer> getProcs() {
		return procs;
	}

	/**
	 * get types map
	 * 
	 * @return a map of type information.
	 */
	public Map<Type, Integer> getTypes() {
		return types;
	}

	/**
	 * get the var map of patterns
	 * 
	 * @return the var map of patterns.
	 */
	public Map<Object, Integer> getVarPattern() {
		return varPattern;
	}

	/**
	 * get variables map
	 * 
	 * @return a map of variable information.
	 */
	public Map<Var, Integer> getVars() {
		return vars;
	}

}
