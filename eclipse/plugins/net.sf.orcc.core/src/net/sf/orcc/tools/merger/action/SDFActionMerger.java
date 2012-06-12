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
package net.sf.orcc.tools.merger.action;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.sf.orcc.df.Action;
import net.sf.orcc.df.Actor;
import net.sf.orcc.df.DfFactory;
import net.sf.orcc.df.FSM;
import net.sf.orcc.df.Pattern;
import net.sf.orcc.df.State;
import net.sf.orcc.df.Transition;
import net.sf.orcc.df.util.DfVisitor;
import net.sf.orcc.graph.Edge;
import net.sf.orcc.ir.Block;
import net.sf.orcc.ir.BlockBasic;
import net.sf.orcc.ir.BlockIf;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.InstAssign;
import net.sf.orcc.ir.IrFactory;
import net.sf.orcc.ir.Param;
import net.sf.orcc.ir.Procedure;
import net.sf.orcc.ir.Var;
import net.sf.orcc.ir.transform.SSATransformation;
import net.sf.orcc.ir.util.IrUtil;

import org.eclipse.emf.ecore.util.EcoreUtil;

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
public class SDFActionMerger extends DfVisitor<Object> {

	private Actor actor;

	private Procedure target;

	/**
	 * Creates a new classifier
	 */
	public SDFActionMerger() {
	}

	@Override
	public Object caseActor(Actor actor) {
		this.actor = actor;

		FSM fsm = actor.getFsm();
		if (fsm == null) {
			List<Action> actions = actor.getActionsOutsideFsm();
			List<Action> mergedActions = tryAndMerge(actions);
			actions.clear();
			actions.addAll(mergedActions);
		} else {
			for (State state : fsm.getStates()) {
				examineState(fsm, state);
			}
		}
		return null;
	}

	private BlockIf createActionCall(Expression expr, Procedure body,
			Pattern inputPattern, Pattern outputPattern) {
		BlockIf nodeIf = IrFactory.eINSTANCE.createBlockIf();
		nodeIf.setJoinBlock(IrFactory.eINSTANCE.createBlockBasic());
		nodeIf.setCondition(expr);

		List<Expression> callExprs = setProcedureParameters(body, inputPattern,
				outputPattern);
		actor.getProcs().add(body);
		List<Block> thenNodes = nodeIf.getThenBlocks();
		BlockBasic node = IrFactory.eINSTANCE.createBlockBasic();

		node.add(IrFactory.eINSTANCE.createInstCall(0, null, body, callExprs));

		thenNodes.add(node);

		return nodeIf;
	}

	private Expression createActionCondition(BlockBasic node,
			Procedure scheduler, Pattern inputPattern, Pattern outputPattern) {

		List<Expression> callExprs = setProcedureParameters(scheduler,
				inputPattern, outputPattern);

		actor.getProcs().add(scheduler);
		Var returnVar = target.newTempLocalVariable(scheduler.getReturnType(),
				scheduler.getName() + "_ret");
		node.add(IrFactory.eINSTANCE.createInstCall(0, returnVar, scheduler,
				callExprs));

		return IrFactory.eINSTANCE.createExprVar(returnVar);
	}

	/**
	 * Creates an isSchedulable procedure for the given input pattern.
	 * 
	 * @param input
	 *            an input pattern
	 * @return a procedure
	 */
	private Procedure createIsSchedulable(Pattern input) {
		Procedure procedure = IrFactory.eINSTANCE.createProcedure(
				"isSchedulable_SDF", 0, IrFactory.eINSTANCE.createTypeBool());

		Var result = procedure.newTempLocalVariable(
				IrFactory.eINSTANCE.createTypeBool(), "result");

		// create "then" nodes
		InstAssign thenAssign = IrFactory.eINSTANCE.createInstAssign(result,
				IrFactory.eINSTANCE.createExprBool(true));
		BlockBasic nodeBlock = IrFactory.eINSTANCE.createBlockBasic();
		nodeBlock.add(thenAssign);
		procedure.getBlocks().add(nodeBlock);

		// add the return
		BlockBasic block = procedure.getLast();
		block.add(IrFactory.eINSTANCE.createInstReturn((IrFactory.eINSTANCE
				.createExprVar(result))));

		// convert to SSA form
		new SSATransformation().doSwitch(procedure);

		return procedure;
	}

