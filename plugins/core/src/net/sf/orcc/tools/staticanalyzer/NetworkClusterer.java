/*
 * Copyright (c) 2010, Ecole Polytechnique Fédérale de Lausanne 
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
 *   * Neither the name of the Ecole Polytechnique Fédérale de Lausanne nor the 
 *     names of its contributors may be used to endorse or promote products 
 *     derived from this software without specific prior written permission.
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

package net.sf.orcc.tools.staticanalyzer;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import net.sf.orcc.OrccException;
import net.sf.orcc.ir.GlobalVariable;
import net.sf.orcc.ir.Port;
import net.sf.orcc.network.Connection;
import net.sf.orcc.network.Instance;
import net.sf.orcc.network.Network;
import net.sf.orcc.network.Vertex;
import net.sf.orcc.util.OrderedMap;
import net.sf.orcc.util.Scope;

import org.jgrapht.DirectedGraph;
import org.jgrapht.graph.DirectedMultigraph;

public class NetworkClusterer {

	protected DirectedGraph<Vertex, Connection> graph;

	protected Network network;

	private int nbInput;

	private int nbOutput;

	private Map<Port, Port> fanout = new HashMap<Port, Port>();

	private Map<Connection, Port> outgoingPort = new HashMap<Connection, Port>();

	private Map<Connection, Port> incomingPort = new HashMap<Connection, Port>();

	public NetworkClusterer(Network network) {
		this.network = network;
		this.graph = network.getGraph();
	}

	private void addInternalConnection(Vertex src, Vertex tgt,
			Connection connection, Network network) {
		Connection internal = new Connection(connection.getSource(), connection
				.getTarget(), connection.getAttributes());
		network.getGraph().addEdge(src, tgt, internal);

	}

	/**
	 * Creates an input port's vertex and a connection if the source of the
	 * connection is on a different partition.
	 * 
	 */

	private void createIncomingConnection(Vertex src, Vertex tgt,
			Connection connection, Network network) throws OrccException {

		Port port = new Port(connection.getSource());
		port.setName("input_" + nbInput++);

		Vertex vertex = new Vertex("Input", port);
		network.getGraph().addVertex(vertex);

		network.getInputs().add("", port.getLocation(), port.getName(), port);

		Connection incoming = new Connection(null, connection.getTarget(),
				connection.getAttributes());

		network.getGraph().addEdge(vertex, tgt, incoming);
		incomingPort.put(connection, port);
	}

	/**
	 * Creates an output port's vertex and a connection if the source of the
	 * connection is on a different partition
	 * 
	 */

	private void createOutgoingConnection(Vertex src, Vertex tgt,
			Connection connection, Network network) throws OrccException {

		Port port = new Port(connection.getTarget());
		port.setName("output_" + nbOutput++);
		Vertex vertex = new Vertex("Output", port);

		if (!fanout.containsKey(connection.getSource())) {
			network.getGraph().addVertex(vertex);
			network.getOutputs().add("", port.getLocation(), port.getName(),
					port);

			Connection outgoing = new Connection(connection.getSource(), null,
					connection.getAttributes());

			network.getGraph().addEdge(src, vertex, outgoing);
			outgoingPort.put(connection, port);
			fanout.put(connection.getSource(), port);
		} else {
			outgoingPort.put(connection, fanout.get(connection.getSource()));
		}
	}

	private void createConnections(Set<Vertex> vertices, Network network)
			throws OrccException {

		nbInput = nbOutput = 0;

		for (Vertex vertex : vertices) {
			for (Connection connection : graph.incomingEdgesOf(vertex)) {
				Vertex src = graph.getEdgeSource(connection);
				Vertex tgt = graph.getEdgeTarget(connection);

				if (vertices.contains(src)) {
					addInternalConnection(src, tgt, connection, network);

				} else {
					createIncomingConnection(src, tgt, connection, network);
				}
			}

			for (Connection connection : graph.outgoingEdgesOf(vertex)) {

				Vertex src = graph.getEdgeSource(connection);
				Vertex tgt = graph.getEdgeTarget(connection);

				if (!vertices.contains(tgt)) {
					createOutgoingConnection(src, tgt, connection, network);

				}
			}
		}
	}

	protected Network createPartition(String name, Set<Vertex> vertices)
			throws OrccException {

		// File file = new File(path + File.separator + name + ".xdf");

		DirectedGraph<Vertex, Connection> graph = new DirectedMultigraph<Vertex, Connection>(
				Connection.class);
		OrderedMap<Port> inputs = new OrderedMap<Port>();
		Map<String, Instance> instances = new HashMap<String, Instance>();
		OrderedMap<Port> outputs = new OrderedMap<Port>();
		Scope<GlobalVariable> parameters = new Scope<GlobalVariable>();
		Scope<GlobalVariable> variables = new Scope<GlobalVariable>(parameters,
				false);

		Network subNetwork = new Network(name, inputs, outputs, parameters,
				variables, graph);

		for (Vertex vertex : vertices) {
			Instance instance = vertex.getInstance();

			instances.put(instance.getId(), instance);
			graph.addVertex(new Vertex(instance));

		}

		for (GlobalVariable var : network.getVariables()) {
			GlobalVariable newVar = new GlobalVariable(var.getLocation(), var
					.getType(), var.getName(), var.getExpression());

			variables.add("", newVar.getLocation(), newVar.getName(), newVar);
		}

		createConnections(vertices, subNetwork);

		// new XDFWriter(new File(path), subNetwork);
		return subNetwork;
	}

	public Vertex cluster(String name, Set<Vertex> vertices)
			throws OrccException {

		Network cluster = createPartition(name, vertices);

		Instance instance = new Instance(name, cluster);
		// Instance instance = new Instance(path, name, cluster.getName(),
		// new HashMap<String, Expression>(),
		// new HashMap<String, IAttribute>());

		Vertex vertex = new Vertex(instance);
		graph.addVertex(vertex);

		Set<Connection> connections = new HashSet<Connection>(graph.edgeSet());

		for (Connection connection : connections) {
			if (incomingPort.containsKey(connection)) {

				Port srcPort = connection.getSource();
				Port tgtPort = incomingPort.get(connection);
				Connection conn = new Connection(srcPort, tgtPort, connection
						.getAttributes());

				Vertex src = graph.getEdgeSource(connection);
				graph.addEdge(src, vertex, conn);
			}

			if (outgoingPort.containsKey(connection)) {

				Port srcPort = outgoingPort.get(connection);
				Port tgtPort = connection.getTarget();
				Connection conn = new Connection(srcPort, tgtPort, connection
						.getAttributes());

				Vertex tgt = graph.getEdgeTarget(connection);
				graph.addEdge(vertex, tgt, conn);
			}

		}
		graph.removeAllVertices(vertices);

		return vertex;
	}

}
