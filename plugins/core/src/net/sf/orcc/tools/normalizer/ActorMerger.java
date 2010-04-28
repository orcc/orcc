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
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import net.sf.orcc.OrccException;
import net.sf.orcc.ir.Action;
import net.sf.orcc.ir.ActionScheduler;
import net.sf.orcc.ir.Actor;
import net.sf.orcc.ir.CFGNode;
import net.sf.orcc.ir.Constant;
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
import net.sf.orcc.ir.expr.IntExpr;
import net.sf.orcc.ir.expr.VarExpr;
import net.sf.orcc.ir.instructions.Assign;
import net.sf.orcc.ir.instructions.Call;
import net.sf.orcc.ir.nodes.BlockNode;
import net.sf.orcc.ir.nodes.WhileNode;
import net.sf.orcc.ir.type.BoolType;
import net.sf.orcc.ir.type.IntType;
import net.sf.orcc.ir.type.ListType;
import net.sf.orcc.ir.type.VoidType;
import net.sf.orcc.network.Connection;
import net.sf.orcc.network.Instance;
import net.sf.orcc.network.Network;
import net.sf.orcc.network.Vertex;
import net.sf.orcc.network.transforms.INetworkTransformation;
import net.sf.orcc.tools.staticanalyzer.BufferManager;
import net.sf.orcc.tools.staticanalyzer.FlatSASScheduler;
import net.sf.orcc.tools.staticanalyzer.Iterand;
import net.sf.orcc.tools.staticanalyzer.Schedule;
import net.sf.orcc.util.ActionList;
import net.sf.orcc.util.OrderedMap;

import org.jgrapht.DirectedGraph;

/**
 * This class defines a network transformation that merges actors until a
 * fixpoint is found.
 * 
 * @author Matthieu Wipliez
 * @author Ghislain Roquier
 * 
 */
public class ActorMerger implements INetworkTransformation {

	private static final String ACTION_NAME = "static_action";

	private static final String SCHEDULER_NAME = "isSchedulable_" + ACTION_NAME;

	private DirectedGraph<Vertex, Connection> graph;

	private Schedule schedule;

	private Map<Connection, Integer> buffers;

	private OrderedMap<Procedure> procs = new OrderedMap<Procedure>();

	private OrderedMap<Variable> variables;

	private OrderedMap<Variable> stateVars;

	private LocalVariable loopVar;

	private List<CFGNode> nodes = new ArrayList<CFGNode>();

	private void addStateVariables(Actor actor) {
		for (Map.Entry<Connection, Integer> entry : buffers.entrySet()) {

			Connection connection = entry.getKey();
			Expression size = new IntExpr(entry.getValue());

			Vertex src = graph.getEdgeSource(connection);
			Vertex tgt = graph.getEdgeTarget(connection);
			Port srcPort = connection.getSource();
			Port tgtPort = connection.getTarget();

			String name = src.getInstance().getId() + "_" + srcPort.getName()
					+ "_" + tgt.getInstance().getId() + "_" + tgtPort.getName();

			Type type = new ListType(size, srcPort.getType());
			StateVariable var = new StateVariable(new Location(), type, name,
					false, (Constant) null);

			stateVars.add(name, new Location(), name, var);
		}
	}

	private Procedure convertActionToFunction(Action action) {
		OrderedMap<Variable> parameters = new OrderedMap<Variable>();
		OrderedMap<Variable> locals = new OrderedMap<Variable>();
		List<CFGNode> nodes = new ArrayList<CFGNode>();

		Procedure proc = new Procedure(action.getName(), false, new Location(),
				new VoidType(), parameters, locals, nodes);

		Pattern inputPattern = action.getInputPattern();

		for (Map.Entry<Port, Integer> entry : inputPattern.entrySet()) {
			Port port = entry.getKey();
			Expression size = new IntExpr(entry.getValue());
			Type type = new ListType(size, port.getType());
			LocalVariable parameter = new LocalVariable(false, 0,
					new Location(), port.getName(), null, null, type);

			parameters.add("", parameter.getLocation(), parameter.getName(),
					parameter);
		}

		Pattern outputPattern = action.getOutputPattern();

		for (Map.Entry<Port, Integer> entry : outputPattern.entrySet()) {
			Port port = entry.getKey();
			Expression size = new IntExpr(entry.getValue());
			Type type = new ListType(size, port.getType());

			LocalVariable parameter = new LocalVariable(false, 0,
					new Location(), port.getName(), null, null, type);

			parameters.add("", new Location(), parameter.getName(), parameter);
		}

		nodes.addAll(action.getBody().getNodes());
		return proc;
	}

	/**
	 * Creates the static action for this actor.
	 * 
	 * @return a static action
	 */
	private Action createAction() {
		Procedure scheduler = createScheduler();
		Procedure body = createBody();
		Tag tag = new Tag();
		return new Action(new Location(), tag, null, null,/* input, output, */
		scheduler, body);
	}

	private Actor createActor() {

		OrderedMap<Variable> parameters = new OrderedMap<Variable>();
		OrderedMap<Port> inputs = new OrderedMap<Port>();
		OrderedMap<Port> outputs = new OrderedMap<Port>();
		stateVars = new OrderedMap<Variable>();

		ActionList actions = new ActionList();
		ActionList initializes = new ActionList();
		ActionScheduler scheduler = new ActionScheduler(actions.getList(), null);

		Actor superActor = new Actor("cluster", "", parameters, inputs,
				outputs, stateVars, procs, actions.getList(), initializes
						.getList(), scheduler, null);

		return superActor;
	}

