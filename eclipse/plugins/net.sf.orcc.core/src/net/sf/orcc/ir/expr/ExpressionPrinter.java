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
package net.sf.orcc.ir.expr;

import java.util.Iterator;
import java.util.List;

import net.sf.orcc.ir.ExprBinary;
import net.sf.orcc.ir.ExprBool;
import net.sf.orcc.ir.ExprFloat;
import net.sf.orcc.ir.ExprInt;
import net.sf.orcc.ir.ExprList;
import net.sf.orcc.ir.ExprString;
import net.sf.orcc.ir.ExprUnary;
import net.sf.orcc.ir.ExprVar;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.OpBinary;
import net.sf.orcc.ir.OpUnary;
import net.sf.orcc.ir.impl.ExprBinaryImpl;

/**
 * This class defines the default expression printer.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class ExpressionPrinter implements ExpressionVisitor {

	protected StringBuilder builder;

	/**
	 * Creates a new expression printer.
	 */
	public ExpressionPrinter() {
		builder = new StringBuilder();
	}

	@Override
	public String toString() {
		return builder.toString();
	}

	/**
	 * Returns the string representation of the given binary operator.
	 * 
	 * @param op
	 *            a binary operator
	 * @return the string representation of the given binary operator
	 */
	protected String toString(OpBinary op) {
		return op.getText();
	}

	/**
	 * Returns the string representation of the binary expression whose
	 * precedence level is given.
	 * 
	 * @param nextPrec
	 *            precedence level to be used when printing e1 and e2
	 * @param e1
	 *            left expression
	 * @param op
	 *            binary operator
	 * @param e2
	 *            right expression
	 * @return the string representation of the binary expression
	 */
	protected void toString(int nextPrec, Expression e1, OpBinary op,
			Expression e2) {
		e1.accept(this, nextPrec, ExprBinaryImpl.LEFT);
		builder.append(" ");
		builder.append(toString(op));
		builder.append(" ");
		e2.accept(this, nextPrec, ExprBinaryImpl.RIGHT);
	}

	/**
	 * Returns the string representation of the given unary operator.
	 * 
	 * @param op
	 *            a unary operator
	 * @return the string representation of the given unary operator
	 */
	protected String toString(OpUnary op) {
		return op.getText();
	}

	@Override
	public void visit(ExprBinary expr, Object... args) {
		OpBinary op = expr.getOp();

		if (op.needsParentheses(args)) {
			builder.append("(");
			toString(op.getPrecedence(), expr.getE1(), op, expr.getE2());
			builder.append(")");
		} else {
			toString(op.getPrecedence(), expr.getE1(), op, expr.getE2());
		}
	}

	@Override
	public void visit(ExprBool expr, Object... args) {
		builder.append(expr.isValue());
	}

	@Override
	public void visit(ExprInt expr, Object... args) {
		builder.append(expr.getValue());
	}

	@Override
	public void visit(ExprFloat expr, Object... args) {
		builder.append(expr.getValue());
	}

	@Override
	public void visit(ExprList expr, Object... args) {
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
	}

	@Override
	public void visit(ExprString expr, Object... args) {
		builder.append(expr.getValue());
	}

	@Override
	public void visit(ExprUnary expr, Object... args) {
		builder.append(toString(expr.getOp()));
		expr.getExpr().accept(this, Integer.MIN_VALUE);
	}

	@Override
	public void visit(ExprVar expr, Object... args) {
		builder.append(expr.getUse().getVariable().getIndexedName());
	}

}
