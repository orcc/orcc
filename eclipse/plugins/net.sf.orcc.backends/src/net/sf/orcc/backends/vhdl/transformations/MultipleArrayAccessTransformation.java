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
package net.sf.orcc.backends.vhdl.transformations;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import net.sf.orcc.ir.Action;
import net.sf.orcc.ir.ActionScheduler;
import net.sf.orcc.ir.Actor;
import net.sf.orcc.ir.CFGNode;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.FSM;
import net.sf.orcc.ir.Instruction;
import net.sf.orcc.ir.Tag;
import net.sf.orcc.ir.FSM.State;
import net.sf.orcc.ir.IrFactory;
import net.sf.orcc.ir.Location;
import net.sf.orcc.ir.Pattern;
import net.sf.orcc.ir.Procedure;
import net.sf.orcc.ir.Variable;
import net.sf.orcc.ir.expr.BoolExpr;
import net.sf.orcc.ir.instructions.Load;
import net.sf.orcc.ir.instructions.Return;
import net.sf.orcc.ir.instructions.Store;
import net.sf.orcc.ir.nodes.BlockNode;
import net.sf.orcc.ir.nodes.IfNode;
import net.sf.orcc.ir.transformations.AbstractActorTransformation;
import net.sf.orcc.util.UniqueEdge;

import org.jgrapht.DirectedGraph;

/**
 * This transformation transforms an actor so that there is at most one access
 * (read or write) per each given array accessed by an action. This pass should
 * be run after inline.
 * 
 * @author Matthieu Wipliez
 * @author Nicolas Siret
 * 
 */
