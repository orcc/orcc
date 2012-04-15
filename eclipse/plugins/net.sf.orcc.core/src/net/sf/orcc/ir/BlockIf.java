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
package net.sf.orcc.ir;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc --> This class defines an If block. An if block is a
 * block with a value used in its condition.
 * 
 * @author Matthieu Wipliez<!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link net.sf.orcc.ir.BlockIf#getCondition <em>Condition</em>}</li>
 *   <li>{@link net.sf.orcc.ir.BlockIf#getElseNodes <em>Else Nodes</em>}</li>
 *   <li>{@link net.sf.orcc.ir.BlockIf#getJoinNode <em>Join Node</em>}</li>
 *   <li>{@link net.sf.orcc.ir.BlockIf#getLineNumber <em>Line Number</em>}</li>
 *   <li>{@link net.sf.orcc.ir.BlockIf#getThenNodes <em>Then Nodes</em>}</li>
 * </ul>
 * </p>
 *
 * @see net.sf.orcc.ir.IrPackage#getBlockIf()
 * @model
 * @generated
 */
public interface BlockIf extends Block {

	/**
	 * Returns the value of the '<em><b>Condition</b></em>' containment reference.
	 * <!-- begin-user-doc --><!-- end-user-doc -->
	 * @return the value of the '<em>Condition</em>' containment reference.
	 * @see #setCondition(Expression)
	 * @see net.sf.orcc.ir.IrPackage#getBlockIf_Condition()
	 * @model containment="true"
	 * @generated
	 */
	Expression getCondition();

	/**
	 * Returns the value of the '<em><b>Else Nodes</b></em>' containment
	 * reference list. The list contents are of type
	 * {@link net.sf.orcc.ir.Block}. <!-- begin-user-doc --><!-- end-user-doc
	 * -->
	 * 
	 * @return the value of the '<em>Else Nodes</em>' containment reference
	 *         list.
	 * @see net.sf.orcc.ir.IrPackage#getBlockIf_ElseNodes()
	 * @model containment="true"
	 * @generated
	 */
	EList<Block> getElseNodes();

	/**
	 * Returns the value of the '<em><b>Join Node</b></em>' containment reference.
	 * <!-- begin-user-doc --><!-- end-user-doc -->
	 * @return the value of the '<em>Join Node</em>' containment reference.
	 * @see #setJoinNode(BlockBasic)
	 * @see net.sf.orcc.ir.IrPackage#getBlockIf_JoinNode()
	 * @model containment="true"
	 * @generated
	 */
	BlockBasic getJoinNode();

	/**
	 * Returns the value of the '<em><b>Line Number</b></em>' attribute. <!--
	 * begin-user-doc --> Returns the line number on which this "if" starts.<!--
	 * end-user-doc -->
	 * 
	 * @return the value of the '<em>Line Number</em>' attribute.
	 * @see #setLineNumber(int)
	 * @see net.sf.orcc.ir.IrPackage#getBlockIf_LineNumber()
	 * @model
	 * @generated
	 */
	int getLineNumber();

	/**
	 * Returns the value of the '<em><b>Then Nodes</b></em>' containment reference list.
	 * The list contents are of type {@link net.sf.orcc.ir.Block}.
	 * <!-- begin-user-doc --> Returns the nodes
	 * in the "then" branch.<!-- end-user-doc -->
	 * @return the value of the '<em>Then Nodes</em>' containment reference list.
	 * @see net.sf.orcc.ir.IrPackage#getBlockIf_ThenNodes()
	 * @model containment="true"
	 * @generated
	 */
	EList<Block> getThenNodes();

	/**
	 * Returns <code>true</code> if it is necessary to generate an "else" branch
	 * in the code.
	 * 
	 * @return <code>true</code> if it is necessary to generate an "else" branch
	 */
	boolean isElseRequired();

	/**
	 * @generated
	 */
	void setCondition(Expression value);

	/**
	 * @generated
	 */
	void setJoinNode(BlockBasic value);

	/**
	 * Sets the value of the '{@link net.sf.orcc.ir.BlockIf#getLineNumber <em>Line Number</em>}' attribute.
	 * <!-- begin-user-doc -->Sets the line
	 * number on which this "if" starts. <!-- end-user-doc -->
	 * @param value the new value of the '<em>Line Number</em>' attribute.
	 * @see #getLineNumber()
	 * @generated
	 */
	void setLineNumber(int value);

}
