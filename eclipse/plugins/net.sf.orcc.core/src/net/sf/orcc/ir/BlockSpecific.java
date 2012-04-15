/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package net.sf.orcc.ir;

/**
 * This interface defines a block that is specific to a given back-end.
 * 
 * @author Jérôme Gorin
 * @model abstract="true"
 */
public interface BlockSpecific extends Block {

	/**
	 * Returns <code>true</code> if the instruction is a backend specific node.
	 * 
	 * @return <code>true</code> if the instruction is a backend specific node
	 */
	public boolean isNodeSpecific();

}
