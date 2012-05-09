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
import java.util.Deque;

import net.sf.dftools.graph.Vertex;

/**
 * This class defines Breadth-First Search (BFS) for a graph.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class BFS extends Ordering {

	/**
	 * Builds the list of vertices that can be reached from the given vertex
	 * using breadth-first search.
	 * 
	 * @param vertex
	 *            a vertex
	 */
	public BFS(Vertex vertex) {
		visitVertex(vertex);
	}

	/**
	 * Builds the search starting from the given vertex.
	 * 
	 * @param vertex
	 *            a vertex
	 */
	public void visitVertex(Vertex vertex) {
		Deque<Vertex> visitList = new ArrayDeque<Vertex>();
		visitList.addLast(vertex);

		while (!visitList.isEmpty()) {
			Vertex next = visitList.removeFirst();

			// only adds the successors if they have not been visited yet.
			if (!visited.contains(next)) {
				visited.add(next);
				vertices.add(next);
				for (Vertex succ : next.getSuccessors()) {
					visitList.addLast(succ);
				}
			}
		}
	}

}
