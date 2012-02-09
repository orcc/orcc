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
import net.sf.orcc.df.Edge;
import net.sf.orcc.df.Entity;
import net.sf.orcc.df.Nameable;
import net.sf.orcc.df.Network;
import net.sf.orcc.df.Vertex;
import net.sf.orcc.df.util.DfSwitch;
import net.sf.orcc.ir.ExprVar;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.Use;
import net.sf.orcc.ir.Var;
import net.sf.orcc.ir.util.IrUtil;
import net.sf.orcc.util.EcoreHelper;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.util.EcoreUtil;

/**
 * This class defines a transformation that flattens a given network in-place.
 * The network must have been instantiated first, otherwise this transformation
 * will not flatten anything.
 * 
 * @author Matthieu Wipliez
 * @author Ghislain Roquier
 * @author Herve Yviquel
 * 
 */
public class NetworkFlattener extends DfSwitch<Void> {

	@Override
	public Void caseNetwork(Network network) {
		List<Entity> entities = new ArrayList<Entity>(network.getEntities());
		for (Entity entity : entities) {
			if (entity.isNetwork()) {
				Network subNetwork = (Network) entity;

				// flatten this sub-network
				new NetworkFlattener().doSwitch(subNetwork);

				// remove adapter of subNetwork
				// so that connections are not automatically updated
				Adapter adapter = subNetwork.eAdapters().remove(0);

				moveEntitiesAndConnections(network, subNetwork);
				propagateVariables(subNetwork.getParameters());
				propagateVariables(subNetwork.getVariables());

				linkOutgoingConnections(network, subNetwork);
				linkIncomingConnections(network, subNetwork);

				// remove entity from network
				network.getEntities().remove(entity);

				// restore adapter, remove connections to clean up
				// incoming/outgoing of instances
				subNetwork.eAdapters().add(adapter);
				subNetwork.getConnections().clear();
			}
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
		List<Edge> incomingEdges = new ArrayList<Edge>(subNetwork.getIncoming());
		for (Edge edge : incomingEdges) {
			Connection connection = (Connection) edge;
			List<Edge> outgoingEdges = connection.getTargetPort().getOutgoing();

			for (Edge outgoingEdge : outgoingEdges) {
				Connection outgoingConnection = (Connection) outgoingEdge;
				Connection incoming = DfFactory.eINSTANCE.createConnection(
						edge.getSource(), connection.getSourcePort(),
						outgoingEdge.getTarget(),
						outgoingConnection.getTargetPort(),
						connection.getAttributes());
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
		List<Edge> outgoingEdges = new ArrayList<Edge>(subNetwork.getOutgoing());
		for (Edge edge : outgoingEdges) {
			Connection connection = (Connection) edge;
			List<Edge> incomingEdges = connection.getSourcePort().getIncoming();

			for (Edge incomingEdge : incomingEdges) {
				Connection incomingConnection = (Connection) incomingEdge;
				Connection incoming = DfFactory.eINSTANCE.createConnection(
						incomingEdge.getSource(),
						incomingConnection.getSourcePort(), edge.getTarget(),
						connection.getTargetPort(), connection.getAttributes());
				network.getConnections().add(incoming);
			}
		}
	}

	private void moveEntitiesAndConnections(Network network, Network subNetwork) {
		// Rename subNetwork entities
		for (Nameable nameable : subNetwork.getEntities()) {
			nameable.setName(subNetwork.getName() + "_" + nameable.getName());
		}
		for (Nameable nameable : subNetwork.getInstances()) {
			nameable.setName(subNetwork.getName() + "_" + nameable.getName());
		}

		// move entities in this network
		network.getEntities().addAll(subNetwork.getEntities());
		network.getInstances().addAll(subNetwork.getInstances());

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

	/**
	 * Propagate the values of the given variables to their uses
	 * 
	 * @param variables
	 *            the variables to propagate
	 */
	private void propagateVariables(EList<Var> variables) {
		for (Var var : variables) {
			for (Use use : var.getUses()) {
				Expression oldExpr = EcoreHelper.getContainerOfType(use,
						ExprVar.class);
				Expression initialValue = var.getInitialValue();
				if (initialValue != null) {
					EcoreUtil.replace(oldExpr, IrUtil.copy(initialValue));
				}
			}
		}
	}

}
