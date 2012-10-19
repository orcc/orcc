/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package net.sf.orcc.ir.impl;

import net.sf.orcc.graph.Edge;
import net.sf.orcc.ir.CfgNode;
import net.sf.orcc.ir.IrPackage;
import net.sf.orcc.ir.Block;
import net.sf.orcc.ir.Procedure;
import net.sf.orcc.util.Attribute;
import net.sf.orcc.util.impl.AttributableImpl;
import net.sf.orcc.util.util.EcoreHelper;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc --> An implementation of the model object '
 * <em><b>CFG Node</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link net.sf.orcc.ir.impl.BlockImpl#getCfgNode <em>Cfg Node</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public abstract class BlockImpl extends AttributableImpl implements Block {

	/**
	 * The cached value of the '{@link #getCfgNode() <em>Cfg Node</em>}' reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getCfgNode()
	 * @generated
	 * @ordered
	 */
	protected CfgNode cfgNode;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	protected BlockImpl() {
		super();
	}

	@Override
	public Edge getEdgeFalse() {
		for (Edge edge : getCfgNode().getOutgoing()) {
			Attribute attr = edge.getAttribute("flag");
			if (attr == null) {
				return edge;
			}
			Object value = attr.getObjectValue();
			if (value == null) {
				return edge;
			}
			if (value instanceof Boolean && !(Boolean) value) {
				return edge;
			}
		}
		return null;
	}

	@Override
	public Edge getEdgeTrue() {
		for (Edge edge : getCfgNode().getOutgoing()) {
			Attribute attr = edge.getAttribute("flag");
			if (attr != null) {
				Object value = attr.getObjectValue();
				if (value instanceof Boolean && (Boolean) value) {
					return edge;
				}
			}
		}
		return null;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return IrPackage.Literals.BLOCK;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public CfgNode getCfgNode() {
		if (cfgNode != null && cfgNode.eIsProxy()) {
			InternalEObject oldCfgNode = (InternalEObject) cfgNode;
			cfgNode = (CfgNode) eResolveProxy(oldCfgNode);
			if (cfgNode != oldCfgNode) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE,
							IrPackage.BLOCK__CFG_NODE, oldCfgNode, cfgNode));
			}
		}
		return cfgNode;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public CfgNode basicGetCfgNode() {
		return cfgNode;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetCfgNode(CfgNode newCfgNode,
			NotificationChain msgs) {
		CfgNode oldCfgNode = cfgNode;
		cfgNode = newCfgNode;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this,
					Notification.SET, IrPackage.BLOCK__CFG_NODE, oldCfgNode,
					newCfgNode);
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
	public void setCfgNode(CfgNode newCfgNode) {
		if (newCfgNode != cfgNode) {
			NotificationChain msgs = null;
			if (cfgNode != null)
				msgs = ((InternalEObject) cfgNode).eInverseRemove(this,
						IrPackage.CFG_NODE__NODE, CfgNode.class, msgs);
			if (newCfgNode != null)
				msgs = ((InternalEObject) newCfgNode).eInverseAdd(this,
						IrPackage.CFG_NODE__NODE, CfgNode.class, msgs);
			msgs = basicSetCfgNode(newCfgNode, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					IrPackage.BLOCK__CFG_NODE, newCfgNode, newCfgNode));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd,
			int featureID, NotificationChain msgs) {
		switch (featureID) {
		case IrPackage.BLOCK__CFG_NODE:
			if (cfgNode != null)
				msgs = ((InternalEObject) cfgNode).eInverseRemove(this,
						IrPackage.CFG_NODE__NODE, CfgNode.class, msgs);
			return basicSetCfgNode((CfgNode) otherEnd, msgs);
		}
		return super.eInverseAdd(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd,
			int featureID, NotificationChain msgs) {
		switch (featureID) {
		case IrPackage.BLOCK__CFG_NODE:
			return basicSetCfgNode(null, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case IrPackage.BLOCK__CFG_NODE:
			if (resolve)
				return getCfgNode();
			return basicGetCfgNode();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
		case IrPackage.BLOCK__CFG_NODE:
			setCfgNode((CfgNode) newValue);
			return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
		case IrPackage.BLOCK__CFG_NODE:
			setCfgNode((CfgNode) null);
			return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
		case IrPackage.BLOCK__CFG_NODE:
			return cfgNode != null;
		}
		return super.eIsSet(featureID);
	}

	@Override
	public Procedure getProcedure() {
		return EcoreHelper.getContainerOfType(this, Procedure.class);
	}

	@Override
	public boolean isNodeBlock() {
		return false;
	}

	@Override
	public boolean isNodeIf() {
		return false;
	}

	@Override
	public boolean isNodeWhile() {
		return false;
	}

} // NodeImpl
