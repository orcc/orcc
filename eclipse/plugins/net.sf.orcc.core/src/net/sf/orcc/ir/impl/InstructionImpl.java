/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package net.sf.orcc.ir.impl;

import net.sf.dftools.util.impl.AttributableImpl;
import net.sf.orcc.ir.Instruction;
import net.sf.orcc.ir.IrPackage;
import net.sf.orcc.ir.NodeBlock;
import net.sf.orcc.ir.Predicate;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc --> An implementation of the model object '
 * <em><b>Instruction</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link net.sf.orcc.ir.impl.InstructionImpl#getLineNumber <em>Line Number</em>}</li>
 *   <li>{@link net.sf.orcc.ir.impl.InstructionImpl#getPredicate <em>Predicate</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public abstract class InstructionImpl extends AttributableImpl implements
		Instruction {

	/**
	 * The default value of the '{@link #getLineNumber() <em>Line Number</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLineNumber()
	 * @generated
	 * @ordered
	 */
	protected static final int LINE_NUMBER_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getLineNumber() <em>Line Number</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLineNumber()
	 * @generated
	 * @ordered
	 */
	protected int lineNumber = LINE_NUMBER_EDEFAULT;

	/**
	 * The cached value of the '{@link #getPredicate() <em>Predicate</em>}' containment reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getPredicate()
	 * @generated
	 * @ordered
	 */
	protected Predicate predicate;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	protected InstructionImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetPredicate(Predicate newPredicate,
			NotificationChain msgs) {
		Predicate oldPredicate = predicate;
		predicate = newPredicate;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this,
					Notification.SET, IrPackage.INSTRUCTION__PREDICATE,
					oldPredicate, newPredicate);
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
		case IrPackage.INSTRUCTION__LINE_NUMBER:
			return getLineNumber();
		case IrPackage.INSTRUCTION__PREDICATE:
			return getPredicate();
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
		case IrPackage.INSTRUCTION__PREDICATE:
			return basicSetPredicate(null, msgs);
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
		case IrPackage.INSTRUCTION__LINE_NUMBER:
			return lineNumber != LINE_NUMBER_EDEFAULT;
		case IrPackage.INSTRUCTION__PREDICATE:
			return predicate != null;
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
		case IrPackage.INSTRUCTION__LINE_NUMBER:
			setLineNumber((Integer) newValue);
			return;
		case IrPackage.INSTRUCTION__PREDICATE:
			setPredicate((Predicate) newValue);
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
		return IrPackage.Literals.INSTRUCTION;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
		case IrPackage.INSTRUCTION__LINE_NUMBER:
			setLineNumber(LINE_NUMBER_EDEFAULT);
			return;
		case IrPackage.INSTRUCTION__PREDICATE:
			setPredicate((Predicate) null);
			return;
		}
		super.eUnset(featureID);
	}

	@Override
	public NodeBlock getBlock() {
		return (NodeBlock) eContainer();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getLineNumber() {
		return lineNumber;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public Predicate getPredicate() {
		return predicate;
	}

	@Override
	public boolean isAssign() {
		return false;
	}

	@Override
	public boolean isCall() {
		return false;
	}

	@Override
	public boolean isLoad() {
		return false;
	}

	@Override
	public boolean isPhi() {
		return false;
	}

	@Override
	public boolean isReturn() {
		return false;
	}

	@Override
	public boolean isStore() {
		return false;
	}
	
	@Override
	public boolean isInstSpecific() {
		return false;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setLineNumber(int newLineNumber) {
		int oldLineNumber = lineNumber;
		lineNumber = newLineNumber;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					IrPackage.INSTRUCTION__LINE_NUMBER, oldLineNumber,
					lineNumber));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public void setPredicate(Predicate newPredicate) {
		if (newPredicate != predicate) {
			NotificationChain msgs = null;
			if (predicate != null)
				msgs = ((InternalEObject) predicate).eInverseRemove(this,
						EOPPOSITE_FEATURE_BASE
								- IrPackage.INSTRUCTION__PREDICATE, null, msgs);
			if (newPredicate != null)
				msgs = ((InternalEObject) newPredicate).eInverseAdd(this,
						EOPPOSITE_FEATURE_BASE
								- IrPackage.INSTRUCTION__PREDICATE, null, msgs);
			msgs = basicSetPredicate(newPredicate, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					IrPackage.INSTRUCTION__PREDICATE, newPredicate,
					newPredicate));
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		if (predicate != null && !predicate.isEmpty()) {
			builder.append(predicate.toString());
			builder.append(" ");
		}
		return builder.toString();
	}

} // InstructionImpl
