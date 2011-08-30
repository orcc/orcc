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
package net.sf.orcc.ir.util;

import java.util.Iterator;

import org.eclipse.emf.ecore.EObject;

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

/**
 * This class defines the default expression printer.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class ExpressionPrinter extends IrSwitch<String> {

	protected int branch;

	protected int precedence;

	/**
	 * Creates a new expression printer.
	 */
	public ExpressionPrinter() {
		branch = 0; // left
		precedence = Integer.MAX_VALUE;
	}

	@Override
	public String caseExprBinary(ExprBinary expr) {
		OpBinary op = expr.getOp();
		if (op.needsParentheses(precedence, branch)) {
			return "(" + doSwitch(expr.getE1(), op.getPrecedence(), 0) + " "
					+ toString(op) + " "
					+ doSwitch(expr.getE2(), op.getPrecedence(), 1) + ")";
		} else {
			return doSwitch(expr.getE1(), op.getPrecedence(), 0) + " "
					+ toString(op) + " "
					+ doSwitch(expr.getE2(), op.getPrecedence(), 1);
		}
	}

	@Override
	public String caseExprBool(ExprBool expr) {
		return String.valueOf(expr.isValue());
	}

	@Override
	public String caseExprFloat(ExprFloat expr) {
		return String.valueOf(expr.getValue());
	}

	@Override
	public String caseExprInt(ExprInt expr) {
		return String.valueOf(expr.getValue());
	}

	@Override
	public String caseExprList(ExprList expr) {
		StringBuilder builder = new StringBuilder();
		builder.append('[');

		Iterator<Expression> it = expr.getValue().iterator();
		if (it.hasNext()) {
			builder.append(doSwitch(it.next()));
			while (it.hasNext()) {
				builder.append(", ").append(
						doSwitch(it.next(), Integer.MAX_VALUE, 0));
			}
		}

		return builder.append(']').toString();
	}

	@Override
	public String caseExprString(ExprString expr) {
		return '"' + String.valueOf(expr.getValue()) + '"';
	}

	@Override
	public String caseExprUnary(ExprUnary expr) {
		return toString(expr.getOp())
				+ doSwitch(expr.getExpr(), Integer.MIN_VALUE, branch);
	}

	@Override
	public String caseExprVar(ExprVar expr) {
		return expr.getUse().getVariable().getIndexedName();
	}

	@Override
	public String doSwitch(EObject theEObject) {
		if (theEObject == null) {
			return "null";
		} else {
			return super.doSwitch(theEObject);
		}
	}

	public String doSwitch(Expression expression, int newPrecedence,
			int newBranch) {
		int oldBranch = branch;
		int oldPrecedence = precedence;

		branch = newBranch;
		precedence = newPrecedence;
		String result = doSwitch(expression);
		precedence = oldPrecedence;
		branch = oldBranch;
		return result;
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
	 * Returns the string representation of the given unary operator.
	 * 
	 * @param op
	 *            a unary operator
	 * @return the string representation of the given unary operator
	 */
	protected String toString(OpUnary op) {
		return op.getText();
	}

}
