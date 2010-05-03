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
import java.util.Map;
import java.util.Set;

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
import net.sf.orcc.ir.expr.BoolExpr;
import net.sf.orcc.ir.expr.IntExpr;
import net.sf.orcc.ir.expr.VarExpr;
import net.sf.orcc.ir.instructions.Assign;
import net.sf.orcc.ir.instructions.Call;
import net.sf.orcc.ir.instructions.HasTokens;
import net.sf.orcc.ir.instructions.Read;
import net.sf.orcc.ir.instructions.Return;
import net.sf.orcc.ir.instructions.Write;
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
import net.sf.orcc.tools.staticanalyzer.StaticSubsetDetector;
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

	private OrderedMap<Port> inputs;

	private OrderedMap<Port> outputs;

	private OrderedMap<Variable> stateVars;

	private List<LocalVariable> indexes = new ArrayList<LocalVariable>();

	private int index = 0;

	/**
	 * Converts FIFO between static actors into state variables
	 * 
	 */
	private void addStateVariables() {
		for (Map.Entry<Connection, Integer> entry : buffers.entrySet()) {

			Connection connection = entry.getKey();
			Expression size = new IntExpr(entry.getValue());

			Vertex src = graph.getEdgeSource(connection);
			Vertex tgt = graph.getEdgeTarget(connection);

			Port srcPort = connection.getSource();
			Port tgtPort = connection.getTarget();

			String name;
			Type type;
			if (src.isPort()) {
				name = src.getPort().getName();
				type = new ListType(size, tgtPort.getType());
			} else if (tgt.isPort()) {
				name = tgt.getPort().getName();
				type = new ListType(size, srcPort.getType());
			} else {
				name = src.getInstance().getId() + "_" + srcPort.getName()
						+ "_" + tgt.getInstance().getId() + "_"
						+ tgtPort.getName();
				type = new ListType(size, srcPort.getType());
			}

			stateVars.add(name, new Location(), name, new StateVariable(
					new Location(), type, name, false, (Constant) null));
		}
	}

	
	/**
	 * Converts a given action into a procedure
	 * 
	 * @param action
	 * @return
	 */
	private Procedure convertActionToFunction(Action action) {
		OrderedMap<Variable> parameters = action.getBody().getParameters();
		OrderedMap<Variable> locals = action.getBody().getLocals();
		List<CFGNode> nodes = action.getBody().getNodes();

		Procedure proc = new Procedure(action.getName(), false, new Location(),
				new VoidType(), parameters, locals, nodes);

		Pattern inputPattern = action.getInputPattern();

		for (Map.Entry<Port, Integer> entry : inputPattern.entrySet()) {
			Port port = entry.getKey();
			Expression size = new IntExpr(entry.getValue());
			Type type = new ListType(size, port.getType());
			LocalVariable parameter = new LocalVariable(false, 0,
					new Location(), port.getName(), null, null, type);
			parameters.add("", new Location(), parameter.getName(), parameter);
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

		return proc;
	}

	/**
	 * Creates the static action for this actor.
	 * 
	 * @return a static action
	 * @throws OrccException
	 */
	private Action createAction() throws OrccException {

		Pattern inputPattern = new Pattern();
		Pattern outputPattern = new Pattern();

		for (Port port : inputs) {
			inputPattern.put(port, port.getNumTokensConsumed());
		}

		for (Port port : outputs) {
			outputPattern.put(port, port.getNumTokensProduced());
		}

		Procedure scheduler = createScheduler();
		Procedure body = createBody();
		Tag tag = new Tag();

		return new Action(new Location(), tag, inputPattern, outputPattern,
				scheduler, body);
	}

	private Actor createActor() {

		stateVars = new OrderedMap<Variable>();

		OrderedMap<Variable> parameters = new OrderedMap<Variable>();
		ActionList actions = new ActionList();
		ActionList initializes = new ActionList();
		ActionScheduler scheduler = new ActionScheduler(actions.getList(), null);

		String name = "";
		for (Vertex vertex : schedule.getScheduledActors()) {
			name += vertex.getInstance().getId();
		}

		Actor superActor = new Actor(name, "", parameters, inputs, outputs,
				stateVars, procs, actions.getList(), initializes.getList(),
				scheduler, null);

		return superActor;
	}

	/**
	 * Creates the body of the static action.
	 * 
	 * @return the body of the static action
	 * @throws OrccException
	 */
	private Procedure createBody() throws OrccException {
		List<CFGNode> nodes = new ArrayList<CFGNode>();
		variables = new OrderedMap<Variable>();

		Procedure procedure = new Procedure(ACTION_NAME, false, new Location(),
				new VoidType(), new OrderedMap<Variable>(), variables, nodes);
		createReads(procedure);
		createLoopedSchedule(procedure, schedule, nodes);
		createWrites(procedure);
		return procedure;
	}

	private void createLoopedSchedule(Procedure procedure, Schedule schedule,
			List<CFGNode> nodes) throws OrccException {
		for (Iterand iterand : schedule.getIterands()) {
			if (iterand.isVertex()) {

				Vertex vertex = iterand.getVertex();

				Actor actor = vertex.getInstance().getActor();

				OrderedMap<Variable> vars = actor.getStateVars();

				for (Variable var : vars) {
					String name = vertex.getInstance().getId() + "_"
							+ var.getName();
					var.setName(name);
					stateVars.add("", new Location(), name, var);
				}
				new RemoveReadWrites().transform(actor);

				List<Action> actions = actor.getActions();

				if (actions.size() <= 1) {
					Procedure proc = convertActionToFunction(actions.get(0));

					proc.setName(vertex.getInstance().getId() + "_"
							+ proc.getName());
					BlockNode blkNode = new BlockNode(procedure);
					LocalVariable loopVar = indexes.get(index - 1);
					List<Expression> parameters = getParams(loopVar, vertex);
					Call call = new Call(null, null, proc, parameters);
					Expression binopExpr = new BinaryExpr(new VarExpr(new Use(
							loopVar)), BinaryOp.PLUS, new IntExpr(1),
							new IntType(new IntExpr(32)));
					Assign assign = new Assign(loopVar, binopExpr);
					blkNode.add(call);
					blkNode.add(assign);
					nodes.add(blkNode);
					procs.add("", new Location(), proc.getName(), proc);
				} else {
					throw new OrccException(
							"SDF actor with multiple actions is not yet supported!");
				}

			} else {

				LocalVariable loopVar = new LocalVariable(true, 0,
						new Location(), "idx_" + index, null, null,
						new IntType(new IntExpr(32)));

				if (indexes.size() <= index) {
					indexes.add(loopVar);
					variables.add("", new Location(), loopVar.getName(),
							loopVar);
				}

				Schedule sched = iterand.getSchedule();

				BlockNode blkNode = new BlockNode(procedure);
				blkNode.add(new Assign(loopVar, new IntExpr(0)));
				nodes.add(blkNode);

				List<CFGNode> statements = new ArrayList<CFGNode>();

				WhileNode whileNode = new WhileNode(procedure, null,
						statements, new BlockNode(procedure));
				
				Expression condition = new BinaryExpr(new VarExpr(new Use(
						loopVar)), BinaryOp.LT, new IntExpr(sched
						.getIterationCount()), new BoolType());
				whileNode.setValue(condition);

				nodes.add(whileNode);

				index++;

				createLoopedSchedule(procedure, sched, statements);

				index--;
			}
		}
	}

	private void createWrites(Procedure procedure) {
		BlockNode block = BlockNode.getLast(procedure, procedure.getNodes());
		for (Port port : outputs) {
			Variable var = stateVars.get(port.getName());
			Write read = new Write(port, port.getNumTokensProduced(), var);
			block.add(read);
		}
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

		int i = 0;
		for (Port port : inputs) {
			Location location = new Location();
			int numTokens = port.getNumTokensConsumed();

			LocalVariable varDef = new LocalVariable(true, i, new Location(),
					"pattern", null, null, new BoolType());
			i++;
			variables.add("", location, varDef.getName(), varDef);

			HasTokens hasTokens = new HasTokens(location, port, numTokens,
					varDef);
			varDef.setInstruction(hasTokens);
			block.add(hasTokens);
		}
	}

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

	private void createReads(Procedure procedure) {
		BlockNode block = BlockNode.getLast(procedure, procedure.getNodes());
		for (Port port : inputs) {
			Variable var = stateVars.get(port.getName());
			Read read = new Read(port, port.getNumTokensConsumed(), var);
			block.add(read);
		}
	}

	private List<Expression> getParams(Variable loopVar, Vertex vertex) {
		List<Expression> parameters = new ArrayList<Expression>();

		for (Connection connection : graph.incomingEdgesOf(vertex)) {
			Vertex src = graph.getEdgeSource(connection);
			Vertex tgt = graph.getEdgeTarget(connection);
			Port srcPort = connection.getSource();
			Port tgtPort = connection.getTarget();

			String name = "";
			if (src.isPort()) {
				name = src.getPort().getName();
			} else {
				name = src.getInstance().getId() + "_" + srcPort.getName()
						+ "_" + tgt.getInstance().getId() + "_"
						+ tgtPort.getName();
			}

			Variable stateVar = stateVars.get(name);

			Expression expr = new BinaryExpr(new IntExpr(tgtPort
					.getNumTokensConsumed()), BinaryOp.TIMES, new VarExpr(
					new Use(loopVar)), new IntType(new IntExpr(32)));

			Expression inParam = new BinaryExpr(new VarExpr(new Use(stateVar)),
					BinaryOp.PLUS, expr, new IntType(new IntExpr(32)));

			parameters.add(inParam);
		}

		for (Connection connection : graph.outgoingEdgesOf(vertex)) {
			Vertex tgt = graph.getEdgeTarget(connection);
			Port srcPort = connection.getSource();
			Port tgtPort = connection.getTarget();

			String name = "";
			if (tgt.isPort()) {
				name = tgt.getPort().getName();
			} else {
				name = vertex.getInstance().getId() + "_" + srcPort.getName()
						+ "_" + tgt.getInstance().getId() + "_"
						+ tgtPort.getName();
			}

			Variable stateVar = stateVars.get(name);

			Expression expr = new BinaryExpr(new IntExpr(srcPort
					.getNumTokensProduced()), BinaryOp.TIMES, new VarExpr(
					new Use(loopVar)), new IntType(new IntExpr(32)));

			Expression outParam = new BinaryExpr(
					new VarExpr(new Use(stateVar)), BinaryOp.PLUS, expr,
					new IntType(new IntExpr(32)));

			parameters.add(outParam);
		}
		return parameters;
	}

	/**
	 * Tries to merge actors.
	 * 
	 * @return <code>true</code> if actors were merged, <code>false</code>
	 *         otherwise
	 * @throws OrccException
	 */
	private Actor mergeActors(Network network) throws OrccException {
		graph = network.getGraph();
		inputs = network.getInputs();
		outputs = network.getOutputs();

		Actor actor = createActor();

		addStateVariables();
		Action action = createAction();
		actor.getActions().add(action);
		Vertex vertex = new Vertex(new Instance("cluster", actor));
		graph.addVertex(vertex);

		return actor;
	}

	@Override
	public void transform(Network network) throws OrccException {

		Set<Set<Vertex>> sets = new StaticSubsetDetector(network)
				.staticRegionSets();

		int index = 0;
		for (Set<Vertex> vertices : sets) {
			Vertex vertex = new StaticNetworkClusterer(network).cluster(
					"clust_" + index, vertices);

			Network cluster = vertex.getInstance().getNetwork();
			schedule = new FlatSASScheduler().schedule(cluster);
			BufferManager bufMngt = new BufferManager(cluster);
			bufMngt.instrument(schedule);
			buffers = bufMngt.getBufferCapacities();

			Actor actor = mergeActors(cluster);
			graph = network.getGraph();
			Vertex merge = new Vertex(new Instance(actor.getName(), actor));
			graph.addVertex(merge);

			for (Connection connection : graph.incomingEdgesOf(vertex)) {
				Connection newConn = new Connection(connection.getSource(),
						inputs.get(connection.getTarget().getName()), null);
				graph.addEdge(graph.getEdgeSource(connection), merge, newConn);
			}
			for (Connection connection : graph.outgoingEdgesOf(vertex)) {
				Connection newConn = new Connection(outputs.get(connection
						.getSource().getName()), connection.getTarget(), null);
				graph.addEdge(merge, graph.getEdgeTarget(connection), newConn);
			}
			graph.removeVertex(vertex);
		}
	}
}
