/*
 * Copyright (c) 2008, IETR/INSA of Rennes
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
package net.sf.orcc.graphiti.model;

import java.util.List;

/**
 * This class represents a vertex.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class Vertex extends AbstractObject {

	/**
	 * String for the "destination vertex" property. Set when a vertex becomes
	 * the destination of a dependency.
	 */
	public static final String PROPERTY_DST_VERTEX = "destination vertex";

	/**
	 * String for the "size" property. Set when the location/size of a vertex
	 * changes.
	 */
	public static final String PROPERTY_SIZE = "size";

	/**
	 * String for the "source vertex" property. Set when a vertex becomes the
	 * source of a dependency.
	 */
	public static final String PROPERTY_SRC_VERTEX = "source vertex";

	/**
	 * String for the "Input port" type.
	 */
	public static final String TYPE_INPUT_PORT = "Input port";

	/**
	 * String for the "Output port" type.
	 */
	public static final String TYPE_OUTPUT_PORT = "Output port";

	/**
	 * String for the "Port" type.
	 */
	public static final String TYPE_PORT = "Port";

	/**
	 * The parent graph of this vertex.
	 */
	Graph parent;

	/**
	 * Creates a vertex with the given type.
	 * 
	 * @param type
	 *            The vertex type.
	 */
	public Vertex(ObjectType type) {
		super(type);

		// set default values
		List<Parameter> parameters = type.getParameters();
		for (Parameter parameter : parameters) {
			setValue(parameter.getName(), parameter.getDefault());
		}
	}

	/**
	 * Creates a new vertex which is a copy of the given vertex.
	 * 
	 * @param vertex
	 *            The source vertex.
	 */
	public Vertex(Vertex vertex) {
		super(vertex);
		parent = vertex.parent;
	}

	/**
	 * Returns the configuration associated with this Vertex.
	 * 
	 * @return The configuration associated with this Vertex.
	 */
	public Configuration getConfiguration() {
		return parent.getConfiguration();
	}

	/**
	 * Returns the parent {@link Graph} of this Vertex.
	 * 
	 * @return The parent {@link Graph} of this Vertex.
	 */
	public Graph getParent() {
		return parent;
	}

	@Override
	public Object setValue(String propertyName, Object newValue) {
		if (ObjectType.PARAMETER_ID.equals(propertyName)) {
			if (parent != null) {
				parent.changeVertexId(this, (String) newValue);
			}
		}
		return super.setValue(propertyName, newValue);
	}

	public String toString() {
		return getType() + ": " + getValue(ObjectType.PARAMETER_ID);
	}

}
