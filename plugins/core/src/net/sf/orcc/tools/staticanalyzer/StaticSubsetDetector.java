/*
 * Copyright (c) 2010, Ecole Polytechnique Fédérale de Lausanne 
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
 *   * Neither the name of the Ecole Polytechnique Fédérale de Lausanne nor the 
 *     names of its contributors may be used to endorse or promote products 
 *     derived from this software without specific prior written permission.
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

package net.sf.orcc.tools.staticanalyzer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import net.sf.orcc.OrccException;
import net.sf.orcc.classes.IClass;
import net.sf.orcc.ir.Actor;
import net.sf.orcc.network.Connection;
import net.sf.orcc.network.Instance;
import net.sf.orcc.network.Network;
import net.sf.orcc.network.Vertex;

import org.jgrapht.DirectedGraph;
import org.jgrapht.alg.StrongConnectivityInspector;
import org.jgrapht.graph.DirectedMultigraph;
import org.jgrapht.graph.EdgeReversedGraph;

/**
 * This class detects statically schedulable regions of the graph. A region
 * cannot be a subset of another region and intersections of regions are
 * potentially non-empty. the network is assumed acyclic. The network classifier
 * is assumed to be computed first.
 * 
 * @author Ghislain Roquier
 * 
 */

public class StaticSubsetDetector {

	private Set<Vertex> discovered = new HashSet<Vertex>();
	private Set<Vertex> finished = new HashSet<Vertex>();

	private List<Vertex> orderedVertices;

	private Set<Set<Vertex>> staticRegionSet;

	private DirectedGraph<Vertex, Connection> graph;

	public StaticSubsetDetector(Network network) {

		graph = network.getGraph();
	}

	/**
	 * Detects the static regions of the network. The detection is done by
	 * traversing the graph
	 * 
	 * 
	 */
	public Set<Set<Vertex>> staticRegionSets() throws OrccException {

		staticRegionSet = new HashSet<Set<Vertex>>();
		List<List<Vertex>> staticRegionList = new ArrayList<List<Vertex>>();
		orderedVertices = new LinkedList<Vertex>();

		// 1) orders vertices according to finishing time.
		orderedVertices = new TopologicalSorter(graph).topologicalSort();
		discovered = new HashSet<Vertex>();
		finished = new HashSet<Vertex>();

		// 2) detects static region considering vertices in order
		// of increasing finishing time
		Iterator<Vertex> it = orderedVertices.iterator();
		while (it.hasNext()) {
			Vertex vertex = it.next();
			IClass clasz = vertex.getInstance().getContentClass();
			if (!discovered.contains(vertex) && clasz.isSDF()) {
				List<Vertex> set = new LinkedList<Vertex>();
				staticRegionList.add(set);
				staticRegionAnalysis(graph, vertex, set);
			}
		}

		// 3) detects static region considering vertices in order of
		// decreasing finishing time
		discovered = new HashSet<Vertex>();
		finished = new HashSet<Vertex>();

		DirectedGraph<Vertex, Connection> inverseGraph = new EdgeReversedGraph<Vertex, Connection>(
				graph);

		it = ((LinkedList<Vertex>) orderedVertices).descendingIterator();
		while (it.hasNext()) {
			Vertex vertex = it.next();
			IClass clasz = vertex.getInstance().getContentClass();
			if (!discovered.contains(vertex) && clasz.isSDF()) {
				List<Vertex> set = new LinkedList<Vertex>();
				staticRegionList.add(set);
				staticRegionAnalysis(inverseGraph, vertex, set);
			}
		}

		for (List<Vertex> list : staticRegionList) {
			Set<Vertex> set = new HashSet<Vertex>(list);
			staticRegionSet.add(set);
		}
		return staticRegionSet;
	}

	/**
	 * DFS
	 * 
	 * @throws OrccException
	 * 
	 */
	private void staticRegionAnalysis(DirectedGraph<Vertex, Connection> graph,
			Vertex vertex, List<Vertex> vertices) throws OrccException {

		LinkedList<Vertex> stack = new LinkedList<Vertex>(Arrays.asList(vertex));

		while (!stack.isEmpty()) {
			Vertex v = stack.pop();
			IClass clasz = v.getInstance().getContentClass();
			if (clasz.isSDF()) {
				if (!discovered.contains(v)) {
					discovered.add(v);
					if (vertices != null) {
						vertices.add(v);
					}
					stack.push(v);
					finished.add(v);
					for (Connection edge : graph.outgoingEdgesOf(v)) {
						Vertex tgtVertex = graph.getEdgeTarget(edge);
						clasz = tgtVertex.getInstance().getContentClass();
						if (!discovered.contains(tgtVertex) && clasz.isSDF()) {
							if (vertices != null) {
								List<Vertex> l = new LinkedList<Vertex>(
										vertices);
								l.add(tgtVertex);
								if (checkCycles(graph, l)) {
									stack.push(tgtVertex);
								}
							}
						}
					}
				}
			}
		}
	}

	private boolean checkCycles(DirectedGraph<Vertex, Connection> graph,
			List<Vertex> vertices) throws OrccException {

		boolean ret = true;

		DirectedGraph<Vertex, Connection> clusteredGraph = new DirectedMultigraph<Vertex, Connection>(
				Connection.class);

		for (Vertex vertex : graph.vertexSet()) {
			clusteredGraph.addVertex(vertex);
		}
		for (Connection connection : graph.edgeSet()) {
			clusteredGraph.addEdge(graph.getEdgeSource(connection), graph
					.getEdgeTarget(connection), connection);
		}

		Actor cluster = new Actor("", "", null, null, null, null, null, null,
				null, null, null);
		Vertex clusterVertex = new Vertex(new Instance(cluster.getName(),
				cluster));

		clusteredGraph.addVertex(clusterVertex);

		for (Connection edge : graph.edgeSet()) {
			Vertex srcVertex = graph.getEdgeSource(edge);
			if (vertices.contains(srcVertex)) {
				Connection incoming = new Connection(edge.getSource(), edge
						.getTarget(), edge.getAttributes());
				clusteredGraph.addEdge(srcVertex, clusterVertex, incoming);
				clusteredGraph.removeEdge(edge);
			}

			Vertex tgtVertex = graph.getEdgeTarget(edge);
			if (vertices.contains(srcVertex)) {
				Connection outgoing = new Connection(edge.getSource(), edge
						.getTarget(), edge.getAttributes());
				clusteredGraph.addEdge(clusterVertex, tgtVertex, outgoing);
				clusteredGraph.removeEdge(edge);
			}
		}

		clusteredGraph.removeAllVertices(vertices);

		List<Set<Vertex>> sccs = new StrongConnectivityInspector<Vertex, Connection>(
				clusteredGraph).stronglyConnectedSets();

		for (Set<Vertex> scc : sccs) {
			int size = scc.size();
			if (scc.remove(clusterVertex) && size > 1) {
				for (Vertex v : scc) {
					IClass clasz = v.getInstance().getContentClass();
					if (clasz.isSDF()) {
						ret = false;
					}
				}
			}
		}
		return ret;
	}

}
