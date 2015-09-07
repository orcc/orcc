package net.sf.orcc.backends.c.dal;

import net.sf.orcc.graph.Graph;
import net.sf.orcc.graph.Vertex;

/**
 * Class traverses a graph for
 * searching a predecessor with a specific name
 *
 * @author Jani Boutellier
 */
public class GraphTraverser {

	Graph graph;

	int distance;

	public GraphTraverser(Graph graph) {
		this.graph = graph;
	}

	private void searchPredecessor(Vertex v, String name, int dist) {
		if (v != null) {
			if (v.getLabel().equals(name)) {
				distance = dist;
			}
			if (v.getSuccessors().size() > 0) {
				for (Vertex p : v.getSuccessors()) {
					searchPredecessor(p, name, dist + 1);
				}
			}
		}
	}

	public int isPredecessor(String origin, String name) {
		distance = -1;
		for (Vertex v : graph.getVertices()) {
			if (v.getLabel().equals(origin)) {
				searchPredecessor(v, name, 0);
				return distance;
			}
		}
		return distance;
	}
}
