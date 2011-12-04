/*
 * Copyright (c) 2009-2011, IETR/INSA of Rennes
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
package net.sf.orcc.df.transformations;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import net.sf.orcc.df.Attribute;
import net.sf.orcc.df.Broadcast;
import net.sf.orcc.df.Connection;
import net.sf.orcc.df.DfFactory;
import net.sf.orcc.df.Entity;
import net.sf.orcc.df.Network;
import net.sf.orcc.df.Port;
import net.sf.orcc.df.Vertex;
import net.sf.orcc.df.util.DfSwitch;

import org.eclipse.emf.ecore.util.EcoreUtil;

import com.google.common.collect.Lists;

/**
 * Adds broadcast actors when needed.
 * 
 * @author Matthieu Wipliez
 * @author Herve Yviquel
 * 
 */
public class BroadcastAdder extends DfSwitch<Void> {

	private Network network;

	protected List<Connection> toBeRemoved;

	@Override
	public Void caseNetwork(Network network) {
		this.network = network;
		toBeRemoved = new ArrayList<Connection>();

		// make a copy of the existing vertex set because the set returned is
		// modified when broadcasts are added
		List<Vertex> vertexSet = Lists.newArrayList(network.getVertices());

		for (Vertex vertex : vertexSet) {
			if (vertex.isEntity()) {
				Entity entity = (Entity) vertex;
				if (entity.isNetwork()) {
					new BroadcastAdder().doSwitch(entity);
				}
				caseVertex(vertex);
			} else {
				doSwitch(vertex);
			}
		}

		// removes old connections
		network.getConnections().removeAll(toBeRemoved);

		return null;
	}

	protected void createBroadcast(String id, Port port,
			List<Connection> outList) {
		// add broadcast vertex
		Broadcast bcast = DfFactory.eINSTANCE.createBroadcast(outList.size(),
				port.getType());
		bcast.setName(id + "_" + port.getName());
		network.getEntities().add(bcast);

		// add connections
		Connection conn = outList.get(0);
		Vertex vertex = conn.getSource();
		Port sourcePort = conn.getSourcePort();
		Collection<Attribute> attributes = EcoreUtil.copyAll(conn
				.getAttributes());

		// creates a connection between the vertex and the broadcast
		Connection incoming = DfFactory.eINSTANCE.createConnection(vertex,
				sourcePort, bcast, bcast.getInput(), attributes);
		network.getConnections().add(incoming);

		createOutgoingConnections(bcast, outList);
	}

	/**
	 * Creates a connection between the broadcast and the target for each
	 * outgoing connection of vertex.
	 * 
	 * @param vertexBCast
	 * @param outList
	 */
	private void createOutgoingConnections(Broadcast bcast,
			List<Connection> outList) {
		int i = 0;
		for (Connection connection : outList) {
			// new connection
			Vertex target = connection.getTarget();
			Port outputPort = bcast.getOutput("output_" + i);
			i++;

			Connection connBcastTarget = DfFactory.eINSTANCE.createConnection(
					bcast, outputPort, target, connection.getTargetPort(),
					connection.getAttributes());
			network.getConnections().add(connBcastTarget);

			// setting source to null so we don't examine it again
			connection.setSourcePort(null);

			// add this connection to the set of connections that are to be
			// removed
			toBeRemoved.add(connection);
		}
	}

	@Override
	public Void casePort(Port port) {
		List<Connection> connections = new ArrayList<Connection>(
				port.getOutgoing());
		if (connections.size() > 1) {
			createBroadcast(network.getName(), port, connections);
		}
		return (Void) new Object();
	}

	@Override
	public Void caseVertex(Vertex vertex) {
		List<Connection> connections = new ArrayList<Connection>(
				vertex.getOutgoing());
		Map<Port, List<Connection>> outMap = vertex.getOutgoingPortMap();
		for (Connection connection : connections) {
			Port srcPort = connection.getSourcePort();
			if (srcPort != null) {
				List<Connection> outList = outMap.get(srcPort);
				int numOutput = outList.size();
				if (numOutput > 1) {
					createBroadcast(vertex.getName(), srcPort, outList);
				}
			}
		}

		return null;
	}

}
