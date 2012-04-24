/*
 * Copyright (c) 2011, IRISA
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
 *   * Neither the name of IRISA nor the names of its
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
package net.sf.orcc.backends.llvm.tta.architecture;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Expr Binary</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link net.sf.orcc.backends.llvm.tta.architecture.ExprBinary#getOperator <em>Operator</em>}</li>
 *   <li>{@link net.sf.orcc.backends.llvm.tta.architecture.ExprBinary#getE1 <em>E1</em>}</li>
 *   <li>{@link net.sf.orcc.backends.llvm.tta.architecture.ExprBinary#getE2 <em>E2</em>}</li>
 * </ul>
 * </p>
 *
 * @see net.sf.orcc.backends.llvm.tta.architecture.ArchitecturePackage#getExprBinary()
 * @model
 * @generated
 */
public interface ExprBinary extends Guard {
	/**
	 * Returns the value of the '<em><b>Operator</b></em>' attribute.
	 * The default value is <code>""</code>.
	 * The literals are from the enumeration {@link net.sf.orcc.backends.llvm.tta.architecture.OpBinary}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Operator</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Operator</em>' attribute.
	 * @see net.sf.orcc.backends.llvm.tta.architecture.OpBinary
	 * @see #setOperator(OpBinary)
	 * @see net.sf.orcc.backends.llvm.tta.architecture.ArchitecturePackage#getExprBinary_Operator()
	 * @model default=""
	 * @generated
	 */
	OpBinary getOperator();

	/**
	 * Sets the value of the '{@link net.sf.orcc.backends.llvm.tta.architecture.ExprBinary#getOperator <em>Operator</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Operator</em>' attribute.
	 * @see net.sf.orcc.backends.llvm.tta.architecture.OpBinary
	 * @see #getOperator()
	 * @generated
	 */
	void setOperator(OpBinary value);

	/**
	 * Returns the value of the '<em><b>E1</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>E1</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>E1</em>' reference.
	 * @see #setE1(ExprUnary)
	 * @see net.sf.orcc.backends.llvm.tta.architecture.ArchitecturePackage#getExprBinary_E1()
	 * @model
	 * @generated
	 */
	ExprUnary getE1();

	/**
	 * Sets the value of the '{@link net.sf.orcc.backends.llvm.tta.architecture.ExprBinary#getE1 <em>E1</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>E1</em>' reference.
	 * @see #getE1()
	 * @generated
	 */
	void setE1(ExprUnary value);

	/**
	 * Returns the value of the '<em><b>E2</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>E2</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>E2</em>' reference.
	 * @see #setE2(ExprUnary)
	 * @see net.sf.orcc.backends.llvm.tta.architecture.ArchitecturePackage#getExprBinary_E2()
	 * @model
	 * @generated
	 */
	ExprUnary getE2();

	/**
	 * Sets the value of the '{@link net.sf.orcc.backends.llvm.tta.architecture.ExprBinary#getE2 <em>E2</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>E2</em>' reference.
	 * @see #getE2()
	 * @generated
	 */
	void setE2(ExprUnary value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation"
	 * @generated
	 */
	boolean isOr();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation"
	 * @generated
	 */
	boolean isAnd();

} // ExprBinary
