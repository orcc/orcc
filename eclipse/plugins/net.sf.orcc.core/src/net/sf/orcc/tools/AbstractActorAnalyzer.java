package net.sf.orcc.tools;

import net.sf.orcc.OrccException;
import net.sf.orcc.ir.Actor;

public abstract class AbstractActorAnalyzer {	
	
	/**
	 * Transforms the given actor.
	 * 
	 * @param actor
	 *            the actor
	 */
	abstract public void transform(Actor actor) throws OrccException;

	/**
	 * Analyze the given actor.
	 * 
	 * @param actor
	 *            the actor
	 */
	abstract public void analyze(Actor actor) throws OrccException;

}
