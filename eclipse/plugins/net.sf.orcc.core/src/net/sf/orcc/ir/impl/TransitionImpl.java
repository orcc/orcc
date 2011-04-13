/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package net.sf.orcc.ir.impl;

import java.util.Collection;

import net.sf.orcc.ir.Action;
import net.sf.orcc.ir.IrPackage;
import net.sf.orcc.ir.State;
import net.sf.orcc.ir.Transition;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.emf.ecore.util.EObjectResolvingEList;

/**
 * <!-- begin-user-doc --> An implementation of the model object '
 * <em><b>Transition</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link net.sf.orcc.ir.impl.TransitionImpl#getTargetActions <em>Target Actions</em>}</li>
 *   <li>{@link net.sf.orcc.ir.impl.TransitionImpl#getTargetStates <em>Target States</em>}</li>
 *   <li>{@link net.sf.orcc.ir.impl.TransitionImpl#getSourceState <em>Source State</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class TransitionImpl extends EObjectImpl implements Transition {

	/**
	 * The cached value of the '{@link #getTargetActions() <em>Target Actions</em>}' reference list.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @see #getTargetActions()
	 * @generated
	 * @ordered
	 */
	protected EList<Action> targetActions;

	/**
	 * The cached value of the '{@link #getTargetStates() <em>Target States</em>}' reference list.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @see #getTargetStates()
	 * @generated
	 * @ordered
	 */
	protected EList<State> targetStates;

	/**
	 * The cached value of the '{@link #getSourceState() <em>Source State</em>}' reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getSourceState()
	 * @generated
	 * @ordered
	 */
	protected State sourceState;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	protected TransitionImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public State basicGetSourceState() {
		return sourceState;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case IrPackage.TRANSITION__TARGET_ACTIONS:
				return getTargetActions();
			case IrPackage.TRANSITION__TARGET_STATES:
				return getTargetStates();
			case IrPackage.TRANSITION__SOURCE_STATE:
				if (resolve) return getSourceState();
				return basicGetSourceState();
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
			case IrPackage.TRANSITION__TARGET_ACTIONS:
				return targetActions != null && !targetActions.isEmpty();
			case IrPackage.TRANSITION__TARGET_STATES:
				return targetStates != null && !targetStates.isEmpty();
			case IrPackage.TRANSITION__SOURCE_STATE:
				return sourceState != null;
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
			case IrPackage.TRANSITION__TARGET_ACTIONS:
				getTargetActions().clear();
				getTargetActions().addAll((Collection<? extends Action>)newValue);
				return;
			case IrPackage.TRANSITION__TARGET_STATES:
				getTargetStates().clear();
				getTargetStates().addAll((Collection<? extends State>)newValue);
				return;
			case IrPackage.TRANSITION__SOURCE_STATE:
				setSourceState((State)newValue);
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
		return IrPackage.Literals.TRANSITION;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case IrPackage.TRANSITION__TARGET_ACTIONS:
				getTargetActions().clear();
				return;
			case IrPackage.TRANSITION__TARGET_STATES:
				getTargetStates().clear();
				return;
			case IrPackage.TRANSITION__SOURCE_STATE:
				setSourceState((State)null);
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public State getSourceState() {
		if (sourceState != null && sourceState.eIsProxy()) {
			InternalEObject oldSourceState = (InternalEObject)sourceState;
			sourceState = (State)eResolveProxy(oldSourceState);
			if (sourceState != oldSourceState) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, IrPackage.TRANSITION__SOURCE_STATE, oldSourceState, sourceState));
			}
		}
		return sourceState;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Action> getTargetActions() {
		if (targetActions == null) {
			targetActions = new EObjectResolvingEList<Action>(Action.class, this, IrPackage.TRANSITION__TARGET_ACTIONS);
		}
		return targetActions;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EList<State> getTargetStates() {
		if (targetStates == null) {
			targetStates = new EObjectResolvingEList<State>(State.class, this, IrPackage.TRANSITION__TARGET_STATES);
		}
		return targetStates;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public void setSourceState(State newSourceState) {
		State oldSourceState = sourceState;
		sourceState = newSourceState;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, IrPackage.TRANSITION__SOURCE_STATE, oldSourceState, sourceState));
	}

} // TransitionImpl
