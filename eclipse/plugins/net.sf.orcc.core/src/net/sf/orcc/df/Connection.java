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

import net.sf.orcc.ir.Expression;

import org.eclipse.emf.common.util.EList;

/**
 * This class represents a connection in a network. A connection can have a
 * number of attributes, that can be types or expressions.
 * 
 * @author Matthieu Wipliez
 * @author Herve Yviquel
 * @model
 */
public interface Connection extends Edge {

	/**
	 * the bufferSize attribute can be attached to a FIFO to specify its size
	 */
	static final String BUFFER_SIZE = "bufferSize";

	/**
	 * Returns the attribute associated with the given name.
	 * 
	 * @param name
	 *            an attribute name
	 * @return the attribute associated with the given name, or if not found,
	 *         <code>null</code>
	 */
	Attribute getAttribute(String name);

	/**
	 * Returns the map of attributes contained in this object.
	 * 
	 * @return the map of attributes contained in this object
	 * @model containment="true"
	 */
	EList<Attribute> getAttributes();

	/**
	 * Returns the size of this connection.
	 * 
	 * @return the size of this connection
	 */
	Integer getSize();

	/**
	 * Returns this connection's source port (may be <code>null</code>).
	 * 
	 * @return this connection's source port (may be <code>null</code>)
	 * @model
	 */
	Port getSourcePort();

	/**
	 * Returns this connection's target port (may be <code>null</code>).
	 * 
	 * @return this connection's target port (may be <code>null</code>)
	 * @model
	 */
	Port getTargetPort();

	/**
	 * Adds an attribute with the given name and value
	 * 
	 * @param name
	 *            attribute name
	 * @param value
	 *            value
	 */
	void setAttribute(String name, Expression value);

	/**
	 * Adds an attribute with the given name and value
	 * 
	 * @param name
	 *            attribute name
	 * @param value
	 *            value
	 */
	void setAttribute(String name, int value);

	/**
	 * Sets the source of this connection to the given port
	 * 
	 * @param source
	 *            a port
	 */
	void setSourcePort(Port source);

	/**
	 * Sets the target of this connection to the given port
	 * 
	 * @param target
	 *            a port
	 */
	void setTargetPort(Port target);

}
