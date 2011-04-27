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
import net.sf.orcc.ir.Actor;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.InstAssign;
import net.sf.orcc.ir.InstReturn;
import net.sf.orcc.ir.IrFactory;
import net.sf.orcc.ir.Node;
import net.sf.orcc.ir.NodeBlock;
import net.sf.orcc.ir.NodeWhile;
import net.sf.orcc.ir.OpBinary;
import net.sf.orcc.ir.Pattern;
import net.sf.orcc.ir.Port;
import net.sf.orcc.ir.Procedure;
import net.sf.orcc.ir.Type;
import net.sf.orcc.ir.Var;
import net.sf.orcc.moc.MocFactory;
import net.sf.orcc.moc.SDFMoC;
import net.sf.orcc.network.Connection;
import net.sf.orcc.network.Instance;
import net.sf.orcc.network.Network;
import net.sf.orcc.network.Vertex;
import net.sf.orcc.network.transformations.INetworkTransformation;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.jgrapht.DirectedGraph;
import org.jgrapht.graph.DirectedSubgraph;

/**
 * This class defines a network transformation that merges SDF actors.
 * 
 * 
 * @author Matthieu Wipliez
 * @author Ghislain Roquier
 * 
 */
public class ActorMerger implements INetworkTransformation {

	private static final String ACTION_NAME = "static_schedule";

	private static final String SCHEDULER_NAME = "isSchedulable_" + ACTION_NAME;

	private Actor superActor;

	private DirectedGraph<Vertex, Connection> graph;

	private SASLoopScheduler scheduler;

	private int index;

	private HashMap<Connection, Port> outputs;

	private HashMap<Connection, Port> inputs;

	private Set<Vertex> vertices;

	private EList<Var> variables;

	private int depth;

	private void addBuffer(String name, int size, Type eltType) {
		IrFactory factory = IrFactory.eINSTANCE;
		Type type = factory.createTypeList(size, eltType);
		Var var = factory.createVar(factory.createLocation(), type, name, true,
				true);
		superActor.getStateVars().add(var);
	}

	private void addCounter(String name) {
		IrFactory factory = IrFactory.eINSTANCE;
		Var varCount = factory
				.createVar(factory.createLocation(), factory.createTypeInt(32),
						name, true, factory.createExprInt(0));
		superActor.getStateVars().add(varCount);
	}

	private Procedure createBody() {
		IrFactory factory = IrFactory.eINSTANCE;

		Procedure procedure = factory.createProcedure("staticSchedule",
				factory.createLocation(), IrFactory.eINSTANCE.createTypeVoid());

		// Add loop counters
		for (int depth = 0; depth < scheduler.getDepth(); depth++) {
			Var counter = factory.createVar(factory.createLocation(),
					factory.createTypeInt(32), "idx_" + depth, false, true);
			procedure.getLocals().add(counter);
		}

		// Initialize read/write counters
		createReads(procedure);
		createStaticSchedule(procedure, scheduler.getSchedule(),
				procedure.getNodes());
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
		final IrFactory factory = IrFactory.eINSTANCE;

		Expression value;
		Iterator<Var> it = variables.iterator();
		if (it.hasNext()) {
			Var previous = it.next();
			value = factory.createExprVar(previous);

			while (it.hasNext()) {
				Var thisOne = it.next();
				value = factory.createExprBinary(value, OpBinary.LOGIC_AND,
						factory.createExprVar(thisOne),
						factory.createTypeBool());
				previous = thisOne;
			}
		} else {
			value = factory.createExprBool(true);
		}

		InstReturn returnInstr = factory.createInstReturn(value);
		block.add(returnInstr);
	}