public class MultipleArrayAccessTransformation extends
		AbstractActorTransformation {

	private class CodeMover extends AbstractActorTransformation {

		public CodeMover() {

		}

		public void moveCode(Procedure oldProc, Procedure newProc) {
			// move instructions
			BlockNode block = BlockNode.getLast(newProc);
			ListIterator<Instruction> itInstr = MultipleArrayAccessTransformation.this.itInstruction;
			Instruction instruction = itInstr.previous();
			itInstr.remove();
			block.add(instruction);
			while (itInstr.hasNext()) {
				instruction = itInstr.next();
				itInstr.remove();
				block.add(instruction);
			}

			// move next nodes
			ListIterator<CFGNode> itNode = MultipleArrayAccessTransformation.this.itNode;
			while (itNode.hasNext()) {
				CFGNode node = itNode.next();
				itNode.remove();
				newProc.getNodes().add(node);
			}
		}

	}

	private Action action;

	private FSM fsm;

	private Map<Variable, Integer> numRW;

	private String sourceName;

	private Map<String, Integer> stateNames;

	private String targetName;

	/**
	 * Adds an FSM to the given action scheduler.
	 * 
	 * @param actionScheduler
	 *            action scheduler
	 */
	private void addFsm(ActionScheduler actionScheduler) {
		fsm = new FSM();
		fsm.setInitialState("init");
		fsm.addState("init");
		for (Action action : actionScheduler.getActions()) {
			fsm.addTransition("init", "init", action);
		}

		actionScheduler.getActions().clear();
		actionScheduler.setFsm(fsm);
	}

	/**
	 * Creates a unique action/state name.
	 * 
	 * @return a unique action/state name
	 */
	private String createName() {
		// get unique state name
		String stateName = targetName + "_" + action.getName();
		Integer count = stateNames.get(stateName);
		if (count == null) {
			count = 1;
		}
		stateNames.put(stateName, count + 1);
		return stateName + "_" + count;
	}

	/**
	 * Creates a new empty action with the given name.
	 * 
	 * @param name
	 *            action name
	 * @return a new empty action with the given name
	 */
	private Action createNewAction(String name) {
		// scheduler
		Procedure scheduler = new Procedure(name, new Location(),
				IrFactory.eINSTANCE.createTypeBool());
		BlockNode block = new BlockNode(scheduler);
		block.add(new Return(new BoolExpr(true)));
		scheduler.getNodes().add(block);

		// body
		Procedure body = new Procedure(name, new Location(),
				IrFactory.eINSTANCE.createTypeVoid());
		block = new BlockNode(scheduler);
		block.add(new Return(null));
		scheduler.getNodes().add(block);

		// tag
		Tag tag = new Tag();
		tag.add(name);

		Action action = new Action(new Location(), tag, new Pattern(),
				new Pattern(), scheduler, body);
		return action;
	}

	@Override
	public void transform(Actor actor) {
		this.actor = actor;
		numRW = new HashMap<Variable, Integer>();
		stateNames = new HashMap<String, Integer>();

		fsm = actor.getActionScheduler().getFsm();
		if (fsm == null) {
			List<Action> actions = new ArrayList<Action>(actor
					.getActionScheduler().getActions());
			for (Action action : actions) {
				sourceName = "init";
				targetName = "init";
				visit(action);
			}
		} else {
			DirectedGraph<State, UniqueEdge> graph = fsm.getGraph();
			Set<UniqueEdge> edges = graph.edgeSet();
			for (UniqueEdge edge : edges) {
				State source = graph.getEdgeSource(edge);
				sourceName = source.getName();

				State target = graph.getEdgeTarget(edge);
				targetName = target.getName();

				Action action = (Action) edge.getObject();
				visit(action);
			}
		}
	}

	private void updateTransitions(Action newAction) {
		// add an FSM if the actor does not have one
		if (fsm == null) {
			addFsm(actor.getActionScheduler());
		}

		// add state
		String stateName = newAction.getName();
		fsm.addState(stateName);

		// update transitions
		fsm.removeTransition(sourceName, targetName, action);
		fsm.addTransition(sourceName, stateName, action);
		fsm.addTransition(stateName, targetName, newAction);
	}

	@Override
	public void visit(Action action) {
		this.action = action;
		numRW.clear();
		
		// we are only interested in the body
		visit(action.getBody());
	}

	@Override
	public void visit(IfNode ifNode) {
		// the idea is that branches of a "if" are exclusive
		// so the number of accesses to consider is the max in each branch
		// rather than the sum

		Map<Variable, Integer> before = new HashMap<Variable, Integer>(numRW);
		visit(ifNode.getThenNodes());
		Map<Variable, Integer> numThen = numRW;

		numRW = new HashMap<Variable, Integer>(before);
		visit(ifNode.getElseNodes());
		Map<Variable, Integer> numElse = numRW;
		numRW = before;

		for (Entry<Variable, Integer> entryT : numThen.entrySet()) {
			Variable var = entryT.getKey();
			if (numElse.containsKey(var)) {
				numRW.put(var, Math.max(numThen.get(var), numElse.get(var)));
			}
		}
		for (Entry<Variable, Integer> entryE : numElse.entrySet()) {
			Variable var = entryE.getKey();
			if (numThen.containsKey(var)) {
				numRW.put(var, Math.max(numThen.get(var), numElse.get(var)));
			}
		}

		visit(ifNode.getJoinNode());
	}

	@Override
	public void visit(Load load) {
		visitLoadStore(load.getSource().getVariable(), load.getIndexes());
	}

	@Override
	public void visit(Store store) {
		visitLoadStore(store.getTarget(), store.getIndexes());
	}

	/**
	 * Visits a load or a store, and updates the numRW map.
	 * 
	 * @param variable
	 *            a variable
	 * @param indexes
	 *            a list of indexes
	 */
	private void visitLoadStore(Variable variable, List<Expression> indexes) {
		if (!indexes.isEmpty()) {
			Integer numAccesses = numRW.get(variable);
			if (numAccesses == null) {
				numRW.put(variable, 1);
			} else {
				// new name for action/state
				String newActionName = createName();

				// create new action
				Action newAction = createNewAction(newActionName);

				// move code
				new CodeMover().moveCode(action.getBody(), newAction.getBody());

				// update transitions
				updateTransitions(newAction);
				
				// set new source state to the new state name
				sourceName = newActionName;
				visit(newAction);
			}
		}
	}

}
