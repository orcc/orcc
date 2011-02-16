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
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.orcc.moc.MoC;
import net.sf.orcc.network.Connection;
import net.sf.orcc.network.Instance;
import net.sf.orcc.network.Network;
import net.sf.orcc.network.Vertex;

import org.jgrapht.DirectedGraph;
import org.jgrapht.alg.ConnectivityInspector;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;

/**
 * This class find the possible static merging in a network.
 * 
 * 
 * @author Jérôme Gorin
 * 
 */
public class StaticGraph {
	private Map<Vertex, LinkedHashSet<Vertex>> adjacentVertices;
	private DirectedGraph<Vertex, Connection> dynamicGraph;
	private DirectedGraph<Vertex, DefaultEdge> staticGraph;
	List<Set<Vertex>> regions;

	public StaticGraph(Network network) {
		dynamicGraph = network.getGraph();
		staticGraph = new DefaultDirectedGraph<Vertex, DefaultEdge>(
				DefaultEdge.class);
		adjacentVertices = new HashMap<Vertex, LinkedHashSet<Vertex>>();
		regions = new ArrayList<Set<Vertex>>();

		// Compute every successor of vertex from dynamic graph
		setAdjacentVertices();

		// Add all static vertex in the static graph
		addStaticVertex();

		// Find static edge in the graph
		addStaticEdge();
		
		// Remove unconnected static vertex
		setRegions();
	}
	
	/**
	 * Set the static regions of the graph
	 */
	private void setRegions(){
		ConnectivityInspector<Vertex, DefaultEdge> inspector = new ConnectivityInspector<Vertex, DefaultEdge>(
				staticGraph);
		List<Set<Vertex>> vertices = inspector.connectedSets();
		List<Vertex> toRemove = new ArrayList<Vertex>();
		
		for (Set<Vertex> region : vertices){
			if (region.size() == 1){
				//Single vertex, remove it from the graph
				toRemove.addAll(region);
			}else{
				//Store the region
				regions.add(region);
			}
		}
		
		staticGraph.removeAllVertices(toRemove);
	}

	public List<Set<Vertex>> getRegions(){
		return regions;
	}
	
	public Boolean hasRegions(){
		return regions.size() != 0;
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
					staticGraph.addEdge(vertex, adjVertex);
				}
			}

		}
	}
	
	public void updateRegion(Set<Vertex> region){
		//TODO : implement a real update of the region
		regions.remove(region);
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
	 * Returns the resulting static graph.
	 * 
	 * @return the static graph
	 */
	public DirectedGraph<Vertex, DefaultEdge> getStaticGraph() {
		return staticGraph;
	}
	
	/**
	 * Returns the static neighbour of the static vertex.
	 * 
	 * @return the static graph
	 */
	public Set<Vertex> getStaticNeighbour(Vertex vertex) {
		Set<Vertex> neighbours = new HashSet<Vertex>();
		LinkedHashSet<Vertex> adjVertices = adjacentVertices.get(vertex);
		
		for (Vertex adjVertice : adjVertices){
			if (staticGraph.containsVertex(adjVertice)){
				neighbours.add(adjVertice);
			}
		}
		
		return neighbours;
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
