/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package net.sf.orcc.ir.impl;

import net.sf.dftools.graph.impl.VertexImpl;
import net.sf.orcc.ir.IrPackage;
import net.sf.orcc.ir.Node;
import net.sf.orcc.ir.Procedure;
import net.sf.orcc.util.EcoreHelper;

import org.eclipse.emf.ecore.EClass;

/**
 * <!-- begin-user-doc --> An implementation of the model object '
 * <em><b>CFG Node</b></em>'. <!-- end-user-doc -->
 * <p>
 * </p>
 *
 * @generated
 */
public abstract class NodeImpl extends VertexImpl implements Node {

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
