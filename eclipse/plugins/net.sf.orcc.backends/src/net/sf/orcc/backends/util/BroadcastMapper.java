package net.sf.orcc.backends.util;

import net.sf.orcc.df.Actor;
import net.sf.orcc.df.Network;
import net.sf.orcc.graph.Vertex;
import net.sf.orcc.util.OrccLogger;

/**
 * @author Jani Boutellier
 *
 */
public class BroadcastMapper {

	public void prepareBroadcastMapping(Network network) {
		for (Actor actor : network.getAllActors()) {
			if (isBroadcast(actor)) {
				Vertex pred = findPredecessor(actor.getPredecessors().get(0));
				if (pred != null) {
					actor.setAttribute("broadcastOrigin", (pred.getAdapter(Actor.class)).getName());
				}
			}
		}
	}

	private Vertex findPredecessor(Vertex origin) {
		Vertex current = origin;
		while (isBroadcast(current.getAdapter(Actor.class))) {
			if (current.getPredecessors().size() > 0) {
				current = current.getPredecessors().get(0);
			} else {
				OrccLogger.warnln("No real source actor found for " + origin.getLabel());
				return null;
			}
		}
		return current;
	}

	private boolean isBroadcast(Actor actor) {
		if (actor.getActions().size() == 1 &&
				actor.getActions().get(0).getName().equals("copy")) {
			return true;
		}
		return false;
	}

}
