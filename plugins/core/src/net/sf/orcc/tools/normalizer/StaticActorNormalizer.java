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
package net.sf.orcc.tools.normalizer;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import net.sf.orcc.classes.CSDFActorClass;
import net.sf.orcc.ir.Action;
import net.sf.orcc.ir.ActionScheduler;
import net.sf.orcc.ir.Actor;
import net.sf.orcc.ir.CFGNode;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.LocalVariable;
import net.sf.orcc.ir.Location;
import net.sf.orcc.ir.Pattern;
import net.sf.orcc.ir.Port;
import net.sf.orcc.ir.Procedure;
import net.sf.orcc.ir.StateVariable;
import net.sf.orcc.ir.Tag;
import net.sf.orcc.ir.Type;
import net.sf.orcc.ir.Use;
import net.sf.orcc.ir.Variable;
import net.sf.orcc.ir.expr.BinaryExpr;
import net.sf.orcc.ir.expr.BinaryOp;
import net.sf.orcc.ir.expr.BoolExpr;
import net.sf.orcc.ir.expr.IntExpr;
import net.sf.orcc.ir.expr.VarExpr;
import net.sf.orcc.ir.instructions.Assign;
import net.sf.orcc.ir.instructions.Call;
import net.sf.orcc.ir.instructions.HasTokens;
import net.sf.orcc.ir.instructions.Read;
import net.sf.orcc.ir.instructions.Return;
import net.sf.orcc.ir.instructions.Store;
import net.sf.orcc.ir.instructions.Write;
import net.sf.orcc.ir.nodes.BlockNode;
import net.sf.orcc.ir.nodes.WhileNode;
import net.sf.orcc.ir.type.BoolType;
import net.sf.orcc.ir.type.IntType;
import net.sf.orcc.ir.type.ListType;
import net.sf.orcc.ir.type.VoidType;
import net.sf.orcc.util.OrderedMap;

