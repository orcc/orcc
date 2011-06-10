/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package net.sf.orcc.backends.tta.architecture.impl;

import net.sf.orcc.backends.tta.architecture.ArchitecturePackage;
import net.sf.orcc.backends.tta.architecture.ExprBinary;
import net.sf.orcc.backends.tta.architecture.ExprUnary;
import net.sf.orcc.backends.tta.architecture.OpBinary;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Expr Binary</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link net.sf.orcc.backends.tta.architecture.impl.ExprBinaryImpl#getOperator <em>Operator</em>}</li>
 *   <li>{@link net.sf.orcc.backends.tta.architecture.impl.ExprBinaryImpl#getE1 <em>E1</em>}</li>
 *   <li>{@link net.sf.orcc.backends.tta.architecture.impl.ExprBinaryImpl#getE2 <em>E2</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ExprBinaryImpl extends EObjectImpl implements ExprBinary {
	/**
	 * The default value of the '{@link #getOperator() <em>Operator</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOperator()
	 * @generated
	 * @ordered
	 */
	protected static final OpBinary OPERATOR_EDEFAULT = OpBinary.AND;

	/**
	 * The cached value of the '{@link #getOperator() <em>Operator</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOperator()
	 * @generated
	 * @ordered
	 */
	protected OpBinary operator = OPERATOR_EDEFAULT;

	/**
	 * The cached value of the '{@link #getE1() <em>E1</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getE1()
	 * @generated
	 * @ordered
	 */
	protected ExprUnary e1;

	/**
	 * The cached value of the '{@link #getE2() <em>E2</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getE2()
	 * @generated
	 * @ordered
	 */
	protected ExprUnary e2;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ExprBinaryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ArchitecturePackage.Literals.EXPR_BINARY;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OpBinary getOperator() {
		return operator;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setOperator(OpBinary newOperator) {
		OpBinary oldOperator = operator;
		operator = newOperator == null ? OPERATOR_EDEFAULT : newOperator;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ArchitecturePackage.EXPR_BINARY__OPERATOR, oldOperator, operator));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ExprUnary getE1() {
		if (e1 != null && e1.eIsProxy()) {
			InternalEObject oldE1 = (InternalEObject)e1;
			e1 = (ExprUnary)eResolveProxy(oldE1);
			if (e1 != oldE1) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, ArchitecturePackage.EXPR_BINARY__E1, oldE1, e1));
			}
		}
		return e1;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ExprUnary basicGetE1() {
		return e1;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setE1(ExprUnary newE1) {
		ExprUnary oldE1 = e1;
		e1 = newE1;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ArchitecturePackage.EXPR_BINARY__E1, oldE1, e1));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ExprUnary getE2() {
		if (e2 != null && e2.eIsProxy()) {
			InternalEObject oldE2 = (InternalEObject)e2;
			e2 = (ExprUnary)eResolveProxy(oldE2);
			if (e2 != oldE2) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, ArchitecturePackage.EXPR_BINARY__E2, oldE2, e2));
			}
		}
		return e2;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ExprUnary basicGetE2() {
		return e2;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setE2(ExprUnary newE2) {
		ExprUnary oldE2 = e2;
		e2 = newE2;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ArchitecturePackage.EXPR_BINARY__E2, oldE2, e2));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case ArchitecturePackage.EXPR_BINARY__OPERATOR:
				return getOperator();
			case ArchitecturePackage.EXPR_BINARY__E1:
				if (resolve) return getE1();
				return basicGetE1();
			case ArchitecturePackage.EXPR_BINARY__E2:
				if (resolve) return getE2();
				return basicGetE2();
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
			case ArchitecturePackage.EXPR_BINARY__OPERATOR:
				setOperator((OpBinary)newValue);
				return;
			case ArchitecturePackage.EXPR_BINARY__E1:
				setE1((ExprUnary)newValue);
				return;
			case ArchitecturePackage.EXPR_BINARY__E2:
				setE2((ExprUnary)newValue);
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
			case ArchitecturePackage.EXPR_BINARY__OPERATOR:
				setOperator(OPERATOR_EDEFAULT);
				return;
			case ArchitecturePackage.EXPR_BINARY__E1:
				setE1((ExprUnary)null);
				return;
			case ArchitecturePackage.EXPR_BINARY__E2:
				setE2((ExprUnary)null);
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case ArchitecturePackage.EXPR_BINARY__OPERATOR:
				return operator != OPERATOR_EDEFAULT;
			case ArchitecturePackage.EXPR_BINARY__E1:
				return e1 != null;
			case ArchitecturePackage.EXPR_BINARY__E2:
				return e2 != null;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (operator: ");
		result.append(operator);
		result.append(')');
		return result.toString();
	}

} //ExprBinaryImpl
