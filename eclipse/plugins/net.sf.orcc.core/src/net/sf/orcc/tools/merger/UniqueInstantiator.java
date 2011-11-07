package net.sf.orcc.tools.merger;

import java.util.HashMap;
import java.util.Map;

import net.sf.orcc.OrccException;
import net.sf.orcc.df.Actor;
import net.sf.orcc.df.Connection;
import net.sf.orcc.df.Instance;
import net.sf.orcc.df.Network;
import net.sf.orcc.df.Port;
import net.sf.orcc.df.Vertex;
import net.sf.orcc.df.transformations.INetworkTransformation;
import net.sf.orcc.ir.util.IrUtil;

public class UniqueInstantiator implements INetworkTransformation {

	private Map<Actor, Integer> actors = new HashMap<Actor, Integer>();

	@Override
	public void transform(Network network) throws OrccException {
		for (Instance instance : network.getInstances()) {
			Actor actor = instance.getActor();
			if (actors.containsKey(actor)) {
				Actor copy = IrUtil.copy(actor);
				instance.setContents(copy);
				int val = actors.get(actor);
				copy.setName(actor.getName() + "_" + val);
				actors.put(actor, val++);
			} else {
				actors.put(actor, 0);
			}
		}

		for (Connection connection : network.getConnections()) {
			updateConnection(connection);
		}

	}

	private void updateConnection(Connection connection) {
		Vertex srcVertex = connection.getSource();
		Vertex tgtVertex = connection.getTarget();

		if (srcVertex.isInstance()) {
			Instance source = (Instance) srcVertex;
			String srcPortName = connection.getSourcePort().getName();
			Port srcPort = source.getActor().getOutput(srcPortName);
			connection.setSourcePort(srcPort);
		}

		if (tgtVertex.isInstance()) {
			Instance target = (Instance) tgtVertex;
			String dstPortName = connection.getTargetPort().getName();
			Port dstPort = target.getActor().getInput(dstPortName);
			connection.setTargetPort(dstPort);
		}
	}

}
