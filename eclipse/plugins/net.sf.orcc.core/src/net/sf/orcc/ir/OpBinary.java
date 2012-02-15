/*
 * Copyright (c) 2009-2011, IETR/INSA of Rennes
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
package net.sf.orcc.ir;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.util.Enumerator;

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
 * @model
 */
public enum OpBinary implements Enumerator {

	/**
	 * bitand <code>&</code>
	 * 
	 * @model
	 */
	BITAND(0, "BITAND", "BITAND"),

	/**
	 * bitor <code>|</code>
	 * 
	 * @model
	 */
	BITOR(1, "BITOR", "BITOR"),

	/**
	 * bitxor <code>^</code>
	 * 
	 * @model
	 */
	BITXOR(2, "BITXOR", "BITXOR"),

	/**
	 * division <code>/</code>
	 * 
	 * @model
	 */
	DIV(3, "DIV", "DIV"),

	/**
	 * integer division <code>div</code>
	 * 
	 * @model
	 */
	DIV_INT(4, "DIV_INT", "DIV_INT"),

	/**
	 * equal <code>==</code>
	 * 
	 * @model
	 */
	EQ(5, "EQ", "EQ"),

	/**
	 * exponentiation <code>**</code>
	 * 
	 * @model
	 */
	EXP(6, "EXP", "EXP"),

	/**
	 * greater than or equal <code>&gt;=</code>
	 * 
	 * @model
	 */
	GE(7, "GE", "GE"),

	/**
	 * greater than <code>&gt;</code>
	 * 
	 * @model
	 */
	GT(8, "GT", "GT"),

	/**
	 * less than or equal <code>&lt;=</code>
	 * 
	 * @model
	 */
	LE(9, "LE", "LE"),

	/**
	 * logical and <code>&&</code>
	 * 
	 * @model
	 */
	LOGIC_AND(10, "LOGIC_AND", "LOGIC_AND"),

	/**
	 * logical or <code>||</code>
	 * 
	 * @model
	 */
	LOGIC_OR(11, "LOGIC_OR", "LOGIC_OR"),

	/**
	 * less than <code>&lt;</code>
	 * 
	 * @model
	 */
	LT(12, "LT", "LT"),

	/**
	 * minus <code>-</code>
	 * 
	 * @model
	 */
	MINUS(13, "MINUS", "MINUS"),

	/**
	 * modulo <code>%</code>
	 * 
	 * @model
	 */
	MOD(14, "MOD", "MOD"),

	/**
	 * not equal <code>!=</code>
	 * 
	 * @model
	 */
	NE(15, "NE", "NE"),

	/**
	 * plus <code>+</code>
	 * 
	 * @model
	 */
	PLUS(16, "PLUS", "PLUS"),

	/**
	 * shift left <code>&lt;&lt;</code>
	 * 
	 * @model
	 */
	SHIFT_LEFT(17, "SHIFT_LEFT", "SHIFT_LEFT"),

	/**
	 * shift right <code>&gt;&gt;</code>
	 * 
	 * @model
	 */
	SHIFT_RIGHT(18, "SHIFT_RIGHT", "SHIFT_RIGHT"),

	/**
	 * times <code>*</code>
	 * 
	 * @model
	 */
	TIMES(19, "TIMES", "TIMES");

	private static final boolean[] ASSOCIATIVITIES_ARRAY = new boolean[] {
			false, false, false, false, false, false, true, false, false,
			false, false, false, false, false, false, false, false, false,
			false, false };

