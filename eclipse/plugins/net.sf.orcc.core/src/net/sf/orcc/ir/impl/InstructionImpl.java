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
import net.sf.orcc.ir.nodes.NodeBlock;

import org.eclipse.emf.ecore.EClass;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Instruction</b></em>'.
 * <!-- end-user-doc -->
 * <p>
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
	
	private NodeBlock block;

	private Location location;

	protected InstructionImpl(Location location) {
		this.location = location;
	}

	@Override
	public NodeBlock getBlock() {
		return block;
	}

	@Override
	public Cast getCast() {
		return null;
	}

	@Override
	public Location getLocation() {
		return location;
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

	@Override
	public void setBlock(NodeBlock block) {
		this.block = block;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

} //InstructionImpl
