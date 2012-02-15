/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package net.sf.orcc.backends.ir.impl;

import net.sf.orcc.backends.ir.InstTernary;
import net.sf.orcc.backends.ir.IrSpecificPackage;

import net.sf.orcc.ir.Def;
import net.sf.orcc.ir.Expression;

import net.sf.orcc.ir.impl.InstSpecificImpl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Inst Ternary</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link net.sf.orcc.backends.ir.impl.InstTernaryImpl#getConditionValue <em>Condition Value</em>}</li>
 *   <li>{@link net.sf.orcc.backends.ir.impl.InstTernaryImpl#getTrueValue <em>True Value</em>}</li>
 *   <li>{@link net.sf.orcc.backends.ir.impl.InstTernaryImpl#getFalseValue <em>False Value</em>}</li>
 *   <li>{@link net.sf.orcc.backends.ir.impl.InstTernaryImpl#getTarget <em>Target</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class InstTernaryImpl extends InstSpecificImpl implements InstTernary {
	/**
	 * The cached value of the '{@link #getConditionValue() <em>Condition Value</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getConditionValue()
	 * @generated
	 * @ordered
	 */
	protected Expression conditionValue;

	/**
	 * The cached value of the '{@link #getTrueValue() <em>True Value</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTrueValue()
	 * @generated
	 * @ordered
	 */
	protected Expression trueValue;

	/**
	 * The cached value of the '{@link #getFalseValue() <em>False Value</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFalseValue()
	 * @generated
	 * @ordered
	 */
	protected Expression falseValue;

	/**
	 * The cached value of the '{@link #getTarget() <em>Target</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTarget()
	 * @generated
	 * @ordered
	 */
	protected Def target;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected InstTernaryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return IrSpecificPackage.Literals.INST_TERNARY;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Expression getConditionValue() {
		return conditionValue;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetConditionValue(
			Expression newConditionValue, NotificationChain msgs) {
		Expression oldConditionValue = conditionValue;
		conditionValue = newConditionValue;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this,
					Notification.SET,
					IrSpecificPackage.INST_TERNARY__CONDITION_VALUE,
					oldConditionValue, newConditionValue);
			if (msgs == null)
				msgs = notification;
			else
				msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setConditionValue(Expression newConditionValue) {
		if (newConditionValue != conditionValue) {
			NotificationChain msgs = null;
			if (conditionValue != null)
				msgs = ((InternalEObject) conditionValue)
						.eInverseRemove(
								this,
								EOPPOSITE_FEATURE_BASE
										- IrSpecificPackage.INST_TERNARY__CONDITION_VALUE,
								null, msgs);
			if (newConditionValue != null)
				msgs = ((InternalEObject) newConditionValue)
						.eInverseAdd(
								this,
								EOPPOSITE_FEATURE_BASE
										- IrSpecificPackage.INST_TERNARY__CONDITION_VALUE,
								null, msgs);
			msgs = basicSetConditionValue(newConditionValue, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					IrSpecificPackage.INST_TERNARY__CONDITION_VALUE,
					newConditionValue, newConditionValue));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Expression getTrueValue() {
		return trueValue;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetTrueValue(Expression newTrueValue,
			NotificationChain msgs) {
		Expression oldTrueValue = trueValue;
		trueValue = newTrueValue;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this,
					Notification.SET,
					IrSpecificPackage.INST_TERNARY__TRUE_VALUE, oldTrueValue,
					newTrueValue);
			if (msgs == null)
				msgs = notification;
			else
				msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTrueValue(Expression newTrueValue) {
		if (newTrueValue != trueValue) {
			NotificationChain msgs = null;
			if (trueValue != null)
				msgs = ((InternalEObject) trueValue).eInverseRemove(this,
						EOPPOSITE_FEATURE_BASE
								- IrSpecificPackage.INST_TERNARY__TRUE_VALUE,
						null, msgs);
			if (newTrueValue != null)
				msgs = ((InternalEObject) newTrueValue).eInverseAdd(this,
						EOPPOSITE_FEATURE_BASE
								- IrSpecificPackage.INST_TERNARY__TRUE_VALUE,
						null, msgs);
			msgs = basicSetTrueValue(newTrueValue, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					IrSpecificPackage.INST_TERNARY__TRUE_VALUE, newTrueValue,
					newTrueValue));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Expression getFalseValue() {
		return falseValue;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetFalseValue(Expression newFalseValue,
			NotificationChain msgs) {
		Expression oldFalseValue = falseValue;
		falseValue = newFalseValue;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this,
					Notification.SET,
					IrSpecificPackage.INST_TERNARY__FALSE_VALUE, oldFalseValue,
					newFalseValue);
			if (msgs == null)
				msgs = notification;
			else
				msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setFalseValue(Expression newFalseValue) {
		if (newFalseValue != falseValue) {
			NotificationChain msgs = null;
			if (falseValue != null)
				msgs = ((InternalEObject) falseValue).eInverseRemove(this,
						EOPPOSITE_FEATURE_BASE
								- IrSpecificPackage.INST_TERNARY__FALSE_VALUE,
						null, msgs);
			if (newFalseValue != null)
				msgs = ((InternalEObject) newFalseValue).eInverseAdd(this,
						EOPPOSITE_FEATURE_BASE
								- IrSpecificPackage.INST_TERNARY__FALSE_VALUE,
						null, msgs);
			msgs = basicSetFalseValue(newFalseValue, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					IrSpecificPackage.INST_TERNARY__FALSE_VALUE, newFalseValue,
					newFalseValue));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Def getTarget() {
		return target;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetTarget(Def newTarget,
			NotificationChain msgs) {
		Def oldTarget = target;
		target = newTarget;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this,
					Notification.SET, IrSpecificPackage.INST_TERNARY__TARGET,
					oldTarget, newTarget);
			if (msgs == null)
				msgs = notification;
			else
				msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTarget(Def newTarget) {
		if (newTarget != target) {
			NotificationChain msgs = null;
			if (target != null)
				msgs = ((InternalEObject) target).eInverseRemove(this,
						EOPPOSITE_FEATURE_BASE
								- IrSpecificPackage.INST_TERNARY__TARGET, null,
						msgs);
			if (newTarget != null)
				msgs = ((InternalEObject) newTarget).eInverseAdd(this,
						EOPPOSITE_FEATURE_BASE
								- IrSpecificPackage.INST_TERNARY__TARGET, null,
						msgs);
			msgs = basicSetTarget(newTarget, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					IrSpecificPackage.INST_TERNARY__TARGET, newTarget,
					newTarget));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd,
			int featureID, NotificationChain msgs) {
		switch (featureID) {
		case IrSpecificPackage.INST_TERNARY__CONDITION_VALUE:
			return basicSetConditionValue(null, msgs);
		case IrSpecificPackage.INST_TERNARY__TRUE_VALUE:
			return basicSetTrueValue(null, msgs);
		case IrSpecificPackage.INST_TERNARY__FALSE_VALUE:
			return basicSetFalseValue(null, msgs);
		case IrSpecificPackage.INST_TERNARY__TARGET:
			return basicSetTarget(null, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case IrSpecificPackage.INST_TERNARY__CONDITION_VALUE:
			return getConditionValue();
		case IrSpecificPackage.INST_TERNARY__TRUE_VALUE:
			return getTrueValue();
		case IrSpecificPackage.INST_TERNARY__FALSE_VALUE:
			return getFalseValue();
		case IrSpecificPackage.INST_TERNARY__TARGET:
			return getTarget();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
		case IrSpecificPackage.INST_TERNARY__CONDITION_VALUE:
			setConditionValue((Expression) newValue);
			return;
		case IrSpecificPackage.INST_TERNARY__TRUE_VALUE:
			setTrueValue((Expression) newValue);
			return;
		case IrSpecificPackage.INST_TERNARY__FALSE_VALUE:
			setFalseValue((Expression) newValue);
			return;
		case IrSpecificPackage.INST_TERNARY__TARGET:
			setTarget((Def) newValue);
			return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
		case IrSpecificPackage.INST_TERNARY__CONDITION_VALUE:
			setConditionValue((Expression) null);
			return;
		case IrSpecificPackage.INST_TERNARY__TRUE_VALUE:
			setTrueValue((Expression) null);
			return;
		case IrSpecificPackage.INST_TERNARY__FALSE_VALUE:
			setFalseValue((Expression) null);
			return;
		case IrSpecificPackage.INST_TERNARY__TARGET:
			setTarget((Def) null);
			return;
		}
		super.eUnset(featureID);
	}

	@Override
	public boolean isInstTernary() {
		return true;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
		case IrSpecificPackage.INST_TERNARY__CONDITION_VALUE:
			return conditionValue != null;
		case IrSpecificPackage.INST_TERNARY__TRUE_VALUE:
			return trueValue != null;
		case IrSpecificPackage.INST_TERNARY__FALSE_VALUE:
			return falseValue != null;
		case IrSpecificPackage.INST_TERNARY__TARGET:
			return target != null;
		}
		return super.eIsSet(featureID);
	}

} //InstTernaryImpl
