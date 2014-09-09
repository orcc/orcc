/*
 * Copyright (c) 2010, IETR/INSA of Rennes
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
package net.sf.orcc.df.transform;

import java.util.List;

import net.sf.orcc.df.Actor;
import net.sf.orcc.df.Pattern;
import net.sf.orcc.df.Port;
import net.sf.orcc.df.util.DfVisitor;
import net.sf.orcc.ir.ExprBinary;
import net.sf.orcc.ir.ExprBool;
import net.sf.orcc.ir.ExprInt;
import net.sf.orcc.ir.ExprUnary;
import net.sf.orcc.ir.IrFactory;
import net.sf.orcc.ir.Param;
import net.sf.orcc.ir.Procedure;
import net.sf.orcc.ir.Type;
import net.sf.orcc.ir.TypeInt;
import net.sf.orcc.ir.TypeList;
import net.sf.orcc.ir.TypeUint;
import net.sf.orcc.ir.Var;
import net.sf.orcc.ir.util.AbstractIrVisitor;

import org.eclipse.emf.ecore.util.EcoreUtil;

/**
 * This class defines a transformation that changes size of variable to fit
 * types of general-purpose programming language such as C, C++ or Java.
 * 
 * @author Jerome Gorin
 * @author Herve Yviquel
 * 
 */
public class TypeResizer extends DfVisitor<Void> {

	protected class InnerTypeResizer extends AbstractIrVisitor<Void> {
		public InnerTypeResizer() {
			super(true);
		}

		@Override
		public Void caseExprBinary(ExprBinary expr) {
			Type type = expr.getType();
			if (!type.isBool()) {
				checkType(type);
			}
			return super.caseExprBinary(expr);
		}

		@Override
		public Void caseExprUnary(ExprUnary expr) {
			Type type = expr.getType();
			if (!type.isBool()) {
				checkType(type);
			}
			return super.caseExprUnary(expr);
		}

		@Override
		public Void caseExprBool(ExprBool expr) {
			if (castBoolToInt) {
				ExprInt newExpr = IrFactory.eINSTANCE.createExprInt(expr
						.isValue() ? 1 : 0);
				EcoreUtil.replace(expr, newExpr);
				return doSwitch(newExpr);
			}
			return null;
		}

		@Override
		public Void caseExprInt(ExprInt expr) {
			checkType(expr.getType());
			return null;
		}

		@Override
		public Void caseProcedure(Procedure procedure) {
			checkParameters(procedure.getParameters());
			checkVariables(procedure.getLocals());
			checkType(procedure.getReturnType());
			return super.caseProcedure(procedure);
		}
	}

	private boolean castNativePort;
	private boolean castTo32bits;
	private boolean castToPow2bits;
	private boolean castBoolToInt;

	public TypeResizer(boolean castToPow2bits, boolean castTo32bits,
			boolean castNativePort, boolean castBoolToInt) {
		this.castToPow2bits = castToPow2bits;
		this.castTo32bits = castTo32bits;
		this.castNativePort = castNativePort;
		this.castBoolToInt = castBoolToInt;
		this.irVisitor = new InnerTypeResizer();
	}

	@Override
	public Void caseActor(Actor actor) {
		checkVariables(actor.getParameters());
		checkVariables(actor.getStateVars());
		return super.caseActor(actor);
	}

	@Override
	public Void casePattern(Pattern pattern) {
		checkVariables(pattern.getVariables());
		return null;
	}

	@Override
	public Void casePort(Port port) {
		if (castNativePort || !port.isNative()) {
			checkType(port.getType());
		}
		return null;
	}

	private void checkParameters(List<Param> parameters) {
		for (Param param : parameters) {
			checkType(param.getVariable().getType());
		}
	}

	private void checkType(Type type) {
		int size;

		if (type.isInt()) {
			TypeInt intType = (TypeInt) type;
			size = getIntSize(intType.getSize());
			intType.setSize(size);
		} else if (type.isUint()) {
			TypeUint uintType = (TypeUint) type;
			size = getIntSize(uintType.getSize());
			uintType.setSize(size);
		} else if (type.isList()) {
			TypeList listType = (TypeList) type;
			checkType(listType.getType());
		} else if (castBoolToInt && type.isBool()) {
			TypeUint newType = IrFactory.eINSTANCE
					.createTypeUint(getIntSize(type.getSizeInBits()));
			EcoreUtil.replace(type, newType);
		}
	}

	private void checkVariables(List<Var> vars) {
		for (Var var : vars) {
			checkType(var.getType());
		}
	}

	private int getIntSize(int size) {
		if (castToPow2bits) {
			if (size <= 8) {
				return 8;
			} else if (size <= 16) {
				return 16;
			} else if (size <= 32 || castTo32bits) {
				return 32;
			} else {
				return 64;
			}
		} else {
			if (size > 32) {
				if (castTo32bits) {
					return 32;
				} else {
					return 64;
				}
			} else {
				return size;
			}
		}
	}
}
