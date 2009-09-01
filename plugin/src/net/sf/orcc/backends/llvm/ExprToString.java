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
package net.sf.orcc.backends.llvm;

import net.sf.orcc.ir.VarDef;
import net.sf.orcc.ir.expr.AbstractExpr;
import net.sf.orcc.ir.expr.BinaryExpr;
import net.sf.orcc.ir.expr.BinaryOp;
import net.sf.orcc.ir.expr.BooleanExpr;
import net.sf.orcc.ir.expr.ExprVisitor;
import net.sf.orcc.ir.expr.IntExpr;
import net.sf.orcc.ir.expr.ListExpr;
import net.sf.orcc.ir.expr.StringExpr;
import net.sf.orcc.ir.expr.UnaryExpr;
import net.sf.orcc.ir.expr.UnaryOp;
import net.sf.orcc.ir.expr.VarExpr;
import net.sf.orcc.ir.expr.TypeExpr;

/**
 * 
 * @author Jérôme GORIN
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
			return "sge";
		case GT:
			return "sgt";
		case LAND:
			return "&&";
		case LE:
			return "sle";
		case LOR:
			return "||";
		case LT:
			return "slt";
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

	private StringBuilder builder;

	private final VarDefPrinter varDefPrinter;

	public ExprToString(VarDefPrinter varDefPrinter, AbstractExpr expr) {
		builder = new StringBuilder();
		this.varDefPrinter = varDefPrinter;
		expr.accept(this, 0);
	}

	private int getPriority(BinaryOp op) {
		switch (op) {
		case LOR:
			return 1;
		case LAND:
			return 2;
		case BOR:
			return 3;
		case BXOR:
			return 4;
		case BAND:
			return 5;

		case EQ:
		case NE:
			return 6;

		case GE:
		case GT:
		case LE:
		case LT:
			return 7;

		case SHIFT_LEFT:
		case SHIFT_RIGHT:
			return 8;

		case MINUS:
		case PLUS:
			return 9;

		case DIV:
		case DIV_INT:
		case MOD:
		case TIMES:
			return 10;

		case EXP:
			return 11;

		default:
			throw new NullPointerException();
		}
	}

	private int getPriority(UnaryOp op) {
		switch (op) {
		case NUM_ELTS:
			return 12;

		case BNOT:
		case LNOT:
			return 13;

		case MINUS:
			return 14;
		default:
			throw new NullPointerException();
		}
	}

	@Override
	public String toString() {
		return builder.toString();
	}

	@Override
	public void visit(BinaryExpr expr, Object... args) {
		int oldPrec = (Integer) args[0];
		int currentPrec = getPriority(expr.getOp());

		if (currentPrec < oldPrec) {
			builder.append("(");
		}
	
		BinaryOp op = expr.getOp();
		
		builder.append("icmp " + toString(op) + " ");
		
		if (op == BinaryOp.SHIFT_LEFT || op == BinaryOp.SHIFT_RIGHT) {
			expr.getE1().accept(this, Integer.MAX_VALUE);
		} else {
			expr.getE1().accept(this, currentPrec);
		}

		builder.append(", ");

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
	public void visit(TypeExpr expr, Object... args) {
		builder.append(expr.getType());
	}

	@Override
	public void visit(ListExpr expr, Object... args) {
		throw new IllegalArgumentException("List expression not supported");
	}

	@Override
	public void visit(StringExpr expr, Object... args) {
		builder.append('"');
		builder.append(expr.getValue().replaceAll("\\\\", "\\\\"));
		builder.append('"');
	}

	@Override
	public void visit(UnaryExpr expr, Object... args) {
		int oldPrec = (Integer) args[0];
		int currentPrec = getPriority(expr.getOp());

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
		VarDef varDef = expr.getVar().getVarDef();
		TypeToString type = new TypeToString(varDef.getType());
		builder.append(type);
		builder.append(" ");
		builder.append(varDefPrinter.getVarDefName(varDef));
	}

}
