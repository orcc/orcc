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
import net.sf.orcc.ir.CFGNode;
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
public abstract class CFGNodeImpl extends UserImpl implements CFGNode {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected CFGNodeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return IrPackage.Literals.CFG_NODE;
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
		CFGNodeImpl.labelCount = labelCount;
	}

	private int label;

	private Location location;

	private Procedure procedure;

	protected CFGNodeImpl(Location location, Procedure procedure) {
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
	public List<CFGNode> getPredecessors() {
		if (procedure == null) {
			return null;
		}

		CFG cfg = procedure.getCFG();
		if (cfg == null) {
			return null;
		}

		Set<CFGEdge> edges = cfg.incomingEdgesOf(this);
		List<CFGNode> predecessors = new ArrayList<CFGNode>(edges.size());
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
	public List<CFGNode> getSuccessors() {
		if (procedure == null) {
			return null;
		}

		CFG cfg = procedure.getCFG();
		if (cfg == null) {
			return null;
		}

		Set<CFGEdge> edges = cfg.outgoingEdgesOf(this);
		List<CFGNode> successors = new ArrayList<CFGNode>(edges.size());
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

} //CFGNodeImpl
