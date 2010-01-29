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
package net.sf.orcc.backends.c.instructions;

import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.Location;
import net.sf.orcc.ir.ValueContainer;
import net.sf.orcc.ir.Variable;
import net.sf.orcc.ir.expr.BinaryOp;
import net.sf.orcc.ir.util.CommonNodeOperations;

/**
 * This class defines a SelfAssignment instruction that implements a
 * self-assignment. A self-assignment is an assignment of a binary expression to
 * a Variable, global or local, where one term of the expression is the target
 * variable. Example: <code>x := x * 3;</code> could be replaced by a
 * self-assigment <code>x *= 3;</code>
 * 
 * @author Matthieu Wipliez
 * 
 */
public class SelfAssignment extends AbstractCInstruction implements
		ValueContainer {

	private BinaryOp op;

	private Expression value;

	private Variable target;

	public SelfAssignment(Location location, Variable target, BinaryOp op,
			Expression value) {
		super(location);
		this.op = op;
		this.value = value;
		this.target = target;
	}

	@Override
	public void accept(CInstructionVisitor visitor, Object... args) {
		visitor.visit(this, args);
	}

	public BinaryOp getOp() {
		return op;
	}

	@Override
	public Expression getValue() {
		return value;
	}

	public Variable getTarget() {
		return target;
	}

	public void setOp(BinaryOp op) {
		this.op = op;
	}

	@Override
	public void setValue(Expression value) {
		CommonNodeOperations.setValue(this, value);
	}

	@Override
	public void setValueSimple(Expression value) {
		this.value = value;
	}

	public void setTarget(Variable target) {
		this.target = target;
	}

}
