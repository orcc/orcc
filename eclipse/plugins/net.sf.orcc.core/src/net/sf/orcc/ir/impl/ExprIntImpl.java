/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package net.sf.orcc.ir.impl;

import java.math.BigInteger;

import net.sf.orcc.ir.ExprBool;
import net.sf.orcc.ir.ExprInt;
import net.sf.orcc.ir.IrFactory;
import net.sf.orcc.ir.IrPackage;
import net.sf.orcc.ir.Type;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc --> An implementation of the model object '
 * <em><b>Expr Int</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link net.sf.orcc.ir.impl.ExprIntImpl#getValue <em>Value</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ExprIntImpl extends ExpressionImpl implements ExprInt {
	/**
	 * The default value of the '{@link #getValue() <em>Value</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getValue()
	 * @generated
	 * @ordered
	 */
	protected static final BigInteger VALUE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getValue() <em>Value</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getValue()
	 * @generated
	 * @ordered
	 */
	protected BigInteger value = VALUE_EDEFAULT;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	protected ExprIntImpl() {
		super();
	}

	public ExprIntImpl(BigInteger value) {
		super();
		setValue(value);
	}

	@Override
	public ExprInt add(ExprInt expr) {
		return new ExprIntImpl(getValue().add(expr.getValue()));
	}

	@Override
	public ExprInt and(ExprInt expr) {
		return new ExprIntImpl(getValue().and(expr.getValue()));
	}

	@Override
	public ExprInt divide(ExprInt expr) {
		return new ExprIntImpl(getValue().divide(expr.getValue()));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case IrPackage.EXPR_INT__VALUE:
				return getValue();
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
			case IrPackage.EXPR_INT__VALUE:
				return VALUE_EDEFAULT == null ? value != null : !VALUE_EDEFAULT.equals(value);
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case IrPackage.EXPR_INT__VALUE:
				setValue((BigInteger)newValue);
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
		return IrPackage.Literals.EXPR_INT;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case IrPackage.EXPR_INT__VALUE:
				setValue(VALUE_EDEFAULT);
				return;
		}
		super.eUnset(featureID);
	}

	@Override
	public ExprBool ge(ExprInt expr) {
		return new ExprBoolImpl(value.compareTo(expr.getValue()) >= 0);
	}

	@Override
	public int getIntValue() {
		return value.intValue();
	}

	@Override
	public long getLongValue() {
		return value.longValue();
	}

	@Override
	public Type getType() {
		int size = value.bitLength() + 1;
		return IrFactory.eINSTANCE.createTypeInt(size);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public BigInteger getValue() {
		return value;
	}

	@Override
	public ExprBool gt(ExprInt expr) {
		return new ExprBoolImpl(value.compareTo(expr.getValue()) > 0);
	}

	@Override
	public boolean isIntExpr() {
		return true;
	}

	@Override
	public boolean isLong() {
		return getIntValue() != getLongValue();
	}

	@Override
	public ExprBool le(ExprInt expr) {
		return new ExprBoolImpl(value.compareTo(expr.getValue()) <= 0);
	}

	@Override
	public ExprBool lt(ExprInt expr) {
		return new ExprBoolImpl(value.compareTo(expr.getValue()) < 0);
	}

	@Override
	public ExprInt mod(ExprInt expr) {
		return new ExprIntImpl(value.mod(expr.getValue()));
	}

	@Override
	public ExprInt multiply(ExprInt expr) {
		return new ExprIntImpl(value.multiply(expr.getValue()));
	}

	@Override
	public ExprInt negate() {
		return new ExprIntImpl(getValue().negate());
	}

	@Override
	public ExprInt not() {
		return new ExprIntImpl(getValue().not());
	}

	@Override
	public ExprInt or(ExprInt expr) {
		return new ExprIntImpl(getValue().or(expr.getValue()));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public void setValue(BigInteger newValue) {
		BigInteger oldValue = value;
		value = newValue;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, IrPackage.EXPR_INT__VALUE, oldValue, value));
	}

	@Override
	public ExprInt shiftLeft(ExprInt expr) {
		return new ExprIntImpl(getValue().shiftLeft(expr.getValue().intValue()));
	}

	@Override
	public ExprInt shiftRight(ExprInt expr) {
		return new ExprIntImpl(getValue()
				.shiftRight(expr.getValue().intValue()));
	}

	@Override
	public ExprInt subtract(ExprInt expr) {
		return new ExprIntImpl(getValue().subtract(expr.getValue()));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (value: ");
		result.append(value);
		result.append(')');
		return result.toString();
	}

	public ExprInt xor(ExprInt expr) {
		return new ExprIntImpl(getValue().xor(expr.getValue()));
	}

} // ExprIntImpl
