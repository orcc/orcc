/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package net.sf.orcc.ir.impl;

import java.util.Collection;
import java.util.List;

import net.sf.orcc.ir.Block;
import net.sf.orcc.ir.BlockBasic;
import net.sf.orcc.ir.BlockIf;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.IrPackage;

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
 * <em><b>BlockIf</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link net.sf.orcc.ir.impl.BlockIfImpl#getCondition <em>Condition</em>}</li>
 *   <li>{@link net.sf.orcc.ir.impl.BlockIfImpl#getElseBlocks <em>Else Blocks</em>}</li>
 *   <li>{@link net.sf.orcc.ir.impl.BlockIfImpl#getJoinBlock <em>Join Block</em>}</li>
 *   <li>{@link net.sf.orcc.ir.impl.BlockIfImpl#getLineNumber <em>Line Number</em>}</li>
 *   <li>{@link net.sf.orcc.ir.impl.BlockIfImpl#getThenBlocks <em>Then Blocks</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class BlockIfImpl extends BlockImpl implements BlockIf {
	/**
	 * The cached value of the '{@link #getCondition() <em>Condition</em>}' containment reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getCondition()
	 * @generated
	 * @ordered
	 */
	protected Expression condition;

	/**
	 * The cached value of the '{@link #getElseBlocks() <em>Else Blocks</em>}' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getElseBlocks()
	 * @generated
	 * @ordered
	 */
	protected EList<Block> elseBlocks;

	/**
	 * The cached value of the '{@link #getJoinBlock() <em>Join Block</em>}' containment reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getJoinBlock()
	 * @generated
	 * @ordered
	 */
	protected BlockBasic joinBlock;

	/**
	 * The default value of the '{@link #getLineNumber() <em>Line Number</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getLineNumber()
	 * @generated
	 * @ordered
	 */
	protected static final int LINE_NUMBER_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getLineNumber() <em>Line Number</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getLineNumber()
	 * @generated
	 * @ordered
	 */
	protected int lineNumber = LINE_NUMBER_EDEFAULT;

	/**
	 * The cached value of the '{@link #getThenBlocks() <em>Then Blocks</em>}' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getThenBlocks()
	 * @generated
	 * @ordered
	 */
	protected EList<Block> thenBlocks;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	protected BlockIfImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetCondition(Expression newCondition, NotificationChain msgs) {
		Expression oldCondition = condition;
		condition = newCondition;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					IrPackage.BLOCK_IF__CONDITION, oldCondition, newCondition);
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
	public NotificationChain basicSetJoinBlock(BlockBasic newJoinBlock, NotificationChain msgs) {
		BlockBasic oldJoinBlock = joinBlock;
		joinBlock = newJoinBlock;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					IrPackage.BLOCK_IF__JOIN_BLOCK, oldJoinBlock, newJoinBlock);
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
		case IrPackage.BLOCK_IF__CONDITION:
			return getCondition();
		case IrPackage.BLOCK_IF__ELSE_BLOCKS:
			return getElseBlocks();
		case IrPackage.BLOCK_IF__JOIN_BLOCK:
			return getJoinBlock();
		case IrPackage.BLOCK_IF__LINE_NUMBER:
			return getLineNumber();
		case IrPackage.BLOCK_IF__THEN_BLOCKS:
			return getThenBlocks();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case IrPackage.BLOCK_IF__CONDITION:
			return basicSetCondition(null, msgs);
		case IrPackage.BLOCK_IF__ELSE_BLOCKS:
			return ((InternalEList<?>) getElseBlocks()).basicRemove(otherEnd, msgs);
		case IrPackage.BLOCK_IF__JOIN_BLOCK:
			return basicSetJoinBlock(null, msgs);
		case IrPackage.BLOCK_IF__THEN_BLOCKS:
			return ((InternalEList<?>) getThenBlocks()).basicRemove(otherEnd, msgs);
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
		case IrPackage.BLOCK_IF__CONDITION:
			return condition != null;
		case IrPackage.BLOCK_IF__ELSE_BLOCKS:
			return elseBlocks != null && !elseBlocks.isEmpty();
		case IrPackage.BLOCK_IF__JOIN_BLOCK:
			return joinBlock != null;
		case IrPackage.BLOCK_IF__LINE_NUMBER:
			return lineNumber != LINE_NUMBER_EDEFAULT;
		case IrPackage.BLOCK_IF__THEN_BLOCKS:
			return thenBlocks != null && !thenBlocks.isEmpty();
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
		case IrPackage.BLOCK_IF__CONDITION:
			setCondition((Expression) newValue);
			return;
		case IrPackage.BLOCK_IF__ELSE_BLOCKS:
			getElseBlocks().clear();
			getElseBlocks().addAll((Collection<? extends Block>) newValue);
			return;
		case IrPackage.BLOCK_IF__JOIN_BLOCK:
			setJoinBlock((BlockBasic) newValue);
			return;
		case IrPackage.BLOCK_IF__LINE_NUMBER:
			setLineNumber((Integer) newValue);
			return;
		case IrPackage.BLOCK_IF__THEN_BLOCKS:
			getThenBlocks().clear();
			getThenBlocks().addAll((Collection<? extends Block>) newValue);
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
		return IrPackage.Literals.BLOCK_IF;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
		case IrPackage.BLOCK_IF__CONDITION:
			setCondition((Expression) null);
			return;
		case IrPackage.BLOCK_IF__ELSE_BLOCKS:
			getElseBlocks().clear();
			return;
		case IrPackage.BLOCK_IF__JOIN_BLOCK:
			setJoinBlock((BlockBasic) null);
			return;
		case IrPackage.BLOCK_IF__LINE_NUMBER:
			setLineNumber(LINE_NUMBER_EDEFAULT);
			return;
		case IrPackage.BLOCK_IF__THEN_BLOCKS:
			getThenBlocks().clear();
			return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public Expression getCondition() {
		return condition;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Block> getElseBlocks() {
		if (elseBlocks == null) {
			elseBlocks = new EObjectContainmentEList<Block>(Block.class, this, IrPackage.BLOCK_IF__ELSE_BLOCKS);
		}
		return elseBlocks;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public BlockBasic getJoinBlock() {
		return joinBlock;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public int getLineNumber() {
		return lineNumber;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Block> getThenBlocks() {
		if (thenBlocks == null) {
			thenBlocks = new EObjectContainmentEList<Block>(Block.class, this, IrPackage.BLOCK_IF__THEN_BLOCKS);
		}
		return thenBlocks;
	}

	@Override
	public boolean isBlockBasic() {
		return false;
	}

	@Override
	public boolean isBlockIf() {
		return true;
	}

	@Override
	public boolean isBlockWhile() {
		return false;
	}

	@Override
	public boolean isElseRequired() {
		List<Block> blocks = getElseBlocks();
		if (blocks.isEmpty()) {
			return false;
		} else if (blocks.size() == 1) {
			Block block = blocks.get(0);
			if (block.isBlockBasic()) {
				return !((BlockBasic) block).getInstructions().isEmpty();
			}
		}

		// more than one block, or one non-empty basic block
		return true;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public void setCondition(Expression newCondition) {
		if (newCondition != condition) {
			NotificationChain msgs = null;
			if (condition != null)
				msgs = ((InternalEObject) condition).eInverseRemove(this,
						EOPPOSITE_FEATURE_BASE - IrPackage.BLOCK_IF__CONDITION, null, msgs);
			if (newCondition != null)
				msgs = ((InternalEObject) newCondition).eInverseAdd(this,
						EOPPOSITE_FEATURE_BASE - IrPackage.BLOCK_IF__CONDITION, null, msgs);
			msgs = basicSetCondition(newCondition, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, IrPackage.BLOCK_IF__CONDITION, newCondition,
					newCondition));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public void setJoinBlock(BlockBasic newJoinBlock) {
		if (newJoinBlock != joinBlock) {
			NotificationChain msgs = null;
			if (joinBlock != null)
				msgs = ((InternalEObject) joinBlock).eInverseRemove(this,
						EOPPOSITE_FEATURE_BASE - IrPackage.BLOCK_IF__JOIN_BLOCK, null, msgs);
			if (newJoinBlock != null)
				msgs = ((InternalEObject) newJoinBlock).eInverseAdd(this,
						EOPPOSITE_FEATURE_BASE - IrPackage.BLOCK_IF__JOIN_BLOCK, null, msgs);
			msgs = basicSetJoinBlock(newJoinBlock, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, IrPackage.BLOCK_IF__JOIN_BLOCK, newJoinBlock,
					newJoinBlock));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public void setLineNumber(int newLineNumber) {
		int oldLineNumber = lineNumber;
		lineNumber = newLineNumber;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, IrPackage.BLOCK_IF__LINE_NUMBER, oldLineNumber,
					lineNumber));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy())
			return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (lineNumber: ");
		result.append(lineNumber);
		result.append(')');
		return result.toString();
	}

} // BlockIfImpl
