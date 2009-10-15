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
import java.util.List;

import net.sf.orcc.common.Location;
import net.sf.orcc.ir.actor.VarUse;
import net.sf.orcc.ir.expr.IExpr;
import net.sf.orcc.ir.nodes.AbstractNode;
import net.sf.orcc.ir.type.IType;

/**
 * @author Matthieu Wipliez
 * 
 */
public class VarDef implements Comparable<VarDef> {

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
	 * location of variable.
	 */
	private Location loc;

	/**
	 * name of variable.
	 */
	private String name;

	/**
	 * the node where the variable is assigned.
	 */
	private AbstractNode node;

	/**
	 * references to this variable.
	 */
	private List<VarUse> references;

	/**
	 * when local variables have the same name but different scopes.
	 */
	private Integer suffix;

	/**
	 * type of this variable.
	 */
	private IType type;

	public VarDef(boolean assignable, boolean global, int index, Location loc,
			String name, AbstractNode node, List<VarUse> references,
			Integer suffix, IType type) {
		this.assignable = assignable;
		this.global = global;
		this.index = index;
		this.loc = loc;
		this.name = name;
		this.node = node;
		this.references = references;
		this.suffix = suffix;
		this.type = type;
		this.constant = false;
		constantExpr = null;
	}

	public VarDef(VarDef other) {
		assignable = other.assignable;
		global = other.global;
		index = other.index;
		loc = new Location();
		name = other.name;
		node = null;
		references = new ArrayList<VarUse>();
		suffix = other.suffix;
		type = other.type;
		this.constant = false;
		constantExpr = null;
	}

	@Override
	public int compareTo(VarDef varDef) {
		return name.compareTo(varDef.name);
	}

	public void duplicate(VarDef varDef) {
		assignable = varDef.isAssignable();
		global = varDef.isGlobal();
		index = varDef.getIndex();
		loc = varDef.getLoc();
		name = varDef.getName();
		node = varDef.getNode();
		references = varDef.getReferences();
		if (varDef.hasSuffix()) {
			suffix = varDef.getSuffix();
		} else {
			suffix = null;
		}

		type = varDef.getType();
		this.constant = varDef.isConstant();
		constantExpr = varDef.getConstant();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof VarDef) {
			VarDef varDef = (VarDef) obj;
			boolean name = varDef.name.equals(this.name);
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

	public Location getLoc() {
		return loc;
	}

	public String getName() {
		return name;
	}

	public AbstractNode getNode() {
		return node;
	}

	public List<VarUse> getReferences() {
		return references;
	}

	public int getSuffix() {

		return suffix;
	}

	public IType getType() {
		return type;
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

	public void setLoc(Location loc) {
		this.loc = loc;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setNode(AbstractNode node) {
		this.node = node;
	}

	public void setReferences(List<VarUse> references) {
		this.references = references;
	}

	public void setSuffix(int suffix) {
		this.suffix = suffix;
	}

	public void setType(IType type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return name;
	}
}
