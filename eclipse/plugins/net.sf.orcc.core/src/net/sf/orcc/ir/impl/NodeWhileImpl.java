/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package net.sf.orcc.ir.impl;

import java.util.Collection;
import java.util.List;

import net.sf.orcc.ir.CFGNode;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.IrPackage;
import net.sf.orcc.ir.Location;
import net.sf.orcc.ir.NodeBlock;
import net.sf.orcc.ir.NodeWhile;
import net.sf.orcc.ir.Procedure;
import net.sf.orcc.ir.nodes.NodeInterpreter;
import net.sf.orcc.ir.nodes.NodeVisitor;
import net.sf.orcc.ir.util.CommonNodeOperations;

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
 * <em><b>Node While</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link net.sf.orcc.ir.impl.NodeWhileImpl#getJoinNode <em>Join Node</em>}</li>
 *   <li>{@link net.sf.orcc.ir.impl.NodeWhileImpl#getNodes <em>Nodes</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class NodeWhileImpl extends CFGNodeImpl implements NodeWhile {
	/**
	 * The cached value of the '{@link #getJoinNode() <em>Join Node</em>}' containment reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getJoinNode()
	 * @generated
	 * @ordered
	 */
	protected NodeBlock joinNode;

	/**
	 * The cached value of the '{@link #getNodes() <em>Nodes</em>}' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getNodes()
	 * @generated
	 * @ordered
	 */
	protected EList<CFGNode> nodes;

	private Expression value;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	protected NodeWhileImpl() {
		super();
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
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetJoinNode(NodeBlock newJoinNode,
			NotificationChain msgs) {
		NodeBlock oldJoinNode = joinNode;
		joinNode = newJoinNode;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, IrPackage.NODE_WHILE__JOIN_NODE, oldJoinNode, newJoinNode);
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
			case IrPackage.NODE_WHILE__JOIN_NODE:
				return getJoinNode();
			case IrPackage.NODE_WHILE__NODES:
				return getNodes();
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
			case IrPackage.NODE_WHILE__JOIN_NODE:
				return basicSetJoinNode(null, msgs);
			case IrPackage.NODE_WHILE__NODES:
				return ((InternalEList<?>)getNodes()).basicRemove(otherEnd, msgs);
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
			case IrPackage.NODE_WHILE__JOIN_NODE:
				return joinNode != null;
			case IrPackage.NODE_WHILE__NODES:
				return nodes != null && !nodes.isEmpty();
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
			case IrPackage.NODE_WHILE__JOIN_NODE:
				setJoinNode((NodeBlock)newValue);
				return;
			case IrPackage.NODE_WHILE__NODES:
				getNodes().clear();
				getNodes().addAll((Collection<? extends CFGNode>)newValue);
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
		return IrPackage.Literals.NODE_WHILE;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case IrPackage.NODE_WHILE__JOIN_NODE:
				setJoinNode((NodeBlock)null);
				return;
			case IrPackage.NODE_WHILE__NODES:
				getNodes().clear();
				return;
		}
		super.eUnset(featureID);
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
	public EList<CFGNode> getNodes() {
		if (nodes == null) {
			nodes = new EObjectContainmentEList<CFGNode>(CFGNode.class, this, IrPackage.NODE_WHILE__NODES);
		}
		return nodes;
	}

	@Override
	public Expression getValue() {
		return value;
	}

	@Override
	public void internalSetValue(Expression value) {
		this.value = value;
	}

	@Override
	public boolean isWhileNode() {
		return true;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public void setJoinNode(NodeBlock newJoinNode) {
		if (newJoinNode != joinNode) {
			NotificationChain msgs = null;
			if (joinNode != null)
				msgs = ((InternalEObject)joinNode).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - IrPackage.NODE_WHILE__JOIN_NODE, null, msgs);
			if (newJoinNode != null)
				msgs = ((InternalEObject)newJoinNode).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - IrPackage.NODE_WHILE__JOIN_NODE, null, msgs);
			msgs = basicSetJoinNode(newJoinNode, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, IrPackage.NODE_WHILE__JOIN_NODE, newJoinNode, newJoinNode));
	}

	@Override
	public void setValue(Expression value) {
		CommonNodeOperations.setValue(this, value);
	}

	@Override
	public String toString() {
		return "while (" + getValue() + ")";
	}

} // NodeWhileImpl
