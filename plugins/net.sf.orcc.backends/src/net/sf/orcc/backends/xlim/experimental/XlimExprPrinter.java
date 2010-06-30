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
package net.sf.orcc.backends.xlim.experimental;

import net.sf.orcc.OrccRuntimeException;
import net.sf.orcc.ir.expr.BinaryExpr;
import net.sf.orcc.ir.expr.BinaryOp;
import net.sf.orcc.ir.expr.BoolExpr;
import net.sf.orcc.ir.expr.ExpressionPrinter;
import net.sf.orcc.ir.expr.ListExpr;

/**
 * This class defines a XLIM expression printer.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class XlimExprPrinter extends ExpressionPrinter {

	@Override
	public void visit(BinaryExpr expr, Object... args) {
		BinaryOp op = expr.getOp();
		int currentPrec = op.getPrecedence();

		int nextPrec;
		if (op == BinaryOp.SHIFT_LEFT || op == BinaryOp.SHIFT_RIGHT) {
			// special case, for shifts always put parentheses because compilers
			// often issue warnings
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

	@Override
	public void visit(BoolExpr expr, Object... args) {
		builder.append(expr.getValue() ? "1" : "0");
	}

	@Override
	public void visit(ListExpr expr, Object... args) {
		throw new OrccRuntimeException("List expression not supported");
	}

}
