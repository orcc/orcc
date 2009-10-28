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

import net.sf.orcc.OrccException;

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
	}

	/**
	 * Returns the binary operator that has the given name.
	 * 
	 * @param name
	 *            an operator name
	 * @return a binary operator
	 * @throws OrccException
	 *             if there is no operator with the given name
	 */
	public static BinaryOp getOperator(String name) throws OrccException {
		BinaryOp op = operators.get(name);
		if (op == null) {
			throw new OrccException("unknown operator \"" + name + "\"");
		} else {
			return op;
		}
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
	 * Returns the textual representation of this operator.
	 * 
	 * @return the textual representation of this operator
	 */
	public String getText() {
		return text;
	}

	/**
	 * Returns true if this operator is right-to-left associative.
	 * 
	 * @return true if this operator is right-to-left associative
	 */
	public boolean isRightAssociative() {
		return rightAssociative;
	}
}
