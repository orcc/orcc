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
package net.sf.orcc.backends.llvm.transform;

import java.util.ArrayList;
import java.util.List;

import net.sf.orcc.df.Actor;
import net.sf.orcc.df.util.DfVisitor;
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
import net.sf.orcc.ir.util.AbstractIrVisitor;

import org.apache.commons.lang.StringUtils;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.util.EcoreUtil;

/**
 * Change string into global constant. Parameters of println becomes state
 * variables into the current actor
 * 
 * @author Jerome Gorin
 * @author Herve Yviquel
 * 
 */
public class StringTransformation extends DfVisitor<Void> {

	private class PrintTransformer extends AbstractIrVisitor<Void> {

		@Override
		public Void caseInstCall(InstCall call) {
			if (call.isPrint()) {
				transformPrint(call);
			} else {
				for (Arg arg : call.getArguments()) {
					if (arg.isByVal()) {
						Expression expr = ((ArgByVal) arg).getValue();
						if (expr.isExprString()) {
							// Create state variable that contains println
							// arguments
							String strExprVal = (((ExprString) expr).getValue());
							Var variable = createStringVariable(strExprVal);
							stateVars.add(variable);

							EcoreUtil.replace(arg, IrFactory.eINSTANCE
									.createArgByVal(variable));
						}
					}
				}
			}
			return null;
		}
	}

	private EList<Var> stateVars;
	private int strCnt;

	/**
	 * Create a transformation that make the calls of 'print' function compliant
	 * with LLVM assembly language reference.
	 */
	public StringTransformation() {
		this.irVisitor = new PrintTransformer();
	}

	@Override
	public Void caseActor(Actor actor) {
		strCnt = 0;
		stateVars = actor.getStateVars();

		while (actor.getProcedure("print") != null) {
			actor.getProcs().remove(actor.getProcedure("print"));
		}

		return super.caseActor(actor);
	}

	/**
	 * Create a variable initialized with an LLVM-compliant ExprString.
	 * 
	 * @param str
	 *            A string
	 * @return A variable initialized with an LLVM-compliant ExprString extract
	 *         from the given string
	 */
	private Var createStringVariable(String str) {
		int size = str.length();
		str = str + "\\00";
		size -= StringUtils.countMatches(str, "\\n");
		str = str.replaceAll("\\\\n", "\\\\0A");
		size -= StringUtils.countMatches(str, "\\t");
		str = str.replaceAll("\\\\t", "\\\\09");

		// Create state variable that contains println arguments
		TypeString type = IrFactory.eINSTANCE.createTypeString();
		type.setSize(size + 1);

		Var variable = IrFactory.eINSTANCE.createVar(type, "str" + strCnt++,
				false, 0);
		variable.setInitialValue(IrFactory.eINSTANCE.createExprString(str));

		return variable;
	}

	/**
	 * Transform a call of 'print' function to make it compliant with LLVM
	 * assembly language reference.
	 * 
	 * @param call
	 *            the given call instruction of 'print' function
	 */
	private void transformPrint(InstCall call) {
		String value = new String();
		List<Arg> newParameters = new ArrayList<Arg>();
		List<Arg> parameters = call.getArguments();

		// Iterate though all the println arguments to provide an only
		// string value
		for (Arg arg : parameters) {
			if (arg.isByVal()) {
				Expression expr = ((ArgByVal) arg).getValue();
				if (expr.isExprString()) {
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

		Var variable = createStringVariable(value);
		stateVars.add(variable);
		newParameters.add(IrFactory.eINSTANCE.createArgByVal(variable));

		for (Arg arg : parameters) {
			if (arg.isByVal()) {
				Expression expr = ((ArgByVal) arg).getValue();
				if (!expr.isExprString()) {
					newParameters.add(arg);
				}
			}
		}
		parameters.clear();
		parameters.addAll(newParameters);
	}

}
