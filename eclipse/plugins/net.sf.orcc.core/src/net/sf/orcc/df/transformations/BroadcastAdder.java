/*
 * Copyright (c) 2009, IETR/INSA of Rennes
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
package net.sf.orcc.df.transformations;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.orcc.df.Broadcast;
import net.sf.orcc.df.Connection;
import net.sf.orcc.df.DfFactory;
import net.sf.orcc.df.Instance;
import net.sf.orcc.df.Network;
import net.sf.orcc.df.Vertex;
import net.sf.orcc.ir.Port;

import org.jgrapht.DirectedGraph;

/**
 * Adds broadcast actors when needed.
 * 
 * @author Matthieu Wipliez
 * @author Herve Yviquel
 * 
 */
public class BroadcastAdder implements INetworkTransformation {

	protected DirectedGraph<Vertex, Connection> graph;

	protected Set<Connection> toBeRemoved;

	protected void createBroadcast(String id, Port port,
			List<Connection> outList) {
		// add broadcast vertex
		Broadcast bcast = DfFactory.eINSTANCE.createBroadcast(outList.size(),
				port.getType());
		String name = id + "_" + port.getName();
		Instance newInst = DfFactory.eINSTANCE.createInstance(name, bcast);
		Vertex vertexBCast = new Vertex(newInst);
		graph.addVertex(vertexBCast);

		// add connections
		createIncomingConnection(outList.get(0),
				graph.getEdgeSource(outList.get(0)), vertexBCast);
		createOutgoingConnections(vertexBCast, outList);
	}

	/**
	 * Creates a connection between the source vertex and the broadcast.
	 * 
	 * @param connection
	 *            the connection outgoing of vertex
	 * @param vertex
	 *            the vertex
	 * @param vertexBCast
	 *            the vertex that contains the broadcast
	 */
	protected void createIncomingConnection(Connection connection,
			Vertex vertex, Vertex vertexBCast) {
		Broadcast bcast = vertexBCast.getInstance().getBroadcast();
		Port bcastInput = bcast.getInput();

		// creates a connection between the vertex and the broadcast
		Port srcPort = connection.getSource();
		Connection incoming = DfFactory.eINSTANCE.createConnection(srcPort,
				bcastInput, connection.getAttributes());
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
		Broadcast bcast = vertexBCast.getInstance().getBroadcast();
		int i = 0;
		for (Connection connection : outList) {
			// new connection
			Vertex target = graph.getEdgeTarget(connection);
			Port outputPort = bcast.getOutput("output_" + i);
			i++;

			Connection connBcastTarget = DfFactory.eINSTANCE.createConnection(
					outputPort, connection.getTarget(),
					connection.getAttributes());
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
	 */
	protected void examineConnections(Vertex vertex,
			Set<Connection> connections, Map<Port, List<Connection>> outMap,
			Network network) {
		if (vertex.isInstance()) {
			Instance instance = vertex.getInstance();
			for (Connection connection : connections) {
				Port srcPort = connection.getSource();
				if (srcPort != null) {
					List<Connection> outList = outMap.get(srcPort);
					int numOutput = outList.size();
					if (numOutput > 1) {
						createBroadcast(instance.getId(), srcPort, outList);
					}
				}
			}
		} else {
			if (connections.size() > 1) {
				Port port = vertex.getPort();
				createBroadcast(network.getName(), port,
						new ArrayList<Connection>(connections));
			}
		}
	}

	protected void examineVertex(Vertex vertex, Network network) {
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

		examineConnections(vertex, connections, outMap, network);
	}

	@Override
	public void transform(Network network) {
		graph = network.getGraph();
		toBeRemoved = new HashSet<Connection>();

		// make a copy of the existing vertex set because the set returned is
		// modified when broadcasts are added
		Set<Vertex> vertexSet = new HashSet<Vertex>(graph.vertexSet());

		for (Vertex vertex : vertexSet) {
			if (vertex.isInstance()) {
				Instance instance = vertex.getInstance();
				if (instance.isNetwork()) {
					new BroadcastAdder().transform(instance.getNetwork());
				}
			}
			examineVertex(vertex, network);
		}

		// removes old connections
		graph.removeAllEdges(toBeRemoved);
	}
}
