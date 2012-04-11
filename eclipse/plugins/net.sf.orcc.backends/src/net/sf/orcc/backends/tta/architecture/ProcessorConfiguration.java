/**
 * <copyright>
 * Copyright (c) 2009-2012, IETR/INSA of Rennes
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
 * </copyright>
 */
package net.sf.orcc.backends.tta.architecture;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.Enumerator;

/**
 * <!-- begin-user-doc --> A representation of the literals of the enumeration '
 * <em><b>Processor Configuration</b></em>', and utility methods for working
 * with them. <!-- end-user-doc -->
 * 
 * @see net.sf.orcc.backends.tta.architecture.ArchitecturePackage#getProcessorConfiguration()
 * @model
 * @generated
 */
public enum ProcessorConfiguration implements Enumerator {
	/**
	 * The '<em><b>Standard</b></em>' literal object. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #STANDARD_VALUE
	 * @generated
	 * @ordered
	 */
	STANDARD(0, "Standard", "Standard"),

	/**
	 * The '<em><b>Custom</b></em>' literal object. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see #CUSTOM_VALUE
	 * @generated
	 * @ordered
	 */
	CUSTOM(1, "Custom", "Custom"),

	/**
	 * The '<em><b>Huge</b></em>' literal object. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see #HUGE_VALUE
	 * @generated
	 * @ordered
	 */
	HUGE(2, "Huge", "Huge");

	/**
	 * The '<em><b>Standard</b></em>' literal value. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Standard</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @see #STANDARD
	 * @model name="Standard"
	 * @generated
	 * @ordered
	 */
	public static final int STANDARD_VALUE = 0;

	/**
	 * The '<em><b>Custom</b></em>' literal value. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Custom</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @see #CUSTOM
	 * @model name="Custom"
	 * @generated
	 * @ordered
	 */
	public static final int CUSTOM_VALUE = 1;

	/**
	 * The '<em><b>Huge</b></em>' literal value. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Huge</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @see #HUGE
	 * @model name="Huge"
	 * @generated
	 * @ordered
	 */
	public static final int HUGE_VALUE = 2;

	/**
	 * An array of all the '<em><b>Processor Configuration</b></em>'
	 * enumerators. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private static final ProcessorConfiguration[] VALUES_ARRAY = new ProcessorConfiguration[] {
			STANDARD, CUSTOM, HUGE, };

	/**
	 * A public read-only list of all the '
	 * <em><b>Processor Configuration</b></em>' enumerators. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public static final List<ProcessorConfiguration> VALUES = Collections
			.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>Processor Configuration</b></em>' literal with the
	 * specified literal value. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public static ProcessorConfiguration get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			ProcessorConfiguration result = VALUES_ARRAY[i];
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Processor Configuration</b></em>' literal with the
	 * specified name. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public static ProcessorConfiguration getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			ProcessorConfiguration result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Processor Configuration</b></em>' literal with the
	 * specified integer value. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public static ProcessorConfiguration get(int value) {
		switch (value) {
		case STANDARD_VALUE:
			return STANDARD;
		case CUSTOM_VALUE:
			return CUSTOM;
		case HUGE_VALUE:
			return HUGE;
		}
		return null;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private final int value;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private final String name;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private final String literal;

	/**
	 * Only this class can construct instances. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 */
	private ProcessorConfiguration(int value, String name, String literal) {
		this.value = value;
		this.name = name;
		this.literal = literal;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public int getValue() {
		return value;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public String getName() {
		return name;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public String getLiteral() {
		return literal;
	}

	/**
	 * Returns the literal value of the enumerator, which is its string
	 * representation. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public String toString() {
		return literal;
	}

	public int getAluNb() {
		switch (this) {
		case CUSTOM:
			return 4;
		case HUGE:
			return 12;
		case STANDARD:
		default:
			return 1;
		}
	}

	public int getBusNb() {
		switch (this) {
		case CUSTOM:
			return 6;
		case HUGE:
			return 32;
		case STANDARD:
		default:
			return 2;
		}
	}

	public int getIntRfNb() {
		switch (this) {
		case CUSTOM:
			return 4;
		case HUGE:
			return 8;
		case STANDARD:
		default:
			return 2;
		}
	}

	public int getMulNb() {
		switch (this) {
		case HUGE:
			return 8;
		case STANDARD:
		case CUSTOM:
		default:
			return 1;
		}
	}

	public int getLsuNb() {
		switch (this) {
		case HUGE:
			return 2;
		case STANDARD:
		case CUSTOM:
		default:
			return 1;
		}
	}

	public int getIntRfSize() {
		switch (this) {
		case HUGE:
			return 32;
		case STANDARD:
		case CUSTOM:
		default:
			return 12;
		}
	}

	public int getBoolRfSize() {
		switch (this) {
		case HUGE:
			return 3;
		case STANDARD:
		case CUSTOM:
		default:
			return 2;
		}
	}

} // ProcessorConfiguration
