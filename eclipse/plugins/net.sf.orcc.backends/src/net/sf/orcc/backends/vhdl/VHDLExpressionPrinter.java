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

import net.sf.orcc.ir.Cast;
import net.sf.orcc.ir.ExprBinary;
import net.sf.orcc.ir.ExprBool;
import net.sf.orcc.ir.ExprList;
import net.sf.orcc.ir.ExprString;
import net.sf.orcc.ir.ExprUnary;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.IrFactory;
import net.sf.orcc.ir.OpBinary;
import net.sf.orcc.ir.OpUnary;
import net.sf.orcc.ir.Type;
import net.sf.orcc.ir.TypeInt;
import net.sf.orcc.ir.TypeList;
import net.sf.orcc.ir.TypeUint;
import net.sf.orcc.ir.impl.ExprBinaryImpl;
import net.sf.orcc.ir.util.ExpressionPrinter;
import net.sf.orcc.util.OrccUtil;

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
	private void printCall(String function, Expression e1, Expression e2,
			Type size) {
		// parent precedence is the highest possible to prevent top-level binary
		// expression from being parenthesized
		int nextPrec = Integer.MAX_VALUE;

		int s_op = Cast.getSizeOfType(size);

		builder.append(function);
		builder.append("(");
		e1.accept(this, nextPrec, ExprBinaryImpl.LEFT);
		builder.append(", ");
		e2.accept(this, nextPrec, ExprBinaryImpl.RIGHT);
		builder.append(", ");
		if (function == "bitand") {
			Type sizee = getLub(e1.getType(), e2.getType());
			int s_e = Cast.getSizeOfType(sizee);
			builder.append(s_e + ", ");
		}
		builder.append(s_op);
		builder.append(")");
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
	public void visit(ExprBinary expr, Object... args) {
		OpBinary op = expr.getOp();
		Expression e1 = expr.getE1();
		Expression e2 = expr.getE2();
		Type size = expr.getType();

		switch (op) {
		case BITAND:
			printCall("bitand", e1, e2, size);
			break;
		case BITOR:
			printCall("bitor", e1, e2, size);
			break;
		case BITXOR:
			printCall("bitxor", e1, e2, size);
			break;
		case DIV:
		case DIV_INT:
			printCall("div", e1, e2, size);
			break;
		case MOD:
			printCall("get_mod", e1, e2, size);
			break;
		case SHIFT_LEFT:
			printCall("shift_left", e1, e2, size);
			break;
		case SHIFT_RIGHT:
			printCall("shift_right", e1, e2, size);
			break;
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

			if (op.needsParentheses(args)) {
				builder.append("(");
				toString(nextPrec, expr.getE1(), op, expr.getE2());
				builder.append(")");
			} else {
				toString(nextPrec, expr.getE1(), op, expr.getE2());
			}
		}
		}
	}

	@Override
	public void visit(ExprBool expr, Object... args) {
		builder.append(expr.isValue() ? "'1'" : "'0'");
	}

	@Override
	public void visit(ExprList expr, Object... args) {
		builder.append('(');
		builder.append(OrccUtil.toString(expr.getValue(), ", "));
		builder.append(')');
	}

	@Override
	public void visit(ExprString expr, Object... args) {
		builder.append('"');
		builder.append(expr.getValue());
		builder.append('"');
	}

	@Override
	public void visit(ExprUnary expr, Object... args) {
		OpUnary op = expr.getOp();
		switch (op) {
		case BITNOT:
			builder.append("bitnot");
			builder.append("(");
			expr.getExpr().accept(this, Integer.MIN_VALUE);
			builder.append(Cast.getSizeOfType(expr.getExpr().getType()));
			builder.append(")");
			break;
		default:
			super.visit(expr, args);
		}
	}

}
