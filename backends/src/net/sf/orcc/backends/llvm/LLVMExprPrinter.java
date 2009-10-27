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

import net.sf.orcc.ir.IExpr;
import net.sf.orcc.ir.IType;
import net.sf.orcc.ir.Variable;
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

/**
 * 
 * @author Jérôme GORIN
 * 
 */
public class LLVMExprPrinter implements ExprVisitor {
	public static String toString(BinaryOp op) {
		switch (op) {
		case BAND:
			return "and";
		case BOR:
			return "or";
		case BXOR:
			return "xor";
		case DIV:
			return "/";
		case DIV_INT:
			return "sdiv";
		case EQ:
			return "icmp eq";
		case EXP:
			return "pow";
		case GE:
			return "icmp sge";
		case GT:
			return "icmp sgt";
		case LAND:
			return "and";
		case LE:
			return "icmp sle";
		case LOR:
			return "or";
		case LT:
			return "icmp slt";
		case MINUS:
			return "sub";
		case MOD:
			return "%";
		case NE:
			return "icmp ne";
		case PLUS:
			return "add";
		case SHIFT_LEFT:
			return "shl";
		case SHIFT_RIGHT:
			return "lshr";
		case TIMES:
			return "mul";
		default:
			throw new NullPointerException();
		}
	}

	public static String toString(UnaryOp op, Object... args) {

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

	private LLVMTypePrinter typePrinter;

	private LLVMVarDefPrinter varDefPrinter;

	public LLVMExprPrinter(LLVMTypePrinter typePrinter,
			LLVMVarDefPrinter varDefPrinter) {
		this.varDefPrinter = varDefPrinter;
		this.typePrinter = typePrinter;
	}

	public String toString(IExpr expr, Object... args) {
		builder = new StringBuilder();
		expr.accept(this, args);
		return builder.toString();
	}

	@Override
	public void visit(BinaryExpr expr, Object... args) {
		BinaryOp op = expr.getOp();

		builder.append(toString(op) + " ");
		expr.getE1().accept(this, expr.getUnderlyingType());
		builder.append(", ");
		expr.getE2().accept(this, false);

	}

	@Override
	public void visit(BooleanExpr expr, Object... args) {
		if (args[0] instanceof IType) {
			IType type = (IType) args[0];
			String typeStr = typePrinter.toString(type);
			builder.append(typeStr + " ");
		}

		builder.append(expr.getValue() ? "1" : "0");
	}

	@Override
	public void visit(IntExpr expr, Object... args) {
		if (args[0] instanceof IType) {
			String type = typePrinter.toString((IType) args[0]);
			builder.append(type + " ");
		}

		builder.append(expr.getValue());
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
		expr.getExpr().accept(this, args);
	}

	@Override
	public void visit(VarExpr expr, Object... args) {
		Boolean showType = false;

		if (args[0] instanceof IType) {
			showType = true;
		}

		Variable variable = expr.getVar().getVariable();
		builder.append(varDefPrinter.getVarDefName(variable, showType));
	}

}
