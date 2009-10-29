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

import net.sf.orcc.ir.IExpr;
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
 * This class defines an expression visitor that builds a textual representation
 * of an expression. Precedence is correctly handled for binary expressions by
 * passing the precedence of the parent expression as a parameter.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class ExprToString implements ExprVisitor {

	public static String toString(BinaryOp op) {
		switch (op) {
		case DIV_INT:
			throw new IllegalArgumentException(
					"integer division operator not supported");
		case EXP:
			throw new IllegalArgumentException(
					"exponentiation operator not supported");
		default:
			return op.getText();
		}
	}

	public static String toString(UnaryOp op) {
		switch (op) {
		case NUM_ELTS:
			throw new IllegalArgumentException(
					"number of elements operator not supported");
		default:
			return op.getText();
		}
	}

	protected StringBuilder builder;

	protected final VarDefPrinter varDefPrinter;

	/**
	 * Creates a new expression printer with the given variable printer.
	 * 
	 * @param varDefPrinter
	 *            a variable printer
	 */
	public ExprToString(VarDefPrinter varDefPrinter) {
		this.varDefPrinter = varDefPrinter;
	}

	/**
	 * Returns the string representation of the given expression.
	 * 
	 * @param expr
	 *            an expression
	 * @return the string representation of the given expression
	 */
	public String toString(IExpr expr) {
		builder = new StringBuilder();

		// parent precedence is the highest possible to prevent top-level binary
		// expression from being parenthesized
		expr.accept(this, Integer.MAX_VALUE);

		return builder.toString();
	}

	@Override
	public void visit(BinaryExpr expr, Object... args) {
		int parentPrec = (Integer) args[0];
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

		// if the parent precedence is lower than the precedence of this
		// operator, the current operation must be parenthesized to prevent the
		// first operand from being used by the parent operator instead of the
		// current one
		if (parentPrec < currentPrec) {
			builder.append("(");
			expr.getE1().accept(this, nextPrec);
			builder.append(" ");
			builder.append(toString(op));
			builder.append(" ");
			expr.getE2().accept(this, nextPrec);
			builder.append(")");
		} else {
			expr.getE1().accept(this, nextPrec);
			builder.append(" ");
			builder.append(toString(op));
			builder.append(" ");
			expr.getE2().accept(this, nextPrec);
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
	public void visit(UnaryExpr expr, Object... args) {
		builder.append(toString(expr.getOp()));
		expr.getExpr().accept(this, Integer.MIN_VALUE);
	}

	@Override
	public void visit(VarExpr expr, Object... args) {
		Variable variable = expr.getVar().getVariable();
		builder.append(varDefPrinter.getVarDefName(variable));
	}

}
