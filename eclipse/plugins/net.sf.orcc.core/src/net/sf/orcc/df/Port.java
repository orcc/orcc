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
 * <!-- begin-user-doc -->This class defines a port. A port has a location, a type, a name.<!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link net.sf.orcc.df.Port#getName <em>Name</em>}</li>
 *   <li>{@link net.sf.orcc.df.Port#getNumTokensConsumed <em>Num Tokens Consumed</em>}</li>
 *   <li>{@link net.sf.orcc.df.Port#getNumTokensProduced <em>Num Tokens Produced</em>}</li>
 *   <li>{@link net.sf.orcc.df.Port#getType <em>Type</em>}</li>
 * </ul>
 * </p>
 *
 * @see net.sf.orcc.df.DfPackage#getPort()
 * @model
 * @generated
 */
public interface Port extends Vertex {

	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc --><!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see net.sf.orcc.df.DfPackage#getPort_Name()
	 * @model transient="true" volatile="true" derived="true"
	 * @generated
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
	 * Returns the value of the '<em><b>Num Tokens Produced</b></em>' attribute.
	 * <!-- begin-user-doc --><!-- end-user-doc -->
	 * @return the value of the '<em>Num Tokens Produced</em>' attribute.
	 * @see #setNumTokensProduced(int)
	 * @see net.sf.orcc.df.DfPackage#getPort_NumTokensProduced()
	 * @model
	 * @generated
	 */
	int getNumTokensProduced();

	/**
	 * Returns the value of the '<em><b>Type</b></em>' containment reference.
	 * <!-- begin-user-doc --><!-- end-user-doc -->
	 * @return the value of the '<em>Type</em>' containment reference.
	 * @see #setType(Type)
	 * @see net.sf.orcc.df.DfPackage#getPort_Type()
	 * @model containment="true"
	 * @generated
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
	 * Sets the value of the '{@link net.sf.orcc.df.Port#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc --><!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

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
	 * Sets the value of the '{@link net.sf.orcc.df.Port#getType <em>Type</em>}' containment reference.
	 * <!-- begin-user-doc --><!-- end-user-doc -->
	 * @param value the new value of the '<em>Type</em>' containment reference.
	 * @see #getType()
	 * @generated
	 */
	void setType(Type value);

}
