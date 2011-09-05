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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.orcc.OrccException;
import net.sf.orcc.OrccRuntimeException;
import net.sf.orcc.ir.Actor;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.Port;
import net.sf.orcc.ir.Type;
import net.sf.orcc.ir.Var;
import net.sf.orcc.network.Connection;
import net.sf.orcc.network.Instance;
import net.sf.orcc.network.Network;
import net.sf.orcc.network.Vertex;

import org.eclipse.core.resources.IFolder;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.jgrapht.DirectedGraph;

/**
 * This class defines a network transformation that instantiates a network.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class Instantiator implements INetworkTransformation {

	private DirectedGraph<Vertex, Connection> graph;

	private Network network;

	private List<IFolder> paths;

	private ResourceSet set;

	/**
	 * Creates a new instantiator that will look up actors in the given path.
	 * 
	 * @param paths
	 *            the paths where actors should be looked up
	 */
	public Instantiator(ResourceSet set, List<IFolder> paths) {
		this.set = set;
		this.paths = paths;
	}

	/**
	 * Checks that the parameters passed to instances match the parameters
	 * declared by their refinment (network or actor).
	 * 
	 * @param network
	 *            the network
	 */
	private void checkParameters(Network network) {
		for (Instance instance : network.getInstances()) {
			List<Var> parameters;
			if (instance.isActor()) {
				parameters = instance.getActor().getParameters();
			} else {
				parameters = instance.getNetwork().getParameters().getList();
			}

			// check all parameters declared have a value
			Map<String, Expression> values = instance.getParameters();
			Map<String, Var> paramsMap = new HashMap<String, Var>();
			for (Var parameter : parameters) {
				String name = parameter.getName();
				Expression value = values.get(name);
				if (value == null) {
					throw new OrccRuntimeException("In network \""
							+ network.getName() + "\": Instance "
							+ instance.getId() + " has no value for parameter "
							+ name);
				}

				paramsMap.put(name, parameter);
			}

			// check that the values all reference a parameter
			for (String name : values.keySet()) {
				Var parameter = paramsMap.get(name);
				if (parameter == null) {
					throw new OrccRuntimeException("In network \""
							+ network.getName() + "\": Instance "
							+ instance.getId()
							+ " is given a value for a parameter " + name
							+ " that is not declared.");
				}
			}
		}
	}

	private void checkPorts(String id, Set<Connection> connections,
			List<Port> ports) throws OrccException {
		for (Port port : ports) {
			boolean portUsed = false;
			for (Connection connection : connections) {
				if (connection.getSource() == port
						|| connection.getTarget() == port) {
					portUsed = true;
					break;
				}
			}

			if (!port.isNative() && !portUsed) {
				throw new OrccException("In network \"" + network.getName()
						+ "\": port \"" + port.getName() + "\" of instance \""
						+ id + "\" is not used!");
			}
		}
	}

	private void checkPortsAreConnected() throws OrccException {
		for (Vertex vertex : graph.vertexSet()) {
			if (vertex.isPort()) {
				Port port = vertex.getPort();
				if (graph.outDegreeOf(vertex) == 0
						&& graph.inDegreeOf(vertex) == 0) {
					throw new OrccException("In network \"" + network.getName()
							+ "\": port \"" + port.getName()
							+ "\" is not used!");
				}
			} else {
				Instance instance = vertex.getInstance();
				String id = instance.getId();
				if (instance.isNetwork()) {
					Network network = instance.getNetwork();

					Set<Connection> connections = graph.incomingEdgesOf(vertex);
					checkPorts(id, connections, network.getInputs());
					connections = graph.outgoingEdgesOf(vertex);
					checkPorts(id, connections, network.getOutputs());
				} else {
					Actor actor = instance.getActor();

					Set<Connection> connections = graph.incomingEdgesOf(vertex);
					checkPorts(id, connections, actor.getInputs());
					connections = graph.outgoingEdgesOf(vertex);
					checkPorts(id, connections, actor.getOutputs());
				}
			}
		}
	}

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
		this.network = network;
		graph = network.getGraph();

		for (Vertex vertex : graph.vertexSet()) {
			if (vertex.isInstance()) {
				Instance instance = vertex.getInstance();
				if (instance.isNetwork()) {
					// instantiate the child network
					instance.getNetwork().instantiate(set, paths);
				} else {
					// at this point there are only actors and networks, so if
					// it is not a network it's an actor: instantiate it
					instance.instantiate(set, paths);
				}
			}
		}

		checkParameters(network);
		updateConnections();
		checkPortsAreConnected();
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

		Port srcPort;
		Type srcPortType;
		String sourceString;
		if (srcVertex.isInstance()) {
			Instance source = srcVertex.getInstance();
			String srcPortName = connection.getSource().getName();

			if (source.isActor()) {
				srcPort = source.getActor().getOutput(srcPortName);
			} else {
				srcPort = source.getNetwork().getOutput(srcPortName);
			}

			if (srcPort == null) {
				throw new OrccException("In network \"" + network.getName()
						+ "\": A Connection refers to "
						+ "a non-existent source port: \"" + srcPortName
						+ "\" of instance \"" + source.getId() + "\"");
			}

			connection.setSource(srcPort);

			srcPortType = srcPort.getType();
			sourceString = srcPort.getName() + " of " + source.getId();
		} else {
			srcPort = srcVertex.getPort();
			srcPortType = srcPort.getType();
			sourceString = srcPort.getName();
		}

		Port dstPort;
		Type dstPortType;
		String targetString;
		if (tgtVertex.isInstance()) {
			Instance target = tgtVertex.getInstance();
			String dstPortName = connection.getTarget().getName();

			if (target.isActor()) {
				dstPort = target.getActor().getInput(dstPortName);
			} else {
				dstPort = target.getNetwork().getInput(dstPortName);
			}

			// check ports exist
			if (dstPort == null) {
				throw new OrccException("In network \"" + network.getName()
						+ "\": A Connection refers to "
						+ "a non-existent target port: \"" + dstPortName
						+ "\" of instance \"" + target.getId() + "\"");
			}

			connection.setTarget(dstPort);

			dstPortType = dstPort.getType();
			targetString = dstPort.getName() + " of " + target.getId();
		} else {
			dstPort = tgtVertex.getPort();
			dstPortType = dstPort.getType();
			targetString = dstPort.getName();
		}

		// check port nativity match
		if (srcPort.isNative() != dstPort.isNative()) {
			throw new OrccException("Error: the nativity of port "
					+ sourceString + " doesn't match with the one of port "
					+ targetString);
		}

		// check port types match
		Type srcType = srcPortType;
		Type dstType = dstPortType;
		if (!EcoreUtil.equals(srcType, dstType)) {
			throw new OrccException("Type error: port " + sourceString + " is "
					+ srcType + ", port " + targetString + " is " + dstType);
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
