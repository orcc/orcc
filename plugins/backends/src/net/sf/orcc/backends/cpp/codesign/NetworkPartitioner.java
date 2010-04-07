/*
 * Copyright (c) 2010,
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
 *   * Neither the name of the Ecole Polytechnique Fédérale de Lausanne 
 *     nor the names of its contributors may be used to endorse or promote products
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
package net.sf.orcc.backends.cpp.codesign;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.orcc.OrccException;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.GlobalVariable;
import net.sf.orcc.ir.Port;
import net.sf.orcc.ir.expr.StringExpr;
import net.sf.orcc.network.Connection;
import net.sf.orcc.network.Instance;
import net.sf.orcc.network.Network;
import net.sf.orcc.network.Vertex;
import net.sf.orcc.network.attributes.IAttribute;
import net.sf.orcc.network.attributes.IValueAttribute;
import net.sf.orcc.network.serialize.XDFWriter;
import net.sf.orcc.util.OrderedMap;
import net.sf.orcc.util.Scope;

import org.jgrapht.DirectedGraph;
import org.jgrapht.graph.DirectedMultigraph;

/**
 * This class defines a transformation that partition a given network into a
 * hierarchical network according to attributes of instances.
 * 
 * @author Ghislain Roquier
 * 
 */

public class NetworkPartitioner {

	private File file;

	/**
	 * 
	 */
	private File folder;

	private Boolean manageFanout;

	private DirectedGraph<Vertex, Connection> graph;

	private Network network;

	private Map<Port, Vertex> incomingFanout;

	private Map<Connection, Port> incomingPort = new HashMap<Connection, Port>();

	private int nbInput;

	private int nbOutput;

	private Map<Port, Map<String, Vertex>> outgoingFanout;

	private Map<Connection, Port> outgoingPort = new HashMap<Connection, Port>();

	private Map<Port, String> partitionMap = new HashMap<Port, String>();

	public NetworkPartitioner(File folder, Network network, Boolean manageFanout) {
		this.folder = folder;
		this.network = network;
		graph = network.getGraph();
		this.manageFanout = manageFanout;
	}

	/**
	 * 
	 * Returns the partName of the given instance.
	 * 
	 * @param instance
	 * 
	 * @throws OrccException
	 *             If the instance does not contain a partName attribute.
	 */
	private String getPartNameOf(Instance instance) throws OrccException {
		String partName = null;

		IAttribute attr = instance.getAttribute("partName");
		if (attr != null && attr.getType() == IAttribute.VALUE) {
			Expression expr = ((IValueAttribute) attr).getValue();
			if (expr.isStringExpr()) {
				partName = ((StringExpr) expr).getValue();
			} else {
				throw new OrccException(
						"partName attribute must be a String expression");
			}
		}
		return partName;
	}

	/**
	 * Copies connections between instances on the same partition.
	 * 
	 */

	private void addInternalConnection(Vertex src, Vertex tgt,
			Connection connection, Network network) {
		Connection newConnection = new Connection(connection.getSource(),
				connection.getTarget(), connection.getAttributes());
		network.getGraph().addEdge(src, tgt, newConnection);

	}

	/**
	 *
	 */

