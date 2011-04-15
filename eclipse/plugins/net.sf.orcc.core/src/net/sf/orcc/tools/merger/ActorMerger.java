/*
 * Copyright (c) 2010, IETR/INSA of Rennes
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
package net.sf.orcc.tools.merger;

import java.util.Set;

import net.sf.orcc.OrccException;
import net.sf.orcc.network.Connection;
import net.sf.orcc.network.Network;
import net.sf.orcc.network.Vertex;
import net.sf.orcc.network.transformations.INetworkTransformation;

import org.jgrapht.DirectedGraph;
import org.jgrapht.graph.DirectedSubgraph;

/**
 * This class defines a network transformation that merges actors.
 * 
 * 
 * @author Matthieu Wipliez
 * @author Ghislain Roquier
 * 
 */
public class ActorMerger implements INetworkTransformation {

	private DirectedGraph<Vertex, Connection> graph;
	@SuppressWarnings("unused")
	private SASFlatScheduler scheduler;

	@Override
	public void transform(Network network) throws OrccException {
		graph = network.getGraph();

		StaticSubsetDetector detector = new StaticSubsetDetector(network);

		for (Set<Vertex> vertices : detector.staticRegionSets()) {

			DirectedGraph<Vertex, Connection> subgraph = new DirectedSubgraph<Vertex, Connection>(
					graph, vertices, null);

			scheduler = new SASFlatScheduler(subgraph);

			mergeActors(vertices);
		}
	}

	private void mergeActors(Set<Vertex> vertices) {
	}

}
