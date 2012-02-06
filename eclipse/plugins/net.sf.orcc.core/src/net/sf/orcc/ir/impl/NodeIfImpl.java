/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package net.sf.orcc.ir.impl;

import java.util.Collection;
import java.util.List;

import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.IrPackage;
import net.sf.orcc.ir.Node;
import net.sf.orcc.ir.NodeBlock;
import net.sf.orcc.ir.NodeIf;

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
 * <em><b>Node If</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link net.sf.orcc.ir.impl.NodeIfImpl#getCondition <em>Condition</em>}</li>
 *   <li>{@link net.sf.orcc.ir.impl.NodeIfImpl#getElseNodes <em>Else Nodes</em>}</li>
 *   <li>{@link net.sf.orcc.ir.impl.NodeIfImpl#getJoinNode <em>Join Node</em>}</li>
 *   <li>{@link net.sf.orcc.ir.impl.NodeIfImpl#getLineNumber <em>Line Number</em>}</li>
 *   <li>{@link net.sf.orcc.ir.impl.NodeIfImpl#getThenNodes <em>Then Nodes</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class NodeIfImpl extends NodeImpl implements NodeIf {
	/**
	 * The cached value of the '{@link #getCondition() <em>Condition</em>}' containment reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getCondition()
	 * @generated
	 * @ordered
	 */
	protected Expression condition;

	/**
	 * The cached value of the '{@link #getElseNodes() <em>Else Nodes</em>}' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getElseNodes()
	 * @generated
	 * @ordered
	 */
	protected EList<Node> elseNodes;

	/**
	 * The cached value of the '{@link #getJoinNode() <em>Join Node</em>}' containment reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getJoinNode()
	 * @generated
	 * @ordered
	 */
	protected NodeBlock joinNode;

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
	 * The cached value of the '{@link #getThenNodes() <em>Then Nodes</em>}' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getThenNodes()
	 * @generated
	 * @ordered
	 */
	protected EList<Node> thenNodes;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	protected NodeIfImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetCondition(Expression newCondition,
			NotificationChain msgs) {
		Expression oldCondition = condition;
		condition = newCondition;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, IrPackage.NODE_IF__CONDITION, oldCondition, newCondition);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetJoinNode(NodeBlock newJoinNode,
			NotificationChain msgs) {
		NodeBlock oldJoinNode = joinNode;
		joinNode = newJoinNode;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, IrPackage.NODE_IF__JOIN_NODE, oldJoinNode, newJoinNode);
			if (msgs == null) msgs = notification; else msgs.add(notification);
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
			case IrPackage.NODE_IF__CONDITION:
				return getCondition();
			case IrPackage.NODE_IF__ELSE_NODES:
				return getElseNodes();
			case IrPackage.NODE_IF__JOIN_NODE:
				return getJoinNode();
			case IrPackage.NODE_IF__LINE_NUMBER:
				return getLineNumber();
			case IrPackage.NODE_IF__THEN_NODES:
				return getThenNodes();
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
			case IrPackage.NODE_IF__CONDITION:
				return basicSetCondition(null, msgs);
			case IrPackage.NODE_IF__ELSE_NODES:
				return ((InternalEList<?>)getElseNodes()).basicRemove(otherEnd, msgs);
			case IrPackage.NODE_IF__JOIN_NODE:
				return basicSetJoinNode(null, msgs);
			case IrPackage.NODE_IF__THEN_NODES:
				return ((InternalEList<?>)getThenNodes()).basicRemove(otherEnd, msgs);
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
			case IrPackage.NODE_IF__CONDITION:
				return condition != null;
			case IrPackage.NODE_IF__ELSE_NODES:
				return elseNodes != null && !elseNodes.isEmpty();
			case IrPackage.NODE_IF__JOIN_NODE:
				return joinNode != null;
			case IrPackage.NODE_IF__LINE_NUMBER:
				return lineNumber != LINE_NUMBER_EDEFAULT;
			case IrPackage.NODE_IF__THEN_NODES:
				return thenNodes != null && !thenNodes.isEmpty();
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
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

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case IrPackage.NODE_IF__CONDITION:
				setCondition((Expression)newValue);
				return;
			case IrPackage.NODE_IF__ELSE_NODES:
				getElseNodes().clear();
				getElseNodes().addAll((Collection<? extends Node>)newValue);
				return;
			case IrPackage.NODE_IF__JOIN_NODE:
				setJoinNode((NodeBlock)newValue);
				return;
			case IrPackage.NODE_IF__LINE_NUMBER:
				setLineNumber((Integer)newValue);
				return;
			case IrPackage.NODE_IF__THEN_NODES:
				getThenNodes().clear();
				getThenNodes().addAll((Collection<? extends Node>)newValue);
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
		return IrPackage.Literals.NODE_IF;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case IrPackage.NODE_IF__CONDITION:
				setCondition((Expression)null);
				return;
			case IrPackage.NODE_IF__ELSE_NODES:
				getElseNodes().clear();
				return;
			case IrPackage.NODE_IF__JOIN_NODE:
				setJoinNode((NodeBlock)null);
				return;
			case IrPackage.NODE_IF__LINE_NUMBER:
				setLineNumber(LINE_NUMBER_EDEFAULT);
				return;
			case IrPackage.NODE_IF__THEN_NODES:
				getThenNodes().clear();
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
	public EList<Node> getElseNodes() {
		if (elseNodes == null) {
			elseNodes = new EObjectContainmentEList<Node>(Node.class, this, IrPackage.NODE_IF__ELSE_NODES);
		}
		return elseNodes;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public NodeBlock getJoinNode() {
		return joinNode;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Node> getThenNodes() {
		if (thenNodes == null) {
			thenNodes = new EObjectContainmentEList<Node>(Node.class, this, IrPackage.NODE_IF__THEN_NODES);
		}
		return thenNodes;
	}

	@Override
	public boolean isElseRequired() {
		List<Node> nodes = getElseNodes();
		if (nodes.isEmpty()) {
			return false;
		} else if (nodes.size() == 1) {
			Node node = nodes.get(0);
			if (node.isNodeBlock()) {
				return !((NodeBlock) node).getInstructions().isEmpty();
			}
		}

		// more than one node, or one non-empty block node
		return true;
	}

	@Override
	public boolean isNodeIf() {
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
				msgs = ((InternalEObject)condition).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - IrPackage.NODE_IF__CONDITION, null, msgs);
			if (newCondition != null)
				msgs = ((InternalEObject)newCondition).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - IrPackage.NODE_IF__CONDITION, null, msgs);
			msgs = basicSetCondition(newCondition, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, IrPackage.NODE_IF__CONDITION, newCondition, newCondition));
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
	public void setLineNumber(int newLineNumber) {
		int oldLineNumber = lineNumber;
		lineNumber = newLineNumber;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, IrPackage.NODE_IF__LINE_NUMBER, oldLineNumber, lineNumber));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public void setJoinNode(NodeBlock newJoinNode) {
		if (newJoinNode != joinNode) {
			NotificationChain msgs = null;
			if (joinNode != null)
				msgs = ((InternalEObject)joinNode).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - IrPackage.NODE_IF__JOIN_NODE, null, msgs);
			if (newJoinNode != null)
				msgs = ((InternalEObject)newJoinNode).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - IrPackage.NODE_IF__JOIN_NODE, null, msgs);
			msgs = basicSetJoinNode(newJoinNode, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, IrPackage.NODE_IF__JOIN_NODE, newJoinNode, newJoinNode));
	}

} // NodeIfImpl
