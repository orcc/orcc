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
import net.sf.orcc.ir.Node;
import net.sf.orcc.ir.IrPackage;
import net.sf.orcc.ir.Location;
import net.sf.orcc.ir.Procedure;

import org.eclipse.emf.ecore.EClass;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>CFG Node</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * </p>
 *
 * @generated
 */
public abstract class NodeImpl extends UserImpl implements Node {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected NodeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return IrPackage.Literals.NODE;
	}
	
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

	private Procedure procedure;

	protected NodeImpl(Location location, Procedure procedure) {
		this.location = location;
		this.procedure = procedure;
		labelCount++;
		this.label = labelCount;
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
		return procedure;
	}

	@Override
	public List<Node> getSuccessors() {
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
	public boolean isCFGNode() {
		return true;
	}

	@Override
	public boolean isIfNode() {
		return false;
	}

	@Override
	public boolean isInstruction() {
		return false;
	}

	@Override
	public boolean isWhileNode() {
		return false;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	@Override
	public void setProcedure(Procedure procedure) {
		this.procedure = procedure;
	}

} //NodeImpl
