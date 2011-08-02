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
import net.sf.orcc.ir.InstLoad;
import net.sf.orcc.ir.InstReturn;
import net.sf.orcc.ir.InstStore;
import net.sf.orcc.ir.IrFactory;
import net.sf.orcc.ir.Node;
import net.sf.orcc.ir.NodeBlock;
import net.sf.orcc.ir.NodeWhile;
import net.sf.orcc.ir.OpBinary;
import net.sf.orcc.ir.Pattern;
import net.sf.orcc.ir.Port;
import net.sf.orcc.ir.Procedure;
import net.sf.orcc.ir.Type;
import net.sf.orcc.ir.TypeList;
import net.sf.orcc.ir.Var;
import net.sf.orcc.moc.CSDFMoC;
import net.sf.orcc.moc.Invocation;
import net.sf.orcc.moc.MocFactory;
import net.sf.orcc.moc.SDFMoC;
import net.sf.orcc.network.Connection;
import net.sf.orcc.network.Instance;
import net.sf.orcc.network.Network;
import net.sf.orcc.network.Vertex;
import net.sf.orcc.network.transformations.INetworkTransformation;

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

	private AbstractScheduler scheduler;

	private int index;

	private HashMap<Connection, Port> outputs;

	private HashMap<Connection, Port> inputs;

	private Set<Vertex> vertices;

	private Map<Port, Port> portsMap;

	private Map<Port, Var> inputToVarMap;

	private int depth;

	private Map<Port, Var> buffersMap;

	private void addBuffer(String name, int size, Type eltType) {
		IrFactory factory = IrFactory.eINSTANCE;
		Type type = factory.createTypeList(size, eltType);
		Var var = factory.createVar(0, type, name, true, true);
		superActor.getStateVars().add(var);
	}

	private void addCounter(String name) {
		IrFactory factory = IrFactory.eINSTANCE;
		Var varCount = factory.createVar(0, factory.createTypeInt(32), name,
				true, factory.createExprInt(0));
		superActor.getStateVars().add(varCount);
	}

	private void copyProcedures() {
		for (Vertex vertex : vertices) {
			String id = vertex.getInstance().getId();
			Actor actor = vertex.getInstance().getActor();
			for (Procedure proc : new ArrayList<Procedure>(actor.getProcs())) {
				proc.setName(id + "_" + proc.getName());
				superActor.getProcs().add(proc);
			}
		}
	}

	private void copyStateVariables() {
		for (Vertex vertex : vertices) {
			String id = vertex.getInstance().getId();
			Actor actor = vertex.getInstance().getActor();
			for (Var var : new ArrayList<Var>(actor.getStateVars())) {
				String name = var.getName();
				actor.getStateVar(name).setName(id + "_" + name);
				superActor.getStateVars().add(var);
			}
		}
	}

	/**
	 * 
	 * @param ip
	 * @param op
	 * @return
	 */
	private Procedure createBody(Pattern ip, Pattern op) {
		IrFactory factory = IrFactory.eINSTANCE;

		Procedure procedure = factory.createProcedure(ACTION_NAME, 0,
				IrFactory.eINSTANCE.createTypeVoid());

		// Add loop counter(s)
		int i = 0;
		do { // one loop var is required even if the schedule as a depth of 0
			Var counter = factory.createVar(0, factory.createTypeInt(32),
					"idx_" + i, false, true);
			procedure.getLocals().add(counter);
			i++;
		} while (i < scheduler.getDepth());

		// Add temp vars to load/store data from ports to buffers
		for (Port port : ip.getPorts()) {
			Type type = port.getType();
			String name = port.getName();
			Var tmp = factory.createVar(0, type, "tmp_" + name, false, true);
			procedure.getLocals().add(tmp);
		}

		for (Port port : op.getPorts()) {
			Type type = port.getType();
			String name = port.getName();
			Var tmp = factory.createVar(0, type, "tmp_" + name, false, true);
			procedure.getLocals().add(tmp);
		}

		// Initialize read/write counters
		for (Var var : new HashSet<Var>(buffersMap.values())) {
			Var read = superActor.getStateVar(var.getName() + "_r");
			Var write = superActor.getStateVar(var.getName() + "_w");

			NodeBlock block = procedure.getLast();
			if (read != null) {
				block.add(factory.createInstStore(read,
						factory.createExprInt(0)));
			}
			if (write != null) {
				block.add(factory.createInstStore(write,
						factory.createExprInt(0)));
			}
		}

		createCopiesFromInputs(procedure, ip);

		createStaticSchedule(procedure, scheduler.getSchedule(),
				procedure.getNodes());

		createCopiesToOutputs(procedure, op);

		return procedure;
	}

	/**
	 * copies the value of source into target
	 * 
	 * @param procedure
	 * @param source
	 * @param target
	 */
	private void createCopies(Procedure procedure, String name, Var source,
			Var target) {
		IrFactory factory = IrFactory.eINSTANCE;

		List<Node> nodes = procedure.getNodes();
		Var loop = procedure.getLocal("idx_0");
		NodeBlock block = procedure.getLast(nodes);
		block.add(factory.createInstAssign(loop, factory.createExprInt(0)));

		Expression condition = factory.createExprBinary(
				factory.createExprVar(loop), OpBinary.LT,
				factory.createExprInt(((TypeList) source.getType()).getSize()),
				factory.createTypeBool());

		NodeWhile nodeWhile = factory.createNodeWhile();
		nodeWhile.setJoinNode(factory.createNodeBlock());
		nodeWhile.setCondition(condition);
		nodes.add(nodeWhile);

		Var tmpVar = procedure.getLocal("tmp_" + name);
		List<Expression> indexes = new ArrayList<Expression>();
		indexes.add(factory.createExprVar(loop));
		InstLoad load = factory.createInstLoad(tmpVar, source, indexes);

		indexes = new ArrayList<Expression>();
		indexes.add(factory.createExprVar(loop));
		InstStore store = factory.createInstStore(0, target, indexes,
				factory.createExprVar(tmpVar));
		NodeBlock childBlock = procedure.getLast(nodeWhile.getNodes());
		childBlock.add(load);
		childBlock.add(store);

		// increment loop counter
		Expression expr = factory.createExprBinary(factory.createExprVar(loop),
				OpBinary.PLUS, factory.createExprInt(1), loop.getType());
		InstAssign assign = factory.createInstAssign(loop, expr);
		childBlock.add(assign);
	}

	private void createCopiesFromInputs(Procedure procedure, Pattern ip) {
		for (Port port : superActor.getInputs()) {
			Var input = ip.getVariable(port);
			Var buffer = inputToVarMap.get(port);

			createCopies(procedure, port.getName(), input, buffer);
		}
	}

	private void createCopiesToOutputs(Procedure procedure, Pattern op) {
		for (Port port : superActor.getOutputs()) {
			Var output = op.getVariable(port);
			Var buffer = inputToVarMap.get(port);

			createCopies(procedure, port.getName(), buffer, output);
		}
	}

	/**
	 * Creates a return instruction that uses the results of the hasTokens tests
	 * previously created. Returns true right now.
	 * 
	 * @param block
	 *            block to which return is to be added
	 */
	private void createInputCondition(NodeBlock block) {
		final IrFactory factory = IrFactory.eINSTANCE;
		InstReturn returnInstr = factory.createInstReturn(factory
				.createExprBool(true));
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
		SDFMoC moc = (SDFMoC) superActor.getMoC();
		for (Port port : superActor.getInputs()) {
			int numTokens = moc.getNumTokensConsumed(port);
			Type type = factory.createTypeList(numTokens,
					EcoreUtil.copy(port.getType()));
			Var var = factory.createVar(0, type, port.getName(), false, true);
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
		SDFMoC moc = (SDFMoC) superActor.getMoC();
		for (Port port : superActor.getOutputs()) {
			int numTokens = moc.getNumTokensProduced(port);
			Type type = factory.createTypeList(numTokens,
					EcoreUtil.copy(port.getType()));
			Var var = factory.createVar(0, type, port.getName(), false, true);
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
		IrFactory factory = IrFactory.eINSTANCE;
		Pattern inputPattern = factory.createPattern();
		Pattern outputPattern = factory.createPattern();

		SDFMoC sdfMoC = MocFactory.eINSTANCE.createSDFMoC();
		sdfMoC.setInputPattern(inputPattern);
		sdfMoC.setOutputPattern(outputPattern);
		superActor.setMoC(sdfMoC);

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
				Port port = factory
						.createPort(EcoreUtil.copy(tgtPort.getType()), "input_"
								+ inIndex++);

				CSDFMoC moc = (CSDFMoC) tgt.getInstance().getMoC();
				int cns = scheduler.getRepetitionVector().get(tgt)
						* moc.getNumTokensConsumed(tgtPort);

				inputPattern.setNumTokens(port, cns);

				superActor.getInputs().add(port);
				inputs.put(connection, port);
				portsMap.put(port, tgtPort);

			} else if (vertices.contains(src) && !vertices.contains(tgt)) {
				Port srcPort = connection.getSource();
				Port port = factory.createPort(
						EcoreUtil.copy(srcPort.getType()), "output_"
								+ outIndex++);

				CSDFMoC moc = (CSDFMoC) src.getInstance().getMoC();
				int prd = scheduler.getRepetitionVector().get(src)
						* moc.getNumTokensProduced(srcPort);

				outputPattern.setNumTokens(port, prd);

				superActor.getOutputs().add(port);
				outputs.put(connection, port);
				portsMap.put(port, srcPort);
			}
		}
	}

	/**
	 * Turns actions of CSDF actors into procedures
	 * 
	 */
	private void createProcedures() {
		IrFactory factory = IrFactory.eINSTANCE;
		for (Vertex vertex : scheduler.getSchedule().getActors()) {
			Instance instance = vertex.getInstance();

			CSDFMoC moc = (CSDFMoC) instance.getMoC();
			Iterator<Invocation> it = moc.getInvocations().iterator();

			Set<Action> alreadyExists = new HashSet<Action>();
			while (it.hasNext()) {
				Action action = it.next().getAction();
				if (!alreadyExists.contains(action)) {
					alreadyExists.add(action);
					String name = instance.getId() + "_" + action.getName();
					Procedure proc = factory.createProcedure(name, 0,
							factory.createTypeVoid());

					Procedure body = action.getBody();
					List<Node> nodes = body.getNodes();
					proc.getLocals().addAll(body.getLocals());
					proc.getNodes().addAll(nodes);
					proc.getLast(nodes).add(factory.createInstReturn());
					superActor.getProcs().add(proc);
					new ChangeFifoArrayAccess(action.getInputPattern(),
							action.getOutputPattern(), buffersMap)
							.doSwitch(superActor);

				}
			}
		}
	}

	/**
	 * Creates the scheduler of the static action.
	 * 
	 * @return the scheduler of the static action
	 */
	private Procedure createScheduler() {
		IrFactory factory = IrFactory.eINSTANCE;
		Procedure procedure = IrFactory.eINSTANCE.createProcedure(
				SCHEDULER_NAME, 0, IrFactory.eINSTANCE.createTypeBool());

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
		inputToVarMap = new HashMap<Port, Var>();
		SDFMoC sdfMoc = (SDFMoC) superActor.getMoC();

		int index = 0;
		// Create buffers for inputs
		for (Port port : superActor.getInputs()) {
			String name = "buffer_" + index++;
			int numTokens = sdfMoc.getNumTokensConsumed(port);
			addBuffer(name, numTokens, port.getType());
			addCounter(name + "_r");
			inputToVarMap.put(port, superActor.getStateVar(name));
			buffersMap.put(portsMap.get(port), superActor.getStateVar(name));
		}

		// Create buffers for outputs
		for (Port port : superActor.getOutputs()) {
			String name = "buffer_" + index++;
			int numTokens = sdfMoc.getNumTokensProduced(port);
			addBuffer(name, numTokens, port.getType());
			addCounter(name + "_w");
			inputToVarMap.put(port, superActor.getStateVar(name));
			buffersMap.put(portsMap.get(port), superActor.getStateVar(name));
		}

		// Create buffers for connections inside the sub-graph
		for (Map.Entry<Connection, Integer> entry : scheduler.getMaxTokens()
				.entrySet()) {
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
	 */
	private void createStaticAction() {
		IrFactory factory = IrFactory.eINSTANCE;

		Pattern ip = createInputPattern();
		Pattern op = createOutputPattern();
		Procedure scheduler = createScheduler();
		Procedure body = createBody(ip, op);

		Action action = factory.createAction(factory.createTag(), ip, op,
				factory.createPattern(), scheduler, body);

		superActor.getActions().add(action);
		superActor.getActionsOutsideFsm().add(action);
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
				CSDFMoC moc = (CSDFMoC) instance.getMoC();
				NodeBlock block = procedure.getLast(nodes);
				for (Invocation invocation : moc.getInvocations()) {
					Action action = invocation.getAction();
					Procedure proc = superActor.getProcedure(instance.getId()
							+ "_" + action.getName());
					block.add(factory.createInstCall(0, null, proc,
							new ArrayList<Expression>()));

				}

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
						factory.createTypeBool());

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
		String name = "SuperActor_" + index;
		superActor.setName(name);

		createPorts();

		copyStateVariables();

		copyProcedures();

		createStateVariables();

		createProcedures();

		createStaticAction();
	}

	@Override
	public void transform(Network network) throws OrccException {
		graph = network.getGraph();

		// make instance unique in the network
		new UniqueInstantiator().transform(network);

		// static region detections
		StaticRegionDetector detector = new StaticRegionDetector(network);
		for (Set<Vertex> vertices : detector.staticRegionSets()) {
			this.vertices = vertices;

			DirectedGraph<Vertex, Connection> subgraph = new DirectedSubgraph<Vertex, Connection>(
					graph, vertices, null);

			// create the static schedule of vertices
			scheduler = new SASLoopScheduler(subgraph);
			scheduler.schedule();

			// merge vertices inside a single actor
			mergeActors();

			// update the graph
			Instance instance = new Instance("superActor_" + index,
					"SuperActor_" + index++);
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
