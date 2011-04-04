/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package net.sf.orcc.ir.impl;

import net.sf.orcc.ir.Cast;
import net.sf.orcc.ir.Instruction;
import net.sf.orcc.ir.IrPackage;
import net.sf.orcc.ir.Location;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import net.sf.orcc.ir.NodeBlock;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Instruction</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link net.sf.orcc.ir.impl.InstructionImpl#getLocation <em>Location</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public abstract class InstructionImpl extends UserImpl implements Instruction {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
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
	@Override
	protected EClass eStaticClass() {
		return IrPackage.Literals.INSTRUCTION;
	}

	private Location location;

	protected InstructionImpl(Location location) {
		this.location = location;
	}

	@Override
	public NodeBlock getBlock() {
		return (NodeBlock) eContainer();
	}

	@Override
	public Cast getCast() {
		return null;
	}

	@Override
	public Location getLocation() {
		return location;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetLocation(Location newLocation, NotificationChain msgs) {
		Location oldLocation = location;
		location = newLocation;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, IrPackage.INSTRUCTION__LOCATION, oldLocation, newLocation);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
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
	public boolean isCFGNode() {
		return false;
	}

	@Override
	public boolean isInstruction() {
		return true;
	}

	@Override
	public boolean isLoad() {
		return false;
	}

	@Override
	public boolean isPeek() {
		return false;
	}

	@Override
	public boolean isPhi() {
		return false;
	}

	@Override
	public boolean isRead() {
		return false;
	}

	@Override
	public boolean isReadEnd() {
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
	public boolean isWrite() {
		return false;
	}

	@Override
	public boolean isWriteEnd() {
		return false;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case IrPackage.INSTRUCTION__LOCATION:
				return basicSetLocation(null, msgs);
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
			case IrPackage.INSTRUCTION__LOCATION:
				return getLocation();
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
			case IrPackage.INSTRUCTION__LOCATION:
				setLocation((Location)newValue);
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
			case IrPackage.INSTRUCTION__LOCATION:
				setLocation((Location)null);
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
			case IrPackage.INSTRUCTION__LOCATION:
				return location != null;
		}
		return super.eIsSet(featureID);
	}

} //InstructionImpl
