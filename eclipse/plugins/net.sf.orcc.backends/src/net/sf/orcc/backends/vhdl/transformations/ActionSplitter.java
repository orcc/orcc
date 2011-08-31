/*
 * Copyright (c) 2011, IETR/INSA of Rennes
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
package net.sf.orcc.backends.vhdl.transformations;

import static net.sf.orcc.ir.IrFactory.eINSTANCE;
import static net.sf.orcc.ir.util.IrUtil.copy;
import static net.sf.orcc.util.EcoreHelper.getContainerOfType;
import static org.eclipse.emf.ecore.util.EcoreUtil.isAncestor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.orcc.backends.instructions.InstSplit;
import net.sf.orcc.ir.Action;
import net.sf.orcc.ir.Actor;
import net.sf.orcc.ir.Def;
import net.sf.orcc.ir.FSM;
import net.sf.orcc.ir.InstLoad;
import net.sf.orcc.ir.InstSpecific;
import net.sf.orcc.ir.InstStore;
import net.sf.orcc.ir.Instruction;
import net.sf.orcc.ir.IrFactory;
import net.sf.orcc.ir.NodeBlock;
import net.sf.orcc.ir.Procedure;
import net.sf.orcc.ir.State;
import net.sf.orcc.ir.Use;
import net.sf.orcc.ir.Var;
import net.sf.orcc.ir.util.AbstractActorVisitor;
import net.sf.orcc.ir.util.IrUtil;
import net.sf.orcc.util.UniqueEdge;

import org.jgrapht.DirectedGraph;

/**
 * This class defines a transformation that splits actions each time a
 * SplitInstruction is encountered.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class ActionSplitter extends AbstractActorVisitor<Object> {

	private class UseUpdater extends AbstractActorVisitor<Object> {

		public UseUpdater() {
			this.actor = ActionSplitter.this.actor;
		}

		@Override
		public Object caseInstLoad(InstLoad load) {
			if (load.getIndexes().isEmpty()) {
				// gets source, target of this load
				Var source = load.getSource().getVariable();
				Var target = load.getTarget().getVariable();

				// saves the global associated with the local
				mapLocalToGlobal.put(target, source);
			}

			return null;
		}

		@Override
		public Object caseInstruction(Instruction instruction) {
			NodeBlock block = instruction.getBlock();
			for (Def def : IrUtil.getObjects(instruction, Def.class)) {
				Var var = def.getVariable();
				if (var.isLocal() && !var.getType().isList()) {
					if (instruction.isAssign()) {
						handleLocalVarDef(block, var);
					}
				}
			}

			return null;
		}

		private Var createGlobal(Var var) {
			String name = "sg_" + procedure.getName() + "_" + var.getName();
			Var global = eINSTANCE.createVar(procedure.getLineNumber(),
					copy(var.getType()), name, true, null);
			actor.getStateVars().add(global);
			mapLocalToGlobal.put(var, global);

			// copy uses and replace those that are in different procedures
			List<Use> uses = new ArrayList<Use>(var.getUses());
			for (Use use : uses) {
				if (!isAncestor(procedure, use)) {
					Procedure procUse = getContainerOfType(use, Procedure.class);
					Var local = procUse.newTempLocalVariable(
							copy(var.getType()), var.getName());
					InstLoad load = eINSTANCE.createInstLoad(local, global);
					load.setPredicate(eINSTANCE.createPredicate());

					Instruction inst = getContainerOfType(use,
							Instruction.class);
					NodeBlock block = inst.getBlock();
					int index = block.indexOf(inst);
					block.add(index, load);

					use.setVariable(local);
				}
			}

			return global;
		}

		private void handleLocalVarDef(NodeBlock block, Var var) {
			boolean interProcUses = false;
			for (Use use : var.getUses()) {
				if (!isAncestor(procedure, use)) {
					interProcUses = true;
				}
			}

			// get global variable
			Var global = mapLocalToGlobal.get(var);
			if (global == null && interProcUses && isAncestor(procedure, var)) {
				global = createGlobal(var);
			}

			if (global != null) {
				// create store
				InstStore store = eINSTANCE.createInstStore(global, var);
				store.setPredicate(eINSTANCE.createPredicate());

				// add store after this assign
				block.add(++indexInst, store);
			}
		}

	}

	/**
	 * name of the branch being visited
	 */
	private String branchName;

	/**
	 * action being visited
	 */
	private Action currentAction;

	/**
	 * FSM of the actor. May be null if the actor has no FSM. May be updated if
	 * an FSM is added to the actor.
	 */
	private FSM fsm;

	private Map<Var, Var> mapLocalToGlobal;

	/**
	 * action to visit next (may be null)
	 */
	private Action nextAction;

	/**
	 * name of the source state
	 */
	private State source;

	/**
	 * Map used to create new unique state names.
	 */
	private Map<String, Integer> stateNames;

	private Map<String, State> statesMap;

	/**
	 * name of the target state
	 */
	private State target;

	/**
	 * Adds an FSM to the given action scheduler.
	 * 
	 * @param actionScheduler
	 *            action scheduler
	 */
	private void addFsm() {
		fsm = IrFactory.eINSTANCE.createFSM();

		State initState = statesMap.get("init");
		fsm.getStates().add(initState);
		fsm.setInitialState(initState);
		for (Action action : actor.getActionsOutsideFsm()) {
			fsm.addTransition(initState, action, initState);
		}

		actor.getActionsOutsideFsm().clear();
		actor.setFsm(fsm);
	}

	@Override
	public Object caseAction(Action action) {
		this.branchName = target.getName() + "_" + action.getName();
		nextAction = action;

		while (nextAction != null) {
			currentAction = nextAction;
			nextAction = null;

			// visit current action
			doSwitch(currentAction.getBody());
		}

		return null;
	}

	@Override
	public Object caseActor(Actor actor) {
		this.actor = actor;
		stateNames = new HashMap<String, Integer>();
		statesMap = new HashMap<String, State>();

		visitAllActions();

		mapLocalToGlobal = new HashMap<Var, Var>();
		for (Action action : actor.getActions()) {
			new UseUpdater().doSwitch(action.getBody());
		}

		return null;
	}

	@Override
	public Object caseInstSpecific(InstSpecific instruction) {
		if (instruction instanceof InstSplit) {
			splitAction((InstSplit) instruction);
		}
		return null;
	}

	/**
	 * Creates a new empty action with the given name.
	 * 
	 * @param name
	 *            action name
	 * @return a new empty action with the given name
	 */
	private Action createNewAction(String name) {
		IrFactory fac = IrFactory.eINSTANCE;

		// scheduler
		Procedure scheduler = fac.createProcedure("isSchedulable_" + name, 0,
				fac.createTypeBool());
		NodeBlock block = scheduler.getFirst();
		Instruction inst = fac.createInstReturn(fac.createExprBool(true));
		inst.setPredicate(fac.createPredicate());
		block.add(inst);

		// body
		Procedure body = fac.createProcedure(name, 0, fac.createTypeVoid());
		block = body.getFirst();
		inst = fac.createInstReturn();
		inst.setPredicate(fac.createPredicate());
		block.add(inst);

		// create action
		Action action = fac.createAction(fac.createTag(name),
				fac.createPattern(), currentAction.getOutputPattern(),
				fac.createPattern(), scheduler, body);
		currentAction.setOutputPattern(fac.createPattern());

		// add action to actor's actions
		actor.getActions().add(action);

		return action;
	}

	/**
	 * Returns a new unique state name.
	 * 
	 * @return a new unique state name
	 */
	private String getNewStateName() {
		String stateName = branchName;
		Integer count = stateNames.get(stateName);
		if (count == null) {
			count = 1;
		}
		stateNames.put(stateName, count + 1);

		return stateName + "_" + count;
	}

	/**
	 * Replaces the transition <code>source</code> -&gt; <code>target</code> by
	 * a transition <code>source</code> -&gt; <code>newState</code> -&gt;
	 * <code>target</code>.
	 * 
	 * @param newAction
	 *            the newly-created action
	 */
	private void replaceTransition(Action newAction) {
		// add an FSM if the actor does not have one
		if (fsm == null) {
			addFsm();
		}

		// add state and transitions
		String newStateName = newAction.getName();
		State newState = IrFactory.eINSTANCE.createState(newStateName);
		statesMap.put(newStateName, newState);
		fsm.getStates().add(newState);

		fsm.replaceTarget(source, currentAction, newState);
		fsm.addTransition(newState, newAction, target);
	}

	/**
	 * Split the current action
	 */
	private void splitAction(InstSplit instSplit) {
		// create new action
		String newActionName = getNewStateName();
		nextAction = createNewAction(newActionName);

		// remove the SplitInstruction
		NodeBlock block = instSplit.getBlock();
		IrUtil.delete(instSplit);

		// move instructions
		NodeBlock targetBlock = nextAction.getBody().getFirst();
		List<Instruction> instructions = block.getInstructions();
		targetBlock.getInstructions().addAll(0,
				instructions.subList(indexInst, instructions.size()));

		// update transitions
		replaceTransition(nextAction);

		// set new source state to the new state name
		source = statesMap.get(newActionName);
	}

	/**
	 * Visits the given transition characterized by its source, target and
	 * action.
	 * 
	 * @param source
	 *            source state
	 * @param target
	 *            target state
	 * @param action
	 *            action associated with transition
	 */
	private void visit(State source, State target, Action action) {
		this.source = source;
		this.target = target;
		doSwitch(action);
	}

	/**
	 * Visits all actions of this actor.
	 */
	private void visitAllActions() {
		fsm = actor.getFsm();
		if (fsm == null) {
			// no FSM: simply visit all the actions
			List<Action> actions = new ArrayList<Action>(
					actor.getActionsOutsideFsm());

			State initState = IrFactory.eINSTANCE.createState("init");
			statesMap.put("init", initState);

			for (Action action : actions) {
				// an FSM will be created if needed, from "init" to "init" (and
				// intermediate transitions created by the BranchVisitor)

				visit(initState, initState, action);
			}
		} else {
			// with an FSM: visits all transitions
			DirectedGraph<State, UniqueEdge> graph = fsm.getGraph();
			Set<UniqueEdge> edges = graph.edgeSet();
			for (UniqueEdge edge : edges) {
				State source = graph.getEdgeSource(edge);
				State target = graph.getEdgeTarget(edge);
				Action action = (Action) edge.getObject();
				visit(source, target, action);
			}
		}
	}

}
