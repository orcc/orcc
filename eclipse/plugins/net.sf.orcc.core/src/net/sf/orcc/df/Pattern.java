/*
 * Copyright (c) 2009-2011, IETR/INSA of Rennes
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
package net.sf.orcc.df;

import net.sf.orcc.ir.Var;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.EMap;
import org.eclipse.emf.ecore.EObject;

/**
 * This class defines a pattern. A pattern is a map between ports and the number
 * of tokens produced/consumed by each of them.
 * 
 * @author Matthieu Wipliez
 * @model
 */
public interface Pattern extends EObject {

	/**
	 * Clears this pattern.
	 */
	void clear();

	/**
	 * Returns <code>true</code> if this pattern contains the given port.
	 * 
	 * @param port
	 *            a port
	 * @return <code>true</code> if this pattern contains the given port
	 */
	boolean contains(Port port);

	/**
	 * Returns <code>true</code> if this pattern contains the given variable.
	 * 
	 * @param var
	 *            a variable
	 * @return <code>true</code> if this pattern contains the given variable
	 */
	boolean contains(Var var);

	/**
	 * Returns the number of tokens produced (or consumed) by the given port.
	 * 
	 * @return the number of tokens produced (or consumed) by the given port
	 */
	int getNumTokens(Port port);

	/**
	 * Returns the number of tokens map. To be used in templates. Use
	 * {@link #getNumTokens(Port)} to retrieve the number of tokens
	 * produced/consumed by a given port.
	 * 
	 * @return the number of tokens map
	 * @model keyType="Port" valueType="Integer"
	 */
	EMap<Port, Integer> getNumTokensMap();

	/**
	 * Returns the port that corresponds to the given variable.
	 * 
	 * @param variable
	 *            a variable that contains tokens produced (or consumed)
	 * @return the port that corresponds to the given variable
	 */
	Port getPort(Var variable);

	/**
	 * Returns the ports of this pattern.
	 * 
	 * @return the ports of this pattern
	 * @model
	 */
	EList<Port> getPorts();

	/**
	 * Returns a variable to port map. To be used in templates. Use
	 * {@link #setVariable(Port, Var)} to modify this map.
	 * 
	 * @return a variable to port map
	 * @model keyType="Port" valueType="Var"
	 */
	EMap<Port, Var> getPortToVarMap();

	/**
	 * Returns the variable that contains tokens produced (or consumed) by the
	 * given port.
	 * 
	 * @return the variable that contains tokens produced (or consumed) by the
	 *         given port
	 */
	Var getVariable(Port port);

	/**
	 * Returns the variables contained in this pattern.
	 * 
	 * @return the variables contained of this pattern
	 * @model containment="true"
	 */
	EList<Var> getVariables();

	/**
	 * Returns a port to variable map. To be used in templates. Use
	 * {@link #setVariable(Port, Var)} to modify this map.
	 * 
	 * @return a port to variable map
	 * @model keyType="Var" valueType="Port"
	 */
	EMap<Var, Port> getVarToPortMap();

	/**
	 * Returns <code>true</code> if this pattern is empty.
	 * 
	 * @return <code>true</code> if this pattern is emptyS
	 */
	boolean isEmpty();

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
	boolean isSupersetOf(Pattern other);

	/**
	 * Removes the given port from this pattern.
	 * 
	 * @param port
	 *            a port
	 */
	void remove(Port port);

	/**
	 * Sets the number of tokens produced (or consumed) by the given port.
	 * 
	 * @param port
	 *            a port
	 * @param numTokens
	 *            number of tokens produced (or consumed) by the given port
	 */
	void setNumTokens(Port port, int numTokens);

	/**
	 * Sets the variable that contains tokens produced (or consumed) by the
	 * given port.
	 * 
	 * @param port
	 *            a port
	 * @param var
	 *            the variable that contains tokens produced (or consumed) by
	 *            the given port
	 */
	void setVariable(Port port, Var var);

	/**
	 * Update production/consumption of the pattern with the given pattern
	 * 
	 * @param pattern
	 *            : the reference pattern
	 */
	void updatePattern(Pattern pattern);

}
