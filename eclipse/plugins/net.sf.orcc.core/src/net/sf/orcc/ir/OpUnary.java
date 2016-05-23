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

import java.lang.String;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.eclipse.emf.common.util.Enumerator;
import java.util.HashMap;
import java.util.Map;

import net.sf.orcc.OrccRuntimeException;

/**
 * This class defines the unary operators of the IR.
 * 
 * @author Matthieu Wipliez
 * @model
 */
public enum OpUnary implements Enumerator {

	/**
	 * a binary not <code>~</code>
	 * 
	 * @model
	 */
	BITNOT(0, "BITNOT", "BITNOT"),

	/**
	 * a logical not <code>!</code>
	 * 
	 * @model
	 */
	LOGIC_NOT(1, "LOGIC_NOT", "LOGIC_NOT"),

	/**
	 * unary minus <code>-</code>
	 * 
	 * @model
	 */
	MINUS(2, "MINUS", "MINUS"),

	/**
	 * number of elements <code>#</code>
	 * 
	 * @model
	 */
	NUM_ELTS(3, "NUM_ELTS", "NUM_ELTS");

	/**
	 * The '<em><b>BITNOT</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>BITNOT</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #BITNOT
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int BITNOT_VALUE = 0;

	/**
	 * The '<em><b>LOGIC NOT</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>LOGIC NOT</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #LOGIC_NOT
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int LOGIC_NOT_VALUE = 1;

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
	public static final int MINUS_VALUE = 2;

	private static final String[] NAMES_ARRAY = new String[] { "~", "!", "-", "#" };

	/**
	 * The '<em><b>NUM ELTS</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>NUM ELTS</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #NUM_ELTS
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int NUM_ELTS_VALUE = 3;

	private static final Map<String, OpUnary> operators = new HashMap<String, OpUnary>();

	/**
	 * An array of all the '<em><b>Op Unary</b></em>' enumerators. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private static final OpUnary[] VALUES_ARRAY = new OpUnary[] { BITNOT, LOGIC_NOT, MINUS, NUM_ELTS, };

	/**
	 * A public read-only list of all the '<em><b>Op Unary</b></em>' enumerators.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public static final List<OpUnary> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	static {
		for (OpUnary op : OpUnary.values()) {
			operators.put(op.getText(), op);
		}

		operators.put("not", LOGIC_NOT);
	}

	/**
	 * Returns the '<em><b>Op Unary</b></em>' literal with the specified integer value.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public static OpUnary get(int value) {
		switch (value) {
		case BITNOT_VALUE:
			return BITNOT;
		case LOGIC_NOT_VALUE:
			return LOGIC_NOT;
		case MINUS_VALUE:
			return MINUS;
		case NUM_ELTS_VALUE:
			return NUM_ELTS;
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Op Unary</b></em>' literal with the specified literal value.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public static OpUnary get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			OpUnary result = VALUES_ARRAY[i];
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Op Unary</b></em>' literal with the specified name.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public static OpUnary getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			OpUnary result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the unary operator that has the given name.
	 * 
	 * @param name
	 *            an operator name
	 * @return a unary operator
	 * @throws OrccRuntimeException
	 *             if there is no operator with the given name
	 */
	public static OpUnary getOperator(String name) throws OrccRuntimeException {
		OpUnary op = operators.get(name);
		if (op == null) {
			throw new OrccRuntimeException("unknown operator \"" + name + "\"");
		} else {
			return op;
		}
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
	private OpUnary(int value, String name, String literal) {
		this.value = value;
		this.name = name;
		this.literal = literal;
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
	 * Returns the literal value of the enumerator, which is its string representation.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		return literal;
	}

}
