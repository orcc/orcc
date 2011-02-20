package net.sf.orcc.tools;

import java.util.HashMap;
import java.util.Map;

import net.sf.orcc.OrccException;
import net.sf.orcc.network.Connection;
import net.sf.orcc.network.Instance;
import net.sf.orcc.network.Network;

public abstract class AbstractNetworkAnalyzer {

	protected Map<String, Object> analysis;

	protected AbstractActorAnalyzer actorAnalyzer;

	public AbstractNetworkAnalyzer(AbstractActorAnalyzer actorAnalyzer) {
		this.actorAnalyzer = actorAnalyzer;
		this.analysis = new HashMap<String, Object>();
	}

	/**
	 * Analyze the given connection.
	 * 
	 * @param connection
	 *            the connection
	 */
	abstract protected void analyze(Connection connection) throws OrccException;

	/**
	 * Analyze the given network.
	 * 
	 * @param network
	 *            the network
	 */
	public void analyze(Network network) throws OrccException {
		for (Instance instance : network.getInstances()) {
			if (instance.isActor()) {
				actorAnalyzer.transform(instance.getActor());
				actorAnalyzer.analyze(instance.getActor());
			} else if (instance.isNetwork()) {
				analyze(instance.getNetwork());
			}
		}
		for (Connection connection : network.getConnections()) {
			analyze(connection);
		}
	}

	abstract public void printResults();

}
