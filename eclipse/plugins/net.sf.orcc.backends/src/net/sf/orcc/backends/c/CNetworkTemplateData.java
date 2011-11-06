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
package net.sf.orcc.backends.c;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.orcc.df.Attribute;
import net.sf.orcc.df.Connection;
import net.sf.orcc.df.DfFactory;
import net.sf.orcc.df.Instance;
import net.sf.orcc.df.Network;
import net.sf.orcc.df.Vertex;
import net.sf.orcc.ir.Port;

import org.jgrapht.DirectedGraph;

/**
 * This class allows the string template accessing informations about
 * application's network
 * 
 * @author Damien de Saint Jorre
 * 
 */
public class CNetworkTemplateData {

	private Map<String, Integer> instanceNameToGroupIdMap;

	private Map<Instance, Integer> instanceToGroupIdMap;

	/**
	 * Contains a list of Id of all connections with an other target.
	 */
	private List<Attribute> listConnectionId;

	/**
	 * Contains a list of instances corresponding to all connections with an
	 * other target.
	 */
	private List<Instance> listMediumInstances;

	/**
	 * Contains a list of ports corresponding to all connections with an other
	 * target.
	 */
	private List<Port> listMediumPorts;

	/**
	 * Map which contains all mediums used for each instance in the network.
	 */
	private Map<Instance, List<String>> listMediumUsed;

	/**
	 * Contains all mediums needed in this network.
	 */
	private List<String> listMediumUsedAllInstances;

	private int numberOfGroups;

	/**
	 * This map links instance's port with a StringAttribute. This
	 * StringAttribute contains the medium connection when it exists.
	 */
	private Map<Instance, Map<Port, Attribute>> portMedium;

	/**
	 * This map links instance's port with a StringAttribute. This
	 * StringAttribute contains the medium (the string is in capital letters)
	 * connection when it exists.
	 */
	private Map<Instance, Map<Port, Attribute>> portMediumUpperCase;

	/**
	 * This list contains all instance which will be launched at the beginning
	 * of the program
	 */
	private List<Instance> sourceInstances;

	private List<Instance> getInstancesRecursively(Network network) {
		List<Instance> instances = new ArrayList<Instance>();
		for (Instance instance : network.getInstances()) {
			if (instance.isNetwork()) {
				instances
						.addAll(getInstancesRecursively(instance.getNetwork()));
			} else {
				instances.add(instance);
			}
		}
		return instances;
	}

	/**
	 * build informations about medium of communication
	 * 
	 * @param network
	 *            a network
	 */
	private void buildMediumInfo(Network network) {
		Map<Connection, Instance> connectionToInstance = new HashMap<Connection, Instance>();
		Map<Connection, Port> connectionToPort = new HashMap<Connection, Port>();
		Set<String> allMedium = new HashSet<String>();

		for (Instance instance : network.getInstances()) {
			if (instance.isActor() || instance.isBroadcast()) {
				Set<String> mediumSet = new HashSet<String>();
				Map<Port, Attribute> instancePorts = new HashMap<Port, Attribute>();
				Map<Port, Attribute> instancePortsUpperCase = new HashMap<Port, Attribute>();

				// For all connections in the instance's input
				for (Connection connection : network.getGraph()
						.incomingEdgesOf(new Vertex(instance))) {
					instancePorts.put(connection.getTargetPort(),
							connection.getAttribute("commMedium"));
					instancePortsUpperCase.put(connection.getTargetPort(),
							connection.getAttribute("commMediumUpperCase"));

					// if the instance connected is in an other target
					if (connection.getAttribute("commMedium") != null) {
						connectionToInstance.put(connection, instance);
						connectionToPort
								.put(connection, connection.getTargetPort());

						// TODO what should we do here?
						// mediumSet.add(connection.getAttribute("commMedium").getValue());
					}
				}

				// For all connections in the instance's output
				for (Connection connection : network.getGraph()
						.outgoingEdgesOf(new Vertex(instance))) {
					instancePorts.put(connection.getSourcePort(),
							connection.getAttribute("commMedium"));
					instancePortsUpperCase.put(connection.getSourcePort(),
							connection.getAttribute("commMediumUpperCase"));

					// if the instance connected is in an other target
					if (connection.getAttribute("commMedium") != null) {
						connectionToInstance.put(connection, instance);
						connectionToPort
								.put(connection, connection.getSourcePort());

						// TODO what should we do here?
						// mediumSet.add(connection.getAttribute("commMedium").getValue());
					}
				}
				allMedium.addAll(mediumSet);

				List<String> mediumList = new ArrayList<String>();
				mediumList.addAll(mediumSet);
				listMediumUsed.put(instance, mediumList);

				portMedium.put(instance, instancePorts);
				portMediumUpperCase.put(instance, instancePortsUpperCase);
			}
		}

		List<Connection> allConnections = new ArrayList<Connection>(
				connectionToInstance.keySet());
		Collections.sort(allConnections, new Comparator<Connection>() {

			@Override
			public int compare(Connection c1, Connection c2) {
				int i1 = c1.getFifoId();
				int i2 = c2.getFifoId();
				return (i1 < i2 ? -1 : (i1 == i2 ? 0 : 1));
			}

		});

		for (Connection connection : allConnections) {
			listMediumInstances.add(connectionToInstance.get(connection));
			listMediumPorts.add(connectionToPort.get(connection));
			listConnectionId.add(connection.getAttribute("connectionId"));
		}
		listMediumUsedAllInstances.addAll(allMedium);
	}

