/*
 * Copyright (c) 2009-2010, IETR/INSA of Rennes
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

import java.util.LinkedHashMap;
import java.util.Map.Entry;

/**
 * This class defines a pattern. A pattern is a map between ports and the number
 * of tokens produced/consumed by each of them.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class Pattern extends LinkedHashMap<Port, Integer> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Pattern() {
		super(4);
	}

	public Pattern(int initialCapacity) {
		super(initialCapacity);
	}

	/**
	 * Returns <code>true</code> if this pattern is a subset of the given
	 * pattern. This can be used to determine time-dependent behavior, which
	 * occurs when an action reads inputs not read by a higher-priority action.
	 * 
	 * @param pattern
	 *            a pattern
	 * @return <code>true</code> if this pattern is a subset of the given
	 *         pattern
	 */
	public boolean isSubsetOf(Pattern pattern) {
		if (pattern.keySet().containsAll(keySet())) {
			for (Entry<Port, Integer> entry : entrySet()) {
				// consumption must be >=
				if (pattern.get(entry.getKey()) < entry.getValue()) {
					return false;
				}
			}

			return true;
		} else {
			return false;
		}
	}

}
