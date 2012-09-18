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
 *   <li>{@link net.sf.orcc.backends.ir.impl.BlockForImpl#getJoinNode <em>Join Node</em>}</li>
 *   <li>{@link net.sf.orcc.backends.ir.impl.BlockForImpl#getLineNumber <em>Line Number</em>}</li>
 *   <li>{@link net.sf.orcc.backends.ir.impl.BlockForImpl#getNodes <em>Nodes</em>}</li>
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
	 * The cached value of the '{@link #getJoinNode() <em>Join Node</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getJoinNode()
	 * @generated
	 * @ordered
	 */
	protected BlockBasic joinNode;

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
	 * The cached value of the '{@link #getNodes() <em>Nodes</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNodes()
	 * @generated
	 * @ordered
	 */
	protected EList<Block> nodes;

	/**
	 * The cached value of the '{@link #getStep() <em>Step</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getStep()
	 * @generated
	 * @ordered
	 */
	protected Instruction step;

	/**
	 * The cached value of the '{@link #getInit() <em>Init</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getInit()
	 * @generated
	 * @ordered
	 */
	protected Instruction init;

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
	public BlockBasic getJoinNode() {
		return joinNode;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetJoinNode(BlockBasic newJoinNode,
			NotificationChain msgs) {
		BlockBasic oldJoinNode = joinNode;
		joinNode = newJoinNode;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this,
					Notification.SET, IrSpecificPackage.BLOCK_FOR__JOIN_NODE,
					oldJoinNode, newJoinNode);
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
	public void setJoinNode(BlockBasic newJoinNode) {
		if (newJoinNode != joinNode) {
			NotificationChain msgs = null;
			if (joinNode != null)
				msgs = ((InternalEObject) joinNode).eInverseRemove(this,
						EOPPOSITE_FEATURE_BASE
								- IrSpecificPackage.BLOCK_FOR__JOIN_NODE, null,
						msgs);
			if (newJoinNode != null)
				msgs = ((InternalEObject) newJoinNode).eInverseAdd(this,
						EOPPOSITE_FEATURE_BASE
								- IrSpecificPackage.BLOCK_FOR__JOIN_NODE, null,
						msgs);
			msgs = basicSetJoinNode(newJoinNode, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					IrSpecificPackage.BLOCK_FOR__JOIN_NODE, newJoinNode,
					newJoinNode));
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
	public EList<Block> getNodes() {
		if (nodes == null) {
			nodes = new EObjectContainmentEList<Block>(Block.class, this,
					IrSpecificPackage.BLOCK_FOR__NODES);
		}
		return nodes;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Instruction getStep() {
		return step;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetStep(Instruction newStep,
			NotificationChain msgs) {
		Instruction oldStep = step;
		step = newStep;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this,
					Notification.SET, IrSpecificPackage.BLOCK_FOR__STEP,
					oldStep, newStep);
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
	public void setStep(Instruction newStep) {
		if (newStep != step) {
			NotificationChain msgs = null;
			if (step != null)
				msgs = ((InternalEObject) step)
						.eInverseRemove(this, EOPPOSITE_FEATURE_BASE
								- IrSpecificPackage.BLOCK_FOR__STEP, null, msgs);
			if (newStep != null)
				msgs = ((InternalEObject) newStep)
						.eInverseAdd(this, EOPPOSITE_FEATURE_BASE
								- IrSpecificPackage.BLOCK_FOR__STEP, null, msgs);
			msgs = basicSetStep(newStep, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					IrSpecificPackage.BLOCK_FOR__STEP, newStep, newStep));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Instruction getInit() {
		return init;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetInit(Instruction newInit,
			NotificationChain msgs) {
		Instruction oldInit = init;
		init = newInit;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this,
					Notification.SET, IrSpecificPackage.BLOCK_FOR__INIT,
					oldInit, newInit);
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
	public void setInit(Instruction newInit) {
		if (newInit != init) {
			NotificationChain msgs = null;
			if (init != null)
				msgs = ((InternalEObject) init)
						.eInverseRemove(this, EOPPOSITE_FEATURE_BASE
								- IrSpecificPackage.BLOCK_FOR__INIT, null, msgs);
			if (newInit != null)
				msgs = ((InternalEObject) newInit)
						.eInverseAdd(this, EOPPOSITE_FEATURE_BASE
								- IrSpecificPackage.BLOCK_FOR__INIT, null, msgs);
			msgs = basicSetInit(newInit, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					IrSpecificPackage.BLOCK_FOR__INIT, newInit, newInit));
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
		case IrSpecificPackage.BLOCK_FOR__JOIN_NODE:
			return basicSetJoinNode(null, msgs);
		case IrSpecificPackage.BLOCK_FOR__NODES:
			return ((InternalEList<?>) getNodes()).basicRemove(otherEnd, msgs);
		case IrSpecificPackage.BLOCK_FOR__STEP:
			return basicSetStep(null, msgs);
		case IrSpecificPackage.BLOCK_FOR__INIT:
			return basicSetInit(null, msgs);
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
		case IrSpecificPackage.BLOCK_FOR__JOIN_NODE:
			return getJoinNode();
		case IrSpecificPackage.BLOCK_FOR__LINE_NUMBER:
			return getLineNumber();
		case IrSpecificPackage.BLOCK_FOR__NODES:
			return getNodes();
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
		case IrSpecificPackage.BLOCK_FOR__JOIN_NODE:
			setJoinNode((BlockBasic) newValue);
			return;
		case IrSpecificPackage.BLOCK_FOR__LINE_NUMBER:
			setLineNumber((Integer) newValue);
			return;
		case IrSpecificPackage.BLOCK_FOR__NODES:
			getNodes().clear();
			getNodes().addAll((Collection<? extends Block>) newValue);
			return;
		case IrSpecificPackage.BLOCK_FOR__STEP:
			setStep((Instruction) newValue);
			return;
		case IrSpecificPackage.BLOCK_FOR__INIT:
			setInit((Instruction) newValue);
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
		case IrSpecificPackage.BLOCK_FOR__JOIN_NODE:
			setJoinNode((BlockBasic) null);
			return;
		case IrSpecificPackage.BLOCK_FOR__LINE_NUMBER:
			setLineNumber(LINE_NUMBER_EDEFAULT);
			return;
		case IrSpecificPackage.BLOCK_FOR__NODES:
			getNodes().clear();
			return;
		case IrSpecificPackage.BLOCK_FOR__STEP:
			setStep((Instruction) null);
			return;
		case IrSpecificPackage.BLOCK_FOR__INIT:
			setInit((Instruction) null);
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
		case IrSpecificPackage.BLOCK_FOR__JOIN_NODE:
			return joinNode != null;
		case IrSpecificPackage.BLOCK_FOR__LINE_NUMBER:
			return lineNumber != LINE_NUMBER_EDEFAULT;
		case IrSpecificPackage.BLOCK_FOR__NODES:
			return nodes != null && !nodes.isEmpty();
		case IrSpecificPackage.BLOCK_FOR__STEP:
			return step != null;
		case IrSpecificPackage.BLOCK_FOR__INIT:
			return init != null;
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
	public boolean isNodeFor() {
		return true;
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

} //NodeForImpl
