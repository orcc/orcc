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
package net.sf.orcc.network.transforms;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import net.sf.orcc.OrccException;
import net.sf.orcc.network.Connection;
import net.sf.orcc.network.Instance;
import net.sf.orcc.network.Network;
import net.sf.orcc.network.Vertex;
import net.sf.orcc.network.attributes.IAttribute;

import org.jgrapht.DirectedGraph;

/**
 * This class defines a transformation that flattens a given network in-place.
 * 
 * @author Matthieu Wipliez
 * @author Ghislain Roquier
 * 
 */
public class NetworkFlattener implements INetworkTransformation {

	/**
	 * Copies all instances and edges between them of subGraph in graph
	 * 
	 * @throws OrccException
	 */
	private void copySubGraph(Map<String, IAttribute> attrs, Network network,
			Network subNetwork) {
		DirectedGraph<Vertex, Connection> graph = network.getGraph();
		DirectedGraph<Vertex, Connection> subGraph = subNetwork.getGraph();

		List<Vertex> vertexSet = new ArrayList<Vertex>(subGraph.vertexSet());
		List<Connection> edgeSet = new ArrayList<Connection>(subGraph.edgeSet());

		for (Vertex vertex : vertexSet) {
			if (vertex.isInstance()) {
				Instance instance = vertex.getInstance();
				instance.setId(subNetwork.getName() + "_" + instance.getId());

				Map<String, IAttribute> vertexAttrs = vertex.getInstance()
						.getAttributes();
				for (Entry<String, IAttribute> attr : attrs.entrySet()) {
					if (!vertexAttrs.containsKey(attr.getKey())) {
						vertexAttrs.put(attr.getKey(), attr.getValue());
					}
				}

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
	 * Links each predecessor of vertex to the successors of the input port in
	 * subGraph
	 * 
	 * @param vertex
	 *            the parent graph
	 * @param graph
	 *            the parent graph
	 * @param subGraph
	 *            the child graph
	 * @throws OrccException
	 */
	private void linkIncomingConnections(Vertex vertex,
			DirectedGraph<Vertex, Connection> graph,
			DirectedGraph<Vertex, Connection> subGraph) {
		List<Connection> incomingEdgeSet = new ArrayList<Connection>(graph
				.incomingEdgesOf(vertex));
		for (Connection edge : incomingEdgeSet) {
			Set<Connection> outgoingEdgeSet = new HashSet<Connection>();
			for (Vertex v : subGraph.vertexSet()) {
				if (v.isPort()) {
					if (edge.getTarget().getName()
							.equals(v.getPort().getName())) {
						outgoingEdgeSet = subGraph.outgoingEdgesOf(v);
					}
				}
			}

			for (Connection newEdge : outgoingEdgeSet) {
				Connection incoming = new Connection(edge.getSource(), newEdge
						.getTarget(), edge.getAttributes());
				graph.addEdge(graph.getEdgeSource(edge), subGraph
						.getEdgeTarget(newEdge), incoming);
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
	 * @param subGraph
	 *            the child graph
	 * @throws OrccException
	 */
	private void linkOutgoingConnections(Vertex vertex,
			DirectedGraph<Vertex, Connection> graph,
			DirectedGraph<Vertex, Connection> subGraph) {
		List<Connection> outgoingEdgeSet = new ArrayList<Connection>(graph
				.outgoingEdgesOf(vertex));
		for (Connection edge : outgoingEdgeSet) {
			Set<Connection> incomingEdgeSet = new HashSet<Connection>();
			for (Vertex v : subGraph.vertexSet()) {
				if (v.isPort()) {
					if (edge.getSource().getName()
							.equals(v.getPort().getName())) {
						incomingEdgeSet = subGraph.incomingEdgesOf(v);
					}
				}
			}

			for (Connection newEdge : incomingEdgeSet) {
				Connection incoming = new Connection(newEdge.getSource(), edge
						.getTarget(), edge.getAttributes());
				graph.addEdge(subGraph.getEdgeSource(newEdge), graph
						.getEdgeTarget(edge), incoming);
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
				if (instance.isNetwork()) {
					Network subNetwork = instance.getNetwork();

					// flatten this sub-network
					subNetwork.flatten();

					// copy vertices and edges
					copySubGraph(instance.getAttributes(), network, subNetwork);
					linkOutgoingConnections(vertex, network.getGraph(),
							subNetwork.getGraph());
					linkIncomingConnections(vertex, network.getGraph(),
							subNetwork.getGraph());

					// remove vertex from this graph
					network.getGraph().removeVertex(vertex);
				}
			}
		}
	}

}
