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
package net.sf.orcc.ir.type.impl;

import net.sf.orcc.ir.Type;
import net.sf.orcc.ir.impl.TypeImpl;
import net.sf.orcc.ir.type.ListType;
import net.sf.orcc.ir.type.TypeInterpreter;
import net.sf.orcc.ir.type.TypePackage;
import net.sf.orcc.ir.type.TypeVisitor;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EDataTypeEList;

/**
 * This class defines a List type.
 * 
 * @author Matthieu Wipliez
 * @author Jérôme Gorin
 * 
 */
public class ListTypeImpl extends TypeImpl implements ListType {

	/**
	 * The default value of the '{@link #getSize() <em>Size</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getSize()
	 * @generated
	 * @ordered
	 */
	protected static final int SIZE_EDEFAULT = 0;
	/**
	 * The cached value of the '{@link #getSize() <em>Size</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getSize()
	 * @generated
	 * @ordered
	 */
	protected int size = SIZE_EDEFAULT;
	/**
	 * The cached value of the '{@link #getType() <em>Type</em>}' reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getType()
	 * @generated
	 * @ordered
	 */
	protected Type type;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected ListTypeImpl() {
		super();
	}

	@Override
	public Object accept(TypeInterpreter interpreter) {
		return interpreter.interpret(this);
	}

	@Override
	public void accept(TypeVisitor visitor) {
		visitor.visit(this);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public Type basicGetElementType() {
		// TODO: implement this method to return the 'Element Type' reference
		// -> do not perform proxy resolution
		// Ensure that you remove @generated or mark it @generated NOT
		throw new UnsupportedOperationException();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public Type basicGetType() {
		return type;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case TypePackage.LIST_TYPE__ELEMENT_TYPE:
			if (resolve)
				return getElementType();
			return basicGetElementType();
		case TypePackage.LIST_TYPE__SIZE:
			return getSize();
		case TypePackage.LIST_TYPE__SIZE_ITERATOR:
			return getSizeIterator();
		case TypePackage.LIST_TYPE__TYPE:
			if (resolve)
				return getType();
			return basicGetType();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
		case TypePackage.LIST_TYPE__ELEMENT_TYPE:
			return basicGetElementType() != null;
		case TypePackage.LIST_TYPE__SIZE:
			return size != SIZE_EDEFAULT;
		case TypePackage.LIST_TYPE__SIZE_ITERATOR:
			return !getSizeIterator().isEmpty();
		case TypePackage.LIST_TYPE__TYPE:
			return type != null;
		}
		return super.eIsSet(featureID);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof ListType) {
			ListType list = (ListType) obj;
			return size == list.getSize() && type.equals(list.getType());
		} else {
			return false;
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
		case TypePackage.LIST_TYPE__SIZE:
			setSize((Integer) newValue);
			return;
		case TypePackage.LIST_TYPE__TYPE:
			setType((Type) newValue);
			return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return TypePackage.Literals.LIST_TYPE;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
		case TypePackage.LIST_TYPE__SIZE:
			setSize(SIZE_EDEFAULT);
			return;
		case TypePackage.LIST_TYPE__TYPE:
			setType((Type) null);
			return;
		}
		super.eUnset(featureID);
	}

	@Override
	public EList<Integer> getDimensions() {
		EList<Integer> dimensions = super.getDimensions();
		dimensions.add(size);
		dimensions.addAll(getType().getDimensions());
		return dimensions;
	}

	/**
	 * Returns the type of the elements of this list
	 * 
	 * @return the number of elements of this list
	 */
	public Type getElementType() {
		if (type.isList()) {
			return ((ListType) type).getElementType();
		}
		return type;
	}

	/**
	 * Returns the number of elements of this list type.
	 * 
	 * @return the number of elements of this list type
	 * @generated
	 */
	public int getSize() {
		return size;
	}

	/**
	 * Returns a list of indexes that can be used inside a template.
	 * 
	 * @return a list of indexes corresponding to the list size
	 */
	public EList<Integer> getSizeIterator() {
		EList<Integer> list = new EDataTypeEList<Integer>(Integer.class, this,
				TypePackage.LIST_TYPE__SIZE_ITERATOR);

		for (int i = 0; i < size; i++) {
			list.add(i);
		}

		return list;
	}

	/**
	 * Returns the type of the list
	 * 
	 * @return the type of the list
	 * @generated
	 */
	public Type getType() {
		if (type != null && type.eIsProxy()) {
			InternalEObject oldType = (InternalEObject) type;
			type = (Type) eResolveProxy(oldType);
			if (type != oldType) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE,
							TypePackage.LIST_TYPE__TYPE, oldType, type));
			}
		}
		return type;
	}

	@Override
	public boolean isList() {
		return true;
	}

	/**
	 * Sets the number of elements of this list type.
	 * 
	 * @param size
	 *            the number of elements of this list type
	 * @generated
	 */
	public void setSize(int newSize) {
		int oldSize = size;
		size = newSize;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					TypePackage.LIST_TYPE__SIZE, oldSize, size));
	}

	/**
	 * Sets the type of this list.
	 * 
	 * @param type
	 *            element type
	 * @generated
	 */
	public void setType(Type newType) {
		Type oldType = type;
		type = newType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					TypePackage.LIST_TYPE__TYPE, oldType, type));
	}

	@Override
	public String toString() {
		return super.toString();
	}

}
