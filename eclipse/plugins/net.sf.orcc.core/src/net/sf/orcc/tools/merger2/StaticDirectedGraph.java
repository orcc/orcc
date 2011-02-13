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

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Map;

import net.sf.orcc.moc.MoC;
import net.sf.orcc.network.Connection;
import net.sf.orcc.network.Instance;
import net.sf.orcc.network.Vertex;

import org.jgrapht.DirectedGraph;
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

	public StaticDirectedGraph(DirectedGraph<Vertex, Connection> graph) {
		dynamicGraph = graph;
		staticGraph = new DefaultDirectedGraph<Vertex, DefaultEdge>(
				DefaultEdge.class);
		adjacentVertices = new HashMap<Vertex, LinkedHashSet<Vertex>>();

		setAdjacentVertices();
		addStaticVertex();
		addStaticEdge();
	}
	
	public DirectedGraph<Vertex, DefaultEdge> getStaticGraph(){
		return staticGraph;
	}

	private void addStaticEdge() {
		for (Vertex vertex : staticGraph.vertexSet()) {
			LinkedHashSet<Vertex> adjVertices = adjacentVertices.get(vertex);
			for (Vertex adjVertex : adjVertices) {
				if (staticGraph.containsVertex(adjVertex)) {
					Boolean exist = findStaticPaths(vertex, adjVertex);

					if (exist) {
						staticGraph.addEdge(vertex, adjVertex);
					}

				}
			}

		}
	}

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

	private LinkedList<Vertex> adjacentNodes(Vertex vertex) {
		LinkedHashSet<Vertex> adjacent = adjacentVertices.get(vertex);
		if (adjacent == null) {
			return new LinkedList<Vertex>();
		}
		return new LinkedList<Vertex>(adjacent);
	}

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
					return false;
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
			if (!findPaths(visited, end)) {
				return false;
			}

			visited.removeLast();
		}

		return true;
	}

	private Boolean findStaticPaths(Vertex source, Vertex target) {
		LinkedList<Vertex> visited = new LinkedList<Vertex>();
		visited.add(source);
		return findPaths(visited, target);
	}

	private Boolean isStaticPath(LinkedList<Vertex> path) {
		for (Vertex node : path) {
			if (!staticGraph.containsVertex(node)) {
				return false;
			}
		}

		return true;
	}

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
