package net.sf.orcc.tools.staticanalyzer;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import net.sf.orcc.network.Connection;
import net.sf.orcc.network.Network;
import net.sf.orcc.network.Vertex;

import org.jgrapht.DirectedGraph;

public class BufferManager {

	private DirectedGraph<Vertex, Connection> graph;

	public BufferManager(Network network) {
		graph = network.getGraph();
	}

	public Map<Connection, Integer> getBufferCapacities(Schedule schedule) {

		Map<Connection, Integer> bufferCapacities = new HashMap<Connection, Integer>();

		LinkedList<Iterand> stack = new LinkedList<Iterand>(schedule
				.getIterands());
		
		int rep = schedule.getIterationCount();
		
		while (!stack.isEmpty()) {
			Iterand iterand = stack.pop();

			if (iterand.isVertex()) {
				Vertex vertex = iterand.getVertex();
				
				for (Connection connection : graph.outgoingEdgesOf(vertex)) {
					int prd = connection.getSource().getNumTokensProduced();
					bufferCapacities.put(connection, rep * prd);
				}

				for (Connection connection : graph.incomingEdgesOf(vertex)) {
					if (!bufferCapacities.containsKey(connection)) {
						int cns = connection.getTarget().getNumTokensConsumed();
						bufferCapacities.put(connection, rep * cns);
					}
				}
			} else {
				Schedule sched = iterand.getSchedule();
				rep = sched.getIterationCount();
				for (Iterand subIterand : sched.getIterands()) {
					stack.push(subIterand);
				}
			}
		}
		return bufferCapacities;
	}
	
}
