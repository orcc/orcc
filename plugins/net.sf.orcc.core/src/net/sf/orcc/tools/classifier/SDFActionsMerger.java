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
package net.sf.orcc.tools.classifier;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.sf.orcc.ir.Action;
import net.sf.orcc.ir.ActionScheduler;
import net.sf.orcc.ir.Actor;
import net.sf.orcc.ir.FSM;
import net.sf.orcc.ir.IrFactory;
import net.sf.orcc.ir.FSM.State;
import net.sf.orcc.ir.Location;
import net.sf.orcc.ir.Pattern;
import net.sf.orcc.ir.Procedure;
import net.sf.orcc.ir.Tag;
import net.sf.orcc.ir.expr.BoolExpr;
import net.sf.orcc.ir.instructions.Return;
import net.sf.orcc.ir.nodes.BlockNode;
import net.sf.orcc.ir.transforms.AbstractActorTransformation;
import net.sf.orcc.util.UniqueEdge;

import org.jgrapht.DirectedGraph;

/**
 * This class defines a transformation that merges actions that have the same
 * input/output patterns together. This allows SDF actors to be represented more
 * simply and to be correctly interpreted. As a matter of fact, it is possible
 * to represent SDF actors with several actions that have guards on input
 * tokens, which means that when interpreted by the abstract interpreter, these
 * actors would be classified as dynamic, and we do not want that.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class SDFActionsMerger extends AbstractActorTransformation {

	private Actor actor;

	/**
	 * Creates a new classifier
	 */
	public SDFActionsMerger() {
	}

	/**
	 * Merges the given actions to a single action.
	 * 
	 * @param actions
	 *            a list of actions that have the same input/output patterns
	 * @param input
	 *            input pattern common to all actions
	 * @param output
	 *            output pattern common to all actions
	 * @return
	 */
	private List<Action> mergeActions(List<Action> actions, Pattern input,
			Pattern output) {
		// merges actions and removes them from the actor
		Procedure scheduler = mergeSDFSchedulers(actions);
		Procedure body = mergeSDFBodies(actions);

		Action action = new Action(new Location(), new Tag(), input, output,
				scheduler, body);

		// removes the actions, add the action merged
		actor.getActions().removeAll(actions);
		actor.getActions().add(action);

		// returns the action merged
		List<Action> newActions = new ArrayList<Action>();
		newActions.add(action);
		return newActions;
	}

	private Procedure mergeSDFBodies(List<Action> actions) {
		Procedure procedure = new Procedure("SDF", new Location(),
				IrFactory.eINSTANCE.createTypeVoid());
		return procedure;
	}

	private Procedure mergeSDFSchedulers(List<Action> actions) {
		Procedure procedure = new Procedure("isSchedulableSDF", new Location(),
				IrFactory.eINSTANCE.createTypeBool());
		BlockNode block = BlockNode.getLast(procedure);
		block.add(new Return(new BoolExpr(true)));
		return procedure;
	}

	@Override
	public void transform(Actor actor) {
		this.actor = actor;

		ActionScheduler scheduler = actor.getActionScheduler();
		FSM fsm = scheduler.getFsm();
		if (fsm == null) {
			List<Action> actions = tryAndMerge(actor.getActions());
			scheduler.getActions().clear();
			scheduler.getActions().addAll(actions);
		} else {
			DirectedGraph<State, UniqueEdge> graph = fsm.getGraph();
			for (State state : graph.vertexSet()) {
				List<Action> actions = new ArrayList<Action>();
				for (UniqueEdge edge : graph.outgoingEdgesOf(state)) {
					actions.add((Action) edge.getObject());
				}

				// TODO merge with FSM
			}
		}
	}

	/**
	 * Merge the given actions to a single action (if possible).
	 * 
	 * @param actions
	 *            a list of actions
	 * @return a list of actions (possibly the same as <code>actions</code> if
	 *         the actions cannot be merged)
	 */
	private List<Action> tryAndMerge(List<Action> actions) {
		int numActions = actions.size();
		if (numActions <= 1) {
			return actions;
		} else {
			// check if actions have the same input/output pattern
			Iterator<Action> it = actions.iterator();
			Action firstAction = it.next();
			Pattern input = firstAction.getInputPattern();
			Pattern output = firstAction.getOutputPattern();

			while (it.hasNext()) {
				Action currentAction = it.next();
				if (!input.equals(currentAction.getInputPattern())
						|| !output.equals(currentAction.getOutputPattern())) {
					// one pattern is not equal to another
					return actions;
				}
			}

			return mergeActions(actions, input, output);
		}
	}

}
