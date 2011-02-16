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
import java.util.Map;

import net.sf.orcc.OrccException;
import net.sf.orcc.moc.MoC;
import net.sf.orcc.network.Connection;
import net.sf.orcc.network.Instance;
import net.sf.orcc.network.Network;
import net.sf.orcc.network.Vertex;
import net.sf.orcc.network.transformations.INetworkTransformation;

import org.jgrapht.DirectedGraph;
import org.jgrapht.graph.DefaultDirectedWeightedGraph;
import org.jgrapht.graph.DefaultWeightedEdge;

/**
 * This class find the possible static merging in a network.
 * 
 * 
 * @author Jérôme Gorin
 * 
 */
public class StaticDirectedGraphTransformation implements
		INetworkTransformation {
	private Map<Vertex, LinkedHashSet<Vertex>> adjacentVertices;
	private DirectedGraph<Vertex, Connection> dynamicGraph;
	private Network network;
	private DirectedGraph<Vertex, DefaultWeightedEdge> staticGraph;

	/**
	 * Add static edge from dynamic graph to the static graph
	 */
	private void addStaticEdge() {
		for (Vertex vertex : staticGraph.vertexSet()) {
			// Get all successors of the static vertex
			LinkedHashSet<Vertex> adjVertices = adjacentVertices.get(vertex);
			for (Vertex adjVertex : adjVertices) {
				if (staticGraph.containsVertex(adjVertex)) {
					PathFinder paths = new PathFinder(vertex, adjVertex,
							staticGraph, adjacentVertices);

					if (!paths.hasDynamicPaths()) {
						// A static path between these two vertex is existing
						SDFCompositionAnalyzer compositionAnalyzer = new SDFCompositionAnalyzer(
								network, vertex, adjVertex,
								paths.getStaticPaths());

						if (compositionAnalyzer.isMergeable()) {

						}
					}
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
	 * Create a new static directed graph from a dynamic directed graph.
	 * 
	 * @param graph
	 *            a directed graph from a network
	 */
	public DirectedGraph<Vertex, DefaultWeightedEdge> getStaticDirectedGraph() {
		// Compute every successor of vertex from dynamic graph
		setAdjacentVertices();

		// Add all static vertex in the static graph
		addStaticVertex();

		// Find static edge in the graph
		addStaticEdge();
		/*
		 * try { //Output resulting graph DOTExporter<Vertex, DefaultEdge>
		 * exporter = new DOTExporter<Vertex, DefaultEdge>(); FileWriter file;
		 * 
		 * file = new
		 * FileWriter("D:/projet/orcc_decoder/C/Xilinx (merger)/test.dot");
		 * exporter.export(file, staticGraph); } catch (IOException e) { // TODO
		 * Auto-generated catch block e.printStackTrace(); }
		 */

		return staticGraph;
	}

	/**
	 * Returns the resulting static graph.
	 * 
	 * @return the static graph
	 */
	public DirectedGraph<Vertex, DefaultWeightedEdge> getStaticGraph() {
		return staticGraph;
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

	@Override
	public void transform(Network network) throws OrccException {
		this.network = network;
		dynamicGraph = network.getGraph();
		staticGraph = new DefaultDirectedWeightedGraph<Vertex, DefaultWeightedEdge>(
				DefaultWeightedEdge.class);
		adjacentVertices = new HashMap<Vertex, LinkedHashSet<Vertex>>();
	}

}
