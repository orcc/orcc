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
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * This class provides a parameter associated with an object (vertex, graph,
 * edge). It has a name, a type, and a position.
 * 
 * @author Jonathan Piat
 * @author Matthieu Wipliez
 * 
 */
public class Parameter {

	private Object defaultValue;

	private String name;

	private ParameterPosition position;

	private Class<?> type;

	/**
	 * Creates a new parameter.
	 * 
	 * @param name
	 *            The parameter name.
	 * @param value
	 *            The parameter default value.
	 * @param position
	 *            Its position, may be <code>null</code>.
	 * @param type
	 *            The parameter type, as a Java {@link Class}&lt;?&gt;.
	 */
	public Parameter(String name, Object value, ParameterPosition position,
			Class<?> type) {
		this.defaultValue = value;
		this.name = name;
		this.position = position;
		this.type = type;
	}

	/**
	 * Returns this parameter's default value.
	 * 
	 * @return This parameter's default value.
	 */
	public Object getDefault() {
		if (defaultValue instanceof Boolean) {
			return new Boolean((Boolean) defaultValue);
		} else if (defaultValue instanceof Float) {
			return new Float((Float) defaultValue);
		} else if (defaultValue instanceof Integer) {
			return new Integer((Integer) defaultValue);
		} else if (defaultValue instanceof List<?>) {
			return new ArrayList<Object>((List<?>) defaultValue);
		} else if (defaultValue instanceof Map<?, ?>) {
			return new TreeMap<Object, Object>((Map<?, ?>) defaultValue);
		} else if (defaultValue instanceof String) {
			return new String((String) defaultValue);
		} else {
			return defaultValue;
		}
	}

	/**
	 * Returns this parameter's name.
	 * 
	 * @return This parameter's name.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Returns this parameter's position.
	 * 
	 * @return This parameter's position.
	 */
	public ParameterPosition getPosition() {
		return position;
	}

	/**
	 * Returns this parameter's type.
	 * 
	 * @return This parameter's type.
	 */
	public Class<?> getType() {
		return type;
	}

	@Override
	public String toString() {
		return name;
	}

}
