/*
 * Copyright (c) 2010, IRISA
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
 *   * Neither the name of the IRISA nor the names of its
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
package net.sf.orcc.backends.tta.transformations;

import java.util.List;

import net.sf.orcc.ir.Actor;
import net.sf.orcc.ir.ExprBinary;
import net.sf.orcc.ir.ExprUnary;
import net.sf.orcc.ir.Pattern;
import net.sf.orcc.ir.Port;
import net.sf.orcc.ir.Procedure;
import net.sf.orcc.ir.Type;
import net.sf.orcc.ir.TypeInt;
import net.sf.orcc.ir.TypeList;
import net.sf.orcc.ir.TypeUint;
import net.sf.orcc.ir.Var;
import net.sf.orcc.ir.util.AbstractActorVisitor;

/**
 * This class defines a transformation that changes size of variable to fit
 * types of TTA architectures
 * 
 * @author Herve Yviquel
 * 
 */
public class TtaTypeResizer extends AbstractActorVisitor<Object> {

	public TtaTypeResizer() {
		super(true);
	}

	@Override
	public Object caseActor(Actor actor) {
		checkVariables(actor.getParameters());
		checkVariables(actor.getStateVars());
		checkPorts(actor.getInputs(), 32);
		checkPorts(actor.getOutputs(), 32);

		return super.caseActor(actor);
	}

	@Override
	public Object caseExprBinary(ExprBinary expr) {
		checkType(expr.getType());
		return super.caseExprBinary(expr);
	}

	@Override
	public Object caseExprUnary(ExprUnary expr) {
		checkType(expr.getType());
		return super.caseExprUnary(expr);
	}

	@Override
	public Object casePattern(Pattern pattern) {
		for (Var var : pattern.getVariables()) {
			if (!pattern.getPort(var).isNative()) {
				checkType(var.getType(), 32);
			}
		}
		return null;
	}

	@Override
	public Object caseProcedure(Procedure procedure) {
		checkVariables(procedure.getParameters());
		checkVariables(procedure.getLocals());
		checkType(procedure.getReturnType());
		return super.caseProcedure(procedure);
	}

	private void checkPorts(List<Port> ports, int newSize) {
		for (Port port : ports) {
			if (!port.isNative()) {
				checkType(port.getType(), newSize);
			}
		}
	}

	private void checkType(Type type) {
		checkType(type, -1);
	}

	private void checkType(Type type, int newSize) {
		int size;

		if (type.isInt()) {
			TypeInt intType = (TypeInt) type;
			if (newSize == -1) {
				size = getIntSize(intType.getSize());
			} else {
				size = newSize;
			}
			intType.setSize(size);
		} else if (type.isUint()) {
			TypeUint uintType = (TypeUint) type;
			if (newSize == -1) {
				size = getIntSize(uintType.getSize());
			} else {
				size = newSize;
			}
			uintType.setSize(size);
		} else if (type.isList()) {
			TypeList listType = (TypeList) type;
			checkType(listType.getType(), newSize);
		}
	}

	private void checkVariables(List<Var> vars, int newSize) {
		for (Var var : vars) {
			checkType(var.getType(), newSize);
		}
	}

	private void checkVariables(List<Var> vars) {
		checkVariables(vars, -1);
	}

	private int getIntSize(int size) {
		if (size <= 8) {
			return 8;
		} else if (size <= 16) {
			return 16;
		} else if (size <= 32) {
			return 32;
		} else {
			return 64;
		}
	}
}
