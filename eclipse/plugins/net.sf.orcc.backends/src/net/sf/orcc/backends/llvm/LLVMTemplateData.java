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

import net.sf.dftools.graph.Edge;
import net.sf.orcc.backends.ir.InstCast;
import net.sf.orcc.df.Action;
import net.sf.orcc.df.Actor;
import net.sf.orcc.df.FSM;
import net.sf.orcc.df.Pattern;
import net.sf.orcc.df.Port;
import net.sf.orcc.df.State;
import net.sf.orcc.ir.Block;
import net.sf.orcc.ir.Procedure;
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

	private Map<Var, Var> castedListReferences;

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
	private Map<Object, Integer> names;

	/**
	 * Label of all nodes
	 */
	private Map<Block, Integer> nodeToLabelMap;

	private Map<EMap<Port, Integer>, Integer> numTokenPattern;

	/**
	 * Medata container of patterns
	 */
	private Map<Pattern, Integer> patterns;

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

	private Map<EMap<Port, Var>, Integer> varPattern;

	/**
	 * Medata container of variables
	 */
	private Map<Var, Integer> vars;

	public LLVMTemplateData(Actor actor) {
		
		nodeToLabelMap = new HashMap<Block, Integer>();
		castedListReferences = new HashMap<Var, Var>();

		// Initialize metadata maps
		ports = new HashMap<Port, Integer>();
		actions = new HashMap<Object, Integer>();
		actionScheduler = new HashMap<Object, Integer>();
		actionMoC = new HashMap<Object, Integer>();
		configurations = new HashMap<Object, Integer>();
		patterns = new HashMap<Pattern, Integer>();
		varPattern = new HashMap<EMap<Port, Var>, Integer>();
		numTokenPattern = new HashMap<EMap<Port, Integer>, Integer>();
		procs = new HashMap<Procedure, Integer>();
		vars = new HashMap<Var, Integer>();
		types = new HashMap<Type, Integer>();
		names = new HashMap<Object, Integer>();
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
		computePattern(action.getPeekPattern());

		// Write action elements
		actions.put(action.getScheduler(), id++);
		actions.put(action.getBody(), id++);
	}

	private void computeActionScheduler(Actor actor) {
		actionScheduler.put(actor, id++);
		if (!actor.getActionsOutsideFsm().isEmpty()) {
			actionScheduler.put(actor.getActionsOutsideFsm(), id++);
		}

		if (actor.hasFsm()) {
			FSM fsm = actor.getFsm();
			actionScheduler.put(fsm, id++);
			actionScheduler.put(fsm.getStates(), id++);
			actionScheduler.put(fsm.getTransitions().getClass(), id++);

			for (State state : fsm.getStates()) {
				actionScheduler.put(state, id++);
				actionScheduler.put(state.getOutgoing(), id++);
				for (Edge edge : state.getOutgoing()) {
					actionScheduler.put(edge, id++);
				}
			}
		}
	}

	private void computeCastedListReferences(Actor actor) {
		TreeIterator<EObject> it = actor.eAllContents();
		while (it.hasNext()) {
			EObject object = it.next();
			if (object instanceof Var) {
				Var var = (Var) object;
				if (var.getType().isList()
						&& !var.getDefs().isEmpty()
						&& (var.getDefs().get(0).eContainer() instanceof InstCast)) {
					castedListReferences.put(var, var);
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
		actionMoC.put(sdfmoc.getInvocations(), id++);
		computePattern(sdfmoc.getInputPattern());
		computePattern(sdfmoc.getOutputPattern());
	}
	
	private void computeMetadataMaps(Actor actor) {
		// Insert source file info
		names.put(actor.getFile().getFullPath(), id++);

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
		while (it.hasNext()) {
			EObject object = it.next();
			if (object instanceof Procedure) {
				Procedure proc = (Procedure) object;
				int label = 1;
				TreeIterator<EObject> it2 = proc.eAllContents();
				while (it2.hasNext()) {
					EObject object2 = it2.next();
					if (object2 instanceof Block) {
						Block node = (Block) object2;
						nodeToLabelMap.put(node, label);
						label++;
					}
				}
			}
		}
	}

	private void computePattern(Pattern pattern) {
		if (!pattern.isEmpty()) {
			patterns.put(pattern, id++);
			if (!pattern.getNumTokensMap().isEmpty()) {
				numTokenPattern.put(pattern.getNumTokensMap(), id++);
			}
			if (!pattern.getPortToVarMap().isEmpty()) {
				varPattern.put(pattern.getPortToVarMap(), id++);
			}
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
			computeConfiguration(action, qsdfMoc.getSDFMoC(action));
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
		computeCastedListReferences(actor);
	}

	private void computeVar(Var var) {
		vars.put(var, id++);
		names.put(var.getName(), id++);
		types.put(var.getType(), id++);
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

	public Map<Var, Var> getCastedListReferences() {
		return castedListReferences;
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
	public Map<Object, Integer> getNames() {
		return names;
	}

	public Map<Block, Integer> getNodeToLabelMap() {
		return nodeToLabelMap;
	}

	/**
	 * get the number of tokens map of patterns
	 * 
	 * @return the number of tokens map of patterns.
	 */
	public Map<EMap<Port, Integer>, Integer> getNumTokenPattern() {
		return numTokenPattern;
	}

	/**
	 * get patterns map
	 * 
	 * @return a map of pattern information.
	 */
	public Map<Pattern, Integer> getPatterns() {
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
	public Map<EMap<Port, Var>, Integer> getVarPattern() {
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
