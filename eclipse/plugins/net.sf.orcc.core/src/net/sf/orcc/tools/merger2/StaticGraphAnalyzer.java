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
package net.sf.orcc.tools.merger2;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import net.sf.orcc.ir.Actor;
import net.sf.orcc.ir.Port;
import net.sf.orcc.moc.MoC;
import net.sf.orcc.network.Connection;
import net.sf.orcc.network.Instance;
import net.sf.orcc.network.Network;
import net.sf.orcc.network.Vertex;
import net.sf.orcc.util.MultiMap;
import net.sf.orcc.util.OrderedMap;

import org.jgrapht.DirectedGraph;
import org.jgrapht.alg.ConnectivityInspector;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;

/**
 * This class defines a static representation graph to ease the merging of a
 * network.
 * 
 * 
 * @author Jerome Gorin
 * 
 */
public class StaticGraphAnalyzer {
	/**
	 * This class represents a static edge with a production and a consumption.
	 * 
	 * 
	 * @author Jerome Gorin
	 * 
	 */
	private class StaticEdge extends DefaultEdge {
		private static final long serialVersionUID = -5886264026878538695L;

		int sourceRate;
		int targetRate;

		public StaticEdge() {
			super();
			sourceRate = 0;
			targetRate = 0;
		}

		public int getSourceRate() {
			return sourceRate;
		}

		public int getTargetRate() {
			return targetRate;
		}

		public void setSourceRate(int rate) {
			sourceRate = rate;
		}

		public void setTargetRate(int rate) {
			targetRate = rate;
		}
	}

	private Map<Vertex, LinkedHashSet<Vertex>> adjacentVertices;
	private DirectedGraph<Vertex, Connection> dynamicGraph;

	private DirectedGraph<Vertex, StaticEdge> graph;

	private List<Vertex> multipleConnectedVertex;
	private List<Vertex> singlyConnectedVertex;

	/**
	 * Create a static representation of a network
	 * 
	 * @param network
	 *            the Network to subtract the static representation
	 */
	public StaticGraphAnalyzer(Network network) {
		// Initialize analyzer variables
		dynamicGraph = network.getGraph();

		// Calculate properties of the static graph
		computeStaticGraph();
	}

	/**
	 * Add static edge from dynamic graph to the static graph
	 */
	private void addStaticEdge() {
		for (Vertex vertex : graph.vertexSet()) {
			// Get all successors of the static vertex
			LinkedHashSet<Vertex> adjVertices = adjacentVertices.get(vertex);
			for (Vertex adjVertex : adjVertices) {
				if (graph.containsVertex(adjVertex)) {
					graph.addEdge(vertex, adjVertex, new StaticEdge());
				}
			}

		}
	}

	/**
	 * Add static vertex from dynamic graph to the static graph
	 */
	private void addStaticVertex() {
		for (Vertex vertex : dynamicGraph.vertexSet()) {
			if (vertex.isInstance()) {
				Instance instance = vertex.getInstance();
				if (instance.getActor().hasMoC()) {
					MoC moc = instance.getActor().getMoC();

					// TODO : extend to QSDF
					if (moc.isCSDF()) {
						graph.addVertex(vertex);
					}

				}
			}
		}
	}

	public void addVertex(Vertex vertex) {
		graph.addVertex(vertex);
		dynamicGraph.addVertex(vertex);
	}

	private MultiMap<Vertex, Connection> checkInput(
			Set<Connection> connections, Vertex vertex) {
		// Get inputs of the destination vertex
		Instance instance = vertex.getInstance();
		Actor actor = instance.getActor();
		OrderedMap<String, Port> inputs = actor.getInputs();

		// List of Connections to keep
		MultiMap<Vertex, Connection> srcConnections = new MultiMap<Vertex, Connection>();

		for (Connection connection : connections) {
			// Get target port name of this connection
			Port target = connection.getTarget();
			String name = target.getName();
			Vertex src = dynamicGraph.getEdgeSource(connection);

			if (inputs.contains(name)) {
				// Target must be connected to the merged instance
				srcConnections.add(src, connection);
			}
		}

		return srcConnections;
	}

	private MultiMap<Vertex, Connection> checkOutput(
			Set<Connection> connections, Vertex vertex) {

		// Get outputs of the destination vertex
		Instance instance = vertex.getInstance();
		Actor actor = instance.getActor();
		OrderedMap<String, Port> outputs = actor.getOutputs();

		// List of Connections to keep
		MultiMap<Vertex, Connection> dstConnections = new MultiMap<Vertex, Connection>();

		for (Connection connection : connections) {
			// Get source port name of this connection
			Port source = connection.getSource();
			String name = source.getName();
			Vertex tgt = dynamicGraph.getEdgeTarget(connection);

			if (outputs.contains(name)) {
				// Source must be connected to the merged instance
				dstConnections.add(tgt, connection);
			}
		}

		return dstConnections;
	}

