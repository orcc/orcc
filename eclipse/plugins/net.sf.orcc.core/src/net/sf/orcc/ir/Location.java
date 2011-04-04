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

import org.eclipse.emf.ecore.EObject;

/**
 * This class represents a location. A location keeps track of where a
 * particular element was in the original file. It contains the line, and
 * starting and ending columns.
 * 
 * @author Matthieu Wipliez
 * @model
 */
public interface Location extends EObject {

	/**
	 * Returns the ending column of this location.
	 * 
	 * @return the ending column of this location
	 * @model
	 */
	int getEndColumn();

	/**
	 * Sets the value of the '{@link net.sf.orcc.ir.Location#getEndColumn <em>End Column</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>End Column</em>' attribute.
	 * @see #getEndColumn()
	 * @generated
	 */
	void setEndColumn(int value);

	/**
	 * Returns the starting column of this location.
	 * 
	 * @return the starting column of this location
	 * @model
	 */
	int getStartColumn();

	/**
	 * Sets the value of the '{@link net.sf.orcc.ir.Location#getStartColumn <em>Start Column</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Start Column</em>' attribute.
	 * @see #getStartColumn()
	 * @generated
	 */
	void setStartColumn(int value);

	/**
	 * Returns the starting line of this location.
	 * 
	 * @return the starting line of this location
	 * @model
	 */
	int getStartLine();

	/**
	 * Sets the value of the '{@link net.sf.orcc.ir.Location#getStartLine <em>Start Line</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Start Line</em>' attribute.
	 * @see #getStartLine()
	 * @generated
	 */
	void setStartLine(int value);

}
