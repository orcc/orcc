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
import net.sf.orcc.ir.Node;
import net.sf.orcc.ir.Procedure;
import net.sf.orcc.util.EcoreHelper;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc --> An implementation of the model object '
 * <em><b>CFG Node</b></em>'. <!-- end-user-doc -->
 * <p>
 * </p>
 *
 * @generated
 */
public abstract class NodeImpl extends EObjectImpl implements Node {

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	protected NodeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return IrPackage.Literals.NODE;
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
