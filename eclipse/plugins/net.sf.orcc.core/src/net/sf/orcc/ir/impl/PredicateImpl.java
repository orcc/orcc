/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package net.sf.orcc.ir.impl;

import java.util.Collection;
import java.util.List;

import net.sf.orcc.ir.ExprUnary;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.IrPackage;
import net.sf.orcc.ir.OpUnary;
import net.sf.orcc.ir.Predicate;
import net.sf.orcc.ir.util.ExpressionPrinter;

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc --> An implementation of the model object '
 * <em><b>Predicate</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link net.sf.orcc.ir.impl.PredicateImpl#getExpressions <em>Expressions</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class PredicateImpl extends EObjectImpl implements Predicate {
	/**
	 * The cached value of the '{@link #getExpressions() <em>Expressions</em>}' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getExpressions()
	 * @generated
	 * @ordered
	 */
	protected EList<Expression> expressions;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	protected PredicateImpl() {
		super();
	}

	@Override
	public void add(Expression expression) {
		getExpressions().add(expression);
	}

	@Override
	public void clear() {
		getExpressions().clear();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case IrPackage.PREDICATE__EXPRESSIONS:
				return getExpressions();
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
			case IrPackage.PREDICATE__EXPRESSIONS:
				return expressions != null && !expressions.isEmpty();
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
			case IrPackage.PREDICATE__EXPRESSIONS:
				getExpressions().clear();
				getExpressions().addAll((Collection<? extends Expression>)newValue);
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
		return IrPackage.Literals.PREDICATE;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case IrPackage.PREDICATE__EXPRESSIONS:
				getExpressions().clear();
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Expression> getExpressions() {
		if (expressions == null) {
			expressions = new EObjectContainmentEList<Expression>(Expression.class, this, IrPackage.PREDICATE__EXPRESSIONS);
		}
		return expressions;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd,
			int featureID, NotificationChain msgs) {
		switch (featureID) {
			case IrPackage.PREDICATE__EXPRESSIONS:
				return ((InternalEList<?>)getExpressions()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	@Override
	public boolean isEmpty() {
		return getExpressions().isEmpty();
	}

	@Override
	public boolean isMutuallyExclusive(Predicate predicate) {
		List<Expression> thisExprs = getExpressions();
		List<Expression> otherExprs = predicate.getExpressions();

		// limited to 1 expression per predicate
		if (thisExprs.size() != 1 || otherExprs.size() != 1) {
			return false;
		}

		// one is a "not" of the other
		Expression e1 = thisExprs.get(0);
		Expression e2 = otherExprs.get(0);
		if (e1.isUnaryExpr()) {
			ExprUnary unary = (ExprUnary) e1;
			if (unary.getOp() == OpUnary.LOGIC_NOT
					&& EcoreUtil.equals(unary.getExpr(), e2)) {
				return true;
			}
		} else if (e2.isUnaryExpr()) {
			ExprUnary unary = (ExprUnary) e2;
			if (unary.getOp() == OpUnary.LOGIC_NOT
					&& EcoreUtil.equals(unary.getExpr(), e1)) {
				return true;
			}
		}

		return false;
	}

	@Override
	public boolean isSameAs(Predicate predicate) {
		return EcoreUtil.equals(this, predicate);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		for (Expression condition : getExpressions()) {
			builder.append("<");
			String str = new ExpressionPrinter().doSwitch(condition);
			builder.append(str);
			builder.append(">");
		}
		return builder.toString();
	}

}
