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

/**
 * A scope is an ordered map of <string, object> extended with the notion of
 * hierarchy.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class Scope<T> extends OrderedMap<T> {

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
	}

	/**
	 * Returns the object that has the given name. If the object is not found in
	 * the current scope, the parent scope is queried.
	 * 
	 * @param name
	 *            the name of an object.
	 * @return the object that has the given name, or <code>null</code>
	 */
	@Override
	public T get(String name) {
		T object = super.get(name);
		if (object == null) {
			if (parent == null) {
				// top-level scope, no object
				return null;
			} else {
				// child scope, query parent
				return parent.get(name);
			}
		} else {
			// object found
			return object;
		}
	}

	/**
	 * Returns the parent of this scope
	 * 
	 * @return a scope, or <code>null</code> if this scope has no parent
	 */
	public Scope<T> getParent() {
		return parent;
	}

	@Override
	public String toString() {
		String res = super.toString();
		if (parent != null) {
			res += "\n" + parent;
		}
		return res;
	}

}
