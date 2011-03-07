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

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * This class defines a pattern. A pattern is a map between ports and the number
 * of tokens produced/consumed by each of them.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class Pattern {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Map<Port, Integer> numTokensMap;

	private Map<Port, Variable> peekedMap;

	private List<Port> ports;

	private Map<Port, Variable> variableMap;

	public Pattern() {
		ports = new ArrayList<Port>();
		numTokensMap = new LinkedHashMap<Port, Integer>();
		peekedMap = new LinkedHashMap<Port, Variable>();
		variableMap = new LinkedHashMap<Port, Variable>();
	}

	public Pattern(int initialCapacity) {
		ports = new ArrayList<Port>(initialCapacity);
		numTokensMap = new LinkedHashMap<Port, Integer>(initialCapacity);
		variableMap = new LinkedHashMap<Port, Variable>(initialCapacity);
		peekedMap = new LinkedHashMap<Port, Variable>(initialCapacity);
	}

	/**
	 * Checks if the given port is present in the {@link #ports} list, and adds
	 * it if necessary.
	 * 
	 * @param port
	 *            a port
	 */
	private void checkPortPresence(Port port) {
		if (!ports.contains(port)) {
			ports.add(port);
		}
	}

	/**
	 * Clears this pattern.
	 */
	public void clear() {
		ports.clear();
		numTokensMap.clear();
		peekedMap.clear();
		variableMap.clear();
	}

	/**
	 * Returns <code>true</code> if this pattern contains the given port.
	 * 
	 * @param port
	 *            a port
	 * @return <code>true</code> if this pattern contains the given port
	 */
	public boolean contains(Port port) {
		return (ports.contains(port));
	}

	/**
	 * Returns the number of tokens produced (or consumed) by the given port.
	 * 
	 * @return the number of tokens produced (or consumed) by the given port
	 */
	public Integer getNumTokens(Port port) {
		return numTokensMap.get(port);
	}

	/**
	 * Returns the number of tokens map.
	 * 
	 * @return the number of tokens map
	 */
	public Map<Port, Integer> getNumTokensMap() {
		return numTokensMap;
	}

	/**
	 * Returns the variable that contains the tokens peeked by the given port.
	 * May be <code>null</code> if the port is not peeked.
	 * 
	 * @return the variable that contains the tokens peeked by the given port
	 */
	public Variable getPeeked(Port port) {
		return peekedMap.get(port);
	}

	/**
	 * Returns the peeked map.
	 * 
	 * @return the peeked map
	 */
	public Map<Port, Variable> getPeekedMap() {
		return peekedMap;
	}

	/**
	 * Returns the ports of this pattern.
	 * 
	 * @return the ports of this pattern
	 */
	public List<Port> getPorts() {
		return ports;
	}

	/**
	 * Returns the variable that contains tokens produced (or consumed) by the
	 * given port.
	 * 
	 * @return the variable that contains tokens produced (or consumed) by the
	 *         given port
	 */
	public Variable getVariable(Port port) {
		return variableMap.get(port);
	}

	/**
	 * Returns the variable map.
	 * 
	 * @return the variable map
	 */
	public Map<Port, Variable> getVariableMap() {
		return variableMap;
	}

	/**
	 * Returns <code>true</code> if this pattern is empty.
	 * 
	 * @return <code>true</code> if this pattern is emptyS
	 */
	public boolean isEmpty() {
		return ports.isEmpty();
	}

	/**
	 * Returns <code>true</code> if this pattern is a superset of the given
	 * pattern. This can be used to determine time-dependent behavior, which
	 * occurs when an action reads inputs not read by a higher-priority action.
	 * 
	 * @param other
	 *            another pattern
	 * @return <code>true</code> if this pattern is a subset of the given
	 *         pattern
	 */
	public boolean isSupersetOf(Pattern other) {
		if (this.getNumTokensMap().keySet()
				.containsAll(other.getNumTokensMap().keySet())) {
			// OK we read from at least the same ports as the other pattern

			// let's check the consumption
			for (Entry<Port, Integer> entry : other.getNumTokensMap()
					.entrySet()) {
				// if this pattern consumes less than the other pattern then
				// this pattern is not a superset
				if (this.getNumTokens(entry.getKey()) < entry.getValue()) {
					return false;
				}
			}

			return true;
		} else {
			return false;
		}
	}

	/**
	 * Removes the given port from this pattern.
	 * 
	 * @param port
	 *            a port
	 */
	public void remove(Port port) {
		ports.remove(port);
		numTokensMap.remove(port);
		peekedMap.remove(port);
		variableMap.remove(port);
	}

	/**
	 * Sets the number of tokens produced (or consumed) by the given port.
	 * 
	 * @param port
	 *            a port
	 * @param numTokens
	 *            number of tokens produced (or consumed) by the given port
	 */
	public void setNumTokens(Port port, int numTokens) {
		checkPortPresence(port);
		this.numTokensMap.put(port, numTokens);
	}

	/**
	 * Sets the variable in which tokens are peeked from the given port.
	 * 
	 * @param port
	 *            a port
	 * @param peeked
	 *            a variable that contains tokens peeked by the given port
	 */
	public void setPeeked(Port port, Variable peeked) {
		checkPortPresence(port);
		this.peekedMap.put(port, peeked);
	}

	/**
	 * Sets the variable that contains tokens produced (or consumed) by the
	 * given port.
	 * 
	 * @param port
	 *            a port
	 * @param variable
	 *            the variable that contains tokens produced (or consumed) by
	 *            the given port
	 */
	public void setVariable(Port port, Variable variable) {
		checkPortPresence(port);
		this.variableMap.put(port, variable);
	}

}
