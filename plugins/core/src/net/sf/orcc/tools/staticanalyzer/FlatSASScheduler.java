package net.sf.orcc.tools.staticanalyzer;

import java.util.List;
import java.util.Map;

import net.sf.orcc.OrccException;
import net.sf.orcc.network.Connection;
import net.sf.orcc.network.Network;
import net.sf.orcc.network.Vertex;

import org.jgrapht.DirectedGraph;

public class FlatSASScheduler implements IScheduler {

	DirectedGraph<Vertex, Connection> graph;

	public Schedule schedule(Network network) throws OrccException {
		graph = network.getGraph();

		Map<Vertex, Integer> rep = new RepetitionVectorAnalyzer(network)
				.computeRepetitionsVector();

		Schedule topSched = new Schedule();

		List<Vertex> sort = new TopologicalSorter(graph).topologicalSort();
		for (Vertex vertex : sort) {
			if (vertex.isInstance()) {
				Schedule subSched = new Schedule();
				subSched.setIterationCount(rep.get(vertex));
				subSched.add(new Iterand(vertex));
				topSched.add(new Iterand(subSched));
			}
		}
		return topSched;
	}

}
