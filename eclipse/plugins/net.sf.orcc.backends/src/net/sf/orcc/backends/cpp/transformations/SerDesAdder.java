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
package net.sf.orcc.backends.cpp.transformations;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.orcc.OrccException;
import net.sf.orcc.df.Attribute;
import net.sf.orcc.df.Broadcast;
import net.sf.orcc.df.Connection;
import net.sf.orcc.df.DfFactory;
import net.sf.orcc.df.Instance;
import net.sf.orcc.df.Network;
import net.sf.orcc.df.Port;
import net.sf.orcc.df.SerDes;
import net.sf.orcc.df.Vertex;
import net.sf.orcc.df.transformations.NetworkVisitor;
import net.sf.orcc.ir.ExprString;

import org.jgrapht.DirectedGraph;

/**
 * This class defines a transformation that replace input and output ports of
 * the network with a (unique) wrapper instance and appropriate connections.
 * This transformation is only used in the context of the codesign.
 * 
 * @author Ghislain Roquier
 * 
 */
public class SerDesAdder implements NetworkVisitor<Void> {

	private DirectedGraph<Vertex, Connection> graph;

	private Set<Connection> toBeRemoved = new HashSet<Connection>();

	private Map<String, Vertex> serdesMap = new HashMap<String, Vertex>();

	private void createIncomingConnection(Connection connection, Vertex vertex,
			Vertex vertexBCast) {
		// creates new input port of broadcast
		Port bcastInput = DfFactory.eINSTANCE.createPort(connection
				.getTargetPort());
		bcastInput.setName("input");

		// creates a connection between the vertex and the broadcast
		Port srcPort = connection.getSourcePort();
		Connection incoming = DfFactory.eINSTANCE.createConnection(vertex,
				srcPort, vertexBCast, bcastInput, connection.getAttributes());
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
			Port srcPort = connection.getSourcePort();
			Port outputPort = DfFactory.eINSTANCE.createPort(srcPort.getType(),
					"output_" + i);
			i++;

			Connection connBcastTarget = DfFactory.eINSTANCE.createConnection(
					vertexBCast, outputPort, target,
					connection.getTargetPort(), connection.getAttributes());
			graph.addEdge(vertexBCast, target, connBcastTarget);

			// setting source to null so we don't examine it again
			connection.setSourcePort(null);

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
			Map<Port, List<Connection>> outMap) {
		Instance instance = (Instance) vertex;
		for (Connection connection : connections) {
			Port srcPort = connection.getSourcePort();
			if (srcPort != null) {
				List<Connection> outList = outMap.get(srcPort);
				int numOutput = outList.size();
				if (numOutput > 1) {
					// add broadcast vertex
					Broadcast bcast = DfFactory.eINSTANCE.createBroadcast(
							numOutput, srcPort.getType());
					String name = "broadcast_" + instance.getId() + "_"
							+ srcPort.getName();
					Instance newInst = DfFactory.eINSTANCE.createInstance(name,
							bcast);
					graph.addVertex(newInst);

					// add connections
					createIncomingConnection(connection, vertex, newInst);
					createOutgoingConnections(newInst, outList);
				}
			}
		}
	}

	private void examineVertex(Vertex vertex) {
		// make a copy of the existing outgoing connections of vertex because
		// the set returned is modified when new edges are added
		Set<Connection> connections = new HashSet<Connection>(
				graph.outgoingEdgesOf(vertex));

		Map<Port, List<Connection>> outMap = new HashMap<Port, List<Connection>>();
		for (Connection connection : connections) {
			Port src = connection.getSourcePort();
			List<Connection> outList = outMap.get(src);
			if (outList == null) {
				outList = new ArrayList<Connection>();
				outMap.put(src, outList);
			}
			outList.add(connection);
		}

		examineConnections(vertex, connections, outMap);
	}

