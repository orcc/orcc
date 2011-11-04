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

/**
 * This class defines a vertex in an XDF network. A vertex is either an input
 * port, an output port, or an instance.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class Vertex {

	/**
	 * This class defines a vertex type.
	 * 
	 * @author Matthieu Wipliez
	 * 
	 */
	private enum Type {
		INPUT_PORT, INSTANCE, OUTPUT_PORT
	};

	/**
	 * the contents of this vertex. Can only be an Instance or a Port.
	 */
	private Object contents;

	/**
	 * the type of this vertex. One of INSTANCE, INPUT or OUTPUT.
	 */
	private Type type;

	/**
	 * Creates a new vertex whose contents will be the given instance.
	 * 
	 * @param instance
	 *            an instance
	 */
	public Vertex(Instance instance) {
		type = Type.INSTANCE;
		contents = instance;
	}

	/**
	 * Creates a new vertex whose contents will be the given port.
	 * 
	 * @param port
	 *            a port
	 */
	public Vertex(String kind, Port port) {
		if (kind.equals("Input")) {
			type = Type.INPUT_PORT;
		} else {
			type = Type.OUTPUT_PORT;
		}
		contents = port;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Vertex) {
			Vertex vertex = (Vertex) obj;
			// the == is deliberate
			return contents == vertex.contents;
		} else {
			return false;
		}
	}

	/**
	 * Returns the instance contained in this vertex.
	 * 
	 * @return the instance contained in this vertex.
	 */
	public Instance getInstance() {
		if (isInstance()) {
			return (Instance) contents;
		} else {
			return null;
		}
	}

	/**
	 * Returns the port contained in this vertex.
	 * 
	 * @return the port contained in this vertex.
	 */
	public Port getPort() {
		if (isPort()) {
			return (Port) contents;
		} else {
			return null;
		}
	}

	@Override
	public int hashCode() {
		return contents.hashCode();
	}

	/**
	 * Returns <code>true</code> if this vertex contains an instance, and
	 * <code>false</code> otherwise. This method must be called to ensure a
	 * vertex is an instance before calling {@link #getInstance()}.
	 * 
	 * @return <code>true</code> if this vertex contains an instance, and
	 *         <code>false</code> otherwise
	 */
	public boolean isInstance() {
		return (type == Type.INSTANCE);
	}

	/**
	 * Returns <code>true</code> if this vertex contains a port, and
	 * <code>false</code> otherwise. This method must be called to ensure a
	 * vertex is a port before calling {@link #getPort()}.
	 * 
	 * @return <code>true</code> if this vertex contains a port, and
	 *         <code>false</code> otherwise
	 */
	public boolean isPort() {
		return (type != Type.INSTANCE);
	}

	@Override
	public String toString() {
		return type + " " + contents;
	}

}
