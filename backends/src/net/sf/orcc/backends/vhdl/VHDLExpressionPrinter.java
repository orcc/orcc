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

import net.sf.orcc.OrccRuntimeException;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.Type;
import net.sf.orcc.ir.expr.BinaryExpr;
import net.sf.orcc.ir.expr.BinaryOp;
import net.sf.orcc.ir.expr.BoolExpr;
import net.sf.orcc.ir.expr.ExpressionEvaluator;
import net.sf.orcc.ir.expr.ListExpr;
import net.sf.orcc.ir.expr.UnaryOp;
import net.sf.orcc.ir.printers.DefaultExpressionPrinter;
import net.sf.orcc.ir.type.IntType;
import net.sf.orcc.ir.type.UintType;

/**
 * This class defines a VHDL expression printer.
 * 
 * @author Nicolas Siret
 * 
 */
public class VHDLExpressionPrinter extends DefaultExpressionPrinter {

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
	private void printCall(String function, Expression e1, Expression e2) {
		// parent precedence is the highest possible to prevent top-level binary
		// expression from being parenthesized
		int nextPrec = Integer.MAX_VALUE;

		builder.append(function);
		builder.append("(");
		e1.accept(this, nextPrec, BinaryExpr.LEFT);
		printType(e1);
		builder.append(", ");
		e2.accept(this, nextPrec, BinaryExpr.RIGHT);
		printType(e2);
		builder.append(")");
	}

	private void printType(Expression expr) {
		builder.append(", ");
		Type type = expr.getType();
		if (type == null) {
			builder.append(32);
		} else {
			if (type.isBool()) {
				builder.append(1);
			} else if (type.isInt()) {
				expr = ((IntType) type).getSize();
				int size = new ExpressionEvaluator().evaluateAsInteger(expr);
				builder.append(size);
			} else if (type.isUint()) {
				expr = ((UintType) type).getSize();
				int size = new ExpressionEvaluator().evaluateAsInteger(expr);
				builder.append(size + 1);
			} else {
				throw new OrccRuntimeException("cannot get size of type: "
						+ type);
			}
		}
	}

	@Override
	protected String toString(BinaryOp op) {
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
	protected String toString(UnaryOp op) {
		switch (op) {
		case LOGIC_NOT:
			return "not";
		default:
			return op.getText();
		}
	}

	@Override
	public void visit(BinaryExpr expr, Object... args) {
		BinaryOp op = expr.getOp();
		Expression e1 = expr.getE1();
		Expression e2 = expr.getE2();

		switch (op) {
		case BITAND:
			printCall("bitand", e1, e2);
			break;
		case BITOR:
			printCall("bitor", e1, e2);
			break;
		case BITXOR:
			printCall("bitxor", e1, e2);
			break;
		case DIV:
		case DIV_INT:
			printCall("div", e1, e2);
			break;
		case MOD:
			printCall("get_mod", e1, e2);
			break;
		case SHIFT_LEFT:
			printCall("shift_left", e1, e2);
			break;
		case SHIFT_RIGHT:
			printCall("shift_right", e1, e2);
			break;
		default:
			if (op.needsParentheses(args)) {
				builder.append("(");
				toString(op.getPrecedence(), expr.getE1(), op, expr.getE2());
				builder.append(")");
			} else {
				toString(op.getPrecedence(), expr.getE1(), op, expr.getE2());
			}
		}
	}

	@Override
	public void visit(BoolExpr expr, Object... args) {
		builder.append(expr.getValue() ? "'1'" : "'0'");
	}

	@Override
	public void visit(ListExpr expr, Object... args) {
		throw new OrccRuntimeException("List expression not supported");
	}

}
