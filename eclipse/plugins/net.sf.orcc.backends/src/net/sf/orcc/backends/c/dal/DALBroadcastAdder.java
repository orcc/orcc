package net.sf.orcc.backends.c.dal;

import java.util.ArrayList;
import java.util.List;

import net.sf.orcc.df.transform.BroadcastAdder;
import net.sf.orcc.df.Actor;
import net.sf.orcc.df.Connection;
import net.sf.orcc.df.Network;
import net.sf.orcc.df.Port;
import net.sf.orcc.graph.Vertex;

/**
 * Adds broadcast actors when needed
 * 
 * @author Jani Boutellier
 * 
 * Based on Orcc BroadcastAdder
 */
public class DALBroadcastAdder extends BroadcastAdder {

	@Override
	public Void caseNetwork(Network network) {
		this.network = network;
		// make a copy of the existing vertex set because the set returned is
		// modified when broadcasts are added
		List<Vertex> vertexSet = new ArrayList<Vertex>(network.getVertices());

		for (Vertex vertex : vertexSet) {
			if (vertex instanceof Network) {
				new DALBroadcastAdder().doSwitch(vertex);
			} else {
				doSwitch(vertex);
			}
		}

		handle(network);

		return null;
	}

	@Override
	public Void caseActor(Actor actor) {
		for(Port port : actor.getOutputs()) {
			List <Connection> connections = actor.getOutgoingPortMap().get(port);
			if (connections.size() > 1) {
				createBroadcast(actor.getName(), port, connections, "fire");
			}
		}
		return null;
	}

}
