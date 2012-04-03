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

import net.sf.dftools.graph.Graph;
import net.sf.dftools.graph.Vertex;

import org.eclipse.emf.common.util.EList;

/**
 * This interface defines a Control-Flow Graph.
 * 
 * @author Matthieu Wipliez
 * @model
 */
public interface Cfg extends Graph {

	/**
	 * Returns the entry node of this CFG.
	 * 
	 * @return the entry node of this CFG
	 * @model
	 */
	CfgNode getEntry();

	/**
	 * Returns the exit node of this CFG.
	 * 
	 * @return the exit node of this CFG
	 * @model
	 */
	CfgNode getExit();

	/**
	 * Returns the list of this CFG's nodes. This returns the same as
	 * {@link #getVertices()} but as a list of {@link CfgNode}s rather than as a
	 * list of {@link Vertex}s.
	 * 
	 * @return the list of nodes
	 */
	EList<CfgNode> getNodes();

	/**
	 * Returns <code>true</code> if the given <code>m</code> node
	 * <i>immediately</i> dominates the given <code>n</code> node. If the
	 * dominance information has not been computed yet, it is computed now.
	 * 
	 * @param m
	 *            a CFG node
	 * @param n
	 *            a CFG node
	 * @return <code>true</code> if <code>m</code> immediately dominates
	 *         <code>n</code>
	 */
	boolean immediatelyDominates(CfgNode m, CfgNode n);

	/**
	 * Sets the entry node of this CFG.
	 * 
	 * @param entry
	 *            the new entry node
	 */
	void setEntry(CfgNode entry);

	/**
	 * Sets the exit node of this CFG.
	 * 
	 * @param exit
	 *            the new exit node
	 */
	void setExit(CfgNode exit);

}
