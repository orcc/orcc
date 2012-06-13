/*
 * Copyright (c) 2012, Synflow
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

import net.sf.orcc.graph.Graph;
import net.sf.orcc.graph.Vertex;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->This interface defines a Control-Flow Graph.<!--
 * end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link net.sf.orcc.ir.Cfg#getEntry <em>Entry</em>}</li>
 *   <li>{@link net.sf.orcc.ir.Cfg#getExit <em>Exit</em>}</li>
 * </ul>
 * </p>
 *
 * @see net.sf.orcc.ir.IrPackage#getCfg()
 * @model
 * @generated
 */
public interface Cfg extends Graph {

	/**
	 * Computes the dominance and post-dominance information
	 */
	void computeDominance();

	/**
	 * Returns the value of the '<em><b>Entry</b></em>' reference. <!--
	 * begin-user-doc --><!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Entry</em>' reference.
	 * @see #setEntry(CfgNode)
	 * @see net.sf.orcc.ir.IrPackage#getCfg_Entry()
	 * @model
	 * @generated
	 */
	CfgNode getEntry();

	/**
	 * Returns the value of the '<em><b>Exit</b></em>' reference. <!--
	 * begin-user-doc --><!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Exit</em>' reference.
	 * @see #setExit(CfgNode)
	 * @see net.sf.orcc.ir.IrPackage#getCfg_Exit()
	 * @model
	 * @generated
	 */
	CfgNode getExit();

	/**
	 * Returns the list of this CFG's nodes. This returns the same as
	 * {@link #getVertices()} but as a list of {@link CfgNode}s rather than as a
	 * list of vertices.
	 * 
	 * @return the list of nodes
	 */
	EList<CfgNode> getNodes();

	/**
	 * Returns <code>true</code> if the given <code>m</code> node
	 * <i>immediately</i> dominates the given <code>n</code> node. The dominance
	 * information must have been computed first.
	 * 
	 * @param m
	 *            a CFG node
	 * @param n
	 *            a CFG node
	 * @return <code>true</code> if <code>m</code> immediately dominates
	 *         <code>n</code>
	 * @throws IllegalStateException
	 *             if the dominance information is not available
	 */
	boolean immediatelyDominates(Vertex m, Vertex n);

	/**
	 * Returns <code>true</code> if the given <code>m</code> node
	 * <i>immediately</i> post-dominates the given <code>n</code> node. The
	 * dominance information must have been computed first.
	 * 
	 * @param m
	 *            a CFG node
	 * @param n
	 *            a CFG node
	 * @return <code>true</code> if <code>m</code> immediately post-dominates
	 *         <code>n</code>
	 * @throws IllegalStateException
	 *             if the dominance information is not available
	 */
	boolean immediatelyPostDominates(Vertex m, Vertex n);

	/**
	 * Returns <code>true</code> if the given node is a loop-starting node,
	 * which is a node that has two incoming edges, of which one is a back edge.
	 * 
	 * @param node
	 *            a node of this CFG
	 * @return <code>true</code> if the node is a loop-starting node
	 */
	boolean isLoop(Vertex node);

	/**
	 * Sets the value of the '{@link net.sf.orcc.ir.Cfg#getEntry <em>Entry</em>}' reference.
	 * <!-- begin-user-doc --><!-- end-user-doc -->
	 * @param value the new value of the '<em>Entry</em>' reference.
	 * @see #getEntry()
	 * @generated
	 */
	void setEntry(CfgNode value);

	/**
	 * Sets the value of the '{@link net.sf.orcc.ir.Cfg#getExit <em>Exit</em>}' reference.
	 * <!-- begin-user-doc --><!-- end-user-doc -->
	 * @param value the new value of the '<em>Exit</em>' reference.
	 * @see #getExit()
	 * @generated
	 */
	void setExit(CfgNode value);

}
