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
package net.sf.orcc.ir.printers;

import java.util.Iterator;
import java.util.List;

import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.Variable;
import net.sf.orcc.ir.expr.BinaryExpr;
import net.sf.orcc.ir.expr.BinaryOp;
import net.sf.orcc.ir.expr.BooleanExpr;
import net.sf.orcc.ir.expr.ExpressionVisitor;
import net.sf.orcc.ir.expr.IntExpr;
import net.sf.orcc.ir.expr.ListExpr;
import net.sf.orcc.ir.expr.StringExpr;
import net.sf.orcc.ir.expr.UnaryExpr;
import net.sf.orcc.ir.expr.VarExpr;

/**
 * This class defines the default expression printer.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class DefaultExpressionPrinter implements ExpressionVisitor {

	protected StringBuilder builder;

	/**
	 * Creates a new expression printer.
	 */
	public DefaultExpressionPrinter() {
		builder = new StringBuilder();
	}

	@Override
	public String toString() {
		return builder.toString();
	}

	@Override
	public Object visit(BinaryExpr expr, Object... args) {
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
			expr.getE1().accept(this, nextPrec, BinaryExpr.LEFT);
			builder.append(" ");
			builder.append(op.getText());
			builder.append(" ");
			expr.getE2().accept(this, nextPrec, BinaryExpr.RIGHT);
			builder.append(")");
		} else {
			expr.getE1().accept(this, nextPrec, BinaryExpr.LEFT);
			builder.append(" ");
			builder.append(op.getText());
			builder.append(" ");
			expr.getE2().accept(this, nextPrec, BinaryExpr.RIGHT);
		}

		return null;
	}

	@Override
	public Object visit(BooleanExpr expr, Object... args) {
		builder.append(expr.getValue());
		return null;
	}

	@Override
	public Object visit(IntExpr expr, Object... args) {
		builder.append(expr.getValue());
		return null;
	}

	@Override
	public Object visit(ListExpr expr, Object... args) {
		List<Expression> list = expr.getValue();
		if (list.isEmpty()) {
			builder.append("[]");
		} else {
			Iterator<Expression> it = list.iterator();
			builder.append('[');
			builder.append(it.next().toString());
			while (it.hasNext()) {
				builder.append(", ");
				builder.append(it.next().toString());
			}
			builder.append(']');
		}
		return null;
	}

	@Override
	public Object visit(StringExpr expr, Object... args) {
		builder.append('"');
		builder.append(expr.getValue().replaceAll("\\\\", "\\\\\\\\"));
		builder.append('"');
		return null;
	}

	@Override
	public Object visit(UnaryExpr expr, Object... args) {
		builder.append(expr.getOp().getText());
		expr.getExpr().accept(this, Integer.MIN_VALUE);
		return null;
	}

	@Override
	public Object visit(VarExpr expr, Object... args) {
		Variable variable = expr.getVar().getVariable();
		builder.append(variable.toString());
		return null;
	}

}