	/**
	 * Creates input and output ports of the merged actor.
	 * 
	 */
	private void createPorts() {
		Pattern inputPattern = IrFactory.eINSTANCE.createPattern();
		Pattern outputPattern = IrFactory.eINSTANCE.createPattern();

		inputs = new HashMap<Connection, Port>();
		outputs = new HashMap<Connection, Port>();

		int index = 0;
		for (Connection connection : graph.edgeSet()) {
			Vertex src = graph.getEdgeSource(connection);
			Vertex tgt = graph.getEdgeTarget(connection);

			if (!vertices.contains(src) && vertices.contains(tgt)) {
				Port tgtPort = connection.getTarget();
				Port port = IrFactory.eINSTANCE.createPort(
						IrFactory.eINSTANCE.createLocation(),
						EcoreUtil.copy(tgtPort.getType()), "input_" + index++);

				int rep = scheduler.getRepetitionVector().get(tgt);
				port.increaseTokenConsumption(rep
						* tgtPort.getNumTokensConsumed());

				superActor.getInputs().add(port);
				inputs.put(connection, port);

				inputPattern.setNumTokens(port,
						rep * tgtPort.getNumTokensConsumed());

			} else if (vertices.contains(src) && !vertices.contains(tgt)) {
				Port srcPort = connection.getSource();
				Port port = IrFactory.eINSTANCE.createPort(
						IrFactory.eINSTANCE.createLocation(),
						EcoreUtil.copy(srcPort.getType()), "output_" + index++);

				port.increaseTokenProduction(scheduler.getRepetitionVector()
						.get(src) * srcPort.getNumTokensProduced());

				superActor.getOutputs().add(port);
				outputs.put(connection, port);

				int rep = scheduler.getRepetitionVector().get(src);
				outputPattern.setNumTokens(port,
						rep * srcPort.getNumTokensProduced());

			}
		}

		SDFMoC sdfMoc = (SDFMoC) superActor.getMoC();
		sdfMoc.setInputPattern(inputPattern);
		sdfMoc.setOutputPattern(outputPattern);

	}

	private void createProcedures() {
		IrFactory factory = IrFactory.eINSTANCE;
		for (Vertex vertex : scheduler.getSchedule().getActors()) {
			Instance instance = vertex.getInstance();

			for (Action action : instance.getActor().getActions()) {
				String name = instance.getId() + "_" + action.getName();
				Procedure proc = factory.createProcedure(name,
						factory.createLocation(), factory.createTypeVoid());

				Procedure body = action.getBody();
				proc.getNodes().addAll(body.getNodes());
				superActor.getProcs().add(proc);
			}
			new ChangeFifoArrayAccess(instance.getId(), instance.getActor())
					.doSwitch(superActor);
		}
	}

	/**
	 * Creates the read instructions of the static action
	 * 
	 * @param procedure
	 */
	private void createReads(Procedure procedure) {
		Pattern pattern = ((SDFMoC) superActor.getMoC()).getInputPattern();
		for (Port port : pattern.getPorts()) {
			Var var = superActor.getStateVar(port.getName());
			pattern.getPortToVarMap().put(port, var);
		}
	}

	/**
	 * Creates the scheduler of the static action.
	 * 
	 * @return the scheduler of the static action
	 * @throws OrccException
	 */
	private Procedure createScheduler() {
		IrFactory factory = IrFactory.eINSTANCE;
		Procedure procedure = IrFactory.eINSTANCE.createProcedure(
				SCHEDULER_NAME, factory.createLocation(),
				IrFactory.eINSTANCE.createTypeBool());

		variables = procedure.getLocals();

		NodeBlock block = factory.createNodeBlock();
		procedure.getNodes().add(block);
		createInputCondition(block);
		return procedure;
	}

	private void createStateVariables() {
		SDFMoC moc = (SDFMoC) superActor.getMoC();

		// Create buffers for inputs
		for (Port port : moc.getInputPattern().getPorts()) {
			String name = port.getName();
			int numTokens = moc.getInputPattern().getNumTokens(port);
			addBuffer(name, numTokens, port.getType());
			addCounter(name + "_r");
		}

		// Create buffers for outputs
		for (Port port : moc.getOutputPattern().getPorts()) {
			String name = port.getName();
			int numTokens = moc.getOutputPattern().getNumTokens(port);
			addBuffer(name, numTokens, port.getType());
			addCounter(name + "_w");
		}

		// Create buffers for connections inside the sub-graph
		int index = 0;
		for (Map.Entry<Connection, Integer> entry : scheduler
				.getBufferCapacities().entrySet()) {
			String name = "buffer_" + index++;

			addBuffer(name, entry.getValue(), entry.getKey().getSource()
					.getType());
			addCounter(name + "_w");
			addCounter(name + "_r");
		}
	}

	/**
	 * Creates the static action for this actor.
	 * 
	 * @return a static action
	 * @throws OrccException
	 */
	private void createStaticAction() {
		Procedure scheduler = createScheduler();
		Procedure body = createBody();
		SDFMoC moc = (SDFMoC) superActor.getMoC();

		IrFactory factory = IrFactory.eINSTANCE;
		Action action = factory.createAction(factory.createLocation(),
				factory.createTag(), moc.getInputPattern(),
				moc.getOutputPattern(), IrFactory.eINSTANCE.createPattern(),
				scheduler, body);

		superActor.getActions().add(action);
	}

