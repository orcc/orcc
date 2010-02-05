/*
 * Copyright (c) 2009, IETR/INSA of Rennes
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
package net.sf.orcc.ir.nodes;

import java.util.List;

import net.sf.orcc.ir.CFGNode;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.Location;
import net.sf.orcc.ir.Procedure;
import net.sf.orcc.ir.ValueContainer;
import net.sf.orcc.ir.util.CommonNodeOperations;

/**
 * This class defines a While node. A while node is a node with a value used in
 * its condition.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class WhileNode extends AbstractNode implements ValueContainer {

	private BlockNode joinNode;

	private List<CFGNode> nodes;

	private Expression value;

	public WhileNode(Location location, Procedure procedure,
			Expression condition, List<CFGNode> nodes, BlockNode joinNode) {
		super(location, procedure);
		this.joinNode = joinNode;
		this.nodes = nodes;
		setValue(condition);
	}

	public WhileNode(Procedure procedure, Expression condition,
			List<CFGNode> nodes, BlockNode joinNode) {
		this(new Location(), procedure, condition, nodes, joinNode);
	}

	@Override
	public Object accept(NodeInterpreter interpreter, Object... args) {
		return interpreter.interpret(this, args);
	}

	@Override
	public void accept(NodeVisitor visitor, Object... args) {
		visitor.visit(this, args);
	}

	public BlockNode getJoinNode() {
		return joinNode;
	}

	public List<CFGNode> getNodes() {
		return nodes;
	}

	@Override
	public Expression getValue() {
		return value;
	}

	public void setJoinNode(BlockNode joinNode) {
		this.joinNode = joinNode;
	}

	@Override
	public void setValue(Expression value) {
		CommonNodeOperations.setValue(this, value);
	}

	@Override
	public void setValueSimple(Expression value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "while (" + getValue() + ")";
	}

}
