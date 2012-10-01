/*
 * Copyright (c) 2012, Synflow
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

import org.eclipse.emf.ecore.resource.Resource;

/**
 * This interface defines how to resolve references to entities. A reference is
 * the qualified name (x.y.z) of an entity. Several resolvers can be registered
 * with the {@link net.sf.orcc.df.util.XdfParser} class. See
 * {@link net.sf.orcc.df.impl.DefaultEntityResolverImpl} for the default
 * implementation.
 * 
 * @author Matthieu Wipliez
 * 
 */
public interface EntityResolver {

	/**
	 * Initializes this resolver with the given resource, which is to be used as
	 * a clue when resolving entities. This method is called exactly once when a
	 * .xdf resource is loaded (and before any call to resolve is made).
	 * 
	 * @param resource
	 *            an EMF resource
	 */
	void initialize(Resource resource);

	/**
	 * Finds the file that defines the entity whose qualified name matches the
	 * given name. If found, this method updates the instance's entity attribute
	 * and return <code>true</code>. An implementation of this interface is free
	 * to set up a proxy or create a concrete entity.
	 * 
	 * <p>
	 * The instance given as a parameter to this method is already contained in
	 * a network, which is always guaranteed to be associated with an EMF
	 * Resource.
	 * </p>
	 * 
	 * @param instance
	 *            an instance
	 * @param className
	 *            qualified class name of the entity
	 * @return <code>true</code> if this resolver could resolve the given class
	 *         name
	 */
	boolean resolve(Instance instance, String className);

	/**
	 * Sets the class name of the given network based on the URI of the Resource
	 * that was given to {@link #initialize(Resource)}.
	 * 
	 * @param network
	 *            network whose class name
	 * @return <code>true</code> if successful, <code>false</code> otherwise
	 */
	boolean setClassName(Network network);

}
