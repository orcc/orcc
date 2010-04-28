package net.sf.orcc.tools.staticanalyzer;

import java.util.HashMap;
import java.util.Map;

import net.sf.orcc.network.Connection;
import net.sf.orcc.network.Network;
import net.sf.orcc.network.Vertex;

import org.jgrapht.DirectedGraph;

public class BufferManager {


	private Map<Connection, Integer> bufferCapacities = new HashMap<Connection, Integer>();

	private DirectedGraph<Vertex, Connection> graph;

	public BufferManager(Network network) {
		graph = network.getGraph();
	}

	public void instrument(Schedule schedule) {
		
		int rep = schedule.getIterationCount();
		for (Iterand iterand : schedule.getIterands()) {
			if (iterand.isVertex()) {				
				Vertex vertex = iterand.getVertex();
				for (Connection connection : graph.outgoingEdgesOf(vertex)) {
					int prd = connection.getSource().getNumTokensProduced();
					bufferCapacities.put(connection, rep * prd);
				}
			} else {
				instrument(iterand.getSchedule());
			}
		}
	}
	
	public Map<Connection, Integer> getBufferCapacities() {
		return bufferCapacities;
	}
}
