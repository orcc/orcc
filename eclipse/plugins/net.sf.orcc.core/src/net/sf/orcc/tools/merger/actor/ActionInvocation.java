package net.sf.orcc.tools.merger.actor;

import net.sf.orcc.df.Actor;
import net.sf.orcc.df.Action;

/**
 * This class defines a single event (~action) in a schedule.
 * The class is analogous to Iterand.
 * 
 * @author Jani Boutellier
 * @author Ghislain Roquier
 * 
 */

public class ActionInvocation {

	private Actor actor;

	private Action action;

	public ActionInvocation(Actor actor, Action action) {
		this.actor = actor;
		this.action = action;
	}

	public Actor getActor() {
		return actor;
	}

	public Action getAction() {
		return action;
	}

	@Override
	public String toString() {
		Object obj = (Object)("" + (actor.getName() + "_" + (action.getName() + "")));
		return "" + obj + "";
	}

}
