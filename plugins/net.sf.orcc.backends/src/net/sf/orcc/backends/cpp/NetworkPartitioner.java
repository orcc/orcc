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
package net.sf.orcc.backends.cpp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.orcc.OrccException;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.GlobalVariable;
import net.sf.orcc.ir.Location;
import net.sf.orcc.ir.Port;
import net.sf.orcc.ir.expr.StringExpr;
import net.sf.orcc.network.Connection;
import net.sf.orcc.network.Instance;
import net.sf.orcc.network.Network;
import net.sf.orcc.network.Vertex;
import net.sf.orcc.network.attributes.IAttribute;
import net.sf.orcc.network.attributes.IValueAttribute;
import net.sf.orcc.network.transforms.INetworkTransformation;

import org.jgrapht.DirectedGraph;

/**
 * This class defines a transformation that partition a given network into a
 * hierarchical network according to attributes of instances.
 * 
 * @author Ghislain Roquier
 * 
 */
public class NetworkPartitioner implements INetworkTransformation {

	private DirectedGraph<Vertex, Connection> graph;

	private Map<Connection, Port> incomingPort = new HashMap<Connection, Port>();

	private int nbInput;

	private int nbOutput;

	private Map<Instance, List<String>> partNames;

	private Map<Connection, Port> outgoingPort = new HashMap<Connection, Port>();

	private Map<Port, String> partitionMap = new HashMap<Port, String>();

	private List<Network> networks = new ArrayList<Network>();

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

	/**
	 * Creates an input port's vertex and a connection if the source of the
	 * connection is on a different partition.
	 * 
	 */
	private void createIncomingConnection(Vertex src, Vertex tgt,
			Connection connection, Network network) throws OrccException {
		Port srcPort = connection.getSource();
		Instance tgtInstance = tgt.getInstance();

		Port port = new Port(srcPort);
		port.setName("input_" + nbInput++);
		Vertex vertex = new Vertex("Input", port);
		network.getGraph().addVertex(vertex);
		network.getInputs().put(port.getName(), port);

		incomingPort.put(connection, port);
		partitionMap.put(port, getPartNameAttribute(tgtInstance).get(0));

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
		Port tgtPort = connection.getTarget();

		Port port = new Port(tgtPort);
		port.setName("output_" + nbOutput++);
		Vertex vertex = new Vertex("Output", port);
		network.getGraph().addVertex(vertex);
		network.getOutputs().put(port.getName(), port);

		Map<String, Vertex> map = new HashMap<String, Vertex>();
		map.put(getPartNameAttribute(inst).get(0), vertex);

		outgoingPort.put(connection, port);

		Connection outgoing = new Connection(connection.getSource(), null,
				connection.getAttributes());

		network.getGraph().addEdge(src, vertex, outgoing);
	}

	/**
	 * 
	 * @param entry
	 * @param network
	 * @return
	 * @throws OrccException
	 */
	private Network createPartition(Map.Entry<String, Set<Vertex>> entry,
			Network network) throws OrccException {
		String name = entry.getKey();

		Map<String, Instance> instances = new HashMap<String, Instance>();

		Network subNetwork = new Network();
		subNetwork.setName(name);

		for (Vertex vertex : entry.getValue()) {
			Instance instance = vertex.getInstance();
			instances.put(instance.getId(), instance);
			network.getGraph().addVertex(new Vertex(instance));
		}

		// adds variables of the previous flatten network to the sub-network
		for (GlobalVariable var : network.getVariables()) {
			GlobalVariable newVar = new GlobalVariable(new Location(),
					var.getType(), var.getName(), var.getExpression());

			subNetwork.getVariables().put(newVar.getName(), newVar);
		}

		createConnections(entry.getValue(), subNetwork);

		return subNetwork;
	}

	public Map<String, Set<Vertex>> getPartitionSets() throws OrccException {
		Map<String, Set<Vertex>> partitionSets = new HashMap<String, Set<Vertex>>();

		for (Vertex vertex : graph.vertexSet()) {
			String partName = partNames.get(vertex.getInstance()).get(0);

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

	/**
	 * 
	 * Returns the partName of the given instance.
	 * 
	 * @param instance
	 * 
	 * @throws OrccException
	 *             If the instance does not contain a partName attribute.
	 */
	public List<String> getPartNameAttribute(Instance instance)
			throws OrccException {
		List<String> partNames = null;

		IAttribute attr = instance.getAttribute("partName");
		if (attr != null && attr.getType() == IAttribute.VALUE) {
			Expression expr = ((IValueAttribute) attr).getValue();
			if (expr.isStringExpr()) {
				partNames = new LinkedList<String>(
						Arrays.asList(((StringExpr) expr).getValue().split("/")));
			} else {
				throw new OrccException(
						"partName attribute must be a String expression");
			}
		}
		return partNames;
	}

	public List<Network> getSubnetworks(Network network) throws OrccException {
		List<Network> subNetworks = new ArrayList<Network>();

		for (Map.Entry<String, Set<Vertex>> entry : getPartitionSets()
				.entrySet()) {
			Network subNetwork = createPartition(entry, network);
			subNetworks.add(subNetwork);
		}
		return subNetworks;
	}

	@Override
	public void transform(Network network) throws OrccException {
		if (partNames == null) {
			initPartNames(network);
		}

		graph = network.getGraph();
		Map<String, Set<Vertex>> partitions = getPartitionSets();

		// Creates a copy of vertices of graph
		Set<Vertex> vertices = new HashSet<Vertex>(graph.vertexSet());
		Map<String, Vertex> topHierNetworkVertices = new HashMap<String, Vertex>();

		// Copies each partition in the top level graph
		for (Map.Entry<String, Set<Vertex>> entry : partitions.entrySet()) {

			Network subNetwork = createPartition(entry, network);

			networks.add(subNetwork);

			Vertex vertex = new Vertex(new Instance(subNetwork.getName(),
					subNetwork));

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

				Connection conn = new Connection(srcPort, tgtPort,
						connection.getAttributes());

				Vertex srcVertex = topHierNetworkVertices.get(partNames.get(
						src.getInstance()).get(0));
				Vertex tgtVertex = topHierNetworkVertices.get(partNames.get(
						tgt.getInstance()).get(0));

				graph.addEdge(srcVertex, tgtVertex, conn);
			}
		}

		graph.removeAllVertices(vertices);
	}

	private void updatePartNames() {
		for (List<String> stringList : partNames.values()) {
			stringList.remove(0);
		}
	}

	private void initPartNames(Network network) throws OrccException {
		partNames = new HashMap<Instance, List<String>>();
		for (Instance instance : network.getInstances()) {
			partNames.put(instance, getPartNameAttribute(instance));
		}
	}

	public List<Network> getNetworks() {
		return networks;
	}

	public boolean hasThreadParallelism() {
		boolean hasThreadParallelism = false;

		for (List<String> stringList : partNames.values()) {
			hasThreadParallelism |= stringList.size() > 1;
		}

		if (hasThreadParallelism) {
			updatePartNames();
		}

		return hasThreadParallelism;
	}

}