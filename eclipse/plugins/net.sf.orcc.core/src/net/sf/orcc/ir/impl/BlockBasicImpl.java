/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package net.sf.orcc.ir.impl;

import java.util.Collection;
import java.util.Iterator;
import java.util.ListIterator;

import net.sf.orcc.ir.BlockBasic;
import net.sf.orcc.ir.Instruction;
import net.sf.orcc.ir.IrPackage;

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc --> An implementation of the model object '
 * <em><b>BlockBasic</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link net.sf.orcc.ir.impl.BlockBasicImpl#getInstructions <em>Instructions</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class BlockBasicImpl extends BlockImpl implements BlockBasic {
	/**
	 * The cached value of the '{@link #getInstructions() <em>Instructions</em>}
	 * ' containment reference list. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @see #getInstructions()
	 * @generated
	 * @ordered
	 */
	protected EList<Instruction> instructions;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	protected BlockBasicImpl() {
		super();
	}

	@Override
	public void add(Instruction instruction) {
		getInstructions().add(instruction);
	}

	@Override
	public void add(int index, Instruction instruction) {
		getInstructions().add(index, instruction);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case IrPackage.BLOCK_BASIC__INSTRUCTIONS:
			return getInstructions();
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
		case IrPackage.BLOCK_BASIC__INSTRUCTIONS:
			return ((InternalEList<?>) getInstructions()).basicRemove(otherEnd,
					msgs);
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
		case IrPackage.BLOCK_BASIC__INSTRUCTIONS:
			return instructions != null && !instructions.isEmpty();
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
		case IrPackage.BLOCK_BASIC__INSTRUCTIONS:
			getInstructions().clear();
			getInstructions().addAll(
					(Collection<? extends Instruction>) newValue);
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
		return IrPackage.Literals.BLOCK_BASIC;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
		case IrPackage.BLOCK_BASIC__INSTRUCTIONS:
			getInstructions().clear();
			return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Instruction> getInstructions() {
		if (instructions == null) {
			instructions = new EObjectContainmentEList<Instruction>(
					Instruction.class, this,
					IrPackage.BLOCK_BASIC__INSTRUCTIONS);
		}
		return instructions;
	}

	@Override
	public int indexOf(Instruction instruction) {
		return getInstructions().indexOf(instruction);
	}

	@Override
	public boolean isBlockBasic() {
		return true;
	}

	@Override
	public boolean isBlockIf() {
		return false;
	}

	@Override
	public boolean isBlockWhile() {
		return false;
	}

	@Override
	public Iterator<Instruction> iterator() {
		return getInstructions().iterator();
	}

	@Override
	public ListIterator<Instruction> lastListIterator() {
		return getInstructions().listIterator(getInstructions().size());
	}

	@Override
	public ListIterator<Instruction> listIterator() {
		return getInstructions().listIterator();
	}

	@Override
	public ListIterator<Instruction> listIterator(int index) {
		return getInstructions().listIterator(index);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (Instruction instruction : getInstructions()) {
			sb.append(instruction.toString());
			sb.append("\n");
		}

		return sb.toString();
	}

} // BlockBasicImpl
