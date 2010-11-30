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
package net.sf.orcc.network;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import net.sf.orcc.OrccException;
import net.sf.orcc.ir.Actor;
import net.sf.orcc.ir.GlobalVariable;
import net.sf.orcc.ir.Port;
import net.sf.orcc.moc.MoC;
import net.sf.orcc.network.transformations.Instantiator;
import net.sf.orcc.network.transformations.NetworkClassifier;
import net.sf.orcc.network.transformations.NetworkFlattener;
import net.sf.orcc.network.transformations.SolveParametersTransform;
import net.sf.orcc.tools.merger.ActorMerger;
import net.sf.orcc.tools.normalizer.ActorNormalizer;
import net.sf.orcc.util.OrderedMap;
import net.sf.orcc.util.Scope;

import org.jgrapht.DirectedGraph;
import org.jgrapht.graph.DirectedMultigraph;

/**
 * This class defines a hierarchical XDF network. It contains several maps so
 * templates can walk through the graph of the network.
 * 
 * @author Matthieu Wipliez
 * @author Herve Yviquel
 * 
 */
public class Network {

	private static Map<String, Actor> actorPool = new HashMap<String, Actor>();

	/**
	 * Clears the actor pool. Should be called after a network has been
	 * instantiated.
	 */
	public static void clearActorPool() {
		actorPool.clear();
	}

	/**
	 * Returns the actor from the pool that has the given class, or
	 * <code>null</code> if there is not.
	 * 
	 * @param actorClass
	 *            the actor class name
	 * @return the actor from the pool that has the given class, or
	 *         <code>null</code> if there is not
	 */
	public static Actor getActorFromPool(String actorClass) {
		return actorPool.get(actorClass);
	}

	/**
	 * Puts the given actor in the pool that has the given class.
	 * 
	 * @param actorClass
	 *            the actor class name
	 * @param actor
	 *            the actor
	 */
	public static void putActorInPool(String actorClass, Actor actor) {
		actorPool.put(actorClass, actor);
	}

	private Map<Connection, Integer> connectionMap;

	private String file;

	private DirectedGraph<Vertex, Connection> graph;

	private Map<Instance, List<Connection>> incomingMap;

	private OrderedMap<String, Port> inputs;

	/**
	 * the class of this network. Initialized to unknown.
	 */
	private MoC moc;

	private String name;

	private Map<Instance, Map<Port, Integer>> numberOfReadersMap;

	private Map<Instance, List<Connection>> outgoingMap;

	private OrderedMap<String, Port> outputs;

	private Scope<String, GlobalVariable> parameters;

	private Map<Instance, Map<Port, Integer>> portToFifoSizeMap;

	private Map<Instance, Map<Port, Instance>> predecessorsMap;

	private Map<Connection, Vertex> sourceMap;

	private Map<Instance, Map<Port, Instance>> successorsMap;

	private Map<Connection, Vertex> targetMap;

	private OrderedMap<String, GlobalVariable> variables;

	/**
	 * Creates a new network.
	 */
	public Network(String file) {
		this.file = file;
		graph = new DirectedMultigraph<Vertex, Connection>(Connection.class);
		inputs = new OrderedMap<String, Port>();
		outputs = new OrderedMap<String, Port>();
		parameters = new Scope<String, GlobalVariable>();
		variables = new Scope<String, GlobalVariable>(parameters, false);
	}

	/**
	 * Classifies this network.
	 * 
	 * @throws OrccException
	 *             if something goes wrong
	 */
	public void classify() throws OrccException {
		new NetworkClassifier().transform(this);
	}

	private void computeIncomingOutgoingMaps() {
		incomingMap = new HashMap<Instance, List<Connection>>();
		outgoingMap = new HashMap<Instance, List<Connection>>();
		for (Vertex vertex : graph.vertexSet()) {
			if (vertex.isInstance()) {
				// incoming edges
				Set<Connection> connections = graph.incomingEdgesOf(vertex);
				List<Connection> incoming = new ArrayList<Connection>(
						connections);
				incomingMap.put(vertex.getInstance(), incoming);

				// outgoing edges
				connections = graph.outgoingEdgesOf(vertex);
				List<Connection> outgoing = new ArrayList<Connection>(
						connections);
				outgoingMap.put(vertex.getInstance(), outgoing);
			}
		}
	}

