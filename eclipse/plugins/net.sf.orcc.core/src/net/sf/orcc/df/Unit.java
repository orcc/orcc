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
package net.sf.orcc.df;

import net.sf.orcc.ir.Procedure;
import net.sf.orcc.ir.Var;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.common.util.EList;

/**
 * This class defines a unit.
 * 
 * @author Matthieu Wipliez
 * @model extends="Entity"
 */
public interface Unit extends Nameable {

	/**
	 * Returns the constants contained in this unit.
	 * 
	 * @return the constants contained in this unit
	 * @model containment="true"
	 */
	EList<Var> getConstants();

	/**
	 * Returns the file this unit is defined in.
	 * 
	 * @return the file this unit is defined in
	 */
	IFile getFile();

	/**
	 * Returns the name of the file this unit is defined in.
	 * 
	 * @return the name of the file this unit is defined in
	 * @model
	 */
	String getFileName();

	/**
	 * Returns the value of the '<em><b>Line Number</b></em>' attribute. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Line Number</em>' attribute isn't clear, there
	 * really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Line Number</em>' attribute.
	 * @see #setLineNumber(int)
	 * @see net.sf.orcc.df.DfPackage#getUnit_LineNumber()
	 * @model
	 * @generated
	 */
	int getLineNumber();

	/**
	 * Returns the functions/procedures contained in this unit.
	 * 
	 * @return the functions/procedures contained in this unit
	 * @model containment="true"
	 */
	EList<Procedure> getProcedures();

	/**
	 * Sets the name of the file this unit is defined in.
	 * 
	 * @param name
	 *            the name of the file this unit is defined in
	 * @generated
	 */
	void setFileName(String value);

	/**
	 * Sets the value of the '{@link net.sf.orcc.df.Unit#getLineNumber <em>Line Number</em>}' attribute.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @param value the new value of the '<em>Line Number</em>' attribute.
	 * @see #getLineNumber()
	 * @generated
	 */
	void setLineNumber(int value);

}
