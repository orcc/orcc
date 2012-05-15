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
package net.sf.orcc.backends.llvm.aot;

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
 * @author Matthieu Wipliez
 * 
 */
public class LLVMExpressionPrinter extends ExpressionPrinter {

	private boolean signed;
	private boolean floating;

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
			if (floating) {
				return "fdiv";
			} else if (signed) {
				return "sdiv";
			} else {
				return "udiv";
			}
		case DIV_INT:
			if (floating) {
				return "fdiv";
			} else if (signed) {
				return "sdiv";
			} else {
				return "udiv";
			}
		case EQ:
			if (floating) {
				return "fcmp oeq";
			} else {
				return "icmp eq";
			}
		case EXP:
			return "pow";
		case GE:
			if (floating) {
				return "fcmp oge";
			} else if (signed) {
				return "icmp sge";
			} else {
				return "icmp uge";
			}

		case GT:
			if (floating) {
				return "fcmp ogt";
			} else if (signed) {
				return "icmp sgt";
			} else {
				return "icmp ugt";
			}
		case LOGIC_AND:
			return "and";
		case LE:
			if (floating) {
				return "fcmp ole";
			} else if (signed) {
				return "icmp sle";
			} else {
				return "icmp ule";
			}
		case LOGIC_OR:
			return "or";
		case LT:
			if (floating) {
				return "fcmp olt";
			} else if (signed) {
				return "icmp slt";
			} else {
				return "icmp ult";
			}
		case MINUS:
			if (floating) {
				return "fsub";
			} else {
				return "sub";
			}
		case MOD:
			if (floating) {
				return "frem";
			} else if (signed) {
				return "srem";
			} else {
				return "urem";
			}
		case NE:
			if (floating) {
				return "fcmp one";
			} else {
				return "icmp ne";
			}
		case PLUS:
			if (floating) {
				return "fadd";
			} else {
				return "add";
			}
		case SHIFT_LEFT:
			return "shl";
		case SHIFT_RIGHT:
			if (signed) {
				return "ashr";
			} else {
				return "lshr";
			}
		case TIMES:
			if (floating) {
				return "fmul";
			} else {
				return "mul";
			}
		default:
			throw new NullPointerException();
		}
	}

	@Override
	public String caseExprBinary(ExprBinary expr) {
		OpBinary op = expr.getOp();
		Type type;
		Expression e1 = expr.getE1();
		Expression e2 = expr.getE2();
		StringBuilder builder = new StringBuilder();

		if (e1 instanceof ExprVar) {
			Use use = ((ExprVar) e1).getUse();
			type = use.getVariable().getType();
		} else if (e2 instanceof ExprVar) {
			Use use = ((ExprVar) e2).getUse();
			type = use.getVariable().getType();
		} else {
			type = expr.getType();
		}

		signed = !type.isUint();
		floating = type.isFloat();

		builder.append(toString(op));
		builder.append(" ");
		builder.append(new LLVMTypePrinter().doSwitch(type));
		builder.append(" ");

		if (e1 instanceof ExprVar) {
			builder.append("%");
		}
		builder.append(doSwitch(expr.getE1()));
		builder.append(", ");
		if (e2 instanceof ExprVar) {
			builder.append("%");
		}
		builder.append(doSwitch(expr.getE2()));
		return builder.toString();
	}

	@Override
	public String caseExprBool(ExprBool expr) {
		return expr.isValue() ? "1" : "0";
	}

	@Override
	public String caseExprInt(ExprInt expr) {
		return expr.getValue().toString();
	}

	@Override
	public String caseExprList(ExprList expr) {
		throw new OrccRuntimeException("List expression not supported");
	}

	@Override
	public String caseExprUnary(ExprUnary expr) {
		throw new OrccRuntimeException("no unary expressions in LLVM");
	}

	@Override
	public String caseExprVar(ExprVar expr) {
		return expr.getUse().getVariable().getIndexedName();
	}

}
