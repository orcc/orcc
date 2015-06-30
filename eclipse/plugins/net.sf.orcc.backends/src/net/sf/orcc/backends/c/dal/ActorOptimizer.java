package net.sf.orcc.backends.c.dal;

import net.sf.orcc.df.Action;
import net.sf.orcc.df.Actor;
import net.sf.orcc.df.Connection;
import net.sf.orcc.df.Pattern;
import net.sf.orcc.df.Port;
import net.sf.orcc.df.Network;
import net.sf.orcc.ir.Type;
import net.sf.orcc.util.OrccLogger;

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
	private int sizeOf(Type type) {
		if (type.isFloat()) {
			return 4;
		} else if (type.isInt() || type.isUint()){
			if (type.getSizeInBits() > 16) {
				return 4;
			} else if (type.getSizeInBits() > 8) {
				return 2;
			}
		}
		return 1;
	}

	private String printConnection(Connection conn) {
		return new String(conn.getSource().getAdapter(Actor.class).getName() +
		"->" + conn.getTarget().getAdapter(Actor.class).getName());
	}

	private void setTokenSizeAndRate(Connection conn, Port port, int rate) {
		Integer sz = new Integer(sizeOf(port.getType()));
		conn.setAttribute("TokenSize", sz);
		conn.setAttribute("TokenRate", rate);
	}

	public void computeTokenSizes(Network network) {
		for (Connection conn : network.getConnections()) {
			Actor actor = conn.getTarget().getAdapter(Actor.class);
			for (Port port : actor.getInputs()) {
				if (conn.getTargetPort().equals(port)) {
					setTokenSizeAndRate(conn, port, port.getNumTokensConsumed());
				}
			}
		}

		for (Connection conn : network.getConnections()) {
			if (!conn.hasAttribute("TokenSize")) {
				Actor actor = conn.getSource().getAdapter(Actor.class);
				for (Port port : actor.getOutputs()) {
					if (conn.getSourcePort().equals(port)) {
						setTokenSizeAndRate(conn, port, -port.getNumTokensProduced());
					}
				}
			}
			if (conn.hasAttribute("TokenSize")) {
				Actor actor = conn.getSource().getAdapter(Actor.class);
				for (Port port : actor.getOutputs()) {
					if (conn.getSourcePort().equals(port)) {
						int thisTokenSize = sizeOf(port.getType());
						int initialTokens = getInitialTokens(actor, port) /
								(-port.getNumTokensProduced());
						conn.setAttribute("InitialTokens", initialTokens);
						Integer oldTokenSize = ((Integer)
								conn.getValueAsObject("TokenSize"));
						if (oldTokenSize == null) {
							OrccLogger.warnln("Connection " +
									printConnection(conn) +
									" token size is null");
						} else if (thisTokenSize != oldTokenSize.intValue()) {
							OrccLogger.warnln("Connection " +
									printConnection(conn) +
									" has a r/w data size mismatch: " +
									thisTokenSize + " vs " +
									oldTokenSize.intValue());
						}
					}
				}
			}
		}
	}

	private int getInitialTokens(Actor actor, Port port) {
		if (actor.getInitializes() != null) {
			for (Action i : actor.getInitializes()) {
				Pattern pat = i.getOutputPattern();
				if (pat.getNumTokensMap().containsKey(port)) {
					int tokens = pat.getNumTokensMap().get(port).intValue();
					OrccLogger.traceln(tokens + " initial samples detected in "
							+ actor.getName() + "_" + port.getName());
					return tokens;
				}
			}
		}
		return 0;
	}

}
