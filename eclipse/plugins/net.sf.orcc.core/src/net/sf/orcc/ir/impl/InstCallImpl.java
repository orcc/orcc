/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package net.sf.orcc.ir.impl;

import java.util.Collection;

import net.sf.orcc.ir.Arg;
import net.sf.orcc.ir.ArgByVal;
import net.sf.orcc.ir.Def;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.InstCall;
import net.sf.orcc.ir.IrPackage;
import net.sf.orcc.ir.Procedure;
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
 * <em><b>Inst Call</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link net.sf.orcc.ir.impl.InstCallImpl#getParameters <em>Parameters</em>}</li>
 *   <li>{@link net.sf.orcc.ir.impl.InstCallImpl#getProcedure <em>Procedure</em>}</li>
 *   <li>{@link net.sf.orcc.ir.impl.InstCallImpl#getTarget <em>Target</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class InstCallImpl extends InstructionImpl implements InstCall {
	/**
	 * The cached value of the '{@link #getParameters() <em>Parameters</em>}' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getParameters()
	 * @generated
	 * @ordered
	 */
	protected EList<Arg> parameters;

	/**
	 * The cached value of the '{@link #getProcedure() <em>Procedure</em>}' reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getProcedure()
	 * @generated
	 * @ordered
	 */
	protected Procedure procedure;

	/**
	 * The cached value of the '{@link #getTarget() <em>Target</em>}' containment reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getTarget()
	 * @generated
	 * @ordered
	 */
	protected Def target;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	protected InstCallImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public Procedure basicGetProcedure() {
		return procedure;
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
			ENotificationImpl notification = new ENotificationImpl(this,
					Notification.SET, IrPackage.INST_CALL__TARGET, oldTarget,
					newTarget);
			if (msgs == null)
				msgs = notification;
			else
				msgs.add(notification);
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
		case IrPackage.INST_CALL__PARAMETERS:
			return getParameters();
		case IrPackage.INST_CALL__PROCEDURE:
			if (resolve)
				return getProcedure();
			return basicGetProcedure();
		case IrPackage.INST_CALL__TARGET:
			return getTarget();
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
		case IrPackage.INST_CALL__PARAMETERS:
			return ((InternalEList<?>) getParameters()).basicRemove(otherEnd,
					msgs);
		case IrPackage.INST_CALL__TARGET:
			return basicSetTarget(null, msgs);
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
		case IrPackage.INST_CALL__PARAMETERS:
			return parameters != null && !parameters.isEmpty();
		case IrPackage.INST_CALL__PROCEDURE:
			return procedure != null;
		case IrPackage.INST_CALL__TARGET:
			return target != null;
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
		case IrPackage.INST_CALL__PARAMETERS:
			getParameters().clear();
			getParameters().addAll((Collection<? extends Arg>) newValue);
			return;
		case IrPackage.INST_CALL__PROCEDURE:
			setProcedure((Procedure) newValue);
			return;
		case IrPackage.INST_CALL__TARGET:
			setTarget((Def) newValue);
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
		return IrPackage.Literals.INST_CALL;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
		case IrPackage.INST_CALL__PARAMETERS:
			getParameters().clear();
			return;
		case IrPackage.INST_CALL__PROCEDURE:
			setProcedure((Procedure) null);
			return;
		case IrPackage.INST_CALL__TARGET:
			setTarget((Def) null);
			return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Arg> getParameters() {
		if (parameters == null) {
			parameters = new EObjectContainmentEList<Arg>(Arg.class, this,
					IrPackage.INST_CALL__PARAMETERS);
		}
		return parameters;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public Procedure getProcedure() {
		if (procedure != null && procedure.eIsProxy()) {
			InternalEObject oldProcedure = (InternalEObject) procedure;
			procedure = (Procedure) eResolveProxy(oldProcedure);
			if (procedure != oldProcedure) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE,
							IrPackage.INST_CALL__PROCEDURE, oldProcedure,
							procedure));
			}
		}
		return procedure;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public Def getTarget() {
		return target;
	}

	@Override
	public boolean hasResult() {
		return (getTarget() != null);
	}

	@Override
	public boolean isCall() {
		return true;
	}

	@Override
	public boolean isInstCall() {
		return true;
	}

	@Override
	public boolean isPrint() {
		return "print".equals(procedure.getName());
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public void setProcedure(Procedure newProcedure) {
		Procedure oldProcedure = procedure;
		procedure = newProcedure;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					IrPackage.INST_CALL__PROCEDURE, oldProcedure, procedure));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public void setTarget(Def newTarget) {
		if (newTarget != target) {
			NotificationChain msgs = null;
			if (target != null)
				msgs = ((InternalEObject) target).eInverseRemove(this,
						EOPPOSITE_FEATURE_BASE - IrPackage.INST_CALL__TARGET,
						null, msgs);
			if (newTarget != null)
				msgs = ((InternalEObject) newTarget).eInverseAdd(this,
						EOPPOSITE_FEATURE_BASE - IrPackage.INST_CALL__TARGET,
						null, msgs);
			msgs = basicSetTarget(newTarget, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					IrPackage.INST_CALL__TARGET, newTarget, newTarget));
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(super.toString());
		builder.append("Call(");
		if (getTarget() != null) {
			builder.append(getTarget().getVariable().getIndexedName()).append(
					", ");
		}

		builder.append(getProcedure().getName());
		for (Arg arg : getParameters()) {
			if (arg.isByVal()) {
				Expression expr = ((ArgByVal) arg).getValue();
				builder.append(", ");
				builder.append(new ExpressionPrinter().doSwitch(expr));
			}
		}
		return builder.append(")").toString();
	}

} // InstCallImpl
