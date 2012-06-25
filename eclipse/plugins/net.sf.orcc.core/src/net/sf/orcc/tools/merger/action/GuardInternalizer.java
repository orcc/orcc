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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.orcc.df.Action;
import net.sf.orcc.df.Actor;
import net.sf.orcc.df.DfFactory;
import net.sf.orcc.df.FSM;
import net.sf.orcc.df.Pattern;
import net.sf.orcc.df.Port;
import net.sf.orcc.df.State;
import net.sf.orcc.df.Transition;
import net.sf.orcc.graph.Edge;
import net.sf.orcc.ir.Block;
import net.sf.orcc.ir.BlockBasic;
import net.sf.orcc.ir.BlockIf;
import net.sf.orcc.ir.Def;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.InstLoad;
import net.sf.orcc.ir.InstReturn;
import net.sf.orcc.ir.InstStore;
import net.sf.orcc.ir.IrFactory;
import net.sf.orcc.ir.Procedure;
import net.sf.orcc.ir.Use;
import net.sf.orcc.ir.Var;
import net.sf.orcc.ir.util.AbstractIrVisitor;
import net.sf.orcc.ir.util.IrUtil;

import org.eclipse.emf.ecore.util.EcoreUtil;

/**
 * This class defines a transformation that merges actions that have the same
 * input/output patterns together. As a matter of fact, it is possible to
 * represent SDF actors with several actions that have guards on input tokens,
 * which means that when interpreted by the abstract interpreter, these actors
 * would be classified as dynamic, and we do not want that.
 * 
 * @author Matthieu Wipliez
 * @author Herve Yviquel
 * 
 */
public class GuardInternalizer {

	private class UpdatePortAccesses extends AbstractIrVisitor<Void> {

		Pattern inputPattern;
		Pattern outputPattern;

		public UpdatePortAccesses(Pattern inputPattern, Pattern outputPattern) {
			this.inputPattern = inputPattern;
			this.outputPattern = outputPattern;
		}

		@Override
		public Void caseInstLoad(InstLoad load) {
			Use use = load.getSource();
			Var var = use.getVariable();
			if (var.eContainer() instanceof Pattern) {
				Pattern oldPattern = (Pattern) var.eContainer();
				Port port = oldPattern.getPort(var);
				use.setVariable(inputPattern.getVariable(port));
			}
			return null;
		}

		@Override
		public Void caseInstStore(InstStore store) {
			Def def = store.getTarget();
			Var var = def.getVariable();
			if (var.eContainer() instanceof Pattern) {
				Pattern oldPattern = (Pattern) var.eContainer();
				Port port = oldPattern.getPort(var);
				def.setVariable(outputPattern.getVariable(port));
			}
			return null;
		}

	}

	private Actor actor;
	private int i;

	private final DfFactory dfFactory = DfFactory.eINSTANCE;
	private final IrFactory irFactory = IrFactory.eINSTANCE;

	/**
	 * Creates a new classifier
	 */
	public GuardInternalizer() {
	}

	private Expression getCondition(Action action) {
		BlockBasic block = IrUtil.getLast(action.getScheduler().getBlocks());
		InstReturn ret = (InstReturn) block.getInstructions().get(
				block.getInstructions().size() - 1);
		EcoreUtil.remove(ret);
		return ret.getValue();
	}

	/**
	 * Creates an isSchedulable.
	 * 
	 * @return a procedure
	 */
	private Procedure createScheduler(String name) {
		Procedure procedure = irFactory.createProcedure(
				"isSchedulable_" + name, 0, irFactory.createTypeBool());

		BlockBasic block = procedure.getLast();
		block.add(irFactory.createInstReturn((irFactory.createExprBool(true))));

		return procedure;
	}

	/**
	 * Merges the given actions to a single action.
	 * 
	 * @param actions
	 *            a list of actions that have the same input/output patterns
	 * @return a new action
	 */
	private Action mergeActions(List<Action> actions) {
		if (actions.size() > 1) {
			System.out.println("Internalize >> Actor " + actor.getSimpleName()
					+ " - Actions " + actions);

			String name = "";
			for (Action mergedAction : actions) {
				name = name + mergedAction.getName();
			}
			name = name + i;
			i++;

			Pattern input = EcoreUtil.copy(actions.get(0).getInputPattern());
			Pattern output = EcoreUtil.copy(actions.get(0).getOutputPattern());
			Pattern peek = dfFactory.createPattern();

			Procedure scheduler = createScheduler(name);
			Procedure body = mergeBodies(name, input, output, actions);

			Action action = dfFactory.createAction(name, input, output, peek,
					scheduler, body);

			actor.getActions().add(action);

			return action;
		} else {
			return actions.get(0);
		}
	}

