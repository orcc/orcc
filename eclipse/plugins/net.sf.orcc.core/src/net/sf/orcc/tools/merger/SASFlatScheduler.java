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

import net.sf.orcc.network.Connection;
import net.sf.orcc.network.Vertex;

import org.jgrapht.DirectedGraph;
import org.jgrapht.traverse.TopologicalOrderIterator;

/**
 * This class computes a flat single appearance schedule (SAS) from the given
 * SDF graph.
 * 
 * @author Ghislain Roquier
 * 
 */
public class SASFlatScheduler extends AbstractScheduler {

	public SASFlatScheduler(DirectedGraph<Vertex, Connection> graph) {
		super(graph);
	}

	@Override
	public void schedule() {
		schedule = new Schedule();

		schedule.setIterationCount(1);
		TopologicalOrderIterator<Vertex, Connection> it = new TopologicalOrderIterator<Vertex, Connection>(
				graph);

		while (it.hasNext()) {
			Vertex vertex = it.next();

			if (vertex.isInstance()) {
				int rep = repetitionsVector.get(vertex);
				Iterand iterand = null;
				for (int i = 0; i < rep; i++) {
					iterand = new Iterand(vertex);
					schedule.add(iterand);
				}
			}
		}
	}

}
