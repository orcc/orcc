/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package net.sf.orcc.ir;

/**
 * <!-- begin-user-doc -->This interface defines a block that is specific to a
 * given back-end.
 * 
 * @author Jerome Gorin<!-- end-user-doc -->
 *
 *
 * @see net.sf.orcc.ir.IrPackage#getBlockSpecific()
 * @model abstract="true"
 * @generated
 */
public interface BlockSpecific extends Block {

	/**
	 * Returns <code>true</code> if the instruction is a backend specific node.
	 * 
	 * @return <code>true</code> if the instruction is a backend specific node
	 */
	public boolean isNodeSpecific();

}
