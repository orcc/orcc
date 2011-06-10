/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package net.sf.orcc.backends.tta.architecture;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.Enumerator;

/**
 * <!-- begin-user-doc -->
 * A representation of the literals of the enumeration '<em><b>Socket Type</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see net.sf.orcc.backends.tta.architecture.ArchitecturePackage#getSocketType()
 * @model
 * @generated
 */
public enum SocketType implements Enumerator {
	/**
	 * The '<em><b>Input</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #INPUT_VALUE
	 * @generated
	 * @ordered
	 */
	INPUT(0, "input", "input"),

	/**
	 * The '<em><b>Output</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #OUTPUT_VALUE
	 * @generated
	 * @ordered
	 */
	OUTPUT(1, "output", "output");

	/**
	 * The '<em><b>Input</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Input</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #INPUT
	 * @model name="input"
	 * @generated
	 * @ordered
	 */
	public static final int INPUT_VALUE = 0;

	/**
	 * The '<em><b>Output</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Output</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #OUTPUT
	 * @model name="output"
	 * @generated
	 * @ordered
	 */
	public static final int OUTPUT_VALUE = 1;

	/**
	 * An array of all the '<em><b>Socket Type</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static final SocketType[] VALUES_ARRAY =
		new SocketType[] {
			INPUT,
			OUTPUT,
		};

	/**
	 * A public read-only list of all the '<em><b>Socket Type</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final List<SocketType> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>Socket Type</b></em>' literal with the specified literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static SocketType get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			SocketType result = VALUES_ARRAY[i];
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Socket Type</b></em>' literal with the specified name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static SocketType getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			SocketType result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Socket Type</b></em>' literal with the specified integer value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static SocketType get(int value) {
		switch (value) {
			case INPUT_VALUE: return INPUT;
			case OUTPUT_VALUE: return OUTPUT;
		}
		return null;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private final int value;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private final String name;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private final String literal;

	/**
	 * Only this class can construct instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private SocketType(int value, String name, String literal) {
		this.value = value;
		this.name = name;
		this.literal = literal;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getValue() {
	  return value;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getName() {
	  return name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getLiteral() {
	  return literal;
	}

	/**
	 * Returns the literal value of the enumerator, which is its string representation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		return literal;
	}
	
} //SocketType
