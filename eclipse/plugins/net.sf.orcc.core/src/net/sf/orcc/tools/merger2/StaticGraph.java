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
 * This class defines a static representation graph to ease the merging of a network.
 * 
 * 
 * @author Jerome Gorin
 * 
 */
public class StaticGraph {
	/**
	 * This class represents a static edge with a production and a comsuption.
	 * 
	 * 
	 * @author Jérôme Gorin
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
	List<Set<Vertex>> regions;

	private DirectedGraph<Vertex, StaticEdge> staticGraph;

	public void updateNetwork(Vertex source, Vertex target, Vertex mergedVertex){
		
	}
	
	/**
	 * Create a static representation of a network
	 * 
	 * @param network
	 *            the Network to subtract the static representation
	 */
	public StaticGraph(Network network) {
		dynamicGraph = network.getGraph();
		staticGraph = new DefaultDirectedGraph<Vertex, StaticEdge>(
				StaticEdge.class);
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
	 * Add static edge from dynamic graph to the static graph
	 */
	private void addStaticEdge() {
		for (Vertex vertex : staticGraph.vertexSet()) {
			// Get all successors of the static vertex
			LinkedHashSet<Vertex> adjVertices = adjacentVertices.get(vertex);
			for (Vertex adjVertex : adjVertices) {
				if (staticGraph.containsVertex(adjVertex)) {
					staticGraph.addEdge(vertex, adjVertex, new StaticEdge());
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
	 * Get static regions of the network;
	 * 
	 * @return a List of static regions
	 */
	public List<Set<Vertex>> getRegions() {
		return regions;
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
		StaticEdge staticEdge = staticGraph.getEdge(source, target);
		return staticEdge.getSourceRate();
	}

	/**
	 * Returns the static neighbors of the static vertex.
	 * 
	 * @return the static graph
	 */
	public Set<Vertex> getStaticNeighbors(Vertex vertex) {
		Set<Vertex> neighbours = new HashSet<Vertex>();
		LinkedHashSet<Vertex> adjVertices = adjacentVertices.get(vertex);

		for (Vertex adjVertice : adjVertices) {
			if (staticGraph.containsVertex(adjVertice)) {
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
		return staticGraph.vertexSet();
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
		StaticEdge staticEdge = staticGraph.getEdge(source, target);
		return staticEdge.getTargetRate();
	}

	/**
	 * Return true if the network has static regions.
	 * 
	 * @return True if the network contains static regions, otherwise false
	 */
	public Boolean hasRegions() {
		return regions.size() != 0;
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
	 * Set the static regions of the graph
	 */
	private void setRegions() {
		ConnectivityInspector<Vertex, StaticEdge> inspector = new ConnectivityInspector<Vertex, StaticEdge>(
				staticGraph);
		List<Set<Vertex>> vertices = inspector.connectedSets();
		List<Vertex> toRemove = new ArrayList<Vertex>();

		for (Set<Vertex> region : vertices) {
			if (region.size() == 1) {
				// Single vertex, remove it from the graph
				toRemove.addAll(region);
			} else {
				// Store the region
				regions.add(region);
			}
		}

		staticGraph.removeAllVertices(toRemove);
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
		StaticEdge staticEdge = staticGraph.getEdge(source, target);
		staticEdge.setSourceRate(production);
		staticEdge.setTargetRate(consumption);
	}

	public void updateRegion(Set<Vertex> region) {
		// TODO : implement a real update of the region
		regions.remove(region);
	}

}