	/**
	 * Create the static schedule of the action
	 * 
	 * @param procedure
	 * @param schedule
	 * @param nodes
	 */
	private void createStaticSchedule(Procedure procedure, Schedule schedule,
			List<Node> nodes) {

		IrFactory factory = IrFactory.eINSTANCE;

		for (Iterand iterand : schedule.getIterands()) {
			if (iterand.isVertex()) {
				Instance instance = iterand.getVertex().getInstance();
				Action action = instance.getActor().getActions().get(0);
				Procedure proc = superActor.getProcedure(instance.getId() + "_"
						+ action.getName());

				NodeBlock block = procedure.getLast(nodes);

				block.add(factory.createInstCall(factory.createLocation(),
						null, proc, new ArrayList<Expression>()));
			} else {
				Schedule sched = iterand.getSchedule();
				Var loopVar = procedure.getLocal("idx_" + depth);

				// init counter
				NodeBlock block = procedure.getLast(nodes);
				block.add(factory.createInstAssign(loopVar,
						factory.createExprInt(0)));

				// while loop
				Expression condition = factory.createExprBinary(
						factory.createExprVar(loopVar), OpBinary.LT,
						factory.createExprInt(sched.getIterationCount()),
						IrFactory.eINSTANCE.createTypeBool());

				NodeWhile nodeWhile = factory.createNodeWhile();
				nodeWhile.setJoinNode(factory.createNodeBlock());
				nodeWhile.setCondition(condition);
				nodes.add(nodeWhile);

				depth++;

				// recursion
				createStaticSchedule(procedure, sched, nodeWhile.getNodes());

				depth--;

				// Increment current while loop variable
				Expression expr = factory.createExprBinary(
						factory.createExprVar(loopVar), OpBinary.PLUS,
						factory.createExprInt(1), loopVar.getType());
				InstAssign assign = factory.createInstAssign(loopVar, expr);
				procedure.getLast(nodeWhile.getNodes()).add(assign);

			}
		}

	}

	/**
	 * Creates the read instructions of the static action
	 * 
	 * @param procedure
	 */
	private void createWrites(Procedure procedure) {
		Pattern pattern = ((SDFMoC) superActor.getMoC()).getOutputPattern();
		for (Port port : pattern.getPorts()) {
			Var var = superActor.getStateVar(port.getName());
			pattern.getPortToVarMap().put(port, var);
		}
	}

	/**
	 * Creates the merged actor.
	 * 
	 */
	private void mergeActors() {
		superActor = IrFactory.eINSTANCE.createActor();

		superActor.setName("SuperActor");
		superActor.setMoC(MocFactory.eINSTANCE.createSDFMoC());

		createPorts();

		createStateVariables();

		createProcedures();

		createStaticAction();

	}

	@Override
	public void transform(Network network) throws OrccException {
		graph = network.getGraph();

		DirectedGraph<Vertex, Connection> subgraph;

		// static region detections
		StaticSubsetDetector detector = new StaticSubsetDetector(network);
		for (Set<Vertex> vertices : detector.staticRegionSets()) {
			this.vertices = vertices;
			subgraph = new DirectedSubgraph<Vertex, Connection>(graph,
					vertices, null);
			scheduler = new SASLoopScheduler(subgraph);

			Set<Actor> actors = new HashSet<Actor>();
			for (Vertex vertex : vertices) {
				Instance inst = vertex.getInstance();

				if (actors.contains(inst.getActor())) {
					inst.setContents(EcoreUtil.copy(inst.getActor()));
				} else {
					actors.add(inst.getActor());
				}
			}

			// merge vertices inside a single actor
			mergeActors();

			// update the graph
			Instance instance = new Instance("superActor " + index++,
					"SuperActor");
			instance.setContents(superActor);

			Vertex mergeVertex = new Vertex(instance);
			graph.addVertex(mergeVertex);
			updateConnection(mergeVertex);
			graph.removeAllVertices(vertices);
		}
	}

	/**
	 * 
	 * @param merge
	 * @param vertices
	 */
	private void updateConnection(Vertex merge) {
		Set<Connection> connections = new HashSet<Connection>(graph.edgeSet());
		for (Connection connection : connections) {
			Vertex src = graph.getEdgeSource(connection);
			Vertex tgt = graph.getEdgeTarget(connection);

			if (!vertices.contains(src) && vertices.contains(tgt)) {
				Connection newConn = new Connection(connection.getSource(),
						inputs.get(connection), connection.getAttributes());
				graph.addEdge(graph.getEdgeSource(connection), merge, newConn);
			}

			if (vertices.contains(src) && !vertices.contains(tgt)) {
				Connection newConn = new Connection(outputs.get(connection),
						connection.getTarget(), connection.getAttributes());
				graph.addEdge(merge, graph.getEdgeTarget(connection), newConn);
			}
		}
	}
}
