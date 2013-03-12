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
package net.sf.orcc.tools.merger.actor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import net.sf.orcc.df.Actor;
import net.sf.orcc.df.Connection;
import net.sf.orcc.df.DfFactory;
import net.sf.orcc.df.Network;
import net.sf.orcc.df.Port;
import net.sf.orcc.graph.Edge;
import net.sf.orcc.graph.Vertex;
import net.sf.orcc.graph.visit.ReversePostOrder;
import net.sf.orcc.graph.visit.SccFinder;
import net.sf.orcc.moc.MoC;

import org.eclipse.emf.ecore.util.EcoreUtil;

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

	private Set<Vertex> discovered;
	private Set<Vertex> finished;

	private Set<List<Vertex>> staticRegionSet;

	private Network network;

	/**
	 * Create a new detector of static region in a given network.
	 * 
	 * @param network
	 *            the network to check
	 */
	public StaticRegionDetector(Network network) {
		this.network = network;
	}

	/**
	 * Check if a list of vertices contains a cycle.
	 * 
	 * @param vertices
	 *            the list of vertices to check
	 * @return true if the list contains a cycle
	 */
	private boolean introduceCycle(List<Vertex> vertices) {
		network = EcoreUtil.copy(network);

		// the clustered graph is just the network
		Network clusteredGraph = network;

		Actor actor = DfFactory.eINSTANCE.createActor();
		actor.setName("cluster");

		clusteredGraph.add(actor);

		List<Vertex> verticesCopy = new ArrayList<Vertex>();
		List<Edge> edges = new ArrayList<Edge>();

		for (Vertex vertex : vertices) {
			for (Vertex other : clusteredGraph.getChildren()) {
				if (vertex.getLabel().equals(other.getLabel())) {
					verticesCopy.add(other);
				}
			}
		}

		for (Connection edge : network.getConnections()) {
			Vertex srcVertex = edge.getSource();
			Vertex tgtVertex = edge.getTarget();

			if (!verticesCopy.contains(srcVertex)
					&& verticesCopy.contains(tgtVertex)) {
				Port tgtPort = DfFactory.eINSTANCE.createPort();
				actor.getInputs().add(tgtPort);
				Connection incoming = DfFactory.eINSTANCE.createConnection(
						srcVertex, edge.getSourcePort(), actor, tgtPort,
						edge.getAttributes());
				edges.add(incoming);
			} else if (verticesCopy.contains(srcVertex)
					&& !verticesCopy.contains(tgtVertex)) {
				Port srcPort = DfFactory.eINSTANCE.createPort();
				actor.getOutputs().add(srcPort);
				Connection outgoing = DfFactory.eINSTANCE.createConnection(
						actor, srcPort, tgtVertex, edge.getTargetPort(),
						edge.getAttributes());
				edges.add(outgoing);
			}
		}

		clusteredGraph.getEdges().addAll(edges);
		clusteredGraph.removeVertices(verticesCopy);

		List<List<Vertex>> sccs = new SccFinder().visitGraph(clusteredGraph);

		for (List<Vertex> scc : sccs) {
			if (scc.size() > 1) {
				if (scc.remove(actor)) {
					for (Vertex v : scc) {
						MoC moc = v.getAdapter(Actor.class).getMoC();
						if (moc != null && !moc.isCSDF()) {
							return true;
						}
					}
				}
			}
		}
		return false;
	}

	/**
	 * Depth-First Search.
	 * 
	 * @param instance
	 * @param instances
	 */
	private void analysisStaticRegion(Vertex instance, List<Vertex> instances) {
		LinkedList<Vertex> stack = new LinkedList<Vertex>(
				Arrays.asList(instance));

		while (!stack.isEmpty()) {
			Vertex v = stack.pop();
			MoC moc = v.getAdapter(Actor.class).getMoC();
			if (moc.isCSDF()) {
				if (!discovered.contains(v)) {
					discovered.add(v);
					if (instances != null) {
						instances.add(v);
					}
					stack.push(v);
					finished.add(v);
					for (Edge edge : v.getOutgoing()) {
						Vertex tgtVertex = edge.getTarget();
						moc = tgtVertex.getAdapter(Actor.class).getMoC();
						if (!discovered.contains(tgtVertex) && moc.isCSDF()) {
							if (instances != null) {
								List<Vertex> l = new LinkedList<Vertex>(
										instances);
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
	 * traversing the graph.
	 * 
	 * @return A set of lists of connected vertices that are static
	 */
	public Set<List<Vertex>> getStaticRegions() {
		staticRegionSet = new HashSet<List<Vertex>>();
		discovered = new HashSet<Vertex>();
		finished = new HashSet<Vertex>();
		
		List<List<Vertex>> staticRegionList = new ArrayList<List<Vertex>>();

		for (Vertex vertex : new ReversePostOrder(network, network.getInputs())) {
			MoC moc = vertex.getAdapter(Actor.class).getMoC();
			if (!discovered.contains(vertex) && moc.isCSDF()) {
				List<Vertex> list = new LinkedList<Vertex>();
				staticRegionList.add(list);
				analysisStaticRegion(vertex, list);
			}
		}

		for (List<Vertex> list1 : staticRegionList) {
			if (list1.size() > 1) {
				List<Vertex> set1 = new ArrayList<Vertex>(list1);
				staticRegionSet.add(set1);
			}
		}

		return staticRegionSet;
	}
}
