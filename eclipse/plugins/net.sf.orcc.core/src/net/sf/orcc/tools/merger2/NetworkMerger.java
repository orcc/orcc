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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
		//while (staticGraph.hasSinglyConnectedVertex()) {
			Map<Vertex, Integer> vertices = new HashMap<Vertex, Integer>();

			// Get two Instances to merge
			Vertex vertex1 = null;
			Vertex vertex2 = null;
			for (Vertex vertex : staticGraph.getSinglyConnectedVertex()){
				//Find only static successors
				List<Vertex> neighbours = staticGraph.getStaticNeighbors(vertex);
				
				if (!neighbours.isEmpty()){
					// Vertex1 and vertex2 are statics, and well ordered
					vertex1 = vertex;
					vertex2 = neighbours.get(0);
					break;
				}
			}

			if ((vertex1 == null)&& (vertex2 == null)){
				// No static regions found
				return ;
			}			
			
			//Get rate of each vertex
			int sourceRate = staticGraph.getSourceRate(vertex1, vertex2);
			int targetRate = staticGraph.getTargetRate(vertex1, vertex2);
			
			// Create list of instance to merge
			vertices.put(vertex1, sourceRate);
			vertices.put(vertex2, targetRate);

			// Transform network
			Vertex mergedVertex = merger.getEquivalentVertices(vertices);
			staticGraph.mergeVertices(vertices.keySet(), mergedVertex);
		//}
	}

}
