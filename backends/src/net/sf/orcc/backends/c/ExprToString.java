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
package net.sf.orcc.backends.c;

import net.sf.orcc.common.Variable;
import net.sf.orcc.ir.expr.BinaryExpr;
import net.sf.orcc.ir.expr.BinaryOp;
import net.sf.orcc.ir.expr.BooleanExpr;
import net.sf.orcc.ir.expr.ExprVisitor;
import net.sf.orcc.ir.expr.IExpr;
import net.sf.orcc.ir.expr.IntExpr;
import net.sf.orcc.ir.expr.ListExpr;
import net.sf.orcc.ir.expr.StringExpr;
import net.sf.orcc.ir.expr.TypeExpr;
import net.sf.orcc.ir.expr.UnaryExpr;
import net.sf.orcc.ir.expr.UnaryOp;
import net.sf.orcc.ir.expr.VarExpr;

/**
 * 
 * @author Matthieu Wipliez
 * 
 */
public class ExprToString implements ExprVisitor {

	public static String toString(BinaryOp op) {
		switch (op) {
		case BAND:
			return "&";
		case BOR:
			return "|";
		case BXOR:
			return "^";
		case DIV:
			return "/";
		case DIV_INT:
			return "/";
		case EQ:
			return "==";
		case EXP:
			return "pow";
		case GE:
			return ">=";
		case GT:
			return ">";
		case LAND:
			return "&&";
		case LE:
			return "<=";
		case LOR:
			return "||";
		case LT:
			return "<";
		case MINUS:
			return "-";
		case MOD:
			return "%";
		case NE:
			return "!=";
		case PLUS:
			return "+";
		case SHIFT_LEFT:
			return "<<";
		case SHIFT_RIGHT:
			return ">>";
		case TIMES:
			return "*";
		default:
			throw new NullPointerException();
		}
	}

	public static String toString(UnaryOp op) {
		switch (op) {
		case BNOT:
			return "~";
		case LNOT:
			return "!";
		case MINUS:
			return "-";
		case NUM_ELTS:
			return "sizeof";
		default:
			throw new NullPointerException();
		}
	}

	protected StringBuilder builder;

	protected final VarDefPrinter varDefPrinter;

	public ExprToString(VarDefPrinter varDefPrinter) {
		this.varDefPrinter = varDefPrinter;
	}

	public String toString(IExpr expr) {
		builder = new StringBuilder();
		expr.accept(this, 0);
		return builder.toString();
	}

	@Override
	public void visit(BinaryExpr expr, Object... args) {
		int oldPrec = (Integer) args[0];
		int currentPrec = expr.getOp().getPriority();

		if (currentPrec < oldPrec) {
			builder.append("(");
		}

		BinaryOp op = expr.getOp();
		if (op == BinaryOp.SHIFT_LEFT || op == BinaryOp.SHIFT_RIGHT) {
			expr.getE1().accept(this, Integer.MAX_VALUE);
		} else {
			expr.getE1().accept(this, currentPrec);
		}

		builder.append(" ");
		builder.append(toString(op));
		builder.append(" ");

		if (op == BinaryOp.SHIFT_LEFT || op == BinaryOp.SHIFT_RIGHT) {
			expr.getE2().accept(this, Integer.MAX_VALUE);
		} else {
			expr.getE2().accept(this, currentPrec);
		}

		if (currentPrec < oldPrec) {
			builder.append(")");
		}
	}

	@Override
	public void visit(BooleanExpr expr, Object... args) {
		builder.append(expr.getValue() ? "1" : "0");
	}

	@Override
	public void visit(IntExpr expr, Object... args) {
		builder.append(expr.getValue());
	}

	@Override
	public void visit(ListExpr expr, Object... args) {
		throw new IllegalArgumentException("List expression not supported");
	}

	@Override
	public void visit(StringExpr expr, Object... args) {
		builder.append('"');
		builder.append(expr.getValue().replaceAll("\\\\", "\\\\\\\\"));
		builder.append('"');
	}

	@Override
	public void visit(TypeExpr expr, Object... args) {
		builder.append(expr.getUnderlyingType());
	}

	@Override
	public void visit(UnaryExpr expr, Object... args) {
		int oldPrec = (Integer) args[0];
		int currentPrec = expr.getOp().getPriority();

		if (oldPrec > currentPrec) {
			builder.append("(");
		}

		builder.append(toString(expr.getOp()));
		expr.getExpr().accept(this, args);

		if (oldPrec > currentPrec) {
			builder.append(")");
		}
	}

	@Override
	public void visit(VarExpr expr, Object... args) {
		Variable variable = expr.getVar().getVariable();
		builder.append(varDefPrinter.getVarDefName(variable));
	}

}
