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
package net.sf.orcc.backends.c.nodes;

import net.sf.orcc.ir.IExpr;
import net.sf.orcc.ir.Location;
import net.sf.orcc.ir.Variable;
import net.sf.orcc.ir.expr.BinaryOp;

/**
 * This class defines a SelfAssignment node that implements a self-assignment. A
 * self-assignment is an assignment of a binary expression to a Variable, global
 * or local, where one term of the expression is the target variable. Example:
 * <code>x := x * 3;</code> could be replaced by a self-assigment
 * <code>x *= 3;</code>
 * 
 * @author Matthieu Wipliez
 * 
 */
public class SelfAssignment extends AbstractCNode {

	private BinaryOp op;

	private IExpr value;

	private Variable variable;

	public SelfAssignment(int id, Location location, Variable variable,
			BinaryOp op, IExpr value) {
		super(id, location);
		this.op = op;
		this.value = value;
		this.variable = variable;
	}

	@Override
	public void accept(CNodeVisitor visitor, Object... args) {
		visitor.visit(this, args);
	}

	public BinaryOp getOp() {
		return op;
	}

	public IExpr getValue() {
		return value;
	}

	public Variable getVar() {
		return variable;
	}

	public void setOp(BinaryOp op) {
		this.op = op;
	}

	public void setValue(IExpr value) {
		this.value = value;
	}

	public void setVar(Variable variable) {
		this.variable = variable;
	}

}
