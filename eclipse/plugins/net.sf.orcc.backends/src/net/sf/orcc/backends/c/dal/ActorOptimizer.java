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

	public void optimizeOutput(Network network, boolean outputBuffering, int fifoSize) {
		for (Actor actor : network.getAllActors()) {
			boolean isSDF = false;
			if (actor.getMoC() != null) {
				isSDF = actor.getMoC().isSDF();
			}
			if (outputBuffering && !isSDF) {
			} else {
				for (Port port : actor.getOutputs()) {
					port.setNumTokensProduced(-port.getNumTokensProduced());
				}
			}
		}
	}

	public void optimizeInput(Network network, boolean inputBuffering, int fifoSize) {
		for (Actor actor : network.getAllActors()) {
			boolean isSDF = false;
			if (actor.getMoC() != null) {
				isSDF = actor.getMoC().isSDF();
			}
			if (inputBuffering && !isSDF) {
			} else {
				actor.addAttribute("variableInputPattern");
			}
		}
	}
}
