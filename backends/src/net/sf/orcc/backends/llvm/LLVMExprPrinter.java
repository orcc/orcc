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

import net.sf.orcc.OrccRuntimeException;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.Use;
import net.sf.orcc.ir.expr.BinaryExpr;
import net.sf.orcc.ir.expr.BinaryOp;
import net.sf.orcc.ir.expr.BoolExpr;
import net.sf.orcc.ir.expr.IntExpr;
import net.sf.orcc.ir.expr.ListExpr;
import net.sf.orcc.ir.expr.UnaryExpr;
import net.sf.orcc.ir.expr.VarExpr;
import net.sf.orcc.ir.printers.DefaultExpressionPrinter;

/**
 * This class defines an LLVM expression printer.
 * 
 * @author Jérôme GORIN
 * 
 */
public class LLVMExprPrinter extends DefaultExpressionPrinter {

	@Override
	protected String toString(BinaryOp op) {
		switch (op) {
		case BITAND:
			return "and";
		case BITOR:
			return "or";
		case BITXOR:
			return "xor";
		case DIV:
			return "sdiv";
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
		case LOGIC_AND:
			return "and";
		case LE:
			return "icmp sle";
		case LOGIC_OR:
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

	@Override
	public void visit(BinaryExpr expr, Object... args) {
		BinaryOp op = expr.getOp();
		Expression e1 = expr.getE1();

		builder.append(toString(op));

		if (e1 instanceof VarExpr) {
			Use use = ((VarExpr) e1).getVar();
			builder.append(" " + use.getVariable().getType().toString() + " ");
		} else {
			builder.append(" i32 ");
		}

		expr.getE1().accept(this);
		builder.append(", ");
		expr.getE2().accept(this);
	}

	@Override
	public void visit(BoolExpr expr, Object... args) {
		builder.append(expr.getValue() ? '1' : '0');
	}

	@Override
	public void visit(IntExpr expr, Object... args) {
		builder.append(expr.getValue());
	}

	@Override
	public void visit(ListExpr expr, Object... args) {
		throw new OrccRuntimeException("List expression not supported");
	}

	@Override
	public void visit(UnaryExpr expr, Object... args) {
		System.err.println("oops: unary expr");
		// throw new OrccRuntimeException("no unary expressions in LLVM");
	}

	@Override
	public void visit(VarExpr expr, Object... args) {
		builder.append('%');
		builder.append(expr.getVar().getVariable());
	}

}
