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
 * This class defines a While node. A while node is a node with a value used in
 * its condition.
 * 
 * @author Matthieu Wipliez
 * @model extends="net.sf.orcc.ir.Node"
 * 
 */
public interface BlockWhile extends Block {

	/**
	 * Returns the condition of this node While.
	 * 
	 * @return the condition of this node While
	 * @model containment="true"
	 */
	Expression getCondition();

	/**
	 * Returns the join node of this NodeWhile.
	 * 
	 * @return the join node of this NodeWhile.
	 * @model containment="true"
	 */
	BlockBasic getJoinNode();

	/**
	 * Returns the line number on which this "while" starts.
	 * 
	 * @return the line number on which this "while" starts
	 * @model
	 */
	public int getLineNumber();

	/**
	 * Returns the nodes of this NodeWhile.
	 * 
	 * @return the nodes of this NodeWhile
	 * @model containment="true"
	 */
	EList<Block> getNodes();

	/**
	 * Sets the condition of this node While.
	 * 
	 * @param condition
	 *            the condition of this node While
	 */
	void setCondition(Expression condition);

	/**
	 * Sets the join node of this NodeWhile.
	 * 
	 * @param joinNode
	 *            the join node of this NodeWhile
	 */
	void setJoinNode(BlockBasic joinNode);

	/**
	 * Sets the line number on which this "while" starts.
	 * 
	 * @param newLineNumber
	 *            the line number on which this "while" starts
	 */
	public void setLineNumber(int newLineNumber);

}
