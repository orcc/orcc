/*
 * Copyright (c) 2009-2010, IETR/INSA of Rennes
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
package net.sf.orcc.ir.impl;

import net.sf.orcc.ir.IrPackage;
import net.sf.orcc.ir.Type;
import net.sf.orcc.ir.type.TypeInterpreter;
import net.sf.orcc.ir.type.TypePrinter;
import net.sf.orcc.ir.type.TypeVisitor;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.EDataTypeEList;

/**
 * This class is an abstract implementation of {@link Type}.
 * 
 * @author Matthieu Wipliez
 * @generated
 * 
 */
public abstract class TypeImpl extends EObjectImpl implements Type {

	/**
	 * The default value of the '{@link #isBool() <em>Bool</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #isBool()
	 * @generated
	 * @ordered
	 */
	protected static final boolean BOOL_EDEFAULT = false;

	/**
	 * The default value of the '{@link #isFloat() <em>Float</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #isFloat()
	 * @generated
	 * @ordered
	 */
	protected static final boolean FLOAT_EDEFAULT = false;

	/**
	 * The default value of the '{@link #isInt() <em>Int</em>}' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #isInt()
	 * @generated
	 * @ordered
	 */
	protected static final boolean INT_EDEFAULT = false;

	/**
	 * The default value of the '{@link #isList() <em>List</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #isList()
	 * @generated
	 * @ordered
	 */
	protected static final boolean LIST_EDEFAULT = false;

	/**
	 * The default value of the '{@link #isString() <em>String</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #isString()
	 * @generated
	 * @ordered
	 */
	protected static final boolean STRING_EDEFAULT = false;

	/**
	 * The default value of the '{@link #isUint() <em>Uint</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #isUint()
	 * @generated
	 * @ordered
	 */
	protected static final boolean UINT_EDEFAULT = false;

	/**
	 * The default value of the '{@link #isVoid() <em>Void</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #isVoid()
	 * @generated
	 * @ordered
	 */
	protected static final boolean VOID_EDEFAULT = false;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	protected TypeImpl() {
		super();
	}

	@Override
	public abstract Object accept(TypeInterpreter interpreter);

	@Override
	public abstract void accept(TypeVisitor visitor);

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case IrPackage.TYPE__DIMENSIONS:
				return getDimensions();
			case IrPackage.TYPE__BOOL:
				return isBool();
			case IrPackage.TYPE__FLOAT:
				return isFloat();
			case IrPackage.TYPE__INT:
				return isInt();
			case IrPackage.TYPE__LIST:
				return isList();
			case IrPackage.TYPE__STRING:
				return isString();
			case IrPackage.TYPE__UINT:
				return isUint();
			case IrPackage.TYPE__VOID:
				return isVoid();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case IrPackage.TYPE__DIMENSIONS:
				return !getDimensions().isEmpty();
			case IrPackage.TYPE__BOOL:
				return isBool() != BOOL_EDEFAULT;
			case IrPackage.TYPE__FLOAT:
				return isFloat() != FLOAT_EDEFAULT;
			case IrPackage.TYPE__INT:
				return isInt() != INT_EDEFAULT;
			case IrPackage.TYPE__LIST:
				return isList() != LIST_EDEFAULT;
			case IrPackage.TYPE__STRING:
				return isString() != STRING_EDEFAULT;
			case IrPackage.TYPE__UINT:
				return isUint() != UINT_EDEFAULT;
			case IrPackage.TYPE__VOID:
				return isVoid() != VOID_EDEFAULT;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return IrPackage.Literals.TYPE;
	}

	@Override
	public EList<Integer> getDimensions() {
		return new EDataTypeEList<Integer>(Integer.class, this,
				IrPackage.TYPE__DIMENSIONS);
	}

	@Override
	public boolean isBool() {
		return false;
	}

	@Override
	public boolean isFloat() {
		return false;
	}

	@Override
	public boolean isInt() {
		return false;
	}

	@Override
	public boolean isList() {
		return false;
	}

	@Override
	public boolean isString() {
		return false;
	}

	@Override
	public boolean isUint() {
		return false;
	}

	@Override
	public boolean isVoid() {
		return false;
	}

	@Override
	public String toString() {
		TypePrinter printer = new TypePrinter();
		accept(printer);
		return printer.toString();
	}

}