	private Procedure mergeBodies(String name, Pattern input, Pattern output,
			List<Action> actions) {
		Procedure body = irFactory.createProcedure(name, 0,
				irFactory.createTypeVoid());
		List<Block> elseBlocks = body.getBlocks();

		for (Action action : actions) {
			Action actionCopy = IrUtil.copy(action);
			new UpdatePortAccesses(input, output)
					.doSwitch(actionCopy.getBody());
			new UpdatePortAccesses(input, output).doSwitch(actionCopy
					.getScheduler());

			BlockIf blockIf = irFactory.createBlockIf();
			blockIf.setCondition(getCondition(actionCopy));

			renameVariables(actionCopy.getScheduler(), body);
			body.getLocals().addAll(actionCopy.getScheduler().getLocals());
			elseBlocks.addAll(actionCopy.getScheduler().getBlocks());

			renameVariables(actionCopy.getBody(), body);
			body.getLocals().addAll(actionCopy.getBody().getLocals());
			blockIf.getThenBlocks().addAll(actionCopy.getBody().getBlocks());

			elseBlocks.add(blockIf);
			elseBlocks = blockIf.getElseBlocks();
		}

		BlockBasic lastBlock = body.getLast();
		lastBlock.add(irFactory.createInstReturn());

		return body;
	}

	private List<List<Action>> sortByPattern(List<Action> actions) {
		List<List<Action>> sortedActions = new ArrayList<List<Action>>();
		for (Action action : actions) {
			Pattern input = action.getInputPattern();
			Pattern output = action.getOutputPattern();
			boolean founded = false;
			for (int i = 0; i < sortedActions.size(); i++) {
				Pattern testedInput = sortedActions.get(i).get(0)
						.getInputPattern();
				Pattern testedOutput = sortedActions.get(i).get(0)
						.getOutputPattern();
				if (testedInput.isSupersetOf(input)
						&& testedOutput.isSupersetOf(output)
						&& input.isSupersetOf(testedInput)
						&& output.isSupersetOf(testedOutput)) {
					founded = true;
					sortedActions.get(i).add(action);
				}
			}

			if (!founded) {
				List<Action> newConfiguration = new ArrayList<Action>(1);
				newConfiguration.add(action);
				sortedActions.add(newConfiguration);
			}
		}
		return sortedActions;
	}

	private Map<State, List<Action>> sortByTargetState(State currentState) {
		Map<State, List<Action>> sortedActions = new HashMap<State, List<Action>>();
		for (Edge edge : currentState.getOutgoing()) {
			Transition transition = (Transition) edge;
			State targetState = (State) edge.getTarget();
			if (sortedActions.containsKey(targetState)) {
				sortedActions.get(targetState).add(transition.getAction());
			} else {
				List<Action> newList = new ArrayList<Action>(1);
				newList.add(transition.getAction());
				sortedActions.put(targetState, newList);
			}
		}
		return sortedActions;
	}

	public void transform(Actor actor) {
		this.actor = actor;
		this.i = 0;

		if (actor.hasFsm()) {
			FSM fsm = actor.getFsm();
			for (State currentState : fsm.getStates()) {
				Map<State, List<Action>> sortedActionsByTarget = sortByTargetState(currentState);
				fsm.removeEdges(currentState.getOutgoing());
				for (State targetState : sortedActionsByTarget.keySet()) {
					for (List<Action> mergableActions : sortByPattern(sortedActionsByTarget
							.get(targetState))) {
						Action action = mergeActions(mergableActions);
						fsm.add(dfFactory.createTransition(currentState,
								action, targetState));
					}
				}
			}
		}

		List<List<Action>> sortedActions = sortByPattern(actor
				.getActionsOutsideFsm());
		actor.getActionsOutsideFsm().clear();
		for (List<Action> mergableActions : sortedActions) {
			Action action = mergeActions(mergableActions);
			actor.getActionsOutsideFsm().add(action);
		}
	}

	private void renameVariables(Procedure oldProc, Procedure newProc) {
		for (Var var : oldProc.getLocals()) {
			String varName = var.getName();
			for (int i = 0; newProc.getLocal(varName) != null
					|| actor.getStateVar(varName) != null
					|| (oldProc.getLocal(varName) != null && oldProc
							.getLocal(varName) != var); i++) {
				varName = var.getName() + i;
			}
			var.setName(varName);
		}
	}
}
