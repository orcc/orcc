package net.sf.orcc.backends.c.dal;

import net.sf.orcc.df.Actor;
import net.sf.orcc.df.Port;
import net.sf.orcc.df.Network;
import net.sf.orcc.moc.MoC;
import net.sf.orcc.moc.MocFactory;
import net.sf.orcc.util.OrccLogger;

/**
 * Class for optimizing the code generation for
 * individual actors
 * 
 * @author Jani Boutellier
 * 
 */
public class ActorOptimizer {

	// Disable actor output buffering from actors that
	// naturally have a high token rate
	public void optimizeOutput(Network network, boolean outputBuffering, int fifoSize) {
		for (Actor actor : network.getAllActors()) {
			boolean isSDF = false;
			if (actor.getMoC() != null) {
				isSDF = actor.getMoC().isSDF();
			}
			if (outputBuffering && !isSDF) {
				int maxRate = 0;
				for (Port port : actor.getOutputs()) {
					if (port.getNumTokensProduced() > maxRate)
						maxRate = port.getNumTokensProduced();
				}
				if (maxRate == 0) {
					for (Port port : actor.getOutputs()) {
						port.setNumTokensProduced(-port.getNumTokensProduced());
					}
				} else if (maxRate > Math.sqrt(fifoSize)) {
					for (Port port : actor.getOutputs()) {
						port.setNumTokensProduced(-port.getNumTokensProduced());
					}
				}
			} else {
				for (Port port : actor.getOutputs()) {
					port.setNumTokensProduced(-port.getNumTokensProduced());
				}
			}
		}
	}

	// Disable actor input buffering for actors that
	// naturally have a high input token rate
	public void optimizeInput(Network network, boolean inputBuffering, int fifoSize) {
		for (Actor actor : network.getAllActors()) {
			boolean isSDF = false;
			if (actor.getMoC() != null) {
				isSDF = actor.getMoC().isSDF();
			}
			if (inputBuffering && !isSDF) {
				int maxRate = 0;
				for (Port port : actor.getInputs()) {
					if (port.getNumTokensConsumed() > maxRate)
						maxRate = port.getNumTokensConsumed();
				}
				if (maxRate >= Math.sqrt(fifoSize)) {
					if (!actor.hasAttribute("variableInputPattern")) {
						actor.addAttribute("variableInputPattern");
					}
				}
			} else {
				actor.addAttribute("variableInputPattern");
			}
		}
	}

	// Disable SDF transformation for actors that
	// naturally have a high token rate
	public void treatMoC(Network network, int fifoSize) {
		for (Actor actor : network.getAllActors()) {
			boolean isSDF = false;
			if (actor.getMoC() != null) {
				isSDF = actor.getMoC().isSDF();
			}
			int maxRate = 0;
			for (Port port : actor.getInputs()) {
				if (port.getNumTokensConsumed() > maxRate)
					maxRate = port.getNumTokensConsumed();
			}
			for (Port port : actor.getOutputs()) {
				if (port.getNumTokensProduced() > maxRate)
					maxRate = port.getNumTokensProduced();
			}
			if (maxRate >= Math.sqrt(fifoSize)) {
				if (isSDF) {
					MoC moc = MocFactory.eINSTANCE.createDPNMoC();
					actor.setMoC(moc);
					isSDF = false;
				}
			}
			if (isSDF) {
				OrccLogger.traceln("Actor " + actor.getName() + " is optimized for static behavior");
			}
		}
	}
}
