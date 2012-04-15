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

import net.sf.dftools.graph.Vertex;
import net.sf.orcc.ir.Type;

/**
 * This class defines a port. A port has a location, a type, a name.
 * 
 * @author Matthieu Wipliez
 * @model extends="Vertex"
 */
public interface Port extends Vertex {

	/**
	 * Returns the name of this port.
	 * 
	 * @return the name of this port
	 * @model derived="true" transient="true" volatile="true"
	 */
	String getName();

	/**
	 * Returns the value of the '<em><b>Num Tokens Consumed</b></em>' attribute.
	 * <!-- begin-user-doc --><!-- end-user-doc -->
	 * @return the value of the '<em>Num Tokens Consumed</em>' attribute.
	 * @see #setNumTokensConsumed(int)
	 * @see net.sf.orcc.df.DfPackage#getPort_NumTokensConsumed()
	 * @model
	 * @generated
	 */
	int getNumTokensConsumed();

	/**
	 * Returns the number of tokens produced by this port.
	 * 
	 * @return the number of tokens produced by this port
	 * @model
	 */
	int getNumTokensProduced();

	/**
	 * Returns the type of this port.
	 * 
	 * @return the type of this port
	 * @model containment="true"
	 */
	Type getType();

	/**
	 * Increases the number of tokens consumed by this port by the given
	 * integer.
	 * 
	 * @param n
	 *            a number of tokens
	 * @throws IllegalArgumentException
	 *             if n is less or equal to zero
	 */
	void increaseTokenConsumption(int n);

	/**
	 * Increases the number of tokens produced by this port by the given
	 * integer.
	 * 
	 * @param n
	 *            a number of tokens
	 * @throws IllegalArgumentException
	 *             if n is less or equal to zero
	 */
	void increaseTokenProduction(int n);

	/**
	 * Returns <code>true</code> if this port is native.
	 * 
	 * @return <code>true</code> if this port is native
	 */
	boolean isNative();

	/**
	 * Resets the number of tokens consumed by this port.
	 */
	void resetTokenConsumption();

	/**
	 * Resets the number of tokens produced by this port.
	 */
	void resetTokenProduction();

	/**
	 * Sets the new name of this port.
	 * 
	 * @param name
	 *            the new name of this port
	 */
	void setName(String name);

	/**
	 * Sets the value of the '{@link net.sf.orcc.df.Port#getNumTokensConsumed <em>Num Tokens Consumed</em>}' attribute.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @param value the new value of the '<em>Num Tokens Consumed</em>' attribute.
	 * @see #getNumTokensConsumed()
	 * @generated
	 */
	void setNumTokensConsumed(int value);

	/**
	 * Sets the value of the '{@link net.sf.orcc.df.Port#getNumTokensProduced <em>Num Tokens Produced</em>}' attribute.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @param value the new value of the '<em>Num Tokens Produced</em>' attribute.
	 * @see #getNumTokensProduced()
	 * @generated
	 */
	void setNumTokensProduced(int value);

	/**
	 * Sets the type of this port.
	 * 
	 * @param type
	 *            the new type of this port
	 */
	void setType(Type type);

}
