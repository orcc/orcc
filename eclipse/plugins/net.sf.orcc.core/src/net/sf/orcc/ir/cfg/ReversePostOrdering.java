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

import net.sf.dftools.graph.Edge;
import net.sf.dftools.graph.Vertex;
import net.sf.dftools.graph.visit.Ordering;
import net.sf.orcc.ir.Cfg;

/**
 * This class computes the reverse post-ordering of a CFG. It also labels the
 * nodes in a post-order manner (using a "order" attribute), starting from "1".
 * 
 * @author Matthieu Wipliez
 * 
 */
public class ReversePostOrdering extends Ordering {

	private int num;

	/**
	 * Creates the reverse post-ordering of the given CFG.
	 * 
	 * @param cfg
	 *            a cfg
	 */
	public ReversePostOrdering(Cfg cfg) {
		num = 1;
		visitVertex(cfg.getEntry());
	}

	/**
	 * Builds the search starting from the given vertex.
	 * 
	 * @param v
	 *            a vertex
	 */
	public void visitVertex(Vertex v) {
		visited.add(v);

		for (Edge edge : v.getOutgoing()) {
			Vertex w = edge.getTarget();
			if (visited.contains(w)) {
				// edge goes to a node already visited
				edge.setBackEdge(true);
			} else {
				edge.setBackEdge(false);
				visitVertex(w);
			}
		}

		// add to the beginning (to reverse the post-order)
		vertices.add(0, v);

		// value of "order" is the post-order numbering
		v.setAttribute("order", num);
		num++;
	}

}