	private void computePortInformationMaps() {
		numberOfReadersMap = new HashMap<Instance, Map<Port, Integer>>();
		for (Instance instance : outgoingMap.keySet()) {
			Map<Port, Integer> portToNumberOfReadersMap = new HashMap<Port, Integer>();
			for (Connection connection : outgoingMap.get(instance)) {
				Port srcPort = connection.getSource();
				if (portToNumberOfReadersMap.get(srcPort) == null) {
					portToNumberOfReadersMap.put(srcPort, 1);
				} else {
					int n = portToNumberOfReadersMap.get(srcPort);
					n++;
					portToNumberOfReadersMap.remove(srcPort);
					portToNumberOfReadersMap.put(srcPort, n);
				}
			}
			numberOfReadersMap.put(instance, portToNumberOfReadersMap);
		}

		portToFifoSizeMap = new HashMap<Instance, Map<Port, Integer>>();
		for (Instance instance : getIncomingMap().keySet()) {
			Map<Port, Integer> portToFifoSize = new HashMap<Port, Integer>();
			for (Connection connection : getIncomingMap().get(instance)) {
				Port trgtPort = connection.getTarget();
				Integer fifoSize = connection.getSize();
				if (fifoSize != null) {
					portToFifoSize.put(trgtPort, fifoSize);
				}
			}
			portToFifoSizeMap.put(instance, portToFifoSize);
		}
	}

	private void computePredecessorsSuccessorsMaps() {
		predecessorsMap = new HashMap<Instance, Map<Port, Instance>>();
		successorsMap = new HashMap<Instance, Map<Port, Instance>>();

		// for each instance
		for (Vertex vertex : graph.vertexSet()) {
			if (vertex.isInstance()) {
				Instance instance = vertex.getInstance();
				if (instance.isActor()) {
					Actor actor = instance.getActor();
					computePredSucc(vertex, actor.getInputs(),
							actor.getOutputs());
				} else if (instance.isBroadcast()) {
					Broadcast bcast = instance.getBroadcast();
					computePredSucc(vertex, bcast.getInputs(),
							bcast.getOutputs());
				}
			}
		}
	}

	private void computePredSucc(Vertex vertex,
			OrderedMap<String, Port> inputs, OrderedMap<String, Port> outputs) {
		Map<Port, Instance> map = new LinkedHashMap<Port, Instance>();
		predecessorsMap.put(vertex.getInstance(), map);
		Set<Connection> incoming = graph.incomingEdgesOf(vertex);
		for (Port port : inputs) {
			for (Connection connection : incoming) {
				if (port.equals(connection.getTarget())) {
					map.put(port, graph.getEdgeSource(connection).getInstance());
				}
			}
		}

		map = new LinkedHashMap<Port, Instance>();
		successorsMap.put(vertex.getInstance(), map);
		Set<Connection> outgoing = graph.outgoingEdgesOf(vertex);
		for (Port port : outputs) {
			for (Connection connection : outgoing) {
				if (port.equals(connection.getSource())) {
					map.put(port, graph.getEdgeTarget(connection).getInstance());
				}
			}
		}
	}

	/**
	 * Computes the source map and target maps that associate each connection to
	 * its source vertex (respectively target vertex).
	 */
	public void computeTemplateMaps() {
		sourceMap = new HashMap<Connection, Vertex>();
		for (Connection connection : graph.edgeSet()) {
			sourceMap.put(connection, graph.getEdgeSource(connection));
		}

		targetMap = new HashMap<Connection, Vertex>();
		for (Connection connection : graph.edgeSet()) {
			targetMap.put(connection, graph.getEdgeTarget(connection));
		}

		connectionMap = new HashMap<Connection, Integer>();
		int i = 0;
		for (Connection connection : graph.edgeSet()) {
			connectionMap.put(connection, i++);
		}

		computeIncomingOutgoingMaps();

		computePredecessorsSuccessorsMaps();

		computePortInformationMaps();
	}

