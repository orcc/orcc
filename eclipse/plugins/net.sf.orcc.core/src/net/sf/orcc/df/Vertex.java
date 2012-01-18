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
package net.sf.orcc.df;

import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.util.EList;

/**
 * This class defines a vertex in a network. A vertex is extended by Port,
 * Instance, Entity.
 * 
 * @author Matthieu Wipliez
 * @model abstract="true"
 */
public interface Vertex extends Nameable {

	/**
	 * Returns the attribute associated with the given name.
	 * 
	 * @param name
	 *            an attribute name
	 * @return the attribute associated with the given name, or if not found,
	 *         <code>null</code>
	 */
	public Attribute getAttribute(String name);

	/**
	 * Returns the list of attributes of this instance.
	 * 
	 * @return the list of attributes of this instance
	 * @model containment="true"
	 */
	public EList<Attribute> getAttributes();

	/**
	 * Returns the path of identifiers from the root container to this vertex.
	 * 
	 * @return the path of identifiers from the root container to this vertex
	 */
	List<String> getHierarchicalId();

	/**
	 * Returns the hierarchical name of this vertex.
	 * 
	 * @return the hierarchical name of this vertex
	 */
	String getHierarchicalName();

	/**
	 * Returns the list of entities from the root container to this vertex.
	 * 
	 * @return the list of entities from the root container to this vertex
	 */
	List<Entity> getHierarchy();

	/**
	 * @model type="Edge" opposite="target"
	 */
	EList<Edge> getIncoming();

	/**
	 * Builds on-the-fly and returns a map from ports to incoming connections.
	 * 
	 * @return a map from ports to incoming connections
	 */
	Map<Port, Connection> getIncomingPortMap();

	/**
	 * @model type="Edge" opposite="source"
	 */
	EList<Edge> getOutgoing();

	/**
	 * Builds on-the-fly and returns a map from ports to outgoing connections.
	 * 
	 * @return a map from ports to outgoing connections
	 */
	Map<Port, List<Connection>> getOutgoingPortMap();

	/**
	 * Builds on-the-fly and returns the list of predecessors of this vertex.
	 * 
	 * @return the list of predecessors of this vertex
	 */
	List<Vertex> getPredecessors();

	/**
	 * Builds the list of successors of this vertex on-the-fly.
	 * 
	 * @return the list of successors of this vertex
	 */
	List<Vertex> getSuccessors();

	/**
	 * Returns an object with template-specific data.
	 * 
	 * @return an object with template-specific data
	 */
	Object getTemplateData();

	/**
	 * Returns <code>true</code> if this vertex is an Entity, and
	 * <code>false</code> otherwise.
	 * 
	 * @return <code>true</code> if this vertex is an Entity
	 */
	boolean isEntity();

	/**
	 * Returns <code>true</code> if this vertex is an instance, and
	 * <code>false</code> otherwise.
	 * 
	 * @return <code>true</code> if this vertex is an instance
	 */
	boolean isInstance();

	/**
	 * Returns <code>true</code> if this vertex is a port.
	 * 
	 * @return <code>true</code> if this vertex is a port
	 */
	boolean isPort();

	/**
	 * Sets the template data associated with this vertex. Template data should
	 * hold data that is specific to a given template.
	 * 
	 * @param templateData
	 *            an object with template-specific data
	 */
	void setTemplateData(Object templateData);

}
