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
package net.sf.orcc.ir;

import java.util.List;

import net.sf.orcc.ir.nodes.BlockNode;
import net.sf.orcc.ir.nodes.IfNode;
import net.sf.orcc.ir.nodes.NodeInterpreter;
import net.sf.orcc.ir.nodes.WhileNode;
import net.sf.orcc.util.INameable;
import net.sf.orcc.util.OrderedMap;

/**
 * This class defines a procedure.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class Procedure extends AbstractLocalizable implements INameable {

	/**
	 * This class defines a CFG builder.
	 * 
	 * @author Matthieu Wipliez
	 * 
	 */
	private class CFGBuilder implements NodeInterpreter {

		private CFG graph;

		/**
		 * Creates a new CFG builder.
		 */
		public CFGBuilder() {
			graph = new CFG();
		}

		/**
		 * Returns the CFG built.
		 * 
		 * @return the CFG built
		 */
		public CFG getCFG() {
			return graph;
		}

		@Override
		public Object interpret(BlockNode node, Object... args) {
			CFGNode previous = (CFGNode) args[0];
			graph.addVertex(node);
			if (previous != null) {
				graph.addEdge(previous, node);
			}

			return node;
		}

		@Override
		public Object interpret(IfNode node, Object... args) {
			CFGNode previous = (CFGNode) args[0];
			graph.addVertex(node);
			if (previous != null) {
				graph.addEdge(previous, node);
			}

			CFGNode join = node.getJoinNode();
			graph.addVertex(join);

			CFGNode last = (CFGNode) visit(node.getThenNodes(), node);
			graph.addEdge(last, join);

			last = (CFGNode) visit(node.getElseNodes(), node);
			graph.addEdge(last, join);

			return join;
		}

		@Override
		public Object interpret(WhileNode node, Object... args) {
			CFGNode previous = (CFGNode) args[0];
			graph.addVertex(node);
			if (previous != null) {
				graph.addEdge(previous, node);
			}

			CFGNode join = node.getJoinNode();
			graph.addVertex(join);
			graph.addEdge(node, join);

			CFGNode last = (CFGNode) visit(node.getNodes(), join);
			graph.addEdge(last, node);

			return node;
		}

		/**
		 * Visits the given node list.
		 * 
		 * @param nodes
		 *            a list of nodes
		 * @param previous
		 *            the previous node, or <code>null</code> if there is none
		 * @return the last node of the node list
		 */
		public Object visit(List<CFGNode> nodes, CFGNode previous) {
			Object last = previous;
			for (CFGNode node : nodes) {
				last = node.accept(this, last);
			}

			return last;
		}

	}

	/**
	 * whether this procedure is external.
	 */
	private boolean external;

	/**
	 * ordered map of local variables
	 */
	private OrderedMap<Variable> locals;

	/**
	 * the name of this procedure
	 */
	private String name;

	/**
	 * the list of nodes of this procedure
	 */
	private List<CFGNode> nodes;

	/**
	 * ordered map of parameters
	 */
	private OrderedMap<Variable> parameters;

	private Expression result;

	/**
	 * the return type of this procedure
	 */
	private Type returnType;

	/**
	 * Construcs a new procedure.
	 * 
	 * @param name
	 *            The procedure name.
	 * @param external
	 *            Whether it is external or not.
	 * @param location
	 *            The procedure location.
	 * @param returnType
	 *            The procedure return type.
	 * @param parameters
	 *            The procedure parameters.
	 * @param locals
	 *            The procedure local variables.
	 */
	public Procedure(String name, boolean external, Location location,
			Type returnType, OrderedMap<Variable> parameters,
			OrderedMap<Variable> locals, List<CFGNode> nodes) {
		super(location);
		this.external = external;
		this.nodes = nodes;
		this.locals = locals;
		this.name = name;
		this.parameters = parameters;
		this.returnType = returnType;
	}

	/**
	 * Builds and returns the CFG of this procedure.
	 * 
	 * @return the CFG built
	 */
	public CFG buildCFG() {
		CFGBuilder builder = new CFGBuilder();
		builder.visit(nodes, null);
		return builder.getCFG();
	}

	/**
	 * Returns the local variables of this procedure as an ordered map.
	 * 
	 * @return the local variables of this procedure as an ordered map
	 */
	public OrderedMap<Variable> getLocals() {
		return locals;
	}

	/**
	 * Returns the name of this procedure.
	 * 
	 * @return the name of this procedure
	 */
	@Override
	public String getName() {
		return name;
	}

	/**
	 * Returns the list of nodes of this procedure.
	 * 
	 * @return the list of nodes of this procedure
	 */
	public List<CFGNode> getNodes() {
		return nodes;
	}

	/**
	 * Returns the parameters of this procedure as an ordered map.
	 * 
	 * @return the parameters of this procedure as an ordered map
	 */
	public OrderedMap<Variable> getParameters() {
		return parameters;
	}

	public Expression getResult() {
		return result;
	}

	/**
	 * Returns the return type of this procedure.
	 * 
	 * @return the return type of this procedure
	 */
	public Type getReturnType() {
		return returnType;
	}

	public boolean isExternal() {
		return external;
	}

	public void setExternal(boolean external) {
		this.external = external;
	}

	/**
	 * Sets the name of this procedure.
	 * 
	 * @param name
	 *            the new name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Sets the node list of this procedure.
	 * 
	 * @param nodes
	 *            a list of CFG nodes
	 */
	public void setNodes(List<CFGNode> nodes) {
		this.nodes = nodes;
	}

	public void setResult(Expression result) {
		this.result = result;
	}

	/**
	 * Sets the return type of this procedure
	 * 
	 * @param returnType
	 *            a type
	 */
	public void setReturnType(Type returnType) {
		this.returnType = returnType;
	}

	@Override
	public String toString() {
		return Printer.getInstance().toString(this);
	}

}
