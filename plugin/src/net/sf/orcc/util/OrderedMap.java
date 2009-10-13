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
package net.sf.orcc.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.orcc.OrccException;
import net.sf.orcc.ir.Location;

/**
 * An ordered map maintains mapping of string to object as well as the order in
 * which those mappings were inserted.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class OrderedMap<T> {

	private Map<String, T> map;

	private List<T> objects;

	/**
	 * Creates an empty ordered map.
	 */
	public OrderedMap() {
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
	 * Returns the object that has the given name.
	 * 
	 * @param name
	 *            the name of an object.
	 * @return the object that has the given name, or if it could not be found,
	 *         <code>null</code>
	 */
	public T resolveName(String name) {
		return map.get(name);
	}

	@Override
	public String toString() {
		return objects.toString();
	}

}
