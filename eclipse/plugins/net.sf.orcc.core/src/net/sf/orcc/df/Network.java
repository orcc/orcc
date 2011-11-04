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
package net.sf.orcc.df;

import java.util.List;
import java.util.Map;

import net.sf.orcc.OrccException;
import net.sf.orcc.ir.Actor;
import net.sf.orcc.ir.Entity;
import net.sf.orcc.ir.Port;
import net.sf.orcc.ir.Var;
import net.sf.orcc.moc.MoC;

import org.eclipse.emf.common.util.EList;
import org.eclipse.core.resources.IFolder;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.jgrapht.DirectedGraph;

/**
 * This class defines a hierarchical XDF network. It contains several maps so
 * templates can walk through the graph of the network.
 * 
 * @author Matthieu Wipliez
 * @author Herve Yviquel
 * @model extends="Entity"
 */
public interface Network extends Entity {

	/**
	 * Classifies this network.
	 * 
	 * @throws OrccException
	 *             if something goes wrong
	 */
	void classify() throws OrccException;

	/**
	 * Computes the source map and target maps that associate each connection to
	 * its source vertex (respectively target vertex).
	 */
	void computeTemplateMaps();

	/**
	 * Flattens this network.
	 */
	void flatten();

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
	List<Actor> getActors();

	/**
	 * Returns a map that associates each connection to a unique integer.
	 * 
	 * @return a map that associates each connection to a unique integer
	 */
	Map<Connection, Integer> getConnectionMap();

	Map<Connection, Integer> getConnectionMapWithoutBroadcast();

	/**
	 * Returns the list of this graph's connections.
	 * 
	 * @return the list of this graph's connections
	 */
	List<Connection> getConnections();

	/**
	 * Returns a graph representing the network's contents
	 * 
	 * @return a graph representing the network's contents
	 */
	DirectedGraph<Vertex, Connection> getGraph();

	/**
	 * Returns a map that associates each instance to the list of its incoming
	 * edges.
	 * 
	 * @return a map that associates each instance to the list of its incoming
	 *         edges
	 */
	Map<Instance, Map<Port, Connection>> getIncomingMap();

	/**
	 * Returns the input port whose name matches the given name.
	 * 
	 * @param name
	 *            the port name
	 * @return an input port whose name matches the given name
	 */
	Port getInput(String name);

	/**
	 * Returns the list of this network's input ports
	 * 
	 * @return the list of this network's input ports
	 * @model containment="true"
	 */
	EList<Port> getInputs();

	Instance getInstance(String id);

	/**
	 * Returns the list of instances referenced by the graph of this network.
	 * 
	 * @return a list of instances
	 */
	List<Instance> getInstances();

	/**
	 * Returns the list of instances of the given actor in the graph.
	 * 
	 * @param actor
	 *            the actor to get the instance of
	 * 
	 * @return a list of instances
	 */
	List<Instance> getInstancesOf(Actor actor);

	/**
	 * Returns the MoC of the network.
	 * 
	 * @return the network MoC.
	 * @model containment="true"
	 */
	MoC getMoC();

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
	List<Network> getNetworks();

	/**
	 * Returns a map that associates each instance to the list of its outgoing
	 * edges.
	 * 
	 * @return a map that associates each instance to the list of its outgoing
	 *         edges
	 */
	Map<Instance, Map<Port, List<Connection>>> getOutgoingMap();

	/**
	 * Returns the output port whose name matches the given name.
	 * 
	 * @param name
	 *            the port name
	 * @return an output port whose name matches the given name
	 */
	Port getOutput(String name);

	/**
	 * Returns the list of this network's output ports
	 * 
	 * @return the list of this network's output ports
	 * @model containment="true"
	 */
	EList<Port> getOutputs();

	/**
	 * Returns the list of this network's parameters
	 * 
	 * @return the list of this network's parameters
	 * @model containment="true"
	 */
	EList<Var> getParameters();

	/**
	 * Returns a map that associates a port to the list of its predecessors.
	 * 
	 * @return a map that associates a port to the list of its predecessors
	 */
	Map<Instance, Map<Port, Instance>> getPredecessorsMap();

	/**
	 * Returns a map that associates each connection to its source vertex.
	 * 
	 * @return a map that associates each connection to its source vertex
	 */
	Map<Connection, Vertex> getSourceMap();

	/**
	 * Returns a map that associates a port to the list of its successors.
	 * 
	 * @return a map that associates a port to the list of its successors
	 */
	Map<Instance, Map<Port, List<Instance>>> getSuccessorsMap();

	/**
	 * Returns a map that associates each connection to its target vertex.
	 * 
	 * @return a map that associates each connection to its target vertex
	 */
	Map<Connection, Vertex> getTargetMap();

	/**
	 * Returns the list of this network's variables
	 * 
	 * @return the list of this network's variables
	 * @model containment="true"
	 */
	EList<Var> getVariables();

	/**
	 * Walks through the hierarchy, instantiate actors, and checks that
	 * connections actually point to ports defined in actors. Instantiating an
	 * actor implies first loading it and then giving it the right parameters.
	 * 
	 * @param paths
	 *            a list of paths
	 */
	void instantiate(ResourceSet set, List<IFolder> paths);

	/**
	 * Merges actors of this network. Note that for this transformation to work
	 * properly, actors must have been classified and normalized first.
	 * 
	 * @throws OrccException
	 *             if something goes wrong
	 */
	void mergeActors() throws OrccException;

	/**
	 * Normalizes actors of this network so they can later be merged. Note that
	 * for this transformation to work properly, actors must have been
	 * classified first.
	 * 
	 * @throws OrccException
	 *             if something goes wrong
	 */
	void normalizeActors() throws OrccException;

	/**
	 * Sets the MoC of this network.
	 * 
	 * @param moc
	 *            the new MoC of this network
	 */
	void setMoC(MoC moc);

	/**
	 * Computes the hierarchical identifier of each instance.
	 */
	void updateIdentifiers();

}
