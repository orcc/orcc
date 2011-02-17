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
package net.sf.orcc.network.transformations;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.orcc.OrccException;
import net.sf.orcc.network.Connection;
import net.sf.orcc.network.Instance;
import net.sf.orcc.network.Network;
import net.sf.orcc.network.Vertex;
import net.sf.orcc.network.attributes.StringAttribute;

import org.jgrapht.DirectedGraph;

/**
 * This class defines a transformation that split a given network into many.
 * 
 * @author Damien de Saint Jorre
 * 
 */

public class NetworkSplitter implements INetworkTransformation {

	private Map<String, List<Instance>> instancesTarget;
	private DirectedGraph<String, StringAttribute> mediumGraph;
	private Set<Network> networks;

	public NetworkSplitter(Map<String, List<Instance>> instancesTarget,
			DirectedGraph<String, StringAttribute> mediumGraph) {
		this.instancesTarget = new HashMap<String, List<Instance>>(
				instancesTarget);
		this.mediumGraph = mediumGraph;
		networks = new HashSet<Network>();
	}

	private void addCommAttribute(Network network) {
		DirectedGraph<Vertex, Connection> graph = network.getGraph();
		Set<Vertex> vertexSet = graph.vertexSet();

		for (Vertex vertex : vertexSet) {
			if (vertex.isInstance()) {
				for (Connection connection : graph.edgesOf(vertex)) {
					if (graph.getEdgeSource(connection).isInstance()
							&& graph.getEdgeTarget(connection).isInstance()) {
						Instance sourceInstance = graph.getEdgeSource(
								connection).getInstance();
						Instance targetInstance = graph.getEdgeTarget(
								connection).getInstance();
						String sourceDevice = null;
						String targetDevice = null;

						for (String targetName : instancesTarget.keySet()) {
							List<Instance> instancesList = instancesTarget
									.get(targetName);

							if (instancesList.contains(sourceInstance)) {
								sourceDevice = new String(targetName);
							}
							if (instancesList.contains(targetInstance)) {
								targetDevice = new String(targetName);
							}
						}
						if (!targetDevice.equals(sourceDevice)) {
							StringAttribute mediumValue = mediumGraph.getEdge(
									sourceDevice, targetDevice);
							connection.getAttributes().put("commMedium",
									mediumValue);
							connection.getAttributes().put(
									"commMediumUpperCase",
									new StringAttribute(mediumValue.getValue()
											.toUpperCase()));
						}
					}
				}
			}
		}
	}

	public Set<Network> getNetworks() {
		return networks;
	}

	private void splitNetwork(Network network, String target) { // Network
		// Network newNetwork = new Network(network.getName() + "_" + target);

		// DirectedGraph<Vertex, Connection> newGraph = newNetwork.getGraph();
		DirectedGraph<Vertex, Connection> oldGraph = network.getGraph();

		List<Instance> instancesToRemove = instancesTarget.get(target);
		List<Instance> newInstancesTarget = new ArrayList<Instance>();

		for (Instance instanceToDel : instancesToRemove) {
			Set<Connection> vertexConnections = oldGraph.edgesOf(new Vertex(
					instanceToDel));
			boolean RemoveGhostInstance = true;
			Instance ghostInstance = new Instance("Ghost_"
					+ instanceToDel.getId(), "Ghost");
			Vertex ghostVertex = new Vertex(ghostInstance);

			oldGraph.addVertex(ghostVertex);
			for (Connection connection : vertexConnections) {
				Vertex sourceVertex = oldGraph.getEdgeSource(connection);
				Vertex targetVertex = oldGraph.getEdgeTarget(connection);

				if (sourceVertex.isInstance() && targetVertex.isInstance()) {
					List<Instance> instanceCollection = new ArrayList<Instance>();
					instanceCollection.add(sourceVertex.getInstance());
					instanceCollection.add(targetVertex.getInstance());

					if (instancesToRemove.containsAll(instanceCollection)) {
						oldGraph.removeEdge(connection);
					} else {
						RemoveGhostInstance = false;

						oldGraph.removeEdge(sourceVertex, targetVertex);
						if (sourceVertex.equals(new Vertex(instanceToDel))) {
							oldGraph.addEdge(ghostVertex, targetVertex,
									connection);
						} else {
							oldGraph.addEdge(sourceVertex, ghostVertex,
									connection);
						}
					}
				}
			}
			if (RemoveGhostInstance) {
				oldGraph.removeVertex(ghostVertex);
			} else {
				newInstancesTarget.add(ghostInstance);
			}
			oldGraph.removeVertex(new Vertex(instanceToDel));
		}
		instancesToRemove.clear();
		instancesToRemove.addAll(newInstancesTarget);
	}

	@Override
	public void transform(Network network) throws OrccException {
		Set<String> targetList = new HashSet<String>(instancesTarget.keySet());
		if (targetList.size() > 0) {
			targetList.remove(targetList.toArray()[0]);
		}

		networks.add(network);
		for (String target : targetList) {
			splitNetwork(network, target);
		}

		addCommAttribute(network);
		network.computeTemplateMaps();
	}
}
