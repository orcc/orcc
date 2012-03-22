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

import net.sf.dftools.graph.Edge;
import net.sf.dftools.graph.Graph;
import net.sf.orcc.OrccException;
import net.sf.orcc.ir.Var;
import net.sf.orcc.moc.MoC;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.common.util.EList;

/**
 * This class defines a hierarchical XDF network. It extends both entity and
 * graph.
 * 
 * @author Matthieu Wipliez
 * @author Herve Yviquel
 * @model extends="Entity"
 */
public interface Network extends Entity, Graph {

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
	 * Returns the list of actors referenced by or contained in this network,
	 * and its sub-networks. This is different from the list of instances of
	 * this network: There are typically more instances than there are actors,
	 * because an actor may be instantiated several times.
	 * 
	 * <p>
	 * The list is computed on the fly by adding all the actors referenced in a
	 * set.
	 * </p>
	 * 
	 * @return a list of actors
	 */
	List<Actor> getAllActors();

	/**
	 * Returns the list of networks referenced by or contained in this network,
	 * and its sub-networks. This is different from the list of instances of
	 * this network: There are typically more instances than there are networks,
	 * because a network may be instantiated several times.
	 * 
	 * <p>
	 * The list is computed on the fly by adding all the networks referenced in
	 * a set.
	 * </p>
	 * 
	 * @return a list of networks
	 */
	List<Network> getAllNetworks();

	/**
	 * Returns the list of this graph's connections. This returns the same as
	 * {@link #getEdges()} but as a list of {@link Connection}s rather than as a
	 * list of {@link Edge}s.
	 * 
	 * @return the list of this graph's connections
	 */
	EList<Connection> getConnections();

	/**
	 * Returns the list of entities contained in this network.
	 * 
	 * @return the list of entities contained in this network
	 * @model containment="true"
	 */
	EList<Entity> getEntities();

	/**
	 * Returns the value of the '<em><b>Inputs</b></em>' containment reference list.
	 * The list contents are of type {@link net.sf.orcc.df.Port}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Inputs</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Inputs</em>' containment reference list.
	 * @see net.sf.orcc.df.DfPackage#getNetwork_Inputs()
	 * @model containment="true"
	 * @generated
	 */
	EList<Port> getInputs();

	/**
	 * Returns the value of the '<em><b>Outputs</b></em>' containment reference list.
	 * The list contents are of type {@link net.sf.orcc.df.Port}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Outputs</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Outputs</em>' containment reference list.
	 * @see net.sf.orcc.df.DfPackage#getNetwork_Outputs()
	 * @model containment="true"
	 * @generated
	 */
	EList<Port> getOutputs();

	/**
	 * Returns the file this network is defined in.
	 * 
	 * @return the file this network is defined in
	 */
	IFile getFile();

	/**
	 * Returns the name of the file this network is defined in.
	 * 
	 * @return the name of the file this network is defined in
	 * @model
	 */
	String getFileName();

	Instance getInstance(String id);

	/**
	 * Returns the list of instances referenced by the graph of this network.
	 * 
	 * @return a list of instances
	 * @model containment="true"
	 */
	EList<Instance> getInstances();

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
	 * Returns the variable with the given name.
	 * 
	 * @param name
	 *            name of a variable
	 * @return the variable with the given name
	 */
	Var getVariable(String name);

	/**
	 * Returns the list of this network's variables
	 * 
	 * @return the list of this network's variables
	 * @model containment="true"
	 */
	EList<Var> getVariables();

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
	 * Sets the name of the file in which this entity is defined.
	 * 
	 * @param fileName
	 *            name of the file in which this entity is defined
	 */
	void setFileName(String fileName);

	/**
	 * Sets the MoC of this network.
	 * 
	 * @param moc
	 *            the new MoC of this network
	 */
	void setMoC(MoC moc);

}
