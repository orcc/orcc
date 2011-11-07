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
package net.sf.orcc.backends.llvm.transformations;

import java.util.ArrayList;
import java.util.List;

import net.sf.orcc.df.Actor;
import net.sf.orcc.ir.Arg;
import net.sf.orcc.ir.ArgByVal;
import net.sf.orcc.ir.ExprString;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.InstCall;
import net.sf.orcc.ir.IrFactory;
import net.sf.orcc.ir.Type;
import net.sf.orcc.ir.TypeInt;
import net.sf.orcc.ir.TypeString;
import net.sf.orcc.ir.TypeUint;
import net.sf.orcc.ir.Var;
import net.sf.orcc.ir.util.AbstractActorVisitor;

import org.eclipse.emf.common.util.EList;

/**
 * Change prinln instruction into printf llvm function fashion. Parameters of
 * println becomes state variables into the current actor
 * 
 * 
 * @author Jerome GORIN
 * 
 */
public class PrintlnTransformation extends AbstractActorVisitor<Object> {

	/**
	 * Change characters in strings to fit LLVM constraints
	 * 
	 * 
	 * @author Jerome GORIN
	 * 
	 */
	private class LLVMString {

		private StringBuffer llvmStr;

		private int size;

		LLVMString(String str) {
			// Replace characters unsupported by llvm
			size = str.length() + 1;
			llvmStr = new StringBuffer(str + "\\00");
			replace("\\n", "\\0A");
			replace("\\t", "\\09");
		}

		int getSize() {
			return size;
		}

		String getStr() {
			return llvmStr.toString();
		}

		void replace(String car, String newCar) {
			int index = llvmStr.indexOf(car);
			while (index != -1) {
				llvmStr.delete(index, index + car.length());
				llvmStr.insert(index, newCar);
				index = llvmStr.indexOf(car);
				size -= 1;
			}

		}
	}

	/**
	 * State variables of the actor
	 */
	private EList<Var> stateVars;

	/**
	 * String counter
	 */
	private int strCnt;

	@Override
	public Object caseActor(Actor actor) {
		strCnt = 0;
		stateVars = actor.getStateVars();

		actor.getProcs().remove(actor.getProcedure("print"));

		return super.caseActor(actor);
	}

	@Override
	public Object caseInstCall(InstCall call) {
		if (call.isPrint()) {
			String value = "";
			List<Arg> newParameters = new ArrayList<Arg>();
			List<Arg> parameters = call.getParameters();
			String name = "str" + strCnt++;

			// Iterate though all the println arguments to provide an only
			// string value
			for (Arg arg : parameters) {
				if (arg.isByVal()) {
					Expression expr = ((ArgByVal) arg).getValue();
					if (expr.isStringExpr()) {
						String strExprVal = (((ExprString) expr).getValue());
						value += strExprVal;
					} else {
						Type type = expr.getType();
						if (type.isBool()) {
							value += "%i";
						} else if (type.isFloat()) {
							value += "%f";
						} else if (type.isInt()) {
							TypeInt intType = (TypeInt) type;
							if (intType.isLong()) {
								value += "%ll";
							} else {
								value += "%i";
							}
						} else if (type.isList()) {
							value += "%p";
						} else if (type.isString()) {
							value += "%s";
						} else if (type.isUint()) {
							TypeUint uintType = (TypeUint) type;
							if (uintType.isLong()) {
								value += "%ll";
							} else {
								value += "%u";
							}
						} else if (type.isVoid()) {
							value += "%p";
						}
					}
				}
			}

			LLVMString llvmStr = new LLVMString(value);

			// Create state variable that contains println arguments
			TypeString type = IrFactory.eINSTANCE.createTypeString();
			type.setSize(llvmStr.getSize());

			Var variable = IrFactory.eINSTANCE.createVar(call.getLineNumber(),
					type, name, true, false);
			variable.setInitialValue(IrFactory.eINSTANCE
					.createExprString(llvmStr.getStr()));

			// Set the created state variable into call argument
			stateVars.add(variable);
			newParameters.add(IrFactory.eINSTANCE.createArgByVal(variable));

			for (Arg arg : parameters) {
				if (arg.isByVal()) {
					Expression expr = ((ArgByVal) arg).getValue();
					if (!expr.isStringExpr()) {
						newParameters.add(arg);
					}
				}
			}
			parameters.clear();
			parameters.addAll(newParameters);
		}
		return null;
	}

}
