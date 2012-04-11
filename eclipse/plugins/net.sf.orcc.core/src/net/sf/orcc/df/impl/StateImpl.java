/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package net.sf.orcc.df.impl;

import net.sf.dftools.graph.impl.VertexImpl;
import net.sf.orcc.df.DfPackage;
import net.sf.orcc.df.State;

import org.eclipse.emf.ecore.EClass;

/**
 * <!-- begin-user-doc --> An implementation of the model object '
 * <em><b>State</b></em>'. <!-- end-user-doc -->
 * <p>
 * </p>
 *
 * @generated
 */
public class StateImpl extends VertexImpl implements State {

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	protected StateImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return DfPackage.Literals.STATE;
	}

	@Override
	public String getName() {
		return (String) getAttribute("name").getValue();
	}

	@Override
	public void setName(String name) {
		setAttribute("name", name);
	}

} // StateImpl
