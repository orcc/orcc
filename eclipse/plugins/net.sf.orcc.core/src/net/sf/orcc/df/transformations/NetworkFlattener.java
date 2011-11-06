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
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.orcc.OrccException;
import net.sf.orcc.df.Attribute;
import net.sf.orcc.df.Connection;
import net.sf.orcc.df.DfFactory;
import net.sf.orcc.df.Instance;
import net.sf.orcc.df.Network;
import net.sf.orcc.df.Vertex;
import net.sf.orcc.ir.Port;

import org.jgrapht.DirectedGraph;

/**
 * This class defines a transformation that flattens a given network in-place.
 * 
 * @author Matthieu Wipliez
 * @author Ghislain Roquier
 * 
 */
public class NetworkFlattener implements INetworkTransformation {

	private Map<String, Integer> identifiers;

	public NetworkFlattener() {
		identifiers = new HashMap<String, Integer>();
	}

	/**
	 * Copies all instances and edges between them of subGraph in graph
	 * 
	 * @throws OrccException
	 */
	private void copySubGraph(List<Attribute> attrs, Network network,
			Instance instance) {
		DirectedGraph<Vertex, Connection> graph = network.getGraph();

		Network subNetwork = instance.getNetwork();
		DirectedGraph<Vertex, Connection> subGraph = subNetwork.getGraph();

		List<Vertex> vertexSet = new ArrayList<Vertex>(subGraph.vertexSet());
		List<Connection> edgeSet = new ArrayList<Connection>(subGraph.edgeSet());

		for (Vertex vertex : vertexSet) {
			if (vertex.isInstance()) {
				Instance subInstance = vertex.getInstance();

				// get a unique identifier
				String id = getUniqueIdentifier(instance.getId(), subInstance);
				subInstance.setId(id);

				// copy attributes
				List<Attribute> vertexAttrs = subInstance.getAttributes();
				vertexAttrs.addAll(attrs);

				graph.addVertex(vertex);
			}
		}

		for (Connection edge : edgeSet) {
			Vertex srcVertex = subGraph.getEdgeSource(edge);
			Vertex tgtVertex = subGraph.getEdgeTarget(edge);

			if (srcVertex.isInstance() && tgtVertex.isInstance()) {
				graph.addEdge(srcVertex, tgtVertex, edge);
			}
		}
	}

	/**
	 * Returns the port in the given sub-network that matches the given port
	 * with the given direction. The port exists as-is in the sub-network if
	 * network has been instantiated, otherwise it uses the input (or output,
	 * depending on the direction argument) port of the sub-network.
	 * 
	 * @param subNetwork
	 *            the sub-network
	 * @param direction
	 *            direction of port
	 * @param port
	 *            a port
	 * @return a vertex that matches the port in the given sub-network
	 */
	private Vertex getPort(Network subNetwork, String direction, Port port) {
		Vertex v = new Vertex(direction, port);
		if (subNetwork.getGraph().containsVertex(v)) {
			return v;
		} else {
			// this is the case when instantiation has not been done
			// so we just pick up the port
			if (direction.equals("Input")) {
				port = subNetwork.getInput(port.getName());
			} else {
				port = subNetwork.getOutput(port.getName());
			}

			return new Vertex(direction, port);
		}
	}

	/**
	 * Returns a unique id in the given network.
	 */
	private String getUniqueIdentifier(String parentId, Instance instance) {
		String id = parentId + "_" + instance.getId();
		if (identifiers.containsKey(id)) {
			// identifier exists in the graph => generates a new one
			int num = identifiers.get(id);
			String newId = String.format(id + "_%02d", num);
			while (identifiers.containsKey(newId)) {
				num++;
				newId = String.format(id + "_%02d", num);
			}
			identifiers.put(id, num + 1);
			return newId;
		} else {
			// identifier does not exist in the graph: returns the original id
			identifiers.put(id, 0);
			return id;
		}
	}

	/**
	 * Links each predecessor of vertex to the successors of the input port in
	 * subGraph
	 * 
	 * @param vertex
	 *            the parent graph
	 * @param graph
	 *            the parent graph
	 * @param subNetwork
	 *            the child network
	 * @throws OrccException
	 */
	private void linkIncomingConnections(Vertex vertex,
			DirectedGraph<Vertex, Connection> graph, Network subNetwork) {
		DirectedGraph<Vertex, Connection> subGraph = subNetwork.getGraph();
		List<Connection> incomingEdgeSet = new ArrayList<Connection>(
				graph.incomingEdgesOf(vertex));
		for (Connection edge : incomingEdgeSet) {
			Vertex v = getPort(subNetwork, "Input", edge.getTarget());
			Set<Connection> outgoingEdgeSet = subGraph.outgoingEdgesOf(v);

			for (Connection newEdge : outgoingEdgeSet) {
				Connection incoming = DfFactory.eINSTANCE.createConnection(
						edge.getSource(), newEdge.getTarget(),
						edge.getAttributes());
				graph.addEdge(graph.getEdgeSource(edge),
						subGraph.getEdgeTarget(newEdge), incoming);
			}
		}
	}

	/**
	 * Links each successor of vertex to the predecessors of the output port in
	 * subGraph
	 * 
	 * @param vertex
	 *            the current vertex
	 * @param graph
	 *            the parent graph
	 * @param subNetwork
	 *            the child network
	 * @throws OrccException
	 */
	private void linkOutgoingConnections(Vertex vertex,
			DirectedGraph<Vertex, Connection> graph, Network subNetwork) {
		DirectedGraph<Vertex, Connection> subGraph = subNetwork.getGraph();
		List<Connection> outgoingEdgeSet = new ArrayList<Connection>(
				graph.outgoingEdgesOf(vertex));
		for (Connection edge : outgoingEdgeSet) {
			Vertex v = getPort(subNetwork, "Output", edge.getSource());
			Set<Connection> incomingEdgeSet = subGraph.incomingEdgesOf(v);

			for (Connection newEdge : incomingEdgeSet) {
				Connection incoming = DfFactory.eINSTANCE.createConnection(
						newEdge.getSource(), edge.getTarget(),
						edge.getAttributes());
				graph.addEdge(subGraph.getEdgeSource(newEdge),
						graph.getEdgeTarget(edge), incoming);
			}
		}
	}

	@Override
	public void transform(Network network) {
		List<Vertex> vertexSet = new ArrayList<Vertex>(network.getGraph()
				.vertexSet());
		for (Vertex vertex : vertexSet) {
			if (vertex.isInstance()) {
				Instance instance = vertex.getInstance();
				identifiers.put(instance.getId(), 0);
			}
		}

		for (Vertex vertex : vertexSet) {
			if (vertex.isInstance()) {
				Instance instance = vertex.getInstance();
				if (instance.isNetwork()) {
					Network subNetwork = instance.getNetwork();

					// flatten this sub-network
					subNetwork.flatten();

					// copy vertices and edges
					copySubGraph(instance.getAttributes(), network, instance);
					linkOutgoingConnections(vertex, network.getGraph(),
							subNetwork);
					linkIncomingConnections(vertex, network.getGraph(),
							subNetwork);

					// remove vertex from this graph
					network.getGraph().removeVertex(vertex);
				}
			}
		}

		for (Instance instance : network.getInstances()) {
			instance.getHierarchicalClass().add(0, network.getName());
		}
	}

}