/**
 * This class defines a normalizer for static actors.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class StaticActorNormalizer {

	/**
	 * This class contains code to transform a pattern to IR code (not entirely
	 * valid because not in SSA form at this point).
	 * 
	 * @author Matthieu Wipliez
	 * 
	 */
	private class MyPatternVisitor implements PatternVisitor {

		private int depth;

		private List<LocalVariable> indexes;

		private List<CFGNode> nodes;

		private Procedure procedure;

		public MyPatternVisitor(Procedure procedure) {
			this.procedure = procedure;
			nodes = procedure.getNodes();
			indexes = new ArrayList<LocalVariable>();
		}

		@Override
		public void visit(LoopPattern pattern) {
			depth++;
			if (indexes.size() < depth) {
				LocalVariable varDef = new LocalVariable(true, depth - 1,
						new Location(), "loop", null, new BoolType());
				variables.add(actor.getFile(), varDef.getLocation(),
						varDef.getName(), varDef);
				indexes.add(varDef);
			}

			LocalVariable loopVar = indexes.get(depth - 1);

			// init var
			BlockNode block = BlockNode.getLast(procedure, nodes);
			Assign assign = new Assign(loopVar, new IntExpr(0));
			block.add(assign);

			// create while
			List<CFGNode> oldNodes = nodes;
			nodes = new ArrayList<CFGNode>();

			WhileNode whileNode = new WhileNode(procedure, null, nodes,
					new BlockNode(procedure));
			oldNodes.add(whileNode);

			// assign condition
			Expression condition = new BinaryExpr(new VarExpr(new Use(loopVar,
					whileNode)), BinaryOp.LT, new IntExpr(
					pattern.getNumIterations()), new BoolType());
			whileNode.setValue(condition);

			// accept sub pattern
			pattern.getPattern().accept(this);

			// add assign
			block = BlockNode.getLast(procedure, nodes);
			assign = new Assign(loopVar, null);
			assign.setValue(new BinaryExpr(
					new VarExpr(new Use(loopVar, assign)), BinaryOp.PLUS,
					new IntExpr(1), new IntType(32)));
			block.add(assign);

			// restore stuff
			this.nodes = oldNodes;
			depth--;
		}

		@Override
		public void visit(SequentialPattern pattern) {
			for (ExecutionPattern subPattern : pattern) {
				subPattern.accept(this);
			}
		}

		@Override
		public void visit(SimplePattern pattern) {
			BlockNode block = BlockNode.getLast(procedure, nodes);
			Call call = new Call(new Location(), null, pattern.getAction()
					.getBody(), new ArrayList<Expression>());
			block.add(call);
		}

	}

	private static final String ACTION_NAME = "xxx";

	private static final String SCHEDULER_NAME = "isSchedulable_" + ACTION_NAME;

	private Actor actor;

	private OrderedMap<Variable> stateVars;

	private CSDFActorClass staticCls;

	private OrderedMap<Variable> variables;

	/**
	 * Creates a new normalizer
	 */
	public StaticActorNormalizer(Actor actor) {
		this.actor = actor;
		staticCls = (CSDFActorClass) actor.getActorClass();
		stateVars = actor.getStateVars();
	}

	/**
	 * Adds state variables to hold the values of data read/stored in the given
	 * pattern. Initializes count to 0.
	 * 
	 * @param procedure
	 *            body of the target action
	 * @param pattern
	 *            input or output pattern
	 */
	private void addStateVariables(Procedure procedure, Pattern pattern) {
		BlockNode block = BlockNode.getLast(procedure, procedure.getNodes());
		for (Entry<Port, Integer> entry : pattern.entrySet()) {
			Port port = entry.getKey();
			int numTokens = entry.getValue();

			Type type = new ListType(numTokens, port.getType());
			StateVariable var = new StateVariable(new Location(), type,
					port.getName(), false);
			stateVars.add(actor.getFile(), var.getLocation(), var.getName(),
					var);

			StateVariable varCount = new StateVariable(new Location(),
					new IntType(32), port.getName() + "_count", true, 0);
			stateVars.add(actor.getFile(), varCount.getLocation(),
					varCount.getName(), varCount);

			Store store = new Store(varCount, new ArrayList<Expression>(),
					new IntExpr(0));
			block.add(store);
		}
	}

	/**
	 * Adds the given action to the actor and to its action scheduler.
	 * 
	 * @param action
	 *            an action
	 */
	private void addStaticAction(Action action) {
		actor.getActionScheduler().getActions().add(action);
		actor.getActions().add(action);
	}

	/**
	 * Cleans up the actor.
	 */
	private void cleanupActor() {
		// removes FSM
		ActionScheduler sched = actor.getActionScheduler();
		sched.setFsm(null);

		// removes all actions from action scheduler
		sched.getActions().clear();

		// all action scheduler now just return true
		for (Action action : actor.getActions()) {
			Procedure scheduler = action.getScheduler();
			Iterator<Variable> it = scheduler.getLocals().iterator();
			while (it.hasNext()) {
				it.next();
				it.remove();
			}

			List<CFGNode> nodes = scheduler.getNodes();
			nodes.clear();
			BlockNode block = new BlockNode(scheduler);
			nodes.add(block);
			block.add(new Return(new Location(), new BoolExpr(true)));
		}
	}

	/**
	 * Creates the static action for this actor.
	 * 
	 * @return a static action
	 */
	private Action createAction() {
		Procedure scheduler = createScheduler();
		Procedure body = createBody();

		Pattern input = staticCls.getInputPattern();
		Pattern output = staticCls.getOutputPattern();
		Tag tag = new Tag();
		tag.add(ACTION_NAME);

		return new Action(new Location(), tag, input, output, scheduler, body);
	}

	/**
	 * Creates the body of the static action.
	 * 
	 * @return the body of the static action
	 */
	private Procedure createBody() {
		Location location = new Location();
		variables = new OrderedMap<Variable>();
		List<CFGNode> nodes = new ArrayList<CFGNode>();

		Procedure procedure = new Procedure(ACTION_NAME, false, location,
				new VoidType(), new OrderedMap<Variable>(), variables, nodes);

		// add state variables
		addStateVariables(procedure, staticCls.getInputPattern());
		addStateVariables(procedure, staticCls.getOutputPattern());

		// change accesses to FIFO
		new ChangeFifoArrayAccess().transform(actor);

		// removes read/writes
		new RemoveReadWrites().transform(actor);

		// add read instructions for input pattern
		createReads(procedure);

		// finds a pattern in the actions and visit it
		LoopPatternRecognizer r = new LoopPatternRecognizer();
		ExecutionPattern pattern = r.getPattern(staticCls.getActions());
		System.out.println(pattern);
		pattern.accept(new MyPatternVisitor(procedure));

		// add write instructions for output pattern
		createWrites(procedure);

		return procedure;
	}

	/**
	 * Creates a return instruction that uses the results of the hasTokens tests
	 * previously created.
	 * 
	 * @param block
	 *            block to which return is to be added
	 */
	private void createInputCondition(BlockNode block) {
		Expression value;
		Iterator<Variable> it = variables.iterator();
		if (it.hasNext()) {
			LocalVariable previous = (LocalVariable) it.next();
			value = new VarExpr(new Use(previous, block));

			while (it.hasNext()) {
				LocalVariable thisOne = (LocalVariable) it.next();
				value = new BinaryExpr(value, BinaryOp.LOGIC_AND, new VarExpr(
						new Use(thisOne, block)), new BoolType());
				previous = thisOne;
			}
		} else {
			value = new BoolExpr(true);
		}

		Return returnInstr = new Return(value);
		block.add(returnInstr);
	}

	/**
	 * Creates hasTokens tests for the input pattern of the static class.
	 * 
	 * @param block
	 *            the block to which hasTokens instructions are added
	 */
	private void createInputTests(BlockNode block) {
		Pattern inputPattern = staticCls.getInputPattern();
		int i = 0;
		for (Entry<Port, Integer> entry : inputPattern.entrySet()) {
			Location location = new Location();
			Port port = entry.getKey();
			int numTokens = entry.getValue();

			LocalVariable varDef = new LocalVariable(true, i, new Location(),
					"pattern", null, new BoolType());
			i++;
			variables.add(actor.getFile(), location, varDef.getName(), varDef);

			HasTokens hasTokens = new HasTokens(location, port, numTokens,
					varDef);
			varDef.setInstruction(hasTokens);
			block.add(hasTokens);
		}
	}

	/**
	 * Creates calls to Read instructions.
	 * 
	 * @param procedure
	 *            the body of the static action being created
	 */
	private void createReads(Procedure procedure) {
		Pattern inputPattern = staticCls.getInputPattern();
		BlockNode block = BlockNode.getLast(procedure, procedure.getNodes());
		for (Entry<Port, Integer> entry : inputPattern.entrySet()) {
			Port port = entry.getKey();
			int numTokens = entry.getValue();
			Variable var = stateVars.get(port.getName());

			Read read = new Read(port, numTokens, var);
			block.add(read);
		}
	}

	/**
	 * Creates an "isSchedulable" procedure for the static action of this actor.
	 * 
	 * @return an "isSchedulable" procedure
	 */
	private Procedure createScheduler() {
		Location location = new Location();
		variables = new OrderedMap<Variable>();
		List<CFGNode> nodes = new ArrayList<CFGNode>();

		Procedure procedure = new Procedure(SCHEDULER_NAME, false, location,
				new BoolType(), new OrderedMap<Variable>(), variables, nodes);

		BlockNode block = new BlockNode(procedure);
		nodes.add(block);

		createInputTests(block);
		createInputCondition(block);

		return procedure;
	}

	/**
	 * Creates calls to Write instructions.
	 * 
	 * @param procedure
	 *            the body of the static action being created
	 */
	private void createWrites(Procedure procedure) {
		Pattern inputPattern = staticCls.getOutputPattern();
		BlockNode block = BlockNode.getLast(procedure, procedure.getNodes());
		for (Entry<Port, Integer> entry : inputPattern.entrySet()) {
			Port port = entry.getKey();
			int numTokens = entry.getValue();
			Variable var = stateVars.get(port.getName());

			Write write = new Write(port, numTokens, var);
			block.add(write);
		}
	}

	/**
	 * Normalizes this actor so it fits the given static class.
	 * 
	 * @param staticCls
	 *            a static class
	 */
	public void normalize() {
		Action staticAction = createAction();
		cleanupActor();
		addStaticAction(staticAction);
	}

}
