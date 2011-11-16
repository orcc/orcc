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
package net.sf.orcc.df.transformations;

import java.util.ArrayList;
import java.util.List;

import net.sf.orcc.OrccException;
import net.sf.orcc.df.Connection;
import net.sf.orcc.df.DfFactory;
import net.sf.orcc.df.Instance;
import net.sf.orcc.df.Network;
import net.sf.orcc.df.Vertex;

import org.eclipse.emf.ecore.util.EcoreUtil.Copier;

/**
 * This class defines a transformation that flattens a given network in-place.
 * 
 * @author Matthieu Wipliez
 * @author Ghislain Roquier
 * 
 */
public class NetworkFlattener implements INetworkTransformation {

	private Copier copier;

	public NetworkFlattener() {
	}

	/**
	 * Copies all instances and edges between them of subGraph in graph
	 * 
	 * @throws OrccException
	 */
	private void copySubGraph(Network network, Instance instance) {
		Network subNetwork = instance.getNetwork();
		network.getInstances()
				.addAll(copier.copyAll(subNetwork.getInstances()));
		copier.copyReferences();

		for (Connection connection : subNetwork.getConnections()) {
			Vertex source = connection.getSource();
			Vertex target = connection.getTarget();
			if (source.isInstance() && target.isInstance()) {
				Vertex sourceCopy = (Vertex) copier.get(source);
				Vertex targetCopy = (Vertex) copier.get(target);
				Connection copy = DfFactory.eINSTANCE.createConnection(
						sourceCopy, connection.getSourcePort(), targetCopy,
						connection.getTargetPort(),
						copier.copyAll(connection.getAttributes()));
				network.getConnections().add(copy);
			}
		}
	}

	/**
	 * Adds edges in the network between predecessors of the vertex and
	 * successors of the input port.
	 * 
	 * @param network
	 *            the network
	 * @param instance
	 *            the current instance
	 */
	private void linkIncomingConnections(Network network, Instance instance) {
		List<Connection> incomingEdges = new ArrayList<Connection>(
				instance.getIncoming());
		for (Connection edge : incomingEdges) {
			Vertex v = (Vertex) edge.getTargetPort();
			List<Connection> outgoingEdges = v.getOutgoing();

			for (Connection newEdge : outgoingEdges) {
				Vertex target = (Vertex) copier.get(newEdge.getTarget());
				Connection incoming = DfFactory.eINSTANCE.createConnection(
						edge.getSource(), edge.getSourcePort(), target,
						newEdge.getTargetPort(), edge.getAttributes());
				network.getConnections().add(incoming);
			}
		}
	}

	/**
	 * Adds edges in the network between predecessors of the output port and
	 * successors of the vertex.
	 * 
	 * @param network
	 *            the network
	 * @param instance
	 *            the current instance
	 */
	private void linkOutgoingConnections(Network network, Instance instance) {
		List<Connection> outgoingEdges = new ArrayList<Connection>(
				instance.getOutgoing());
		for (Connection edge : outgoingEdges) {
			Vertex v = edge.getSourcePort();
			List<Connection> incomingEdges = v.getIncoming();

			for (Connection newEdge : incomingEdges) {
				Vertex source = (Vertex) copier.get(newEdge.getSource());
				Connection incoming = DfFactory.eINSTANCE.createConnection(
						source, newEdge.getSourcePort(), edge.getTarget(),
						edge.getTargetPort(), edge.getAttributes());
				network.getConnections().add(incoming);
			}
		}
	}

	@Override
	public void transform(Network network) {
		List<Instance> instances = new ArrayList<Instance>(
				network.getInstances());
		for (Instance instance : instances) {
			if (instance.isNetwork()) {
				Network subNetwork = instance.getNetwork();

				// flatten this sub-network
				subNetwork.flatten();

				// copy vertices and edges
				copier = new Copier();
				copySubGraph(network, instance);
				linkOutgoingConnections(network, instance);
				linkIncomingConnections(network, instance);
				copier = null;

				// remove instance from network
				network.getInstances().remove(instance);
			}
		}

		// TODO renaming if necessary
	}

}
