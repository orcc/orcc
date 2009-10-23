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
package net.sf.orcc.common;

import java.util.List;

import net.sf.orcc.ir.expr.IExpr;
import net.sf.orcc.ir.nodes.AbstractNode;
import net.sf.orcc.ir.type.IType;

/**
 * This class represents a local variable. A local variable is a variable that
 * can be assigned, has a SSA index, and a reference to the node where it is
 * defined.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class LocalVariable extends Variable implements
		Comparable<LocalVariable> {

	/**
	 * whether the variable is assignable.
	 */
	private boolean assignable;

	/**
	 * Used for constant's propagation
	 */
	private boolean constant;

	private IExpr constantExpr;

	/**
	 * if the variable is global.
	 */
	private boolean global;

	/**
	 * if the variable is local, index for SSA. Meaningless otherwise.
	 */
	private int index;

	/**
	 * the node where the variable is assigned.
	 */
	private AbstractNode node;

	/**
	 * when local variables have the same name but different scopes.
	 */
	private Integer suffix;

	public LocalVariable(boolean assignable, boolean global, int index,
			Location loc, String name, AbstractNode node,
			List<Use> references, Integer suffix, IType type) {
		super(loc, type, name);
		this.assignable = assignable;
		this.global = global;
		this.index = index;
		this.node = node;
		this.suffix = suffix;
		this.constant = false;
		constantExpr = null;
	}

	public LocalVariable(LocalVariable other) {
		super(other);
		assignable = other.assignable;
		global = other.global;
		index = other.index;
		node = null;
		suffix = other.suffix;
		this.constant = false;
		constantExpr = null;
	}

	@Override
	public int compareTo(LocalVariable varDef) {
		return getName().compareTo(varDef.getName());
	}

	public void duplicate(LocalVariable varDef) {
		assignable = varDef.isAssignable();
		global = varDef.isGlobal();
		index = varDef.getIndex();
		setLocation(varDef.getLocation());
		setName(varDef.getName());
		node = varDef.getNode();
		if (varDef.hasSuffix()) {
			suffix = varDef.getSuffix();
		} else {
			suffix = null;
		}

		setType(varDef.getType());
		this.constant = varDef.isConstant();
		constantExpr = varDef.getConstant();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof LocalVariable) {
			LocalVariable varDef = (LocalVariable) obj;
			boolean name = varDef.getName().equals(this.getName());
			boolean suffix = hasSuffix() && varDef.hasSuffix()
					&& getSuffix() == varDef.getSuffix() || !hasSuffix()
					&& !varDef.hasSuffix();
			boolean index = (this.index == varDef.index);
			return name && suffix && index;
		}
		return false;
	}

	public IExpr getConstant() {
		return constantExpr;
	}

	public int getIndex() {
		return index;
	}

	public AbstractNode getNode() {
		return node;
	}

	public int getSuffix() {

		return suffix;
	}

	public boolean hasSuffix() {
		return (suffix != null);
	}

	public boolean isAssignable() {
		return assignable;
	}

	public boolean isConstant() {
		return constant;
	}

	public boolean isGlobal() {
		return global;
	}

	public void setAssignable(boolean assignable) {
		this.assignable = assignable;
	}

	public void setConstant(IExpr expr) {
		constantExpr = expr;
		constant = true;
	}

	public void setGlobal(boolean global) {
		this.global = global;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public void setNode(AbstractNode node) {
		this.node = node;
	}

	public void setSuffix(int suffix) {
		this.suffix = suffix;
	}
}
