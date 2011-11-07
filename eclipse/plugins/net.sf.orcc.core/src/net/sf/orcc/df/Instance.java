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

import org.eclipse.emf.common.util.EList;
import java.util.List;
import java.util.Map;

import net.sf.orcc.ir.Expression;
import net.sf.orcc.moc.MoC;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.ecore.EObject;

/**
 * This class defines an instance. An instance has an id, a class, parameters
 * and attributes. The class of the instance points to an actor or a network.
 * 
 * @author Matthieu Wipliez
 * @model extends="Vertex"
 */
public interface Instance extends Vertex {

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
	 * Returns the map of attributes contained in this object.
	 * 
	 * @return the map of attributes contained in this object
	 * @model containment="true"
	 */
	public EList<Attribute> getAttributes();

	/**
	 * Returns the classification class of the instance.
	 * 
	 * @return the classification class of this instance
	 */
	MoC getMoC();
	
	/**
	 * Returns the actor referenced by this instance.
	 * 
	 * @return the actor referenced by this instance, or <code>null</code> if
	 *         this instance does not reference an actor
	 */
	Actor getActor();

	/**
	 * Returns <code>true</code> if this instance references an actor.
	 * 
	 * @return <code>true</code> if this instance references an actor
	 */
	boolean isActor();

	/**
	 * Returns <code>true</code> if this instance references a network.
	 * 
	 * @return <code>true</code> if this instance references a network
	 */
	boolean isNetwork();

	/**
	 * Returns <code>true</code> if this instance references a broadcast.
	 * 
	 * @return <code>true</code> if this instance references a broadcast
	 */
	boolean isBroadcast();

	/**
	 * Returns <code>true</code> if this instance references a wrapper.
	 * 
	 * @return <code>true</code> if this instance references a wrapper
	 */
	boolean isWrapper();

	/**
	 * Returns the broadcast referenced by this instance.
	 * 
	 * @return the broadcast referenced by this instance, or <code>null</code>
	 *         if this instance does not reference a broadcasst
	 */
	Broadcast getBroadcast();

	/**
	 * Returns the class of this instance.
	 * 
	 * @return the class of this instance
	 */
	String getClasz();

	/**
	 * Returns the contents of this instance.
	 * 
	 * @model
	 */
	EObject getContents();

	/**
	 * Returns the file in which this instance is defined. This file is only
	 * valid for instances that refer to actors and were instantiated.
	 * 
	 * @return the file in which this instance is defined
	 */
	IFile getFile();

	/**
	 * Returns the path of classes from the top-level to this instance.
	 * 
	 * @return the path of classes from the top-level to this instance
	 */
	List<String> getHierarchicalClass();

	/**
	 * Returns the path of identifiers from the top-level to this instance.
	 * 
	 * @return the path of identifiers from the top-level to this instance
	 */
	List<String> getHierarchicalId();

	/**
	 * Returns the path of identifiers from the top-level to this instance as a
	 * path of the form /top/network/.../instance.
	 * 
	 * @return the path of identifiers from the top-level to this instance as a
	 *         path of the form /top/network/.../instance
	 */
	String getHierarchicalPath();

	/**
	 * Returns the identifier of this instance.
	 * 
	 * @return the identifier of this instance
	 * @model
	 */
	String getId();

	/**
	 * Returns the network referenced by this instance.
	 * 
	 * @return the network referenced by this instance, or <code>null</code> if
	 *         this instance does not reference a network
	 */
	Network getNetwork();

	/**
	 * Returns the parameters of this instance. This is a reference, not a copy.
	 * 
	 * @return the parameters of this instance
	 */
	Map<String, Expression> getParameters();

	/**
	 * Returns the wrapper referenced by this instance.
	 * 
	 * @return the wrapper referenced by this instance, or <code>null</code> if
	 *         this instance does not reference a wrapper
	 */
	SerDes getWrapper();

	/**
	 * Sets the contents of this instance to be that of an actor. Removes any
	 * prior contents.
	 * 
	 * @param actor
	 *            an actor
	 */
	void setContents(EObject contents);

	/**
	 * Changes the identifier of this instance.
	 * 
	 * @param id
	 *            a new identifier
	 */
	public void setId(String id);

}