	/**
	 * Creates the body of the static action.
	 * 
	 * @return the body of the static action
	 */
	private Procedure createBody() {
		Location location = new Location();

		variables = new OrderedMap<Variable>();
		// List<CFGNode> nodes = new ArrayList<CFGNode>();

		// add a loop counter variable
		loopVar = new LocalVariable(true, 0, new Location(), "index", null,
				null, new IntType(new IntExpr(32)));

		variables.add("", new Location(), "index", loopVar);

		Procedure procedure = new Procedure(ACTION_NAME, false, location,
				new VoidType(), new OrderedMap<Variable>(), variables, nodes);

		createReads(procedure);

		createLoopedSchedule(procedure, schedule);

		createWrites(procedure);

		return procedure;
	}

	private void createLoopedSchedule(Procedure procedure, Schedule schedule) {

		LinkedList<Iterand> stack = new LinkedList<Iterand>(schedule
				.getIterands());

		WhileNode whileNode = null;

		while (!stack.isEmpty()) {

			Iterand iterand = stack.removeFirst();

			if (iterand.isVertex()) {
				Vertex vertex = iterand.getVertex();
				Actor actor = vertex.getInstance().getActor();

				new RemoveReadWrites().transform(actor);

				List<Action> actions = actor.getActions();
				// Assume that the actor has a single action.
				Procedure proc = convertActionToFunction(actions.get(0));
				proc.setName(actor.getName() + "_" + proc.getName());

				// Add a block node that contains one call instruction and
				// increment the loop counter.
				BlockNode blkNode = new BlockNode(procedure);
				List<Expression> parameters = getParams(vertex);

				Call call = new Call(null, null, proc, parameters);

				Assign assign = new Assign(loopVar, null);
				assign.setValue(new BinaryExpr(new VarExpr(new Use(loopVar,
						assign)), BinaryOp.PLUS, new IntExpr(1), new IntType(
						new IntExpr(32))));

				blkNode.add(call);
				blkNode.add(assign);

				whileNode.getNodes().add(blkNode);

				procs.add("", new Location(), proc.getName(), proc);
			} else {
				Schedule sched = iterand.getSchedule();

				// Assign loop counter to zero
				BlockNode blkNode = new BlockNode(procedure);
				blkNode.add(new Assign(loopVar, new IntExpr(0)));
				nodes.add(blkNode);

				whileNode = new WhileNode(procedure, null,
						new ArrayList<CFGNode>(), new BlockNode(procedure));

				Expression condition = new BinaryExpr(new VarExpr(new Use(
						loopVar, whileNode)), BinaryOp.LT, new IntExpr(sched
						.getIterationCount()), new BoolType());
				whileNode.setValue(condition);

				nodes.add(whileNode);

				stack.addAll(0, sched.getIterands());
			}
		}
	}

	private void createReads(Procedure procedure) {
	}

	private Procedure createScheduler() {
		Location location = new Location();
		List<CFGNode> nodes = new ArrayList<CFGNode>();

		Procedure procedure = new Procedure(SCHEDULER_NAME, false, location,
				new BoolType(), new OrderedMap<Variable>(),
				null/* variables */, nodes);

		return procedure;
	}

	private void createWrites(Procedure procedure) {
	}

	private List<Expression> getParams(Vertex vertex) {
		List<Expression> parameters = new ArrayList<Expression>();

		for (Connection connection : graph.incomingEdgesOf(vertex)) {
			Vertex src = graph.getEdgeSource(connection);
			Vertex tgt = graph.getEdgeTarget(connection);
			Port srcPort = connection.getSource();
			Port tgtPort = connection.getTarget();

			String name = src.getInstance().getId() + "_" + srcPort.getName()
					+ "_" + tgt.getInstance().getId() + "_" + tgtPort.getName();
			Variable stateVar = stateVars.get(name);

			Expression expr = new BinaryExpr(new IntExpr(tgtPort
					.getNumTokensConsumed()), BinaryOp.TIMES, new VarExpr(
					new Use(loopVar)), new IntType(new IntExpr(32)));

			Expression inParam = new BinaryExpr(new VarExpr(new Use(stateVar)),
					BinaryOp.PLUS, expr, new IntType(new IntExpr(32)));

			parameters.add(inParam);
		}

		for (Connection connection : graph.outgoingEdgesOf(vertex)) {
			Vertex src = graph.getEdgeSource(connection);
			Vertex tgt = graph.getEdgeTarget(connection);
			Port srcPort = connection.getSource();
			Port tgtPort = connection.getTarget();

			String name = src.getInstance().getId() + "_" + srcPort.getName()
					+ "_" + tgt.getInstance().getId() + "_" + tgtPort.getName();
			Variable stateVar = stateVars.get(name);

			Expression expr = new BinaryExpr(new IntExpr(srcPort
					.getNumTokensProduced()), BinaryOp.TIMES, new VarExpr(
					new Use(loopVar)), new IntType(new IntExpr(32)));

			Expression outParam = new BinaryExpr(new VarExpr(new Use(stateVar)),
					BinaryOp.PLUS, expr, new IntType(new IntExpr(32)));

			parameters.add(outParam);
		}
		return parameters;
	}

	/**
	 * Tries to merge actors.
	 * 
	 * @return <code>true</code> if actors were merged, <code>false</code>
	 *         otherwise
	 */
	private boolean mergeActors() {

		Actor actor = createActor();

		addStateVariables(actor);

		Action action = createAction();

		actor.getActions().add(action);
		Vertex vertex = new Vertex(new Instance("cluster", actor));

		graph.addVertex(vertex);

		return false;
	}

	@Override
	public void transform(Network network) throws OrccException {
		graph = network.getGraph();

		schedule = new FlatSASScheduler().schedule(network);
		BufferManager bufMngt = new BufferManager(network);
		bufMngt.instrument(schedule);
		buffers = bufMngt.getBufferCapacities();

		mergeActors();

	}

}
