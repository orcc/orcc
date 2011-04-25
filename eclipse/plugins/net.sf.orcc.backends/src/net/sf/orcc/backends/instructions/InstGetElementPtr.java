/*
 * Copyright (c) 2010-2011, IETR/INSA of Rennes
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
package net.sf.orcc.backends.instructions;

import net.sf.orcc.ir.Def;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.InstSpecific;
import net.sf.orcc.ir.Use;

import org.eclipse.emf.common.util.EList;

/**
 * This interface defines a Get Element Pointer instruction.
 * 
 * @author Jerome Gorin
 * @author Herve Yviquel
 * @model extends="net.sf.orcc.ir.InstSpecific"
 */
public interface InstGetElementPtr extends InstSpecific {
	/**
	 * Returns the value of the '<em><b>Indexes</b></em>' containment reference list.
	 * The list contents are of type {@link net.sf.orcc.ir.Expression}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Indexes</em>' containment reference list isn't
	 * clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Indexes</em>' containment reference list.
	 * @see net.sf.orcc.backends.instructions.InstructionsPackage#getInstGetElementPtr_Indexes()
	 * @model containment="true"
	 * @generated
	 */
	EList<Expression> getIndexes();

	/**
	 * Returns the value of the '<em><b>Target</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Target</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Target</em>' containment reference.
	 * @see #setTarget(Def)
	 * @see net.sf.orcc.backends.instructions.InstructionsPackage#getInstGetElementPtr_Target()
	 * @model containment="true"
	 * @generated
	 */
	Def getTarget();

	/**
	 * Sets the value of the '{@link net.sf.orcc.backends.instructions.InstGetElementPtr#getTarget <em>Target</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Target</em>' containment reference.
	 * @see #getTarget()
	 * @generated
	 */
	void setTarget(Def value);

	/**
	 * Returns the value of the '<em><b>Source</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Source</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Source</em>' containment reference.
	 * @see #setSource(Use)
	 * @see net.sf.orcc.backends.instructions.InstructionsPackage#getInstGetElementPtr_Source()
	 * @model containment="true"
	 * @generated
	 */
	Use getSource();

	/**
	 * Sets the value of the '{@link net.sf.orcc.backends.instructions.InstGetElementPtr#getSource <em>Source</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Source</em>' containment reference.
	 * @see #getSource()
	 * @generated
	 */
	void setSource(Use value);

	/**
	 * Returns true if the current instruction is GEP (which is always true for
	 * the current instruction). This method is only useable to detect this
	 * specific instruction in template document.
	 * 
	 * @return true if current instruction is GEP
	 */
	public boolean isGep();

	@Override
	public boolean isLoad();

} // InstGetElementPtr
