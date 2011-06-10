/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package net.sf.orcc.backends.tta.architecture.impl;

import net.sf.orcc.backends.tta.architecture.ArchitecturePackage;
import net.sf.orcc.backends.tta.architecture.Reads;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Reads</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link net.sf.orcc.backends.tta.architecture.impl.ReadsImpl#getStartCycle <em>Start Cycle</em>}</li>
 *   <li>{@link net.sf.orcc.backends.tta.architecture.impl.ReadsImpl#getCycles <em>Cycles</em>}</li>
 *   <li>{@link net.sf.orcc.backends.tta.architecture.impl.ReadsImpl#getName <em>Name</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ReadsImpl extends EObjectImpl implements Reads {
	/**
	 * The default value of the '{@link #getStartCycle() <em>Start Cycle</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getStartCycle()
	 * @generated
	 * @ordered
	 */
	protected static final int START_CYCLE_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getStartCycle() <em>Start Cycle</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getStartCycle()
	 * @generated
	 * @ordered
	 */
	protected int startCycle = START_CYCLE_EDEFAULT;

	/**
	 * The default value of the '{@link #getCycles() <em>Cycles</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCycles()
	 * @generated
	 * @ordered
	 */
	protected static final int CYCLES_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getCycles() <em>Cycles</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCycles()
	 * @generated
	 * @ordered
	 */
	protected int cycles = CYCLES_EDEFAULT;

	/**
	 * The default value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected static final int NAME_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected int name = NAME_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ReadsImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ArchitecturePackage.Literals.READS;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getStartCycle() {
		return startCycle;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setStartCycle(int newStartCycle) {
		int oldStartCycle = startCycle;
		startCycle = newStartCycle;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ArchitecturePackage.READS__START_CYCLE, oldStartCycle, startCycle));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getCycles() {
		return cycles;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCycles(int newCycles) {
		int oldCycles = cycles;
		cycles = newCycles;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ArchitecturePackage.READS__CYCLES, oldCycles, cycles));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getName() {
		return name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setName(int newName) {
		int oldName = name;
		name = newName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ArchitecturePackage.READS__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isReads() {
		// TODO: implement this method
		// Ensure that you remove @generated or mark it @generated NOT
		throw new UnsupportedOperationException();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isWrites() {
		// TODO: implement this method
		// Ensure that you remove @generated or mark it @generated NOT
		throw new UnsupportedOperationException();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isResource() {
		// TODO: implement this method
		// Ensure that you remove @generated or mark it @generated NOT
		throw new UnsupportedOperationException();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case ArchitecturePackage.READS__START_CYCLE:
				return getStartCycle();
			case ArchitecturePackage.READS__CYCLES:
				return getCycles();
			case ArchitecturePackage.READS__NAME:
				return getName();
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
			case ArchitecturePackage.READS__START_CYCLE:
				setStartCycle((Integer)newValue);
				return;
			case ArchitecturePackage.READS__CYCLES:
				setCycles((Integer)newValue);
				return;
			case ArchitecturePackage.READS__NAME:
				setName((Integer)newValue);
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
			case ArchitecturePackage.READS__START_CYCLE:
				setStartCycle(START_CYCLE_EDEFAULT);
				return;
			case ArchitecturePackage.READS__CYCLES:
				setCycles(CYCLES_EDEFAULT);
				return;
			case ArchitecturePackage.READS__NAME:
				setName(NAME_EDEFAULT);
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
			case ArchitecturePackage.READS__START_CYCLE:
				return startCycle != START_CYCLE_EDEFAULT;
			case ArchitecturePackage.READS__CYCLES:
				return cycles != CYCLES_EDEFAULT;
			case ArchitecturePackage.READS__NAME:
				return name != NAME_EDEFAULT;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (startCycle: ");
		result.append(startCycle);
		result.append(", cycles: ");
		result.append(cycles);
		result.append(", name: ");
		result.append(name);
		result.append(')');
		return result.toString();
	}

} //ReadsImpl
