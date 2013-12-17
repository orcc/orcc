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
	public void optimizeOutput(Network network, boolean outputBuffering) {
		for (Actor actor : network.getAllActors()) {
			if (outputBuffering) {
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
			} else {
				for (Port port : actor.getOutputs()) {
					port.setNumTokensProduced(-1);
				}
			}
		}
	}

	// Disable actor input buffering for actors that
	// naturally have a high input token rate
	public void optimizeInput(Network network, boolean inputBuffering) {
		for (Actor actor : network.getAllActors()) {
			if (inputBuffering) {
				int maxRate = 0;
				for (Port port : actor.getInputs()) {
					if (port.getNumTokensConsumed() > maxRate)
						maxRate = port.getNumTokensConsumed();
				}
				if (maxRate >= RATE_LIMIT) {
					if (!actor.hasAttribute("variableInputPattern")) {
						actor.addAttribute("variableInputPattern");
					}
				}
			} else {
				actor.addAttribute("variableInputPattern");
			}
		}
	}
}
