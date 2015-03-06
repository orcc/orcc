package net.sf.orcc.backends.c.dal;

import net.sf.orcc.df.Actor;
import net.sf.orcc.df.Connection;
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

	public void computeTokenSizes(Network network) {
		for (Connection conn : network.getConnections()) {
			Actor actor = conn.getTarget().getAdapter(Actor.class);
			if (actor.getMoC() == null) {
				OrccLogger.traceln("Actor classification has not been done " +
						"-- mandatory for OpenCL targets");
				return;
			} else if (!actor.getMoC().isSDF()) {
				OrccLogger.traceln("Actor " + actor.getName() +
						" does not appear to be SDF. OpenCL execution not " +
						"possible");
				return;
			} else {
				for (Port port : actor.getInputs()) {
					Integer maxIter = actor.getValueAsObject("MaxIter");
					Integer sz = new Integer(port.getNumTokensConsumed() *
							sizeOf(port.getType()) * maxIter.intValue());
					conn.setAttribute("TokenSize", sz);
				}
			}
		}
		// The second round is for verification only
		for (Connection conn : network.getConnections()) {
			Actor actor = conn.getSource().getAdapter(Actor.class);
			if (conn.hasAttribute("TokenSize")) {
				if (actor.getMoC().isSDF()) {
					for (Port port : actor.getOutputs()) {
						Integer maxIter = actor.getValueAsObject("MaxIter");
						int thisTokenSize = Math.abs(
								port.getNumTokensProduced()) *
								sizeOf(port.getType()) * maxIter;
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
			} else {
				OrccLogger.warnln("Connection " +
						printConnection(conn) +
						" does not have TokenSize attribute");
			}
		}
	}

	private int updateMaxIter(int defaultSize, Integer size, int maxIter, int rate) {
		if (size == null) {
			size = new Integer(defaultSize);
		}
		int tmpIter =  size.intValue() / rate;
		if (tmpIter < maxIter) {
			return tmpIter;
		}
		return maxIter;
	}

	public void computeMaxIter(Network network, int fifoSize) {
		for (Actor actor : network.getAllActors()) {
			if (actor.getMoC() == null) {
				continue;
			}
			int maxIter = 1000000;
			if (actor.getMoC().isSDF()) {
				if (actor.getInputs().size() > 0) {
					for (Port port : actor.getInputs()) {
						maxIter = updateMaxIter(fifoSize,
								actor.getIncomingPortMap().get(port).getSize(),
								maxIter, port.getNumTokensConsumed());
					}
				}
				if (actor.getOutputs().size() > 0) {
					for (Port port : actor.getOutputs()) {
						maxIter = updateMaxIter(fifoSize,
								actor.getOutgoingPortMap().get(port).get(0).getSize(),
								maxIter, Math.abs(port.getNumTokensProduced()));
					}
				}
				actor.setAttribute("MaxIter", new Integer(maxIter));
			} else if (!actor.hasAttribute("variableInputPattern")) {
				if (actor.getInputs().size() > 0) {
					for (Port port : actor.getInputs()) {
						maxIter = updateMaxIter(fifoSize,
								actor.getIncomingPortMap().get(port).getSize(),
								maxIter, port.getNumTokensConsumed());
					}
				}
				actor.setAttribute("MaxIter", new Integer(maxIter));
			}
		}
	}
}
