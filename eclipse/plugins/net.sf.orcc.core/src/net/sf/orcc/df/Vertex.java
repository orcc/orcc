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

import net.sf.orcc.ir.Port;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

/**
 * This class defines a vertex in an XDF network. A vertex is either an input
 * port, an output port, or an instance.
 * 
 * @author Matthieu Wipliez
 * @model
 */
public interface Vertex extends EObject {

	/**
	 * Returns the object contained in this vertex.
	 * 
	 * @return the object contained in this vertex.
	 * @model
	 */
	EObject getContents();

	/**
	 * @model type="Connection" opposite="target"
	 */
	EList<Connection> getIncomingEdges();

	/**
	 * Returns the instance contained in this vertex.
	 * 
	 * @return the instance contained in this vertex.
	 */
	Instance getInstance();

	/**
	 * @model type="Connection" opposite="source"
	 */
	EList<Connection> getOutgoingEdges();

	/**
	 * Returns the port contained in this vertex.
	 * 
	 * @return the port contained in this vertex.
	 */
	Port getPort();

	/**
	 * @model type="Vertex" opposite="successors"
	 */
	EList<Vertex> getPredecessors();

	/**
	 * @model type="Vertex" opposite="predecessors"
	 */
	EList<Vertex> getSuccessors();

	/**
	 * Returns <code>true</code> if this vertex contains an instance, and
	 * <code>false</code> otherwise. This method must be called to ensure a
	 * vertex is an instance before calling {@link #getInstance()}.
	 * 
	 * @return <code>true</code> if this vertex contains an instance, and
	 *         <code>false</code> otherwise
	 */
	boolean isInstance();

	/**
	 * Returns <code>true</code> if this vertex contains a port, and
	 * <code>false</code> otherwise. This method must be called to ensure a
	 * vertex is a port before calling {@link #getPort()}.
	 * 
	 * @return <code>true</code> if this vertex contains a port, and
	 *         <code>false</code> otherwise
	 */
	boolean isPort();

	/**
	 * Sets the object contained in this vertex.
	 * 
	 * @param contents
	 *            the object contained in this vertex.
	 */
	void setContents(EObject contents);

}
