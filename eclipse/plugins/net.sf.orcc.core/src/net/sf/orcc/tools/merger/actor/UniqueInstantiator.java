package net.sf.orcc.tools.merger.actor;

import java.util.HashMap;
import java.util.Map;

import net.sf.orcc.df.Actor;
import net.sf.orcc.df.Connection;
import net.sf.orcc.df.Instance;
import net.sf.orcc.df.Network;
import net.sf.orcc.df.Port;
import net.sf.orcc.df.util.DfSwitch;
import net.sf.orcc.graph.Vertex;
import net.sf.orcc.ir.util.IrUtil;

public class UniqueInstantiator extends DfSwitch<Void> {

	private Map<Actor, Integer> actors = new HashMap<Actor, Integer>();

	@Override
	public Void caseNetwork(Network network) {
		for (Instance instance : network.getInstances()) {
			Actor actor = instance.getActor();
			if (actors.containsKey(actor)) {
				Actor copy = IrUtil.copy(actor);
				instance.setEntity(copy);
				int val = actors.get(actor);
				copy.setName(actor.getName() + "_" + val);
				actors.put(actor, ++val);
			} else {
				actors.put(actor, 0);
			}
		}

		for (Connection connection : network.getConnections()) {
			updateConnection(connection);
		}

		return null;
	}

	private void updateConnection(Connection connection) {
		Vertex srcVertex = connection.getSource();
		Vertex tgtVertex = connection.getTarget();

		if (srcVertex instanceof Instance) {
			Instance source = (Instance) srcVertex;
			String srcPortName = connection.getSourcePort().getName();
			Port srcPort = source.getActor().getOutput(srcPortName);
			connection.setSourcePort(srcPort);
		}

		if (tgtVertex instanceof Instance) {
			Instance target = (Instance) tgtVertex;
			String dstPortName = connection.getTargetPort().getName();
			Port dstPort = target.getActor().getInput(dstPortName);
			connection.setTargetPort(dstPort);
		}
	}

}
