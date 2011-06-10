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
 * A representation of the literals of the enumeration '<em><b>Extension</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see net.sf.orcc.backends.tta.architecture.ArchitecturePackage#getExtension()
 * @model
 * @generated
 */
public enum Extension implements Enumerator {
	/**
	 * The '<em><b>Sign</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #SIGN_VALUE
	 * @generated
	 * @ordered
	 */
	SIGN(0, "sign", "sign"),

	/**
	 * The '<em><b>Zero</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #ZERO_VALUE
	 * @generated
	 * @ordered
	 */
	ZERO(1, "zero", "zero");

	/**
	 * The '<em><b>Sign</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Sign</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #SIGN
	 * @model name="sign"
	 * @generated
	 * @ordered
	 */
	public static final int SIGN_VALUE = 0;

	/**
	 * The '<em><b>Zero</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Zero</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #ZERO
	 * @model name="zero"
	 * @generated
	 * @ordered
	 */
	public static final int ZERO_VALUE = 1;

	/**
	 * An array of all the '<em><b>Extension</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static final Extension[] VALUES_ARRAY =
		new Extension[] {
			SIGN,
			ZERO,
		};

	/**
	 * A public read-only list of all the '<em><b>Extension</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final List<Extension> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>Extension</b></em>' literal with the specified literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static Extension get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			Extension result = VALUES_ARRAY[i];
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Extension</b></em>' literal with the specified name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static Extension getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			Extension result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Extension</b></em>' literal with the specified integer value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static Extension get(int value) {
		switch (value) {
			case SIGN_VALUE: return SIGN;
			case ZERO_VALUE: return ZERO;
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
	private Extension(int value, String name, String literal) {
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
	
} //Extension
