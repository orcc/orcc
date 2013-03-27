/*
 * Copyright (c) 2012-2013, Synflow SAS
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
package net.sf.orcc.graph.visit;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.orcc.graph.Edge;
import net.sf.orcc.graph.Graph;
import net.sf.orcc.graph.GraphPackage;
import net.sf.orcc.graph.Vertex;

import org.eclipse.emf.ecore.EReference;

/**
 * This class computes the dominance information of a graph using the algorithm
 * described in "A Simple, Fast Dominance Algorithm" by Keith D. Cooper, Timothy
 * J. Harvey, and Ken Kennedy.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class DominatorComputer {

	private final EReference refEdges;

	private final EReference refVertex;

	private final List<Vertex> vertices;

	/**
	 * 
	 * @param graph
	 *            a directed graph
	 * @param root
	 *            the root vertex (entry for dominance, exit for post-dominance)
	 * @param isPost
	 *            <code>true</code> if computing post-dominance information,
	 *            <code>false</code> otherwise
	 */
	public DominatorComputer(Graph graph, Vertex root, boolean isPost) {
		if (isPost) {
			refEdges = GraphPackage.Literals.VERTEX__OUTGOING;
			refVertex = GraphPackage.Literals.EDGE__TARGET;
		} else {
			refEdges = GraphPackage.Literals.VERTEX__INCOMING;
			refVertex = GraphPackage.Literals.EDGE__SOURCE;
		}

		// opposite of source/target is outgoing/incoming
		// opposite of incoming/outgoing is target/source
		Ordering rpo = new ReversePostOrder(graph, refVertex.getEOpposite(),
				refEdges.getEOpposite(), root);
		this.vertices = rpo.getVertices();
	}

	/**
	 * Computes the dominance information.
	 */
	public Map<Vertex, Vertex> computeDominance() {
		// initialize doms
		// 0 is considered as "Undefined"
		// so we start from 1 (hence the "n + 1" allocation)
		int n = vertices.size();
		int[] doms = new int[n + 1];

		// n is the start node by definition of the post-order numbering
		doms[n] = n;

		// update dominance information
		updateDom(doms, n);

		// return the immediate dominator map
		Map<Vertex, Vertex> map = new HashMap<Vertex, Vertex>(doms.length);
		for (int i = 1; i < n; i++) {
			// b is the post-order number of vertex
			Vertex vertex = vertices.get(i);
			int b = vertex.getNumber();
			map.put(vertex, vertices.get(n - doms[b]));
		}
		return map;
	}

	private int intersect(int[] doms, int b1, int b2) {
		int finger1 = b1;
		int finger2 = b2;
		while (finger1 != finger2) {
			while (finger1 < finger2) {
				finger1 = doms[finger1];
			}
			while (finger2 < finger1) {
				finger2 = doms[finger2];
			}
		}
		return finger1;
	}

	private void updateDom(int[] doms, int n) {
		boolean changed = true;
		while (changed) {
			changed = false;
			// skip the first node of vertices (the start node)
			for (int i = 1; i < n; i++) {
				// find the first processed predecessor and set newIdom
				int newIdom = 0;
				Vertex processed = null;
				Vertex vertex = vertices.get(i);

				@SuppressWarnings("unchecked")
				List<Edge> edges = (List<Edge>) vertex.eGet(refEdges);
				for (Edge edge : edges) {
					Vertex pred = (Vertex) edge.eGet(refVertex);
					int p = pred.getNumber();
					if (doms[p] != 0) {
						// pred has already been processed, set newIdom
						processed = pred;
						newIdom = p;
						break;
					}
				}

				// for all predecessors different from processed
				for (Edge edge : edges) {
					Vertex pred = (Vertex) edge.eGet(refVertex);
					if (pred != processed) {
						int p = pred.getNumber();
						if (doms[p] != 0) {
							// i.e., if doms[p] already calculated
							newIdom = intersect(doms, p, newIdom);
						}
					}
				}

				// b is the post-order number of vertex
				int b = vertex.getNumber();
				if (doms[b] != newIdom) {
					doms[b] = newIdom;
					changed = true;
				}
			}
		}
	}

}
