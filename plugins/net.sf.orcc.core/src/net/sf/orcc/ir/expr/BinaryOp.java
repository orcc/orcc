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

import java.util.HashMap;
import java.util.Map;

/**
 * This class defines the binary operators of the IR.
 * 
 * <p>
 * Below is a table of the operators sorted by increasing precedence. The higher
 * the precedence, the lower the operator "binds" to its operands.
 * </p>
 * 
 * <table border="1">
 * <tr>
 * <th>precedence level</th>
 * <th>operator</th>
 * </tr>
 * 
 * <tr>
 * <td>4</td>
 * <td>{@link #EXP}</td>
 * </tr>
 * 
 * <tr>
 * <td rowspan="4">5</td>
 * <td>{@link #TIMES}</td>
 * </tr>
 * <tr>
 * <td>{@link #DIV}</td>
 * </tr>
 * <tr>
 * <td>{@link #DIV_INT}</td>
 * </tr>
 * <tr>
 * <td>{@link #MOD}</td>
 * </tr>
 * 
 * <tr>
 * <td rowspan="2">6</td>
 * <td>{@link #PLUS}</td>
 * </tr>
 * <tr>
 * <td>{@link #MINUS}</td>
 * </tr>
 * 
 * <tr>
 * <td rowspan="2">7</td>
 * <td>{@link #SHIFT_LEFT}</td>
 * </tr>
 * <tr>
 * <td>{@link #SHIFT_RIGHT}</td>
 * </tr>
 * 
 * <tr>
 * <td rowspan="4">8</td>
 * <td>{@link #LT}</td>
 * </tr>
 * <tr>
 * <td>{@link #LE}</td>
 * </tr>
 * <tr>
 * <td>{@link #GT}</td>
 * </tr>
 * <tr>
 * <td>{@link #GE}</td>
 * </tr>
 * 
 * <tr>
 * <td rowspan="2">9</td>
 * <td>{@link #EQ}</td>
 * </tr>
 * <tr>
 * <td>{@link #NE}</td>
 * </tr>
 * 
 * <tr>
 * <td>10</td>
 * <td>{@link #BITAND}</td>
 * </tr>
 * 
 * <tr>
 * <td>11</td>
 * <td>{@link #BITXOR}</td>
 * </tr>
 * 
 * <tr>
 * <td>12</td>
 * <td>{@link #BITOR}</td>
 * </tr>
 * 
 * <tr>
 * <td>13</td>
 * <td>{@link #LOGIC_AND}</td>
 * </tr>
 * 
 * <tr>
 * <td>14</td>
 * <td>{@link #LOGIC_OR}</td>
 * </tr>
 * 
 * </table>
 * 
 * @author Matthieu Wipliez
 * 
 */
public enum BinaryOp {

	/**
	 * bitand <code>&</code>
	 */
	BITAND(10, "&", false),

	/**
	 * bitor <code>|</code>
	 */
	BITOR(12, "|", false),

	/**
	 * bitxor <code>^</code>
	 */
	BITXOR(11, "^", false),

	/**
	 * division <code>/</code>
	 */
	DIV(5, "/", false),

	/**
	 * integer division <code>div</code>
	 */
	DIV_INT(5, "div", false),

	/**
	 * equal <code>==</code>
	 */
	EQ(9, "==", false),

	/**
	 * exponentiation <code>**</code>
	 */
	EXP(3, "**", true),

	/**
	 * greater than or equal <code>&gt;=</code>
	 */
	GE(8, ">=", false),

	/**
	 * greater than <code>&gt;</code>
	 */
	GT(8, ">", false),

	/**
	 * less than or equal <code>&lt;=</code>
	 */
	LE(8, "<=", false),

	/**
	 * logical and <code>&&</code>
	 */
	LOGIC_AND(13, "&&", false),

	/**
	 * logical or <code>||</code>
	 */
	LOGIC_OR(14, "||", false),

	/**
	 * less than <code>&lt;</code>
	 */
	LT(8, "<", false),

	/**
	 * minus <code>-</code>
	 */
	MINUS(6, "-", false),

	/**
	 * modulo <code>%</code>
	 */
	MOD(5, "%", false),

	/**
	 * not equal <code>!=</code>
	 */
	NE(9, "!=", false),

	/**
	 * plus <code>+</code>
	 */
	PLUS(6, "+", false),

	/**
	 * shift left <code>&lt;&lt;</code>
	 */
	SHIFT_LEFT(7, "<<", false),

	/**
	 * shift right <code>&gt;&gt;</code>
	 */
	SHIFT_RIGHT(7, ">>", false),

	/**
	 * times <code>*</code>
	 */
	TIMES(5, "*", false);

	private static final Map<String, BinaryOp> operators = new HashMap<String, BinaryOp>();

	static {
		for (BinaryOp op : BinaryOp.values()) {
			operators.put(op.text, op);
		}

		operators.put("=", EQ);
		operators.put("and", LOGIC_AND);
		operators.put("or", LOGIC_OR);
		operators.put("mod", MOD);
		operators.put("bitand", BITAND);
		operators.put("bitor", BITOR);
		operators.put("bitxor", BITXOR);
		operators.put("lshift", SHIFT_LEFT);
		operators.put("rshift", SHIFT_RIGHT);
	}

