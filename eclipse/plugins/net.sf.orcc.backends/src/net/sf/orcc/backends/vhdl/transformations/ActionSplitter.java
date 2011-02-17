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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.orcc.ir.AbstractActorVisitor;
import net.sf.orcc.ir.Action;
import net.sf.orcc.ir.ActionScheduler;
import net.sf.orcc.ir.Actor;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.FSM;
import net.sf.orcc.ir.FSM.State;
import net.sf.orcc.ir.IrFactory;
import net.sf.orcc.ir.LocalVariable;
import net.sf.orcc.ir.Location;
import net.sf.orcc.ir.Pattern;
import net.sf.orcc.ir.Procedure;
import net.sf.orcc.ir.Tag;
import net.sf.orcc.ir.Use;
import net.sf.orcc.ir.expr.BoolExpr;
import net.sf.orcc.ir.expr.VarExpr;
import net.sf.orcc.ir.instructions.Assign;
import net.sf.orcc.ir.instructions.Return;
import net.sf.orcc.ir.nodes.BlockNode;
import net.sf.orcc.util.UniqueEdge;

import org.jgrapht.DirectedGraph;

/**
 * This class contains code that is useful for transformations that split
 * actions in several actions. These transformations are
 * WhileSplitTransformation (WST) and MultipleArrayAccessTransformation (MAAT).
 * 
 * @author Matthieu Wipliez
 * 
 */
public abstract class ActionSplitter extends AbstractActorVisitor {

	/**
	 * This class contains an abstract branch visitor.
	 * 
	 * @author Matthieu Wipliez
	 * 
	 */
	protected class AbstractBranchVisitor extends AbstractActorVisitor {

		/**
		 * name of the branch being visited
		 */
		private String branchName;

		/**
		 * action being visited
		 */
		protected Action currentAction;

		/**
		 * action to visit next (may be null)
		 */
		protected Action nextAction;

		/**
		 * name of the source state
		 */
		protected String sourceName;

		/**
		 * name of the target state
		 */
		protected String targetName;

		public AbstractBranchVisitor(String sourceName, String targetName) {
			this.sourceName = sourceName;
			this.targetName = targetName;
		}

		/**
		 * Creates a new empty action with the given name.
		 * 
		 * @param name
		 *            action name
		 * @return a new empty action with the given name
		 */
		final protected Action createNewAction(Expression condition, String name) {
			// scheduler
			Procedure scheduler = new Procedure("isSchedulable_" + name,
					new Location(), IrFactory.eINSTANCE.createTypeBool());
			LocalVariable result = scheduler.newTempLocalVariable(
					ActionSplitter.this.actor.getFile(),
					IrFactory.eINSTANCE.createTypeBool(), "result");
			result.setIndex(1);
			scheduler.getLocals().remove(result.getBaseName());
			scheduler.getLocals().put(result.getName(), result);
			
			BlockNode block = new BlockNode(scheduler);
			block.add(new Assign(result, condition));
			block.add(new Return(new VarExpr(new Use(result))));
			scheduler.getNodes().add(block);

			// body
			Procedure body = new Procedure(name, new Location(),
					IrFactory.eINSTANCE.createTypeVoid());
			block = new BlockNode(body);
			block.add(new Return(null));
			body.getNodes().add(block);

			// tag
			Tag tag = new Tag();
			tag.add(name);

			Action action = new Action(new Location(), tag, new Pattern(),
					new Pattern(), scheduler, body);

			// add action to actor's actions
			ActionSplitter.this.actor.getActions().add(action);

			return action;
		}

		/**
		 * Returns a new unique state name.
		 * 
		 * @return a new unique state name
		 */
		final protected String getNewStateName() {
			String stateName = branchName;
			Integer count = stateNames.get(stateName);
			if (count == null) {
				count = 1;
			}
			stateNames.put(stateName, count + 1);

			return stateName + "_" + count;
		}

		/**
		 * Replaces the transition <code>source</code> -&gt; <code>target</code>
		 * by a transition <code>source</code> -&gt; <code>newState</code> -&gt;
		 * <code>target</code>.
		 * 
		 * @param newAction
		 *            the newly-created action
		 */
		final protected void replaceTransition(Action newAction) {
			// add an FSM if the actor does not have one
			if (fsm == null) {
				addFsm();
			}

			// add state and transitions
			String newStateName = newAction.getName();
			fsm.addState(newStateName);

			fsm.replaceTarget(sourceName, currentAction, newStateName);
			fsm.addTransition(newStateName, newAction, targetName);
		}

