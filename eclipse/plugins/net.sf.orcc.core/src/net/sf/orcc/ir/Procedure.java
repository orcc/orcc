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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.sf.orcc.ir.instructions.Load;
import net.sf.orcc.ir.instructions.Store;
import net.sf.orcc.ir.nodes.NodeBlock;
import net.sf.orcc.util.OrderedMap;

/**
 * This class defines a procedure.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class Procedure {

	/**
	 * Returns the first block in the list of nodes of the given procedure. A
	 * new block is created if there is no block in the given node list.
	 * 
	 * @param procedure
	 *            a procedure
	 * @return a block
	 */
	public NodeBlock getFirst() {
		return getFirst(getNodes());
	}

	/**
	 * Returns the first block in the given list of nodes. A new block is
	 * created if there is no block in the given node list.
	 * 
	 * @param procedure
	 *            a procedure
	 * @param nodes
	 *            a list of nodes of the given procedure
	 * @return a block
	 */
	public NodeBlock getFirst(List<CFGNode> nodes) {
		NodeBlock block;
		if (nodes.isEmpty()) {
			block = new NodeBlock(this);
			nodes.add(block);
		} else {
			CFGNode node = nodes.get(0);
			if (node.isBlockNode()) {
				block = (NodeBlock) node;
			} else {
				block = new NodeBlock(this);
				nodes.add(0, block);
			}
		}

		return block;
	}

	/**
	 * Returns the last block in the list of nodes of the given procedure. A new
	 * block is created if there is no block in the given node list.
	 * 
	 * @param procedure
	 *            a procedure
	 * @return a block
	 */
	public NodeBlock getLast() {
		return getLast(getNodes());
	}

	/**
	 * Returns the last block in the given list of nodes. A new block is created
	 * if there is no block in the given node list.
	 * 
	 * @param procedure
	 *            a procedure
	 * @param nodes
	 *            a list of nodes that are a subset of the given procedure's
	 *            nodes
	 * @return a block
	 */
	public NodeBlock getLast(List<CFGNode> nodes) {
		NodeBlock block;
		if (nodes.isEmpty()) {
			block = new NodeBlock(this);
			nodes.add(block);
		} else {
			CFGNode node = nodes.get(nodes.size() - 1);
			if (node.isBlockNode()) {
				block = (NodeBlock) node;
			} else {
				block = new NodeBlock(this);
				nodes.add(block);
			}
		}

		return block;
	}

	/**
	 * This class visits the procedure to find the state variables used.
	 * 
	 * @author Matthieu Wipliez
	 * 
	 */
	private class ProcVisitor extends AbstractActorVisitor {

		private Set<GlobalVariable> loadedVariables;

		private Set<GlobalVariable> storedVariables;

		public ProcVisitor() {
			storedVariables = new HashSet<GlobalVariable>();
			loadedVariables = new HashSet<GlobalVariable>();
		}

		public List<GlobalVariable> getLoadedVariables() {
			return new ArrayList<GlobalVariable>(loadedVariables);
		}

		public List<GlobalVariable> getStoredVariables() {
			return new ArrayList<GlobalVariable>(storedVariables);
		}

		@Override
		public void visit(Load node) {
			Variable var = node.getSource().getVariable();
			if (!var.getType().isList()) {
				loadedVariables.add((GlobalVariable) var);
			}
		}

		@Override
		public void visit(Store store) {
			Variable var = store.getTarget();
			if (!var.getType().isList()) {
				storedVariables.add((GlobalVariable) var);
			}
		}

	}

	private CFG graph;

	/**
	 * ordered map of local variables
	 */
	private OrderedMap<String, LocalVariable> locals;

	private Location location;

	/**
	 * the name of this procedure
	 */
	private String name;

	/**
	 * whether this procedure is external.
	 */
	private boolean nativeFlag;

	/**
	 * the list of nodes of this procedure
	 */
	private List<CFGNode> nodes;

	/**
	 * ordered map of parameters
	 */
	private OrderedMap<String, LocalVariable> parameters;

	private Expression result;

	/**
	 * the return type of this procedure
	 */
	private Type returnType;

	/**
	 * Creates a new procedure.
	 * 
	 * @param name
	 *            The procedure name.
	 * @param native Whether it is native or not.
	 * @param location
	 *            The procedure location.
	 * @param returnType
	 *            The procedure return type.
	 * @param parameters
	 *            The procedure parameters.
	 * @param locals
	 *            The procedure local variables.
	 */
	public Procedure(String name, boolean nativeFlag, Location location,
			Type returnType, OrderedMap<String, LocalVariable> parameters,
			OrderedMap<String, LocalVariable> locals, List<CFGNode> nodes) {
		this.location = location;
		this.nativeFlag = nativeFlag;
		this.nodes = nodes;
		this.locals = locals;
		this.name = name;
		this.parameters = parameters;
		this.returnType = returnType;
	}

	/**
	 * Creates a new procedure, not external, with empty parameters, locals, and
	 * nodes.
	 * 
	 * @param name
	 *            The procedure name.
	 * @param external
	 *            Whether it is external or not.
	 * @param location
	 *            The procedure location.
	 * @param returnType
	 *            The procedure return type.
	 */
	public Procedure(String name, Location location, Type returnType) {
		this.location = location;
		this.nativeFlag = false;
		this.nodes = new ArrayList<CFGNode>();
		this.locals = new OrderedMap<String, LocalVariable>();
		this.name = name;
		this.parameters = new OrderedMap<String, LocalVariable>();
		this.returnType = returnType;
	}

	/**
	 * Returns the CFG of this procedure. The CFG must be set by calling
	 * {@link #setGraph(CFG)}.
	 * 
	 * @return the CFG of this procedure
	 */
	public CFG getCFG() {
		return graph;
	}

	/**
	 * Computes and returns the list of scalar variables loaded by this
	 * procedure.
	 * 
	 * @return the list of scalar variables loaded by this procedure
	 */
	public List<GlobalVariable> getLoadedVariables() {
		ProcVisitor visitor = new ProcVisitor();
		visitor.visit(nodes);
		return visitor.getLoadedVariables();
	}

	/**
	 * Returns the local variables of this procedure as an ordered map.
	 * 
	 * @return the local variables of this procedure as an ordered map
	 */
	public OrderedMap<String, LocalVariable> getLocals() {
		return locals;
	}

	/**
	 * Returns the location of this procedure.
	 * 
	 * @return the location of this procedure
	 */
	public Location getLocation() {
		return location;
	}

	/**
	 * Returns the name of this procedure.
	 * 
	 * @return the name of this procedure
	 */
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
	public OrderedMap<String, LocalVariable> getParameters() {
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
	 * Computes and returns the list of scalar variables stored by this
	 * procedure.
	 * 
	 * @return the list of scalar variables stored by this procedure
	 */
	public List<GlobalVariable> getStoredVariables() {
		ProcVisitor visitor = new ProcVisitor();
		visitor.visit(nodes);
		return visitor.getStoredVariables();
	}

	/**
	 * Returns <code>true</code> if this procedure is native.
	 * 
	 * @return <code>true</code> if this procedure is native
	 */
	public boolean isNative() {
		return nativeFlag;
	}

	/**
	 * Creates a new local variable that can be used to hold intermediate
	 * results. The variable is added to {@link #procedure}'s locals.
	 * 
	 * @param file
	 *            the file in which this procedure resides
	 * @param type
	 *            type of the variable
	 * @param name
	 *            hint for the variable name
	 * @return a new local variable
	 */
	public LocalVariable newTempLocalVariable(String file, Type type,
			String hint) {
		String name = hint;
		LocalVariable variable = locals.get(name);
		int i = 0;
		while (variable != null) {
			name = hint + i;
			variable = locals.get(name);
			i++;
		}

		variable = new LocalVariable(true, 0, new Location(), name, type);
		locals.put(file, variable.getLocation(), variable.getName(), variable);
		return (LocalVariable) variable;
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

	public void setLocation(Location location) {
		this.location = location;
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
	 * Sets this procedure as "native".
	 * 
	 * @param nativeFlag
	 *            value of native flag
	 */
	public void setNative(boolean nativeFlag) {
		this.nativeFlag = nativeFlag;
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
		return name;
	}

}
