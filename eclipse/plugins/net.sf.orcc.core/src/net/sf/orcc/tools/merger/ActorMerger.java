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
import net.sf.orcc.ir.AbstractActorVisitor;
import net.sf.orcc.ir.Action;
import net.sf.orcc.ir.ActionScheduler;
import net.sf.orcc.ir.Actor;
import net.sf.orcc.ir.Node;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.VarGlobal;
import net.sf.orcc.ir.IrFactory;
import net.sf.orcc.ir.VarLocal;
import net.sf.orcc.ir.Location;
import net.sf.orcc.ir.NodeBlock;
import net.sf.orcc.ir.NodeWhile;
import net.sf.orcc.ir.Pattern;
import net.sf.orcc.ir.Port;
import net.sf.orcc.ir.Procedure;
import net.sf.orcc.ir.Tag;
import net.sf.orcc.ir.Type;
import net.sf.orcc.ir.Use;
import net.sf.orcc.ir.Var;
import net.sf.orcc.ir.expr.BinaryExpr;
import net.sf.orcc.ir.expr.BinaryOp;
import net.sf.orcc.ir.expr.BoolExpr;
import net.sf.orcc.ir.expr.IntExpr;
import net.sf.orcc.ir.expr.VarExpr;
import net.sf.orcc.ir.impl.IrFactoryImpl;
import net.sf.orcc.ir.instructions.Assign;
import net.sf.orcc.ir.instructions.Call;
import net.sf.orcc.ir.instructions.Load;
import net.sf.orcc.ir.instructions.Return;
import net.sf.orcc.ir.instructions.Store;
import net.sf.orcc.network.Connection;
import net.sf.orcc.network.Instance;
import net.sf.orcc.network.Network;
import net.sf.orcc.network.Vertex;
import net.sf.orcc.network.transformations.INetworkTransformation;
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

	private class ModifyLoadStoreIndexes extends AbstractActorVisitor {

		private String id;

		private Map<Var, Integer> loads;
		private Map<Var, Integer> stores;
		private NodeBlock currentBlock;

		public ModifyLoadStoreIndexes(String id) {
			this.id = id;
		}

		@Override
		public void visit(Actor actor) {
			this.actor = actor;
			for (Procedure proc : actor.getProcs()) {
				loads = new HashMap<Var, Integer>();
				stores = new HashMap<Var, Integer>();
				visit(proc);

				currentBlock = proc.getLast();

				updateLoadIndex();
				updateStoreIndex();
			}
		}

		private void updateLoadIndex() {
			for (Map.Entry<Var, Integer> entry : loads.entrySet()) {
				Var var = entry.getKey();
				int cns = entry.getValue();

				Var readVar = actor.getStateVars().get(
						var.getName() + "_r");

				BinaryExpr incr = new BinaryExpr(new VarExpr(new Use(readVar)),
						BinaryOp.PLUS, new IntExpr(cns), null);

				Store store = new Store(readVar, incr);
				currentBlock.add(store);
			}
		}

		private void updateStoreIndex() {
			for (Map.Entry<Var, Integer> entry : stores.entrySet()) {
				Var var = entry.getKey();
				int prd = entry.getValue();

				Var readVar = actor.getStateVars().get(
						var.getName() + "_w");

				BinaryExpr incr = new BinaryExpr(new VarExpr(new Use(readVar)),
						BinaryOp.PLUS, new IntExpr(prd), null);

				Store store = new Store(readVar, incr);
				currentBlock.add(store);
			}
		}

		@Override
		public void visit(Load load) {
			Use use = load.getSource();
			Var var = use.getVariable();
			if (!var.isGlobal() && isPort((VarLocal) var)) {
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
			Var var = store.getTarget();
			if (!var.isGlobal() && isPort((VarLocal) var)) {
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

	private Map<Connection, Var> buffersMap;

	private int clusterIdx = 0;

	private int depth = 0;

	private DirectedGraph<Vertex, Connection> graph;

	private Map<Connection, Port> inputPorts;

	private Map<Connection, Port> outputPorts;

	private Map<String, Var> internalBuffersMap;

	private HashMap<String, Port> portsMap;

	private IScheduler scheduler;

	private OrderedMap<String, VarLocal> variables;

	/**
	 * @throws OrccException
	 * 
	 */
	private void addProceduresAndStateVars() throws OrccException {
		for (Vertex vertex : scheduler.getSchedule().getActors()) {
			Instance instance = vertex.getInstance();
			String id = instance.getId();
			Actor current = instance.getActor();

			for (Procedure proc : current.getProcs()) {
				if (!proc.isNative()) {
					proc.setName(id + "_" + proc.getName());
					actor.getProcs().put(proc.getName(), proc);
				}
			}

			for (Action action : current.getActions()) {
				Procedure body = action.getBody();
				Procedure proc = IrFactory.eINSTANCE.createProcedure(
						instance.getId() + "_" + action.getName(), false,
						new Location(), IrFactory.eINSTANCE.createTypeVoid(),
						new OrderedMap<String, VarLocal>(),
						body.getLocals(), body.getNodes());
				actor.getProcs().put(proc.getName(), proc);
			}
			new ModifyLoadStoreIndexes(id).visit(actor);
		}
	}

	private OrderedMap<String, VarGlobal> getStateVariables() {
		OrderedMap<String, VarGlobal> stateVars = new OrderedMap<String, VarGlobal>();

		// first create internal buffers
		createInternalBuffers();

		for (Var var : buffersMap.values()) {
			stateVars.put(var.getName(), (VarGlobal) var);

			VarGlobal rCount = new VarGlobal(new Location(),
					IrFactory.eINSTANCE.createTypeUint(32), var.getName()
							+ "_r", true, new IntExpr(0));
			stateVars.put(rCount.getName(), rCount);
			VarGlobal wCount = new VarGlobal(new Location(),
					IrFactory.eINSTANCE.createTypeUint(32), var.getName()
							+ "_w", true, new IntExpr(0));
			stateVars.put(wCount.getName(), wCount);
		}

		for (Vertex vertex : scheduler.getSchedule().getActors()) {
			Instance instance = vertex.getInstance();
			String id = instance.getId();
			Actor current = instance.getActor();

			for (VarGlobal var : current.getStateVars()) {
				var.setName(id + "_" + var.getName());
				stateVars.put(var.getName(), var);
			}
		}

		return stateVars;
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
			inputPattern.setNumTokens(port, port.getNumTokensConsumed());
		}

		Pattern outputPattern = new Pattern();
		for (Port port : actor.getOutputs()) {
			outputPattern.setNumTokens(port, port.getNumTokensProduced());
		}

		return new Action(new Location(), new Tag(), inputPattern,
				outputPattern, createScheduler(), createBody());
	}

	/**
	 * 
	 */
	private void createActor(Set<Vertex> vertices) throws OrccException {
		String name = "Cluster_" + clusterIdx++;

		OrderedMap<String, Port> inputs = getInputs(vertices);
		OrderedMap<String, Port> outputs = getOutputs(vertices);
		OrderedMap<String, VarGlobal> stateVars = getStateVariables();

		OrderedMap<String, Procedure> procs = new OrderedMap<String, Procedure>();
		OrderedMap<String, VarGlobal> parameters = new OrderedMap<String, VarGlobal>();
		List<Action> actions = new ArrayList<Action>();
		List<Action> initializes = new ArrayList<Action>();
		ActionScheduler sched = new ActionScheduler(actions, null);

		actor = new Actor(name, "", parameters, inputs, outputs, false,
				stateVars, procs, actions, initializes, sched);

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
		List<Node> nodes = new ArrayList<Node>();

		OrderedMap<String, VarLocal> locals = new OrderedMap<String, VarLocal>();

		Procedure procedure = IrFactory.eINSTANCE.createProcedure(ACTION_NAME,
				false, new Location(), IrFactory.eINSTANCE.createTypeVoid(),
				new OrderedMap<String, VarLocal>(), locals, nodes);

		// Add loop counters
		for (int depth = 0; depth < scheduler.getDepth(); depth++) {
			VarLocal counter = new VarLocal(true, 0, new Location(),
					"idx_" + depth, IrFactory.eINSTANCE.createTypeInt(32));
			locals.put(counter.getName(), counter);
		}

		// Initialize read/write counters
		for (Var var : internalBuffersMap.values()) {
			Var read = actor.getStateVars().get(var.getName() + "_r");
			Var write = actor.getStateVars().get(var.getName() + "_w");

			NodeBlock block = procedure.getLast(nodes);
			block.add(new Store(read, new IntExpr(0)));
			block.add(new Store(write, new IntExpr(0)));
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
	private void createInputCondition(NodeBlock block) {
		Expression value;
		Iterator<VarLocal> it = variables.iterator();
		if (it.hasNext()) {
			VarLocal previous = (VarLocal) it.next();
			value = new VarExpr(new Use(previous, block));

			while (it.hasNext()) {
				VarLocal thisOne = (VarLocal) it.next();
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
	 * turns FIFOs between static actors into buffers
	 * 
	 */
	private void createInternalBuffers() {
		int index = 0;

		buffersMap = new HashMap<Connection, Var>();
		internalBuffersMap = new HashMap<String, Var>();
		portsMap = new HashMap<String, Port>();

		// Create internal buffer for inputs
		for (Map.Entry<Connection, Port> entry : inputPorts.entrySet()) {
			Connection connection = entry.getKey();
			Port port = entry.getValue();
			int size = port.getNumTokensConsumed();
			Type type = IrFactory.eINSTANCE
					.createTypeList(size, port.getType());
			Var var = new VarGlobal(new Location(), type,
					port.getName(), true);
			buffersMap.put(connection, var);
			String srcName = graph.getEdgeTarget(connection).getInstance()
					.getId()
					+ "" + connection.getTarget().getName();
			internalBuffersMap.put(srcName, var);
			portsMap.put(srcName, connection.getTarget());

		}

		// Create internal buffer for outputs
		for (Map.Entry<Connection, Port> entry : outputPorts.entrySet()) {
			Connection connection = entry.getKey();
			Port port = entry.getValue();

			int size = port.getNumTokensProduced();
			Type type = IrFactory.eINSTANCE
					.createTypeList(size, port.getType());
			Var var = new VarGlobal(new Location(), type,
					port.getName(), true);
			buffersMap.put(connection, var);
			String srcName = graph.getEdgeSource(connection).getInstance()
					.getId()
					+ "" + connection.getSource().getName();
			internalBuffersMap.put(srcName, var);
			portsMap.put(srcName, connection.getSource());
		}

		// Create internal buffer connections inside the cluster
		for (Map.Entry<Connection, Integer> entry : scheduler
				.getBufferCapacities().entrySet()) {
			Connection connection = entry.getKey();
			String name = "_static_buf_" + index++;
			Type type = IrFactory.eINSTANCE.createTypeList(entry.getValue(),
					connection.getSource().getType());
			Var buf = new VarGlobal(new Location(), type, name, true);
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
			List<Node> nodes) throws OrccException {
		for (Iterand iterand : schedule.getIterands()) {
			if (iterand.isVertex()) {
				Instance instance = iterand.getVertex().getInstance();
				Action action = instance.getActor().getActions().get(0);
				Procedure proc = actor.getProcs().get(
						instance.getId() + "_" + action.getName());

				NodeBlock blkNode = procedure.getLast(nodes);
				blkNode.add(new Call(new Location(), null, proc,
						new ArrayList<Expression>()));
			} else {
				Schedule sched = iterand.getSchedule();

				OrderedMap<String, VarLocal> locals = procedure
						.getLocals();
				VarLocal loopVar = (VarLocal) locals.get("idx_"
						+ depth);

				// Reset loop counter
				NodeBlock blkNode = procedure.getLast(nodes);
				blkNode.add(new Assign(loopVar, new IntExpr(0)));

				Expression condition = new BinaryExpr(new VarExpr(new Use(
						loopVar)), BinaryOp.LT, new IntExpr(
						sched.getIterationCount()),
						IrFactory.eINSTANCE.createTypeBool());

				NodeWhile nodeWhile = IrFactoryImpl.eINSTANCE.createNodeWhile();
				nodeWhile
						.setJoinNode(IrFactoryImpl.eINSTANCE.createNodeBlock());
				nodeWhile.setValue(condition);
				nodes.add(nodeWhile);

				depth++;

				createLoopedSchedule(procedure, sched, nodeWhile.getNodes());

				depth--;

				// Increment current while loop variable
				Assign assign = new Assign(loopVar, new BinaryExpr(new VarExpr(
						new Use(loopVar)), BinaryOp.PLUS, new IntExpr(1),
						loopVar.getType()));
				procedure.getLast(nodeWhile.getNodes()).add(assign);

			}
		}
	}

	/**
	 * Creates the read instructions of the static action
	 */
	private void createReads(Procedure procedure) {
		for (Port port : actor.getInputs()) {
			Var var = actor.getStateVars().get(port.getName());
			int numTokens = port.getNumTokensConsumed();
			System.out.println("must read " + numTokens + " tokens from "
					+ var.getName());
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
		variables = new OrderedMap<String, VarLocal>();
		List<Node> nodes = new ArrayList<Node>();
		Procedure procedure = IrFactory.eINSTANCE.createProcedure(
				SCHEDULER_NAME, false, location,
				IrFactory.eINSTANCE.createTypeBool(),
				new OrderedMap<String, VarLocal>(), variables, nodes);
		NodeBlock block = IrFactoryImpl.eINSTANCE.createNodeBlock();
		nodes.add(block);
		createInputCondition(block);
		return procedure;
	}

	/**
	 * Creates the write instructions of the static action
	 */
	private void createWrites(Procedure procedure) {
		for (Port port : actor.getOutputs()) {
			Var var = actor.getStateVars().get(port.getName());
			int numTokens = port.getNumTokensProduced();
			System.out.println("must write " + numTokens + " tokens from "
					+ var.getName());
		}
	}

	private OrderedMap<String, Port> getInputs(Set<Vertex> vertices) {
		OrderedMap<String, Port> inputs = new OrderedMap<String, Port>();
		inputPorts = new HashMap<Connection, Port>();

		int index = 0;
		for (Connection connection : graph.edgeSet()) {
			Vertex src = graph.getEdgeSource(connection);
			Vertex tgt = graph.getEdgeTarget(connection);

			if (!vertices.contains(src) && vertices.contains(tgt)) {
				Port tgtPort = connection.getTarget();
				Port port = new Port(tgtPort);
				port.setName("_input_" + index);

				int rep = scheduler.getRepetitionVector().get(tgt);
				port.increaseTokenConsumption(rep
						* tgtPort.getNumTokensConsumed());
				inputs.put(port.getName(), port);
				index++;

				// fill the map for the later update of connections
				inputPorts.put(connection, port);
			}
		}
		return inputs;
	}

	private OrderedMap<String, Port> getOutputs(Set<Vertex> vertices) {
		OrderedMap<String, Port> outputs = new OrderedMap<String, Port>();
		outputPorts = new HashMap<Connection, Port>();

		int index = 0;
		for (Connection connection : graph.edgeSet()) {
			Vertex src = graph.getEdgeSource(connection);
			Vertex tgt = graph.getEdgeTarget(connection);

			if (vertices.contains(src) && !vertices.contains(tgt)) {
				Port srcPort = connection.getSource();
				Port port = new Port(srcPort);
				port.setName("_output_" + index);

				int rep = scheduler.getRepetitionVector().get(src);
				port.increaseTokenProduction(rep
						* srcPort.getNumTokensProduced());
				outputs.put(port.getName(), port);
				index++;

				// fill the map for the later update of connections
				outputPorts.put(connection, port);

			}
		}
		return outputs;
	}

	/**
	 * Tries to merge actors.
	 * 
	 * @throws OrccException
	 */
	private void mergeActors(Set<Vertex> vertices) throws OrccException {
		createActor(vertices);
		String name = actor.getName();
		Instance instance = new Instance(name.toLowerCase(), name);
		instance.setContents(actor);
		Vertex[] v = (Vertex[]) vertices.toArray(new Vertex[0]);
		instance.getAttributes().putAll(v[0].getInstance().getAttributes());

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

			scheduler = new SASFlatScheduler(subgraph);

			for (Vertex vertex : vertices) {
				// Actor actor = vertex.getInstance().getActor();
				System.out.println("used to remove read writes here " + vertex);
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