	/**
	 * Classify instances connection type
	 */
	private void classifyConnections() {
		// Erase previous computation
		singlyConnectedVertex = new ArrayList<Vertex>();
		multipleConnectedVertex = new ArrayList<Vertex>();

		// Analyze each static vertex to determine its connection
		for (Vertex vertex : graph.vertexSet()) {
			Set<StaticEdge> edges = graph.outgoingEdgesOf(vertex);

			if (edges.size() > 1) {
				// Instance is connected to an multiple other static actor
				multipleConnectedVertex.add(vertex);
			} else {
				// Instance is connected to an only other static actor
				singlyConnectedVertex.add(vertex);
			}
		}
	}

	/**
	 * Remove isolated static vertices and classify connected static vertices.
	 */
	public void computeStaticGraph() {
		// Clear previous computed graph
		graph = new DefaultDirectedGraph<Vertex, StaticEdge>(StaticEdge.class);
		adjacentVertices = new HashMap<Vertex, LinkedHashSet<Vertex>>();

		// Compute every successor of vertex from dynamic graph
		setAdjacentVertices();

		// Add all static vertex in the static graph
		addStaticVertex();

		// Find static edge in the graph
		addStaticEdge();

		// Remove unconnected static vertex
		removeSingleInstances();

		// Classify merging case
		classifyConnections();
	}

	private void connectInputs(Vertex vertex,
			MultiMap<Vertex, Connection> inputs) {
		for (Entry<Vertex, Collection<Connection>> entry : inputs.entrySet()) {
			Vertex srcVertex = entry.getKey();

			if (dynamicGraph.containsVertex(srcVertex)) {
				// Source vertex still exist in network
				for (Connection connection : entry.getValue()) {
					dynamicGraph.addEdge(srcVertex, vertex, connection);
				}
			}
		}
	}

	private void connectOutputs(Vertex vertex,
			MultiMap<Vertex, Connection> inputs) {
		for (Entry<Vertex, Collection<Connection>> entry : inputs.entrySet()) {
			Vertex dstVertex = entry.getKey();

			if (dynamicGraph.containsVertex(dstVertex)) {
				// Destination vertex still exist in network
				for (Connection connection : entry.getValue()) {
					dynamicGraph.addEdge(vertex, dstVertex, connection);
				}
			}
		}
	}

	/**
	 * Get vertices connected to multiple others statics vertices.
	 * 
	 * @return a List of multiple connected vertex
	 */
	public List<Vertex> getMultipleConnectedVertex() {
		return multipleConnectedVertex;
	}

	/**
	 * Get vertices connected to only on other static vertex.
	 * 
	 * @return a List of singly connected vertex
	 */
	public List<Vertex> getSinglyConnectedVertex() {
		return singlyConnectedVertex;
	}

	/**
	 * Get source repetition factor between two vertices.
	 * 
	 * @param source
	 *            the source vertex
	 * 
	 * 
	 * @param target
	 *            the target Vertex
	 * 
	 * @return the source repetition factor
	 */
	public int getSourceRate(Vertex source, Vertex target) {
		StaticEdge staticEdge = graph.getEdge(source, target);
		return staticEdge.getSourceRate();
	}

	/**
	 * Returns the static neighbors of the static vertex.
	 * 
	 * @return the static graph
	 */
	public List<Vertex> getStaticNeighbors(Vertex vertex) {
		List<Vertex> neighbours = new ArrayList<Vertex>();
		LinkedHashSet<Vertex> adjVertices = adjacentVertices.get(vertex);

		for (Vertex adjVertice : adjVertices) {
			if (graph.containsVertex(adjVertice)) {
				neighbours.add(adjVertice);
			}
		}

		return neighbours;
	}

	/**
	 * Get all static vertices of the network.
	 * 
	 * @return a set of static vertices
	 */
	public Set<Vertex> getStaticVertices() {
		return graph.vertexSet();
	}

	/**
	 * Get target repetition factor between two vertices.
	 * 
	 * @param source
	 *            the source vertex
	 * 
	 * 
	 * @param target
	 *            the target Vertex
	 * 
	 * @return the source repetition factor
	 */
	public int getTargetRate(Vertex source, Vertex target) {
		StaticEdge staticEdge = graph.getEdge(source, target);
		return staticEdge.getTargetRate();
	}

