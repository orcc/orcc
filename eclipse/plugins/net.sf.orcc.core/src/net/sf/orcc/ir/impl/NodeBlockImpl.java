/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package net.sf.orcc.ir.impl;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import net.sf.orcc.ir.Instruction;
import net.sf.orcc.ir.IrPackage;
import net.sf.orcc.ir.NodeBlock;
import net.sf.orcc.ir.nodes.NodeInterpreter;
import net.sf.orcc.ir.nodes.NodeVisitor;

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Node Block</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link net.sf.orcc.ir.impl.NodeBlockImpl#getInstructions <em>Instructions</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class NodeBlockImpl extends NodeImpl implements NodeBlock {
	/**
	 * The cached value of the '{@link #getInstructions() <em>Instructions</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getInstructions()
	 * @generated
	 * @ordered
	 */
	protected EList<Instruction> instructions;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected NodeBlockImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return IrPackage.Literals.NODE_BLOCK;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Instruction> getInstructions() {
		if (instructions == null) {
			instructions = new EObjectContainmentEList<Instruction>(Instruction.class, this, IrPackage.NODE_BLOCK__INSTRUCTIONS);
		}
		return instructions;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case IrPackage.NODE_BLOCK__INSTRUCTIONS:
				return ((InternalEList<?>)getInstructions()).basicRemove(otherEnd, msgs);
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
			case IrPackage.NODE_BLOCK__INSTRUCTIONS:
				return getInstructions();
		}
		return super.eGet(featureID, resolve, coreType);
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
			case IrPackage.NODE_BLOCK__INSTRUCTIONS:
				getInstructions().clear();
				getInstructions().addAll((Collection<? extends Instruction>)newValue);
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
			case IrPackage.NODE_BLOCK__INSTRUCTIONS:
				getInstructions().clear();
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
			case IrPackage.NODE_BLOCK__INSTRUCTIONS:
				return instructions != null && !instructions.isEmpty();
		}
		return super.eIsSet(featureID);
	}

	@Override
	public Object accept(NodeInterpreter interpreter, Object... args) {
		return interpreter.interpret(this, args);
	}

	@Override
	public void accept(NodeVisitor visitor) {
		visitor.visit(this);
	}

	/**
	 * Appends the instructions of the specified block at the end of this block.
	 * 
	 * @param block
	 *            a block
	 */
	public void add(NodeBlock block) {
		for (Instruction instruction : block.getInstructions()) {
			add(instruction);
		}
	}

	/**
	 * Appends the specified instruction to the end of this block.
	 * 
	 * @param instruction
	 *            an instruction
	 */
	public void add(Instruction instruction) {
		getInstructions().add(instruction);
	}

	/**
	 * Appends the specified instruction to this block at the specified index.
	 * 
	 * @param index
	 *            the index
	 * @param instruction
	 *            an instruction
	 */
	public void add(int index, Instruction instruction) {
		getInstructions().add(index, instruction);
	}

	/**
	 * Appends a list of instruction to the end of this block.
	 * 
	 * @param instructions
	 *            a list of instruction
	 */
	public void addAll(List<Instruction> instructions) {
		for (Instruction instr : instructions) {
			add(instr);
		}
	}

	/**
	 * Returns the index of the given instruction in the list of instructions of
	 * this block.
	 * 
	 * @param instruction
	 *            an instruction
	 * @return the index of the given instruction in the list of instructions of
	 *         this block
	 */
	public int indexOf(Instruction instruction) {
		return getInstructions().indexOf(instruction);
	}

	@Override
	public boolean isBlockNode() {
		return true;
	}

	@Override
	public Iterator<Instruction> iterator() {
		return getInstructions().iterator();
	}

	/**
	 * Returns a list iterator over the elements in this list (in proper
	 * sequence) that is positioned after the last instruction.
	 * 
	 * @return a list iterator over the elements in this list (in proper
	 *         sequence)
	 */
	public ListIterator<Instruction> lastListIterator() {
		return getInstructions().listIterator(getInstructions().size());
	}

	/**
	 * Returns a list iterator over the elements in this list (in proper
	 * sequence).
	 * 
	 * @return a list iterator over the elements in this list (in proper
	 *         sequence)
	 */
	public ListIterator<Instruction> listIterator() {
		return getInstructions().listIterator();
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

} //NodeBlockImpl
