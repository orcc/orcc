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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.orcc.OrccException;
import net.sf.orcc.ir.Port;
import net.sf.orcc.network.Broadcast;
import net.sf.orcc.network.Connection;
import net.sf.orcc.network.Instance;
import net.sf.orcc.network.Network;
import net.sf.orcc.network.Vertex;
import net.sf.orcc.network.Wrapper;
import net.sf.orcc.network.attributes.IAttribute;
import net.sf.orcc.util.OrderedMap;

import org.jgrapht.DirectedGraph;

/**
 * This class defines a transformation that replace input and output ports of
 * the network with a (unique) wrapper instance and appropriate connections.
 * This transformation is only used in the context of the codesign.
 * 
 * @author Ghislain Roquier
 * 
 */

public class WrapperAdder {

	private DirectedGraph<Vertex, Connection> graph;

	private Set<Connection> toBeRemoved = new HashSet<Connection>();

	private void createIncomingConnection(Connection connection, Vertex vertex,
			Vertex vertexBCast) {
		// creates new input port of broadcast
		Port bcastInput = new Port(connection.getTarget());
		bcastInput.setName("input");

		// creates a connection between the vertex and the broadcast
		Map<String, IAttribute> attributes = connection.getAttributes();
		Port srcPort = connection.getSource();
		Connection incoming = new Connection(srcPort, bcastInput, attributes);
		graph.addEdge(vertex, vertexBCast, incoming);
	}

	/**
	 * Creates a connection between the broadcast and the target for each
	 * outgoing connection of vertex.
	 * 
	 * @param vertexBCast
	 * @param outList
	 */
	private void createOutgoingConnections(Vertex vertexBCast,
			List<Connection> outList) {
		int i = 0;
		for (Connection connection : outList) {
			// new connection
			Vertex target = graph.getEdgeTarget(connection);
			Port srcPort = connection.getSource();
			Port outputPort = new Port(srcPort.getLocation(),
					srcPort.getType(), "output_" + i);
			i++;

			Map<String, IAttribute> attributes = connection.getAttributes();
			Connection connBcastTarget = new Connection(outputPort,
					connection.getTarget(), attributes);
			graph.addEdge(vertexBCast, target, connBcastTarget);

			// setting source to null so we don't examine it again
			connection.setSource(null);

			// add this connection to the set of connections that are to be
			// removed
			toBeRemoved.add(connection);
		}
	}

	/**
	 * Examine the outgoing connections of vertex.
	 * 
	 * @param vertex
	 *            a vertex
	 * @param connections
	 *            the outgoing connections of vertex
	 * @param outMap
	 *            a map from each output port P(i) of vertex to a list of
	 *            outgoing connections from P(i)
	 * @throws OrccException
	 */
	private void examineConnections(Vertex vertex, Set<Connection> connections,
			Map<Port, List<Connection>> outMap) throws OrccException {
		Instance instance = vertex.getInstance();
		for (Connection connection : connections) {
			Port srcPort = connection.getSource();
			if (srcPort != null) {
				List<Connection> outList = outMap.get(srcPort);
				int numOutput = outList.size();
				if (numOutput > 1) {
					// add broadcast vertex
					Broadcast bcast = new Broadcast(numOutput,
							srcPort.getType());
					String name = "broadcast_" + instance.getId() + "_"
							+ srcPort.getName();
					Instance newInst = new Instance(name, bcast);
					Vertex vertexBCast = new Vertex(newInst);
					graph.addVertex(vertexBCast);

					// add connections
					createIncomingConnection(connection, vertex, vertexBCast);
					createOutgoingConnections(vertexBCast, outList);
				}
			}
		}
	}

	private void examineVertex(Vertex vertex) throws OrccException {
		// make a copy of the existing outgoing connections of vertex because
		// the set returned is modified when new edges are added
		Set<Connection> connections = new HashSet<Connection>(
				graph.outgoingEdgesOf(vertex));

		// for each connection, add it to a port => connection map
		// port is a port of vertex
		Map<Port, List<Connection>> outMap = new HashMap<Port, List<Connection>>();
		for (Connection connection : connections) {
			Port src = connection.getSource();
			List<Connection> outList = outMap.get(src);
			if (outList == null) {
				outList = new ArrayList<Connection>();
				outMap.put(src, outList);
			}
			outList.add(connection);
		}

		examineConnections(vertex, connections, outMap);
	}

	public void transform(Network network) throws OrccException {

		graph = network.getGraph();

		OrderedMap<Port> inputs = network.getInputs();
		OrderedMap<Port> outputs = network.getOutputs();

		if (inputs.size() > 0 || outputs.size() > 0) {
			Wrapper wrapper = new Wrapper(inputs.size(), outputs.size());
			Vertex vWrap = new Vertex(new Instance(network.getName(), wrapper));

			graph.addVertex(vWrap);

			Set<Vertex> vertexToRemove = new HashSet<Vertex>();

			for (Vertex vertex : graph.vertexSet()) {
				if (vertex.isPort()) {
					Port port = vertex.getPort();
					if (outputs.contains(port)) {
						Set<Connection> conns = graph.incomingEdgesOf(vertex);

						// FIX: there should be only one connection since fan-in
						// is not allowed
						for (Connection connection : conns) {

							Port srcPort = connection.getSource();
							// FIX: set the type of the source port to the one
							// of the target
							srcPort.setType(port.getType());
							Port tgtPort = new Port(port);

							Connection incoming = new Connection(srcPort,
									tgtPort, connection.getAttributes());
							Vertex vSrc = graph.getEdgeSource(connection);
							graph.addEdge(vSrc, vWrap, incoming);

							vertexToRemove.add(vertex);
							outputs.remove(port);
						}
					} else {

						Iterator<Connection> it = graph.outgoingEdgesOf(vertex)
								.iterator();

						Connection connection = it.next();
						Port srcPort = new Port(port);
						Port tgtPort = connection.getTarget();
						// FIX: set the type of the target port to the one of
						// the source
						tgtPort.setType(port.getType());
						Vertex vTgt = graph.getEdgeTarget(connection);
						Connection outgoing = new Connection(srcPort, tgtPort,
								connection.getAttributes());
						graph.addEdge(vWrap, vTgt, outgoing);
						vertexToRemove.add(vertex);
						inputs.remove(port);

						while (it.hasNext()) {
							connection = it.next();
							tgtPort = connection.getTarget();
							// FIX: set the type of the target port to the one
							// of the source
							tgtPort.setType(port.getType());
							vTgt = graph.getEdgeTarget(connection);
							Connection newOutgoing = new Connection(srcPort,
									tgtPort, connection.getAttributes());
							graph.addEdge(vWrap, vTgt, newOutgoing);
						}
					}
				} else {
				}
			}
			examineVertex(vWrap);
			graph.removeAllVertices(vertexToRemove);
			graph.removeAllEdges(toBeRemoved);
		}

	}
}
