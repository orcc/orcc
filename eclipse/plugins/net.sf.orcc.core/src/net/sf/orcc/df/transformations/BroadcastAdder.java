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
import java.util.List;
import java.util.Map;

import net.sf.dftools.graph.Edge;
import net.sf.dftools.graph.Vertex;
import net.sf.orcc.df.Broadcast;
import net.sf.orcc.df.Connection;
import net.sf.orcc.df.DfFactory;
import net.sf.orcc.df.Entity;
import net.sf.orcc.df.Instance;
import net.sf.orcc.df.Network;
import net.sf.orcc.df.Port;
import net.sf.orcc.df.util.DfSwitch;

import org.eclipse.emf.ecore.util.EcoreUtil;

/**
 * Adds broadcast actors when needed.
 * 
 * @author Matthieu Wipliez
 * @author Herve Yviquel
 * 
 */
public class BroadcastAdder extends DfSwitch<Void> {

	private Network network;

	@Override
	public Void caseEntity(Entity entity) {
		handle(entity.getName(), entity.getOutgoingPortMap());
		return null;
	}

	@Override
	public Void caseInstance(Instance instance) {
		handle(instance.getName(), instance.getOutgoingPortMap());
		return null;
	}

	@Override
	public Void caseNetwork(Network network) {
		this.network = network;
		// make a copy of the existing vertex set because the set returned is
		// modified when broadcasts are added
		List<Vertex> vertexSet = new ArrayList<Vertex>(network.getVertices());

		for (Vertex vertex : vertexSet) {
			if (vertex instanceof Entity) {
				Entity entity = (Entity) vertex;
				if (entity.isNetwork()) {
					new BroadcastAdder().doSwitch(entity);
				}
				caseVertex(vertex);
			} else {
				doSwitch(vertex);
			}
		}
		return null;
	}

	@Override
	public Void casePort(Port port) {
		List<Edge> connections = new ArrayList<Edge>(port.getOutgoing());
		if (connections.size() > 1) {
			createBroadcast(network.getName(), port, connections);
		}
		return null;
	}

	protected void createBroadcast(String id, Port port,
			List<? extends Edge> outList) {
		// Add broadcast vertex
		Broadcast bcast = DfFactory.eINSTANCE.createBroadcast(outList.size(),
				port.getType());
		bcast.setName(id + "_" + port.getName());
		network.add(bcast);

		// Creates a connection between the vertex and the broadcast
		Connection conn = (Connection) outList.get(0);
		Connection incoming = DfFactory.eINSTANCE.createConnection(
				conn.getSource(), conn.getSourcePort(), bcast,
				bcast.getInput(), EcoreUtil.copyAll(conn.getAttributes()));
		network.getConnections().add(incoming);

		// Change the source of the other connections
		int i = 0;
		for (Edge edge : outList) {
			Port outputPort = bcast.getOutput("output_" + i);
			i++;
			Connection connection = (Connection) edge;
			connection.setSourcePort(outputPort);
			connection.setSource(bcast);
		}
	}

	private void handle(String name, Map<Port, List<Connection>> outMap) {
		for (Port srcPort : outMap.keySet()) {
			List<Connection> outList = outMap.get(srcPort);
			if (outList.size() > 1) {
				createBroadcast(name, srcPort, outList);
			}
		}
	}

}
