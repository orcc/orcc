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
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.orcc.OrccException;
import net.sf.orcc.ir.Action;
import net.sf.orcc.ir.ActionScheduler;
import net.sf.orcc.ir.Actor;
import net.sf.orcc.ir.CFGNode;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.IrFactory;
import net.sf.orcc.ir.LocalVariable;
import net.sf.orcc.ir.Location;
import net.sf.orcc.ir.Pattern;
import net.sf.orcc.ir.Port;
import net.sf.orcc.ir.Procedure;
import net.sf.orcc.ir.StateVariable;
import net.sf.orcc.ir.Tag;
import net.sf.orcc.ir.Type;
import net.sf.orcc.ir.TypeList;
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
import net.sf.orcc.ir.instructions.Load;
import net.sf.orcc.ir.instructions.Read;
import net.sf.orcc.ir.instructions.Return;
import net.sf.orcc.ir.instructions.Store;
import net.sf.orcc.ir.instructions.Write;
import net.sf.orcc.ir.nodes.BlockNode;
import net.sf.orcc.ir.nodes.WhileNode;
import net.sf.orcc.ir.transformations.AbstractActorTransformation;
import net.sf.orcc.network.Connection;
import net.sf.orcc.network.Instance;
import net.sf.orcc.network.Network;
import net.sf.orcc.network.Vertex;
import net.sf.orcc.network.transforms.INetworkTransformation;
import net.sf.orcc.tools.transforms.RemoveReadWrites;
import net.sf.orcc.util.ActionList;
import net.sf.orcc.util.OrderedMap;

import org.jgrapht.DirectedGraph;
import org.jgrapht.graph.DirectedSubgraph;

/**
 * This class defines a network transformation that merges actors.
 * 
 * 
 * @author Matthieu Wipliez
 * @author Ghislain Roquier
 * 
 */
public class ActorMerger implements INetworkTransformation {

	private class ModifyLoadStoreIndexes extends AbstractActorTransformation {

		private String id;

		private Map<Variable, Integer> loads;
		private Map<Variable, Integer> stores;
		private BlockNode currentBlock;

		public ModifyLoadStoreIndexes(String id) {
			this.id = id;
		}

		@Override
		public void transform(Actor actor) throws OrccException {
			for (Procedure proc : actor.getProcs()) {
				loads = new HashMap<Variable, Integer>();
				stores = new HashMap<Variable, Integer>();
				visitProcedure(proc);

				currentBlock = BlockNode.getLast(proc);

				updateLoadIndex();
				updateStoreIndex();
			}
		}

		private void updateLoadIndex() {
			for (Map.Entry<Variable, Integer> entry : loads.entrySet()) {
				Variable var = entry.getKey();
				int cns = entry.getValue();

				Variable readVar = actor.getStateVars().get(
						var.getName() + "_r");

				BinaryExpr incr = new BinaryExpr(new VarExpr(new Use(readVar)),
						BinaryOp.PLUS, new IntExpr(cns), null);

				IntExpr modSize = new IntExpr(
						((TypeList) var.getType()).getSize());

				@SuppressWarnings("unused")
				BinaryExpr modExpr = new BinaryExpr(incr, BinaryOp.MOD,
						modSize, null);
				List<Expression> indexes = new ArrayList<Expression>();
				Store store = new Store(readVar, indexes, incr);
				currentBlock.add(store);
			}
		}

		private void updateStoreIndex() {
			for (Map.Entry<Variable, Integer> entry : stores.entrySet()) {
				Variable var = entry.getKey();
				int prd = entry.getValue();
				Variable readVar = actor.getStateVars().get(
						var.getName() + "_w");

				BinaryExpr incr = new BinaryExpr(new VarExpr(new Use(readVar)),
						BinaryOp.PLUS, new IntExpr(prd), null);

				IntExpr modSize = new IntExpr(
						((TypeList) var.getType()).getSize());

				@SuppressWarnings("unused")
				BinaryExpr modExpr = new BinaryExpr(incr, BinaryOp.MOD,
						modSize, null);
				List<Expression> indexes = new ArrayList<Expression>();
				Store store = new Store(readVar, indexes, incr);
				currentBlock.add(store);
			}
		}

