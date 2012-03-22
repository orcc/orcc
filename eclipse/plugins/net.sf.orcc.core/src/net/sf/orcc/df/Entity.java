/*
 * Copyright (c) 2011, IETR/INSA of Rennes
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

import net.sf.dftools.graph.Vertex;
import net.sf.orcc.ir.Var;

import org.eclipse.emf.common.util.EList;

/**
 * This interface defines an entity that can be instantiated by an Instance, or
 * contained in a Network.
 * 
 * @model abstract="true" extends="Vertex"
 * @generated
 */
public interface Entity extends Vertex {

	List<String> getHierarchicalId();

	String getHierarchicalName();

	List<Entity> getHierarchy();

	Map<Port, Connection> getIncomingPortMap();

	/**
	 * Returns the input port whose name matches the given name.
	 * 
	 * @param name
	 *            the port name
	 * @return an input port whose name matches the given name
	 */
	Port getInput(String name);

	/**
	 * Returns the list of this entity's input ports
	 * 
	 * @return the list of this entity's input ports
	 * @model containment="true"
	 */
	EList<Port> getInputs();

	/**
	 * Returns the qualified name of this vertex.
	 * 
	 * @return the qualified name of this vertex
	 * @model
	 */
	String getName();

	Map<Port, List<Connection>> getOutgoingPortMap();

	/**
	 * Returns the output port whose name matches the given name.
	 * 
	 * @param name
	 *            the port name
	 * @return an output port whose name matches the given name
	 */
	Port getOutput(String name);

	/**
	 * Returns the list of this entity's output ports
	 * 
	 * @return the list of this entity's output ports
	 * @model containment="true"
	 */
	EList<Port> getOutputs();

	String getPackage();

	/**
	 * Returns the parameter with the given name.
	 * 
	 * @param name
	 *            name of a parameter
	 * @return the parameter with the given name
	 */
	Var getParameter(String name);

	/**
	 * Returns the list of this entity's parameters
	 * 
	 * @return the list of this entity's parameters
	 * @model containment="true"
	 */
	EList<Var> getParameters();

	/**
	 * Returns the port whose name matches the given name.
	 * 
	 * @param name
	 *            the port name
	 * @return a port whose name matches the given name
	 */
	Port getPort(String name);

	String getSimpleName();

	Object getTemplateData();

	/**
	 * Returns <code>true</code> if this entity is an actor.
	 * 
	 * @return <code>true</code> if this entity is an actor
	 */
	boolean isActor();

	/**
	 * Returns <code>true</code> if this entity is a broadcast.
	 * 
	 * @return <code>true</code> if this entity is a broadcast
	 */
	boolean isBroadcast();

	/**
	 * Returns <code>true</code> if this entity is a network.
	 * 
	 * @return <code>true</code> if this entity is a network
	 */
	boolean isNetwork();

	/**
	 * Sets the qualified name of this vertex.
	 * 
	 * @param name
	 *            the qualified name of this vertex
	 */
	void setName(String name);

	void setTemplateData(Object templateData);

}