	/**
	 * Return true if the static graph contains multiple connected vertices.
	 * 
	 * @return true if the static graph contains multiple connected vertices,
	 *         otherwise false;
	 */
	public Boolean hasMultipleConnectedVertex() {
		return multipleConnectedVertex.size() != 0;
	}

	/**
	 * Return true if the static graph contains singly connected vertices.
	 * 
	 * @return true if the static graph contains singly connected vertices,
	 *         otherwise false;
	 */
	public Boolean hasSinglyConnectedVertex() {
		return singlyConnectedVertex.size() != 0;
	}

	/**
	 * Merges a list of vertices into a single vertex in a network.
	 * 
	 * @param srcVertices
	 *            : a list of vertex to merge
	 * 
	 * @param dstVertex
	 *            : the corresponding vertex
	 */
	public void mergeVertices(Set<Vertex> srcVertices, Vertex dstVertex) {
		// Add the destination vertex in network
		addVertex(dstVertex);

		// Update connections
		updateNetwork(srcVertices, dstVertex);

		// Update resulting graph
		computeStaticGraph();
	}

	/**
	 * Remove isolated static instances
	 */
	private void removeSingleInstances() {
		ConnectivityInspector<Vertex, StaticEdge> inspector = new ConnectivityInspector<Vertex, StaticEdge>(
				graph);
		List<Set<Vertex>> vertices = inspector.connectedSets();
		List<Vertex> toRemove = new ArrayList<Vertex>();

		for (Set<Vertex> region : vertices) {
			if (region.size() == 1) {
				// Single vertex, remove it from the graph
				toRemove.addAll(region);
			}
		}

		graph.removeAllVertices(toRemove);
	}

	public void removeVertex(Vertex vertex) {
		graph.removeVertex(vertex);
		dynamicGraph.removeVertex(vertex);
	}

	/**
	 * Set all the adjacent vertices from all vertices of the graph
	 */
	private void setAdjacentVertices() {
		// Iterate though all connection of dynamic graph to determine direct
		// successor
		for (Connection connection : dynamicGraph.edgeSet()) {
			Vertex source = dynamicGraph.getEdgeSource(connection);
			Vertex target = dynamicGraph.getEdgeTarget(connection);

			LinkedHashSet<Vertex> adjacent = adjacentVertices.get(source);
			if (adjacent == null) {
				// Source has not been set yet
				adjacent = new LinkedHashSet<Vertex>();
				adjacentVertices.put(source, adjacent);
			}

			if (!adjacent.contains(target)) {
				// Add target if not already set
				adjacent.add(target);
			}
		}
	}

	/**
	 * Set repetition factor of two vertices.
	 * 
	 * @param source
	 *            the source vertex
	 * 
	 * @param production
	 *            the production rate of the source vertex
	 * 
	 * @param target
	 *            the target Vertex
	 * 
	 * @param comsuption
	 *            the consumption rate of the target Vertex
	 */
	public void setRepetitionFactor(Vertex source, int production,
			Vertex target, int consumption) {
		StaticEdge staticEdge = graph.getEdge(source, target);
		staticEdge.setSourceRate(production);
		staticEdge.setTargetRate(consumption);
	}

	private void updateNetwork(Set<Vertex> srcVertices, Vertex dstVertex) {
		MultiMap<Vertex, Connection> srcConnections = new MultiMap<Vertex, Connection>();
		MultiMap<Vertex, Connection> dstConnections = new MultiMap<Vertex, Connection>();

		// Check connections of the source vertices to keep
		for (Vertex vertex : srcVertices) {
			Set<Connection> incomingEdges = dynamicGraph
					.incomingEdgesOf(vertex);
			Set<Connection> outgoingEdges = dynamicGraph
					.outgoingEdgesOf(vertex);

			// Check connected connections to the vertex
			MultiMap<Vertex, Connection> inConnections = checkInput(
					incomingEdges, dstVertex);
			MultiMap<Vertex, Connection> outConnections = checkOutput(
					outgoingEdges, dstVertex);

			// Set connections to add
			srcConnections.addAll(inConnections);
			dstConnections.addAll(outConnections);
		}

		// Remove source vertices
		for (Vertex vertex : srcVertices) {
			removeVertex(vertex);
		}

		// Connect the merged instance in the network
		connectInputs(dstVertex, srcConnections);
		connectOutputs(dstVertex, dstConnections);
	}

}
