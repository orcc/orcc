package net.sf.orcc.backends.c.dal;

import net.sf.orcc.df.Actor;
import net.sf.orcc.df.Port;
import net.sf.orcc.df.Network;

/**
 * Class for optimizing the code generation for
 * individual actors
 * 
 * @author Jani Boutellier
 * 
 */
public class ActorOptimizer {

	private final int RATE_LIMIT = 8;
	
	// Disable actor output buffering from actors that
	// naturally have a high token rate
	public void optimizeOutput(Network network) {
		for (Actor actor : network.getAllActors()) {
			int maxRate = 0;
			for (Port port : actor.getOutputs()) {
				if (port.getNumTokensProduced() > maxRate)
					maxRate = port.getNumTokensProduced();
			}
			if (maxRate >= RATE_LIMIT) {
				for (Port port : actor.getOutputs()) {
					port.setNumTokensProduced(-1);
				}
			}
		}
	}

}
