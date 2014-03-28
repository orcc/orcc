/*
 * Copyright (c) 2011, IETR/INSA of Rennes
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

import net.sf.orcc.util.Attributable;

/**
 * This class defines a parameter.
 * 
 * @author Matthieu Wipliez
 * @model
 */
public interface Param extends Attributable {

	/**
	 * Returns the variable declaration associated with this parameter.
	 * 
	 * @return the variable declaration associated with this parameter
	 * @model containment="true"
	 */
	Var getVariable();

	/**
	 * Returns <code>true</code> if this parameter is declared by reference.
	 * 
	 * @return <code>true</code> if this parameter is declared by reference
	 * @model
	 */
	boolean isByRef();

	/**
	 * Sets if this parameter is declared by reference.
	 * 
	 * @param byRef
	 *            <code>true</code> if this parameter is declared by reference
	 */
	void setByRef(boolean byRef);

	/**
	 * Sets the variable declaration associated with this parameter.
	 * 
	 * @param variable
	 *            the variable declaration associated with this parameter
	 */
	void setVariable(Var variable);

}