	/**
	 * Find all instances which belong to instance's network
	 * 
	 * @param instance
	 *            instance which will be analyzed
	 * @param graph
	 *            connection graph in order to know predecessors and successors
	 *            of each instances
	 * @return list of all instances linked together
	 */
	private Set<Instance> findLinkedInstances(Instance instance,
			DirectedGraph<Vertex, Connection> graph) {
		Set<Instance> linkedInstances = new HashSet<Instance>();
		List<Instance> instanceListToCheck = new ArrayList<Instance>();
		boolean instanceListEmpty = false;

		instanceListToCheck.add(instance);

		while (!instanceListEmpty) {
			Instance instanceToCheck = instanceListToCheck.get(0);
			instanceListToCheck.remove(0);
			linkedInstances.add(instanceToCheck);

			Vertex vertexToCheck = new Vertex(instanceToCheck);
			for (Connection connection : graph.edgesOf(vertexToCheck)) {
				Vertex connectedVertex = graph.getEdgeSource(connection);

				if (connectedVertex.equals(vertexToCheck)) {
					connectedVertex = graph.getEdgeTarget(connection);
				}
				if (connectedVertex.isInstance()) {
					Instance connectedInstance = connectedVertex.getInstance();
					if (!linkedInstances.contains(connectedInstance)) {
						instanceListToCheck.add(connectedInstance);
					}
				}
			}
			instanceListEmpty = instanceListToCheck.isEmpty();
		}
		return linkedInstances;
	}

	/**
	 * Find out all source instances
	 * 
	 * @param network
	 *            network to analyze
	 */
	private void buildSourceInstances(Network network) {
		Set<Vertex> graphVertex = network.getGraph().vertexSet();
		DirectedGraph<Vertex, Connection> graph = network.getGraph();
		List<Set<Instance>> networksNotConnected = new ArrayList<Set<Instance>>();

		// Searching for all non-connected networks
		for (Vertex vertex : graphVertex) {
			if (vertex.isInstance()) {
				Instance instanceToCheck = vertex.getInstance();

				if (networksNotConnected.isEmpty()) {
					networksNotConnected.add(findLinkedInstances(
							instanceToCheck, network.getGraph()));
				} else {
					boolean instanceAlreadyAnalysed = false;

					for (Set<Instance> networkOfInstances : networksNotConnected) {
						if (networkOfInstances.contains(instanceToCheck)) {
							instanceAlreadyAnalysed = true;
						}
					}
					if (!instanceAlreadyAnalysed) {
						networksNotConnected.add(findLinkedInstances(
								instanceToCheck, network.getGraph()));
					}
				}
			}
		}

		// Searching for source instance(s) for each network
		for (Set<Instance> instances : networksNotConnected) {
			boolean sourceDetected = false;

			for (Instance instance : instances) {
				if (graph.inDegreeOf(new Vertex(instance)) == 0) {
					sourceInstances.add(instance);
					sourceDetected = true;
				}
			}
			// if there isn't any "source" instance (instance without
			// predecessors) in this network
			if (!sourceDetected) {
				// we will launch random instance for this network.
				sourceInstances.add(instances.iterator().next());
			}
		}
	}

	public void computeHierarchicalTemplateMaps(Network network) {
		instanceToGroupIdMap = new HashMap<Instance, Integer>();
		numberOfGroups = 0;
		Instance instance = DfFactory.eINSTANCE.createInstance("network",
				network);
		recursiveGroupsComputation(instance, 2);
	}

