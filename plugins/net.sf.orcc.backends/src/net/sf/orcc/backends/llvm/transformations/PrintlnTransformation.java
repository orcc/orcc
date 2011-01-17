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

import net.sf.orcc.ir.Action;
import net.sf.orcc.ir.Actor;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.GlobalVariable;
import net.sf.orcc.ir.IrFactory;
import net.sf.orcc.ir.Procedure;
import net.sf.orcc.ir.Type;
import net.sf.orcc.ir.TypeInt;
import net.sf.orcc.ir.TypeString;
import net.sf.orcc.ir.TypeUint;
import net.sf.orcc.ir.Use;
import net.sf.orcc.ir.expr.StringExpr;
import net.sf.orcc.ir.expr.VarExpr;
import net.sf.orcc.ir.instructions.Call;
import net.sf.orcc.ir.transformations.AbstractActorTransformation;
import net.sf.orcc.util.OrderedMap;

/**
 * Change prinln instruction into printf llvm function fashion. Parameters of
 * println becomes state variables into the current actor
 * 
 * 
 * @author Jerome GORIN
 * 
 */
public class PrintlnTransformation extends AbstractActorTransformation {

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
	private OrderedMap<String, GlobalVariable> stateVars;

	/**
	 * String counter
	 */
	private int strCnt;

	@Override
	public void transform(Actor actor) {
		strCnt = 0;
		stateVars = actor.getStateVars();
		OrderedMap<String, Procedure> procs = actor.getProcs();
		Procedure print = procs.get("print");

		if (print != null) {
			procs.remove(print.getName());
		}

		for (Procedure proc : actor.getProcs()) {
			visit(proc);
		}

		for (Action action : actor.getActions()) {
			visit(action.getBody());
			visit(action.getScheduler());
		}

		for (Action action : actor.getInitializes()) {
			visit(action.getBody());
			visit(action.getScheduler());
		}

	}

	@Override
	public void visit(Call call) {
		if (call.isPrint()) {
			String value = "";
			List<Expression> parameters = new ArrayList<Expression>();
			String name = "str" + strCnt++;

			// Iterate though all the println arguments to provide an only
			// string value
			for (Expression expr : call.getParameters()) {
				if (expr.isStringExpr()) {
					String strExprVal = (((StringExpr) expr).getValue());
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

			LLVMString llvmStr = new LLVMString(value);

			// Create state variable that contains println arguments
			TypeString type = IrFactory.eINSTANCE.createTypeString();
			type.setSize(llvmStr.getSize());

			GlobalVariable variable = new GlobalVariable(call.getLocation(),
					type, name, false, new StringExpr(llvmStr.getStr()));
			Use use = new Use(variable);

			// Set the created state variable into call argument
			stateVars.put(name, variable);
			parameters.add(new VarExpr(use));

			for (Expression expr : call.getParameters()) {
				if (!expr.isStringExpr()) {
					parameters.add(expr);
				}
			}
			call.setParameters(parameters);
		}

	}

}