	/**
	 * Flattens this network.
	 */
	public void flatten() {
		new SolveParametersTransform().transform(this);
		new NetworkFlattener().transform(this);
	}

	/**
	 * Returns the list of actors referenced by the graph of this network. This
	 * is different from the list of instances of this network: There are
	 * typically more instances than there are actors, because an actor may be
	 * instantiated several times.
	 * 
	 * <p>
	 * The list is computed on the fly by adding all the actors referenced in a
	 * set.
	 * </p>
	 * 
	 * @return a list of actors
	 */
	public List<Actor> getActors() {
		Set<Actor> actors = new TreeSet<Actor>();
		for (Vertex vertex : getGraph().vertexSet()) {
			if (vertex.isInstance()) {
				Instance instance = vertex.getInstance();
				if (instance.isActor()) {
					Actor actor = instance.getActor();
					actors.add(actor);
				} else if (instance.isNetwork()) {
					Network network = instance.getNetwork();
					actors.addAll(network.getActors());
				}
			}
		}

		return new ArrayList<Actor>(actors);
	}

	/**
	 * Returns a map that associates each connection to a unique integer.
	 * 
	 * @return a map that associates each connection to a unique integer
	 */
	public Map<Connection, Integer> getConnectionMap() {
		return connectionMap;
	}

	/**
	 * Returns the list of this graph's connections.
	 * 
	 * @return the list of this graph's connections
	 */
	public List<Connection> getConnections() {
		return Arrays.asList(graph.edgeSet().toArray(new Connection[0]));
	}

	/**
	 * Returns the XDF file this network was declared in.
	 * 
	 * @return the XDF file this network was declared in
	 */
	public String getFile() {
		return file;
	}

	/**
	 * Returns a graph representing the network's contents
	 * 
	 * @return a graph representing the network's contents
	 */
	public DirectedGraph<Vertex, Connection> getGraph() {
		return graph;
	}

	/**
	 * Returns a map that associates each instance to the list of its incoming
	 * edges.
	 * 
	 * @return a map that associates each instance to the list of its incoming
	 *         edges
	 */
	public Map<Instance, List<Connection>> getIncomingMap() {
		return incomingMap;
	}

	/**
	 * Returns the input port whose name matches the given name.
	 * 
	 * @param name
	 *            the port name
	 * @return an input port whose name matches the given name
	 */
	public Port getInput(String name) {
		return inputs.get(name);
	}

	/**
	 * Returns the list of this network's input ports
	 * 
	 * @return the list of this network's input ports
	 */
	public OrderedMap<String, Port> getInputs() {
		return inputs;
	}

	/**
	 * Returns the list of instances referenced by the graph of this network.
	 * 
	 * @return a list of instances
	 */
	public List<Instance> getInstances() {
		List<Instance> instances = new ArrayList<Instance>();
		for (Vertex vertex : getGraph().vertexSet()) {
			if (vertex.isInstance()) {
				Instance instance = vertex.getInstance();
				instances.add(instance);
			}
		}

		return instances;
	}

	public MoC getMoC() {
		return moc;
	}

	/**
	 * Returns the name of this network
	 * 
	 * @return the name of this network
	 */
	public String getName() {
		return name;
	}

	/**
	 * Returns the list of networks referenced by the graph of this network.
	 * This is different from the list of instances of this network: There are
	 * typically more instances than there are networks, because a network may
	 * be instantiated several times.
	 * 
	 * <p>
	 * The list is computed on the fly by adding all the networks referenced in
	 * a set.
	 * </p>
	 * 
	 * @return a list of networks
	 */
	public List<Network> getNetworks() {
		Set<Network> networks = new HashSet<Network>();
		for (Vertex vertex : getGraph().vertexSet()) {
			if (vertex.isInstance()) {
				Instance instance = vertex.getInstance();
				if (instance.isNetwork()) {
					Network network = instance.getNetwork();
					networks.add(network);
					networks.addAll(network.getNetworks());
				}
			}
		}

		return Arrays.asList(networks.toArray(new Network[0]));
	}

	/**
	 * Returns a map that associates each output port to the number of readers.
	 * 
	 * @return a map that associates each output port to the number of readers
	 */
	public Map<Instance, Map<Port, Integer>> getNumberOfReadersMap() {
		return numberOfReadersMap;
	}

