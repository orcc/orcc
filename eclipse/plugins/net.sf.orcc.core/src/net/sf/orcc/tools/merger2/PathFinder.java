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
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import net.sf.orcc.network.Vertex;

import org.jgrapht.DirectedGraph;
import org.jgrapht.graph.DefaultWeightedEdge;

/**
 * This class find all the possible path between two vertices.
 * 
 * 
 * @author Jérôme Gorin
 * 
 */
public class PathFinder {
	Map<Vertex, LinkedHashSet<Vertex>> adjacentVertices;
	List<List<Vertex>> dynamicPaths;
	
	Vertex source;
	DirectedGraph<Vertex, DefaultWeightedEdge> staticGraph;
	List<List<Vertex>> staticPaths;
	Vertex target;
	
	/**
	 * Create a new path finder.
	 * 
	 * @param source
	 *            the source vertex
	 *            
	 * @param source
	 *            the target vertex
	 *            
	 * @param adjacentVertices
	 *            A map of successors of vertices
	 *
	 * @param staticGraph
	 *            The corresponding static graph
	 */
	public PathFinder(Vertex source, Vertex target, DirectedGraph<Vertex, DefaultWeightedEdge> staticGraph, Map<Vertex, LinkedHashSet<Vertex>> adjacentVertices){
		this.source = source;
		this.target = target;
		this.adjacentVertices = adjacentVertices;
		this.staticGraph = staticGraph;
		
		LinkedList<Vertex> visited = new LinkedList<Vertex>();
		visited.add(source);
		findPaths(visited, target);
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
	private void findPaths(LinkedList<Vertex> visited, Vertex end) {
		LinkedList<Vertex> nodes = adjacentNodes(visited.getLast());

		// examine adjacent nodes
		for (Vertex node : nodes) {
			if (visited.contains(node)) {
				continue;
			}
			if (node.equals(end)) {
				visited.add(node);
				storePath(visited);
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
			findPaths(visited, end);
			visited.removeLast();
		}
	}
	
	public List<List<Vertex>> getAllPaths() {
		List<List<Vertex>> paths = new ArrayList<List<Vertex>>(staticPaths);
		paths.addAll(dynamicPaths);
		return paths;
	}
	
	public List<List<Vertex>> getDynamicPaths(){
		return staticPaths;
	}
	
	public List<List<Vertex>> getStaticPaths(){
		return staticPaths;
	}
	
	public boolean hasDynamicPaths(){
		return true;
	}
	
	
	/**
	 * Return true if the given path as only static actors.
	 * 
	 * @param path
	 *            the path to check
	 * 
	 * @return True if the path is detected as static otherwise false.
	 */
	private void storePath(LinkedList<Vertex> path) {
		for (Vertex node : path) {
			if (!staticGraph.containsVertex(node)) {
				dynamicPaths.add(new ArrayList<Vertex>(path));
				return;
			}
		}

		staticPaths.add(new ArrayList<Vertex>(path));
	}
}
