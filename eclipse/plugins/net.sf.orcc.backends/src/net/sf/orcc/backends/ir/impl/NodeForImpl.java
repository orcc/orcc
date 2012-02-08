/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package net.sf.orcc.backends.ir.impl;

import java.util.Collection;

import net.sf.orcc.backends.ir.IrSpecificPackage;
import net.sf.orcc.backends.ir.NodeFor;

import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.Node;
import net.sf.orcc.ir.NodeBlock;

import net.sf.orcc.ir.impl.NodeSpecificImpl;

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
 *   <li>{@link net.sf.orcc.backends.ir.impl.NodeForImpl#getCondition <em>Condition</em>}</li>
 *   <li>{@link net.sf.orcc.backends.ir.impl.NodeForImpl#getJoinNode <em>Join Node</em>}</li>
 *   <li>{@link net.sf.orcc.backends.ir.impl.NodeForImpl#getLineNumber <em>Line Number</em>}</li>
 *   <li>{@link net.sf.orcc.backends.ir.impl.NodeForImpl#getNodes <em>Nodes</em>}</li>
 *   <li>{@link net.sf.orcc.backends.ir.impl.NodeForImpl#getLoopCounter <em>Loop Counter</em>}</li>
 *   <li>{@link net.sf.orcc.backends.ir.impl.NodeForImpl#getInit <em>Init</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class NodeForImpl extends NodeSpecificImpl implements NodeFor {
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
	protected NodeBlock joinNode;

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
	protected EList<Node> nodes;

	/**
	 * The cached value of the '{@link #getLoopCounter() <em>Loop Counter</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLoopCounter()
	 * @generated
	 * @ordered
	 */
	protected EList<Expression> loopCounter;

	/**
	 * The cached value of the '{@link #getInit() <em>Init</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getInit()
	 * @generated
	 * @ordered
	 */
	protected EList<Expression> init;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected NodeForImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return IrSpecificPackage.Literals.NODE_FOR;
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
	public NotificationChain basicSetCondition(Expression newCondition, NotificationChain msgs) {
		Expression oldCondition = condition;
		condition = newCondition;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, IrSpecificPackage.NODE_FOR__CONDITION, oldCondition, newCondition);
			if (msgs == null) msgs = notification; else msgs.add(notification);
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
				msgs = ((InternalEObject)condition).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - IrSpecificPackage.NODE_FOR__CONDITION, null, msgs);
			if (newCondition != null)
				msgs = ((InternalEObject)newCondition).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - IrSpecificPackage.NODE_FOR__CONDITION, null, msgs);
			msgs = basicSetCondition(newCondition, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, IrSpecificPackage.NODE_FOR__CONDITION, newCondition, newCondition));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NodeBlock getJoinNode() {
		return joinNode;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetJoinNode(NodeBlock newJoinNode, NotificationChain msgs) {
		NodeBlock oldJoinNode = joinNode;
		joinNode = newJoinNode;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, IrSpecificPackage.NODE_FOR__JOIN_NODE, oldJoinNode, newJoinNode);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setJoinNode(NodeBlock newJoinNode) {
		if (newJoinNode != joinNode) {
			NotificationChain msgs = null;
			if (joinNode != null)
				msgs = ((InternalEObject)joinNode).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - IrSpecificPackage.NODE_FOR__JOIN_NODE, null, msgs);
			if (newJoinNode != null)
				msgs = ((InternalEObject)newJoinNode).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - IrSpecificPackage.NODE_FOR__JOIN_NODE, null, msgs);
			msgs = basicSetJoinNode(newJoinNode, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, IrSpecificPackage.NODE_FOR__JOIN_NODE, newJoinNode, newJoinNode));
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
			eNotify(new ENotificationImpl(this, Notification.SET, IrSpecificPackage.NODE_FOR__LINE_NUMBER, oldLineNumber, lineNumber));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Node> getNodes() {
		if (nodes == null) {
			nodes = new EObjectContainmentEList<Node>(Node.class, this, IrSpecificPackage.NODE_FOR__NODES);
		}
		return nodes;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Expression> getLoopCounter() {
		if (loopCounter == null) {
			loopCounter = new EObjectContainmentEList<Expression>(Expression.class, this, IrSpecificPackage.NODE_FOR__LOOP_COUNTER);
		}
		return loopCounter;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Expression> getInit() {
		if (init == null) {
			init = new EObjectContainmentEList<Expression>(Expression.class, this, IrSpecificPackage.NODE_FOR__INIT);
		}
		return init;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case IrSpecificPackage.NODE_FOR__CONDITION:
				return basicSetCondition(null, msgs);
			case IrSpecificPackage.NODE_FOR__JOIN_NODE:
				return basicSetJoinNode(null, msgs);
			case IrSpecificPackage.NODE_FOR__NODES:
				return ((InternalEList<?>)getNodes()).basicRemove(otherEnd, msgs);
			case IrSpecificPackage.NODE_FOR__LOOP_COUNTER:
				return ((InternalEList<?>)getLoopCounter()).basicRemove(otherEnd, msgs);
			case IrSpecificPackage.NODE_FOR__INIT:
				return ((InternalEList<?>)getInit()).basicRemove(otherEnd, msgs);
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
			case IrSpecificPackage.NODE_FOR__CONDITION:
				return getCondition();
			case IrSpecificPackage.NODE_FOR__JOIN_NODE:
				return getJoinNode();
			case IrSpecificPackage.NODE_FOR__LINE_NUMBER:
				return getLineNumber();
			case IrSpecificPackage.NODE_FOR__NODES:
				return getNodes();
			case IrSpecificPackage.NODE_FOR__LOOP_COUNTER:
				return getLoopCounter();
			case IrSpecificPackage.NODE_FOR__INIT:
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
			case IrSpecificPackage.NODE_FOR__CONDITION:
				setCondition((Expression)newValue);
				return;
			case IrSpecificPackage.NODE_FOR__JOIN_NODE:
				setJoinNode((NodeBlock)newValue);
				return;
			case IrSpecificPackage.NODE_FOR__LINE_NUMBER:
				setLineNumber((Integer)newValue);
				return;
			case IrSpecificPackage.NODE_FOR__NODES:
				getNodes().clear();
				getNodes().addAll((Collection<? extends Node>)newValue);
				return;
			case IrSpecificPackage.NODE_FOR__LOOP_COUNTER:
				getLoopCounter().clear();
				getLoopCounter().addAll((Collection<? extends Expression>)newValue);
				return;
			case IrSpecificPackage.NODE_FOR__INIT:
				getInit().clear();
				getInit().addAll((Collection<? extends Expression>)newValue);
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
			case IrSpecificPackage.NODE_FOR__CONDITION:
				setCondition((Expression)null);
				return;
			case IrSpecificPackage.NODE_FOR__JOIN_NODE:
				setJoinNode((NodeBlock)null);
				return;
			case IrSpecificPackage.NODE_FOR__LINE_NUMBER:
				setLineNumber(LINE_NUMBER_EDEFAULT);
				return;
			case IrSpecificPackage.NODE_FOR__NODES:
				getNodes().clear();
				return;
			case IrSpecificPackage.NODE_FOR__LOOP_COUNTER:
				getLoopCounter().clear();
				return;
			case IrSpecificPackage.NODE_FOR__INIT:
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
			case IrSpecificPackage.NODE_FOR__CONDITION:
				return condition != null;
			case IrSpecificPackage.NODE_FOR__JOIN_NODE:
				return joinNode != null;
			case IrSpecificPackage.NODE_FOR__LINE_NUMBER:
				return lineNumber != LINE_NUMBER_EDEFAULT;
			case IrSpecificPackage.NODE_FOR__NODES:
				return nodes != null && !nodes.isEmpty();
			case IrSpecificPackage.NODE_FOR__LOOP_COUNTER:
				return loopCounter != null && !loopCounter.isEmpty();
			case IrSpecificPackage.NODE_FOR__INIT:
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
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (lineNumber: ");
		result.append(lineNumber);
		result.append(')');
		return result.toString();
	}
	
	@Override
	public boolean isNodeFor(){
		return true;	
	}

} //NodeForImpl