	/**
	 * Returns a map that associates each instance to the list of its outgoing
	 * edges.
	 * 
	 * @return a map that associates each instance to the list of its outgoing
	 *         edges
	 */
	public Map<Instance, List<Connection>> getOutgoingMap() {
		return outgoingMap;
	}

	/**
	 * Returns the output port whose name matches the given name.
	 * 
	 * @param name
	 *            the port name
	 * @return an output port whose name matches the given name
	 */
	public Port getOutput(String name) {
		return outputs.get(name);
	}

	/**
	 * Returns the list of this network's output ports
	 * 
	 * @return the list of this network's output ports
	 */
	public OrderedMap<String, Port> getOutputs() {
		return outputs;
	}

	/**
	 * Returns the list of this network's parameters
	 * 
	 * @return the list of this network's parameters
	 */
	public OrderedMap<String, GlobalVariable> getParameters() {
		return parameters;
	}

	/**
	 * Returns a map that associates each input port to the local specified size
	 * of its FIFO.
	 * 
	 * @return a map that associates each input port to the local specified size
	 *         of its FIFO
	 */
	public Map<Instance, Map<Port, Integer>> getPortToFifoSizeMap() {
		return portToFifoSizeMap;
	}

	/**
	 * Returns a map that associates a port to the list of its predecessors.
	 * 
	 * @return a map that associates a port to the list of its predecessors
	 */
	public Map<Instance, Map<Port, Instance>> getPredecessorsMap() {
		return predecessorsMap;
	}

	/**
	 * Returns a map that associates each connection to its source vertex.
	 * 
	 * @return a map that associates each connection to its source vertex
	 */
	public Map<Connection, Vertex> getSourceMap() {
		return sourceMap;
	}

	/**
	 * Returns a map that associates a port to the list of its successors.
	 * 
	 * @return a map that associates a port to the list of its successors
	 */
	public Map<Instance, Map<Port, Instance>> getSuccessorsMap() {
		return successorsMap;
	}

	/**
	 * Returns a map that associates each connection to its target vertex.
	 * 
	 * @return a map that associates each connection to its target vertex
	 */
	public Map<Connection, Vertex> getTargetMap() {
		return targetMap;
	}

	/**
	 * Returns the list of this network's variables
	 * 
	 * @return the list of this network's variables
	 */
	public OrderedMap<String, GlobalVariable> getVariables() {
		return variables;
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
	public void instantiate(String path) throws OrccException {
		new Instantiator(path).transform(this);
	}

	/**
	 * Merges actors of this network. Note that for this transformation to work
	 * properly, actors must have been classified and normalized first.
	 * 
	 * @throws OrccException
	 *             if something goes wrong
	 */
	public void mergeActors() throws OrccException {
		new ActorMerger().transform(this);
	}

	/**
	 * Normalizes actors of this network so they can later be merged. Note that
	 * for this transformation to work properly, actors must have been
	 * classified first.
	 * 
	 * @throws OrccException
	 *             if something goes wrong
	 */
	public void normalizeActors() throws OrccException {
		for (Actor actor : getActors()){
			new ActorNormalizer().transform(actor);
		}
	}

	/**
	 * Sets the MoC of this network.
	 * 
	 * @param moc
	 *            the new MoC of this network
	 */
	public void setMoC(MoC moc) {
		this.moc = moc;
	}

	/**
	 * Sets the name of this network
	 * 
	 * @param name
	 *            the new name of this network
	 */
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return name;
	}

	/**
	 * Computes the hierarchical identifier of each instance.
	 */
	public void updateIdentifiers() {
		List<String> identifiers = new ArrayList<String>(1);
		identifiers.add(name);
		updateIdentifiers(identifiers);
	}

	private void updateIdentifiers(List<String> identifiers) {
		for (Instance instance : getInstances()) {
			instance.getHierarchicalId().addAll(0, identifiers);
			if (instance.isNetwork()) {
				List<String> subNetworkId = new ArrayList<String>(identifiers);
				subNetworkId.add(instance.getId());
				instance.getNetwork().updateIdentifiers(subNetworkId);
			}
		}
	}

}
