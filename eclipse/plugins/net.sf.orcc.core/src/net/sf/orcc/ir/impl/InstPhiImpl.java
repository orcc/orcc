/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package net.sf.orcc.ir.impl;

import static net.sf.orcc.ir.util.IrUtil.getNameSSA;

import java.util.Collection;

import net.sf.orcc.ir.Def;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.InstPhi;
import net.sf.orcc.ir.IrPackage;
import net.sf.orcc.ir.Var;
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
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Inst Phi</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link net.sf.orcc.ir.impl.InstPhiImpl#getOldVariable <em>Old Variable</em>}</li>
 *   <li>{@link net.sf.orcc.ir.impl.InstPhiImpl#getTarget <em>Target</em>}</li>
 *   <li>{@link net.sf.orcc.ir.impl.InstPhiImpl#getValues <em>Values</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class InstPhiImpl extends InstructionImpl implements InstPhi {
	/**
	 * The cached value of the '{@link #getOldVariable() <em>Old Variable</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOldVariable()
	 * @generated
	 * @ordered
	 */
	protected Var oldVariable;
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
	 * The cached value of the '{@link #getValues() <em>Values</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getValues()
	 * @generated
	 * @ordered
	 */
	protected EList<Expression> values;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected InstPhiImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Var basicGetOldVariable() {
		return oldVariable;
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
					Notification.SET, IrPackage.INST_PHI__TARGET, oldTarget,
					newTarget);
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
		case IrPackage.INST_PHI__OLD_VARIABLE:
			if (resolve)
				return getOldVariable();
			return basicGetOldVariable();
		case IrPackage.INST_PHI__TARGET:
			return getTarget();
		case IrPackage.INST_PHI__VALUES:
			return getValues();
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
		case IrPackage.INST_PHI__TARGET:
			return basicSetTarget(null, msgs);
		case IrPackage.INST_PHI__VALUES:
			return ((InternalEList<?>) getValues()).basicRemove(otherEnd, msgs);
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
		case IrPackage.INST_PHI__OLD_VARIABLE:
			return oldVariable != null;
		case IrPackage.INST_PHI__TARGET:
			return target != null;
		case IrPackage.INST_PHI__VALUES:
			return values != null && !values.isEmpty();
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
		case IrPackage.INST_PHI__OLD_VARIABLE:
			setOldVariable((Var) newValue);
			return;
		case IrPackage.INST_PHI__TARGET:
			setTarget((Def) newValue);
			return;
		case IrPackage.INST_PHI__VALUES:
			getValues().clear();
			getValues().addAll((Collection<? extends Expression>) newValue);
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
		return IrPackage.Literals.INST_PHI;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
		case IrPackage.INST_PHI__OLD_VARIABLE:
			setOldVariable((Var) null);
			return;
		case IrPackage.INST_PHI__TARGET:
			setTarget((Def) null);
			return;
		case IrPackage.INST_PHI__VALUES:
			getValues().clear();
			return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Var getOldVariable() {
		if (oldVariable != null && oldVariable.eIsProxy()) {
			InternalEObject oldOldVariable = (InternalEObject) oldVariable;
			oldVariable = (Var) eResolveProxy(oldOldVariable);
			if (oldVariable != oldOldVariable) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE,
							IrPackage.INST_PHI__OLD_VARIABLE, oldOldVariable,
							oldVariable));
			}
		}
		return oldVariable;
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
	public EList<Expression> getValues() {
		if (values == null) {
			values = new EObjectContainmentEList<Expression>(Expression.class,
					this, IrPackage.INST_PHI__VALUES);
		}
		return values;
	}

	@Override
	public boolean isInstPhi() {
		return true;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setOldVariable(Var newOldVariable) {
		Var oldOldVariable = oldVariable;
		oldVariable = newOldVariable;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					IrPackage.INST_PHI__OLD_VARIABLE, oldOldVariable,
					oldVariable));
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
						EOPPOSITE_FEATURE_BASE - IrPackage.INST_PHI__TARGET,
						null, msgs);
			if (newTarget != null)
				msgs = ((InternalEObject) newTarget).eInverseAdd(this,
						EOPPOSITE_FEATURE_BASE - IrPackage.INST_PHI__TARGET,
						null, msgs);
			msgs = basicSetTarget(newTarget, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					IrPackage.INST_PHI__TARGET, newTarget, newTarget));
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(super.toString());
		builder.append("Phi(").append(getNameSSA(getTarget().getVariable()));
		for (Expression value : getValues()) {
			builder.append(", ");
			builder.append(new ExpressionPrinter().doSwitch(value));
		}
		return builder.append(")").toString();
	}

} //InstPhiImpl
