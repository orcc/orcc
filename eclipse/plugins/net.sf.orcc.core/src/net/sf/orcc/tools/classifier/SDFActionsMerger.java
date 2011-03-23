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

import net.sf.orcc.ir.AbstractActorVisitor;
import net.sf.orcc.ir.Action;
import net.sf.orcc.ir.ActionScheduler;
import net.sf.orcc.ir.Actor;
import net.sf.orcc.ir.CFGNode;
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
import net.sf.orcc.ir.instructions.Call;
import net.sf.orcc.ir.instructions.Return;
import net.sf.orcc.ir.nodes.BlockNode;
import net.sf.orcc.ir.nodes.IfNode;
import net.sf.orcc.ir.transformations.SSATransformation;
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
public class SDFActionsMerger extends AbstractActorVisitor {

	private Actor actor;

	private String file;

	private Procedure target;

	/**
	 * Creates a new classifier
	 */
	public SDFActionsMerger() {
	}

	private IfNode createActionCall(Expression expr, Procedure body) {
		actor.getProcs().put(body.getName(), body);
		List<CFGNode> thenNodes = new ArrayList<CFGNode>();
		BlockNode node = new BlockNode(target);

		node.add(new Call(new Location(), null, body,
				new ArrayList<Expression>()));

		thenNodes.add(node);
		return new IfNode(target, expr, thenNodes, new ArrayList<CFGNode>(),
				new BlockNode(target));
	}

	private Expression createActionCondition(BlockNode node, Procedure scheduler) {
		actor.getProcs().put(scheduler.getName(), scheduler);
		LocalVariable returnVar = target.newTempLocalVariable(file,
				scheduler.getReturnType(), scheduler.getName() + "_ret");
		node.add(new Call(new Location(), returnVar, scheduler,
				new ArrayList<Expression>()));

		Use use = new Use(returnVar);
		return new VarExpr(use);
	}

	/**
	 * Creates an isSchedulable procedure for the given input pattern.
	 * 
	 * @param input
	 *            an input pattern
	 * @return a procedure
	 */
	private Procedure createIsSchedulable(Pattern input) {
		Procedure procedure = new Procedure("isSchedulable_SDF",
				new Location(), IrFactory.eINSTANCE.createTypeBool());

		LocalVariable result = procedure.newTempLocalVariable(file,
				IrFactory.eINSTANCE.createTypeBool(), "result");

		// create "then" nodes
		Assign thenAssign = new Assign(result, new BoolExpr(true));
		BlockNode blockNode = new BlockNode(procedure);
		blockNode.add(thenAssign);
		procedure.getNodes().add(blockNode);

		// add the return
		BlockNode block = BlockNode.getLast(procedure);
		block.add(new Return(new VarExpr(new Use(result))));

		// convert to SSA form
		new SSATransformation().visit(procedure);

		return procedure;
	}