	/**
	 * The '<em><b>BITAND</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>BITAND</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #BITAND
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int BITAND_VALUE = 0;

	/**
	 * The '<em><b>BITOR</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>BITOR</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #BITOR
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int BITOR_VALUE = 1;

	/**
	 * The '<em><b>BITXOR</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>BITXOR</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #BITXOR
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int BITXOR_VALUE = 2;

	/**
	 * The '<em><b>DIV</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>DIV</b></em>' literal object isn't clear, there
	 * really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #DIV
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int DIV_VALUE = 3;

	/**
	 * The '<em><b>DIV INT</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>DIV INT</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #DIV_INT
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int DIV_INT_VALUE = 4;

	/**
	 * The '<em><b>EQ</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>EQ</b></em>' literal object isn't clear, there
	 * really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #EQ
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int EQ_VALUE = 5;

	/**
	 * The '<em><b>EXP</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>EXP</b></em>' literal object isn't clear, there
	 * really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #EXP
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int EXP_VALUE = 6;

	/**
	 * The '<em><b>GE</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>GE</b></em>' literal object isn't clear, there
	 * really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #GE
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int GE_VALUE = 7;

	/**
	 * The '<em><b>GT</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>GT</b></em>' literal object isn't clear, there
	 * really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #GT
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int GT_VALUE = 8;

	/**
	 * The '<em><b>LE</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>LE</b></em>' literal object isn't clear, there
	 * really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #LE
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int LE_VALUE = 9;

	/**
	 * The '<em><b>LOGIC AND</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>LOGIC AND</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #LOGIC_AND
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int LOGIC_AND_VALUE = 10;

	/**
	 * The '<em><b>LOGIC OR</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>LOGIC OR</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #LOGIC_OR
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int LOGIC_OR_VALUE = 11;

	/**
	 * The '<em><b>LT</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>LT</b></em>' literal object isn't clear, there
	 * really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #LT
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int LT_VALUE = 12;

	/**
	 * The '<em><b>MINUS</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>MINUS</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #MINUS
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int MINUS_VALUE = 13;

	/**
	 * The '<em><b>MOD</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>MOD</b></em>' literal object isn't clear, there
	 * really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #MOD
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int MOD_VALUE = 14;

	private static final String[] NAMES_ARRAY = new String[] { "&", "|", "^",
			"/", "div", "==", "**", ">=", ">", "<=", "&&", "||", "<", "-", "%",
			"!=", "+", "<<", ">>", "*" };

	/**
	 * The '<em><b>NE</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>NE</b></em>' literal object isn't clear, there
	 * really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #NE
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int NE_VALUE = 15;

	private static final Map<String, OpBinary> operators = new HashMap<String, OpBinary>();

	/**
	 * The '<em><b>PLUS</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>PLUS</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #PLUS
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int PLUS_VALUE = 16;

	private static final int[] PRECEDENCES_ARRAY = new int[] { 10, 12, 11, 5,
			5, 9, 3, 8, 8, 8, 13, 14, 8, 6, 5, 9, 6, 7, 7, 5 };

	/**
	 * The '<em><b>SHIFT LEFT</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>SHIFT LEFT</b></em>' literal object isn't
	 * clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #SHIFT_LEFT
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int SHIFT_LEFT_VALUE = 17;

	/**
	 * The '<em><b>SHIFT RIGHT</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>SHIFT RIGHT</b></em>' literal object isn't
	 * clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #SHIFT_RIGHT
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int SHIFT_RIGHT_VALUE = 18;

	/**
	 * The '<em><b>TIMES</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>TIMES</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #TIMES
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int TIMES_VALUE = 19;

	/**
	 * An array of all the '<em><b>Binary Op</b></em>' enumerators. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private static final OpBinary[] VALUES_ARRAY = new OpBinary[] { BITAND,
			BITOR, BITXOR, DIV, DIV_INT, EQ, EXP, GE, GT, LE, LOGIC_AND,
			LOGIC_OR, LT, MINUS, MOD, NE, PLUS, SHIFT_LEFT, SHIFT_RIGHT, TIMES, };

	/**
	 * A public read-only list of all the '<em><b>Op Binary</b></em>' enumerators.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public static final List<OpBinary> VALUES = Collections
			.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	static {
		for (OpBinary op : OpBinary.values()) {
			operators.put(op.getText(), op);
		}

		operators.put("=", EQ);
		operators.put("and", LOGIC_AND);
		operators.put("or", LOGIC_OR);
		operators.put("mod", MOD);
	}

	/**
	 * Returns the '<em><b>Op Binary</b></em>' literal with the specified integer value.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public static OpBinary get(int value) {
		switch (value) {
		case BITAND_VALUE:
			return BITAND;
		case BITOR_VALUE:
			return BITOR;
		case BITXOR_VALUE:
			return BITXOR;
		case DIV_VALUE:
			return DIV;
		case DIV_INT_VALUE:
			return DIV_INT;
		case EQ_VALUE:
			return EQ;
		case EXP_VALUE:
			return EXP;
		case GE_VALUE:
			return GE;
		case GT_VALUE:
			return GT;
		case LE_VALUE:
			return LE;
		case LOGIC_AND_VALUE:
			return LOGIC_AND;
		case LOGIC_OR_VALUE:
			return LOGIC_OR;
		case LT_VALUE:
			return LT;
		case MINUS_VALUE:
			return MINUS;
		case MOD_VALUE:
			return MOD;
		case NE_VALUE:
			return NE;
		case PLUS_VALUE:
			return PLUS;
		case SHIFT_LEFT_VALUE:
			return SHIFT_LEFT;
		case SHIFT_RIGHT_VALUE:
			return SHIFT_RIGHT;
		case TIMES_VALUE:
			return TIMES;
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Op Binary</b></em>' literal with the specified literal value.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public static OpBinary get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			OpBinary result = VALUES_ARRAY[i];
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Op Binary</b></em>' literal with the specified name.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public static OpBinary getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			OpBinary result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the binary operator that has the given name.
	 * 
	 * @param name
	 *            an operator name
	 * @return a binary operator, or <code>null</code> if the operator is
	 *         unknown
	 */
	public static OpBinary getOperator(String name) {
		return operators.get(name);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	private final String literal;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	private final String name;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	private final int value;

	/**
	 * Only this class can construct instances.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @generated
	 */
	private OpBinary(int value, String name, String literal) {
		this.value = value;
		this.name = name;
		this.literal = literal;
	}

	/**
	 * Returns the inverse operator, e.g. if this operator is {@link #GE},
	 * returns {@link #LT}. If this operator has no inverse, returns itself.
	 * 
	 * @return the inverse operator
	 */
	public OpBinary getInverse() {
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
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public String getLiteral() {
		return literal;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public String getName() {
		return name;
	}

	/**
	 * Returns this operator's precedence. An operator O1 that has a lower
	 * precedence than another operator O2 means that the operation involving O1
	 * is to be evaluated first.
	 * 
	 * @return this operator's precedence
	 */
	public int getPrecedence() {
		return PRECEDENCES_ARRAY[value];
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
	public OpBinary getReversedInequality() {
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
		return NAMES_ARRAY[value];
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public int getValue() {
		return value;
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
		return ASSOCIATIVITIES_ARRAY[value];
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
	public boolean needsParentheses(int parentPrec, int branch) {
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

			if (isRightAssociative()) {
				return (branch == 0);
			} else {
				return (branch == 1);
			}
		} else {
			return false;
		}
	}

	/**
	 * Returns the literal value of the enumerator, which is its string representation.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		return literal;
	}

}