		/**
		 * Split the current action
		 */
		protected void splitAction() {
			String newActionName = getNewStateName();

			// create new action
			nextAction = createNewAction(new BoolExpr(true), newActionName);

			// move code
			itInstruction.previous();
			CodeMover codeMover = new CodeMover();
			codeMover.setTargetProcedure(nextAction.getBody());
			codeMover.moveInstructions(itInstruction);
			codeMover.moveNodes(itNode);

			// update transitions
			replaceTransition(nextAction);

			// set new source state to the new state name
			sourceName = newActionName;
		}

		@Override
		public void visit(Action action) {
			this.branchName = targetName + "_" + action.getName();
			nextAction = action;
			visitInBranch();
		}

		/**
		 * Visits the next action(s) without updating the branch name.
		 */
		protected void visitInBranch() {
			while (nextAction != null) {
				currentAction = nextAction;
				nextAction = null;

				visit(currentAction.getBody());
			}
		}

	}

	/**
	 * FSM of the actor. May be null if the actor has no FSM. May be updated if
	 * an FSM is added to the actor.
	 */
	protected FSM fsm;

	/**
	 * Map used to create new unique state names.
	 */
	private Map<String, Integer> stateNames;

	/**
	 * Adds an FSM to the given action scheduler.
	 * 
	 * @param actionScheduler
	 *            action scheduler
	 */
	final public void addFsm() {
		ActionScheduler scheduler = actor.getActionScheduler();

		fsm = new FSM();
		fsm.setInitialState("init");
		fsm.addState("init");
		for (Action action : scheduler.getActions()) {
			fsm.addTransition("init", action, "init");
		}

		scheduler.getActions().clear();
		scheduler.setFsm(fsm);
	}

	/**
	 * Adds a transition <code>source</code> -&gt; <code>target</code> with the
	 * given action.
	 * 
	 * @param newAction
	 *            the newly-created action
	 */
	final public void addTransition(String sourceName, String targetName,
			Action newAction) {
		// add an FSM if the actor does not have one
		if (fsm == null) {
			addFsm();
		}

		// add state
		fsm.addState(sourceName);
		fsm.addState(targetName);

		// update transitions
		fsm.addTransition(sourceName, newAction, targetName);
	}

	/**
	 * Removes the transition <code>source</code> -&gt; <code>target</code> with
	 * the given action.
	 * 
	 * @param source
	 *            name of source state
	 * @param action
	 *            an action
	 */
	final public void removeTransition(String source, Action action) {
		// add an FSM if the actor does not have one
		if (fsm == null) {
			addFsm();
		}

		// remove transition
		fsm.removeTransition(source, action);
	}

	/**
	 * Replaces the target of the transition from the state whose name is given
	 * by <code>source</code> and whose action equals to the given action by a
	 * target state with the given name.
	 * 
	 * @param source
	 *            name of source state
	 * @param action
	 *            action associated with the transition
	 * @param newTargetName
	 *            name of the new target state
	 */
	final public void replaceTarget(String source, Action action,
			String newTargetName) {
		// add an FSM if the actor does not have one
		if (fsm == null) {
			addFsm();
		}

		// remove transition
		fsm.replaceTarget(source, action, newTargetName);
	}

	@Override
	public void visit(Actor actor) {
		this.actor = actor;
		stateNames = new HashMap<String, Integer>();
	}

	/**
	 * Visits the given transition characterized by its source name, target name
	 * and action.
	 * 
	 * @param sourceName
	 *            name of source state
	 * @param targetName
	 *            name of target state
	 * @param action
	 *            action associated with transition
	 */
	abstract protected void visit(String sourceName, String targetName,
			Action action);

	/**
	 * Visits all actions of this actor.
	 */
	protected final void visitAllActions() {
		fsm = actor.getActionScheduler().getFsm();
		if (fsm == null) {
			// no FSM: simply visit all the actions
			List<Action> actions = new ArrayList<Action>(actor
					.getActionScheduler().getActions());
			for (Action action : actions) {
				// an FSM will be created if needed, from "init" to "init" (and
				// intermediate transitions created by the BranchVisitor)

				String sourceName = "init";
				String targetName = "init";
				visit(sourceName, targetName, action);
			}
		} else {
			// with an FSM: visits all transitions
			DirectedGraph<State, UniqueEdge> graph = fsm.getGraph();
			Set<UniqueEdge> edges = graph.edgeSet();
			for (UniqueEdge edge : edges) {
				State source = graph.getEdgeSource(edge);
				String sourceName = source.getName();

				State target = graph.getEdgeTarget(edge);
				String targetName = target.getName();

				Action action = (Action) edge.getObject();
				visit(sourceName, targetName, action);
			}
		}
	}

}
