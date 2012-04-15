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

import net.sf.dftools.graph.Vertex;
import net.sf.orcc.moc.MoC;

import org.eclipse.emf.common.util.EList;

/**
 * This class defines an instance. An instance has an id, a class, parameters
 * and attributes. The class of the instance points to an actor or a network.
 * 
 * @author Matthieu Wipliez
 * @model extends="Vertex"
 */
public interface Instance extends Vertex {

	/**
	 * Returns the actor referenced by this instance.
	 * 
	 * @return the actor referenced by this instance, or <code>null</code> if
	 *         this instance does not reference an actor
	 */
	Actor getActor();

	/**
	 * Returns the list of argument of this instance.
	 * 
	 * @return the list of argument of this instance
	 * @model containment="true"
	 */
	public EList<Argument> getArguments();

	/**
	 * Returns the broadcast referenced by this instance.
	 * 
	 * @return the broadcast referenced by this instance, or <code>null</code>
	 *         if this instance does not reference a broadcasst
	 */
	Broadcast getBroadcast();

	/**
	 * Returns the instantiable object referenced by this instance.
	 * 
	 * @model
	 */
	Entity getEntity();

	List<String> getHierarchicalId();

	String getHierarchicalName();

	List<Entity> getHierarchy();

	/**
	 * Returns the identifier of this instance. Delegates to {@link #getName()}.
	 * 
	 * @return the identifier of this instance
	 */
	@Deprecated
	String getId();

	Map<Port, Connection> getIncomingPortMap();

	/**
	 * Returns the classification class of this instance.
	 * 
	 * @return the classification class of this instance
	 */
	MoC getMoC();

	/**
	 * Returns the qualified name of this vertex.
	 * 
	 * @return the qualified name of this vertex
	 * @model derived="true" transient="true" volatile="true"
	 */
	String getName();

	/**
	 * Returns the network referenced by this instance.
	 * 
	 * @return the network referenced by this instance, or <code>null</code> if
	 *         this instance does not reference a network
	 */
	Network getNetwork();

	Map<Port, List<Connection>> getOutgoingPortMap();

	String getPackage();

	String getSimpleName();

	/**
	 * Returns <code>true</code> if this instance references an actor.
	 * 
	 * @return <code>true</code> if this instance references an actor
	 */
	boolean isActor();

	/**
	 * Returns <code>true</code> if this instance references a broadcast.
	 * 
	 * @return <code>true</code> if this instance references a broadcast
	 */
	boolean isBroadcast();

	/**
	 * Returns <code>true</code> if this instance references a network.
	 * 
	 * @return <code>true</code> if this instance references a network
	 */
	boolean isNetwork();

	/**
	 * Sets the entity referenced by this instance.
	 * 
	 * @param entity
	 *            an entity
	 */
	void setEntity(Entity entity);

	/**
	 * Sets the qualified name of this vertex.
	 * 
	 * @param name
	 *            the qualified name of this vertex
	 */
	void setName(String name);

}
