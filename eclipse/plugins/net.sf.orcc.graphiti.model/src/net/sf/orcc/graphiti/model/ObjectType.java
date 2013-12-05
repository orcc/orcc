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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class provides an object type.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class ObjectType implements Comparable<ObjectType> {

	/**
	 * String for the "color" attribute. Defines the color of a graph, a vertex,
	 * an edge.
	 */
	public static final String ATTRIBUTE_COLOR = "color";

	/**
	 * String for the "directed" attribute.
	 */
	public static final String ATTRIBUTE_DIRECTED = "directed";

	/**
	 * String for the "height" attribute. Defines the vertex height.
	 */
	public static final String ATTRIBUTE_HEIGHT = "height";

	/**
	 * String for the "shape" attribute. Defines the vertex shape.
	 */
	public static final String ATTRIBUTE_SHAPE = "shape";

	/**
	 * String for the "width" attribute. Defines the vertex width.
	 */
	public static final String ATTRIBUTE_WIDTH = "width";

	/**
	 * String for the "id" parameter. Defines the vertex id.
	 */
	public static final String PARAMETER_ID = "id";

	/**
	 * String for the "refinement" parameter. Defines the vertex refinement.
	 */
	public static final String PARAMETER_REFINEMENT = "refinement";

	/**
	 * String for the "source port" parameter. Defines the edge source port (if
	 * any).
	 */
	public static final String PARAMETER_SOURCE_PORT = "source port";

	/**
	 * String for the "target port" parameter. Defines the edge target port (if
	 * any).
	 */
	public static final String PARAMETER_TARGET_PORT = "target port";

	private Map<String, Object> attributes;

	private String name;

	private Map<String, Parameter> parameters;

	/**
	 * Creates a new abstract type with the given name.
	 * 
	 * @param name
	 *            The type name.
	 */
	public ObjectType(String name) {
		this.name = name;
		attributes = new HashMap<String, Object>();
		parameters = new HashMap<String, Parameter>();
	}

	/**
	 * Adds the given attribute to this type.
	 * 
	 * @param attributeName
	 *            The attribute name.
	 */
	public void addAttribute(String attributeName, Object value) {
		attributes.put(attributeName, value);
	}

	/**
	 * Adds the given parameter to this type.
	 * 
	 * @param parameter
	 *            A parameter.
	 */
	public void addParameter(Parameter parameter) {
		parameters.put(parameter.getName(), parameter);
	}

	@Override
	public int compareTo(ObjectType type) {
		return name.compareTo(type.name);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof ObjectType) {
			ObjectType type = (ObjectType) obj;
			return name.equals(type.name);
		} else {
			return false;
		}
	}

	/**
	 * Returns the type's attribute that has the given name.
	 * 
	 * @param name
	 *            The name of the attribute we're looking for.
	 * @return The relevant attribute.
	 */
	public Object getAttribute(String name) {
		return attributes.get(name);
	}

	/**
	 * Returns this type's name.
	 * 
	 * @return This type's name.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Returns this type's parameter that has the given name.
	 * 
	 * @param name
	 *            The name of the parameter we're looking for.
	 * @return The relevant parameter.
	 */
	public Parameter getParameter(String name) {
		return parameters.get(name);
	}

	/**
	 * Returns a copy of this type's parameters.
	 * 
	 * @return A {@link List} containing a copy of this type's parameters.
	 */
	public List<Parameter> getParameters() {
		return new ArrayList<Parameter>(parameters.values());
	}

	@Override
	public String toString() {
		return getName();
	}

}