	private void examineState(DirectedGraph<State, UniqueEdge> graph,
			State source) {
		Iterator<UniqueEdge> it = graph.outgoingEdgesOf(source).iterator();
		if (it.hasNext()) {
			boolean mergeActions = true;
			List<Action> actions = new ArrayList<Action>();

			UniqueEdge edge = it.next();
			State target = graph.getEdgeTarget(edge);
			actions.add((Action) edge.getObject());

			while (it.hasNext()) {
				edge = it.next();
				if (target != graph.getEdgeTarget(edge)) {
					mergeActions = false;
					break;
				}
				actions.add((Action) edge.getObject());
			}

			if (mergeActions) {
				List<Action> newActions = tryAndMerge(actions);
				if (actions.size() > 1 && newActions.size() == 1) {
					System.out.println("in actor " + actor.getName()
							+ ", state " + source + ", merging actions "
							+ actions);
					// TODO : this is a fix that may be bugged in case of
					// transition
					// than need a merged action in the list as a unique action
					// Update graph with the new action
					List<UniqueEdge> upEdges = new ArrayList<UniqueEdge>();
					for (UniqueEdge checkEdge : graph.edgeSet()) {
						if (actions.contains(checkEdge.getObject())) {
							upEdges.add(checkEdge);
						}
					}

					UniqueEdge newEdge = new UniqueEdge(newActions.get(0));

					// Remove all transitions and create a new one
					for (UniqueEdge upEdge : upEdges) {
						State sourceState = graph.getEdgeSource(upEdge);
						State targetState = graph.getEdgeTarget(upEdge);
						graph.removeEdge(upEdge);

						graph.addEdge(sourceState, targetState, newEdge);

					}
				}
			}
		}
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
		// creates a isSchedulable function
		Procedure scheduler = createIsSchedulable(input);

		// merges actions
		Procedure body = mergeSDFBodies(input, output, actions);

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

	private Procedure mergeSDFBodies(Pattern input, Pattern output,
			List<Action> actions) {
		target = new Procedure("SDF", new Location(),
				IrFactory.eINSTANCE.createTypeVoid());
/*
		for (Entry<Port, Integer> entry : input.entrySet()) {
			Port port = entry.getKey();
			int numTokens = entry.getValue();
			Type type = IrFactory.eINSTANCE.createTypeList(numTokens,
					port.getType());
			LocalVariable variable = target.newTempLocalVariable(file, type,
					port.getName());
			block.add(new Read(port, numTokens, variable));
		}*/

		// Launch action
		List<CFGNode> elseNodes = target.getNodes();

		for (Action action : actions) {
			BlockNode thenBlock = BlockNode.getFirst(target, elseNodes);
			Expression callExpr = createActionCondition(thenBlock,
					action.getScheduler());
			IfNode ifNode = createActionCall(callExpr, action.getBody());
			elseNodes.add(ifNode);
			elseNodes = ifNode.getElseNodes();
		}
		// TODO Add input pattern to the merged action
		/*
		 * for (Entry<Port, Integer> entry : output.entrySet()) { Port port =
		 * entry.getKey(); int numTokens = entry.getValue(); Type type =
		 * IrFactory.eINSTANCE.createTypeList(numTokens, port.getType());
		 * LocalVariable variable = target.newTempLocalVariable(file, type,
		 * port.getName()); block.add(new Write(port, numTokens, variable)); }
		 */

		return target;
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
			return new ArrayList<Action>(actions);
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
					return new ArrayList<Action>(actions);
				}
			}

			return mergeActions(actions, input, output);
		}
	}

	private FSM updateFSM(State initialState,
			DirectedGraph<State, UniqueEdge> graph) {
		FSM fsm = new FSM();

		// Set states of the fsm
		for (State state : graph.vertexSet()) {
			fsm.addState(state.getName());
		}

		// Set initial state
		fsm.setInitialState(initialState.toString());

		// Set transitions of the fsm
		for (UniqueEdge edge : graph.edgeSet()) {
			State source = graph.getEdgeSource(edge);
			State target = graph.getEdgeTarget(edge);
			Action action = (Action) edge.getObject();
			fsm.addTransition(source.getName(), action, target.getName());
		}

		return fsm;
	}

	@Override
	public void visit(Actor actor) {
		this.actor = actor;
		this.file = actor.getFile();

		ActionScheduler scheduler = actor.getActionScheduler();
		FSM fsm = scheduler.getFsm();
		if (fsm == null) {
			List<Action> actions = scheduler.getActions();
			List<Action> mergedActions = tryAndMerge(scheduler.getActions());
			actions.clear();
			actions.addAll(mergedActions);
		} else {
			DirectedGraph<State, UniqueEdge> graph = fsm.getGraph();
			for (State state : graph.vertexSet()) {
				examineState(graph, state);
			}

			// Update the fsm
			scheduler.setFsm(updateFSM(fsm.getInitialState(), graph));
		}
	}

}
