package net.sf.orcc.backends.c.dal;

import java.util.Collection;

import net.sf.orcc.ir.Instruction;
import net.sf.orcc.ir.Var;

/**
 * Defines an input token to be evaluated as part of the actor constraints
 *
 * @author James Guthrie
 *
 */
public interface Token extends Comparable<Token>{

	/**
	 * The instruction which is encapsulated within this token
	 * @return
	 */
	public Instruction getInstruction();

	/**
	 * The target var of this token
	 * @return
	 */
	public Var getTargetVar();

	/**
	 * Whether this token is a state token
	 * @return
	 */
	public boolean isStateToken();

	/**
	 * Whether this token is an input token
	 * @return
	 */
	public boolean isInputToken();

	/**
	 * The defs upon which this token depends
	 * @return
	 */
	public Collection<Var> dependencies();

	/**
	 * Whether the var in the def of this token is in vars
	 * @param vars
	 * @return
	 */
	public boolean in(Collection<Var> vars);

	/**
	 * Whether all dependencies are in vars
	 *
	 * @param vars
	 * @return
	 */
	public boolean depsFulfilledBy(Collection<Var> vars);

}
