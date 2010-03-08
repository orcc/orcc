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
 *   * Neither the name of the Ecole Polytechnique Fédérale de Lausanne 
 *     nor the names of its contributors may be used to endorse or promote products
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
package net.sf.orcc.backends.cpp.codesign;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import net.sf.orcc.OrccException;
import net.sf.orcc.ir.Port;
import net.sf.orcc.network.Connection;
import net.sf.orcc.network.Network;
import net.sf.orcc.network.Vertex;
import net.sf.orcc.util.OrderedMap;

import org.jgrapht.DirectedGraph;

/**
 * This class defines a transformation that replace input and output ports of
 * the network with a (unique) wrapper instance and appropriate connections.
 * This transformation is only used in the context of the codesign.
 * 
 * @author Ghislain Roquier
 * 
 */

public class WrapperAdder {

	private DirectedGraph<Vertex, Connection> graph;

	/**
	 * 
	 */

	public void transform(Network network) throws OrccException {

		graph = network.getGraph();

		OrderedMap<Port> inputs = network.getInputs();
		OrderedMap<Port> outputs = network.getOutputs();

		if (inputs.size() > 0 || outputs.size() > 0) {

			Wrapper wrapper = new Wrapper(network.getName(), inputs.size(),
					outputs.size());
			Vertex vWrap = new Vertex(wrapper);

			graph.addVertex(vWrap);

			Set<Vertex> vertexToRemove = new HashSet<Vertex>();

			for (Vertex vertex : graph.vertexSet()) {
				if (vertex.isPort()) {
					Port port = vertex.getPort();
					if (outputs.contains(port)) {
						Set<Connection> conns = graph.incomingEdgesOf(vertex);

						// FIX: there should be only one connection since fan-in
						// is not allowed
						for (Connection connection : conns) {

							Port srcPort = connection.getSource();
							// FIX: set the type of the source port to the one
							// of the target
							srcPort.setType(port.getType());
							Port tgtPort = new Port(port);

							Connection incoming = new Connection(srcPort,
									tgtPort, connection.getAttributes());
							Vertex vSrc = graph.getEdgeSource(connection);
							graph.addEdge(vSrc, vWrap, incoming);

							vertexToRemove.add(vertex);
							outputs.remove(port);
						}
					} else {

						Iterator<Connection> it = graph.outgoingEdgesOf(vertex)
								.iterator();

						Connection connection = it.next();
						Port srcPort = new Port(port);
						Port tgtPort = connection.getTarget();
						// FIX: set the type of the target port to the one of
						// the source
						tgtPort.setType(port.getType());
						Vertex vTgt = graph.getEdgeTarget(connection);
						Connection outgoing = new Connection(srcPort, tgtPort,
								connection.getAttributes());
						graph.addEdge(vWrap, vTgt, outgoing);
						vertexToRemove.add(vertex);
						inputs.remove(port);

						while (it.hasNext()) {
							connection = it.next();
							tgtPort = connection.getTarget();
							// FIX: set the type of the target port to the one
							// of the source
							tgtPort.setType(port.getType());
							vTgt = graph.getEdgeTarget(connection);
							Connection newOutgoing = new Connection(srcPort,
									tgtPort, connection.getAttributes());
							graph.addEdge(vWrap, vTgt, newOutgoing);
						}
					}
				} else {
				}
			}

			graph.removeAllVertices(vertexToRemove);
		}
	}

}