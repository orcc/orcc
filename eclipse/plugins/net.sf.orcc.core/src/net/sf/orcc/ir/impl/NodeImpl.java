/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package net.sf.orcc.ir.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import net.sf.orcc.ir.CFG;
import net.sf.orcc.ir.CFGEdge;
import net.sf.orcc.ir.IrPackage;
import net.sf.orcc.ir.Location;
import net.sf.orcc.ir.Node;
import net.sf.orcc.ir.Procedure;
import net.sf.orcc.ir.util.EcoreHelper;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc --> An implementation of the model object '
 * <em><b>CFG Node</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link net.sf.orcc.ir.impl.NodeImpl#getLocation <em>Location</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public abstract class NodeImpl extends EObjectImpl implements Node {
	private static int labelCount;

	/**
	 * Resets the label count. The label count is used to assign labels to newly
	 * created CFG nodes. Labels must be unique within a {@link Procedure},
	 * which means this function can NOT be called in the middle of a procedure.
	 */
	public static synchronized void resetLabelCount() {
		labelCount = 0;
	}

	/**
	 * Set the label count to the given value. This function should be called
	 * before visiting a procedure to ensure newly-created nodes do not have the
	 * same labels as existing nodes.
	 * 
	 * @param labelCount
	 *            new value of labelCount
	 * @see #resetLabelCount()
	 */
	public static void setLabelCount(int labelCount) {
		NodeImpl.labelCount = labelCount;
	}

	private int label;

	private Location location;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	protected NodeImpl() {
		super();
	}

	protected NodeImpl(Location location) {
		this.location = location;
		labelCount++;
		this.label = labelCount;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetLocation(Location newLocation, NotificationChain msgs) {
		Location oldLocation = location;
		location = newLocation;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, IrPackage.NODE__LOCATION, oldLocation, newLocation);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case IrPackage.NODE__LOCATION:
				return getLocation();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case IrPackage.NODE__LOCATION:
				return basicSetLocation(null, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case IrPackage.NODE__LOCATION:
				return location != null;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case IrPackage.NODE__LOCATION:
				setLocation((Location)newValue);
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
		return IrPackage.Literals.NODE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case IrPackage.NODE__LOCATION:
				setLocation((Location)null);
				return;
		}
		super.eUnset(featureID);
	}

	@Override
	public int getLabel() {
		return label;
	}

	@Override
	public Location getLocation() {
		return location;
	}

	@Override
	public List<Node> getPredecessors() {
		Procedure procedure = getProcedure();
		if (procedure == null) {
			return null;
		}

		CFG cfg = procedure.getCFG();
		if (cfg == null) {
			return null;
		}

		Set<CFGEdge> edges = cfg.incomingEdgesOf(this);
		List<Node> predecessors = new ArrayList<Node>(edges.size());
		for (CFGEdge edge : edges) {
			predecessors.add(cfg.getEdgeSource(edge));
		}
		return predecessors;
	}

	@Override
	public Procedure getProcedure() {
		return EcoreHelper.getContainerOfType(this, Procedure.class);
	}

	@Override
	public List<Node> getSuccessors() {
		Procedure procedure = getProcedure();
		if (procedure == null) {
			return null;
		}

		CFG cfg = procedure.getCFG();
		if (cfg == null) {
			return null;
		}

		Set<CFGEdge> edges = cfg.outgoingEdgesOf(this);
		List<Node> successors = new ArrayList<Node>(edges.size());
		for (CFGEdge edge : edges) {
			successors.add(cfg.getEdgeTarget(edge));
		}
		return successors;
	}

	@Override
	public boolean isBlockNode() {
		return false;
	}

	@Override
	public boolean isIfNode() {
		return false;
	}

	@Override
	public boolean isWhileNode() {
		return false;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

} // NodeImpl
