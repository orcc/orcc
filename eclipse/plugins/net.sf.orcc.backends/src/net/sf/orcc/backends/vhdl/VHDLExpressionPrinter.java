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
import net.sf.orcc.ir.OpBinary;
import net.sf.orcc.ir.OpUnary;
import net.sf.orcc.ir.Type;
import net.sf.orcc.ir.util.ExpressionPrinter;
import net.sf.orcc.ir.util.TypeUtil;

/**
 * This class defines a VHDL expression printer.
 * 
 * @author Nicolas Siret
 * @author Matthieu Wipliez
 * 
 */
public class VHDLExpressionPrinter extends ExpressionPrinter {

	@Override
	public String caseExprBinary(ExprBinary expr) {
		OpBinary op = expr.getOp();
		Expression e1 = expr.getE1();
		Expression e2 = expr.getE2();
		Type type = expr.getType();
		boolean isUint = type.isUint();

		switch (op) {
		case BITAND:
			return printCall(isUint, "bitand", e1, e2,
					TypeUtil.getLub(e1.getType(), e2.getType()), type);
		case BITOR:
			return printCall(isUint, "bitor", e1, e2, type);
		case BITXOR:
			return printCall(isUint, "bitxor", e1, e2, type);
		case DIV:
		case DIV_INT:
			return printCall(isUint, "div", e1, e2, type);
		case MOD:
			return printCall(isUint, "get_mod", e1, e2, type);
		case SHIFT_LEFT:
			return printCall(isUint, "shift_left", e1, e2, type);
		case SHIFT_RIGHT:
			return printCall(isUint, "shift_right", e1, e2, e1.getType(), type);
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
	 * @param type
	 *            type to give the function
	 * @param types
	 *            additional types to pass the function
	 */
	private String printCall(boolean isUint, String function, Expression e1,
			Expression e2, Type... types) {
		// parent precedence is the highest possible to prevent top-level binary
		// expression from being parenthesized
		int nextPrec = Integer.MAX_VALUE;
		StringBuilder builder = new StringBuilder();
		if (isUint) {
			builder.append('u');
		}
		builder.append(function);
		builder.append('(');
		builder.append(doSwitch(e1, nextPrec, 0));
		builder.append(", ");
		builder.append(doSwitch(e2, nextPrec, 1));

		for (Type type : types) {
			builder.append(", ");
			builder.append(type.getSizeInBits());
		}
		builder.append(')');

		return builder.toString();
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

}
