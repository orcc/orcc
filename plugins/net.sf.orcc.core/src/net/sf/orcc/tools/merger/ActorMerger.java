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
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;

import net.sf.orcc.OrccException;
import net.sf.orcc.ir.Action;
import net.sf.orcc.ir.ActionScheduler;
import net.sf.orcc.ir.Actor;
import net.sf.orcc.ir.CFGNode;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.Instruction;
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
import net.sf.orcc.ir.transforms.AbstractActorTransformation;
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

	private class ModifyAccess extends AbstractActorTransformation {

		private OrderedMap<String, StateVariable> stateVars;

		private String id;

		public ModifyAccess(String id) {
			this.id = id;
		}

		@Override
		public void transform(Actor actor) {
			stateVars = actor.getStateVars();
			super.transform(actor);
		}

		@SuppressWarnings("unchecked")
		private void updateIndex(Variable var, Instruction instr,
				List<Expression> indexes, String mode, Object... args) {
			Variable varCount = stateVars.get(var.getName() + mode);
			Use use = new Use(varCount, instr);
			indexes.set(0, new VarExpr(use));

			indexes = new ArrayList<Expression>(0);
			use = new Use(varCount);
			Store store = new Store(varCount, indexes, getExpr(varCount, 1,
					((TypeList) var.getType()).getSize()));
			use.setNode(store);

			ListIterator<Instruction> it = (ListIterator<Instruction>) args[0];
			it.add(store);
		}

		@Override
		public void visit(Load load, Object... args) {
			Use use = load.getSource();
			Variable var = use.getVariable();
			if (!var.isGlobal() && var.isPort()) {
				var = portsMap.get(id + var.getName());
				load.setSource(new Use(stateVars.get(var.getName()), load));
				updateIndex(var, load, load.getIndexes(), "_r", args);
			}
		}

		@Override
		public void visit(Store store, Object... args) {
			Variable var = store.getTarget();
			if (!var.isGlobal() && var.isPort()) {
				var = portsMap.get(id + var.getName());
				store.setTarget(var);
				updateIndex(var, store, store.getIndexes(), "_w", args);
			}
		}

	}

	private static final String ACTION_NAME = "schedule_loop";

	private static final String SCHEDULER_NAME = "isSchedulable_" + ACTION_NAME;

	private int clusterIdx = 0;

	private DirectedGraph<Vertex, Connection> graph;

	private IScheduler scheduler;

	private OrderedMap<String, Variable> variables;

	private List<LocalVariable> indexes;

	private int depth = 0;

	private Map<Connection, Port> outputPorts;

	private Map<Connection, Port> inputPorts;

	private Map<String, Variable> portsMap;

	private Map<Connection, Variable> buffersMap;

	private Actor actor;

	/**
	 * Converts a given action into a procedure
	 * 
	 * @param action
	 */
	private Procedure convertAction(String id, Action action) {
		OrderedMap<String, Variable> parameters = new OrderedMap<String, Variable>();
		OrderedMap<String, Variable> locals = action.getBody().getLocals();
		List<CFGNode> nodes = action.getBody().getNodes();

		return new Procedure(id + "_" + action.getName(), false,
				new Location(), IrFactory.eINSTANCE.createTypeVoid(),
				parameters, locals, nodes);
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

		for (Port port : actor.getInputs()) {
			inputPattern.put(port, port.getNumTokensConsumed());
		}

		for (Port port : actor.getOutputs()) {
			outputPattern.put(port, port.getNumTokensProduced());
		}

		Procedure scheduler = createScheduler();

		Procedure body = createBody();

		return new Action(new Location(), new Tag(), inputPattern,
				outputPattern, scheduler, body);
	}

	/**
	 * 
	 */
	private Actor createActor(Set<Vertex> vertices) throws OrccException {
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
		// addProceduresAndStateVars();

		Action action = createAction();
		actor.getActions().add(action);

		return actor;
	}

	/**
	 * @throws OrccException
	 * 
	 */
	@SuppressWarnings("unused")
	private void addProceduresAndStateVars() throws OrccException {
		for (Vertex vertex : scheduler.getSchedule().getActors()) {
			Instance instance = vertex.getInstance();

			for (StateVariable oldVar : instance.getActor().getStateVars()) {
				String name = instance.getId() + "_" + oldVar.getName();
				StateVariable newVar = new StateVariable(new Location(),
						oldVar.getType(), name, oldVar.isAssignable(),
						oldVar.getConstantValue());

				List<Use> uses = new ArrayList<Use>(oldVar.getUses());
				for (Use use : uses) {
					use.setVariable(newVar);
				}
				actor.getStateVars().put(newVar.getName(), newVar);
			}

			for (Procedure oldProc : instance.getActor().getProcs()) {
				String name = instance.getId() + "_" + oldProc.getName();
				Procedure newProc = new Procedure(name, false, new Location(),
						oldProc.getReturnType(), oldProc.getParameters(),
						oldProc.getLocals(), oldProc.getNodes());
				actor.getProcs().put(name, newProc);
			}
		}
	}

	/**
	 * Creates the body of the static action.
	 * 
	 * @return the body of the static action
	 * @throws OrccException
	 */
	private Procedure createBody() throws OrccException {
		List<CFGNode> nodes = new ArrayList<CFGNode>();
		indexes = new LinkedList<LocalVariable>();

		Procedure procedure = new Procedure(ACTION_NAME, false, new Location(),
				IrFactory.eINSTANCE.createTypeVoid(),
				new OrderedMap<String, Variable>(),
				new OrderedMap<String, Variable>(), nodes);

		addStateVariables();
		createReads(procedure);
		createLoopedSchedule(procedure, scheduler.getSchedule(), nodes);
		createWrites(procedure);
		return procedure;
	}

	private void addStateVariables() {
		OrderedMap<String, StateVariable> stateVars = actor.getStateVars();

		for (Variable var : buffersMap.values()) {
			stateVars.put(var.getName(), (StateVariable) var);

			StateVariable rCount = new StateVariable(new Location(),
					IrFactory.eINSTANCE.createTypeUint(32), var.getName()
							+ "_r", true, 0);
			stateVars.put(rCount.getName(), rCount);
			StateVariable wCount = new StateVariable(new Location(),
					IrFactory.eINSTANCE.createTypeUint(32), var.getName()
							+ "_w", true, 0);
			stateVars.put(wCount.getName(), wCount);
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
		portsMap = new HashMap<String, Variable>();

		// Create internal buffer for inputs
		for (Map.Entry<Connection, Port> entry : inputPorts.entrySet()) {
			Port port = entry.getValue();
			int size = port.getNumTokensConsumed();
			Type type = IrFactory.eINSTANCE
					.createTypeList(size, port.getType());
			Variable var = new StateVariable(new Location(), type,
					port.getName(), true);
			buffersMap.put(entry.getKey(), var);
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
			portsMap.put(srcName, buf);
			String tgtName = graph.getEdgeSource(connection).getInstance()
					.getId()
					+ "" + connection.getSource().getName();

			portsMap.put(tgtName, buf);
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
				Vertex vertex = iterand.getVertex();
				Instance instance = vertex.getInstance();

				Procedure proc;
				if (instance.getActor().getActions().size() == 1) {
					Action action = instance.getActor().getActions().get(0);
					proc = convertAction(instance.getId(), action);
					actor.getProcs().put(proc.getName(), proc);
				} else {
					throw new OrccException(
							"SDF actor with multiple actions is not yet supported!");
				}
				new ModifyAccess(instance.getId()).transform(actor);
				new RemoveReadWrites().transform(actor);

				BlockNode blkNode = BlockNode.getLast(procedure, nodes);

				blkNode.add(new Call(new Location(), null, proc,
						new ArrayList<Expression>()));
			} else {
				Schedule sched = iterand.getSchedule();

				LocalVariable loopVar = null;
				if (indexes.size() <= depth) {
					loopVar = new LocalVariable(true, 0, new Location(), "idx_"
							+ depth, IrFactory.eINSTANCE.createTypeInt(32));
					indexes.add(loopVar);
					procedure.getLocals().put(loopVar.getName(), loopVar);
				} else {
					loopVar = (LocalVariable) procedure.getLocals().get(
							"idx_" + depth);
				}

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

	private Expression getExpr(Variable data, int offset, int modulo) {
		Expression expr = new BinaryExpr(new BinaryExpr(new VarExpr(new Use(
				data)), BinaryOp.PLUS, new IntExpr(offset), null),
				BinaryOp.MOD, new IntExpr(modulo), null);
		return expr;
	}

	/**
	 * Creates the read instructions of the static action
	 */
	private void createReads(Procedure procedure) {
		BlockNode block = BlockNode.getLast(procedure);
		for (Port port : actor.getInputs()) {
			Variable local = procedure.getLocals().get(port.getName());
			int numTokens = port.getNumTokensConsumed();
			Read read = new Read(port, numTokens, local);
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
			Variable local = procedure.getLocals().get(port.getName());
			int numTokens = port.getNumTokensProduced();
			Write read = new Write(port, numTokens, local);
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
				inputPorts.put(connection, port);

				actor.getInputs().put(port.getName(), port);
				index++;
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
				outputPorts.put(connection, port);

				actor.getOutputs().put(port.getName(), port);
				index++;
			}
		}
	}

	/**
	 * Tries to merge actors.
	 * 
	 * @return <code>true</code> if actors were merged, <code>false</code>
	 *         otherwise
	 * @throws OrccException
	 */
	private void mergeActors(Set<Vertex> vertices) throws OrccException {
		createActor(vertices);
		Vertex mergeVertex = new Vertex(new Instance(actor.getName(), actor));
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
