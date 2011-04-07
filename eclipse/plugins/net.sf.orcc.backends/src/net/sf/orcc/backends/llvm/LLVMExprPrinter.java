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
import net.sf.orcc.ir.ExprBinary;
import net.sf.orcc.ir.ExprBool;
import net.sf.orcc.ir.ExprInt;
import net.sf.orcc.ir.ExprList;
import net.sf.orcc.ir.ExprUnary;
import net.sf.orcc.ir.ExprVar;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.OpBinary;
import net.sf.orcc.ir.Type;
import net.sf.orcc.ir.Use;
import net.sf.orcc.ir.util.ExpressionPrinter;

/**
 * This class defines an LLVM expression printer.
 * 
 * @author Jerome GORIN
 * 
 */
public class LLVMExprPrinter extends ExpressionPrinter {

	private boolean signed;

	@Override
	protected String toString(OpBinary op) {
		switch (op) {
		case BITAND:
			return "and";
		case BITOR:
			return "or";
		case BITXOR:
			return "xor";
		case DIV:
			if (signed) {
				return "sdiv";
			}
			return "udiv";
		case DIV_INT:
			if (signed) {
				return "sdiv";
			}
			return "udiv";
		case EQ:
			return "icmp eq";
		case EXP:
			return "pow";
		case GE:
			if (signed) {
				return "icmp sge";
			}
			return "icmp uge";

		case GT:
			if (signed) {
				return "icmp sgt";
			}
			return "icmp ugt";
		case LOGIC_AND:
			return "and";
		case LE:
			if (signed) {
				return "icmp sle";
			}
			return "icmp ule";
		case LOGIC_OR:
			return "or";
		case LT:
			if (signed) {
				return "icmp slt";
			}
			return "icmp ult";
		case MINUS:
			return "sub";
		case MOD:
			if (signed) {
				return "srem";
			}
			return "urem";
		case NE:
			return "icmp ne";
		case PLUS:
			return "add";
		case SHIFT_LEFT:
			return "shl";
		case SHIFT_RIGHT:
			if (signed) {
				return "ashr";
			}
			return "lshr";
		case TIMES:
			return "mul";
		default:
			throw new NullPointerException();
		}
	}

	@Override
	public void visit(ExprBinary expr, Object... args) {
		OpBinary op = expr.getOp();
		Type type;
		Expression e1 = expr.getE1();
		Expression e2 = expr.getE2();
		LLVMTypePrinter typePrinter = new LLVMTypePrinter();

		if (e1 instanceof ExprVar) {
			Use use = ((ExprVar) e1).getUse();
			type = use.getVariable().getType();
		} else if (e2 instanceof ExprVar) {
			Use use = ((ExprVar) e2).getUse();
			type = use.getVariable().getType();
		} else {
			type = expr.getType();
		}

		if (type.isUint()) {
			signed = false;
		} else {
			signed = true;
		}

		builder.append(toString(op));

		type.accept(typePrinter);

		builder.append(" " + typePrinter.toString() + " ");

		if (e1 instanceof ExprVar) {
			builder.append("%");
		}
		expr.getE1().accept(this);
		builder.append(", ");
		if (e2 instanceof ExprVar) {
			builder.append("%");
		}
		expr.getE2().accept(this);
	}

	@Override
	public void visit(ExprBool expr, Object... args) {
		builder.append(expr.isValue() ? '1' : '0');
	}

	@Override
	public void visit(ExprInt expr, Object... args) {
		builder.append(expr.getValue());
	}

	@Override
	public void visit(ExprList expr, Object... args) {
		throw new OrccRuntimeException("List expression not supported");
	}

	@Override
	public void visit(ExprUnary expr, Object... args) {
		System.err.println("oops: unary expr");
		// throw new OrccRuntimeException("no unary expressions in LLVM");
	}

	@Override
	public void visit(ExprVar expr, Object... args) {
		builder.append(expr.getUse().getVariable());
	}

}
