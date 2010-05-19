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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;

import net.sf.orcc.ir.instructions.AbstractInstructionVisitor;
import net.sf.orcc.ir.instructions.Load;
import net.sf.orcc.ir.instructions.SpecificInstruction;
import net.sf.orcc.ir.instructions.Store;
import net.sf.orcc.ir.nodes.BlockNode;
import net.sf.orcc.ir.nodes.IfNode;
import net.sf.orcc.ir.nodes.NodeVisitor;
import net.sf.orcc.ir.nodes.WhileNode;
import net.sf.orcc.ir.type.VoidType;
import net.sf.orcc.util.INameable;
import net.sf.orcc.util.OrderedMap;

/**
 * This class defines a procedure.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class Procedure extends AbstractLocalizable implements INameable {

	public static final Procedure print = new Procedure("print", true,
			new Location(), new VoidType(), new OrderedMap<Variable>(),
			new OrderedMap<Variable>(), new ArrayList<CFGNode>());

	/**
	 * This class visits the procedure to find the state variables used.
	 * 
	 * @author Matthieu Wipliez
	 * 
	 */
	private class ProcVisitor extends AbstractInstructionVisitor implements
			NodeVisitor {

		private Set<StateVariable> vars;

		public ProcVisitor() {
			vars = new HashSet<StateVariable>();
		}

		public List<StateVariable> getStateVarsUsed() {
			return Arrays.asList(vars.toArray(new StateVariable[0]));
		}

		@Override
		public void visit(BlockNode node, Object... args) {
			ListIterator<Instruction> it = node.listIterator();
			while (it.hasNext()) {
				it.next().accept(this, it);
			}
		}

		@Override
		public void visit(IfNode node, Object... args) {
			visit(node.getThenNodes());
			visit(node.getElseNodes());
			visit(node.getJoinNode(), args);
		}

		/**
		 * Visits the nodes of the given node list.
		 * 
		 * @param nodes
		 *            a list of nodes that belong to a procedure
		 * @param args
		 *            arguments
		 */
		public void visit(List<CFGNode> nodes, Object... args) {
			ListIterator<CFGNode> it = nodes.listIterator();
			while (it.hasNext()) {
				CFGNode node = it.next();
				node.accept(this, it);
			}
		}

		@Override
		public void visit(Load node, Object... args) {
			Variable var = node.getSource().getVariable();
			if (!var.getType().isList()) {
				vars.add((StateVariable) var);
			}
		}

		@Override
		public void visit(SpecificInstruction specific, Object... args) {
		}

		@Override
		public void visit(Store node, Object... args) {
			Variable var = node.getTarget().getVariable();
			if (!var.getType().isList()) {
				vars.add((StateVariable) var);
			}
		}

		@Override
		public void visit(WhileNode node, Object... args) {
			visit(node.getNodes());
			visit(node.getJoinNode(), args);
		}

	}

	/**
	 * whether this procedure is external.
	 */
	private boolean external;

	private CFG graph;

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
	 * Returns the CFG of this procedure. The CFG must be set by calling
	 * {@link #buildCFG()} transformation.
	 * 
	 * @return the CFG of this procedure
	 */
	public CFG getCFG() {
		return graph;
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

	/**
	 * Computes and returns the list of state variables used (loaded or stored)
	 * by this procedure.
	 * 
	 * @return the list of state variables used (loaded or stored) by this
	 *         procedure
	 */
	public List<StateVariable> getStateVarsUsed() {
		ProcVisitor visitor = new ProcVisitor();
		visitor.visit(nodes);
		return visitor.getStateVarsUsed();
	}

	public boolean isExternal() {
		return external;
	}

	public void setExternal(boolean external) {
		this.external = external;
	}

	/**
	 * Set the CFG of this procedure.
	 * 
	 * @param the
	 *            CFG of this procedure
	 */
	public void setGraph(CFG graph) {
		this.graph = graph;
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
