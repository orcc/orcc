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

	private Integer connectionIdMax;

	/**
	 * Contains map of targets' name to a list of instances which will be
	 * launched in.
	 */
	private Map<String, List<Instance>> instancesTarget;

	/**
	 * Graph which informs about communications between targets.
	 */
	private DirectedGraph<String, StringAttribute> mediumGraph;

	/**
	 * map with all targets' name and their networks.
	 */
	private Map<String, Network> mapTargetsNetworks;

	/**
	 * Constructor
	 * 
	 * @param instancesTarget
	 *            Contains map of targets' name to a list of instances which
	 *            will be launched in.
	 * @param mediumGraph
	 *            Contains graph which informs about communications between
	 *            targets.
	 */
	public NetworkSplitter(Map<String, List<Instance>> instancesTarget,
			DirectedGraph<String, StringAttribute> mediumGraph) {
		this.instancesTarget = new HashMap<String, List<Instance>>(
				instancesTarget);
		this.mediumGraph = mediumGraph;
		mapTargetsNetworks = new HashMap<String, Network>();
		connectionIdMax = 0;
	}

	/**
	 * Add attributes about communication medium in all connections contained in
	 * a network.
	 * 
	 * @param network
	 *            network to modify
	 */
	private void addCommAttribute(Network network) {
		DirectedGraph<Vertex, Connection> graph = network.getGraph();
		Set<Vertex> vertexSet = graph.vertexSet();

		for (Vertex vertex : vertexSet) {
			if (vertex.isInstance()) {
				for (Connection connection : graph.edgesOf(vertex)) {
					Vertex sourceVertex = graph.getEdgeSource(connection);
					Vertex targetVertex = graph.getEdgeTarget(connection);

					if (sourceVertex.isInstance() && targetVertex.isInstance()) {
						Instance sourceInstance = sourceVertex.getInstance();
						Instance targetInstance = targetVertex.getInstance();
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
							if (!connection.getAttributes().containsKey(
									"connectionId")) {
								connection.getAttributes()
										.put("connectionId",
												new StringAttribute(connectionIdMax
														.toString()));
								connectionIdMax++;
							}
						}
					}
				}
			}
		}
	}

	/**
	 * Create a new network (and add it to the list of networks) containing all
	 * instances which are launched in <code>target</code>.
	 * 
	 * @param oldNetwork
	 *            network where are instances to remove.
	 * @param target
	 *            target's name.
	 */
	private void addNewNetwork(Network oldNetwork, String target) {
		DirectedGraph<Vertex, Connection> oldGraph = oldNetwork.getGraph();
		Network newNetwork = new Network(oldNetwork.getFile());
		newNetwork.setName(oldNetwork.getName());
		mapTargetsNetworks.put(target, newNetwork);

		DirectedGraph<Vertex, Connection> newGraph = newNetwork.getGraph();

		List<Instance> instancesNewGraph = new ArrayList<Instance>(
				instancesTarget.get(target));
		for (Instance instanceNewGraph : instancesNewGraph) {
			// We don't create a new vertex because this one should be removed
			// in the old graph (it will be done later).
			newGraph.addVertex(new Vertex(instanceNewGraph));
		}

		Set<Instance> allInstances = new HashSet<Instance>();
		for (String targetName : instancesTarget.keySet()) {
			allInstances.addAll(instancesTarget.get(targetName));
		}

		for (Instance instance : allInstances) {
			if (oldGraph.containsVertex(new Vertex(instance))) {
				Set<Connection> vertexConnections = oldGraph
						.edgesOf(new Vertex(instance));
				boolean removeGhostInstance = true;
				Instance ghostInstance = new Instance("Ghost_"
						+ instance.getId(), "Ghost");
				Vertex ghostVertex = new Vertex(ghostInstance);

				newGraph.addVertex(ghostVertex);
				for (Connection connection : vertexConnections) {
					Vertex sourceVertex = oldGraph.getEdgeSource(connection);
					Vertex targetVertex = oldGraph.getEdgeTarget(connection);

					if (sourceVertex.isInstance() && targetVertex.isInstance()) {
						Set<Instance> instanceCollection = new HashSet<Instance>();
						instanceCollection.add(sourceVertex.getInstance());
						instanceCollection.add(targetVertex.getInstance());

						// We don't create a new connection. This connection
						// will be
						// used in two networks, because it's a connection
						// cross-networks :
						//
						// -Network a => Port1 from ActorA ==> connection ==>
						// Port1
						// from GhostActorB
						//
						// -Network b => Port1 from GhostActorA ==> connection
						// ==>
						// Port1 from ActorB
						if (instancesNewGraph.contains(sourceVertex
								.getInstance())
								&& instancesNewGraph.contains(targetVertex
										.getInstance())) {
							newGraph.addEdge(sourceVertex, targetVertex,
									connection);
						} else if (instancesNewGraph.contains(sourceVertex
								.getInstance())) {
							removeGhostInstance = false;
							newGraph.addEdge(sourceVertex, ghostVertex,
									connection);
						} else if (instancesNewGraph.contains(targetVertex
								.getInstance())) {
							removeGhostInstance = false;
							newGraph.addEdge(ghostVertex, targetVertex,
									connection);
						}
					}
				}
				if (removeGhostInstance) {
					newGraph.removeVertex(ghostVertex);
				} else {
					for (String targetName : instancesTarget.keySet()) {
						if (instancesTarget.get(targetName).contains(instance)) {
							instancesTarget.get(targetName).add(ghostInstance);
						}
					}
				}
			}
		}
	}

	/**
	 * Returns all networks constituting the application
	 * 
	 * @return list of networks
	 */
	public Map<String, Network> getNetworksMap() {
		return mapTargetsNetworks;
	}

	/**
	 * Remove from <code>network</code>, all instances which are launched in
	 * target
	 * 
	 * @param network
	 *            network where instances from target have to be removed from
	 * @param target
	 *            target's name.
	 */
	private void removeInstancesTargetInNetwork(Network network, String target) {
		DirectedGraph<Vertex, Connection> oldGraph = network.getGraph();

		List<Instance> instancesToRemove = instancesTarget.get(target);
		List<Instance> instancesTargetForOldGraph = new ArrayList<Instance>();

		for (Instance instanceToDel : instancesToRemove) {
			if (instanceToDel.isActor() || instanceToDel.isBroadcast()) {
				Set<Connection> vertexConnections = oldGraph
						.edgesOf(new Vertex(instanceToDel));
				boolean removeGhostInstance = true;
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
							removeGhostInstance = false;

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
				if (removeGhostInstance) {
					oldGraph.removeVertex(ghostVertex);
				} else {
					instancesTargetForOldGraph.add(ghostInstance);
				}
				oldGraph.removeVertex(new Vertex(instanceToDel));
			}
		}
		instancesToRemove.addAll(instancesTargetForOldGraph);
	}

	/**
	 * Moving all instances which belong to target from network to a new
	 * network.
	 * 
	 * @param network
	 *            origin network containing instances' target
	 * @param target
	 *            target's name
	 */
	private void splitNetwork(Network network, String target) {
		addNewNetwork(network, target);
		removeInstancesTargetInNetwork(network, target);
	}

	/**
	 * Split one networks containing many targets'instances into many networks
	 * containing one target's instances
	 */
	@Override
	public void transform(Network network) throws OrccException {
		List<String> targetList = new ArrayList<String>(
				instancesTarget.keySet());
		if (targetList.size() > 0) {
			mapTargetsNetworks.put(targetList.get(0), network);
			targetList.remove(0);
		}

		for (String target : targetList) {
			splitNetwork(network, target);
		}

		for (Network workingNetwork : mapTargetsNetworks.values()) {
			addCommAttribute(workingNetwork);
			workingNetwork.computeTemplateMaps();
		}
	}
}