	public Void doSwitch(Network network) {
		// graph = network.getGraph();

		List<Port> inputs = network.getInputs();
		List<Port> outputs = network.getOutputs();

		if (inputs.size() > 0 || outputs.size() > 0) {
			for (Connection conn : graph.edgeSet()) {
				if (graph.getEdgeSource(conn).isPort()) {
					Attribute attr = conn.getAttribute("busRef");
					String attrName = ((ExprString) attr.getValue()).getValue();

					if (serdesMap.containsKey(attrName)) {
						Vertex v = serdesMap.get(attrName);
						SerDes serdes = ((Instance) v).getWrapper();
						int out = serdes.getNumOutputs();
						serdes.setNumOutputs(out++);
					} else {
						// Instance inst = DfFactory.eINSTANCE.createInstance(
						// attrName, new SerDes(0, 1));
						// serdesMap.put(attrName, inst);
						// graph.addVertex(inst);
					}
				}

				if (graph.getEdgeTarget(conn).isPort()) {
					Attribute attr = conn.getAttribute("busRef");
					String attrName = ((ExprString) attr.getValue()).getValue();

					if (serdesMap.containsKey(attrName)) {
						Vertex v = serdesMap.get(attrName);
						SerDes serdes = ((Instance) v).getWrapper();
						int in = serdes.getNumInputs();
						serdes.setNumOutputs(in++);
					} else {
						// Instance inst = DfFactory.eINSTANCE.createInstance(
						// attrName, new SerDes(1, 0));
						// serdesMap.put(attrName, inst);
						// graph.addVertex(inst);
					}
				}
			}

			Set<Vertex> vertexToRemove = new HashSet<Vertex>();

			for (Vertex vertex : graph.vertexSet()) {
				if (vertex.isPort()) {
					Port port = (Port) vertex;

					if (outputs.contains(port)) {
						Set<Connection> conns = graph.incomingEdgesOf(vertex);

						// FIXME: there should be only one connection since
						// fan-in is not allowed
						for (Connection connection : conns) {

							Port srcPort = connection.getSourcePort();
							srcPort.setType(port.getType());
							Port tgtPort = DfFactory.eINSTANCE.createPort(port);

							Vertex vSrc = graph.getEdgeSource(connection);

							Attribute attr = connection.getAttribute("busRef");
							String attrName = ((ExprString) attr.getValue())
									.getValue();

							Connection incoming = DfFactory.eINSTANCE
									.createConnection(vSrc, srcPort,
											serdesMap.get(attrName), tgtPort,
											connection.getAttributes());
							graph.addEdge(vSrc, serdesMap.get(attrName),
									incoming);

							vertexToRemove.add(vertex);
							outputs.remove(port);
						}
					} else {
						Iterator<Connection> it = graph.outgoingEdgesOf(vertex)
								.iterator();

						Connection connection = it.next();
						Port srcPort = DfFactory.eINSTANCE.createPort(port);
						Port tgtPort = connection.getTargetPort();
						tgtPort.setType(port.getType());
						Vertex vTgt = graph.getEdgeTarget(connection);

						Attribute attr = connection.getAttribute("busRef");
						String attrName = ((ExprString) attr.getValue())
								.getValue();
						Connection outgoing = DfFactory.eINSTANCE
								.createConnection(serdesMap.get(attrName),
										srcPort, vTgt, tgtPort,
										connection.getAttributes());

						graph.addEdge(serdesMap.get(attrName), vTgt, outgoing);
						vertexToRemove.add(vertex);
						inputs.remove(port);

						while (it.hasNext()) {
							connection = it.next();
							tgtPort = connection.getTargetPort();
							tgtPort.setType(port.getType());
							vTgt = graph.getEdgeTarget(connection);
							Connection newOutgoing = DfFactory.eINSTANCE
									.createConnection(serdesMap.get(attrName),
											srcPort, vTgt, tgtPort,
											connection.getAttributes());

							attr = connection.getAttribute("busRef");
							attrName = ((ExprString) attr.getValue())
									.getValue();

							graph.addEdge(serdesMap.get(attrName), vTgt,
									newOutgoing);
						}
					}
				}
			}

			for (Vertex serdes : serdesMap.values()) {
				examineVertex(serdes);
			}
			graph.removeAllVertices(vertexToRemove);
			graph.removeAllEdges(toBeRemoved);
		}

		return null;
	}

}
