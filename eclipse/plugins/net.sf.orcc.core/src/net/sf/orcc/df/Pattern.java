/**
 * <copyright>
 * Copyright (c) 2009-2012, IETR/INSA of Rennes
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
 * </copyright>
 */
package net.sf.orcc.df;

import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.Var;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.EMap;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Pattern</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link net.sf.orcc.df.Pattern#getPorts <em>Ports</em>}</li>
 *   <li>{@link net.sf.orcc.df.Pattern#getPortToVarMap <em>Port To Var Map</em>}</li>
 *   <li>{@link net.sf.orcc.df.Pattern#getVariables <em>Variables</em>}</li>
 *   <li>{@link net.sf.orcc.df.Pattern#getVarToPortMap <em>Var To Port Map</em>}</li>
 *   <li>{@link net.sf.orcc.df.Pattern#getExprTokensMap <em>Expr Tokens Map</em>}</li>
 *   <li>{@link net.sf.orcc.df.Pattern#getRepeatExpressions <em>Repeat Expressions</em>}</li>
 * </ul>
 * </p>
 *
 * @see net.sf.orcc.df.DfPackage#getPattern()
 * @model
 * @generated
 */
public interface Pattern extends EObject {
	/**
	 * Returns the value of the '<em><b>Expr Tokens Map</b></em>' map.
	 * The key is of type {@link net.sf.orcc.df.Port},
	 * and the value is of type {@link net.sf.orcc.ir.Expression},
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Expr Tokens Map</em>' map isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Expr Tokens Map</em>' map.
	 * @see net.sf.orcc.df.DfPackage#getPattern_ExprTokensMap()
	 * @model mapType="net.sf.orcc.df.PortToExpressionMapEntry<net.sf.orcc.df.Port, net.sf.orcc.ir.Expression>"
	 * @generated
	 */
	EMap<Port, Expression> getExprTokensMap();

	/**
	 * Returns the value of the '<em><b>Repeat Expressions</b></em>' containment reference list.
	 * The list contents are of type {@link net.sf.orcc.ir.Expression}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Repeat Expressions</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Repeat Expressions</em>' containment reference list.
	 * @see net.sf.orcc.df.DfPackage#getPattern_RepeatExpressions()
	 * @model containment="true"
	 * @generated
	 */
	EList<Expression> getRepeatExpressions();

	/**
	 * Returns the value of the '<em><b>Ports</b></em>' reference list.
	 * The list contents are of type {@link net.sf.orcc.df.Port}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Ports</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Ports</em>' reference list.
	 * @see net.sf.orcc.df.DfPackage#getPattern_Ports()
	 * @model
	 * @generated
	 */
	EList<Port> getPorts();

	/**
	 * Returns the value of the '<em><b>Port To Var Map</b></em>' map.
	 * The key is of type {@link net.sf.orcc.df.Port},
	 * and the value is of type {@link net.sf.orcc.ir.Var},
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Port To Var Map</em>' map isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Port To Var Map</em>' map.
	 * @see net.sf.orcc.df.DfPackage#getPattern_PortToVarMap()
	 * @model mapType="net.sf.orcc.df.PortToVarMapEntry<net.sf.orcc.df.Port, net.sf.orcc.ir.Var>"
	 * @generated
	 */
	EMap<Port, Var> getPortToVarMap();

	/**
	 * Returns the value of the '<em><b>Variables</b></em>' containment reference list.
	 * The list contents are of type {@link net.sf.orcc.ir.Var}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Variables</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Variables</em>' containment reference list.
	 * @see net.sf.orcc.df.DfPackage#getPattern_Variables()
	 * @model containment="true"
	 * @generated
	 */
	EList<Var> getVariables();

	/**
	 * Returns the value of the '<em><b>Var To Port Map</b></em>' map.
	 * The key is of type {@link net.sf.orcc.ir.Var},
	 * and the value is of type {@link net.sf.orcc.df.Port},
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Var To Port Map</em>' map isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Var To Port Map</em>' map.
	 * @see net.sf.orcc.df.DfPackage#getPattern_VarToPortMap()
	 * @model mapType="net.sf.orcc.df.VarToPortMapEntry<net.sf.orcc.ir.Var, net.sf.orcc.df.Port>"
	 * @generated
	 */
	EMap<Var, Port> getVarToPortMap();

} // Pattern
