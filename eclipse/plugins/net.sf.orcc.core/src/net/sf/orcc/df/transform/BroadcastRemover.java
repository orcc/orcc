package net.sf.orcc.df.transform;

import java.util.ArrayList;
import java.util.List;

import net.sf.orcc.df.Actor;
import net.sf.orcc.df.Connection;
import net.sf.orcc.df.DfFactory;
import net.sf.orcc.df.Network;
import net.sf.orcc.df.Port;
import net.sf.orcc.df.util.DfVisitor;
import net.sf.orcc.graph.Vertex;

/**
 * Removes broadcast actors from the network.
 * This class is used by the actor merger.
 * 
 * @author Jani Boutellier
 * 
 */
public class BroadcastRemover extends DfVisitor<Void> {

	private static DfFactory dfFactory = DfFactory.eINSTANCE;

	protected Network network;

	private List<Actor> targetActors;
	private List<Port> targetPorts;	
	private Actor sourceActor;
	private Port sourcePort;
	private Integer fifoSize;
	private boolean broadcastFound;

	@Override
	public Void caseNetwork(Network network) {
		this.network = network;
		boolean finished = false;
		while(!finished) {
			Vertex vertex = findBroadcast();
			if (vertex != null) {
				removeBroadcast(vertex);
				reconnect();
			} else {
				finished = true;
			}
		}
		return null;
	}

	private Vertex findBroadcast() {
		broadcastFound = false;
		for (Vertex vertex : network.getVertices()) {
			if (vertex instanceof Network) {
				new BroadcastRemover().doSwitch(vertex);
			} else {
				doSwitch(vertex);
				if (broadcastFound) {
					return vertex;
				}
			}
		}
		return null;
	}

	@Override
	public Void caseActor(Actor actor) {
		if (actor.getActions().size() == 1 &&
				actor.getActions().get(0).getName().equals("copy")) {
			findConnecting(actor);
			broadcastFound = true;
		}
		return null;
	}

	private void findConnecting(Actor actor) {
		targetActors = new ArrayList<Actor>();
		targetPorts = new ArrayList<Port>();

		Connection ci = actor.getIncomingPortMap().get(actor.getInputs().get(0));
		sourceActor = ci.getSource().getAdapter(Actor.class);
		sourcePort = ci.getSourcePort();
		fifoSize = ci.getSize();
		for (Port output : actor.getOutputs()) {
			for (Connection co : actor.getOutgoingPortMap().get(output)) {
				targetActors.add(co.getTarget().getAdapter(Actor.class));
				targetPorts.add(co.getTargetPort());
			}
		}
	}

	private void removeBroadcast(Vertex vertex) {
		network.remove(vertex);
	}

	private void reconnect() {
		for(int i = 0; i < targetActors.size(); i++) {
			network.getConnections().add(dfFactory.createConnection(sourceActor,
					sourcePort, targetActors.get(i), targetPorts.get(i),
					fifoSize));
		}
	}
}
