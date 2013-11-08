package net.sf.orcc.tools.merger.actor;

import java.util.HashMap;
import java.util.Map;

import net.sf.orcc.df.Action;
import net.sf.orcc.df.Actor;
import net.sf.orcc.df.Connection;
import net.sf.orcc.df.Network;
import net.sf.orcc.df.Pattern;
import net.sf.orcc.df.Port;
import net.sf.orcc.graph.Edge;

/**
 * This class computer buffer sizes for a given schedule.
 * The functionality used to be inside ScheduleParser.
 * 
 * @author Jani Boutellier
 * @author Ghislain Roquier
 * 
 */

public class BufferSizer {

	private Map<Connection, Integer> maxTokens;

	private Map<Connection, Integer> tokens;

	private Network network;

	public BufferSizer(Network network) {
		this.network = network;
	}
	
	/**
	 * @return
	 */
	public Map<Connection, Integer> getMaxTokens(Schedule schedule) {
		if (maxTokens == null) {
			maxTokens = new HashMap<Connection, Integer>();
			tokens = new HashMap<Connection, Integer>();

			for (Connection connection : network.getConnections()) {
				Actor src = connection.getSource().getAdapter(Actor.class);
				Actor tgt = connection.getTarget().getAdapter(Actor.class);
				if (src != null && tgt != null)
					maxTokens.put(connection, 0);
				tokens.put(connection, 0);
			}
			computeMemoryBound(schedule);
		}
		return maxTokens;
	}

	/**
	 * @param schedule
	 */
	private void computeMemoryBound(Schedule schedule) {
		for (Iterand iterand : schedule.getIterands()) {
			Actor actor = locateActionOwner(iterand.getAction());
			for (Edge edge : actor.getIncoming()) {
				Connection conn = (Connection) edge;
				Actor src = conn.getSource().getAdapter(Actor.class);
				if (src != null) {
					Connection connection = (Connection) edge;
					Pattern inputPattern = iterand.getAction().getInputPattern();
					Port targetPort = connection.getTargetPort();
					int cns = inputPattern.getNumTokens(targetPort);
					tokens.put(connection, tokens.get(connection) - cns);
				}
			}

			for (Edge edge : actor.getOutgoing()) {
				Connection conn = (Connection) edge;
				Actor tgt = conn.getTarget().getAdapter(Actor.class);
				if (tgt != null) {
					Connection connection = (Connection) edge;
					Pattern outputPattern = iterand.getAction().getOutputPattern();
					Port sourcePort = connection.getSourcePort();
					int current = tokens.get(connection);
					int max = maxTokens.get(connection);
					int prd = outputPattern.getNumTokens(sourcePort);
					tokens.put(connection, current + prd);
					maxTokens.put(connection, max + prd);
				}
			}
		}
	}
	
	private Actor locateActionOwner(Action act) {
		for (Actor actor : network.getAllActors()) {
			for (Action action : actor.getActions()) {
				if (act.equals(action)) {
					return actor;
				}
			}
		}
		return null;
	}

}
