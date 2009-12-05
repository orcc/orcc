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
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

import net.sf.orcc.OrccException;
import net.sf.orcc.ir.Location;

/**
 * This class defines an ordered map of nameable objects. It is backed by a
 * linked hash map, to which most of the calls are delegated to. The specific
 * functionality of this map is that there is only one public method to add
 * variables, and it checks for uniqueness or throw an exception.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class OrderedMap<T extends INameable> implements Iterable<T> {

	/**
	 * a cache for the getList method
	 */
	private List<T> list;

	/**
	 * a linked hash map which maintains the order in which objects are added
	 */
	private LinkedHashMap<String, T> map;

	/**
	 * <code>true</code> if the map has been modified
	 */
	private boolean modified;

	/**
	 * Creates an empty ordered map.
	 */
	public OrderedMap() {
		map = new LinkedHashMap<String, T>();
		modified = true;
	}

	/**
	 * Adds an object to this ordered map with the given name. The file and
	 * location information are only used for error reporting if the object is
	 * already present in the map.
	 * 
	 * @param file
	 *            the file where the object located
	 * @param location
	 *            the location of the object
	 * @param name
	 *            the name of an object
	 * @param object
	 *            an object
	 * @throws OrccException
	 *             if the object is already defined
	 */
	public void add(String file, Location location, String name, T object)
			throws OrccException {
		if (map.containsKey(name)) {
			throw new OrccException(file, location, "\"" + name
					+ "\" already defined in this scope");
		}

		map.put(name, object);
		modified = true;
	}

	/**
	 * Adds an object to this ordered map with the given name, without checking
	 * for existing bindings.
	 * 
	 * @param name
	 *            the name of an object
	 * @param object
	 *            an object
	 */
	protected final void add(String name, T object) {
		map.put(name, object);
		modified = true;
	}

	/**
	 * Returns <code>true</code> if this map contains a mapping for
	 * <code>object.getName()</code>.
	 * 
	 * @param object
	 *            an object whose name we are looking for in this map.
	 * @return <code>true</code> if this map contains this object,
	 *         <code>false</code> otherwise.
	 */
	public boolean contains(T object) {
		return map.containsKey(object.getName());
	}

	/**
	 * Returns the object that has the given name.
	 * 
	 * @param name
	 *            the name of an object.
	 * @return the object that has the given name, or if it could not be found,
	 *         <code>null</code>
	 */
	public T get(String name) {
		return map.get(name);
	}

	/**
	 * Returns the list of objects of this scope. The list returned is a copy.
	 * Note that the list is cached for efficiency, so as long as no objects are
	 * added or removed, calling this method will return a reference to the same
	 * list.
	 * 
	 * @return the list of objects of this scope
	 */
	public List<T> getList() {
		if (modified) {
			list = new ArrayList<T>(map.values());
		}
		return list;
	}

	@Override
	public Iterator<T> iterator() {
		Iterator<T> it = map.values().iterator();
		// because somebody may call "remove" on the iterator
		modified = true;
		return it;
	}

	/**
	 * Removes the given object from this ordered map. Note: This method removes
	 * the object from both the map in O(1) and the list in O(n) on the order of
	 * the number of objects present, so depending on your needs you might want
	 * to use an iterator to remove variables from this map.
	 * 
	 * @param object
	 *            the object to remove
	 */
	public void remove(T object) {
		map.remove(object.getName());
		modified = true;
	}

	/**
	 * Returns the number of elements in this ordered map.
	 * 
	 * @return the number of elements in this ordered map
	 */
	public int size() {
		return map.size();
	}

	@Override
	public String toString() {
		return map.toString();
	}

}
