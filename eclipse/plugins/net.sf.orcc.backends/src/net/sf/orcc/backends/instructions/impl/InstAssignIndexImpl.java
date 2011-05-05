/*
 * Copyright (c) 2010-2011, IETR/INSA of Rennes
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
package net.sf.orcc.backends.instructions.impl;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import net.sf.orcc.backends.instructions.InstAssignIndex;
import net.sf.orcc.backends.instructions.InstructionsPackage;
import net.sf.orcc.ir.Def;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.Type;
import net.sf.orcc.ir.impl.InstSpecificImpl;
import net.sf.orcc.ir.util.ExpressionPrinter;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc --> An implementation of the model object '
 * <em><b>Inst Assign Index</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link net.sf.orcc.backends.instructions.impl.InstAssignIndexImpl#getIndexes <em>Indexes</em>}</li>
 *   <li>{@link net.sf.orcc.backends.instructions.impl.InstAssignIndexImpl#getTarget <em>Target</em>}</li>
 *   <li>{@link net.sf.orcc.backends.instructions.impl.InstAssignIndexImpl#getListType <em>List Type</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class InstAssignIndexImpl extends InstSpecificImpl implements
		InstAssignIndex {
	/**
	 * The cached value of the '{@link #getIndexes() <em>Indexes</em>}' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getIndexes()
	 * @generated
	 * @ordered
	 */
	protected EList<Expression> indexes;

	/**
	 * The cached value of the '{@link #getTarget() <em>Target</em>}' containment reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getTarget()
	 * @generated
	 * @ordered
	 */
	protected Def target;

	/**
	 * The cached value of the '{@link #getListType() <em>List Type</em>}' containment reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getListType()
	 * @generated
	 * @ordered
	 */
	protected Type listType;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	protected InstAssignIndexImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetListType(Type newListType,
			NotificationChain msgs) {
		Type oldListType = listType;
		listType = newListType;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, InstructionsPackage.INST_ASSIGN_INDEX__LIST_TYPE, oldListType, newListType);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetTarget(Def newTarget,
			NotificationChain msgs) {
		Def oldTarget = target;
		target = newTarget;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, InstructionsPackage.INST_ASSIGN_INDEX__TARGET, oldTarget, newTarget);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case InstructionsPackage.INST_ASSIGN_INDEX__INDEXES:
				return getIndexes();
			case InstructionsPackage.INST_ASSIGN_INDEX__TARGET:
				return getTarget();
			case InstructionsPackage.INST_ASSIGN_INDEX__LIST_TYPE:
				return getListType();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd,
			int featureID, NotificationChain msgs) {
		switch (featureID) {
			case InstructionsPackage.INST_ASSIGN_INDEX__INDEXES:
				return ((InternalEList<?>)getIndexes()).basicRemove(otherEnd, msgs);
			case InstructionsPackage.INST_ASSIGN_INDEX__TARGET:
				return basicSetTarget(null, msgs);
			case InstructionsPackage.INST_ASSIGN_INDEX__LIST_TYPE:
				return basicSetListType(null, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case InstructionsPackage.INST_ASSIGN_INDEX__INDEXES:
				return indexes != null && !indexes.isEmpty();
			case InstructionsPackage.INST_ASSIGN_INDEX__TARGET:
				return target != null;
			case InstructionsPackage.INST_ASSIGN_INDEX__LIST_TYPE:
				return listType != null;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case InstructionsPackage.INST_ASSIGN_INDEX__INDEXES:
				getIndexes().clear();
				getIndexes().addAll((Collection<? extends Expression>)newValue);
				return;
			case InstructionsPackage.INST_ASSIGN_INDEX__TARGET:
				setTarget((Def)newValue);
				return;
			case InstructionsPackage.INST_ASSIGN_INDEX__LIST_TYPE:
				setListType((Type)newValue);
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
		return InstructionsPackage.Literals.INST_ASSIGN_INDEX;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case InstructionsPackage.INST_ASSIGN_INDEX__INDEXES:
				getIndexes().clear();
				return;
			case InstructionsPackage.INST_ASSIGN_INDEX__TARGET:
				setTarget((Def)null);
				return;
			case InstructionsPackage.INST_ASSIGN_INDEX__LIST_TYPE:
				setListType((Type)null);
				return;
		}
		super.eUnset(featureID);
	}

	@Override
	public Map<Expression, Integer> getExpressionToIndexMap() {
		Map<Expression, Integer> expressionToIndexMap = new HashMap<Expression, Integer>();
		for (int i = 0; i < getIndexes().size(); i++) {
			expressionToIndexMap.put(getIndexes().get(i), i);
		}
		return expressionToIndexMap;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Expression> getIndexes() {
		if (indexes == null) {
			indexes = new EObjectContainmentEList<Expression>(Expression.class, this, InstructionsPackage.INST_ASSIGN_INDEX__INDEXES);
		}
		return indexes;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public Type getListType() {
		return listType;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public Def getTarget() {
		return target;
	}

	@Override
	public boolean isInstAssignIndex() {
		return true;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public void setListType(Type newListType) {
		if (newListType != listType) {
			NotificationChain msgs = null;
			if (listType != null)
				msgs = ((InternalEObject)listType).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - InstructionsPackage.INST_ASSIGN_INDEX__LIST_TYPE, null, msgs);
			if (newListType != null)
				msgs = ((InternalEObject)newListType).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - InstructionsPackage.INST_ASSIGN_INDEX__LIST_TYPE, null, msgs);
			msgs = basicSetListType(newListType, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, InstructionsPackage.INST_ASSIGN_INDEX__LIST_TYPE, newListType, newListType));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public void setTarget(Def newTarget) {
		if (newTarget != target) {
			NotificationChain msgs = null;
			if (target != null)
				msgs = ((InternalEObject)target).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - InstructionsPackage.INST_ASSIGN_INDEX__TARGET, null, msgs);
			if (newTarget != null)
				msgs = ((InternalEObject)newTarget).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - InstructionsPackage.INST_ASSIGN_INDEX__TARGET, null, msgs);
			msgs = basicSetTarget(newTarget, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, InstructionsPackage.INST_ASSIGN_INDEX__TARGET, newTarget, newTarget));
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(super.toString());
		builder.append("AssignIndex(").append(
				getTarget().getVariable().getIndexedName());
		for (Expression index : getIndexes()) {
			builder.append("[");
			builder.append(new ExpressionPrinter().doSwitch(index));
			builder.append("]");
		}
		return builder.append(")").toString();
	}

} // InstAssignIndexImpl
