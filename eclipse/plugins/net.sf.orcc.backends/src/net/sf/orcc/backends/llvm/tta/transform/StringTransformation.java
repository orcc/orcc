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
package net.sf.orcc.backends.llvm.tta.transform;

import java.util.ArrayList;

import net.sf.orcc.df.Actor;
import net.sf.orcc.ir.Arg;
import net.sf.orcc.ir.ArgByVal;
import net.sf.orcc.ir.ExprString;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.InstCall;
import net.sf.orcc.ir.Param;
import net.sf.orcc.ir.Procedure;
import net.sf.orcc.ir.Var;

/**
 * Change string into global constant. Parameters of println becomes state
 * variables into the current actor
 * 
 * @author Jerome Gorin
 * @author Herve Yviquel
 * 
 */
public class StringTransformation extends
		net.sf.orcc.backends.llvm.transform.StringTransformation {

	private class LightWeightPrintTransformer extends PrintTransformer {

		/**
		 * Transform a call of 'print' function to make it compliant with LLVM
		 * assembly language reference.
		 * 
		 * @param call
		 *            the given call instruction of 'print' function
		 */
		protected void transformPrint(InstCall call) {
			for (Arg arg : new ArrayList<Arg>(call.getArguments())) {
				if (arg.isByVal()) {
					InstCall print = factory.createInstCall();
					Expression expr = ((ArgByVal) arg).getValue();
					if (expr.getType().isString()) {
						if (expr.isExprString()) {
							Var var = createStringVariable(((ExprString) expr)
									.getValue());
							stateVars.add(var);
							((ArgByVal) arg).setValue(factory
									.createExprVar(var));
						}
						print.setProcedure(getPrintStr());
					} else {
						print.setProcedure(getPrintInt());
					}
					print.getArguments().add(arg);
					call.getBlock().add(indexInst, print);
					indexInst++;
				}
			}

			call.getBlock().getInstructions().remove(indexInst);
			indexInst--;
		}
	}

	private Procedure printStr;

	private Procedure printInt;

	/**
	 * Create a transformation that make the calls of 'print' function compliant
	 * with LLVM assembly language reference.
	 */
	public StringTransformation() {
		this.irVisitor = new LightWeightPrintTransformer();
	}

	@Override
	public Void caseActor(Actor actor) {
		printStr = null;
		printInt = null;

		super.caseActor(actor);

		if (printStr != null) {
			actor.getProcs().add(0, printStr);
		}
		if (printInt != null) {
			actor.getProcs().add(0, printInt);
		}

		return null;
	}

	private Procedure getPrintInt() {
		if (printInt == null) {
			Param n = factory.createParam(factory.createVar(
					factory.createTypeInt(32), "n", true, 0));
			printInt = factory.createProcedure("lwpr_print_int",
					factory.createTypeVoid(), n);
		}
		return printInt;
	}

	private Procedure getPrintStr() {
		if (printStr == null) {
			Param str = factory.createParam(factory.createVar(
					factory.createTypeString(), "str", true, 0));
			printStr = factory.createProcedure("lwpr_print_str",
					factory.createTypeVoid(), str);
		}
		return printStr;
	}

}
