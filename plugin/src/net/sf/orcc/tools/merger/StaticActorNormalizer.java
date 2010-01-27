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
package net.sf.orcc.tools.merger;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import net.sf.orcc.OrccException;
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
import net.sf.orcc.ir.Tag;
import net.sf.orcc.ir.Use;
import net.sf.orcc.ir.Variable;
import net.sf.orcc.ir.classes.StaticClass;
import net.sf.orcc.ir.expr.BinaryExpr;
import net.sf.orcc.ir.expr.BinaryOp;
import net.sf.orcc.ir.expr.BoolExpr;
import net.sf.orcc.ir.expr.VarExpr;
import net.sf.orcc.ir.instructions.Call;
import net.sf.orcc.ir.instructions.HasTokens;
import net.sf.orcc.ir.instructions.Return;
import net.sf.orcc.ir.nodes.BlockNode;
import net.sf.orcc.ir.type.BoolType;
import net.sf.orcc.ir.type.VoidType;
import net.sf.orcc.util.OrderedMap;

/**
 * This class defines a normalizer for static actors.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class StaticActorNormalizer {

	private static final String ACTION_NAME = "xxx";

	private static final String SCHEDULER_NAME = "isSchedulable_" + ACTION_NAME;

	private Actor actor;

	private StaticClass staticCls;

	private OrderedMap<Variable> variables;

	/**
	 * Creates a new normalizer
	 */
	public StaticActorNormalizer(Actor actor) {
		this.actor = actor;
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
			block.add(new Return(block, new Location(), new BoolExpr(
					new Location(), true)));
		}
	}

	private Procedure createBody() {
		Location location = new Location();
		variables = new OrderedMap<Variable>();
		List<CFGNode> nodes = new ArrayList<CFGNode>();

		Procedure procedure = new Procedure(ACTION_NAME, false, location,
				new VoidType(), new OrderedMap<Variable>(), variables, nodes);

		BlockNode block = new BlockNode(procedure);
		nodes.add(block);

		for (Action action : staticCls.getActions()) {
			createBodyAction(block, action);
		}

		return procedure;
	}

	private void createBodyAction(BlockNode block, Action action) {
		Call call = new Call(block, new Location(), null, action.getBody(),
				new ArrayList<Expression>());
		block.add(call);
	}

	/**
	 * Creates a return instruction that uses the results of the hasTokens tests
	 * previously created.
	 * 
	 * @param block
	 *            block to which return is to be added
	 */
	private void createInputCondition(BlockNode block) {
		Location location = new Location();
		Expression value;
		Iterator<Variable> it = variables.iterator();
		if (it.hasNext()) {
			LocalVariable previous = (LocalVariable) it.next();
			value = new VarExpr(location, new Use(previous, block));

			while (it.hasNext()) {
				LocalVariable thisOne = (LocalVariable) it.next();
				value = new BinaryExpr(location, value, BinaryOp.LOGIC_AND,
						new VarExpr(location, new Use(thisOne, block)),
						new BoolType());
				previous = thisOne;
			}
		} else {
			value = new BoolExpr(location, true);
		}

		Return returnInstr = new Return(block, location, value);
		block.add(returnInstr);
	}

	/**
	 * Creates hasTokens tests for the input pattern of the static class.
	 * 
	 * @param block
	 *            the block to which hasTokens instructions are added
	 * @throws OrccException
	 */
	private void createInputTests(BlockNode block) throws OrccException {
		Pattern inputPattern = staticCls.getInputPattern();
		int i = 0;
		for (Entry<Port, Integer> entry : inputPattern.entrySet()) {
			Location location = new Location();
			Port port = entry.getKey();
			int numTokens = entry.getValue();

			LocalVariable varDef = new LocalVariable(true, i, new Location(),
					"pattern", null, null, new BoolType());
			i++;
			variables.add(actor.getFile(), location, varDef.getName(), varDef);

			HasTokens hasTokens = new HasTokens(block, location, port,
					numTokens, varDef);
			varDef.setInstruction(hasTokens);
			block.add(hasTokens);
		}
	}

	/**
	 * Creates an "isSchedulable" procedure for the static action of this actor.
	 * 
	 * @return an "isSchedulable" procedure
	 * @throws OrccException
	 */
	private Procedure createScheduler() throws OrccException {
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
	 * Normalizes this actor so it fits the given static class.
	 * 
	 * @param staticCls
	 *            a static class
	 * @throws OrccException
	 */
	public void normalize() throws OrccException {
		staticCls = (StaticClass) actor.getActorClass();
		Procedure scheduler = createScheduler();
		Procedure body = createBody();

		Pattern input = staticCls.getInputPattern();
		Pattern output = staticCls.getOutputPattern();
		Tag tag = new Tag();
		tag.add(ACTION_NAME);

		Action staticAction = new Action(new Location(), tag, input, output,
				scheduler, body);

		// removes useless stuff, add the static action
		cleanupActor();
		addStaticAction(staticAction);
	}

}
