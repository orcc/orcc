/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package net.sf.orcc.ir.impl;

import java.util.Collection;

import net.sf.orcc.ir.ExprInt;
import net.sf.orcc.ir.ExprList;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.IrPackage;
import net.sf.orcc.ir.Type;
import net.sf.orcc.ir.util.TypeUtil;

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.AbstractEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc --> An implementation of the model object '
 * <em><b>Expr List</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link net.sf.orcc.ir.impl.ExprListImpl#getValue <em>Value</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ExprListImpl extends ExpressionImpl implements ExprList {

	/**
	 * The cached value of the '{@link #getValue() <em>Value</em>}' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getValue()
	 * @generated
	 * @ordered
	 */
	protected EList<Expression> value;
	
	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	protected ExprListImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case IrPackage.EXPR_LIST__VALUE:
				return getValue();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case IrPackage.EXPR_LIST__VALUE:
				return ((InternalEList<?>)getValue()).basicRemove(otherEnd, msgs);
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
			case IrPackage.EXPR_LIST__VALUE:
				return value != null && !value.isEmpty();
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
			case IrPackage.EXPR_LIST__VALUE:
				getValue().clear();
				getValue().addAll((Collection<? extends Expression>)newValue);
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
		return IrPackage.Literals.EXPR_LIST;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case IrPackage.EXPR_LIST__VALUE:
				getValue().clear();
				return;
		}
		super.eUnset(featureID);
	}

	@Override
	public Expression get(ExprInt index) {
		if (index == null || index.getValue() == null) {
			return null;
		}
		return getValue().get(index.getIntValue());
	}

	@Override
	public Expression get(int index) {
		return getValue().get(index);
	}

	@Override
	public int getSize() {
		return getValue().size();
	}

	@Override
	public Type getType() {
		return TypeUtil.getType(this);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Expression> getValue() {
		if (value == null) {
			value = new EObjectContainmentEList<Expression>(Expression.class, this, IrPackage.EXPR_LIST__VALUE);
		}
		return value;
	}

	@Override
	public boolean isExprList() {
		return true;
	}

	@Override
	public void set(ExprInt index, Expression value) {
		((AbstractEList<Expression>) getValue()).setUnique(index.getIntValue(), value);
	}

	@Override
	public void set(int index, Expression value) {
		((AbstractEList<Expression>) getValue()).set(index, value);
	}

} // ExprListImpl
