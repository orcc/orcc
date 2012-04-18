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
 * <!-- begin-user-doc -->This class defines a While block. A while block is a
 * block with a value used in its condition.<!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link net.sf.orcc.ir.BlockWhile#getCondition <em>Condition</em>}</li>
 *   <li>{@link net.sf.orcc.ir.BlockWhile#getJoinBlock <em>Join Block</em>}</li>
 *   <li>{@link net.sf.orcc.ir.BlockWhile#getLineNumber <em>Line Number</em>}</li>
 *   <li>{@link net.sf.orcc.ir.BlockWhile#getBlocks <em>Blocks</em>}</li>
 * </ul>
 * </p>
 *
 * @see net.sf.orcc.ir.IrPackage#getBlockWhile()
 * @model
 * @generated
 */
public interface BlockWhile extends Block {

	/**
	 * Returns the value of the '<em><b>Condition</b></em>' containment reference.
	 * <!-- begin-user-doc --><!-- end-user-doc -->
	 * @return the value of the '<em>Condition</em>' containment reference.
	 * @see #setCondition(Expression)
	 * @see net.sf.orcc.ir.IrPackage#getBlockWhile_Condition()
	 * @model containment="true"
	 * @generated
	 */
	Expression getCondition();

	@Deprecated
	BlockBasic getJoinNode();

	/**
	 * Returns the value of the '<em><b>Line Number</b></em>' attribute. <!--
	 * begin-user-doc -->Returns the line number on which this "while" starts.
	 * 
	 * @return the line number on which this "while" starts<!-- end-user-doc -->
	 * @return the value of the '<em>Line Number</em>' attribute.
	 * @see #setLineNumber(int)
	 * @see net.sf.orcc.ir.IrPackage#getBlockWhile_LineNumber()
	 * @model
	 * @generated
	 */
	int getLineNumber();

	@Deprecated
	EList<Block> getNodes();

	/**
	 * Sets the value of the '{@link net.sf.orcc.ir.BlockWhile#getCondition <em>Condition</em>}' containment reference.
	 * <!-- begin-user-doc --><!-- end-user-doc -->
	 * @param value the new value of the '<em>Condition</em>' containment reference.
	 * @see #getCondition()
	 * @generated
	 */
	void setCondition(Expression value);

	/**
	 * Returns the value of the '<em><b>Join Block</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Join Block</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Join Block</em>' containment reference.
	 * @see #setJoinBlock(BlockBasic)
	 * @see net.sf.orcc.ir.IrPackage#getBlockWhile_JoinBlock()
	 * @model containment="true"
	 * @generated
	 */
	BlockBasic getJoinBlock();

	/**
	 * Sets the value of the '{@link net.sf.orcc.ir.BlockWhile#getJoinBlock <em>Join Block</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Join Block</em>' containment reference.
	 * @see #getJoinBlock()
	 * @generated
	 */
	void setJoinBlock(BlockBasic value);

	@Deprecated
	void setJoinNode(BlockBasic value);

	/**
	 * Sets the value of the '{@link net.sf.orcc.ir.BlockWhile#getLineNumber <em>Line Number</em>}' attribute.
	 * <!-- begin-user-doc -->Sets the line number on which this "while" starts.
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Line Number</em>' attribute.
	 * @see #getLineNumber()
	 * @generated
	 */
	void setLineNumber(int value);

	/**
	 * Returns the value of the '<em><b>Blocks</b></em>' containment reference list.
	 * The list contents are of type {@link net.sf.orcc.ir.Block}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Blocks</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Blocks</em>' containment reference list.
	 * @see net.sf.orcc.ir.IrPackage#getBlockWhile_Blocks()
	 * @model containment="true"
	 * @generated
	 */
	EList<Block> getBlocks();

}