	private void createConnections(Set<Vertex> vertices, Network network)
			throws OrccException {

		outgoingFanout = new HashMap<Port, Map<String, Vertex>>();
		incomingFanout = new HashMap<Port, Vertex>();

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

	/**
	 * Creates an input port's vertex and a connection if the source of the
	 * connection is on a different partition.
	 * 
	 */

	private void createIncomingConnection(Vertex src, Vertex tgt,
			Connection connection, Network network) throws OrccException {

		Port srcPort = connection.getSource();

		Instance tgtInstance = tgt.getInstance();

		Vertex vertex = null;
		if (!incomingFanout.containsKey(srcPort) || !manageFanout) {
			Port port = new Port(srcPort);
			port.setName("input_" + nbInput);

			vertex = new Vertex("Input", port);
			network.getGraph().addVertex(vertex);

			network.getInputs().add(file.getAbsolutePath(), port.getLocation(),
					port.getName(), port);

			incomingFanout.put(srcPort, vertex);
			incomingPort.put(connection, port);
			partitionMap.put(port, getPartNameOf(tgtInstance));

			nbInput++;

		} else {
			vertex = incomingFanout.get(srcPort);
		}

		Connection incoming = new Connection(null, connection.getTarget(),
				connection.getAttributes());

		network.getGraph().addEdge(vertex, tgt, incoming);

	}

	/**
	 * Creates an output port's vertex and a connection if the source of the
	 * connection is on a different partition
	 * 
	 */

	private void createOutgoingConnection(Vertex src, Vertex tgt,
			Connection connection, Network network) throws OrccException {

		Instance inst = tgt.getInstance();
		Port srcPort = connection.getSource();
		Port tgtPort = connection.getTarget();

		Port port = new Port(tgtPort);
		port.setName("output_" + nbOutput);
		Vertex vertex = new Vertex("Output", port);
		network.getGraph().addVertex(vertex);
		network.getOutputs().add(file.getAbsolutePath(), port.getLocation(),
				port.getName(), port);

		nbOutput++;

		if (!outgoingFanout.containsKey(srcPort) || !manageFanout) {

			Map<String, Vertex> map = new HashMap<String, Vertex>();
			map.put(getPartNameOf(inst), vertex);
			outgoingFanout.put(srcPort, map);
			outgoingPort.put(connection, port);

			Connection outgoing = new Connection(connection.getSource(), null,
					connection.getAttributes());

			network.getGraph().addEdge(src, vertex, outgoing);

		} else {
			if (!outgoingFanout.get(srcPort).containsKey(getPartNameOf(inst))) {

				outgoingFanout.get(srcPort).put(getPartNameOf(inst), vertex);
				outgoingPort.put(connection, port);

				Connection outgoing = new Connection(connection.getSource(),
						null, connection.getAttributes());

				network.getGraph().addEdge(src, vertex, outgoing);

			} else {

				network.getGraph().removeVertex(vertex);
				network.getOutputs().remove(port);
				nbOutput--;
			}
		}

	}

	/**
	 *
	 */

	private Network createPartition(Map.Entry<String, Set<Vertex>> entry,
			Network network) throws OrccException {

		String name = entry.getKey();
		file = new File(folder.getAbsolutePath() + File.separator + name
				+ ".xdf");

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

		for (Vertex vertex : entry.getValue()) {
			Instance instance = vertex.getInstance();

			instances.put(instance.getId(), instance);
			graph.addVertex(new Vertex(instance));

		}

		// adds variables of the previous flatten network to the sub-network
		for (GlobalVariable var : network.getVariables()) {
			GlobalVariable newVar = new GlobalVariable(var.getLocation(), var
					.getType(), var.getName(), var.getExpression());

			variables.add(file.getAbsolutePath(), newVar.getLocation(), newVar
					.getName(), newVar);
		}

		createConnections(entry.getValue(), subNetwork);

		new XDFWriter(folder, subNetwork);
		return subNetwork;
	}

	public Map<String, Set<Vertex>> getPartitionSets() throws OrccException {
		Map<String, Set<Vertex>> partitionSets = new HashMap<String, Set<Vertex>>();

		for (Vertex vertex : graph.vertexSet()) {
			String partName = getPartNameOf(vertex.getInstance());

			if (partName == null) {
				throw new OrccException("partName attribute of instance "
						+ vertex.getInstance().getId() + " is missing");
			}

			if (!partitionSets.containsKey(partName)) {
				Set<Vertex> vertices = new HashSet<Vertex>();
				vertices.add(vertex);
				partitionSets.put(partName, vertices);
			} else {
				partitionSets.get(partName).add(vertex);
			}
		}

		return partitionSets;
	}

	public List<Network> getSubnetworks() throws OrccException {
		List<Network> subNetworks = new ArrayList<Network>();

		for (Map.Entry<String, Set<Vertex>> entry : getPartitionSets()
				.entrySet()) {
			Network subNetwork = createPartition(entry, network);
			subNetworks.add(subNetwork);
		}
		return subNetworks;
	}

	/**
	 *
	 */

	public void transform() throws OrccException {
		graph = network.getGraph();

		Map<String, Set<Vertex>> partitions = new HashMap<String, Set<Vertex>>();

		partitions = getPartitionSets();

		// Creates a copy of vertices of graph
		Set<Vertex> vertices = new HashSet<Vertex>(graph.vertexSet());
		Map<String, Vertex> topHierNetworkVertices = new HashMap<String, Vertex>();

		// Copies each partition in the top level graph
		for (Map.Entry<String, Set<Vertex>> entry : partitions.entrySet()) {

			Network subNetwork = createPartition(entry, network);

			Instance instance = new Instance(folder.getAbsolutePath(), entry
					.getKey(), subNetwork.getName(),
					new HashMap<String, Expression>(),
					new HashMap<String, IAttribute>());

			Vertex vertex = new Vertex(instance);
			graph.addVertex(vertex);

			topHierNetworkVertices.put(entry.getKey(), vertex);

		}

		// Creates connections of the top level graph
		Set<Connection> connections = new HashSet<Connection>(graph.edgeSet());
		for (Connection connection : connections) {
			Vertex src = graph.getEdgeSource(connection);
			Vertex tgt = graph.getEdgeTarget(connection);

			if (incomingPort.containsKey(connection)) {

				Port srcPort = outgoingPort.get(connection);
				Port tgtPort = incomingPort.get(connection);

				Connection conn = new Connection(srcPort, tgtPort, connection
						.getAttributes());

				Vertex srcVertex = topHierNetworkVertices.get(getPartNameOf(src
						.getInstance()));
				Vertex tgtVertex = topHierNetworkVertices.get(getPartNameOf(tgt
						.getInstance()));

				graph.addEdge(srcVertex, tgtVertex, conn);
			}
		}
		graph.removeAllVertices(vertices);
	}

}