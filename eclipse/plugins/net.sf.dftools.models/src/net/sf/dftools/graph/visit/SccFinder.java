/*
 * Copyright (c) 2012, Synflow
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
package net.sf.dftools.graph.visit;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.dftools.graph.Edge;
import net.sf.dftools.graph.Graph;
import net.sf.dftools.graph.Vertex;

/**
 * This class implements Tarjan's strongly connected components algorithm.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class SccFinder {

	private int index;

	private final Map<Vertex, Integer> indexMap;

	private final Map<Vertex, Integer> lowlinkMap;

	private List<List<Vertex>> sccs;

	private final Deque<Vertex> stack;

	public SccFinder() {
		indexMap = new HashMap<Vertex, Integer>();
		lowlinkMap = new HashMap<Vertex, Integer>();
		stack = new ArrayDeque<Vertex>();
	}

	public List<List<Vertex>> visitGraph(Graph graph) {
		sccs = new ArrayList<List<Vertex>>();
		index = 0;
		for (Vertex vertex : graph.getVertices()) {
			if (!indexMap.containsKey(vertex)) {
				strongConnect(vertex);
			}
		}

		return sccs;
	}

	private void strongConnect(Vertex v) {
		// Set the depth index for v to the smallest unused index
		indexMap.put(v, index);
		lowlinkMap.put(v, index);
		index++;
		stack.push(v);

		// Consider successors of v
		for (Edge edge : v.getOutgoing()) {
			Vertex w = edge.getTarget();
			if (!indexMap.containsKey(w)) {
				// Successor w has not yet been visited; recurse on it
				strongConnect(w);
				lowlinkMap.put(v,
						Math.min(lowlinkMap.get(v), lowlinkMap.get(w)));
			} else if (stack.contains(w)) {
				// Successor w is in stack S and hence in the current SCC
				lowlinkMap.put(v, Math.min(lowlinkMap.get(v), indexMap.get(w)));
			}
		}

		// If v is a root node, pop the stack and generate an SCC
		if (lowlinkMap.get(v) == indexMap.get(v)) {
			List<Vertex> scc = new ArrayList<Vertex>();
			Vertex w;
			do {
				w = stack.pop();
				scc.add(w);
			} while (w != v);
			sccs.add(scc);
		}
	}

}
