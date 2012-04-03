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
package net.sf.orcc.ir;

import net.sf.dftools.graph.Vertex;

/**
 * This class defines a node of a CFG graph. In addition to a vertex, a CFG node
 * can be associated to a Node of a procedure (NodeBlock, NodeIf, NodeWhile). It
 * also has a post-order number, useful for computing dominators.
 * 
 * @model
 */
public interface CfgNode extends Vertex {

	/**
	 * Returns the Node associated with this CFG node.
	 * 
	 * @return the Node associated with this CFG node
	 * @model
	 */
	Node getNode();

	/**
	 * Returns the post-order number of this CFG node.
	 * 
	 * @return the post-order number of this CFG node
	 * @model transient="true"
	 */
	int getNumber();

	/**
	 * Sets the Node associated with this CFG node.
	 * 
	 * @param node
	 *            the Node to associate with this CFG node
	 */
	void setNode(Node node);

	/**
	 * Sets the post-order number of this CFG node.
	 * 
	 * @param number
	 *            the post-order number of this CFG node
	 */
	void setNumber(int number);

}