	/**
	 * build all informations needed in the template data.
	 * 
	 * @param network
	 *            a network
	 */
	public void computeTemplateMaps(Network network) {
		portMedium = new HashMap<Instance, Map<Port, Attribute>>();
		portMediumUpperCase = new HashMap<Instance, Map<Port, Attribute>>();
		listMediumInstances = new ArrayList<Instance>();
		listMediumPorts = new ArrayList<Port>();
		listMediumUsed = new HashMap<Instance, List<String>>();
		listConnectionId = new ArrayList<Attribute>();
		listMediumUsedAllInstances = new ArrayList<String>();
		sourceInstances = new ArrayList<Instance>();
		instanceNameToGroupIdMap = new HashMap<String, Integer>();

		buildMediumInfo(network);
		buildSourceInstances(network);
		transferMap();
	}

	/**
	 * Return a list of mediums used in the application
	 * 
	 * @return list of mediums' name
	 */
	public List<String> getAllMediumsAllInstances() {
		return listMediumUsedAllInstances;
	}

	public Map<String, Integer> getInstanceNameToGroupIdMap() {
		return instanceNameToGroupIdMap;
	}

	public Map<Instance, Integer> getInstanceToGroupIdMap() {
		return instanceToGroupIdMap;
	}

	/**
	 * Return a map which inform about all medium used in each instance.
	 * 
	 * @return map of instance to a list of medium's name
	 */
	public Map<Instance, List<String>> getListAllMedium() {
		return listMediumUsed;
	}

	/**
	 * Returns the list of connectionId corresponding to instance connected to
	 * the sorted connections set
	 * 
	 * @return a list of instances
	 */
	public List<Attribute> getListConnectionId() {
		return listConnectionId;
	}

	/**
	 * Returns the list of instances corresponding to instance connected to the
	 * sorted connections set
	 * 
	 * @return a list of instances
	 */
	public List<Instance> getListMediumInstances() {
		return listMediumInstances;
	}

	/**
	 * Returns the list of ports corresponding to port connected to the sorted
	 * connections set
	 * 
	 * @return a list of ports
	 */
	public List<Port> getListMediumPorts() {
		return listMediumPorts;
	}

	public int getNumberOfGroups() {
		return numberOfGroups;
	}

	/**
	 * Returns the map of instance's ports to get the communication medium for
	 * this port
	 * 
	 * @return a map of instance's ports to communication medium
	 */
	public Map<Instance, Map<Port, Attribute>> getPortMedium() {
		return portMedium;
	}

	/**
	 * Returns a list which contains all the instances to launch at the
	 * beginning of the program
	 * 
	 * @return the list of instances
	 */
	public List<Instance> getSourceInstances() {
		return sourceInstances;
	}

	/**
	 * Returns the map of instance's ports to get the communication medium for
	 * this port
	 * 
	 * @return a map of instance's ports to communication medium (the
	 *         corresponding string is composed by capital letters)
	 */
	public Map<Instance, Map<Port, Attribute>> getUpperCasePortMedium() {
		return portMediumUpperCase;
	}

	private boolean isLastedHierarchy(Network network) {
		for (Instance instance : network.getInstances()) {
			if (instance.isNetwork()) {
				return false;
			}
		}
		return true;
	}

	private boolean isNthLastedHierarchy(Network network, int n) {
		if (n == 1) {
			return isLastedHierarchy(network);
		}
		for (Instance instance : network.getInstances()) {
			if (instance.isNetwork()) {
				if (!isNthLastedHierarchy(instance.getNetwork(), n - 1)) {
					return false;
				}
			}
		}
		return true;
	}

	private void recursiveGroupsComputation(Instance instance,
			int hierarchyLevel) {
		if (instance.isNetwork()) {
			Network network = instance.getNetwork();
			if (isNthLastedHierarchy(network, hierarchyLevel)) {
				for (Instance subInstance : getInstancesRecursively(network)) {
					instanceToGroupIdMap.put(subInstance, numberOfGroups);
				}
				numberOfGroups++;
			} else {
				for (Instance subInstance : network.getInstances()) {
					recursiveGroupsComputation(subInstance, hierarchyLevel);
				}
			}
		} else {
			instanceToGroupIdMap.put(instance, numberOfGroups);
			numberOfGroups++;
		}
	}

	public void transferMap() {
		for (Map.Entry<Instance, Integer> entry : instanceToGroupIdMap
				.entrySet()) {
			instanceNameToGroupIdMap.put(entry.getKey().getId(),
					entry.getValue());
		}
	}
}
