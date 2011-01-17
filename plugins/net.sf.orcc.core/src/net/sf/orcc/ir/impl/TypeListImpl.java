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
package net.sf.orcc.ir.impl;

import java.util.ArrayList;
import java.util.List;

import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.IrPackage;
import net.sf.orcc.ir.Type;
import net.sf.orcc.ir.TypeList;
import net.sf.orcc.ir.expr.ExpressionEvaluator;
import net.sf.orcc.ir.expr.IntExpr;
import net.sf.orcc.ir.type.TypeInterpreter;
import net.sf.orcc.ir.type.TypeVisitor;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * This class defines a List type.
 * 
 * @author Matthieu Wipliez
 * @author Jerome Gorin
 * 
 */
public class TypeListImpl extends TypeImpl implements TypeList {

	/**
	 * The cached value of the '{@link #getSizeExpr() <em>Size Expr</em>}' reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getSizeExpr()
	 * @generated
	 * @ordered
	 */
	protected Expression sizeExpr;

	/**
	 * The cached value of the '{@link #getType() <em>Type</em>}' reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getType()
	 * @generated
	 * @ordered
	 */
	protected Type type;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	protected TypeListImpl() {
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
	 * @generated
	 */
	public Type basicGetType() {
		return type;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case IrPackage.TYPE_LIST__SIZE_EXPR:
				if (resolve) return getSizeExpr();
				return basicGetSizeExpr();
			case IrPackage.TYPE_LIST__TYPE:
				if (resolve) return getType();
				return basicGetType();
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
			case IrPackage.TYPE_LIST__SIZE_EXPR:
				return sizeExpr != null;
			case IrPackage.TYPE_LIST__TYPE:
				return type != null;
		}
		return super.eIsSet(featureID);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof TypeList) {
			TypeList list = (TypeList) obj;
			return this.getSize() == list.getSize()
					&& type.equals(list.getType());
		} else {
			return false;
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case IrPackage.TYPE_LIST__SIZE_EXPR:
				setSizeExpr((Expression)newValue);
				return;
			case IrPackage.TYPE_LIST__TYPE:
				setType((Type)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return IrPackage.Literals.TYPE_LIST;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case IrPackage.TYPE_LIST__SIZE_EXPR:
				setSizeExpr((Expression)null);
				return;
			case IrPackage.TYPE_LIST__TYPE:
				setType((Type)null);
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * Return the dimensions of the TypeList as a List of Integer. An exception
	 * is raised if one dimension is not an integer constant.
	 */
	@Override
	public List<Integer> getDimensions() {
		List<Integer> dimensions = new ArrayList<Integer>();
		dimensions.add(this.getSize());
		dimensions.addAll(getType().getDimensions());
		return dimensions;
	}

	public List<Expression> getDimensionsExpr() {
		List<Expression> dimensions = new ArrayList<Expression>();
		dimensions.add(this.getSizeExpr());
		dimensions.addAll(getType().getDimensionsExpr());
		return dimensions;
	}

	@Override
	public Type getElementType() {
		if (type.isList()) {
			return ((TypeList) type).getElementType();
		}
		return type;
	}

	public int getSize() {
		if (sizeExpr == null) {
			// Size depends on a actor parameter.
			return -1;
		} else {
			// Evaluate the expression.
			return new ExpressionEvaluator().evaluateAsInteger(sizeExpr);
		}
	}

	/**
	 * Returns the number of elements of this list type as an expression.
	 * 
	 * @return the number of elements of this list type as an expression
	 */
	public Expression getSizeExpr() {
		return sizeExpr;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Expression basicGetSizeExpr() {
		return sizeExpr;
	}

	/**
	 * Returns a list of indexes that can be used inside a template.
	 * 
	 * @return a list of indexes corresponding to the list size
	 */
	public List<Integer> getSizeIterator() {
		int s = getSize();
		List<Integer> list = new ArrayList<Integer>(s);
		for (int i = 0; i < s; i++) {
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
			InternalEObject oldType = (InternalEObject)type;
			type = (Type)eResolveProxy(oldType);
			if (type != oldType) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, IrPackage.TYPE_LIST__TYPE, oldType, type));
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
	 */
	public void setSize(int newSize) {
		setSizeExpr(new IntExpr(newSize));
	}

	/**
	 * Sets the number of elements of this list type as an expression.
	 * 
	 * @param value
	 *            the number of elements of this list type as an expression
	 * @generated
	 */
	public void setSizeExpr(Expression newSizeExpr) {
		Expression oldSizeExpr = sizeExpr;
		sizeExpr = newSizeExpr;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, IrPackage.TYPE_LIST__SIZE_EXPR, oldSizeExpr, sizeExpr));
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
			eNotify(new ENotificationImpl(this, Notification.SET, IrPackage.TYPE_LIST__TYPE, oldType, type));
	}

	@Override
	public String toString() {
		return super.toString();
	}

}
