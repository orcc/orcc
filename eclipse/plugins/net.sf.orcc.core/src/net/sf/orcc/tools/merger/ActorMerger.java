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

	private Map<Port, Port> portsMap;

	private int depth;

	private Map<Port, Var> buffersMap;

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

		createStaticSchedule(procedure, scheduler.getSchedule(),
				procedure.getNodes());

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
	 * Creates the input pattern of the static action.
	 * 
	 * @return ip
	 */
	private Pattern createInputPattern() {
		IrFactory factory = IrFactory.eINSTANCE;
		Pattern ip = factory.createPattern();
		for (Port port : superActor.getInputs()) {
			int numTokens = port.getNumTokensConsumed();
			Type type = factory.createTypeList(numTokens,
					EcoreUtil.copy(port.getType()));
			Var var = factory.createVar(factory.createLocation(), type,
					port.getName(), false, true);
			ip.setNumTokens(port, numTokens);
			ip.setVariable(port, var);
		}
		return ip;
	}

	/**
	 * Creates the output pattern of the static action.
	 * 
	 * @return op
	 */
	private Pattern createOutputPattern() {
		IrFactory factory = IrFactory.eINSTANCE;
		Pattern op = factory.createPattern();
		for (Port port : superActor.getOutputs()) {
			int numTokens = port.getNumTokensProduced();
			Type type = factory.createTypeList(numTokens,
					EcoreUtil.copy(port.getType()));
			Var var = factory.createVar(factory.createLocation(), type,
					port.getName(), false, true);
			op.setNumTokens(port, numTokens);
			op.setVariable(port, var);
		}
		return op;
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
		portsMap = new HashMap<Port, Port>();

		int inIndex = 0;
		int outIndex = 0;
		for (Connection connection : graph.edgeSet()) {
			Vertex src = graph.getEdgeSource(connection);
			Vertex tgt = graph.getEdgeTarget(connection);

			if (!vertices.contains(src) && vertices.contains(tgt)) {
				Port tgtPort = connection.getTarget();
				Port port = IrFactory.eINSTANCE
						.createPort(IrFactory.eINSTANCE.createLocation(),
								EcoreUtil.copy(tgtPort.getType()), "input_"
										+ inIndex++);

				int cns = scheduler.getRepetitionVector().get(tgt)
						* tgtPort.getNumTokensConsumed();
				port.increaseTokenConsumption(cns);
				inputPattern.setNumTokens(port, cns);

				superActor.getInputs().add(port);
				inputs.put(connection, port);
				portsMap.put(port, tgtPort);

			} else if (vertices.contains(src) && !vertices.contains(tgt)) {
				Port srcPort = connection.getSource();
				Port port = IrFactory.eINSTANCE.createPort(
						IrFactory.eINSTANCE.createLocation(),
						EcoreUtil.copy(srcPort.getType()), "output_"
								+ outIndex++);

				int prd = scheduler.getRepetitionVector().get(src)
						* srcPort.getNumTokensProduced();
				port.increaseTokenProduction(prd);
				outputPattern.setNumTokens(port, prd);

				superActor.getOutputs().add(port);
				outputs.put(connection, port);
				portsMap.put(port, srcPort);
			}
		}

		SDFMoC sdfMoc = (SDFMoC) superActor.getMoC();
		sdfMoc.setInputPattern(inputPattern);
		sdfMoc.setOutputPattern(outputPattern);

	}

	/**
	 * Turns actions of SDF actors into procedures
	 * 
	 */
	private void createProcedures() {
		IrFactory factory = IrFactory.eINSTANCE;
		for (Vertex vertex : scheduler.getSchedule().getActors()) {
			Instance instance = vertex.getInstance();

			Iterator<Action> it = instance.getActor().getActions().iterator();
			// at this stage, SDF actor should have only one action
			if (it.hasNext()) {
				Action action = it.next();
				String name = instance.getId() + "_" + action.getName();
				Procedure proc = factory.createProcedure(name,
						factory.createLocation(), factory.createTypeVoid());

				Procedure body = action.getBody();
				List<Node> nodes = body.getNodes();
				proc.getNodes().addAll(nodes);
				NodeBlock block = proc.getLast(nodes);
				InstReturn ret = factory.createInstReturn();
				block.add(ret);
				superActor.getProcs().add(proc);

				new ChangeFifoArrayAccess(action.getInputPattern(),
						action.getOutputPattern(), buffersMap)
						.doSwitch(superActor);
			}
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

	/**
	 * Create global list variables to replace the FIFOs.
	 * 
	 */
	private void createStateVariables() {
		buffersMap = new HashMap<Port, Var>();

		int index = 0;
		// Create buffers for inputs
		for (Port port : superActor.getInputs()) {
			String name = "buffer_" + index++;
			int numTokens = port.getNumTokensConsumed();
			addBuffer(name, numTokens, port.getType());
			addCounter(name + "_r");
			buffersMap.put(portsMap.get(port), superActor.getStateVar(name));
		}

		// Create buffers for outputs
		for (Port port : superActor.getOutputs()) {
			String name = "buffer_" + index++;
			int numTokens = port.getNumTokensProduced();
			addBuffer(name, numTokens, port.getType());
			addCounter(name + "_w");
			buffersMap.put(portsMap.get(port), superActor.getStateVar(name));
		}

		// Create buffers for connections inside the sub-graph
		for (Map.Entry<Connection, Integer> entry : scheduler
				.getBufferCapacities().entrySet()) {
			String name = "buffer_" + index++;
			Connection conn = entry.getKey();
			addBuffer(name, entry.getValue(), conn.getSource().getType());
			addCounter(name + "_w");
			addCounter(name + "_r");
			buffersMap.put(conn.getSource(), superActor.getStateVar(name));
			buffersMap.put(conn.getTarget(), superActor.getStateVar(name));
		}
	}

	/**
	 * Creates the static action for this actor.
	 * 
	 * @return a static action
	 * @throws OrccException
	 */
	private void createStaticAction() {
		IrFactory factory = IrFactory.eINSTANCE;

		Pattern ip = createInputPattern();
		Pattern op = createOutputPattern();
		Procedure scheduler = createScheduler();
		Procedure body = createBody();

		Action action = factory.createAction(factory.createLocation(),
				factory.createTag(), ip, op,
				IrFactory.eINSTANCE.createPattern(), scheduler, body);

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

		createActionScheduler();

	}

	private void createActionScheduler() {
		superActor.getActionsOutsideFsm().addAll(superActor.getActions());
	}

	@Override
	public void transform(Network network) throws OrccException {
		graph = network.getGraph();

		DirectedGraph<Vertex, Connection> subgraph;

		// static region detections
		StaticSubsetDetector detector = new StaticSubsetDetector(network);
		for (Set<Vertex> vertices : detector.staticRegionSets()) {
			this.vertices = vertices;

			// make unique instances
			Set<Actor> actors = new HashSet<Actor>();
			for (Vertex vertex : vertices) {
				Instance inst = vertex.getInstance();

				if (actors.contains(inst.getActor())) {
					inst.setContents(EcoreUtil.copy(inst.getActor()));
				} else {
					actors.add(inst.getActor());
				}
			}

			subgraph = new DirectedSubgraph<Vertex, Connection>(graph,
					vertices, null);
			scheduler = new SASLoopScheduler(subgraph);

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
