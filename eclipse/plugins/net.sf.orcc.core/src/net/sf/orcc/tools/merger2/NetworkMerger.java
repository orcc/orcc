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
package net.sf.orcc.tools.merger2;

import java.util.ArrayList;
import java.util.List;

import net.sf.orcc.OrccException;
import net.sf.orcc.network.Network;
import net.sf.orcc.network.Vertex;
import net.sf.orcc.network.transformations.INetworkTransformation;

/**
 * This class defines a network transformation that merges static actors in a
 * network.
 * 
 * 
 * @author Jérôme Gorin
 * 
 */
public class NetworkMerger implements INetworkTransformation {

	@Override
	public void transform(Network network) throws OrccException {

		if (!network.hasMoc()) {
			// Actors of the network has not been classified
			network.classify();
		}

		StaticGraphAnalyzer staticGraph = new StaticGraphAnalyzer(network);
		InstanceMerger merger = new InstanceMerger(network);

		// Merger simple cases
		while (staticGraph.hasSinglyConnectedVertex()) {
			List<Vertex> vertices = new ArrayList<Vertex>();

			// Get an Instance to merge
			Vertex vertex1 = staticGraph.getSinglyConnectedVertex().get(0);
			Vertex vertex2 = staticGraph.getStaticNeighbors(vertex1).get(0);

			// Create list of instance to merge
			vertices.add(vertex1);
			vertices.add(vertex2);

			// Transform network
			Vertex mergedVertex = merger.getEquivalentVertices(vertices);
			staticGraph.mergeVertices(vertices, mergedVertex);
		}
	}

}
