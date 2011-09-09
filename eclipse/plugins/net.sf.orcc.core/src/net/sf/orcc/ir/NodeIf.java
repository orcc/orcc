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
 * This class defines an If node. An if node is a node with a value used in its
 * condition.
 * 
 * @author Matthieu Wipliez
 * @model extends="net.sf.orcc.ir.Node"
 * 
 */
public interface NodeIf extends Node {

	/**
	 * Returns the condition of this node If.
	 * 
	 * @return the condition of this node If
	 * @model containment="true"
	 */
	Expression getCondition();

	/**
	 * Returns <code>true</code> if it is necessary to generate an "else" branch
	 * in the code.
	 * 
	 * @return <code>true</code> if it is necessary to generate an "else" branch
	 */
	boolean isElseRequired();

	/**
	 * Returns the nodes in the "else" branch of this NodeIf.
	 * 
	 * @return the nodes in the "else" branch of this NodeIf
	 * @model containment="true"
	 */
	EList<Node> getElseNodes();

	/**
	 * Returns the join node of this NodeIf.
	 * 
	 * @return the join node of this NodeIf.
	 * @model containment="true"
	 */
	NodeBlock getJoinNode();

	/**
	 * Returns the line number on which this "if" starts.
	 * 
	 * @return the line number on which this "if" starts
	 * @model
	 */
	public int getLineNumber();

	/**
	 * Returns the nodes in the "then" branch of this NodeIf.
	 * 
	 * @return the nodes in the "then" branch of this NodeIf
	 * @model containment="true"
	 */
	EList<Node> getThenNodes();

	/**
	 * Sets the condition of this node If.
	 * 
	 * @param condition
	 *            the condition of this node If
	 */
	void setCondition(Expression condition);

	/**
	 * Sets the join node of this NodeIf.
	 * 
	 * @param joinNode
	 *            the join node of this NodeIf
	 */
	void setJoinNode(NodeBlock joinNode);

	/**
	 * Sets the line number on which this "if" starts.
	 * 
	 * @param newLineNumber
	 *            the line number on which this "if" starts
	 */
	public void setLineNumber(int newLineNumber);

}
