/*
 * Copyright (c) 2009, IETR/INSA of Rennes
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
package net.sf.orcc.network.transforms;

import net.sf.orcc.OrccException;
import net.sf.orcc.common.Port;
import net.sf.orcc.ir.type.IType;
import net.sf.orcc.network.Connection;
import net.sf.orcc.network.Instance;
import net.sf.orcc.network.Network;
import net.sf.orcc.network.Vertex;

import org.jgrapht.DirectedGraph;

/**
 * This class defines a network transformation that instantiates a network.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class Instantiator implements INetworkTransformation {

	private DirectedGraph<Vertex, Connection> graph;

	/**
	 * Walks through the hierarchy, instantiate actors, and checks that
	 * connections actually point to ports defined in actors. Instantiating an
	 * actor implies first loading it and then giving it the right parameters.
	 * 
	 * @throws OrccException
	 *             if an actor could not be instantiated, or a connection is
	 *             wrong
	 */
	public void transform(Network network) throws OrccException {
		graph = network.getGraph();
		for (Vertex vertex : graph.vertexSet()) {
			if (vertex.isInstance()) {
				Instance instance = vertex.getInstance();
				if (instance.isNetwork()) {
					// instantiate the child network
					instance.getNetwork().instantiate();
				} else if (!instance.isBroadcast()) {
					// instantiate the child actor
					instance.instantiate();
				}
			}
		}

		updateConnections();
	}

	/**
	 * Updates the given connection's source and target port by getting the
	 * ports from the source and target instances, after checking the ports
	 * exist and have compatible types.
	 * 
	 * @param connection
	 *            a connection
	 * @throws OrccException
	 */
	private void updateConnection(Connection connection) throws OrccException {
		Vertex srcVertex = graph.getEdgeSource(connection);
		Vertex tgtVertex = graph.getEdgeTarget(connection);

		if (srcVertex.isInstance() && tgtVertex.isInstance()) {
			Instance source = srcVertex.getInstance();
			Instance target = tgtVertex.getInstance();

			String srcPortName = connection.getSource().getName();
			Port srcPort = source.getActor().getOutput(srcPortName);

			String dstPortName = connection.getTarget().getName();
			Port dstPort = target.getActor().getInput(dstPortName);

			// check ports exist
			if (srcPort == null) {
				throw new OrccException("A Connection refers to "
						+ "a non-existent source port: \"" + srcPortName + "\"");
			} else if (dstPort == null) {
				throw new OrccException("A Connection refers to "
						+ "a non-existent target port: \"" + dstPortName + "\"");
			}

			// check port types match
			IType srcType = srcPort.getType();
			IType dstType = dstPort.getType();
			if (!srcType.equals(dstType)) {
				throw new OrccException("Type error: " + source.getActor()
						+ "." + srcPort + " is " + srcType + ", "
						+ target.getActor() + "." + dstPort + " is " + dstType);
			}

			// update connection
			connection.setSource(srcPort);
			connection.setTarget(dstPort);
		}
	}

	/**
	 * Updates the connections of this network. Must be called after actors have
	 * been instantiated.
	 * 
	 * @throws OrccException
	 *             if a connection could not be updated because it references a
	 *             port that does not exist or have source and target ports that
	 *             have incompatible types
	 */
	private void updateConnections() throws OrccException {
		for (Connection connection : graph.edgeSet()) {
			updateConnection(connection);
		}
	}

}
