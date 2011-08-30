/*
 * Copyright (c) 2009-2010, LEAD TECH DESIGN Rennes - France
 * Copyright (c) 2009-2010, IETR/INSA of Rennes
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
package net.sf.orcc.backends.vhdl;

import java.util.Iterator;

import net.sf.orcc.ir.ExprBinary;
import net.sf.orcc.ir.ExprBool;
import net.sf.orcc.ir.ExprList;
import net.sf.orcc.ir.ExprUnary;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.IrFactory;
import net.sf.orcc.ir.OpBinary;
import net.sf.orcc.ir.OpUnary;
import net.sf.orcc.ir.Type;
import net.sf.orcc.ir.TypeInt;
import net.sf.orcc.ir.TypeList;
import net.sf.orcc.ir.TypeUint;
import net.sf.orcc.ir.util.ExpressionPrinter;

/**
 * This class defines a VHDL expression printer.
 * 
 * @author Nicolas Siret
 * 
 */
public class VHDLExpressionPrinter extends ExpressionPrinter {

	/**
	 * Prints a function call to the function with the given name, whose
	 * arguments are the expressions e1 and e2.
	 * 
	 * @param function
	 *            a function name
	 * @param e1
	 *            first expression
	 * @param e2
	 *            second expression
	 */
	private String printCall(String function, Expression e1, Expression e2,
			Type size) {
		// parent precedence is the highest possible to prevent top-level binary
		// expression from being parenthesized
		int nextPrec = Integer.MAX_VALUE;
		String call = function + "(" + doSwitch(e1, nextPrec, 0) + ", "
				+ doSwitch(e2, nextPrec, 1) + ", ";

		if (size.isUint()) {
			call = "u" + call;
		}
		
		if (function == "bitand") {
			Type lub = getLub(e1.getType(), e2.getType());
			call += lub.getSizeInBits() + ", ";
		}
		return call + size.getSizeInBits() + ")";
	}

	/**
	 * Returns the Least Upper Bound of the given types.
	 * 
	 * @param t1
	 *            a type
	 * @param t2
	 *            another type
	 * @return the Least Upper Bound of the given types
	 */
	public Type getLub(Type t1, Type t2) {
		if (t1 == null || t2 == null) {
			return null;
		}

		if (t1.isBool() && t2.isBool()) {
			return t1;
		} else if (t1.isFloat() && t2.isFloat()) {
			return t1;
		} else if (t1.isString() && t2.isString()) {
			return t1;
		} else if (t1.isInt() && t2.isInt()) {
			return IrFactory.eINSTANCE.createTypeInt(Math.max(
					((TypeInt) t1).getSize(), ((TypeInt) t2).getSize()));
		} else if (t1.isList() && t2.isList()) {
			TypeList listType1 = (TypeList) t1;
			TypeList listType2 = (TypeList) t2;
			Type type = getLub(listType1.getType(), listType2.getType());
			if (type != null) {
				// only return a list when the underlying type is valid
				int size = Math.max(listType1.getSize(), listType2.getSize());
				return IrFactory.eINSTANCE.createTypeList(size, type);
			}
		} else if (t1.isUint() && t2.isUint()) {
			return IrFactory.eINSTANCE.createTypeUint(Math.max(
					((TypeUint) t1).getSize(), ((TypeUint) t2).getSize()));
		} else if (t1.isInt() && t2.isUint()) {
			int si = ((TypeInt) t1).getSize();
			int su = ((TypeUint) t2).getSize();
			if (si > su) {
				return IrFactory.eINSTANCE.createTypeInt(si);
			} else {
				return IrFactory.eINSTANCE.createTypeInt(su + 1);
			}
		} else if (t1.isUint() && t2.isInt()) {
			int su = ((TypeUint) t1).getSize();
			int si = ((TypeInt) t2).getSize();
			if (si > su) {
				return IrFactory.eINSTANCE.createTypeInt(si);
			} else {
				return IrFactory.eINSTANCE.createTypeInt(su + 1);
			}
		}

		return null;
	}

	@Override
	protected String toString(OpBinary op) {
		switch (op) {
		case EQ:
			return "=";
		case LOGIC_AND:
			return "and";
		case LOGIC_OR:
			return "or";
		case NE:
			return "/=";
		default:
			return op.getText();
		}
	}

	@Override
	protected String toString(OpUnary op) {
		switch (op) {
		case LOGIC_NOT:
			return "not ";
		default:
			return op.getText();
		}
	}

	@Override
	public String caseExprBinary(ExprBinary expr) {
		OpBinary op = expr.getOp();
		Expression e1 = expr.getE1();
		Expression e2 = expr.getE2();
		Type size = expr.getType();

		switch (op) {
		case BITAND:
			return printCall("bitand", e1, e2, size);
		case BITOR:
			return printCall("bitor", e1, e2, size);
		case BITXOR:
			return printCall("bitxor", e1, e2, size);
		case DIV:
		case DIV_INT:
			return printCall("div", e1, e2, size);
		case MOD:
			return printCall("get_mod", e1, e2, size);
		case SHIFT_LEFT:
			return printCall("shift_left", e1, e2, size);
		case SHIFT_RIGHT:
			return printCall("shift_right", e1, e2, size);
		default: {
			int currentPrec = op.getPrecedence();
			int nextPrec;
			if (op == OpBinary.LOGIC_OR) {
				// special case, for "or" always put parentheses because
				// VHDL does not get it if we don't
				nextPrec = Integer.MIN_VALUE;
			} else {
				nextPrec = currentPrec;
			}

			if (op.needsParentheses(precedence, branch)) {
				return "(" + doSwitch(expr.getE1(), nextPrec, 0) + " "
						+ toString(op) + " "
						+ doSwitch(expr.getE2(), nextPrec, 1) + ")";
			} else {
				return doSwitch(expr.getE1(), nextPrec, 0) + " " + toString(op)
						+ " " + doSwitch(expr.getE2(), nextPrec, 1);
			}
		}
		}
	}

	@Override
	public String caseExprBool(ExprBool expr) {
		return expr.isValue() ? "'1'" : "'0'";
	}

	@Override
	public String caseExprList(ExprList expr) {
		StringBuilder builder = new StringBuilder();
		builder.append('(');

		Iterator<Expression> it = expr.getValue().iterator();
		if (it.hasNext()) {
			builder.append(doSwitch(it.next()));
			while (it.hasNext()) {
				builder.append(", ").append(
						doSwitch(it.next(), Integer.MAX_VALUE, 0));
			}
		}

		builder.append(')');
		return builder.toString();
	}

	@Override
	public String caseExprUnary(ExprUnary expr) {
		OpUnary op = expr.getOp();
		String utype = "";

		if (expr.getType().isUint()) {
			utype = "u";
		}

		switch (op) {
		case BITNOT:
			return utype + "bitnot("
					+ doSwitch(expr.getExpr(), Integer.MIN_VALUE, 0) + ", "
					+ expr.getExpr().getType().getSizeInBits() + ")";
		default:
			return super.caseExprUnary(expr);
		}
	}

}