	/**
	 * Returns the binary operator that has the given name.
	 * 
	 * @param name
	 *            an operator name
	 * @return a binary operator, or <code>null</code> if the operator is
	 *         unknown
	 */
	public static BinaryOp getOperator(String name) {
		return operators.get(name);
	}

	/**
	 * precedence of this operator
	 */
	private int precedence;

	/**
	 * true if this operator is right-to-left associative.
	 */
	private boolean rightAssociative;

	/**
	 * textual representation of this operator
	 */
	private String text;

	/**
	 * Creates a new binary operator with the given precedence.
	 * 
	 * @param precedence
	 *            the operator's precedence
	 * @param text
	 *            textual representation of this operator
	 * @param rightAssociative
	 *            <code>true</code> if this operator is right-associative.
	 */
	private BinaryOp(int precedence, String text, boolean rightAssociative) {
		this.precedence = precedence;
		this.text = text;
		this.rightAssociative = rightAssociative;
	}

	/**
	 * Returns the inverse operator, e.g. if this operator is {@link #GE},
	 * returns {@link #LT}. If this operator has no inverse, returns itself.
	 * 
	 * @return the inverse operator
	 */
	public BinaryOp getInverse() {
		switch (this) {
		case DIV:
			return TIMES;
		case DIV_INT:
			return TIMES;
		case EQ:
			return NE;
		case GE:
			return LT;
		case GT:
			return LE;
		case LE:
			return GT;
		case LT:
			return GE;
		case MINUS:
			return PLUS;
		case NE:
			return EQ;
		case PLUS:
			return MINUS;
		case SHIFT_LEFT:
			return SHIFT_RIGHT;
		case SHIFT_RIGHT:
			return SHIFT_LEFT;
		case TIMES:
			return DIV;
		default:
			return this;
		}
	}

	/**
	 * Returns this operator's precedence. An operator O1 that has a lower
	 * precedence than another operator O2 means that the operation involving O1
	 * is to be evaluated first.
	 * 
	 * @return this operator's precedence
	 */
	public int getPrecedence() {
		return precedence;
	}

	/**
	 * If this operator is an inequality operator (greater/less than (or equal
	 * to)), returns the reverse operator, e.g. if this operator is {@link #GE},
	 * returns {@link #LE}; this is different from {@link #getInverse()}, which
	 * would return {@link #LT} in this case. If this operator is not an
	 * inequality operator, returns <code>this</code>.
	 * 
	 * @return if this operator is an inequality operator, returns the reverse
	 *         operator; otherwise, returns <code>this</code>
	 */
	public BinaryOp getReversedInequality() {
		switch (this) {
		case GE:
			return LE;
		case GT:
			return LT;
		case LE:
			return GE;
		case LT:
			return GT;
		default:
			return this;
		}
	}

	/**
	 * Returns the textual representation of this operator.
	 * 
	 * @return the textual representation of this operator
	 */
	public String getText() {
		return text;
	}

	/**
	 * Returns a boolean indicating if this operator is a comparison operator
	 * ((not) equal, greater/less than (or equal to)).
	 * 
	 * @return <code>true</code> if this operator is a comparison operator,
	 *         <code>false</code> otherwise
	 */
	public boolean isComparison() {
		switch (this) {
		case EQ:
		case GE:
		case GT:
		case LE:
		case LT:
		case NE:
			return true;
		default:
			return false;
		}
	}

	/**
	 * Returns true if this operator is right-to-left associative.
	 * 
	 * @return true if this operator is right-to-left associative
	 */
	public boolean isRightAssociative() {
		return rightAssociative;
	}

	/**
	 * Returns true if a binary expression involving this operator needs
	 * parentheses, given the precedence of the parent expression and the
	 * associativity of this operator.
	 * 
	 * @param args
	 *            arguments such that <code>args[0]</code> is the precedence of
	 *            the parent expression, and <code>args[1]</code> is the branch
	 *            if the parent expression is a binary expression
	 * @return <code>true</code> if a binary expression involving this operator
	 *         needs parentheses
	 */
	public boolean needsParentheses(Object[] args) {
		int parentPrec = (Integer) args[0];
		int currentPrec = getPrecedence();
		if (parentPrec < currentPrec) {
			// if the parent precedence is lower than the precedence of this
			// operator, the current expression must be parenthesized to prevent
			// the first operand from being used by the parent operator instead
			// of the current one

			return true;
		} else if (parentPrec == currentPrec) {
			// if the parent precedence is the same as the precedence of this
			// operator, the current expression must be parenthesized if the
			// expression tree contradicts the normal operator precedence as
			// implemented by the test below

			// parent is a binary expression, so args[1] is defined
			Object thisBranch = args[1];
			boolean res;
			if (isRightAssociative()) {
				res = (thisBranch == BinaryExpr.LEFT);
			} else {
				res = (thisBranch == BinaryExpr.RIGHT);
			}
			return res;
		} else {
			return false;
		}
	}

}
