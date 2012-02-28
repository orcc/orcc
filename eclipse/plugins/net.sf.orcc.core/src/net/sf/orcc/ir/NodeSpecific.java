/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package net.sf.orcc.ir;

/**
 * This interface defines a node that is specific to a given back-end
 *
 *
 * @author Jérôme Gorin
 * @model abstract="true" extends="net.sf.orcc.ir.Node"
 */
public interface NodeSpecific extends Node {
	/**
	 * Return <code>true</code> if the instruction is a NodeFor
	 * 
	 * @return <code>true</code> if the instruction is a NodeFor
	 */
	public boolean isNodeFor();
}
