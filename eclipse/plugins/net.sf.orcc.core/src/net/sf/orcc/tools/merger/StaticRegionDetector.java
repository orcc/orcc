/*
 * Copyright (c) 2010, EPFL
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
 *   * Neither the name of the EPFL nor the names of its contributors may be used 
 *     to endorse or promote products derived from this software without specific 
 *     prior written permission.
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

package net.sf.orcc.tools.merger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import net.sf.orcc.OrccException;
import net.sf.orcc.df.Actor;
import net.sf.orcc.df.Connection;
import net.sf.orcc.df.DfFactory;
import net.sf.orcc.df.Instance;
import net.sf.orcc.df.Network;
import net.sf.orcc.df.Port;
import net.sf.orcc.df.Vertex;
import net.sf.orcc.moc.MoC;

import org.jgrapht.DirectedGraph;
import org.jgrapht.alg.StrongConnectivityInspector;
import org.jgrapht.graph.DirectedMultigraph;

/**
 * This class detects statically schedulable regions of the graph. A region
 * cannot be a subset of another region and intersections of regions are
 * potentially non-empty. the network is assumed acyclic. The network classifier
 * is assumed to be computed first.
 * 
 * @author Ghislain Roquier
 * 
 */
public class StaticRegionDetector {

	private Set<Vertex> discovered = new HashSet<Vertex>();
	private Set<Vertex> finished = new HashSet<Vertex>();

	private Set<Set<Vertex>> staticRegionSet;

	private Network network;

	public StaticRegionDetector(Network network) {
		this.network = network;
	}

	/**
	 * 
	 * @param graph
	 * @param vertices
	 * @return
	 */
	private boolean introduceCycle(List<Vertex> vertices) {
		boolean ret = false;
		int inIndex = 0;
		int outIndex = 0;

		DirectedGraph<Vertex, Connection> clusteredGraph = new DirectedMultigraph<Vertex, Connection>(
				Connection.class);

		for (Vertex vertex : network.getVertices()) {
			clusteredGraph.addVertex(vertex);
		}

		for (Connection connection : network.getConnections()) {
			clusteredGraph.addEdge(connection.getSource(),
					connection.getTarget(), connection);
		}

		Actor cluster = DfFactory.eINSTANCE.createActor();
		cluster.setName("cluster");

		Instance inst = DfFactory.eINSTANCE.createInstance(cluster.getName(),
				cluster);
		Vertex clusterVertex = inst;

		clusteredGraph.addVertex(clusterVertex);

		for (Connection edge : network.getConnections()) {
			Vertex srcVertex = edge.getSource();
			Vertex tgtVertex = edge.getTarget();

			if (!vertices.contains(srcVertex) && vertices.contains(tgtVertex)) {
				Port tgtPort = DfFactory.eINSTANCE.createPort(edge
						.getTargetPort());
				tgtPort.setName("input_" + outIndex++);
				cluster.getInputs().add(tgtPort);
				Connection incoming = DfFactory.eINSTANCE.createConnection(
						srcVertex, edge.getSourcePort(), clusterVertex,
						tgtPort, edge.getAttributes());
				clusteredGraph.addEdge(srcVertex, clusterVertex, incoming);
			} else if (vertices.contains(srcVertex)
					&& !vertices.contains(tgtVertex)) {
				Port srcPort = DfFactory.eINSTANCE.createPort(edge
						.getSourcePort());
				srcPort.setName("output_" + inIndex++);
				cluster.getOutputs().add(srcPort);
				Connection outgoing = DfFactory.eINSTANCE.createConnection(
						clusterVertex, srcPort, tgtVertex,
						edge.getTargetPort(), edge.getAttributes());
				clusteredGraph.addEdge(clusterVertex, tgtVertex, outgoing);
			}
		}

		clusteredGraph.removeAllVertices(vertices);

		List<Set<Vertex>> sccs = new StrongConnectivityInspector<Vertex, Connection>(
				clusteredGraph).stronglyConnectedSets();

		for (Set<Vertex> scc : sccs) {
			if (scc.size() > 1) {
				if (scc.remove(clusterVertex)) {
					for (Vertex v : scc) {
						MoC clasz = ((Instance) v).getMoC();
						if (!clasz.isCSDF()) {
							ret = true;
						}
					}
				}
			}
		}
		return ret;
	}

	/**
	 * DFS
	 * 
	 * @throws OrccException
	 * 
	 */
	private void staticRegionAnalysis(Vertex vertex, List<Vertex> vertices) {
		LinkedList<Vertex> stack = new LinkedList<Vertex>(Arrays.asList(vertex));

		while (!stack.isEmpty()) {
			Vertex v = stack.pop();
			MoC moc = ((Instance) v).getMoC();
			if (moc.isCSDF()) {
				if (!discovered.contains(v)) {
					discovered.add(v);
					if (vertices != null) {
						vertices.add(v);
					}
					stack.push(v);
					finished.add(v);
					for (Connection edge : v.getOutgoing()) {
						Vertex tgtVertex = edge.getTarget();
						moc = ((Instance) tgtVertex).getMoC();
						if (!discovered.contains(tgtVertex) && moc.isCSDF()) {
							if (vertices != null) {
								List<Vertex> l = new LinkedList<Vertex>(
										vertices);
								l.add(tgtVertex);
								if (!introduceCycle(l)) {
									stack.push(tgtVertex);
								}
							}
						}
					}
				}
			}
		}
	}

	/**
	 * Detects the static regions of the network. The detection is done by
	 * traversing the graph
	 * 
	 * 
	 */
	public Set<Set<Vertex>> staticRegionSets() {
		staticRegionSet = new HashSet<Set<Vertex>>();
		List<List<Vertex>> staticRegionList = new ArrayList<List<Vertex>>();

		discovered = new HashSet<Vertex>();
		finished = new HashSet<Vertex>();

		List<Vertex> vertices = new TopologicalSorter(network)
				.topologicalSort();
		for (Vertex vertex : vertices) {
			if (vertex.isInstance()) {
				MoC clasz = ((Instance) vertex).getMoC();
				if (!discovered.contains(vertex) && clasz.isCSDF()) {
					List<Vertex> set = new LinkedList<Vertex>();
					staticRegionList.add(set);
					staticRegionAnalysis(vertex, set);
				}
			}
		}

		for (List<Vertex> list1 : staticRegionList) {
			if (list1.size() > 1) {
				Set<Vertex> set1 = new HashSet<Vertex>(list1);
				staticRegionSet.add(set1);
			}
		}

		return staticRegionSet;
	}
}
