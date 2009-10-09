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
package net.sf.orcc.frontend;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.orcc.OrccException;
import net.sf.orcc.ir.Location;

/**
 * Scope.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class Scope<T> {

	private Map<String, T> map;

	private List<T> objects;

	private Scope<T> parent;

	/**
	 * Creates a top-level scope.
	 */
	public Scope() {
		this(null);
	}

	/**
	 * Creates a scope with the given parent.
	 * 
	 * @param parent
	 *            the parent scope
	 */
	public Scope(Scope<T> parent) {
		this.parent = parent;
		objects = new ArrayList<T>();
		map = new HashMap<String, T>();
	}

	/**
	 * Returns the list of objects of this scope
	 * 
	 * @return the list of objects of this scope
	 */
	public List<T> getList() {
		return objects;
	}

	/**
	 * Returns the parent of this scope
	 * 
	 * @return a scope, or <code>null</code> if this scope has no parent
	 */
	public Scope<T> getParent() {
		return parent;
	}

	/**
	 * Registers an object with the given name.
	 * 
	 * @param name
	 *            the name of an object
	 * @param object
	 *            an object
	 * @throws OrccException
	 *             if the object is already defined
	 */
	public void register(String file, Location location, String name, T object)
			throws OrccException {
		if (map.containsKey(name)) {
			throw new OrccException(file, location, "\"" + name
					+ "\" already defined in this scope");
		}
		objects.add(object);
		map.put(name, object);
	}

	/**
	 * Returns the object that has the given name. If the object is not found in
	 * the current scope, the parent scope is queried.
	 * 
	 * @param name
	 *            the name of an object.
	 * @return the object that has the given name, or <code>null</code>
	 */
	public T resolveName(String name) {
		T object = map.get(name);
		if (object == null) {
			if (parent == null) {
				// top-level scope, no object
				return null;
			} else {
				// child scope, query parent
				return parent.resolveName(name);
			}
		} else {
			// object found
			return object;
		}
	}

	@Override
	public String toString() {
		String res = objects.toString();
		if (parent != null) {
			res += "\n" + parent;
		}
		return res;
	}

}
