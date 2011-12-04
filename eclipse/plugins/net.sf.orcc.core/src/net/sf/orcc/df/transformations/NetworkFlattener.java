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

import net.sf.orcc.df.Connection;
import net.sf.orcc.df.DfFactory;
import net.sf.orcc.df.Entity;
import net.sf.orcc.df.Network;
import net.sf.orcc.df.Vertex;
import net.sf.orcc.df.util.DfSwitch;

/**
 * This class defines a transformation that flattens a given network in-place.
 * The network must have been instantiated first, otherwise this transformation
 * will not flatten anything.
 * 
 * @author Matthieu Wipliez
 * @author Ghislain Roquier
 * 
 */
public class NetworkFlattener extends DfSwitch<Void> {

	public NetworkFlattener() {
	}

	@Override
	public Void caseNetwork(Network network) {
		List<Entity> entities = new ArrayList<Entity>(network.getEntities());
		for (Entity entity : entities) {
			if (entity.isNetwork()) {
				Network subNetwork = (Network) entity;

				// flatten this sub-network
				new NetworkFlattener().doSwitch(subNetwork);

				moveEntitiesAndConnections(network, subNetwork);

				linkOutgoingConnections(network, subNetwork);
				linkIncomingConnections(network, subNetwork);

				// remove entity from network
				network.getEntities().remove(entity);
			}
		}

		// if network is top-level, rename all entities
		if (network.eContainer() == null) {
			new UniqueNameTransformation().doSwitch(network);
		}

		return null;
	}

	/**
	 * Adds edges in the network between predecessors of the sub-network and
	 * successors of its input ports.
	 * 
	 * @param network
	 *            the network
	 * @param subNetwork
	 *            the sub network
	 */
	private void linkIncomingConnections(Network network, Network subNetwork) {
		List<Connection> incomingEdges = new ArrayList<Connection>(
				subNetwork.getIncoming());
		for (Connection edge : incomingEdges) {
			List<Connection> outgoingEdges = edge.getTargetPort().getOutgoing();

			for (Connection outgoingEdge : outgoingEdges) {
				Connection incoming = DfFactory.eINSTANCE.createConnection(
						edge.getSource(), edge.getSourcePort(),
						outgoingEdge.getTarget(), outgoingEdge.getTargetPort(),
						edge.getAttributes());
				network.getConnections().add(incoming);
			}
		}
	}

	/**
	 * Adds edges in the network between successors of the sub-network and
	 * predecessors of its output ports.
	 * 
	 * @param network
	 *            the network
	 * @param subNetwork
	 *            the sub network
	 */
	private void linkOutgoingConnections(Network network, Network subNetwork) {
		List<Connection> outgoingEdges = new ArrayList<Connection>(
				subNetwork.getOutgoing());
		for (Connection edge : outgoingEdges) {
			List<Connection> incomingEdges = edge.getSourcePort().getIncoming();

			for (Connection incomingEdge : incomingEdges) {
				Connection incoming = DfFactory.eINSTANCE.createConnection(
						incomingEdge.getSource(), incomingEdge.getSourcePort(),
						edge.getTarget(), edge.getTargetPort(),
						edge.getAttributes());
				network.getConnections().add(incoming);
			}
		}
	}

	private void moveEntitiesAndConnections(Network network, Network subNetwork) {
		// remove adapter of subNetwork
		// so that connections are not automatically updated
		subNetwork.eAdapters().clear();

		// move entities in this network
		network.getEntities().addAll(subNetwork.getEntities());

		// move connections between entities in this network
		List<Connection> connections = new ArrayList<Connection>();
		for (Connection connection : subNetwork.getConnections()) {
			Vertex source = connection.getSource();
			Vertex target = connection.getTarget();
			if (!source.isPort() && !target.isPort()) {
				connections.add(connection);
			}
		}

		network.getConnections().addAll(connections);
	}

}
