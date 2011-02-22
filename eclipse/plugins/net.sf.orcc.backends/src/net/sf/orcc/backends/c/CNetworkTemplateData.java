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
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.orcc.ir.Port;
import net.sf.orcc.network.Connection;
import net.sf.orcc.network.Instance;
import net.sf.orcc.network.Network;
import net.sf.orcc.network.Vertex;
import net.sf.orcc.network.attributes.IAttribute;
import net.sf.orcc.network.attributes.StringAttribute;

import org.jgrapht.DirectedGraph;

/**
 * This class allows the string template accessing informations about
 * application's network
 * 
 * @author Damien de Saint Jorre
 * 
 */
public class CNetworkTemplateData {

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

	/**
	 * This map links instance's port with a StringAttribute. This
	 * StringAttribute contains the medium connection when it exists.
	 */
	private Map<Instance, Map<Port, IAttribute>> portMedium;

	/**
	 * This list contains all instance which will be launched at the beginning
	 * of the program
	 */
	private List<Instance> sourceInstances;

	/**
	 * This map links instance's port with a StringAttribute. This
	 * StringAttribute contains the medium (the string is in capital letters)
	 * connection when it exists.
	 */
	private Map<Instance, Map<Port, IAttribute>> portMediumUpperCase;

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
				Map<Port, IAttribute> instancePorts = new HashMap<Port, IAttribute>();
				Map<Port, IAttribute> instancePortsUpperCase = new HashMap<Port, IAttribute>();

				// For all connections in the instance's input
				for (Connection connection : network.getIncomingMap().get(
						instance)) {
					instancePorts.put(connection.getTarget(),
							connection.getAttribute("commMedium"));
					instancePortsUpperCase.put(connection.getTarget(),
							connection.getAttribute("commMediumUpperCase"));

					// if the instance connected is in an other target
					if (connection.getAttributes().containsKey("commMedium")) {
						connectionToInstance.put(connection, instance);
						connectionToPort
								.put(connection, connection.getTarget());

						StringAttribute connectionAttribute = (StringAttribute) connection
								.getAttribute("commMedium");
						mediumSet.add(connectionAttribute.getValue());
					}
				}

				// For all connections in the instance's output
				for (Connection connection : network.getOutgoingMap().get(
						instance)) {
					instancePorts.put(connection.getSource(),
							connection.getAttribute("commMedium"));
					instancePortsUpperCase.put(connection.getSource(),
							connection.getAttribute("commMediumUpperCase"));

					// if the instance connected is in an other target
					if (connection.getAttributes().containsKey("commMedium")) {
						connectionToInstance.put(connection, instance);
						connectionToPort
								.put(connection, connection.getSource());

						StringAttribute connectionAttribute = (StringAttribute) connection
								.getAttribute("commMedium");
						mediumSet.add(connectionAttribute.getValue());
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
		Collections.sort(allConnections);
		for (Connection connection : allConnections) {
			listMediumInstances.add(connectionToInstance.get(connection));
			listMediumPorts.add(connectionToPort.get(connection));
		}
		listMediumUsedAllInstances.addAll(allMedium);
	}

	/**
	 * Find out all source instances
	 * 
	 * @param network
	 *            network to analyze
	 */
	private void buildSourceInstances(Network network) {
		Set<Instance> instancesAnalysed = new HashSet<Instance>();
		Set<Vertex> graphVertex = network.getGraph().vertexSet();

		for (Vertex vertex : graphVertex) {
			if (vertex.isInstance()) {
				Instance instanceToCheck = vertex.getInstance();
				boolean sourceDetected;

				if (!instancesAnalysed.contains(instanceToCheck)) {
					sourceDetected = checkSourceInstances(instanceToCheck,
							instancesAnalysed, network.getGraph());
					if (!sourceDetected) {
						sourceInstances.add(instanceToCheck);
					}
				}
			}
		}
	}

	/**
	 * Check if an instance is a source Instance and analyze all instances
	 * closed to this one.
	 * 
	 * @param instanceToCheck
	 *            instance which will be analyzed
	 * @param instancesAnalysed
	 *            Set of instances analyzed in order to prevent multiple
	 *            analyzes on instances
	 * @param graph
	 *            connection graph in order to know predecessors and successors
	 *            of each instances
	 * @return
	 */
	private boolean checkSourceInstances(Instance instanceToCheck,
			Set<Instance> instancesAnalysed,
			DirectedGraph<Vertex, Connection> graph) {
		if ((instanceToCheck.isActor() || instanceToCheck.isBroadcast())
				&& !instancesAnalysed.contains(instanceToCheck)) {
			instancesAnalysed.add(instanceToCheck);

			boolean sourceDetected = false;
			Set<Connection> connections;

			connections = graph.incomingEdgesOf(new Vertex(instanceToCheck));
			if (connections.isEmpty()) {
				sourceInstances.add(instanceToCheck);
				sourceDetected = true;
			} else {
				for (Connection connection : connections) {
					Vertex inputVertex = graph.getEdgeSource(connection);

					if (inputVertex.isInstance()) {
						Instance inputInstance = inputVertex.getInstance();
						boolean result = checkSourceInstances(inputInstance,
								instancesAnalysed, graph);
						if (result == true) {
							sourceDetected = true;
						}
					}

				}
			}

			connections = graph.outgoingEdgesOf(new Vertex(instanceToCheck));
			if (!connections.isEmpty()) {
				for (Connection connection : connections) {
					Vertex inputVertex = graph.getEdgeTarget(connection);

					if (inputVertex.isInstance()) {
						Instance outputInstance = inputVertex.getInstance();
						boolean result = checkSourceInstances(outputInstance,
								instancesAnalysed, graph);
						if (result == true) {
							sourceDetected = true;
						}
					}
				}
			}
			return sourceDetected;
		}
		return false;
	}

	/**
	 * build all informations needed in the template data.
	 * 
	 * @param network
	 *            a network
	 */
	public void computeTemplateMaps(Network network) {
		portMedium = new HashMap<Instance, Map<Port, IAttribute>>();
		portMediumUpperCase = new HashMap<Instance, Map<Port, IAttribute>>();
		listMediumInstances = new ArrayList<Instance>();
		listMediumPorts = new ArrayList<Port>();
		listMediumUsed = new HashMap<Instance, List<String>>();
		listMediumUsedAllInstances = new ArrayList<String>();
		sourceInstances = new ArrayList<Instance>();

		buildMediumInfo(network);
		buildSourceInstances(network);
	}

	/**
	 * Return a list of mediums used in the application
	 * 
	 * @return list of mediums' name
	 */
	public List<String> getAllMediumsAllInstances() {
		return listMediumUsedAllInstances;
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

	/**
	 * Returns the map of instance's ports to get the communication medium for
	 * this port
	 * 
	 * @return a map of instance's ports to communication medium
	 */
	public Map<Instance, Map<Port, IAttribute>> getPortMedium() {
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
	public Map<Instance, Map<Port, IAttribute>> getUpperCasePortMedium() {
		return portMediumUpperCase;
	}
}
