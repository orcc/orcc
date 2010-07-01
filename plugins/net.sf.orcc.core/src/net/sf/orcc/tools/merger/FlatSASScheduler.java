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

package net.sf.orcc.tools.merger;

import java.util.List;
import java.util.Map;

import net.sf.orcc.OrccException;
import net.sf.orcc.network.Connection;
import net.sf.orcc.network.Vertex;

import org.jgrapht.DirectedGraph;

/**
 * This class computes a static schedule from the given network. All instances
 * of the network are assumed to be static (SDF/CSDF). The network classifier is
 * assumed to be computed first.
 * 
 * @author Ghislain Roquier
 * 
 */

public class FlatSASScheduler extends AbstractScheduler {

	public FlatSASScheduler(DirectedGraph<Vertex, Connection> graph)
			throws OrccException {
		super(graph);
	}

	public Schedule schedule() throws OrccException {
		Map<Vertex, Integer> repetitions = new RepetitionVectorAnalyzer(graph)
				.computeRepetitionsVector();

		Schedule schedule = new Schedule();

		List<Vertex> sort = new TopologicalSorter(graph).topologicalSort();
		for (Vertex vertex : sort) {
			if (vertex.isInstance()) {
				Schedule subSched = new Schedule();
				subSched.setIterationCount(repetitions.get(vertex));
				subSched.add(new Iterand(vertex));
				schedule.add(new Iterand(subSched));
			}
		}
		return schedule;
	}

}
