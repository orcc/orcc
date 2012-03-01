/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package net.sf.orcc.backends.ir.impl;

import net.sf.orcc.backends.ir.InstCast;
import net.sf.orcc.backends.ir.IrSpecificPackage;
import net.sf.orcc.ir.Def;
import net.sf.orcc.ir.Type;
import net.sf.orcc.ir.TypeList;
import net.sf.orcc.ir.Use;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Inst Cast</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link net.sf.orcc.backends.ir.impl.InstCastImpl#getTarget <em>Target</em>}</li>
 *   <li>{@link net.sf.orcc.backends.ir.impl.InstCastImpl#getSource <em>Source</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class InstCastImpl extends IrInstSpecificImpl implements InstCast {
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
	 * The cached value of the '{@link #getSource() <em>Source</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSource()
	 * @generated
	 * @ordered
	 */
	protected Use source;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected InstCastImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return IrSpecificPackage.Literals.INST_CAST;
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
					Notification.SET, IrSpecificPackage.INST_CAST__TARGET,
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
								- IrSpecificPackage.INST_CAST__TARGET, null,
						msgs);
			if (newTarget != null)
				msgs = ((InternalEObject) newTarget).eInverseAdd(this,
						EOPPOSITE_FEATURE_BASE
								- IrSpecificPackage.INST_CAST__TARGET, null,
						msgs);
			msgs = basicSetTarget(newTarget, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					IrSpecificPackage.INST_CAST__TARGET, newTarget, newTarget));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Use getSource() {
		return source;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetSource(Use newSource,
			NotificationChain msgs) {
		Use oldSource = source;
		source = newSource;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this,
					Notification.SET, IrSpecificPackage.INST_CAST__SOURCE,
					oldSource, newSource);
			if (msgs == null)
				msgs = notification;
			else
				msgs.add(notification);
		}
		return msgs;
	}

	@Override
	public boolean isInstCast() {
		return true;
	}

	/**
	 * Return true if the target type is different from the source type.
	 * 
	 * @return a boolean indicating if target type is different from the source
	 *         type
	 */
	@Override
	public boolean isDifferent() {
		Type targetType = target.getVariable().getType();
		Type sourceType = source.getVariable().getType();
		if (targetType.isList()) {
			return sourceType.getClass() != ((TypeList) targetType)
					.getInnermostType().getClass();
		} else {
			return sourceType.getClass() != targetType.getClass();
		}
	}

	/**
	 * Return true if the target type is extended from the source type.
	 * 
	 * @return a boolean indicating if target type is extended from the source
	 *         type
	 */
	@Override
	public boolean isExtended() {
		Type targetType = target.getVariable().getType();
		Type sourceType = source.getVariable().getType();

		return sourceType.getSizeInBits() < targetType.getSizeInBits();
	}

	/**
	 * Return true if the source type is signed
	 * 
	 * @return a boolean indicating if source is signed type
	 */
	@Override
	public boolean isSigned() {
		Type targetType = target.getVariable().getType();
		Type sourceType = source.getVariable().getType();
		if (sourceType.isUint() || targetType.isUint()) {
			return false;
		}
		if (sourceType.isBool() || targetType.isBool()) {
			return false;
		}
		if (sourceType.isList()) {
			Type elementType = ((TypeList) source).getInnermostType();
			if (elementType.isUint() || elementType.isBool()) {
				return false;
			}
		}
		if (targetType.isList()) {
			Type elementType = ((TypeList) targetType).getInnermostType();
			if (elementType.isUint() || elementType.isBool()) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Return true if the target type is trunced from the source type.
	 * 
	 * @return a boolean indicating if target type is trunced from the source
	 *         type
	 */
	@Override
	public boolean isTrunced() {
		Type targetType = target.getVariable().getType();
		Type sourceType = source.getVariable().getType();

		return sourceType.getSizeInBits() > targetType.getSizeInBits();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSource(Use newSource) {
		if (newSource != source) {
			NotificationChain msgs = null;
			if (source != null)
				msgs = ((InternalEObject) source).eInverseRemove(this,
						EOPPOSITE_FEATURE_BASE
								- IrSpecificPackage.INST_CAST__SOURCE, null,
						msgs);
			if (newSource != null)
				msgs = ((InternalEObject) newSource).eInverseAdd(this,
						EOPPOSITE_FEATURE_BASE
								- IrSpecificPackage.INST_CAST__SOURCE, null,
						msgs);
			msgs = basicSetSource(newSource, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					IrSpecificPackage.INST_CAST__SOURCE, newSource, newSource));
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
		case IrSpecificPackage.INST_CAST__TARGET:
			return basicSetTarget(null, msgs);
		case IrSpecificPackage.INST_CAST__SOURCE:
			return basicSetSource(null, msgs);
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
		case IrSpecificPackage.INST_CAST__TARGET:
			return getTarget();
		case IrSpecificPackage.INST_CAST__SOURCE:
			return getSource();
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
		case IrSpecificPackage.INST_CAST__TARGET:
			setTarget((Def) newValue);
			return;
		case IrSpecificPackage.INST_CAST__SOURCE:
			setSource((Use) newValue);
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
		case IrSpecificPackage.INST_CAST__TARGET:
			setTarget((Def) null);
			return;
		case IrSpecificPackage.INST_CAST__SOURCE:
			setSource((Use) null);
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
		case IrSpecificPackage.INST_CAST__TARGET:
			return target != null;
		case IrSpecificPackage.INST_CAST__SOURCE:
			return source != null;
		}
		return super.eIsSet(featureID);
	}

} //InstCastImpl
