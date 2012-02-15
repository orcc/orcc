/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package net.sf.orcc.ir.impl;

import java.util.Collection;

import net.sf.orcc.ir.ArgByRef;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.IrPackage;
import net.sf.orcc.ir.Use;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Arg By Ref</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link net.sf.orcc.ir.impl.ArgByRefImpl#getIndexes <em>Indexes</em>}</li>
 *   <li>{@link net.sf.orcc.ir.impl.ArgByRefImpl#getUse <em>Use</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ArgByRefImpl extends ArgImpl implements ArgByRef {
	/**
	 * The cached value of the '{@link #getIndexes() <em>Indexes</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getIndexes()
	 * @generated
	 * @ordered
	 */
	protected EList<Expression> indexes;

	/**
	 * The cached value of the '{@link #getUse() <em>Use</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getUse()
	 * @generated
	 * @ordered
	 */
	protected Use use;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ArgByRefImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetUse(Use newUse, NotificationChain msgs) {
		Use oldUse = use;
		use = newUse;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this,
					Notification.SET, IrPackage.ARG_BY_REF__USE, oldUse, newUse);
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
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case IrPackage.ARG_BY_REF__INDEXES:
			return getIndexes();
		case IrPackage.ARG_BY_REF__USE:
			return getUse();
		}
		return super.eGet(featureID, resolve, coreType);
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
		case IrPackage.ARG_BY_REF__INDEXES:
			return ((InternalEList<?>) getIndexes())
					.basicRemove(otherEnd, msgs);
		case IrPackage.ARG_BY_REF__USE:
			return basicSetUse(null, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
		case IrPackage.ARG_BY_REF__INDEXES:
			return indexes != null && !indexes.isEmpty();
		case IrPackage.ARG_BY_REF__USE:
			return use != null;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
		case IrPackage.ARG_BY_REF__INDEXES:
			getIndexes().clear();
			getIndexes().addAll((Collection<? extends Expression>) newValue);
			return;
		case IrPackage.ARG_BY_REF__USE:
			setUse((Use) newValue);
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
	protected EClass eStaticClass() {
		return IrPackage.Literals.ARG_BY_REF;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
		case IrPackage.ARG_BY_REF__INDEXES:
			getIndexes().clear();
			return;
		case IrPackage.ARG_BY_REF__USE:
			setUse((Use) null);
			return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Expression> getIndexes() {
		if (indexes == null) {
			indexes = new EObjectContainmentEList<Expression>(Expression.class,
					this, IrPackage.ARG_BY_REF__INDEXES);
		}
		return indexes;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Use getUse() {
		return use;
	}

	@Override
	public boolean isByRef() {
		return true;
	}

	@Override
	public boolean isByVal() {
		return false;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setUse(Use newUse) {
		if (newUse != use) {
			NotificationChain msgs = null;
			if (use != null)
				msgs = ((InternalEObject) use).eInverseRemove(this,
						EOPPOSITE_FEATURE_BASE - IrPackage.ARG_BY_REF__USE,
						null, msgs);
			if (newUse != null)
				msgs = ((InternalEObject) newUse).eInverseAdd(this,
						EOPPOSITE_FEATURE_BASE - IrPackage.ARG_BY_REF__USE,
						null, msgs);
			msgs = basicSetUse(newUse, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					IrPackage.ARG_BY_REF__USE, newUse, newUse));
	}

} //ArgByRefImpl
