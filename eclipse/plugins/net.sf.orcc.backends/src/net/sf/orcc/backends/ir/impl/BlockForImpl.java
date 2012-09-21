/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package net.sf.orcc.backends.ir.impl;

import java.util.Collection;

import net.sf.orcc.backends.ir.BlockFor;
import net.sf.orcc.backends.ir.IrSpecificPackage;
import net.sf.orcc.ir.Block;
import net.sf.orcc.ir.BlockBasic;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.Instruction;
import net.sf.orcc.ir.impl.BlockImpl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Node For</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link net.sf.orcc.backends.ir.impl.BlockForImpl#getCondition <em>Condition</em>}</li>
 *   <li>{@link net.sf.orcc.backends.ir.impl.BlockForImpl#getJoinBlock <em>Join Block</em>}</li>
 *   <li>{@link net.sf.orcc.backends.ir.impl.BlockForImpl#getLineNumber <em>Line Number</em>}</li>
 *   <li>{@link net.sf.orcc.backends.ir.impl.BlockForImpl#getBlocks <em>Blocks</em>}</li>
 *   <li>{@link net.sf.orcc.backends.ir.impl.BlockForImpl#getStep <em>Step</em>}</li>
 *   <li>{@link net.sf.orcc.backends.ir.impl.BlockForImpl#getInit <em>Init</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class BlockForImpl extends BlockImpl implements BlockFor {
	/**
	 * The cached value of the '{@link #getCondition() <em>Condition</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCondition()
	 * @generated
	 * @ordered
	 */
	protected Expression condition;

	/**
	 * The cached value of the '{@link #getJoinBlock() <em>Join Block</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getJoinBlock()
	 * @generated
	 * @ordered
	 */
	protected BlockBasic joinBlock;

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
	 * The cached value of the '{@link #getBlocks() <em>Blocks</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBlocks()
	 * @generated
	 * @ordered
	 */
	protected EList<Block> blocks;

	/**
	 * The cached value of the '{@link #getStep() <em>Step</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getStep()
	 * @generated
	 * @ordered
	 */
	protected EList<Instruction> step;

	/**
	 * The cached value of the '{@link #getInit() <em>Init</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getInit()
	 * @generated
	 * @ordered
	 */
	protected EList<Instruction> init;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected BlockForImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return IrSpecificPackage.Literals.BLOCK_FOR;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Expression getCondition() {
		return condition;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetCondition(Expression newCondition,
			NotificationChain msgs) {
		Expression oldCondition = condition;
		condition = newCondition;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this,
					Notification.SET, IrSpecificPackage.BLOCK_FOR__CONDITION,
					oldCondition, newCondition);
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
	public void setCondition(Expression newCondition) {
		if (newCondition != condition) {
			NotificationChain msgs = null;
			if (condition != null)
				msgs = ((InternalEObject) condition).eInverseRemove(this,
						EOPPOSITE_FEATURE_BASE
								- IrSpecificPackage.BLOCK_FOR__CONDITION, null,
						msgs);
			if (newCondition != null)
				msgs = ((InternalEObject) newCondition).eInverseAdd(this,
						EOPPOSITE_FEATURE_BASE
								- IrSpecificPackage.BLOCK_FOR__CONDITION, null,
						msgs);
			msgs = basicSetCondition(newCondition, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					IrSpecificPackage.BLOCK_FOR__CONDITION, newCondition,
					newCondition));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BlockBasic getJoinBlock() {
		return joinBlock;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetJoinBlock(BlockBasic newJoinBlock,
			NotificationChain msgs) {
		BlockBasic oldJoinBlock = joinBlock;
		joinBlock = newJoinBlock;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this,
					Notification.SET, IrSpecificPackage.BLOCK_FOR__JOIN_BLOCK,
					oldJoinBlock, newJoinBlock);
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
	public void setJoinBlock(BlockBasic newJoinBlock) {
		if (newJoinBlock != joinBlock) {
			NotificationChain msgs = null;
			if (joinBlock != null)
				msgs = ((InternalEObject) joinBlock).eInverseRemove(this,
						EOPPOSITE_FEATURE_BASE
								- IrSpecificPackage.BLOCK_FOR__JOIN_BLOCK,
						null, msgs);
			if (newJoinBlock != null)
				msgs = ((InternalEObject) newJoinBlock).eInverseAdd(this,
						EOPPOSITE_FEATURE_BASE
								- IrSpecificPackage.BLOCK_FOR__JOIN_BLOCK,
						null, msgs);
			msgs = basicSetJoinBlock(newJoinBlock, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					IrSpecificPackage.BLOCK_FOR__JOIN_BLOCK, newJoinBlock,
					newJoinBlock));
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
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setLineNumber(int newLineNumber) {
		int oldLineNumber = lineNumber;
		lineNumber = newLineNumber;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					IrSpecificPackage.BLOCK_FOR__LINE_NUMBER, oldLineNumber,
					lineNumber));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Block> getBlocks() {
		if (blocks == null) {
			blocks = new EObjectContainmentEList<Block>(Block.class, this,
					IrSpecificPackage.BLOCK_FOR__BLOCKS);
		}
		return blocks;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Instruction> getStep() {
		if (step == null) {
			step = new EObjectContainmentEList<Instruction>(Instruction.class,
					this, IrSpecificPackage.BLOCK_FOR__STEP);
		}
		return step;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Instruction> getInit() {
		if (init == null) {
			init = new EObjectContainmentEList<Instruction>(Instruction.class,
					this, IrSpecificPackage.BLOCK_FOR__INIT);
		}
		return init;
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
		case IrSpecificPackage.BLOCK_FOR__CONDITION:
			return basicSetCondition(null, msgs);
		case IrSpecificPackage.BLOCK_FOR__JOIN_BLOCK:
			return basicSetJoinBlock(null, msgs);
		case IrSpecificPackage.BLOCK_FOR__BLOCKS:
			return ((InternalEList<?>) getBlocks()).basicRemove(otherEnd, msgs);
		case IrSpecificPackage.BLOCK_FOR__STEP:
			return ((InternalEList<?>) getStep()).basicRemove(otherEnd, msgs);
		case IrSpecificPackage.BLOCK_FOR__INIT:
			return ((InternalEList<?>) getInit()).basicRemove(otherEnd, msgs);
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
		case IrSpecificPackage.BLOCK_FOR__CONDITION:
			return getCondition();
		case IrSpecificPackage.BLOCK_FOR__JOIN_BLOCK:
			return getJoinBlock();
		case IrSpecificPackage.BLOCK_FOR__LINE_NUMBER:
			return getLineNumber();
		case IrSpecificPackage.BLOCK_FOR__BLOCKS:
			return getBlocks();
		case IrSpecificPackage.BLOCK_FOR__STEP:
			return getStep();
		case IrSpecificPackage.BLOCK_FOR__INIT:
			return getInit();
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
		case IrSpecificPackage.BLOCK_FOR__CONDITION:
			setCondition((Expression) newValue);
			return;
		case IrSpecificPackage.BLOCK_FOR__JOIN_BLOCK:
			setJoinBlock((BlockBasic) newValue);
			return;
		case IrSpecificPackage.BLOCK_FOR__LINE_NUMBER:
			setLineNumber((Integer) newValue);
			return;
		case IrSpecificPackage.BLOCK_FOR__BLOCKS:
			getBlocks().clear();
			getBlocks().addAll((Collection<? extends Block>) newValue);
			return;
		case IrSpecificPackage.BLOCK_FOR__STEP:
			getStep().clear();
			getStep().addAll((Collection<? extends Instruction>) newValue);
			return;
		case IrSpecificPackage.BLOCK_FOR__INIT:
			getInit().clear();
			getInit().addAll((Collection<? extends Instruction>) newValue);
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
		case IrSpecificPackage.BLOCK_FOR__CONDITION:
			setCondition((Expression) null);
			return;
		case IrSpecificPackage.BLOCK_FOR__JOIN_BLOCK:
			setJoinBlock((BlockBasic) null);
			return;
		case IrSpecificPackage.BLOCK_FOR__LINE_NUMBER:
			setLineNumber(LINE_NUMBER_EDEFAULT);
			return;
		case IrSpecificPackage.BLOCK_FOR__BLOCKS:
			getBlocks().clear();
			return;
		case IrSpecificPackage.BLOCK_FOR__STEP:
			getStep().clear();
			return;
		case IrSpecificPackage.BLOCK_FOR__INIT:
			getInit().clear();
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
		case IrSpecificPackage.BLOCK_FOR__CONDITION:
			return condition != null;
		case IrSpecificPackage.BLOCK_FOR__JOIN_BLOCK:
			return joinBlock != null;
		case IrSpecificPackage.BLOCK_FOR__LINE_NUMBER:
			return lineNumber != LINE_NUMBER_EDEFAULT;
		case IrSpecificPackage.BLOCK_FOR__BLOCKS:
			return blocks != null && !blocks.isEmpty();
		case IrSpecificPackage.BLOCK_FOR__STEP:
			return step != null && !step.isEmpty();
		case IrSpecificPackage.BLOCK_FOR__INIT:
			return init != null && !init.isEmpty();
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
		if (eIsProxy())
			return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (lineNumber: ");
		result.append(lineNumber);
		result.append(')');
		return result.toString();
	}

	@Override
	public boolean isBlockBasic() {
		return false;
	}

	@Override
	public boolean isBlockIf() {
		return false;
	}

	@Override
	public boolean isBlockWhile() {
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.sf.orcc.backends.ir.BlockFor#isNodeFor()
	 */
	@Override
	public boolean isNodeFor() {
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.sf.orcc.backends.ir.BlockFor#isBlockFor()
	 */
	@Override
	public boolean isBlockFor() {
		return true;
	}

} //NodeForImpl
