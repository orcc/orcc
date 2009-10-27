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
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.orcc.OrccException;
import net.sf.orcc.common.Location;

/**
 * An ordered map maintains mapping of string to object as well as the order in
 * which those mappings were inserted.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class OrderedMap<T extends INameable> implements Iterable<T> {

	private class Itr implements Iterator<T> {

		/**
		 * Index of element to be returned by subsequent call to next.
		 */
		private int cursor = 0;

		/**
		 * Index of element returned by most recent call to next or previous.
		 * Reset to -1 if this element is deleted by a call to remove.
		 */
		int lastRet = -1;

		@Override
		public boolean hasNext() {
			return cursor != size();
		}

		@Override
		public T next() {
			T next = objects.get(cursor);
			lastRet = cursor++;
			return next;
		}

		@Override
		public void remove() {
			if (lastRet == -1) {
				throw new IllegalStateException();
			}

			// remove the object at the given index
			T next = objects.remove(lastRet);

			// remove the object from the map
			map.remove(next.getName());

			if (lastRet < cursor) {
				cursor--;
			}
			lastRet = -1;
		}

	}

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

		objects.add(object);
		map.put(name, object);
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
	 * Returns the list of objects of this scope. Warning: DO NOT modify the
	 * list returned! Indeed, the list is returned by reference for efficiency,
	 * should you want to add objects to this ordered map, please do so using
	 * {@link #add(String, Location, String, INameable)}; to remove objects, we
	 * advise you to use an iterator wherever possible, and
	 * {@link #remove(INameable)} elsewhere.
	 * 
	 * @return the list of objects of this scope
	 */
	public List<T> getList() {
		return objects;
	}

	@Override
	public Iterator<T> iterator() {
		return new Itr();
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
		objects.remove(object);
	}

	/**
	 * Returns the number of elements in this ordered map.
	 * 
	 * @return the number of elements in this ordered map
	 */
	public int size() {
		return objects.size();
	}

	@Override
	public String toString() {
		return objects.toString();
	}

}
