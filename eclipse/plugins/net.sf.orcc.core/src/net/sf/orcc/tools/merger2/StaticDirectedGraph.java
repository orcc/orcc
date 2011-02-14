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

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.orcc.moc.MoC;
import net.sf.orcc.network.Connection;
import net.sf.orcc.network.Instance;
import net.sf.orcc.network.Vertex;

import org.jgrapht.DirectedGraph;
import org.jgrapht.alg.ConnectivityInspector;
import org.jgrapht.ext.DOTExporter;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;

/**
 * This class find the possible static path between two vertex of a graph.
 * 
 * 
 * @author Jérôme Gorin
 * 
 */
public class StaticDirectedGraph {
	private Map<Vertex, LinkedHashSet<Vertex>> adjacentVertices;
	private DirectedGraph<Vertex, Connection> dynamicGraph;
	private DirectedGraph<Vertex, DefaultEdge> staticGraph;

	/**
	 * Create a new static directed graph from a dynamic directed graph.
	 * 
	 * @param graph
	 *            a directed graph from a network
	 */
	public StaticDirectedGraph(DirectedGraph<Vertex, Connection> graph) {
		dynamicGraph = graph;
		staticGraph = new DefaultDirectedGraph<Vertex, DefaultEdge>(
				DefaultEdge.class);
		adjacentVertices = new HashMap<Vertex, LinkedHashSet<Vertex>>();

		// Compute every successor of vertex from dynamic graph
		setAdjacentVertices();

		// Add all static vertex in the static graph
		addStaticVertex();

		// Find static edge in the graph
		addStaticEdge();
		
		//Refine graph by removing potentially dynamic paths
		refineStaticGraph();
		
		try {
			//Output resulting graph
			DOTExporter<Vertex, DefaultEdge> exporter = new DOTExporter<Vertex, DefaultEdge>();
			FileWriter file;
			
			file = new FileWriter("D:/projet/orcc_decoder/C/Xilinx (merger)/test.dot");
			exporter.export(file, staticGraph);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Add static edge from dynamic graph to the static graph
	 */
	private void addStaticEdge() {
		for (Vertex vertex : staticGraph.vertexSet()) {
			// Get all successors of the static vertex
			LinkedHashSet<Vertex> adjVertices = adjacentVertices.get(vertex);
			for (Vertex adjVertex : adjVertices) {
				if (staticGraph.containsVertex(adjVertex)) {
					// A static path between these two vertex is existing
					staticGraph.addEdge(vertex, adjVertex);
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
						staticGraph.addVertex(vertex);
					}

				}
			}
		}
	}

	/**
	 * Return the adjacent nodes of a vertex.
	 * 
	 * @param vertex
	 *            the vertex to get adjacent node from.
	 * 
	 * 
	 * @return a Linked list of all adjacent vertices.
	 */
	private LinkedList<Vertex> adjacentNodes(Vertex vertex) {
		LinkedHashSet<Vertex> adjacent = adjacentVertices.get(vertex);
		if (adjacent == null) {
			return new LinkedList<Vertex>();
		}
		return new LinkedList<Vertex>(adjacent);
	}

	/**
	 * Find a dynamic path between the last vertex of a LinkedList and the given
	 * vertex using breadth-first search.
	 * 
	 * @param visited
	 *            a LinkedList of all vertices already visited.
	 * 
	 * 
	 * @return true if the paths found is dynamic, otherwise false.
	 */
	private Boolean findPaths(LinkedList<Vertex> visited, Vertex end) {
		LinkedList<Vertex> nodes = adjacentNodes(visited.getLast());

		// examine adjacent nodes
		for (Vertex node : nodes) {
			if (visited.contains(node)) {
				continue;
			}
			if (node.equals(end)) {
				visited.add(node);
				if (!isStaticPath(visited)) {
					return true;
				}

				visited.removeLast();
				break;
			}
		}
		// in breadth-first, recursion needs to come after visiting adjacent
		// nodes
		for (Vertex node : nodes) {
			if (visited.contains(node) || node.equals(end)) {
				continue;
			}
			visited.addLast(node);
			if (findPaths(visited, end)) {
				return true;
			}

			visited.removeLast();
		}

		return false;
	}

	/**
	 * Returns the resulting static graph.
	 * 
	 * @return the static graph
	 */
	public DirectedGraph<Vertex, DefaultEdge> getStaticGraph() {
		return staticGraph;
	}

	/**
	 * Return true if a path between two vertices is dynamic.
	 * 
	 * @param source
	 *            the source vertex
	 * 
	 * @param target
	 *            the target vertex
	 * 
	 * @return True if a dynamic path is detected.
	 */
	private Boolean hasDynamicPaths(Vertex source, Vertex target) {
		LinkedList<Vertex> visited = new LinkedList<Vertex>();
		visited.add(source);
		return findPaths(visited, target);
	}

	/**
	 * Return true if the given path as only static actors.
	 * 
	 * @param path
	 *            the path to check
	 * 
	 * @return True if the path is detected as static otherwise false.
	 */
	private Boolean isStaticPath(LinkedList<Vertex> path) {
		for (Vertex node : path) {
			if (!staticGraph.containsVertex(node)) {
				return false;
			}
		}

		return true;
	}

	private void refineStaticGraph(){
		ConnectivityInspector<Vertex, DefaultEdge> inspector = new ConnectivityInspector<Vertex, DefaultEdge>(
				staticGraph);
		List<Set<Vertex>> regions = inspector.connectedSets();
		
		for (Set<Vertex> region : regions){
			//Examine if a region contains dynamic paths
			
			for (Vertex source : region){
				for (Vertex target : region){
					if (source != target){
						// Two static vertex are connected
						if (hasDynamicPaths(source, target)){
							//Separate this actor from the static region
							//TODO : Find something reliable
						}
					}
				}
			}
		}
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

}