		@Override
		public void visit(Load load) {
			Use use = load.getSource();
			Variable var = use.getVariable();
			if (!var.isGlobal() && var.isPort()) {
				int cns = portsMap.get(id + var.getName())
						.getNumTokensConsumed();
				var = internalBuffersMap.get(id + var.getName());
				loads.put(var, cns);

				load.setSource(new Use(var, load));

				Expression vare = new VarExpr(new Use(actor.getStateVars().get(
						var.getName() + "_r")));
				Expression expr = load.getIndexes().get(0);
				Expression bop = new BinaryExpr(expr, BinaryOp.PLUS, vare, null);
				load.getIndexes().set(0, bop);
			}
		}

		@Override
		public void visit(Store store) {
			Variable var = store.getTarget();
			if (!var.isGlobal() && var.isPort()) {
				int prd = portsMap.get(id + var.getName())
						.getNumTokensProduced();
				var = internalBuffersMap.get(id + var.getName());
				stores.put(var, prd);
				store.setTarget(var);
				new Use(var, store);

				Expression vare = new VarExpr(new Use(actor.getStateVars().get(
						var.getName() + "_w")));
				Expression expr = store.getIndexes().get(0);
				Expression bop = new BinaryExpr(expr, BinaryOp.PLUS, vare, null);
				store.getIndexes().set(0, bop);
			}
		}

	}

	private static final String ACTION_NAME = "static_schedule";

	private static final String SCHEDULER_NAME = "isSchedulable_" + ACTION_NAME;

	private Actor actor;

	private Map<Connection, Variable> buffersMap;

	private int clusterIdx = 0;

	private int depth = 0;

	private DirectedGraph<Vertex, Connection> graph;

	private Map<Connection, Port> inputPorts;

	private Map<Connection, Port> outputPorts;

	private Map<String, Variable> internalBuffersMap;

	private HashMap<String, Port> portsMap;

	private IScheduler scheduler;

	private OrderedMap<String, Variable> variables;

	/**
	 * @throws OrccException
	 * 
	 */
	private void addProceduresAndStateVars() throws OrccException {
		for (Vertex vertex : scheduler.getSchedule().getActors()) {
			Instance instance = vertex.getInstance();
			String id = instance.getId();
			Actor current = instance.getActor();

			for (StateVariable var : current.getStateVars()) {
				var.setName(id + "_" + var.getName());
				actor.getStateVars().put(var.getName(), var);
			}

			for (Procedure proc : current.getProcs()) {
				if (!proc.isExternal()) {
					proc.setName(id + "_" + proc.getName());
					actor.getProcs().put(proc.getName(), proc);
				}
			}

			for (Action action : current.getActions()) {
				Procedure body = action.getBody();
				Procedure proc = new Procedure(instance.getId() + "_"
						+ action.getName(), false, new Location(),
						IrFactory.eINSTANCE.createTypeVoid(),
						new OrderedMap<String, Variable>(), body.getLocals(),
						body.getNodes());
				actor.getProcs().put(proc.getName(), proc);
			}
			new ModifyLoadStoreIndexes(id).transform(actor);
		}
	}

	private void addStateVariables() {
		OrderedMap<String, StateVariable> stateVars = actor.getStateVars();

		for (Variable var : buffersMap.values()) {
			stateVars.put(var.getName(), (StateVariable) var);

			StateVariable rCount = new StateVariable(new Location(),
					IrFactory.eINSTANCE.createTypeUint(32), var.getName()
							+ "_r", true, new IntExpr(0));
			stateVars.put(rCount.getName(), rCount);
			StateVariable wCount = new StateVariable(new Location(),
					IrFactory.eINSTANCE.createTypeUint(32), var.getName()
							+ "_w", true, new IntExpr(0));
			stateVars.put(wCount.getName(), wCount);
		}
	}

	/**
	 * Creates the static action for this actor.
	 * 
	 * @return a static action
	 * @throws OrccException
	 */
	private Action createAction() throws OrccException {
		Pattern inputPattern = new Pattern();
		for (Port port : actor.getInputs()) {
			inputPattern.put(port, port.getNumTokensConsumed());
		}

		Pattern outputPattern = new Pattern();
		for (Port port : actor.getOutputs()) {
			outputPattern.put(port, port.getNumTokensProduced());
		}

		return new Action(new Location(), new Tag(), inputPattern,
				outputPattern, createScheduler(), createBody());
	}

