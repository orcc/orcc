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
package net.sf.orcc.ir.cfg;

import java.util.List;

import net.sf.dftools.graph.Edge;
import net.sf.dftools.graph.Vertex;
import net.sf.dftools.graph.visit.Ordering;
import net.sf.dftools.graph.visit.ReversePostOrder;
import net.sf.orcc.ir.Cfg;

/**
 * This class computes the dominance information of a CFG using the algorithm
 * described in "A Simple, Fast Dominance Algorithm" by Keith D. Cooper, Timothy
 * J. Harvey, and Ken Kennedy.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class DominatorComputer {

	private int[] doms;

	/**
	 * Computes the dominance information of the given CFG.
	 * 
	 * @param cfg
	 *            a CFG
	 */
	public int[] computeDominanceInformation(Cfg cfg) {
		// compute reverse post-order
		Ordering rpo = new ReversePostOrder(cfg, cfg.getEntry());
		List<Vertex> vertices = rpo.getVertices();

		// initialize doms
		// 0 is considered as "Undefined"
		// so we start from 1 (hence the "n + 1" allocation)
		int n = vertices.size();
		doms = new int[n + 1];

		// n is the start node by definition of the post-order numbering
		doms[n] = n;

		boolean changed = true;
		while (changed) {
			changed = false;
			// skip the first node of vertices (the start node)
			for (int i = 1; i < n; i++) {
				// find the first processed predecessor and set newIdom
				int newIdom = 0;
				Vertex processed = null;
				Vertex vertex = vertices.get(i);

				List<Edge> edges = vertex.getIncoming();
				for (Edge edge : edges) {
					Vertex pred = edge.getSource();
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
					Vertex pred = edge.getSource();
					if (pred != processed) {
						int p = pred.getNumber();
						if (doms[p] != 0) {
							// i.e., if doms[p] already calculated
							newIdom = intersect(p, newIdom);
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

		return doms;
	}

	private int intersect(int b1, int b2) {
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

}
