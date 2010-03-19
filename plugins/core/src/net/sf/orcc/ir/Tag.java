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
package net.sf.orcc.ir;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * This class defines an action tag as a list of strings.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class Tag implements Comparable<Tag>, Iterable<String> {

	private List<String> identifiers;

	/**
	 * Creates an empty tag.
	 */
	public Tag() {
		this(0);
	}

	/**
	 * Creates a tag from an AST tag object.
	 */
	public Tag(net.sf.orcc.cal.cal.Tag tag) {
		List<String> identifiers = tag.getIdentifiers();
		this.identifiers = new ArrayList<String>(identifiers);
	}

	/**
	 * Creates an empty tag with a given initial size.
	 * 
	 * @param size
	 *            initial size of the tag
	 * 
	 */
	public Tag(int size) {
		identifiers = new ArrayList<String>(size);
	}

	/**
	 * Creates a tag with a given initial size and initializes it with the given
	 * tag.
	 * 
	 * @param size
	 *            initial size of the tag
	 * @param tag
	 *            tag to initialize this tag from
	 */
	public Tag(int size, Tag tag) {
		identifiers = new ArrayList<String>(size);
		identifiers.addAll(tag.identifiers);
	}

	/**
	 * Adds an identifier to this tag.
	 * 
	 * @param identifier
	 *            an identifier
	 */
	public void add(String identifier) {
		identifiers.add(identifier);
	}

	@Override
	public int compareTo(Tag tag) {
		return toString().compareTo(tag.toString());
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Tag) {
			return identifiers.equals(((Tag) obj).identifiers);
		} else {
			return false;
		}
	}

	/**
	 * Returns the identifier at the given index.
	 * 
	 * @param index
	 *            index of the identifier to return
	 * @return the identifier at the given index
	 */
	public String get(int index) {
		return identifiers.get(index);
	}

	@Override
	public int hashCode() {
		return identifiers.hashCode();
	}

	/**
	 * Returns true if this tag is empty.
	 * 
	 * @return true if this tag is empty
	 */
	public boolean isEmpty() {
		return identifiers.isEmpty();
	}

	@Override
	public Iterator<String> iterator() {
		return identifiers.iterator();
	}

	/**
	 * Returns the number of identifiers in this tag.
	 * 
	 * @return the number of identifiers in this tag
	 */
	public int size() {
		return identifiers.size();
	}

	@Override
	public String toString() {
		return identifiers.toString();
	}

}