	/**
	 * If all outgoing transitions from the given source state are to the same
	 * target state, and all associated actions can be merged into one, merges
	 * them and updates the given FSM in-place.
	 * 
	 * @param fsm
	 *            the FSM to which the state belongs
	 * @param source
	 *            a state
	 */
	private void examineState(FSM fsm, State source) {
		Iterator<Edge> it = source.getOutgoing().iterator();
		if (it.hasNext()) {
			boolean mergeActions = true;
			List<Action> actions = new ArrayList<Action>();

			Transition transition = (Transition) it.next();
			State target = transition.getTarget();
			actions.add(transition.getAction());

			while (it.hasNext()) {
				transition = (Transition) it.next();
				if (target != transition.getTarget()) {
					mergeActions = false;
					break;
				}
				actions.add(transition.getAction());
			}

			if (mergeActions) {
				List<Action> newActions = tryAndMerge(actions);
				if (actions.size() > 1 && newActions.size() == 1) {
					System.out.println("in actor " + actor.getName()
							+ ", state " + source + ", merging actions "
							+ actions);

					source.getOutgoing().clear();
					fsm.addTransition(source, newActions.get(0), target);
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
	private List<Action> mergeActions(List<Action> actions) {
		Pattern input = actions.get(0).getInputPattern();
		Pattern output = actions.get(0).getInputPattern();
		Pattern peek = actions.get(0).getPeekPattern();

		// creates a isSchedulable function
		Procedure scheduler = createIsSchedulable(input);

		// merges actions
		Procedure body = mergeSDFBodies(actions);

		Action action = DfFactory.eINSTANCE.createAction(
				DfFactory.eINSTANCE.createTag(), input, output, peek,
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
		target = IrFactory.eINSTANCE.createProcedure("SDF", 0,
				IrFactory.eINSTANCE.createTypeVoid());

		// Launch action
		List<Block> elseNodes = target.getBlocks();

		for (Action action : actions) {
			Pattern input = action.getInputPattern();
			Pattern output = action.getOutputPattern();

			BlockBasic thenBlock = IrUtil.getFirst(elseNodes);
			Expression callExpr = createActionCondition(thenBlock,
					action.getScheduler(), input, output);
			BlockIf nodeIf = createActionCall(callExpr, action.getBody(),
					input, output);
			elseNodes.add(nodeIf);
			elseNodes = nodeIf.getElseBlocks();
		}

		BlockBasic lastBlock = target.getLast();
		lastBlock.add(IrFactory.eINSTANCE.createInstReturn());

		return target;
	}

	private List<Expression> setProcedureParameters(Procedure procedure,
			Pattern inputPattern, Pattern outputPattern) {
		List<Expression> exprs = new ArrayList<Expression>();

		List<Param> parameters = procedure.getParameters();

		// Add inputs to procedure parameters
		for (Var var : inputPattern.getVariables()) {
			parameters.add(IrFactory.eINSTANCE.createParam(var));
			exprs.add(IrFactory.eINSTANCE.createExprVar(var));
		}

		// Add outputs to procedure parameters
		for (Var var : outputPattern.getVariables()) {
			parameters.add(IrFactory.eINSTANCE.createParam(var));
			exprs.add(IrFactory.eINSTANCE.createExprVar(var));
		}

		return exprs;
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
				if (!EcoreUtil.equals(input, currentAction.getInputPattern())
						|| !EcoreUtil.equals(output,
								currentAction.getOutputPattern())) {
					// one pattern is not equal to another
					return new ArrayList<Action>(actions);
				}
			}

			return mergeActions(actions);
		}
	}

}