	/**
	 * 
	 */
	private void createActor(Set<Vertex> vertices) throws OrccException {
		OrderedMap<String, Port> inputs = new OrderedMap<String, Port>();
		OrderedMap<String, Port> outputs = new OrderedMap<String, Port>();
		OrderedMap<String, StateVariable> stateVars = new OrderedMap<String, StateVariable>();
		OrderedMap<String, Procedure> procs = new OrderedMap<String, Procedure>();
		OrderedMap<String, Variable> parameters = new OrderedMap<String, Variable>();
		ActionList actions = new ActionList();
		ActionList initializes = new ActionList();
		ActionScheduler scheduler = new ActionScheduler(
				actions.getAllActions(), null);

		actor = new Actor("cluster_" + clusterIdx++, "", parameters, inputs,
				outputs, stateVars, procs, actions.getAllActions(),
				initializes.getAllActions(), scheduler);

		getInputs(vertices);
		getOutputs(vertices);
		createInternalBuffers();
		addStateVariables();
		addProceduresAndStateVars();

		Action action = createAction();
		actor.getActions().add(action);
	}

	/**
	 * Creates the body of the static action.
	 * 
	 * @return the body of the static action
	 * @throws OrccException
	 */
	private Procedure createBody() throws OrccException {
		List<CFGNode> nodes = new ArrayList<CFGNode>();

		OrderedMap<String, Variable> locals = new OrderedMap<String, Variable>();

		Procedure procedure = new Procedure(ACTION_NAME, false, new Location(),
				IrFactory.eINSTANCE.createTypeVoid(),
				new OrderedMap<String, Variable>(), locals, nodes);

		// Add loop counters
		for (int depth = 0; depth < scheduler.getDepth(); depth++) {
			LocalVariable counter = new LocalVariable(true, 0, new Location(),
					"idx_" + depth, IrFactory.eINSTANCE.createTypeInt(32));
			locals.put(counter.getName(), counter);
		}

		// Initialize read/write counters
		for (Variable var : internalBuffersMap.values()) {
			Variable read = actor.getStateVars().get(var.getName() + "_r");
			Variable write = actor.getStateVars().get(var.getName() + "_w");

			BlockNode block = BlockNode.getLast(procedure, nodes);
			block.add(new Store(read, new ArrayList<Expression>(), new IntExpr(
					0)));
			block.add(new Store(write, new ArrayList<Expression>(),
					new IntExpr(0)));
		}

		createReads(procedure);
		createLoopedSchedule(procedure, scheduler.getSchedule(), nodes);
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
						new Use(thisOne, block)),
						IrFactory.eINSTANCE.createTypeBool());
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
		for (Port port : actor.getInputs()) {
			Location location = new Location();
			int numTokens = port.getNumTokensConsumed();
			LocalVariable varDef = new LocalVariable(true, i, new Location(),
					"pattern", IrFactory.eINSTANCE.createTypeBool());
			i++;
			variables.put(varDef.getName(), varDef);
			HasTokens hasTokens = new HasTokens(location, port, numTokens,
					varDef);
			varDef.setInstruction(hasTokens);
			block.add(hasTokens);
		}
	}

	/**
	 * turns FIFOs between static actors into buffers
	 * 
	 */
	private void createInternalBuffers() {
		int index = 0;

		buffersMap = new HashMap<Connection, Variable>();
		internalBuffersMap = new HashMap<String, Variable>();
		portsMap = new HashMap<String, Port>();

		// Create internal buffer for inputs
		for (Map.Entry<Connection, Port> entry : inputPorts.entrySet()) {
			Port port = entry.getValue();
			int size = port.getNumTokensConsumed();
			Type type = IrFactory.eINSTANCE
					.createTypeList(size, port.getType());
			Variable var = new StateVariable(new Location(), type,
					port.getName(), true);
			buffersMap.put(entry.getKey(), var);
			String srcName = graph.getEdgeTarget(entry.getKey()).getInstance()
					.getId()
					+ "" + entry.getKey().getTarget().getName();
			internalBuffersMap.put(srcName, var);
			portsMap.put(srcName, entry.getKey().getTarget());

		}

		// Create internal buffer for outputs
		for (Map.Entry<Connection, Port> entry : outputPorts.entrySet()) {
			Port port = entry.getValue();
			int size = port.getNumTokensProduced();
			Type type = IrFactory.eINSTANCE
					.createTypeList(size, port.getType());
			Variable var = new StateVariable(new Location(), type,
					port.getName(), true);
			buffersMap.put(entry.getKey(), var);
			String srcName = graph.getEdgeSource(entry.getKey()).getInstance()
					.getId()
					+ "" + entry.getKey().getSource().getName();
			internalBuffersMap.put(srcName, var);
			portsMap.put(srcName, entry.getKey().getSource());
		}

		// Create internal buffer connections inside the cluster
		for (Map.Entry<Connection, Integer> entry : scheduler
				.getBufferCapacities().entrySet()) {
			Connection connection = entry.getKey();
			String name = "_static_buf_" + index++;
			Type type = IrFactory.eINSTANCE.createTypeList(entry.getValue(),
					connection.getSource().getType());
			Variable buf = new StateVariable(new Location(), type, name, true);
			buffersMap.put(connection, buf);
			String srcName = graph.getEdgeTarget(connection).getInstance()
					.getId()
					+ "" + connection.getTarget().getName();
			internalBuffersMap.put(srcName, buf);
			portsMap.put(srcName, connection.getTarget());
			String tgtName = graph.getEdgeSource(connection).getInstance()
					.getId()
					+ "" + connection.getSource().getName();
			internalBuffersMap.put(tgtName, buf);
			portsMap.put(tgtName, connection.getSource());
		}
	}

	/**
	 * Creates the schedule loop of the
	 * 
	 */
	private void createLoopedSchedule(Procedure procedure, Schedule schedule,
			List<CFGNode> nodes) throws OrccException {
		for (Iterand iterand : schedule.getIterands()) {
			if (iterand.isVertex()) {
				Instance instance = iterand.getVertex().getInstance();
				Action action = instance.getActor().getActions().get(0);
				Procedure proc = actor.getProcs().get(
						instance.getId() + "_" + action.getName());

				BlockNode blkNode = BlockNode.getLast(procedure, nodes);
				blkNode.add(new Call(new Location(), null, proc,
						new ArrayList<Expression>()));
			} else {
				Schedule sched = iterand.getSchedule();

				OrderedMap<String, Variable> locals = procedure.getLocals();
				LocalVariable loopVar = (LocalVariable) locals.get("idx_"
						+ depth);

				// Reset loop counter
				BlockNode blkNode = BlockNode.getLast(procedure, nodes);
				blkNode.add(new Assign(loopVar, new IntExpr(0)));

				Expression condition = new BinaryExpr(new VarExpr(new Use(
						loopVar)), BinaryOp.LT, new IntExpr(
						sched.getIterationCount()),
						IrFactory.eINSTANCE.createTypeBool());

				WhileNode whileNode = new WhileNode(procedure, condition,
						new ArrayList<CFGNode>(), new BlockNode(procedure));
				nodes.add(whileNode);

				depth++;

				createLoopedSchedule(procedure, sched, whileNode.getNodes());

				depth--;

				// Increment current while loop variable
				Assign assign = new Assign(loopVar, new BinaryExpr(new VarExpr(
						new Use(loopVar)), BinaryOp.PLUS, new IntExpr(1),
						loopVar.getType()));
				BlockNode.getLast(procedure, whileNode.getNodes()).add(assign);

			}
		}
	}

	/**
	 * Creates the read instructions of the static action
	 */
	private void createReads(Procedure procedure) {
		BlockNode block = BlockNode.getLast(procedure);
		for (Port port : actor.getInputs()) {
			Variable var = actor.getStateVars().get(port.getName());
			int numTokens = port.getNumTokensConsumed();
			Read read = new Read(port, numTokens, var);
			block.add(read);
		}
	}

	/**
	 * Creates the scheduler of the static action.
	 * 
	 * @return the scheduler of the static action
	 * @throws OrccException
	 */
	private Procedure createScheduler() {
		Location location = new Location();
		variables = new OrderedMap<String, Variable>();
		List<CFGNode> nodes = new ArrayList<CFGNode>();
		Procedure procedure = new Procedure(SCHEDULER_NAME, false, location,
				IrFactory.eINSTANCE.createTypeBool(),
				new OrderedMap<String, Variable>(), variables, nodes);
		BlockNode block = new BlockNode(procedure);
		nodes.add(block);
		createInputTests(block);
		createInputCondition(block);
		return procedure;
	}

	/**
	 * Creates the write instructions of the static action
	 */
	private void createWrites(Procedure procedure) {
		BlockNode block = BlockNode.getLast(procedure);
		for (Port port : actor.getOutputs()) {
			Variable var = actor.getStateVars().get(port.getName());
			int numTokens = port.getNumTokensProduced();
			Write read = new Write(port, numTokens, var);
			block.add(read);
		}
	}

	private void getInputs(Set<Vertex> vertices) {
		inputPorts = new HashMap<Connection, Port>();

		int index = 0;
		for (Connection connection : graph.edgeSet()) {
			Vertex src = graph.getEdgeSource(connection);
			Vertex tgt = graph.getEdgeTarget(connection);

			if (!vertices.contains(src) && vertices.contains(tgt)) {
				Port tgtPort = connection.getTarget();
				Port port = new Port(tgtPort);
				port.setName("_input_" + index);
				port.increaseTokenConsumption(tgtPort.getNumTokensConsumed());
				actor.getInputs().put(port.getName(), port);
				index++;

				// fill the map for the later update of connections
				inputPorts.put(connection, port);
			}
		}
	}

	private void getOutputs(Set<Vertex> vertices) {
		int index = 0;
		outputPorts = new HashMap<Connection, Port>();

		for (Connection connection : graph.edgeSet()) {
			Vertex src = graph.getEdgeSource(connection);
			Vertex tgt = graph.getEdgeTarget(connection);

			if (vertices.contains(src) && !vertices.contains(tgt)) {
				Port srcPort = connection.getSource();
				Port port = new Port(srcPort);
				port.setName("_output_" + index);
				port.increaseTokenProduction(srcPort.getNumTokensProduced());
				actor.getOutputs().put(port.getName(), port);
				index++;

				// fill the map for the later update of connections
				outputPorts.put(connection, port);

			}
		}
	}

	/**
	 * Tries to merge actors.
	 * 
	 * @throws OrccException
	 */
	private void mergeActors(Set<Vertex> vertices) throws OrccException {
		createActor(vertices);
		Instance instance = new Instance(actor.getName(), actor);
		Object[] v = vertices.toArray();
		instance.getAttributes().putAll(
				((Vertex) v[0]).getInstance().getAttributes());
		Vertex mergeVertex = new Vertex(instance);
		graph.addVertex(mergeVertex);
		updateConnection(mergeVertex, vertices);
		graph.removeAllVertices(vertices);
	}

	@Override
	public void transform(Network network) throws OrccException {
		graph = network.getGraph();

		for (Set<Vertex> vertices : new StaticSubsetDetector(network)
				.staticRegionSets()) {
			DirectedGraph<Vertex, Connection> subgraph = new DirectedSubgraph<Vertex, Connection>(
					graph, vertices, null);

			scheduler = new FlatSASScheduler(subgraph);
			scheduler.getDepth();

			for (Vertex vertex : vertices) {
				Actor actor = vertex.getInstance().getActor();
				new RemoveReadWrites().transform(actor);
			}

			mergeActors(vertices);
		}
	}

	private void updateConnection(Vertex merge, Set<Vertex> vertices) {
		Set<Connection> connections = new HashSet<Connection>(graph.edgeSet());
		for (Connection connection : connections) {
			Vertex src = graph.getEdgeSource(connection);
			Vertex tgt = graph.getEdgeTarget(connection);
			if (!vertices.contains(src) && vertices.contains(tgt)) {
				Connection newConn = new Connection(connection.getSource(),
						inputPorts.get(connection), connection.getAttributes());
				graph.addEdge(graph.getEdgeSource(connection), merge, newConn);
			}
			if (vertices.contains(src) && !vertices.contains(tgt)) {
				Connection newConn = new Connection(
						outputPorts.get(connection), connection.getTarget(),
						connection.getAttributes());
				graph.addEdge(merge, graph.getEdgeTarget(connection), newConn);
			}
		}
	}
}
